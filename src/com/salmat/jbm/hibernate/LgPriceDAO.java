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
 * LgPrice entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.LgPrice
 * @author MyEclipse Persistence Tools
 */

public class LgPriceDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(LgPriceDAO.class);
	// property constants

	public void save(LgPrice transientInstance) {
		log.debug("saving LgPrice instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(LgPrice persistentInstance) {
		log.debug("deleting LgPrice instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LgPrice findById(java.lang.Integer id) {
		log.debug("getting LgPrice instance with id: " + id);
		try {
			LgPrice instance = (LgPrice) getSession().get(
					"com.salmat.jbm.hibernate.LgPrice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(LgPrice instance) {
		log.debug("finding LgPrice instance by example");
		try {
			List results = getSession()
					.createCriteria(com.salmat.jbm.hibernate.LgPrice.class)
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
		log.debug("finding LgPrice instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from LgPrice as model where model."
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
		log.debug("finding all LgPrice instances");
		try {
			String queryString = "from LgPrice";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public LgPrice merge(LgPrice detachedInstance) {
		log.debug("merging LgPrice instance");
		try {
			LgPrice result = (LgPrice) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(LgPrice instance) {
		log.debug("attaching dirty LgPrice instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LgPrice instance) {
		log.debug("attaching clean LgPrice instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}