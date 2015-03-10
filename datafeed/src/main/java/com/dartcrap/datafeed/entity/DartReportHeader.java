package com.dartcrap.datafeed.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.dartcrap.datafeed.util.Scrappable;

public class DartReportHeader implements Scrappable{
	private String rcpNo;
	private String reportName;
	private String reportType;
	private String companyId;
	private String companyName;
	private String reporterName;
	private String submitDate;
	private String desc;
	private String subUrl;
	
	
	public DartReportHeader() {
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

	public String getRcpNo() {
		return rcpNo;
	}

	public void setRcpNo(String rcpNo) {
		this.rcpNo = rcpNo;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
		str. append(this.getCompanyId()).append(";")
		   	.append(this.getCompanyName()).append(";")
		   	.append(this.getRcpNo()).append(";")
		   	.append(this.getReportType()).append(";")
		   	.append(this.getReportName()).append(";")
			.append(this.getReporterName()).append(";")
			.append(this.getSubmitDate()).append(";")
			.append(this.getSubUrl());
		return str.toString();
		
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DartReportHeader){
			DartReportHeader other = (DartReportHeader)obj;
			if(this.rcpNo.equals(other.rcpNo)){
				return true;
			}
		}
		return false;
	}
	
	
}
