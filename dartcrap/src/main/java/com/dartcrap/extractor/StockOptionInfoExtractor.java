package com.dartcrap.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrap.entity.GenericDartInfo;
import com.dartcrap.entity.StockOptionInfo;

public class StockOptionInfoExtractor extends InfoExtractor{
	private static Logger log = LoggerFactory.getLogger( StockOptionInfoExtractor.class );
	
	/**
	 * TO-DO
	 */
	public GenericDartInfo extract(){
		GenericDartInfo	info = new GenericDartInfo(this.getDoc().getHeader());
		log.info("Extract: " + this.getDoc().getHeader());
		return  info;
	}
	
	/**
	 * 
	 */

}
