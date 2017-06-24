package com.salmat.jbm.hibernate;

import java.util.Date;

/**
 * AcctSum1 entity. @author MyEclipse Persistence Tools
 */

public class AcctSum1 implements java.io.Serializable {

	// Fields

	private Integer id;
	private AcctDno acctDno;
	private AcctCustomerContract acctCustomerContract;
	private AcctChargeItem acctChargeItem;
	private Customer customer;
	private String idfJobCodeNo;
	private String idfJobBagNo;
	private String costCenter;
	private String itemName;
	private String itemNameTw;
	private String title;
	private String subTitle;
	private String ep1AccountCode;
	private String calculatType;
	private Double unitPrice;
	private Integer sumQty;
	private Date cycleDate;
	private Boolean isMinimalCharge;
	private String progNm;
	private Integer sumQtyOri;
	private Boolean autoCostCenter;
	private String lpPcode;

	// Constructors
	/** default constructor */
	public AcctSum1() {
	}

	/** full constructor */
	public AcctSum1(AcctDno acctDno, AcctCustomerContract acctCustomerContract,
			AcctChargeItem acctChargeItem, Customer customer,
			String idfJobCodeNo, String idfJobBagNo, String costCenter,
			String itemName, String itemNameTw, String title, String subTitle,
			String ep1AccountCode, String calculatType, Double unitPrice,
			Integer sumQty, Date cycleDate, Boolean isMinimalCharge,
			String progNm, String lpPcode) {
		this.acctDno = acctDno;
		this.acctCustomerContract = acctCustomerContract;
		this.acctChargeItem = acctChargeItem;
		this.customer = customer;
		this.idfJobCodeNo = idfJobCodeNo;
		this.idfJobBagNo = idfJobBagNo;
		this.costCenter = costCenter;
		this.itemName = itemName;
		this.itemNameTw = itemNameTw;
		this.title = title;
		this.subTitle = subTitle;
		this.ep1AccountCode = ep1AccountCode;
		this.calculatType = calculatType;
		this.unitPrice = unitPrice;
		this.sumQty = sumQty;
		this.cycleDate = cycleDate;
		this.isMinimalCharge = isMinimalCharge;
		this.progNm = progNm;
		this.lpPcode = lpPcode;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AcctDno getAcctDno() {
		return this.acctDno;
	}

	public void setAcctDno(AcctDno acctDno) {
		this.acctDno = acctDno;
	}

	public AcctCustomerContract getAcctCustomerContract() {
		return this.acctCustomerContract;
	}

	public void setAcctCustomerContract(
			AcctCustomerContract acctCustomerContract) {
		this.acctCustomerContract = acctCustomerContract;
	}

	public AcctChargeItem getAcctChargeItem() {
		return this.acctChargeItem;
	}

	public void setAcctChargeItem(AcctChargeItem acctChargeItem) {
		this.acctChargeItem = acctChargeItem;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getIdfJobCodeNo() {
		return this.idfJobCodeNo;
	}

	public void setIdfJobCodeNo(String idfJobCodeNo) {
		this.idfJobCodeNo = idfJobCodeNo;
	}

	public String getIdfJobBagNo() {
		return this.idfJobBagNo;
	}

	public void setIdfJobBagNo(String idfJobBagNo) {
		this.idfJobBagNo = idfJobBagNo;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemNameTw() {
		return this.itemNameTw;
	}

	public void setItemNameTw(String itemNameTw) {
		this.itemNameTw = itemNameTw;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getEp1AccountCode() {
		return this.ep1AccountCode;
	}

	public void setEp1AccountCode(String ep1AccountCode) {
		this.ep1AccountCode = ep1AccountCode;
	}

	public String getCalculatType() {
		return this.calculatType;
	}

	public void setCalculatType(String calculatType) {
		this.calculatType = calculatType;
	}

	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getSumQty() {
		return this.sumQty;
	}

	public void setSumQty(Integer sumQty) {
		this.sumQty = sumQty;
	}

	public Date getCycleDate() {
		return this.cycleDate;
	}

	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}

	public Boolean getIsMinimalCharge() {
		return this.isMinimalCharge;
	}

	public void setIsMinimalCharge(Boolean isMinimalCharge) {
		this.isMinimalCharge = isMinimalCharge;
	}

	public String getProgNm() {
		return this.progNm;
	}

	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}

	public Integer getSumQtyOri() {
		return sumQtyOri;
	}

	public void setSumQtyOri(Integer sumQtyOri) {
		this.sumQtyOri = sumQtyOri;
	}
	public Boolean getAutoCostCenter() {
		return autoCostCenter;
	}

	public void setAutoCostCenter(Boolean autoCostCenter) {
		this.autoCostCenter = autoCostCenter;
	}
	public String getLpPcode() {
		return lpPcode;
	}

	public void setLpPcode(String lpPcode) {
		this.lpPcode = lpPcode;
	}


}