package com.salmat.jbm.hibernate;

import java.util.Date;

/**
 * JobBagSplite entity. @author MyEclipse Persistence Tools
 */

public class JobBagSplite implements java.io.Serializable {

	// Fields

	private String jobBagSpliteNo;
	private JobBag jobBag;
	private Integer lpZipCodeBegin;
	private Integer lpZipCodeEnd;
	private Integer lpZipCodeDiff;
	private Integer lpBlankBegin;
	private Integer lpBlankEnd;
	private Integer lpBlankDiff;
	private Integer lpPaperBegin;
	private Integer lpPaperEnd;
	private Integer lpPaperDiff;
	private Integer lpPagesSeqBegin;
	private Integer lpPagesSeqEnd;
	private Integer lpPagesSeqDiff;
	private Integer lpPagesSeqExtra;
	private Integer lpAccountSeqBegin;
	private Integer lpAccountSeqEnd;
	private Integer lpAccountSeqDiff;
	private String lpMachineName;
	private String lpCompletedUser;
	private String lpCompletedManager;
	private Date lpCompletedDateByUser;
	private Date lpCompletedDateByManager;
	private Boolean mpHasDamage;
	private String mpCompletedUser;
	private String mpCompletedManager;
	private Date mpCompletedDateByUser;
	private Date mpCompletedDateByManager;
	private String mpMachineName;
	private String lgCompletedUser;
	private String lgCompletedManager;
	private Date lgCompletedDateByUser;
	private Date lgCompletedDateByManager;
	private Integer printingCount;
	private Date latestPrintingDate;
	private String afpFilename;
	private String logFilename;
	private String lineData;
	private Integer accounts;
	private Integer pages;
	private Integer sheets;
	private Integer feeder2;
	private Integer feeder3;
	private Integer feeder4;
	private Integer feeder5;
	private Integer tray1;
	private Integer tray2;
	private Integer tray3;
	private Integer tray4;
	private Integer tray5;
	private Integer tray6;
	private Integer tray7;
	private Integer tray8;
	private Date receiveDate;
	private Integer p1accounts;
	private Integer p2accounts;
	private Integer p3accounts;
	private Integer p4accounts;
	private Integer p5accounts;
	private Integer p6accounts;
	private Integer pxaccounts;
	private String logFileSeq;

	// Constructors

	/** default constructor */
	public JobBagSplite() {
	}

	/** minimal constructor */
	public JobBagSplite(String jobBagSpliteNo) {
		this.jobBagSpliteNo = jobBagSpliteNo;
	}

	/** full constructor */
	public JobBagSplite(String jobBagSpliteNo, JobBag jobBag,
			Integer lpZipCodeBegin, Integer lpZipCodeEnd,
			Integer lpZipCodeDiff, Integer lpBlankBegin, Integer lpBlankEnd,
			Integer lpBlankDiff, Integer lpPaperBegin, Integer lpPaperEnd,
			Integer lpPaperDiff, Integer lpPagesSeqBegin,
			Integer lpPagesSeqEnd, Integer lpPagesSeqDiff,
			Integer lpPagesSeqExtra, Integer lpAccountSeqBegin,
			Integer lpAccountSeqEnd, Integer lpAccountSeqDiff,
			String lpMachineName, String lpCompletedUser,
			String lpCompletedManager, Date lpCompletedDateByUser,
			Date lpCompletedDateByManager, Boolean mpHasDamage,
			String mpCompletedUser, String mpCompletedManager,
			Date mpCompletedDateByUser, Date mpCompletedDateByManager,
			String mpMachineName, String lgCompletedUser,
			String lgCompletedManager, Date lgCompletedDateByUser,
			Date lgCompletedDateByManager, Integer printingCount,
			Date latestPrintingDate, String afpFilename, String logFilename,
			String lineData, Integer accounts, Integer pages, Integer sheets,
			Integer feeder2, Integer feeder3, Integer feeder4, Integer feeder5,
			Integer tray1, Integer tray2, Integer tray3, Integer tray4,
			Integer tray5, Integer tray6, Integer tray7, Integer tray8,
			Date receiveDate, Integer p1accounts, Integer p2accounts,
			Integer p3accounts, Integer p4accounts, Integer p5accounts,
			Integer p6accounts, Integer pxaccounts, String logFileSeq) {
		this.jobBagSpliteNo = jobBagSpliteNo;
		this.jobBag = jobBag;
		this.lpZipCodeBegin = lpZipCodeBegin;
		this.lpZipCodeEnd = lpZipCodeEnd;
		this.lpZipCodeDiff = lpZipCodeDiff;
		this.lpBlankBegin = lpBlankBegin;
		this.lpBlankEnd = lpBlankEnd;
		this.lpBlankDiff = lpBlankDiff;
		this.lpPaperBegin = lpPaperBegin;
		this.lpPaperEnd = lpPaperEnd;
		this.lpPaperDiff = lpPaperDiff;
		this.lpPagesSeqBegin = lpPagesSeqBegin;
		this.lpPagesSeqEnd = lpPagesSeqEnd;
		this.lpPagesSeqDiff = lpPagesSeqDiff;
		this.lpPagesSeqExtra = lpPagesSeqExtra;
		this.lpAccountSeqBegin = lpAccountSeqBegin;
		this.lpAccountSeqEnd = lpAccountSeqEnd;
		this.lpAccountSeqDiff = lpAccountSeqDiff;
		this.lpMachineName = lpMachineName;
		this.lpCompletedUser = lpCompletedUser;
		this.lpCompletedManager = lpCompletedManager;
		this.lpCompletedDateByUser = lpCompletedDateByUser;
		this.lpCompletedDateByManager = lpCompletedDateByManager;
		this.mpHasDamage = mpHasDamage;
		this.mpCompletedUser = mpCompletedUser;
		this.mpCompletedManager = mpCompletedManager;
		this.mpCompletedDateByUser = mpCompletedDateByUser;
		this.mpCompletedDateByManager = mpCompletedDateByManager;
		this.mpMachineName = mpMachineName;
		this.lgCompletedUser = lgCompletedUser;
		this.lgCompletedManager = lgCompletedManager;
		this.lgCompletedDateByUser = lgCompletedDateByUser;
		this.lgCompletedDateByManager = lgCompletedDateByManager;
		this.printingCount = printingCount;
		this.latestPrintingDate = latestPrintingDate;
		this.afpFilename = afpFilename;
		this.logFilename = logFilename;
		this.lineData = lineData;
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
		this.tray5 = tray5;
		this.tray6 = tray6;
		this.tray7 = tray7;
		this.tray8 = tray8;
		this.receiveDate = receiveDate;
		this.p1accounts = p1accounts;
		this.p2accounts = p2accounts;
		this.p3accounts = p3accounts;
		this.p4accounts = p4accounts;
		this.p5accounts = p5accounts;
		this.p6accounts = p6accounts;
		this.pxaccounts = pxaccounts;
		this.logFileSeq = logFileSeq;
	}

	// Property accessors

	public String getJobBagSpliteNo() {
		return this.jobBagSpliteNo;
	}

	public void setJobBagSpliteNo(String jobBagSpliteNo) {
		this.jobBagSpliteNo = jobBagSpliteNo;
	}

	public JobBag getJobBag() {
		return this.jobBag;
	}

	public void setJobBag(JobBag jobBag) {
		this.jobBag = jobBag;
	}

	public Integer getLpZipCodeBegin() {
		return this.lpZipCodeBegin;
	}

	public void setLpZipCodeBegin(Integer lpZipCodeBegin) {
		this.lpZipCodeBegin = lpZipCodeBegin;
	}

	public Integer getLpZipCodeEnd() {
		return this.lpZipCodeEnd;
	}

	public void setLpZipCodeEnd(Integer lpZipCodeEnd) {
		this.lpZipCodeEnd = lpZipCodeEnd;
	}

	public Integer getLpZipCodeDiff() {
		return this.lpZipCodeDiff;
	}

	public void setLpZipCodeDiff(Integer lpZipCodeDiff) {
		this.lpZipCodeDiff = lpZipCodeDiff;
	}

	public Integer getLpBlankBegin() {
		return this.lpBlankBegin;
	}

	public void setLpBlankBegin(Integer lpBlankBegin) {
		this.lpBlankBegin = lpBlankBegin;
	}

	public Integer getLpBlankEnd() {
		return this.lpBlankEnd;
	}

	public void setLpBlankEnd(Integer lpBlankEnd) {
		this.lpBlankEnd = lpBlankEnd;
	}

	public Integer getLpBlankDiff() {
		return this.lpBlankDiff;
	}

	public void setLpBlankDiff(Integer lpBlankDiff) {
		this.lpBlankDiff = lpBlankDiff;
	}

	public Integer getLpPaperBegin() {
		return this.lpPaperBegin;
	}

	public void setLpPaperBegin(Integer lpPaperBegin) {
		this.lpPaperBegin = lpPaperBegin;
	}

	public Integer getLpPaperEnd() {
		return this.lpPaperEnd;
	}

	public void setLpPaperEnd(Integer lpPaperEnd) {
		this.lpPaperEnd = lpPaperEnd;
	}

	public Integer getLpPaperDiff() {
		return this.lpPaperDiff;
	}

	public void setLpPaperDiff(Integer lpPaperDiff) {
		this.lpPaperDiff = lpPaperDiff;
	}

	public Integer getLpPagesSeqBegin() {
		return this.lpPagesSeqBegin;
	}

	public void setLpPagesSeqBegin(Integer lpPagesSeqBegin) {
		this.lpPagesSeqBegin = lpPagesSeqBegin;
	}

	public Integer getLpPagesSeqEnd() {
		return this.lpPagesSeqEnd;
	}

	public void setLpPagesSeqEnd(Integer lpPagesSeqEnd) {
		this.lpPagesSeqEnd = lpPagesSeqEnd;
	}

	public Integer getLpPagesSeqDiff() {
		return this.lpPagesSeqDiff;
	}

	public void setLpPagesSeqDiff(Integer lpPagesSeqDiff) {
		this.lpPagesSeqDiff = lpPagesSeqDiff;
	}

	public Integer getLpPagesSeqExtra() {
		return this.lpPagesSeqExtra;
	}

	public void setLpPagesSeqExtra(Integer lpPagesSeqExtra) {
		this.lpPagesSeqExtra = lpPagesSeqExtra;
	}

	public Integer getLpAccountSeqBegin() {
		return this.lpAccountSeqBegin;
	}

	public void setLpAccountSeqBegin(Integer lpAccountSeqBegin) {
		this.lpAccountSeqBegin = lpAccountSeqBegin;
	}

	public Integer getLpAccountSeqEnd() {
		return this.lpAccountSeqEnd;
	}

	public void setLpAccountSeqEnd(Integer lpAccountSeqEnd) {
		this.lpAccountSeqEnd = lpAccountSeqEnd;
	}

	public Integer getLpAccountSeqDiff() {
		return this.lpAccountSeqDiff;
	}

	public void setLpAccountSeqDiff(Integer lpAccountSeqDiff) {
		this.lpAccountSeqDiff = lpAccountSeqDiff;
	}

	public String getLpMachineName() {
		return this.lpMachineName;
	}

	public void setLpMachineName(String lpMachineName) {
		this.lpMachineName = lpMachineName;
	}

	public String getLpCompletedUser() {
		return this.lpCompletedUser;
	}

	public void setLpCompletedUser(String lpCompletedUser) {
		this.lpCompletedUser = lpCompletedUser;
	}

	public String getLpCompletedManager() {
		return this.lpCompletedManager;
	}

	public void setLpCompletedManager(String lpCompletedManager) {
		this.lpCompletedManager = lpCompletedManager;
	}

	public Date getLpCompletedDateByUser() {
		return this.lpCompletedDateByUser;
	}

	public void setLpCompletedDateByUser(Date lpCompletedDateByUser) {
		this.lpCompletedDateByUser = lpCompletedDateByUser;
	}

	public Date getLpCompletedDateByManager() {
		return this.lpCompletedDateByManager;
	}

	public void setLpCompletedDateByManager(Date lpCompletedDateByManager) {
		this.lpCompletedDateByManager = lpCompletedDateByManager;
	}

	public Boolean getMpHasDamage() {
		return this.mpHasDamage;
	}

	public void setMpHasDamage(Boolean mpHasDamage) {
		this.mpHasDamage = mpHasDamage;
	}

	public String getMpCompletedUser() {
		return this.mpCompletedUser;
	}

	public void setMpCompletedUser(String mpCompletedUser) {
		this.mpCompletedUser = mpCompletedUser;
	}

	public String getMpCompletedManager() {
		return this.mpCompletedManager;
	}

	public void setMpCompletedManager(String mpCompletedManager) {
		this.mpCompletedManager = mpCompletedManager;
	}

	public Date getMpCompletedDateByUser() {
		return this.mpCompletedDateByUser;
	}

	public void setMpCompletedDateByUser(Date mpCompletedDateByUser) {
		this.mpCompletedDateByUser = mpCompletedDateByUser;
	}

	public Date getMpCompletedDateByManager() {
		return this.mpCompletedDateByManager;
	}

	public void setMpCompletedDateByManager(Date mpCompletedDateByManager) {
		this.mpCompletedDateByManager = mpCompletedDateByManager;
	}

	public String getMpMachineName() {
		return this.mpMachineName;
	}

	public void setMpMachineName(String mpMachineName) {
		this.mpMachineName = mpMachineName;
	}

	public String getLgCompletedUser() {
		return this.lgCompletedUser;
	}

	public void setLgCompletedUser(String lgCompletedUser) {
		this.lgCompletedUser = lgCompletedUser;
	}

	public String getLgCompletedManager() {
		return this.lgCompletedManager;
	}

	public void setLgCompletedManager(String lgCompletedManager) {
		this.lgCompletedManager = lgCompletedManager;
	}

	public Date getLgCompletedDateByUser() {
		return this.lgCompletedDateByUser;
	}

	public void setLgCompletedDateByUser(Date lgCompletedDateByUser) {
		this.lgCompletedDateByUser = lgCompletedDateByUser;
	}

	public Date getLgCompletedDateByManager() {
		return this.lgCompletedDateByManager;
	}

	public void setLgCompletedDateByManager(Date lgCompletedDateByManager) {
		this.lgCompletedDateByManager = lgCompletedDateByManager;
	}

	public Integer getPrintingCount() {
		return this.printingCount;
	}

	public void setPrintingCount(Integer printingCount) {
		this.printingCount = printingCount;
	}

	public Date getLatestPrintingDate() {
		return this.latestPrintingDate;
	}

	public void setLatestPrintingDate(Date latestPrintingDate) {
		this.latestPrintingDate = latestPrintingDate;
	}

	public String getAfpFilename() {
		return this.afpFilename;
	}

	public void setAfpFilename(String afpFilename) {
		this.afpFilename = afpFilename;
	}

	public String getLogFilename() {
		return this.logFilename;
	}

	public void setLogFilename(String logFilename) {
		this.logFilename = logFilename;
	}

	public String getLineData() {
		return this.lineData;
	}

	public void setLineData(String lineData) {
		this.lineData = lineData;
	}

	public Integer getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Integer accounts) {
		this.accounts = accounts;
	}

	public Integer getPages() {
		return this.pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getSheets() {
		return this.sheets;
	}

	public void setSheets(Integer sheets) {
		this.sheets = sheets;
	}

	public Integer getFeeder2() {
		return this.feeder2;
	}

	public void setFeeder2(Integer feeder2) {
		this.feeder2 = feeder2;
	}

	public Integer getFeeder3() {
		return this.feeder3;
	}

	public void setFeeder3(Integer feeder3) {
		this.feeder3 = feeder3;
	}

	public Integer getFeeder4() {
		return this.feeder4;
	}

	public void setFeeder4(Integer feeder4) {
		this.feeder4 = feeder4;
	}

	public Integer getFeeder5() {
		return this.feeder5;
	}

	public void setFeeder5(Integer feeder5) {
		this.feeder5 = feeder5;
	}

	public Integer getTray1() {
		return this.tray1;
	}

	public void setTray1(Integer tray1) {
		this.tray1 = tray1;
	}

	public Integer getTray2() {
		return this.tray2;
	}

	public void setTray2(Integer tray2) {
		this.tray2 = tray2;
	}

	public Integer getTray3() {
		return this.tray3;
	}

	public void setTray3(Integer tray3) {
		this.tray3 = tray3;
	}

	public Integer getTray4() {
		return this.tray4;
	}

	public void setTray4(Integer tray4) {
		this.tray4 = tray4;
	}

	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Integer getP1accounts() {
		return this.p1accounts;
	}

	public void setP1accounts(Integer p1accounts) {
		this.p1accounts = p1accounts;
	}

	public Integer getP2accounts() {
		return this.p2accounts;
	}

	public void setP2accounts(Integer p2accounts) {
		this.p2accounts = p2accounts;
	}

	public Integer getP3accounts() {
		return this.p3accounts;
	}

	public void setP3accounts(Integer p3accounts) {
		this.p3accounts = p3accounts;
	}

	public Integer getP4accounts() {
		return this.p4accounts;
	}

	public void setP4accounts(Integer p4accounts) {
		this.p4accounts = p4accounts;
	}

	public Integer getP5accounts() {
		return this.p5accounts;
	}

	public void setP5accounts(Integer p5accounts) {
		this.p5accounts = p5accounts;
	}

	public Integer getP6accounts() {
		return this.p6accounts;
	}

	public void setP6accounts(Integer p6accounts) {
		this.p6accounts = p6accounts;
	}

	public Integer getPxaccounts() {
		return this.pxaccounts;
	}

	public void setPxaccounts(Integer pxaccounts) {
		this.pxaccounts = pxaccounts;
	}

	public Integer getTray5() {
		return tray5;
	}

	public void setTray5(Integer tray5) {
		this.tray5 = tray5;
	}

	public Integer getTray6() {
		return tray6;
	}

	public void setTray6(Integer tray6) {
		this.tray6 = tray6;
	}

	public Integer getTray7() {
		return tray7;
	}

	public void setTray7(Integer tray7) {
		this.tray7 = tray7;
	}

	public Integer getTray8() {
		return tray8;
	}

	public void setTray8(Integer tray8) {
		this.tray8 = tray8;
	}

	public String getLogFileSeq() {
		return logFileSeq;
	}

	public void setLogFileSeq(String logFileSeq) {
		this.logFileSeq = logFileSeq;
	}
	

}