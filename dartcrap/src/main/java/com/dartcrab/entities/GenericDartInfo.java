package com.dartcrab.entities;

import java.util.HashMap;
import java.util.Map;

import com.dartcrab.reports.ReportHeader;

public class GenericDartInfo{
	private ReportHeader	header;
	private Map<String,String> map; // To-be refined...I need more generic model for complex objects
	
	public GenericDartInfo(ReportHeader header){
		this.header = header;
		map = new HashMap();
	}
	
	/**
	 * To-do
	 * @param key
	 * @param value
	 */
	public GenericDartInfo addInfo(String key, String value){
		map.put(key, value);
		return this;
	}

	@Override
	public String toString() {
		return "GenericDartInfo [header=" + header + ", map=" + map + "]";
	}
	
	
}
