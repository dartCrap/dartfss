package com.dartcrab.reports;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.util.DartCrabSettings;

/**
 * 
 * @author Gi
 *
 */
public class ReportWebDocFactory {
	private static Logger log = LoggerFactory.getLogger( ReportWebDocFactory.class );
	/**
	 * TO-DO
	 */
	public static ReportWebDoc loadHttpReportWebDoc (ReportHeader header){
		
		ReportWebDoc report = null;
		
		try{
				Document doc = Jsoup.connect(DartCrabSettings.REPORT_URL
					+"?rcpNo="+ header.getRcpNo())
					.userAgent(DartCrabSettings.USER_AGENT)
					.get();
				
				Elements	viewdocScript = doc.getElementsByTag("script"); // Retrieve javascript only
				
				Pattern		viewdocPattern = Pattern.compile(DartCrabSettings.VIEW_DOC_MATCHER.replace("@@RCP_NO@@", header.getRcpNo()),Pattern.MULTILINE);
				Matcher		viewdocMatcher = viewdocPattern.matcher(viewdocScript.toString());
	
				log.info("Fetch a repot with rcp_no " + header.getRcpNo() + "-->" 
							+String.valueOf(viewdocMatcher.find())
							+", dcmNo = " + viewdocMatcher.group(1)
							+", dtd = " + viewdocMatcher.group(2));
				
				String reportViewRequest = DartCrabSettings.REPORT_VIEW_URL 
									+ "?rcpNo=" + header.getRcpNo()
									+ "&dcmNo=" + viewdocMatcher.group(1)
									+ "&eleId=1&offset=0&length=0" // default
									+ "&dtd=" + viewdocMatcher.group(2);
				report = new ReportWebDoc(header, 
							new String(
										Jsoup.connect(reportViewRequest)
											.userAgent(DartCrabSettings.USER_AGENT)
											.timeout(DartCrabSettings.DEFAULT_TIME_OUT) // --> to be changed
											.get()
											.getElementsByTag("body")
											.html().getBytes()
											, StandardCharsets.UTF_8
									)
								);
				
				log.info("Html load: " + reportViewRequest);
 								
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return report;
	}
	
	/**
	 * TO-DO
	 */
	public static ReportWebDoc loadFileReportWebDoc (ReportHeader header) {
		ReportWebDoc report = null;
		try{
			byte[] encoded = Files.readAllBytes(Paths.get("target/data/"+header.getRcpNo()+".html"));   // TO-DO: paramererize file path
			report = new ReportWebDoc(header,
					new String(encoded, StandardCharsets.UTF_8)
			    );
			log.info("File load: " + "target/data/"+header.getRcpNo()+".html");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return report;
	}
	
	
	/**
	 * TO-DO
	 */
	public static ReportWebDoc loadAndStoreReportWebDoc (ReportHeader header) {

		File f = new File("target/data/"+header.getRcpNo() + ".html");  // To do: Parameterize the file path
		if(f.exists() && !f.isDirectory()) { 
			return loadFileReportWebDoc(header); 
		} 
			
		return loadHttpReportWebDoc(header).storeFile();
		
	}	
	
	/**
	 * TO-DO
	 */
	public static ReportWebDoc loadDbmsReportWebDoc (ReportHeader header) {
		ReportWebDoc report=null;
		return report;
	}
	
	
	
}
