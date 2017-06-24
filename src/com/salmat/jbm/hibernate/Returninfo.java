package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Returninfo entity. @author MyEclipse Persistence Tools
 */

public class Returninfo implements java.io.Serializable {

	// Fields

	private String returnNo;
	private Customer customer;
	private String userName;
	private String userCompany;
	private String returnAddress;
	private String userTel;
	private Set jobCodes = new HashSet(0);
	private Set jobBags = new HashSet(0);

	// Constructors

	/** default constructor */
	public Returninfo() {
	}

	/** minimal constructor */
	public Returninfo(String returnNo, Customer customer, String userName) {
		this.returnNo = returnNo;
		this.customer = customer;
		this.userName = userName;
	}

	/** full constructor */
	public Returninfo(String returnNo, Customer customer, String userName,
			String userCompany, String returnAddress, String userTel,
			Set jobCodes, Set jobBags) {
		this.returnNo = returnNo;
		this.customer = customer;
		this.userName = userName;
		this.userCompany = userCompany;
		this.returnAddress = returnAddress;
		this.userTel = userTel;
		this.jobCodes = jobCodes;
		this.jobBags = jobBags;
	}

	// Property accessors

	public String getReturnNo() {
		return this.returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserCompany() {
		return this.userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	public String getReturnAddress() {
		return this.returnAddress;
	}

	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}

	public String getUserTel() {
		return this.userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public Set getJobCodes() {
		return this.jobCodes;
	}

	public void setJobCodes(Set jobCodes) {
		this.jobCodes = jobCodes;
	}

	public Set getJobBags() {
		return this.jobBags;
	}

	public void setJobBags(Set jobBags) {
		this.jobBags = jobBags;
	}

}