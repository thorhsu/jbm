package com.salmat.jbm.hibernate;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * AcctJobCodeChargeItem entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.AcctJobCodeChargeItem
 * @author MyEclipse Persistence Tools
 */

public class AcctJobCodeChargeItemDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcctJobCodeChargeItemDAO.class);
	// property constants
	public static final String ADJUSTMENT_PERCENT = "adjustmentPercent";
	public static final String PRINT_TIMES = "printTimes";

	public void save(AcctJobCodeChargeItem transientInstance) {
		log.debug("saving AcctJobCodeChargeItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcctJobCodeChargeItem persistentInstance) {
		log.debug("deleting AcctJobCodeChargeItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctJobCodeChargeItem findById(java.lang.Integer id) {
		log.debug("getting AcctJobCodeChargeItem instance with id: " + id);
		try {
			AcctJobCodeChargeItem instance = (AcctJobCodeChargeItem) getSession()
					.get("com.salmat.jbm.hibernate.AcctJobCodeChargeItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctJobCodeChargeItem instance) {
		log.debug("finding AcctJobCodeChargeItem instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.salmat.jbm.hibernate.AcctJobCodeChargeItem")
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
		log.debug("finding AcctJobCodeChargeItem instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AcctJobCodeChargeItem as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAdjustmentPercent(Object adjustmentPercent) {
		return findByProperty(ADJUSTMENT_PERCENT, adjustmentPercent);
	}

	public List findByPrintTimes(Object printTimes) {
		return findByProperty(PRINT_TIMES, printTimes);
	}

	public List findAll() {
		log.debug("finding all AcctJobCodeChargeItem instances");
		try {
			String queryString = "from AcctJobCodeChargeItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctJobCodeChargeItem merge(AcctJobCodeChargeItem detachedInstance) {
		log.debug("merging AcctJobCodeChargeItem instance");
		try {
			AcctJobCodeChargeItem result = (AcctJobCodeChargeItem) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctJobCodeChargeItem instance) {
		log.debug("attaching dirty AcctJobCodeChargeItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctJobCodeChargeItem instance) {
		log.debug("attaching clean AcctJobCodeChargeItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}