package com.dartcrab.extractor;

import com.dartcrab.entities.GenericDartReport;
import com.dartcrab.reports.ReportWebDoc;

/**
 * ReportWebDoc을 받아서 정보를 추출해내는 Extractor의 추상클래스
 * 
 * 
 * @author Gi Kim
 * @version 1.0
 * @since Mar-25-2015
 */

public abstract class ReportExtractor {
	private ReportWebDoc	doc;
	
	/**
	 * Extractor 객체의 핵심 기능으로, 정보를 추출해내는 method이다.
	 * 인터페이스만을 정의하므로, 구체적인 로직은 각 보고서 유형별로 별도 Class개발이 필요하다.
	 *  
	 * @return GenericDartReport
	 */
	public abstract GenericDartReport extract();
	
	/*
	 * Getters and setters
	 */
	public ReportExtractor setDoc(ReportWebDoc doc){
		this.doc = doc;
		return this;
	}
	
	public ReportWebDoc getDoc(){
		return this.doc;
	}	
	
	/*
	 * Constructors 
	 */
	public ReportExtractor (ReportWebDoc doc){
		this.doc = doc;
	}
	
	public ReportExtractor(){
		this.doc = null;
	}
}

