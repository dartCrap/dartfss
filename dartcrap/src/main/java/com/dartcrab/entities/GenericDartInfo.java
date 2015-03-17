package com.dartcrab.entities;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.dartcrab.reports.ReportHeader;
import com.dartcrab.util.DartCrabSettings;

public class GenericDartInfo{
	private ReportHeader	header;
	private Document		document; 
	
	public GenericDartInfo(ReportHeader header){
		this.header = header;
		document = new Document(DartCrabSettings.BASE_URI);
		Element head = document.appendElement("head"); //Conventional header
		document.appendElement(DartCrabSettings.MAIN_TAG);
		document.title(header.getCrpNm()+": "+header.getRptNm() +"("+header.getRcpDt()+")");
		head.appendElement("rcp_no").text(header.getRcpNo());
		head.appendElement("rcp_nm").text(header.getRptNm());
		head.appendElement("crp_cd").text(header.getCrpCd());
		head.appendElement("crp_nm").text(header.getCrpNm());
		head.appendElement("rcp_dt").text(header.getRcpDt());
	}



	public synchronized ReportHeader getHeader() {
		return header;
	}

	@Override
	public String toString() {
		return "GenericDartInfo [header=" + header + ", document=" + document + "]";
	}

	public synchronized Document getDocument() {
		return document;
	}

	public synchronized void setDocument(Document document) {
		this.document = document;
	}
	
	/**
	 * 
	 */
	public Element getMainTagElement(){
		return this.document.getElementsByTag(DartCrabSettings.MAIN_TAG).get(0);
	}
	
	
	/**
	 *	 
	 */
	public GenericDartInfo store(){ 
		try{
			PrintWriter out = new PrintWriter("target/data/"+this.header.getRcpNo()+".xml"); // To do: Parameterize the default file location 
			out.print(this.document.html());
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		return this;
}
	
	
}
