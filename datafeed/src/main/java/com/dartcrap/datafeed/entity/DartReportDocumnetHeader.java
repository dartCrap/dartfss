package com.dartcrap.datafeed.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dartcrap.datafeed.util.Scrappable;

public class DartReportDocumnetHeader implements Scrappable{
	// SmallChange
	
	private String rcpNo;
	private String dcmNo;
	private int dcmSize;
	
	private String reportName;
	private String companyId;
	private String companyName;

	private String subUrl;

	
	
	public DartReportDocumnetHeader() {
	}
	
	public DartReportDocumnetHeader(String rcpNo, String dcmNo, int dcmSize,  String subUrl) {
		this.rcpNo = rcpNo;
		this.dcmNo = dcmNo;
		this.dcmSize = dcmSize;
		this.subUrl = subUrl;
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
		this.dcmNo = dcmNo;
	}

	public int getDcmSize() {
		return dcmSize;
	}

	public void setDcmSize(int dcmSize) {
		this.dcmSize = dcmSize;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
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

	public String getSubUrl() {
		return subUrl;
	}

	public void setSubUrl(String subUrl) {
		this.subUrl = subUrl;
	}

	@Override
	public String toString(){
		StringBuffer str = new StringBuffer();
		str.append(this.getRcpNo()).append(";")
			.append(this.getDcmNo()).append(";")
		   	.append(this.getDcmSize()).append(";")
//			.append("1;1;dart3.xsd")
		   	;
		return str.toString();
		
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DartReportDocumnetHeader){
			DartReportDocumnetHeader other = (DartReportDocumnetHeader)obj;
			if(this.rcpNo.equals(other.rcpNo)){
				return true;
			}
		}
		return false;
	}
	
}
