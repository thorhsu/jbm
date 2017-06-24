package com.salmat.jbm.bean;

import java.util.Date;

public class CiticardFormater {
	private String clientID; //固定為 CT (CitiOne/CitiGold)
	private String accounts;
	private String pageno;
	private String f2;
	private String f3;
	private String f4;
	private String f5;
	private String prodate;
	private String protime;	
	private String afpFileName;
	private String mAcct;
	private String pagenoB;
	private String cAcct;
	private String sortI;	
	private String logFileName;
	private String cycleDate; //抓log 檔案日期
	private Date receiveDate;  //固定為 實體檔案 產生日期
	private String SEQ; //pattern 為 nn/n 的序號	
	private String lineData;
	private String pattern;

	public String getProtime() {
		return protime;
	}



	public void setProtime(String protime) {
		this.protime = protime;
	}



	public String getClientID() {
		return clientID;
	}



	public void setClientID(String clientID) {
		this.clientID = clientID;
	}



	public String getAccounts() {
		return accounts;
	}



	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}



	public String getPageno() {
		return pageno;
	}



	public void setPageno(String pageno) {
		this.pageno = pageno;
	}



	public String getF2() {
		return f2;
	}



	public void setF2(String f2) {
		this.f2 = f2;
	}



	public String getF3() {
		return f3;
	}



	public void setF3(String f3) {
		this.f3 = f3;
	}



	public String getF4() {
		return f4;
	}



	public void setF4(String f4) {
		this.f4 = f4;
	}



	public String getF5() {
		return f5;
	}



	public void setF5(String f5) {
		this.f5 = f5;
	}



	public String getProdate() {
		return prodate;
	}



	public void setProdate(String prodate) {
		this.prodate = prodate;
	}



	public String getAfpFileName() {
		return afpFileName;
	}



	public void setAfpFileName(String afpFileName) {
		this.afpFileName = afpFileName;
	}



	public String getmAcct() {
		return mAcct;
	}



	public void setmAcct(String mAcct) {
		this.mAcct = mAcct;
	}



	public String getPagenoB() {
		return pagenoB;
	}



	public void setPagenoB(String pagenoB) {
		this.pagenoB = pagenoB;
	}



	public String getcAcct() {
		return cAcct;
	}



	public void setcAcct(String cAcct) {
		this.cAcct = cAcct;
	}



	public String getSortI() {
		return sortI;
	}



	public void setSortI(String sortI) {
		this.sortI = sortI;
	}



	public String getLogFileName() {
		return logFileName;
	}



	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
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



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.clientID + ":" + this.afpFileName + ":" +  this.pattern + ":" +    this.logFileName;
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
