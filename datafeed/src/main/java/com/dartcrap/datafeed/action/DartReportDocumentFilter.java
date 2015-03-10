package com.dartcrap.datafeed.action;

/**
 * 1. Write Document File with the result of the request on rcpNo
 * 	 1.1 get dcmNo and dcmSize
 *   1.2 loop on dcmSize with rcpNo, dcmNo 
 * 2. On the same time write the list of Document File    
 * 	  
 *  
 *   
*/
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrap.datafeed.util.FileUtil;
import com.dartcrap.datafeed.util.WebScrappUtil;

/**
 * @author takion77
 *
 */

public class DartReportDocumentFilter {
	private final Logger logger = LoggerFactory.getLogger(DartReportDocumentFilter.class);

	private String dartUrl = "http://dart.fss.or.kr";
//	private String rptSubUrl = "/dsaf001/main.do?rcpNo=";
	private String rptSubUrl = "/dsaf001/main.do";
	private String docSubUrl = "//report/viewer.do";
	
	private String url = "http://dart.fss.or.kr/dsaf001/main.do?rcpNo=";

	private String rcpNo ="20131231000048";
	private String dcmNo = "3997747";
	private int dcmSize ;
	private String eleId = "5";
	private String offset = "0";
	private String length = "0";
	private String dtd ="dart3.xsd";
	
	private Map<String, String> argMap = new HashMap<String, String>();

	public DartReportDocumentFilter() {
	}
	
	public DartReportDocumentFilter( String dtd) {
		this.dtd = dtd;
		
		argMap.put("offset", offset);
		argMap.put("length", length);
		argMap.put("dtd", dtd);
	}
	
	public DartReportDocumentFilter(String rcpNo, String dcmNo, String eleId, String dtd) {
		this.rcpNo = rcpNo;
		this.dcmNo = dcmNo;

		this.dtd = dtd;
		
		argMap.put("rcpNo", rcpNo);
		argMap.put("dcmNo", dcmNo);
		argMap.put("eleId", eleId);
		argMap.put("offset", offset);
		argMap.put("length", length);
		argMap.put("dtd", dtd);
	}

//	**************************Getter & Setter ******************

	public String getDartUrl() {
		return dartUrl;
	}

	public void setDartUrl(String dartUrl) {
		this.dartUrl = dartUrl;
	}


	public String getRptSubUrl() {
		return rptSubUrl;
	}

	public void setRptSubUrl(String rptSubUrl) {
		this.rptSubUrl = rptSubUrl;
	}

	public String getDocSubUrl() {
		return docSubUrl;
	}

	public void setDocSubUrl(String docSubUrl) {
		this.docSubUrl = docSubUrl;
	}

	public String getRcpNo() {
		return rcpNo;
	}

	public void setRcpNo(String rcpNo) {
		this.rcpNo = rcpNo;
	}

	public String getDcmNo() {
		return dcmNo;
	}

	public void setDcmNo(String dcmNo) {
		argMap.put("dcmNo", dcmNo);
		this.dcmNo = dcmNo;
	}

	public int getDcmSize() {
		return dcmSize;
	}

	public void setDcmSize(int dcmSize) {
		this.dcmSize = dcmSize;
	}

	public String getEleId() {
		argMap.put("eleId", eleId);
		return eleId;
	}

	public void setEleId(String eleId) {
		this.eleId = eleId;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
	public String getDtd() {
		return dtd;
	}

	public void setDtd(String dtd) {
		this.dtd = dtd;
	}

	public String getRptUrl(){
		return getDartUrl() + getRptSubUrl();
	}
	public String getDocUrl(){
		return getDartUrl() + getDocSubUrl();
		
	}
//**************************End of Getter & Setter ******************
			
//**************************Public Method**************************************	
	
	public void writeDartReportDocument(String reportListFile, String directory){
		Map<String, String> arg = new HashMap<String, String>();
		try {
			List<String> list = FileUtil.readFileToList(reportListFile);
			for (String aa : list) {
				String dcmNo = "";
				String writeTo = directory + arg.get("rcpNo") + "_" + arg.get("dcmNo") + "_" + arg.get("eleId");
				for(int j=0 ; j< dcmSize; j++){
					FileUtil.writeFile(writeTo, getReportDocument(rcpNo, dcmNo, eleId).toString());
				}
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public void writeDartReportDocumentFrom(String docmentListFile, String directory){
		Map<String, String> arg = new HashMap<String, String>();
		try {
			List<String> list = FileUtil.readFileToList(docmentListFile);
			for (String aa : list) {
				String dcmNo = "";
				String writeTo = directory + arg.get("rcpNo") + "_" + arg.get("dcmNo") + "_" + arg.get("eleId");
				for(int j=0 ; j< dcmSize; j++){
					FileUtil.writeFile(writeTo, getReportDocument(rcpNo, dcmNo, eleId).toString());
				}
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
//	**************************Private Method******************************
	
	public Document  getReportDocument( String rcpNo, String dcmNo, String eleId) {
			Map<String, String> arg = new HashMap<String, String>();
			arg.put("rcpNo", rcpNo);
			arg.put("dcmNo", dcmNo);
			arg.put("eleId", eleId);
			arg.put("offset", offset);
			arg.put("length", length);
//			arg.put("dtd", dtd);

			Document doc = WebScrappUtil.post(getDocUrl(), arg);
			return doc;
	}
	
	public Document  getReportDocument( String rcpNo, String dcmNo, String eleId, String dtd) {
		Map<String, String> arg = new HashMap<String, String>();
		arg.put("rcpNo", rcpNo);
		arg.put("dcmNo", dcmNo);
		arg.put("eleId", eleId);
		arg.put("offset", offset);
		arg.put("length", length);
		if(dtd == null){
			arg.put("dtd", "dart3.xsd");
		}
		else{
			arg.put("dtd", dtd);
		}

		Document doc = WebScrappUtil.post(getDocUrl(), arg);
		return doc;
	}
	public String getDcmNo(String rcpNo) {
		String dcmNo = "";
		Map<String, String> arg = new HashMap<String, String>();
		arg.put("rcpNo", rcpNo);
		Document doc = WebScrappUtil.get(getRptUrl(), arg);

//		Document doc = WebScrappUtil.get(getRptUrl() + rcpNo);
		
		Elements doclet = doc.select("ul>li>a[href=#download");
//		logger.info("Doclet In the GetDcmNo : {},{}", doclet);
		for (Element aa : doclet) {
			if(aa.hasAttr("onclick")){
//			<a href="#download" onclick="openPdfDownload('20150123000299', '4455769'); return false;">
//				<img src="/images/common/viewer_down.gif" style="cursor:pointer;" alt="다운로드" title="다운로드" />
//			</a> 를 파싱한다.
				dcmNo = aa.attr("onclick").split("', '")[1].split("'")[0];
			}
		}
//		logger.info("In the GetDcmNo : {},{}", rcpNo, dcmNo);
		return dcmNo;
	}

	public int getDcmSize(String rcpNo) {
		int dcmSize = 0;
		String[] funNames = null;
		
		Map<String, String> arg = new HashMap<String, String>();
		arg.put("rcpNo", rcpNo);
		Document doc = WebScrappUtil.post(getRptUrl() ,arg);
//		Document doc = WebScrappUtil.get(getRptUrl() + rcpNo);

//		Elements doclet = doc.select("script[type=text/javascript]");
		Elements doclet = doc.select("script:not([src])");
		
		for (Element aa : doclet) {
			if(aa.toString().contains("initPage")){
				funNames = doclet.toString().split("function initPage()")[1].split("appendChild");
				break;
			}
		}
		dcmSize = funNames.length -1;
		logger.info("Number of Document in the Report : {},{}", dcmSize);
		return Integer.valueOf(dcmSize);
	}
	
}
