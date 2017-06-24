package com.salmat.jbm.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.salmat.jbm.hibernate.Code;

public class LGFormBean {
	private String idfCustNo;
	private String lgNo;
	private String custName;	
	private String lgTitle;
	private Integer codeMailToArea;
	private String mailToArea;
	private Integer codeLgType;
	private String lgType;
	private Integer codeMailCategory;
	private String mailCategory;	
	private BigDecimal price;
	private Integer weight;
	private Integer qty;
	private String jobBagNo;	
	private String jobCodeNo;
	private String progNm;	
	private String pathCustStampImage;	
	public String getIdfCustNo() {
		return idfCustNo;
	}
	public void setIdfCustNo(String idfCustNo) {
		this.idfCustNo = idfCustNo;
	}

	public String getLgNo() {
		return lgNo;
	}
	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getLgTitle() {
		return lgTitle;
	}
	public void setLgTitle(String lgTitle) {
		this.lgTitle = lgTitle;
	}
	public Integer getCodeMailToArea() {
		return codeMailToArea;
	}
	public void setCodeMailToArea(Integer codeMailToArea) {
		this.codeMailToArea = codeMailToArea;
	}
	public String getMailToArea() {
		return mailToArea;
	}
	public void setMailToArea(String mailToArea) {
		this.mailToArea = mailToArea;
	}
	public Integer getCodeLgType() {
		return codeLgType;
	}
	public void setCodeLgType(Integer codeLgType) {
		this.codeLgType = codeLgType;
	}
	public String getLgType() {
		return lgType;
	}
	public void setLgType(String lgType) {
		this.lgType = lgType;
	}
	public Integer getCodeMailCategory() {
		return codeMailCategory;
	}
	public void setCodeMailCategory(Integer codeMailCategory) {
		this.codeMailCategory = codeMailCategory;
	}
	public String getMailCategory() {
		return mailCategory;
	}
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public String getJobBagNo() {
		return jobBagNo;
	}
	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}
	public String getJobCodeNo() {
		return jobCodeNo;
	}
	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}
	public String getProgNm() {
		return progNm;
	}
	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}
	public String getPathCustStampImage() {
		return pathCustStampImage;
	}
	public void setPathCustStampImage(String pathCustStampImage) {
		this.pathCustStampImage = pathCustStampImage;
	}



}
