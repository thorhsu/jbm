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
 * AcctInvoice entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.AcctInvoice
 * @author MyEclipse Persistence Tools
 */

public class AcctInvoiceDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcctInvoiceDAO.class);
	// property constants
	public static final String COST_CENTER = "costCenter";
	public static final String EXPENSE = "expense";
	public static final String INVOICENO = "invoiceno";
	public static final String DEPT = "dept";
	public static final String TAX = "tax";
	public static final String JOBTYPE = "jobtype";
	public static final String JOB_CODE_NO = "jobCodeNo";
	public static final String INVOICETYP = "invoicetyp";

	public void save(AcctInvoice transientInstance) {
		log.debug("saving AcctInvoice instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcctInvoice persistentInstance) {
		log.debug("deleting AcctInvoice instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctInvoice findById(java.lang.Integer id) {
		log.debug("getting AcctInvoice instance with id: " + id);
		try {
			AcctInvoice instance = (AcctInvoice) getSession().get(
					"com.salmat.jbm.hibernate.AcctInvoice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctInvoice instance) {
		log.debug("finding AcctInvoice instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.AcctInvoice")
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
		log.debug("finding AcctInvoice instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AcctInvoice as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCostCenter(Object costCenter) {
		return findByProperty(COST_CENTER, costCenter);
	}

	public List findByExpense(Object expense) {
		return findByProperty(EXPENSE, expense);
	}

	public List findByInvoiceno(Object invoiceno) {
		return findByProperty(INVOICENO, invoiceno);
	}

	public List findByDept(Object dept) {
		return findByProperty(DEPT, dept);
	}

	public List findByTax(Object tax) {
		return findByProperty(TAX, tax);
	}

	public List findByJobtype(Object jobtype) {
		return findByProperty(JOBTYPE, jobtype);
	}

	public List findByJobCodeNo(Object jobCodeNo) {
		return findByProperty(JOB_CODE_NO, jobCodeNo);
	}

	public List findByInvoicetyp(Object invoicetyp) {
		return findByProperty(INVOICETYP, invoicetyp);
	}

	public List findAll() {
		log.debug("finding all AcctInvoice instances");
		try {
			String queryString = "from AcctInvoice";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctInvoice merge(AcctInvoice detachedInstance) {
		log.debug("merging AcctInvoice instance");
		try {
			AcctInvoice result = (AcctInvoice) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctInvoice instance) {
		log.debug("attaching dirty AcctInvoice instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctInvoice instance) {
		log.debug("attaching clean AcctInvoice instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}