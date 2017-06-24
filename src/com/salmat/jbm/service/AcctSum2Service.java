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


public class AcctSum2Service extends AcctSum2DAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AcctSum2Service.class);
    
    /* Constants */
    private static final AcctSum2Service instance = new AcctSum2Service();
    //private static CustomerDAO CustomerDao;

    
    private AcctSum2Service() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AcctSum2Service getInstance() {
        return instance;
    }
    

	//	找出sum2 中, 工單的基礎資料清單
	//  
	public List findByDebitNote(AcctDno acctDno) {
	try {		
	    	String queryString = " select {s.*}  "
	    		+ " from acct_sum2 s "
	    		+ " where s.idf_debit_no= ?	 and  s.is_minimal_charge is null order by s.cost_center, s.ITEM_TYPE, idf_JOB_CODE_NO desc, idf_acct_charge_item" ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("s", AcctSum2.class);
		    sqlQuery.setParameter(0, acctDno.getDebitNo());
		    //sqlQuery.setParameter(1, itemType);
		    

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}		


}
