package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
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


public class HolidayService extends HolidayDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(HolidayService.class);
    private static Date now = null;
    private static List<Holiday> next30Days = null;
    
    /* Constants */
    private static final HolidayService instance = new HolidayService();
    private HolidayService() {
    }    
	public static HolidayService getInstance() {
        return instance;
    }
	
	public List<Holiday> queryByDatePeriod(Date startDate, Date endDate){
		Session session = getSession();
		Criteria criteria = session.createCriteria(Holiday.class);
		criteria.add(Restrictions.between("date", startDate, endDate)).addOrder(Order.asc("date"));
		List<Holiday> retList = criteria.list();
		session.close();
		return retList;
	}
	public static List<Holiday> queryByNow(Date now){
		//如果日期不同或next30Days是 null
		if(now == null)
			now = new Date();
		if(HolidayService.now == null || HolidayService.now.compareTo(now) != 0 || next30Days != null){
			HolidayService.now = now;
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DATE, 30);
			Session session = HolidayService.getInstance().getSession();
			Criteria criteria = session.createCriteria(Holiday.class);
			criteria.add(Restrictions.between("date", now, cal.getTime())).addOrder(Order.asc("date"));
			List<Holiday> retList = criteria.list();
			next30Days = retList;
		}
		return next30Days;
		
	}
}
