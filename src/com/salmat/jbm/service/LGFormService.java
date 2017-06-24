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
import com.painter.util.SessionUtil;
import com.painter.util.Util;


import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.String;
import java.net.URLEncoder;


public class LGFormService extends LgformDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(LGFormService.class);
    
    /* Constants */
    private static final LGFormService instance = new LGFormService();
    //private static CustomerDAO CustomerDao;

    
    private LGFormService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static LGFormService getInstance() {
        return instance;
    }
	
	/*
	 * 根據 cust_no + statement_no 找到該 郵資單
	 */
	public Lgform findByCustNoStatementNo(String custNo, Integer statementNo) {
		try {
	    	String queryString = "select  {l.*} " 
	    		+ " from lgform l" 
	        	+ " where l.idf_CUST_NO=? and l.STATEMENT_NO= ?" ;
	    	
		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("l", Lgform.class);
		    sqlQuery.setParameter(0, custNo);
		    sqlQuery.setParameter(1, statementNo);

	        List retList = sqlQuery.list();
	        if (null!=retList && retList.size()>0  ) 
	        	return (Lgform)retList.get(0);
	        else
	        	return null;
	        
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}
    



}
