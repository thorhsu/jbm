package com.salmat.jbm.hibernate;

/**
 * AcctInvoice entity. @author MyEclipse Persistence Tools
 */

public class AcctInvoice implements java.io.Serializable {

	// Fields

	private Integer id;
	private AcctDno acctDno;
	private Customer customer;
	private String costCenter;
	private Double expense;
	private String invoiceno;
	private String dept;
	private Double tax;
	private String jobtype;
	private String jobCodeNo;
	private String invoicetyp;

	// Constructors

	/** default constructor */
	public AcctInvoice() {
	}

	/** minimal constructor */
	public AcctInvoice(AcctDno acctDno, Customer customer) {
		this.acctDno = acctDno;
		this.customer = customer;
	}

	/** full constructor */
	public AcctInvoice(AcctDno acctDno, Customer customer, String costCenter,
			Double expense, String invoiceno, String dept, Double tax,
			String jobtype, String jobCodeNo, String invoicetyp) {
		this.acctDno = acctDno;
		this.customer = customer;
		this.costCenter = costCenter;
		this.expense = expense;
		this.invoiceno = invoiceno;
		this.dept = dept;
		this.tax = tax;
		this.jobtype = jobtype;
		this.jobCodeNo = jobCodeNo;
		this.invoicetyp = invoicetyp;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AcctDno getAcctDno() {
		return this.acctDno;
	}

	public void setAcctDno(AcctDno acctDno) {
		this.acctDno = acctDno;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public Double getExpense() {
		return this.expense;
	}

	public void setExpense(Double expense) {
		this.expense = expense;
	}

	public String getInvoiceno() {
		return this.invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getJobtype() {
		return this.jobtype;
	}

	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}

	public String getJobCodeNo() {
		return this.jobCodeNo;
	}

	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}

	public String getInvoicetyp() {
		return this.invoicetyp;
	}

	public void setInvoicetyp(String invoicetyp) {
		this.invoicetyp = invoicetyp;
	}

}