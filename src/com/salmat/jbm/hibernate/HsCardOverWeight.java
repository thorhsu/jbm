package com.salmat.jbm.hibernate;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * HsCardOverWeight entity. @author MyEclipse Persistence Tools
 */

public class HsCardOverWeight implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -478002546187805838L;
	/**
	 * 
	 */
	
	// Fields	
	private String jobBagNo;
	private Date cycleDate;
	private Integer accounts;
	private Integer pages;
	private Integer aa;
	private Integer bb;
	private Integer cc;
	private Integer gg;
	private Integer feeder2;
	private Integer feeder3;
	private Integer feeder4;
	private Integer feeder5;
	private Integer onePage;
	private Integer twoPages;
	private Integer threePages;
	private Integer morePages;
	private Integer overWeightId;
	private String fileName;
	private Set<HsCardRpt> hsCardRpts;
	

	public Set<HsCardRpt> getHsCardRpts() {
		return hsCardRpts;
	}

	public void setHsCardRpts(Set<HsCardRpt> hsCardRpts) {
		this.hsCardRpts = hsCardRpts;
	}

	// Constructors
	/** default constructor */
	public HsCardOverWeight() {
	}

	public String getJobBagNo() {
		return jobBagNo;
	}

	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}

	public Date getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}

	public Integer getAccounts() {
		return accounts;
	}

	public void setAccounts(Integer accounts) {
		this.accounts = accounts;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getAa() {
		return aa;
	}

	public void setAa(Integer aa) {
		this.aa = aa;
	}

	public Integer getBb() {
		return bb;
	}

	public void setBb(Integer bb) {
		this.bb = bb;
	}

	public Integer getCc() {
		return cc;
	}

	public void setCc(Integer cc) {
		this.cc = cc;
	}

	public Integer getGg() {
		return gg;
	}

	public void setGg(Integer gg) {
		this.gg = gg;
	}

	public Integer getFeeder2() {
		return feeder2;
	}

	public void setFeeder2(Integer feeder2) {
		this.feeder2 = feeder2;
	}

	public Integer getFeeder3() {
		return feeder3;
	}

	public void setFeeder3(Integer feeder3) {
		this.feeder3 = feeder3;
	}

	public Integer getFeeder4() {
		return feeder4;
	}

	public void setFeeder4(Integer feeder4) {
		this.feeder4 = feeder4;
	}

	public Integer getFeeder5() {
		return feeder5;
	}

	public void setFeeder5(Integer feeder5) {
		this.feeder5 = feeder5;
	}

	public Integer getOnePage() {
		return onePage;
	}

	public void setOnePage(Integer onePage) {
		this.onePage = onePage;
	}

	public Integer getTwoPages() {
		return twoPages;
	}

	public void setTwoPages(Integer twoPages) {
		this.twoPages = twoPages;
	}

	public Integer getThreePages() {
		return threePages;
	}

	public void setThreePages(Integer threePages) {
		this.threePages = threePages;
	}

	public Integer getMorePages() {
		return morePages;
	}

	public void setMorePages(Integer morePages) {
		this.morePages = morePages;
	}

	public Integer getOverWeightId() {
		return overWeightId;
	}

	public void setOverWeightId(Integer overWeightId) {
		this.overWeightId = overWeightId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override 
    public int hashCode() { 
            return HashCodeBuilder.reflectionHashCode(this); 
    }
	
	@Override 
    public boolean equals(Object obj) { 
            return EqualsBuilder.reflectionEquals(this, obj); 

    }

}