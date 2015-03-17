package com.dartcrab.util;

/**
 * TO-DO
 * 
 * @author Gi
 *
 */
public class DartCrabSettings {
	public final static String API_URL = "http://dart.fss.or.kr/api/search.xml";
	public final static String REPORT_URL = "http://dart.fss.or.kr/dsaf001/main.do";
	public final static String REPORT_VIEW_URL = "https://dart.fss.or.kr/report/viewer.do";
	public final static String USER_AGENT = "Chrome/41.0.2272.89";
	
	public final static int DEFAULT_TIME_OUT = 100000;
	
	
	public final static String VIEW_DOC_MATCHER = "javascript: viewDoc\\('@@RCP_NO@@', '([0-9]*)', null, null, null, '(.*)'";
	private static volatile DartCrabSettings instance = null;
	private DartCrabSettings(){}
}
