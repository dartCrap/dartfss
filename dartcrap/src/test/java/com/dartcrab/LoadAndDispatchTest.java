package com.dartcrab;

import java.io.FileInputStream;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.reports.ReportHeader;
import com.dartcrab.reports.ReportSearchRequest;
import com.dartcrab.reports.ReportSearchResponse;
import com.dartcrab.reports.ReportWebDocFactory;


/**
 * dartcrab 기본 라이브러리의 작동을 확인하기 위한 JUnit test method의 모음
 * 
 * @author Gi
 * @version 1.0
 * @category Test cases
 * @since Mar-25-2015
 * 
 */
public class LoadAndDispatchTest {
	private static Logger log = LoggerFactory.getLogger( LoadAndDispatchTest.class );
	
	// Hibernate 
	private EntityManagerFactory emf;
	private EntityManager em;
	
	
	private java.util.Properties properties;

	@Before
	public void setUp() {
		
		initHibernate();
		
		try{ 
			this.properties 	= new java.util.Properties();
			FileInputStream in 	= new FileInputStream("src/main/resources"
										+ "/dartcrab.properties");
			this.properties.load(in);
			in.close();
		} catch (Exception e){
				e.printStackTrace();
		}
		
		// Proxy setting
		System.setProperty("http.proxyHost", properties.getProperty("http.proxyHost"));
		System.setProperty("http.proxyPort", properties.getProperty("http.proxyPort"));
		System.setProperty("https.proxyHost", properties.getProperty("https.proxyHost"));
		System.setProperty("https.proxyPort", properties.getProperty("https.proxyPort"));

	}

	@After
	public void tearDown() {
		purge();
	}

	/**
	 * Dart 사이트의 OpenAPI를 사용하여, 지정 기간 내의 보고서 목록을 가져온다.
	 * 현재 주식매수권행사과 파생결합증권 두 가지에 대해 각각 함수를 만들어 놓았다.
	 * 서비스 오픈 전 개발단계에서부터 미리 Dart 사이트의 정보를 서비스 환경으로 가져올 필요가 있다.
	 * 
	 * @throws Exception
	 */
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

	/**
	 * Dart 사이트의 OpenAPI를 사용하여, 지정 기간 내의 보고서 목록을 가져온다.
	 * 현재 주식매수권행사과 파생결합증권 두 가지에 대해 각각 함수를 만들어 놓았다.
	 * 서비스 오픈 전 개발단계에서부터 미리 Dart 사이트의 정보를 서비스 환경으로 가져올 필요가 있다.
	 * 
	 * @throws Exception
	 */
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

	/**
	 * 본 프로젝트에서는 Dart 사이트에서 바로 가져온 HTML 형태의 공시보고서를 ReportWebDoc으로 부르고 있다
	 * 본 함수는 HTML 보고서를 가져와서 JPA 인터페이스로 저장 (Persist) 하는 역할을 수행한다
	 * 향후 서비스가 운영되면 본 함수는 서비스로 등록되어야 한다
	 * 
	 * @throws Exception
	 */
	@Test
	public void extractReportWebDoc() throws Exception {

		em.getTransaction().begin();
		
		
		@SuppressWarnings("unchecked")
		List<ReportHeader> headers = (List<ReportHeader>) em.createNativeQuery(
				"select h.* from ReportHeader h left outer join ReportWebDoc d "
				+ "on h.rcpNo = d.rcpNo where d.rcpNo is null", ReportHeader.class)
				.getResultList();
	
		for (ReportHeader header : headers){
			try{
				ReportWebDocFactory.loadAndPersistReportWebDoc(header.getRcpNo());
			}	catch (Exception e){
				log.error("Http Fetch failed)");
				continue;
			}
			
			// Dart 사이트는 DDoS 공격 방어벽이 있다. Web Scrapping은 공격으로 오해받을수 있으므로
			// 적절한 interval로 가져오도록 한다.
			Thread.sleep(new Random().nextInt(20000)+3000);
		}	
	
	}


	
	// Private methods
	private void initHibernate() {
		emf = Persistence.createEntityManagerFactory( "dartcrab-persistence" );
		em = emf.createEntityManager();
	}

	private void purge() {

		emf.close();
	}

}
