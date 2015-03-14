package com.dartcrap.reports;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.log.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Gi
 *
 */
public class ReportSearchResponse {
	private static Logger log = LoggerFactory.getLogger( ReportSearchResponse.class );

	private List<Document> searchResponseXml;		// XML response from "http://dart.fss.or.kr/api/search.xml?"

	/**
	 * TO-DO
	 */
	public List<ReportHeader> retrieveReportList(){
		List<ReportHeader> reports = null;
		return reports;
	}

	/**
	 * 
	 * @param searchReponseXml
	 */
	public ReportSearchResponse(List<Document> searchResponseXml) {
		super();
		this.searchResponseXml = searchResponseXml;
	}
	
	/**
	 * TO-DO
	 */
	public List<ReportHeader> extractReportHeaders() {
		List<ReportHeader> headers = new LinkedList();
		
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
