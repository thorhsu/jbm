package com.salmat.jbm.bean;

import java.util.HashSet;
import java.util.Set;

import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.Customer;

/**
 * Lpinfo entity. @author MyEclipse Persistence Tools
 */

public class LginfoJSON implements java.io.Serializable {
	private String lgNo;
	private String mailToArea1;
	private String mailCategory;
	private String mailToPostoffice;
	private String lgType;
	private Customer customer;
	private String progNm;
	private String dispPosta;
	private String PType;
	private Boolean PList;
	private String mailToAreaDisplay;
	
	private Integer codeMailToArea1;
	private Integer codeMailToArea5;
	private Integer codeMailToArea4;
	private Integer codeMailToArea3;
	private Integer codeMailToArea2;
	private Integer codeMailToArea6;	
	
	
	public String getLgNo() {
		return lgNo;
	}
	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}
	public String getMailToArea1() {
		return mailToArea1;
	}
	public void setMailToArea1(String mailToArea1) {
		this.mailToArea1 = mailToArea1;
	}
	public String getMailCategory() {
		return mailCategory;
	}
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}
	public String getMailToPostoffice() {
		return mailToPostoffice;
	}
	public void setMailToPostoffice(String mailToPostoffice) {
		this.mailToPostoffice = mailToPostoffice;
	}

	public String getLgType() {
		return lgType;
	}
	public void setLgType(String lgType) {
		this.lgType = lgType;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getProgNm() {
		return progNm;
	}
	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}
	public String getDispPosta() {
		return dispPosta;
	}
	public void setDispPosta(String dispPosta) {
		this.dispPosta = dispPosta;
	}
	public String getPType() {
		return PType;
	}
	public void setPType(String pType) {
		PType = pType;
	}
	public Boolean getPList() {
		return PList;
	}
	public void setPList(Boolean pList) {
		PList = pList;
	}
	public String getMailToAreaDisplay() {
		return mailToAreaDisplay;
	}
	public void setMailToAreaDisplay(String mailToAreaDisplay) {
		this.mailToAreaDisplay = mailToAreaDisplay;
	}
	public Integer getCodeMailToArea1() {
		return codeMailToArea1;
	}
	public void setCodeMailToArea1(Integer codeMailToArea1) {
		this.codeMailToArea1 = codeMailToArea1;
	}
	public Integer getCodeMailToArea5() {
		return codeMailToArea5;
	}
	public void setCodeMailToArea5(Integer codeMailToArea5) {
		this.codeMailToArea5 = codeMailToArea5;
	}
	public Integer getCodeMailToArea4() {
		return codeMailToArea4;
	}
	public void setCodeMailToArea4(Integer codeMailToArea4) {
		this.codeMailToArea4 = codeMailToArea4;
	}
	public Integer getCodeMailToArea3() {
		return codeMailToArea3;
	}
	public void setCodeMailToArea3(Integer codeMailToArea3) {
		this.codeMailToArea3 = codeMailToArea3;
	}
	public Integer getCodeMailToArea2() {
		return codeMailToArea2;
	}
	public void setCodeMailToArea2(Integer codeMailToArea2) {
		this.codeMailToArea2 = codeMailToArea2;
	}
	public Integer getCodeMailToArea6() {
		return codeMailToArea6;
	}
	public void setCodeMailToArea6(Integer codeMailToArea6) {
		this.codeMailToArea6 = codeMailToArea6;
	}
	
}