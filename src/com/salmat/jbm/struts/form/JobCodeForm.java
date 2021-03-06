/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.form;

import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.salmat.jbm.bean.*;
import com.salmat.jbm.hibernate.Code;
import com.salmat.jbm.hibernate.Customer;
import com.salmat.jbm.hibernate.Employee;
import com.salmat.jbm.hibernate.Lcinfo;
import com.salmat.jbm.hibernate.Lginfo;
import com.salmat.jbm.hibernate.Lpinfo;
import com.salmat.jbm.hibernate.Mpinfo;
import com.salmat.jbm.hibernate.Psinfo;
import com.salmat.jbm.hibernate.Returninfo;



public class JobCodeForm extends ActionForm {
	// Fields
	private String jobCodeNo;
	private String returnNo;
	private String lcNo;
	private Integer jobCodeType;
	private String mpNo3;
	private String psNo;
	private String mpNo2;
	private String lpNo1;
	private String lgNo;
	private String mpNo1;
	private String lpNo2;
	private String lpNo3;
	private String custNo;
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
	private String lpBegindate1;
	private String lpEnddate1;
	private String lp2JudgeBy;
	private String lpBegindate2;
	private String lpEnddate2;
	private String lp3JudgeBy;
	private String lpBegindate3;
	private String lpEnddate3;
	private String lpNote;
	private String mp1JudgeBy;
	private String mpBegindate1;
	private String mpEnddate1;
	private String mp2JudgeBy;
	private String mpBegindate2;
	private String mpEnddate2;
	private String mp3JudgeBy;
	private String mpBegindate3;
	private String mpEnddate3;
	private String mpNote;
	private String psNote;
	private String dmNote;
	private String dispatchType;
	private String lgNote;
	private String mailType;
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
	private Integer codeMailToPostoffice;
	private Integer codeLgType;
	private Integer codeMailCategory;	
	private Boolean useLg;
	private String customerDept;	
	
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
	private String previewDate;
	private Boolean custConfirm;
	private String damageJobCodeNo;
	private Integer fromPages;
	private Integer endPages;

	public Integer getCodeMailCategory() {
		return codeMailCategory;
	}
	public void setCodeMailCategory(Integer codeMailCategory) {
		this.codeMailCategory = codeMailCategory;
	}
	private String jobCodeNos[];
	private String lpNos[];
	private String mpNos[];
	
	public String getJobCodeNo() {
		return jobCodeNo;
	}
	public void setJobCodeNo(String jobCodeNo) {
		this.jobCodeNo = jobCodeNo;
	}

	public String getReturnNo() {
		return returnNo;
	}
	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}
	public String getLcNo() {
		return lcNo;
	}
	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}

	public Integer getJobCodeType() {
		return jobCodeType;
	}
	public void setJobCodeType(Integer jobCodeType) {
		this.jobCodeType = jobCodeType;
	}
	public String getMpNo3() {
		return mpNo3;
	}
	public void setMpNo3(String mpNo3) {
		this.mpNo3 = mpNo3;
	}
	public String getPsNo() {
		return psNo;
	}
	public void setPsNo(String psNo) {
		this.psNo = psNo;
	}
	public String getMpNo2() {
		return mpNo2;
	}
	public void setMpNo2(String mpNo2) {
		this.mpNo2 = mpNo2;
	}
	public String getLpNo1() {
		return lpNo1;
	}
	public void setLpNo1(String lpNo1) {
		this.lpNo1 = lpNo1;
	}
	public String getLgNo() {
		return lgNo;
	}
	public void setLgNo(String lgNo) {
		this.lgNo = lgNo;
	}
	public String getMpNo1() {
		return mpNo1;
	}
	public void setMpNo1(String mpNo1) {
		this.mpNo1 = mpNo1;
	}
	public String getLpNo2() {
		return lpNo2;
	}
	public void setLpNo2(String lpNo2) {
		this.lpNo2 = lpNo2;
	}
	public String getLpNo3() {
		return lpNo3;
	}
	public void setLpNo3(String lpNo3) {
		this.lpNo3 = lpNo3;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getJobCodeId() {
		return jobCodeId;
	}
	public void setJobCodeId(String jobCodeId) {
		this.jobCodeId = jobCodeId;
	}
	public String getProgNm() {
		return progNm;
	}
	public void setProgNm(String progNm) {
		this.progNm = progNm;
	}
	public Boolean getIsLp() {
		return isLp;
	}
	public void setIsLp(Boolean isLp) {
		this.isLp = isLp;
	}
	public Boolean getIsMps() {
		return isMps;
	}
	public void setIsMps(Boolean isMps) {
		this.isMps = isMps;
	}
	public Boolean getIsLg() {
		return isLg;
	}
	public void setIsLg(Boolean isLg) {
		this.isLg = isLg;
	}
	public Boolean getIsEdd() {
		return isEdd;
	}
	public void setIsEdd(Boolean isEdd) {
		this.isEdd = isEdd;
	}
	public String getMpDmPs() {
		return mpDmPs;
	}
	public void setMpDmPs(String mpDmPs) {
		this.mpDmPs = mpDmPs;
	}
	public String getDispatchTime() {
		return dispatchTime;
	}
	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	public String getDeadTime() {
		return deadTime;
	}
	public void setDeadTime(String deadTime) {
		this.deadTime = deadTime;
	}
	public Boolean getIsEnabledContract() {
		return isEnabledContract;
	}
	public void setIsEnabledContract(Boolean isEnabledContract) {
		this.isEnabledContract = isEnabledContract;
	}
	public Boolean getIsConvertResult() {
		return isConvertResult;
	}
	public void setIsConvertResult(Boolean isConvertResult) {
		this.isConvertResult = isConvertResult;
	}
	public String getLpBegindate1() {
		return lpBegindate1;
	}
	public void setLpBegindate1(String lpBegindate1) {
		this.lpBegindate1 = lpBegindate1;
	}
	public String getLpEnddate1() {
		return lpEnddate1;
	}
	public void setLpEnddate1(String lpEnddate1) {
		this.lpEnddate1 = lpEnddate1;
	}
	public String getLpBegindate2() {
		return lpBegindate2;
	}
	public void setLpBegindate2(String lpBegindate2) {
		this.lpBegindate2 = lpBegindate2;
	}
	public String getLpEnddate2() {
		return lpEnddate2;
	}
	public void setLpEnddate2(String lpEnddate2) {
		this.lpEnddate2 = lpEnddate2;
	}
	public String getLpBegindate3() {
		return lpBegindate3;
	}
	public void setLpBegindate3(String lpBegindate3) {
		this.lpBegindate3 = lpBegindate3;
	}
	public String getLpEnddate3() {
		return lpEnddate3;
	}
	public void setLpEnddate3(String lpEnddate3) {
		this.lpEnddate3 = lpEnddate3;
	}
	public String getLpNote() {
		return lpNote;
	}
	public void setLpNote(String lpNote) {
		this.lpNote = lpNote;
	}
	public String getMpBegindate1() {
		return mpBegindate1;
	}
	public void setMpBegindate1(String mpBegindate1) {
		this.mpBegindate1 = mpBegindate1;
	}
	public String getMpEnddate1() {
		return mpEnddate1;
	}
	public void setMpEnddate1(String mpEnddate1) {
		this.mpEnddate1 = mpEnddate1;
	}
	public String getMpBegindate2() {
		return mpBegindate2;
	}
	public void setMpBegindate2(String mpBegindate2) {
		this.mpBegindate2 = mpBegindate2;
	}
	public String getMpEnddate2() {
		return mpEnddate2;
	}
	public void setMpEnddate2(String mpEnddate2) {
		this.mpEnddate2 = mpEnddate2;
	}
	public String getMpBegindate3() {
		return mpBegindate3;
	}
	public void setMpBegindate3(String mpBegindate3) {
		this.mpBegindate3 = mpBegindate3;
	}
	public String getMpEnddate3() {
		return mpEnddate3;
	}
	public void setMpEnddate3(String mpEnddate3) {
		this.mpEnddate3 = mpEnddate3;
	}
	public String getMpNote() {
		return mpNote;
	}
	public void setMpNote(String mpNote) {
		this.mpNote = mpNote;
	}
	public String getPsNote() {
		return psNote;
	}
	public void setPsNote(String psNote) {
		this.psNote = psNote;
	}
	public String getDmNote() {
		return dmNote;
	}
	public void setDmNote(String dmNote) {
		this.dmNote = dmNote;
	}
	public String getDispatchType() {
		return dispatchType;
	}
	public void setDispatchType(String dispatchType) {
		this.dispatchType = dispatchType;
	}
	public String getLgNote() {
		return lgNote;
	}
	public void setLgNote(String lgNote) {
		this.lgNote = lgNote;
	}
	public Boolean getCycle01() {
		return cycle01;
	}
	public void setCycle01(Boolean cycle01) {
		this.cycle01 = cycle01;
	}
	public Boolean getCycle02() {
		return cycle02;
	}
	public void setCycle02(Boolean cycle02) {
		this.cycle02 = cycle02;
	}
	public Boolean getCycle03() {
		return cycle03;
	}
	public void setCycle03(Boolean cycle03) {
		this.cycle03 = cycle03;
	}
	public Boolean getCycle04() {
		return cycle04;
	}
	public void setCycle04(Boolean cycle04) {
		this.cycle04 = cycle04;
	}
	public Boolean getCycle05() {
		return cycle05;
	}
	public void setCycle05(Boolean cycle05) {
		this.cycle05 = cycle05;
	}
	public Boolean getCycle06() {
		return cycle06;
	}
	public void setCycle06(Boolean cycle06) {
		this.cycle06 = cycle06;
	}
	public Boolean getCycle07() {
		return cycle07;
	}
	public void setCycle07(Boolean cycle07) {
		this.cycle07 = cycle07;
	}
	public Boolean getCycle08() {
		return cycle08;
	}
	public void setCycle08(Boolean cycle08) {
		this.cycle08 = cycle08;
	}
	public Boolean getCycle09() {
		return cycle09;
	}
	public void setCycle09(Boolean cycle09) {
		this.cycle09 = cycle09;
	}
	public Boolean getCycle10() {
		return cycle10;
	}
	public void setCycle10(Boolean cycle10) {
		this.cycle10 = cycle10;
	}
	public Boolean getCycle11() {
		return cycle11;
	}
	public void setCycle11(Boolean cycle11) {
		this.cycle11 = cycle11;
	}
	public Boolean getCycle12() {
		return cycle12;
	}
	public void setCycle12(Boolean cycle12) {
		this.cycle12 = cycle12;
	}
	public Boolean getCycle13() {
		return cycle13;
	}
	public void setCycle13(Boolean cycle13) {
		this.cycle13 = cycle13;
	}
	public Boolean getCycle14() {
		return cycle14;
	}
	public void setCycle14(Boolean cycle14) {
		this.cycle14 = cycle14;
	}
	public Boolean getCycle15() {
		return cycle15;
	}
	public void setCycle15(Boolean cycle15) {
		this.cycle15 = cycle15;
	}
	public Boolean getCycle16() {
		return cycle16;
	}
	public void setCycle16(Boolean cycle16) {
		this.cycle16 = cycle16;
	}
	public Boolean getCycle17() {
		return cycle17;
	}
	public void setCycle17(Boolean cycle17) {
		this.cycle17 = cycle17;
	}
	public Boolean getCycle18() {
		return cycle18;
	}
	public void setCycle18(Boolean cycle18) {
		this.cycle18 = cycle18;
	}
	public Boolean getCycle19() {
		return cycle19;
	}
	public void setCycle19(Boolean cycle19) {
		this.cycle19 = cycle19;
	}
	public Boolean getCycle20() {
		return cycle20;
	}
	public void setCycle20(Boolean cycle20) {
		this.cycle20 = cycle20;
	}
	public Boolean getCycle21() {
		return cycle21;
	}
	public void setCycle21(Boolean cycle21) {
		this.cycle21 = cycle21;
	}
	public Boolean getCycle22() {
		return cycle22;
	}
	public void setCycle22(Boolean cycle22) {
		this.cycle22 = cycle22;
	}
	public Boolean getCycle23() {
		return cycle23;
	}
	public void setCycle23(Boolean cycle23) {
		this.cycle23 = cycle23;
	}
	public Boolean getCycle24() {
		return cycle24;
	}
	public void setCycle24(Boolean cycle24) {
		this.cycle24 = cycle24;
	}
	public Boolean getCycle25() {
		return cycle25;
	}
	public void setCycle25(Boolean cycle25) {
		this.cycle25 = cycle25;
	}
	public Boolean getCycle26() {
		return cycle26;
	}
	public void setCycle26(Boolean cycle26) {
		this.cycle26 = cycle26;
	}
	public Boolean getCycle27() {
		return cycle27;
	}
	public void setCycle27(Boolean cycle27) {
		this.cycle27 = cycle27;
	}
	public Boolean getCycle28() {
		return cycle28;
	}
	public void setCycle28(Boolean cycle28) {
		this.cycle28 = cycle28;
	}
	public Boolean getCycle29() {
		return cycle29;
	}
	public void setCycle29(Boolean cycle29) {
		this.cycle29 = cycle29;
	}
	public Boolean getCycle30() {
		return cycle30;
	}
	public void setCycle30(Boolean cycle30) {
		this.cycle30 = cycle30;
	}
	public Boolean getCycle31() {
		return cycle31;
	}	
	public Boolean getCycleEnd() {
		return cycleEnd;
	}
	public void setCycleEnd(Boolean cycleEnd) {
		this.cycleEnd = cycleEnd;
	}
	public void setCycle31(Boolean cycle31) {
		this.cycle31 = cycle31;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Integer getCodeMailToPostoffice() {
		return codeMailToPostoffice;
	}
	public void setCodeMailToPostoffice(Integer codeMailToPostoffice) {
		this.codeMailToPostoffice = codeMailToPostoffice;
	}
	public Integer getCodeLgType() {
		return codeLgType;
	}
	public void setCodeLgType(Integer codeLgType) {
		this.codeLgType = codeLgType;
	}
	public Boolean getUseLg() {
		return useLg;
	}
	public void setUseLg(Boolean useLg) {
		this.useLg = useLg;
	}


	public String[] getJobCodeNos() {
		return jobCodeNos;
	}
	public void setJobCodeNos(String[] jobCodeNos) {
		this.jobCodeNos = jobCodeNos;
	}
	public String[] getLpNos() {
		return lpNos;
	}
	public void setLpNos(String[] lpNos) {
		this.lpNos = lpNos;
	}
	public String[] getMpNos() {
		return mpNos;
	}
	public void setMpNos(String[] mpNos) {
		this.mpNos = mpNos;
	}
	public String getCustomerDept() {
		return customerDept;
	}
	public void setCustomerDept(String customerDept) {
		this.customerDept = customerDept;
	}
	public Boolean getCreateByCycle() {
		return createByCycle;
	}
	public void setCreateByCycle(Boolean createByCycle) {
		this.createByCycle = createByCycle;
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
		if(noJobBagFrom == null)
			return "";
		else{
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  return sdf.format(noJobBagFrom);
		}
	}

	public String getNoJobBagEndStr() {
		if(noJobBagEnd == null)
			return "";
		else{
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  return sdf.format(noJobBagEnd);
		}
	}
	public void setNoJobBagFromStr(String noJobBagFromStr) throws ParseException {
		if(noJobBagFromStr == null || "".equals(noJobBagFromStr.trim()))
			noJobBagFrom = null;
		else
		   this.noJobBagFrom = new SimpleDateFormat("yyyy-MM-dd").parse(noJobBagFromStr);
	}
	
	public void setNoJobBagEndStr(String noJobBagEndStr) throws ParseException {
		if(noJobBagEndStr == null || "".equals(noJobBagEndStr.trim()))
			noJobBagEnd = null;
		else
		   this.noJobBagEnd = new SimpleDateFormat("yyyy-MM-dd").parse(noJobBagEndStr);
	}
	
	public String getPreviewDate() {
		return previewDate;
	}
	public void setPreviewDate(String previewDate) {
		this.previewDate = previewDate;
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