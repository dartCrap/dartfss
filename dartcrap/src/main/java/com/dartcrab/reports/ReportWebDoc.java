/**
 * 
 */
package com.dartcrab.reports;


import javax.persistence.*;

/**
 * Dart사이트에서 바로 가져오는 HTML 공시보고서를 모델링한다.
 * 하나의 rcpNo는 하나의 HTML 보고서에 대응한다.
 * 
 * @author Gi Kim
 * @since Mar-25-2015
 */


@Entity
@Table
public class ReportWebDoc {
	@Id
	private String rcpNo;

	@OneToOne
	@JoinColumn(name="rcpNo")
	private ReportHeader  	header;		
	
	@Lob
	private String 			contents;
	
	public void setHeader(ReportHeader header) {
		this.header = header;
		this.rcpNo = header.getRcpNo();
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public ReportHeader getHeader() {
		return header;
	}

	public String getContents() {
		return contents;
	}
	
	

	public ReportWebDoc() {
	super();
	}

	public ReportWebDoc(ReportHeader  header, String contents) {
		super();
		this.rcpNo	= header.getRcpNo();
		this.header = header;
		this.contents = contents;
	}
	
	public ReportWebDoc(String rcpNo, String contents) {
		super();
		this.rcpNo = rcpNo;
		// To-Do
		this.contents = contents;
		}
	
}
