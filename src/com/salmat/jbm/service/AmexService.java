package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.hibernate.transform.Transformers;

import com.salmat.jbm.bean.AmexFormater;
import com.salmat.jbm.bean.CiticardFormater;
import com.salmat.jbm.bean.DamageFormater;
import com.salmat.jbm.bean.HScardFormater;
import com.salmat.jbm.bean.LGFormBean;
import com.salmat.jbm.bean.LcinfoJSON;
import com.salmat.jbm.bean.LginfoJSON;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.bean.LpinfoJSON;
import com.salmat.jbm.bean.MpinfoJSON;
import com.salmat.jbm.bean.OneGoldFormater;
import com.salmat.jbm.bean.PsinfoJSON;
import com.salmat.jbm.bean.ReturninfoJSON;
import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.logisticParsingService.LogisticParsingObj;
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


public class AmexService extends AmexDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AmexService.class);
    private static final JobCodeService jobcodeService = JobCodeService.getInstance();
	private static final JobBagService jobbagService = JobBagService.getInstance(); 
    private static Map<String, Map<JobCode, String>> custFilePatternMap = new HashMap<String, Map<JobCode, String>>();
    private static Map<String, LogisticParsingService> logisticParsingServices ;

	/* Constants */
    private static final AmexService instance = new AmexService();
    //private static CustomerDAO CustomerDao;

    
    private AmexService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AmexService getInstance() {
        return instance;
    }
	
	public static JobBag findJobBagByDamage(DamageFormater formater) {
		
		try {
			Date receiveDate = formater.getProcessDate();
			
			Calendar beginCal = Calendar.getInstance();
			beginCal.setTime(receiveDate);
			Calendar endCal = Calendar.getInstance();
			Calendar endCal2 = Calendar.getInstance();
			
			endCal.setTime(receiveDate);
			beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE) - 2, 23, 59, 59); //收檔日往前推一天，所以設為兩天前的最後一秒
			endCal2.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE) + 7, 23, 59, 59); //較鬆的區間，有客戶名時使用
			endCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH),  endCal.get(Calendar.DATE), 23, 59, 59);
			
			
			
			
			//頁數加減二，account加減一的問題，所以要用between
	    	String queryString = "select  {j.*} " 
	    		+ " from job_bag_splite j ,job_bag j1 " 
	    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j.AFP_FILENAME like ? and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.ACCOUNTS between ? and ?) and (j.PAGES between ? and ? ) and j.MP_HAS_DAMAGE = 1 " +
	    				" and (IDF_JOB_BAG_NO is not null and IDF_JOB_BAG_NO <> '' and IDF_JOB_BAG_NO not like '%D' and IDF_JOB_BAG_NO not like '%D_')" ;
	    	
	    	String queryString2 = "select  {j.*} " 
	    		+ " from job_bag_splite j , job_bag j1 " 
	    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j.AFP_FILENAME like ? and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.ACCOUNTS between ? and ?) and (j.PAGES between ? and ? )  " +
	    				" and (IDF_JOB_BAG_NO is not null and IDF_JOB_BAG_NO <> '' and IDF_JOB_BAG_NO not like '%D' and IDF_JOB_BAG_NO not like '%D_')" ;
	    	
	    	String afpName = formater.getFilename() ;
	    	
	    	//city one or city gold
	    	boolean custNoExist = false;
	    	if(null != formater.getFilename() && formater.getFilename().length() == 11 
	    			&&  formater.getFilename().matches("(0[1-9]|1[0-9]|2[0-9]|3[0-1])_[tT][wW][0-9][0-9][a-zA-Z][a-zA-Z]..")){ //"CT onegold"
	    		afpName = formater.getFilename().replaceFirst("_[tT][wW]", "");
	    		
	    		queryString = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'CT' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	
		    	queryString2 = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'CT' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    		
		    	custNoExist = true;
		    	//有客戶代號時，區間設大一點
		    	beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE)- 7, 23, 59, 59);
	    	}else if(null != formater.getFilename() && formater.getFilename().length() == 8 
	    			&&  formater.getFilename().matches("...(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9][fF][cC]")){//"FC"
	    		afpName = formater.getFilename().substring(0, 6);
	    		
	    		queryString = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'FC' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and  j.MP_HAS_DAMAGE = 1  and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D'  and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	
		    	queryString2 = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'FC' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?)  and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D'  and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	custNoExist = true;
		    	//有客戶代號時，區間設大一點
		    	beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE)- 7, 23, 59, 59);
	    	}else if(null != formater.getFilename()  &&  (formater.getFilename().matches("[sS][tT][a-eA-E][a-eA-E](0[1-9]|1[0-9]|2[0-9]|3[0-1])[cC][hH]") 
	    				 || formater.getFilename().matches("([cC][mM]|[iI][nN])(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[cC][hH]"))
	    			 )
	    	{
	    		afpName = formater.getFilename().substring(0, formater.getFilename().length() - 2);  //CH
	    		queryString = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'CH' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and  j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D'  and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	
		    	queryString2 = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'CH' " 
		    		+  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D'  and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	custNoExist = true;
		    	//有客戶代號時，區間設大一點
		    	beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE) - 7, 23, 59, 59);
	    	}else if(null != formater.getFilename()  &&  
	    			(formater.getFilename().matches("[dD][0-9][0-9](0[1-9]|1[0-2])[1-6][tT][aA]") || formater.getFilename().matches("[dD][iI](0[1-9]|1[0-2])(0[1-9]|[1-9][0-9])[tT][aA]"))){
		    		afpName = formater.getFilename().substring(0, formater.getFilename().length() - 2);  //ND
		    		queryString = "select  {j.*} " 
			    		+ " from job_bag_splite j , job_bag j1" 
			    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'ND' " 
			    		+ " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and  j.MP_HAS_DAMAGE = 1 " 
			    		+ "and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D')" ;
			    	
			    	queryString2 = "select  {j.*} " 
			    		+ " from job_bag_splite j , job_bag j1" 
			    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'ND' " +
			    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D'  and j.IDF_JOB_BAG_NO not like '%D_')" ;
			    	custNoExist = true;
			    	//有客戶代號時，區間設大一點
			    	beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE) - 7, 23, 59, 59);
		    }else if(null != formater.getFilename()  
	    			&&  formater.getFilename().matches("(X|N).._(0[1-9]|1[0-9]|2[0-9]|3[0-1])(CT|x1|x2)")){ //"CT card"
	    		afpName = formater.getFilename();
	    		
	    		queryString = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'CT' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D'  and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	
		    	queryString2 = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'CT' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    		
		    	custNoExist = true;
		    	//有客戶代號時，區間設大一點
		    	beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE) - 7, 23, 59, 59);
	    	}else if(null != formater.getFilename()  
	    			&&  formater.getFilename().matches("(ST|st|nn|NN|cm|CM|in|IN)(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])(NT|nt)")){ //"NT"
	    		afpName = formater.getFilename().substring(0, formater.getFilename().length() - 2);
	    		
	    		queryString = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'NT' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	
		    	queryString2 = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'NT' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    		
		    	custNoExist = true;
		    	//有客戶代號時，區間設大一點
		    	beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE) - 7, 23, 59, 59);
	    	}else if(null != formater.getFilename()  
	    			&&  formater.getFilename().matches("03(O|B)2(0|1)[0-9][0-9](0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[0-9][0-9][0-9][0-9]")){
                afpName = formater.getFilename();  
                //富邦個人險   FB 此正規表示法可以用兩百年	    		
	    		queryString = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'FB' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	
		    	queryString2 = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'FB' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    		
		    	custNoExist = true;
		    	//這個只要有檔名就一定可以找到，區間可以設超大
		    	endCal2.set(3999, 11, 31); //Java應該不存在了吧
		    	beginCal.set(2000, 0, 0);
	    	}else if(null != formater.getFilename()  
	    			&&  (formater.getFilename().matches("(AX|ax|BX|bx|CE|ce|DF|df|GI|gi|HJ|hj|KX|kx|LX|lx|Mx|mx|NX|nx)(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])(CA|ca)") || 
	    					formater.getFilename().matches("(N|n)(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])(S|s)(CA|ca)"))){ 
	    		//"CA"
	    		afpName = formater.getFilename().substring(0, formater.getFilename().length() - 2);
	    		
	    		queryString = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'CA' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	
		    	queryString2 = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j1.idf_CUST_NO = 'CA' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    		
		    	custNoExist = true;
		    	//有客戶代號時，區間設大一點
		    	beginCal.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH),  beginCal.get(Calendar.DATE) - 7, 23, 59, 59);
	    	}
	    	
	    	
            afpName = afpName + "%";
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("j", JobBagSplite.class);
		    sqlQuery.setParameter(0, afpName);
		    sqlQuery.setParameter(1, beginCal.getTime());
		    
		       
		    if(!custNoExist)
		        sqlQuery.setParameter(2, endCal.getTime());
		    else
		    	sqlQuery.setParameter(2, endCal2.getTime()); //有客戶名的，區間可以更寬鬆一點
		    
		    if(!custNoExist){
		    	sqlQuery.setParameter(3, Integer.parseInt(formater.getOrgAccounts()) - 1);
			    sqlQuery.setParameter(4, Integer.parseInt(formater.getOrgAccounts()) + 1 );
				sqlQuery.setParameter(5, Integer.parseInt(formater.getOrgPages()) - 5 );
			    sqlQuery.setParameter(6, Integer.parseInt(formater.getOrgPages()) + 1 );
		    }
		    
			List retList = sqlQuery.list();
			
	    	SQLQuery sqlQuery2 = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString2);
	    	
		    sqlQuery2.addEntity("j", JobBagSplite.class);
		    sqlQuery2.setParameter(0, afpName);
			sqlQuery2.setParameter(1, beginCal.getTime());
			
			if(!custNoExist)			    
		        sqlQuery2.setParameter(2, endCal.getTime());
		    else
		    	sqlQuery2.setParameter(2, endCal2.getTime());   //有客戶名的，區間可以更寬鬆一點
            
		    if(!custNoExist){
		    	sqlQuery2.setParameter(3, Integer.parseInt(formater.getOrgAccounts()) - 1);
			    sqlQuery2.setParameter(4, Integer.parseInt(formater.getOrgAccounts()) + 1 );
				sqlQuery2.setParameter(5, Integer.parseInt(formater.getOrgPages()) - 5 );
			    sqlQuery2.setParameter(6, Integer.parseInt(formater.getOrgPages()) + 1 );

		    }
			List retList2 = sqlQuery2.list();
			
			
			//如果都沒有，放寬區間條件query一次，不用管pages，accounts放寬為 +-2，收檔日放寬到一個月(一般最短周期都以月來計算)
			if ((null == retList || retList.size() == 0) && 
					(null== retList2 || retList2.size()==0)) {
			    queryString = "select  {j.*} " 
	    		    + " from job_bag_splite j , job_bag j1 " 
	    		    + " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and (j.AFP_FILENAME like ?) and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.ACCOUNTS between ? and ?)  and j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    	
	    	    queryString2 = "select  {j.*} " 
	    		   + " from job_bag_splite j, job_bag j1 " 
	    		   + " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j.AFP_FILENAME like ? and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.ACCOUNTS between ? and ?)  and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    	    sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
	    	    
			    sqlQuery.addEntity("j", JobBagSplite.class);
			    sqlQuery.setParameter(0, afpName);
			    sqlQuery.setParameter(1, beginCal.getTime());
			    sqlQuery.setParameter(2, endCal.getTime());
			    sqlQuery.setParameter(3, Integer.parseInt(formater.getOrgAccounts()) - 2);
				sqlQuery.setParameter(4, Integer.parseInt(formater.getOrgAccounts()) + 2 );
				retList = sqlQuery.list();
				
				sqlQuery2 = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString2);
				
			    sqlQuery2.addEntity("j", JobBagSplite.class);
			    sqlQuery2.setParameter(0, afpName);
			    
			    Calendar queryBegin = (Calendar) beginCal.clone();
			    Calendar queryEnd = (Calendar) endCal.clone();
			    queryBegin.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH), beginCal.get(Calendar.DATE) - 14);
			    queryEnd.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DATE) + 8);
			    sqlQuery2.setParameter(1, queryBegin.getTime());
			    sqlQuery2.setParameter(2, queryEnd.getTime());
	            sqlQuery2.setParameter(3, Integer.parseInt(formater.getOrgAccounts()) - 2);
				sqlQuery2.setParameter(4, Integer.parseInt(formater.getOrgAccounts()) + 2 );
				retList2 = sqlQuery2.list();
			}
						
			//如果還是沒有，有可能是結尾為客戶代號
			if ((null == retList || retList.size() == 0) && 
					(null== retList2 || retList2.size()==0)) {
                afpName = formater.getFilename().substring(0, formater.getFilename().length() - 2);
                String custNo = formater.getFilename().substring(formater.getFilename().length() - 2);
	    		
	    		queryString = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and j1.idf_CUST_NO = '" + custNo + "' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
		    	
		    	queryString2 = "select  {j.*} " 
		    		+ " from job_bag_splite j , job_bag j1" 
		    		+ " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and j1.idf_CUST_NO = '" + custNo + "' " +
		    		  " and j.AFP_FILENAME like ?  and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?) and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    		
		    	custNoExist = true;
		    	
		    	sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
			    sqlQuery.addEntity("j", JobBagSplite.class);
			    sqlQuery.setParameter(0, afpName);
			    sqlQuery.setParameter(1, beginCal.getTime());
			    sqlQuery.setParameter(2, endCal2.getTime()); //有客戶名的，區間可以更寬鬆一點			    
			    retList = sqlQuery.list();
				
		    	sqlQuery2 = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString2);
			    sqlQuery2.addEntity("j", JobBagSplite.class);
			    sqlQuery2.setParameter(0, afpName);
				sqlQuery2.setParameter(1, beginCal.getTime());
				sqlQuery2.setParameter(2, endCal2.getTime()); //有客戶名的，區間可以更寬鬆一點
				retList2 = sqlQuery2.list();			    
			}
			
			//如果還是都沒有，最後嘗試再放寬區間條件query一次，不用管pages和accounts，只用收檔日及檔名查
			if ((null == retList || retList.size() == 0) && 
					(null== retList2 || retList2.size()==0)) {
			    queryString = "select  {j.*} " 
	    		    + " from job_bag_splite j , job_bag j1 " 
	    		    + " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and (j.AFP_FILENAME like ?) and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?)  and j.MP_HAS_DAMAGE = 1 and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    	
	    	    queryString2 = "select  {j.*} " 
	    		   + " from job_bag_splite j, job_bag j1 " 
	    		   + " where j1.JOB_BAG_NO = j.IDF_JOB_BAG_NO and (j1.IS_DELETED is null or j1.IS_DELETED = 0) and j.AFP_FILENAME like ? and (j.RECEIVE_DATE >= ? and j.RECEIVE_DATE <= ?)  and (j.IDF_JOB_BAG_NO is not null and j.IDF_JOB_BAG_NO <> '' and j.IDF_JOB_BAG_NO not like '%D' and j.IDF_JOB_BAG_NO not like '%D_')" ;
	    	    sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
	    	    
			    sqlQuery.addEntity("j", JobBagSplite.class);
			    sqlQuery.setParameter(0, afpName);
			    sqlQuery.setParameter(1, beginCal.getTime());
			    sqlQuery.setParameter(2, endCal.getTime());
				retList = sqlQuery.list();
				
				sqlQuery2 = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString2);
				
			    sqlQuery2.addEntity("j", JobBagSplite.class);
			    sqlQuery2.setParameter(0, afpName);
			    
			    Calendar queryBegin = (Calendar) beginCal.clone();
			    Calendar queryEnd = (Calendar) endCal.clone();
			    queryBegin.set(beginCal.get(Calendar.YEAR), beginCal.get(Calendar.MONTH), beginCal.get(Calendar.DATE) - 14);
			    queryEnd.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), endCal.get(Calendar.DATE) + 8);
			    sqlQuery2.setParameter(1, queryBegin.getTime());
			    sqlQuery2.setParameter(2, queryEnd.getTime());
				retList2 = sqlQuery2.list();
			}


			if ((null!= retList && retList.size() >= 1) || 
					(null!= retList2 && retList2.size() >= 1)) {
				
				boolean notCommitDamage = false;
				if(null== retList || retList.size() < 1){
					retList = retList2;
					notCommitDamage = true; //表示OP忘了key這張單有Damage
				}
				String jobBagNo = null; 

				//因為是用like去query的(不得不用)，有可能query出多個
				List<JobBagSplite> jobBagSplites = new ArrayList<JobBagSplite>();
				for(int i = 0 ; i < retList.size() ; i++){
					JobBagSplite jobBagSplite = (JobBagSplite)retList.get(i);
					String fileNm = jobBagSplite.getAfpFilename();					
					//完全相同時放入
					if (jobBagSplite.getAfpFilename() != null && afpName != null && 
							jobBagSplite.getAfpFilename().toUpperCase().equals(formater.getFilename().toUpperCase())){						
						jobBagSplites.add(jobBagSplite);
					}					
				}
				//如果有完全相同檔名的，就用這個來代替查出來的結果
				//沒有完全相同檔名的話就算了，用查出來的去生成 
				if(jobBagSplites.size() > 0){
					retList = jobBagSplites;
				}
				
				//檢查所有的jobbagSplite是不是都屬於同一個jobBag
				for(int i = 0 ; i < retList.size() ; i++){
					JobBagSplite jobBagSplite = (JobBagSplite)retList.get(i);
					if(i == 0 || jobBagNo == null){
						jobBagNo = jobBagSplite.getJobBagSpliteNo().substring(0, jobBagSplite.getJobBagSpliteNo().length()-3);
					}else if(!jobBagNo.equals(jobBagSplite.getJobBagSpliteNo().substring(0,jobBagSplite.getJobBagSpliteNo().length()-3))){
            			SysLog syslog = new SysLog();
            			syslog.setLogType("CREATE_JOBBAG_DAMAGE");
            			syslog.setSubject("DAMAGE工單找到不只一個 ");
            			syslog.setMessageBody("1." + jobBagNo + " | 2."  +  jobBagSplite.getJobBagSpliteNo().substring(0, jobBagSplite.getJobBagSpliteNo().length() - 3) );
            			syslog.setIsException(true);            			            			
            			syslog.setCreateDate(new Date());
            			SyslogService.getInstance().save(syslog);
						return null; //如果抓到的root job bag no不同，系統無法判斷此damage工單該分配給那一個						
					}
				}
				
				
				JobBagSplite jobBagSplite = (JobBagSplite)retList.get(0);
				JobBag rootJobbag = jobBagSplite.getJobBag();
				
				if (null!= jobBagSplite) {					
					jobBagNo = jobBagSplite.getJobBagSpliteNo().substring(0,jobBagSplite.getJobBagSpliteNo().length()-3);
					String rootJobbagNo="";
					
					//非第一批工單批號
					//String jobbagSpliteLast3Code = jobBagSplite.getJobBagSpliteNo().substring(jobBagSplite.getJobBagSpliteNo().length()-3,jobBagSplite.getJobBagSpliteNo().length());
					rootJobbagNo = jobBagNo;
					
					if(rootJobbag == null)
					   rootJobbag = jobbagService.findById(rootJobbagNo);
					
					//代表此工單分檔之前未被通報毀損
					if(notCommitDamage){
						if(retList.size() == 1)
						    jobBagSplite.setMpHasDamage(true);
						if(rootJobbag != null)
						   rootJobbag.setHasDamage(true);
						Session session = HibernateSessionFactory.getSession();
						Transaction tx = session.beginTransaction();
						session.update(jobBagSplite);				
						if(rootJobbag != null)
							session.update(rootJobbag);
						tx.commit();
					}										
					return rootJobbag;
				}else{
					return null;
				}
			}else{
			   return null;	
			}
			
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}finally{
			if(HibernateSessionFactory.getSession().isOpen())
				   HibernateSessionFactory.closeSession();
		}		

	}	
	
	public static JobCode findJobCodeByAmex(AmexFormater formater) {
		String country = "";
		String fileName = formater.getAfpFileName();
		Integer fileLength = formater.getAfpFileName().length();
		String filePattern="";
		String typeOfFile = "";
		String speaker = "";
		String logFileSEQ="001"; //預設值 為 001, 表是第一批		

		
		try {
			if (null!= fileName && fileName.length() > 1 && fileName.indexOf("JG") >0 )  { 
				// JGTW - Globestar ex: AMEX.PROD.201106JGTW8201.PER.GSX.1.MLD.L.a ==>
				// SEEK SUBSTR(m.afpname,17,4) + SUBSTR(m.afpname,26,4) + "***." + SUBSTR(m.afpname,36,3) + SUBSTR(m.afpname,22,1) + "*"
				country = fileName.substring(16, 20);
		    	filePattern =   fileName.substring(25, 29) + "***." + fileName.substring(35, 38);
		    	typeOfFile = fileName.substring(21, 22);
		    	speaker = "*";
		    	//+ fileName.substring(21, 22);
			}
		    else { // && DSTW - RCP
		    	// SEEK SUBSTR(m.afpname,17,4) + SUBSTR(m.afpname,26,8) + SUBSTR(m.afpname,36,3) + SUBSTR(m.afpname,22,1) + "*"
		    	if (fileName.substring(33, 34).equalsIgnoreCase("E") || fileName.substring(33, 34).equalsIgnoreCase("L")) {
		    		country = fileName.substring(16, 20);
		    		filePattern = fileName.substring(25, 33) +  fileName.substring(35, 38) ;
		    		typeOfFile = fileName.substring(21, 22);
		    		speaker = "*";
		    		//+ fileName.substring(21, 22) + "*";
		    	}
		    	// SEEK SUBSTR(m.afpname,17,4) + SUBSTR(m.afpname,26,8) + SUBSTR(m.afpname,36,3) + SUBSTR(m.afpname,22,1) + SUBSTR(m.afpname,34,1)
		    	// && for SG, oversea, brunei
		    	else {
		    		country = fileName.substring(16, 20);
		    		filePattern = fileName.substring(25, 33) +  fileName.substring(35, 38) ;
		    		typeOfFile = fileName.substring(21, 22);
		    		speaker = fileName.substring(33, 34);
		    	}
			}
			//回壓pattern, 可再log 上顯示, 方便若找不到 job_code 時, 使用
	    	formater.setPattern(filePattern);

	    	String queryString = "select  {a.*} " 
	    		+ " from amex a " 
	    		+ " where a.country = ? and a.afp_name= ? and a.typefile=? and a.speaker=? " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", Amex.class);
		    sqlQuery.setParameter(0, country);
		    sqlQuery.setParameter(1, filePattern);
		    sqlQuery.setParameter(2, typeOfFile);
		    sqlQuery.setParameter(3, speaker);
 
			List retList = sqlQuery.list();
			if (null!=retList && retList.size() == 1  ) {
				Amex amex = (Amex)retList.get(0);
				JobCode jobCode = jobcodeService.findById(amex.getJobCodeNo());
	        	if (null== jobCode ) 
	        		log.warn("amex mapping : 工單樣本被刪除" + amex.getJobCodeNo()+ " :" + formater.toString());	
	        	else {
	        		log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	  
	        		jobCode.setLogFileSeq(logFileSEQ);
	        	}
	        	//若macReport有值,  表示要再加印 mac report job bag.
	        	/*  不產生mac report了
	        	if (null!= amex.getMacreport() && amex.getMacreport().length() > 0) {
	        		Boolean ret = generateMacreport(amex.getMacreport());
	        		if (!ret) {
		        		log.error("generateMacreport error " + amex.getMacreport());		        			
	        		}
	        	}
	        	*/
	        	// end macReport 
				return jobCode;
			} 
			else if (null!=retList && retList.size()==2  )  {
				//多筆 amex 處理
				Amex amex1 = (Amex)retList.get(0);
				Amex amex2 = (Amex)retList.get(1);
				int pageSplit = getPageSplit(amex1, amex2);
				
				
	        	//若macReport有值,  表示要再加印 mac report job bag.
				/*
	        	if ( (null!= amex1.getMacreport() && amex1.getMacreport().length() > 0) || (null!= amex2.getMacreport() && amex2.getMacreport().length() > 0)) {
	        		Boolean ret = generateMacreport(amex1.getMacreport());
	        		if (!ret) {
		        		log.error("generateMacreport error " + amex1.getMacreport());		        			
	        		}
	        	}
	        	*/
	        	// end macReport 
	        	
				JobCode jobCode = null;
				if ( Integer.parseInt(formater.getPages()) > pageSplit && null!= amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("1") )
					jobCode = jobcodeService.findById(amex1.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPages()) > pageSplit && null!= amex2.getSpecial()&& amex2.getSpecial().equalsIgnoreCase("1") )
					jobCode = jobcodeService.findById(amex2.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPages()) < pageSplit && null!= amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("") )
					jobCode = jobcodeService.findById(amex1.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPages()) < pageSplit && null!= amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("") )
					jobCode = jobcodeService.findById(amex2.getJobCodeNo());		

				if (null!= jobCode) {
					log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	 
					jobCode.setLogFileSeq(logFileSEQ);
					return jobCode;					
				} else {
					log.warn("2筆的 amexNo Matcing : " + formater.toString());	        	
					return jobCode;		
				}
				
		    }
			else if (null!=retList && retList.size()>2  )  {
				log.warn("3筆以上的 amex 處理 :" + formater.toString());
				return null;
		    }			
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
		
		log.error("未知錯誤 :" + formater.toString());
		return null;
	}	
	
	/*
	 * 產生 macReport 工單
	 */
	/*
	private static Boolean generateMacreport(String jobCodeNoMacReport) {
		try {
			JobCode jobCode_macReport = jobcodeService.findById(jobCodeNoMacReport);
	    	if (null== jobCode_macReport )  {
	    		log.warn("amex mapping : 工單樣本被刪除" + jobCode_macReport.getJobCodeNo());	
	    		return false; 
	    	}
	    	else {
	    		log.info("Macreport mapping : " + jobCode_macReport.getJobCodeNo());	  
	    		jobCode_macReport.setLogFileSeq("001");
	    		
	    		JobBag jobbag = jobbagService.createNewJobBag(jobCode_macReport, 0, 0,  0,  "" ,  new Date(),  new Date(),"",0,0,0,0,0,0,0,0,"",0,0,0,0,0,0,0);
	    		if (null == jobbag)
	    			return false;
	    		else 
	    			return true;
	    	}		
		} catch (RuntimeException re) {
			log.error("generateMacreport error", re);
			return null;
		}    	
	}
*/
	public static JobCode findJobCodeByCiticard(CiticardFormater formater) {
		String custNo = formater.getClientID();
		String fileName = formater.getAfpFileName();
		Integer fileLength = formater.getAfpFileName().length();
		String filePattern="";
		String logFileSEQ="001"; //預設值 為 001, 表是第一批
		
		try {

	    	//LEFT(UPPER(filename),4) + "DD" + SUBSTR(UPPER(filename),7,2)
	    	filePattern = fileName.substring(0, 4) + "DD" + fileName.substring(6, 8);
			//回壓pattern, 可再log 上顯示, 方便若找不到 job_code 時, 使用
	    	formater.setPattern(filePattern);

			//先直接找job_code
			 List<JobCode> jobCodeList = null;
			 selectFromCustId(custNo);
			 String fileNameLowCase = fileName.toLowerCase();
			 
			 jobCodeList = match(custNo, fileNameLowCase);
			 
			 if(jobCodeList != null && jobCodeList.size() == 1){
			     JobCode jobCode = jobCodeList.get(0);
				 jobCode.setLogFileSeq(logFileSEQ);
				 return jobCode;
			 }			    				   	   

	    	
	    	
	    	String queryString = "select  {a.*} " 
	    		+ " from amex a " 
	    		+ " where a.country = ? and a.afp_name= ?  " ;
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", Amex.class);
		    sqlQuery.setParameter(0, formater.getClientID());
		    sqlQuery.setParameter(1, filePattern);	


			List retList = sqlQuery.list();
			if (null!=retList && retList.size() == 1  ) {
				Amex amex = (Amex)retList.get(0);
				JobCode jobCode = jobcodeService.findById(amex.getJobCodeNo());
	        	if (null== jobCode ) 
	        		log.warn("amex mapping : 工單樣本被刪除" + amex.getJobCodeNo()+ " :" + formater.toString());	
	        	else {
	        		log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	 
	        		jobCode.setLogFileSeq(logFileSEQ);
	        	}
				return jobCode;
			} else if (null!=retList && retList.size()==2  )  {
				//多筆 amex 處理
				Amex amex1 = (Amex)retList.get(0);
				Amex amex2 = (Amex)retList.get(1);
				int pageSplit = getPageSplit(amex1, amex2);
				
				JobCode jobCode = null;
				if ( Integer.parseInt(formater.getPageno()) > pageSplit && null!= amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("1") )
					jobCode = jobcodeService.findById(amex1.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPageno()) > pageSplit && null!= amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("1") )
					jobCode = jobcodeService.findById(amex2.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPageno()) < pageSplit && null!= amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("") )
					jobCode = jobcodeService.findById(amex1.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPageno()) < pageSplit && null!= amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("") )
					jobCode = jobcodeService.findById(amex2.getJobCodeNo());		

				if (null!= jobCode) {
					log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());
					jobCode.setLogFileSeq(logFileSEQ);
					return jobCode;					
				} else {
					log.warn("2筆的 amexNo Matcing : " + formater.toString());	        	
					return jobCode;
				}
				
		    }
			else if (null!=retList && retList.size()>2  )  {
				log.warn("3筆以上的 amex 處理 :" + formater.toString());
				return null;
		    }			
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
		
		log.warn("未知錯誤 :" + formater.toString());
		return null;
	}
	
	
	public static JobCode findJobCodeByOneGold(OneGoldFormater formater) {
		String custNo = formater.getClientID();
		String fileName = formater.getAfpFileName();
		Integer fileLength = formater.getAfpFileName().length();
		String filePattern="";
		Boolean isDdn = false;
		Boolean isMmdd = false;
		Boolean isdd = false;
		String logFileSEQ="001"; //預設值 為 001, 表是第一批
		
		try {
	    	String queryString = "select  {a.*} " 
	    		+ " from amex a " ;
	
	    	SQLQuery sqlQuery = null;
			if (formater.getEnvelop().equalsIgnoreCase("0")) {
				queryString = queryString +  " where a.country = ? and a.afp_name= ? and a.typefile=? and a.speaker=? " ;

			    sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
			    sqlQuery.addEntity("a", Amex.class);
			    sqlQuery.setParameter(0, formater.getClientID());
			    sqlQuery.setParameter(1, formater.getStattype().substring(0, 1) + formater.getType());	//
			    sqlQuery.setParameter(2, formater.getBiz()); 	//Typefile(biz)	
			    sqlQuery.setParameter(3, formater.getCS());	//Speaker(cs)

			} else {
				queryString = queryString +  " where a.country = ? and a.afp_name= ? and a.typefile=? and a.speaker=? and a.special=?" ;
			    sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
			    sqlQuery.addEntity("a", Amex.class);
			    sqlQuery.setParameter(0, formater.getClientID());
			    sqlQuery.setParameter(1, formater.getStattype().substring(0, 1) + formater.getType());	//
			    sqlQuery.setParameter(2, formater.getBiz()); 	//Typefile(biz)	
			    sqlQuery.setParameter(3, formater.getCS());	//Speaker(cs)	
			    sqlQuery.setParameter(4, formater.getEnvelop());	//Special(envelop)
			}
			

			List retList = sqlQuery.list();
			if (null!=retList && retList.size() == 1  ) {
				Amex amex = (Amex)retList.get(0);
				JobCode jobCode = jobcodeService.findById(amex.getJobCodeNo());
	        	if (null== jobCode ) 
	        		log.warn("amex mapping : 工單樣本被刪除" + amex.getJobCodeNo()+ " :" + formater.toString());	
	        	else {
	        		jobCode.setLogFileSeq(logFileSEQ);
	        		log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	
	        	}
				return jobCode;
			} 
			else if (null!=retList && retList.size()==2  )  {
				//多筆 amex 處理
				Amex amex1 = (Amex)retList.get(0);
				Amex amex2 = (Amex)retList.get(1);
				int pageSplit = getPageSplit(amex1, amex2);
				JobCode jobCode = null;
				if ( Integer.parseInt(formater.getTotpage()) > pageSplit && null!=amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("1") )
					jobCode = jobcodeService.findById(amex1.getJobCodeNo());
				else if ( Integer.parseInt(formater.getTotpage()) >pageSplit && null!=amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("1") )
					jobCode = jobcodeService.findById(amex2.getJobCodeNo());
				else if ( Integer.parseInt(formater.getTotpage()) <pageSplit && null!=amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("") )
					jobCode = jobcodeService.findById(amex1.getJobCodeNo());
				else if ( Integer.parseInt(formater.getTotpage()) <pageSplit && null!=amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("") )
					jobCode = jobcodeService.findById(amex2.getJobCodeNo());		

				if (null!= jobCode) {
					log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	  
					jobCode.setLogFileSeq(logFileSEQ);
					return jobCode;					
				} else {
					log.warn("2筆的 amexNo Matcing : " + formater.toString());	        	
					return jobCode;		
				}
		    }
			else if (null!=retList && retList.size()>2  )  {

				log.warn("3筆以上的 amex 處理 :" + retList.size() + "筆 : " +  formater.toString());
				return null;
		    }			
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
		
		log.warn("未知錯誤 :" + formater.toString());
		return null;
	}
	
	
	public static JobCode findJobCodeByHScard(HScardFormater formater) {
		String custNo = formater.getClientID();
		String fileName = formater.getAfpFileName();
		Integer fileLength = formater.getAfpFileName().length();
		String filePattern="";
		Boolean isDdn = false;
		Boolean isMmdd = false;
		Boolean isdd = false;
		String logFileSEQ="001"; //預設值 為 001, 表是第一批
		
		try {
			//先以 前三碼 + cycledate.DD + 最後一碼, 查amex table 
			//若沒有 改為  前三碼 + "dd" + 最後一碼, 查amex table
			//GXM191.AFP 
			String cycleDateDD = formater.getCycleDate().substring(2,4);//mmdd
	    	filePattern = fileName.substring(0, 3) + cycleDateDD + fileName.substring(5, 6);
	    	formater.setPattern(filePattern);

	    	String queryString = "select  {a.*} " 
	    		+ " from amex a " 
	    		+ " where a.country = ? and a.afp_name= ?  " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", Amex.class);
		    sqlQuery.setParameter(0, formater.getClientID());
		    sqlQuery.setParameter(1, filePattern);
			List retList = sqlQuery.list();
			
			if (null==retList || (null!=retList && retList.size() == 0) ) {
				//若沒有 改為  前三碼 + "dd" + 最後一碼, 查amex table
		    	filePattern = fileName.substring(0, 3) + "dd" + fileName.substring(5, 6);
		    	formater.setPattern(filePattern);
			    sqlQuery.setParameter(1, filePattern);
				retList = sqlQuery.list();
				
			    if (null==retList || (null!=retList && retList.size() == 0) ) {
					   //如果都找不到，直接找job_code
					   List<JobCode> jobCodeList = null;
					   selectFromCustId(custNo);
					   String fileNameLowCase = fileName.toLowerCase();
					   if(fileNameLowCase.indexOf(".afp") > 0 || fileNameLowCase.indexOf(".pdf") > 0){
						   fileNameLowCase = fileNameLowCase.replaceAll("\\.afp", "");
						   fileNameLowCase = fileNameLowCase.replaceAll("\\.pdf", "");
						   jobCodeList = match(custNo, fileNameLowCase);
					   }
					   if(jobCodeList != null && jobCodeList.size() == 1){
						   JobCode jobCode = jobCodeList.get(0);
						   jobCode.setLogFileSeq(logFileSEQ);
						   return jobCode;
					   }else{
						   log.warn("No Matcing :" + formater.toString());
						   return null;					
					   }			    				   	   
			    }		
			}			
			
			
			
			if (null!=retList && retList.size() == 1  ) {
				Amex amex = (Amex)retList.get(0);
				JobCode jobCode = jobcodeService.findById(amex.getJobCodeNo());
	        	if (null== jobCode ) 
	        		log.warn("amex mapping : 工單樣本被刪除" + amex.getJobCodeNo()+ " :" + formater.toString());	
	        	else {
	        		jobCode.setLogFileSeq(logFileSEQ);
	        		log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	
	        	}
				return jobCode;
			} 
			else if (null!=retList && retList.size()==2  )  {
				//多筆 amex 處理
				Amex amex1 = (Amex)retList.get(0);
				Amex amex2 = (Amex)retList.get(1);
				int pageSplit = getPageSplit(amex1, amex2);
				JobCode jobCode = null;
				if ( Integer.parseInt(formater.getPageno()) > pageSplit && null!=amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("1") )
					jobCode = jobcodeService.findById(amex1.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPageno()) >pageSplit && null!=amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("1") )
					jobCode = jobcodeService.findById(amex2.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPageno()) <pageSplit && null!=amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("") )
					jobCode = jobcodeService.findById(amex1.getJobCodeNo());
				else if ( Integer.parseInt(formater.getPageno()) <pageSplit && null!=amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("") )
					jobCode = jobcodeService.findById(amex2.getJobCodeNo());		

				if (null!= jobCode) {
					log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	  
					jobCode.setLogFileSeq(logFileSEQ);
					return jobCode;					
				} else {
					log.warn("2筆的 amexNo Matcing : " + formater.toString());	        	
					return jobCode;		
				}
		    }
			else if (null!=retList && retList.size()>2  )  {

				log.warn("3筆以上的 amex 處理 :" + retList.size() + "筆 : " +  formater.toString());
				return null;
		    }			
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		
		
		log.warn("未知錯誤 :" + formater.toString());
		return null;
	}
	
	
	public static JobCode findJobCodeByLogistic(LogisticFormater formater) {
		String custNo = formater.getClientID();
		String fileName = formater.getAfpFileName();
		//設定此正規表示法Map
		AmexService.selectFromCustId(custNo);
		
		String filePattern="";
		String typeOfFile="";
		String speaker ="";
		String logFileSEQ="001"; //預設值 為 001, 表示是第一批

		//AE Mac Report來自logistic檔，因為是以工作時間去計算cycle date，有可能mac report和Amex檔案的cycle date不同，要調成一樣
		if (custNo.equalsIgnoreCase("AE")) {
			try{
 			   //找出cycle date最近的job bag
			   String queryString = "select top 1  {j.*} from  job_bag j  where  j.LOG_FILENAME like 'DFTW%' and j.idf_CUST_NO = 'AE' order by j.CYCLE_DATE desc" ;
	    	   SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		       sqlQuery.addEntity("j", JobBag.class);
		       List retList = sqlQuery.list();
		       if(retList != null && retList.size() > 0){
		    	   JobBag aeJobBag = (JobBag)retList.get(0);
		    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		    	   String cycleDate_str = formater.getCycleDate();
		    	   if (cycleDate_str.length() >0 && !cycleDate_str.equalsIgnoreCase("　　　　　")) {
                	   String cycleDateArray[] = cycleDate_str.split("/");
                	   if (cycleDateArray.length ==3 ) {
                		   String YYYY= cycleDateArray[0];
                		   String MM= cycleDateArray[1];
                		   String DD= cycleDateArray[2];
                		
                		   if (YYYY.length() < 3) 
                			   YYYY = "20"+ YYYY;
                		
                		   if (MM.length() < 2) 
                  			   MM = "0"+ MM;
                		
                		   if (DD.length() < 2) 
                			   DD = "0"+ DD;			                        		
                		   cycleDate_str = YYYY +"/" + MM+ "/" + DD;
                	   }		    	
		           }
		    	   
		    	   //如果Mac report的cycle date在job_bag之後，並且在Mac report的cycle date - 1日間時改成job_bag的cycleDate
		    	   if(aeJobBag.getCycleDate() != null && !sdf.format(aeJobBag.getCycleDate()).equals(cycleDate_str)){
		    		   try {
						   if(sdf.parse(cycleDate_str).getTime() > aeJobBag.getCycleDate().getTime() 
								   && aeJobBag.getCycleDate().getTime() >= sdf.parse(cycleDate_str).getTime() - (24 * 60 * 60 * 1000) ){
							   formater.setCycleDate(sdf.format(aeJobBag.getCycleDate()));
						   }
					   } catch (ParseException e) {
						   log.error("date parse error", e);
						   e.printStackTrace();
					   }
		    	   }
		       }
			}catch(Exception e){
				log.error("", e);
				e.printStackTrace();
			}
		    	
		}
		

		if (null == formater.getPattern() || "".equals(formater.getPattern().trim())) {		
			//沒有傳filePattern時進入此處
			List<JobCode> jobCodeList = null;
			//正規表示法轉換
			 selectFromCustId(custNo);
			 String fileNameLowCase = fileName.toLowerCase().trim();
			 if(fileNameLowCase.endsWith(".afp")){
			    fileNameLowCase = fileNameLowCase.replaceAll(".afp", "");					   
			 }else if(fileNameLowCase.endsWith(".pdf")){
			    fileNameLowCase = fileNameLowCase.replaceAll(".pdf", "");
			 }
			 //找出filepattern符合的工單樣本有幾個			 
			 if("IN".equals(custNo)){
				 //ING與富邦已合併 
				 jobCodeList = match("FB", fileNameLowCase);
			 }else{
			     jobCodeList = match(custNo, fileNameLowCase);
			 }
			 if(jobCodeList != null && jobCodeList.size() == 1){
				 //正規表示法找到filePattern且只有一個時
			     JobCode jobCode = jobCodeList.get(0);
				 if(formater.getSEQ() != null && formater.getSEQ().trim().length() > 0){
				    logFileSEQ = formater.getSEQ();
				 }
				 jobCode.setLogFileSeq(logFileSEQ);
				 formater.setPattern(jobCode.getFilename());				 
				 return jobCode;
		     }else if(jobCodeList != null && jobCodeList.size() >= 2 ){
		    	 String filePatt = null;
		    	 String jobCodeNoList = "";
		    	 boolean returnNull = false;
		    	 for(JobCode jobCode : jobCodeList){
		    		 jobCodeNoList += jobCode.getJobCodeNo() + ",";
		    		 if(jobCode.getFilename() == null)
		    			 returnNull = true;
		    		 //如果多筆工單樣本的filePattern不相同時，無法決定使用那一個
		    		 if(filePatt != null && jobCode.getFilename() != null && !filePatt.equals(jobCode.getFilename().trim())){
		    			 returnNull = true;					
		    		 }
		    		 filePatt = jobCode.getFilename().trim();
		    	 }
		    	 if(returnNull){
		    		 formater.setPattern("查出多筆工單樣本" + jobCodeNoList + "，JBM無法決定");
		    		 return null;
		    	 }
		    	 
				 //有多筆相同filePattern時轉往amex table處理
				 filePattern = jobCodeList.get(0).getFilename().trim();
				 if(formater.getSEQ() != null && formater.getSEQ().trim().length() > 0){
				    logFileSEQ = formater.getSEQ();
				 }
			 }else{
				 //如果正規表示法無法解析，轉往各客戶特別規則解析
				 Map<String, LogisticParsingService> map = getLogisticParsingServices();
				 LogisticParsingService logisticParsingService = map.get(custNo.toUpperCase());
				 if(logisticParsingService == null){
					 formater.setPattern("未設定客戶的logistic parsing service");
					 return null;
				 }
				 LogisticParsingObj lpo = logisticParsingService.parseFilePattern(formater);
				 if(lpo == null){
					 formater.setPattern("未傳入file pattern，也無法解析出file pattern");
					 return null;
				 }else if(lpo.getJobCode() != null){
					 //如果能直接解出JobCode就回送JobCode
					 formater.setPattern(lpo.getJobCode().getFilename());
					 return lpo.getJobCode();
			     }else{
			    	 filePattern = lpo.getFilePattern();
			 		 typeOfFile = lpo.getTypeOfFile();
					 speaker = lpo.getSpeaker();
			     }
			 }

		} else {
			filePattern = formater.getPattern();
			logFileSEQ = formater.getSEQ();
			log.info("Matcing from pattern:" + formater.toString());
			
		}
		
		if (filePattern == null || filePattern.length()==0  || filePattern.equalsIgnoreCase("Error")) 
			return null;
		
		
		//回壓pattern, 可在log 上顯示, 方便若找不到 job_code 時, 使用
    	formater.setPattern(filePattern);
    	
    	
		//從 amex, job_code table 找出 jobcode 
		//優先找 job_code , 若job_code為唯一就回傳
    	//若沒有,  再找 amex table 
		try {
			
			SQLQuery sqlQuery = null;
			String queryString ="";
			
	        //由 job_code table 尋找
	    	queryString = "select  {j.*} " 
	    		+ " from job_code j " 
	        	+ " where j.idf_CUST_NO = ? and  j.FILENAME = ? and j.IS_ENABLED_CONTRACT = 1" ;

		    sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("j", JobCode.class);

		    sqlQuery.setParameter(0, formater.getClientID());
		    sqlQuery.setParameter(1, filePattern);

	        List retList = sqlQuery.list();
	        if (null!=retList && retList.size() == 1  ) {
	        	log.info("job_code mapping : " + ((JobCode)retList.get(0)).getJobCodeNo()+ " :" + formater.toString());
	        	
	        	JobCode jobCode = (JobCode)retList.get(0);
	        	jobCode.setLogFileSeq(logFileSEQ);
	        	return jobCode;
	        } else if (null!=retList && retList.size() >= 2  && formater.getPages() != null && !"".equals(formater.getPages().trim()))  {
	        	queryString = "select  {j.*} " 
	    	    		+ " from job_code j " 
	    	        	+ " where j.idf_CUST_NO = ? and  j.FILENAME = ? and (j.fromPages is not null or j.endPages is not null) "
	    	        	+ " and j.IS_ENABLED_CONTRACT = 1 order by job_code_no";

	    		sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
	    		sqlQuery.addEntity("j", JobCode.class);
	    		sqlQuery.setParameter(0, formater.getClientID());
	    		sqlQuery.setParameter(1, filePattern);
	    		retList = sqlQuery.list();
	    		    
	    		if (null!=retList && retList.size() >= 1  ) {
	    			for(int i = 0 ; i < retList.size() ; i++){
	    				JobCode jc = (JobCode)retList.get(i);	    				
	    				int fromPages = jc.getFromPages() == null ? 1 : jc.getFromPages().intValue();
	    				int endPages = jc.getEndPages() == null ? Integer.MAX_VALUE : jc.getEndPages().intValue();
	    				//如果是在設定範圍內的話就回傳
	    				if(formater.getPages() != null && !formater.getPages().equals("") 
	    						&& new Integer(formater.getPages()).intValue() >= fromPages && new Integer(formater.getPages()).intValue() <= endPages){
	    					log.info("muti job_code, job_code mapping : " + jc.getJobCodeNo()+ " :" + formater.toString());	    	        		    	    	       	
	    	    	       	jc.setLogFileSeq(logFileSEQ);
	    	    	       	return jc;
	    				}
	    			}	    				    	      	
	    	    }
	    		SysLog syslog = new SysLog();
    			syslog.setLogType("MUlTI_JOB_CODE");
    			syslog.setSubject("Job code couldn't match ");
    			syslog.setMessageBody(formater.getClientID() + "|" + filePattern + "|" + formater.getAfpFileName() + "|" + formater.getLogFileName());
    			syslog.setIsException(true);            			            			
    			syslog.setCreateDate(new Date());
    			SyslogService.getInstance().save(syslog);

	        	//多筆 job_code 處理
	        	log.info("多筆 job_code 處理 :共" + retList.size() + "筆 : " +  formater.toString() + "轉往amex table處理")  ;
	        } else {
	        	//amex, job_code 找不到對應的 job_code 
	        	log.warn("job_code 裡找不到對應的 job_code :" + filePattern + ":" + formater.toString());
	        }

			//以下是以頁數區分的舊寫法，之後將全部轉往 JobCode處理就好，amex table只處理amex的事務
			if (typeOfFile.equalsIgnoreCase("") && speaker.equalsIgnoreCase("")) {
		    	queryString = "select  {a.*} " 
		    		+ " from amex a " 
		        	+ " where a.country = ? and a.afp_name= ? " ;

			    sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
			    sqlQuery.addEntity("a", Amex.class);
			    sqlQuery.setParameter(0, formater.getClientID());
			    sqlQuery.setParameter(1, filePattern);				
			}
			else {
		    	queryString = "select  {a.*} " 
		    		+ " from amex a " 
		        	+ " where a.country = ? and a.afp_name= ? and a.typefile =? and  a.speaker= ? " ;

			    sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
			    sqlQuery.addEntity("a", Amex.class);
			    sqlQuery.setParameter(0, formater.getClientID());
			    sqlQuery.setParameter(1, filePattern);	
			    sqlQuery.setParameter(2, typeOfFile);
			    sqlQuery.setParameter(3, speaker);
			}

	        retList = sqlQuery.list();
	        if (null!=retList && retList.size() == 1) {
	        	Amex amex = (Amex)retList.get(0);
	        	JobCode jobCode = jobcodeService.findById(amex.getJobCodeNo());
	        	if (null== jobCode ) 
	        		log.warn("amex mapping : 工單樣本被刪除" + amex.getJobCodeNo()+ " :" + formater.toString());	
	        	else {
		        	jobCode.setLogFileSeq(logFileSEQ);	        		
	        		log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	
	        	}

	        	return jobCode;
	        }else if (null!=retList && retList.size() == 2  )  {
	        	//2筆 amex 處理  Thor修改為可依page處理
				Amex amex1 = (Amex)retList.get(0);
				Amex amex2 = (Amex)retList.get(1);
				int pageSplit = getPageSplit(amex1, amex2);
				
				JobCode jobCode = null;
				String JobCodeNo="";
				if ( Integer.parseInt(formater.getPages()) >= pageSplit && null!=amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("1") )
					JobCodeNo = amex1.getJobCodeNo();
				else if ( Integer.parseInt(formater.getPages()) >= pageSplit && null!=amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("1") )
					JobCodeNo = amex2.getJobCodeNo();
				else if ( Integer.parseInt(formater.getPages()) < pageSplit && null!=amex1.getSpecial() && amex1.getSpecial().equalsIgnoreCase("") )
					JobCodeNo = amex1.getJobCodeNo();
				else if ( Integer.parseInt(formater.getPages()) < pageSplit && null!=amex2.getSpecial() && amex2.getSpecial().equalsIgnoreCase("") )
					JobCodeNo = amex2.getJobCodeNo();

				jobCode = jobcodeService.findById(JobCodeNo);
				
				if (null!= jobCode) {
					log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	   
					
					jobCode.setLogFileSeq(logFileSEQ);
					return jobCode;					
				} else if (JobCodeNo=="") {
					log.warn("2筆的 amexNo Matcing : " + formater.toString());	  
					//jobCode.setLogFileSeq(logFileSEQ);
					return jobCode;
				} else {
					log.warn("2筆的工單樣本被刪除" + JobCodeNo + " :" + formater.toString());	 	
				}	        	
	        } else if (null!=retList && retList.size() > 2  )  {
	        	//多筆 amex 處理
	        	log.warn("多筆 amex 處理 :共" + retList.size() + "筆 : " +  formater.toString());
	        } else {
	        	log.warn(" amex 和 job_code 處理 :均找不到filePattern" + filePattern);
	        }	        
		} catch (RuntimeException re) {
			log.error("info:" +  filePattern + ":" + formater.toString());
			log.error("", re);
			return null;
		}
		return null;
	}
	
	//把yyyymmddhhmmssnn轉成正規表示法 ，放入map中
	private static void selectFromCustId(String custNo){
		if(!custFilePatternMap.containsKey(custNo.toUpperCase())){
	       String queryStr = "select {j.*} from job_code j where j.idf_CUST_NO = ? and j.IS_ENABLED_CONTRACT = 1";
	       SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryStr);
	       sqlQuery.addEntity("j", JobCode.class);
	       sqlQuery.setParameter(0, custNo);	    
		   List<JobCode> retList = sqlQuery.list();
		   if(HibernateSessionFactory.getSession().isOpen())
	          HibernateSessionFactory.closeSession();

		
		   Map<JobCode, String> filePatternMap = new HashMap<JobCode, String> ();
		   for(JobCode jobCode: retList){
			   String jobCodeNo = jobCode.getJobCodeNo();
			   String filePattern = null ;
			   if(jobCode.getFilename() != null)
			      filePattern = jobCode.getFilename().trim();
			   if(filePattern != null){
				  //分和月一樣都是mm標註，只能看它是不是和dd及hh相連來判斷是分或月，所以先把ddmm和hhmm轉換
			      if(filePattern.indexOf("ddmm") >= 0){
				      filePattern = filePattern.replaceAll("ddmm", "(0[1-9]|1[0-9]|2[0-9]|3[0-1])([0-5][0-9])");
			      }
			      if(filePattern.indexOf("hhmm") >= 0){
				      filePattern = filePattern.replaceAll("hhmm", "([0-1][0-9]|2[0-3])([0-5][0-9])");
			      }
			      //年有三種，沒有3開頭的，此系統不可能用一千年 <-- I assert it
			      if(filePattern.indexOf("yyyy") >= 0){
				      filePattern = filePattern.replaceAll("yyyy", "(1[0-9][0-9][0-9]|2[0-9][0-9][0-9])");
			      }
			      if(filePattern.indexOf("yyy") >= 0){
				      filePattern = filePattern.replaceAll("yyy", "([0-9][0-9][0-9])");
			      }
			      if(filePattern.indexOf("yy") >= 0){
				      filePattern = filePattern.replaceAll("yy", "([0-9][0-9])");
			      }
			      //月
			      if(filePattern.indexOf("mm") >= 0){
				      filePattern = filePattern.replaceAll("mm", "(0[1-9]|1[0-2])");
			      }
			      //日
			      if(filePattern.indexOf("dd") >= 0){
				      filePattern = filePattern.replaceAll("dd", "(0[1-9]|1[0-9]|2[0-9]|3[0-1])");
			      }
			      //秒
			      if(filePattern.indexOf("ss") >= 0){
				      filePattern = filePattern.replaceAll("ss", "([0-5][0-9])");
			      }
			      //批號
			      if(filePattern.indexOf("nnnn") >= 0){
				      filePattern = filePattern.replaceAll("nnnn", "(0[0-9][0-9][1-9]|[1-9][0-9][0-9][0-9])");
			      }
			      //批號
			      if(filePattern.indexOf("nnn") >= 0){
				      filePattern = filePattern.replaceAll("nnn", "(0[0-9][1-9]|[1-9][0-9][0-9])");
			      }
			      //批號
			      if(filePattern.indexOf("nn") >= 0){
				      filePattern = filePattern.replaceAll("nn", "(0[1-9]|[1-9][0-9])");
			      }
			      //批號
			      if(filePattern.indexOf("n") >= 0){
				      filePattern = filePattern.replaceAll("n", "([1-9])");
			      }
			      filePattern = filePattern.toUpperCase();
			      filePatternMap.put(jobCode, filePattern);
			   }
		   }
		   custFilePatternMap.put(custNo.toUpperCase(), filePatternMap);
		
		   //return retList;
		}
		//else
			//return null;
	}
	
	//找出檔名符合pattern的JobCode
	private static List<JobCode> match(String custNo, String fileName){
		Map<JobCode, String> filePatterns =  custFilePatternMap.get(custNo.toUpperCase());
		List<JobCode> retList = new ArrayList<JobCode>();
		if(filePatterns != null){
			Set<JobCode> keySet = filePatterns.keySet();
			for(JobCode jobCode: keySet){
				String filePattern = filePatterns.get(jobCode);				
				fileName = fileName.toUpperCase();	
				if(filePattern != null){
				   if(fileName.matches(filePattern)){
					   retList.add(jobCode);
				   }
				}
			}
		}
		return retList;
		
	}
		
	//解析切檔的頁數多寡  Thor add
    static private int getPageSplit(Amex amex1, Amex amex2){
    	int pageSplit = 3000; //預設為3000
    	if(amex1 == null || amex2 == null)
    		return pageSplit;
    	int page1 = (null == amex1.getPages())? 0 : amex1.getPages();
		int page2 = (null == amex2.getPages())? 0 : amex2.getPages();
		
		if(page1 == page2 && page1 == 0){
			pageSplit = 3000;
		}else{
			if(page1 > page2)
				pageSplit = page1;
			else 
				pageSplit = page2;
		}
		return pageSplit;
    	
    }
    public static Map<String, Map<JobCode, String>> getCustFilePatternMap() {
		return custFilePatternMap;
	}

	public static void setReloaded() {
		AmexService.custFilePatternMap = new HashMap<String, Map<JobCode, String>>();
	}
	
	public static Map<String, LogisticParsingService> getLogisticParsingServices() {
		return logisticParsingServices;
	}

	public void setLogisticParsingServices(
			Map<String, LogisticParsingService> logisticParsingServices) {
		AmexService.logisticParsingServices = logisticParsingServices;
	}

}
