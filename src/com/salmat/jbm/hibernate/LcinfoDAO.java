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
 * Lcinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Lcinfo
 * @author MyEclipse Persistence Tools
 */

public class LcinfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(LcinfoDAO.class);
	// property constants
	public static final String PROG_NM = "progNm";
	public static final String _PLIST = "PList";
	public static final String NEXT_EFFECTIVE_DATE = "nextEffectiveDate";
	public static final String NEXT_PROG_NM = "nextProgNm";
	public static final String NEXT_PLIST = "nextPList";
	public static final String TEMPLATE = "template";
	public static final String NEXT_TEMPLATE = "nextTemplate";
	public static final String EXTRA_LG_TYPE1 = "extraLgType1";
	public static final String EXTRA_LG_TYPE2 = "extraLgType2";
	public static final String EXTRA_LG_TYPE3 = "extraLgType3";
	public static final String EXTRA_LG_TYPE4 = "extraLgType4";
	public static final String EXTRA_LG_TYPE5 = "extraLgType5";
	public static final String EXTRA_LG_TYPE6 = "extraLgType6";

	public void save(Lcinfo transientInstance) {
		log.debug("saving Lcinfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Lcinfo persistentInstance) {
		log.debug("deleting Lcinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Lcinfo findById(java.lang.String id) {
		log.debug("getting Lcinfo instance with id: " + id);
		try {
			Lcinfo instance = (Lcinfo) getSession().get(
					"com.salmat.jbm.hibernate.Lcinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Lcinfo instance) {
		log.debug("finding Lcinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Lcinfo")
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
		log.debug("finding Lcinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Lcinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProgNm(Object progNm) {
		return findByProperty(PROG_NM, progNm);
	}

	public List findByPList(Object PList) {
		return findByProperty(_PLIST, PList);
	}

	public List findByNextEffectiveDate(Object nextEffectiveDate) {
		return findByProperty(NEXT_EFFECTIVE_DATE, nextEffectiveDate);
	}

	public List findByNextProgNm(Object nextProgNm) {
		return findByProperty(NEXT_PROG_NM, nextProgNm);
	}

	public List findByNextPList(Object nextPList) {
		return findByProperty(NEXT_PLIST, nextPList);
	}

	public List findByTemplate(Object template) {
		return findByProperty(TEMPLATE, template);
	}

	public List findByNextTemplate(Object nextTemplate) {
		return findByProperty(NEXT_TEMPLATE, nextTemplate);
	}

	public List findByExtraLgType1(Object extraLgType1) {
		return findByProperty(EXTRA_LG_TYPE1, extraLgType1);
	}

	public List findByExtraLgType2(Object extraLgType2) {
		return findByProperty(EXTRA_LG_TYPE2, extraLgType2);
	}

	public List findByExtraLgType3(Object extraLgType3) {
		return findByProperty(EXTRA_LG_TYPE3, extraLgType3);
	}

	public List findByExtraLgType4(Object extraLgType4) {
		return findByProperty(EXTRA_LG_TYPE4, extraLgType4);
	}

	public List findByExtraLgType5(Object extraLgType5) {
		return findByProperty(EXTRA_LG_TYPE5, extraLgType5);
	}

	public List findByExtraLgType6(Object extraLgType6) {
		return findByProperty(EXTRA_LG_TYPE6, extraLgType6);
	}

	public List findAll() {
		log.debug("finding all Lcinfo instances");
		try {
			String queryString = "from Lcinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Lcinfo merge(Lcinfo detachedInstance) {
		log.debug("merging Lcinfo instance");
		try {
			Lcinfo result = (Lcinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Lcinfo instance) {
		log.debug("attaching dirty Lcinfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Lcinfo instance) {
		log.debug("attaching clean Lcinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}