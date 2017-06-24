package com.salmat.jbm.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * JobBag entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.JobBag
 * @author MyEclipse Persistence Tools
 */

public class JobBagDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(JobBagDAO.class);
	// property constants
	public static final String JOB_CODE_ID = "jobCodeId";
	public static final String PROG_NM = "progNm";
	public static final String IS_LP = "isLp";
	public static final String IS_MPS = "isMps";
	public static final String IS_LG = "isLg";
	public static final String IS_EDD = "isEdd";
	public static final String MP_DM_PS = "mpDmPs";
	public static final String DISPATCH_TIME = "dispatchTime";
	public static final String DEAD_TIME = "deadTime";
	public static final String IS_ENABLED_CONTRACT = "isEnabledContract";
	public static final String IS_CONVERT_RESULT = "isConvertResult";
	public static final String LP_NOTE = "lpNote";
	public static final String MP_NOTE = "mpNote";
	public static final String PS_NOTE = "psNote";
	public static final String DM_NOTE = "dmNote";
	public static final String DISPATCH_TYPE = "dispatchType";
	public static final String LG_NOTE = "lgNote";
	public static final String FILENAME = "filename";
	public static final String USE_LG = "useLg";
	public static final String ACCOUNTS = "accounts";
	public static final String PAGES = "pages";
	public static final String SHEETS = "sheets";
	public static final String SPLITE_COUNT = "spliteCount";
	public static final String JOB_BAG_STATUS = "jobBagStatus";
	public static final String AMX_NAME = "amxName";
	public static final String AFP_NAME = "afpName";
	public static final String IS_DAMAGE = "isDamage";
	public static final String HAS_DAMAGE = "hasDamage";
	public static final String IS_DELETED = "isDeleted";
	public static final String DELETED_REASON = "deletedReason";
	public static final String POSTS_STATEMENT_NO = "postsStatementNo";
	public static final String FEEDER2 = "feeder2";
	public static final String FEEDER3 = "feeder3";
	public static final String FEEDER4 = "feeder4";
	public static final String FEEDER5 = "feeder5";
	public static final String TRAY1 = "tray1";
	public static final String TRAY2 = "tray2";
	public static final String TRAY3 = "tray3";
	public static final String TRAY4 = "tray4";
	public static final String TRAY5 = "tray5";
	public static final String LOG_FILENAME = "logFilename";
	public static final String DAMAGE_COUNT = "damageCount";
	public static final String LP_PRODMEMO = "lpProdmemo";
	public static final String LP_PCODE1 = "lpPcode1";
	public static final String LP_PCODE2 = "lpPcode2";
	public static final String LP_PCODE3 = "lpPcode3";
	public static final String LP_PCODE4 = "lpPcode4";
	public static final String LP_REMARK = "lpRemark";
	public static final String MP_PRODMEMO = "mpProdmemo";
	public static final String MP_PRINT_CODE = "mpPrintCode";
	public static final String MP_STAMP_SET_FG = "mpStampSetFg";
	public static final String MP_ZIP_FG = "mpZipFg";
	public static final String MP_MP1_IFLAG = "mpMp1Iflag";
	public static final String MP_MP2_IFLAG = "mpMp2Iflag";
	public static final String MP_MP3_IFLAG = "mpMp3Iflag";
	public static final String MP_MP4_IFLAG = "mpMp4Iflag";
	public static final String MP_MP1_WORD = "mpMp1Word";
	public static final String MP_MP2_WORD = "mpMp2Word";
	public static final String MP_MP3_WORD = "mpMp3Word";
	public static final String MP_MP4_WORD = "mpMp4Word";
	public static final String MP_REMARK = "mpRemark";
	public static final String PS_PRODMEMO = "psProdmemo";
	public static final String PS_REMARK = "psRemark";
	public static final String PS_ZIP_FG = "psZipFg";
	public static final String LG_PROG_NM = "lgProgNm";
	public static final String LG_MAIL_TO_AREA_DISPLAY = "lgMailToAreaDisplay";
	public static final String LG_PLIST = "lgPList";
	public static final String LC_PROG_NM = "lcProgNm";
	public static final String LC_PLIST = "lcPList";
	public static final String LC_TEMPLATE = "lcTemplate";
	public static final String RET_RETURN_ADDRESS = "retReturnAddress";
	public static final String RET_USER_COMPANY = "retUserCompany";
	public static final String RET_USER_NAME = "retUserName";
	public static final String RET_USER_TEL = "retUserTel";
	public static final String LOG_FILE_SEQ = "logFileSeq";
	public static final String CUSTOMER_DEPT = "customerDept";
	public static final String PAGES2 = "pages2";
	public static final String PAGES3 = "pages3";
	public static final String _VPAGES2 = "VPages2";
	public static final String _VPAGES3 = "VPages3";
	public static final String P1ACCOUNTS = "p1accounts";
	public static final String P2ACCOUNTS = "p2accounts";
	public static final String P3ACCOUNTS = "p3accounts";
	public static final String P4ACCOUNTS = "p4accounts";
	public static final String P5ACCOUNTS = "p5accounts";
	public static final String P6ACCOUNTS = "p6accounts";
	public static final String PXACCOUNTS = "pxaccounts";
	public static final String CREATE_BY_CYCLE = "createByCycle";
	public static final String MRDF = "mrdf";
	public static final String FEEDER6 = "feeder6";
	public static final String FEEDER7 = "feeder7";

	public void save(JobBag transientInstance) {
		log.debug("saving JobBag instance");
		try {
			
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch(NonUniqueObjectException e){
			getSession().merge(transientInstance);
		}catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(JobBag persistentInstance) {
		log.debug("deleting JobBag instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public JobBag findById(java.lang.String id) {
		log.debug("getting JobBag instance with id: " + id);
		try {			
			JobBag jobBag = (JobBag)getSession().get(
					"com.salmat.jbm.hibernate.JobBag", id);
			if(jobBag == null){
			   String queryStr = "select {j.*} "
    		      + " from job_bag j"
    		      + " where j.JOB_BAG_NO = ?";
			   getSession().clear();
			   SQLQuery sqlQuery = (SQLQuery) getSession().createSQLQuery(queryStr);
		       sqlQuery.addEntity("j", JobBag.class);
		       sqlQuery.setParameter(0, id);
		       List<JobBag> retList = sqlQuery.list();
			   if(retList != null && retList.size() == 1)
				   return retList.get(0);
			   else 
				   return null;
			}else{
				return jobBag;
			}
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(JobBag instance) {
		log.debug("finding JobBag instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.JobBag")
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
		log.debug("finding JobBag instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from JobBag as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByJobCodeId(Object jobCodeId) {
		return findByProperty(JOB_CODE_ID, jobCodeId);
	}

	public List findByProgNm(Object progNm) {
		return findByProperty(PROG_NM, progNm);
	}

	public List findByIsLp(Object isLp) {
		return findByProperty(IS_LP, isLp);
	}

	public List findByIsMps(Object isMps) {
		return findByProperty(IS_MPS, isMps);
	}

	public List findByIsLg(Object isLg) {
		return findByProperty(IS_LG, isLg);
	}

	public List findByIsEdd(Object isEdd) {
		return findByProperty(IS_EDD, isEdd);
	}

	public List findByMpDmPs(Object mpDmPs) {
		return findByProperty(MP_DM_PS, mpDmPs);
	}

	public List findByDispatchTime(Object dispatchTime) {
		return findByProperty(DISPATCH_TIME, dispatchTime);
	}

	public List findByDeadTime(Object deadTime) {
		return findByProperty(DEAD_TIME, deadTime);
	}

	public List findByIsEnabledContract(Object isEnabledContract) {
		return findByProperty(IS_ENABLED_CONTRACT, isEnabledContract);
	}

	public List findByIsConvertResult(Object isConvertResult) {
		return findByProperty(IS_CONVERT_RESULT, isConvertResult);
	}

	public List findByLpNote(Object lpNote) {
		return findByProperty(LP_NOTE, lpNote);
	}

	public List findByMpNote(Object mpNote) {
		return findByProperty(MP_NOTE, mpNote);
	}

	public List findByPsNote(Object psNote) {
		return findByProperty(PS_NOTE, psNote);
	}

	public List findByDmNote(Object dmNote) {
		return findByProperty(DM_NOTE, dmNote);
	}

	public List findByDispatchType(Object dispatchType) {
		return findByProperty(DISPATCH_TYPE, dispatchType);
	}

	public List findByLgNote(Object lgNote) {
		return findByProperty(LG_NOTE, lgNote);
	}

	public List findByFilename(Object filename) {
		return findByProperty(FILENAME, filename);
	}

	public List findByUseLg(Object useLg) {
		return findByProperty(USE_LG, useLg);
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

	public List findBySpliteCount(Object spliteCount) {
		return findByProperty(SPLITE_COUNT, spliteCount);
	}

	public List findByJobBagStatus(Object jobBagStatus) {
		return findByProperty(JOB_BAG_STATUS, jobBagStatus);
	}

	public List findByAmxName(Object amxName) {
		return findByProperty(AMX_NAME, amxName);
	}

	public List findByAfpName(Object afpName) {
		return findByProperty(AFP_NAME, afpName);
	}

	public List findByIsDamage(Object isDamage) {
		return findByProperty(IS_DAMAGE, isDamage);
	}

	public List findByHasDamage(Object hasDamage) {
		return findByProperty(HAS_DAMAGE, hasDamage);
	}

	public List findByIsDeleted(Object isDeleted) {
		return findByProperty(IS_DELETED, isDeleted);
	}

	public List findByDeletedReason(Object deletedReason) {
		return findByProperty(DELETED_REASON, deletedReason);
	}

	public List findByPostsStatementNo(Object postsStatementNo) {
		return findByProperty(POSTS_STATEMENT_NO, postsStatementNo);
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

	public List findByTray5(Object tray5) {
		return findByProperty(TRAY5, tray5);
	}

	public List findByLogFilename(Object logFilename) {
		return findByProperty(LOG_FILENAME, logFilename);
	}

	public List findByDamageCount(Object damageCount) {
		return findByProperty(DAMAGE_COUNT, damageCount);
	}

	public List findByLpProdmemo(Object lpProdmemo) {
		return findByProperty(LP_PRODMEMO, lpProdmemo);
	}

	public List findByLpPcode1(Object lpPcode1) {
		return findByProperty(LP_PCODE1, lpPcode1);
	}

	public List findByLpPcode2(Object lpPcode2) {
		return findByProperty(LP_PCODE2, lpPcode2);
	}

	public List findByLpPcode3(Object lpPcode3) {
		return findByProperty(LP_PCODE3, lpPcode3);
	}

	public List findByLpPcode4(Object lpPcode4) {
		return findByProperty(LP_PCODE4, lpPcode4);
	}

	public List findByLpRemark(Object lpRemark) {
		return findByProperty(LP_REMARK, lpRemark);
	}

	public List findByMpProdmemo(Object mpProdmemo) {
		return findByProperty(MP_PRODMEMO, mpProdmemo);
	}

	public List findByMpPrintCode(Object mpPrintCode) {
		return findByProperty(MP_PRINT_CODE, mpPrintCode);
	}

	public List findByMpStampSetFg(Object mpStampSetFg) {
		return findByProperty(MP_STAMP_SET_FG, mpStampSetFg);
	}

	public List findByMpZipFg(Object mpZipFg) {
		return findByProperty(MP_ZIP_FG, mpZipFg);
	}

	public List findByMpMp1Iflag(Object mpMp1Iflag) {
		return findByProperty(MP_MP1_IFLAG, mpMp1Iflag);
	}

	public List findByMpMp2Iflag(Object mpMp2Iflag) {
		return findByProperty(MP_MP2_IFLAG, mpMp2Iflag);
	}

	public List findByMpMp3Iflag(Object mpMp3Iflag) {
		return findByProperty(MP_MP3_IFLAG, mpMp3Iflag);
	}

	public List findByMpMp4Iflag(Object mpMp4Iflag) {
		return findByProperty(MP_MP4_IFLAG, mpMp4Iflag);
	}

	public List findByMpMp1Word(Object mpMp1Word) {
		return findByProperty(MP_MP1_WORD, mpMp1Word);
	}

	public List findByMpMp2Word(Object mpMp2Word) {
		return findByProperty(MP_MP2_WORD, mpMp2Word);
	}

	public List findByMpMp3Word(Object mpMp3Word) {
		return findByProperty(MP_MP3_WORD, mpMp3Word);
	}

	public List findByMpMp4Word(Object mpMp4Word) {
		return findByProperty(MP_MP4_WORD, mpMp4Word);
	}

	public List findByMpRemark(Object mpRemark) {
		return findByProperty(MP_REMARK, mpRemark);
	}

	public List findByPsProdmemo(Object psProdmemo) {
		return findByProperty(PS_PRODMEMO, psProdmemo);
	}

	public List findByPsRemark(Object psRemark) {
		return findByProperty(PS_REMARK, psRemark);
	}

	public List findByPsZipFg(Object psZipFg) {
		return findByProperty(PS_ZIP_FG, psZipFg);
	}

	public List findByLgProgNm(Object lgProgNm) {
		return findByProperty(LG_PROG_NM, lgProgNm);
	}

	public List findByLgMailToAreaDisplay(Object lgMailToAreaDisplay) {
		return findByProperty(LG_MAIL_TO_AREA_DISPLAY, lgMailToAreaDisplay);
	}

	public List findByLgPList(Object lgPList) {
		return findByProperty(LG_PLIST, lgPList);
	}

	public List findByLcProgNm(Object lcProgNm) {
		return findByProperty(LC_PROG_NM, lcProgNm);
	}

	public List findByLcPList(Object lcPList) {
		return findByProperty(LC_PLIST, lcPList);
	}

	public List findByLcTemplate(Object lcTemplate) {
		return findByProperty(LC_TEMPLATE, lcTemplate);
	}

	public List findByRetReturnAddress(Object retReturnAddress) {
		return findByProperty(RET_RETURN_ADDRESS, retReturnAddress);
	}

	public List findByRetUserCompany(Object retUserCompany) {
		return findByProperty(RET_USER_COMPANY, retUserCompany);
	}

	public List findByRetUserName(Object retUserName) {
		return findByProperty(RET_USER_NAME, retUserName);
	}

	public List findByRetUserTel(Object retUserTel) {
		return findByProperty(RET_USER_TEL, retUserTel);
	}

	public List findByLogFileSeq(Object logFileSeq) {
		return findByProperty(LOG_FILE_SEQ, logFileSeq);
	}

	public List findByCustomerDept(Object customerDept) {
		return findByProperty(CUSTOMER_DEPT, customerDept);
	}

	public List findByPages2(Object pages2) {
		return findByProperty(PAGES2, pages2);
	}

	public List findByPages3(Object pages3) {
		return findByProperty(PAGES3, pages3);
	}

	public List findByVPages2(Object VPages2) {
		return findByProperty(_VPAGES2, VPages2);
	}

	public List findByVPages3(Object VPages3) {
		return findByProperty(_VPAGES3, VPages3);
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

	public List findByCreateByCycle(Object createByCycle) {
		return findByProperty(CREATE_BY_CYCLE, createByCycle);
	}

	public List findByMrdf(Object mrdf) {
		return findByProperty(MRDF, mrdf);
	}

	public List findByFeeder6(Object feeder6) {
		return findByProperty(FEEDER6, feeder6);
	}

	public List findByFeeder7(Object feeder7) {
		return findByProperty(FEEDER7, feeder7);
	}

	public List findAll() {
		log.debug("finding all JobBag instances");
		try {
			String queryString = "from JobBag";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public JobBag merge(JobBag detachedInstance) {
		log.debug("merging JobBag instance");
		try {
			JobBag result = (JobBag) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(JobBag instance) {
		log.debug("attaching dirty JobBag instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(JobBag instance) {
		log.debug("attaching clean JobBag instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}