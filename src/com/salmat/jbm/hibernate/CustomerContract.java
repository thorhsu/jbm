package com.salmat.jbm.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Arrays;

/**
 * AcctSum1 entity. @author MyEclipse Persistence Tools
 */

public class CustomerContract implements java.io.Serializable {

	// Fields

	private String contractNo;
	private Customer customer;
	private CustomerReceiver receiver;
	private String accountManager ;
	private String custService;
	private String backupStaff;
	private String contractName;
	private Integer deadlineCode;
	private Date contractBegin;
	private Date contractEnd;
	private Integer remindBeforeDeadline;
	private String itEmpStr;
	private String billingWay;
	private String note;
	private String fileName;
	private String filePath;
	private Integer freeHours;
	private Integer usedHours;
	
	private Code billingCycleCode;
	private Set<Sla> slas;
	private Set<Penalty> penalties;
	private Set<Service> services;
	private Set<AcctItem> acctItems;
	private Set<Operation> operations;
	
	public Set<Sla> getSlas() {
		return slas;
	}

	public String getItEmpStr() {
		return itEmpStr;
	}


	public void setItEmpStr(String itEmpStr) {
		this.itEmpStr = itEmpStr;
	}
	
	public List<String> getItEmployees(){
		if(this.itEmpStr != null && !this.itEmpStr.trim().equals("")){
			String [] itEmp = itEmpStr.split(",");
			return new ArrayList<String>(Arrays.asList(itEmp));
		}
		return null;
	}
	
	public void setItEmployees(List<String> itEmps){		
		if(itEmps != null){
			String toDb = ",";
			for(String itemp : itEmps){
				toDb += itemp + ",";
			}
			if(toDb.length() > 1)
			   setItEmpStr(toDb);
			else
			   setItEmpStr(null);
		}else{
			setItEmpStr(null);
		}
	}


	public void setSlas(Set<Sla> slas) {
		this.slas = slas;
	}


	public Set<Penalty> getPenalties() {
		return penalties;
	}


	public void setPenalties(Set<Penalty> penalties) {
		this.penalties = penalties;
	}


	public Set<Service> getServices() {
		return services;
	}


	public void setServices(Set<Service> services) {
		this.services = services;
	}


	public Set<Operation> getOperations() {
		return operations;
	}


	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	// Constructors
	/** default constructor */
	public CustomerContract() {
	}


	public String getContractNo() {
		return contractNo;
	}


	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public Customer getCustomer() {
		return customer;
	}


	public CustomerReceiver getReceiver() {
		return receiver;
	}


	public void setReceiver(CustomerReceiver receiver) {
		this.receiver = receiver;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public String getAccountManager() {
		return accountManager;
	}


	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}


	public String getCustService() {
		return custService;
	}


	public void setCustService(String custService) {
		this.custService = custService;
	}


	public String getBackupStaff() {
		return backupStaff;
	}


	public void setBackupStaff(String backupStaff) {
		this.backupStaff = backupStaff;
	}


	public String getContractName() {
		return contractName;
	}


	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	
	public Integer getDeadlineCode() {
		return deadlineCode;
	}


	public void setDeadlineCode(Integer deadlineCode) {
		this.deadlineCode = deadlineCode;
	}

	public Date getContractBegin() {
		return contractBegin;
	}


	public void setContractBegin(Date contractBegin) {
		this.contractBegin = contractBegin;
	}


	public Date getContractEnd() {
		return contractEnd;
	}


	public void setContractEnd(Date contractEnd) {
		this.contractEnd = contractEnd;
	}


	public Integer getRemindBeforeDeadline() {
		return remindBeforeDeadline;
	}

	public String getBillingWay() {
		return billingWay;
	}


	public void setBillingWay(String billingWay) {
		this.billingWay = billingWay;
	}


	public Code getBillingCycleCode() {
		return billingCycleCode;
	}


	public void setBillingCycleCode(Code billingCycleCode) {
		this.billingCycleCode = billingCycleCode;
	}


	public void setRemindBeforeDeadline(Integer remindBeforeDeadline) {
		this.remindBeforeDeadline = remindBeforeDeadline;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Set<AcctItem> getAcctItems() {
		return acctItems;
	}


	public void setAcctItems(Set<AcctItem> acctItems) {
		this.acctItems = acctItems;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Integer getFreeHours() {
		return freeHours;
	}

	public void setFreeHours(Integer freeHours) {
		this.freeHours = freeHours;
	}

	public Integer getUsedHours() {
		return usedHours;
	}

	public void setUsedHours(Integer usedHours) {
		this.usedHours = usedHours;
	}

	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
}