package com.dartcrab.reports;

import javax.persistence.*;

/**
 * 본 Class는 OpenAPI를 통해 가져오는 각 보고서 항목을 모델링한다.
 * 일단 한번 가져온 항목은 JPA로 관리한다.
 * 
 * @author Gi Kim
 * @version 1.0
 * @since Mar-25-2015
 */
@Entity
@Table
public class ReportHeader {
	@Id
	private String rcpNo; 		// Report No --> GET request for http://dart.fss.or.kr/dsaf001/main.do?rcpNo=xxxxxxxxxxxxxxx
	private String crpCd;
	private String crpNm;
	private String crpCls;
	private String rptNm;
	private String flrNm;		// According to DART page, the issuer of a report can be different from the company who owns it.
	private String rcpDt;
	private String remark;

	
	/*
	 * Constructors
	 */
	public ReportHeader(String rcpNo, String crpCd, String crpNm,
			String crpCls, String rptNm, String flrNm, String rcpDt,
			String remark) {
		super();
		this.rcpNo = rcpNo;
		this.crpCd = crpCd;
		this.crpNm = crpNm;
		this.crpCls = crpCls;
		this.rptNm = rptNm;
		this.flrNm = flrNm;
		this.rcpDt = rcpDt;
		this.remark = remark;
	}
	public ReportHeader() {
		super();
	}	

	
	/*
	 * Getters and setters
	 */
	
	public String getRcpNo() {
		return rcpNo;
	}

	public ReportHeader setRcpNo(String rcpNo) {
		this.rcpNo = rcpNo;
		return this;
	}

	public String getCrpCd() {
		return crpCd;
	}

	public ReportHeader setCrpCd(String crpCd) {
		this.crpCd = crpCd;
		return this;
	}

	public String getCrpNm() {
		return crpNm;
	}

	public ReportHeader setCrpNm(String crpNm) {
		this.crpNm = crpNm;
		return this;
	}

	public String getCrpCls() {
		return crpCls;
	}

	public ReportHeader setCrpCls(String crpCls) {
		this.crpCls = crpCls;
		return this;
	}

	public String getRptNm() {
		return rptNm;
	}

	public ReportHeader setRptNm(String rptNm) {
		this.rptNm = rptNm;
		return this;
	}

	public String getFlrNm() {
		return flrNm;
	}

	public ReportHeader setFlrNm(String flrNm) {
		this.flrNm = flrNm;
		return this;
	}

	public String getRcpDt() {
		return rcpDt;
	}

	public ReportHeader setRcpDt(String rcpDt) {
		this.rcpDt = rcpDt;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public ReportHeader setRemark(String remark) {
		this.remark = remark;
		return this;
	}
	
	/*
	 * toString()
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportHeader [rcpNo=" + rcpNo + ", crpCd=" + crpCd + ", crpNm="
				+ crpNm + ", crpCls=" + crpCls + ", rptNm=" + rptNm
				+ ", flrNm=" + flrNm + ", rcpDt=" + rcpDt + ", remark="
				+ remark + "]";
	}
}
