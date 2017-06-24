package com.salmat.jbm.hibernate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.painter.util.ChineseCalendar;

/**
 * Sms entity. @author MyEclipse Persistence Tools
 */

public class EddNotSend implements java.io.Serializable {
	// Fields
	private int id;
	private String afpName;
	private Integer notSent;
	private Date insertDate;
	private Boolean syncToJobbag;
	private String custNo;
	private String jobBagNo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAfpName() {
		return afpName;
	}
	public void setAfpName(String afpName) {
		this.afpName = afpName;
	}
	public Integer getNotSent() {
		return notSent;
	}
	public void setNotSent(Integer notSent) {
		this.notSent = notSent;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public Boolean getSyncToJobbag() {
		return syncToJobbag;
	}
	public void setSyncToJobbag(Boolean syncToJobbag) {
		this.syncToJobbag = syncToJobbag;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getJobBagNo() {
		return jobBagNo;
	}
	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}
	 	
	
		
}