/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.form;

import java.sql.Clob;
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



public class AcctGenerateDebitNoteForm extends ActionForm {
	// Fields

	private Integer id;
	private String custNo;
	private String section;
	private Double formatP;
	private Double printP;
	private Double mailP;
	private Double ps2P;
	private Double stickP;
	private Double insertP;
	private Double manualP;
	private Double programP;
	private Double addP;
	private Double lowvol;
	private Double insert1P;
	private String receiver;
	private String custname;
	private String address;
	private String tel;
	private String startdt;
	private String enddt;
	private String payterm;
	private String bankname;
	private String account;
	private Boolean vat;
	private Double mail1P;
	private Double mail2P;
	private Double FFNonS;
	private Double FFSP;
	private Double IFSetup;
	private Double OFSetup;
	private Double standardR;
	private Double addFAP;
	private Double accManP;
	private Double dedupeP;
	private Double outputP;
	private Double processP;
	private Double matchback;
	private Double matchback2;
	private Double zipcodeP;
	private Double cdromP;
	private Double discount;
	private Double qcaP;
	private Double minFree;
	private Double pstock1P;
	private Double pstock2P;
	private Double estock1P;
	private Double estock2P;
	private Double adjLP;
	private Double courierP;
	private Double print1P;
	private Double bindP;
	private Double bind1P;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public Double getFormatP() {
		return formatP;
	}
	public void setFormatP(Double formatP) {
		this.formatP = formatP;
	}
	public Double getPrintP() {
		return printP;
	}
	public void setPrintP(Double printP) {
		this.printP = printP;
	}
	public Double getMailP() {
		return mailP;
	}
	public void setMailP(Double mailP) {
		this.mailP = mailP;
	}
	public Double getPs2P() {
		return ps2P;
	}
	public void setPs2P(Double ps2p) {
		ps2P = ps2p;
	}
	public Double getStickP() {
		return stickP;
	}
	public void setStickP(Double stickP) {
		this.stickP = stickP;
	}
	public Double getInsertP() {
		return insertP;
	}
	public void setInsertP(Double insertP) {
		this.insertP = insertP;
	}
	public Double getManualP() {
		return manualP;
	}
	public void setManualP(Double manualP) {
		this.manualP = manualP;
	}
	public Double getProgramP() {
		return programP;
	}
	public void setProgramP(Double programP) {
		this.programP = programP;
	}
	public Double getAddP() {
		return addP;
	}
	public void setAddP(Double addP) {
		this.addP = addP;
	}
	public Double getLowvol() {
		return lowvol;
	}
	public void setLowvol(Double lowvol) {
		this.lowvol = lowvol;
	}
	public Double getInsert1P() {
		return insert1P;
	}
	public void setInsert1P(Double insert1p) {
		insert1P = insert1p;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getStartdt() {
		return startdt;
	}
	public void setStartdt(String startdt) {
		this.startdt = startdt;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getPayterm() {
		return payterm;
	}
	public void setPayterm(String payterm) {
		this.payterm = payterm;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Boolean getVat() {
		return vat;
	}
	public void setVat(Boolean vat) {
		this.vat = vat;
	}
	public Double getMail1P() {
		return mail1P;
	}
	public void setMail1P(Double mail1p) {
		mail1P = mail1p;
	}
	public Double getMail2P() {
		return mail2P;
	}
	public void setMail2P(Double mail2p) {
		mail2P = mail2p;
	}
	public Double getFFNonS() {
		return FFNonS;
	}
	public void setFFNonS(Double fFNonS) {
		FFNonS = fFNonS;
	}
	public Double getFFSP() {
		return FFSP;
	}
	public void setFFSP(Double fFSP) {
		FFSP = fFSP;
	}
	public Double getIFSetup() {
		return IFSetup;
	}
	public void setIFSetup(Double iFSetup) {
		IFSetup = iFSetup;
	}
	public Double getOFSetup() {
		return OFSetup;
	}
	public void setOFSetup(Double oFSetup) {
		OFSetup = oFSetup;
	}
	public Double getStandardR() {
		return standardR;
	}
	public void setStandardR(Double standardR) {
		this.standardR = standardR;
	}
	public Double getAddFAP() {
		return addFAP;
	}
	public void setAddFAP(Double addFAP) {
		this.addFAP = addFAP;
	}
	public Double getAccManP() {
		return accManP;
	}
	public void setAccManP(Double accManP) {
		this.accManP = accManP;
	}
	public Double getDedupeP() {
		return dedupeP;
	}
	public void setDedupeP(Double dedupeP) {
		this.dedupeP = dedupeP;
	}
	public Double getOutputP() {
		return outputP;
	}
	public void setOutputP(Double outputP) {
		this.outputP = outputP;
	}
	public Double getProcessP() {
		return processP;
	}
	public void setProcessP(Double processP) {
		this.processP = processP;
	}
	public Double getMatchback() {
		return matchback;
	}
	public void setMatchback(Double matchback) {
		this.matchback = matchback;
	}
	public Double getMatchback2() {
		return matchback2;
	}
	public void setMatchback2(Double matchback2) {
		this.matchback2 = matchback2;
	}
	public Double getZipcodeP() {
		return zipcodeP;
	}
	public void setZipcodeP(Double zipcodeP) {
		this.zipcodeP = zipcodeP;
	}
	public Double getCdromP() {
		return cdromP;
	}
	public void setCdromP(Double cdromP) {
		this.cdromP = cdromP;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Double getQcaP() {
		return qcaP;
	}
	public void setQcaP(Double qcaP) {
		this.qcaP = qcaP;
	}
	public Double getMinFree() {
		return minFree;
	}
	public void setMinFree(Double minFree) {
		this.minFree = minFree;
	}
	public Double getPstock1P() {
		return pstock1P;
	}
	public void setPstock1P(Double pstock1p) {
		pstock1P = pstock1p;
	}
	public Double getPstock2P() {
		return pstock2P;
	}
	public void setPstock2P(Double pstock2p) {
		pstock2P = pstock2p;
	}
	public Double getEstock1P() {
		return estock1P;
	}
	public void setEstock1P(Double estock1p) {
		estock1P = estock1p;
	}
	public Double getEstock2P() {
		return estock2P;
	}
	public void setEstock2P(Double estock2p) {
		estock2P = estock2p;
	}
	public Double getAdjLP() {
		return adjLP;
	}
	public void setAdjLP(Double adjLP) {
		this.adjLP = adjLP;
	}
	public Double getCourierP() {
		return courierP;
	}
	public void setCourierP(Double courierP) {
		this.courierP = courierP;
	}
	public Double getPrint1P() {
		return print1P;
	}
	public void setPrint1P(Double print1p) {
		print1P = print1p;
	}
	public Double getBindP() {
		return bindP;
	}
	public void setBindP(Double bindP) {
		this.bindP = bindP;
	}
	public Double getBind1P() {
		return bind1P;
	}
	public void setBind1P(Double bind1p) {
		bind1P = bind1p;
	}
	
	
	
}