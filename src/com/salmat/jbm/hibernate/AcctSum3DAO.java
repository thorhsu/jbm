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
 * AcctSum3 entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.AcctSum3
 * @author MyEclipse Persistence Tools
 */

public class AcctSum3DAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcctSum3DAO.class);
	// property constants
	public static final String TITLE = "title";
	public static final String SUBTITLE = "subtitle";
	public static final String SUM_QTY = "sumQty";
	public static final String UNIT_PRICE = "unitPrice";
	public static final String TOTAL = "total";
	public static final String COST_CENTER = "costCenter";
	public static final String PURPOSE = "purpose";
	public static final String EP1ACCCD = "ep1acccd";

	public void save(AcctSum3 transientInstance) {
		log.debug("saving AcctSum3 instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcctSum3 persistentInstance) {
		log.debug("deleting AcctSum3 instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctSum3 findById(java.lang.Integer id) {
		log.debug("getting AcctSum3 instance with id: " + id);
		try {
			AcctSum3 instance = (AcctSum3) getSession().get(
					"com.salmat.jbm.hibernate.AcctSum3", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctSum3 instance) {
		log.debug("finding AcctSum3 instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.AcctSum3")
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
		log.debug("finding AcctSum3 instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AcctSum3 as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findBySubtitle(Object subtitle) {
		return findByProperty(SUBTITLE, subtitle);
	}

	public List findBySumQty(Object sumQty) {
		return findByProperty(SUM_QTY, sumQty);
	}

	public List findByUnitPrice(Object unitPrice) {
		return findByProperty(UNIT_PRICE, unitPrice);
	}

	public List findByTotal(Object total) {
		return findByProperty(TOTAL, total);
	}

	public List findByCostCenter(Object costCenter) {
		return findByProperty(COST_CENTER, costCenter);
	}

	public List findByPurpose(Object purpose) {
		return findByProperty(PURPOSE, purpose);
	}

	public List findByEp1acccd(Object ep1acccd) {
		return findByProperty(EP1ACCCD, ep1acccd);
	}

	public List findAll() {
		log.debug("finding all AcctSum3 instances");
		try {
			String queryString = "from AcctSum3";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctSum3 merge(AcctSum3 detachedInstance) {
		log.debug("merging AcctSum3 instance");
		try {
			AcctSum3 result = (AcctSum3) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctSum3 instance) {
		log.debug("attaching dirty AcctSum3 instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctSum3 instance) {
		log.debug("attaching clean AcctSum3 instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}