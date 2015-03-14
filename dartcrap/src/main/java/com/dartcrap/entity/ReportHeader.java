package com.dartcrap.entity;


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




	public void setRcpNo(String rcpNo) {
		this.rcpNo = rcpNo;
	}




	public String getCrpCd() {
		return crpCd;
	}




	public void setCrpCd(String crpCd) {
		this.crpCd = crpCd;
	}




	public String getCrpNm() {
		return crpNm;
	}




	public void setCrpNm(String crpNm) {
		this.crpNm = crpNm;
	}




	public String getCrpCls() {
		return crpCls;
	}




	public void setCrpCls(String crpCls) {
		this.crpCls = crpCls;
	}




	public String getRptNm() {
		return rptNm;
	}




	public void setRptNm(String rptNm) {
		this.rptNm = rptNm;
	}




	public String getFlrNm() {
		return flrNm;
	}




	public void setFlrNm(String flrNm) {
		this.flrNm = flrNm;
	}




	public String getRcpDt() {
		return rcpDt;
	}




	public void setRcpDt(String rcpDt) {
		this.rcpDt = rcpDt;
	}




	public String getRemark() {
		return remark;
	}




	public void setRemark(String remark) {
		this.remark = remark;
	}
}
