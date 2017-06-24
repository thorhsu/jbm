package com.salmat.jbm.hibernate;

/**
 * Amex entity. @author MyEclipse Persistence Tools
 */

public class Amex implements java.io.Serializable {

	// Fields

	private Integer id;
	private String country;
	private String afpName;
	private String jobCodeNo;
	private String typefile;
	private String speaker;
	private String special;
	private String macreport;
	private String aebType;
	private Integer pages;

	// Constructors

	/** default constructor */
	public Amex() {
	}

	/** full constructor */
	public Amex(String country, String afpName, String jobCodeNo,
			String typefile, String speaker, String special, String macreport,
			String aebType, Integer pages) {
		this.country = country;
		this.afpName = afpName;
		this.jobCodeNo = jobCodeNo;
		this.typefile = typefile;
		this.speaker = speaker;
		this.special = special;
		this.macreport = macreport;
		this.aebType = aebType;
		this.pages = pages;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAfpName() {
		return this.afpName;
	}

	public void setAfpName(String afpName) {
		this.afpName = afpName;
	}

	public String getJobCodeNo() {
		return this.jobCodeNo;
	}

	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}

	public String getTypefile() {
		return this.typefile;
	}

	public void setTypefile(String typefile) {
		this.typefile = typefile;
	}

	public String getSpeaker() {
		return this.speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getSpecial() {
		return this.special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getMacreport() {
		return this.macreport;
	}

	public void setMacreport(String macreport) {
		this.macreport = macreport;
	}

	public String getAebType() {
		return this.aebType;
	}

	public void setAebType(String aebType) {
		this.aebType = aebType;
	}

	public Integer getPages() {
		return this.pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

}