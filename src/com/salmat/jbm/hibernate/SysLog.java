package com.salmat.jbm.hibernate;

import java.util.Date;

/**
 * SysLog entity. @author MyEclipse Persistence Tools
 */

public class SysLog implements java.io.Serializable {

	// Fields
	private Long id;
	private String logType;
	private String subject;
	private String messageBody;
	private Boolean isException;
	private String errorLog;
	private Date createDate;

	// Constructors

	/** default constructor */
	public SysLog() {
	}

	/** full constructor */
	public SysLog(String logType, String subject, String messageBody,
			Boolean isException, String errorLog, Date createDate) {
		this.logType = logType;
		this.subject = subject;
		this.messageBody = messageBody;
		this.isException = isException;
		this.errorLog = errorLog;
		this.createDate = createDate;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageBody() {
		return this.messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public Boolean getIsException() {
		return this.isException;
	}

	public void setIsException(Boolean isException) {
		this.isException = isException;
	}

	public String getErrorLog() {
		return this.errorLog;
	}

	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}