package com.dartcrap;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.dartcrab.Book;
import com.dartcrab.extractor.ExtractorDispatcher;
import com.dartcrab.extractor.ReportExtractor;
import com.dartcrab.reports.ReportHeader;
import com.dartcrab.reports.ReportSearchRequest;
import com.dartcrab.reports.ReportSearchResponse;
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
		
		//initHibernate();
		
		try{ 
			this.properties = new java.util.Properties();
			FileInputStream in = new FileInputStream("src/main/resources"
					+ "/dartcrap.properties");
			this.properties.load(in);
			in.close();
		} catch (Exception e){
				e.printStackTrace();
		} 
	}

	@After
	public void tearDown() {
		//purge();
	}

	@Test
	public void searchReport() throws Exception {
		
		/*System.setProperty("http.proxyHost", "104.223.3.223");
		System.setProperty("http.proxyPort", "7808");
		System.setProperty("https.proxyHost", "104.223.3.223");
		System.setProperty("https.proxyPort", "7808");*/		
		
		ReportSearchRequest listRequest = new ReportSearchRequest()
											.setAuth(properties.getProperty("auth"))
											.setBsnDp("C003")		
											.setCrpCd("016360")		 
											.setStartDt("20150312")	
											.setEndDt("20150312")	
											;
		
		log.info("Request: " + listRequest);
		
		ReportSearchResponse searchResult = listRequest.send();
		
		int i = 1;
		
		for (ReportHeader header: searchResult.extractReportHeaders()){
	
			ReportWebDocFactory.loadAndStoreReportWebDoc(header);
		
			if (i++ > 20) break; // I'd like to test first 20 reports only.
		}
		//assertEquals( "Should get empty list since nothing is indexed yet", 0, books.size() );
	}

	
	@Test
	public void extractReport() throws Exception {
		// TO-DO
		String[][] rcpList = {
				{"20150312000239","일괄신고추가서류( 결합증권 )"}
//				{"20150303900342","주식매수선택권행사"}
			};
		
		ReportExtractor extractor = null;
		
		for (String[] rcpHeader : rcpList){
			//TO-DO
			ReportHeader header = new ReportHeader(rcpHeader[0], null, null,null, rcpHeader[1], null, null,null);
			
			extractor = ExtractorDispatcher.getInstance()
						.dispatch(
								ReportWebDocFactory
								.loadAndStoreReportWebDoc(header)
								);
					
			if (extractor != null) extractor.extract();
		}	
	
	}

	private void initHibernate() {
		emf = Persistence.createEntityManagerFactory( "dartcrap-persistence" );
		em = emf.createEntityManager();
	}

	private void purge() {
		FullTextEntityManager ftEm = org.hibernate.search.jpa.Search.getFullTextEntityManager( em );
		ftEm.purgeAll( Book.class );
		ftEm.flushToIndexes();
		ftEm.close();
		emf.close();
	}

	

}
