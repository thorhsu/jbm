package com.salmat.jbm.bean;

import java.util.Date;

public class DamageFormater {
	private String Filename; //固定為 CT (CitiOne/CitiGold)
	private Date ProcessDate;
	private String accounts;
	private String pages;
	private String orgAccounts;
	private String orgPages;
	
	private String logFileName;
	private Date cycleDate; //固定為 實體檔案 產生日期
	private Date receiveDate;  //固定為 實體檔案 產生日期
	private String SEQ; //pattern 為 nn/n 的序號	
	private String lineData;
	private String pattern;
	
	public String getFilename() {
		return Filename;
	}
	public void setFilename(String filename) {
		Filename = filename;
	}


	public Date getProcessDate() {
		return ProcessDate;
	}
	public void setProcessDate(Date processDate) {
		ProcessDate = processDate;
	}
	public String getAccounts() {
		return accounts;
	}
	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getOrgAccounts() {
		return orgAccounts;
	}
	public void setOrgAccounts(String orgAccounts) {
		this.orgAccounts = orgAccounts;
	}
	public String getOrgPages() {
		return orgPages;
	}
	public void setOrgPages(String orgPages) {
		this.orgPages = orgPages;
	}
	public String getLogFileName() {
		return logFileName;
	}
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}
	public Date getCycleDate() {
		return cycleDate;
	}
	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getSEQ() {
		return SEQ;
	}
	public void setSEQ(String sEQ) {
		SEQ = sEQ;
	}
	public String getLineData() {
		return lineData;
	}
	public void setLineData(String lineData) {
		this.lineData = lineData;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.Filename + ":" + this.pattern+ ":" +   this.logFileName;
	}
}
