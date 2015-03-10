package test;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DartReportParser {
	private final static Logger logger = LoggerFactory.getLogger(DartReportParser.class);
	private static Properties properties = new Properties();

	private static String baseDir = "/home/takion77/isincode/stockOptionReport/strike/";
	private static String directory = baseDir + "financialReport/";
	// private static String rptDocumentHeaderFile = directory+
	// "20150127900495_4458079_0";
	// private static String rptDocumentHeaderFile = directory+
	// "20141118900406_4388480_0";
	private static String rptDocumentHeaderFile = directory + "20050725000188_1142923_1";

	private static String dartUrl = "http://dart.fss.or.kr/";

	public static void main(String[] args) {
		try {
			properties.load(DartReportParser.class.getResourceAsStream("/documentItem.properties"));

			/*
			 * List<String> rptHeaderList = FileUtil.readFileToList(baseDir +
			 * "stockOptionStrikeRptDocumentHeader.txt"); String rptUrl;
			 * BufferedWriter out = new BufferedWriter(new FileWriter(baseDir +
			 * "summary.txt"));
			 * 
			 * for(String aa : rptHeaderList){ rptUrl = aa.split(";")[0] +
			 * "_"+aa.split(";")[1] + "_"+ aa.split(";")[2]; //
			 * logger.info("aa: {}", rptUrl);
			 * out.write(getStockOptionStrike(directory + rptUrl));
			 * out.newLine(); } out.close();
			 */
			testStockOptionStrike(rptDocumentHeaderFile);

		} catch (Exception e) {
		}
	}

	private static String getStockOptionStrike(String rptDocumentFile) {
		List<String> rst = new ArrayList<String>();
		File fileInput;
		Document doc;
		// List<DartStockOptionStrikeEntity> rstList = new
		// ArrayList<DartStockOptionStrikeEntity>();
		// DartStockOptionStrikeEntity rstEntity = new
		// DartStockOptionStrikeEntity();

		StringBuffer rstString = new StringBuffer();
		rstString.append(rptDocumentFile.split("/")[7]).append(";");

		String strikeDate = null;
		String stockOptionHolderName = null;
		String companyId = null;
		String companyName = null;

		String relationToCompany = null;
		String strikePrice = null;
		String faceValue = null;
		String individualStrikeStockNum = null;
		String individualNewStockNum = null;
		String individualInnerStockNum = null;
		String individualDiffSettleStockNum = null;

		String totalStockNum = null;
		String totalNewStockNum = null;
		String totalInnerStockNum = null;
		String totalDiffSettleStockNum = null;
		String totalStrikeStockNum = null;
		String strikeStockRatio = null;
		String newListingDate = null;
		String description = null;
		String relatedDoc = null;

		String totalResidualStockOptionNum = null;

		String reviseDate = null;
		String reviseSubmitDate = null;
		String reviseDocumentName = null;
		String revisedItem = null;
		String beforeRevise = null;
		String afterRevise = null;
		String temp;
		Element tr;
		Elements trTags;

		try {
			fileInput = new File(rptDocumentFile);
			doc = Jsoup.parse(fileInput, "UTF-8", "http://dart.fss.or.kr/");
			Elements tables = doc.select("table");
			// trTags = doc.select("table tr") ;;
			// logger.info("siblings :{},{}",trTags);
			int idx = 0;
			for (Element table : tables) {
				trTags = table.select("tr");
				tr = trTags.first();
				do {
					idx = idx + 1;
					temp = tr.child(0).text();
					// logger.info("siblings :{},{}",tr, temp);

					if ("정정일자".equals(temp)) {
						reviseDate = tr.child(1).text();
						rstString.append(reviseDate).append(";");
					} else if ("1. 정정관련 공시서류".equals(temp)) {
						reviseDocumentName = tr.child(1).text();
						rstString = rstString.append(reviseDocumentName).append(";");
					} else if ("2. 정정관련 공시서류 제출일".equals(temp) || "2. 정정관련 공시서류제출일".equals(temp)) {
						reviseSubmitDate = tr.child(1).text();
						rstString = rstString.append(reviseSubmitDate).append(";");
						// logger.info("TR:{}",tr);
					} else if ("4. 정정사항".equals(temp)) {
						tr = tr.nextElementSibling();
						tr = tr.nextElementSibling();
						revisedItem = tr.child(0).text();
						beforeRevise = tr.child(1).text();
						afterRevise = tr.child(2).text();
						// logger.info("TR:{}",tr);

						rstString = rstString.append(revisedItem).append(";");
						rstString = rstString.append(beforeRevise).append(";");
						rstString = rstString.append(afterRevise).append(";");
					} else if ("1. 총발행주식수(주)".equals(temp) || "1. 발행주식총수(주)".equals(temp)
							|| "1. 발행주식총수(주)".equals(temp)) {
						// logger.info("TR1:{}",tr);
						tr = tr.nextElementSibling();
						// logger.info("TR2:{}",tr);
						tr = tr.nextElementSibling();
						// logger.info("TR3:{}",tr);

						if (tr.children().size() > 5) {
							totalStockNum = tr.child(0).text();
							totalNewStockNum = tr.child(1).text();
							totalInnerStockNum = tr.child(2).text();
							totalDiffSettleStockNum = tr.child(3).text();
							totalStrikeStockNum = tr.child(4).text();
							strikeStockRatio = tr.child(5).text();
						} else if (tr.children().size() > 4) {
							// totalStockNum =tr.child(0).text();
							totalNewStockNum = tr.child(0).text();
							totalInnerStockNum = tr.child(1).text();
							totalDiffSettleStockNum = tr.child(2).text();
							totalStrikeStockNum = tr.child(3).text();
							strikeStockRatio = tr.child(4).text();
						}
						rstString = rstString.append(totalStockNum).append(";");
						rstString = rstString.append(totalNewStockNum).append(";");
						rstString = rstString.append(totalInnerStockNum).append(";");
						rstString = rstString.append(totalDiffSettleStockNum).append(";");
						rstString = rstString.append(totalStrikeStockNum).append(";");
						rstString = rstString.append(strikeStockRatio).append(";");

					} else if ("2. 신주상장예정일".equals(temp)) {
						newListingDate = tr.child(1).text();
						rstString = rstString.append(newListingDate).append(";");
					} else if ("3. 기타 투자판단에 참고할 사항".equals(temp)) {
						description = tr.child(1).text();

						tr = tr.nextElementSibling();
						relatedDoc = tr.child(1).text();

						rstString = rstString.append(description).append(";");
						rstString = rstString.append(relatedDoc).append(";");

					} else if ("행사일".equals(temp)) {
						// else
						// if(properties.getProperty("strikeDate").equals(temp)){
						tr = tr.nextElementSibling();
						tr = tr.nextElementSibling();
						// logger.info("TR:{}", new
						// String(properties.getProperty("strikeDate").getBytes("ISO-8859-1"),
						// "UTF-8"));
						// logger.info("TR:{}",
						// properties.getProperty("strikeDate"));

						strikeDate = tr.child(0).text();
						relationToCompany = tr.child(1).text();
						stockOptionHolderName = tr.child(2).text();
						strikePrice = tr.child(3).text();
						faceValue = tr.child(4).text();
						individualNewStockNum = tr.child(5).text();
						individualInnerStockNum = tr.child(6).text();
						individualDiffSettleStockNum = tr.child(7).text();
						individualStrikeStockNum = tr.child(8).text();

						rstString = rstString.append(strikeDate).append(";");
						rstString = rstString.append(relationToCompany).append(";");
						rstString = rstString.append(stockOptionHolderName).append(";");
						rstString = rstString.append(strikePrice).append(";");
						rstString = rstString.append(faceValue).append(";");
						rstString = rstString.append(individualNewStockNum).append(";");
						rstString = rstString.append(individualInnerStockNum).append(";");
						rstString = rstString.append(individualDiffSettleStockNum).append(";");
						rstString = rstString.append(individualStrikeStockNum).append(";");
					} else if ("주식매수선택권잔여주식수(주)".equals(temp)) {
						tr = tr.nextElementSibling();
						tr = tr.nextElementSibling();
						totalResidualStockOptionNum = tr.child(0).text();

						rstString = rstString.append(totalResidualStockOptionNum).append(";");
					}
					tr = tr.nextElementSibling();
				} while (tr != null);
			}
			logger.info("rst : {},{}", idx, rstString.toString());
			return rstString.toString();
		} catch (IOException e) {
			logger.info("error");
			e.printStackTrace();
		}
		return null;
	}

	private static void testStockOptionStrike(String rptDocumentFile) {
		List<String> rst = new ArrayList<String>();
		File fileInput;
		Document doc;
		// List<DartStockOptionStrikeEntity> rstList = new
		// ArrayList<DartStockOptionStrikeEntity>();
		// DartStockOptionStrikeEntity rstEntity = new
		// DartStockOptionStrikeEntity();

		StringBuffer rstString = new StringBuffer();
		rstString.append(rptDocumentFile).append(";");

		String strikeDate = null;
		String stockOptionHolderName = null;
		String companyId = null;
		String companyName = null;

		String relationToCompany = null;
		String strikePrice = null;
		String faceValue = null;
		String individualStrikeStockNum = null;
		String individualNewStockNum = null;
		String individualInnerStockNum = null;
		String individualDiffSettleStockNum = null;

		String totalStockNum = null;
		String totalNewStockNum = null;
		String totalInnerStockNum = null;
		String totalDiffSettleStockNum = null;
		String totalStrikeStockNum = null;
		String strikeStockRatio = null;
		String newListingDate = null;
		String description = null;
		String relatedDoc = null;

		String totalResidualStockOptionNum = null;

		String reviseDate = null;
		String reviseSubmitDate = null;
		String reviseDocumentName = null;
		String revisedItem = null;
		String beforeRevise = null;
		String afterRevise = null;
		String temp="";
		Element tr;
		Element refTr;
		Elements trTags;

		try {
			fileInput = new File(rptDocumentFile);
			doc = Jsoup.parse(fileInput, "UTF-8", "http://dart.fss.or.kr/");
			Elements tables = doc.select("table");
//			trTags = doc.select("table>tbody>tr, table>thead>tr");
			trTags = doc.select("table tr");
//			logger.info("TR Tags :{},{}", trTags.size(), trTags);
			int idx = 0;
			for (int i = 0; i < trTags.size(); i++) {
				logger.info("siblings 1 :{},{}", i, trTags.get(i));
				tr = trTags.get(i);
				logger.info("siblings 2:{},{}", i, tr.children().size());
				if(tr.child(0)!=null){
					temp = tr.child(0).text();
				}
				else {
					temp = "";
				}
				System.out.println("aa");
				
				if ("정정일자".equals(temp)) {
					reviseDate = tr.child(1).text();
					rstString.append(reviseDate).append(";");
				} else if ("1. 정정관련 공시서류".equals(temp)) {
					reviseDocumentName = tr.child(1).text();
					rstString = rstString.append(reviseDocumentName).append(";");
				} else if ("2. 정정관련 공시서류 제출일".equals(temp) || "2. 정정관련 공시서류제출일".equals(temp)) {
					reviseSubmitDate = tr.child(1).text();
					rstString = rstString.append(reviseSubmitDate).append(";");
					// logger.info("TR:{}",tr);
				} else if ("4. 정정사항".equals(temp)) {
					refTr = trTags.get(i+2);
					revisedItem = refTr.child(0).text();
					beforeRevise = refTr.child(1).text();
					afterRevise = refTr.child(2).text();
					// logger.info("TR:{}",tr);

					rstString = rstString.append(revisedItem).append(";");
					rstString = rstString.append(beforeRevise).append(";");
					rstString = rstString.append(afterRevise).append(";");
				} else if ("1. 총발행주식수(주)".equals(temp) || "1. 발행주식총수(주)".equals(temp) || "1. 발행주식총수(주)".equals(temp)) {
					logger.info("TR1:{}", tr);
					refTr = trTags.get(i+2);
					logger.info("TR2:{}", tr);
					
					if (refTr.children().size() > 5) {
						totalStockNum = refTr.child(0).text();
						totalNewStockNum = refTr.child(1).text();
						totalInnerStockNum = refTr.child(2).text();
						totalDiffSettleStockNum = refTr.child(3).text();
						totalStrikeStockNum = refTr.child(4).text();
						strikeStockRatio = refTr.child(5).text();
					} else if (refTr.children().size() > 4) {
						// totalStockNum =tr.child(0).text();
						totalNewStockNum = refTr.child(0).text();
						totalInnerStockNum = refTr.child(1).text();
						totalDiffSettleStockNum = refTr.child(2).text();
						totalStrikeStockNum = refTr.child(3).text();
						strikeStockRatio = refTr.child(4).text();
					}
					rstString = rstString.append(totalStockNum).append(";");
					rstString = rstString.append(totalNewStockNum).append(";");
					rstString = rstString.append(totalInnerStockNum).append(";");
					rstString = rstString.append(totalDiffSettleStockNum).append(";");
					rstString = rstString.append(totalStrikeStockNum).append(";");
					rstString = rstString.append(strikeStockRatio).append(";");

				} else if ("2. 신주상장예정일".equals(temp)) {
					newListingDate = tr.child(1).text();
					rstString = rstString.append(newListingDate).append(";");
				} else if ("3. 기타 투자판단에 참고할 사항".equals(temp)) {
					description = tr.child(1).text();

					refTr = trTags.get(i+1);
					relatedDoc = refTr.child(1).text();

					rstString = rstString.append(description).append(";");
					rstString = rstString.append(relatedDoc).append(";");

				} else if ("행사일".equals(temp)) {
					// elseif(properties.getProperty("strikeDate").equals(temp)){
					refTr = trTags.get(i+2);
					// logger.info("TR:{}", new
					// String(properties.getProperty("strikeDate").getBytes("ISO-8859-1"), "UTF-8"));
					// logger.info("TR:{}", properties.getProperty("strikeDate"));

					strikeDate = refTr.child(0).text();
					relationToCompany = refTr.child(1).text();
					stockOptionHolderName = refTr.child(2).text();
					strikePrice = refTr.child(3).text();
					faceValue = refTr.child(4).text();
					individualNewStockNum = refTr.child(5).text();
					individualInnerStockNum = refTr.child(6).text();
					individualDiffSettleStockNum = refTr.child(7).text();
					individualStrikeStockNum = refTr.child(8).text();

					rstString = rstString.append(strikeDate).append(";");
					rstString = rstString.append(relationToCompany).append(";");
					rstString = rstString.append(stockOptionHolderName).append(";");
					rstString = rstString.append(strikePrice).append(";");
					rstString = rstString.append(faceValue).append(";");
					rstString = rstString.append(individualNewStockNum).append(";");
					rstString = rstString.append(individualInnerStockNum).append(";");
					rstString = rstString.append(individualDiffSettleStockNum).append(";");
					rstString = rstString.append(individualStrikeStockNum).append(";");
				} else if ("주식매수선택권잔여주식수(주)".equals(temp)) {
					refTr = trTags.get(i+2);
					totalResidualStockOptionNum = refTr.child(0).text();

					rstString = rstString.append(totalResidualStockOptionNum).append(";");
				}

			}
			logger.info("rst : {}", rstString.toString());
		} catch (IOException e) {
			logger.info("error");
			e.printStackTrace();
		}
	}

	/*
	 * private static void testStockOptionStrike(String rptDocumentFile){
	 * List<String> rst = new ArrayList<String>(); File fileInput; Document doc
	 * ; // List<DartStockOptionStrikeEntity> rstList = new
	 * ArrayList<DartStockOptionStrikeEntity>(); // DartStockOptionStrikeEntity
	 * rstEntity = new DartStockOptionStrikeEntity();
	 * 
	 * StringBuffer rstString = new StringBuffer();
	 * rstString.append(rptDocumentFile).append(";");
	 * 
	 * String strikeDate =null; String stockOptionHolderName =null; String
	 * companyId=null; String companyName=null;
	 * 
	 * String relationToCompany=null; String strikePrice=null; String
	 * faceValue=null; String individualStrikeStockNum=null; String
	 * individualNewStockNum=null; String individualInnerStockNum=null; String
	 * individualDiffSettleStockNum=null;
	 * 
	 * String totalStockNum=null; String totalNewStockNum=null; String
	 * totalInnerStockNum=null; String totalDiffSettleStockNum=null; String
	 * totalStrikeStockNum=null; String strikeStockRatio=null; String
	 * newListingDate=null; String description=null; String relatedDoc=null;
	 * 
	 * String totalResidualStockOptionNum=null;
	 * 
	 * String reviseDate=null; String reviseSubmitDate=null; String
	 * reviseDocumentName=null; String revisedItem=null; String
	 * beforeRevise=null; String afterRevise=null; String temp ; Element tr;
	 * Elements trTags;
	 * 
	 * try { fileInput = new File(rptDocumentFile); doc = Jsoup.parse(fileInput,
	 * "UTF-8", "http://dart.fss.or.kr/"); Elements tables = doc.select("table")
	 * ; // trTags = doc.select("table tr") ;; //
	 * logger.info("siblings :{},{}",trTags); int idx =0; for(Element table :
	 * tables){ trTags = table.select("tr"); tr =trTags.first(); do { idx
	 * =idx+1; temp = tr.child(0).text(); logger.info("siblings :{},{}",tr,
	 * temp);
	 * 
	 * if("정정일자".equals(temp)){ reviseDate = tr.child(1).text();
	 * rstString.append(reviseDate).append(";"); } else
	 * if("1. 정정관련 공시서류".equals(temp)){ reviseDocumentName= tr.child(1).text();
	 * rstString = rstString.append(reviseDocumentName).append(";"); } else
	 * if("2. 정정관련 공시서류 제출일".equals(temp) || "2. 정정관련 공시서류제출일".equals(temp)){
	 * reviseSubmitDate= tr.child(1).text(); rstString =
	 * rstString.append(reviseSubmitDate).append(";"); //
	 * logger.info("TR:{}",tr); } else if("4. 정정사항".equals(temp)){ tr =
	 * tr.nextElementSibling(); tr = tr.nextElementSibling(); revisedItem
	 * =tr.child(0).text(); beforeRevise =tr.child(1).text(); afterRevise
	 * =tr.child(2).text(); // logger.info("TR:{}",tr);
	 * 
	 * rstString = rstString.append(revisedItem).append(";"); rstString =
	 * rstString.append(beforeRevise).append(";"); rstString =
	 * rstString.append(afterRevise).append(";"); } else
	 * if("1. 총발행주식수(주)".equals(temp) || "1. 발행주식총수(주)".equals(temp) ||
	 * "1. 발행주식총수 (주)".equals(temp)){ logger.info("TR1:{}",tr); tr =
	 * tr.nextElementSibling(); logger.info("TR2:{}",tr); tr =
	 * tr.nextElementSibling(); logger.info("TR3:{},{}",tr,
	 * tr.children().size()); if(tr.children().size() > 5){ totalStockNum
	 * =tr.child(0).text(); totalNewStockNum =tr.child(1).text();
	 * totalInnerStockNum =tr.child(2).text(); totalDiffSettleStockNum =
	 * tr.child(3).text(); totalStrikeStockNum =tr.child(4).text();
	 * strikeStockRatio = tr.child(5).text(); } else if(tr.children().size() >
	 * 4){ // totalStockNum =tr.child(0).text(); totalNewStockNum
	 * =tr.child(0).text(); totalInnerStockNum =tr.child(1).text();
	 * totalDiffSettleStockNum = tr.child(2).text(); totalStrikeStockNum
	 * =tr.child(3).text(); strikeStockRatio = tr.child(4).text(); } rstString =
	 * rstString.append(totalStockNum).append(";"); rstString =
	 * rstString.append(totalNewStockNum).append(";"); rstString =
	 * rstString.append(totalInnerStockNum).append(";"); rstString =
	 * rstString.append(totalDiffSettleStockNum).append(";"); rstString =
	 * rstString.append(totalStrikeStockNum).append(";"); rstString =
	 * rstString.append(strikeStockRatio).append(";");
	 * 
	 * } else if("2. 신주상장예정일".equals(temp)){ newListingDate =
	 * tr.child(1).text(); rstString =
	 * rstString.append(newListingDate).append(";"); } else
	 * if("3. 기타 투자판단에 참고할 사항".equals(temp)){ description = tr.child(1).text();
	 * 
	 * tr =tr.nextElementSibling(); relatedDoc = tr.child(1).text();
	 * 
	 * rstString = rstString.append(description).append(";"); rstString =
	 * rstString.append(relatedDoc).append(";");
	 * 
	 * } else if("행사일".equals(temp)){ // else
	 * if(properties.getProperty("strikeDate").equals(temp)){ tr
	 * =tr.nextElementSibling(); tr =tr.nextElementSibling(); //
	 * logger.info("TR:{}", new
	 * String(properties.getProperty("strikeDate").getBytes("ISO-8859-1"),
	 * "UTF-8")); // logger.info("TR:{}", properties.getProperty("strikeDate"));
	 * 
	 * 
	 * strikeDate = tr.child(0).text(); relationToCompany = tr.child(1).text();
	 * stockOptionHolderName = tr.child(2).text(); strikePrice =
	 * tr.child(3).text(); faceValue = tr.child(4).text(); individualNewStockNum
	 * = tr.child(5).text(); individualInnerStockNum = tr.child(6).text();
	 * individualDiffSettleStockNum = tr.child(7).text();
	 * individualStrikeStockNum = tr.child(8).text();
	 * 
	 * rstString = rstString.append(strikeDate).append(";"); rstString =
	 * rstString.append(relationToCompany).append(";"); rstString =
	 * rstString.append(stockOptionHolderName).append(";"); rstString =
	 * rstString.append(strikePrice).append(";"); rstString =
	 * rstString.append(faceValue).append(";"); rstString =
	 * rstString.append(individualNewStockNum).append(";"); rstString =
	 * rstString.append(individualInnerStockNum).append(";"); rstString =
	 * rstString.append(individualDiffSettleStockNum).append(";"); rstString =
	 * rstString.append(individualStrikeStockNum).append(";"); } else
	 * if("주식매수선택권잔여주식수(주)".equals(temp)){ tr =tr.nextElementSibling(); tr
	 * =tr.nextElementSibling(); totalResidualStockOptionNum =
	 * tr.child(0).text();
	 * 
	 * rstString = rstString.append(totalResidualStockOptionNum).append(";"); }
	 * tr = tr.nextElementSibling(); } while(tr!=null); }
	 * logger.info("rst : {}", rstString.toString()); } catch (IOException e) {
	 * logger.info("error"); e.printStackTrace(); } }
	 */
}
