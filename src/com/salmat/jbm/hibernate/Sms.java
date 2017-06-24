package com.salmat.jbm.hibernate;


import java.util.Date;
/**
 * Sms entity. @author MyEclipse Persistence Tools
 */

public class Sms implements java.io.Serializable {
	// Fields
	private int id;
	private String phoneNos;
	private String msg;
	private String type;
	private String status;
	private boolean sent;
	private Date insertDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoneNos() {
		return phoneNos;
	}
	public void setPhoneNos(String phoneNos) {
		this.phoneNos = phoneNos;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
}