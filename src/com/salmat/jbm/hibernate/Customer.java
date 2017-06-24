package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Customer entity. @author MyEclipse Persistence Tools
 */

public class Customer implements java.io.Serializable {

	// Fields

	private String custNo;
	private Employee employee;
	private String area;
	private String easyName;
	private String custName;
	private String address1;
	private String address2;
	private String address3;
	private String PNo;
	private String tel1;
	private String tel2;
	private String fax;
	private String contaceP;
	private String ext;
	private String BBCall;
	private String mobile;
	private String sex;
	private String contactDe;
	private String title;
	private double discount;
	private String payment;
	private String rpBy;
	private String class_;
	private double deadline;
	private String ep1no;
	private String pathCustNameImage;
	private String pathCustStampImage;
	private String bankName;
	private String bankAccount;
	private Set acctCustomerContracts = new HashSet(0);
	private Set lginfos = new HashSet(0);
	private Set acctInvoices = new HashSet(0);
	private Set jobBags = new HashSet(0);
	private Set acctSum2s = new HashSet(0);
	private Set acctSectionJobCodeMappings = new HashSet(0);
	private Set acctSum3s = new HashSet(0);
	private Set acctJobCodeChargeItems = new HashSet(0);
	private Set jobCodes = new HashSet(0);
	private Set acctSectionReceiverMappings = new HashSet(0);
	private Set customerReceivers = new HashSet(0);
	private Set lpinfos = new HashSet(0);
	private Set acctDnos = new HashSet(0);
	private Set psinfos = new HashSet(0);
	private Set acctSum1s = new HashSet(0);
	private Set lcinfos = new HashSet(0);
	private Set mpinfos = new HashSet(0);
	private Set returninfos = new HashSet(0);

	// Constructors

	/** default constructor */
	public Customer() {
	}

	/** minimal constructor */
	public Customer(String custNo) {
		this.custNo = custNo;
	}

	/** full constructor */
	public Customer(String custNo, Employee employee, String area,
			String easyName, String custName, String address1, String address2,
			String address3, String PNo, String tel1, String tel2, String fax,
			String contaceP, String ext, String BBCall, String mobile,
			String sex, String contactDe, String title, double discount,
			String payment, String rpBy, String class_, double deadline,
			String ep1no, String pathCustNameImage, String pathCustStampImage,
			String bankName, String bankAccount, Set acctCustomerContracts,
			Set lginfos, Set acctInvoices, Set jobBags, Set acctSum2s,
			Set acctSectionJobCodeMappings, Set acctSum3s,
			Set acctJobCodeChargeItems, Set jobCodes,
			Set acctSectionReceiverMappings, Set customerReceivers,
			Set lpinfos, Set acctDnos, Set psinfos, Set acctSum1s, Set lcinfos,
			Set mpinfos, Set returninfos) {
		this.custNo = custNo;
		this.employee = employee;
		this.area = area;
		this.easyName = easyName;
		this.custName = custName;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.PNo = PNo;
		this.tel1 = tel1;
		this.tel2 = tel2;
		this.fax = fax;
		this.contaceP = contaceP;
		this.ext = ext;
		this.BBCall = BBCall;
		this.mobile = mobile;
		this.sex = sex;
		this.contactDe = contactDe;
		this.title = title;
		this.discount = discount;
		this.payment = payment;
		this.rpBy = rpBy;
		this.class_ = class_;
		this.deadline = deadline;
		this.ep1no = ep1no;
		this.pathCustNameImage = pathCustNameImage;
		this.pathCustStampImage = pathCustStampImage;
		this.bankName = bankName;
		this.bankAccount = bankAccount;
		this.acctCustomerContracts = acctCustomerContracts;
		this.lginfos = lginfos;
		this.acctInvoices = acctInvoices;
		this.jobBags = jobBags;
		this.acctSum2s = acctSum2s;
		this.acctSectionJobCodeMappings = acctSectionJobCodeMappings;
		this.acctSum3s = acctSum3s;
		this.acctJobCodeChargeItems = acctJobCodeChargeItems;
		this.jobCodes = jobCodes;
		this.acctSectionReceiverMappings = acctSectionReceiverMappings;
		this.customerReceivers = customerReceivers;
		this.lpinfos = lpinfos;
		this.acctDnos = acctDnos;
		this.psinfos = psinfos;
		this.acctSum1s = acctSum1s;
		this.lcinfos = lcinfos;
		this.mpinfos = mpinfos;
		this.returninfos = returninfos;
	}

	// Property accessors

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getEasyName() {
		return this.easyName;
	}

	public void setEasyName(String easyName) {
		this.easyName = easyName;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return this.address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getPNo() {
		return this.PNo;
	}

	public void setPNo(String PNo) {
		this.PNo = PNo;
	}

	public String getTel1() {
		return this.tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return this.tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContaceP() {
		return this.contaceP;
	}

	public void setContaceP(String contaceP) {
		this.contaceP = contaceP;
	}

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getBBCall() {
		return this.BBCall;
	}

	public void setBBCall(String BBCall) {
		this.BBCall = BBCall;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getContactDe() {
		return this.contactDe;
	}

	public void setContactDe(String contactDe) {
		this.contactDe = contactDe;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getDiscount() {
		return this.discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getRpBy() {
		return this.rpBy;
	}

	public void setRpBy(String rpBy) {
		this.rpBy = rpBy;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public double getDeadline() {
		return this.deadline;
	}

	public void setDeadline(double deadline) {
		this.deadline = deadline;
	}

	public String getEp1no() {
		return this.ep1no;
	}

	public void setEp1no(String ep1no) {
		this.ep1no = ep1no;
	}

	public String getPathCustNameImage() {
		return this.pathCustNameImage;
	}

	public void setPathCustNameImage(String pathCustNameImage) {
		this.pathCustNameImage = pathCustNameImage;
	}

	public String getPathCustStampImage() {
		return this.pathCustStampImage;
	}

	public void setPathCustStampImage(String pathCustStampImage) {
		this.pathCustStampImage = pathCustStampImage;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return this.bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Set getAcctCustomerContracts() {
		return this.acctCustomerContracts;
	}

	public void setAcctCustomerContracts(Set acctCustomerContracts) {
		this.acctCustomerContracts = acctCustomerContracts;
	}

	public Set getLginfos() {
		return this.lginfos;
	}

	public void setLginfos(Set lginfos) {
		this.lginfos = lginfos;
	}

	public Set getAcctInvoices() {
		return this.acctInvoices;
	}

	public void setAcctInvoices(Set acctInvoices) {
		this.acctInvoices = acctInvoices;
	}

	public Set getJobBags() {
		return this.jobBags;
	}

	public void setJobBags(Set jobBags) {
		this.jobBags = jobBags;
	}

	public Set getAcctSum2s() {
		return this.acctSum2s;
	}

	public void setAcctSum2s(Set acctSum2s) {
		this.acctSum2s = acctSum2s;
	}

	public Set getAcctSectionJobCodeMappings() {
		return this.acctSectionJobCodeMappings;
	}

	public void setAcctSectionJobCodeMappings(Set acctSectionJobCodeMappings) {
		this.acctSectionJobCodeMappings = acctSectionJobCodeMappings;
	}

	public Set getAcctSum3s() {
		return this.acctSum3s;
	}

	public void setAcctSum3s(Set acctSum3s) {
		this.acctSum3s = acctSum3s;
	}

	public Set getAcctJobCodeChargeItems() {
		return this.acctJobCodeChargeItems;
	}

	public void setAcctJobCodeChargeItems(Set acctJobCodeChargeItems) {
		this.acctJobCodeChargeItems = acctJobCodeChargeItems;
	}

	public Set getJobCodes() {
		return this.jobCodes;
	}

	public void setJobCodes(Set jobCodes) {
		this.jobCodes = jobCodes;
	}

	public Set getAcctSectionReceiverMappings() {
		return this.acctSectionReceiverMappings;
	}

	public void setAcctSectionReceiverMappings(Set acctSectionReceiverMappings) {
		this.acctSectionReceiverMappings = acctSectionReceiverMappings;
	}

	public Set getCustomerReceivers() {
		return this.customerReceivers;
	}

	public void setCustomerReceivers(Set customerReceivers) {
		this.customerReceivers = customerReceivers;
	}

	public Set getLpinfos() {
		return this.lpinfos;
	}

	public void setLpinfos(Set lpinfos) {
		this.lpinfos = lpinfos;
	}

	public Set getAcctDnos() {
		return this.acctDnos;
	}

	public void setAcctDnos(Set acctDnos) {
		this.acctDnos = acctDnos;
	}

	public Set getPsinfos() {
		return this.psinfos;
	}

	public void setPsinfos(Set psinfos) {
		this.psinfos = psinfos;
	}

	public Set getAcctSum1s() {
		return this.acctSum1s;
	}

	public void setAcctSum1s(Set acctSum1s) {
		this.acctSum1s = acctSum1s;
	}

	public Set getLcinfos() {
		return this.lcinfos;
	}

	public void setLcinfos(Set lcinfos) {
		this.lcinfos = lcinfos;
	}

	public Set getMpinfos() {
		return this.mpinfos;
	}

	public void setMpinfos(Set mpinfos) {
		this.mpinfos = mpinfos;
	}

	public Set getReturninfos() {
		return this.returninfos;
	}

	public void setReturninfos(Set returninfos) {
		this.returninfos = returninfos;
	}

}