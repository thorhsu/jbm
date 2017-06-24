package com.salmat.jbm.hibernate;

/**
 * AcctSum2 entity. @author MyEclipse Persistence Tools
 */

public class AcctSum2 implements java.io.Serializable {

	// Fields

	private Integer id;
	private AcctDno acctDno;
	private AcctCustomerContract acctCustomerContract;
	private AcctChargeItem acctChargeItem;
	private Customer customer;
	private String costCenter;
	private String idfJobCodeNo;
	private String itemType;
	private String itemName;
	private String itemNameTw;
	private String title;
	private String subTitle;
	private String ep1AccountCode;
	private Double unitPrice;
	private Double sumQty;
	private Double total;
	private Boolean isMinimalCharge;
	private String printCode;
	private String progNm;
	private Boolean autoCostCenter;

	// Constructors

	/** default constructor */
	public AcctSum2() {
	}

	/** full constructor */
	public AcctSum2(AcctDno acctDno, AcctCustomerContract acctCustomerContract,
			AcctChargeItem acctChargeItem, Customer customer,
			String costCenter, String idfJobCodeNo, String itemType,
			String itemName, String itemNameTw, String title, String subTitle,
			String ep1AccountCode, Double unitPrice, Double sumQty,
			Double total, Boolean isMinimalCharge, String printCode,
			String progNm) {
		this.acctDno = acctDno;
		this.acctCustomerContract = acctCustomerContract;
		this.acctChargeItem = acctChargeItem;
		this.customer = customer;
		this.costCenter = costCenter;
		this.idfJobCodeNo = idfJobCodeNo;
		this.itemType = itemType;
		this.itemName = itemName;
		this.itemNameTw = itemNameTw;
		this.title = title;
		this.subTitle = subTitle;
		this.ep1AccountCode = ep1AccountCode;
		this.unitPrice = unitPrice;
		this.sumQty = sumQty;
		this.total = total;
		this.isMinimalCharge = isMinimalCharge;
		this.printCode = printCode;
		this.progNm = progNm;
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

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public String getIdfJobCodeNo() {
		return this.idfJobCodeNo;
	}

	public void setIdfJobCodeNo(String idfJobCodeNo) {
		this.idfJobCodeNo = idfJobCodeNo;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
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

	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getSumQty() {
		return this.sumQty;
	}

	public void setSumQty(Double sumQty) {
		this.sumQty = sumQty;
	}

	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Boolean getIsMinimalCharge() {
		return this.isMinimalCharge;
	}

	public void setIsMinimalCharge(Boolean isMinimalCharge) {
		this.isMinimalCharge = isMinimalCharge;
	}

	public String getPrintCode() {
		return this.printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}

	public String getProgNm() {
		return this.progNm;
	}

	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}
	public Boolean getAutoCostCenter() {
		return autoCostCenter;
	}

	public void setAutoCostCenter(Boolean autoCostCenter) {
		this.autoCostCenter = autoCostCenter;
	}

}