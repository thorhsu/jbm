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
import com.salmat.jbm.hibernate.JobBag;



public class AcctMasterForm extends ActionForm {
	// Fields

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getJobBagNo() {
		return jobBagNo;
	}
	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getAcctjobno() {
		return acctjobno;
	}
	public void setAcctjobno(String acctjobno) {
		this.acctjobno = acctjobno;
	}
	public String getCostcenter() {
		return costcenter;
	}
	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}
	public Boolean getFormatFg() {
		return formatFg;
	}
	public void setFormatFg(Boolean formatFg) {
		this.formatFg = formatFg;
	}
	public Boolean getPrintFg() {
		return printFg;
	}
	public void setPrintFg(Boolean printFg) {
		this.printFg = printFg;
	}
	public Boolean getMailFg() {
		return mailFg;
	}
	public void setMailFg(Boolean mailFg) {
		this.mailFg = mailFg;
	}
	public Boolean getPs2Fg() {
		return ps2Fg;
	}
	public void setPs2Fg(Boolean ps2Fg) {
		this.ps2Fg = ps2Fg;
	}
	public Boolean getStickFg() {
		return stickFg;
	}
	public void setStickFg(Boolean stickFg) {
		this.stickFg = stickFg;
	}
	public Boolean getAddFg() {
		return addFg;
	}
	public void setAddFg(Boolean addFg) {
		this.addFg = addFg;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public Boolean getA4Adj1Fg() {
		return a4Adj1Fg;
	}
	public void setA4Adj1Fg(Boolean a4Adj1Fg) {
		this.a4Adj1Fg = a4Adj1Fg;
	}
	public Double getA4Adj1No() {
		return a4Adj1No;
	}
	public void setA4Adj1No(Double a4Adj1No) {
		this.a4Adj1No = a4Adj1No;
	}
	public Boolean getA4Adj2Fg() {
		return a4Adj2Fg;
	}
	public void setA4Adj2Fg(Boolean a4Adj2Fg) {
		this.a4Adj2Fg = a4Adj2Fg;
	}
	public Double getA4Adj2No() {
		return a4Adj2No;
	}
	public void setA4Adj2No(Double a4Adj2No) {
		this.a4Adj2No = a4Adj2No;
	}
	public Double getA4Adj2Re() {
		return a4Adj2Re;
	}
	public void setA4Adj2Re(Double a4Adj2Re) {
		this.a4Adj2Re = a4Adj2Re;
	}
	public Boolean getA4Adj3Fg() {
		return a4Adj3Fg;
	}
	public void setA4Adj3Fg(Boolean a4Adj3Fg) {
		this.a4Adj3Fg = a4Adj3Fg;
	}
	public Double getA4Adj3No() {
		return a4Adj3No;
	}
	public void setA4Adj3No(Double a4Adj3No) {
		this.a4Adj3No = a4Adj3No;
	}
	public Double getA4Adj3Re() {
		return a4Adj3Re;
	}
	public void setA4Adj3Re(Double a4Adj3Re) {
		this.a4Adj3Re = a4Adj3Re;
	}
	public Boolean getSameFg() {
		return sameFg;
	}
	public void setSameFg(Boolean sameFg) {
		this.sameFg = sameFg;
	}
	public String getSJobno() {
		return SJobno;
	}
	public void setSJobno(String sJobno) {
		SJobno = sJobno;
	}
	public Double getSPcent() {
		return SPcent;
	}
	public void setSPcent(Double sPcent) {
		SPcent = sPcent;
	}
	public String getSJobno1() {
		return SJobno1;
	}
	public void setSJobno1(String sJobno1) {
		SJobno1 = sJobno1;
	}
	public Double getSPcent1() {
		return SPcent1;
	}
	public void setSPcent1(Double sPcent1) {
		SPcent1 = sPcent1;
	}
	public String getSJobno2() {
		return SJobno2;
	}
	public void setSJobno2(String sJobno2) {
		SJobno2 = sJobno2;
	}
	public Double getSPcent2() {
		return SPcent2;
	}
	public void setSPcent2(Double sPcent2) {
		SPcent2 = sPcent2;
	}
	public String getSJobno3() {
		return SJobno3;
	}
	public void setSJobno3(String sJobno3) {
		SJobno3 = sJobno3;
	}
	public Double getSPcent3() {
		return SPcent3;
	}
	public void setSPcent3(Double sPcent3) {
		SPcent3 = sPcent3;
	}
	public String getJobtype() {
		return jobtype;
	}
	public void setJobtype(String jobtype) {
		this.jobtype = jobtype;
	}
	public Boolean getPrinttime() {
		return printtime;
	}
	public void setPrinttime(Boolean printtime) {
		this.printtime = printtime;
	}
	public Boolean getDuplex() {
		return duplex;
	}
	public void setDuplex(Boolean duplex) {
		this.duplex = duplex;
	}
	public Boolean getMail1Fg() {
		return mail1Fg;
	}
	public void setMail1Fg(Boolean mail1Fg) {
		this.mail1Fg = mail1Fg;
	}
	public Boolean getMail2Fg() {
		return mail2Fg;
	}
	public void setMail2Fg(Boolean mail2Fg) {
		this.mail2Fg = mail2Fg;
	}
	public Boolean getDuplexFp() {
		return duplexFp;
	}
	public void setDuplexFp(Boolean duplexFp) {
		this.duplexFp = duplexFp;
	}
	public Boolean getDataclean() {
		return dataclean;
	}
	public void setDataclean(Boolean dataclean) {
		this.dataclean = dataclean;
	}
	public Boolean getQcaFg() {
		return qcaFg;
	}
	public void setQcaFg(Boolean qcaFg) {
		this.qcaFg = qcaFg;
	}
	public Boolean getPstock1Fg() {
		return pstock1Fg;
	}
	public void setPstock1Fg(Boolean pstock1Fg) {
		this.pstock1Fg = pstock1Fg;
	}
	public Boolean getPstock2Fg() {
		return pstock2Fg;
	}
	public void setPstock2Fg(Boolean pstock2Fg) {
		this.pstock2Fg = pstock2Fg;
	}
	public Boolean getEstock1Fg() {
		return estock1Fg;
	}
	public void setEstock1Fg(Boolean estock1Fg) {
		this.estock1Fg = estock1Fg;
	}
	public Boolean getEstock2Fg() {
		return estock2Fg;
	}
	public void setEstock2Fg(Boolean estock2Fg) {
		this.estock2Fg = estock2Fg;
	}
	public Boolean getPrint1Fg() {
		return print1Fg;
	}
	public void setPrint1Fg(Boolean print1Fg) {
		this.print1Fg = print1Fg;
	}
	public Boolean getCourierFg() {
		return courierFg;
	}
	public void setCourierFg(Boolean courierFg) {
		this.courierFg = courierFg;
	}
	public Boolean getBindFg() {
		return bindFg;
	}
	public void setBindFg(Boolean bindFg) {
		this.bindFg = bindFg;
	}
	public Boolean getBind1Fg() {
		return bind1Fg;
	}
	public void setBind1Fg(Boolean bind1Fg) {
		this.bind1Fg = bind1Fg;
	}
	private Integer id;
	private String jobBagNo;
	private String section;
	private String custNo;
	private String acctjobno;
	private String costcenter;
	private Boolean formatFg;
	private Boolean printFg;
	private Boolean mailFg;
	private Boolean ps2Fg;
	private Boolean stickFg;
	private Boolean addFg;
	private String jobDesc;
	private Boolean a4Adj1Fg;
	private Double a4Adj1No;
	private Boolean a4Adj2Fg;
	private Double a4Adj2No;
	private Double a4Adj2Re;
	private Boolean a4Adj3Fg;
	private Double a4Adj3No;
	private Double a4Adj3Re;
	private Boolean sameFg;
	private String SJobno;
	private Double SPcent;
	private String SJobno1;
	private Double SPcent1;
	private String SJobno2;
	private Double SPcent2;
	private String SJobno3;
	private Double SPcent3;
	private String jobtype;
	private Boolean printtime;
	private Boolean duplex;
	private Boolean mail1Fg;
	private Boolean mail2Fg;
	private Boolean duplexFp;
	private Boolean dataclean;
	private Boolean qcaFg;
	private Boolean pstock1Fg;
	private Boolean pstock2Fg;
	private Boolean estock1Fg;
	private Boolean estock2Fg;
	private Boolean print1Fg;
	private Boolean courierFg;
	private Boolean bindFg;
	private Boolean bind1Fg;
	
}