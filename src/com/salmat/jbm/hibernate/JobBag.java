package com.salmat.jbm.hibernate;

import java.text.NumberFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * JobBag entity. @author MyEclipse Persistence Tools
 */

public class JobBag implements java.io.Serializable {

	// Fields

	private String jobBagNo;
	private Code codeByLpCodePrintType;
	private Lcinfo lcinfo;
	private Code codeByCodeMailToPostoffice;
	private Employee employee;
	private Lginfo lginfo;
	private JobCode jobCode;
	private Code codeByPsCodePsType;
	private Code codeByLgCodeMailToArea3;
	private Code codeByLgCodeMailToArea2;
	private Code codeByLgCodeMailToArea5;
	private Code codeByCodeLgType;
	private Code codeByLgCodeMailToArea4;
	private Returninfo returninfo;
	private Code codeByLgCodeMailToArea1;
	private Mpinfo mpinfoByIdfMpNo3;
	private Mpinfo mpinfoByIdfMpNo2;
	private Code codeByLgCodeMailToArea6;
	private Mpinfo mpinfoByIdfMpNo1;
	private Code codeByLpCodePrinter;
	private Code codeByCodeJobCodeType;
	private Code codeByCodeMailCategory;
	private Lpinfo lpinfoByIdfLpNo1;
	private Lpinfo lpinfoByIdfLpNo2;
	private Lpinfo lpinfoByIdfLpNo3;
	private Psinfo psinfo;
	private Customer customer;
	private String jobCodeId;
	private String progNm;
	private Boolean isLp;
	private Boolean isMps;
	private Boolean isLg;
	private Boolean isEdd;
	private String mpDmPs;
	private String dispatchTime;
	private String deadTime;
	private Boolean isEnabledContract;
	private Boolean isConvertResult;
	private String lpNote;
	private String mpNote;
	private String psNote;
	private String dmNote;
	private String dispatchType;
	private String lgNote;
	private String filename;
	private Boolean useLg;
	private Date cycleDate;
	private Date receiveDate;
	private Date deadLine;
	private Integer accounts;
	private Integer pages;
	private Integer sheets;
	private Integer spliteCount;
	private String jobBagStatus;
	private String prevStatus;
	private String amxName;
	private String afpName;
	private Boolean isDamage;
	private Boolean hasDamage;
	private Boolean isDeleted;
	private String deletedReason;
	private Date deletedDate;
	private Date lcSentDate;
	private String postsStatementNo;
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
	private String logFilename;
	private Date createDate;
	private Date completedDate;
	private Integer damageCount;
	private String lpProdmemo;
	private String lpPcode1;
	private String lpPcode2;
	private String lpPcode3;
	private String lpPcode4;
	private String lpPcode5;
	private String lpPcode6;
	private String lpPcode7;
	private String lpPcode8;
	private String lpRemark;
	private String mpProdmemo;
	private String mpPrintCode;
	private Boolean mpStampSetFg;
	private Boolean mpZipFg;
	private Boolean mpMp1Iflag;
	private Boolean mpMp2Iflag;
	private Boolean mpMp3Iflag;
	private Boolean mpMp4Iflag;
	private String mpMp1Word;
	private String mpMp2Word;
	private String mpMp3Word;
	private String mpMp4Word;
	private String mpRemark;
	private String psProdmemo;
	private String psRemark;
	private Boolean psZipFg;
	private String lgProgNm;
	private String lgMailToAreaDisplay;
	private Boolean lgPList;
	private String lcProgNm;
	private Boolean lcPList;
	private String lcTemplate;
	private String retReturnAddress;
	private String retUserCompany;
	private String retUserName;
	private String retUserTel;
	private String logFileSeq;
	private String customerDept;
	private Integer pages2;
	private Integer pages3;
	private Integer VPages2;
	private Integer VPages3;
	private Integer VPages2Ori;
	private Integer VPages3Ori;
	private Integer pagesOri;
	private Integer pages2Ori;
	private Integer pages3Ori;
	
	private Integer p1accounts;
	private Integer p2accounts;
	private Integer p3accounts;
	private Integer p4accounts;
	private Integer p5accounts;
	private Integer p6accounts;
	private Integer pxaccounts;
	private Boolean createByCycle;
	private Integer mrdf;
	private Integer feeder6;

	private Integer feeder7;
	private Boolean ctSpecialSplite;
	private Integer ctAcctsOri;
	
	private String notes;
	private Boolean lgDisplayQty;
	private Boolean custConfirm;
	private Date custConfirmDate;
	private String mailType;
	private Boolean fromDamage;
	private Integer notSent;
			
	private Set jobBagSplites = new HashSet(0);
	NumberFormat nf = NumberFormat.getInstance();
	
	// Constructors

	/** default constructor */
	public JobBag() {
		nf.setMaximumIntegerDigits(3);
		nf.setMinimumIntegerDigits(3);
	}

	/** minimal constructor */
	public JobBag(String jobBagNo) {
		nf.setMaximumIntegerDigits(3);
		nf.setMinimumIntegerDigits(3);
		this.jobBagNo = jobBagNo;
	}

	/** full constructor */
	public JobBag(String jobBagNo, Code codeByLpCodePrintType, Lcinfo lcinfo,
			Code codeByCodeMailToPostoffice, Employee employee, Lginfo lginfo,
			JobCode jobCode, Code codeByPsCodePsType,
			Code codeByLgCodeMailToArea3, Code codeByLgCodeMailToArea2,
			Code codeByLgCodeMailToArea5, Code codeByCodeLgType,
			Code codeByLgCodeMailToArea4, Returninfo returninfo,
			Code codeByLgCodeMailToArea1, Mpinfo mpinfoByIdfMpNo3,
			Mpinfo mpinfoByIdfMpNo2, Code codeByLgCodeMailToArea6,
			Mpinfo mpinfoByIdfMpNo1, Code codeByLpCodePrinter,
			Code codeByCodeJobCodeType, Code codeByCodeMailCategory,
			Lpinfo lpinfoByIdfLpNo1, Lpinfo lpinfoByIdfLpNo2,
			Lpinfo lpinfoByIdfLpNo3, Psinfo psinfo, Customer customer,
			String jobCodeId, String progNm, Boolean isLp, Boolean isMps,
			Boolean isLg, Boolean isEdd, String mpDmPs, String dispatchTime,
			String deadTime, Boolean isEnabledContract,
			Boolean isConvertResult, String lpNote, String mpNote,
			String psNote, String dmNote, String dispatchType, String lgNote,
			String filename, Boolean useLg, Date cycleDate, Date receiveDate,
			Date deadLine, Integer accounts, Integer pages, Integer sheets,
			Integer pagesOri, Integer pages2Ori, Integer pages3Ori,
			Integer spliteCount, String jobBagStatus, String prevStatus, String amxName,
			String afpName, Boolean isDamage, Boolean hasDamage,
			Boolean isDeleted, String deletedReason, Date deletedDate,
			Date lcSentDate, String postsStatementNo, Integer feeder2,
			Integer feeder3, Integer feeder4, Integer feeder5, 
			Integer tray1, Integer tray2, Integer tray3, Integer tray4, 
			Integer tray5, Integer tray6, Integer tray7, Integer tray8,
			String logFilename, Date createDate, Date completedDate,
			Integer damageCount, String lpProdmemo, String lpPcode1,
			String lpPcode2, String lpPcode3, String lpPcode4,
			String lpPcode5,
			String lpPcode6, String lpPcode7, String lpPcode8,
			String lpRemark,
			String mpProdmemo, String mpPrintCode, Boolean mpStampSetFg,
			Boolean mpZipFg, Boolean mpMp1Iflag, Boolean mpMp2Iflag,
			Boolean mpMp3Iflag, Boolean mpMp4Iflag, String mpMp1Word,
			String mpMp2Word, String mpMp3Word, String mpMp4Word,
			String mpRemark, String psProdmemo, String psRemark,
			Boolean psZipFg, String lgProgNm, String lgMailToAreaDisplay,
			Boolean lgPList, String lcProgNm, Boolean lcPList,
			String lcTemplate, String retReturnAddress, String retUserCompany,
			String retUserName, String retUserTel, String logFileSeq,
			String customerDept, Integer pages2, Integer pages3,
			Integer VPages2, Integer VPages3, Integer vPages2Ori, Integer vPages3Ori,
			Integer p1accounts,
			Integer p2accounts, Integer p3accounts, Integer p4accounts,
			Integer p5accounts, Integer p6accounts, Integer pxaccounts,
			Boolean createByCycle, Integer mrdf, Integer feeder6,
			Integer feeder7, Set jobBagSplites, Integer ctAcctsOri, String notes, Boolean lgDisplayQty) {
		
		this.jobBagNo = jobBagNo;
		this.codeByLpCodePrintType = codeByLpCodePrintType;
		this.lcinfo = lcinfo;
		this.codeByCodeMailToPostoffice = codeByCodeMailToPostoffice;
		this.employee = employee;
		this.lginfo = lginfo;
		this.jobCode = jobCode;
		this.codeByPsCodePsType = codeByPsCodePsType;
		this.codeByLgCodeMailToArea3 = codeByLgCodeMailToArea3;
		this.codeByLgCodeMailToArea2 = codeByLgCodeMailToArea2;
		this.codeByLgCodeMailToArea5 = codeByLgCodeMailToArea5;
		this.codeByCodeLgType = codeByCodeLgType;
		this.codeByLgCodeMailToArea4 = codeByLgCodeMailToArea4;
		this.returninfo = returninfo;
		this.codeByLgCodeMailToArea1 = codeByLgCodeMailToArea1;
		this.mpinfoByIdfMpNo3 = mpinfoByIdfMpNo3;
		this.mpinfoByIdfMpNo2 = mpinfoByIdfMpNo2;
		this.codeByLgCodeMailToArea6 = codeByLgCodeMailToArea6;
		this.mpinfoByIdfMpNo1 = mpinfoByIdfMpNo1;
		this.codeByLpCodePrinter = codeByLpCodePrinter;
		this.codeByCodeJobCodeType = codeByCodeJobCodeType;
		this.codeByCodeMailCategory = codeByCodeMailCategory;
		this.lpinfoByIdfLpNo1 = lpinfoByIdfLpNo1;
		this.lpinfoByIdfLpNo2 = lpinfoByIdfLpNo2;
		this.lpinfoByIdfLpNo3 = lpinfoByIdfLpNo3;
		this.psinfo = psinfo;
		this.customer = customer;
		this.jobCodeId = jobCodeId;
		this.progNm = progNm;
		this.isLp = isLp;
		this.isMps = isMps;
		this.isLg = isLg;
		this.isEdd = isEdd;
		this.mpDmPs = mpDmPs;
		this.dispatchTime = dispatchTime;
		this.deadTime = deadTime;
		this.isEnabledContract = isEnabledContract;
		this.isConvertResult = isConvertResult;
		this.lpNote = lpNote;
		this.mpNote = mpNote;
		this.psNote = psNote;
		this.dmNote = dmNote;
		this.dispatchType = dispatchType;
		this.lgNote = lgNote;
		this.filename = filename;
		this.useLg = useLg;
		this.cycleDate = cycleDate;
		this.receiveDate = receiveDate;
		this.deadLine = deadLine;
		this.accounts = accounts;
		this.pages = pages;
		this.sheets = sheets;
		this.spliteCount = spliteCount;
		this.jobBagStatus = jobBagStatus;
		this.prevStatus = prevStatus;
		this.amxName = amxName;
		this.afpName = afpName;
		this.isDamage = isDamage;
		this.hasDamage = hasDamage;
		this.isDeleted = isDeleted;
		this.deletedReason = deletedReason;
		this.deletedDate = deletedDate;
		this.lcSentDate = lcSentDate;
		this.postsStatementNo = postsStatementNo;
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
		this.logFilename = logFilename;
		this.createDate = createDate;
		this.completedDate = completedDate;
		this.damageCount = damageCount;
		this.lpProdmemo = lpProdmemo;
		this.lpPcode1 = lpPcode1;
		this.lpPcode2 = lpPcode2;
		this.lpPcode3 = lpPcode3;
		this.lpPcode4 = lpPcode4;
		this.lpPcode5 = lpPcode5;
		this.lpPcode6 = lpPcode6;
		this.lpPcode7 = lpPcode7;
		this.lpPcode8 = lpPcode8;
		this.lpRemark = lpRemark;
		this.mpProdmemo = mpProdmemo;
		this.mpPrintCode = mpPrintCode;
		this.mpStampSetFg = mpStampSetFg;
		this.mpZipFg = mpZipFg;
		this.mpMp1Iflag = mpMp1Iflag;
		this.mpMp2Iflag = mpMp2Iflag;
		this.mpMp3Iflag = mpMp3Iflag;
		this.mpMp4Iflag = mpMp4Iflag;
		this.mpMp1Word = mpMp1Word;
		this.mpMp2Word = mpMp2Word;
		this.mpMp3Word = mpMp3Word;
		this.mpMp4Word = mpMp4Word;
		this.mpRemark = mpRemark;
		this.psProdmemo = psProdmemo;
		this.psRemark = psRemark;
		this.psZipFg = psZipFg;
		this.lgProgNm = lgProgNm;
		this.lgMailToAreaDisplay = lgMailToAreaDisplay;
		this.lgPList = lgPList;
		this.lcProgNm = lcProgNm;
		this.lcPList = lcPList;
		this.lcTemplate = lcTemplate;
		this.retReturnAddress = retReturnAddress;
		this.retUserCompany = retUserCompany;
		this.retUserName = retUserName;
		this.retUserTel = retUserTel;
		this.logFileSeq = logFileSeq;
		this.customerDept = customerDept;
		this.pages2 = pages2;
		this.pages3 = pages3;
		this.VPages2 = VPages2;
		this.VPages3 = VPages3;
		this.VPages2Ori = vPages2Ori;
		this.VPages3Ori = vPages3Ori;
		this.p1accounts = p1accounts;
		this.p2accounts = p2accounts;
		this.p3accounts = p3accounts;
		this.p4accounts = p4accounts;
		this.p5accounts = p5accounts;
		this.p6accounts = p6accounts;
		this.pxaccounts = pxaccounts;
		this.createByCycle = createByCycle;
		this.mrdf = mrdf;
		this.feeder6 = feeder6;
		this.feeder7 = feeder7;
		this.jobBagSplites = jobBagSplites;
		this.pagesOri = pagesOri;
		this.pages2Ori = pages2Ori;
		this.pages3Ori = pages3Ori;
		this.ctAcctsOri = ctAcctsOri;
		this.notes = notes;
		this.lgDisplayQty = lgDisplayQty;;
		nf.setMaximumIntegerDigits(3);
		nf.setMinimumIntegerDigits(3);
	}

	// Property accessors

	public String getJobBagNo() {
		return this.jobBagNo;
	}

	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}

	public Code getCodeByLpCodePrintType() {
		return this.codeByLpCodePrintType;
	}

	public void setCodeByLpCodePrintType(Code codeByLpCodePrintType) {
		this.codeByLpCodePrintType = codeByLpCodePrintType;
	}

	public Lcinfo getLcinfo() {
		return this.lcinfo;
	}

	public void setLcinfo(Lcinfo lcinfo) {
		this.lcinfo = lcinfo;
	}

	public Code getCodeByCodeMailToPostoffice() {
		return this.codeByCodeMailToPostoffice;
	}

	public void setCodeByCodeMailToPostoffice(Code codeByCodeMailToPostoffice) {
		this.codeByCodeMailToPostoffice = codeByCodeMailToPostoffice;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Lginfo getLginfo() {
		return this.lginfo;
	}

	public void setLginfo(Lginfo lginfo) {
		this.lginfo = lginfo;
	}

	public JobCode getJobCode() {
		return this.jobCode;
	}

	public void setJobCode(JobCode jobCode) {
		this.jobCode = jobCode;
	}

	public Code getCodeByPsCodePsType() {
		return this.codeByPsCodePsType;
	}

	public void setCodeByPsCodePsType(Code codeByPsCodePsType) {
		this.codeByPsCodePsType = codeByPsCodePsType;
	}

	public Code getCodeByLgCodeMailToArea3() {
		return this.codeByLgCodeMailToArea3;
	}

	public void setCodeByLgCodeMailToArea3(Code codeByLgCodeMailToArea3) {
		this.codeByLgCodeMailToArea3 = codeByLgCodeMailToArea3;
	}

	public Code getCodeByLgCodeMailToArea2() {
		return this.codeByLgCodeMailToArea2;
	}

	public void setCodeByLgCodeMailToArea2(Code codeByLgCodeMailToArea2) {
		this.codeByLgCodeMailToArea2 = codeByLgCodeMailToArea2;
	}

	public Code getCodeByLgCodeMailToArea5() {
		return this.codeByLgCodeMailToArea5;
	}

	public void setCodeByLgCodeMailToArea5(Code codeByLgCodeMailToArea5) {
		this.codeByLgCodeMailToArea5 = codeByLgCodeMailToArea5;
	}

	public Code getCodeByCodeLgType() {
		return this.codeByCodeLgType;
	}

	public void setCodeByCodeLgType(Code codeByCodeLgType) {
		this.codeByCodeLgType = codeByCodeLgType;
	}

	public Code getCodeByLgCodeMailToArea4() {
		return this.codeByLgCodeMailToArea4;
	}

	public void setCodeByLgCodeMailToArea4(Code codeByLgCodeMailToArea4) {
		this.codeByLgCodeMailToArea4 = codeByLgCodeMailToArea4;
	}

	public Returninfo getReturninfo() {
		return this.returninfo;
	}

	public void setReturninfo(Returninfo returninfo) {
		this.returninfo = returninfo;
	}

	public Code getCodeByLgCodeMailToArea1() {
		return this.codeByLgCodeMailToArea1;
	}

	public void setCodeByLgCodeMailToArea1(Code codeByLgCodeMailToArea1) {
		this.codeByLgCodeMailToArea1 = codeByLgCodeMailToArea1;
	}

	public Mpinfo getMpinfoByIdfMpNo3() {
		return this.mpinfoByIdfMpNo3;
	}

	public void setMpinfoByIdfMpNo3(Mpinfo mpinfoByIdfMpNo3) {
		this.mpinfoByIdfMpNo3 = mpinfoByIdfMpNo3;
	}

	public Mpinfo getMpinfoByIdfMpNo2() {
		return this.mpinfoByIdfMpNo2;
	}

	public void setMpinfoByIdfMpNo2(Mpinfo mpinfoByIdfMpNo2) {
		this.mpinfoByIdfMpNo2 = mpinfoByIdfMpNo2;
	}

	public Code getCodeByLgCodeMailToArea6() {
		return this.codeByLgCodeMailToArea6;
	}

	public void setCodeByLgCodeMailToArea6(Code codeByLgCodeMailToArea6) {
		this.codeByLgCodeMailToArea6 = codeByLgCodeMailToArea6;
	}

	public Mpinfo getMpinfoByIdfMpNo1() {
		return this.mpinfoByIdfMpNo1;
	}

	public void setMpinfoByIdfMpNo1(Mpinfo mpinfoByIdfMpNo1) {
		this.mpinfoByIdfMpNo1 = mpinfoByIdfMpNo1;
	}

	public Code getCodeByLpCodePrinter() {
		return this.codeByLpCodePrinter;
	}

	public void setCodeByLpCodePrinter(Code codeByLpCodePrinter) {
		this.codeByLpCodePrinter = codeByLpCodePrinter;
	}

	public Code getCodeByCodeJobCodeType() {
		return this.codeByCodeJobCodeType;
	}

	public void setCodeByCodeJobCodeType(Code codeByCodeJobCodeType) {
		this.codeByCodeJobCodeType = codeByCodeJobCodeType;
	}

	public Code getCodeByCodeMailCategory() {
		return this.codeByCodeMailCategory;
	}

	public void setCodeByCodeMailCategory(Code codeByCodeMailCategory) {
		this.codeByCodeMailCategory = codeByCodeMailCategory;
	}

	public Lpinfo getLpinfoByIdfLpNo1() {
		return this.lpinfoByIdfLpNo1;
	}

	public void setLpinfoByIdfLpNo1(Lpinfo lpinfoByIdfLpNo1) {
		this.lpinfoByIdfLpNo1 = lpinfoByIdfLpNo1;
	}

	public Lpinfo getLpinfoByIdfLpNo2() {
		return this.lpinfoByIdfLpNo2;
	}

	public void setLpinfoByIdfLpNo2(Lpinfo lpinfoByIdfLpNo2) {
		this.lpinfoByIdfLpNo2 = lpinfoByIdfLpNo2;
	}

	public Lpinfo getLpinfoByIdfLpNo3() {
		return this.lpinfoByIdfLpNo3;
	}

	public void setLpinfoByIdfLpNo3(Lpinfo lpinfoByIdfLpNo3) {
		this.lpinfoByIdfLpNo3 = lpinfoByIdfLpNo3;
	}

	public Psinfo getPsinfo() {
		return this.psinfo;
	}

	public void setPsinfo(Psinfo psinfo) {
		this.psinfo = psinfo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getJobCodeId() {
		return this.jobCodeId;
	}

	public void setJobCodeId(String jobCodeId) {
		this.jobCodeId = jobCodeId;
	}

	public String getProgNm() {
		return this.progNm;
	}

	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}

	public Boolean getIsLp() {
		return this.isLp;
	}

	public void setIsLp(Boolean isLp) {
		this.isLp = isLp;
	}

	public Boolean getIsMps() {
		return this.isMps;
	}

	public void setIsMps(Boolean isMps) {
		this.isMps = isMps;
	}

	public Boolean getIsLg() {
		return this.isLg;
	}

	public void setIsLg(Boolean isLg) {
		this.isLg = isLg;
	}

	public Boolean getIsEdd() {
		return this.isEdd;
	}

	public void setIsEdd(Boolean isEdd) {
		this.isEdd = isEdd;
	}

	public String getMpDmPs() {
		return this.mpDmPs;
	}

	public void setMpDmPs(String mpDmPs) {
		this.mpDmPs = mpDmPs;
	}

	public String getDispatchTime() {
		return this.dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getDeadTime() {
		return this.deadTime;
	}

	public void setDeadTime(String deadTime) {
		this.deadTime = deadTime;
	}

	public Boolean getIsEnabledContract() {
		return this.isEnabledContract;
	}

	public void setIsEnabledContract(Boolean isEnabledContract) {
		this.isEnabledContract = isEnabledContract;
	}

	public Boolean getIsConvertResult() {
		return this.isConvertResult;
	}

	public void setIsConvertResult(Boolean isConvertResult) {
		this.isConvertResult = isConvertResult;
	}

	public String getLpNote() {
		return this.lpNote;
	}

	public void setLpNote(String lpNote) {
		this.lpNote = lpNote;
	}

	public String getMpNote() {
		return this.mpNote;
	}

	public void setMpNote(String mpNote) {
		this.mpNote = mpNote;
	}

	public String getPsNote() {
		return this.psNote;
	}

	public void setPsNote(String psNote) {
		this.psNote = psNote;
	}

	public String getDmNote() {
		return this.dmNote;
	}

	public void setDmNote(String dmNote) {
		this.dmNote = dmNote;
	}

	public String getDispatchType() {
		return this.dispatchType;
	}

	public void setDispatchType(String dispatchType) {
		this.dispatchType = dispatchType;
	}

	public String getLgNote() {
		return this.lgNote;
	}

	public void setLgNote(String lgNote) {
		this.lgNote = lgNote;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Boolean getUseLg() {
		return this.useLg;
	}

	public void setUseLg(Boolean useLg) {
		this.useLg = useLg;
	}

	public Date getCycleDate() {
		return this.cycleDate;
	}

	public void setCycleDate(Date cycleDate) {
		this.cycleDate = cycleDate;
	}

	public Date getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getDeadLine() {
		return this.deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public Integer getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Integer accounts) {		
		if((getCtAcctsOri() == null || getCtAcctsOri().intValue() == 0) && accounts != null && accounts.intValue() != 0){
			setCtAcctsOri(accounts);
		}else if(getCtAcctsOri() == null  && this.accounts == null){
			//如果都沒有值時，就都塞入
			setCtAcctsOri(accounts);
		}else if(this.accounts != null && getCtAcctsOri() != null && this.accounts.intValue() == getCtAcctsOri().intValue()){
			//如果兩個都有值，而且兩個都相等的話，代表之前有塞過，而且是ctAccountsOri有塞入相同值，此時同步更新
			//ctAccountsOri和accounts會不同只有來自bank card，ctAccountsOri會另外塞入
			setCtAcctsOri(accounts);
		}
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

	public Integer getSpliteCount() {
		return this.spliteCount;
	}

	public void setSpliteCount(Integer spliteCount) {
		this.spliteCount = spliteCount;
	}

	public String getJobBagStatus() {
		return this.jobBagStatus;
	}

	public void setJobBagStatus(String jobBagStatus) {
		//相同狀態不更新prevstatus
		if(jobBagStatus != null && !jobBagStatus.equals(this.jobBagStatus))
		   setPrevStatus(this.jobBagStatus);
		this.jobBagStatus = jobBagStatus;
	}

	public String getAmxName() {
		return this.amxName;
	}

	public void setAmxName(String amxName) {
		this.amxName = amxName;
	}

	public String getAfpName() {
		return this.afpName;
	}

	public void setAfpName(String afpName) {
		this.afpName = afpName;
	}

	public Boolean getIsDamage() {
		return this.isDamage;
	}

	public void setIsDamage(Boolean isDamage) {
		this.isDamage = isDamage;
	}

	public Boolean getHasDamage() {
		return this.hasDamage;
	}

	public void setHasDamage(Boolean hasDamage) {
		this.hasDamage = hasDamage;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDeletedReason() {
		return this.deletedReason;
	}

	public void setDeletedReason(String deletedReason) {
		this.deletedReason = deletedReason;
	}

	public Date getDeletedDate() {
		return this.deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Date getLcSentDate() {
		return this.lcSentDate;
	}

	public void setLcSentDate(Date lcSentDate) {
		this.lcSentDate = lcSentDate;
	}

	public String getPostsStatementNo() {
		return this.postsStatementNo;
	}

	public void setPostsStatementNo(String postsStatementNo) {
		this.postsStatementNo = postsStatementNo;
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

	public Integer getTray5() {
		return this.tray5;
	}

	public void setTray5(Integer tray5) {
		this.tray5 = tray5;
	}

	public String getLogFilename() {
		return this.logFilename;
	}

	public void setLogFilename(String logFilename) {
		this.logFilename = logFilename;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Integer getDamageCount() {
		return this.damageCount;
	}

	public void setDamageCount(Integer damageCount) {
		this.damageCount = damageCount;
	}

	public String getLpProdmemo() {
		return this.lpProdmemo;
	}

	public void setLpProdmemo(String lpProdmemo) {
		this.lpProdmemo = lpProdmemo;
	}

	public String getLpPcode1() {
		return this.lpPcode1;
	}

	public void setLpPcode1(String lpPcode1) {
		this.lpPcode1 = lpPcode1;
	}

	public String getLpPcode2() {
		return this.lpPcode2;
	}

	public void setLpPcode2(String lpPcode2) {
		this.lpPcode2 = lpPcode2;
	}

	public String getLpPcode3() {
		return this.lpPcode3;
	}

	public void setLpPcode3(String lpPcode3) {
		this.lpPcode3 = lpPcode3;
	}

	public String getLpPcode4() {
		return this.lpPcode4;
	}

	public void setLpPcode4(String lpPcode4) {
		this.lpPcode4 = lpPcode4;
	}

	public String getLpRemark() {
		return this.lpRemark;
	}

	public void setLpRemark(String lpRemark) {
		this.lpRemark = lpRemark;
	}

	public String getMpProdmemo() {
		return this.mpProdmemo;
	}

	public void setMpProdmemo(String mpProdmemo) {
		this.mpProdmemo = mpProdmemo;
	}

	public String getMpPrintCode() {
		return this.mpPrintCode;
	}

	public void setMpPrintCode(String mpPrintCode) {
		this.mpPrintCode = mpPrintCode;
	}

	public Boolean getMpStampSetFg() {
		return this.mpStampSetFg;
	}

	public void setMpStampSetFg(Boolean mpStampSetFg) {
		this.mpStampSetFg = mpStampSetFg;
	}

	public Boolean getMpZipFg() {
		return this.mpZipFg;
	}

	public void setMpZipFg(Boolean mpZipFg) {
		this.mpZipFg = mpZipFg;
	}

	public Boolean getMpMp1Iflag() {
		return this.mpMp1Iflag;
	}

	public void setMpMp1Iflag(Boolean mpMp1Iflag) {
		this.mpMp1Iflag = mpMp1Iflag;
	}

	public Boolean getMpMp2Iflag() {
		return this.mpMp2Iflag;
	}

	public void setMpMp2Iflag(Boolean mpMp2Iflag) {
		this.mpMp2Iflag = mpMp2Iflag;
	}

	public Boolean getMpMp3Iflag() {
		return this.mpMp3Iflag;
	}

	public void setMpMp3Iflag(Boolean mpMp3Iflag) {
		this.mpMp3Iflag = mpMp3Iflag;
	}

	public Boolean getMpMp4Iflag() {
		return this.mpMp4Iflag;
	}

	public void setMpMp4Iflag(Boolean mpMp4Iflag) {
		this.mpMp4Iflag = mpMp4Iflag;
	}

	public String getMpMp1Word() {
		return this.mpMp1Word;
	}

	public void setMpMp1Word(String mpMp1Word) {
		this.mpMp1Word = mpMp1Word;
	}

	public String getMpMp2Word() {
		return this.mpMp2Word;
	}

	public void setMpMp2Word(String mpMp2Word) {
		this.mpMp2Word = mpMp2Word;
	}

	public String getMpMp3Word() {
		return this.mpMp3Word;
	}

	public void setMpMp3Word(String mpMp3Word) {
		this.mpMp3Word = mpMp3Word;
	}

	public String getMpMp4Word() {
		return this.mpMp4Word;
	}

	public void setMpMp4Word(String mpMp4Word) {
		this.mpMp4Word = mpMp4Word;
	}

	public String getMpRemark() {
		return this.mpRemark;
	}

	public void setMpRemark(String mpRemark) {
		this.mpRemark = mpRemark;
	}

	public String getPsProdmemo() {
		return this.psProdmemo;
	}

	public void setPsProdmemo(String psProdmemo) {
		this.psProdmemo = psProdmemo;
	}

	public String getPsRemark() {
		return this.psRemark;
	}

	public void setPsRemark(String psRemark) {
		this.psRemark = psRemark;
	}

	public Boolean getPsZipFg() {
		return this.psZipFg;
	}

	public void setPsZipFg(Boolean psZipFg) {
		this.psZipFg = psZipFg;
	}

	public String getLgProgNm() {
		return this.lgProgNm;
	}

	public void setLgProgNm(String lgProgNm) {
		this.lgProgNm = lgProgNm;
	}

	public String getLgMailToAreaDisplay() {
		return this.lgMailToAreaDisplay;
	}

	public void setLgMailToAreaDisplay(String lgMailToAreaDisplay) {
		this.lgMailToAreaDisplay = lgMailToAreaDisplay;
	}

	public Boolean getLgPList() {
		return this.lgPList;
	}

	public void setLgPList(Boolean lgPList) {
		this.lgPList = lgPList;
	}

	public String getLcProgNm() {
		return this.lcProgNm;
	}

	public void setLcProgNm(String lcProgNm) {
		this.lcProgNm = lcProgNm;
	}

	public Boolean getLcPList() {
		return this.lcPList;
	}

	public void setLcPList(Boolean lcPList) {
		this.lcPList = lcPList;
	}

	public String getLcTemplate() {
		return this.lcTemplate;
	}

	public void setLcTemplate(String lcTemplate) {
		this.lcTemplate = lcTemplate;
	}

	public String getRetReturnAddress() {
		return this.retReturnAddress;
	}

	public void setRetReturnAddress(String retReturnAddress) {
		this.retReturnAddress = retReturnAddress;
	}

	public String getRetUserCompany() {
		return this.retUserCompany;
	}

	public void setRetUserCompany(String retUserCompany) {
		this.retUserCompany = retUserCompany;
	}

	public String getRetUserName() {
		return this.retUserName;
	}

	public void setRetUserName(String retUserName) {
		this.retUserName = retUserName;
	}

	public String getRetUserTel() {
		return this.retUserTel;
	}

	public void setRetUserTel(String retUserTel) {
		this.retUserTel = retUserTel;
	}

	public String getLogFileSeq() {
		return this.logFileSeq;
	}

	public void setLogFileSeq(String logFileSeq) {
	    int logseq; 
	    try{
	       logseq = Integer.parseInt(logFileSeq);
	    }catch(Exception e){
	       logseq = 1;
	    }
	     
		this.logFileSeq = nf.format(logseq);
	}

	public String getCustomerDept() {
		return this.customerDept;
	}

	public void setCustomerDept(String customerDept) {
		this.customerDept = customerDept;
	}

	public Integer getPages2() {
		return this.pages2;
	}

	public void setPages2(Integer pages2) {
		this.pages2 = pages2;
	}

	public Integer getPages3() {
		return this.pages3;
	}

	public void setPages3(Integer pages3) {
		this.pages3 = pages3;
	}

	public Integer getVPages2() {
		return this.VPages2;
	}

	public void setVPages2(Integer VPages2) {
		this.VPages2 = VPages2;
	}

	public Integer getVPages3() {
		return this.VPages3;
	}

	public void setVPages3(Integer VPages3) {
		this.VPages3 = VPages3;
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

	public Boolean getCreateByCycle() {
		return this.createByCycle;
	}

	public void setCreateByCycle(Boolean createByCycle) {
		this.createByCycle = createByCycle;
	}

	public Integer getMrdf() {
		return this.mrdf;
	}

	public void setMrdf(Integer mrdf) {
		this.mrdf = mrdf;
	}

	public Integer getFeeder6() {
		return this.feeder6;
	}

	public void setFeeder6(Integer feeder6) {
		this.feeder6 = feeder6;
	}

	public Integer getFeeder7() {
		return this.feeder7;
	}

	public void setFeeder7(Integer feeder7) {
		this.feeder7 = feeder7;
	}

	public Set getJobBagSplites() {
		return this.jobBagSplites;
	}

	public void setJobBagSplites(Set jobBagSplites) {
		this.jobBagSplites = jobBagSplites;
	}
	

	public Integer getPagesOri() {
		return pagesOri;
	}

	public void setPagesOri(Integer pagesOri) {
		this.pagesOri = pagesOri;
	}


	public Integer getVPages2Ori() {
		return VPages2Ori;
	}

	public void setVPages2Ori(Integer vPages2Ori) {
		VPages2Ori = vPages2Ori;
	}

	public Integer getVPages3Ori() {
		return VPages3Ori;
	}

	public void setVPages3Ori(Integer vPages3Ori) {
		VPages3Ori = vPages3Ori;
	}

	public Boolean getCtSpecialSplite() {
		return ctSpecialSplite;
	}

	public void setCtSpecialSplite(Boolean ctSpecialSplite) {
		this.ctSpecialSplite = ctSpecialSplite;
	}
	
	public Integer getPages2Ori() {
		return pages2Ori;
	}

	public void setPages2Ori(Integer pages2Ori) {
		this.pages2Ori = pages2Ori;
	}

	public Integer getPages3Ori() {
		return pages3Ori;
	}

	public void setPages3Ori(Integer pages3Ori) {
		this.pages3Ori = pages3Ori;
	}

	public String getPrevStatus() {
		return prevStatus;
	}

	public void setPrevStatus(String prevStatus) {
		this.prevStatus = prevStatus;
	}

	public Integer getCtAcctsOri() {
		return ctAcctsOri;
	}

	public void setCtAcctsOri(Integer ctAcctsOri) {
		this.ctAcctsOri = ctAcctsOri;
	}
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Boolean getLgDisplayQty() {
		return lgDisplayQty;
	}

	public void setLgDisplayQty(Boolean lgDisplayQty) {
		this.lgDisplayQty = lgDisplayQty;
	}

	public Boolean getCustConfirm() {
		return custConfirm;
	}

	public void setCustConfirm(Boolean custConfirm) {
		this.custConfirm = custConfirm;
	}

	public Date getCustConfirmDate() {
		return custConfirmDate;
	}

	public void setCustConfirmDate(Date custConfirmDate) {
		this.custConfirmDate = custConfirmDate;
	}

	public String getLpPcode5() {
		return lpPcode5;
	}

	public void setLpPcode5(String lpPcode5) {
		this.lpPcode5 = lpPcode5;
	}

	public String getLpPcode6() {
		return lpPcode6;
	}

	public void setLpPcode6(String lpPcode6) {
		this.lpPcode6 = lpPcode6;
	}

	public String getLpPcode7() {
		return lpPcode7;
	}

	public void setLpPcode7(String lpPcode7) {
		this.lpPcode7 = lpPcode7;
	}

	public String getLpPcode8() {
		return lpPcode8;
	}

	public void setLpPcode8(String lpPcode8) {
		this.lpPcode8 = lpPcode8;
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

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public Boolean getFromDamage() {
		return fromDamage;
	}

	public void setFromDamage(Boolean fromDamage) {
		this.fromDamage = fromDamage;
	}

	public Integer getNotSent() {
		return notSent;
	}

	public void setNotSent(Integer notSent) {
		this.notSent = notSent;
	}
	

}