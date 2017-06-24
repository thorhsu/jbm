package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.NumberFormat;
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


public class AcctSum1Service extends AcctSum1DAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AcctSum1Service.class);
    
    /* Constants */
    private static final AcctSum1Service instance = new AcctSum1Service();
    private static final AcctChargeItemService acctChargeItemService = AcctChargeItemService.getInstance();    
    private static final JobCodeService jobCodeService = JobCodeService.getInstance();
    private static final JobBagService jobBagService = JobBagService.getInstance();

    
    private AcctSum1Service() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AcctSum1Service getInstance() {
        return instance;
    }


    
//	找出sum1 中, 工單的基礎資料清單
	public List findByJobBag(String  jobBagNo) {
	try {		
	    	String queryString = " select {s.*}  "
	    		+ " from acct_sum1 s "
	    		+ " where s.IDF_JOB_BAG_NO= ?	 " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("s", AcctSum1.class);
		    sqlQuery.setParameter(0, jobBagNo);

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}

		
	
//	找出sum1 中, 工單的基礎資料清單
	public List findByDebitNoteList(String  debitNoteList) {
		try {		
	    	String queryString = " select {s.*}  "
	    		+ " from acct_sum1 s "
	    		+ " where s.idf_debit_no in ("+debitNoteList+")	 order by s.idf_debit_no , s.PROG_NM, s.CYCLE_DATE asc,  s.IDF_JOB_BAG_NO, s.lpPcode desc" ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("s", AcctSum1.class);
		    //sqlQuery.setParameter(0, debitNoteList);

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}

	}
	
	
    //找出sum1 中, 工單的基礎資料清單
	public List findByDebitNote(AcctDno acctDno) {
		try {		
	    	String queryString = " select {s.*}  "
	    		+ " from acct_sum1 s "
	    		+ " where s.idf_debit_no= ?	 order by s.cost_center , s.PROG_NM,s.CYCLE_DATE ,  s.IDF_JOB_BAG_NO" ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("s", AcctSum1.class);
		    sqlQuery.setParameter(0, acctDno.getDebitNo());

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}
	
    //找出sum1 中有那些工單樣本
	public List<String> findJobCodeByDebitNote(AcctDno acctDno) {
		try {		
	    	String queryString = " select distinct s.idf_JOB_CODE_NO "
	    		+ " from acct_sum1 s "
	    		+ " where s.idf_debit_no= ?	 " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.setParameter(0, acctDno.getDebitNo());
		    
			List<String> retList = sqlQuery.list();			
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}
	

    //檢查是否有工單總收費小於 最低收費
	//若有小於最低收費的工單, 將該工單 sum1 中  的項目 標為 IsMinimalCharge = true;
	//另外該工單新增一筆 MinimalCharge 的收費相目, 數量 =1, 收費金額  = 工單樣本上定義的最低收費金額
	public Boolean checkMinimalCharge(AcctDno acctDno) {
		try {
	    	String queryString = " select {s1.*} "
	    		   + " from acct_sum1 s1, job_code j, (  "
	    		   + " select  s.IDF_JOB_CODE_NO, s.CYCLE_DATE, sum( s.unit_price * s.sum_qty ) total    "
	    		   + " from dbo.acct_sum1 s  "
	    		   + " where s.idf_debit_no = '" + acctDno.getDebitNo()  + "' " 
	    		   + " group by s.idf_JOB_CODE_NO, s.CYCLE_DATE" 
	    		   + " ) js " 
	    		   + " where " 	    		   
	    		   + " s1.idf_JOB_CODE_NO = j.JOB_CODE_NO  " 
	    		   + " and s1.IDF_JOB_CODE_NO = js.idf_JOB_CODE_NO  " 
	    		   + " and s1.CYCLE_DATE = js.CYCLE_DATE  "
	    		   + " and js.total < j.minimal_charge_price  and s1.idf_debit_no = '" + acctDno.getDebitNo() + "' "
 		   		   + " order by s1.IDF_JOB_BAG_NO , s1.CYCLE_DATE" ;	    	
	    		   
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("s1", AcctSum1.class);
		    //如果用參數輸入時，莫明地會變成非常慢(大量時會超過十分鐘)，有可能是jtds的實作上有問題，所以改成用拚裝的方法執行
		    //sqlQuery.setParameter(0, acctDno.getDebitNo());
		    //sqlQuery.setParameter(1, acctDno.getDebitNo());		    
			List retList = sqlQuery.list();
			
			
			String prevJobCodeNoPlusCycleDate="";
			for (int i=0; i<retList.size(); i++) {
				AcctSum1 acctSum1 = (AcctSum1)retList.get(i);
				acctSum1.setIsMinimalCharge(true);
				this.save(acctSum1);
				
	        	//minimalChargeItemId 最低收費的 chargeItem Id
	        	Integer minimalChargeItemId = Integer.parseInt(Util.getString("accounting.minimalChargeItemId"));
	        	
				//產生新的sum1 資料, 最低收費
				if (! (acctSum1.getIdfJobCodeNo() + acctSum1.getCycleDate()).equalsIgnoreCase(prevJobCodeNoPlusCycleDate)) {
					AcctSum1 _acctSum1 = new AcctSum1();
					_acctSum1.setCustomer(acctSum1.getCustomer());
					_acctSum1.setAcctDno(acctSum1.getAcctDno());
					_acctSum1.setIdfJobCodeNo(acctSum1.getIdfJobCodeNo());
					_acctSum1.setIdfJobBagNo(acctSum1.getIdfJobBagNo());
					_acctSum1.setCostCenter(acctSum1.getCostCenter());
					AcctChargeItem  acctChargeItem = acctChargeItemService.findById(minimalChargeItemId); //142 : minimal_charge
					_acctSum1.setAcctChargeItem(acctChargeItem);
					_acctSum1.setAcctCustomerContract(null);
					_acctSum1.setItemName(acctChargeItem.getItemName());
					_acctSum1.setItemNameTw(acctChargeItem.getItemNameTw());
					_acctSum1.setTitle(acctChargeItem.getCodeByTitle().getCodeValueTw());
					_acctSum1.setSubTitle(acctChargeItem.getCodeBySubTitle().getCodeValueTw());
					_acctSum1.setEp1AccountCode("0");
					_acctSum1.setCalculatType("FIXED");
					_acctSum1.setProgNm(acctSum1.getProgNm());
					_acctSum1.setCycleDate(acctSum1.getCycleDate());
					JobCode _jobCode = jobCodeService.findById(acctSum1.getIdfJobCodeNo());
					if (null!= _jobCode)
						_acctSum1.setUnitPrice(_jobCode.getMinimalChargePrice());
					_acctSum1.setSumQty(1);
					_acctSum1.setCycleDate(acctSum1.getCycleDate());
					this.save(_acctSum1);
					//用jobCode + cycleDate去區分
					prevJobCodeNoPlusCycleDate = acctSum1.getIdfJobCodeNo() + acctSum1.getCycleDate();
				}
				
			}

			return true;
		} catch (RuntimeException re) {
			log.error("", re);
			return false;
		}	

	}

	

}
