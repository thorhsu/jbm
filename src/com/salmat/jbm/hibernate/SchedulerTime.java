package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Code entity. @author MyEclipse Persistence Tools
 * 定時寄件時間設定
 */

public class SchedulerTime implements java.io.Serializable {

	// Fields
	private Long timerId;
	private String url;  // url + custNo + purpose為理論上的PK
	private String schedulerMin;
	private String schedulerHour;
	private String schedulerDate;
	private String schedulerMonth;
	private String schedulerYear;
	private String schedulerWeekDay;
	private String custNo;
	private String purpose;
	private String cronTrigger;
	private Set<SchedulerEmail> schedulerEmails;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSchedulerMin() {
		return schedulerMin;
	}

	public void setSchedulerMin(String schedulerMin) {
		this.schedulerMin = schedulerMin;
	}

	public String getSchedulerHour() {
		return schedulerHour;
	}

	public void setSchedulerHour(String schedulerHour) {
		this.schedulerHour = schedulerHour;
	}

	public String getSchedulerDate() {
		return schedulerDate;
	}

	public void setSchedulerDate(String schedulerDate) {
		this.schedulerDate = schedulerDate;
	}

	public String getSchedulerMonth() {
		return schedulerMonth;
	}

	public void setSchedulerMonth(String schedulerMonth) {
		this.schedulerMonth = schedulerMonth;
	}

	public String getSchedulerYear() {
		return schedulerYear;
	}

	public void setSchedulerYear(String schedulerYear) {
		this.schedulerYear = schedulerYear;
	}

	public String getSchedulerWeekDay() {
		return schedulerWeekDay;
	}

	public void setSchedulerWeekDay(String schedulerWeekDay) {
		this.schedulerWeekDay = schedulerWeekDay;
	}

	public Set<SchedulerEmail> getSchedulerEmails() {
		return schedulerEmails;
	}

	public void setSchedulerEmails(Set<SchedulerEmail> schedulerEmails) {
		this.schedulerEmails = schedulerEmails;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public Long getTimerId() {
		return timerId;
	}

	public void setTimerId(Long timerId) {
		this.timerId = timerId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCronTrigger() {
		return cronTrigger;
	}

	public void setCronTrigger(String cronTrigger) {
		this.cronTrigger = cronTrigger;
	}

}