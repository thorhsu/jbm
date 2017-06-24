package com.salmat.jbm.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AcctCustomerContract entity. @author MyEclipse Persistence Tools
 */

public class AcctCustomerContract implements java.io.Serializable {

	// Fields

	private Integer id;
	private AcctChargeItem acctChargeItem;
	private Customer customer;
	private Date contactDateBegin;
	private Date contactDateEnd;
	private Double unitPrice;
	private String reportTitle;
	private Integer orderby;
	private Set acctSum1s = new HashSet(0);
	private Set acctJobCodeChargeItems = new HashSet(0);
	private Set acctSum2s = new HashSet(0);
	private Boolean autoExtend;

	// Constructors


	/** default constructor */
	public AcctCustomerContract() {
	}

	/** full constructor */
	public AcctCustomerContract(AcctChargeItem acctChargeItem,
			Customer customer, Date contactDateBegin, Date contactDateEnd,
			Double unitPrice, String reportTitle, Integer orderby,
			Set acctSum1s, Set acctJobCodeChargeItems, Set acctSum2s, Boolean autoExtend) {
		this.acctChargeItem = acctChargeItem;
		this.customer = customer;
		this.contactDateBegin = contactDateBegin;
		this.contactDateEnd = contactDateEnd;
		this.unitPrice = unitPrice;
		this.reportTitle = reportTitle;
		this.orderby = orderby;
		this.acctSum1s = acctSum1s;
		this.acctJobCodeChargeItems = acctJobCodeChargeItems;
		this.acctSum2s = acctSum2s;
		this.autoExtend = autoExtend;
		
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AcctChargeItem getAcctChargeItem() {
		return this.acctChargeItem;
	}

	public void setAcctChargeItem(AcctChargeItem acctChargeItem) {
		this.acctChargeItem = acctChargeItem;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getContactDateBegin() {
		return this.contactDateBegin;
	}

	public void setContactDateBegin(Date contactDateBegin) {
		this.contactDateBegin = contactDateBegin;
	}

	public Date getContactDateEnd() {
		return this.contactDateEnd;
	}

	public void setContactDateEnd(Date contactDateEnd) {
		this.contactDateEnd = contactDateEnd;
	}

	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getReportTitle() {
		return this.reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public Integer getOrderby() {
		return this.orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public Set getAcctSum1s() {
		return this.acctSum1s;
	}

	public void setAcctSum1s(Set acctSum1s) {
		this.acctSum1s = acctSum1s;
	}

	public Set getAcctJobCodeChargeItems() {
		return this.acctJobCodeChargeItems;
	}

	public void setAcctJobCodeChargeItems(Set acctJobCodeChargeItems) {
		this.acctJobCodeChargeItems = acctJobCodeChargeItems;
	}

	public Set getAcctSum2s() {
		return this.acctSum2s;
	}

	public void setAcctSum2s(Set acctSum2s) {
		this.acctSum2s = acctSum2s;
	}
	
	public Boolean getAutoExtend() {
		return autoExtend;
	}

	public int getAutoExtendForm() {
		if(autoExtend == null)
			return 1;
	    else if(autoExtend)
	    	return 1;
	    else
	    	return 0;
	}
	public void setAutoExtend(Boolean autoExtend) {
		this.autoExtend = autoExtend;
	}


}