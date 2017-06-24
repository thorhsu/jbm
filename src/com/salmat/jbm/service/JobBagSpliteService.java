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


public class JobBagSpliteService extends JobBagSpliteDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(JobBagSpliteService.class);
    
    /* Constants */
    private static final JobBagSpliteService instance = new JobBagSpliteService();


    
    private JobBagSpliteService() {

    }
    
	public static JobBagSpliteService getInstance() {
        return instance;
    }
    
	public int  deleteLineCheck(String jobBagNo){
		Session session = getSession();              
		String hql = "delete from JobBagSplite where jobBagSpliteNo like :jobBagNo";
        Query query = session.createQuery(hql);
        jobBagNo = jobBagNo.toUpperCase();
        query.setString("jobBagNo", jobBagNo + "___U%");
        int rowCount = query.executeUpdate();
	    return rowCount;	
	}
	
	//此方法會 commit，請注意使用時機
	public int  updateJobBagSpliteNums(Integer accounts, Integer pages, Integer sheets, String jobBagNo){		
		Session session = null;
		Transaction tx = null;
		try{
		   session = getSession();              
		   tx = session.beginTransaction();
		   String sql = "update job_bag_splite set ACCOUNTS = ?, PAGES = ?, SHEETS = ? where IDF_JOB_BAG_NO = ?";
           SQLQuery query = session.createSQLQuery(sql);
           query.setParameter(0, accounts);
           query.setParameter(1, pages);
           query.setParameter(2, sheets);
           query.setString(3, jobBagNo);
           int rowCount = query.executeUpdate();
           tx.commit();
           return rowCount;
		}catch(Exception e){
		   log.error("", e);
		   if(tx != null)
			   tx.rollback();
		   return 0;
		}finally{
			if(session != null && session.isOpen())
				session.close();
		}
	    	
	}


}
