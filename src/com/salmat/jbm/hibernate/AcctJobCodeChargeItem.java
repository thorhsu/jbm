package com.salmat.jbm.hibernate;

/**
 * AcctJobCodeChargeItem entity. @author MyEclipse Persistence Tools
 */

public class AcctJobCodeChargeItem implements java.io.Serializable {

	// Fields

	private Integer id;
	private AcctCustomerContract acctCustomerContract;
	private AcctChargeItem acctChargeItem;
	private JobCode jobCode;
	private Customer customer;
	private Double adjustmentPercent;
	private Integer printTimes;

	// Constructors

	/** default constructor */
	public AcctJobCodeChargeItem() {
	}

	/** full constructor */
	public AcctJobCodeChargeItem(AcctCustomerContract acctCustomerContract,
			AcctChargeItem acctChargeItem, JobCode jobCode, Customer customer,
			Double adjustmentPercent, Integer printTimes) {
		this.acctCustomerContract = acctCustomerContract;
		this.acctChargeItem = acctChargeItem;
		this.jobCode = jobCode;
		this.customer = customer;
		this.adjustmentPercent = adjustmentPercent;
		this.printTimes = printTimes;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AcctCustomerContract getAcctCustomerContract() {
		return this.acctCustomerContract;
	}

	public void setAcctCustomerContract(
			AcctCustomerContract acctCustomerContract) {
		this.acctCustomerContract = acctCustomerContract;
	}

	public AcctChargeItem getAcctChargeItem() {
		return this.acctChargeItem;
	}

	public void setAcctChargeItem(AcctChargeItem acctChargeItem) {
		this.acctChargeItem = acctChargeItem;
	}

	public JobCode getJobCode() {
		return this.jobCode;
	}

	public void setJobCode(JobCode jobCode) {
		this.jobCode = jobCode;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getAdjustmentPercent() {
		return this.adjustmentPercent;
	}

	public void setAdjustmentPercent(Double adjustmentPercent) {
		this.adjustmentPercent = adjustmentPercent;
	}

	public Integer getPrintTimes() {
		return this.printTimes;
	}

	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

}