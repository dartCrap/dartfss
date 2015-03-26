/**
 * 
 */
package com.dartcrab.reports;


import javax.persistence.*;

/**
 * @author Gi
 *
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
		return null;
		//return header;
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
