package com.salmat.jbm.hibernate;

import java.sql.Clob;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Employee entity. @author MyEclipse Persistence Tools
 */

public class Employee implements java.io.Serializable {

	// Fields

	private String empNo;
	private Code code;
	private String EName;
	private String CName;
	private String pid;
	private String dept;
	private String title;
	private String ext;
	private String HAddress;
	private String areaCode;
	private String HTel;
	private String bbcall;
	private String mobile;
	private String birthday;
	private Clob picture1;
	private String userId;
	private String userName;
	private String password;
	private Date createDate;
	private Date modifyDate;
	private Boolean enabled;
	private Boolean role1s;
	private Boolean role1c;
	private Boolean role2s;
	private Boolean role2c;
	private Boolean role3s;
	private Boolean role3c;
	private Boolean role4s;
	private Boolean role4c;
	private Boolean role5s;
	private Boolean role5c;
	private Boolean role6m;
	private String password1;
	private String password2;
	private String password3;
	private String password4;
	private String password5;
	private Date latestLoginDate;
	private String adName;
	private String adPassword;
	private String substitute;
	
	private Integer loginErrorCount;
	private Set customers = new HashSet(0);
	private Set jobBags = new HashSet(0);

	// Constructors

	/** default constructor */
	public Employee() {
	}

	/** minimal constructor */
	public Employee(String empNo) {
		this.empNo = empNo;
	}

	/** full constructor */
	public Employee(String empNo, Code code, String EName, String CName,
			String pid, String dept, String title, String ext, String HAddress,
			String areaCode, String HTel, String bbcall, String mobile,
			String birthday, Clob picture1, String userId, String userName,
			String password, Date createDate, Date modifyDate, Boolean enabled,
			Boolean role1s, Boolean role1c, Boolean role2s, Boolean role2c,
			Boolean role3s, Boolean role3c, Boolean role4s, Boolean role4c,
			Boolean role5s, Boolean role5c, Boolean role6m, String password1,
			String password2, String password3, String password4,
			String password5, Date latestLoginDate, Integer loginErrorCount,
			String adName, String adPassowrd,
			Set customers, Set jobBags) {
		this.empNo = empNo;
		this.code = code;
		this.EName = EName;
		this.CName = CName;
		this.pid = pid;
		this.dept = dept;
		this.title = title;
		this.ext = ext;
		this.HAddress = HAddress;
		this.areaCode = areaCode;
		this.HTel = HTel;
		this.bbcall = bbcall;
		this.mobile = mobile;
		this.birthday = birthday;
		this.picture1 = picture1;
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.enabled = enabled;
		this.role1s = role1s;
		this.role1c = role1c;
		this.role2s = role2s;
		this.role2c = role2c;
		this.role3s = role3s;
		this.role3c = role3c;
		this.role4s = role4s;
		this.role4c = role4c;
		this.role5s = role5s;
		this.role5c = role5c;
		this.role6m = role6m;
		this.password1 = password1;
		this.password2 = password2;
		this.password3 = password3;
		this.password4 = password4;
		this.password5 = password5;
		this.latestLoginDate = latestLoginDate;
		this.loginErrorCount = loginErrorCount;
		this.adName = adName;
		this.adPassword = adPassowrd;
		this.customers = customers;
		this.jobBags = jobBags;
	}

	// Property accessors

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public Code getCode() {
		return this.code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public String getEName() {
		return this.EName;
	}

	public void setEName(String EName) {
		this.EName = EName;
	}

	public String getCName() {
		return this.CName;
	}

	public void setCName(String CName) {
		this.CName = CName;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExt() {
		return this.ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getHAddress() {
		return this.HAddress;
	}

	public void setHAddress(String HAddress) {
		this.HAddress = HAddress;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getHTel() {
		return this.HTel;
	}

	public void setHTel(String HTel) {
		this.HTel = HTel;
	}

	public String getBbcall() {
		return this.bbcall;
	}

	public void setBbcall(String bbcall) {
		this.bbcall = bbcall;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Clob getPicture1() {
		return this.picture1;
	}

	public void setPicture1(Clob picture1) {
		this.picture1 = picture1;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getRole1s() {
		return this.role1s;
	}

	public void setRole1s(Boolean role1s) {
		this.role1s = role1s;
	}

	public Boolean getRole1c() {
		return this.role1c;
	}

	public void setRole1c(Boolean role1c) {
		this.role1c = role1c;
	}

	public Boolean getRole2s() {
		return this.role2s;
	}

	public void setRole2s(Boolean role2s) {
		this.role2s = role2s;
	}

	public Boolean getRole2c() {
		return this.role2c;
	}

	public void setRole2c(Boolean role2c) {
		this.role2c = role2c;
	}

	public Boolean getRole3s() {
		return this.role3s;
	}

	public void setRole3s(Boolean role3s) {
		this.role3s = role3s;
	}

	public Boolean getRole3c() {
		return this.role3c;
	}

	public void setRole3c(Boolean role3c) {
		this.role3c = role3c;
	}

	public Boolean getRole4s() {
		return this.role4s;
	}

	public void setRole4s(Boolean role4s) {
		this.role4s = role4s;
	}

	public Boolean getRole4c() {
		return this.role4c;
	}

	public void setRole4c(Boolean role4c) {
		this.role4c = role4c;
	}

	public Boolean getRole5s() {
		return this.role5s;
	}

	public void setRole5s(Boolean role5s) {
		this.role5s = role5s;
	}

	public Boolean getRole5c() {
		return this.role5c;
	}

	public void setRole5c(Boolean role5c) {
		this.role5c = role5c;
	}

	public Boolean getRole6m() {
		return this.role6m;
	}

	public void setRole6m(Boolean role6m) {
		this.role6m = role6m;
	}

	public String getPassword1() {
		return this.password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return this.password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword3() {
		return this.password3;
	}

	public void setPassword3(String password3) {
		this.password3 = password3;
	}

	public String getPassword4() {
		return this.password4;
	}

	public void setPassword4(String password4) {
		this.password4 = password4;
	}

	public String getPassword5() {
		return this.password5;
	}

	public void setPassword5(String password5) {
		this.password5 = password5;
	}

	public Date getLatestLoginDate() {
		return this.latestLoginDate;
	}

	public void setLatestLoginDate(Date latestLoginDate) {
		this.latestLoginDate = latestLoginDate;
	}

	public Integer getLoginErrorCount() {
		return this.loginErrorCount;
	}

	public void setLoginErrorCount(Integer loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}

	public Set getCustomers() {
		return this.customers;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdPassword() {
		return adPassword;
	}

	public void setAdPassword(String adPassword) {
		this.adPassword = adPassword;
	}

	public void setCustomers(Set customers) {
		this.customers = customers;
	}

	public Set getJobBags() {
		return this.jobBags;
	}

	public void setJobBags(Set jobBags) {
		this.jobBags = jobBags;
	}
	
	public String getSubstitute() {
		return substitute;
	}

	public void setSubstitute(String substitute) {
		this.substitute = substitute;
	}


}