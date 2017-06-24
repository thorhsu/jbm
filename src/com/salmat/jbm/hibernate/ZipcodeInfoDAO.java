package com.salmat.jbm.hibernate;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * AcctDno entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.ZipcodeInfo
 * @author James_Tsai
 */

public class ZipcodeInfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(ZipcodeInfoDAO.class);
	// property constants
	public static final String CUST_NO = "custNo";
	public static final String SRC_FILENAME = "srcFilename";
	public static final String ZIPCODE_TOTAL = "zipcodeTotal";
	public static final String JOB_BAG_NO = "jobBagNo";
	public static final String CYCLE_DATE = "cycleDate";
	public static final String RECEIVE_DATE = "receiveDate";
	public static final String AFP_FILENAME = "afpFilename";
	public static final String FILE_PATTERN = "filePattern";
	public static final String CREATE_DATE = "createDate";
	public static final String UPDATE_DATE = "updateDate";
	public static final String UPDATE_USER = "updateUser";
	public static final String COMBINED_NUM = "combinedNum";
	public static final String ZIPCODE_FILENAME = "zipcodeFilename";
	public static final String DETAIL_IS_DELETED = "detailIsDeleted";
	public static final String DELETED_DATE = "deletedDate";
	public static final String DISPATCH_TYPE = "dispatchType";
	public static final String DISPATCH_KIND = "dispatchKind";

	public void save(ZipcodeInfo transientInstance) {
		log.debug("saving ZipcodeInfo instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch(NonUniqueObjectException e){
			getSession().merge(transientInstance);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ZipcodeInfo persistentInstance) {
		log.debug("deleting ZipcodeInfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ZipcodeInfo findByZipcodeInfoNo(Integer zipcodeInfoNo) {
		log.debug("getting ZipcodeInfo instance with zipcodeInfoNo: " + zipcodeInfoNo);
		try {
			ZipcodeInfo instance = (ZipcodeInfo) getSession().get(
					"com.salmat.jbm.hibernate.ZipcodeInfo", zipcodeInfoNo);
			
			return instance;
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ZipcodeInfo instance) {
		log.debug("finding ZipcodeInfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.ZipcodeInfo")
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
		log.debug("finding ZipcodeInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ZipcodeInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCustNo(Object custNo) {
		return findByProperty(CUST_NO, custNo);
	}

	public List findByJobBagNo(Object jobBagNo) {
		return findByProperty(JOB_BAG_NO, jobBagNo);
	}

	public List findAll() {
		log.debug("finding all ZipcodeInfo instances");
		try {
			String queryString = "from ZipcodeInfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ZipcodeInfo merge(ZipcodeInfo detachedInstance) {
		log.debug("merging ZipcodeInfo instance");
		try {
			ZipcodeInfo result = (ZipcodeInfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ZipcodeInfo instance) {
		log.debug("attaching dirty ZipcodeInfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ZipcodeInfo instance) {
		log.debug("attaching clean ZipcodeInfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}