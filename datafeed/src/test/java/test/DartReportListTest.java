package test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dartcrap.datafeed.action.DartReportDocumentFilter;
import com.dartcrap.datafeed.action.DartReportFilter;
import com.dartcrap.datafeed.entity.DartReportHeader;
import com.dartcrap.datafeed.util.FileUtil;

public class DartReportListTest {
	private final static Logger logger = LoggerFactory.getLogger(DartReportListTest.class);
	
	private static String baseDir ="/home/takion77/isincode/stockOptionReport/strike/";
	private static String directory = baseDir + "financialReport/";
	private static String reportHeaderFile = baseDir + "stockOptionReportHeader.txt";
	private static String rptDocumentHeaderFile = baseDir+ "stockOptionRptDocumentHeader.txt";
//	private static String rptDocumentHeaderFile = baseDir+ "stockOptionStrikeRptDocumentHeader_test.txt";
	
	private static String startDate = "2001115";
	private static String endDate = "20150215";
	private static String compName = "";
//	private static String pubType = "A001";결산
	private static String pubType = "E004"; //stockOptionCode
	
//	private static String textCrpCik = "00126380";					// 회사명 검색시 회사코드
//	private static String textCrpNm = "삼성전자";						// 회사명 검색
	private static String reportName = "주식매수선택권행사"; 				
//	private static String typesOfBusiness = "1033"; 					// 제조업
//	private static String corporationType = "P"; 						//유가증권시장
//	private static String closingAccountsMonth = "all" ; 						//결산월결산
	
	private static Set<DartReportHeader> reportRst = new HashSet<DartReportHeader>();
//	private static DartReportDocumentFilter docHeaderFilter = new DartReportDocumentFilter();

	public static void main(String[] args) {
		
//		DartReportFilter aa = new DartReportFilter(startDate, endDate, null, pubType, null);
		DartReportFilter aa = new DartReportFilter(startDate, endDate, null, null, reportName);
		try {
			// report Type 으로 보고서 목록을 파일로 쓰기			
//			FileUtil.writeFile(reportHeaderFile,  aa.getDartReportHeaderSet());
			
// 			보고서 목록 파일의 내용을 읽어서 Document 목록을 파일로 쓰기
//			writeDoumentHeaderFrom(reportHeaderFile, rptDocumentHeaderFile);
//			보고서 목록 파일의 내용을 읽어서 Document 내용을 파일로 쓰기, dtd 의 default 는 dart3.xsd 임 
//			writeReportDocument(rptDocumentHeaderFile, directory, null);
//			주식매수선택권행사는 dtd 가  HTML 
//			writeReportDocument(rptDocumentHeaderFile, directory, "HTML");
			
		} catch (Exception e) {
		}
	}
	
	private static void writeDoumentHeaderFrom(String rptHeaderFile, String rptDocHeaderFile){
		List<String> docHeaderList = new ArrayList<String>();
		List<String> rptHeaderList = new ArrayList<String>();
		
		DartReportDocumentFilter docHeaderFilter = new DartReportDocumentFilter();
		String docHeader;
		String rcpNo;
		String dcmNo;
		int dcmSize=0;
		String url = ""; 
		try {
			rptHeaderList = FileUtil.readFileToList(rptHeaderFile);
			logger.info("ReportHeader :{},{}", rptHeaderList.get(0), rptHeaderList.size());
			for(String aa : rptHeaderList){
				if(aa.split(";").length > 2){
					rcpNo = aa.split(";")[2];		
					dcmNo=docHeaderFilter.getDcmNo(rcpNo);
					dcmSize =docHeaderFilter.getDcmSize(rcpNo);
//					url = docHeaderFilter.getDocUrl();
					url = docHeaderFilter.getDocSubUrl();
					
					docHeader = rcpNo + ";" 
							+ dcmNo + ";" 
							+ dcmSize + ";" 
							+ url +";"
							+ aa
							;
					docHeaderList.add(docHeader);
					logger.info("ReportDocumentHeader :{}", docHeader);
				}
			}
			FileUtil.writeFile(rptDocHeaderFile, docHeaderList);
		} catch (IOException e) {
			logger.info("error");
			e.printStackTrace();
		}
	}
	
	private static void writeReportDocument(String rptDocumentHeaderListFile, String directory, String dtd){
		List<String> rptDocList = new ArrayList<String>();
		
		DartReportDocumentFilter docHeaderFilter = new DartReportDocumentFilter(dtd);
		Document doc ;
		String rcpNo;
		String dcmNo;
		String eleId;
		int dcmSize =0;
		
		try {
			rptDocList = FileUtil.readFileToList(rptDocumentHeaderListFile);
			logger.info("aa : {},{}",rptDocList.get(0));
			for(String aa : rptDocList){
				dcmSize =0;
				String[] arr = aa.split(";");
				dcmSize = Integer.valueOf(arr[2]);
				rcpNo = arr[0];
				dcmNo = arr[1];
				logger.info("parameter : {},{}",arr[0], dcmSize);
				if(dcmSize ==0){
					doc = docHeaderFilter.getReportDocument(rcpNo, dcmNo, "0");
					String fileName = directory + rcpNo +"_"+ dcmNo+ "_" + "0" ;
					FileUtil.writeFile(fileName, doc.toString());
				}
				else{
					for(int i=0 ; i< dcmSize; i++){
						eleId = String.valueOf(i+1);
						doc = docHeaderFilter.getReportDocument(rcpNo, dcmNo, eleId);
						String fileName = directory + rcpNo +"_"+ dcmNo+ "_" +eleId ;
						FileUtil.writeFile(fileName, doc.toString());
						System.out.println("AAA");
					}
				}
			}
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
