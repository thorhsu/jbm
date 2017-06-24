package com.salmat.jbm.hibernate;

import java.util.Date;

/**
 * Lgform entity. @author MyEclipse Persistence Tools
 */

public class Lgform implements java.io.Serializable {

	// Fields

	private Integer id;
	private String idfCustNo;
	private String custName;
	private Integer codeMailToArea;
	private String mailToArea;
	private Integer codeLgType;
	private String lgType;
	private Integer codeMailCategory;
	private String mailCategory;
	private Integer qty;
	private Integer weight;
	private double price;
	private String jobCodeNo;
	private String jobBagNo;
	private String progNm;
	private Integer statementNo;
	private Date dispatchDate;
	private String dispatcher;
	private String pathCustStampImage;
	private String lgTitle;
	private String lgNo;

	// Constructors

	/** default constructor */
	public Lgform() {
	}

	/** full constructor */
	public Lgform(String idfCustNo, String custName, Integer codeMailToArea,
			String mailToArea, Integer codeLgType, String lgType,
			Integer codeMailCategory, String mailCategory, Integer qty,
			Integer weight, double price, String jobCodeNo, String jobBagNo,
			String progNm, Integer statementNo, Date dispatchDate,
			String dispatcher, String pathCustStampImage, String lgTitle) {
		this.idfCustNo = idfCustNo;
		this.custName = custName;
		this.codeMailToArea = codeMailToArea;
		this.mailToArea = mailToArea;
		this.codeLgType = codeLgType;
		this.lgType = lgType;
		this.codeMailCategory = codeMailCategory;
		this.mailCategory = mailCategory;
		this.qty = qty;
		this.weight = weight;
		this.price = price;
		this.jobCodeNo = jobCodeNo;
		this.jobBagNo = jobBagNo;
		this.progNm = progNm;
		this.statementNo = statementNo;
		this.dispatchDate = dispatchDate;
		this.dispatcher = dispatcher;
		this.pathCustStampImage = pathCustStampImage;
		this.lgTitle = lgTitle;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdfCustNo() {
		return this.idfCustNo;
	}

	public void setIdfCustNo(String idfCustNo) {
		this.idfCustNo = idfCustNo;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Integer getCodeMailToArea() {
		return this.codeMailToArea;
	}

	public void setCodeMailToArea(Integer codeMailToArea) {
		this.codeMailToArea = codeMailToArea;
	}

	public String getMailToArea() {
		return this.mailToArea;
	}

	public void setMailToArea(String mailToArea) {
		this.mailToArea = mailToArea;
	}

	public Integer getCodeLgType() {
		return this.codeLgType;
	}

	public void setCodeLgType(Integer codeLgType) {
		this.codeLgType = codeLgType;
	}

	public String getLgType() {
		return this.lgType;
	}

	public void setLgType(String lgType) {
		this.lgType = lgType;
	}

	public Integer getCodeMailCategory() {
		return this.codeMailCategory;
	}

	public void setCodeMailCategory(Integer codeMailCategory) {
		this.codeMailCategory = codeMailCategory;
	}

	public String getMailCategory() {
		return this.mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getJobCodeNo() {
		return this.jobCodeNo;
	}

	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}

	public String getJobBagNo() {
		return this.jobBagNo;
	}

	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}

	public String getProgNm() {
		return this.progNm;
	}

	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}

	public Integer getStatementNo() {
		return this.statementNo;
	}

	public void setStatementNo(Integer statementNo) {
		this.statementNo = statementNo;
	}

	public Date getDispatchDate() {
		return this.dispatchDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public String getDispatcher() {
		return this.dispatcher;
	}

	public void setDispatcher(String dispatcher) {
		this.dispatcher = dispatcher;
	}

	public String getPathCustStampImage() {
		return this.pathCustStampImage;
	}

	public void setPathCustStampImage(String pathCustStampImage) {
		this.pathCustStampImage = pathCustStampImage;
	}

	public String getLgTitle() {
		return this.lgTitle;
	}

	public void setLgTitle(String lgTitle) {
		this.lgTitle = lgTitle;
	}

	public String getLgNo() {
		return lgNo;
	}

	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}
	

}