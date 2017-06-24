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
 * JobBagSplite entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.JobBagSplite
 * @author MyEclipse Persistence Tools
 */

public class JobBagSpliteDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(JobBagSpliteDAO.class);
	// property constants
	public static final String LP_ZIP_CODE_BEGIN = "lpZipCodeBegin";
	public static final String LP_ZIP_CODE_END = "lpZipCodeEnd";
	public static final String LP_ZIP_CODE_DIFF = "lpZipCodeDiff";
	public static final String LP_BLANK_BEGIN = "lpBlankBegin";
	public static final String LP_BLANK_END = "lpBlankEnd";
	public static final String LP_BLANK_DIFF = "lpBlankDiff";
	public static final String LP_PAPER_BEGIN = "lpPaperBegin";
	public static final String LP_PAPER_END = "lpPaperEnd";
	public static final String LP_PAPER_DIFF = "lpPaperDiff";
	public static final String LP_PAGES_SEQ_BEGIN = "lpPagesSeqBegin";
	public static final String LP_PAGES_SEQ_END = "lpPagesSeqEnd";
	public static final String LP_PAGES_SEQ_DIFF = "lpPagesSeqDiff";
	public static final String LP_PAGES_SEQ_EXTRA = "lpPagesSeqExtra";
	public static final String LP_ACCOUNT_SEQ_BEGIN = "lpAccountSeqBegin";
	public static final String LP_ACCOUNT_SEQ_END = "lpAccountSeqEnd";
	public static final String LP_ACCOUNT_SEQ_DIFF = "lpAccountSeqDiff";
	public static final String LP_MACHINE_NAME = "lpMachineName";
	public static final String LP_COMPLETED_USER = "lpCompletedUser";
	public static final String LP_COMPLETED_MANAGER = "lpCompletedManager";
	public static final String MP_HAS_DAMAGE = "mpHasDamage";
	public static final String MP_COMPLETED_USER = "mpCompletedUser";
	public static final String MP_COMPLETED_MANAGER = "mpCompletedManager";
	public static final String MP_MACHINE_NAME = "mpMachineName";
	public static final String LG_COMPLETED_USER = "lgCompletedUser";
	public static final String LG_COMPLETED_MANAGER = "lgCompletedManager";
	public static final String PRINTING_COUNT = "printingCount";
	public static final String AFP_FILENAME = "afpFilename";
	public static final String LOG_FILENAME = "logFilename";
	public static final String LINE_DATA = "lineData";
	public static final String ACCOUNTS = "accounts";
	public static final String PAGES = "pages";
	public static final String SHEETS = "sheets";
	public static final String FEEDER2 = "feeder2";
	public static final String FEEDER3 = "feeder3";
	public static final String FEEDER4 = "feeder4";
	public static final String FEEDER5 = "feeder5";
	public static final String TRAY1 = "tray1";
	public static final String TRAY2 = "tray2";
	public static final String TRAY3 = "tray3";
	public static final String TRAY4 = "tray4";
	public static final String P1ACCOUNTS = "p1accounts";
	public static final String P2ACCOUNTS = "p2accounts";
	public static final String P3ACCOUNTS = "p3accounts";
	public static final String P4ACCOUNTS = "p4accounts";
	public static final String P5ACCOUNTS = "p5accounts";
	public static final String P6ACCOUNTS = "p6accounts";
	public static final String PXACCOUNTS = "pxaccounts";

	public void save(JobBagSplite transientInstance) {
		log.debug("saving JobBagSplite instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		}catch(NonUniqueObjectException e){
			getSession().merge(transientInstance);
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(JobBagSplite persistentInstance) {
		log.debug("deleting JobBagSplite instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public JobBagSplite findById(java.lang.String id) {
		log.debug("getting JobBagSplite instance with id: " + id);
		try {
			JobBagSplite instance = (JobBagSplite) getSession().get(
					"com.salmat.jbm.hibernate.JobBagSplite", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(JobBagSplite instance) {
		log.debug("finding JobBagSplite instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.JobBagSplite")
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
		log.debug("finding JobBagSplite instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from JobBagSplite as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLpZipCodeBegin(Object lpZipCodeBegin) {
		return findByProperty(LP_ZIP_CODE_BEGIN, lpZipCodeBegin);
	}

	public List findByLpZipCodeEnd(Object lpZipCodeEnd) {
		return findByProperty(LP_ZIP_CODE_END, lpZipCodeEnd);
	}

	public List findByLpZipCodeDiff(Object lpZipCodeDiff) {
		return findByProperty(LP_ZIP_CODE_DIFF, lpZipCodeDiff);
	}

	public List findByLpBlankBegin(Object lpBlankBegin) {
		return findByProperty(LP_BLANK_BEGIN, lpBlankBegin);
	}

	public List findByLpBlankEnd(Object lpBlankEnd) {
		return findByProperty(LP_BLANK_END, lpBlankEnd);
	}

	public List findByLpBlankDiff(Object lpBlankDiff) {
		return findByProperty(LP_BLANK_DIFF, lpBlankDiff);
	}

	public List findByLpPaperBegin(Object lpPaperBegin) {
		return findByProperty(LP_PAPER_BEGIN, lpPaperBegin);
	}

	public List findByLpPaperEnd(Object lpPaperEnd) {
		return findByProperty(LP_PAPER_END, lpPaperEnd);
	}

	public List findByLpPaperDiff(Object lpPaperDiff) {
		return findByProperty(LP_PAPER_DIFF, lpPaperDiff);
	}

	public List findByLpPagesSeqBegin(Object lpPagesSeqBegin) {
		return findByProperty(LP_PAGES_SEQ_BEGIN, lpPagesSeqBegin);
	}

	public List findByLpPagesSeqEnd(Object lpPagesSeqEnd) {
		return findByProperty(LP_PAGES_SEQ_END, lpPagesSeqEnd);
	}

	public List findByLpPagesSeqDiff(Object lpPagesSeqDiff) {
		return findByProperty(LP_PAGES_SEQ_DIFF, lpPagesSeqDiff);
	}

	public List findByLpPagesSeqExtra(Object lpPagesSeqExtra) {
		return findByProperty(LP_PAGES_SEQ_EXTRA, lpPagesSeqExtra);
	}

	public List findByLpAccountSeqBegin(Object lpAccountSeqBegin) {
		return findByProperty(LP_ACCOUNT_SEQ_BEGIN, lpAccountSeqBegin);
	}

	public List findByLpAccountSeqEnd(Object lpAccountSeqEnd) {
		return findByProperty(LP_ACCOUNT_SEQ_END, lpAccountSeqEnd);
	}

	public List findByLpAccountSeqDiff(Object lpAccountSeqDiff) {
		return findByProperty(LP_ACCOUNT_SEQ_DIFF, lpAccountSeqDiff);
	}

	public List findByLpMachineName(Object lpMachineName) {
		return findByProperty(LP_MACHINE_NAME, lpMachineName);
	}

	public List findByLpCompletedUser(Object lpCompletedUser) {
		return findByProperty(LP_COMPLETED_USER, lpCompletedUser);
	}

	public List findByLpCompletedManager(Object lpCompletedManager) {
		return findByProperty(LP_COMPLETED_MANAGER, lpCompletedManager);
	}

	public List findByMpHasDamage(Object mpHasDamage) {
		return findByProperty(MP_HAS_DAMAGE, mpHasDamage);
	}

	public List findByMpCompletedUser(Object mpCompletedUser) {
		return findByProperty(MP_COMPLETED_USER, mpCompletedUser);
	}

	public List findByMpCompletedManager(Object mpCompletedManager) {
		return findByProperty(MP_COMPLETED_MANAGER, mpCompletedManager);
	}

	public List findByMpMachineName(Object mpMachineName) {
		return findByProperty(MP_MACHINE_NAME, mpMachineName);
	}

	public List findByLgCompletedUser(Object lgCompletedUser) {
		return findByProperty(LG_COMPLETED_USER, lgCompletedUser);
	}

	public List findByLgCompletedManager(Object lgCompletedManager) {
		return findByProperty(LG_COMPLETED_MANAGER, lgCompletedManager);
	}

	public List findByPrintingCount(Object printingCount) {
		return findByProperty(PRINTING_COUNT, printingCount);
	}

	public List findByAfpFilename(Object afpFilename) {
		return findByProperty(AFP_FILENAME, afpFilename);
	}

	public List findByLogFilename(Object logFilename) {
		return findByProperty(LOG_FILENAME, logFilename);
	}

	public List findByLineData(Object lineData) {
		return findByProperty(LINE_DATA, lineData);
	}

	public List findByAccounts(Object accounts) {
		return findByProperty(ACCOUNTS, accounts);
	}

	public List findByPages(Object pages) {
		return findByProperty(PAGES, pages);
	}

	public List findBySheets(Object sheets) {
		return findByProperty(SHEETS, sheets);
	}

	public List findByFeeder2(Object feeder2) {
		return findByProperty(FEEDER2, feeder2);
	}

	public List findByFeeder3(Object feeder3) {
		return findByProperty(FEEDER3, feeder3);
	}

	public List findByFeeder4(Object feeder4) {
		return findByProperty(FEEDER4, feeder4);
	}

	public List findByFeeder5(Object feeder5) {
		return findByProperty(FEEDER5, feeder5);
	}

	public List findByTray1(Object tray1) {
		return findByProperty(TRAY1, tray1);
	}

	public List findByTray2(Object tray2) {
		return findByProperty(TRAY2, tray2);
	}

	public List findByTray3(Object tray3) {
		return findByProperty(TRAY3, tray3);
	}

	public List findByTray4(Object tray4) {
		return findByProperty(TRAY4, tray4);
	}

	public List findByP1accounts(Object p1accounts) {
		return findByProperty(P1ACCOUNTS, p1accounts);
	}

	public List findByP2accounts(Object p2accounts) {
		return findByProperty(P2ACCOUNTS, p2accounts);
	}

	public List findByP3accounts(Object p3accounts) {
		return findByProperty(P3ACCOUNTS, p3accounts);
	}

	public List findByP4accounts(Object p4accounts) {
		return findByProperty(P4ACCOUNTS, p4accounts);
	}

	public List findByP5accounts(Object p5accounts) {
		return findByProperty(P5ACCOUNTS, p5accounts);
	}

	public List findByP6accounts(Object p6accounts) {
		return findByProperty(P6ACCOUNTS, p6accounts);
	}

	public List findByPxaccounts(Object pxaccounts) {
		return findByProperty(PXACCOUNTS, pxaccounts);
	}

	public List findAll() {
		log.debug("finding all JobBagSplite instances");
		try {
			String queryString = "from JobBagSplite";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public JobBagSplite merge(JobBagSplite detachedInstance) {
		log.debug("merging JobBagSplite instance");
		try {
			JobBagSplite result = (JobBagSplite) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(JobBagSplite instance) {
		log.debug("attaching dirty JobBagSplite instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(JobBagSplite instance) {
		log.debug("attaching clean JobBagSplite instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}