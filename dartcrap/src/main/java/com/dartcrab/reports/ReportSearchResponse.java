package com.dartcrab.reports;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dart의 OpenAPI로 전송한 Request에 대한 Response를 모델링한 Class이다.
 * 
 * @author Gi Kim
 * @version 1.0
 * @since Mar-25-2015
 * 
 */
public class ReportSearchResponse {
	private static Logger log = LoggerFactory.getLogger( ReportSearchResponse.class );

	private List<Document> searchResponseXml;		// XML response from "http://dart.fss.or.kr/api/search.xml?"


	/*
	 * Constructors
	 */
	public ReportSearchResponse(List<Document> searchResponseXml) {
		super();
		this.searchResponseXml = searchResponseXml;
	}
	
	
	/**
	 * OpenAPI에서 가져온 공시보고서를 ReportHeader객체의 List로 반환한다.
	 * @return List<ReportHeader>
	 */

	public List<ReportHeader> retrieveReportList(){
		List<ReportHeader> reports = null;
		return reports;
	}
	
	/**
	 * TO-DO
	 */
	public List<ReportHeader> extractReportHeaders() {
		List<ReportHeader> headers = new LinkedList<ReportHeader>();
		
		for (Document d: searchResponseXml){
			Elements elements = d.select("list");
			for (Element e : elements){
				ReportHeader header= null;
				header = new ReportHeader(
							e.getElementsByTag("rcp_no").text(), 
							e.getElementsByTag("crp_cd").text(),
							e.getElementsByTag("crp_nm").text(),
							e.getElementsByTag("crp_cls").text(),
							e.getElementsByTag("rpt_nm").text(),
							e.getElementsByTag("flr_nm").text(),
							e.getElementsByTag("rcp_dt").text(),
							e.getElementsByTag("rmrk").text()
						);
				headers.add(header);
				log.info("Report found: "+header.toString());
			}

		}
		
		return headers;
	}
}
