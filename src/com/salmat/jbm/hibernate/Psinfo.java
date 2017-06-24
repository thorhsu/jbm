package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Psinfo entity. @author MyEclipse Persistence Tools
 */

public class Psinfo implements java.io.Serializable {

	// Fields

	private String psNo;
	private Code codeByNextCodePsType;
	private Code codeByCodePsType;
	private Customer customer;
	private String size;
	private String psType;
	private String remark;
	private double psPrice;
	private String printCode;
	private Boolean zipFg;
	private String prodmemo;
	private String nextEffectiveDate;
	private String nextProdmemo;
	private String nextRemark;
	private Boolean nextZipFg;
	private Set jobCodes = new HashSet(0);
	private Set jobBags = new HashSet(0);

	// Constructors

	/** default constructor */
	public Psinfo() {
	}

	/** minimal constructor */
	public Psinfo(String psNo) {
		this.psNo = psNo;
	}

	/** full constructor */
	public Psinfo(String psNo, Code codeByNextCodePsType,
			Code codeByCodePsType, Customer customer, String size,
			String psType, String remark, double psPrice, String printCode,
			Boolean zipFg, String prodmemo, String nextEffectiveDate,
			String nextProdmemo, String nextRemark, Boolean nextZipFg,
			Set jobCodes, Set jobBags) {
		this.psNo = psNo;
		this.codeByNextCodePsType = codeByNextCodePsType;
		this.codeByCodePsType = codeByCodePsType;
		this.customer = customer;
		this.size = size;
		this.psType = psType;
		this.remark = remark;
		this.psPrice = psPrice;
		this.printCode = printCode;
		this.zipFg = zipFg;
		this.prodmemo = prodmemo;
		this.nextEffectiveDate = nextEffectiveDate;
		this.nextProdmemo = nextProdmemo;
		this.nextRemark = nextRemark;
		this.nextZipFg = nextZipFg;
		this.jobCodes = jobCodes;
		this.jobBags = jobBags;
	}

	// Property accessors

	public String getPsNo() {
		return this.psNo;
	}

	public void setPsNo(String psNo) {
		this.psNo = psNo;
	}

	public Code getCodeByNextCodePsType() {
		return this.codeByNextCodePsType;
	}

	public void setCodeByNextCodePsType(Code codeByNextCodePsType) {
		this.codeByNextCodePsType = codeByNextCodePsType;
	}

	public Code getCodeByCodePsType() {
		return this.codeByCodePsType;
	}

	public void setCodeByCodePsType(Code codeByCodePsType) {
		this.codeByCodePsType = codeByCodePsType;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPsType() {
		return this.psType;
	}

	public void setPsType(String psType) {
		this.psType = psType;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getPsPrice() {
		return this.psPrice;
	}

	public void setPsPrice(double psPrice) {
		this.psPrice = psPrice;
	}

	public String getPrintCode() {
		return this.printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}

	public Boolean getZipFg() {
		return this.zipFg;
	}

	public void setZipFg(Boolean zipFg) {
		this.zipFg = zipFg;
	}

	public String getProdmemo() {
		return this.prodmemo;
	}

	public void setProdmemo(String prodmemo) {
		this.prodmemo = prodmemo;
	}

	public String getNextEffectiveDate() {
		return this.nextEffectiveDate;
	}

	public void setNextEffectiveDate(String nextEffectiveDate) {
		this.nextEffectiveDate = nextEffectiveDate;
	}

	public String getNextProdmemo() {
		return this.nextProdmemo;
	}

	public void setNextProdmemo(String nextProdmemo) {
		this.nextProdmemo = nextProdmemo;
	}

	public String getNextRemark() {
		return this.nextRemark;
	}

	public void setNextRemark(String nextRemark) {
		this.nextRemark = nextRemark;
	}

	public Boolean getNextZipFg() {
		return this.nextZipFg;
	}

	public void setNextZipFg(Boolean nextZipFg) {
		this.nextZipFg = nextZipFg;
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