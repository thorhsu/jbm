package com.salmat.jbm.hibernate;

/**
 * AcctSum2Id entity. @author MyEclipse Persistence Tools
 */

public class AcctSum2Id implements java.io.Serializable {

	// Fields

	private Integer id;
	private Customer customer;
	private AcctDno acctDno;
	private String costCenter;
	private JobCode jobCode;
	private JobBag jobBag;
	private AcctChargeItem acctChargeItem;
	private AcctCustomerContract acctCustomerContract;
	private String itemName;
	private String itemNameTw;
	private String title;
	private String subTitle;
	private String ep1AccountCode;
	private String calculatType;
	private Double calculatUnitPrice;
	private Integer sumQty;

	// Constructors

	/** default constructor */
	public AcctSum2Id() {
	}

	/** minimal constructor */
	public AcctSum2Id(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AcctSum2Id(Integer id, Customer customer, AcctDno acctDno,
			String costCenter, JobCode jobCode, JobBag jobBag,
			AcctChargeItem acctChargeItem,
			AcctCustomerContract acctCustomerContract, String itemName,
			String itemNameTw, String title, String subTitle,
			String ep1AccountCode, String calculatType,
			Double calculatUnitPrice, Integer sumQty) {
		this.id = id;
		this.customer = customer;
		this.acctDno = acctDno;
		this.costCenter = costCenter;
		this.jobCode = jobCode;
		this.jobBag = jobBag;
		this.acctChargeItem = acctChargeItem;
		this.acctCustomerContract = acctCustomerContract;
		this.itemName = itemName;
		this.itemNameTw = itemNameTw;
		this.title = title;
		this.subTitle = subTitle;
		this.ep1AccountCode = ep1AccountCode;
		this.calculatType = calculatType;
		this.calculatUnitPrice = calculatUnitPrice;
		this.sumQty = sumQty;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public AcctDno getAcctDno() {
		return this.acctDno;
	}

	public void setAcctDno(AcctDno acctDno) {
		this.acctDno = acctDno;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public JobCode getJobCode() {
		return this.jobCode;
	}

	public void setJobCode(JobCode jobCode) {
		this.jobCode = jobCode;
	}

	public JobBag getJobBag() {
		return this.jobBag;
	}

	public void setJobBag(JobBag jobBag) {
		this.jobBag = jobBag;
	}

	public AcctChargeItem getAcctChargeItem() {
		return this.acctChargeItem;
	}

	public void setAcctChargeItem(AcctChargeItem acctChargeItem) {
		this.acctChargeItem = acctChargeItem;
	}

	public AcctCustomerContract getAcctCustomerContract() {
		return this.acctCustomerContract;
	}

	public void setAcctCustomerContract(
			AcctCustomerContract acctCustomerContract) {
		this.acctCustomerContract = acctCustomerContract;
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
		return this.calculatUnitPrice;
	}

	public void setUnitPrice(Double calculatUnitPrice) {
		this.calculatUnitPrice = calculatUnitPrice;
	}

	public Integer getSumQty() {
		return this.sumQty;
	}

	public void setSumQty(Integer sumQty) {
		this.sumQty = sumQty;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AcctSum2Id))
			return false;
		AcctSum2Id castOther = (AcctSum2Id) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getCustomer() == castOther.getCustomer()) || (this
						.getCustomer() != null
						&& castOther.getCustomer() != null && this
						.getCustomer().equals(castOther.getCustomer())))
				&& ((this.getAcctDno() == castOther.getAcctDno()) || (this
						.getAcctDno() != null && castOther.getAcctDno() != null && this
						.getAcctDno().equals(castOther.getAcctDno())))
				&& ((this.getCostCenter() == castOther.getCostCenter()) || (this
						.getCostCenter() != null
						&& castOther.getCostCenter() != null && this
						.getCostCenter().equals(castOther.getCostCenter())))
				&& ((this.getJobCode() == castOther.getJobCode()) || (this
						.getJobCode() != null && castOther.getJobCode() != null && this
						.getJobCode().equals(castOther.getJobCode())))
				&& ((this.getJobBag() == castOther.getJobBag()) || (this
						.getJobBag() != null && castOther.getJobBag() != null && this
						.getJobBag().equals(castOther.getJobBag())))
				&& ((this.getAcctChargeItem() == castOther.getAcctChargeItem()) || (this
						.getAcctChargeItem() != null
						&& castOther.getAcctChargeItem() != null && this
						.getAcctChargeItem().equals(
								castOther.getAcctChargeItem())))
				&& ((this.getAcctCustomerContract() == castOther
						.getAcctCustomerContract()) || (this
						.getAcctCustomerContract() != null
						&& castOther.getAcctCustomerContract() != null && this
						.getAcctCustomerContract().equals(
								castOther.getAcctCustomerContract())))
				&& ((this.getItemName() == castOther.getItemName()) || (this
						.getItemName() != null
						&& castOther.getItemName() != null && this
						.getItemName().equals(castOther.getItemName())))
				&& ((this.getItemNameTw() == castOther.getItemNameTw()) || (this
						.getItemNameTw() != null
						&& castOther.getItemNameTw() != null && this
						.getItemNameTw().equals(castOther.getItemNameTw())))
				&& ((this.getTitle() == castOther.getTitle()) || (this
						.getTitle() != null && castOther.getTitle() != null && this
						.getTitle().equals(castOther.getTitle())))
				&& ((this.getSubTitle() == castOther.getSubTitle()) || (this
						.getSubTitle() != null
						&& castOther.getSubTitle() != null && this
						.getSubTitle().equals(castOther.getSubTitle())))
				&& ((this.getEp1AccountCode() == castOther.getEp1AccountCode()) || (this
						.getEp1AccountCode() != null
						&& castOther.getEp1AccountCode() != null && this
						.getEp1AccountCode().equals(
								castOther.getEp1AccountCode())))
				&& ((this.getCalculatType() == castOther.getCalculatType()) || (this
						.getCalculatType() != null
						&& castOther.getCalculatType() != null && this
						.getCalculatType().equals(castOther.getCalculatType())))
				&& ((this.getUnitPrice() == castOther
						.getUnitPrice()) || (this
						.getUnitPrice() != null
						&& castOther.getUnitPrice() != null && this
						.getUnitPrice().equals(
								castOther.getUnitPrice())))
				&& ((this.getSumQty() == castOther.getSumQty()) || (this
						.getSumQty() != null && castOther.getSumQty() != null && this
						.getSumQty().equals(castOther.getSumQty())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getCustomer() == null ? 0 : this.getCustomer().hashCode());
		result = 37 * result
				+ (getAcctDno() == null ? 0 : this.getAcctDno().hashCode());
		result = 37
				* result
				+ (getCostCenter() == null ? 0 : this.getCostCenter()
						.hashCode());
		result = 37 * result
				+ (getJobCode() == null ? 0 : this.getJobCode().hashCode());
		result = 37 * result
				+ (getJobBag() == null ? 0 : this.getJobBag().hashCode());
		result = 37
				* result
				+ (getAcctChargeItem() == null ? 0 : this.getAcctChargeItem()
						.hashCode());
		result = 37
				* result
				+ (getAcctCustomerContract() == null ? 0 : this
						.getAcctCustomerContract().hashCode());
		result = 37 * result
				+ (getItemName() == null ? 0 : this.getItemName().hashCode());
		result = 37
				* result
				+ (getItemNameTw() == null ? 0 : this.getItemNameTw()
						.hashCode());
		result = 37 * result
				+ (getTitle() == null ? 0 : this.getTitle().hashCode());
		result = 37 * result
				+ (getSubTitle() == null ? 0 : this.getSubTitle().hashCode());
		result = 37
				* result
				+ (getEp1AccountCode() == null ? 0 : this.getEp1AccountCode()
						.hashCode());
		result = 37
				* result
				+ (getCalculatType() == null ? 0 : this.getCalculatType()
						.hashCode());
		result = 37
				* result
				+ (getUnitPrice() == null ? 0 : this
						.getUnitPrice().hashCode());
		result = 37 * result
				+ (getSumQty() == null ? 0 : this.getSumQty().hashCode());
		return result;
	}

}