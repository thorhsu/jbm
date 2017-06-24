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
 * AcctDno entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.AcctDno
 * @author MyEclipse Persistence Tools
 */

public class AcctDnoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(AcctDnoDAO.class);
	// property constants
	public static final String SECTION = "section";
	public static final String DES_SDT = "desSdt";
	public static final String DES_EDT = "desEdt";
	public static final String PROCESSDT = "processdt";
	public static final String PROCESSDT2 = "processdt2";
	public static final String PROCESSDT3 = "processdt3";
	public static final String IN_NO = "inNo";
	public static final String IN_DT = "inDt";
	public static final String IN_DUEDT = "inDuedt";
	public static final String TAX = "tax";
	public static final String REFUND = "refund";
	public static final String REFUNDAMT = "refundamt";
	public static final String EP1 = "ep1";
	public static final String EP1_CONFIRM = "ep1Confirm";
	public static final String CYCLE_SDT = "cycleSdt";
	public static final String CYCLE_EDT = "cycleEdt";
	public static final String AMOUNT_INCLUDE_TAX = "amountIncludeTax";
	public static final String AMOUNT_EXCLUDE_TAX = "amountExcludeTax";

	public void save(AcctDno transientInstance) {
		log.debug("saving AcctDno instance");
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

	public void delete(AcctDno persistentInstance) {
		log.debug("deleting AcctDno instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctDno findById(java.lang.String id) {
		log.debug("getting AcctDno instance with id: " + id);
		try {
			AcctDno instance = (AcctDno) getSession().get(
					"com.salmat.jbm.hibernate.AcctDno", id);
			
			return instance;
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctDno instance) {
		log.debug("finding AcctDno instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.AcctDno")
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
		log.debug("finding AcctDno instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AcctDno as model where model."
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

	public List findByDesSdt(Object desSdt) {
		return findByProperty(DES_SDT, desSdt);
	}

	public List findByDesEdt(Object desEdt) {
		return findByProperty(DES_EDT, desEdt);
	}

	public List findByProcessdt(Object processdt) {
		return findByProperty(PROCESSDT, processdt);
	}

	public List findByProcessdt2(Object processdt2) {
		return findByProperty(PROCESSDT2, processdt2);
	}

	public List findByProcessdt3(Object processdt3) {
		return findByProperty(PROCESSDT3, processdt3);
	}

	public List findByInNo(Object inNo) {
		return findByProperty(IN_NO, inNo);
	}

	public List findByInDt(Object inDt) {
		return findByProperty(IN_DT, inDt);
	}

	public List findByInDuedt(Object inDuedt) {
		return findByProperty(IN_DUEDT, inDuedt);
	}

	public List findByTax(Object tax) {
		return findByProperty(TAX, tax);
	}

	public List findByRefund(Object refund) {
		return findByProperty(REFUND, refund);
	}

	public List findByRefundamt(Object refundamt) {
		return findByProperty(REFUNDAMT, refundamt);
	}

	public List findByEp1(Object ep1) {
		return findByProperty(EP1, ep1);
	}

	public List findByEp1Confirm(Object ep1Confirm) {
		return findByProperty(EP1_CONFIRM, ep1Confirm);
	}

	public List findByCycleSdt(Object cycleSdt) {
		return findByProperty(CYCLE_SDT, cycleSdt);
	}

	public List findByCycleEdt(Object cycleEdt) {
		return findByProperty(CYCLE_EDT, cycleEdt);
	}

	public List findByAmountIncludeTax(Object amountIncludeTax) {
		return findByProperty(AMOUNT_INCLUDE_TAX, amountIncludeTax);
	}

	public List findByAmountExcludeTax(Object amountExcludeTax) {
		return findByProperty(AMOUNT_EXCLUDE_TAX, amountExcludeTax);
	}

	public List findAll() {
		log.debug("finding all AcctDno instances");
		try {
			String queryString = "from AcctDno";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctDno merge(AcctDno detachedInstance) {
		log.debug("merging AcctDno instance");
		try {
			AcctDno result = (AcctDno) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctDno instance) {
		log.debug("attaching dirty AcctDno instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctDno instance) {
		log.debug("attaching clean AcctDno instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}