/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.form;

import java.sql.Clob;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.salmat.jbm.bean.*;
import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.Customer;
import com.salmat.jbm.hibernate.Employee;



public class AcctMatchForm extends ActionForm {
	private Integer id;
	private String custNo;
	private String acctjobno;
	private String jobNo;
	private String filename;
	private String filename1;
	private String filename2;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getAcctjobno() {
		return acctjobno;
	}
	public void setAcctjobno(String acctjobno) {
		this.acctjobno = acctjobno;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilename1() {
		return filename1;
	}
	public void setFilename1(String filename1) {
		this.filename1 = filename1;
	}
	public String getFilename2() {
		return filename2;
	}
	public void setFilename2(String filename2) {
		this.filename2 = filename2;
	}
	
	
	
}