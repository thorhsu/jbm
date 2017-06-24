package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Lpinfo entity. @author MyEclipse Persistence Tools
 */

public class Lpinfo implements java.io.Serializable {

	// Fields

	private String lpNo;
	private Code codeByCodePrintType;
	private Code codeByCodePrinter;
	private Code codeByNextCodePrinter;
	private Code codeByNextCodePrintType;
	private Customer customer;
	private String printType;
	private String printSet;
	private double resolution;
	private double sugToner;
	private String remark;
	private double lpPrice;
	private String prodmemo;
	private Boolean printer;
	private String pcode1;
	private String pcode2;
	private String pcode3;
	private String pcode4;
	private String pcode5;
	private String pcode6;
	private String pcode7;
	private String pcode8;
	private String nextEffectiveDate;
	private String nextProdmemo;
	private String nextPcode1;
	private String nextPcode2;
	private String nextPcode3;
	private String nextPcode4;
	private String nextPcode5;
	private String nextPcode6;
	private String nextPcode7;
	private String nextPcode8;
	private String nextRemark;		
	private Set jobCodesForIdfLpNo1 = new HashSet(0);
	private Set jobCodesForIdfLpNo2 = new HashSet(0);
	private Set jobCodesForIdfLpNo3 = new HashSet(0);
	private Set jobBagsForIdfLpNo1 = new HashSet(0);
	private Set jobBagsForIdfLpNo2 = new HashSet(0);
	private Set jobBagsForIdfLpNo3 = new HashSet(0);
	

	// Constructors

	/** default constructor */
	public Lpinfo() {
	}

	/** minimal constructor */
	public Lpinfo(String lpNo) {
		this.lpNo = lpNo;
	}

	/** full constructor */
	public Lpinfo(String lpNo, Code codeByCodePrintType,
			Code codeByCodePrinter, Code codeByNextCodePrinter,
			Code codeByNextCodePrintType, Customer customer, String printType,
			String printSet, double resolution, double sugToner, String remark,
			double lpPrice, String prodmemo, Boolean printer, String pcode1,
			String pcode2, String pcode3, String pcode4,
			String pcode5, String pcode6, String pcode7, String pcode8,
			String nextEffectiveDate, String nextProdmemo, String nextPcode1,
			String nextPcode2, String nextPcode3, String nextPcode4,
			String nextRemark, Set jobCodesForIdfLpNo2, Set jobBagsForIdfLpNo1,
			Set jobCodesForIdfLpNo1, Set jobCodesForIdfLpNo3,
			Set jobBagsForIdfLpNo3, Set jobBagsForIdfLpNo2) {
		this.lpNo = lpNo;
		this.codeByCodePrintType = codeByCodePrintType;
		this.codeByCodePrinter = codeByCodePrinter;
		this.codeByNextCodePrinter = codeByNextCodePrinter;
		this.codeByNextCodePrintType = codeByNextCodePrintType;
		this.customer = customer;
		this.printType = printType;
		this.printSet = printSet;
		this.resolution = resolution;
		this.sugToner = sugToner;
		this.remark = remark;
		this.lpPrice = lpPrice;
		this.prodmemo = prodmemo;
		this.printer = printer;
		this.pcode1 = pcode1;
		this.pcode2 = pcode2;
		this.pcode3 = pcode3;
		this.pcode4 = pcode4;
		this.pcode5 = pcode5;
		this.pcode6 = pcode6;
		this.pcode7 = pcode7;
		this.pcode8 = pcode8;
		this.nextEffectiveDate = nextEffectiveDate;
		this.nextProdmemo = nextProdmemo;
		this.nextPcode1 = nextPcode1;
		this.nextPcode2 = nextPcode2;
		this.nextPcode3 = nextPcode3;
		this.nextPcode4 = nextPcode4;
		this.nextRemark = nextRemark;
		this.jobCodesForIdfLpNo2 = jobCodesForIdfLpNo2;
		this.jobBagsForIdfLpNo1 = jobBagsForIdfLpNo1;
		this.jobCodesForIdfLpNo1 = jobCodesForIdfLpNo1;
		this.jobCodesForIdfLpNo3 = jobCodesForIdfLpNo3;
		this.jobBagsForIdfLpNo3 = jobBagsForIdfLpNo3;
		this.jobBagsForIdfLpNo2 = jobBagsForIdfLpNo2;
	}

	// Property accessors

	public String getLpNo() {
		return this.lpNo;
	}

	public void setLpNo(String lpNo) {
		this.lpNo = lpNo;
	}

	public Code getCodeByCodePrintType() {
		return this.codeByCodePrintType;
	}

	public void setCodeByCodePrintType(Code codeByCodePrintType) {
		this.codeByCodePrintType = codeByCodePrintType;
	}

	public Code getCodeByCodePrinter() {
		return this.codeByCodePrinter;
	}

	public void setCodeByCodePrinter(Code codeByCodePrinter) {
		this.codeByCodePrinter = codeByCodePrinter;
	}

	public Code getCodeByNextCodePrinter() {
		return this.codeByNextCodePrinter;
	}

	public void setCodeByNextCodePrinter(Code codeByNextCodePrinter) {
		this.codeByNextCodePrinter = codeByNextCodePrinter;
	}

	public Code getCodeByNextCodePrintType() {
		return this.codeByNextCodePrintType;
	}

	public void setCodeByNextCodePrintType(Code codeByNextCodePrintType) {
		this.codeByNextCodePrintType = codeByNextCodePrintType;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPrintType() {
		return this.printType;
	}

	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getPrintSet() {
		return this.printSet;
	}

	public void setPrintSet(String printSet) {
		this.printSet = printSet;
	}

	public double getResolution() {
		return this.resolution;
	}

	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	public double getSugToner() {
		return this.sugToner;
	}

	public void setSugToner(double sugToner) {
		this.sugToner = sugToner;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getLpPrice() {
		return this.lpPrice;
	}

	public void setLpPrice(double lpPrice) {
		this.lpPrice = lpPrice;
	}

	public String getProdmemo() {
		return this.prodmemo;
	}

	public void setProdmemo(String prodmemo) {
		this.prodmemo = prodmemo;
	}

	public Boolean getPrinter() {
		return this.printer;
	}

	public void setPrinter(Boolean printer) {
		this.printer = printer;
	}

	public String getPcode1() {
		return this.pcode1;
	}

	public void setPcode1(String pcode1) {
		this.pcode1 = pcode1;
	}

	public String getPcode2() {
		return this.pcode2;
	}

	public void setPcode2(String pcode2) {
		this.pcode2 = pcode2;
	}

	public String getPcode3() {
		return this.pcode3;
	}

	public void setPcode3(String pcode3) {
		this.pcode3 = pcode3;
	}

	public String getPcode4() {
		return this.pcode4;
	}

	public void setPcode4(String pcode4) {
		this.pcode4 = pcode4;
	}

	public String getNextEffectiveDate() {
		return this.nextEffectiveDate;
	}

	public void setNextEffectiveDate(String nextEffectiveDate) {
		this.nextEffectiveDate = nextEffectiveDate;
	}

	public String getNextProdmemo() {
		return this.nextProdmemo;
	}

	public void setNextProdmemo(String nextProdmemo) {
		this.nextProdmemo = nextProdmemo;
	}

	public String getNextPcode1() {
		return this.nextPcode1;
	}

	public void setNextPcode1(String nextPcode1) {
		this.nextPcode1 = nextPcode1;
	}

	public String getNextPcode2() {
		return this.nextPcode2;
	}

	public void setNextPcode2(String nextPcode2) {
		this.nextPcode2 = nextPcode2;
	}

	public String getNextPcode3() {
		return this.nextPcode3;
	}

	public void setNextPcode3(String nextPcode3) {
		this.nextPcode3 = nextPcode3;
	}

	public String getNextPcode4() {
		return this.nextPcode4;
	}

	public void setNextPcode4(String nextPcode4) {
		this.nextPcode4 = nextPcode4;
	}

	public String getNextRemark() {
		return this.nextRemark;
	}

	public void setNextRemark(String nextRemark) {
		this.nextRemark = nextRemark;
	}

	public Set getJobCodesForIdfLpNo2() {
		return this.jobCodesForIdfLpNo2;
	}

	public void setJobCodesForIdfLpNo2(Set jobCodesForIdfLpNo2) {
		this.jobCodesForIdfLpNo2 = jobCodesForIdfLpNo2;
	}

	public Set getJobBagsForIdfLpNo1() {
		return this.jobBagsForIdfLpNo1;
	}

	public void setJobBagsForIdfLpNo1(Set jobBagsForIdfLpNo1) {
		this.jobBagsForIdfLpNo1 = jobBagsForIdfLpNo1;
	}

	public Set getJobCodesForIdfLpNo1() {
		return this.jobCodesForIdfLpNo1;
	}

	public void setJobCodesForIdfLpNo1(Set jobCodesForIdfLpNo1) {
		this.jobCodesForIdfLpNo1 = jobCodesForIdfLpNo1;
	}

	public Set getJobCodesForIdfLpNo3() {
		return this.jobCodesForIdfLpNo3;
	}

	public void setJobCodesForIdfLpNo3(Set jobCodesForIdfLpNo3) {
		this.jobCodesForIdfLpNo3 = jobCodesForIdfLpNo3;
	}

	public Set getJobBagsForIdfLpNo3() {
		return this.jobBagsForIdfLpNo3;
	}

	public void setJobBagsForIdfLpNo3(Set jobBagsForIdfLpNo3) {
		this.jobBagsForIdfLpNo3 = jobBagsForIdfLpNo3;
	}

	public Set getJobBagsForIdfLpNo2() {
		return this.jobBagsForIdfLpNo2;
	}

	public void setJobBagsForIdfLpNo2(Set jobBagsForIdfLpNo2) {
		this.jobBagsForIdfLpNo2 = jobBagsForIdfLpNo2;
	}

	public String getPcode5() {
		return pcode5;
	}

	public void setPcode5(String pcode5) {
		this.pcode5 = pcode5;
	}

	public String getPcode6() {
		return pcode6;
	}

	public void setPcode6(String pcode6) {
		this.pcode6 = pcode6;
	}

	public String getPcode7() {
		return pcode7;
	}

	public void setPcode7(String pcode7) {
		this.pcode7 = pcode7;
	}

	public String getPcode8() {
		return pcode8;
	}

	public void setPcode8(String pcode8) {
		this.pcode8 = pcode8;
	}

	public String getNextPcode5() {
		return nextPcode5;
	}

	public void setNextPcode5(String nextPcode5) {
		this.nextPcode5 = nextPcode5;
	}

	public String getNextPcode6() {
		return nextPcode6;
	}

	public void setNextPcode6(String nextPcode6) {
		this.nextPcode6 = nextPcode6;
	}

	public String getNextPcode7() {
		return nextPcode7;
	}

	public void setNextPcode7(String nextPcode7) {
		this.nextPcode7 = nextPcode7;
	}

	public String getNextPcode8() {
		return nextPcode8;
	}

	public void setNextPcode8(String nextPcode8) {
		this.nextPcode8 = nextPcode8;
	}
	

}