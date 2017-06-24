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
 * Mpinfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Mpinfo
 * @author MyEclipse Persistence Tools
 */

public class MpinfoDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(MpinfoDAO.class);
	// property constants
	public static final String SIZE = "size";
	public static final String STAMP_SET = "stampSet";
	public static final String STAMP_SET_FG = "stampSetFg";
	public static final String SELECT_FG = "selectFg";
	public static final String ZIP_FG = "zipFg";
	public static final String REMARK = "remark";
	public static final String MP_PRICE = "mpPrice";
	public static final String PRINT_CODE = "printCode";
	public static final String INSERT_CNT = "insertCnt";
	public static final String MP1_WORD = "mp1Word";
	public static final String MP2_WORD = "mp2Word";
	public static final String MP3_WORD = "mp3Word";
	public static final String MP4_WORD = "mp4Word";
	public static final String PRODMEMO = "prodmemo";
	public static final String MP1_IFLAG = "mp1Iflag";
	public static final String MP2_IFLAG = "mp2Iflag";
	public static final String MP3_IFLAG = "mp3Iflag";
	public static final String MP4_IFLAG = "mp4Iflag";
	public static final String PRINT_CODE_IMAGE = "printCodeImage";
	public static final String MP1_IMAGE = "mp1Image";
	public static final String MP2_IMAGE = "mp2Image";
	public static final String MP3_IMAGE = "mp3Image";
	public static final String MP4_IMAGE = "mp4Image";
	public static final String NEXT_EFFECTIVE_DATE = "nextEffectiveDate";
	public static final String NEXT_PRODMEMO = "nextProdmemo";
	public static final String NEXT_PRINT_CODE = "nextPrintCode";
	public static final String NEXT_PRINT_CODE_IMAGE = "nextPrintCodeImage";
	public static final String NEXT_STAMP_SET_FG = "nextStampSetFg";
	public static final String NEXT_ZIP_FG = "nextZipFg";
	public static final String NEXT_SELECT_FG = "nextSelectFg";
	public static final String NEXT_REMARK = "nextRemark";
	public static final String NEXT_MP1_WORD = "nextMp1Word";
	public static final String NEXT_MP2_WORD = "nextMp2Word";
	public static final String NEXT_MP3_WORD = "nextMp3Word";
	public static final String NEXT_MP4_WORD = "nextMp4Word";
	public static final String NEXT_MP1_IFLAG = "nextMp1Iflag";
	public static final String NEXT_MP2_IFLAG = "nextMp2Iflag";
	public static final String NEXT_MP3_IFLAG = "nextMp3Iflag";
	public static final String NEXT_MP4_IFLAG = "nextMp4Iflag";
	public static final String NEXT_MP1_IMAGE = "nextMp1Image";
	public static final String NEXT_MP2_IMAGE = "nextMp2Image";
	public static final String NEXT_MP3_IMAGE = "nextMp3Image";
	public static final String NEXT_MP4_IMAGE = "nextMp4Image";

	public void save(Mpinfo transientInstance) {
		log.debug("saving Mpinfo instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Mpinfo persistentInstance) {
		log.debug("deleting Mpinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Mpinfo findById(java.lang.String id) {
		log.debug("getting Mpinfo instance with id: " + id);
		try {
			Mpinfo instance = (Mpinfo) getSession().get(
					"com.salmat.jbm.hibernate.Mpinfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Mpinfo instance) {
		log.debug("finding Mpinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Mpinfo")
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
		log.debug("finding Mpinfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Mpinfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySize(Object size) {
		return findByProperty(SIZE, size);
	}

	public List findByStampSet(Object stampSet) {
		return findByProperty(STAMP_SET, stampSet);
	}

	public List findByStampSetFg(Object stampSetFg) {
		return findByProperty(STAMP_SET_FG, stampSetFg);
	}

	public List findBySelectFg(Object selectFg) {
		return findByProperty(SELECT_FG, selectFg);
	}

	public List findByZipFg(Object zipFg) {
		return findByProperty(ZIP_FG, zipFg);
	}

	public List findByRemark(Object remark) {
		return findByProperty(REMARK, remark);
	}

	public List findByMpPrice(Object mpPrice) {
		return findByProperty(MP_PRICE, mpPrice);
	}

	public List findByPrintCode(Object printCode) {
		return findByProperty(PRINT_CODE, printCode);
	}

	public List findByInsertCnt(Object insertCnt) {
		return findByProperty(INSERT_CNT, insertCnt);
	}

	public List findByMp1Word(Object mp1Word) {
		return findByProperty(MP1_WORD, mp1Word);
	}

	public List findByMp2Word(Object mp2Word) {
		return findByProperty(MP2_WORD, mp2Word);
	}

	public List findByMp3Word(Object mp3Word) {
		return findByProperty(MP3_WORD, mp3Word);
	}

	public List findByMp4Word(Object mp4Word) {
		return findByProperty(MP4_WORD, mp4Word);
	}

	public List findByProdmemo(Object prodmemo) {
		return findByProperty(PRODMEMO, prodmemo);
	}

	public List findByMp1Iflag(Object mp1Iflag) {
		return findByProperty(MP1_IFLAG, mp1Iflag);
	}

	public List findByMp2Iflag(Object mp2Iflag) {
		return findByProperty(MP2_IFLAG, mp2Iflag);
	}

	public List findByMp3Iflag(Object mp3Iflag) {
		return findByProperty(MP3_IFLAG, mp3Iflag);
	}

	public List findByMp4Iflag(Object mp4Iflag) {
		return findByProperty(MP4_IFLAG, mp4Iflag);
	}

	public List findByPrintCodeImage(Object printCodeImage) {
		return findByProperty(PRINT_CODE_IMAGE, printCodeImage);
	}

	public List findByMp1Image(Object mp1Image) {
		return findByProperty(MP1_IMAGE, mp1Image);
	}

	public List findByMp2Image(Object mp2Image) {
		return findByProperty(MP2_IMAGE, mp2Image);
	}

	public List findByMp3Image(Object mp3Image) {
		return findByProperty(MP3_IMAGE, mp3Image);
	}

	public List findByMp4Image(Object mp4Image) {
		return findByProperty(MP4_IMAGE, mp4Image);
	}

	public List findByNextEffectiveDate(Object nextEffectiveDate) {
		return findByProperty(NEXT_EFFECTIVE_DATE, nextEffectiveDate);
	}

	public List findByNextProdmemo(Object nextProdmemo) {
		return findByProperty(NEXT_PRODMEMO, nextProdmemo);
	}

	public List findByNextPrintCode(Object nextPrintCode) {
		return findByProperty(NEXT_PRINT_CODE, nextPrintCode);
	}

	public List findByNextPrintCodeImage(Object nextPrintCodeImage) {
		return findByProperty(NEXT_PRINT_CODE_IMAGE, nextPrintCodeImage);
	}

	public List findByNextStampSetFg(Object nextStampSetFg) {
		return findByProperty(NEXT_STAMP_SET_FG, nextStampSetFg);
	}

	public List findByNextZipFg(Object nextZipFg) {
		return findByProperty(NEXT_ZIP_FG, nextZipFg);
	}

	public List findByNextSelectFg(Object nextSelectFg) {
		return findByProperty(NEXT_SELECT_FG, nextSelectFg);
	}

	public List findByNextRemark(Object nextRemark) {
		return findByProperty(NEXT_REMARK, nextRemark);
	}

	public List findByNextMp1Word(Object nextMp1Word) {
		return findByProperty(NEXT_MP1_WORD, nextMp1Word);
	}

	public List findByNextMp2Word(Object nextMp2Word) {
		return findByProperty(NEXT_MP2_WORD, nextMp2Word);
	}

	public List findByNextMp3Word(Object nextMp3Word) {
		return findByProperty(NEXT_MP3_WORD, nextMp3Word);
	}

	public List findByNextMp4Word(Object nextMp4Word) {
		return findByProperty(NEXT_MP4_WORD, nextMp4Word);
	}

	public List findByNextMp1Iflag(Object nextMp1Iflag) {
		return findByProperty(NEXT_MP1_IFLAG, nextMp1Iflag);
	}

	public List findByNextMp2Iflag(Object nextMp2Iflag) {
		return findByProperty(NEXT_MP2_IFLAG, nextMp2Iflag);
	}

	public List findByNextMp3Iflag(Object nextMp3Iflag) {
		return findByProperty(NEXT_MP3_IFLAG, nextMp3Iflag);
	}

	public List findByNextMp4Iflag(Object nextMp4Iflag) {
		return findByProperty(NEXT_MP4_IFLAG, nextMp4Iflag);
	}

	public List findByNextMp1Image(Object nextMp1Image) {
		return findByProperty(NEXT_MP1_IMAGE, nextMp1Image);
	}

	public List findByNextMp2Image(Object nextMp2Image) {
		return findByProperty(NEXT_MP2_IMAGE, nextMp2Image);
	}

	public List findByNextMp3Image(Object nextMp3Image) {
		return findByProperty(NEXT_MP3_IMAGE, nextMp3Image);
	}

	public List findByNextMp4Image(Object nextMp4Image) {
		return findByProperty(NEXT_MP4_IMAGE, nextMp4Image);
	}

	public List findAll() {
		log.debug("finding all Mpinfo instances");
		try {
			String queryString = "from Mpinfo";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Mpinfo merge(Mpinfo detachedInstance) {
		log.debug("merging Mpinfo instance");
		try {
			Mpinfo result = (Mpinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Mpinfo instance) {
		log.debug("attaching dirty Mpinfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Mpinfo instance) {
		log.debug("attaching clean Mpinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}