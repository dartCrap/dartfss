package com.dartcrab.extractor;

import java.util.HashMap;
import java.util.Iterator;

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
		
		// TO-DO: refactoing
		
		// 행사주식수현황
		for ( HashMap<String,String> e :
			DartHtmlProcessor.parseHorizontalHeadingTable(table1.getElementsByTag("tr"),2,1)){
			Iterator iter = e.keySet().iterator();
			while (iter.hasNext()){
				String key = iter.next().toString();
				info.addInfo(key, e.get(key));
			}
		}
			

		// 일별행사내역
		for ( HashMap<String,String> e : 
			DartHtmlProcessor.parseHorizontalHeadingTable(table2.getElementsByTag("tr"),2) ){ 
			int i = 1;
			info.addInfo("행사내역" + String.format("%02d", i) , e.toString());
		}
		;
		
		// 주식매수선택권 잔여현황;
		for ( HashMap<String,String> e :
			DartHtmlProcessor.parseHorizontalHeadingTable(table3.getElementsByTag("tr"),2)){
			Iterator iter = e.keySet().iterator();
			while (iter.hasNext()){
				String key = iter.next().toString();
				info.addInfo(key, e.get(key));
			}
		}
		
		// 관련공시
		for (Element e : tables.select("a[href^=/dsaf001/main.do]")){
			int i = 1;

			info.addInfo(String.format("관련공시%2d",i),
					"{" + "공시명="+e.text() 
					+", 보고서접수번호" + e.attr("href").substring(e.attr("href").indexOf("=")));

		};
		
		return  info;
	}
	
	/**
	 * 
	 */

}
