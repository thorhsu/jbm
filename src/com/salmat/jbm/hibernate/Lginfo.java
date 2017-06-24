package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Lginfo entity. @author MyEclipse Persistence Tools
 */

public class Lginfo implements java.io.Serializable {

	// Fields

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
	private Boolean lgDisplayQty;
	private Boolean nextLgDisplayQty;
	
	private Set jobCodes = new HashSet(0);
	private Set jobBags = new HashSet(0);

	// Constructors

	/** default constructor */
	public Lginfo() {
	}

	/** minimal constructor */
	public Lginfo(String lgNo, Boolean PList) {
		this.lgNo = lgNo;
		this.PList = PList;
	}

	/** full constructor */
	public Lginfo(String lgNo, Code codeByNextCodeLgType,
			Code codeByCodeMailToArea1, Code codeByCodeMailCategory,
			Code codeByCodeMailToPostoffice, Code codeByNextCodeMailToArea1,
			Code codeByNextCodeMailToPostoffice,
			Code codeByNextCodeMailToArea5, Code codeByNextCodeMailCategory,
			Code codeByNextCodeMailToArea4, Code codeByNextCodeMailToArea3,
			Code codeByNextCodeMailToArea2, Code codeByNextCodeMailToArea6,
			Code codeByCodeLgType, Code codeByCodeMailToArea4,
			Code codeByCodeMailToArea5, Code codeByCodeMailToArea2,
			Code codeByCodeMailToArea3, Code codeByCodeMailToArea6,
			Customer customer, String progNm, String dispPosta, String PType,
			Boolean PList, String nextEffectiveDate, String nextProgNm,
			Boolean nextPList, String mailToAreaDisplay, 
			Boolean lgDisplayQty, Boolean nextLgDisplayQty,
			Set jobCodes,	Set jobBags) {
		this.lgNo = lgNo;
		this.codeByNextCodeLgType = codeByNextCodeLgType;
		this.codeByCodeMailToArea1 = codeByCodeMailToArea1;
		this.codeByCodeMailCategory = codeByCodeMailCategory;
		this.codeByCodeMailToPostoffice = codeByCodeMailToPostoffice;
		this.codeByNextCodeMailToArea1 = codeByNextCodeMailToArea1;
		this.codeByNextCodeMailToPostoffice = codeByNextCodeMailToPostoffice;
		this.codeByNextCodeMailToArea5 = codeByNextCodeMailToArea5;
		this.codeByNextCodeMailCategory = codeByNextCodeMailCategory;
		this.codeByNextCodeMailToArea4 = codeByNextCodeMailToArea4;
		this.codeByNextCodeMailToArea3 = codeByNextCodeMailToArea3;
		this.codeByNextCodeMailToArea2 = codeByNextCodeMailToArea2;
		this.codeByNextCodeMailToArea6 = codeByNextCodeMailToArea6;
		this.codeByCodeLgType = codeByCodeLgType;
		this.codeByCodeMailToArea4 = codeByCodeMailToArea4;
		this.codeByCodeMailToArea5 = codeByCodeMailToArea5;
		this.codeByCodeMailToArea2 = codeByCodeMailToArea2;
		this.codeByCodeMailToArea3 = codeByCodeMailToArea3;
		this.codeByCodeMailToArea6 = codeByCodeMailToArea6;
		this.customer = customer;
		this.progNm = progNm;
		this.dispPosta = dispPosta;
		this.PType = PType;
		this.PList = PList;
		this.nextEffectiveDate = nextEffectiveDate;
		this.nextProgNm = nextProgNm;
		this.nextPList = nextPList;
		this.mailToAreaDisplay = mailToAreaDisplay;
		this.lgDisplayQty = lgDisplayQty; 
		this.nextLgDisplayQty = nextLgDisplayQty;
		this.jobCodes = jobCodes;
		this.jobBags = jobBags;
	}

	// Property accessors

	public String getLgNo() {
		return this.lgNo;
	}

	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}

	public Code getCodeByNextCodeLgType() {
		return this.codeByNextCodeLgType;
	}

	public void setCodeByNextCodeLgType(Code codeByNextCodeLgType) {
		this.codeByNextCodeLgType = codeByNextCodeLgType;
	}

	public Code getCodeByCodeMailToArea1() {
		return this.codeByCodeMailToArea1;
	}

	public void setCodeByCodeMailToArea1(Code codeByCodeMailToArea1) {
		this.codeByCodeMailToArea1 = codeByCodeMailToArea1;
	}

	public Code getCodeByCodeMailCategory() {
		return this.codeByCodeMailCategory;
	}

	public void setCodeByCodeMailCategory(Code codeByCodeMailCategory) {
		this.codeByCodeMailCategory = codeByCodeMailCategory;
	}

	public Code getCodeByCodeMailToPostoffice() {
		return this.codeByCodeMailToPostoffice;
	}

	public void setCodeByCodeMailToPostoffice(Code codeByCodeMailToPostoffice) {
		this.codeByCodeMailToPostoffice = codeByCodeMailToPostoffice;
	}

	public Code getCodeByNextCodeMailToArea1() {
		return this.codeByNextCodeMailToArea1;
	}

	public void setCodeByNextCodeMailToArea1(Code codeByNextCodeMailToArea1) {
		this.codeByNextCodeMailToArea1 = codeByNextCodeMailToArea1;
	}

	public Code getCodeByNextCodeMailToPostoffice() {
		return this.codeByNextCodeMailToPostoffice;
	}

	public void setCodeByNextCodeMailToPostoffice(
			Code codeByNextCodeMailToPostoffice) {
		this.codeByNextCodeMailToPostoffice = codeByNextCodeMailToPostoffice;
	}

	public Code getCodeByNextCodeMailToArea5() {
		return this.codeByNextCodeMailToArea5;
	}

	public void setCodeByNextCodeMailToArea5(Code codeByNextCodeMailToArea5) {
		this.codeByNextCodeMailToArea5 = codeByNextCodeMailToArea5;
	}

	public Code getCodeByNextCodeMailCategory() {
		return this.codeByNextCodeMailCategory;
	}

	public void setCodeByNextCodeMailCategory(Code codeByNextCodeMailCategory) {
		this.codeByNextCodeMailCategory = codeByNextCodeMailCategory;
	}

	public Code getCodeByNextCodeMailToArea4() {
		return this.codeByNextCodeMailToArea4;
	}

	public void setCodeByNextCodeMailToArea4(Code codeByNextCodeMailToArea4) {
		this.codeByNextCodeMailToArea4 = codeByNextCodeMailToArea4;
	}

	public Code getCodeByNextCodeMailToArea3() {
		return this.codeByNextCodeMailToArea3;
	}

	public void setCodeByNextCodeMailToArea3(Code codeByNextCodeMailToArea3) {
		this.codeByNextCodeMailToArea3 = codeByNextCodeMailToArea3;
	}

	public Code getCodeByNextCodeMailToArea2() {
		return this.codeByNextCodeMailToArea2;
	}

	public void setCodeByNextCodeMailToArea2(Code codeByNextCodeMailToArea2) {
		this.codeByNextCodeMailToArea2 = codeByNextCodeMailToArea2;
	}

	public Code getCodeByNextCodeMailToArea6() {
		return this.codeByNextCodeMailToArea6;
	}

	public void setCodeByNextCodeMailToArea6(Code codeByNextCodeMailToArea6) {
		this.codeByNextCodeMailToArea6 = codeByNextCodeMailToArea6;
	}

	public Code getCodeByCodeLgType() {
		return this.codeByCodeLgType;
	}

	public void setCodeByCodeLgType(Code codeByCodeLgType) {
		this.codeByCodeLgType = codeByCodeLgType;
	}

	public Code getCodeByCodeMailToArea4() {
		return this.codeByCodeMailToArea4;
	}

	public void setCodeByCodeMailToArea4(Code codeByCodeMailToArea4) {
		this.codeByCodeMailToArea4 = codeByCodeMailToArea4;
	}

	public Code getCodeByCodeMailToArea5() {
		return this.codeByCodeMailToArea5;
	}

	public void setCodeByCodeMailToArea5(Code codeByCodeMailToArea5) {
		this.codeByCodeMailToArea5 = codeByCodeMailToArea5;
	}

	public Code getCodeByCodeMailToArea2() {
		return this.codeByCodeMailToArea2;
	}

	public void setCodeByCodeMailToArea2(Code codeByCodeMailToArea2) {
		this.codeByCodeMailToArea2 = codeByCodeMailToArea2;
	}

	public Code getCodeByCodeMailToArea3() {
		return this.codeByCodeMailToArea3;
	}

	public void setCodeByCodeMailToArea3(Code codeByCodeMailToArea3) {
		this.codeByCodeMailToArea3 = codeByCodeMailToArea3;
	}

	public Code getCodeByCodeMailToArea6() {
		return this.codeByCodeMailToArea6;
	}

	public void setCodeByCodeMailToArea6(Code codeByCodeMailToArea6) {
		this.codeByCodeMailToArea6 = codeByCodeMailToArea6;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getProgNm() {
		return this.progNm;
	}

	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}

	public String getDispPosta() {
		return this.dispPosta;
	}

	public void setDispPosta(String dispPosta) {
		this.dispPosta = dispPosta;
	}

	public String getPType() {
		return this.PType;
	}

	public void setPType(String PType) {
		this.PType = PType;
	}

	public Boolean getPList() {
		return this.PList;
	}

	public void setPList(Boolean PList) {
		this.PList = PList;
	}

	public String getNextEffectiveDate() {
		return this.nextEffectiveDate;
	}

	public void setNextEffectiveDate(String nextEffectiveDate) {
		this.nextEffectiveDate = nextEffectiveDate;
	}

	public String getNextProgNm() {
		return this.nextProgNm;
	}

	public void setNextProgNm(String nextProgNm) {
		this.nextProgNm = nextProgNm;
	}

	public Boolean getNextPList() {
		return this.nextPList;
	}

	public void setNextPList(Boolean nextPList) {
		this.nextPList = nextPList;
	}

	public String getMailToAreaDisplay() {
		return this.mailToAreaDisplay;
	}

	public void setMailToAreaDisplay(String mailToAreaDisplay) {
		this.mailToAreaDisplay = mailToAreaDisplay;
	}
	
	public Boolean getLgDisplayQty() {
		return lgDisplayQty;
	}

	public void setLgDisplayQty(Boolean lgDisplayQty) {
		this.lgDisplayQty = lgDisplayQty;
	}	
	
	public Set getJobCodes() {
		return this.jobCodes;
	}

	public void setJobCodes(Set jobCodes) {
		this.jobCodes = jobCodes;
	}

	public Set getJobBags() {
		return this.jobBags;
	}

	public void setJobBags(Set jobBags) {
		this.jobBags = jobBags;
	}
	public Boolean getNextLgDisplayQty() {
		return nextLgDisplayQty;
	}

	public void setNextLgDisplayQty(Boolean nextLgDisplayQty) {
		this.nextLgDisplayQty = nextLgDisplayQty;
	}
	

}