package com.salmat.jbm.hibernate;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Lpinfo entity. @author MyEclipse Persistence Tools
 */

public class FbRegisterNos implements java.io.Serializable {

	// Fields
	private Integer id;
	private String jobBagNo;
	private String jobCodeNo;
	private Date receiveDate;
	private Date cycleDate;
	private String afpName;
	private String registerNoBegin;
	private String registerNoEnd;
	private String querySession;
	private String csvName;
	private String readLine;
	

	// Constructors

	/** default constructor */
	public FbRegisterNos() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getJobBagNo() {
		return jobBagNo;
	}


	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}


	public String getJobCodeNo() {
		return jobCodeNo;
	}


	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}


	public Date getReceiveDate() {
		return receiveDate;
	}


	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}


	public Date getCycleDate() {
		return cycleDate;
	}


	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}


	public String getAfpName() {
		return afpName;
	}


	public void setAfpName(String afpName) {
		this.afpName = afpName;
	}


	public String getRegisterNoBegin() {
		return registerNoBegin;
	}


	public void setRegisterNoBegin(String registerNoBegin) {
		this.registerNoBegin = registerNoBegin;
	}


	public String getRegisterNoEnd() {
		return registerNoEnd;
	}


	public void setRegisterNoEnd(String registerNoEnd) {
		this.registerNoEnd = registerNoEnd;
	}


	public String getQuerySession() {
		return querySession;
	}


	public void setQuerySession(String querySession) {
		this.querySession = querySession;
	}


	public String getCsvName() {
		return csvName;
	}


	public void setCsvName(String csvName) {
		this.csvName = csvName;
	}


	public String getReadLine() {
		return readLine;
	}


	public void setReadLine(String readLine) {
		this.readLine = readLine;
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