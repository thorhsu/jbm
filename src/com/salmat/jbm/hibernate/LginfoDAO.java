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
 * Lginfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Lginfo
 * @author MyEclipse Persistence Tools
 */

public class LginfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(LginfoDAO.class);
	// property constants
	public static final String PROG_NM = "progNm";
	public static final String DISP_POSTA = "dispPosta";
	public static final String _PTYPE = "PType";
	public static final String _PLIST = "PList";
	public static final String NEXT_EFFECTIVE_DATE = "nextEffectiveDate";
	public static final String NEXT_PROG_NM = "nextProgNm";
	public static final String NEXT_PLIST = "nextPList";
	public static final String MAIL_TO_AREA_DISPLAY = "mailToAreaDisplay";

	public void save(Lginfo transientInstance) {
		log.debug("saving Lginfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Lginfo persistentInstance) {
		log.debug("deleting Lginfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Lginfo findById(java.lang.String id) {
		log.debug("getting Lginfo instance with id: " + id);
		try {
			Lginfo instance = (Lginfo) getSession().get(
					"com.salmat.jbm.hibernate.Lginfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Lginfo instance) {
		log.debug("finding Lginfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Lginfo")
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
		log.debug("finding Lginfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Lginfo as model where model."
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

	public List findByDispPosta(Object dispPosta) {
		return findByProperty(DISP_POSTA, dispPosta);
	}

	public List findByPType(Object PType) {
		return findByProperty(_PTYPE, PType);
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

	public List findByMailToAreaDisplay(Object mailToAreaDisplay) {
		return findByProperty(MAIL_TO_AREA_DISPLAY, mailToAreaDisplay);
	}

	public List findAll() {
		log.debug("finding all Lginfo instances");
		try {
			String queryString = "from Lginfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Lginfo merge(Lginfo detachedInstance) {
		log.debug("merging Lginfo instance");
		try {
			Lginfo result = (Lginfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Lginfo instance) {
		log.debug("attaching dirty Lginfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Lginfo instance) {
		log.debug("attaching clean Lginfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}