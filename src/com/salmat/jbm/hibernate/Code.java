package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Code entity. @author MyEclipse Persistence Tools
 */

public class Code implements java.io.Serializable {

	// Fields

	private Integer id;
	private String codeTypeName;
	private String codeTypeNameTw;
	private String codeKey;
	private String codeValueTw;
	private String codeValueEn;
	private String codeValueCn;
	private Integer orderbyId;
	private Boolean isHidden;
	private String f1;
	private String f2;
	private String f3;
	private Set jobBagsForLgCodeMailToArea6 = new HashSet(0);
	private Set jobCodesForCodeMailCategory = new HashSet(0);
	private Set jobBagsForCodeLgType = new HashSet(0);
	private Set jobCodesForCodeLgType = new HashSet(0);
	private Set jobBagsForLpCodePrintType = new HashSet(0);
	private Set lginfosForNextCodeLgType = new HashSet(0);
	private Set jobCodesForCodeJobCodeType = new HashSet(0);
	private Set jobBagsForPsCodePsType = new HashSet(0);
	private Set lginfosForNextCodeMailToPostoffice = new HashSet(0);
	private Set acctChargeItemsForTitle = new HashSet(0);
	private Set jobBagsForLpCodePrinter = new HashSet(0);
	private Set lpinfosForCodePrintType = new HashSet(0);
	private Set psinfosForCodePsType = new HashSet(0);
	private Set lginfosForNextCodeMailCategory = new HashSet(0);
	private Set lginfosForCodeMailCategory = new HashSet(0);
	private Set jobBagsForCodeMailCategory = new HashSet(0);
	private Set lginfosForCodeMailToPostoffice = new HashSet(0);
	private Set lginfosForCodeMailToArea2 = new HashSet(0);
	private Set lginfosForCodeMailToArea1 = new HashSet(0);
	private Set lginfosForCodeMailToArea6 = new HashSet(0);
	private Set jobBagsForCodeMailToPostoffice = new HashSet(0);
	private Set lginfosForCodeMailToArea5 = new HashSet(0);
	private Set lginfosForCodeMailToArea4 = new HashSet(0);
	private Set lginfosForCodeMailToArea3 = new HashSet(0);
	private Set acctChargeItemsForTitle_1 = new HashSet(0);
	private Set acctChargeItemsForSubTitle = new HashSet(0);
	private Set employees = new HashSet(0);
	private Set jobCodesForCodeMailToPostoffice = new HashSet(0);
	private Set lpinfosForNextCodePrintType = new HashSet(0);
	private Set lginfosForNextCodeMailToArea4 = new HashSet(0);
	private Set jobBagsForLgCodeMailToArea5 = new HashSet(0);
	private Set lpinfosForNextCodePrinter = new HashSet(0);
	private Set lginfosForNextCodeMailToArea3 = new HashSet(0);
	private Set jobBagsForLgCodeMailToArea4 = new HashSet(0);
	private Set jobBagsForCodeJobCodeType = new HashSet(0);
	private Set psinfosForNextCodePsType = new HashSet(0);
	private Set lginfosForNextCodeMailToArea6 = new HashSet(0);
	private Set jobBagsForLgCodeMailToArea3 = new HashSet(0);
	private Set lpinfosForCodePrinter = new HashSet(0);
	private Set lginfosForNextCodeMailToArea5 = new HashSet(0);
	private Set jobBagsForLgCodeMailToArea2 = new HashSet(0);
	private Set jobBagsForLgCodeMailToArea1 = new HashSet(0);
	private Set lginfosForNextCodeMailToArea2 = new HashSet(0);
	private Set lginfosForNextCodeMailToArea1 = new HashSet(0);
	private Set lginfosForCodeLgType = new HashSet(0);

	// Constructors

	/** default constructor */
	public Code() {
	}

	/** full constructor */
	public Code(String codeTypeName, String codeTypeNameTw, String codeKey,
			String codeValueTw, String codeValueEn, String codeValueCn,
			Integer orderbyId, Boolean isHidden, String f1, String f2,
			String f3, Set jobBagsForLgCodeMailToArea6,
			Set jobCodesForCodeMailCategory, Set jobBagsForCodeLgType,
			Set jobCodesForCodeLgType, Set jobBagsForLpCodePrintType,
			Set lginfosForNextCodeLgType, Set jobCodesForCodeJobCodeType,
			Set jobBagsForPsCodePsType, Set lginfosForNextCodeMailToPostoffice,
			Set acctChargeItemsForTitle, Set jobBagsForLpCodePrinter,
			Set lpinfosForCodePrintType, Set psinfosForCodePsType,
			Set lginfosForNextCodeMailCategory, Set lginfosForCodeMailCategory,
			Set jobBagsForCodeMailCategory, Set lginfosForCodeMailToPostoffice,
			Set lginfosForCodeMailToArea2, Set lginfosForCodeMailToArea1,
			Set lginfosForCodeMailToArea6, Set jobBagsForCodeMailToPostoffice,
			Set lginfosForCodeMailToArea5, Set lginfosForCodeMailToArea4,
			Set lginfosForCodeMailToArea3, Set acctChargeItemsForTitle_1,
			Set acctChargeItemsForSubTitle, Set employees,
			Set jobCodesForCodeMailToPostoffice,
			Set lpinfosForNextCodePrintType, Set lginfosForNextCodeMailToArea4,
			Set jobBagsForLgCodeMailToArea5, Set lpinfosForNextCodePrinter,
			Set lginfosForNextCodeMailToArea3, Set jobBagsForLgCodeMailToArea4,
			Set jobBagsForCodeJobCodeType, Set psinfosForNextCodePsType,
			Set lginfosForNextCodeMailToArea6, Set jobBagsForLgCodeMailToArea3,
			Set lpinfosForCodePrinter, Set lginfosForNextCodeMailToArea5,
			Set jobBagsForLgCodeMailToArea2, Set jobBagsForLgCodeMailToArea1,
			Set lginfosForNextCodeMailToArea2,
			Set lginfosForNextCodeMailToArea1, Set lginfosForCodeLgType) {
		this.codeTypeName = codeTypeName;
		this.codeTypeNameTw = codeTypeNameTw;
		this.codeKey = codeKey;
		this.codeValueTw = codeValueTw;
		this.codeValueEn = codeValueEn;
		this.codeValueCn = codeValueCn;
		this.orderbyId = orderbyId;
		this.isHidden = isHidden;
		this.f1 = f1;
		this.f2 = f2;
		this.f3 = f3;
		this.jobBagsForLgCodeMailToArea6 = jobBagsForLgCodeMailToArea6;
		this.jobCodesForCodeMailCategory = jobCodesForCodeMailCategory;
		this.jobBagsForCodeLgType = jobBagsForCodeLgType;
		this.jobCodesForCodeLgType = jobCodesForCodeLgType;
		this.jobBagsForLpCodePrintType = jobBagsForLpCodePrintType;
		this.lginfosForNextCodeLgType = lginfosForNextCodeLgType;
		this.jobCodesForCodeJobCodeType = jobCodesForCodeJobCodeType;
		this.jobBagsForPsCodePsType = jobBagsForPsCodePsType;
		this.lginfosForNextCodeMailToPostoffice = lginfosForNextCodeMailToPostoffice;
		this.acctChargeItemsForTitle = acctChargeItemsForTitle;
		this.jobBagsForLpCodePrinter = jobBagsForLpCodePrinter;
		this.lpinfosForCodePrintType = lpinfosForCodePrintType;
		this.psinfosForCodePsType = psinfosForCodePsType;
		this.lginfosForNextCodeMailCategory = lginfosForNextCodeMailCategory;
		this.lginfosForCodeMailCategory = lginfosForCodeMailCategory;
		this.jobBagsForCodeMailCategory = jobBagsForCodeMailCategory;
		this.lginfosForCodeMailToPostoffice = lginfosForCodeMailToPostoffice;
		this.lginfosForCodeMailToArea2 = lginfosForCodeMailToArea2;
		this.lginfosForCodeMailToArea1 = lginfosForCodeMailToArea1;
		this.lginfosForCodeMailToArea6 = lginfosForCodeMailToArea6;
		this.jobBagsForCodeMailToPostoffice = jobBagsForCodeMailToPostoffice;
		this.lginfosForCodeMailToArea5 = lginfosForCodeMailToArea5;
		this.lginfosForCodeMailToArea4 = lginfosForCodeMailToArea4;
		this.lginfosForCodeMailToArea3 = lginfosForCodeMailToArea3;
		this.acctChargeItemsForTitle_1 = acctChargeItemsForTitle_1;
		this.acctChargeItemsForSubTitle = acctChargeItemsForSubTitle;
		this.employees = employees;
		this.jobCodesForCodeMailToPostoffice = jobCodesForCodeMailToPostoffice;
		this.lpinfosForNextCodePrintType = lpinfosForNextCodePrintType;
		this.lginfosForNextCodeMailToArea4 = lginfosForNextCodeMailToArea4;
		this.jobBagsForLgCodeMailToArea5 = jobBagsForLgCodeMailToArea5;
		this.lpinfosForNextCodePrinter = lpinfosForNextCodePrinter;
		this.lginfosForNextCodeMailToArea3 = lginfosForNextCodeMailToArea3;
		this.jobBagsForLgCodeMailToArea4 = jobBagsForLgCodeMailToArea4;
		this.jobBagsForCodeJobCodeType = jobBagsForCodeJobCodeType;
		this.psinfosForNextCodePsType = psinfosForNextCodePsType;
		this.lginfosForNextCodeMailToArea6 = lginfosForNextCodeMailToArea6;
		this.jobBagsForLgCodeMailToArea3 = jobBagsForLgCodeMailToArea3;
		this.lpinfosForCodePrinter = lpinfosForCodePrinter;
		this.lginfosForNextCodeMailToArea5 = lginfosForNextCodeMailToArea5;
		this.jobBagsForLgCodeMailToArea2 = jobBagsForLgCodeMailToArea2;
		this.jobBagsForLgCodeMailToArea1 = jobBagsForLgCodeMailToArea1;
		this.lginfosForNextCodeMailToArea2 = lginfosForNextCodeMailToArea2;
		this.lginfosForNextCodeMailToArea1 = lginfosForNextCodeMailToArea1;
		this.lginfosForCodeLgType = lginfosForCodeLgType;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeTypeName() {
		return this.codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}

	public String getCodeTypeNameTw() {
		return this.codeTypeNameTw;
	}

	public void setCodeTypeNameTw(String codeTypeNameTw) {
		this.codeTypeNameTw = codeTypeNameTw;
	}

	public String getCodeKey() {
		return this.codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public String getCodeValueTw() {
		return this.codeValueTw;
	}

	public void setCodeValueTw(String codeValueTw) {
		this.codeValueTw = codeValueTw;
	}

	public String getCodeValueEn() {
		return this.codeValueEn;
	}

	public void setCodeValueEn(String codeValueEn) {
		this.codeValueEn = codeValueEn;
	}

	public String getCodeValueCn() {
		return this.codeValueCn;
	}

	public void setCodeValueCn(String codeValueCn) {
		this.codeValueCn = codeValueCn;
	}

	public Integer getOrderbyId() {
		return this.orderbyId;
	}

	public void setOrderbyId(Integer orderbyId) {
		this.orderbyId = orderbyId;
	}

	public Boolean getIsHidden() {
		return this.isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public String getF1() {
		return this.f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public String getF2() {
		return this.f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}

	public String getF3() {
		return this.f3;
	}

	public void setF3(String f3) {
		this.f3 = f3;
	}

	public Set getJobBagsForLgCodeMailToArea6() {
		return this.jobBagsForLgCodeMailToArea6;
	}

	public void setJobBagsForLgCodeMailToArea6(Set jobBagsForLgCodeMailToArea6) {
		this.jobBagsForLgCodeMailToArea6 = jobBagsForLgCodeMailToArea6;
	}

	public Set getJobCodesForCodeMailCategory() {
		return this.jobCodesForCodeMailCategory;
	}

	public void setJobCodesForCodeMailCategory(Set jobCodesForCodeMailCategory) {
		this.jobCodesForCodeMailCategory = jobCodesForCodeMailCategory;
	}

	public Set getJobBagsForCodeLgType() {
		return this.jobBagsForCodeLgType;
	}

	public void setJobBagsForCodeLgType(Set jobBagsForCodeLgType) {
		this.jobBagsForCodeLgType = jobBagsForCodeLgType;
	}

	public Set getJobCodesForCodeLgType() {
		return this.jobCodesForCodeLgType;
	}

	public void setJobCodesForCodeLgType(Set jobCodesForCodeLgType) {
		this.jobCodesForCodeLgType = jobCodesForCodeLgType;
	}

	public Set getJobBagsForLpCodePrintType() {
		return this.jobBagsForLpCodePrintType;
	}

	public void setJobBagsForLpCodePrintType(Set jobBagsForLpCodePrintType) {
		this.jobBagsForLpCodePrintType = jobBagsForLpCodePrintType;
	}

	public Set getLginfosForNextCodeLgType() {
		return this.lginfosForNextCodeLgType;
	}

	public void setLginfosForNextCodeLgType(Set lginfosForNextCodeLgType) {
		this.lginfosForNextCodeLgType = lginfosForNextCodeLgType;
	}

	public Set getJobCodesForCodeJobCodeType() {
		return this.jobCodesForCodeJobCodeType;
	}

	public void setJobCodesForCodeJobCodeType(Set jobCodesForCodeJobCodeType) {
		this.jobCodesForCodeJobCodeType = jobCodesForCodeJobCodeType;
	}

	public Set getJobBagsForPsCodePsType() {
		return this.jobBagsForPsCodePsType;
	}

	public void setJobBagsForPsCodePsType(Set jobBagsForPsCodePsType) {
		this.jobBagsForPsCodePsType = jobBagsForPsCodePsType;
	}

	public Set getLginfosForNextCodeMailToPostoffice() {
		return this.lginfosForNextCodeMailToPostoffice;
	}

	public void setLginfosForNextCodeMailToPostoffice(
			Set lginfosForNextCodeMailToPostoffice) {
		this.lginfosForNextCodeMailToPostoffice = lginfosForNextCodeMailToPostoffice;
	}

	public Set getAcctChargeItemsForTitle() {
		return this.acctChargeItemsForTitle;
	}

	public void setAcctChargeItemsForTitle(Set acctChargeItemsForTitle) {
		this.acctChargeItemsForTitle = acctChargeItemsForTitle;
	}

	public Set getJobBagsForLpCodePrinter() {
		return this.jobBagsForLpCodePrinter;
	}

	public void setJobBagsForLpCodePrinter(Set jobBagsForLpCodePrinter) {
		this.jobBagsForLpCodePrinter = jobBagsForLpCodePrinter;
	}

	public Set getLpinfosForCodePrintType() {
		return this.lpinfosForCodePrintType;
	}

	public void setLpinfosForCodePrintType(Set lpinfosForCodePrintType) {
		this.lpinfosForCodePrintType = lpinfosForCodePrintType;
	}

	public Set getPsinfosForCodePsType() {
		return this.psinfosForCodePsType;
	}

	public void setPsinfosForCodePsType(Set psinfosForCodePsType) {
		this.psinfosForCodePsType = psinfosForCodePsType;
	}

	public Set getLginfosForNextCodeMailCategory() {
		return this.lginfosForNextCodeMailCategory;
	}

	public void setLginfosForNextCodeMailCategory(
			Set lginfosForNextCodeMailCategory) {
		this.lginfosForNextCodeMailCategory = lginfosForNextCodeMailCategory;
	}

	public Set getLginfosForCodeMailCategory() {
		return this.lginfosForCodeMailCategory;
	}

	public void setLginfosForCodeMailCategory(Set lginfosForCodeMailCategory) {
		this.lginfosForCodeMailCategory = lginfosForCodeMailCategory;
	}

	public Set getJobBagsForCodeMailCategory() {
		return this.jobBagsForCodeMailCategory;
	}

	public void setJobBagsForCodeMailCategory(Set jobBagsForCodeMailCategory) {
		this.jobBagsForCodeMailCategory = jobBagsForCodeMailCategory;
	}

	public Set getLginfosForCodeMailToPostoffice() {
		return this.lginfosForCodeMailToPostoffice;
	}

	public void setLginfosForCodeMailToPostoffice(
			Set lginfosForCodeMailToPostoffice) {
		this.lginfosForCodeMailToPostoffice = lginfosForCodeMailToPostoffice;
	}

	public Set getLginfosForCodeMailToArea2() {
		return this.lginfosForCodeMailToArea2;
	}

	public void setLginfosForCodeMailToArea2(Set lginfosForCodeMailToArea2) {
		this.lginfosForCodeMailToArea2 = lginfosForCodeMailToArea2;
	}

	public Set getLginfosForCodeMailToArea1() {
		return this.lginfosForCodeMailToArea1;
	}

	public void setLginfosForCodeMailToArea1(Set lginfosForCodeMailToArea1) {
		this.lginfosForCodeMailToArea1 = lginfosForCodeMailToArea1;
	}

	public Set getLginfosForCodeMailToArea6() {
		return this.lginfosForCodeMailToArea6;
	}

	public void setLginfosForCodeMailToArea6(Set lginfosForCodeMailToArea6) {
		this.lginfosForCodeMailToArea6 = lginfosForCodeMailToArea6;
	}

	public Set getJobBagsForCodeMailToPostoffice() {
		return this.jobBagsForCodeMailToPostoffice;
	}

	public void setJobBagsForCodeMailToPostoffice(
			Set jobBagsForCodeMailToPostoffice) {
		this.jobBagsForCodeMailToPostoffice = jobBagsForCodeMailToPostoffice;
	}

	public Set getLginfosForCodeMailToArea5() {
		return this.lginfosForCodeMailToArea5;
	}

	public void setLginfosForCodeMailToArea5(Set lginfosForCodeMailToArea5) {
		this.lginfosForCodeMailToArea5 = lginfosForCodeMailToArea5;
	}

	public Set getLginfosForCodeMailToArea4() {
		return this.lginfosForCodeMailToArea4;
	}

	public void setLginfosForCodeMailToArea4(Set lginfosForCodeMailToArea4) {
		this.lginfosForCodeMailToArea4 = lginfosForCodeMailToArea4;
	}

	public Set getLginfosForCodeMailToArea3() {
		return this.lginfosForCodeMailToArea3;
	}

	public void setLginfosForCodeMailToArea3(Set lginfosForCodeMailToArea3) {
		this.lginfosForCodeMailToArea3 = lginfosForCodeMailToArea3;
	}

	public Set getAcctChargeItemsForTitle_1() {
		return this.acctChargeItemsForTitle_1;
	}

	public void setAcctChargeItemsForTitle_1(Set acctChargeItemsForTitle_1) {
		this.acctChargeItemsForTitle_1 = acctChargeItemsForTitle_1;
	}

	public Set getAcctChargeItemsForSubTitle() {
		return this.acctChargeItemsForSubTitle;
	}

	public void setAcctChargeItemsForSubTitle(Set acctChargeItemsForSubTitle) {
		this.acctChargeItemsForSubTitle = acctChargeItemsForSubTitle;
	}

	public Set getEmployees() {
		return this.employees;
	}

	public void setEmployees(Set employees) {
		this.employees = employees;
	}

	public Set getJobCodesForCodeMailToPostoffice() {
		return this.jobCodesForCodeMailToPostoffice;
	}

	public void setJobCodesForCodeMailToPostoffice(
			Set jobCodesForCodeMailToPostoffice) {
		this.jobCodesForCodeMailToPostoffice = jobCodesForCodeMailToPostoffice;
	}

	public Set getLpinfosForNextCodePrintType() {
		return this.lpinfosForNextCodePrintType;
	}

	public void setLpinfosForNextCodePrintType(Set lpinfosForNextCodePrintType) {
		this.lpinfosForNextCodePrintType = lpinfosForNextCodePrintType;
	}

	public Set getLginfosForNextCodeMailToArea4() {
		return this.lginfosForNextCodeMailToArea4;
	}

	public void setLginfosForNextCodeMailToArea4(
			Set lginfosForNextCodeMailToArea4) {
		this.lginfosForNextCodeMailToArea4 = lginfosForNextCodeMailToArea4;
	}

	public Set getJobBagsForLgCodeMailToArea5() {
		return this.jobBagsForLgCodeMailToArea5;
	}

	public void setJobBagsForLgCodeMailToArea5(Set jobBagsForLgCodeMailToArea5) {
		this.jobBagsForLgCodeMailToArea5 = jobBagsForLgCodeMailToArea5;
	}

	public Set getLpinfosForNextCodePrinter() {
		return this.lpinfosForNextCodePrinter;
	}

	public void setLpinfosForNextCodePrinter(Set lpinfosForNextCodePrinter) {
		this.lpinfosForNextCodePrinter = lpinfosForNextCodePrinter;
	}

	public Set getLginfosForNextCodeMailToArea3() {
		return this.lginfosForNextCodeMailToArea3;
	}

	public void setLginfosForNextCodeMailToArea3(
			Set lginfosForNextCodeMailToArea3) {
		this.lginfosForNextCodeMailToArea3 = lginfosForNextCodeMailToArea3;
	}

	public Set getJobBagsForLgCodeMailToArea4() {
		return this.jobBagsForLgCodeMailToArea4;
	}

	public void setJobBagsForLgCodeMailToArea4(Set jobBagsForLgCodeMailToArea4) {
		this.jobBagsForLgCodeMailToArea4 = jobBagsForLgCodeMailToArea4;
	}

	public Set getJobBagsForCodeJobCodeType() {
		return this.jobBagsForCodeJobCodeType;
	}

	public void setJobBagsForCodeJobCodeType(Set jobBagsForCodeJobCodeType) {
		this.jobBagsForCodeJobCodeType = jobBagsForCodeJobCodeType;
	}

	public Set getPsinfosForNextCodePsType() {
		return this.psinfosForNextCodePsType;
	}

	public void setPsinfosForNextCodePsType(Set psinfosForNextCodePsType) {
		this.psinfosForNextCodePsType = psinfosForNextCodePsType;
	}

	public Set getLginfosForNextCodeMailToArea6() {
		return this.lginfosForNextCodeMailToArea6;
	}

	public void setLginfosForNextCodeMailToArea6(
			Set lginfosForNextCodeMailToArea6) {
		this.lginfosForNextCodeMailToArea6 = lginfosForNextCodeMailToArea6;
	}

	public Set getJobBagsForLgCodeMailToArea3() {
		return this.jobBagsForLgCodeMailToArea3;
	}

	public void setJobBagsForLgCodeMailToArea3(Set jobBagsForLgCodeMailToArea3) {
		this.jobBagsForLgCodeMailToArea3 = jobBagsForLgCodeMailToArea3;
	}

	public Set getLpinfosForCodePrinter() {
		return this.lpinfosForCodePrinter;
	}

	public void setLpinfosForCodePrinter(Set lpinfosForCodePrinter) {
		this.lpinfosForCodePrinter = lpinfosForCodePrinter;
	}

	public Set getLginfosForNextCodeMailToArea5() {
		return this.lginfosForNextCodeMailToArea5;
	}

	public void setLginfosForNextCodeMailToArea5(
			Set lginfosForNextCodeMailToArea5) {
		this.lginfosForNextCodeMailToArea5 = lginfosForNextCodeMailToArea5;
	}

	public Set getJobBagsForLgCodeMailToArea2() {
		return this.jobBagsForLgCodeMailToArea2;
	}

	public void setJobBagsForLgCodeMailToArea2(Set jobBagsForLgCodeMailToArea2) {
		this.jobBagsForLgCodeMailToArea2 = jobBagsForLgCodeMailToArea2;
	}

	public Set getJobBagsForLgCodeMailToArea1() {
		return this.jobBagsForLgCodeMailToArea1;
	}

	public void setJobBagsForLgCodeMailToArea1(Set jobBagsForLgCodeMailToArea1) {
		this.jobBagsForLgCodeMailToArea1 = jobBagsForLgCodeMailToArea1;
	}

	public Set getLginfosForNextCodeMailToArea2() {
		return this.lginfosForNextCodeMailToArea2;
	}

	public void setLginfosForNextCodeMailToArea2(
			Set lginfosForNextCodeMailToArea2) {
		this.lginfosForNextCodeMailToArea2 = lginfosForNextCodeMailToArea2;
	}

	public Set getLginfosForNextCodeMailToArea1() {
		return this.lginfosForNextCodeMailToArea1;
	}

	public void setLginfosForNextCodeMailToArea1(
			Set lginfosForNextCodeMailToArea1) {
		this.lginfosForNextCodeMailToArea1 = lginfosForNextCodeMailToArea1;
	}

	public Set getLginfosForCodeLgType() {
		return this.lginfosForCodeLgType;
	}

	public void setLginfosForCodeLgType(Set lginfosForCodeLgType) {
		this.lginfosForCodeLgType = lginfosForCodeLgType;
	}

}