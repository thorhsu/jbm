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
 * Psinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Psinfo
 * @author MyEclipse Persistence Tools
 */

public class PsinfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(PsinfoDAO.class);
	// property constants
	public static final String SIZE = "size";
	public static final String PS_TYPE = "psType";
	public static final String REMARK = "remark";
	public static final String PS_PRICE = "psPrice";
	public static final String PRINT_CODE = "printCode";
	public static final String ZIP_FG = "zipFg";
	public static final String PRODMEMO = "prodmemo";
	public static final String NEXT_EFFECTIVE_DATE = "nextEffectiveDate";
	public static final String NEXT_PRODMEMO = "nextProdmemo";
	public static final String NEXT_REMARK = "nextRemark";
	public static final String NEXT_ZIP_FG = "nextZipFg";

	public void save(Psinfo transientInstance) {
		log.debug("saving Psinfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Psinfo persistentInstance) {
		log.debug("deleting Psinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Psinfo findById(java.lang.String id) {
		log.debug("getting Psinfo instance with id: " + id);
		try {
			Psinfo instance = (Psinfo) getSession().get(
					"com.salmat.jbm.hibernate.Psinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Psinfo instance) {
		log.debug("finding Psinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Psinfo")
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
		log.debug("finding Psinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Psinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySize(Object size) {
		return findByProperty(SIZE, size);
	}

	public List findByPsType(Object psType) {
		return findByProperty(PS_TYPE, psType);
	}

	public List findByRemark(Object remark) {
		return findByProperty(REMARK, remark);
	}

	public List findByPsPrice(Object psPrice) {
		return findByProperty(PS_PRICE, psPrice);
	}

	public List findByPrintCode(Object printCode) {
		return findByProperty(PRINT_CODE, printCode);
	}

	public List findByZipFg(Object zipFg) {
		return findByProperty(ZIP_FG, zipFg);
	}

	public List findByProdmemo(Object prodmemo) {
		return findByProperty(PRODMEMO, prodmemo);
	}

	public List findByNextEffectiveDate(Object nextEffectiveDate) {
		return findByProperty(NEXT_EFFECTIVE_DATE, nextEffectiveDate);
	}

	public List findByNextProdmemo(Object nextProdmemo) {
		return findByProperty(NEXT_PRODMEMO, nextProdmemo);
	}

	public List findByNextRemark(Object nextRemark) {
		return findByProperty(NEXT_REMARK, nextRemark);
	}

	public List findByNextZipFg(Object nextZipFg) {
		return findByProperty(NEXT_ZIP_FG, nextZipFg);
	}

	public List findAll() {
		log.debug("finding all Psinfo instances");
		try {
			String queryString = "from Psinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Psinfo merge(Psinfo detachedInstance) {
		log.debug("merging Psinfo instance");
		try {
			Psinfo result = (Psinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Psinfo instance) {
		log.debug("attaching dirty Psinfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Psinfo instance) {
		log.debug("attaching clean Psinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}