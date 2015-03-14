/**
 * 
 */
package com.dartcrap.reports;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gi
 *
 */
public class ReportWebDoc {
	private static Logger log = LoggerFactory.getLogger( ReportWebDoc.class );
	
	private ReportHeader  	header;				
	private String 			contents;
	
	public ReportWebDoc(ReportHeader  header, String contents) {
		super();
		this.header = header;
		this.contents = contents;
	}
	
	public ReportHeader getHeader() {
		return header;
	}

	public String getContents() {
		return contents;
	}
	
	
	/**
	 * TO_DO
	 */
	public ReportWebDoc storeFile(){
		try{
			PrintWriter out = new PrintWriter("target/data/"+header.getRcpNo()+".html"); // To do: Parameterize the default file location 
			out.print(contents);
			out.close();
			log.info("Report HTML stored: "+"target/data/"+header.getRcpNo()+".html");
		} catch (Exception e){
			e.printStackTrace();
		}
		return this;
	}
}
