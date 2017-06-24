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
 * SysLog entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.SysLog
 * @author MyEclipse Persistence Tools
 */

public class SysLogDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(SysLogDAO.class);
	// property constants
	public static final String LOG_TYPE = "logType";
	public static final String SUBJECT = "subject";
	public static final String MESSAGE_BODY = "messageBody";
	public static final String IS_EXCEPTION = "isException";
	public static final String ERROR_LOG = "errorLog";

	public void save(SysLog transientInstance) {
		log.debug("saving SysLog instance");
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

	public void delete(SysLog persistentInstance) {
		log.debug("deleting SysLog instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public SysLog findById(java.lang.Integer id) {
		log.debug("getting SysLog instance with id: " + id);
		try {
			SysLog instance = (SysLog) getSession().get(
					"com.salmat.jbm.hibernate.SysLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(SysLog instance) {
		log.debug("finding SysLog instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.SysLog")
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
		log.debug("finding SysLog instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from SysLog as model where model."
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
		log.debug("finding all SysLog instances");
		try {
			String queryString = "from SysLog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public SysLog merge(SysLog detachedInstance) {
		log.debug("merging SysLog instance");
		try {
			SysLog result = (SysLog) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(SysLog instance) {
		log.debug("attaching dirty SysLog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(SysLog instance) {
		log.debug("attaching clean SysLog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}