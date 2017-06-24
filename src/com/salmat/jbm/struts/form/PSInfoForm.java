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



public class PSInfoForm extends ActionForm {
	private String psNo;
	private Code codeByNextCodePsType;
	private Code codeByCodePsType;
	private Customer customer;
	private String size;
	private String psType;
	private String remark;
	private double psPrice;
	private String printCode;
	private Boolean zipFg;
	private String prodmemo;
	private String nextEffectiveDate;
	private String nextProdmemo;
	private String nextRemark;
	private Boolean nextZipFg;
	
	
	private String custNo;
	private Integer codePsType;
	private Integer nextCodePsType;


	public String getPsNo() {
		return psNo;
	}


	public void setPsNo(String psNo) {
		this.psNo = psNo;
	}


	public Code getCodeByNextCodePsType() {
		return codeByNextCodePsType;
	}


	public void setCodeByNextCodePsType(Code codeByNextCodePsType) {
		this.codeByNextCodePsType = codeByNextCodePsType;
	}


	public Code getCodeByCodePsType() {
		return codeByCodePsType;
	}


	public void setCodeByCodePsType(Code codeByCodePsType) {
		this.codeByCodePsType = codeByCodePsType;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getPsType() {
		return psType;
	}


	public void setPsType(String psType) {
		this.psType = psType;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public double getPsPrice() {
		return psPrice;
	}


	public void setPsPrice(double psPrice) {
		this.psPrice = psPrice;
	}


	public String getPrintCode() {
		return printCode;
	}


	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}


	public Boolean getZipFg() {
		return zipFg;
	}


	public void setZipFg(Boolean zipFg) {
		this.zipFg = zipFg;
	}


	public String getProdmemo() {
		return prodmemo;
	}


	public void setProdmemo(String prodmemo) {
		this.prodmemo = prodmemo;
	}


	public String getNextEffectiveDate() {
		return nextEffectiveDate;
	}


	public void setNextEffectiveDate(String nextEffectiveDate) {
		this.nextEffectiveDate = nextEffectiveDate;
	}


	public String getNextProdmemo() {
		return nextProdmemo;
	}


	public void setNextProdmemo(String nextProdmemo) {
		this.nextProdmemo = nextProdmemo;
	}


	public String getNextRemark() {
		return nextRemark;
	}


	public void setNextRemark(String nextRemark) {
		this.nextRemark = nextRemark;
	}


	public Boolean getNextZipFg() {
		return nextZipFg;
	}


	public void setNextZipFg(Boolean nextZipFg) {
		this.nextZipFg = nextZipFg;
	}


	public String getCustNo() {
		return custNo;
	}


	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}


	public Integer getCodePsType() {
		return codePsType;
	}


	public void setCodePsType(Integer codePsType) {
		this.codePsType = codePsType;
	}


	public Integer getNextCodePsType() {
		return nextCodePsType;
	}


	public void setNextCodePsType(Integer nextCodePsType) {
		this.nextCodePsType = nextCodePsType;
	}









	
}