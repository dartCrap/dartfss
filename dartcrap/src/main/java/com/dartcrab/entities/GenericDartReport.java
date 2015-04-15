package com.dartcrab.entities;

import com.dartcrab.reports.ReportHeader;
import javax.persistence.*;

/**
 * 
 * @author Gi Kim
 * @since Mar-25-2015
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="ReportType")
public class GenericDartReport{
	@Id
	private String			rcpNo;
	
	@OneToOne
	@JoinColumn(name="rcpNo")
	private ReportHeader	header;
	
	public String getRcpNo() {
		return rcpNo;
	}

	public void setRcpNo(String rcpNo) {
		this.rcpNo = rcpNo;
	}

	public GenericDartReport(ReportHeader header){
		this.header = header;
		this.rcpNo = header.getRcpNo();
	}

	public ReportHeader getHeader() {
		return header;
	}
	
	public void setHeader(ReportHeader header) {
		this.header = header;
		this.rcpNo = header.getRcpNo();
	}
	
	@Override
	public String toString() {
		return "GenericDartReport [header=" + header + "]";
	}

	public GenericDartReport(String rcpNo) {
		super();
		this.rcpNo=rcpNo;
	}
	
	public GenericDartReport(){}
}
