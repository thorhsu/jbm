package com.salmat.jbm.bean;

import java.util.Date;
import java.util.List;

import com.salmat.jbm.hibernate.AcctDno;
import com.salmat.jbm.hibernate.Customer;

public class AcctInvoiceInfo {
	private Integer id;
	private AcctDno acctDno;
	private Customer customer;
	private String costCenter;
	private Double expense;
	private String invoiceno;
	private String dept;
	private Double tax;
	private String jobtype;
	private String jobCodeNo;
	private String invoicetyp;
	private Integer amountIncludeTax =0;  //含稅金額
	private Integer amountExcludeTax =0;  //未稅金額
	private Integer amountTax = 0;			//稅額
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public AcctDno getAcctDno() {
		return acctDno;
	}
	public void setAcctDno(AcctDno acctDno) {
		this.acctDno = acctDno;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public Double getExpense() {
		return expense;
	}
	public void setExpense(Double expense) {
		this.expense = expense;
	}
	public String getInvoiceno() {
		return invoiceno;
	}
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public String getJobtype() {
		return jobtype;
	}
	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}
	public String getJobCodeNo() {
		return jobCodeNo;
	}
	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}
	public String getInvoicetyp() {
		return invoicetyp;
	}
	public void setInvoicetyp(String invoicetyp) {
		this.invoicetyp = invoicetyp;
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

}
