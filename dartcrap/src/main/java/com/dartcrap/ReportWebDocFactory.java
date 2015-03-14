package com.dartcrap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrap.util.DartCrapSettings;

/**
 * 
 * @author Gi
 *
 */
public class ReportWebDocFactory {
	private static Logger log = LoggerFactory.getLogger( IndexAndSearchTest.class );
	/**
	 * TO-DO
	 */
	public static ReportWebDoc loadHttpReportWebDoc (String rcpNo){
		
		ReportWebDoc report = null;
		
		try{
				Document doc = Jsoup.connect(DartCrapSettings.REPORT_URL
					+"?rcpNo="+ rcpNo)
					.userAgent(DartCrapSettings.USER_AGENT)
					.get();
				
				Elements	viewdocScript = doc.getElementsByTag("script"); // Retrieve javascript only
				
				Pattern		viewdocPattern = Pattern.compile(DartCrapSettings.VIEW_DOC_MATCHER.replace("@@RCP_NO@@", rcpNo),Pattern.MULTILINE);
				Matcher		viewdocMatcher = viewdocPattern.matcher(viewdocScript.toString());
	
				log.info("Fetch a repot with rcp_no " + rcpNo + "-->" 
							+String.valueOf(viewdocMatcher.find())
							+", dcmNo = " + viewdocMatcher.group(1)
							+", dtd = " + viewdocMatcher.group(2));
				
				String reportViewRequest = DartCrapSettings.REPORT_VIEW_URL 
									+ "?rcpNo=" + rcpNo
									+ "&dcmNo=" + viewdocMatcher.group(1)
									+ "&eleId=1&offset=0&length=0" // default
									+ "&dtd=" + viewdocMatcher.group(2);
				report = new ReportWebDoc(rcpNo,
							Jsoup.connect(reportViewRequest)
								.userAgent(DartCrapSettings.USER_AGENT)
								.timeout(DartCrapSettings.DEFAULT_TIME_OUT) // --> to be changed
								.get()
								.html().toString()
								);
 								
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return report;
	}
	
	/**
	 * TO-DO
	 */
	public static ReportWebDoc loadFileReportWebDoc (String rcpNo) {
		ReportWebDoc report=null;
		return report;
	}
	
	/**
	 * TO-DO
	 */
	public static ReportWebDoc loadDbmsReportWebDoc (String rcpNo) {
		ReportWebDoc report=null;
		return report;
	}
	
	
	
}
