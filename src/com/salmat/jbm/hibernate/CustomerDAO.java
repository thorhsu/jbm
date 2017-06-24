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
 * Customer entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Customer
 * @author MyEclipse Persistence Tools
 */

public class CustomerDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerDAO.class);
	// property constants
	public static final String AREA = "area";
	public static final String EASY_NAME = "easyName";
	public static final String CUST_NAME = "custName";
	public static final String ADDRESS1 = "address1";
	public static final String ADDRESS2 = "address2";
	public static final String ADDRESS3 = "address3";
	public static final String _PNO = "PNo";
	public static final String TEL1 = "tel1";
	public static final String TEL2 = "tel2";
	public static final String FAX = "fax";
	public static final String CONTACE_P = "contaceP";
	public static final String EXT = "ext";
	public static final String _BBCALL = "BBCall";
	public static final String MOBILE = "mobile";
	public static final String SEX = "sex";
	public static final String CONTACT_DE = "contactDe";
	public static final String TITLE = "title";
	public static final String DISCOUNT = "discount";
	public static final String PAYMENT = "payment";
	public static final String RP_BY = "rpBy";
	public static final String CLASS_ = "class_";
	public static final String DEADLINE = "deadline";
	public static final String EP1NO = "ep1no";
	public static final String PATH_CUST_NAME_IMAGE = "pathCustNameImage";
	public static final String PATH_CUST_STAMP_IMAGE = "pathCustStampImage";
	public static final String BANK_NAME = "bankName";
	public static final String BANK_ACCOUNT = "bankAccount";

	public void save(Customer transientInstance) {
		log.debug("saving Customer instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Customer persistentInstance) {
		log.debug("deleting Customer instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Customer findById(java.lang.String id) {
		log.debug("getting Customer instance with id: " + id);
		try {
			Customer instance = (Customer) getSession().get(
					"com.salmat.jbm.hibernate.Customer", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Customer instance) {
		log.debug("finding Customer instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Customer")
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
		log.debug("finding Customer instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Customer as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByArea(Object area) {
		return findByProperty(AREA, area);
	}

	public List findByEasyName(Object easyName) {
		return findByProperty(EASY_NAME, easyName);
	}

	public List findByCustName(Object custName) {
		return findByProperty(CUST_NAME, custName);
	}

	public List findByAddress1(Object address1) {
		return findByProperty(ADDRESS1, address1);
	}

	public List findByAddress2(Object address2) {
		return findByProperty(ADDRESS2, address2);
	}

	public List findByAddress3(Object address3) {
		return findByProperty(ADDRESS3, address3);
	}

	public List findByPNo(Object PNo) {
		return findByProperty(_PNO, PNo);
	}

	public List findByTel1(Object tel1) {
		return findByProperty(TEL1, tel1);
	}

	public List findByTel2(Object tel2) {
		return findByProperty(TEL2, tel2);
	}

	public List findByFax(Object fax) {
		return findByProperty(FAX, fax);
	}

	public List findByContaceP(Object contaceP) {
		return findByProperty(CONTACE_P, contaceP);
	}

	public List findByExt(Object ext) {
		return findByProperty(EXT, ext);
	}

	public List findByBBCall(Object BBCall) {
		return findByProperty(_BBCALL, BBCall);
	}

	public List findByMobile(Object mobile) {
		return findByProperty(MOBILE, mobile);
	}

	public List findBySex(Object sex) {
		return findByProperty(SEX, sex);
	}

	public List findByContactDe(Object contactDe) {
		return findByProperty(CONTACT_DE, contactDe);
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByDiscount(Object discount) {
		return findByProperty(DISCOUNT, discount);
	}

	public List findByPayment(Object payment) {
		return findByProperty(PAYMENT, payment);
	}

	public List findByRpBy(Object rpBy) {
		return findByProperty(RP_BY, rpBy);
	}

	public List findByClass_(Object class_) {
		return findByProperty(CLASS_, class_);
	}

	public List findByDeadline(Object deadline) {
		return findByProperty(DEADLINE, deadline);
	}

	public List findByEp1no(Object ep1no) {
		return findByProperty(EP1NO, ep1no);
	}

	public List findByPathCustNameImage(Object pathCustNameImage) {
		return findByProperty(PATH_CUST_NAME_IMAGE, pathCustNameImage);
	}

	public List findByPathCustStampImage(Object pathCustStampImage) {
		return findByProperty(PATH_CUST_STAMP_IMAGE, pathCustStampImage);
	}

	public List findByBankName(Object bankName) {
		return findByProperty(BANK_NAME, bankName);
	}

	public List findByBankAccount(Object bankAccount) {
		return findByProperty(BANK_ACCOUNT, bankAccount);
	}

	public List findAll() {
		log.debug("finding all Customer instances");
		try {
			String queryString = "from Customer";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Customer merge(Customer detachedInstance) {
		log.debug("merging Customer instance");
		try {
			Customer result = (Customer) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Customer instance) {
		log.debug("attaching dirty Customer instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Customer instance) {
		log.debug("attaching clean Customer instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}