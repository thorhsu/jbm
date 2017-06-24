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
 * EddNotSend entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.EddNotSend
 * @author MyEclipse Persistence Tools
 */

public class EddNotSendDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(EddNotSendDAO.class);
	// property constants
	

	public void save(EddNotSend transientInstance) {
		log.debug("saving EddNotSend instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch(NonUniqueObjectException e){
			getSession().merge(transientInstance);
		}catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(EddNotSend persistentInstance) {
		log.debug("deleting EddNotSend instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EddNotSend findById(int id) {
		log.debug("getting EddNotSend instance with id: " + id);
		try {
			EddNotSend instance = (EddNotSend) getSession().get(
					"com.salmat.jbm.hibernate.EddNotSend", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EddNotSend instance) {
		log.debug("finding EddNotSend instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.EddNotSend")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<EddNotSend> findByProperty(String propertyName, Object value) {
		log.debug("finding EddNotSend instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from EddNotSend as model where model."
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
		log.debug("finding all EddNotSend instances");
		try {
			String queryString = "from EddNotSend";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public EddNotSend merge(EddNotSend detachedInstance) {
		log.debug("merging EddNotSend instance");
		try {
			EddNotSend result = (EddNotSend) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(EddNotSend instance) {
		log.debug("attaching dirty EddNotSend instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EddNotSend instance) {
		log.debug("attaching clean EddNotSend instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}