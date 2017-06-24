package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * AcctChargeItem entity. @author MyEclipse Persistence Tools
 */

public class AcctChargeItem implements java.io.Serializable {

	// Fields

	private Integer id;
	private Code codeBySubTitle;
	private Code codeByTitle;
	private String itemType;
	private String itemName;
	private String itemNameTw;
	private String ep1AccountCode;
	private String calculatType;
	private Boolean accounts;
	private Boolean pages;
	private Boolean sheets;
	private Boolean feeder2;
	private Boolean feeder3;
	private Boolean feeder4;
	private Boolean feeder5;
	private Boolean tray1;
	private Boolean tray2;
	private Boolean tray3;
	private Boolean tray4;
	private Boolean tray5;
	private Boolean tray6;
	private Boolean tray7;
	private Boolean tray8;
	private Integer fixedValue;
	private String reportTitle;
	private String expression;
	private Boolean isExcludeQty;
	private Boolean pages2;
	private Boolean pages3;
	private Boolean p1accounts;
	private Boolean p2accounts;
	private Boolean p3accounts;
	private Boolean p4accounts;
	private Boolean p5accounts;
	private Boolean p6accounts;
	private Boolean pxaccounts;
	private Boolean ctaccounts;
	private Integer fubonCode;
	private Boolean notSent;
	private Boolean zeroExpress;
	
	private Set acctSum2s = new HashSet(0);
	private Set acctSum1s = new HashSet(0);
	private Set acctJobCodeChargeItems = new HashSet(0);
	private Set acctCustomerContracts = new HashSet(0);
	

	// Constructors

	/** default constructor */
	public AcctChargeItem() {
	}

	/** full constructor */
	public AcctChargeItem(Code codeBySubTitle, Code codeByTitle,
			String itemType, String itemName, String itemNameTw,
			String ep1AccountCode, String calculatType, Boolean accounts,
			Boolean pages, Boolean sheets, Boolean feeder2, Boolean feeder3,
			Boolean feeder4, Boolean feeder5, Boolean tray1, Boolean tray2,
			Boolean tray3, Boolean tray4, Integer fixedValue,
			String reportTitle, String expression, Boolean isExcludeQty,
			Boolean pages2, Boolean pages3, Boolean p1accounts,
			Boolean p2accounts, Boolean p3accounts, Boolean p4accounts,
			Boolean p5accounts, Boolean p6accounts, Boolean pxaccounts,
			Set acctSum2s, Set acctSum1s, Set acctJobCodeChargeItems,
			Set acctCustomerContracts) {
		this.codeBySubTitle = codeBySubTitle;
		this.codeByTitle = codeByTitle;
		this.itemType = itemType;
		this.itemName = itemName;
		this.itemNameTw = itemNameTw;
		this.ep1AccountCode = ep1AccountCode;
		this.calculatType = calculatType;
		this.accounts = accounts;
		this.pages = pages;
		this.sheets = sheets;
		this.feeder2 = feeder2;
		this.feeder3 = feeder3;
		this.feeder4 = feeder4;
		this.feeder5 = feeder5;
		this.tray1 = tray1;
		this.tray2 = tray2;
		this.tray3 = tray3;
		this.tray4 = tray4;
		this.fixedValue = fixedValue;
		this.reportTitle = reportTitle;
		this.expression = expression;
		this.isExcludeQty = isExcludeQty;
		this.pages2 = pages2;
		this.pages3 = pages3;
		this.p1accounts = p1accounts;
		this.p2accounts = p2accounts;
		this.p3accounts = p3accounts;
		this.p4accounts = p4accounts;
		this.p5accounts = p5accounts;
		this.p6accounts = p6accounts;
		this.pxaccounts = pxaccounts;
		this.acctSum2s = acctSum2s;
		this.acctSum1s = acctSum1s;
		this.acctJobCodeChargeItems = acctJobCodeChargeItems;
		this.acctCustomerContracts = acctCustomerContracts;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Code getCodeBySubTitle() {
		return this.codeBySubTitle;
	}

	public void setCodeBySubTitle(Code codeBySubTitle) {
		this.codeBySubTitle = codeBySubTitle;
	}

	public Code getCodeByTitle() {
		return this.codeByTitle;
	}

	public void setCodeByTitle(Code codeByTitle) {
		this.codeByTitle = codeByTitle;
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

	public Boolean getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Boolean accounts) {
		this.accounts = accounts;
	}

	public Boolean getPages() {
		return this.pages;
	}

	public void setPages(Boolean pages) {
		this.pages = pages;
	}

	public Boolean getSheets() {
		return this.sheets;
	}

	public void setSheets(Boolean sheets) {
		this.sheets = sheets;
	}

	public Boolean getFeeder2() {
		return this.feeder2;
	}

	public void setFeeder2(Boolean feeder2) {
		this.feeder2 = feeder2;
	}

	public Boolean getFeeder3() {
		return this.feeder3;
	}

	public void setFeeder3(Boolean feeder3) {
		this.feeder3 = feeder3;
	}

	public Boolean getFeeder4() {
		return this.feeder4;
	}

	public void setFeeder4(Boolean feeder4) {
		this.feeder4 = feeder4;
	}

	public Boolean getFeeder5() {
		return this.feeder5;
	}

	public void setFeeder5(Boolean feeder5) {
		this.feeder5 = feeder5;
	}

	public Boolean getTray1() {
		return this.tray1;
	}

	public void setTray1(Boolean tray1) {
		this.tray1 = tray1;
	}

	public Boolean getTray2() {
		return this.tray2;
	}

	public void setTray2(Boolean tray2) {
		this.tray2 = tray2;
	}

	public Boolean getTray3() {
		return this.tray3;
	}

	public void setTray3(Boolean tray3) {
		this.tray3 = tray3;
	}

	public Boolean getTray4() {
		return this.tray4;
	}

	public void setTray4(Boolean tray4) {
		this.tray4 = tray4;
	}

	public Integer getFixedValue() {
		return this.fixedValue;
	}

	public void setFixedValue(Integer fixedValue) {
		this.fixedValue = fixedValue;
	}

	public String getReportTitle() {
		return this.reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Boolean getIsExcludeQty() {
		return this.isExcludeQty;
	}

	public void setIsExcludeQty(Boolean isExcludeQty) {
		this.isExcludeQty = isExcludeQty;
	}

	public Boolean getPages2() {
		return this.pages2;
	}

	public void setPages2(Boolean pages2) {
		this.pages2 = pages2;
	}

	public Boolean getPages3() {
		return this.pages3;
	}

	public void setPages3(Boolean pages3) {
		this.pages3 = pages3;
	}

	public Boolean getP1accounts() {
		return this.p1accounts;
	}

	public void setP1accounts(Boolean p1accounts) {
		this.p1accounts = p1accounts;
	}

	public Boolean getP2accounts() {
		return this.p2accounts;
	}

	public void setP2accounts(Boolean p2accounts) {
		this.p2accounts = p2accounts;
	}

	public Boolean getP3accounts() {
		return this.p3accounts;
	}

	public void setP3accounts(Boolean p3accounts) {
		this.p3accounts = p3accounts;
	}

	public Boolean getP4accounts() {
		return this.p4accounts;
	}

	public void setP4accounts(Boolean p4accounts) {
		this.p4accounts = p4accounts;
	}

	public Boolean getP5accounts() {
		return this.p5accounts;
	}

	public void setP5accounts(Boolean p5accounts) {
		this.p5accounts = p5accounts;
	}

	public Boolean getP6accounts() {
		return this.p6accounts;
	}

	public void setP6accounts(Boolean p6accounts) {
		this.p6accounts = p6accounts;
	}

	public Boolean getPxaccounts() {
		return this.pxaccounts;
	}

	public void setPxaccounts(Boolean pxaccounts) {
		this.pxaccounts = pxaccounts;
	}

	public Set getAcctSum2s() {
		return this.acctSum2s;
	}

	public void setAcctSum2s(Set acctSum2s) {
		this.acctSum2s = acctSum2s;
	}

	public Set getAcctSum1s() {
		return this.acctSum1s;
	}

	public void setAcctSum1s(Set acctSum1s) {
		this.acctSum1s = acctSum1s;
	}

	public Set getAcctJobCodeChargeItems() {
		return this.acctJobCodeChargeItems;
	}

	public void setAcctJobCodeChargeItems(Set acctJobCodeChargeItems) {
		this.acctJobCodeChargeItems = acctJobCodeChargeItems;
	}

	public Set getAcctCustomerContracts() {
		return this.acctCustomerContracts;
	}

	public void setAcctCustomerContracts(Set acctCustomerContracts) {
		this.acctCustomerContracts = acctCustomerContracts;
	}

	public Boolean getCtaccounts() {
		return ctaccounts;
	}

	public void setCtaccounts(Boolean ctaccounts) {
		this.ctaccounts = ctaccounts;
	}

	public Boolean getTray5() {
		return tray5;
	}

	public void setTray5(Boolean tray5) {
		this.tray5 = tray5;
	}

	public Boolean getTray6() {
		return tray6;
	}

	public void setTray6(Boolean tray6) {
		this.tray6 = tray6;
	}

	public Boolean getTray7() {
		return tray7;
	}

	public void setTray7(Boolean tray7) {
		this.tray7 = tray7;
	}

	public Boolean getTray8() {
		return tray8;
	}

	public void setTray8(Boolean tray8) {
		this.tray8 = tray8;
	}

	public Integer getFubonCode() {
		return fubonCode;
	}

	public void setFubonCode(Integer fubonCode) {
		this.fubonCode = fubonCode;
	}

	public Boolean getNotSent() {
		return notSent;
	}

	public void setNotSent(Boolean notSent) {
		this.notSent = notSent;
	}

	public Boolean getZeroExpress() {
		return zeroExpress;
	}

	public void setZeroExpress(Boolean zeroExpress) {
		this.zeroExpress = zeroExpress;
	}
	
	
	

}