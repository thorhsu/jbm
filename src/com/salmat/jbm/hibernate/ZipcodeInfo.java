package com.salmat.jbm.hibernate;

import java.util.Date;

/**
 * ZipcodeInfo entity. 
 * @author James_Tsai
 */

public class ZipcodeInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7860455853833598028L;
	// Fields

	private Integer zipcodeInfoNo; // PK
	private String custNo;
	private String srcFilename;
	private Integer zipcodeTotal;
	private String jobBagNo;
	private Date cycleDate;
	private Date receiveDate;
	private String afpFilename;
	private String filePattern;
	private Date createDate;
	private Date updateDate;
	private String updateUser;
	private Integer combinedNum;
	private String zipcodeFilename;
	private Boolean detailIsDelete;	
	private Date deletedDate;
	private String dispatchTime;
	private String dispatchKind;
	

	// Constructors

	/** default constructor */
	public ZipcodeInfo() {

	}

	/** minimal constructor */
	public ZipcodeInfo(Integer zipcodeInfoNo) {
		this.zipcodeInfoNo = zipcodeInfoNo;
	}

	/** full constructor */
	public ZipcodeInfo(Integer zipcodeInfoNo, String custNo,
			String srcFilename, Integer zipcodeTotal, String jobBagNo,
			Date cycleDate, String afpFilename, String filePattern, Date createDate, Date receiveDate,
			Date updateDate, String updateUser, Integer combinedNum, String zipcodeFilename,
			Boolean detailIsDelete, Date deletedDate,
			String dispatchTime, String dispatchKind){
		
		this.zipcodeInfoNo = zipcodeInfoNo;
		this.custNo = custNo;
		this.srcFilename = srcFilename;
		this.zipcodeTotal = zipcodeTotal;
		this.jobBagNo = jobBagNo;
		this.cycleDate = cycleDate;
		this.receiveDate = receiveDate;
		this.afpFilename = afpFilename;
		this.filePattern = filePattern;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		this.combinedNum = combinedNum;
		this.zipcodeFilename = zipcodeFilename;
		this.detailIsDelete = detailIsDelete;
		this.deletedDate = deletedDate;
		this.dispatchTime = dispatchTime;
		this.dispatchKind = dispatchKind;
	}

	public Integer getZipcodeInfoNo() {
		return zipcodeInfoNo;
	}

	public void setZipcodeInfoNo(Integer zipcodeInfoNo) {
		this.zipcodeInfoNo = zipcodeInfoNo;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getSrcFilename() {
		return srcFilename;
	}

	public void setSrcFilename(String srcFilename) {
		this.srcFilename = srcFilename;
	}

	public Integer getZipcodeTotal() {
		return zipcodeTotal;
	}

	public void setZipcodeTotal(Integer zipcodeTotal) {
		this.zipcodeTotal = zipcodeTotal;
	}

	public String getJobBagNo() {
		return jobBagNo;
	}

	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
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

	public String getAfpFilename() {
		return afpFilename;
	}

	public void setAfpFilename(String afpFilename) {
		this.afpFilename = afpFilename;
	}

	public String getFilePattern() {
		return filePattern;
	}

	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getCombinedNum() {
		return combinedNum;
	}

	public void setCombinedNum(Integer combinedNum) {
		this.combinedNum = combinedNum;
	}

	public String getZipcodeFilename() {
		return zipcodeFilename;
	}

	public void setZipcodeFilename(String zipcodeFilename) {
		this.zipcodeFilename = zipcodeFilename;
	}

	public Boolean getDetailIsDelete() {
		return detailIsDelete;
	}

	public void setDetailIsDelete(Boolean detailIsDelete) {
		this.detailIsDelete = detailIsDelete;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
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
	
	
}