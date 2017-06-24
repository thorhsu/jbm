package com.salmat.jbm.hibernate;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * HsCardOverWeight entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.HsCardOverWeight
 * @author MyEclipse Persistence Tools
 */

public class HsCardOverWeightDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(HsCardOverWeightDAO.class);
	// property constants

	public void save(HsCardOverWeight transientInstance) {
		log.debug("saving HsCardOverWeight instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(HsCardOverWeight persistentInstance) {
		log.debug("deleting HsCardOverWeight instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public HsCardOverWeight findById(java.lang.String id) {
		log.debug("getting HsCardOverWeight instance with id: " + id);
		try {
			HsCardOverWeight instance = (HsCardOverWeight) getSession().get(
					"com.salmat.jbm.hibernate.HsCardOverWeight", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(HsCardOverWeight instance) {
		log.debug("finding HsCardOverWeight instance by example");
		try {
			List results = getSession()
					.createCriteria(com.salmat.jbm.hibernate.HsCardOverWeight.class)
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
		log.debug("finding HsCardOverWeight instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from HsCardOverWeight as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	public List findAll() {
		log.debug("finding all HsCardOverWeight instances");
		try {
			String queryString = "from HsCardOverWeight";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public HsCardOverWeight merge(HsCardOverWeight detachedInstance) {
		log.debug("merging HsCardOverWeight instance");
		try {
			HsCardOverWeight result = (HsCardOverWeight) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(HsCardOverWeight instance) {
		log.debug("attaching dirty HsCardOverWeight instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(HsCardOverWeight instance) {
		log.debug("attaching clean HsCardOverWeight instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}