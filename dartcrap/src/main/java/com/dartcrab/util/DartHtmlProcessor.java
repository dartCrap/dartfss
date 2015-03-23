package com.dartcrab.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * @author Gi
 *
 */
public class DartHtmlProcessor {
	private static Logger log = LoggerFactory.getLogger( DartHtmlProcessor.class );
		
	/**
	 * 
	 * @param ele
	 * @return
	 */
	public static Element parseHorizontalHeadingTable (Elements tableSrc, int headerRows){
		return parseHorizontalHeadingTable (tableSrc,headerRows,0);
	}
	
	/**
	 * 
	 * @param ele
	 * @param headerRows
	 * @param dataRows
	 * @return
	 */
		public static Element parseHorizontalHeadingTable (Elements tableSrc, int headerRows, int dataRowsNum){
		Element tableTarget = new Element(Tag.valueOf("TABLE"),DartCrabSettings.BASE_URI);
			
		__fetchRowHeaders(headerRows,tableSrc, tableTarget);
		
		for ( int cursor = headerRows; cursor <tableSrc.size() ; cursor++){
			if (dataRowsNum !=0 && cursor >= headerRows+dataRowsNum) break;
			__fetchRowData(tableSrc,cursor, tableTarget);
		}

		return tableTarget;
	}

	
	/**
	 * 
	 * @param 
	 * @return
	 */
	private static Element __fetchRowHeaders(int headerRows,Elements tableSrc, Element tableTarget) {
		// First Row
		Elements firstHeader = tableSrc.get(0).select("td");
		for (int i = 0 ; i < firstHeader.size(); i++){
			int colspan = Integer.parseInt("0"+firstHeader.get(i).attr("colspan").toString());
			
			Element child = tableTarget.appendElement(__normalizeTag(firstHeader.get(i).text()));
			
			if (colspan == 0) child.attr("LEAF","Y");
			else {
				for (int j = 0 ; j < colspan; j++)
					child.appendElement("CHILD_RESERVED");
			}
		}
		
		Elements child = tableTarget.getAllElements(); 
		
		for ( int i = 1 ; i < headerRows ; i++){
			child = child.select("CHILD_RESERVED");
			Elements src = tableSrc.get(i).select("td");
			__fetchNextRowHeader(child, src);
		}
	
		return tableTarget;
	}

	private static void __fetchNextRowHeader(Elements child, Elements src){
		int cursor = 0;
		for (Element e: child){
				e.tagName(__normalizeTag(src.get(cursor++).text()));
				int colspan = Integer.parseInt("0"+ e.attr("colspan").toString());
				if (colspan == 0 ) e.attr("LEAF","Y");
				else {
					for (int j = 0 ; j < colspan; j++)
						e.appendElement("CHILD_RESERVED");
				}

		}
	}
	
	/**
	 * 
	 * @param 
	 * @return
	 */
	private static void __fetchRowData(Elements tableSrc, int dataIndex, Element tableTarget) {
		Elements row = tableSrc.get(dataIndex).select("td");

		// Get leaves
		Elements leafNodes = tableTarget.getAllElements().select("[leaf=Y]");
		int i = 0;
		for (Element e : leafNodes){
			e.text(row.get(i++).text()) ;
		}
		
	}
	
	
	
	/**
	 * Limited use
	 * @param ele
	 * @param headerRows
	 * @param dataRows
	 * @return
	 */
		public static Element parseVerticalHeadingTable (Elements tableSrc){
		Element tableTarget = new Element(Tag.valueOf("TABLE"),DartCrabSettings.BASE_URI);
		
		Elements tbody = tableSrc.select("tbody");
		
		Elements tbodyTr = tbody.select("tr");
		
		Element	previous = null;
		
		int count = 0 ; //
		
		for (Element e : tbodyTr){
			int rowspan = Integer.parseInt("0"+ e.child(0).attr("rowspan").toString());
			
			if (rowspan == 0 && count == 0) {
				tableTarget.appendElement(__normalizeTag(e.child(0).text())).text(e.child(1).text());
			} else if (rowspan > 1 && count == 0 ){
				count = rowspan - 1;
				previous = tableTarget.appendElement(__normalizeTag(e.child(0).text()));
				previous.appendElement(__normalizeTag(e.child(1).text())).text(e.child(2).text());
			} else if (rowspan == 0 && count >0){
				if (e.childNodeSize() == 2 )
					previous.appendElement(__normalizeTag(e.child(0).text())).text(e.child(1).text());
				else 
					previous.appendElement(__normalizeTag(e.child(0).text())).text(previous.text());
				count--;
			} else {
				log.error("Invalid table layout");
			}
		}	
		
		return tableTarget;
	}
	
		
	
	/**
	 * 
	 */
	private static String __normalizeTag(String tag){

		String rtr = tag
			.replaceAll("&nbsp;", "")
			.replaceAll("&nbsp", "")
			.replaceAll("[\n\f\r\b \t.]", "")
			.replaceAll("[(,/]", "_")
			.replaceAll("[)1-9]", "")
			.trim();

		return rtr;
	}
}
