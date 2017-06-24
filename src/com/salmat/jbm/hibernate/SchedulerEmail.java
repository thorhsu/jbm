package com.salmat.jbm.hibernate;

/**
 * Code entity. @author MyEclipse Persistence Tools
 * 定時寄件時間設定
 */

public class SchedulerEmail implements java.io.Serializable {

	// Fields
	private Long id;
	private Integer timerId;
	private String email;
	private String telNo;
	private String note;	
	private SchedulerTime schedulerTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public Integer getTimerId() {
		return timerId;
	}
	public void setTimerId(Integer timerId) {
		this.timerId = timerId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public SchedulerTime getSchedulerTime() {
		return schedulerTime;
	}
	public void setSchedulerTime(SchedulerTime schedulerTime) {
		this.schedulerTime = schedulerTime;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	
		

}