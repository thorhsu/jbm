package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * AcctDno entity. @author MyEclipse Persistence Tools
 */

public class AcctDno implements java.io.Serializable {

	// Fields

	private String debitNo;
	private Customer customer;
	private String section;
	private String desSdt;
	private String desEdt;
	private String processdt;
	private String processdt2;
	private String processdt3;
	private String inNo;
	private String inDt;
	private String inDuedt;
	private Double tax;
	private String refund;
	private Double refundamt;
	private Boolean ep1;
	private Boolean ep1Confirm;
	private String cycleSdt;
	private String cycleEdt;
	private Integer amountIncludeTax;
	private Integer amountExcludeTax;
	private Integer digitsLen;
	private String modifiedDate;
	private Boolean acctsShow;
	private Boolean pagesShow;
	private Boolean sheetsShow;
	private Short pageBreak  ;
	private Boolean displayOriAnymore;

	private Set acctSum2s = new HashSet(0);
	private Set acctSum3s = new HashSet(0);
	private Set acctSum1s = new HashSet(0);
	private Set acctInvoices = new HashSet(0);

	// Constructors

	/** default constructor */
	public AcctDno() {
	}

	/** minimal constructor */
	public AcctDno(String debitNo, Customer customer) {
		this.debitNo = debitNo;
		this.customer = customer;
	}

	/** full constructor */
	public AcctDno(String debitNo, Customer customer, String section,
			String desSdt, String desEdt, String processdt, String processdt2,
			String processdt3, String inNo, String inDt, String inDuedt,
			Double tax, String refund, Double refundamt, Boolean ep1,
			Boolean ep1Confirm, String cycleSdt, String cycleEdt,
			Integer amountIncludeTax, Integer amountExcludeTax, Boolean acctsShow, Boolean pagesShow, Boolean sheetsShow,
			Set acctSum2s,	Set acctSum3s, Set acctSum1s, Set acctInvoices) {
		this.debitNo = debitNo;
		this.customer = customer;
		this.section = section;
		this.desSdt = desSdt;
		this.desEdt = desEdt;
		this.processdt = processdt;
		this.processdt2 = processdt2;
		this.processdt3 = processdt3;
		this.inNo = inNo;
		this.inDt = inDt;
		this.inDuedt = inDuedt;
		this.tax = tax;
		this.refund = refund;
		this.refundamt = refundamt;
		this.ep1 = ep1;
		this.ep1Confirm = ep1Confirm;
		this.cycleSdt = cycleSdt;
		this.cycleEdt = cycleEdt;
		this.amountIncludeTax = amountIncludeTax;
		this.amountExcludeTax = amountExcludeTax;
		this.acctSum2s = acctSum2s;
		this.acctSum3s = acctSum3s;
		this.acctSum1s = acctSum1s;
		this.acctInvoices = acctInvoices;
		this.acctsShow = acctsShow;
		this.pagesShow = pagesShow;
		this.sheetsShow = sheetsShow;
	}

	// Property accessors

	public String getDebitNo() {
		return this.debitNo;
	}

	public void setDebitNo(String debitNo) {
		this.debitNo = debitNo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDesSdt() {
		return this.desSdt;
	}

	public void setDesSdt(String desSdt) {
		this.desSdt = desSdt;
	}

	public String getDesEdt() {
		return this.desEdt;
	}

	public void setDesEdt(String desEdt) {
		this.desEdt = desEdt;
	}

	public String getProcessdt() {
		return this.processdt;
	}

	public void setProcessdt(String processdt) {
		this.processdt = processdt;
	}

	public String getProcessdt2() {
		return this.processdt2;
	}

	public void setProcessdt2(String processdt2) {
		this.processdt2 = processdt2;
	}

	public String getProcessdt3() {
		return this.processdt3;
	}

	public void setProcessdt3(String processdt3) {
		this.processdt3 = processdt3;
	}

	public String getInNo() {
		return this.inNo;
	}

	public void setInNo(String inNo) {
		this.inNo = inNo;
	}

	public String getInDt() {
		return this.inDt;
	}

	public void setInDt(String inDt) {
		this.inDt = inDt;
	}

	public String getInDuedt() {
		return this.inDuedt;
	}

	public void setInDuedt(String inDuedt) {
		this.inDuedt = inDuedt;
	}

	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public String getRefund() {
		return this.refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

	public Double getRefundamt() {
		return this.refundamt;
	}

	public void setRefundamt(Double refundamt) {
		this.refundamt = refundamt;
	}

	public Boolean getEp1() {
		return this.ep1;
	}

	public void setEp1(Boolean ep1) {
		this.ep1 = ep1;
	}

	public Boolean getEp1Confirm() {
		return this.ep1Confirm;
	}

	public void setEp1Confirm(Boolean ep1Confirm) {
		this.ep1Confirm = ep1Confirm;
	}

	public String getCycleSdt() {
		return this.cycleSdt;
	}

	public void setCycleSdt(String cycleSdt) {
		this.cycleSdt = cycleSdt;
	}

	public String getCycleEdt() {
		return this.cycleEdt;
	}

	public void setCycleEdt(String cycleEdt) {
		this.cycleEdt = cycleEdt;
	}

	public Integer getAmountIncludeTax() {
		return this.amountIncludeTax;
	}

	public void setAmountIncludeTax(Integer amountIncludeTax) {
		this.amountIncludeTax = amountIncludeTax;
	}

	public Integer getAmountExcludeTax() {
		return this.amountExcludeTax;
	}

	public void setAmountExcludeTax(Integer amountExcludeTax) {
		this.amountExcludeTax = amountExcludeTax;
	}

	public Set getAcctSum2s() {
		return this.acctSum2s;
	}

	public void setAcctSum2s(Set acctSum2s) {
		this.acctSum2s = acctSum2s;
	}

	public Set getAcctSum3s() {
		return this.acctSum3s;
	}

	public void setAcctSum3s(Set acctSum3s) {
		this.acctSum3s = acctSum3s;
	}

	public Set getAcctSum1s() {
		return this.acctSum1s;
	}

	public void setAcctSum1s(Set acctSum1s) {
		this.acctSum1s = acctSum1s;
	}

	public Set getAcctInvoices() {
		return this.acctInvoices;
	}

	public void setAcctInvoices(Set acctInvoices) {
		this.acctInvoices = acctInvoices;
	}
	public Integer getDigitsLen() {
		return digitsLen;
	}

	public void setDigitsLen(Integer digitsLen) {
		this.digitsLen = digitsLen;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Boolean getAcctsShow() {
		//if(acctsShow == null)
			//return true;
		return acctsShow;
	}

	public void setAcctsShow(Boolean acctsShow) {
		this.acctsShow = acctsShow;
	}

	public Boolean getPagesShow() {
		//if(pagesShow == null)
			//return true;
		return pagesShow;
	}

	public void setPagesShow(Boolean pagesShow) {
		this.pagesShow = pagesShow;
	}

	public Boolean getSheetsShow() {
		//if(sheetsShow == null)
			//return true;
		return sheetsShow;
	}

	public void setSheetsShow(Boolean sheetsShow) {
		this.sheetsShow = sheetsShow;
	}
	public Short getPageBreak() {
		return pageBreak;
	}

	public void setPageBreak(Short pageBreak) {
		this.pageBreak = pageBreak;
	}

	public Boolean getDisplayOriAnymore() {
		return displayOriAnymore;
	}

	public void setDisplayOriAnymore(Boolean displayOriAnymore) {
		this.displayOriAnymore = displayOriAnymore;
	}

}