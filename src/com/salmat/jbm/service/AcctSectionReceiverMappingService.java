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


public class AcctSectionReceiverMappingService extends AcctSectionReceiverMappingDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AcctSectionReceiverMappingService.class);
    
    /* Constants */
    private static final AcctSectionReceiverMappingService instance = new AcctSectionReceiverMappingService();
    //private static CustomerDAO CustomerDao;

    
    private AcctSectionReceiverMappingService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AcctSectionReceiverMappingService getInstance() {
        return instance;
    }

	public AcctSectionReceiverMapping findByDebitNo(AcctDno acctDno) {
		try {		
	    	String queryString = " select {m.*}  "
	    		+ " from dbo.acct_section_receiver_mapping m "
	    		+ " where m.idf_CUST_NO =? and m.section=?	 " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("m", AcctSectionReceiverMapping.class);
		    sqlQuery.setParameter(0, acctDno.getCustomer().getCustNo());
		    sqlQuery.setParameter(1, acctDno.getSection());

			List retList = sqlQuery.list();
			if (null!= retList && retList.size() >0)
				return (AcctSectionReceiverMapping)retList.get(0);
			else
				return null;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
	}
    
	/*
	 * 檢查 customer + section mapping 是否完整
	 */
	public Boolean checkCustomerSectionInfo(AcctDno acctDno) {
	    //檢查客戶+Section 是否正確
		AcctSectionReceiverMapping acctSectionReceiverMapping = this.findByDebitNo(acctDno);
		if (null == acctSectionReceiverMapping) {
	        return false;
		}
		
	    //檢查客戶+Section 是否有指定 報價是否含稅     	
		if (null == acctSectionReceiverMapping.getVat()){
			return false;
		}
		
	    //檢查客戶+Section 是否有指定 帳期
		if (null == acctSectionReceiverMapping.getPaymentTerm()){
			return false;
		}		
		
	    //檢查客戶+Section 是否有指定 帳務聯絡人        	
		if (null == acctSectionReceiverMapping.getCustomerReceiver()){
			return false;
		}
		
		

		return true;
	}
	



}
