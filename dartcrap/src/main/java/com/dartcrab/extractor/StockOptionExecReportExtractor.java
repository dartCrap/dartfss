package com.dartcrab.extractor;

import com.dartcrab.entities.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.entities.GenericDartReport;
import com.dartcrab.reports.ReportHeader;
import com.dartcrab.util.DartHtmlProcessor;

public class StockOptionExecReportExtractor extends ReportExtractor{
	private static Logger log = LoggerFactory.getLogger( StockOptionExecReportExtractor.class );
	
	/**
	 * TO-DO
	 */
	public GenericDartReport extract(){
		StockOptionExecReport report =  new StockOptionExecReport(this.getDoc().getHeader());
				
		
		Document doc = Jsoup.parse(this.getDoc().getContents());
		
		Elements tables = doc.select("table"); 
		
		Element table1 = tables.get(0); // 행사주식수현황 
		Element table2 = tables.get(1); // 일별행사내역
		Element table3 = tables.get(2); // 주식매수선택권잔여현황

		// 행사주식수현황 / TO-DO
		__processTable1(DartHtmlProcessor.parseHorizontalHeadingTable(table1.getElementsByTag("tr"),2,1)
				,report);
		
			
		// 일별행사내역
		__processTable2(DartHtmlProcessor.parseHorizontalHeadingTable(table2.getElementsByTag("tr"),2)
				,report);
							
		
		// 주식매수선택권 잔여현황;
						
		__processTable3(DartHtmlProcessor.parseHorizontalHeadingTable(table3.getElementsByTag("tr"),2)
				, report);
		
		// 관련공시
		for (Element e : tables.select("a[href^=/dsaf001/main.do]")){
			report.getRelatedReports().add( new ReportHeader()
									.setRcpNo(e.attr("href").substring(e.attr("href").indexOf("=")+1))
									.setRptNm(e.text())
									);	
		}
		log.info(report.toString());
		return  (GenericDartReport) report;
	}
	
	private StockOptionExecReport __processTable1(Element table,StockOptionExecReport report){
		
		report.setTotalIssueUnits(
					Integer.parseInt(table.getElementsByTag("총발행주식수_주").text().replace(",", "").replace("-", "0"))
				);
		report.setNewSharesDistributedUnits(
					Integer.parseInt(table.getElementsByTag("신주교부").text().replace(",", "").replace("-", "0"))
				);
		report.setTreasuryStockDistributedUnits(
					Integer.parseInt(table.getElementsByTag("자기주식교부").text().replace(",", "").replace("-", "0"))
				);
		report.setCashSettlePaidUnits(
					Integer.parseInt(table.getElementsByTag("차액보상").text().replace(",", "").replace("-", "0"))
				);
		report.setExecRatio(
					Float.parseFloat(table.getElementsByTag("행사비율").text().replace(",", "").replace("-", "0").replace("%",""))/100
				);
		return report;
	}
	
	private StockOptionExecReport __processTable2(Element table,StockOptionExecReport report){

		Elements row = table.select("row");
		for (Element e : row){
			report.addExecDetail(
						java.sql.Date.valueOf(e.getElementsByTag("행사일").text().trim()),
						e.getElementsByTag("성명_법인인경우법인명").text(),
						e.getElementsByTag("회사와관계").text(),
						Float.parseFloat(e.getElementsByTag("행사가격").text().replace(",","").replace("원","").replace("-", "0")), 
						Float.parseFloat(e.getElementsByTag("주당액면가").text().replace(",", "").replace("원","").replace("-", "0")), 
						Integer.parseInt(e.getElementsByTag("신주교부").text().replace(",", "").replace("-", "0")), 
						Integer.parseInt(e.getElementsByTag("자기주식교부").text().replace(",", "").replace("-", "0")),
						Integer.parseInt(e.getElementsByTag("차액보상").text().replace(",", "").replace("-", "0"))
					);
		}
		
		return report;
	}
	
	
	private StockOptionExecReport __processTable3(Element table,StockOptionExecReport report){
		
		report.setNewSharesRemainingUnits(
				Integer.parseInt(table.getElementsByTag("신주교부_주").text().replace(",", "").replace("-", "0")));
		report.setTreasuryStockRemainingUnits(
				Integer.parseInt(table.getElementsByTag("자기주식교부_주").text().replace(",", "").replace("-", "0")));
		report.setCashSettleRemainingUnits(
				Integer.parseInt(table.getElementsByTag("차액보상_주").text().replace(",", "").replace("-", "0")));
		
		return report;
	}
}