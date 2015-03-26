package com.dartcrab.reports;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.util.DartCrabSettings;

/**
 * 공시보고서 HTML을 추출하여 ReportWebDoc 객체로 관리
 * @author Gi Kim
 * @version 1.0
 * @since Mar-25-2015
 * @see ReportWebDoc
 */
public class ReportWebDocFactory {
	private static Logger log = LoggerFactory.getLogger( ReportWebDocFactory.class );

	/**
	 * Http로 Dart사이트에서 바로 HTML을 읽어온다.
	 * 
	 * @param rcpNo
	 * @return
	 */
	public static ReportWebDoc loadHttpReportWebDoc (String rcpNo){
		
		ReportWebDoc report = null;
		
		try{
				Document doc = Jsoup.connect(DartCrabSettings.REPORT_URL
					+"?rcpNo="+ rcpNo)
					.userAgent(DartCrabSettings.USER_AGENT)
					.timeout(DartCrabSettings.DEFAULT_TIME_OUT)
					.get();
				
				Elements	viewdocScript = doc.getElementsByTag("script"); // Retrieve javascript only
				
				Pattern		viewdocPattern = Pattern.compile(DartCrabSettings.VIEW_DOC_MATCHER.replace("@@RCP_NO@@", rcpNo),Pattern.MULTILINE);
				Matcher		viewdocMatcher = viewdocPattern.matcher(viewdocScript.toString());
				
				log.info("Fetch a report with rcp_no " + rcpNo + "-->" 
							+String.valueOf(viewdocMatcher.find())
							+", dcmNo = " + viewdocMatcher.group(1)
							+", dtd = " + viewdocMatcher.group(2));
				
				String reportViewRequest = DartCrabSettings.REPORT_VIEW_URL 
									+ "?rcpNo=" + rcpNo
									+ "&dcmNo=" + viewdocMatcher.group(1)
									+ "&eleId=1&offset=0&length=0" // default
									+ "&dtd=" + viewdocMatcher.group(2);
				report = new ReportWebDoc(rcpNo, 
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
	 * 우선 JPA 객체로 ReportWebDoc을 찾아보고 (즉 DB에서 search하고) 없으면 Http로 가져온다.
	 * 일반적으로는 ReportWebDoc을 얻기 위해 이 함수를 사용해야 한다. 
	 * @param rcpNo
	 * @return
	 */
	public static ReportWebDoc loadAndPersistReportWebDoc (String rcpNo) {
		EntityManager em = 
				Persistence.createEntityManagerFactory( "dartcrab-persistence" ).createEntityManager();
		ReportWebDoc result = em.find(ReportWebDoc.class, rcpNo);
		if (result == null ) {
			log.info("New ReportWebDoc");
			result = loadHttpReportWebDoc(rcpNo);
			em.getTransaction().begin();
			em.persist(result);
			em.getTransaction().commit();
		}
		return result;
	}
	
	/**
	 * @deprecated
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
}
