package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.*;
import com.painter.util.EnCryptUtils;
import com.painter.util.Global;
import com.painter.util.MailUtil;
import com.painter.util.Util;


import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.String;
import java.net.URLEncoder;


public class AcctJobCodeChargeItemService extends AcctJobCodeChargeItemDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AcctJobCodeChargeItemService.class);
    
    /* Constants */
    private static final AcctJobCodeChargeItemService instance = new AcctJobCodeChargeItemService();
    //private static CustomerDAO CustomerDao;

    
    private AcctJobCodeChargeItemService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AcctJobCodeChargeItemService getInstance() {
        return instance;
    }


    
    /**
     * 取得工單 acct_job_code_charge_item list
     * input: String jobCodeNoList
     * return AcctCustomerContract list
     */
	public List findByJobCodeNo(String jobCodeNoList) {
		try {
	    	String queryString = " select {a.*}  "
	    		+ " from acct_job_code_charge_item a "
	    		+ " where a.idf_JOB_CODE_NO in ("+jobCodeNoList+") " ;
	    	
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", AcctJobCodeChargeItem.class);


			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}
	
	
	/**
     * 取得此debit note的收費項目
     * input: acctNo
     * return AcctJobCodeChargeItem list
     */
	public List<AcctJobCodeChargeItem> findChargeItemsByAcctDno(AcctDno acctDno) {
		try {
			//select a.* from acct_job_code_charge_item a, acct_section_job_code_mapping a2 where a.idf_cust_no = a2.idf_CUST_NO and a.idf_JOB_CODE_NO = a2.idf_JOB_CODE_NO and a2.section = 2 and a2.idf_CUST_NO = 'CT'
	    	String queryString = " select {a.*}  from acct_job_code_charge_item a, acct_section_job_code_mapping a2 "
	    		+ " where a.idf_cust_no = a2.idf_CUST_NO and a.idf_JOB_CODE_NO = a2.idf_JOB_CODE_NO "
	    		+ " and a2.idf_CUST_NO = ? and a2.section = ? " ;
	    	
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", AcctJobCodeChargeItem.class);
		    sqlQuery.setParameter(0, acctDno.getCustomer().getCustNo());
		    sqlQuery.setParameter(1, acctDno.getSection());


			List<AcctJobCodeChargeItem> retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}
	
    /**
     * 根據工單, 刪除  acct_job_code_charge_item 
     * input: jobCodeNoList 
     * return Boolean
     */
	public Boolean deleteByJobCodeNo(String jobCodeNoList) {
		try {
	    	String queryString = " delete   "
	    		+ " from acct_job_code_charge_item  "
	    		+ " where idf_JOB_CODE_NO in ("+jobCodeNoList+") " ;
	    	
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
	    	int ret = sqlQuery.executeUpdate();

			return true;

		} catch (RuntimeException re) {
			log.error("", re);
			return false;
		}		

	}	

}
