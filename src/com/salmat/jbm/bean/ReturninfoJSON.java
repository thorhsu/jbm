package com.salmat.jbm.bean;

import java.util.HashSet;
import java.util.Set;

import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.Customer;

/**
 * Lpinfo entity. @author MyEclipse Persistence Tools
 */

public class ReturninfoJSON implements java.io.Serializable {
	private String returnNo;
	private Customer customer;
	private String userName;
	private String userCompany;
	private String returnAddress;
	private String userTel;
	public String getReturnNo() {
		return returnNo;
	}
	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserCompany() {
		return userCompany;
	}
	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}
	public String getReturnAddress() {
		return returnAddress;
	}
	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	
	
}