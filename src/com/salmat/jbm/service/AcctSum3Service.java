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


public class AcctSum3Service extends AcctSum3DAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AcctSum3Service.class);
    
    /* Constants */
    private static final AcctSum3Service instance = new AcctSum3Service();
    //private static CustomerDAO CustomerDao;

    
    private AcctSum3Service() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AcctSum3Service getInstance() {
        return instance;
    }
    
//	根據 Debit Note , 尋找sum3
	public List findByDebitNote(AcctDno acctDno ) {
	try {		
	    	String queryString = " select {s.*}  "
	    		+ " from acct_sum3 s "
	    		+ " where s.idf_debit_no= ?	 order by s.title desc, s.subtitle " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("s", AcctSum3.class);
		    sqlQuery.setParameter(0, acctDno.getDebitNo());	    
		    

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}	
	
	
//	根據 Debit Note , 尋找sum3
	public List findByDebitNote(AcctDno acctDno, String purpose ) {
	try {		
	    	String queryString = " select {s.*}  "
	    		+ " from acct_sum3 s "
	    		+ " where s.idf_debit_no= ?	 and s.purpose in ( "+purpose+" ) order by s.title desc, s.subtitle " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("s", AcctSum3.class);
		    sqlQuery.setParameter(0, acctDno.getDebitNo());
		    //sqlQuery.setParameter(1, purpose);		    
		    

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}		


}
