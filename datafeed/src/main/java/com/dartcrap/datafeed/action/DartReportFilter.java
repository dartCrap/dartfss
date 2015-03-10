package com.dartcrap.datafeed.action;

/**
 * 1. Get DartReport Entity from Dart URL through Web Method get/post with argument startDate, endDate, publicType
 * 		example : 20140101 , 20150209, E004(StockOption)
 * 2. Write the result to File   
 * 	  
 * 3. Get DartRerpot with companyId 
 *   
*/
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrap.datafeed.entity.DartReportHeader;
import com.dartcrap.datafeed.util.WebScrappUtil;

/**
 * @author takion77
 *
 */
public class DartReportFilter {
	private final Logger logger = LoggerFactory.getLogger(DartReportFilter.class);
		
	private String startDate;
	private String endDate;
	private String companyId;
	private String companyName;
	private String finalReport = "recent";
	private String publicType;
	private int currentPage;
	
//	private  String textCrpCik = "00126380";					// 회사명 검색시 회사코드
//	private  String textCrpNm = "삼성전자";						// 회사명 검색
	private  String reportName = "주식매수선택권행사"; 				
	
	private  String typesOfBusiness = "all"; 					// 1033 : 제조업
	private  String corporationType = "all"; 						// P: 유가증권시장 A: 코스닥
	private  String closingAccountsMonth = "all" ; 				//결산월결산


	private String dartUrl = "http://dart.fss.or.kr";
	private String subUrl  ;
	private String rptTypeSubUrl = "/dsab001/search.ax";
	private String rptNameSubUrl = "/dsab002/search.ax";
	
//	private String url = "http://dart.fss.or.kr/dsab001/search.ax";
	
	private Map<String, String> argMap = new HashMap<String, String>();
//	private Set<DartReportHeader> reportHeaderSet = new HashSet<DartReportHeader>();

	public DartReportFilter() {
	}
	
	public DartReportFilter(String stDate, String endDate, String companyName, String publicType, String reportName) {
		this.startDate = stDate;
		this.endDate = endDate;
		this.currentPage = 1;
		this.companyName = companyName;
		this.publicType = publicType;
		this.reportName = reportName;

		argMap.put("startDate", startDate);
		argMap.put("endDate", endDate);
		
		argMap.put("currentPage", String.valueOf(currentPage));
		argMap.put("finalReport", finalReport);
		argMap.put("maxResults", "15");				//Default : 15
		argMap.put("maxLinks", "10");				//Default : 10
		argMap.put("typesOfBusiness", typesOfBusiness);
		argMap.put("corporationType", corporationType);
		argMap.put("closingAccountsMonth", closingAccountsMonth);

		if (companyName != null) {
			argMap.put("textCrpNm", companyName);
		}
		if (publicType != null) {
			argMap.put("publicType", publicType);
			subUrl = rptTypeSubUrl;
		}
//		ReportName 으로 검색시 publicType 은 입력하지 않음.
		if (reportName != null) {
			argMap.put("reportName", reportName);
			subUrl = rptNameSubUrl;
		}
	}

//	**************************Getter & Setter ******************
			
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFinalReport() {
		return finalReport;
	}

	public void setFinalReport(String finalReport) {
		this.finalReport = finalReport;
	}

	public String getPublicType() {
		if(publicType==null){
			return "Z000";
		}
		return publicType;
	}

	public void setPublicType(String publicType) {
		argMap.put("publicType", publicType);
		this.publicType = publicType;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		argMap.put("currentPage", String.valueOf(currentPage));
		this.currentPage = currentPage;
	}

	public String getDartUrl() {
		return dartUrl;
	}

	public void setDartUrl(String dartUrl) {
		this.dartUrl = dartUrl;
	}

	public String getSubUrl() {
		return subUrl;
	}

	public void setSubUrl(String subUrl) {
		this.subUrl = subUrl;
	}

	public Map<String, String> getArgMap() {
		return argMap;
	}

	public void setArgMap(Map<String, String> argMap) {
		this.argMap = argMap;
	}
	public String getUrl(){
		return getDartUrl() + getSubUrl();
	}

	//*****************Public Method****************************************	
	
	public Set<DartReportHeader> getDartReportHeaderSet() {
		Set<DartReportHeader> rst = new HashSet<DartReportHeader>();
		int size = getPageNum();
		for(int i=0 ; i< size; i++){
			rst.addAll(getDartReportHeaderSet(i+1));
		}
		logger.info("Total Report Header Size:{},{}", rst.size());
		return rst;
	}


//****************************private method********************************888

	private Set<DartReportHeader> getDartReportHeaderSet(int pageIndex) {
		Set<DartReportHeader> rst = new HashSet<DartReportHeader>();
//		logger.info("Map : {}", argMap);
		setCurrentPage(pageIndex);
		
		Document doc = WebScrappUtil.post(getUrl(), getArgMap());
//		logger.info("zzz: {},{}",  getArgMap(),doc);
		Elements doclet = doc.select("div[class=table_list]>table>tbody>tr");

//		logger.info("Doclet : {},{}", doclet.first());
//		logger.info("Doclet1 : {},{}", doclet.first().child(1).select("[onclick]").attr("onclick").split("'")[1]);

		if(doclet.isEmpty()){
			logger.error("Report List Error : {}");
		}
		else{
			for (Element el : doclet) {
				DartReportHeader temp = new DartReportHeader();
//			temp.setCompanyId(el.child(1).child(0).child(1).attr("onclick").substring(14, 22));
//			temp.setCompanyId(el.child(1).select("[onclick]").attr("onclick").substring(14, 22));
				temp.setCompanyId(el.child(1).select("[onclick]").attr("onclick").split("'")[1]);
				temp.setCompanyName(el.child(1).text());
				temp.setReportName(el.child(2).text());
				temp.setRcpNo(el.child(2).child(0).attr("href").split("=")[1]);
				temp.setSubUrl(el.child(2).child(0).attr("href"));
				temp.setReporterName(el.child(3).text());
				temp.setSubmitDate(el.child(4).text());
				temp.setDesc(el.child(5).text());
				temp.setReportType(getPublicType());
				
				rst.add(temp);
			}
		}
		return rst;
	}

/**
 * 	get the number of report list in the responded pages
 * 
*/	
	private int getPageNum() {
		setCurrentPage(1);
		Document doc = WebScrappUtil.post(getUrl(), this.getArgMap());
		Elements doclet = doc.select("div[class=page_list]>p[class=page_info]");
		logger.info("AAA1:{},{}",  doclet);
		
		String[] pageString = doclet.text().split("]");

		if (pageString.length > 1) {
//			logger.info("aa:{}",pageString[0].split("/")[1]);
//			int indexname = pageString[0].lastIndexOf("/");
//			String pageNum = pageString[0].substring(indexname + 1, pageString[0].length());
			
			String pageNum = pageString[0].split("/")[1];
			return Integer.valueOf(pageNum);
		}
		return 1;
	}
}
