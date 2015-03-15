package com.dartcrab.extractor;

import com.dartcrab.entities.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.entities.GenericDartInfo;
import com.dartcrab.entities.StockOptionInfo;

public class StockOptionInfoExtractor extends InfoExtractor{
	private static Logger log = LoggerFactory.getLogger( StockOptionInfoExtractor.class );
	
	/**
	 * TO-DO
	 */
	public GenericDartInfo extract(){
		GenericDartInfo	info = new GenericDartInfo(this.getDoc().getHeader());
		log.info("Extract: " + this.getDoc().getHeader());
		
		Document doc = Jsoup.parse(this.getDoc().getContents());
		
		Elements ele = doc.select("td"); // select CSS
		/* TO-DO */
		
		return  info;
	}
	
	/**
	 * 
	 */

}
