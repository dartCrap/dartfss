package com.dartcrab.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.reports.ReportSearchRequest;
import com.dartcrab.reports.ReportWebDoc;

/**
 * 
 * @author Gi
 *
 */

public class ExtractorDispatcher {
	private static Logger log = LoggerFactory.getLogger( ExtractorDispatcher.class );
	private static volatile ExtractorDispatcher instance= null; // Singleton
	
	/*
	 * 
	 */
	private StockOptionInfoExtractor stockOptionInfoExtractor;

	
	
	public static ExtractorDispatcher getInstance(){
		synchronized (ExtractorDispatcher.class){
			if (instance == null){
				log.info("Initialize...");
				instance = new ExtractorDispatcher();
			}
		}
		return instance;
	}
	
	
	/**
	 * TO-DO 
	 */
	private ExtractorDispatcher(){
		this.stockOptionInfoExtractor = new StockOptionInfoExtractor();
	}
	
	/**
	 * TO-DO....best effort....
	 */
	public InfoExtractor dispatch(ReportWebDoc report){
		
		if (report.getHeader().getRptNm().endsWith("주식매수선택권부여에관한신고")){
			this.stockOptionInfoExtractor.setDoc(report);
			log.info ("Report "+report.getHeader().getRcpDt() 
					+ " " + report.getHeader().getCrpNm()
					+ " " + report.getHeader().getRptNm()
					+ "==>" + this.stockOptionInfoExtractor.getClass().getName());
		} else if (true){
			
		}
		return this.stockOptionInfoExtractor;
	}
}
