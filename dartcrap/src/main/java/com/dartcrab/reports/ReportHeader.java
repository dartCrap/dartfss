package com.dartcrab.reports;


/**
 * 
 * @author Gi
 *
 */
public class ReportHeader {
	private String rcpNo; 		// Report No --> GET request for http://dart.fss.or.kr/dsaf001/main.do?rcpNo=xxxxxxxxxxxxxxx
	private String crpCd;
	private String crpNm;
	private String crpCls;
	private String rptNm;
	private String flrNm;		// According to DART page, the issuer of a report can be different from the company who owns it.
	private String rcpDt;
	private String remark;

	
	/**
	 * TO-DO: Constructors
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

	
	@Override
	public String toString() {
		return "ReportHeader [rcpNo=" + rcpNo + ", crpCd=" + crpCd + ", crpNm="
				+ crpNm + ", crpCls=" + crpCls + ", rptNm=" + rptNm
				+ ", flrNm=" + flrNm + ", rcpDt=" + rcpDt + ", remark="
				+ remark + "]";
	}



	/**
	 * TO-DO: Getters and setters
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
}
