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


public class AcctSectionJobCodeMappingService extends AcctSectionJobCodeMappingDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AcctSectionJobCodeMappingService.class);
    
    /* Constants */
    private static final AcctSectionJobCodeMappingService instance = new AcctSectionJobCodeMappingService();
    //private static CustomerDAO CustomerDao;

    
    private AcctSectionJobCodeMappingService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AcctSectionJobCodeMappingService getInstance() {
        return instance;
    }
	
	public List<AcctSectionJobCodeMapping> findByDebitNo(AcctDno acctDno) {
		try {		
	    	String queryString = " select {m.*}  "
	    		+ " from acct_section_job_code_mapping m "
	    		+ " where m.idf_CUST_NO =? and m.section=?	 " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("m", AcctSectionJobCodeMapping.class);
		    sqlQuery.setParameter(0, acctDno.getCustomer().getCustNo());
		    sqlQuery.setParameter(1, acctDno.getSection());

			List retList = sqlQuery.list();
			if (null!= retList && retList.size() >0)
				return retList;
			else
				return null;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
	}
    



}
