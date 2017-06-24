package com.salmat.jbm.hibernate;

/**
 * ZipcodeInfo entity. 
 * @author James_Tsai
 */

public class ZipcodeDetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7860455853833598028L;
	// Fields

	private Integer id; // PK
	private ZipcodeInfo zipcodeInfo; // FK
	private String zipcode;
	private Integer zipcodeSum;
	private Integer zipcodeTotal;
	

	// Constructors

	/** default constructor */
	public ZipcodeDetail() {

	}

	/** minimal constructor */
	public ZipcodeDetail(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public ZipcodeDetail(Integer id, ZipcodeInfo zipcodeInfo,
			String zipcode, Integer zipcodeSum, Integer zipcodeTotal){
		
		this.id = id;
		this.zipcodeInfo = zipcodeInfo;
		this.zipcode = zipcode;
		this.zipcodeSum = zipcodeSum;
		this.zipcodeTotal = zipcodeTotal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZipcodeInfo getZipcodeInfo() {
		return zipcodeInfo;
	}

	public void setZipcodeInfo(ZipcodeInfo zipcodeInfo) {
		this.zipcodeInfo = zipcodeInfo;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Integer getZipcodeSum() {
		return zipcodeSum;
	}

	public void setZipcodeSum(Integer zipcodeSum) {
		this.zipcodeSum = zipcodeSum;
	}

	public Integer getZipcodeTotal() {
		return zipcodeTotal;
	}

	public void setZipcodeTotal(Integer zipcodeTotal) {
		this.zipcodeTotal = zipcodeTotal;
	}

}