/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.form;

import java.sql.Clob;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.salmat.jbm.bean.*;
import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.Customer;
import com.salmat.jbm.hibernate.Employee;



public class LGInfoForm extends ActionForm {
	private String lgNo;
	private Code codeByNextCodeLgType;
	private Code codeByCodeMailToArea1;
	private Code codeByCodeMailCategory;
	private Code codeByCodeMailToPostoffice;
	private Code codeByNextCodeMailToArea1;
	private Code codeByNextCodeMailToPostoffice;
	private Code codeByNextCodeMailToArea5;
	private Code codeByNextCodeMailCategory;
	private Code codeByNextCodeMailToArea4;
	private Code codeByNextCodeMailToArea3;
	private Code codeByNextCodeMailToArea2;
	private Code codeByNextCodeMailToArea6;
	private Code codeByCodeLgType;
	private Code codeByCodeMailToArea4;
	private Code codeByCodeMailToArea5;
	private Code codeByCodeMailToArea2;
	private Code codeByCodeMailToArea3;
	private Code codeByCodeMailToArea6;
	private Customer customer;
	private String progNm;
	private String dispPosta;
	private String PType;
	private Boolean PList;
	private String nextEffectiveDate;
	private String nextProgNm;
	private Boolean nextPList;
	private String mailToAreaDisplay;
	
	private String custNo;
	private Integer codeLgType;
	private Integer nextCodeLgType;
	private Integer codeMailCategory;
	private Integer nextCodeMailCategory;
	private Integer codeMailToPostoffice;
	private Integer nextCodeMailToPostoffice;
	private Boolean lgDisplayQty;
	private Boolean nextLgDisplayQty;
	private Integer[] codeMailToArea;
	private Integer[] nextCodeMailToArea;
	
	
	public String getLgNo() {
		return lgNo;
	}
	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}
	public Code getCodeByNextCodeLgType() {
		return codeByNextCodeLgType;
	}
	public void setCodeByNextCodeLgType(Code codeByNextCodeLgType) {
		this.codeByNextCodeLgType = codeByNextCodeLgType;
	}
	public Code getCodeByCodeMailToArea1() {
		return codeByCodeMailToArea1;
	}
	public void setCodeByCodeMailToArea1(Code codeByCodeMailToArea1) {
		this.codeByCodeMailToArea1 = codeByCodeMailToArea1;
	}
	public Code getCodeByCodeMailCategory() {
		return codeByCodeMailCategory;
	}
	public void setCodeByCodeMailCategory(Code codeByCodeMailCategory) {
		this.codeByCodeMailCategory = codeByCodeMailCategory;
	}
	public Code getCodeByCodeMailToPostoffice() {
		return codeByCodeMailToPostoffice;
	}
	public void setCodeByCodeMailToPostoffice(Code codeByCodeMailToPostoffice) {
		this.codeByCodeMailToPostoffice = codeByCodeMailToPostoffice;
	}
	public Code getCodeByNextCodeMailToArea1() {
		return codeByNextCodeMailToArea1;
	}
	public void setCodeByNextCodeMailToArea1(Code codeByNextCodeMailToArea1) {
		this.codeByNextCodeMailToArea1 = codeByNextCodeMailToArea1;
	}
	public Code getCodeByNextCodeMailToPostoffice() {
		return codeByNextCodeMailToPostoffice;
	}
	public void setCodeByNextCodeMailToPostoffice(
			Code codeByNextCodeMailToPostoffice) {
		this.codeByNextCodeMailToPostoffice = codeByNextCodeMailToPostoffice;
	}
	public Code getCodeByNextCodeMailToArea5() {
		return codeByNextCodeMailToArea5;
	}
	public void setCodeByNextCodeMailToArea5(Code codeByNextCodeMailToArea5) {
		this.codeByNextCodeMailToArea5 = codeByNextCodeMailToArea5;
	}
	public Code getCodeByNextCodeMailCategory() {
		return codeByNextCodeMailCategory;
	}
	public void setCodeByNextCodeMailCategory(Code codeByNextCodeMailCategory) {
		this.codeByNextCodeMailCategory = codeByNextCodeMailCategory;
	}
	public Code getCodeByNextCodeMailToArea4() {
		return codeByNextCodeMailToArea4;
	}
	public void setCodeByNextCodeMailToArea4(Code codeByNextCodeMailToArea4) {
		this.codeByNextCodeMailToArea4 = codeByNextCodeMailToArea4;
	}
	public Code getCodeByNextCodeMailToArea3() {
		return codeByNextCodeMailToArea3;
	}
	public void setCodeByNextCodeMailToArea3(Code codeByNextCodeMailToArea3) {
		this.codeByNextCodeMailToArea3 = codeByNextCodeMailToArea3;
	}
	public Code getCodeByNextCodeMailToArea2() {
		return codeByNextCodeMailToArea2;
	}
	public void setCodeByNextCodeMailToArea2(Code codeByNextCodeMailToArea2) {
		this.codeByNextCodeMailToArea2 = codeByNextCodeMailToArea2;
	}
	public Code getCodeByNextCodeMailToArea6() {
		return codeByNextCodeMailToArea6;
	}
	public void setCodeByNextCodeMailToArea6(Code codeByNextCodeMailToArea6) {
		this.codeByNextCodeMailToArea6 = codeByNextCodeMailToArea6;
	}
	public Code getCodeByCodeLgType() {
		return codeByCodeLgType;
	}
	public void setCodeByCodeLgType(Code codeByCodeLgType) {
		this.codeByCodeLgType = codeByCodeLgType;
	}
	public Code getCodeByCodeMailToArea4() {
		return codeByCodeMailToArea4;
	}
	public void setCodeByCodeMailToArea4(Code codeByCodeMailToArea4) {
		this.codeByCodeMailToArea4 = codeByCodeMailToArea4;
	}
	public Code getCodeByCodeMailToArea5() {
		return codeByCodeMailToArea5;
	}
	public void setCodeByCodeMailToArea5(Code codeByCodeMailToArea5) {
		this.codeByCodeMailToArea5 = codeByCodeMailToArea5;
	}
	public Code getCodeByCodeMailToArea2() {
		return codeByCodeMailToArea2;
	}
	public void setCodeByCodeMailToArea2(Code codeByCodeMailToArea2) {
		this.codeByCodeMailToArea2 = codeByCodeMailToArea2;
	}
	public Code getCodeByCodeMailToArea3() {
		return codeByCodeMailToArea3;
	}
	public void setCodeByCodeMailToArea3(Code codeByCodeMailToArea3) {
		this.codeByCodeMailToArea3 = codeByCodeMailToArea3;
	}
	public Code getCodeByCodeMailToArea6() {
		return codeByCodeMailToArea6;
	}
	public void setCodeByCodeMailToArea6(Code codeByCodeMailToArea6) {
		this.codeByCodeMailToArea6 = codeByCodeMailToArea6;
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
	public String getNextEffectiveDate() {
		return nextEffectiveDate;
	}
	public void setNextEffectiveDate(String nextEffectiveDate) {
		this.nextEffectiveDate = nextEffectiveDate;
	}
	public String getNextProgNm() {
		return nextProgNm;
	}
	public void setNextProgNm(String nextProgNm) {
		this.nextProgNm = nextProgNm;
	}
	public Boolean getNextPList() {
		return nextPList;
	}
	public void setNextPList(Boolean nextPList) {
		this.nextPList = nextPList;
	}
	public String getMailToAreaDisplay() {
		return mailToAreaDisplay;
	}
	public void setMailToAreaDisplay(String mailToAreaDisplay) {
		this.mailToAreaDisplay = mailToAreaDisplay;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public Integer getCodeLgType() {
		return codeLgType;
	}
	public void setCodeLgType(Integer codeLgType) {
		this.codeLgType = codeLgType;
	}

	public Integer getNextCodeLgType() {
		return nextCodeLgType;
	}
	public void setNextCodeLgType(Integer nextCodeLgType) {
		this.nextCodeLgType = nextCodeLgType;
	}
	public Integer getCodeMailCategory() {
		return codeMailCategory;
	}
	public void setCodeMailCategory(Integer codeMailCategory) {
		this.codeMailCategory = codeMailCategory;
	}
	public Integer getNextCodeMailCategory() {
		return nextCodeMailCategory;
	}
	public void setNextCodeMailCategory(Integer nextCodeMailCategory) {
		this.nextCodeMailCategory = nextCodeMailCategory;
	}
	public Integer getCodeMailToPostoffice() {
		return codeMailToPostoffice;
	}
	public void setCodeMailToPostoffice(Integer codeMailToPostoffice) {
		this.codeMailToPostoffice = codeMailToPostoffice;
	}
	public Integer getNextCodeMailToPostoffice() {
		return nextCodeMailToPostoffice;
	}
	public void setNextCodeMailToPostoffice(Integer nextCodeMailToPostoffice) {
		this.nextCodeMailToPostoffice = nextCodeMailToPostoffice;
	}
	public Boolean getLgDisplayQty() {
		return lgDisplayQty;
	}
	public void setLgDisplayQty(Boolean lgDisplayQty) {
		this.lgDisplayQty = lgDisplayQty;
	}
	public Boolean getNextLgDisplayQty() {
		return nextLgDisplayQty;
	}
	public void setNextLgDisplayQty(Boolean nextLgDisplayQty) {
		this.nextLgDisplayQty = nextLgDisplayQty;
	}
	
	public Integer[] getCodeMailToArea() {
		return codeMailToArea;
	}
	public void setCodeMailToArea(Integer[] codeMailToArea) {
		this.codeMailToArea = codeMailToArea;
	}
	public Integer[] getNextCodeMailToArea() {
		return nextCodeMailToArea;
	}
	public void setNextCodeMailToArea(Integer[] nextCodeMailToArea) {
		this.nextCodeMailToArea = nextCodeMailToArea;
	}
	
	
	
}