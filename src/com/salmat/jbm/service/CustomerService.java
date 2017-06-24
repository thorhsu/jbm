package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.salmat.jbm.bean.LcinfoJSON;
import com.salmat.jbm.bean.LginfoJSON;
import com.salmat.jbm.bean.LpinfoJSON;
import com.salmat.jbm.bean.MpinfoJSON;
import com.salmat.jbm.bean.PsinfoJSON;
import com.salmat.jbm.bean.ReturninfoJSON;
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


public class CustomerService extends CustomerDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(CustomerService.class);
    private static final LPInfoService lpinfoService = LPInfoService.getInstance();
    private static final MPInfoService mpinfoService = MPInfoService.getInstance();    
    private static final PSInfoService psinfoService = PSInfoService.getInstance();       
    
    /* Constants */
    private static final CustomerService instance = new CustomerService();
    //private static CustomerDAO CustomerDao;

    public List<Customer> getAllCustomers(){
    	Session session = HibernateSessionFactory.getSession();
    	Criteria criteria = session.createCriteria(Customer.class);
    	List list = criteria.list();
    	if(list == null)
    		list = new ArrayList<Customer>();
    	return list;
    	
    }
    public CustomerService() {
    	//CustomerDao = new CustomerDAO();    	
    }
    
	public static CustomerService getInstance() {
        return instance;
    }
    

	public List getCustomerLPList(String CustomerNo) {
		List list = new ArrayList<Lpinfo>();  
    	Customer customer = this.findById(CustomerNo);		
    	if (null ==customer)
    		return null;
    	
		try {
	    	String queryString = "select  {l.*} " 
	    		+ " from lpinfo l" 
	        	+ " where l.idf_CUST_NO = ? or l.idf_CUST_NO is null or l.idf_CUST_NO='' " 
	        	+ " order by lp_no " ;

		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("l", Lpinfo.class);
		    sqlQuery.setParameter(0, CustomerNo);

	        List retList = sqlQuery.list();

	        for (int i=0; i<retList.size(); i++) {
    			Lpinfo lpinfo = (Lpinfo)retList.get(i);
    			LpinfoJSON json = new LpinfoJSON();
    			json.setLpNo(lpinfo.getLpNo());
    			json.setProdmemo(lpinfo.getProdmemo());
    			if (null != lpinfo.getCodeByCodePrintType())
    				json.setPrintType(lpinfo.getCodeByCodePrintType().getCodeValueTw());
    			if (null != lpinfo.getCodeByCodePrinter())
    				json.setPrinter(lpinfo.getCodeByCodePrinter().getCodeValueTw());
    			json.setPcode1(lpinfo.getPcode1());
    			json.setPcode2(lpinfo.getPcode2());
    			json.setPcode3(lpinfo.getPcode3());
    			json.setPcode4(lpinfo.getPcode4());
    			json.setPcode5(lpinfo.getPcode5());
    			json.setPcode6(lpinfo.getPcode6());
    			json.setPcode7(lpinfo.getPcode7());
    			json.setPcode8(lpinfo.getPcode8());
    			json.setRemark(lpinfo.getRemark());
    			
    			list.add(json);	        	
	        }
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}

    	return list;
	}

	public List getCustomerMPList(String CustomerNo) {
		List list = new ArrayList<Mpinfo>();  
    	Customer customer = this.findById(CustomerNo);		
    	if (null ==customer)
    		return null;
    	
		try {
	    	String queryString = "select  {l.*} " 
	    		+ " from mpinfo l" 
	        	+ " where l.idf_CUST_NO = ? or l.idf_CUST_NO is null or l.idf_CUST_NO='' " 
	        	+ " order by mp_no " ;

		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("l", Mpinfo.class);
		    sqlQuery.setParameter(0, CustomerNo);

	        List retList = sqlQuery.list();

	        for (int i=0; i<retList.size(); i++) {
    			Mpinfo mpinfo = (Mpinfo)retList.get(i);
    			MpinfoJSON json = new MpinfoJSON();
    			json.setMpNo(mpinfo.getMpNo());
    			json.setProdmemo(mpinfo.getProdmemo());
    			json.setPrintCode(mpinfo.getPrintCode());
    			json.setStampSetFg(mpinfo.getStampSetFg());
    			json.setZipFg(mpinfo.getZipFg());
    			json.setMp1Word(mpinfo.getMp1Word());
    			json.setMp2Word(mpinfo.getMp2Word());
    			json.setMp3Word(mpinfo.getMp3Word());
    			json.setMp4Word(mpinfo.getMp4Word());
    			json.setMp1Iflag(mpinfo.getMp1Iflag());
    			json.setMp2Iflag(mpinfo.getMp2Iflag());
    			json.setMp3Iflag(mpinfo.getMp3Iflag());
    			json.setMp4Iflag(mpinfo.getMp4Iflag());
    			json.setRemark(mpinfo.getRemark());
    			list.add(json);    	
	        }
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}

    	return list;
    	
    	
		
	}
	
	public List getCustomerPSList(String CustomerNo) {
		List list = new ArrayList<Mpinfo>();  
    	Customer customer = this.findById(CustomerNo);		
    	if (null ==customer)
    		return null;
    	
		try {
	    	String queryString = "select  {l.*} " 
	    		+ " from psinfo l" 
	        	+ " where l.idf_CUST_NO = ? or l.idf_CUST_NO is null or l.idf_CUST_NO='' " 
	        	+ " order by ps_no " ;

		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("l", Psinfo.class);
		    sqlQuery.setParameter(0, CustomerNo);

	        List retList = sqlQuery.list();

	        for (int i=0; i<retList.size(); i++) {
    			Psinfo psinfo = (Psinfo)retList.get(i);
    			PsinfoJSON json = new PsinfoJSON();
    			if (null!= psinfo.getCodeByCodePsType())
    				json.setPsType(psinfo.getCodeByCodePsType().getCodeValueTw());
    			json.setPsNo(psinfo.getPsNo());
    			json.setProdmemo(psinfo.getProdmemo());
    			json.setZipFg(psinfo.getZipFg());
    			json.setRemark(psinfo.getRemark());

    			list.add(json);	
	        }
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}

    	return list;
    	
	}
	
	public List getCustomerLGList(String CustomerNo) {
		List list = new ArrayList<Lpinfo>();  
    	Customer customer = this.findById(CustomerNo);		
    	if (null ==customer)
    		return null;
    	else {
        	Set<Lginfo> lginfos = customer.getLginfos();        		

    		Iterator iterator = lginfos.iterator();
    		while (iterator.hasNext()) {
    			Lginfo lginfo = (Lginfo)iterator.next();
    			LginfoJSON json = new LginfoJSON();
    			json.setLgNo(lginfo.getLgNo());
    			json.setProgNm(lginfo.getProgNm());
    			if (null!= lginfo.getCodeByCodeMailCategory())
    				json.setMailCategory(lginfo.getCodeByCodeMailCategory().getCodeValueTw());
    			if (null!= lginfo.getCodeByCodeLgType())
    				json.setLgType(lginfo.getCodeByCodeLgType().getCodeValueTw());
    			if (null!= lginfo.getCodeByCodeMailToPostoffice())
    				json.setMailToPostoffice(lginfo.getCodeByCodeMailToPostoffice().getCodeValueTw());
    			
    			if (null!= lginfo.getCodeByCodeMailToArea1())
    				json.setCodeMailToArea1(lginfo.getCodeByCodeMailToArea1().getId());
    			if (null!= lginfo.getCodeByCodeMailToArea2())    			
    				json.setCodeMailToArea2(lginfo.getCodeByCodeMailToArea2().getId());
    			if (null!= lginfo.getCodeByCodeMailToArea3())    			
    				json.setCodeMailToArea3(lginfo.getCodeByCodeMailToArea3().getId());
    			if (null!= lginfo.getCodeByCodeMailToArea4())    			
    				json.setCodeMailToArea4(lginfo.getCodeByCodeMailToArea4().getId());
    			if (null!= lginfo.getCodeByCodeMailToArea5())    			
    				json.setCodeMailToArea5(lginfo.getCodeByCodeMailToArea5().getId());
    			if (null!= lginfo.getCodeByCodeMailToArea6())    			
    				json.setCodeMailToArea6(lginfo.getCodeByCodeMailToArea6().getId());
    			json.setMailToAreaDisplay(lginfo.getMailToAreaDisplay());
    			list.add(json);
    		}
    	}
    	return list;
	}
	
	public List getCustomerReturnList(String CustomerNo) {
		List list = new ArrayList<Lpinfo>();  
    	Customer customer = this.findById(CustomerNo);		
    	if (null ==customer)
    		return null;
    	else {
        	Set<Returninfo> returninfos = customer.getReturninfos();        		

    		Iterator iterator = returninfos.iterator();
    		while (iterator.hasNext()) {
    			Returninfo returninfo = (Returninfo)iterator.next();
    			ReturninfoJSON json = new ReturninfoJSON();
    			json.setReturnNo(returninfo.getReturnNo());
    			json.setUserCompany(returninfo.getUserCompany());
    			json.setUserName(returninfo.getUserName());
    			json.setReturnAddress(returninfo.getReturnAddress());
    			json.setUserTel(returninfo.getUserTel());
    			list.add(json);
    		}
    	}
    	return list;
	}
	
	public List getCustomerLCList(String CustomerNo) {
		List list = new ArrayList<Lpinfo>();  
    	Customer customer = this.findById(CustomerNo);		
    	if (null ==customer)
    		return null;
    	else {
        	Set<Lcinfo> lcinfos = customer.getLcinfos();        		

    		Iterator iterator = lcinfos.iterator();
    		while (iterator.hasNext()) {
    			Lcinfo lcinfo = (Lcinfo)iterator.next();
    			LcinfoJSON json = new LcinfoJSON();
    			json.setLcNo(lcinfo.getLcNo());
    			json.setProgNm(lcinfo.getProgNm());
    			list.add(json);
    		}
    	}
    	return list;
	}	
	
}
