package com.dartcrab.extractor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.dartcrab.entities.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.entities.GenericDartReport;
import com.dartcrab.util.DartCrabSettings;
import com.dartcrab.util.DartHtmlProcessor;

public class DlsInfoExtractor extends ReportExtractor {
	private static Logger log = LoggerFactory.getLogger( DlsInfoExtractor.class );
	
	/**
	 * TO-DO
	 */
	public GenericDartReport extract(){
		GenericDartReport info = new GenericDartReport(this.getDoc().getHeader());
		Document doc = Jsoup.parse(this.getDoc().getContents());

		
		/* TO-DO */
		String	allHtml = this.getDoc().getContents();
		String [] sectionString = allHtml.split("\\[ 모집 또는 매출의 개요 \\]");
		
		List<Elements>	sectionElements = new ArrayList();
		
		Elements cover = Jsoup.parse(sectionString[0]).getAllElements();
		
		for (int i = 1 ; i < sectionString.length; i++){  // Divide...
			sectionElements.add(Jsoup.parse(sectionString[i]).getAllElements());
			
			// 모집 또는 매출의 개요
			String table1 = sectionString[i]
						.substring(sectionString[i].indexOf("<p> &nbsp;<br />(2) 모집 또는 매출의 개요<br /> </p>"));
			
			table1 = table1.substring(table1.indexOf("<table"));
			
			table1 = table1.substring(0, table1.indexOf("</table>")+"</table>".length());
			
			Element tableElement1 = 
					this.__processTable1(Jsoup.parse(table1).getAllElements());
			
			
			
			// 상황별 손익구조
			String table2 = sectionString[i]
					.substring(sectionString[i].indexOf("(1) 상황별 손익구조"));
		
			table2 = table2.substring(table2.indexOf("<table"));
		
			table2 = table2.substring(0, table2.indexOf("</table>")+"</table>".length());
			
			Element tableElement2 =
					this.__processTable2(Jsoup.parse(table2).getAllElements());
			
			// 최초기준가격 및 자동조기상환 내역
			String table3 = sectionString[i]
					.substring(sectionString[i].indexOf("- 최초기준가격 및 자동조기상환내역"));
		
			table3 = table3.substring(table3.indexOf("<table"));
			table3 = table3.substring(0, table3.indexOf("</table>")+"</table>".length());
			
			Element tableElement3 =
					this.__processTable3(Jsoup.parse(table3).getAllElements());
			
			//--> post process
			
			// 만기상환내역	
			String table4 = sectionString[i]
					.substring(sectionString[i].indexOf("- 만기상환내역"));
		
			table4 = table4.substring(table4.indexOf("<table"));
		
			table4 = table4.substring(0, table4.indexOf("</table>")+"</table>".length());
			
			Element tableElement4 =
					this.__processTable4(Jsoup.parse(table4).getAllElements());
			
			
			//--> wrap-up process					
		}
		//--> wrap-up process
		return  info;
	}
	
	
	/**
	 * 
	 * @param table
	 * @return
	 */
	private Element __processTable1(Elements table) {
		Element result = null;
		Element interim = 
				DartHtmlProcessor.parseVerticalHeadingTable(table);
 
		result = interim;
		
		return result;
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
