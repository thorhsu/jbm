package com.salmat.jbm.hibernate;

/**
 * AcctSum3 entity. @author MyEclipse Persistence Tools
 */

public class AcctSum3 implements java.io.Serializable {

	// Fields

	private Integer id;
	private AcctDno acctDno;
	private Customer customer;
	private String title;
	private String subtitle;
	private Double sumQty;
	private Double unitPrice;
	private Double total;
	private String costCenter;
	private String purpose;
	private String ep1acccd;
	private String displayTitle;
	private Integer idfAcctChargeItem;
	private String printCode;

	// Constructors
	/** default constructor */
	public AcctSum3() {
	}

	/** minimal constructor */
	public AcctSum3(AcctDno acctDno) {
		this.acctDno = acctDno;
	}

	/** full constructor */
	public AcctSum3(AcctDno acctDno, Customer customer, String title,
			String subtitle, Double sumQty, Double unitPrice, Double total,
			String costCenter, String purpose, String ep1acccd, String displayTitle, Integer idfAcctChargeItem) {
		this.acctDno = acctDno;
		this.customer = customer;
		this.title = title;
		this.subtitle = subtitle;
		this.sumQty = sumQty;
		this.unitPrice = unitPrice;
		this.total = total;
		this.costCenter = costCenter;
		this.purpose = purpose;
		this.ep1acccd = ep1acccd;
		this.displayTitle = displayTitle;
		this.idfAcctChargeItem = idfAcctChargeItem;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public Double getSumQty() {
		return this.sumQty;
	}

	public void setSumQty(Double sumQty) {
		this.sumQty = sumQty;
	}

	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getEp1acccd() {
		return this.ep1acccd;
	}

	public void setEp1acccd(String ep1acccd) {
		this.ep1acccd = ep1acccd;
	}
	public String getDisplayTitle() {
		return displayTitle;
	}

	public void setDisplayTitle(String displayTitle) {
		this.displayTitle = displayTitle;
	}


	public Integer getIdfAcctChargeItem() {
		return idfAcctChargeItem;
	}

	public void setIdfAcctChargeItem(Integer idfAcctChargeItem) {
		this.idfAcctChargeItem = idfAcctChargeItem;
	}
	public String getPrintCode() {
		return printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}

}