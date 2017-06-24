package com.salmat.jbm.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OneGoldFormater {
	private String clientID; //固定為 CT (CitiOne/CitiGold)
	private String splname1;
	private String type;
	private String stattype;
	private String accounts;
	private String totsheet;
	private String totpage;
	private String totpage_wb;
	private String afpFileName;
	private String biz;
	private String CS;
	private String Envelop;
	private String logFileName;
	private String cycleDate; //抓log 檔案日期
	private Date receiveDate;  //固定為 實體檔案 產生日期
	private String SEQ; //pattern 為 nn/n 的序號	
	private String lineData;
	private String pattern;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	public Date getRealCycleDate() throws ParseException{
		if(cycleDate != null){
			return sdf.parse(cycleDate);
		}else{
			return new Date();
		}
			
	}
	
	public String getCycleDate() {
		return cycleDate;
	}
	public void setCycleDate(String cycleDate) {
		this.cycleDate = cycleDate;
	}
	public Date getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getSplname1() {
		return splname1;
	}

	public void setSplname1(String splname1) {
		this.splname1 = splname1;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStattype() {
		return stattype;
	}

	public void setStattype(String stattype) {
		this.stattype = stattype;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public String getTotsheet() {
		return totsheet;
	}

	public void setTotsheet(String totsheet) {
		this.totsheet = totsheet;
	}

	public String getTotpage() {
		return totpage;
	}

	public void setTotpage(String totpage) {
		this.totpage = totpage;
	}

	public String getTotpage_wb() {
		return totpage_wb;
	}

	public void setTotpage_wb(String totpageWb) {
		totpage_wb = totpageWb;
	}


	public String getAfpFileName() {
		return afpFileName;
	}
	public void setAfpFileName(String afpFileName) {
		this.afpFileName = afpFileName;
	}
	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public String getCS() {
		return CS;
	}

	public void setCS(String cS) {
		CS = cS;
	}

	public String getEnvelop() {
		return Envelop;
	}

	public void setEnvelop(String envelop) {
		Envelop = envelop;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.clientID + ":" + this.afpFileName + ":" + this.pattern + ":" +  this.logFileName;
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


}
