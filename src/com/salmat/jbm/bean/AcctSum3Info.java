package com.salmat.jbm.bean;

import java.util.Date;
import java.util.List;

import com.salmat.jbm.hibernate.AcctDno;
import com.salmat.jbm.hibernate.Customer;

public class AcctSum3Info {
	private Integer id;
	private AcctDno acctDno;
	private Customer customer;
	private String title;
	private String subtitle;
	private Double sumQty;
	private Double unitPrice;
	private Double total;
	private String costCenter;
	private String purpose;
	private String ep1acccd;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public Double getSumQty() {
		return sumQty;
	}
	public void setSumQty(Double sumQty) {
		this.sumQty = sumQty;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getEp1acccd() {
		return ep1acccd;
	}
	public void setEp1acccd(String ep1acccd) {
		this.ep1acccd = ep1acccd;
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
