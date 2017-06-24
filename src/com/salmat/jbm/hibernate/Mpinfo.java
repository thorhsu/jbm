package com.salmat.jbm.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Mpinfo entity. @author MyEclipse Persistence Tools
 */

public class Mpinfo implements java.io.Serializable {

	// Fields

	private String mpNo;
	private Customer customer;
	private String size;
	private String stampSet;
	private Boolean stampSetFg;
	private Boolean selectFg;
	private Boolean zipFg;
	private String remark;
	private double mpPrice;
	private String printCode;
	private double insertCnt;
	private String mp1Word;
	private String mp2Word;
	private String mp3Word;
	private String mp4Word;
	private String prodmemo;
	private Boolean mp1Iflag;
	private Boolean mp2Iflag;
	private Boolean mp3Iflag;
	private Boolean mp4Iflag;
	private String printCodeImage;
	private String mp1Image;
	private String mp2Image;
	private String mp3Image;
	private String mp4Image;
	private String nextEffectiveDate;
	private String nextProdmemo;
	private String nextPrintCode;
	private String nextPrintCodeImage;
	private Boolean nextStampSetFg;
	private Boolean nextZipFg;
	private Boolean nextSelectFg;
	private String nextRemark;
	private String nextMp1Word;
	private String nextMp2Word;
	private String nextMp3Word;
	private String nextMp4Word;
	private Boolean nextMp1Iflag;
	private Boolean nextMp2Iflag;
	private Boolean nextMp3Iflag;
	private Boolean nextMp4Iflag;
	private String nextMp1Image;
	private String nextMp2Image;
	private String nextMp3Image;
	private String nextMp4Image;
	private Set jobBagsForIdfMpNo1 = new HashSet(0);
	private Set jobBagsForIdfMpNo2 = new HashSet(0);
	private Set jobBagsForIdfMpNo3 = new HashSet(0);
	private Set jobCodesForIdfMpNo1 = new HashSet(0);
	private Set jobCodesForIdfMpNo2 = new HashSet(0);
	private Set jobCodesForIdfMpNo3 = new HashSet(0);

	// Constructors

	/** default constructor */
	public Mpinfo() {
	}

	/** minimal constructor */
	public Mpinfo(String mpNo) {
		this.mpNo = mpNo;
	}

	/** full constructor */
	public Mpinfo(String mpNo, Customer customer, String size, String stampSet,
			Boolean stampSetFg, Boolean selectFg, Boolean zipFg, String remark,
			double mpPrice, String printCode, double insertCnt, String mp1Word,
			String mp2Word, String mp3Word, String mp4Word, String prodmemo,
			Boolean mp1Iflag, Boolean mp2Iflag, Boolean mp3Iflag,
			Boolean mp4Iflag, String printCodeImage, String mp1Image,
			String mp2Image, String mp3Image, String mp4Image,
			String nextEffectiveDate, String nextProdmemo,
			String nextPrintCode, String nextPrintCodeImage,
			Boolean nextStampSetFg, Boolean nextZipFg, Boolean nextSelectFg,
			String nextRemark, String nextMp1Word, String nextMp2Word,
			String nextMp3Word, String nextMp4Word, Boolean nextMp1Iflag,
			Boolean nextMp2Iflag, Boolean nextMp3Iflag, Boolean nextMp4Iflag,
			String nextMp1Image, String nextMp2Image, String nextMp3Image,
			String nextMp4Image, Set jobBagsForIdfMpNo1,
			Set jobBagsForIdfMpNo2, Set jobBagsForIdfMpNo3,
			Set jobCodesForIdfMpNo1, Set jobCodesForIdfMpNo2,
			Set jobCodesForIdfMpNo3) {
		this.mpNo = mpNo;
		this.customer = customer;
		this.size = size;
		this.stampSet = stampSet;
		this.stampSetFg = stampSetFg;
		this.selectFg = selectFg;
		this.zipFg = zipFg;
		this.remark = remark;
		this.mpPrice = mpPrice;
		this.printCode = printCode;
		this.insertCnt = insertCnt;
		this.mp1Word = mp1Word;
		this.mp2Word = mp2Word;
		this.mp3Word = mp3Word;
		this.mp4Word = mp4Word;
		this.prodmemo = prodmemo;
		this.mp1Iflag = mp1Iflag;
		this.mp2Iflag = mp2Iflag;
		this.mp3Iflag = mp3Iflag;
		this.mp4Iflag = mp4Iflag;
		this.printCodeImage = printCodeImage;
		this.mp1Image = mp1Image;
		this.mp2Image = mp2Image;
		this.mp3Image = mp3Image;
		this.mp4Image = mp4Image;
		this.nextEffectiveDate = nextEffectiveDate;
		this.nextProdmemo = nextProdmemo;
		this.nextPrintCode = nextPrintCode;
		this.nextPrintCodeImage = nextPrintCodeImage;
		this.nextStampSetFg = nextStampSetFg;
		this.nextZipFg = nextZipFg;
		this.nextSelectFg = nextSelectFg;
		this.nextRemark = nextRemark;
		this.nextMp1Word = nextMp1Word;
		this.nextMp2Word = nextMp2Word;
		this.nextMp3Word = nextMp3Word;
		this.nextMp4Word = nextMp4Word;
		this.nextMp1Iflag = nextMp1Iflag;
		this.nextMp2Iflag = nextMp2Iflag;
		this.nextMp3Iflag = nextMp3Iflag;
		this.nextMp4Iflag = nextMp4Iflag;
		this.nextMp1Image = nextMp1Image;
		this.nextMp2Image = nextMp2Image;
		this.nextMp3Image = nextMp3Image;
		this.nextMp4Image = nextMp4Image;
		this.jobBagsForIdfMpNo1 = jobBagsForIdfMpNo1;
		this.jobBagsForIdfMpNo2 = jobBagsForIdfMpNo2;
		this.jobBagsForIdfMpNo3 = jobBagsForIdfMpNo3;
		this.jobCodesForIdfMpNo1 = jobCodesForIdfMpNo1;
		this.jobCodesForIdfMpNo2 = jobCodesForIdfMpNo2;
		this.jobCodesForIdfMpNo3 = jobCodesForIdfMpNo3;
	}

	// Property accessors

	public String getMpNo() {
		return this.mpNo;
	}

	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStampSet() {
		return this.stampSet;
	}

	public void setStampSet(String stampSet) {
		this.stampSet = stampSet;
	}

	public Boolean getStampSetFg() {
		return this.stampSetFg;
	}

	public void setStampSetFg(Boolean stampSetFg) {
		this.stampSetFg = stampSetFg;
	}

	public Boolean getSelectFg() {
		return this.selectFg;
	}

	public void setSelectFg(Boolean selectFg) {
		this.selectFg = selectFg;
	}

	public Boolean getZipFg() {
		return this.zipFg;
	}

	public void setZipFg(Boolean zipFg) {
		this.zipFg = zipFg;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getMpPrice() {
		return this.mpPrice;
	}

	public void setMpPrice(double mpPrice) {
		this.mpPrice = mpPrice;
	}

	public String getPrintCode() {
		return this.printCode;
	}

	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}

	public double getInsertCnt() {
		return this.insertCnt;
	}

	public void setInsertCnt(double insertCnt) {
		this.insertCnt = insertCnt;
	}

	public String getMp1Word() {
		return this.mp1Word;
	}

	public void setMp1Word(String mp1Word) {
		this.mp1Word = mp1Word;
	}

	public String getMp2Word() {
		return this.mp2Word;
	}

	public void setMp2Word(String mp2Word) {
		this.mp2Word = mp2Word;
	}

	public String getMp3Word() {
		return this.mp3Word;
	}

	public void setMp3Word(String mp3Word) {
		this.mp3Word = mp3Word;
	}

	public String getMp4Word() {
		return this.mp4Word;
	}

	public void setMp4Word(String mp4Word) {
		this.mp4Word = mp4Word;
	}

	public String getProdmemo() {
		return this.prodmemo;
	}

	public void setProdmemo(String prodmemo) {
		this.prodmemo = prodmemo;
	}

	public Boolean getMp1Iflag() {
		return this.mp1Iflag;
	}

	public void setMp1Iflag(Boolean mp1Iflag) {
		this.mp1Iflag = mp1Iflag;
	}

	public Boolean getMp2Iflag() {
		return this.mp2Iflag;
	}

	public void setMp2Iflag(Boolean mp2Iflag) {
		this.mp2Iflag = mp2Iflag;
	}

	public Boolean getMp3Iflag() {
		return this.mp3Iflag;
	}

	public void setMp3Iflag(Boolean mp3Iflag) {
		this.mp3Iflag = mp3Iflag;
	}

	public Boolean getMp4Iflag() {
		return this.mp4Iflag;
	}

	public void setMp4Iflag(Boolean mp4Iflag) {
		this.mp4Iflag = mp4Iflag;
	}

	public String getPrintCodeImage() {
		return this.printCodeImage;
	}

	public void setPrintCodeImage(String printCodeImage) {
		this.printCodeImage = printCodeImage;
	}

	public String getMp1Image() {
		return this.mp1Image;
	}

	public void setMp1Image(String mp1Image) {
		this.mp1Image = mp1Image;
	}

	public String getMp2Image() {
		return this.mp2Image;
	}

	public void setMp2Image(String mp2Image) {
		this.mp2Image = mp2Image;
	}

	public String getMp3Image() {
		return this.mp3Image;
	}

	public void setMp3Image(String mp3Image) {
		this.mp3Image = mp3Image;
	}

	public String getMp4Image() {
		return this.mp4Image;
	}

	public void setMp4Image(String mp4Image) {
		this.mp4Image = mp4Image;
	}

	public String getNextEffectiveDate() {
		return this.nextEffectiveDate;
	}

	public void setNextEffectiveDate(String nextEffectiveDate) {
		this.nextEffectiveDate = nextEffectiveDate;
	}

	public String getNextProdmemo() {
		return this.nextProdmemo;
	}

	public void setNextProdmemo(String nextProdmemo) {
		this.nextProdmemo = nextProdmemo;
	}

	public String getNextPrintCode() {
		return this.nextPrintCode;
	}

	public void setNextPrintCode(String nextPrintCode) {
		this.nextPrintCode = nextPrintCode;
	}

	public String getNextPrintCodeImage() {
		return this.nextPrintCodeImage;
	}

	public void setNextPrintCodeImage(String nextPrintCodeImage) {
		this.nextPrintCodeImage = nextPrintCodeImage;
	}

	public Boolean getNextStampSetFg() {
		return this.nextStampSetFg;
	}

	public void setNextStampSetFg(Boolean nextStampSetFg) {
		this.nextStampSetFg = nextStampSetFg;
	}

	public Boolean getNextZipFg() {
		return this.nextZipFg;
	}

	public void setNextZipFg(Boolean nextZipFg) {
		this.nextZipFg = nextZipFg;
	}

	public Boolean getNextSelectFg() {
		return this.nextSelectFg;
	}

	public void setNextSelectFg(Boolean nextSelectFg) {
		this.nextSelectFg = nextSelectFg;
	}

	public String getNextRemark() {
		return this.nextRemark;
	}

	public void setNextRemark(String nextRemark) {
		this.nextRemark = nextRemark;
	}

	public String getNextMp1Word() {
		return this.nextMp1Word;
	}

	public void setNextMp1Word(String nextMp1Word) {
		this.nextMp1Word = nextMp1Word;
	}

	public String getNextMp2Word() {
		return this.nextMp2Word;
	}

	public void setNextMp2Word(String nextMp2Word) {
		this.nextMp2Word = nextMp2Word;
	}

	public String getNextMp3Word() {
		return this.nextMp3Word;
	}

	public void setNextMp3Word(String nextMp3Word) {
		this.nextMp3Word = nextMp3Word;
	}

	public String getNextMp4Word() {
		return this.nextMp4Word;
	}

	public void setNextMp4Word(String nextMp4Word) {
		this.nextMp4Word = nextMp4Word;
	}

	public Boolean getNextMp1Iflag() {
		return this.nextMp1Iflag;
	}

	public void setNextMp1Iflag(Boolean nextMp1Iflag) {
		this.nextMp1Iflag = nextMp1Iflag;
	}

	public Boolean getNextMp2Iflag() {
		return this.nextMp2Iflag;
	}

	public void setNextMp2Iflag(Boolean nextMp2Iflag) {
		this.nextMp2Iflag = nextMp2Iflag;
	}

	public Boolean getNextMp3Iflag() {
		return this.nextMp3Iflag;
	}

	public void setNextMp3Iflag(Boolean nextMp3Iflag) {
		this.nextMp3Iflag = nextMp3Iflag;
	}

	public Boolean getNextMp4Iflag() {
		return this.nextMp4Iflag;
	}

	public void setNextMp4Iflag(Boolean nextMp4Iflag) {
		this.nextMp4Iflag = nextMp4Iflag;
	}

	public String getNextMp1Image() {
		return this.nextMp1Image;
	}

	public void setNextMp1Image(String nextMp1Image) {
		this.nextMp1Image = nextMp1Image;
	}

	public String getNextMp2Image() {
		return this.nextMp2Image;
	}

	public void setNextMp2Image(String nextMp2Image) {
		this.nextMp2Image = nextMp2Image;
	}

	public String getNextMp3Image() {
		return this.nextMp3Image;
	}

	public void setNextMp3Image(String nextMp3Image) {
		this.nextMp3Image = nextMp3Image;
	}

	public String getNextMp4Image() {
		return this.nextMp4Image;
	}

	public void setNextMp4Image(String nextMp4Image) {
		this.nextMp4Image = nextMp4Image;
	}

	public Set getJobBagsForIdfMpNo1() {
		return this.jobBagsForIdfMpNo1;
	}

	public void setJobBagsForIdfMpNo1(Set jobBagsForIdfMpNo1) {
		this.jobBagsForIdfMpNo1 = jobBagsForIdfMpNo1;
	}

	public Set getJobBagsForIdfMpNo2() {
		return this.jobBagsForIdfMpNo2;
	}

	public void setJobBagsForIdfMpNo2(Set jobBagsForIdfMpNo2) {
		this.jobBagsForIdfMpNo2 = jobBagsForIdfMpNo2;
	}

	public Set getJobBagsForIdfMpNo3() {
		return this.jobBagsForIdfMpNo3;
	}

	public void setJobBagsForIdfMpNo3(Set jobBagsForIdfMpNo3) {
		this.jobBagsForIdfMpNo3 = jobBagsForIdfMpNo3;
	}

	public Set getJobCodesForIdfMpNo1() {
		return this.jobCodesForIdfMpNo1;
	}

	public void setJobCodesForIdfMpNo1(Set jobCodesForIdfMpNo1) {
		this.jobCodesForIdfMpNo1 = jobCodesForIdfMpNo1;
	}

	public Set getJobCodesForIdfMpNo2() {
		return this.jobCodesForIdfMpNo2;
	}

	public void setJobCodesForIdfMpNo2(Set jobCodesForIdfMpNo2) {
		this.jobCodesForIdfMpNo2 = jobCodesForIdfMpNo2;
	}

	public Set getJobCodesForIdfMpNo3() {
		return this.jobCodesForIdfMpNo3;
	}

	public void setJobCodesForIdfMpNo3(Set jobCodesForIdfMpNo3) {
		this.jobCodesForIdfMpNo3 = jobCodesForIdfMpNo3;
	}

}