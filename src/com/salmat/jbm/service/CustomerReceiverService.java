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


public class CustomerReceiverService extends CustomerReceiverDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(CustomerReceiverService.class);
    
    /* Constants */
    private static final CustomerReceiverService instance = new CustomerReceiverService();
    //private static CustomerDAO CustomerDao;

    
    private CustomerReceiverService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static CustomerReceiverService getInstance() {
        return instance;
    }
	
	public static List<CustomerReceiver> getAllCustomerReceivers() {        
        return HibernateSessionFactory.getSession().createQuery("from CustomerReceiver order by customer").list();
    }
    



}
