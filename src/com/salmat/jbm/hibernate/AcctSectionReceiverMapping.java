package com.salmat.jbm.hibernate;

/**
 * AcctSectionReceiverMapping entity. @author MyEclipse Persistence Tools
 */

public class AcctSectionReceiverMapping implements java.io.Serializable {

	// Fields

	private Integer id;
	private CustomerReceiver customerReceiver;
	private Customer customer;
	private String section;
	private String reportIiiTitle;
	private Boolean mergeReportIiiFg1;
	private Boolean mergeReportIiiFg2;
	private Boolean mergeReportIiiFg3;
	private String mergeReportIiiTitle1;
	private String mergeReportIiiTitle2;
	private String mergeReportIiiTitle3;
	private String mergeReportIiiSubtitle1;
	private String mergeReportIiiSubtitle2;
	private String mergeReportIiiSubtitle3;
	private Boolean vat;
	private Integer paymentTerm;
	private Boolean isSummarizationOfSum1;

	// Constructors

	/** default constructor */
	public AcctSectionReceiverMapping() {
	}

	/** full constructor */
	public AcctSectionReceiverMapping(CustomerReceiver customerReceiver,
			Customer customer, String section, String reportIiiTitle,
			Boolean mergeReportIiiFg1, Boolean mergeReportIiiFg2,
			Boolean mergeReportIiiFg3, String mergeReportIiiTitle1,
			String mergeReportIiiTitle2, String mergeReportIiiTitle3,
			String mergeReportIiiSubtitle1, String mergeReportIiiSubtitle2,
			String mergeReportIiiSubtitle3, Boolean vat, Integer paymentTerm,
			Boolean isSummarizationOfSum1) {
		this.customerReceiver = customerReceiver;
		this.customer = customer;
		this.section = section;
		this.reportIiiTitle = reportIiiTitle;
		this.mergeReportIiiFg1 = mergeReportIiiFg1;
		this.mergeReportIiiFg2 = mergeReportIiiFg2;
		this.mergeReportIiiFg3 = mergeReportIiiFg3;
		this.mergeReportIiiTitle1 = mergeReportIiiTitle1;
		this.mergeReportIiiTitle2 = mergeReportIiiTitle2;
		this.mergeReportIiiTitle3 = mergeReportIiiTitle3;
		this.mergeReportIiiSubtitle1 = mergeReportIiiSubtitle1;
		this.mergeReportIiiSubtitle2 = mergeReportIiiSubtitle2;
		this.mergeReportIiiSubtitle3 = mergeReportIiiSubtitle3;
		this.vat = vat;
		this.paymentTerm = paymentTerm;
		this.isSummarizationOfSum1 = isSummarizationOfSum1;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CustomerReceiver getCustomerReceiver() {
		return this.customerReceiver;
	}

	public void setCustomerReceiver(CustomerReceiver customerReceiver) {
		this.customerReceiver = customerReceiver;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getSection() {
		return this.section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getReportIiiTitle() {
		return this.reportIiiTitle;
	}

	public void setReportIiiTitle(String reportIiiTitle) {
		this.reportIiiTitle = reportIiiTitle;
	}

	public Boolean getMergeReportIiiFg1() {
		return this.mergeReportIiiFg1;
	}

	public void setMergeReportIiiFg1(Boolean mergeReportIiiFg1) {
		this.mergeReportIiiFg1 = mergeReportIiiFg1;
	}

	public Boolean getMergeReportIiiFg2() {
		return this.mergeReportIiiFg2;
	}

	public void setMergeReportIiiFg2(Boolean mergeReportIiiFg2) {
		this.mergeReportIiiFg2 = mergeReportIiiFg2;
	}

	public Boolean getMergeReportIiiFg3() {
		return this.mergeReportIiiFg3;
	}

	public void setMergeReportIiiFg3(Boolean mergeReportIiiFg3) {
		this.mergeReportIiiFg3 = mergeReportIiiFg3;
	}

	public String getMergeReportIiiTitle1() {
		return this.mergeReportIiiTitle1;
	}

	public void setMergeReportIiiTitle1(String mergeReportIiiTitle1) {
		this.mergeReportIiiTitle1 = mergeReportIiiTitle1;
	}

	public String getMergeReportIiiTitle2() {
		return this.mergeReportIiiTitle2;
	}

	public void setMergeReportIiiTitle2(String mergeReportIiiTitle2) {
		this.mergeReportIiiTitle2 = mergeReportIiiTitle2;
	}

	public String getMergeReportIiiTitle3() {
		return this.mergeReportIiiTitle3;
	}

	public void setMergeReportIiiTitle3(String mergeReportIiiTitle3) {
		this.mergeReportIiiTitle3 = mergeReportIiiTitle3;
	}

	public String getMergeReportIiiSubtitle1() {
		return this.mergeReportIiiSubtitle1;
	}

	public void setMergeReportIiiSubtitle1(String mergeReportIiiSubtitle1) {
		this.mergeReportIiiSubtitle1 = mergeReportIiiSubtitle1;
	}

	public String getMergeReportIiiSubtitle2() {
		return this.mergeReportIiiSubtitle2;
	}

	public void setMergeReportIiiSubtitle2(String mergeReportIiiSubtitle2) {
		this.mergeReportIiiSubtitle2 = mergeReportIiiSubtitle2;
	}

	public String getMergeReportIiiSubtitle3() {
		return this.mergeReportIiiSubtitle3;
	}

	public void setMergeReportIiiSubtitle3(String mergeReportIiiSubtitle3) {
		this.mergeReportIiiSubtitle3 = mergeReportIiiSubtitle3;
	}

	public Boolean getVat() {
		return this.vat;
	}

	public void setVat(Boolean vat) {
		this.vat = vat;
	}

	public Integer getPaymentTerm() {
		return this.paymentTerm;
	}

	public void setPaymentTerm(Integer paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public Boolean getIsSummarizationOfSum1() {
		return this.isSummarizationOfSum1;
	}

	public void setIsSummarizationOfSum1(Boolean isSummarizationOfSum1) {
		this.isSummarizationOfSum1 = isSummarizationOfSum1;
	}

}