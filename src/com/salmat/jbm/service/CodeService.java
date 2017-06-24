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


public class CodeService extends CodeDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(CodeService.class);
    
    /* Constants */
    private static final CodeService instance = new CodeService();


    
    private CodeService() {


    }
    
	public static CodeService getInstance() {
        return instance;
    }

	
	/**根據codeTypeName  找出代碼清單
	 * @param codeTypeName
	 * @return code list 
	 */		
    public List findBycodeTypeName(String codeTypeName) {
		log.debug("findBycodeTypeName:  codeTypeName: " + codeTypeName);

		try {
	    	String queryString = "select  {a.*} " 
	    		+ " from code a" 
	        	+ " where a.CODE_TYPE_NAME=? " 
	        	+ " order by ORDERBY_ID , id" ;
	    	
		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", Code.class);
		    sqlQuery.setParameter(0, codeTypeName);

	        List retList = sqlQuery.list();
            return retList;
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
		
    }




}
