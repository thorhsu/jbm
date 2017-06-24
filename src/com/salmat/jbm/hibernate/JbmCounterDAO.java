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
 * AcctDno entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.JbmCounter
 * @author James_Tsai
 */

public class JbmCounterDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(JbmCounterDAO.class);
	// property constants
	public static final String COUNTER_NAME = "counterName";

	public void save(JbmCounter transientInstance) {
		log.debug("saving JbmCounter instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch(NonUniqueObjectException e){
			getSession().merge(transientInstance);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void update(String counterName, Integer curCounterNo){
		// getSession().update(arg0, arg1)
	}

	public void delete(JbmCounter persistentInstance) {
		log.debug("deleting JbmCounter instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public JbmCounter findByCounterName(java.lang.String counterName) {
		log.debug("getting ZipcodeInfo instance with counterName: " + counterName);
		try {
			JbmCounter instance = (JbmCounter) getSession().get(
					"com.salmat.jbm.hibernate.JbmCounter", counterName);
			
			return instance;
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(JbmCounter instance) {
		log.debug("finding JbmCounter instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.JbmCounter")
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
		log.debug("finding JbmCounter instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from JbmCounter as model where model."
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
		log.debug("finding all JbmCounter instances");
		try {
			String queryString = "from JbmCounter";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public JbmCounter merge(JbmCounter detachedInstance) {
		log.debug("merging JbmCounter instance");
		try {
			JbmCounter result = (JbmCounter) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(JbmCounter instance) {
		log.debug("attaching dirty JbmCounter instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(JbmCounter instance) {
		log.debug("attaching clean JbmCounter instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}