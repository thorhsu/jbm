package com.salmat.jbm.hibernate;

import java.util.Date;
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
 * AcctCustomerContract entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.AcctCustomerContract
 * @author MyEclipse Persistence Tools
 */

public class AcctCustomerContractDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcctCustomerContractDAO.class);
	// property constants
	public static final String UNIT_PRICE = "unitPrice";
	public static final String REPORT_TITLE = "reportTitle";
	public static final String ORDERBY = "orderby";

	public void save(AcctCustomerContract transientInstance) {
		log.debug("saving AcctCustomerContract instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcctCustomerContract persistentInstance) {
		log.debug("deleting AcctCustomerContract instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctCustomerContract findById(java.lang.Integer id) {
		log.debug("getting AcctCustomerContract instance with id: " + id);
		try {
			AcctCustomerContract instance = (AcctCustomerContract) getSession()
					.get("com.salmat.jbm.hibernate.AcctCustomerContract", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctCustomerContract instance) {
		log.debug("finding AcctCustomerContract instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.salmat.jbm.hibernate.AcctCustomerContract")
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
		log.debug("finding AcctCustomerContract instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AcctCustomerContract as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUnitPrice(Object unitPrice) {
		return findByProperty(UNIT_PRICE, unitPrice);
	}

	public List findByReportTitle(Object reportTitle) {
		return findByProperty(REPORT_TITLE, reportTitle);
	}

	public List findByOrderby(Object orderby) {
		return findByProperty(ORDERBY, orderby);
	}

	public List findAll() {
		log.debug("finding all AcctCustomerContract instances");
		try {
			String queryString = "from AcctCustomerContract";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctCustomerContract merge(AcctCustomerContract detachedInstance) {
		log.debug("merging AcctCustomerContract instance");
		try {
			AcctCustomerContract result = (AcctCustomerContract) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctCustomerContract instance) {
		log.debug("attaching dirty AcctCustomerContract instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctCustomerContract instance) {
		log.debug("attaching clean AcctCustomerContract instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}