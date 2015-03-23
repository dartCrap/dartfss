package com.dartcrab.entities;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.dartcrab.reports.ReportHeader;
import com.dartcrab.util.DartCrabSettings;

public class GenericDartReport{
	private ReportHeader	header;
	//private Document		document;		// Depleted Mar-23-2015 by Gi
	
	
	public GenericDartReport(ReportHeader header){
		this.header = header;
	}


	public ReportHeader getHeader() {
		return header;
	}
	
	public ReportHeader setHeader() {
		return header;
	}
	
}
