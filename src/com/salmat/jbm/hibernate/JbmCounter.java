package com.salmat.jbm.hibernate;

import java.util.Date;

/**
 * ZipcodeInfo entity. 
 * @author James_Tsai
 */

public class JbmCounter implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2021882907249415142L;
	// Fields

	private String counterName; // PK
	private Integer curCounterNo;
	private Date createDate;
	

	// Constructors

	/** default constructor */
	public JbmCounter() {

	}

	/** minimal constructor */
	public JbmCounter(String counterName) {
		this.counterName = counterName;
	}

	/** full constructor */
	public JbmCounter(String counterName, Integer curCounterNo,
			Date createDate){
		this.counterName = counterName;
		this.curCounterNo = curCounterNo;
		this.createDate = createDate;
	}

	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public Integer getCurCounterNo() {
		return curCounterNo;
	}

	public void setCurCounterNo(Integer curCounterNo) {
		this.curCounterNo = curCounterNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	

}