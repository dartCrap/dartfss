package com.dartcrab.extractor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.entities.Dls;
import com.dartcrab.entities.DlsIssueReport;
import com.dartcrab.entities.GenericDartReport;
import com.dartcrab.util.DartCrabSettings;
import com.dartcrab.util.DartHtmlProcessor;


/**
 * 파생결합증권 공시 보고서 처리용
 * 
 * @author Gi Kim
 * @version 1.0
 * @since Mar-25-2015
 */
public class DlsIssueReportExtractor extends ReportExtractor {
	private static Logger log = LoggerFactory.getLogger( DlsIssueReportExtractor.class );
	
	/*
	 * 현재의 구현 패턴은 다음과 같다.
	 * 
	 * - 우선 대부분의 정보는 <table/> 태그안에 포함되어 있다. 
	 * - HTML 내에 존재하는 <table/>의 위치는 하나의 보고서 유형 안에서는 대부분 같다.
	 * - 따라서 순서에 따라 각 테이블을 쪼갠 뒤, 테이블의 header와 content를 분리하여 DOM으로 만든다.
	 *  (이는 상당히 공통적인 로직이므로 dartcrab.util.DartHtmlProcessor에 library화 하였다.
	 *  보고서에 따라 특이한 테이블의 경우에는 각 Extractor내에서 구현하도록 한다.)
	 *  - 일단 DOM으로 만들고 나면, 각 Node의 이름(한글)을 보고, 어떤 정보인지 파악하도록 한다.
	 *  
	 *  파생결합증권 공시의 경우에는 하나의 공시보고서 내에, 다수 종목의 발행이 함께 묶여있다.
	 *  따라서 보고서의 내용을 다시 쪼개어 각각의 종목 정보로 나눌 필요가 있다.
	 *  
	 */
	public GenericDartReport extract(){
		DlsIssueReport report = new DlsIssueReport(this.getDoc().getHeader());
		
		String	allHtml = this.getDoc().getContents();
		
		/* 우선 보고서를 쪼갠다. 
		 * 아래와 같이 쪼개면  [0]은 일괄공시추가서류의 요약, [1] 이후는 각 종목별 보고서가 된다*/
		String [] sectionString = allHtml.split("\\[ 모집 또는 매출의 개요 \\]");
		
		List<Elements>	sectionElements = new ArrayList<Elements>();
		
		Elements cover = Jsoup.parse(sectionString[0]).getAllElements();
		
		__processCover(cover, report);
		
		//  각 종목별 발행 정보
		for (int i = 1 ; i < sectionString.length; i++){  // Divide...
			Dls dls = new Dls();
			
			sectionElements.add(Jsoup.parse(sectionString[i]).getAllElements());
			
			// 모집 또는 매출의 개요
			String table1 = sectionString[i]
						.substring(sectionString[i].indexOf("<p> &nbsp;<br />(2) 모집 또는 매출의 개요<br /> </p>"));
			
			table1 = table1.substring(table1.indexOf("<table"));
			
			table1 = table1.substring(0, table1.indexOf("</table>")+"</table>".length());
			
			__processTable1(Jsoup.parse(table1).getAllElements(), dls);
			
			// 상황별 손익구조
			String table2 = sectionString[i]
					.substring(sectionString[i].indexOf("(1) 상황별 손익구조"));
		
			table2 = table2.substring(table2.indexOf("<table"));
		
			table2 = table2.substring(0, table2.indexOf("</table>")+"</table>".length());
			
			__processTable2(Jsoup.parse(table2).getAllElements(), dls);
			
			// 최초기준가격 및 자동조기상환 내역
			String table3 = sectionString[i]
					.substring(sectionString[i].indexOf("- 최초기준가격 및 자동조기상환내역"));
		
			table3 = table3.substring(table3.indexOf("<table"));
			table3 = table3.substring(0, table3.indexOf("</table>")+"</table>".length());
			
			this.__processTable3(Jsoup.parse(table3).getAllElements(),dls);

			// 만기상환내역	
			String table4 = sectionString[i]
					.substring(sectionString[i].indexOf("- 만기상환내역"));
		
			table4 = table4.substring(table4.indexOf("<table"));
		
			table4 = table4.substring(0, table4.indexOf("</table>")+"</table>".length());
			
			this.__processTable4(Jsoup.parse(table4).getAllElements(), dls);
			
			report.addDlsInfo(dls);
		}
		log.info(report.toString()); 
		return  report;
	}
	
	
	private void __processCover(Elements cover, DlsIssueReport report) {
		// TODO Auto-generated method stub
		
	}


	/* 개별 테이블 처리 로직 */
	private void __processTable1(Elements table, Dls dls) {

		Element interim = 
				DartHtmlProcessor.parseVerticalHeadingTable(table);
		
		
		dls.setInstTitle(interim.select("종목명").text());
		
		dls.setUnderlying(interim.select("기초자산").text().replace("&amp;","&").split(" 및 "));

		dls.setTotalIssueAmt(
				Long.parseLong(
						interim.select("모집총액").text().replace(",","").replace("원","").trim()));
		dls.setUnitParPrice(
				Integer.parseInt(
						interim.select("좌당액면가액").text().replace(",","").replace("원","").trim()));
		dls.setUnitIssuePrice(
				Integer.parseInt(
						interim.select("좌당발행가액").text().replace(",","").replace("원","").trim()));
		dls.setTotalIssueUnits(
				Integer.parseInt(
						interim.select("발행수량").text().replace(",","").replace("좌","").trim()));
		dls.setSubscriptionStrtDt(
				Date.valueOf(
						interim.select("청약시작일").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-")));
		dls.setSubscriptionEndDt(
				Date.valueOf(
						interim.select("청약종료일").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-")));
		dls.setSubscriptionSttlDt(
				Date.valueOf(
						interim.select("납입일").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-")));
		dls.setSubscriptionDlvrDt(
				Date.valueOf(
						interim.select("배정및환불일").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-")));
		dls.setIssueDt(
				Date.valueOf(
						interim.select("발행일").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-")));
		dls.setListedExchange(
				interim.select("증권의상장여부").text());
		
		
		dls.setEarlyRedemptionSttlMethod(
				interim.select("자동조기상환시결제방법").text());
		
		dls.setMaturityDt(
				Date.valueOf(
						interim.select("만기일_예정").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-")));

		
		{// 만기평가일. 보통 특정 영업일이지만, 3개 영업일의 평균가격을 사용하는 경우가 있음
			String 	str = interim.select("만기평가일_예정").text().replaceAll("\\p{Z}", "").replaceAll("\\[|\\]", "").replace("일", "").replaceAll(",","Z").replaceAll("[^0-9Z]","-");
			String [] dateStr = str.split("Z");
			Date [] dates  = new Date[dateStr.length];
			
			for (int i = 0 ; i< dateStr.length; i++){
				dates[i] = Date.valueOf(dateStr[i]);
			}
			dls.setMaturityEvalDt(dates);
		}
		
		dls.setMaturitySttlDt(
						Date.valueOf(interim.select("만기상환금액지급일_예정").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-")));
		
		dls.setMaturitySettlMethod(
				interim.select("만기시결제방법").text());
		
		dls.setHedgeTrader(
				interim.select("헤지운용사").text());
		
		
		
	}
	
	private void __processTable2(Elements table, Dls dls) {
		Element interim = 
				__parsePayOffStructure(table);
		
		Elements redeem = interim.select("case");
	
		for (Element e : redeem){
			dls.addRedemptionSchedule(
					Integer.parseInt(e.val())
					,e.getElementsByTag("type").text()
					,e.getElementsByTag("provision").text()
					,Float.parseFloat(e.getElementsByTag("yield").text().split("%")[0].replaceAll("연 ",""))/100
					);
		}

	}
	
	private void __processTable3(Elements table, Dls dls) {
		//log.info(table.toString());
		// TO-DO
	}	
	
	private void __processTable4(Elements table, Dls dls) {
		//log.info(table.toString());
	}	
	
	private Element __parsePayOffStructure(Elements table) {
		Element tableTarget = new Element(Tag.valueOf("payoff"),DartCrabSettings.BASE_URI);
		
		
		Elements tbodyTr = table.select("tbody").select("tr");

		String	type = null;
		String	value = null;
		Element cursor = null;
		
		int i = 0;
		
		for (Element e : tbodyTr){
			switch (e.children().size()){
				case 1:		
					cursor = tableTarget.appendElement("case").val(i++ +"");
					cursor.appendElement("type").text(type);
					cursor.appendElement("provision").text(e.child(0).text());
					cursor.appendElement("yield").text(value);
					break;
				case 2:
					value = e.child(1).text();
					cursor = tableTarget.appendElement("case").val(i++ +"");
					cursor.appendElement("type").text(type);
					cursor.appendElement("provision").text(e.child(0).text());
					cursor.appendElement("yield").text(value);		
					break;
				case 3:
					if ( i == 0 ) {i++;break;}
					type = e.child(0).text().replaceAll("\\p{Z}", "").replaceAll("/","_");	
					value = e.child(2).text();
					
					cursor = tableTarget.appendElement("case").val(i++ +"");
					cursor.appendElement("type").text(type);
					cursor.appendElement("provision").text(e.child(1).text());
					cursor.appendElement("yield").text(value);
					break;
				default:
					log.error("Invalid payoff layout");
			}
		}
		
		return tableTarget;
	}

	
}
