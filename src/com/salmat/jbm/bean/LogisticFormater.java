package com.salmat.jbm.bean;

import java.util.Date;

public class LogisticFormater {
	private String clientID;
	private String afpFileName;
	private String cycleDate;
	private String processDate;
	private String accounts;
	private String pages;
	private String feeder2;
	private String feeder3;
	private String feeder4;
	private String feeder5;
	private String tray1;
	private String tray2;
	private String tray3;
	private String tray4;
	private String tray5;
	private String tray6;
	private String tray7;
	private String tray8;
	private String pattern;
	private String logFileName;
	private Date receiveDate;  //固定為 實體檔案 產生日期
	private String SEQ; //pattern 為 nn/n 的序號	
	private String lineData;
	private String p1accounts;
	private String p2accounts;
	private String p3accounts;
	private String p4accounts;
	private String p5accounts;
	private String p6accounts;
	private String pxaccounts;
	public String getAfpFileName() {
		return afpFileName;
	}
	public void setAfpFileName(String afpFileName) {
		this.afpFileName = afpFileName;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getLogFileName() {
		return logFileName;
	}
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getCycleDate() {
		return cycleDate;
	}
	public void setCycleDate(String cycleDate) {
		this.cycleDate = cycleDate;
	}
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
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
	public String getFeeder2() {
		return feeder2;
	}
	public void setFeeder2(String feeder2) {
		this.feeder2 = feeder2;
	}
	public String getFeeder3() {
		return feeder3;
	}
	public void setFeeder3(String feeder3) {
		this.feeder3 = feeder3;
	}
	public String getFeeder4() {
		return feeder4;
	}
	public void setFeeder4(String feeder4) {
		this.feeder4 = feeder4;
	}
	public String getFeeder5() {
		return feeder5;
	}
	public void setFeeder5(String feeder5) {
		this.feeder5 = feeder5;
	}
	public String getTray1() {
		return tray1;
	}
	public void setTray1(String tray1) {
		this.tray1 = tray1;
	}
	public String getTray2() {
		return tray2;
	}
	public void setTray2(String tray2) {
		this.tray2 = tray2;
	}
	public String getTray3() {
		return tray3;
	}
	public void setTray3(String tray3) {
		this.tray3 = tray3;
	}
	public String getTray4() {
		return tray4;
	}
	public void setTray4(String tray4) {
		this.tray4 = tray4;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.clientID + ":" + this.afpFileName + ":" + this.pattern + ":" +   this.logFileName;
	}
	public String getSEQ() {
		return SEQ;
	}
	public void setSEQ(String sEQ) {
		SEQ = sEQ;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getLineData() {
		return lineData;
	}
	public void setLineData(String lineData) {
		this.lineData = lineData;
	}
	public String getP1accounts() {
		return p1accounts;
	}
	public void setP1accounts(String p1accounts) {
		this.p1accounts = p1accounts;
	}
	public String getP2accounts() {
		return p2accounts;
	}
	public void setP2accounts(String p2accounts) {
		this.p2accounts = p2accounts;
	}
	public String getP3accounts() {
		return p3accounts;
	}
	public void setP3accounts(String p3accounts) {
		this.p3accounts = p3accounts;
	}
	public String getP4accounts() {
		return p4accounts;
	}
	public void setP4accounts(String p4accounts) {
		this.p4accounts = p4accounts;
	}
	public String getP5accounts() {
		return p5accounts;
	}
	public void setP5accounts(String p5accounts) {
		this.p5accounts = p5accounts;
	}
	public String getP6accounts() {
		return p6accounts;
	}
	public void setP6accounts(String p6accounts) {
		this.p6accounts = p6accounts;
	}
	public String getPxaccounts() {
		return pxaccounts;
	}
	public void setPxaccounts(String pxaccounts) {
		this.pxaccounts = pxaccounts;
	}
	public String getTray5() {
		return tray5;
	}
	public void setTray5(String tray5) {
		this.tray5 = tray5;
	}
	public String getTray6() {
		return tray6;
	}
	public void setTray6(String tray6) {
		this.tray6 = tray6;
	}
	public String getTray7() {
		return tray7;
	}
	public void setTray7(String tray7) {
		this.tray7 = tray7;
	}
	public String getTray8() {
		return tray8;
	}
	public void setTray8(String tray8) {
		this.tray8 = tray8;
	}
}
