package com.salmat.jbm.hibernate;

/**
 * Lcform entity. @author MyEclipse Persistence Tools
 */

public class Lcform implements java.io.Serializable {

	// Fields

	private Integer id;
	private String querySession;
	private String postOfficeName;
	private String postOfficeTel;
	private String postOfficeFax;
	private String postOfficeContact;
	private String salmatTel;
	private String salmatFax;
	private String salmatContact;
	private String custNo;
	private String custName;
	private String lgType;
	private String mailCategory;
	private String jobName;
	private String cycleDateList;
	private Integer qty;
	private Integer orderby;
	private String jobCodeNo;
	private String extraLgType1;
	private String extraLgType2;
	private String extraLgType3;
	private String extraLgType4;
	private String extraLgType5;
	private String extraLgType6;
	private String customerDept;
	private String mailToArea;
	private Double price;
	private String registerNos;

	// Constructors

	/** default constructor */
	public Lcform() {
	}

	/** full constructor */
	public Lcform(String querySession, String postOfficeName,
			String postOfficeTel, String postOfficeFax,
			String postOfficeContact, String salmatTel, String salmatFax,
			String salmatContact, String custNo, String custName,
			String lgType, String mailCategory, String jobName,
			String cycleDateList, Integer qty, Integer orderby,
			String jobCodeNo, String extraLgType1, String extraLgType2,
			String extraLgType3, String extraLgType4, String extraLgType5,
			String extraLgType6, String customerDept, String mailToArea,
			double price) {
		this.querySession = querySession;
		this.postOfficeName = postOfficeName;
		this.postOfficeTel = postOfficeTel;
		this.postOfficeFax = postOfficeFax;
		this.postOfficeContact = postOfficeContact;
		this.salmatTel = salmatTel;
		this.salmatFax = salmatFax;
		this.salmatContact = salmatContact;
		this.custNo = custNo;
		this.custName = custName;
		this.lgType = lgType;
		this.mailCategory = mailCategory;
		this.jobName = jobName;
		this.cycleDateList = cycleDateList;
		this.qty = qty;
		this.orderby = orderby;
		this.jobCodeNo = jobCodeNo;
		this.extraLgType1 = extraLgType1;
		this.extraLgType2 = extraLgType2;
		this.extraLgType3 = extraLgType3;
		this.extraLgType4 = extraLgType4;
		this.extraLgType5 = extraLgType5;
		this.extraLgType6 = extraLgType6;
		this.customerDept = customerDept;
		this.mailToArea = mailToArea;
		this.price = price;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuerySession() {
		return this.querySession;
	}

	public void setQuerySession(String querySession) {
		this.querySession = querySession;
	}

	public String getPostOfficeName() {
		return this.postOfficeName;
	}

	public void setPostOfficeName(String postOfficeName) {
		this.postOfficeName = postOfficeName;
	}

	public String getPostOfficeTel() {
		return this.postOfficeTel;
	}

	public void setPostOfficeTel(String postOfficeTel) {
		this.postOfficeTel = postOfficeTel;
	}

	public String getPostOfficeFax() {
		return this.postOfficeFax;
	}

	public void setPostOfficeFax(String postOfficeFax) {
		this.postOfficeFax = postOfficeFax;
	}

	public String getPostOfficeContact() {
		return this.postOfficeContact;
	}

	public void setPostOfficeContact(String postOfficeContact) {
		this.postOfficeContact = postOfficeContact;
	}

	public String getSalmatTel() {
		return this.salmatTel;
	}

	public void setSalmatTel(String salmatTel) {
		this.salmatTel = salmatTel;
	}

	public String getSalmatFax() {
		return this.salmatFax;
	}

	public void setSalmatFax(String salmatFax) {
		this.salmatFax = salmatFax;
	}

	public String getSalmatContact() {
		return this.salmatContact;
	}

	public void setSalmatContact(String salmatContact) {
		this.salmatContact = salmatContact;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getLgType() {
		return this.lgType;
	}

	public void setLgType(String lgType) {
		this.lgType = lgType;
	}

	public String getMailCategory() {
		return this.mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCycleDateList() {
		return this.cycleDateList;
	}

	public void setCycleDateList(String cycleDateList) {
		this.cycleDateList = cycleDateList;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getOrderby() {
		return this.orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public String getJobCodeNo() {
		return this.jobCodeNo;
	}

	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}

	public String getExtraLgType1() {
		return this.extraLgType1;
	}

	public void setExtraLgType1(String extraLgType1) {
		this.extraLgType1 = extraLgType1;
	}

	public String getExtraLgType2() {
		return this.extraLgType2;
	}

	public void setExtraLgType2(String extraLgType2) {
		this.extraLgType2 = extraLgType2;
	}

	public String getExtraLgType3() {
		return this.extraLgType3;
	}

	public void setExtraLgType3(String extraLgType3) {
		this.extraLgType3 = extraLgType3;
	}

	public String getExtraLgType4() {
		return this.extraLgType4;
	}

	public void setExtraLgType4(String extraLgType4) {
		this.extraLgType4 = extraLgType4;
	}

	public String getExtraLgType5() {
		return this.extraLgType5;
	}

	public void setExtraLgType5(String extraLgType5) {
		this.extraLgType5 = extraLgType5;
	}

	public String getExtraLgType6() {
		return this.extraLgType6;
	}

	public void setExtraLgType6(String extraLgType6) {
		this.extraLgType6 = extraLgType6;
	}

	public String getCustomerDept() {
		return this.customerDept;
	}

	public void setCustomerDept(String customerDept) {
		this.customerDept = customerDept;
	}

	public String getMailToArea() {
		return this.mailToArea;
	}

	public void setMailToArea(String mailToArea) {
		this.mailToArea = mailToArea;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRegisterNos() {
		return registerNos;
	}

	public void setRegisterNos(String registerNos) {
		this.registerNos = registerNos;
	}

}