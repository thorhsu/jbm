package com.salmat.jbm.hibernate;

import java.util.Date;
import java.util.Set;

/**
 * AcctSum1 entity. @author MyEclipse Persistence Tools
 */

public class AcctItem implements java.io.Serializable {

	// Fields

	private String acctItemNo; //acctItem_no(contractNo +  id(acctItemFx's id))	
	private String name;
	private Double cost;
	private Double toCustPrice;
	private Double acctPrice;
	private String printNo;
	private Boolean isMaterial;
	private String custNo;
	private String unitName;
	private Integer unitCode;
	private CustomerContract customerContract;
	private AcctItemFx acctItemFx;
	private AcctChargeItem acctChargeItem;
	private Integer categoryCode;
	private String categoryName;
	private String color;
	private Double pound;
	private Double length;
	private Double width;
	private Integer lengthUnit;
	private String lengthUnitNm;	
	private Double weight;
	private Integer weightUnit;
	private String weightUnitNm;
	
	
	public String getAcctItemNo() {
		return acctItemNo;
	}
	public void setAcctItemNo(String acctItemNo) {
		this.acctItemNo = acctItemNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public Double getToCustPrice() {
		return toCustPrice;
	}
	public void setToCustPrice(Double toCustPrice) {
		this.toCustPrice = toCustPrice;
	}
	public Double getAcctPrice() {
		return acctPrice;
	}
	public void setAcctPrice(Double acctPrice) {
		this.acctPrice = acctPrice;
	}
	public String getPrintNo() {
		return printNo;
	}
	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}
	public Boolean getIsMaterial() {
		return isMaterial;
	}
	public void setIsMaterial(Boolean isMaterial) {
		this.isMaterial = isMaterial;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	public Integer getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(Integer unitCode) {
		this.unitCode = unitCode;
	}
	public CustomerContract getCustomerContract() {
		return customerContract;
	}
	public void setCustomerContract(CustomerContract customerContract) {
		this.customerContract = customerContract;
	}
	public AcctItemFx getAcctItemFx() {
		return acctItemFx;
	}
	public void setAcctItemFx(AcctItemFx acctItemFx) {
		this.acctItemFx = acctItemFx;
	}
	public AcctChargeItem getAcctChargeItem() {
		return acctChargeItem;
	}
	public void setAcctChargeItem(AcctChargeItem acctChargeItem) {
		this.acctChargeItem = acctChargeItem;
	}
	public Integer getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(Integer categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Double getPound() {
		return pound;
	}
	public void setPound(Double pound) {
		this.pound = pound;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Integer getLengthUnit() {
		return lengthUnit;
	}
	public void setLengthUnit(Integer lengthUnit) {
		this.lengthUnit = lengthUnit;
	}
	public String getLengthUnitNm() {
		return lengthUnitNm;
	}
	public void setLengthUnitNm(String lengthUnitNm) {
		this.lengthUnitNm = lengthUnitNm;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Integer getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(Integer weightUnit) {
		this.weightUnit = weightUnit;
	}
	public String getWeightUnitNm() {
		return weightUnitNm;
	}
	public void setWeightUnitNm(String weightUnitNm) {
		this.weightUnitNm = weightUnitNm;
	}
		
}