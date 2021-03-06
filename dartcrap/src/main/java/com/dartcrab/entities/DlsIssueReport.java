package com.dartcrab.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.dartcrab.reports.ReportHeader;
import javax.persistence.*;

@Entity
@Table
@DiscriminatorValue("DLS")
public class DlsIssueReport extends GenericDartReport{

	@OneToMany(mappedBy="dlsReport", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Dls>	dlsIssueList;
	
	private Date		registrationDate; // 일괄신고제출일
	private Date		registrationEffectiveDate; // 일괄신고효력발생일
	private int			registrationTotalAmt;		// 발행예정금액
	private int			annexIssueAmt; // 이번모집또는매출총액
	private int			totalIssueAmt; // 발행예정기간 중 모집, 매출 총액
	private int			totalOutstandingAmt; // 실제발행액


	public DlsIssueReport(ReportHeader header) {
		super(header);
		dlsIssueList = new ArrayList<Dls>();
		setTotalIssueAmt(0);
	}

	public DlsIssueReport addDlsInfo (Dls dls){
		if (dlsIssueList == null ) dlsIssueList = new ArrayList<Dls>();
 		dlsIssueList.add(dls);
		return this;
	}

	public DlsIssueReport(String rcpNo) {
		super(rcpNo);
	}
	public DlsIssueReport(){super();}

	public List<Dls> getDlsIssueList() {
		return dlsIssueList;
	}

	public void setDlsIssueList(List<Dls> dlsIssueList) {
		this.dlsIssueList = dlsIssueList;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getRegistrationEffectiveDate() {
		return registrationEffectiveDate;
	}

	public void setRegistrationEffectiveDate(Date registrationEffectiveDate) {
		this.registrationEffectiveDate = registrationEffectiveDate;
	}

	public int getRegistrationTotalAmt() {
		return registrationTotalAmt;
	}

	public void setRegistrationTotalAmt(int registrationTotalAmt) {
		this.registrationTotalAmt = registrationTotalAmt;
	}

	public int getAnnexIssueAmt() {
		return annexIssueAmt;
	}

	public void setAnnexIssueAmt(int annexIssueAmt) {
		this.annexIssueAmt = annexIssueAmt;
	}

	public int getTotalOutstandingAmt() {
		return totalOutstandingAmt;
	}

	public void setTotalOutstandingAmt(int totalOutstandingAmt) {
		this.totalOutstandingAmt = totalOutstandingAmt;
	}

	public void setTotalIssueAmt(int totalIssueAmt) {
		this.totalIssueAmt = totalIssueAmt;
	}

	public int getTotalIssueAmt() {
		return totalIssueAmt;
	}

	@Override
	public String toString() {
		return "DlsIssueReport [dlsIssueList=" + dlsIssueList
				+ ", registrationDate=" + registrationDate
				+ ", registrationEffectiveDate=" + registrationEffectiveDate
				+ ", registrationTotalAmt=" + registrationTotalAmt
				+ ", annexIssueAmt=" + annexIssueAmt + ", totalIssueAmt="
				+ totalIssueAmt + ", totalOutstandingAmt="
				+ totalOutstandingAmt + "]";
	}


}
