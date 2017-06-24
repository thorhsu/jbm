package com.salmat.jbm.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZipCodeFormater {
	
	
	private String clientID; // 客戶編號
	private Date cycleDate; // CYCLE DATE
	private Date receiveDate;  //收檔時間
	private String afpFileName; // AFP檔案名稱
	private String zipCode; // 郵遞區號
	private Long sum; // 單筆郵遞區號總計
	private String pattern; // FILEPATTER
	private Long total; // 全部總計
	
	private String jobBagNo; // 對應工單代號
	private String logFileName; // 處理檔案名稱
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	
	public String getClientID() {
		return clientID;
	}



	public void setClientID(String clientID) {
		this.clientID = clientID;
	}


	public Date getCycleDate() {
		return cycleDate;
	}



	public void setCycleDate(String cycleDate) throws ParseException {
		
		if(cycleDate != null)
			   this.cycleDate = sdf.parse(cycleDate);
			else
			   this.cycleDate = null;
	}
	
	public void setReceiveDate(String receiveDate) throws ParseException {
		if(receiveDate != null)
			   this.receiveDate = sdf.parse(receiveDate);
			else
			   this.receiveDate = null;
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



	public String getZipCode() {
		return zipCode;
	}



	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}



	public Long getSum() {
		return sum;
	}



	public void setSum(Long sum) {
		this.sum = sum;
	}



	public String getPattern() {
		return pattern;
	}



	public void setPattern(String pattern) {
		this.pattern = pattern;
	}



	public Long getTotal() {
		return total;
	}



	public void setTotal(Long total) {
		this.total = total;
	}



	public String getJobBagNo() {
		return jobBagNo;
	}



	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}



	public String getLogFileName() {
		return logFileName;
	}



	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}



	public Date getReceiveDate() {
		return receiveDate;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ClientID:" + this.clientID + ", cycleDate:" + this.cycleDate + ", afpFileName:" + this.afpFileName 
				+ ", zipCode:" + this.zipCode + ", sum:" + this.sum + ", pattern:" + this.pattern + ", total:" + this.total
				+ ", logFileName:" + this.logFileName + ", jobBagNo:" + this.jobBagNo;
	}

}
