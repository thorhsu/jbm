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
 * FbRegisterNos entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.FbRegisterNos
 * @author MyEclipse Persistence Tools
 */

public class FbRegisterNosDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(FbRegisterNosDAO.class);
	// property constants

	public void save(FbRegisterNos transientInstance) {
		log.debug("saving FbRegisterNos instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FbRegisterNos persistentInstance) {
		log.debug("deleting FbRegisterNos instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FbRegisterNos findById(java.lang.String id) {
		log.debug("getting FbRegisterNos instance with id: " + id);
		try {
			FbRegisterNos instance = (FbRegisterNos) getSession().get(
					"com.salmat.jbm.hibernate.FbRegisterNos", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FbRegisterNos instance) {
		log.debug("finding FbRegisterNos instance by example");
		try {
			List results = getSession()
					.createCriteria(com.salmat.jbm.hibernate.FbRegisterNos.class)
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
		log.debug("finding FbRegisterNos instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from FbRegisterNos as model where model."
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
		log.debug("finding all FbRegisterNos instances");
		try {
			String queryString = "from FbRegisterNos";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FbRegisterNos merge(FbRegisterNos detachedInstance) {
		log.debug("merging FbRegisterNos instance");
		try {
			FbRegisterNos result = (FbRegisterNos) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FbRegisterNos instance) {
		log.debug("attaching dirty FbRegisterNos instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FbRegisterNos instance) {
		log.debug("attaching clean FbRegisterNos instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}