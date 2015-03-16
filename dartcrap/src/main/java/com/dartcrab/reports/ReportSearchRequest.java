package com.dartcrab.reports;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;







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
public class ReportSearchRequest {
	private static Logger log = LoggerFactory.getLogger( ReportSearchRequest.class );
	
	private String auth;		// Authentification key issued by FSS. Valid for one year. Needed to be updated on regular basis.
	private String crpCd="";		// Company ID. Could be common stock ID (6-digit) or corporate ID number (8-digit)
	private String startDt="", endDt=""; // YYYYMMDD
	private String finRpt="";		// 'Y/N' - If final report or not?
	private String bsnDp=""; 		// See detailed code http://dart.fss.or.kr/dsap001/guide.do
	private int timeout=1000000;
	final private int	pageSize = 100;
	public int getTimeout() {
		return timeout;
	}


	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	
	/**
	 * TO-DO: send an XML OpenAPI request to http://dart.fss.or.kr. Retrive reponse
	 */
	public ReportSearchResponse send() throws Exception {
		String reqBase = DartCrabSettings.API_URL + "?auth="+auth
				+"&crp_cd=" + crpCd
				+"&start_dt=" + startDt + "&end_dt=" + endDt 
				+ "&fin_rpt=" + finRpt 
				+ "&bsn_tp=" + bsnDp; 
		
		List<Document> responseList = new ArrayList();
		
		int page = 1;
		
		try{
			String req = reqBase + "&page_set="+pageSize+"&page_no="+page;
			log.info("Sending request: "+req);
			Document doc = Jsoup.connect(req)
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
			
			for (page = 2; page <= Integer.parseInt(doc.select("total_page").text()); page++){
				log.info("Fetching more page(s) : page "+ page);
				req = reqBase + "&page_set="+pageSize+"&page_no="+page;
				doc = Jsoup.connect(req)
						.userAgent("Chrome/41.0.2272.89")
						.timeout(timeout)
						.get(); // get the first page
				responseList.add(doc);
			}
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		} finally{
			
		}
		
		
		return new ReportSearchResponse(responseList);
	}


	/**
	 * TO-DO: Constructors
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

	/**
	 *  TO-DO: Getters and setters
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





	@Override
	public String toString() {
		return "ReportSearchRequest [auth=" + auth + ", crpCd=" + crpCd
				+ ", startDt=" + startDt + ", endDt=" + endDt + ", finRpt="
				+ finRpt + ", bsnDp=" + bsnDp + "]";
	}
	

}
