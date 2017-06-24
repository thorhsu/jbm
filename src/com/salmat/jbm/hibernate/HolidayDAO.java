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
 * Holiday entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Holiday
 * @author MyEclipse Persistence Tools
 */

public class HolidayDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(HolidayDAO.class);
	// property constants
	public static final String LOG_TYPE = "logType";
	public static final String SUBJECT = "subject";
	public static final String MESSAGE_BODY = "messageBody";
	public static final String IS_EXCEPTION = "isException";
	public static final String ERROR_LOG = "errorLog";

	public void save(Holiday transientInstance) {
		log.debug("saving Holiday instance");
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

	public void delete(Holiday persistentInstance) {
		log.debug("deleting Holiday instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Holiday findById(Date id) {
		log.debug("getting Holiday instance with id: " + id);
		try {
			Holiday instance = (Holiday) getSession().get(
					"com.salmat.jbm.hibernate.Holiday", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Holiday instance) {
		log.debug("finding Holiday instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Holiday")
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
		log.debug("finding Holiday instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Holiday as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLogType(Object logType) {
		return findByProperty(LOG_TYPE, logType);
	}

	public List findBySubject(Object subject) {
		return findByProperty(SUBJECT, subject);
	}

	public List findByMessageBody(Object messageBody) {
		return findByProperty(MESSAGE_BODY, messageBody);
	}

	public List findByIsException(Object isException) {
		return findByProperty(IS_EXCEPTION, isException);
	}

	public List findByErrorLog(Object errorLog) {
		return findByProperty(ERROR_LOG, errorLog);
	}

	public List findAll() {
		log.debug("finding all Holiday instances");
		try {
			String queryString = "from Holiday";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Holiday merge(Holiday detachedInstance) {
		log.debug("merging Holiday instance");
		try {
			Holiday result = (Holiday) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Holiday instance) {
		log.debug("attaching dirty Holiday instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Holiday instance) {
		log.debug("attaching clean Holiday instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}