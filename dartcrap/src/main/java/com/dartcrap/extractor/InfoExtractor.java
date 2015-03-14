package com.dartcrap.extractor;

import com.dartcrap.entity.GenericDartInfo;
import com.dartcrap.reports.ReportWebDoc;

public abstract class InfoExtractor {
	private ReportWebDoc	doc;
	/**
	 * 
	 * @return
	 */
	public abstract GenericDartInfo extract();
	
	/**
	 * 
	 */
	public InfoExtractor setDoc(ReportWebDoc doc){
		this.doc = doc;
		return this;
	}
	
	public ReportWebDoc getDoc(){
		return this.doc;
	}	
	
	/**
	 * 
	 */
	public InfoExtractor (ReportWebDoc doc){
		this.doc = doc;
	}
	
	public InfoExtractor(){
		this.doc = null;
	}
}

