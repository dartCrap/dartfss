package com.dartcrab.reports;

import java.util.ArrayList;
import java.util.List;







import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrab.util.DartCrabSettings;

/**
 * Dart의 OpenAPI로 전송하기 위한 Requst를 모델링한다.
 * @author Gi Kim
 * @since Mar-25-2015
 * @version 1.0
 */
public class ReportSearchRequest {
	private static Logger log = LoggerFactory.getLogger( ReportSearchRequest.class );
	
	private String 	auth;				// Auth key issued by FSS. Valid for one year. Needed to be updated on regular basis.
	private String 	crpCd="";			// Company ID. Could be common stock ID (6-digit) or corporate ID number (8-digit)
	private String 	startDt="", endDt=""; // YYYYMMDD
	private String 	finRpt="";			// 'Y/N' - If final report or not?
	private String 	bsnDp=""; 			// See detailed code http://dart.fss.or.kr/dsap001/guide.do
	private int 	timeout=1000000;
	final private int	pageSize = 100;
	public int getTimeout() {
		return timeout;
	}


	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	
	/**
	 * Send an XML OpenAPI request to http://dart.fss.or.kr. 
	 * 
	 * @return ReportSearchResponse
	 * @throws Exception
	 */
	public ReportSearchResponse send() throws Exception {
		String reqBase = DartCrabSettings.API_URL + "?auth="+auth
				+"&crp_cd=" + crpCd
				+"&start_dt=" + startDt + "&end_dt=" + endDt 
				+ "&fin_rpt=" + finRpt 
				+ "&bsn_tp=" + bsnDp; 
		
		List<Document> responseList	= new ArrayList<Document>();
		
		int page = 1;
		
		try{
			String req 				= reqBase + "&page_set="+pageSize+"&page_no="+page;
			log.info("Sending request: "+req);
			Document doc 			= Jsoup.connect(req)
										.userAgent(DartCrabSettings.USER_AGENT)
										.timeout(timeout)
										.get(); // get the first page
			
			log.info("Open API returned: "
					+ "err_msg = " + doc.select("err_msg").text()
					+ ", total_count = " + doc.select("total_count").text()
					+ ", total_page = " + doc.select("total_page").text()
					);
			
			if (doc.select("err_code").text().compareTo("000") != 0) {
				log.error(doc.select("err_code").text());
				throw new Exception ("DART OpenAPI Failed");
			}
			responseList.add(doc);
			
			// 여러 page가 있을경우
			for (page = 2; page <= Integer.parseInt(doc.select("total_page").text()); page++){
				log.info("Fetching more page(s) : page "+ page);
				req 	= reqBase + "&page_set="+pageSize+"&page_no="+page;
				doc 	= Jsoup.connect(req)
							.userAgent("Chrome/41.0.2272.89")
							.timeout(timeout)
							.get(); // get the first page
				responseList.add(doc);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return new ReportSearchResponse(responseList);
	}


	/*
	 * Constructors
	 */
	public ReportSearchRequest(String auth, String crpCd, String startDt,
			String endDt, String finRpt,  String bsnDp) {
		super();
		this.auth = auth;
		this.crpCd = crpCd;
		this.startDt = startDt;
		this.endDt = endDt;
		this.finRpt = finRpt;
		this.bsnDp = bsnDp;
	}

	public ReportSearchRequest(){
		super();
	}

	/*
	 * Getters and setters
	 */
	
	public String getAuth() {
		return auth;
	}

	public ReportSearchRequest setAuth(String auth) {
		this.auth = auth;
		return this;
	}

	public String getCrpCd() {
		return crpCd;
	}

	public ReportSearchRequest setCrpCd(String crpCd) {
		this.crpCd = crpCd;
		return this;
	}

	public String getStartDt() {
		return startDt;
	}

	public ReportSearchRequest setStartDt(String startDt) {
		this.startDt = startDt;
		return this;
	}

	public String getEndDt() {
		return endDt;
	}

	public ReportSearchRequest setEndDt(String endDt) {
		this.endDt = endDt;
		return this;
	}

	public String getFinRpt() {
		return finRpt;
	}

	public ReportSearchRequest setFinRpt(String finRpt) {
		this.finRpt = finRpt;
		return this;
	}

	public String getBsnDp() {
		return bsnDp;
	}

	public ReportSearchRequest setBsnDp(String bsnDp) {
		this.bsnDp = bsnDp;
		return this;
	}

	/*
	 * toString()
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportSearchRequest [auth=" + auth + ", crpCd=" + crpCd
				+ ", startDt=" + startDt + ", endDt=" + endDt + ", finRpt="
				+ finRpt + ", bsnDp=" + bsnDp + "]";
	}
}
