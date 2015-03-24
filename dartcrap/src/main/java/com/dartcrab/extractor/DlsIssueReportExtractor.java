package com.dartcrab.extractor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.entities.Dls;
import com.dartcrab.entities.DlsIssueReport;
import com.dartcrab.entities.GenericDartReport;
import com.dartcrab.util.DartHtmlProcessor;

public class DlsIssueReportExtractor extends ReportExtractor {
	private static Logger log = LoggerFactory.getLogger( DlsIssueReportExtractor.class );
	
	/**
	 * TO-DO
	 */
	public GenericDartReport extract(){
		DlsIssueReport report = new DlsIssueReport(this.getDoc().getHeader());
		
		Document doc = Jsoup.parse(this.getDoc().getContents());

		
		/* TO-DO */
		String	allHtml = this.getDoc().getContents();
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
			
//			
//			
//			// 상황별 손익구조
//			String table2 = sectionString[i]
//					.substring(sectionString[i].indexOf("(1) 상황별 손익구조"));
//		
//			table2 = table2.substring(table2.indexOf("<table"));
//		
//			table2 = table2.substring(0, table2.indexOf("</table>")+"</table>".length());
//			
//			Element tableElement2 =
//					this.__processTable2(Jsoup.parse(table2).getAllElements());
//			
//			// 최초기준가격 및 자동조기상환 내역
//			String table3 = sectionString[i]
//					.substring(sectionString[i].indexOf("- 최초기준가격 및 자동조기상환내역"));
//		
//			table3 = table3.substring(table3.indexOf("<table"));
//			table3 = table3.substring(0, table3.indexOf("</table>")+"</table>".length());
//			
//			Element tableElement3 =
//					this.__processTable3(Jsoup.parse(table3).getAllElements());
//			
//			//--> post process
//			
//			// 만기상환내역	
//			String table4 = sectionString[i]
//					.substring(sectionString[i].indexOf("- 만기상환내역"));
//		
//			table4 = table4.substring(table4.indexOf("<table"));
//		
//			table4 = table4.substring(0, table4.indexOf("</table>")+"</table>".length());
//			
//			Element tableElement4 =
//					this.__processTable4(Jsoup.parse(table4).getAllElements());
//			
			
			//--> wrap-up process					
		}
		//--> wrap-up process
		return  report;
	}
	
	
	private void __processCover(Elements cover, DlsIssueReport report) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * 
	 * @param table
	 * @param dls 
	 * @return
	 */
	private void __processTable1(Elements table, Dls dls) {

		Element interim = 
				DartHtmlProcessor.parseVerticalHeadingTable(table);
		
		log.info(interim.toString());
		
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
				interim.select("증권의상장여부").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-"));
		dls.setMaturityDt(
				Date.valueOf(
						interim.select("만기일_예정").text().replaceAll("\\[|\\]","").replace("일", "").replaceAll("[^0-9]+","-")));
		// 만기평가일부터...리스트로 수정해야 함
		// 조기상환시 결제방법 추가
		log.info(dls.toString());
		
	}
	
	/**
	 * 
	 * @param table
	 * @return
	 */
	private Element __processTable2(Elements table) {
		Element result = null;
		Element interim = 
				DartHtmlProcessor.parseVerticalHeadingTable(table);
		// TO-DO ==> 완전 다시
		result = interim;
		
		return result;
	}
	
	/**
	 * 
	 * @param table
	 * @return
	 */
	private Element __processTable3(Elements table) {
		Element result = null;
		Element interim = 
				DartHtmlProcessor.parseVerticalHeadingTable(table);
		// TO-DO
		result = interim;
		log.info(result.toString());
		return result;
	}	
	
	/**
	 * 
	 * @param table
	 * @return
	 */
	private Element __processTable4(Elements table) {
		Element result = null;
		Element interim = 
				DartHtmlProcessor.parseVerticalHeadingTable(table);
		// TO-DO
		result = interim;
		return result;
	}	
	
}
