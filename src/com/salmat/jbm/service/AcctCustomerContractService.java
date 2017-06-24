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


public class AcctCustomerContractService extends AcctCustomerContractDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AcctCustomerContractService.class);
    
    /* Constants */
    private static final AcctCustomerContractService instance = new AcctCustomerContractService();
    //private static CustomerDAO CustomerDao;

    
    private AcctCustomerContractService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AcctCustomerContractService getInstance() {
        return instance;
    }
    

    /**
     * 取得客戶的合約內容 AcctCustomerContract list
     * input: String custNo, String contractDateBegin
     * return AcctCustomerContract list
     */
	public List getAcctCustomerContractList(String custNo, String contractDateBegin) {
		// TODO Auto-generated method stub

		
		try {
	    	String queryString = " select {ac.*}  "
	    		+ " from acct_customer_contract ac "
	    		+ " where  ac.idf_cust_no =? and  convert(date, ?, 120)  between  convert(date, ac.contact_date_begin, 120)  and  convert(date, ac.contact_date_end, 120)	 "
	    		+ " order by ac.orderby ";
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("ac", AcctCustomerContract.class);
		    sqlQuery.setParameter(0, custNo);
		    sqlQuery.setParameter(1, contractDateBegin);


			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
	}
	
    /**
     * 取得客戶 合約內容 有 指定 debite Note, AcctCustomerContract list
     * input: AcctDno acctDno, String custNo, String contractDateBegin
     * return AcctCustomerContract list
     */
	public List getAcctCustomerContractListInDebiteNote(AcctDno acctDno, String custNo, String contractDateBegin) {
		// TODO Auto-generated method stub

		
		try {
	    	String queryString = " select {ac.*}  "
	    		+ " from acct_customer_contract ac "
	    		+ " where  ac.idf_cust_no =? and  convert(date, ?, 120)  between  convert(date, ac.contact_date_begin, 120)  and  convert(date, ac.contact_date_end, 120)	 "
	    		+ " and ac.idf_charge_item in  "
	    		+ " ( select distinct as1.idf_acct_charge_item "
	    		+ " from acct_sum1 as1 "
	    		+ " where as1.idf_debit_no=  ? ) "
	    		+ " order by ac.orderby ";
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("ac", AcctCustomerContract.class);
		    sqlQuery.setParameter(0, custNo);
		    sqlQuery.setParameter(1, contractDateBegin);
		    sqlQuery.setParameter(2, acctDno.getDebitNo());		    


			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
	}	
	
	public List getAcctCustomerContractListInDebiteNote(AcctDno acctDno, String custNo, String contractDateBegin, String job_bag_no) {
		// TODO Auto-generated method stub

		
		try {
	    	String queryString = " select {ac.*}  "
	    		+ " from acct_customer_contract ac "
	    		+ " where  ac.idf_cust_no =? and  convert(date, ?, 120)  between  convert(date, ac.contact_date_begin, 120)  and  convert(date, ac.contact_date_end, 120)	 "
	    		+ " and ac.idf_charge_item in  "
	    		+ " ( select distinct as1.idf_acct_charge_item "
	    		+ " from acct_sum1 as1 "
	    		+ " where as1.idf_debit_no=  ? and as1.IDF_JOB_BAG_NO = ?) "
	    		+ " order by ac.orderby ";
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("ac", AcctCustomerContract.class);
		    sqlQuery.setParameter(0, custNo);
		    sqlQuery.setParameter(1, contractDateBegin);
		    sqlQuery.setParameter(2, acctDno.getDebitNo());		    
		    sqlQuery.setParameter(3, job_bag_no);


			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
	}	

	//檢查合約日期是否重疊	
	//若 isExtendContrct = true, 表示 合約延展, 檢查重疊時, 需過濾過  contactDateBegin 相同的合約
	public boolean checkIsOverlapping(String custNo,String contactDateBegin, String contactDateEnd, Boolean isExtendContrct) {
		try {
	    	String queryString = " select {ac.*}  "
	    		+ " from acct_customer_contract ac "
	    		+ " where ac.idf_cust_no=? and  "
	    		+ " ( convert(date, ?, 120)  between  convert(date, ac.contact_date_begin, 120)  and  convert(date, ac.contact_date_end, 120)   " 
	    		+ " or " 
	    		+ " convert(date, ?, 120)  between  convert(date, ac.contact_date_begin, 120)  and  convert(date, ac.contact_date_end, 120)   "
	    		+ " ) " ;
	    	
	    	if (isExtendContrct)
	    		queryString = queryString + " and convert(date, ?, 120)  !=  convert(date, ac.contact_date_begin, 120) " ;
	    	
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("ac", AcctCustomerContract.class);
		    sqlQuery.setParameter(0, custNo);
		    sqlQuery.setParameter(1, contactDateBegin);
		    sqlQuery.setParameter(2, contactDateEnd);
	    	if (isExtendContrct)
	    		sqlQuery.setParameter(3, contactDateBegin);

			List retList = sqlQuery.list();
			
			if (null!= retList && retList.size() >1 )
				return true;
			else
				return false;

		} catch (RuntimeException re) {
			log.error("", re);
			return true;
		}		
	}
	
	//檢查合約日期是否在區間內
	public boolean checkIsInContract(String custNo,String forCheckDate) {
		try {
	    	String queryString = "select distinct convert(varchar(10), contact_date_begin, 120) contact_date_begin, " +
	    			"convert(varchar(10), contact_date_end, 120) contact_date_end from acct_customer_contract where idf_cust_no = ? " +
	    			" and ? between contact_date_begin and contact_date_end" ;
	    	
	    	
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.setParameter(0, custNo);
		    sqlQuery.setParameter(1, forCheckDate);
			List retList = sqlQuery.list();
			if (null!= retList && retList.size() >1 )
				return true;
			else
				return false;

		} catch (RuntimeException re) {
			log.error("", re);
			return true;
		}		
	}
	
	

	public Boolean deleteByCustomerContractDate(String custNo,	String contactDateBegin) {
		try {
	    	String queryString = " delete  "
	    		+ " from acct_customer_contract "
	    		+ " where  idf_cust_no =? and  convert(date, ?, 120)  =  convert(date, contact_date_begin, 120) ";
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("ac", AcctCustomerContract.class);
		    sqlQuery.setParameter(0, custNo);
		    sqlQuery.setParameter(1, contactDateBegin);


			int ret = sqlQuery.executeUpdate();
			return true;

		} catch (RuntimeException re) {
			log.error("", re);
			return false;
		}	
	}

}
