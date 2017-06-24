package com.salmat.jbm.hibernate;

import java.util.Date;
import java.util.Set;

/**
 * AcctSum1 entity. @author MyEclipse Persistence Tools
 */

public class AcctItemFx implements java.io.Serializable {

	// Fields

	private Integer id;	
	private String name;
	private Double cost;	
	private Double acctPrice;
	private String printNo;
	
	private String custNo;
	private boolean isMaterial;
	private String unitName;	
	private Integer unitCode;
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
	
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	private Set<AcctItem> acctItems;
	private AcctChargeItem acctChargeItem;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public boolean getIsMaterial() {
		return isMaterial;
	}
	public void setIsMaterial(boolean isMaterial) {
		this.isMaterial = isMaterial;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Set<AcctItem> getAcctItems() {
		return acctItems;
	}
	public void setAcctItems(Set<AcctItem> acctItems) {
		this.acctItems = acctItems;
	}
	public Integer getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(Integer unitCode) {
		this.unitCode = unitCode;
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