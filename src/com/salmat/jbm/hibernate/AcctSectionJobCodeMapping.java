package com.salmat.jbm.hibernate;

/**
 * AcctSectionJobCodeMapping entity. @author MyEclipse Persistence Tools
 */

public class AcctSectionJobCodeMapping implements java.io.Serializable {

	// Fields

	private Integer id;
	private JobCode jobCode;
	private Customer customer;
	private String section;

	// Constructors

	/** default constructor */
	public AcctSectionJobCodeMapping() {
	}

	/** full constructor */
	public AcctSectionJobCodeMapping(JobCode jobCode, Customer customer,
			String section) {
		this.jobCode = jobCode;
		this.customer = customer;
		this.section = section;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

}