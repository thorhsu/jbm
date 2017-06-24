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
 * Lpinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Lpinfo
 * @author MyEclipse Persistence Tools
 */

public class LpinfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(LpinfoDAO.class);
	// property constants
	public static final String PRINT_TYPE = "printType";
	public static final String PRINT_SET = "printSet";
	public static final String RESOLUTION = "resolution";
	public static final String SUG_TONER = "sugToner";
	public static final String REMARK = "remark";
	public static final String LP_PRICE = "lpPrice";
	public static final String PRODMEMO = "prodmemo";
	public static final String PRINTER = "printer";
	public static final String PCODE1 = "pcode1";
	public static final String PCODE2 = "pcode2";
	public static final String PCODE3 = "pcode3";
	public static final String PCODE4 = "pcode4";
	public static final String NEXT_EFFECTIVE_DATE = "nextEffectiveDate";
	public static final String NEXT_PRODMEMO = "nextProdmemo";
	public static final String NEXT_PCODE1 = "nextPcode1";
	public static final String NEXT_PCODE2 = "nextPcode2";
	public static final String NEXT_PCODE3 = "nextPcode3";
	public static final String NEXT_PCODE4 = "nextPcode4";
	public static final String NEXT_REMARK = "nextRemark";

	public void save(Lpinfo transientInstance) {
		log.debug("saving Lpinfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Lpinfo persistentInstance) {
		log.debug("deleting Lpinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Lpinfo findById(java.lang.String id) {
		log.debug("getting Lpinfo instance with id: " + id);
		try {
			Lpinfo instance = (Lpinfo) getSession().get(
					"com.salmat.jbm.hibernate.Lpinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Lpinfo instance) {
		log.debug("finding Lpinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Lpinfo")
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
		log.debug("finding Lpinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Lpinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPrintType(Object printType) {
		return findByProperty(PRINT_TYPE, printType);
	}

	public List findByPrintSet(Object printSet) {
		return findByProperty(PRINT_SET, printSet);
	}

	public List findByResolution(Object resolution) {
		return findByProperty(RESOLUTION, resolution);
	}

	public List findBySugToner(Object sugToner) {
		return findByProperty(SUG_TONER, sugToner);
	}

	public List findByRemark(Object remark) {
		return findByProperty(REMARK, remark);
	}

	public List findByLpPrice(Object lpPrice) {
		return findByProperty(LP_PRICE, lpPrice);
	}

	public List findByProdmemo(Object prodmemo) {
		return findByProperty(PRODMEMO, prodmemo);
	}

	public List findByPrinter(Object printer) {
		return findByProperty(PRINTER, printer);
	}

	public List findByPcode1(Object pcode1) {
		return findByProperty(PCODE1, pcode1);
	}

	public List findByPcode2(Object pcode2) {
		return findByProperty(PCODE2, pcode2);
	}

	public List findByPcode3(Object pcode3) {
		return findByProperty(PCODE3, pcode3);
	}

	public List findByPcode4(Object pcode4) {
		return findByProperty(PCODE4, pcode4);
	}

	public List findByNextEffectiveDate(Object nextEffectiveDate) {
		return findByProperty(NEXT_EFFECTIVE_DATE, nextEffectiveDate);
	}

	public List findByNextProdmemo(Object nextProdmemo) {
		return findByProperty(NEXT_PRODMEMO, nextProdmemo);
	}

	public List findByNextPcode1(Object nextPcode1) {
		return findByProperty(NEXT_PCODE1, nextPcode1);
	}

	public List findByNextPcode2(Object nextPcode2) {
		return findByProperty(NEXT_PCODE2, nextPcode2);
	}

	public List findByNextPcode3(Object nextPcode3) {
		return findByProperty(NEXT_PCODE3, nextPcode3);
	}

	public List findByNextPcode4(Object nextPcode4) {
		return findByProperty(NEXT_PCODE4, nextPcode4);
	}

	public List findByNextRemark(Object nextRemark) {
		return findByProperty(NEXT_REMARK, nextRemark);
	}

	public List findAll() {
		log.debug("finding all Lpinfo instances");
		try {
			String queryString = "from Lpinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Lpinfo merge(Lpinfo detachedInstance) {
		log.debug("merging Lpinfo instance");
		try {
			Lpinfo result = (Lpinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Lpinfo instance) {
		log.debug("attaching dirty Lpinfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Lpinfo instance) {
		log.debug("attaching clean Lpinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}