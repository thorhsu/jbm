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
 * @see com.salmat.jbm.hibernate.ZipcodeDetail
 * @author James_Tsai
 */

public class ZipcodeDetailDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(ZipcodeDetailDAO.class);
	// property constants
	public static final String ZIPCODE_INFO_NO = "zipcodeInfo";
	public static final String ZIPCODE = "zipcode";
	public static final String ZIPCODE_SUM = "zipcodeSum";
	public static final String ZIPCODE_TOTAL = "zipcodeTotal";

	public void save(ZipcodeDetail transientInstance) {
		log.debug("saving ZipcodeDetail instance");
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

	public void delete(ZipcodeDetail persistentInstance) {
		log.debug("deleting ZipcodeDetail instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ZipcodeDetail findById(java.lang.String id) {
		log.debug("getting ZipcodeDetail instance with id: " + id);
		try {
			ZipcodeDetail instance = (ZipcodeDetail) getSession().get(
					"com.salmat.jbm.hibernate.ZipcodeDetail", id);
			
			return instance;
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ZipcodeDetail instance) {
		log.debug("finding ZipcodeDetail instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.ZipcodeDetail")
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
		log.debug("finding ZipcodeDetail instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ZipcodeDetail as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByZipcodeInfo(ZipcodeInfo zipcodeInfoNo) {
		return findByProperty(ZIPCODE_INFO_NO, zipcodeInfoNo);
	}

	public List findAll() {
		log.debug("finding all ZipcodeDetail instances");
		try {
			String queryString = "from ZipcodeDetail";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ZipcodeDetail merge(ZipcodeDetail detachedInstance) {
		log.debug("merging ZipcodeDetail instance");
		try {
			ZipcodeDetail result = (ZipcodeDetail) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ZipcodeDetail instance) {
		log.debug("attaching dirty ZipcodeDetail instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ZipcodeDetail instance) {
		log.debug("attaching clean ZipcodeDetail instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}