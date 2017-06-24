package com.salmat.jbm.hibernate;

import java.util.Date;

/**
 * HistoryReport entity. @author MyEclipse Persistence Tools
 */

public class HistoryReport implements java.io.Serializable {

	// Fields

	private Integer id;
	private String reportName;
	private String pdfPath;
	private String empNo;
	private Date createDate;

	// Constructors

	/** default constructor */
	public HistoryReport() {
	}

	/** full constructor */
	public HistoryReport(String reportName, String pdfPath, String empNo,
			Date createDate) {
		this.reportName = reportName;
		this.pdfPath = pdfPath;
		this.empNo = empNo;
		this.createDate = createDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getPdfPath() {
		return this.pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}