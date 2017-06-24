package com.salmat.jbm.hibernate;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for Amex
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Amex
 * @author MyEclipse Persistence Tools
 */

public class AmexDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(AmexDAO.class);
	// property constants
	public static final String COUNTRY = "country";
	public static final String AFP_NAME = "afpName";
	public static final String JOB_CODE_NO = "jobCodeNo";
	public static final String TYPEFILE = "typefile";
	public static final String SPEAKER = "speaker";
	public static final String SPECIAL = "special";
	public static final String MACREPORT = "macreport";
	public static final String AEB_TYPE = "aebType";
	public static final String PAGES = "pages";

	public void save(Amex transientInstance) {
		log.debug("saving Amex instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Amex persistentInstance) {
		log.debug("deleting Amex instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Amex findById(java.lang.Integer id) {
		log.debug("getting Amex instance with id: " + id);
		try {
			Amex instance = (Amex) getSession().get(
					"com.salmat.jbm.hibernate.Amex", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Amex instance) {
		log.debug("finding Amex instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Amex")
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
		log.debug("finding Amex instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Amex as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCountry(Object country) {
		return findByProperty(COUNTRY, country);
	}

	public List findByAfpName(Object afpName) {
		return findByProperty(AFP_NAME, afpName);
	}

	public List findByJobCodeNo(Object jobCodeNo) {
		return findByProperty(JOB_CODE_NO, jobCodeNo);
	}

	public List findByTypefile(Object typefile) {
		return findByProperty(TYPEFILE, typefile);
	}

	public List findBySpeaker(Object speaker) {
		return findByProperty(SPEAKER, speaker);
	}

	public List findBySpecial(Object special) {
		return findByProperty(SPECIAL, special);
	}

	public List findByMacreport(Object macreport) {
		return findByProperty(MACREPORT, macreport);
	}

	public List findByAebType(Object aebType) {
		return findByProperty(AEB_TYPE, aebType);
	}

	public List findByPages(Object pages) {
		return findByProperty(PAGES, pages);
	}

	public List findAll() {
		log.debug("finding all Amex instances");
		try {
			String queryString = "from Amex";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Amex merge(Amex detachedInstance) {
		log.debug("merging Amex instance");
		try {
			Amex result = (Amex) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Amex instance) {
		log.debug("attaching dirty Amex instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Amex instance) {
		log.debug("attaching clean Amex instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}