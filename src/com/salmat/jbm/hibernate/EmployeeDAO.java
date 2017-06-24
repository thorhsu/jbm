package com.salmat.jbm.hibernate;

import java.sql.Clob;
import java.util.Date;
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
 * Employee entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.salmat.jbm.hibernate.Employee
 * @author MyEclipse Persistence Tools
 */

public class EmployeeDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(EmployeeDAO.class);
	// property constants
	public static final String _ENAME = "EName";
	public static final String _CNAME = "CName";
	public static final String PID = "pid";
	public static final String DEPT = "dept";
	public static final String TITLE = "title";
	public static final String EXT = "ext";
	public static final String _HADDRESS = "HAddress";
	public static final String AREA_CODE = "areaCode";
	public static final String _HTEL = "HTel";
	public static final String BBCALL = "bbcall";
	public static final String MOBILE = "mobile";
	public static final String BIRTHDAY = "birthday";
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String ENABLED = "enabled";
	public static final String ROLE1S = "role1s";
	public static final String ROLE1C = "role1c";
	public static final String ROLE2S = "role2s";
	public static final String ROLE2C = "role2c";
	public static final String ROLE3S = "role3s";
	public static final String ROLE3C = "role3c";
	public static final String ROLE4S = "role4s";
	public static final String ROLE4C = "role4c";
	public static final String ROLE5S = "role5s";
	public static final String ROLE5C = "role5c";
	public static final String ROLE6M = "role6m";
	public static final String PASSWORD1 = "password1";
	public static final String PASSWORD2 = "password2";
	public static final String PASSWORD3 = "password3";
	public static final String PASSWORD4 = "password4";
	public static final String PASSWORD5 = "password5";
	public static final String LOGIN_ERROR_COUNT = "loginErrorCount";

	public void save(Employee transientInstance) {
		log.debug("saving Employee instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Employee persistentInstance) {
		log.debug("deleting Employee instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Employee findById(java.lang.String id) {
		log.debug("getting Employee instance with id: " + id);
		try {
			Employee instance = (Employee) getSession().get(
					"com.salmat.jbm.hibernate.Employee", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Employee instance) {
		log.debug("finding Employee instance by example");
		try {
			List results = getSession()
					.createCriteria("com.salmat.jbm.hibernate.Employee")
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
		log.debug("finding Employee instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Employee as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEName(Object EName) {
		return findByProperty(_ENAME, EName);
	}

	public List findByCName(Object CName) {
		return findByProperty(_CNAME, CName);
	}

	public List findByPid(Object pid) {
		return findByProperty(PID, pid);
	}

	public List findByDept(Object dept) {
		return findByProperty(DEPT, dept);
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByExt(Object ext) {
		return findByProperty(EXT, ext);
	}

	public List findByHAddress(Object HAddress) {
		return findByProperty(_HADDRESS, HAddress);
	}

	public List findByAreaCode(Object areaCode) {
		return findByProperty(AREA_CODE, areaCode);
	}

	public List findByHTel(Object HTel) {
		return findByProperty(_HTEL, HTel);
	}

	public List findByBbcall(Object bbcall) {
		return findByProperty(BBCALL, bbcall);
	}

	public List findByMobile(Object mobile) {
		return findByProperty(MOBILE, mobile);
	}

	public List findByBirthday(Object birthday) {
		return findByProperty(BIRTHDAY, birthday);
	}

	public List findByUserId(Object userId) {
		return findByProperty(USER_ID, userId);
	}

	public List findByUserName(Object userName) {
		return findByProperty(USER_NAME, userName);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByEnabled(Object enabled) {
		return findByProperty(ENABLED, enabled);
	}

	public List findByRole1s(Object role1s) {
		return findByProperty(ROLE1S, role1s);
	}

	public List findByRole1c(Object role1c) {
		return findByProperty(ROLE1C, role1c);
	}

	public List findByRole2s(Object role2s) {
		return findByProperty(ROLE2S, role2s);
	}

	public List findByRole2c(Object role2c) {
		return findByProperty(ROLE2C, role2c);
	}

	public List findByRole3s(Object role3s) {
		return findByProperty(ROLE3S, role3s);
	}

	public List findByRole3c(Object role3c) {
		return findByProperty(ROLE3C, role3c);
	}

	public List findByRole4s(Object role4s) {
		return findByProperty(ROLE4S, role4s);
	}

	public List findByRole4c(Object role4c) {
		return findByProperty(ROLE4C, role4c);
	}

	public List findByRole5s(Object role5s) {
		return findByProperty(ROLE5S, role5s);
	}

	public List findByRole5c(Object role5c) {
		return findByProperty(ROLE5C, role5c);
	}

	public List findByRole6m(Object role6m) {
		return findByProperty(ROLE6M, role6m);
	}

	public List findByPassword1(Object password1) {
		return findByProperty(PASSWORD1, password1);
	}

	public List findByPassword2(Object password2) {
		return findByProperty(PASSWORD2, password2);
	}

	public List findByPassword3(Object password3) {
		return findByProperty(PASSWORD3, password3);
	}

	public List findByPassword4(Object password4) {
		return findByProperty(PASSWORD4, password4);
	}

	public List findByPassword5(Object password5) {
		return findByProperty(PASSWORD5, password5);
	}

	public List findByLoginErrorCount(Object loginErrorCount) {
		return findByProperty(LOGIN_ERROR_COUNT, loginErrorCount);
	}

	public List findAll() {
		log.debug("finding all Employee instances");
		try {
			String queryString = "from Employee";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Employee merge(Employee detachedInstance) {
		log.debug("merging Employee instance");
		try {
			Employee result = (Employee) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Employee instance) {
		log.debug("attaching dirty Employee instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Employee instance) {
		log.debug("attaching clean Employee instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}