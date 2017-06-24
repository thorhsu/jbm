package com.salmat.jbm.bean;

import java.util.Date;

public class MinimalChargePrice {
	private Integer sum1Id;
	private String jobBagNo; //固定為 CT (CitiOne/CitiGold)
	private Double[] total ;
	private Integer chargeItemCounter;	
	private Double percent;
	private Double newTotal;
	public Integer getSum1Id() {
		return sum1Id;
	}
	public void setSum1Id(Integer sum1Id) {
		this.sum1Id = sum1Id;
	}
	public String getJobBagNo() {
		return jobBagNo;
	}
	public void setJobBagNo(String jobBagNo) {
		this.jobBagNo = jobBagNo;
	}


	public Double[] getTotal() {
		return total;
	}
	public void setTotal(Double[] total) {
		this.total = total;
	}
	public Integer getChargeItemCounter() {
		return chargeItemCounter;
	}
	public void setChargeItemCounter(Integer chargeItemCounter) {
		this.chargeItemCounter = chargeItemCounter;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	public Double getNewTotal() {
		return newTotal;
	}
	public void setNewTotal(Double newTotal) {
		this.newTotal = newTotal;
	}

}
