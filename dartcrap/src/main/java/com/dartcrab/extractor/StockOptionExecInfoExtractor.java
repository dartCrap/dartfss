package com.dartcrab.extractor;

import com.dartcrab.entities.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.entities.GenericDartInfo;
import com.dartcrab.util.DartHtmlProcessor;

public class StockOptionExecInfoExtractor extends InfoExtractor{
	private static Logger log = LoggerFactory.getLogger( StockOptionExecInfoExtractor.class );
	
	/**
	 * TO-DO
	 */
	public GenericDartInfo extract(){
		
		GenericDartInfo	info = new GenericDartInfo(this.getDoc().getHeader());
		
		Document doc = Jsoup.parse(this.getDoc().getContents());
		
		Elements tables = doc.select("table"); 
		
		Element table1 = tables.get(0); // 행사주식수현황 
		Element table2 = tables.get(1); // 일별행사내역
		Element table3 = tables.get(2); // 주식매수선택권잔여현황
		
		// 행사주식수현황
		// TO-DO
		DartHtmlProcessor.parseHorizontalHeadingTable(table1.getElementsByTag("tr"),2,1);
		// 일별행사내역
		DartHtmlProcessor.parseHorizontalHeadingTable(table2.getElementsByTag("tr"),2);
		
		
		//DartHtmlProcessor.parseVerticalHeadingTable(ele);
		
		return  info;
	}
	
	/**
	 * 
	 */

}
