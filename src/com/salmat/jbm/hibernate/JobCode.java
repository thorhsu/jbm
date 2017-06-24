package com.salmat.jbm.hibernate;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * JobCode entity. @author MyEclipse Persistence Tools
 */

public class JobCode implements java.io.Serializable {

	// Fields

	private String jobCodeNo;
	private Lcinfo lcinfo;
	private Code codeByCodeJobCodeType;
	private Code codeByCodeMailCategory;
	private Code codeByCodeMailToPostoffice;
	private Lginfo lginfo;
	private Lpinfo lpinfoByIdfLpNo1;
	private Lpinfo lpinfoByIdfLpNo2;
	private Lpinfo lpinfoByIdfLpNo3;
	private Code codeByCodeLgType;
	private Returninfo returninfo;
	private Mpinfo mpinfoByIdfMpNo3;
	private Psinfo psinfo;
	private Mpinfo mpinfoByIdfMpNo2;
	private Mpinfo mpinfoByIdfMpNo1;
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
	private String lp1JudgeBy;

	private Integer lpBegindate1;
	private Integer lpEnddate1;
	private String lp2JudgeBy;
	private Integer lpBegindate2;
	private Integer lpEnddate2;
	private String lp3JudgeBy;
	private Integer lpBegindate3;
	private Integer lpEnddate3;
	private String lpNote;
	private String mp1JudgeBy;
	private Integer mpBegindate1;
	private Integer mpEnddate1;
	private String mp2JudgeBy;
	private Integer mpBegindate2;
	private Integer mpEnddate2;
	private String mp3JudgeBy;
	private Integer mpBegindate3;
	private Integer mpEnddate3;
	private String mpNote;
	private String psNote;
	private String dmNote;
	private String dispatchType;
	private String lgNote;
	private Boolean createByCycle;
	private Boolean cycle01;
	private Boolean cycle02;
	private Boolean cycle03;
	private Boolean cycle04;
	private Boolean cycle05;
	private Boolean cycle06;
	private Boolean cycle07;
	private Boolean cycle08;
	private Boolean cycle09;
	private Boolean cycle10;
	private Boolean cycle11;
	private Boolean cycle12;
	private Boolean cycle13;
	private Boolean cycle14;
	private Boolean cycle15;
	private Boolean cycle16;
	private Boolean cycle17;
	private Boolean cycle18;
	private Boolean cycle19;
	private Boolean cycle20;
	private Boolean cycle21;
	private Boolean cycle22;
	private Boolean cycle23;
	private Boolean cycle24;
	private Boolean cycle25;
	private Boolean cycle26;
	private Boolean cycle27;
	private Boolean cycle28;
	private Boolean cycle29;
	private Boolean cycle30;
	private Boolean cycle31;
	private Boolean cycleEnd;
	private String filename;
	private Boolean useLg;
	private String logFileSeq;
	private String customerDept;
	private String costCenter;
	private Double minimalChargePrice;
	private String splitJobCodeNo1;
	private String splitJobCodeNo2;
	private String splitJobCodeNo3;
	private String splitJobCodeNo4;
	private Double splitJobCodePercent1;
	private Double splitJobCodePercent2;
	private Double splitJobCodePercent3;
	private Double splitJobCodePercent4;
	private String mainJobCodeNo;
	private String mainJobCodeProgNm;
	private Set acctSectionJobCodeMappings = new HashSet(0);
	private Set jobBags = new HashSet(0);
	private Set acctJobCodeChargeItems = new HashSet(0);
    
	private Integer lpBeginMonth1;
	private Integer lpBeginMonth2;
    private Integer lpBeginMonth3;
    private Integer lpEndMonth1;
    private Integer lpEndMonth2;
    private Integer lpEndMonth3;
    private Integer mpBeginMonth1;
    private Integer mpBeginMonth2;
    private Integer mpBeginMonth3;
    private Integer mpEndMonth1;
    private Integer mpEndMonth2;
    private Integer mpEndMonth3;
    
    private Date noJobBagFrom;
	private Date noJobBagEnd;
	private Integer transferType;
	private Boolean selectByWeek;
	private Boolean weekDay1;
	private Boolean weekDay2;
	private Boolean weekDay3;
	private Boolean weekDay4;
	private Boolean weekDay5;
	private Boolean weekDay6;
	private Boolean weekDay7;
	private Boolean autoDelete;
	
	private String noJobBagFromStr;
	private String noJobBagEndStr;
	private Boolean custConfirm;
	private String damageJobCodeNo;
	private Integer fromPages;
	private Integer endPages;
	private String mailType;

	NumberFormat nf = NumberFormat.getInstance();
	

	// Constructors

	/** default constructor */
	public JobCode() {
	}

	/** minimal constructor */
	public JobCode(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}

	/** full constructor */
	public JobCode(String jobCodeNo, Lcinfo lcinfo, Code codeByCodeJobCodeType,
			Code codeByCodeMailCategory, Code codeByCodeMailToPostoffice,
			Lginfo lginfo, Lpinfo lpinfoByIdfLpNo1, Lpinfo lpinfoByIdfLpNo2,
			Lpinfo lpinfoByIdfLpNo3, Code codeByCodeLgType,
			Returninfo returninfo, Mpinfo mpinfoByIdfMpNo3, Psinfo psinfo,
			Mpinfo mpinfoByIdfMpNo2, Mpinfo mpinfoByIdfMpNo1,
			Customer customer, String jobCodeId, String progNm, Boolean isLp,
			Boolean isMps, Boolean isLg, Boolean isEdd, String mpDmPs,
			String dispatchTime, String deadTime, Boolean isEnabledContract,
			Boolean isConvertResult, Integer lpBegindate1, Integer lpEnddate1,
			Integer lpBegindate2, Integer lpEnddate2, Integer lpBegindate3,
			Integer lpEnddate3, String lpNote, Integer mpBegindate1,
			Integer mpEnddate1, Integer mpBegindate2, Integer mpEnddate2,
			Integer mpBegindate3, Integer mpEnddate3, String mpNote,
			String psNote, String dmNote, String dispatchType, String lgNote,
			Boolean createByCycle, Boolean cycle01, Boolean cycle02,
			Boolean cycle03, Boolean cycle04, Boolean cycle05, Boolean cycle06,
			Boolean cycle07, Boolean cycle08, Boolean cycle09, Boolean cycle10,
			Boolean cycle11, Boolean cycle12, Boolean cycle13, Boolean cycle14,
			Boolean cycle15, Boolean cycle16, Boolean cycle17, Boolean cycle18,
			Boolean cycle19, Boolean cycle20, Boolean cycle21, Boolean cycle22,
			Boolean cycle23, Boolean cycle24, Boolean cycle25, Boolean cycle26,
			Boolean cycle27, Boolean cycle28, Boolean cycle29, Boolean cycle30,
			Boolean cycle31, Boolean cycleEnd, String filename, Boolean useLg, String logFileSeq,
			String customerDept, String costCenter, Double minimalChargePrice,
			String splitJobCodeNo1, String splitJobCodeNo2,
			String splitJobCodeNo3, String splitJobCodeNo4,
			Double splitJobCodePercent1, Double splitJobCodePercent2,
			Double splitJobCodePercent3, Double splitJobCodePercent4,
			String mainJobCodeNo, String mainJobCodeProgNm,
			Set acctSectionJobCodeMappings, Set jobBags,
			Set acctJobCodeChargeItems, 
			Integer lpBeginMonth1, Integer lpBeginMonth2, Integer lpBeginMonth3, 
			Integer lpEndMonth1, Integer lpEndMonth2, Integer lpEndMonth3,
			Integer mpBeginMonth1, Integer mpBeginMonth2, Integer mpBeginMonth3, 
			Integer mpEndMonth1, Integer mpEndMonth2, Integer mpEndMonth3, Date noJobBagFrom,
	        Date noJobBagEnd, Integer transferType) {
		this.jobCodeNo = jobCodeNo;
		this.lcinfo = lcinfo;
		this.codeByCodeJobCodeType = codeByCodeJobCodeType;
		this.codeByCodeMailCategory = codeByCodeMailCategory;
		this.codeByCodeMailToPostoffice = codeByCodeMailToPostoffice;
		this.lginfo = lginfo;
		this.lpinfoByIdfLpNo1 = lpinfoByIdfLpNo1;
		this.lpinfoByIdfLpNo2 = lpinfoByIdfLpNo2;
		this.lpinfoByIdfLpNo3 = lpinfoByIdfLpNo3;
		this.codeByCodeLgType = codeByCodeLgType;
		this.returninfo = returninfo;
		this.mpinfoByIdfMpNo3 = mpinfoByIdfMpNo3;
		this.psinfo = psinfo;
		this.mpinfoByIdfMpNo2 = mpinfoByIdfMpNo2;
		this.mpinfoByIdfMpNo1 = mpinfoByIdfMpNo1;
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
		this.lpBegindate1 = lpBegindate1;
		this.lpEnddate1 = lpEnddate1;
		this.lpBegindate2 = lpBegindate2;
		this.lpEnddate2 = lpEnddate2;
		this.lpBegindate3 = lpBegindate3;
		this.lpEnddate3 = lpEnddate3;
		this.lpNote = lpNote;
		this.mpBegindate1 = mpBegindate1;
		this.mpEnddate1 = mpEnddate1;
		this.mpBegindate2 = mpBegindate2;
		this.mpEnddate2 = mpEnddate2;
		this.mpBegindate3 = mpBegindate3;
		this.mpEnddate3 = mpEnddate3;
		this.mpNote = mpNote;
		this.psNote = psNote;
		this.dmNote = dmNote;
		this.dispatchType = dispatchType;
		this.lgNote = lgNote;
		this.createByCycle = createByCycle;
		this.cycle01 = cycle01;
		this.cycle02 = cycle02;
		this.cycle03 = cycle03;
		this.cycle04 = cycle04;
		this.cycle05 = cycle05;
		this.cycle06 = cycle06;
		this.cycle07 = cycle07;
		this.cycle08 = cycle08;
		this.cycle09 = cycle09;
		this.cycle10 = cycle10;
		this.cycle11 = cycle11;
		this.cycle12 = cycle12;
		this.cycle13 = cycle13;
		this.cycle14 = cycle14;
		this.cycle15 = cycle15;
		this.cycle16 = cycle16;
		this.cycle17 = cycle17;
		this.cycle18 = cycle18;
		this.cycle19 = cycle19;
		this.cycle20 = cycle20;
		this.cycle21 = cycle21;
		this.cycle22 = cycle22;
		this.cycle23 = cycle23;
		this.cycle24 = cycle24;
		this.cycle25 = cycle25;
		this.cycle26 = cycle26;
		this.cycle27 = cycle27;
		this.cycle28 = cycle28;
		this.cycle29 = cycle29;
		this.cycle30 = cycle30;
		this.cycle31 = cycle31;
		this.cycleEnd = cycleEnd;
		this.filename = filename;
		this.useLg = useLg;
		this.logFileSeq = logFileSeq;
		this.customerDept = customerDept;
		this.costCenter = costCenter;
		this.minimalChargePrice = minimalChargePrice;
		this.splitJobCodeNo1 = splitJobCodeNo1;
		this.splitJobCodeNo2 = splitJobCodeNo2;
		this.splitJobCodeNo3 = splitJobCodeNo3;
		this.splitJobCodeNo4 = splitJobCodeNo4;
		this.splitJobCodePercent1 = splitJobCodePercent1;
		this.splitJobCodePercent2 = splitJobCodePercent2;
		this.splitJobCodePercent3 = splitJobCodePercent3;
		this.splitJobCodePercent4 = splitJobCodePercent4;
		this.mainJobCodeNo = mainJobCodeNo;
		this.mainJobCodeProgNm = mainJobCodeProgNm;
		this.acctSectionJobCodeMappings = acctSectionJobCodeMappings;
		this.jobBags = jobBags;
		this.acctJobCodeChargeItems = acctJobCodeChargeItems;
		this.lpBeginMonth1 = lpBeginMonth1;
		this.lpBeginMonth2 = lpBeginMonth2;
		this.lpBeginMonth3 = lpBeginMonth3;
		this.lpEndMonth1 = lpEndMonth1;
		this.lpEndMonth2 = lpEndMonth2;
		this.lpEndMonth3 = lpEndMonth3;
		this.mpBeginMonth1 = mpBeginMonth1;
		this.mpBeginMonth2 = mpBeginMonth2;
		this.mpBeginMonth3 = mpBeginMonth3;
		this.mpEndMonth1 = mpEndMonth1;
		this.mpEndMonth2 = mpEndMonth2;
		this.mpEndMonth3 = mpEndMonth3;
		this.noJobBagFrom = noJobBagFrom;
		this.noJobBagEnd = noJobBagEnd;
		this.transferType = transferType;
	}

	// Property accessors

	public String getJobCodeNo() {
		return this.jobCodeNo;
	}

	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}

	public Lcinfo getLcinfo() {
		return this.lcinfo;
	}

	public void setLcinfo(Lcinfo lcinfo) {
		this.lcinfo = lcinfo;
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

	public Code getCodeByCodeMailToPostoffice() {
		return this.codeByCodeMailToPostoffice;
	}

	public void setCodeByCodeMailToPostoffice(Code codeByCodeMailToPostoffice) {
		this.codeByCodeMailToPostoffice = codeByCodeMailToPostoffice;
	}

	public Lginfo getLginfo() {
		return this.lginfo;
	}

	public void setLginfo(Lginfo lginfo) {
		this.lginfo = lginfo;
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

	public Code getCodeByCodeLgType() {
		return this.codeByCodeLgType;
	}

	public void setCodeByCodeLgType(Code codeByCodeLgType) {
		this.codeByCodeLgType = codeByCodeLgType;
	}

	public Returninfo getReturninfo() {
		return this.returninfo;
	}

	public void setReturninfo(Returninfo returninfo) {
		this.returninfo = returninfo;
	}

	public Mpinfo getMpinfoByIdfMpNo3() {
		return this.mpinfoByIdfMpNo3;
	}

	public void setMpinfoByIdfMpNo3(Mpinfo mpinfoByIdfMpNo3) {
		this.mpinfoByIdfMpNo3 = mpinfoByIdfMpNo3;
	}

	public Psinfo getPsinfo() {
		return this.psinfo;
	}

	public void setPsinfo(Psinfo psinfo) {
		this.psinfo = psinfo;
	}

	public Mpinfo getMpinfoByIdfMpNo2() {
		return this.mpinfoByIdfMpNo2;
	}

	public void setMpinfoByIdfMpNo2(Mpinfo mpinfoByIdfMpNo2) {
		this.mpinfoByIdfMpNo2 = mpinfoByIdfMpNo2;
	}

	public Mpinfo getMpinfoByIdfMpNo1() {
		return this.mpinfoByIdfMpNo1;
	}

	public void setMpinfoByIdfMpNo1(Mpinfo mpinfoByIdfMpNo1) {
		this.mpinfoByIdfMpNo1 = mpinfoByIdfMpNo1;
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

	public Integer getLpBegindate1() {
		return this.lpBegindate1;
	}

	public void setLpBegindate1(Integer lpBegindate1) {
		this.lpBegindate1 = lpBegindate1;
	}

	public Integer getLpEnddate1() {
		return this.lpEnddate1;
	}

	public void setLpEnddate1(Integer lpEnddate1) {
		this.lpEnddate1 = lpEnddate1;
	}

	public Integer getLpBegindate2() {
		return this.lpBegindate2;
	}

	public void setLpBegindate2(Integer lpBegindate2) {
		this.lpBegindate2 = lpBegindate2;
	}

	public Integer getLpEnddate2() {
		return this.lpEnddate2;
	}

	public void setLpEnddate2(Integer lpEnddate2) {
		this.lpEnddate2 = lpEnddate2;
	}

	public Integer getLpBegindate3() {
		return this.lpBegindate3;
	}

	public void setLpBegindate3(Integer lpBegindate3) {
		this.lpBegindate3 = lpBegindate3;
	}

	public Integer getLpEnddate3() {
		return this.lpEnddate3;
	}

	public void setLpEnddate3(Integer lpEnddate3) {
		this.lpEnddate3 = lpEnddate3;
	}

	public String getLpNote() {
		return this.lpNote;
	}

	public void setLpNote(String lpNote) {
		this.lpNote = lpNote;
	}

	public Integer getMpBegindate1() {
		return this.mpBegindate1;
	}

	public void setMpBegindate1(Integer mpBegindate1) {
		this.mpBegindate1 = mpBegindate1;
	}

	public Integer getMpEnddate1() {
		return this.mpEnddate1;
	}

	public void setMpEnddate1(Integer mpEnddate1) {
		this.mpEnddate1 = mpEnddate1;
	}

	public Integer getMpBegindate2() {
		return this.mpBegindate2;
	}

	public void setMpBegindate2(Integer mpBegindate2) {
		this.mpBegindate2 = mpBegindate2;
	}

	public Integer getMpEnddate2() {
		return this.mpEnddate2;
	}

	public void setMpEnddate2(Integer mpEnddate2) {
		this.mpEnddate2 = mpEnddate2;
	}

	public Integer getMpBegindate3() {
		return this.mpBegindate3;
	}

	public void setMpBegindate3(Integer mpBegindate3) {
		this.mpBegindate3 = mpBegindate3;
	}

	public Integer getMpEnddate3() {
		return this.mpEnddate3;
	}

	public void setMpEnddate3(Integer mpEnddate3) {
		this.mpEnddate3 = mpEnddate3;
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

	public Boolean getCreateByCycle() {
		return this.createByCycle;
	}

	public void setCreateByCycle(Boolean createByCycle) {
		this.createByCycle = createByCycle;
	}

	public Boolean getCycle01() {
		return this.cycle01;
	}

	public void setCycle01(Boolean cycle01) {
		this.cycle01 = cycle01;
	}

	public Boolean getCycle02() {
		return this.cycle02;
	}

	public void setCycle02(Boolean cycle02) {
		this.cycle02 = cycle02;
	}

	public Boolean getCycle03() {
		return this.cycle03;
	}

	public void setCycle03(Boolean cycle03) {
		this.cycle03 = cycle03;
	}

	public Boolean getCycle04() {
		return this.cycle04;
	}

	public void setCycle04(Boolean cycle04) {
		this.cycle04 = cycle04;
	}

	public Boolean getCycle05() {
		return this.cycle05;
	}

	public void setCycle05(Boolean cycle05) {
		this.cycle05 = cycle05;
	}

	public Boolean getCycle06() {
		return this.cycle06;
	}

	public void setCycle06(Boolean cycle06) {
		this.cycle06 = cycle06;
	}

	public Boolean getCycle07() {
		return this.cycle07;
	}

	public void setCycle07(Boolean cycle07) {
		this.cycle07 = cycle07;
	}

	public Boolean getCycle08() {
		return this.cycle08;
	}

	public void setCycle08(Boolean cycle08) {
		this.cycle08 = cycle08;
	}

	public Boolean getCycle09() {
		return this.cycle09;
	}

	public void setCycle09(Boolean cycle09) {
		this.cycle09 = cycle09;
	}

	public Boolean getCycle10() {
		return this.cycle10;
	}

	public void setCycle10(Boolean cycle10) {
		this.cycle10 = cycle10;
	}

	public Boolean getCycle11() {
		return this.cycle11;
	}

	public void setCycle11(Boolean cycle11) {
		this.cycle11 = cycle11;
	}

	public Boolean getCycle12() {
		return this.cycle12;
	}

	public void setCycle12(Boolean cycle12) {
		this.cycle12 = cycle12;
	}

	public Boolean getCycle13() {
		return this.cycle13;
	}

	public void setCycle13(Boolean cycle13) {
		this.cycle13 = cycle13;
	}

	public Boolean getCycle14() {
		return this.cycle14;
	}

	public void setCycle14(Boolean cycle14) {
		this.cycle14 = cycle14;
	}

	public Boolean getCycle15() {
		return this.cycle15;
	}

	public void setCycle15(Boolean cycle15) {
		this.cycle15 = cycle15;
	}

	public Boolean getCycle16() {
		return this.cycle16;
	}

	public void setCycle16(Boolean cycle16) {
		this.cycle16 = cycle16;
	}

	public Boolean getCycle17() {
		return this.cycle17;
	}

	public void setCycle17(Boolean cycle17) {
		this.cycle17 = cycle17;
	}

	public Boolean getCycle18() {
		return this.cycle18;
	}

	public void setCycle18(Boolean cycle18) {
		this.cycle18 = cycle18;
	}

	public Boolean getCycle19() {
		return this.cycle19;
	}

	public void setCycle19(Boolean cycle19) {
		this.cycle19 = cycle19;
	}

	public Boolean getCycle20() {
		return this.cycle20;
	}

	public void setCycle20(Boolean cycle20) {
		this.cycle20 = cycle20;
	}

	public Boolean getCycle21() {
		return this.cycle21;
	}

	public void setCycle21(Boolean cycle21) {
		this.cycle21 = cycle21;
	}

	public Boolean getCycle22() {
		return this.cycle22;
	}

	public void setCycle22(Boolean cycle22) {
		this.cycle22 = cycle22;
	}

	public Boolean getCycle23() {
		return this.cycle23;
	}

	public void setCycle23(Boolean cycle23) {
		this.cycle23 = cycle23;
	}

	public Boolean getCycle24() {
		return this.cycle24;
	}

	public void setCycle24(Boolean cycle24) {
		this.cycle24 = cycle24;
	}

	public Boolean getCycle25() {
		return this.cycle25;
	}

	public void setCycle25(Boolean cycle25) {
		this.cycle25 = cycle25;
	}

	public Boolean getCycle26() {
		return this.cycle26;
	}

	public void setCycle26(Boolean cycle26) {
		this.cycle26 = cycle26;
	}

	public Boolean getCycle27() {
		return this.cycle27;
	}

	public void setCycle27(Boolean cycle27) {
		this.cycle27 = cycle27;
	}

	public Boolean getCycle28() {
		return this.cycle28;
	}

	public void setCycle28(Boolean cycle28) {
		this.cycle28 = cycle28;
	}

	public Boolean getCycle29() {
		return this.cycle29;
	}

	public void setCycle29(Boolean cycle29) {
		this.cycle29 = cycle29;
	}

	public Boolean getCycle30() {
		return this.cycle30;
	}

	public void setCycle30(Boolean cycle30) {
		this.cycle30 = cycle30;
	}

	public Boolean getCycle31() {
		return this.cycle31;
	}

	public void setCycle31(Boolean cycle31) {
		this.cycle31 = cycle31;
	}
	
	public Boolean getCycleEnd() {
		return cycleEnd;
	}

	public void setCycleEnd(Boolean cycleEnd) {
		this.cycleEnd = cycleEnd;
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
	    nf.setMaximumIntegerDigits(3);
	    nf.setMinimumIntegerDigits(3);
	    
		this.logFileSeq = nf.format(logseq);
	}

	public String getCustomerDept() {
		return this.customerDept;
	}

	public void setCustomerDept(String customerDept) {
		this.customerDept = customerDept;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public Double getMinimalChargePrice() {
		return this.minimalChargePrice;
	}

	public void setMinimalChargePrice(Double minimalChargePrice) {
		this.minimalChargePrice = minimalChargePrice;
	}

	public String getSplitJobCodeNo1() {
		return this.splitJobCodeNo1;
	}

	public void setSplitJobCodeNo1(String splitJobCodeNo1) {
		this.splitJobCodeNo1 = splitJobCodeNo1;
	}

	public String getSplitJobCodeNo2() {
		return this.splitJobCodeNo2;
	}

	public void setSplitJobCodeNo2(String splitJobCodeNo2) {
		this.splitJobCodeNo2 = splitJobCodeNo2;
	}

	public String getSplitJobCodeNo3() {
		return this.splitJobCodeNo3;
	}

	public void setSplitJobCodeNo3(String splitJobCodeNo3) {
		this.splitJobCodeNo3 = splitJobCodeNo3;
	}

	public String getSplitJobCodeNo4() {
		return this.splitJobCodeNo4;
	}

	public void setSplitJobCodeNo4(String splitJobCodeNo4) {
		this.splitJobCodeNo4 = splitJobCodeNo4;
	}

	public Double getSplitJobCodePercent1() {
		return this.splitJobCodePercent1;
	}

	public void setSplitJobCodePercent1(Double splitJobCodePercent1) {
		this.splitJobCodePercent1 = splitJobCodePercent1;
	}

	public Double getSplitJobCodePercent2() {
		return this.splitJobCodePercent2;
	}

	public void setSplitJobCodePercent2(Double splitJobCodePercent2) {
		this.splitJobCodePercent2 = splitJobCodePercent2;
	}

	public Double getSplitJobCodePercent3() {
		return this.splitJobCodePercent3;
	}

	public void setSplitJobCodePercent3(Double splitJobCodePercent3) {
		this.splitJobCodePercent3 = splitJobCodePercent3;
	}

	public Double getSplitJobCodePercent4() {
		return this.splitJobCodePercent4;
	}

	public void setSplitJobCodePercent4(Double splitJobCodePercent4) {
		this.splitJobCodePercent4 = splitJobCodePercent4;
	}

	public String getMainJobCodeNo() {
		return this.mainJobCodeNo;
	}

	public void setMainJobCodeNo(String mainJobCodeNo) {
		this.mainJobCodeNo = mainJobCodeNo;
	}

	public String getMainJobCodeProgNm() {
		return this.mainJobCodeProgNm;
	}

	public void setMainJobCodeProgNm(String mainJobCodeProgNm) {
		this.mainJobCodeProgNm = mainJobCodeProgNm;
	}

	public Set getAcctSectionJobCodeMappings() {
		return this.acctSectionJobCodeMappings;
	}

	public void setAcctSectionJobCodeMappings(Set acctSectionJobCodeMappings) {
		this.acctSectionJobCodeMappings = acctSectionJobCodeMappings;
	}

	public Set getJobBags() {
		return this.jobBags;
	}

	public void setJobBags(Set jobBags) {
		this.jobBags = jobBags;
	}

	public Set getAcctJobCodeChargeItems() {
		return this.acctJobCodeChargeItems;
	}

	public void setAcctJobCodeChargeItems(Set acctJobCodeChargeItems) {
		this.acctJobCodeChargeItems = acctJobCodeChargeItems;
	}
	
    public Integer getLpBeginMonth1() {
		return lpBeginMonth1;
	}

	public void setLpBeginMonth1(Integer lpBeginMonth1) {
		this.lpBeginMonth1 = lpBeginMonth1;
	}

	public Integer getLpBeginMonth2() {
		return lpBeginMonth2;
	}

	public void setLpBeginMonth2(Integer lpBeginMonth2) {
		this.lpBeginMonth2 = lpBeginMonth2;
	}

	public Integer getLpBeginMonth3() {
		return lpBeginMonth3;
	}

	public void setLpBeginMonth3(Integer lpBeginMonth3) {
		this.lpBeginMonth3 = lpBeginMonth3;
	}

	public Integer getLpEndMonth1() {
		return lpEndMonth1;
	}

	public void setLpEndMonth1(Integer lpEndMonth1) {
		this.lpEndMonth1 = lpEndMonth1;
	}

	public Integer getLpEndMonth2() {
		return lpEndMonth2;
	}

	public void setLpEndMonth2(Integer lpEndMonth2) {
		this.lpEndMonth2 = lpEndMonth2;
	}

	public Integer getLpEndMonth3() {
		return lpEndMonth3;
	}

	public void setLpEndMonth3(Integer lpEndMonth3) {
		this.lpEndMonth3 = lpEndMonth3;
	}

	public Integer getMpBeginMonth1() {
		return mpBeginMonth1;
	}

	public void setMpBeginMonth1(Integer mpBeginMonth1) {
		this.mpBeginMonth1 = mpBeginMonth1;
	}

	public Integer getMpBeginMonth2() {
		return mpBeginMonth2;
	}

	public void setMpBeginMonth2(Integer mpBeginMonth2) {
		this.mpBeginMonth2 = mpBeginMonth2;
	}

	public Integer getMpBeginMonth3() {
		return mpBeginMonth3;
	}

	public void setMpBeginMonth3(Integer mpBeginMonth3) {
		this.mpBeginMonth3 = mpBeginMonth3;
	}

	public Integer getMpEndMonth1() {
		return mpEndMonth1;
	}

	public void setMpEndMonth1(Integer mpEndMonth1) {
		this.mpEndMonth1 = mpEndMonth1;
	}

	public Integer getMpEndMonth2() {
		return mpEndMonth2;
	}

	public void setMpEndMonth2(Integer mpEndMonth2) {
		this.mpEndMonth2 = mpEndMonth2;
	}

	public Integer getMpEndMonth3() {
		return mpEndMonth3;
	}

	public void setMpEndMonth3(Integer mpEndMonth3) {
		this.mpEndMonth3 = mpEndMonth3;
	}
	public String getLp1JudgeBy() {
		return lp1JudgeBy;
	}

	public void setLp1JudgeBy(String lp1JudgeBy) {
		this.lp1JudgeBy = lp1JudgeBy;
	}

	public String getLp2JudgeBy() {
		return lp2JudgeBy;
	}

	public void setLp2JudgeBy(String lp2JudgeBy) {
		this.lp2JudgeBy = lp2JudgeBy;
	}

	public String getLp3JudgeBy() {
		return lp3JudgeBy;
	}

	public void setLp3JudgeBy(String lp3JudgeBy) {
		this.lp3JudgeBy = lp3JudgeBy;
	}

	public String getMp1JudgeBy() {
		return mp1JudgeBy;
	}

	public void setMp1JudgeBy(String mp1JudgeBy) {
		this.mp1JudgeBy = mp1JudgeBy;
	}

	public String getMp2JudgeBy() {
		return mp2JudgeBy;
	}

	public void setMp2JudgeBy(String mp2JudgeBy) {
		this.mp2JudgeBy = mp2JudgeBy;
	}

	public String getMp3JudgeBy() {
		return mp3JudgeBy;
	}

	public void setMp3JudgeBy(String mp3JudgeBy) {
		this.mp3JudgeBy = mp3JudgeBy;
	}
    public Date getNoJobBagFrom() {
		return noJobBagFrom;
	}

	public void setNoJobBagFrom(Date noJobBagFrom) {
		this.noJobBagFrom = noJobBagFrom;
	}

	public Date getNoJobBagEnd() {
		return noJobBagEnd;
	}

	public void setNoJobBagEnd(Date noJobBagEnd) {
		this.noJobBagEnd = noJobBagEnd;
	}

	public String getNoJobBagFromStr() {
		if(noJobBagFrom == null){
			this.setNoJobBagFromStr("");
			return this.noJobBagFromStr;
		}else{
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  this.setNoJobBagFromStr(sdf.format(noJobBagFrom));
		  return this.noJobBagFromStr;
		}
	}

	public String getNoJobBagEndStr() {
		if(noJobBagEnd == null){
			this.setNoJobBagEndStr("");
			return this.noJobBagEndStr;
		}else{
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  this.setNoJobBagEndStr(sdf.format(noJobBagEnd));
		  return this.noJobBagEndStr;
		}
	}
	public void setNoJobBagFromStr(String noJobBagFromStr) {
		this.noJobBagFromStr = noJobBagFromStr;
	}

	public void setNoJobBagEndStr(String noJobBagEndStr) {
		this.noJobBagEndStr = noJobBagEndStr;
	}
	
	public Integer getTransferType() {
		return transferType;
	}

	public void setTransferType(Integer transferType) {
		this.transferType = transferType;
	}

	public Boolean getSelectByWeek() {
		return selectByWeek;
	}

	public void setSelectByWeek(Boolean selectByWeek) {
		this.selectByWeek = selectByWeek;
	}

	public Boolean getWeekDay1() {
		return weekDay1;
	}

	public void setWeekDay1(Boolean weekDay1) {
		this.weekDay1 = weekDay1;
	}

	public Boolean getWeekDay2() {
		return weekDay2;
	}

	public void setWeekDay2(Boolean weekDay2) {
		this.weekDay2 = weekDay2;
	}

	public Boolean getWeekDay3() {
		return weekDay3;
	}

	public void setWeekDay3(Boolean weekDay3) {
		this.weekDay3 = weekDay3;
	}

	public Boolean getWeekDay4() {
		return weekDay4;
	}

	public void setWeekDay4(Boolean weekDay4) {
		this.weekDay4 = weekDay4;
	}

	public Boolean getWeekDay5() {
		return weekDay5;
	}

	public void setWeekDay5(Boolean weekDay5) {
		this.weekDay5 = weekDay5;
	}

	public Boolean getWeekDay6() {
		return weekDay6;
	}

	public void setWeekDay6(Boolean weekDay6) {
		this.weekDay6 = weekDay6;
	}

	public Boolean getWeekDay7() {
		return weekDay7;
	}

	public void setWeekDay7(Boolean weekDay7) {
		this.weekDay7 = weekDay7;
	}
	public Boolean getAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(Boolean autoDelete) {
		this.autoDelete = autoDelete;
	}

	public Boolean getCustConfirm() {
		return custConfirm;
	}

	public void setCustConfirm(Boolean custConfirm) {
		this.custConfirm = custConfirm;
	}

	public String getDamageJobCodeNo() {
		return damageJobCodeNo;
	}

	public void setDamageJobCodeNo(String damageJobCodeNo) {
		this.damageJobCodeNo = damageJobCodeNo;
	}

	public Integer getFromPages() {
		return fromPages;
	}

	public void setFromPages(Integer fromPages) {
		this.fromPages = fromPages;
	}

	public Integer getEndPages() {
		return endPages;
	}

	public void setEndPages(Integer endPages) {
		this.endPages = endPages;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	

}