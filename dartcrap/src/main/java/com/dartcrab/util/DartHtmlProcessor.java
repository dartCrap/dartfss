package com.dartcrab.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
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
	public static Set<HashMap<String/*key*/, String/*value*/>>  parseHorizontalHeadingTable (Elements table, int headerRows){
		return parseHorizontalHeadingTable (table,headerRows,0);
	}
	
	/**
	 * 
	 * @param ele
	 * @param headerRows
	 * @param dataRows
	 * @return
	 */
		public static Set<HashMap<String/*key*/, String/*value*/>> parseHorizontalHeadingTable (Elements table, int headerRows, int dataRowsNum){
		List<String> header = __fetchRowHeaders(headerRows,table);
		
		Set<HashMap<String,String>> tableData= new HashSet();
		
		for ( int cursor = headerRows; cursor <table.size() ; cursor++){
			if (dataRowsNum !=0 && cursor >= headerRows+dataRowsNum) break;
			
			List<String> data	= __fetchRowData(table,cursor);
			
			if (header.size() != data.size()) {
				log.error("Invalid table layout");
				return null;
			}
	
			HashMap<String,String> rowData = new HashMap();
			for (int i = 0 ; i < header.size() ; i++)
				rowData.put(header.get(i).toString(), data.get(i).toString());

			tableData.add(rowData);

		}
		
		return tableData;
	}

	
	/**
	 * 
	 * @param 
	 * @return
	 */
	private static List<String> __fetchRowHeaders(int headerRows,Elements table) {
		List<String> header = new ArrayList();
		
		// First Row
		Elements firstHeader = table.get(0).select("td");
		for (int i = 0 ; i < firstHeader.size(); i++){
			int colspan = Integer.parseInt("0"+firstHeader.get(i).attr("colspan").toString());
			
			if (colspan == 0 ) header.add(firstHeader.get(i).text());
			else {
				for (int j = 0 ; j < colspan; j++)
					header.add(firstHeader.get(i).text()+DartCrabSettings.DELIMETER);
			}
		}
			
		// Rest rows
		for (int i = 1 ; i < headerRows ; i++){
			int cursor = 0;
			Elements otherHeader = table.get(i).select("td");
			for (int j = 0 ; j < header.size(); j++){
				if ( header.get(j).endsWith(DartCrabSettings.DELIMETER) ) 
					header.set(j, header.get(j)+ otherHeader.get(cursor++).text());
			}
		}
			
		return header;
	}

	/**
	 * 
	 * @param 
	 * @return
	 */
	private static List<String> __fetchRowData(Elements table, int dataIndex) {
		List<String> data = new ArrayList();
		
		Elements row = table.get(dataIndex).select("td");
		
		for (int i =0; i<row.size(); i++){
			data.add(row.get(i).text());
		}
		
		return data;
	}
}
