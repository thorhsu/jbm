package com.salmat.jbm.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FbRegisterFormater {
	private String afpName;
	private Date cycleDate;
	private String registerNoBegin;
	private String registerNoEnd;
	private String filePattern;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	public String getAfpName() {
		return afpName;
	}
	public String getAfpNameWithoutExtensionName(){
		int index = 0;
		if((index = afpName.indexOf(".")) >= 0){
			return afpName.substring(0, index);
		}else{
			return afpName;
		}
	}
	
	public void setAfpName(String afpName) {
		this.afpName = afpName;
	}
	public Date getCycleDate() {
		return cycleDate;
	}
	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}
	public void setCycleDate(String cycleDate) throws ParseException {
		if(cycleDate != null)
		   this.cycleDate = sdf.parse(cycleDate);
		else
		   this.cycleDate = null;
	}
	
	public String getRegisterNoBegin() {
		return registerNoBegin;
	}
	public void setRegisterNoBegin(String registerNoBegin) {
		this.registerNoBegin = registerNoBegin;
	}
	public String getRegisterNoEnd() {
		return registerNoEnd;
	}
	public void setRegisterNoEnd(String registerNoEnd) {
		this.registerNoEnd = registerNoEnd;
	}
	public String getFilePattern() {
		return filePattern;
	}
	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}
	
	
	
	
}
