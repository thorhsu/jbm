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
 * AcctSectionReceiverMapping entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.salmat.jbm.hibernate.AcctSectionReceiverMapping
 * @author MyEclipse Persistence Tools
 */

public class AcctSectionReceiverMappingDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(AcctSectionReceiverMappingDAO.class);
	// property constants
	public static final String SECTION = "section";
	public static final String REPORT_III_TITLE = "reportIiiTitle";
	public static final String MERGE_REPORT_III_FG1 = "mergeReportIiiFg1";
	public static final String MERGE_REPORT_III_FG2 = "mergeReportIiiFg2";
	public static final String MERGE_REPORT_III_FG3 = "mergeReportIiiFg3";
	public static final String MERGE_REPORT_III_TITLE1 = "mergeReportIiiTitle1";
	public static final String MERGE_REPORT_III_TITLE2 = "mergeReportIiiTitle2";
	public static final String MERGE_REPORT_III_TITLE3 = "mergeReportIiiTitle3";
	public static final String MERGE_REPORT_III_SUBTITLE1 = "mergeReportIiiSubtitle1";
	public static final String MERGE_REPORT_III_SUBTITLE2 = "mergeReportIiiSubtitle2";
	public static final String MERGE_REPORT_III_SUBTITLE3 = "mergeReportIiiSubtitle3";
	public static final String VAT = "vat";
	public static final String PAYMENT_TERM = "paymentTerm";
	public static final String IS_SUMMARIZATION_OF_SUM1 = "isSummarizationOfSum1";

	public void save(AcctSectionReceiverMapping transientInstance) {
		log.debug("saving AcctSectionReceiverMapping instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(AcctSectionReceiverMapping persistentInstance) {
		log.debug("deleting AcctSectionReceiverMapping instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AcctSectionReceiverMapping findById(java.lang.Integer id) {
		log.debug("getting AcctSectionReceiverMapping instance with id: " + id);
		try {
			AcctSectionReceiverMapping instance = (AcctSectionReceiverMapping) getSession()
					.get("com.salmat.jbm.hibernate.AcctSectionReceiverMapping",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(AcctSectionReceiverMapping instance) {
		log.debug("finding AcctSectionReceiverMapping instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.salmat.jbm.hibernate.AcctSectionReceiverMapping")
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
		log.debug("finding AcctSectionReceiverMapping instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from AcctSectionReceiverMapping as model where model."
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

	public List findByReportIiiTitle(Object reportIiiTitle) {
		return findByProperty(REPORT_III_TITLE, reportIiiTitle);
	}

	public List findByMergeReportIiiFg1(Object mergeReportIiiFg1) {
		return findByProperty(MERGE_REPORT_III_FG1, mergeReportIiiFg1);
	}

	public List findByMergeReportIiiFg2(Object mergeReportIiiFg2) {
		return findByProperty(MERGE_REPORT_III_FG2, mergeReportIiiFg2);
	}

	public List findByMergeReportIiiFg3(Object mergeReportIiiFg3) {
		return findByProperty(MERGE_REPORT_III_FG3, mergeReportIiiFg3);
	}

	public List findByMergeReportIiiTitle1(Object mergeReportIiiTitle1) {
		return findByProperty(MERGE_REPORT_III_TITLE1, mergeReportIiiTitle1);
	}

	public List findByMergeReportIiiTitle2(Object mergeReportIiiTitle2) {
		return findByProperty(MERGE_REPORT_III_TITLE2, mergeReportIiiTitle2);
	}

	public List findByMergeReportIiiTitle3(Object mergeReportIiiTitle3) {
		return findByProperty(MERGE_REPORT_III_TITLE3, mergeReportIiiTitle3);
	}

	public List findByMergeReportIiiSubtitle1(Object mergeReportIiiSubtitle1) {
		return findByProperty(MERGE_REPORT_III_SUBTITLE1,
				mergeReportIiiSubtitle1);
	}

	public List findByMergeReportIiiSubtitle2(Object mergeReportIiiSubtitle2) {
		return findByProperty(MERGE_REPORT_III_SUBTITLE2,
				mergeReportIiiSubtitle2);
	}

	public List findByMergeReportIiiSubtitle3(Object mergeReportIiiSubtitle3) {
		return findByProperty(MERGE_REPORT_III_SUBTITLE3,
				mergeReportIiiSubtitle3);
	}

	public List findByVat(Object vat) {
		return findByProperty(VAT, vat);
	}

	public List findByPaymentTerm(Object paymentTerm) {
		return findByProperty(PAYMENT_TERM, paymentTerm);
	}

	public List findByIsSummarizationOfSum1(Object isSummarizationOfSum1) {
		return findByProperty(IS_SUMMARIZATION_OF_SUM1, isSummarizationOfSum1);
	}

	public List findAll() {
		log.debug("finding all AcctSectionReceiverMapping instances");
		try {
			String queryString = "from AcctSectionReceiverMapping";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public AcctSectionReceiverMapping merge(
			AcctSectionReceiverMapping detachedInstance) {
		log.debug("merging AcctSectionReceiverMapping instance");
		try {
			AcctSectionReceiverMapping result = (AcctSectionReceiverMapping) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AcctSectionReceiverMapping instance) {
		log.debug("attaching dirty AcctSectionReceiverMapping instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AcctSectionReceiverMapping instance) {
		log.debug("attaching clean AcctSectionReceiverMapping instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}