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
import com.dartcrab.extractor.InfoExtractor;
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
	public void testReportLoad() throws Exception {
		
		/*System.setProperty("http.proxyHost", "104.223.3.223");
		System.setProperty("http.proxyPort", "7808");
		System.setProperty("https.proxyHost", "104.223.3.223");
		System.setProperty("https.proxyPort", "7808");*/		
		
		ReportSearchRequest listRequest = new ReportSearchRequest()
											.setAuth(properties.getProperty("auth"))
											.setBsnDp("I001")		// 수시공시 (주식매수선택권 행사 포함)
											.setCrpCd("053300")			// 
											.setStartDt("20150303")			//
											.setEndDt("20150303")			// not specified
											;
		
		log.info("Request: " + listRequest);
		
		ReportSearchResponse searchResult = listRequest.send();
		
		int i = 1;
		InfoExtractor extractor = null;
		
		for (ReportHeader header: searchResult.extractReportHeaders()){
			if (header.getRptNm().equals("주식매수선택권행사"))
					extractor = ExtractorDispatcher.getInstance()
								.dispatch(
										ReportWebDocFactory
										.loadAndStoreReportWebDoc(header)
										);
					
					if (extractor != null) extractor.extract().store();
			
			if (i++ > 20) break; // I'd like to test first 20 reports only.
			
		}

		
		//assertEquals( "Should get empty list since nothing is indexed yet", 0, books.size() );

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
