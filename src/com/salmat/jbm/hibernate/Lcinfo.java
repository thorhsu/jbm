package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Lcinfo entity. @author MyEclipse Persistence Tools
 */

public class Lcinfo implements java.io.Serializable {

	// Fields

	private String lcNo;
	private Customer customer;
	private String progNm;
	private Boolean PList;
	private String nextEffectiveDate;
	private String nextProgNm;
	private Boolean nextPList;
	private String template;
	private String nextTemplate;
	private String extraLgType1;
	private String extraLgType2;
	private String extraLgType3;
	private String extraLgType4;
	private String extraLgType5;
	private String extraLgType6;
	private Set jobBags = new HashSet(0);
	private Set jobCodes = new HashSet(0);

	// Constructors

	/** default constructor */
	public Lcinfo() {
	}

	/** minimal constructor */
	public Lcinfo(String lcNo) {
		this.lcNo = lcNo;
	}

	/** full constructor */
	public Lcinfo(String lcNo, Customer customer, String progNm, Boolean PList,
			String nextEffectiveDate, String nextProgNm, Boolean nextPList,
			String template, String nextTemplate, String extraLgType1,
			String extraLgType2, String extraLgType3, String extraLgType4,
			String extraLgType5, String extraLgType6, Set jobBags, Set jobCodes) {
		this.lcNo = lcNo;
		this.customer = customer;
		this.progNm = progNm;
		this.PList = PList;
		this.nextEffectiveDate = nextEffectiveDate;
		this.nextProgNm = nextProgNm;
		this.nextPList = nextPList;
		this.template = template;
		this.nextTemplate = nextTemplate;
		this.extraLgType1 = extraLgType1;
		this.extraLgType2 = extraLgType2;
		this.extraLgType3 = extraLgType3;
		this.extraLgType4 = extraLgType4;
		this.extraLgType5 = extraLgType5;
		this.extraLgType6 = extraLgType6;
		this.jobBags = jobBags;
		this.jobCodes = jobCodes;
	}

	// Property accessors

	public String getLcNo() {
		return this.lcNo;
	}

	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getProgNm() {
		return this.progNm;
	}

	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}

	public Boolean getPList() {
		return this.PList;
	}

	public void setPList(Boolean PList) {
		this.PList = PList;
	}

	public String getNextEffectiveDate() {
		return this.nextEffectiveDate;
	}

	public void setNextEffectiveDate(String nextEffectiveDate) {
		this.nextEffectiveDate = nextEffectiveDate;
	}

	public String getNextProgNm() {
		return this.nextProgNm;
	}

	public void setNextProgNm(String nextProgNm) {
		this.nextProgNm = nextProgNm;
	}

	public Boolean getNextPList() {
		return this.nextPList;
	}

	public void setNextPList(Boolean nextPList) {
		this.nextPList = nextPList;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getNextTemplate() {
		return this.nextTemplate;
	}

	public void setNextTemplate(String nextTemplate) {
		this.nextTemplate = nextTemplate;
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

	public Set getJobBags() {
		return this.jobBags;
	}

	public void setJobBags(Set jobBags) {
		this.jobBags = jobBags;
	}

	public Set getJobCodes() {
		return this.jobCodes;
	}

	public void setJobCodes(Set jobCodes) {
		this.jobCodes = jobCodes;
	}

}