package com.salmat.jbm.hibernate;

public class LgPrice {
	private Integer id;
	private Integer lgType;
	private String lgTypeName;
	private Integer lgMailCategory;
	private String lgMailCategoryName;
	private Double price;
	private Integer weight;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLgType() {
		return lgType;
	}

	public void setLgType(Integer lgType) {
		this.lgType = lgType;
	}

	public String getLgTypeName() {
		return lgTypeName;
	}

	public void setLgTypeName(String lgTypeName) {
		this.lgTypeName = lgTypeName;
	}

	public Integer getLgMailCategory() {
		return lgMailCategory;
	}

	public void setLgMailCategory(Integer lgMailCategory) {
		this.lgMailCategory = lgMailCategory;
	}

	public String getLgMailCategoryName() {
		return lgMailCategoryName;
	}

	public void setLgMailCategoryName(String lgMailCategoryName) {
		this.lgMailCategoryName = lgMailCategoryName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}
