package com.salmat.jbm.hibernate;

/**
 * MenuItemPermission entity. @author MyEclipse Persistence Tools
 */

public class MenuItemPermission implements java.io.Serializable {

	// Fields

	private Integer id;
	private String menuName;
	private String menuValueTw;
	private String menuValueEn;
	private String menuValueCn;
	private String menuItemName;
	private String menuItemValueTw;
	private String menuItemValueEn;
	private String menuItemValueCn;
	private String uri;
	private String uriList;
	private Boolean roles1s;
	private Boolean roles1c;
	private Boolean roles2s;
	private Boolean roles2c;
	private Boolean roles3s;
	private Boolean roles3c;
	private Boolean roles4s;
	private Boolean roles4c;
	private Boolean roles5s;
	private Boolean roles5c;
	private Boolean roles6m;

	// Constructors

	/** default constructor */
	public MenuItemPermission() {
	}

	/** minimal constructor */
	public MenuItemPermission(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public MenuItemPermission(Integer id, String menuName, String menuValueTw,
			String menuValueEn, String menuValueCn, String menuItemName,
			String menuItemValueTw, String menuItemValueEn,
			String menuItemValueCn, String uri, String uriList,
			Boolean roles1s, Boolean roles1c, Boolean roles2s, Boolean roles2c,
			Boolean roles3s, Boolean roles3c, Boolean roles4s, Boolean roles4c,
			Boolean roles5s, Boolean roles5c, Boolean roles6m) {
		this.id = id;
		this.menuName = menuName;
		this.menuValueTw = menuValueTw;
		this.menuValueEn = menuValueEn;
		this.menuValueCn = menuValueCn;
		this.menuItemName = menuItemName;
		this.menuItemValueTw = menuItemValueTw;
		this.menuItemValueEn = menuItemValueEn;
		this.menuItemValueCn = menuItemValueCn;
		this.uri = uri;
		this.uriList = uriList;
		this.roles1s = roles1s;
		this.roles1c = roles1c;
		this.roles2s = roles2s;
		this.roles2c = roles2c;
		this.roles3s = roles3s;
		this.roles3c = roles3c;
		this.roles4s = roles4s;
		this.roles4c = roles4c;
		this.roles5s = roles5s;
		this.roles5c = roles5c;
		this.roles6m = roles6m;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuValueTw() {
		return this.menuValueTw;
	}

	public void setMenuValueTw(String menuValueTw) {
		this.menuValueTw = menuValueTw;
	}

	public String getMenuValueEn() {
		return this.menuValueEn;
	}

	public void setMenuValueEn(String menuValueEn) {
		this.menuValueEn = menuValueEn;
	}

	public String getMenuValueCn() {
		return this.menuValueCn;
	}

	public void setMenuValueCn(String menuValueCn) {
		this.menuValueCn = menuValueCn;
	}

	public String getMenuItemName() {
		return this.menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public String getMenuItemValueTw() {
		return this.menuItemValueTw;
	}

	public void setMenuItemValueTw(String menuItemValueTw) {
		this.menuItemValueTw = menuItemValueTw;
	}

	public String getMenuItemValueEn() {
		return this.menuItemValueEn;
	}

	public void setMenuItemValueEn(String menuItemValueEn) {
		this.menuItemValueEn = menuItemValueEn;
	}

	public String getMenuItemValueCn() {
		return this.menuItemValueCn;
	}

	public void setMenuItemValueCn(String menuItemValueCn) {
		this.menuItemValueCn = menuItemValueCn;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUriList() {
		return this.uriList;
	}

	public void setUriList(String uriList) {
		this.uriList = uriList;
	}

	public Boolean getRoles1s() {
		return this.roles1s;
	}

	public void setRoles1s(Boolean roles1s) {
		this.roles1s = roles1s;
	}

	public Boolean getRoles1c() {
		return this.roles1c;
	}

	public void setRoles1c(Boolean roles1c) {
		this.roles1c = roles1c;
	}

	public Boolean getRoles2s() {
		return this.roles2s;
	}

	public void setRoles2s(Boolean roles2s) {
		this.roles2s = roles2s;
	}

	public Boolean getRoles2c() {
		return this.roles2c;
	}

	public void setRoles2c(Boolean roles2c) {
		this.roles2c = roles2c;
	}

	public Boolean getRoles3s() {
		return this.roles3s;
	}

	public void setRoles3s(Boolean roles3s) {
		this.roles3s = roles3s;
	}

	public Boolean getRoles3c() {
		return this.roles3c;
	}

	public void setRoles3c(Boolean roles3c) {
		this.roles3c = roles3c;
	}

	public Boolean getRoles4s() {
		return this.roles4s;
	}

	public void setRoles4s(Boolean roles4s) {
		this.roles4s = roles4s;
	}

	public Boolean getRoles4c() {
		return this.roles4c;
	}

	public void setRoles4c(Boolean roles4c) {
		this.roles4c = roles4c;
	}

	public Boolean getRoles5s() {
		return this.roles5s;
	}

	public void setRoles5s(Boolean roles5s) {
		this.roles5s = roles5s;
	}

	public Boolean getRoles5c() {
		return this.roles5c;
	}

	public void setRoles5c(Boolean roles5c) {
		this.roles5c = roles5c;
	}

	public Boolean getRoles6m() {
		return this.roles6m;
	}

	public void setRoles6m(Boolean roles6m) {
		this.roles6m = roles6m;
	}

}