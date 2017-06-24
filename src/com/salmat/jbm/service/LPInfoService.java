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


public class LPInfoService extends LpinfoDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(LPInfoService.class);
    
    /* Constants */
    private static final LPInfoService instance = new LPInfoService();
    //private static CustomerDAO CustomerDao;

    
    private LPInfoService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static LPInfoService getInstance() {
        return instance;
    }
    


    /**
     * get getEffectiveLpInfo
     * return EffectiveLpInfo List
     */
	public List getEffectiveLpInfo() {
		
		
		
		try {
	    	String queryString = " select {a.*} "
	    		+ " from lpinfo a  "
	    		+ " where convert(char, a.NEXT_EFFECTIVE_DATE, 101)   = convert(char, getdate(), 101) " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", Lpinfo.class);

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}		

}
