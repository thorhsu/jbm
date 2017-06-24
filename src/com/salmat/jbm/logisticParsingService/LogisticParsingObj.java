package com.salmat.jbm.logisticParsingService;

import com.salmat.jbm.hibernate.JobCode;

public class LogisticParsingObj {
	private String filePattern;
	private String typeOfFile = "";
	private String speaker = "";
	private JobCode jobCode ;
	//可以繼續增加
	
	
	public JobCode getJobCode() {
		return jobCode;
	}
	public void setJobCode(JobCode jobCode) {
		this.jobCode = jobCode;
	}
	public String getFilePattern() {
		return filePattern;
	}
	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}
	public String getTypeOfFile() {
		return typeOfFile;
	}
	public void setTypeOfFile(String typeOfFile) {
		this.typeOfFile = typeOfFile;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

}
