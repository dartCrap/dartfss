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
	private StockOptionExecInfoExtractor 	stockOptionInfoExtractor;
	private DlsInfoExtractor 				dlsInfoExtractor;

	
	
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
		this.stockOptionInfoExtractor 	= new StockOptionExecInfoExtractor();
		this.dlsInfoExtractor 			= new DlsInfoExtractor();
	}
	
	/**
	 * TO-DO....best effort....
	 */
	public InfoExtractor dispatch(ReportWebDoc report) throws Exception{
		InfoExtractor extractor = null;
		if (report.getHeader().getRptNm().endsWith("주식매수선택권행사")){
			extractor = this.stockOptionInfoExtractor.setDoc(report);
			log.info ("Report "+report.getHeader().getRcpDt() 
					+ " " + report.getHeader().getCrpNm()
					+ " " + report.getHeader().getRptNm()
					+ "==>" + this.stockOptionInfoExtractor.getClass().getName());
			
		} else if (report.getHeader().getRptNm().matches("일괄신고추가서류(.*결합증권.*)")){
			extractor = this.dlsInfoExtractor.setDoc(report);
			log.info ("Report "+report.getHeader().getRcpDt() 
					+ " " + report.getHeader().getCrpNm()
					+ " " + report.getHeader().getRptNm()
					+ "==>" + this.dlsInfoExtractor.getClass().getName());
			
		} else if (true){
			log.error("No matching extractor");
		}
		return extractor;
	}
	
	
}
