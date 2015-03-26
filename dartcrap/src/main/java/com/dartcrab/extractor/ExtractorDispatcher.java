package com.dartcrab.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.reports.ReportWebDoc;

/**
 * ReportWebDoc 객체와 ReportExtractor 객체를 매핑하는 기능을 수행한다.
 * 
 * 현재는 Singleton으로 사용된다. 향후 multi-thread 도입을 염두에 두고 개발이 진행되어야 한다.
 * 
 * @author Gi Kim
 * @version 0.5
 * @since Mar-25-2015
 */

public class ExtractorDispatcher {
	private static Logger log = LoggerFactory.getLogger( ExtractorDispatcher.class );
	
	private static volatile ExtractorDispatcher instance= null; // Singleton
	private StockOptionExecReportExtractor 	stockOptionInfoExtractor;
	private DlsIssueReportExtractor 				dlsInfoExtractor;

	/**
	 * Singleton pattern
	 * @return ExtractorDispatcher
	 */
	public static ExtractorDispatcher getInstance(){
		synchronized (ExtractorDispatcher.class){
			if (instance == null){
				instance = new ExtractorDispatcher();
			}
		}
		return instance;
	}
	
	
	/*
	 * Constructor는 사용 불가. (Singleton이므로)
	 */
	private ExtractorDispatcher(){
		this.stockOptionInfoExtractor 	= new StockOptionExecReportExtractor();
		this.dlsInfoExtractor 			= new DlsIssueReportExtractor();
	}
	
	/**
	 * ReportWebDoc에 대해 적절한 ReportExtractor를 찾아준다.
	 * 현재 알고리즘은 Best Effort에 기반한다. 즉, ReportWebDoc을 collectively exhaustive하게
	 * 분류하는 것을 불가능하게 보고, 연속된 if 절을 통해 순차적으로 가장 적합한 분류를 찾아간다.
	 * 개선의 여지에 대해 검토해볼 필요는 있다.
	 */
	public ReportExtractor dispatch(ReportWebDoc report) throws Exception{
		ReportExtractor extractor = null;
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
