package com.salmat.jbm.hibernate;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * AcctSum1 entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.AcctSum1
 * @author MyEclipse Persistence Tools
 */

public class AcctSum1DAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcctSum1DAO.class);
	// property constants
	public static final String IDF_JOB_CODE_NO = "idfJobCodeNo";
	public static final String IDF_JOB_BAG_NO = "idfJobBagNo";
	public static final String COST_CENTER = "costCenter";
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_NAME_TW = "itemNameTw";
	public static final String TITLE = "title";
	public static final String SUB_TITLE = "subTitle";
	public static final String EP1_ACCOUNT_CODE = "ep1AccountCode";
	public static final String CALCULAT_TYPE = "calculatType";
	public static final String UNIT_PRICE = "unitPrice";
	public static final String SUM_QTY = "sumQty";
	public static final String IS_MINIMAL_CHARGE = "isMinimalCharge";
	public static final String PROG_NM = "progNm";

	public void save(AcctSum1 transientInstance) {
		log.debug("saving AcctSum1 instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcctSum1 persistentInstance) {
		log.debug("deleting AcctSum1 instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctSum1 findById(java.lang.Integer id) {
		log.debug("getting AcctSum1 instance with id: " + id);
		try {
			AcctSum1 instance = (AcctSum1) getSession().get(
					"com.salmat.jbm.hibernate.AcctSum1", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctSum1 instance) {
		log.debug("finding AcctSum1 instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.AcctSum1")
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
		log.debug("finding AcctSum1 instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AcctSum1 as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByIdfJobCodeNo(Object idfJobCodeNo) {
		return findByProperty(IDF_JOB_CODE_NO, idfJobCodeNo);
	}

	public List findByIdfJobBagNo(Object idfJobBagNo) {
		return findByProperty(IDF_JOB_BAG_NO, idfJobBagNo);
	}

	public List findByCostCenter(Object costCenter) {
		return findByProperty(COST_CENTER, costCenter);
	}

	public List findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List findByItemNameTw(Object itemNameTw) {
		return findByProperty(ITEM_NAME_TW, itemNameTw);
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findBySubTitle(Object subTitle) {
		return findByProperty(SUB_TITLE, subTitle);
	}

	public List findByEp1AccountCode(Object ep1AccountCode) {
		return findByProperty(EP1_ACCOUNT_CODE, ep1AccountCode);
	}

	public List findByCalculatType(Object calculatType) {
		return findByProperty(CALCULAT_TYPE, calculatType);
	}

	public List findByUnitPrice(Object unitPrice) {
		return findByProperty(UNIT_PRICE, unitPrice);
	}

	public List findBySumQty(Object sumQty) {
		return findByProperty(SUM_QTY, sumQty);
	}

	public List findByIsMinimalCharge(Object isMinimalCharge) {
		return findByProperty(IS_MINIMAL_CHARGE, isMinimalCharge);
	}

	public List findByProgNm(Object progNm) {
		return findByProperty(PROG_NM, progNm);
	}

	public List findAll() {
		log.debug("finding all AcctSum1 instances");
		try {
			String queryString = "from AcctSum1";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctSum1 merge(AcctSum1 detachedInstance) {
		log.debug("merging AcctSum1 instance");
		try {
			AcctSum1 result = (AcctSum1) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctSum1 instance) {
		log.debug("attaching dirty AcctSum1 instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctSum1 instance) {
		log.debug("attaching clean AcctSum1 instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}