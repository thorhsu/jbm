package com.salmat.jbm.bean;

import java.util.Date;
import java.util.List;

import com.salmat.jbm.hibernate.AcctDno;
import com.salmat.jbm.hibernate.Customer;

public class AcctSum3IncludeQty {
	private String title;
	private String subTitle;
	private String excludeQty;
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getExcludeQty() {
		return excludeQty;
	}
	public void setExcludeQty(String excludeQty) {
		this.excludeQty = excludeQty;
	}

	
	
}
