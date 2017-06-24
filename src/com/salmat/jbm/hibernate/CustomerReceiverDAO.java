package com.salmat.jbm.hibernate;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * CustomerReceiver entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.CustomerReceiver
 * @author MyEclipse Persistence Tools
 */

public class CustomerReceiverDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerReceiverDAO.class);
	// property constants
	public static final String RECEIVER = "receiver";
	public static final String INVOICE_TITLE = "invoiceTitle";
	public static final String PIN = "pin";
	public static final String ADDRESS = "address";
	public static final String TEL = "tel";

	public void save(CustomerReceiver transientInstance) {
		log.debug("saving CustomerReceiver instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(CustomerReceiver persistentInstance) {
		log.debug("deleting CustomerReceiver instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public CustomerReceiver findById(java.lang.String id) {
		log.debug("getting CustomerReceiver instance with id: " + id);
		try {
			CustomerReceiver instance = (CustomerReceiver) getSession().get(
					"com.salmat.jbm.hibernate.CustomerReceiver", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(CustomerReceiver instance) {
		log.debug("finding CustomerReceiver instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.CustomerReceiver")
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
		log.debug("finding CustomerReceiver instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from CustomerReceiver as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByReceiver(Object receiver) {
		return findByProperty(RECEIVER, receiver);
	}

	public List findByInvoiceTitle(Object invoiceTitle) {
		return findByProperty(INVOICE_TITLE, invoiceTitle);
	}

	public List findByPin(Object pin) {
		return findByProperty(PIN, pin);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByTel(Object tel) {
		return findByProperty(TEL, tel);
	}

	public List findAll() {
		log.debug("finding all CustomerReceiver instances");
		try {
			String queryString = "from CustomerReceiver";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public CustomerReceiver merge(CustomerReceiver detachedInstance) {
		log.debug("merging CustomerReceiver instance");
		try {
			CustomerReceiver result = (CustomerReceiver) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(CustomerReceiver instance) {
		log.debug("attaching dirty CustomerReceiver instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(CustomerReceiver instance) {
		log.debug("attaching clean CustomerReceiver instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}