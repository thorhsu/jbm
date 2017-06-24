package com.salmat.jbm.hibernate;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * JobCode entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.JobCode
 * @author MyEclipse Persistence Tools
 */

public class JobCodeDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(JobCodeDAO.class);
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
	public static final String LP_BEGINDATE1 = "lpBegindate1";
	public static final String LP_ENDDATE1 = "lpEnddate1";
	public static final String LP_BEGINDATE2 = "lpBegindate2";
	public static final String LP_ENDDATE2 = "lpEnddate2";
	public static final String LP_BEGINDATE3 = "lpBegindate3";
	public static final String LP_ENDDATE3 = "lpEnddate3";
	public static final String LP_NOTE = "lpNote";
	public static final String MP_BEGINDATE1 = "mpBegindate1";
	public static final String MP_ENDDATE1 = "mpEnddate1";
	public static final String MP_BEGINDATE2 = "mpBegindate2";
	public static final String MP_ENDDATE2 = "mpEnddate2";
	public static final String MP_BEGINDATE3 = "mpBegindate3";
	public static final String MP_ENDDATE3 = "mpEnddate3";
	public static final String MP_NOTE = "mpNote";
	public static final String PS_NOTE = "psNote";
	public static final String DM_NOTE = "dmNote";
	public static final String DISPATCH_TYPE = "dispatchType";
	public static final String LG_NOTE = "lgNote";
	public static final String CREATE_BY_CYCLE = "createByCycle";
	public static final String CYCLE01 = "cycle01";
	public static final String CYCLE02 = "cycle02";
	public static final String CYCLE03 = "cycle03";
	public static final String CYCLE04 = "cycle04";
	public static final String CYCLE05 = "cycle05";
	public static final String CYCLE06 = "cycle06";
	public static final String CYCLE07 = "cycle07";
	public static final String CYCLE08 = "cycle08";
	public static final String CYCLE09 = "cycle09";
	public static final String CYCLE10 = "cycle10";
	public static final String CYCLE11 = "cycle11";
	public static final String CYCLE12 = "cycle12";
	public static final String CYCLE13 = "cycle13";
	public static final String CYCLE14 = "cycle14";
	public static final String CYCLE15 = "cycle15";
	public static final String CYCLE16 = "cycle16";
	public static final String CYCLE17 = "cycle17";
	public static final String CYCLE18 = "cycle18";
	public static final String CYCLE19 = "cycle19";
	public static final String CYCLE20 = "cycle20";
	public static final String CYCLE21 = "cycle21";
	public static final String CYCLE22 = "cycle22";
	public static final String CYCLE23 = "cycle23";
	public static final String CYCLE24 = "cycle24";
	public static final String CYCLE25 = "cycle25";
	public static final String CYCLE26 = "cycle26";
	public static final String CYCLE27 = "cycle27";
	public static final String CYCLE28 = "cycle28";
	public static final String CYCLE29 = "cycle29";
	public static final String CYCLE30 = "cycle30";
	public static final String CYCLE31 = "cycle31";
	public static final String FILENAME = "filename";
	public static final String USE_LG = "useLg";
	public static final String LOG_FILE_SEQ = "logFileSeq";
	public static final String CUSTOMER_DEPT = "customerDept";
	public static final String COST_CENTER = "costCenter";
	public static final String MINIMAL_CHARGE_PRICE = "minimalChargePrice";
	public static final String SPLIT_JOB_CODE_NO1 = "splitJobCodeNo1";
	public static final String SPLIT_JOB_CODE_NO2 = "splitJobCodeNo2";
	public static final String SPLIT_JOB_CODE_NO3 = "splitJobCodeNo3";
	public static final String SPLIT_JOB_CODE_NO4 = "splitJobCodeNo4";
	public static final String SPLIT_JOB_CODE_PERCENT1 = "splitJobCodePercent1";
	public static final String SPLIT_JOB_CODE_PERCENT2 = "splitJobCodePercent2";
	public static final String SPLIT_JOB_CODE_PERCENT3 = "splitJobCodePercent3";
	public static final String SPLIT_JOB_CODE_PERCENT4 = "splitJobCodePercent4";
	public static final String MAIN_JOB_CODE_NO = "mainJobCodeNo";
	public static final String MAIN_JOB_CODE_PROG_NM = "mainJobCodeProgNm";

	public void save(JobCode transientInstance) {
		log.debug("saving JobCode instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(JobCode persistentInstance) {
		log.debug("deleting JobCode instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public JobCode findById(java.lang.String id) {
		log.debug("getting JobCode instance with id: " + id);
		try {
			JobCode instance = (JobCode) getSession().get(
					"com.salmat.jbm.hibernate.JobCode", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(JobCode instance) {
		log.debug("finding JobCode instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.JobCode")
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
		log.debug("finding JobCode instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from JobCode as model where model."
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

	public List findByLpBegindate1(Object lpBegindate1) {
		return findByProperty(LP_BEGINDATE1, lpBegindate1);
	}

	public List findByLpEnddate1(Object lpEnddate1) {
		return findByProperty(LP_ENDDATE1, lpEnddate1);
	}

	public List findByLpBegindate2(Object lpBegindate2) {
		return findByProperty(LP_BEGINDATE2, lpBegindate2);
	}

	public List findByLpEnddate2(Object lpEnddate2) {
		return findByProperty(LP_ENDDATE2, lpEnddate2);
	}

	public List findByLpBegindate3(Object lpBegindate3) {
		return findByProperty(LP_BEGINDATE3, lpBegindate3);
	}

	public List findByLpEnddate3(Object lpEnddate3) {
		return findByProperty(LP_ENDDATE3, lpEnddate3);
	}

	public List findByLpNote(Object lpNote) {
		return findByProperty(LP_NOTE, lpNote);
	}

	public List findByMpBegindate1(Object mpBegindate1) {
		return findByProperty(MP_BEGINDATE1, mpBegindate1);
	}

	public List findByMpEnddate1(Object mpEnddate1) {
		return findByProperty(MP_ENDDATE1, mpEnddate1);
	}

	public List findByMpBegindate2(Object mpBegindate2) {
		return findByProperty(MP_BEGINDATE2, mpBegindate2);
	}

	public List findByMpEnddate2(Object mpEnddate2) {
		return findByProperty(MP_ENDDATE2, mpEnddate2);
	}

	public List findByMpBegindate3(Object mpBegindate3) {
		return findByProperty(MP_BEGINDATE3, mpBegindate3);
	}

	public List findByMpEnddate3(Object mpEnddate3) {
		return findByProperty(MP_ENDDATE3, mpEnddate3);
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

	public List findByCreateByCycle(Object createByCycle) {
		return findByProperty(CREATE_BY_CYCLE, createByCycle);
	}

	public List findByCycle01(Object cycle01) {
		return findByProperty(CYCLE01, cycle01);
	}

	public List findByCycle02(Object cycle02) {
		return findByProperty(CYCLE02, cycle02);
	}

	public List findByCycle03(Object cycle03) {
		return findByProperty(CYCLE03, cycle03);
	}

	public List findByCycle04(Object cycle04) {
		return findByProperty(CYCLE04, cycle04);
	}

	public List findByCycle05(Object cycle05) {
		return findByProperty(CYCLE05, cycle05);
	}

	public List findByCycle06(Object cycle06) {
		return findByProperty(CYCLE06, cycle06);
	}

	public List findByCycle07(Object cycle07) {
		return findByProperty(CYCLE07, cycle07);
	}

	public List findByCycle08(Object cycle08) {
		return findByProperty(CYCLE08, cycle08);
	}

	public List findByCycle09(Object cycle09) {
		return findByProperty(CYCLE09, cycle09);
	}

	public List findByCycle10(Object cycle10) {
		return findByProperty(CYCLE10, cycle10);
	}

	public List findByCycle11(Object cycle11) {
		return findByProperty(CYCLE11, cycle11);
	}

	public List findByCycle12(Object cycle12) {
		return findByProperty(CYCLE12, cycle12);
	}

	public List findByCycle13(Object cycle13) {
		return findByProperty(CYCLE13, cycle13);
	}

	public List findByCycle14(Object cycle14) {
		return findByProperty(CYCLE14, cycle14);
	}

	public List findByCycle15(Object cycle15) {
		return findByProperty(CYCLE15, cycle15);
	}

	public List findByCycle16(Object cycle16) {
		return findByProperty(CYCLE16, cycle16);
	}

	public List findByCycle17(Object cycle17) {
		return findByProperty(CYCLE17, cycle17);
	}

	public List findByCycle18(Object cycle18) {
		return findByProperty(CYCLE18, cycle18);
	}

	public List findByCycle19(Object cycle19) {
		return findByProperty(CYCLE19, cycle19);
	}

	public List findByCycle20(Object cycle20) {
		return findByProperty(CYCLE20, cycle20);
	}

	public List findByCycle21(Object cycle21) {
		return findByProperty(CYCLE21, cycle21);
	}

	public List findByCycle22(Object cycle22) {
		return findByProperty(CYCLE22, cycle22);
	}

	public List findByCycle23(Object cycle23) {
		return findByProperty(CYCLE23, cycle23);
	}

	public List findByCycle24(Object cycle24) {
		return findByProperty(CYCLE24, cycle24);
	}

	public List findByCycle25(Object cycle25) {
		return findByProperty(CYCLE25, cycle25);
	}

	public List findByCycle26(Object cycle26) {
		return findByProperty(CYCLE26, cycle26);
	}

	public List findByCycle27(Object cycle27) {
		return findByProperty(CYCLE27, cycle27);
	}

	public List findByCycle28(Object cycle28) {
		return findByProperty(CYCLE28, cycle28);
	}

	public List findByCycle29(Object cycle29) {
		return findByProperty(CYCLE29, cycle29);
	}

	public List findByCycle30(Object cycle30) {
		return findByProperty(CYCLE30, cycle30);
	}

	public List findByCycle31(Object cycle31) {
		return findByProperty(CYCLE31, cycle31);
	}

	public List findByFilename(Object filename) {
		return findByProperty(FILENAME, filename);
	}

	public List findByUseLg(Object useLg) {
		return findByProperty(USE_LG, useLg);
	}

	public List findByLogFileSeq(Object logFileSeq) {
		return findByProperty(LOG_FILE_SEQ, logFileSeq);
	}

	public List findByCustomerDept(Object customerDept) {
		return findByProperty(CUSTOMER_DEPT, customerDept);
	}

	public List findByCostCenter(Object costCenter) {
		return findByProperty(COST_CENTER, costCenter);
	}

	public List findByMinimalChargePrice(Object minimalChargePrice) {
		return findByProperty(MINIMAL_CHARGE_PRICE, minimalChargePrice);
	}

	public List findBySplitJobCodeNo1(Object splitJobCodeNo1) {
		return findByProperty(SPLIT_JOB_CODE_NO1, splitJobCodeNo1);
	}

	public List findBySplitJobCodeNo2(Object splitJobCodeNo2) {
		return findByProperty(SPLIT_JOB_CODE_NO2, splitJobCodeNo2);
	}

	public List findBySplitJobCodeNo3(Object splitJobCodeNo3) {
		return findByProperty(SPLIT_JOB_CODE_NO3, splitJobCodeNo3);
	}

	public List findBySplitJobCodeNo4(Object splitJobCodeNo4) {
		return findByProperty(SPLIT_JOB_CODE_NO4, splitJobCodeNo4);
	}

	public List findBySplitJobCodePercent1(Object splitJobCodePercent1) {
		return findByProperty(SPLIT_JOB_CODE_PERCENT1, splitJobCodePercent1);
	}

	public List findBySplitJobCodePercent2(Object splitJobCodePercent2) {
		return findByProperty(SPLIT_JOB_CODE_PERCENT2, splitJobCodePercent2);
	}

	public List findBySplitJobCodePercent3(Object splitJobCodePercent3) {
		return findByProperty(SPLIT_JOB_CODE_PERCENT3, splitJobCodePercent3);
	}

	public List findBySplitJobCodePercent4(Object splitJobCodePercent4) {
		return findByProperty(SPLIT_JOB_CODE_PERCENT4, splitJobCodePercent4);
	}

	public List findByMainJobCodeNo(Object mainJobCodeNo) {
		return findByProperty(MAIN_JOB_CODE_NO, mainJobCodeNo);
	}

	public List findByMainJobCodeProgNm(Object mainJobCodeProgNm) {
		return findByProperty(MAIN_JOB_CODE_PROG_NM, mainJobCodeProgNm);
	}

	public List findAll() {
		log.debug("finding all JobCode instances");
		try {
			String queryString = "from JobCode";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public JobCode merge(JobCode detachedInstance) {
		log.debug("merging JobCode instance");
		try {
			JobCode result = (JobCode) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(JobCode instance) {
		log.debug("attaching dirty JobCode instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(JobCode instance) {
		log.debug("attaching clean JobCode instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}