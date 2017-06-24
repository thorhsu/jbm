package com.salmat.jbm.hibernate;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * MenuItemPermission entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.MenuItemPermission
 * @author MyEclipse Persistence Tools
 */

public class MenuItemPermissionDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(MenuItemPermissionDAO.class);
	// property constants
	public static final String MENU_NAME = "menuName";
	public static final String MENU_VALUE_TW = "menuValueTw";
	public static final String MENU_VALUE_EN = "menuValueEn";
	public static final String MENU_VALUE_CN = "menuValueCn";
	public static final String MENU_ITEM_NAME = "menuItemName";
	public static final String MENU_ITEM_VALUE_TW = "menuItemValueTw";
	public static final String MENU_ITEM_VALUE_EN = "menuItemValueEn";
	public static final String MENU_ITEM_VALUE_CN = "menuItemValueCn";
	public static final String URI = "uri";
	public static final String URI_LIST = "uriList";
	public static final String ROLES1S = "roles1s";
	public static final String ROLES1C = "roles1c";
	public static final String ROLES2S = "roles2s";
	public static final String ROLES2C = "roles2c";
	public static final String ROLES3S = "roles3s";
	public static final String ROLES3C = "roles3c";
	public static final String ROLES4S = "roles4s";
	public static final String ROLES4C = "roles4c";
	public static final String ROLES5S = "roles5s";
	public static final String ROLES5C = "roles5c";
	public static final String ROLES6M = "roles6m";

	public void save(MenuItemPermission transientInstance) {
		log.debug("saving MenuItemPermission instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(MenuItemPermission persistentInstance) {
		log.debug("deleting MenuItemPermission instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public MenuItemPermission findById(java.lang.Integer id) {
		log.debug("getting MenuItemPermission instance with id: " + id);
		try {
			MenuItemPermission instance = (MenuItemPermission) getSession()
					.get("com.salmat.jbm.hibernate.MenuItemPermission", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(MenuItemPermission instance) {
		log.debug("finding MenuItemPermission instance by example");
		try {
			List results = getSession().createCriteria(
					"com.salmat.jbm.hibernate.MenuItemPermission").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding MenuItemPermission instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from MenuItemPermission as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByMenuName(Object menuName) {
		return findByProperty(MENU_NAME, menuName);
	}

	public List findByMenuValueTw(Object menuValueTw) {
		return findByProperty(MENU_VALUE_TW, menuValueTw);
	}

	public List findByMenuValueEn(Object menuValueEn) {
		return findByProperty(MENU_VALUE_EN, menuValueEn);
	}

	public List findByMenuValueCn(Object menuValueCn) {
		return findByProperty(MENU_VALUE_CN, menuValueCn);
	}

	public List findByMenuItemName(Object menuItemName) {
		return findByProperty(MENU_ITEM_NAME, menuItemName);
	}

	public List findByMenuItemValueTw(Object menuItemValueTw) {
		return findByProperty(MENU_ITEM_VALUE_TW, menuItemValueTw);
	}

	public List findByMenuItemValueEn(Object menuItemValueEn) {
		return findByProperty(MENU_ITEM_VALUE_EN, menuItemValueEn);
	}

	public List findByMenuItemValueCn(Object menuItemValueCn) {
		return findByProperty(MENU_ITEM_VALUE_CN, menuItemValueCn);
	}

	public List findByUri(Object uri) {
		return findByProperty(URI, uri);
	}

	public List findByUriList(Object uriList) {
		return findByProperty(URI_LIST, uriList);
	}

	public List findByRoles1s(Object roles1s) {
		return findByProperty(ROLES1S, roles1s);
	}

	public List findByRoles1c(Object roles1c) {
		return findByProperty(ROLES1C, roles1c);
	}

	public List findByRoles2s(Object roles2s) {
		return findByProperty(ROLES2S, roles2s);
	}

	public List findByRoles2c(Object roles2c) {
		return findByProperty(ROLES2C, roles2c);
	}

	public List findByRoles3s(Object roles3s) {
		return findByProperty(ROLES3S, roles3s);
	}

	public List findByRoles3c(Object roles3c) {
		return findByProperty(ROLES3C, roles3c);
	}

	public List findByRoles4s(Object roles4s) {
		return findByProperty(ROLES4S, roles4s);
	}

	public List findByRoles4c(Object roles4c) {
		return findByProperty(ROLES4C, roles4c);
	}

	public List findByRoles5s(Object roles5s) {
		return findByProperty(ROLES5S, roles5s);
	}

	public List findByRoles5c(Object roles5c) {
		return findByProperty(ROLES5C, roles5c);
	}

	public List findByRoles6m(Object roles6m) {
		return findByProperty(ROLES6M, roles6m);
	}

	public List findAll() {
		log.debug("finding all MenuItemPermission instances");
		try {
			String queryString = "from MenuItemPermission";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public MenuItemPermission merge(MenuItemPermission detachedInstance) {
		log.debug("merging MenuItemPermission instance");
		try {
			MenuItemPermission result = (MenuItemPermission) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(MenuItemPermission instance) {
		log.debug("attaching dirty MenuItemPermission instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(MenuItemPermission instance) {
		log.debug("attaching clean MenuItemPermission instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}