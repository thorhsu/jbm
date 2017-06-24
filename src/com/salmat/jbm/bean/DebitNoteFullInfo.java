package com.salmat.jbm.bean;

import java.util.Date;
import java.util.List;

import com.salmat.jbm.hibernate.Customer;

public class DebitNoteFullInfo {
	private String debitNo;
	private String custNo;
	private String section;
	private String desSdt;
	private String desEdt;
	private String cycleSdt;
	private String cycleEdt;	
	private String processdt;
	private String processdt2;
	private String processdt3;
	private String inNo;
	private String inDt;
	private String inDuedt;
	private Integer amountIncludeTax =0;  //含稅金額
	private Integer amountExcludeTax =0;  //未稅金額
	private Integer amountTax = 0;		  //稅額
	private String refund;
	private Integer refundamt;
	private Boolean ep1;
	private List acctSum3InfoList;
	private List acctInvoiceInfoList;	
	private Boolean vat; //報價是否含稅
	private Integer paymentTerm;	
	private Integer amountBySumToInteger =0;  //合計後再取整數
	private Integer amountByIntegerToSum =0;  //取整數後再合計	
	
	
	public String getDebitNo() {
		return debitNo;
	}
	public void setDebitNo(String debitNo) {
		this.debitNo = debitNo;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getDesSdt() {
		return desSdt;
	}
	public void setDesSdt(String desSdt) {
		this.desSdt = desSdt;
	}
	public String getDesEdt() {
		return desEdt;
	}
	public void setDesEdt(String desEdt) {
		this.desEdt = desEdt;
	}
	public String getCycleSdt() {
		return cycleSdt;
	}
	public void setCycleSdt(String cycleSdt) {
		this.cycleSdt = cycleSdt;
	}
	public String getCycleEdt() {
		return cycleEdt;
	}
	public void setCycleEdt(String cycleEdt) {
		this.cycleEdt = cycleEdt;
	}
	public String getProcessdt() {
		return processdt;
	}
	public void setProcessdt(String processdt) {
		this.processdt = processdt;
	}
	public String getProcessdt2() {
		return processdt2;
	}
	public void setProcessdt2(String processdt2) {
		this.processdt2 = processdt2;
	}
	public String getProcessdt3() {
		return processdt3;
	}
	public void setProcessdt3(String processdt3) {
		this.processdt3 = processdt3;
	}
	public String getInNo() {
		return inNo;
	}
	public void setInNo(String inNo) {
		this.inNo = inNo;
	}
	public String getInDt() {
		return inDt;
	}
	public void setInDt(String inDt) {
		this.inDt = inDt;
	}
	public String getInDuedt() {
		return inDuedt;
	}
	public void setInDuedt(String inDuedt) {
		this.inDuedt = inDuedt;
	}
	public Integer getAmountIncludeTax() {
		return amountIncludeTax;
	}
	public void setAmountIncludeTax(Integer amountIncludeTax) {
		this.amountIncludeTax = amountIncludeTax;
	}
	public Integer getAmountExcludeTax() {
		return amountExcludeTax;
	}
	public void setAmountExcludeTax(Integer amountExcludeTax) {
		this.amountExcludeTax = amountExcludeTax;
	}
	public Integer getAmountTax() {
		return amountTax;
	}
	public void setAmountTax(Integer amountTax) {
		this.amountTax = amountTax;
	}
	public String getRefund() {
		return refund;
	}
	public void setRefund(String refund) {
		this.refund = refund;
	}

	public Integer getRefundamt() {
		return refundamt;
	}
	public void setRefundamt(Integer refundamt) {
		this.refundamt = refundamt;
	}
	public Boolean getEp1() {
		return ep1;
	}
	public void setEp1(Boolean ep1) {
		this.ep1 = ep1;
	}
	public List getAcctSum3InfoList() {
		return acctSum3InfoList;
	}
	public void setAcctSum3InfoList(List acctSum3InfoList) {
		this.acctSum3InfoList = acctSum3InfoList;
	}
	public List getAcctInvoiceInfoList() {
		return acctInvoiceInfoList;
	}
	public void setAcctInvoiceInfoList(List acctInvoiceInfoList) {
		this.acctInvoiceInfoList = acctInvoiceInfoList;
	}
	public Boolean getVat() {
		return vat;
	}
	public void setVat(Boolean vat) {
		this.vat = vat;
	}
	public Integer getAmountBySumToInteger() {
		return amountBySumToInteger;
	}
	public void setAmountBySumToInteger(Integer amountBySumToInteger) {
		this.amountBySumToInteger = amountBySumToInteger;
	}
	public Integer getAmountByIntegerToSum() {
		return amountByIntegerToSum;
	}
	public void setAmountByIntegerToSum(Integer amountByIntegerToSum) {
		this.amountByIntegerToSum = amountByIntegerToSum;
	}
	public Integer getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(Integer paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	

}
