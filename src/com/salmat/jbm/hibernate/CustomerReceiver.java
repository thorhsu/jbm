package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * CustomerReceiver entity. @author MyEclipse Persistence Tools
 */

public class CustomerReceiver implements java.io.Serializable {

	// Fields

	private String receiverNo;
	private Customer customer;
	private String receiver;
	private String invoiceTitle;
	private String pin;
	private String address;
	private String tel;
	private String email;
	private Set acctSectionReceiverMappings = new HashSet(0);

	// Constructors

	/** default constructor */
	public CustomerReceiver() {
	}

	/** minimal constructor */
	public CustomerReceiver(String receiverNo) {
		this.receiverNo = receiverNo;
	}

	/** full constructor */
	public CustomerReceiver(String receiverNo, Customer customer,
			String receiver, String invoiceTitle, String pin, String address,
			String tel, Set acctSectionReceiverMappings, String email) {
		this.receiverNo = receiverNo;
		this.customer = customer;
		this.receiver = receiver;
		this.invoiceTitle = invoiceTitle;
		this.pin = pin;
		this.address = address;
		this.tel = tel;
		this.acctSectionReceiverMappings = acctSectionReceiverMappings;
		this.email = email;
	}

	// Property accessors

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReceiverNo() {
		return this.receiverNo;
	}

	public void setReceiverNo(String receiverNo) {
		this.receiverNo = receiverNo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getPin() {
		return this.pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Set getAcctSectionReceiverMappings() {
		return this.acctSectionReceiverMappings;
	}

	public void setAcctSectionReceiverMappings(Set acctSectionReceiverMappings) {
		this.acctSectionReceiverMappings = acctSectionReceiverMappings;
	}

}