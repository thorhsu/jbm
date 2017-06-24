package com.salmat.jbm.hibernate;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Lgform entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Lgform
 * @author MyEclipse Persistence Tools
 */

public class LgformDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(LgformDAO.class);
	// property constants
	public static final String IDF_CUST_NO = "idfCustNo";
	public static final String CUST_NAME = "custName";
	public static final String CODE_MAIL_TO_AREA = "codeMailToArea";
	public static final String MAIL_TO_AREA = "mailToArea";
	public static final String CODE_LG_TYPE = "codeLgType";
	public static final String LG_TYPE = "lgType";
	public static final String CODE_MAIL_CATEGORY = "codeMailCategory";
	public static final String MAIL_CATEGORY = "mailCategory";
	public static final String QTY = "qty";
	public static final String WEIGHT = "weight";
	public static final String PRICE = "price";
	public static final String JOB_CODE_NO = "jobCodeNo";
	public static final String JOB_BAG_NO = "jobBagNo";
	public static final String PROG_NM = "progNm";
	public static final String STATEMENT_NO = "statementNo";
	public static final String DISPATCHER = "dispatcher";
	public static final String PATH_CUST_STAMP_IMAGE = "pathCustStampImage";
	public static final String LG_TITLE = "lgTitle";

	public void save(Lgform transientInstance) {
		log.debug("saving Lgform instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Lgform persistentInstance) {
		log.debug("deleting Lgform instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Lgform findById(java.lang.Integer id) {
		log.debug("getting Lgform instance with id: " + id);
		try {
			Lgform instance = (Lgform) getSession().get(
					"com.salmat.jbm.hibernate.Lgform", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Lgform instance) {
		log.debug("finding Lgform instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Lgform")
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
		log.debug("finding Lgform instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Lgform as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByIdfCustNo(Object idfCustNo) {
		return findByProperty(IDF_CUST_NO, idfCustNo);
	}

	public List findByCustName(Object custName) {
		return findByProperty(CUST_NAME, custName);
	}

	public List findByCodeMailToArea(Object codeMailToArea) {
		return findByProperty(CODE_MAIL_TO_AREA, codeMailToArea);
	}

	public List findByMailToArea(Object mailToArea) {
		return findByProperty(MAIL_TO_AREA, mailToArea);
	}

	public List findByCodeLgType(Object codeLgType) {
		return findByProperty(CODE_LG_TYPE, codeLgType);
	}

	public List findByLgType(Object lgType) {
		return findByProperty(LG_TYPE, lgType);
	}

	public List findByCodeMailCategory(Object codeMailCategory) {
		return findByProperty(CODE_MAIL_CATEGORY, codeMailCategory);
	}

	public List findByMailCategory(Object mailCategory) {
		return findByProperty(MAIL_CATEGORY, mailCategory);
	}

	public List findByQty(Object qty) {
		return findByProperty(QTY, qty);
	}

	public List findByWeight(Object weight) {
		return findByProperty(WEIGHT, weight);
	}

	public List findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	public List findByJobCodeNo(Object jobCodeNo) {
		return findByProperty(JOB_CODE_NO, jobCodeNo);
	}

	public List findByJobBagNo(Object jobBagNo) {
		return findByProperty(JOB_BAG_NO, jobBagNo);
	}

	public List findByProgNm(Object progNm) {
		return findByProperty(PROG_NM, progNm);
	}

	public List findByStatementNo(Object statementNo) {
		return findByProperty(STATEMENT_NO, statementNo);
	}

	public List findByDispatcher(Object dispatcher) {
		return findByProperty(DISPATCHER, dispatcher);
	}

	public List findByPathCustStampImage(Object pathCustStampImage) {
		return findByProperty(PATH_CUST_STAMP_IMAGE, pathCustStampImage);
	}

	public List findByLgTitle(Object lgTitle) {
		return findByProperty(LG_TITLE, lgTitle);
	}

	public List findAll() {
		log.debug("finding all Lgform instances");
		try {
			String queryString = "from Lgform";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Lgform merge(Lgform detachedInstance) {
		log.debug("merging Lgform instance");
		try {
			Lgform result = (Lgform) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Lgform instance) {
		log.debug("attaching dirty Lgform instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Lgform instance) {
		log.debug("attaching clean Lgform instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}