package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.salmat.jbm.bean.LCFormBean;
import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.*;
import com.painter.util.EnCryptUtils;
import com.painter.util.Global;
import com.painter.util.MailUtil;
import com.painter.util.Util;


import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.String;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;


public class LCInfoService extends LcinfoDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(LCInfoService.class);
    private static final LCFormService lcFormService = LCFormService.getInstance();
    private static final LGInfoService lginfoService = LGInfoService.getInstance();
    private static final FbRegisterNosService fbRegisterNosService = FbRegisterNosService.getInstance();
    
    
    /* Constants */
    private static final LCInfoService instance = new LCInfoService();
    //private static CustomerDAO CustomerDao;

    
    private LCInfoService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static LCInfoService getInstance() {
        return instance;
    }
    
    /**
     * get getEffectiveLcInfo
     * return EffectiveLcInfo List
     */
	public List getEffectiveLcInfo() {
		
		
		
		try {
	    	String queryString = " select {a.*} "
	    		+ " from lcinfo a  "
	    		+ " where convert(char, a.NEXT_EFFECTIVE_DATE, 101)   = convert(char, getdate(), 101) " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", Lcinfo.class);

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}	

    /* 批次產生 寄送的交寄管制表 */
	public String batchGenerateLCForm(String jobBagList, String templateName) {
		
		String querySession="";

		try {
			//合併明細代表列印同一型態之明細設定為否
			// Step1: 找出 標準格式合併明細的管制表 清單
			String queryString ="";
			//String template_name="STANDARD_POST_OFFICE";
			queryString =" select   POSTOFFICE.CODE_VALUE_TW postOfficeName, POSTOFFICE.F1 postOfficeTel ,POSTOFFICE.F2 postOfficeFax , POSTOFFICE.F3 postOfficeContact  ,lc.EXTRA_LG_TYPE1 extraLgType1,lc.EXTRA_LG_TYPE2 extraLgType2,lc.EXTRA_LG_TYPE3 extraLgType3,lc.EXTRA_LG_TYPE4 extraLgType4,lc.EXTRA_LG_TYPE5 extraLgType5 ,lc.EXTRA_LG_TYPE6 extraLgType6,  j.idf_CUST_NO custNo,   c.CUST_NAME custName, c1.CODE_VALUE_TW lgType, c2.CODE_VALUE_TW mailCategory, lc.LC_NO jobCodeNo, lc.PROG_NM  jobName, '' cycleDateList, ''  customerDept, price.price ,  sum(j.accounts) qty"
	    		+ " from job_bag j, lcinfo lc,  customer c, code c1, code c2, code POSTOFFICE, LG_price price  "
	    		+ " where  "
	    		+ " j.idf_CUST_NO = c.CUST_NO "
	    		+ " and j.CODE_MAIL_TO_POSTOFFICE = POSTOFFICE.id "  
	    		+ " and j.CODE_LG_TYPE = c1.id  "
	    		+ " and j.CODE_MAIL_CATEGORY = c2.id "
	    		+ " and j.job_bag_status in ('COMPLETED_MP','NON_MP','PRINTED_LG_FORM','COMPLETED_LG', 'COMPLETED_LP', 'PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_LP', 'ACCOUNTING_LOCKED') "  
	    		+ " and j.job_bag_no in (" + jobBagList+ ")"  
	    		+ " and j.idf_lc_no  = lc.LC_NO "
	    		+ " and lc.TEMPLATE='" + templateName + "' "
	    		+ " and lc.P_LIST = 1  and j.CODE_LG_TYPE = price.LG_TYPE and j.CODE_MAIL_CATEGORY = price.LG_MAIL_CATEGORY "
	    		+ " group by POSTOFFICE.CODE_VALUE_TW ,  POSTOFFICE.F1  ,POSTOFFICE.F2  ,POSTOFFICE.F3 ,lc.EXTRA_LG_TYPE1,lc.EXTRA_LG_TYPE2,lc.EXTRA_LG_TYPE3,lc.EXTRA_LG_TYPE4,lc.EXTRA_LG_TYPE5,lc.EXTRA_LG_TYPE6 , j.idf_CUST_NO, c.CUST_NAME, c1.CODE_VALUE_TW , c2.CODE_VALUE_TW , lc.LC_NO , lc.PROG_NM , price.price "
	    		+ " union  "
	    		+ " select  POSTOFFICE.CODE_VALUE_TW postOfficeName, POSTOFFICE.F1 postOfficeTel ,POSTOFFICE.F2 postOfficeFax , POSTOFFICE.F3 postOfficeContact  ,lc.EXTRA_LG_TYPE1 extraLgType1,lc.EXTRA_LG_TYPE2 extraLgType2,lc.EXTRA_LG_TYPE3 extraLgType3,lc.EXTRA_LG_TYPE4 extraLgType4,lc.EXTRA_LG_TYPE5 extraLgType5 ,lc.EXTRA_LG_TYPE6 extraLgType6, j.idf_CUST_NO custNo,   c.CUST_NAME custName, c1.CODE_VALUE_TW lgType, c2.CODE_VALUE_TW mailCategory, j.idf_JOB_CODE_NO jobCodeNo, j.PROG_NM + '##' +  j.job_bag_no jobName ,  '(' + convert(char(5),j.cycle_date,101) + ')' cycleDateList ,j.CUSTOMER_DEPT customerDept, price.price , sum(j.accounts) qty  "
	    		+ " from job_bag j, lcinfo lc,  customer c, code c1, code c2, code POSTOFFICE, LG_price price  "
	    		+ " where  "	    		 
	    		+ " j.idf_CUST_NO = c.CUST_NO "
	    		+ " and j.CODE_MAIL_TO_POSTOFFICE = POSTOFFICE.id "  
	    		+ " and j.CODE_LG_TYPE = c1.id "
	    		+ " and j.CODE_MAIL_CATEGORY = c2.id "
	    		+ " and j.job_bag_status in ('COMPLETED_MP','NON_MP','PRINTED_LG_FORM','COMPLETED_LG', 'COMPLETED_LP', 'PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_LP', 'ACCOUNTING_LOCKED')  "  
	    		+ " and j.job_bag_no in (" + jobBagList+ ")"  
	    		+ " and j.idf_lc_no  = lc.LC_NO "
	    		+ " and lc.TEMPLATE='" + templateName + "' "
	    		+ " and lc.P_LIST <> 1  and j.CODE_LG_TYPE = price.LG_TYPE and j.CODE_MAIL_CATEGORY = price.LG_MAIL_CATEGORY "
	    		+ " group by POSTOFFICE.CODE_VALUE_TW ,  POSTOFFICE.F1  ,POSTOFFICE.F2  ,POSTOFFICE.F3 , lc.EXTRA_LG_TYPE1,lc.EXTRA_LG_TYPE2,lc.EXTRA_LG_TYPE3,lc.EXTRA_LG_TYPE4,lc.EXTRA_LG_TYPE5,lc.EXTRA_LG_TYPE6 ,j.idf_CUST_NO, c.CUST_NAME, c1.CODE_VALUE_TW , c2.CODE_VALUE_TW , j.idf_JOB_CODE_NO , j.PROG_NM  , '(' + convert(char(5),j.cycle_date,101) + ')' , j.JOB_BAG_NO,  j.CUSTOMER_DEPT  , price.price"
	    		+ " order by POSTOFFICE.CODE_VALUE_TW ,  POSTOFFICE.F1  ,POSTOFFICE.F2  ,POSTOFFICE.F3 , j.idf_CUST_NO, c.CUST_NAME, c1.CODE_VALUE_TW , c2.CODE_VALUE_TW , jobCodeNo , jobName , cycleDateList  " ; 


   		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
   		    System.out.println(sqlQuery);
   		    //sqlQuery.setResultTransformer(Transformers.aliasToBean(LCFormBean.class));
            List<Object [] > list = sqlQuery.list();
            List<FbRegisterNos> fbList = null;
            
            //找出fubon的掛號號碼
            if("FUBON".equals(templateName)){
            	fbList = fbRegisterNosService.getFromJobBagList(jobBagList);            	
            }
   	        List<LCFormBean> lcformList = new ArrayList<LCFormBean>();
    	    if (null == list || list.size() ==0) {
    	    	return ""; //若沒資料, 回傳空白的querysession
    	    }else {
    	    	for(Object[] objArr : list){
    	    		LCFormBean lcBean = new LCFormBean();
    
    	    		lcBean.setPostOfficeName((String)objArr[0]);
    	    		lcBean.setPostOfficeTel((String)objArr[1]);
    	    		lcBean.setPostOfficeFax((String)objArr[2]);
    	    		lcBean.setPostOfficeContact((String)objArr[3]);
    	    		lcBean.setExtraLgType1((String)objArr[4]);
    	    		lcBean.setExtraLgType2((String)objArr[5]);
    	    		lcBean.setExtraLgType3((String)objArr[6]);
    	    		lcBean.setExtraLgType4((String)objArr[7]);
    	    		lcBean.setExtraLgType5((String)objArr[8]);
    	    		lcBean.setExtraLgType6((String)objArr[9]);
    	    		lcBean.setCustNo((String)objArr[10]);
    	    		lcBean.setCustName((String)objArr[11]);
    	    		lcBean.setLgType((String)objArr[12]);
    	    		lcBean.setMailCategory((String)objArr[13]);
    	    		lcBean.setJobCodeNo((String)objArr[14]);
    	    		String jobName = (String)objArr[15];
    	    		if(jobName != null ){
    	    			int index = jobName.indexOf("##");
    	    			if(index >= 0){
    	    				jobName = jobName.substring(0, index);
    	    				String jobBagNo = ((String)objArr[15]).substring(index + 2);
    	    				lcBean.setJobBagNo(jobBagNo);
    	    			}
    	    			
    	    		}
    	    		lcBean.setJobName(jobName);
    	    		lcBean.setCycleDateList((String)objArr[16]);
    	    		lcBean.setCustomerDept((String)objArr[17]);
    	    		lcBean.setPrice((BigDecimal)objArr[18]) ;
    	    		lcBean.setQty((Integer)objArr[19]);
    	    		lcformList.add(lcBean);
    	    	}
    	    }
    	    
    		//取得系統時間
			Calendar calendar = Calendar.getInstance();
			Date now = new Date();
			calendar.setTime(now);    
			synchronized(this){
			   querySession = Long.toString(calendar.getTimeInMillis()); //將時間轉為 querySession
			   try {
				   Thread.sleep(1);
			   } catch (InterruptedException e) {
				   log.error("", e);
				   e.printStackTrace();
			   }
			}

        	//HibernateSessionFactory.getSession().clear();
        	//HibernateSessionFactory.getSession().getTransaction().begin();
        	
        	String salmatTel= Util.getString("jbm.salmat.Tel");
        	String salmatFax= Util.getString("jbm.salmat.Fax");
        	String salmatContact= Util.getString("jbm.salmat.Contact");
        	if(fbList != null){
        	   for(FbRegisterNos fbRegisterNos : fbList){
        	      fbRegisterNos.setQuerySession(querySession);
        	      fbRegisterNosService.save(fbRegisterNos);
        	   }
        	}
        	
	        for (int ii=0; ii<lcformList.size(); ii++) {
	        	LCFormBean lcFormBean = (LCFormBean)lcformList.get(ii);
	        	
	        	Lcform lcform = new Lcform();
	        	
	        	
	        	java.util.Date defaultValue = null;
	        	DateConverter converter = new DateConverter(defaultValue);
	        	ConvertUtils.register(converter, java.util.Date.class);    	        	    	        		
        		BeanUtils.copyProperties(lcform,lcFormBean);

        		lcform.setSalmatTel(salmatTel);
        		lcform.setSalmatFax(salmatFax);
        		lcform.setSalmatContact(salmatContact);
        		lcform.setQuerySession(querySession);
        		String cycleDateString="";
        		
        		// 若 CycleDateList == null , 表示合併列印明細
        		//須找到原工單, 再組合出 cycle date list 

        		if (lcform.getCycleDateList().length()==0) {
        			//List _jobBagList = lginfoService.getJobBagListByJobCode(jobBagList, lcFormBean.getJobCodeNo());
        			List _jobBagList = lginfoService.getJobBagListByJobCode(jobBagList, lcFormBean.getJobCodeNo());
    	        	for (int i=0; i<_jobBagList.size(); i++) {
    	        		JobBag _jobbag = (JobBag)_jobBagList.get(i);
    	        		String cycleDate = new SimpleDateFormat("yyyy/MM/dd").format(_jobbag.getCycleDate());
    	        		if (cycleDateString.indexOf(cycleDate.substring(5,10)) < 0) // 當cycle date 不存在時, 再加入
    	        			cycleDateString = cycleDateString + cycleDate.substring(5,10) + ",";
    	        	}     
            		cycleDateString =  "(" + cycleDateString + ")";
            		lcform.setCycleDateList(cycleDateString);    	        	
        		}
        		lcform.setOrderby(ii);
        		
        		//富邦專用 客戶單位 及 分區
        		if (lcform.getCustNo().equalsIgnoreCase("FB") && templateName.equalsIgnoreCase("FUBON")) {
        			String jobBagNo = lcFormBean.getJobBagNo();
        			lcform.setPrice(lcFormBean.getPrice().doubleValue());
        			
        			//找出掛號號碼
        			if(jobBagNo != null && !"".equals(jobBagNo.trim())){
        				if(fbList != null){
        				   String registerNos = "";
        				   for(FbRegisterNos fbRegisterNos : fbList){
        	        	       if(fbRegisterNos.getJobBagNo() != null && fbRegisterNos.getJobBagNo().equals(jobBagNo)){
        	        	    	   registerNos += fbRegisterNos.getRegisterNoBegin() + "~" + fbRegisterNos.getRegisterNoEnd() + "<br />";
        	        	       }
        	        	   }
        				   int lastBreakTag = registerNos.lastIndexOf("<br />");
        				   if(lastBreakTag >= 0)
        				      registerNos = registerNos.substring(0, lastBreakTag);
        				   lcform.setRegisterNos(registerNos);
        				}
        			}
        			
        		}

        		//富邦專用 客戶單位 及 分區
        		if (lcform.getCustNo().equalsIgnoreCase("FB") && templateName.equalsIgnoreCase("FUBON") && lcform.getQty().compareTo(3000) >=0) {
        			lcform.setMailToArea("本地\r外地\r雜項");  			
        		}

	        	lcFormService.save(lcform);


	        	//處理額外交寄方式1
        		if (null!=lcform.getExtraLgType1() && lcform.getExtraLgType1().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType1());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
        		}
        		
	        	//處理額外交寄方式2
        		if (null!=lcform.getExtraLgType2() && lcform.getExtraLgType2().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType2());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
      		
        		}
        		
	        	//處理額外交寄方式3
        		if (null!=lcform.getExtraLgType3() && lcform.getExtraLgType3().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType3());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
       		
        		}
        		
	        	//處理額外交寄方式4
        		if (null!=lcform.getExtraLgType4() && lcform.getExtraLgType4().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType4());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
         		
        		}
        		
	        	//處理額外交寄方式5
        		if (null!=lcform.getExtraLgType5() && lcform.getExtraLgType5().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType5());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
         		
        		}    
        		
	        	//處理額外交寄方式6
        		if (null!=lcform.getExtraLgType6() && lcform.getExtraLgType6().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType6());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
         		
        		}
        		

	        }
    	        
        	//HibernateSessionFactory.getSession().getTransaction().commit(); 
			
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return querySession;
		
	}
	
    /* 批次產生 寄送的交寄管制表 */
	public String batchGenerateReturnLCForm(String jobBagList, String templateName) {
		
		String querySession="";

		try {
			//合併明細代表列印同一型態之明細設定為否
			// Step1: 找出 標準格式合併明細的管制表 清單
			String queryString ="";
			//String template_name="STANDARD_POST_OFFICE";
			queryString =" select  j.ret_user_company postOfficeName, j.ret_user_tel postOfficeTel , '' postOfficeFax , j.ret_user_name postOfficeContact  ,lc.EXTRA_LG_TYPE1 extraLgType1,lc.EXTRA_LG_TYPE2 extraLgType2,lc.EXTRA_LG_TYPE3 extraLgType3,lc.EXTRA_LG_TYPE4 extraLgType4,lc.EXTRA_LG_TYPE5 extraLgType5 ,lc.EXTRA_LG_TYPE6 extraLgType6,"
			    + "  j.idf_CUST_NO custNo,   c.CUST_NAME custName, '' lgType, '送回客戶' mailCategory, lc.LC_NO jobCodeNo, lc.PROG_NM  jobName, '' cycleDateList, ''  customerDept, 0 ,  sum(j.accounts) qty"
	    		+ " from job_bag j, lcinfo lc,  customer c "
	    		+ " where  "
	    		+ " j.idf_CUST_NO = c.CUST_NO "	    		 	    		
	    		+ " and j.job_bag_status in ('INIT', 'COMPLETED_MP','NON_MP','PRINTED_LG_FORM','COMPLETED_LG', 'COMPLETED_LP', 'PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_LP', 'ACCOUNTING_LOCKED') "  
	    		+ " and j.job_bag_no in (" + jobBagList+ ")"  
	    		+ " and j.idf_lc_no  = lc.LC_NO "
	    		+ " and lc.TEMPLATE='"+templateName+"' "
	    		+ " and lc.P_LIST = 1   "
	    		+ " group by j.ret_user_company,  j.ret_user_tel , j.ret_user_name ,lc.EXTRA_LG_TYPE1,lc.EXTRA_LG_TYPE2,lc.EXTRA_LG_TYPE3,lc.EXTRA_LG_TYPE4,lc.EXTRA_LG_TYPE5,lc.EXTRA_LG_TYPE6 , j.idf_CUST_NO, c.CUST_NAME,  lc.LC_NO , lc.PROG_NM "
	    		+ " union  "
	    		+ " select  j.ret_user_company postOfficeName, j.ret_user_tel postOfficeTel, '' postOfficeFax , j.ret_user_name postOfficeContact  ,lc.EXTRA_LG_TYPE1 extraLgType1,lc.EXTRA_LG_TYPE2 extraLgType2,lc.EXTRA_LG_TYPE3 extraLgType3,lc.EXTRA_LG_TYPE4 extraLgType4,lc.EXTRA_LG_TYPE5 extraLgType5 ,lc.EXTRA_LG_TYPE6 extraLgType6, "
	    		+ " j.idf_CUST_NO custNo,   c.CUST_NAME custName, '' lgType, '送回客戶' mailCategory, j.idf_JOB_CODE_NO jobCodeNo, j.PROG_NM + '##' +  j.job_bag_no jobName ,  '(' + convert(char(5),j.cycle_date,101) + ')' cycleDateList , j.CUSTOMER_DEPT customerDept, 0 , sum(j.accounts) qty  "
	    		+ " from job_bag j, lcinfo lc,  customer c, job_code  "
	    		+ " where  "
	    		+ " j.idf_JOB_CODE_NO = job_code.JOB_CODE_NO " 
	    		+ " and j.idf_CUST_NO = c.CUST_NO "	    		 	    		
	    		+ " and j.job_bag_status in ('INIT', 'COMPLETED_MP','NON_MP','PRINTED_LG_FORM','COMPLETED_LG', 'COMPLETED_LP', 'PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_LP', 'ACCOUNTING_LOCKED')  "  
	    		+ " and j.job_bag_no in (" + jobBagList+ ")"  
	    		+ " and j.idf_lc_no  = lc.LC_NO "
	    		+ " and lc.TEMPLATE='"+templateName+"' "
	    		+ " and lc.P_LIST <> 1 "
	    		+ " group by j.ret_user_company ,  j.ret_user_tel   ,j.ret_user_name , lc.EXTRA_LG_TYPE1,lc.EXTRA_LG_TYPE2,lc.EXTRA_LG_TYPE3,lc.EXTRA_LG_TYPE4,lc.EXTRA_LG_TYPE5,lc.EXTRA_LG_TYPE6 ,j.idf_CUST_NO, c.CUST_NAME, j.idf_JOB_CODE_NO , j.PROG_NM  , '(' + convert(char(5),j.cycle_date,101) + ')' , j.JOB_BAG_NO,  j.CUSTOMER_DEPT "
	    		+ " order by j.ret_user_company ,  j.ret_user_tel   ,j.ret_user_name , j.idf_CUST_NO, c.CUST_NAME, jobCodeNo , jobName , cycleDateList  " ; 


   		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
   		    System.out.println(sqlQuery);
   		    //sqlQuery.setResultTransformer(Transformers.aliasToBean(LCFormBean.class));
            List<Object [] > list = sqlQuery.list();
            List<FbRegisterNos> fbList = null;
            
            //找出fubon的掛號號碼
            if("FUBON".equals(templateName)){
            	fbList = fbRegisterNosService.getFromJobBagList(jobBagList);            	
            }
   	        List<LCFormBean> lcformList = new ArrayList<LCFormBean>();
    	    if (null == list || list.size() ==0) {
    	    	return ""; //若沒資料, 回傳空白的querysession
    	    }else {
    	    	for(Object[] objArr : list){
    	    		LCFormBean lcBean = new LCFormBean();
    
    	    		lcBean.setPostOfficeName((String)objArr[0]);
    	    		lcBean.setPostOfficeTel((String)objArr[1]);
    	    		lcBean.setPostOfficeFax((String)objArr[2]);
    	    		lcBean.setPostOfficeContact((String)objArr[3]);
    	    		lcBean.setExtraLgType1((String)objArr[4]);
    	    		lcBean.setExtraLgType2((String)objArr[5]);
    	    		lcBean.setExtraLgType3((String)objArr[6]);
    	    		lcBean.setExtraLgType4((String)objArr[7]);
    	    		lcBean.setExtraLgType5((String)objArr[8]);
    	    		lcBean.setExtraLgType6((String)objArr[9]);
    	    		lcBean.setCustNo((String)objArr[10]);
    	    		lcBean.setCustName((String)objArr[11]);
    	    		lcBean.setLgType((String)objArr[12]);
    	    		lcBean.setMailCategory((String)objArr[13]);
    	    		lcBean.setJobCodeNo((String)objArr[14]);
    	    		String jobName = (String)objArr[15];
    	    		if(jobName != null ){
    	    			int index = jobName.indexOf("##");
    	    			if(index >= 0){
    	    				jobName = jobName.substring(0, index);
    	    				String jobBagNo = ((String)objArr[15]).substring(index + 2);
    	    				lcBean.setJobBagNo(jobBagNo);
    	    			}
    	    			
    	    		}
    	    		lcBean.setJobName(jobName);
    	    		lcBean.setCycleDateList((String)objArr[16]);
    	    		lcBean.setCustomerDept((String)objArr[17]);
    	    		lcBean.setPrice(null) ;
    	    		lcBean.setQty((Integer)objArr[19]);
    	    		lcformList.add(lcBean);
    	    	}
    	    }
    	    
    		//取得系統時間
			Calendar calendar = Calendar.getInstance();
			Date now = new Date();
			calendar.setTime(now);    
			synchronized(this){
			   querySession = Long.toString(calendar.getTimeInMillis()); //將時間轉為 querySession
			   try {
				   Thread.sleep(1);
			   } catch (InterruptedException e) {
				   log.error("", e);
				   e.printStackTrace();
			   }
			}

        	//HibernateSessionFactory.getSession().clear();
        	//HibernateSessionFactory.getSession().getTransaction().begin();
        	
        	String salmatTel= Util.getString("jbm.salmat.Tel");
        	String salmatFax= Util.getString("jbm.salmat.Fax");
        	String salmatContact= Util.getString("jbm.salmat.Contact");
        	if(fbList != null){
        	   for(FbRegisterNos fbRegisterNos : fbList){
        	      fbRegisterNos.setQuerySession(querySession);
        	      fbRegisterNosService.save(fbRegisterNos);
        	   }
        	}
        	
	        for (int ii=0; ii<lcformList.size(); ii++) {
	        	LCFormBean lcFormBean = (LCFormBean)lcformList.get(ii);
	        	
	        	Lcform lcform = new Lcform();
	        	
	        	
	        	java.util.Date defaultValue = null;
	        	DateConverter converter = new DateConverter(defaultValue);
	        	ConvertUtils.register(converter, java.util.Date.class);    	        	    	        		
        		BeanUtils.copyProperties(lcform,lcFormBean);

        		lcform.setSalmatTel(salmatTel);
        		lcform.setSalmatFax(salmatFax);
        		lcform.setSalmatContact(salmatContact);
        		lcform.setQuerySession(querySession);
        		String cycleDateString="";
        		
        		// 若 CycleDateList == null , 表示合併列印明細
        		//須找到原工單, 再組合出 cycle date list 

        		if (lcform.getCycleDateList().length()==0) {
        			//List _jobBagList = lginfoService.getJobBagListByJobCode(jobBagList, lcFormBean.getJobCodeNo());
        			List _jobBagList = lginfoService.getJobBagListByJobCode(jobBagList, lcFormBean.getJobCodeNo());
    	        	for (int i=0; i<_jobBagList.size(); i++) {
    	        		JobBag _jobbag = (JobBag)_jobBagList.get(i);
    	        		String cycleDate = new SimpleDateFormat("yyyy/MM/dd").format(_jobbag.getCycleDate());
    	        		if (cycleDateString.indexOf(cycleDate.substring(5,10)) < 0) // 當cycle date 不存在時, 再加入
    	        			cycleDateString = cycleDateString + cycleDate.substring(5,10) + ",";
    	        	}     
            		cycleDateString =  "(" + cycleDateString + ")";
            		lcform.setCycleDateList(cycleDateString);    	        	
        		}
        		lcform.setOrderby(ii);
        		
        		//富邦專用 客戶單位 及 分區
        		if (lcform.getCustNo().equalsIgnoreCase("FB") && templateName.equalsIgnoreCase("FUBON")) {
        			String jobBagNo = lcFormBean.getJobBagNo();
        			lcform.setPrice(lcFormBean.getPrice().doubleValue());
        			
        			//找出掛號號碼
        			if(jobBagNo != null && !"".equals(jobBagNo.trim())){
        				if(fbList != null){
        				   String registerNos = "";
        				   for(FbRegisterNos fbRegisterNos : fbList){
        	        	       if(fbRegisterNos.getJobBagNo() != null && fbRegisterNos.getJobBagNo().equals(jobBagNo)){
        	        	    	   registerNos += fbRegisterNos.getRegisterNoBegin() + "~" + fbRegisterNos.getRegisterNoEnd() + "<br />";
        	        	       }
        	        	   }
        				   int lastBreakTag = registerNos.lastIndexOf("<br />");
        				   if(lastBreakTag >= 0)
        				      registerNos = registerNos.substring(0, lastBreakTag);
        				   lcform.setRegisterNos(registerNos);
        				}
        			}
        			
        		}

        		//富邦專用 客戶單位 及 分區
        		if (lcform.getCustNo().equalsIgnoreCase("FB") && templateName.equalsIgnoreCase("FUBON") && lcform.getQty().compareTo(3000) >=0) {
        			lcform.setMailToArea("本地\r外地\r雜項");  			
        		}

	        	lcFormService.save(lcform);


	        	//處理額外交寄方式1
        		if (null!=lcform.getExtraLgType1() && lcform.getExtraLgType1().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType1());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
        		}
        		
	        	//處理額外交寄方式2
        		if (null!=lcform.getExtraLgType2() && lcform.getExtraLgType2().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType2());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
      		
        		}
        		
	        	//處理額外交寄方式3
        		if (null!=lcform.getExtraLgType3() && lcform.getExtraLgType3().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType3());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
       		
        		}
        		
	        	//處理額外交寄方式4
        		if (null!=lcform.getExtraLgType4() && lcform.getExtraLgType4().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType4());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
         		
        		}
        		
	        	//處理額外交寄方式5
        		if (null!=lcform.getExtraLgType5() && lcform.getExtraLgType5().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType5());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
         		
        		}    
        		
	        	//處理額外交寄方式6
        		if (null!=lcform.getExtraLgType6() && lcform.getExtraLgType6().length() >0) {
        			Lcform _lcform = new Lcform();
            		BeanUtils.copyProperties(_lcform,lcform);
            		_lcform.setLgType("");
            		_lcform.setMailCategory("");
            		_lcform.setJobName(lcform.getExtraLgType6());
            		_lcform.setCycleDateList("");
            		_lcform.setCustName("");
            		_lcform.setQty(null);
    	        	lcFormService.save(_lcform);
         		
        		}
        		

	        }
    	        
        	//HibernateSessionFactory.getSession().getTransaction().commit(); 
			
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return querySession;
		
	}
}
