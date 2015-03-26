package com.dartcrab.entities;

import com.dartcrab.reports.ReportHeader;


/**
 * 
 * @author Gi Kim
 * @since Mar-25-2015
 */
public class GenericDartReport{
	private ReportHeader	header;
	
	public GenericDartReport(ReportHeader header){
		this.header = header;
	}

	public ReportHeader getHeader() {
		return header;
	}
	
	public ReportHeader setHeader() {
		return header;
	}
	
	@Override
	public String toString() {
		return "GenericDartReport [header=" + header + "]";
	}
}
