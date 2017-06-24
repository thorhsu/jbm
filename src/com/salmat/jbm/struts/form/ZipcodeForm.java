package com.salmat.jbm.struts.form;

import org.apache.struts.action.ActionForm;



public class ZipcodeForm extends ActionForm {
	// Fields

	private static final long serialVersionUID = -184725059403443585L;
	
	// 查詢用參數
	private String custNo; // 客戶代號
	private String cycleDate_form; // CYCLE DATE
	private String jobBagNo; // 工單代號
	private String receiveDate_form; // 轉檔(收檔)時間
	private String zipcodeFilename; // 產生的ZIP CODE檔案名稱
	private String dispatchTime_form; // 郵件種類 - 投遞時效
	private String[] dispatchKind_form; // 郵件種類 - 郵件類型
	private String[] jobBagStatuses; // 工單進度
	private Integer pages; // 每頁頁數
	private String isCreatedFile; // 是否已產生檔案
	private String zipcodeTotal_form; // 交寄筆數
	private Integer codeMailToPostoffice; //交寄郵局或公司
	
	// 產生檔案用參數
	private String[] zipcodeInfoNos; // check box
	private String dispatchTime; // 投遞時效
	private String dispatchKind; // 郵件類型
	private String serverPath; // 產生檔案存放位置
	private String updateUser; // 操作人員
	
	
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getCycleDate_form() {
		return cycleDate_form;
	}
	public void setCycleDate_form(String cycleDate_form) {
		this.cycleDate_form = cycleDate_form;
	}
	public String getJobBagNo() {
		return jobBagNo;
	}
	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}
	public String getReceiveDate_form() {
		return receiveDate_form;
	}
	public void setReceiveDate_form(String receiveDate_form) {
		this.receiveDate_form = receiveDate_form;
	}
	public String getZipcodeFilename() {
		return zipcodeFilename;
	}
	public void setZipcodeFilename(String zipcodeFilename) {
		this.zipcodeFilename = zipcodeFilename;
	}
	public String getDispatchTime_form() {
		return dispatchTime_form;
	}
	public void setDispatchTime_form(String dispatchTime_form) {
		this.dispatchTime_form = dispatchTime_form;
	}
	public String[] getDispatchKind_form() {
		return dispatchKind_form;
	}
	public void setDispatchKind_form(String[] dispatchKind_form) {
		this.dispatchKind_form = dispatchKind_form;
	}
	public String[] getJobBagStatuses() {
		return jobBagStatuses;
	}
	public void setJobBagStatuses(String[] jobBagStatuses) {
		this.jobBagStatuses = jobBagStatuses;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public String getIsCreatedFile() {
		return isCreatedFile;
	}
	public void setIsCreatedFile(String isCreatedFile) {
		this.isCreatedFile = isCreatedFile;
	}
	public String getZipcodeTotal_form() {
		return zipcodeTotal_form;
	}
	public void setZipcodeTotal_form(String zipcodeTotal_form) {
		this.zipcodeTotal_form = zipcodeTotal_form;
	}
	public String[] getZipcodeInfoNos() {
		return zipcodeInfoNos;
	}
	public void setZipcodeInfoNos(String[] zipcodeInfoNos) {
		this.zipcodeInfoNos = zipcodeInfoNos;
	}
	public String getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public String getDispatchKind() {
		return dispatchKind;
	}
	public void setDispatchKind(String dispatchKind) {
		this.dispatchKind = dispatchKind;
	}
	public String getServerPath() {
		return serverPath;
	}
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Integer getCodeMailToPostoffice() {
		return codeMailToPostoffice;
	}
	public void setCodeMailToPostoffice(Integer codeMailToPostoffice) {
		this.codeMailToPostoffice = codeMailToPostoffice;
	}
	
	
}