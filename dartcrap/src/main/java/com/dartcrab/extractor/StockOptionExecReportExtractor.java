package com.dartcrab.extractor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import com.dartcrab.entities.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.entities.GenericDartReport;
import com.dartcrab.util.DartCrabSettings;
import com.dartcrab.util.DartHtmlProcessor;

public class StockOptionExecReportExtractor extends ReportExtractor{
	private static Logger log = LoggerFactory.getLogger( StockOptionExecReportExtractor.class );
	
	/**
	 * TO-DO
	 */
	public GenericDartReport extract(){
		GenericDartReport info = new GenericDartReport(this.getDoc().getHeader());
				
		
		Document doc = Jsoup.parse(this.getDoc().getContents());
		
		Elements tables = doc.select("table"); 
		
		Element table1 = tables.get(0); // 행사주식수현황 
		Element table2 = tables.get(1); // 일별행사내역
		Element table3 = tables.get(2); // 주식매수선택권잔여현황

		Document result = new Document("");

		// 행사주식수현황 / TO-DO
	
						
		result.appendChild(	DartHtmlProcessor.parseHorizontalHeadingTable(table1.getElementsByTag("tr"),2,1)
								.tagName("행사주식현황"))
		;

			
		// 일별행사내역
						
		result.appendChild(	DartHtmlProcessor.parseHorizontalHeadingTable(table2.getElementsByTag("tr"),2)
								.tagName("일별_행사내역")
								);
		
		// 주식매수선택권 잔여현황;
		result.appendChild (				
				DartHtmlProcessor.parseHorizontalHeadingTable(table3.getElementsByTag("tr"),2)
								.tagName("주식매수선택권_잔여현황")
								);
		
		// 관련공시
		Element eRelated = result.appendElement("관련공시");
		for (Element e : tables.select("a[href^=/dsaf001/main.do]")){
			eRelated.appendElement("공시명").text(e.text());
			eRelated.appendElement("보고서접수번호").text(e.attr("href").substring(e.attr("href").indexOf("=")+1));		
		}

		// TO-DO
		log.info(result.toString());
		
		return  info;
	}
	
}
