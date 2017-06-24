package com.salmat.jbm.bean;

import java.util.HashSet;
import java.util.Set;

import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.Customer;

/**
 * Lpinfo entity. @author MyEclipse Persistence Tools
 */

public class LcinfoJSON implements java.io.Serializable {
	private String lcNo;
	private Customer customer;
	private String progNm;
	private Boolean PList;
	private String template;
	public String getLcNo() {
		return lcNo;
	}
	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getProgNm() {
		return progNm;
	}
	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}
	public Boolean getPList() {
		return PList;
	}
	public void setPList(Boolean pList) {
		PList = pList;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}

	
}