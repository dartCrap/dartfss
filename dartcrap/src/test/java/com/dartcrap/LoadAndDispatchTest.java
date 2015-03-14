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

import com.dartcrap.entity.ReportHeader;



/**
 * Example test cases
 * @author Gi
 *
 */
public class LoadAndDispatchTest {
	private EntityManagerFactory emf;

	private EntityManager em;

	private static Logger log = LoggerFactory.getLogger( IndexAndSearchTest.class );
	
	private java.util.Properties properties;

	@Before
	public void setUp() {
		
		//initHibernate();
		
		try{ 
			this.properties = new java.util.Properties();
			FileInputStream in = new FileInputStream("src/main/resources/dartcrap.properties");
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
		
		ReportSearchRequest listRequest = new ReportSearchRequest()
											.setAuth(properties.getProperty("auth"))
											.setBsnDp("E004")		// Stock Option
											.setCrpCd("")			// any company...
											.setStartDt("20150101")			
											.setEndDt("")			// not specified
											;
		
		log.info("Request: " + listRequest);
		
		ReportSearchResponse searchResult = listRequest.send();
		
		for (ReportHeader header: searchResult.extractReportHeaders()){
			ReportWebDocFactory.loadHttpReportWebDoc(header.getRcpNo())
					.storeFile();
			break; // test...
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
