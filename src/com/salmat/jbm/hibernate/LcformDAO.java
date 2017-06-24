package com.salmat.jbm.hibernate;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Lcform entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Lcform
 * @author MyEclipse Persistence Tools
 */

public class LcformDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(LcformDAO.class);
	// property constants
	public static final String QUERY_SESSION = "querySession";
	public static final String POST_OFFICE_NAME = "postOfficeName";
	public static final String POST_OFFICE_TEL = "postOfficeTel";
	public static final String POST_OFFICE_FAX = "postOfficeFax";
	public static final String POST_OFFICE_CONTACT = "postOfficeContact";
	public static final String SALMAT_TEL = "salmatTel";
	public static final String SALMAT_FAX = "salmatFax";
	public static final String SALMAT_CONTACT = "salmatContact";
	public static final String CUST_NO = "custNo";
	public static final String CUST_NAME = "custName";
	public static final String LG_TYPE = "lgType";
	public static final String MAIL_CATEGORY = "mailCategory";
	public static final String JOB_NAME = "jobName";
	public static final String CYCLE_DATE_LIST = "cycleDateList";
	public static final String QTY = "qty";
	public static final String ORDERBY = "orderby";
	public static final String JOB_CODE_NO = "jobCodeNo";
	public static final String EXTRA_LG_TYPE1 = "extraLgType1";
	public static final String EXTRA_LG_TYPE2 = "extraLgType2";
	public static final String EXTRA_LG_TYPE3 = "extraLgType3";
	public static final String EXTRA_LG_TYPE4 = "extraLgType4";
	public static final String EXTRA_LG_TYPE5 = "extraLgType5";
	public static final String EXTRA_LG_TYPE6 = "extraLgType6";
	public static final String CUSTOMER_DEPT = "customerDept";
	public static final String MAIL_TO_AREA = "mailToArea";
	public static final String PRICE = "price";

	public void save(Lcform transientInstance) {
		log.debug("saving Lcform instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Lcform persistentInstance) {
		log.debug("deleting Lcform instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Lcform findById(java.lang.Integer id) {
		log.debug("getting Lcform instance with id: " + id);
		try {
			Lcform instance = (Lcform) getSession().get(
					"com.salmat.jbm.hibernate.Lcform", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Lcform instance) {
		log.debug("finding Lcform instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Lcform")
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
		log.debug("finding Lcform instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Lcform as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByQuerySession(Object querySession) {
		return findByProperty(QUERY_SESSION, querySession);
	}

	public List findByPostOfficeName(Object postOfficeName) {
		return findByProperty(POST_OFFICE_NAME, postOfficeName);
	}

	public List findByPostOfficeTel(Object postOfficeTel) {
		return findByProperty(POST_OFFICE_TEL, postOfficeTel);
	}

	public List findByPostOfficeFax(Object postOfficeFax) {
		return findByProperty(POST_OFFICE_FAX, postOfficeFax);
	}

	public List findByPostOfficeContact(Object postOfficeContact) {
		return findByProperty(POST_OFFICE_CONTACT, postOfficeContact);
	}

	public List findBySalmatTel(Object salmatTel) {
		return findByProperty(SALMAT_TEL, salmatTel);
	}

	public List findBySalmatFax(Object salmatFax) {
		return findByProperty(SALMAT_FAX, salmatFax);
	}

	public List findBySalmatContact(Object salmatContact) {
		return findByProperty(SALMAT_CONTACT, salmatContact);
	}

	public List findByCustNo(Object custNo) {
		return findByProperty(CUST_NO, custNo);
	}

	public List findByCustName(Object custName) {
		return findByProperty(CUST_NAME, custName);
	}

	public List findByLgType(Object lgType) {
		return findByProperty(LG_TYPE, lgType);
	}

	public List findByMailCategory(Object mailCategory) {
		return findByProperty(MAIL_CATEGORY, mailCategory);
	}

	public List findByJobName(Object jobName) {
		return findByProperty(JOB_NAME, jobName);
	}

	public List findByCycleDateList(Object cycleDateList) {
		return findByProperty(CYCLE_DATE_LIST, cycleDateList);
	}

	public List findByQty(Object qty) {
		return findByProperty(QTY, qty);
	}

	public List findByOrderby(Object orderby) {
		return findByProperty(ORDERBY, orderby);
	}

	public List findByJobCodeNo(Object jobCodeNo) {
		return findByProperty(JOB_CODE_NO, jobCodeNo);
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

	public List findByCustomerDept(Object customerDept) {
		return findByProperty(CUSTOMER_DEPT, customerDept);
	}

	public List findByMailToArea(Object mailToArea) {
		return findByProperty(MAIL_TO_AREA, mailToArea);
	}

	public List findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	public List findAll() {
		log.debug("finding all Lcform instances");
		try {
			String queryString = "from Lcform";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Lcform merge(Lcform detachedInstance) {
		log.debug("merging Lcform instance");
		try {
			Lcform result = (Lcform) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Lcform instance) {
		log.debug("attaching dirty Lcform instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Lcform instance) {
		log.debug("attaching clean Lcform instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}