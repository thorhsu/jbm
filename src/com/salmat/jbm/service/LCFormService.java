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


public class LCFormService extends LcformDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(LCFormService.class);
    
    /* Constants */
    private static final LCFormService instance = new LCFormService();
    //private static CustomerDAO CustomerDao;

    
    private LCFormService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static LCFormService getInstance() {
        return instance;
    }
	

    



}
