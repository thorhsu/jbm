package com.salmat.jbm.bean;

import java.util.HashSet;
import java.util.Set;

import com.salmat.jbm.hibernate.Customer;

/**
 * Lpinfo entity. @author MyEclipse Persistence Tools
 */

public class MpinfoJSON implements java.io.Serializable {

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
	public String getMpNo() {
		return mpNo;
	}
	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getStampSet() {
		return stampSet;
	}
	public void setStampSet(String stampSet) {
		this.stampSet = stampSet;
	}
	public Boolean getStampSetFg() {
		return stampSetFg;
	}
	public void setStampSetFg(Boolean stampSetFg) {
		this.stampSetFg = stampSetFg;
	}
	public Boolean getSelectFg() {
		return selectFg;
	}
	public void setSelectFg(Boolean selectFg) {
		this.selectFg = selectFg;
	}
	public Boolean getZipFg() {
		return zipFg;
	}
	public void setZipFg(Boolean zipFg) {
		this.zipFg = zipFg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public double getMpPrice() {
		return mpPrice;
	}
	public void setMpPrice(double mpPrice) {
		this.mpPrice = mpPrice;
	}
	public String getPrintCode() {
		return printCode;
	}
	public void setPrintCode(String printCode) {
		this.printCode = printCode;
	}
	public double getInsertCnt() {
		return insertCnt;
	}
	public void setInsertCnt(double insertCnt) {
		this.insertCnt = insertCnt;
	}

	public String getMp1Word() {
		return mp1Word;
	}
	public void setMp1Word(String mp1Word) {
		this.mp1Word = mp1Word;
	}
	public String getMp2Word() {
		return mp2Word;
	}
	public void setMp2Word(String mp2Word) {
		this.mp2Word = mp2Word;
	}
	public String getMp3Word() {
		return mp3Word;
	}
	public void setMp3Word(String mp3Word) {
		this.mp3Word = mp3Word;
	}
	public String getMp4Word() {
		return mp4Word;
	}
	public void setMp4Word(String mp4Word) {
		this.mp4Word = mp4Word;
	}
	public String getProdmemo() {
		return prodmemo;
	}
	public void setProdmemo(String prodmemo) {
		this.prodmemo = prodmemo;
	}
	public Boolean getMp1Iflag() {
		return mp1Iflag;
	}
	public void setMp1Iflag(Boolean mp1Iflag) {
		this.mp1Iflag = mp1Iflag;
	}
	public Boolean getMp2Iflag() {
		return mp2Iflag;
	}
	public void setMp2Iflag(Boolean mp2Iflag) {
		this.mp2Iflag = mp2Iflag;
	}
	public Boolean getMp3Iflag() {
		return mp3Iflag;
	}
	public void setMp3Iflag(Boolean mp3Iflag) {
		this.mp3Iflag = mp3Iflag;
	}
	public Boolean getMp4Iflag() {
		return mp4Iflag;
	}
	public void setMp4Iflag(Boolean mp4Iflag) {
		this.mp4Iflag = mp4Iflag;
	}
	
	
}