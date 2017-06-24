package com.salmat.jbm.hibernate;

import java.util.Date;

/**
 * AcctSum1 entity. @author MyEclipse Persistence Tools
 */

public class Operation implements java.io.Serializable {

	// Fields

	private Integer id;
	private String contents;
	private String note;
	private CustomerContract customerContract;
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getContents() {
		return contents;
	}


	public void setContents(String contents) {
		this.contents = contents;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public CustomerContract getCustomerContract() {
		return customerContract;
	}


	public void setCustomerContract(CustomerContract customerContract) {
		this.customerContract = customerContract;
	}


	// Constructors
	/** default constructor */
	public Operation() {
	}


		
}