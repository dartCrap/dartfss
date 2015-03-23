package com.dartcrab.extractor;

import com.dartcrab.entities.GenericDartReport;
import com.dartcrab.reports.ReportWebDoc;

public abstract class ReportExtractor {
	private ReportWebDoc	doc;
	/**
	 * 
	 * @return
	 */
	public abstract GenericDartReport extract();
	
	/**
	 * 
	 */
	public ReportExtractor setDoc(ReportWebDoc doc){
		this.doc = doc;
		return this;
	}
	
	public ReportWebDoc getDoc(){
		return this.doc;
	}	
	
	/**
	 * 
	 */
	public ReportExtractor (ReportWebDoc doc){
		this.doc = doc;
	}
	
	public ReportExtractor(){
		this.doc = null;
	}
}

