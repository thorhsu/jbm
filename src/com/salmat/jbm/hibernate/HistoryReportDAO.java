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
 * HistoryReport entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.HistoryReport
 * @author MyEclipse Persistence Tools
 */

public class HistoryReportDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(HistoryReportDAO.class);
	// property constants
	public static final String REPORT_NAME = "reportName";
	public static final String PDF_PATH = "pdfPath";
	public static final String EMP_NO = "empNo";

	public void save(HistoryReport transientInstance) {
		log.debug("saving HistoryReport instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(HistoryReport persistentInstance) {
		log.debug("deleting HistoryReport instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public HistoryReport findById(java.lang.Integer id) {
		log.debug("getting HistoryReport instance with id: " + id);
		try {
			HistoryReport instance = (HistoryReport) getSession().get(
					"com.salmat.jbm.hibernate.HistoryReport", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(HistoryReport instance) {
		log.debug("finding HistoryReport instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.HistoryReport")
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
		log.debug("finding HistoryReport instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from HistoryReport as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByReportName(Object reportName) {
		return findByProperty(REPORT_NAME, reportName);
	}

	public List findByPdfPath(Object pdfPath) {
		return findByProperty(PDF_PATH, pdfPath);
	}

	public List findByEmpNo(Object empNo) {
		return findByProperty(EMP_NO, empNo);
	}

	public List findAll() {
		log.debug("finding all HistoryReport instances");
		try {
			String queryString = "from HistoryReport";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public HistoryReport merge(HistoryReport detachedInstance) {
		log.debug("merging HistoryReport instance");
		try {
			HistoryReport result = (HistoryReport) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(HistoryReport instance) {
		log.debug("attaching dirty HistoryReport instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(HistoryReport instance) {
		log.debug("attaching clean HistoryReport instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}