package com.salmat.jbm.hibernate;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * AcctChargeItem entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.AcctChargeItem
 * @author MyEclipse Persistence Tools
 */

public class AcctChargeItemDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcctChargeItemDAO.class);
	// property constants
	public static final String ITEM_TYPE = "itemType";
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_NAME_TW = "itemNameTw";
	public static final String EP1_ACCOUNT_CODE = "ep1AccountCode";
	public static final String CALCULAT_TYPE = "calculatType";
	public static final String ACCOUNTS = "accounts";
	public static final String PAGES = "pages";
	public static final String SHEETS = "sheets";
	public static final String FEEDER2 = "feeder2";
	public static final String FEEDER3 = "feeder3";
	public static final String FEEDER4 = "feeder4";
	public static final String FEEDER5 = "feeder5";
	public static final String TRAY1 = "tray1";
	public static final String TRAY2 = "tray2";
	public static final String TRAY3 = "tray3";
	public static final String TRAY4 = "tray4";
	public static final String FIXED_VALUE = "fixedValue";
	public static final String REPORT_TITLE = "reportTitle";
	public static final String EXPRESSION = "expression";
	public static final String IS_EXCLUDE_QTY = "isExcludeQty";
	public static final String PAGES2 = "pages2";
	public static final String PAGES3 = "pages3";
	public static final String P1ACCOUNTS = "p1accounts";
	public static final String P2ACCOUNTS = "p2accounts";
	public static final String P3ACCOUNTS = "p3accounts";
	public static final String P4ACCOUNTS = "p4accounts";
	public static final String P5ACCOUNTS = "p5accounts";
	public static final String P6ACCOUNTS = "p6accounts";
	public static final String PXACCOUNTS = "pxaccounts";

	public void save(AcctChargeItem transientInstance) {
		log.debug("saving AcctChargeItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcctChargeItem persistentInstance) {
		log.debug("deleting AcctChargeItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctChargeItem findById(java.lang.Integer id) {
		log.debug("getting AcctChargeItem instance with id: " + id);
		try {
			AcctChargeItem instance = (AcctChargeItem) getSession().get(
					"com.salmat.jbm.hibernate.AcctChargeItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctChargeItem instance) {
		log.debug("finding AcctChargeItem instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.AcctChargeItem")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding AcctChargeItem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AcctChargeItem as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItemType(Object itemType) {
		return findByProperty(ITEM_TYPE, itemType);
	}

	public List findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List findByItemNameTw(Object itemNameTw) {
		return findByProperty(ITEM_NAME_TW, itemNameTw);
	}

	public List findByEp1AccountCode(Object ep1AccountCode) {
		return findByProperty(EP1_ACCOUNT_CODE, ep1AccountCode);
	}

	public List findByCalculatType(Object calculatType) {
		return findByProperty(CALCULAT_TYPE, calculatType);
	}

	public List findByAccounts(Object accounts) {
		return findByProperty(ACCOUNTS, accounts);
	}

	public List findByPages(Object pages) {
		return findByProperty(PAGES, pages);
	}

	public List findBySheets(Object sheets) {
		return findByProperty(SHEETS, sheets);
	}

	public List findByFeeder2(Object feeder2) {
		return findByProperty(FEEDER2, feeder2);
	}

	public List findByFeeder3(Object feeder3) {
		return findByProperty(FEEDER3, feeder3);
	}

	public List findByFeeder4(Object feeder4) {
		return findByProperty(FEEDER4, feeder4);
	}

	public List findByFeeder5(Object feeder5) {
		return findByProperty(FEEDER5, feeder5);
	}

	public List findByTray1(Object tray1) {
		return findByProperty(TRAY1, tray1);
	}

	public List findByTray2(Object tray2) {
		return findByProperty(TRAY2, tray2);
	}

	public List findByTray3(Object tray3) {
		return findByProperty(TRAY3, tray3);
	}

	public List findByTray4(Object tray4) {
		return findByProperty(TRAY4, tray4);
	}

	public List findByFixedValue(Object fixedValue) {
		return findByProperty(FIXED_VALUE, fixedValue);
	}

	public List findByReportTitle(Object reportTitle) {
		return findByProperty(REPORT_TITLE, reportTitle);
	}

	public List findByExpression(Object expression) {
		return findByProperty(EXPRESSION, expression);
	}

	public List findByIsExcludeQty(Object isExcludeQty) {
		return findByProperty(IS_EXCLUDE_QTY, isExcludeQty);
	}

	public List findByPages2(Object pages2) {
		return findByProperty(PAGES2, pages2);
	}

	public List findByPages3(Object pages3) {
		return findByProperty(PAGES3, pages3);
	}

	public List findByP1accounts(Object p1accounts) {
		return findByProperty(P1ACCOUNTS, p1accounts);
	}

	public List findByP2accounts(Object p2accounts) {
		return findByProperty(P2ACCOUNTS, p2accounts);
	}

	public List findByP3accounts(Object p3accounts) {
		return findByProperty(P3ACCOUNTS, p3accounts);
	}

	public List findByP4accounts(Object p4accounts) {
		return findByProperty(P4ACCOUNTS, p4accounts);
	}

	public List findByP5accounts(Object p5accounts) {
		return findByProperty(P5ACCOUNTS, p5accounts);
	}

	public List findByP6accounts(Object p6accounts) {
		return findByProperty(P6ACCOUNTS, p6accounts);
	}

	public List findByPxaccounts(Object pxaccounts) {
		return findByProperty(PXACCOUNTS, pxaccounts);
	}

	public List findAll() {
		log.debug("finding all AcctChargeItem instances");
		try {
			String queryString = "from AcctChargeItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctChargeItem merge(AcctChargeItem detachedInstance) {
		log.debug("merging AcctChargeItem instance");
		try {
			AcctChargeItem result = (AcctChargeItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctChargeItem instance) {
		log.debug("attaching dirty AcctChargeItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctChargeItem instance) {
		log.debug("attaching clean AcctChargeItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}