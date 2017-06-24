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
 * HsCardRpt entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.HsCardRpt
 * @author MyEclipse Persistence Tools
 */

public class HsCardRptDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(HsCardRptDAO.class);
	// property constants

	public void save(HsCardRpt transientInstance) {
		log.debug("saving HsCardRpt instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(HsCardRpt persistentInstance) {
		log.debug("deleting HsCardRpt instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public HsCardRpt findById(java.lang.String id) {
		log.debug("getting HsCardRpt instance with id: " + id);
		try {
			HsCardRpt instance = (HsCardRpt) getSession().get(
					"com.salmat.jbm.hibernate.HsCardRpt", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(HsCardRpt instance) {
		log.debug("finding HsCardRpt instance by example");
		try {
			List results = getSession()
					.createCriteria(com.salmat.jbm.hibernate.HsCardRpt.class)
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
		log.debug("finding HsCardRpt instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from HsCardRpt as model where model."
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
		log.debug("finding all HsCardRpt instances");
		try {
			String queryString = "from HsCardRpt";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public HsCardRpt merge(HsCardRpt detachedInstance) {
		log.debug("merging HsCardRpt instance");
		try {
			HsCardRpt result = (HsCardRpt) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(HsCardRpt instance) {
		log.debug("attaching dirty HsCardRpt instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(HsCardRpt instance) {
		log.debug("attaching clean HsCardRpt instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}