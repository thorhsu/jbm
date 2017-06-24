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
 * AcctSectionJobCodeMapping entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.salmat.jbm.hibernate.AcctSectionJobCodeMapping
 * @author MyEclipse Persistence Tools
 */

public class AcctSectionJobCodeMappingDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcctSectionJobCodeMappingDAO.class);
	// property constants
	public static final String SECTION = "section";

	public void save(AcctSectionJobCodeMapping transientInstance) {
		log.debug("saving AcctSectionJobCodeMapping instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcctSectionJobCodeMapping persistentInstance) {
		log.debug("deleting AcctSectionJobCodeMapping instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctSectionJobCodeMapping findById(java.lang.Integer id) {
		log.debug("getting AcctSectionJobCodeMapping instance with id: " + id);
		try {
			AcctSectionJobCodeMapping instance = (AcctSectionJobCodeMapping) getSession()
					.get("com.salmat.jbm.hibernate.AcctSectionJobCodeMapping",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctSectionJobCodeMapping instance) {
		log.debug("finding AcctSectionJobCodeMapping instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.salmat.jbm.hibernate.AcctSectionJobCodeMapping")
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
		log.debug("finding AcctSectionJobCodeMapping instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AcctSectionJobCodeMapping as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySection(Object section) {
		return findByProperty(SECTION, section);
	}

	public List findAll() {
		log.debug("finding all AcctSectionJobCodeMapping instances");
		try {
			String queryString = "from AcctSectionJobCodeMapping";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctSectionJobCodeMapping merge(
			AcctSectionJobCodeMapping detachedInstance) {
		log.debug("merging AcctSectionJobCodeMapping instance");
		try {
			AcctSectionJobCodeMapping result = (AcctSectionJobCodeMapping) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctSectionJobCodeMapping instance) {
		log.debug("attaching dirty AcctSectionJobCodeMapping instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctSectionJobCodeMapping instance) {
		log.debug("attaching clean AcctSectionJobCodeMapping instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}