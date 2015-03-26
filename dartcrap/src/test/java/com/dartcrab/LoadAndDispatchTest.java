package com.dartcrab;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.extractor.ExtractorDispatcher;
import com.dartcrab.extractor.ReportExtractor;
import com.dartcrab.reports.ReportHeader;
import com.dartcrab.reports.ReportSearchRequest;
import com.dartcrab.reports.ReportSearchResponse;
import com.dartcrab.reports.ReportWebDoc;
import com.dartcrab.reports.ReportWebDocFactory;



/**
 * Example test cases
 * @author Gi
 *
 */
public class LoadAndDispatchTest {
	private EntityManagerFactory emf;

	private EntityManager em;

	private static Logger log = LoggerFactory.getLogger( LoadAndDispatchTest.class );
	
	private java.util.Properties properties;

	@Before
	public void setUp() {
		System.setProperty("http.proxyHost", "54.225.133.44");
		System.setProperty("http.proxyPort", "8118");
		System.setProperty("https.proxyHost", "54.225.133.44");
		System.setProperty("https.proxyPort", "8118");
		
		
		initHibernate();
		
		try{ 
			this.properties = new java.util.Properties();
			FileInputStream in = new FileInputStream("src/main/resources"
					+ "/dartcrab.properties");
			this.properties.load(in);
			in.close();
		} catch (Exception e){
				e.printStackTrace();
		} 
	}

	@After
	public void tearDown() {
		purge();
	}

	@Test
	public void searchReportStockOptionExec() throws Exception {
				
		ReportSearchRequest listRequest = new ReportSearchRequest()
											.setAuth(properties.getProperty("auth"))
											.setBsnDp("I001")		
											.setCrpCd("")		 
											.setStartDt("20150101")	
											.setEndDt("20150325")	
											.setFinRpt("Y")
											;
		
		log.info("Request: " + listRequest);
		
		ReportSearchResponse searchResult = listRequest.send();
		
		em.getTransaction().begin();
		for (ReportHeader header: searchResult.extractReportHeaders()){
			if(header.getRptNm().contains("주식매수선택권행사")) 
				em.merge(header);
		}
		em.getTransaction().commit();
	}

	@Test
	public void searchReportDls() throws Exception {
				
		ReportSearchRequest listRequest = new ReportSearchRequest()
											.setAuth(properties.getProperty("auth"))
											.setBsnDp("C003")		
											.setCrpCd("")		 
											.setStartDt("20150101")	
											.setEndDt("20150325")	
											.setFinRpt("Y")
											;
		
		log.info("Request: " + listRequest);
		
		ReportSearchResponse searchResult = listRequest.send();
		
		em.getTransaction().begin();
		for (ReportHeader header: searchResult.extractReportHeaders()){
			if(header.getRptNm().contains("일괄신고추가서류")) 
				em.merge(header);
		}
		em.getTransaction().commit();
	}

	
	
	@Test
	public void extractReport() throws Exception {

		em.getTransaction().begin();
		
		
		@SuppressWarnings("unchecked")
		List<ReportHeader> headers = (List<ReportHeader>) em.createQuery(
				"select h from ReportHeader h")
				.getResultList();
		
//		ReportExtractor extractor = null;
	
		for (ReportHeader header : headers){
			log.info(header.getRcpNo());
			ReportWebDoc doc = ReportWebDocFactory.loadAndPersistReportWebDoc(header.getRcpNo());
//			extractor = ExtractorDispatcher.getInstance()
//						.dispatch();
//					
//			if (extractor != null) extractor.extract();
//			log.info("before");
//			Thread.sleep(new Random().nextInt(0)+0);
//			log.info("after");
		}	
	
	}

	private void initHibernate() {
		emf = Persistence.createEntityManagerFactory( "dartcrab-persistence" );
		em = emf.createEntityManager();
	}

	private void purge() {

		emf.close();
	}

	

}
