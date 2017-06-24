package com.salmat.jbm.bean;

import java.util.Date;

public class AmexFormater {
	private String wriFileName;
	private String country;
	private String cycleNumber;
	private String typeOfFile;
	private String language;
	private Date cycleDate;
	private String afpFileName;
	private String sequence;
	private String accounts;
	private String pages;
	private String sheets;
	private String feeder2;
	private String feeder3;
	private String feeder4;
	private String feeder5;
	private String feeder6;
	private String feeder7;
	private String tray1;
	private String tray2;
	private String tray3;
	private String tray4;
	private String csvFileName;
	private Date receiveDate;  //固定為 實體檔案 產生日期
	private String SEQ; //pattern 為 nn/n 的序號
	private String lineData;
	private String pattern;
	private String MRDF;	

	
	public String getSheets() {
		return sheets;
	}



	public void setSheets(String sheets) {
		this.sheets = sheets;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public String getCycleNumber() {
		return cycleNumber;
	}



	public void setCycleNumber(String cycleNumber) {
		this.cycleNumber = cycleNumber;
	}



	public String getTypeOfFile() {
		return typeOfFile;
	}



	public void setTypeOfFile(String typeOfFile) {
		this.typeOfFile = typeOfFile;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}






	public Date getCycleDate() {
		return cycleDate;
	}



	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}



	public String getAfpFileName() {
		return afpFileName;
	}



	public void setAfpFileName(String afpFileName) {
		this.afpFileName = afpFileName;
	}



	public String getSequence() {
		return sequence;
	}



	public void setSequence(String sequence) {
		this.sequence = sequence;
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





	public Date getReceiveDate() {
		return receiveDate;
	}



	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.country + ":" + this.afpFileName + ":" +  this.pattern + ":" +   this.csvFileName;
	}



	public String getWriFileName() {
		return wriFileName;
	}



	public void setWriFileName(String wriFileName) {
		this.wriFileName = wriFileName;
	}



	public String getCsvFileName() {
		return csvFileName;
	}



	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
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



	public String getFeeder6() {
		return feeder6;
	}



	public void setFeeder6(String feeder6) {
		this.feeder6 = feeder6;
	}



	public String getFeeder7() {
		return feeder7;
	}



	public void setFeeder7(String feeder7) {
		this.feeder7 = feeder7;
	}



	public String getMRDF() {
		return MRDF;
	}



	public void setMRDF(String mRDF) {
		MRDF = mRDF;
	}




	

}
