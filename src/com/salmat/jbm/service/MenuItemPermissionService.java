package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.*;
import com.painter.util.CookieUtils;
import com.painter.util.EnCryptUtils;
import com.painter.util.Global;
import com.painter.util.MailUtil;
import com.painter.util.SessionUtil;
import com.painter.util.Util;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.String;
import java.net.URLEncoder;

public class MenuItemPermissionService extends MenuItemPermissionDAO {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger
			.getLogger(MenuItemPermissionService.class);

	/* Constants */
	private static final MenuItemPermissionService instance = new MenuItemPermissionService();

	private MenuItemPermissionService() {

	}

	public static MenuItemPermissionService getInstance() {
		return instance;
	}

	/**
	 * 根據 URL 找出 MenuItemPermission
	 * 
	 * @param uri
	 * @return MenuItemPermission List
	 */
	public List findByURI(String uri) {

		String lang = SessionUtil.getLanguage();
		try {
			String queryString = "select  {m.*} "
					+ " from menu_item_permission m"
					+ " where m.URI_LIST like ?";

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("m", MenuItemPermission.class);
			sqlQuery.setParameter(0, "%" + uri + "%");

			List retList = sqlQuery.list();
			if (null != retList && retList.size() > 0)
				return retList;
			else
				return null;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}

	}

	/**
	 * 找出對應語系的 Menu Name
	 * 
	 * @param menuName
	 *            , retString
	 * @return Menu Name
	 */
	public String findMenuValue(String menuName, String retString) {

		String lang = SessionUtil.getLanguage();
		try {
			String queryString = "select  {m.*} "
					+ " from menu_item_permission m"
					+ " where m.MENU_NAME = ? ";

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("m", MenuItemPermission.class);
			sqlQuery.setParameter(0, menuName);

			List retList = sqlQuery.list();
			MenuItemPermission _menu = (MenuItemPermission) retList.get(0);

			if (lang.equalsIgnoreCase("TW"))
				return _menu.getMenuValueTw();
			else if (lang.equalsIgnoreCase("EN"))
				return _menu.getMenuValueEn();
			else if (lang.equalsIgnoreCase("CN"))
				return _menu.getMenuValueCn();
			else
				return retString;
		} catch (RuntimeException re) {
			log.error("", re);
			return "";
		}

	}

	/**
	 * 找出對應語系的 Menu Name
	 * 
	 * @param menuName
	 *            , retString
	 * @return Menu Name
	 */
	public String findMenuItemValue(HttpServletRequest request,
			String menuName, String retString, Map<String, String> extraParam) {
		String menuItem = "";
		String lang = SessionUtil.getLanguage();
		Employee employee = SessionUtil.getAccount(request.getSession());

		try {
			String queryString = "select  {m.*} "
					+ " from menu_item_permission m"
					+ " where m.MENU_ITEM_NAME = ? ";

			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(queryString);
			sqlQuery.addEntity("m", MenuItemPermission.class);
			sqlQuery.setParameter(0, menuName);

			List retList = sqlQuery.list();
			if (retList != null && retList.size() > 0) {
				MenuItemPermission _menu = (MenuItemPermission) retList.get(0);

				Boolean _permission = false;
				if (null != employee.getRole1c() && null != _menu.getRoles1c()
						&& employee.getRole1c() && _menu.getRoles1c())
					_permission = true;
				if (null != employee.getRole1s() && null != _menu.getRoles1s()
						&& employee.getRole1s() && _menu.getRoles1s())
					_permission = true;
				if (null != employee.getRole2c() && null != _menu.getRoles2c()
						&& employee.getRole2c() && _menu.getRoles2c())
					_permission = true;
				if (null != employee.getRole2s() && null != _menu.getRoles2s()
						&& employee.getRole2s() && _menu.getRoles2s())
					_permission = true;
				if (null != employee.getRole3c() && null != _menu.getRoles3c()
						&& employee.getRole3c() && _menu.getRoles3c())
					_permission = true;
				if (null != employee.getRole3s() && null != _menu.getRoles3s()
						&& employee.getRole3s() && _menu.getRoles3s())
					_permission = true;
				if (null != employee.getRole4c() && null != _menu.getRoles4c()
						&& employee.getRole4c() && _menu.getRoles4c())
					_permission = true;
				if (null != employee.getRole4s() && null != _menu.getRoles4s()
						&& employee.getRole4s() && _menu.getRoles4s())
					_permission = true;
				if (null != employee.getRole5c() && null != _menu.getRoles5c()
						&& employee.getRole5c() && _menu.getRoles5c())
					_permission = true;
				if (null != employee.getRole5s() && null != _menu.getRoles5s()
						&& employee.getRole5s() && _menu.getRoles5s())
					_permission = true;
				if (null != employee.getRole6m() && null != _menu.getRoles6m()
						&& employee.getRole6m() && _menu.getRoles6m())
					_permission = true;

				if (lang.equalsIgnoreCase("TW"))
					menuItem = _menu.getMenuItemValueTw(); // MenuItem Name
				else if (lang.equalsIgnoreCase("EN"))
					menuItem = _menu.getMenuItemValueEn();
				else if (lang.equalsIgnoreCase("CN"))
					menuItem = _menu.getMenuItemValueCn();
				else
					return retString;

				if (_permission && extraParam == null)
					return "<a href=\"" + _menu.getUri() + "\">" + menuItem
							+ "</a>";
				else if (_permission && extraParam != null) {
					String extraParams = "";
					Set<String> mapKeys = extraParam.keySet();
					for (String key : mapKeys) {
						extraParams += "&" + key + "=" + extraParam.get(key);
					}
					return "<a href=\"" + _menu.getUri() + extraParams + "\">"
							+ menuItem + "</a>";

				} else
					return "<div  class=\"dropdown-header\">" + menuItem
							+ "</div>";
			}
			return null;
		} catch (RuntimeException re) {
			log.error("", re);
			return "";
		}

	}

	public String findMenuItemValue(HttpServletRequest request,
			String menuName, String retString) {
		return findMenuItemValue(request, menuName, retString, null);
	}

}
