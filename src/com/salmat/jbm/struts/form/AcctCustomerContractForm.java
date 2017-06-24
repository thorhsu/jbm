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
import com.salmat.jbm.hibernate.AcctChargeItem;
import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.Customer;
import com.salmat.jbm.hibernate.Employee;
import com.salmat.jbm.hibernate.JobBag;



public class AcctCustomerContractForm extends ActionForm {
	// Fields

	private Integer id;
	private String custNo;
	private String contactDateBegin_form;
	private String contactDateEnd_form;
	private Double unitPrice;
	private Double unitPriceAdjustmentPercent;	
	private String reportTitle;
	private Integer reportWidth;
	private Integer orderby;
	private Boolean autoExtend;
	
	private Integer[] chargeItemIds;
	private Double[] unitPrices;
	private Double[] unitPriceAdjustmentPercents;	
	private String[] reportTitles;
	private Integer[] reportWidths;
	private Integer[] orderbys;
	private Integer[] delIds;
	private Integer[] acctCustomerContractIds;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getContactDateBegin_form() {
		return contactDateBegin_form;
	}
	public void setContactDateBegin_form(String contactDateBegin_form) {
		this.contactDateBegin_form = contactDateBegin_form;
	}
	public String getContactDateEnd_form() {
		return contactDateEnd_form;
	}
	public void setContactDateEnd_form(String contactDateEnd_form) {
		this.contactDateEnd_form = contactDateEnd_form;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public Integer getReportWidth() {
		return reportWidth;
	}
	public void setReportWidth(Integer reportWidth) {
		this.reportWidth = reportWidth;
	}
	public Integer getOrderby() {
		return orderby;
	}
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}
	public Integer[] getChargeItemIds() {
		return chargeItemIds;
	}
	public void setChargeItemIds(Integer[] chargeItemIds) {
		this.chargeItemIds = chargeItemIds;
	}
	public Double[] getUnitPrices() {
		return unitPrices;
	}
	public void setUnitPrices(Double[] unitPrices) {
		this.unitPrices = unitPrices;
	}
	public String[] getReportTitles() {
		return reportTitles;
	}
	public void setReportTitles(String[] reportTitles) {
		this.reportTitles = reportTitles;
	}
	public Integer[] getReportWidths() {
		return reportWidths;
	}
	public void setReportWidths(Integer[] reportWidths) {
		this.reportWidths = reportWidths;
	}
	public Integer[] getOrderbys() {
		return orderbys;
	}
	public void setOrderbys(Integer[] orderbys) {
		this.orderbys = orderbys;
	}
	public Integer[] getAcctCustomerContractIds() {
		return acctCustomerContractIds;
	}
	public void setAcctCustomerContractIds(Integer[] acctCustomerContractIds) {
		this.acctCustomerContractIds = acctCustomerContractIds;
	}
	public Double getUnitPriceAdjustmentPercent() {
		return unitPriceAdjustmentPercent;
	}
	public void setUnitPriceAdjustmentPercent(Double unitPriceAdjustmentPercent) {
		this.unitPriceAdjustmentPercent = unitPriceAdjustmentPercent;
	}
	public Double[] getUnitPriceAdjustmentPercents() {
		return unitPriceAdjustmentPercents;
	}
	public void setUnitPriceAdjustmentPercents(Double[] unitPriceAdjustmentPercents) {
		this.unitPriceAdjustmentPercents = unitPriceAdjustmentPercents;
	}
	public Integer[] getDelIds() {
		return delIds;
	}
	public void setDelIds(Integer[] delIds) {
		this.delIds = delIds;
	}
	
	public Boolean getAutoExtend() {
		return autoExtend;
	}
	public void setAutoExtend(Boolean autoExtend) {
		this.autoExtend = autoExtend;
	}
	
	
}