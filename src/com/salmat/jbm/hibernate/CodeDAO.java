package com.salmat.jbm.hibernate;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for Code
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Code
 * @author MyEclipse Persistence Tools
 */

public class CodeDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(CodeDAO.class);
	// property constants
	public static final String CODE_TYPE_NAME = "codeTypeName";
	public static final String CODE_TYPE_NAME_TW = "codeTypeNameTw";
	public static final String CODE_KEY = "codeKey";
	public static final String CODE_VALUE_TW = "codeValueTw";
	public static final String CODE_VALUE_EN = "codeValueEn";
	public static final String CODE_VALUE_CN = "codeValueCn";
	public static final String ORDERBY_ID = "orderbyId";
	public static final String IS_HIDDEN = "isHidden";
	public static final String F1 = "f1";
	public static final String F2 = "f2";
	public static final String F3 = "f3";

	public void save(Code transientInstance) {
		log.debug("saving Code instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Code persistentInstance) {
		log.debug("deleting Code instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Code findById(java.lang.Integer id) {
		log.debug("getting Code instance with id: " + id);
		try {
			Code instance = (Code) getSession().get(
					"com.salmat.jbm.hibernate.Code", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Code instance) {
		log.debug("finding Code instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Code")
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
		log.debug("finding Code instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Code as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCodeTypeName(Object codeTypeName) {
		return findByProperty(CODE_TYPE_NAME, codeTypeName);
	}

	public List findByCodeTypeNameTw(Object codeTypeNameTw) {
		return findByProperty(CODE_TYPE_NAME_TW, codeTypeNameTw);
	}

	public List findByCodeKey(Object codeKey) {
		return findByProperty(CODE_KEY, codeKey);
	}

	public List findByCodeValueTw(Object codeValueTw) {
		return findByProperty(CODE_VALUE_TW, codeValueTw);
	}

	public List findByCodeValueEn(Object codeValueEn) {
		return findByProperty(CODE_VALUE_EN, codeValueEn);
	}

	public List findByCodeValueCn(Object codeValueCn) {
		return findByProperty(CODE_VALUE_CN, codeValueCn);
	}

	public List findByOrderbyId(Object orderbyId) {
		return findByProperty(ORDERBY_ID, orderbyId);
	}

	public List findByIsHidden(Object isHidden) {
		return findByProperty(IS_HIDDEN, isHidden);
	}

	public List findByF1(Object f1) {
		return findByProperty(F1, f1);
	}

	public List findByF2(Object f2) {
		return findByProperty(F2, f2);
	}

	public List findByF3(Object f3) {
		return findByProperty(F3, f3);
	}

	public List findAll() {
		log.debug("finding all Code instances");
		try {
			String queryString = "from Code";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Code merge(Code detachedInstance) {
		log.debug("merging Code instance");
		try {
			Code result = (Code) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Code instance) {
		log.debug("attaching dirty Code instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		}catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Code instance) {
		log.debug("attaching clean Code instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}