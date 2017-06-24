package com.salmat.jbm.bean;

import java.util.HashSet;
import java.util.Set;

import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.Customer;

/**
 * Lpinfo entity. @author MyEclipse Persistence Tools
 */

public class PsinfoJSON implements java.io.Serializable {

	private String psNo;
	private String psType;
	private Customer customer;
	private String size;
	private String remark;
	private double psPrice;
	private String printCode;
	private Boolean zipFg;
	private String prodmemo;
	public String getPsNo() {
		return psNo;
	}
	public void setPsNo(String psNo) {
		this.psNo = psNo;
	}
	public String getPsType() {
		return psType;
	}
	public void setPsType(String psType) {
		this.psType = psType;
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
	
	
}