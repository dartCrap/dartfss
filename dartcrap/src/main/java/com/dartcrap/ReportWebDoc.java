/**
 * 
 */
package com.dartcrap;

import java.io.PrintWriter;

import com.dartcrap.entity.ReportHeader;

/**
 * @author Gi
 *
 */
public class ReportWebDoc {
	private String			rcpNo;				// ID
	private String 			contents;
	
	public ReportWebDoc(String rcpNo, String contents) {
		super();
		this.rcpNo = rcpNo;
		this.contents = contents;
	}
	
	public String getRcpNo() {
		return rcpNo;
	}

	public String getContents() {
		return contents;
	}
	
	
	/**
	 * TO_DO
	 */
	public ReportWebDoc storeFile(){
		try{
			PrintWriter out = new PrintWriter(rcpNo+".html");
			out.print(contents);
			out.close();
		} catch (Exception e){
			
		}
		return this;
	}
}
