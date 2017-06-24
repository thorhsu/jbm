package com.salmat.jbm.service;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.salmat.jbm.bean.LGFormBean;
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


public class LGInfoService extends LginfoDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(LGInfoService.class);
    
    /* Constants */
    private static final LGInfoService instance = new LGInfoService();
	private static final LGFormService lgFormService = LGFormService.getInstance();
	private static final JobBagService jobBagService = JobBagService.getInstance();
    
    private LGInfoService() {
    }
    
	public static LGInfoService getInstance() {
        return instance;
    }
	
    /**
     * get getEffectiveLgInfo
     * return EffectiveLgInfo List
     */
	public List getEffectiveLgInfo() {
		
		
		
		try {
	    	String queryString = " select {a.*} "
	    		+ " from lginfo a  "
	    		+ " where convert(char, a.NEXT_EFFECTIVE_DATE, 101)   = convert(char, getdate(), 101) " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", Lginfo.class);

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}	
	
	
	
    /**
     * 產生Statement 流水號
     * input: custNo 
     */
	public Integer getStatementNo(String custNo) {
        
		try {
	    	String queryString = " select max( STATEMENT_NO ) maxId "
	    		+ " from LGForm  "
	    		+ " where idf_CUST_NO = '"+custNo+"' " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    //sqlQuery.addEntity("j", JobBag.class);
	        //sqlQuery.addScalar("maxId");
		    //sqlQuery.setParameter(0, jobCodeNo+NowTime);

			List retList = sqlQuery.list();
			Integer currentNo=0;
			if (null != retList && retList.size() >0 )
				currentNo = (Integer)retList.get(0);
			
			if (null == currentNo)
				currentNo =0;
			return currentNo;

		} catch (RuntimeException re) {
			log.error("", re);
			return 0;
		}		

	}	
    
    /* 批次產生 寄送的郵資單 */
	public List batchGenerateLGForm(String jobBagList) {
		
		List retLGFormList= new ArrayList();		
		try {
			// Step1: 找出 合併列印的郵資單 清單(列印同一型態之明細)
			String lg_code_mail_to_area="lg_code_mail_to_area";
			String queryString ="";
			HashMap<String, String> cycleDatesMap = new HashMap<String, String>();
			for (int i=1;i<6;i++) {
				lg_code_mail_to_area = "lg_code_mail_to_area" + i;
				queryString = queryString +  " select " 
	    		+ " j.idf_cust_no idfCustNo,   l.lg_no lgNo, l.prog_nm lgTitle," 
	    		+ " j."+lg_code_mail_to_area+" codeMailToArea, c1.code_value_tw mailToArea, " 
	    		//+ " j.code_lg_type codeLgType, c2.code_value_tw lgType, " 
	    		//+ " j.code_mail_category codeMailCategory , c3.code_value_tw mailCategory, "
	    		+ " j.code_lg_type codeLgType, price.LG_TYPE_NAME lgType, " 
	    		+ " j.code_mail_category codeMailCategory , price.LG_MAIL_CATEGORY_NAME mailCategory, "
	    		+ " price.price," 
	    		+ " price.weight," 
	    		+ " j.job_bag_no jobBagNo, " 
	    		+ " j.idf_job_code_no jobCodeNo, " 
	    		+ " j.prog_nm + '##(' + convert(char(5),j.cycle_date,101) + ')' as  progNm ," 
	    		+ " sum(j.accounts) qty , " 
	    		+ " c.cust_name custName," 
	    		+ " c.path_cust_stamp_image pathCustStampImage " 
	    		+ " from job_bag j " 
	    		+ " join lginfo l on j.idf_lg_no = l.lg_no "  
	    		+ " join customer c on j.idf_cust_no =c.cust_no " 
	    		+ " join code c1 on c1.id = j."+lg_code_mail_to_area+"  " 
	    		+ " join code c2 on c2.id = j.code_lg_type  " 
	    		+ " join code c3 on c3.id = j.code_mail_category" 
	    		+ " left outer join lg_price price on price.lg_type = j.code_lg_type and price.lg_mail_category = j.code_mail_category" 
	    		+ " where  " 
	    		+ " j.job_bag_status in ('COMPLETED_MP','NON_MP','PRINTED_LG_FORM','COMPLETED_LG', 'COMPLETED_LP', 'PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_LP', 'ACCOUNTING_LOCKED', 'ACCT_DN_GENERATED') " 
	    		+ " and j.job_bag_no in (" + jobBagList+ ")" 
	    		+ " and  j.use_lg = 1 " 
	    		+ " and  j.idf_lg_no is not null " 
	    		+ " and l.p_list = 1" 
	    		//+ " group by  j.idf_cust_no,  l.lg_no, l.prog_nm, j."+lg_code_mail_to_area+", c1.code_value_tw , j.code_lg_type, c2.code_value_tw, j.code_mail_category, c3.code_value_tw, price.price," 
	    		+ " group by  j.idf_cust_no,  l.lg_no, l.prog_nm, j."+lg_code_mail_to_area+", c1.code_value_tw , j.code_lg_type, price.LG_TYPE_NAME, j.code_mail_category, price.LG_MAIL_CATEGORY_NAME, price.price,"
	    		+ " price.weight, j.idf_job_code_no,j.job_bag_no, j.prog_nm + '##(' + convert(char(5),j.cycle_date,101) + ')' , c.cust_name , c.path_cust_stamp_image     "
				+ " union" ;
			}

			queryString = queryString.substring(0,queryString.length()-5); // 去除最後一個 union
			//queryString = queryString +  " order by j.idf_cust_no, l.prog_nm, j.lg_code_mail_to_area1, c1.code_value_tw , j.code_lg_type, c2.code_value_tw, j.code_mail_category, c3.code_value_tw, j.idf_job_code_no       " ;
			queryString = queryString +  " order by j.idf_cust_no, l.prog_nm, j.lg_code_mail_to_area1, c1.code_value_tw , j.code_lg_type, price.LG_TYPE_NAME, j.code_mail_category, price.LG_MAIL_CATEGORY_NAME, j.idf_job_code_no       " ;



   		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
   		    sqlQuery.setResultTransformer(Transformers.aliasToBean(LGFormBean.class));

   	        List<LGFormBean> lgformList = sqlQuery.list();
   	        List<String> jobBagNoList = new ArrayList<String>();
   	        for (LGFormBean lgFormBean : lgformList) { 
   	    	   jobBagNoList.add(lgFormBean.getJobBagNo());
   	        }
   	        Calendar dispatchDate = Calendar.getInstance();
   	        dispatchDate.set(dispatchDate.get(Calendar.YEAR), dispatchDate.get(Calendar.MONTH) - 1, dispatchDate.get(Calendar.DATE));
   	        //把之前產生過的jobbagno消除 
   	        if(jobBagNoList != null && jobBagNoList.size() > 0){
   	           //HibernateSessionFactory.getSession().createQuery("update Lgform set jobBagNo = null where jobBagNo in (:jobBagNoList)").setParameterList("jobBagNoList", jobBagNoList).executeUpdate();
   	        	
   	           Session session = HibernateSessionFactory.getSession();
           	   Transaction tx = session.beginTransaction();
   	           session.createQuery("update OldLgform set jobBagNo = null where jobBagNo in (:jobBagNoList) and dispatchDate > :dispatchDate").setParameterList("jobBagNoList", jobBagNoList).setParameter("dispatchDate", dispatchDate.getTime()).executeUpdate();
               tx.commit();
               session.close();
   	        }
    	        
    		//取得系統時間
			Calendar calendar = Calendar.getInstance();
			Date now = new Date();
			calendar.setTime(now);    
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String serial = sdf.format(now) + "0000";
			//Integer theNewMothSerial = Integer.parseInt(serial); //每個月statementNo要從頭編
			Integer currenStatementNo = 0;   
        	
        	
	        LGFormBean prevFormBean = new LGFormBean();
	        prevFormBean.setCodeLgType(0);
	        prevFormBean.setCodeMailCategory(0);
	        prevFormBean.setCodeMailToArea(0);
	        
	        Lgform prevLgform = new Lgform();
	        prevLgform.setIdfCustNo("");
	        prevLgform.setStatementNo(0);
	        
	        Integer counter=0;
	        for (int ii=0; ii<lgformList.size(); ii++) {
	        	LGFormBean lgFormBean = (LGFormBean)lgformList.get(ii);

            	HibernateSessionFactory.getSession().clear();
            	HibernateSessionFactory.getSession().getTransaction().begin();
            	
            	if (!lgFormBean.getCustName().equalsIgnoreCase(prevFormBean.getCustName())  ||
            		!lgFormBean.getLgTitle().equalsIgnoreCase(prevFormBean.getLgTitle()) ||
            		lgFormBean.getCodeMailToArea().compareTo(prevFormBean.getCodeMailToArea())!= 0  || 
            		lgFormBean.getCodeLgType().compareTo(prevFormBean.getCodeLgType())!= 0  ||
            		lgFormBean.getCodeMailCategory().compareTo(prevFormBean.getCodeMailCategory())!= 0 ||
            		counter.compareTo(6)==0 ) {
            		//  客戶 + 郵資單抬頭 + 寄送地區 + 郵資單總類 + 交寄方式 不同時 or counter =5 , 就要分頁 ( 產生新的 currenStatementNo ) 
            		currenStatementNo = this.getStatementNo(lgFormBean.getIdfCustNo()); //取得這客戶的 目前的 statementNo	
            		//if(currenStatementNo < theNewMothSerial)
            			//currenStatementNo = theNewMothSerial;
            		currenStatementNo = currenStatementNo + 1;
            		counter = 1;
	        	}
	        	Lgform lgform = new Lgform();
	        	lgform.setIdfCustNo(lgFormBean.getIdfCustNo());	   
	        
	        	lgform.setCustName(lgFormBean.getCustName());
	        	lgform.setPathCustStampImage(lgFormBean.getPathCustStampImage());
	        	lgform.setCodeMailToArea(lgFormBean.getCodeMailToArea());
	        	lgform.setMailToArea(lgFormBean.getMailToArea());
	        	lgform.setCodeLgType(lgFormBean.getCodeLgType());
	        	lgform.setLgType(lgFormBean.getLgType());
	        	lgform.setCodeMailCategory(lgFormBean.getCodeMailCategory());
	        	lgform.setMailCategory(lgFormBean.getMailCategory());	
	        	if (null!= lgFormBean.getQty() && lgFormBean.getQty().compareTo(0) >0)
	        		lgform.setQty(lgFormBean.getQty()); //押上合計的數量
	        	else
	        		lgform.setQty(null); //若合計數量==0, 押上 null
	        	lgform.setWeight(lgFormBean.getWeight());
	        	if (null== lgFormBean.getPrice())
	        		lgFormBean.setPrice(new BigDecimal(0));

	        	lgform.setPrice(lgFormBean.getPrice().doubleValue());
	        	lgform.setJobCodeNo(lgFormBean.getJobCodeNo());
	        	lgform.setJobBagNo(lgFormBean.getJobBagNo());
	        	lgform.setLgNo(lgFormBean.getLgNo());
	        	JobBag jobbag = jobBagService.findById(lgform.getJobBagNo());
	        	//如果設為不顯示數量，把價格、重量、和件數都消掉
	        	if(jobbag != null && jobbag.getLgDisplayQty() != null && !jobbag.getLgDisplayQty()){
	        		lgform.setPrice(0.00);
	        		lgform.setWeight(null);
	        		lgform.setQty(null);
	        	}
	        	lgform.setStatementNo(currenStatementNo);
	        	
                String mapKey = lgform.getIdfCustNo() + lgform.getStatementNo();
	        	
	        	lgform.setDispatchDate(calendar.getTime());
	        	String progNm = lgFormBean.getProgNm() == null ? "" : lgFormBean.getProgNm();
	        	String cycleDate = "";
	        	int spliteIndex ; 
	        	if((spliteIndex = progNm.indexOf("##")) >= 0){
	        		progNm = lgFormBean.getProgNm().substring(0, spliteIndex);
	        		cycleDate = lgFormBean.getProgNm().substring(spliteIndex + 2);

	        		if(cycleDate.endsWith(",)"))
	        		   cycleDate = cycleDate.substring(0, cycleDate.length() - 2) + ")";
	        	}
	        	String cycleDates = (cycleDatesMap.get(mapKey) == null)? "" : cycleDatesMap.get(mapKey);
	        	if(cycleDates.indexOf(cycleDate) < 0){	        		
	        		cycleDates += cycleDate;
	        		
	        	}
	        	

	        	cycleDatesMap.put(mapKey, cycleDates);
	        	
	        	
	        	lgform.setProgNm(progNm);
	        	lgform.setLgTitle(lgFormBean.getLgTitle());//寄送人
	        	//lgform.setDispatcher(lginfo.getProgNm() + "(" + sycleDateBngin + "~" + sycleDateEnd +")" );
	        	


	        	lgFormService.save(lgform);
	        	OldLgform oform = new OldLgform();
	        	org.springframework.beans.BeanUtils.copyProperties(lgform, oform);
	        	HibernateSessionFactory.getSession().save(oform);
	        	HibernateSessionFactory.getSession().getTransaction().commit(); 

	        	//當 客戶ID or Statement 變換, 增加一筆LGFormList
	        	if (! lgform.getIdfCustNo().equalsIgnoreCase(prevLgform.getIdfCustNo()) || lgform.getStatementNo().compareTo(prevLgform.getStatementNo())!=0 )
	        		retLGFormList.add(lgform);
	        	
	        	
	        	java.util.Date defaultValue = null;
	        	BigDecimal bdDefault = null;
	        	DateConverter converter = new DateConverter(defaultValue);
	        	BigDecimalConverter bDconverter = new BigDecimalConverter(bdDefault);
	        	ConvertUtils.register(converter, java.util.Date.class);    	        	    	        		
	        	ConvertUtils.register(bDconverter, BigDecimal.class);
        		BeanUtils.copyProperties(prevFormBean,lgFormBean);
        		BeanUtils.copyProperties(prevLgform,lgform);
        		counter = counter + 1;
	        }
	        Set<String> keySet = cycleDatesMap.keySet();
	        
	        HibernateSessionFactory.getSession().beginTransaction();
	        //把title改成title(cycledate1, cycledate2, cycledate3...)
	        for(String key : keySet){
	        	String updateString = "update lgform set LG_TITLE = (LG_TITLE + '" + cycleDatesMap.get(key) + "') where (idf_CUST_NO + cast(STATEMENT_NO as varchar(6))) = '" + key + "'";

	        	SQLQuery updateQuery = (SQLQuery) HibernateSessionFactory
				    .getSession().createSQLQuery(updateString);
	        	updateQuery.executeUpdate();	        	
	        }
	        HibernateSessionFactory.getSession().getTransaction().commit();
    	        
	        
			// Step2: 找出 不合併列印的郵資單 清單(不列印同一型態之明細)
			lg_code_mail_to_area="lg_code_mail_to_area";
			
			//找出要顯示與不顯示數量的job bag
			List<JobBag> notDisplayList = jobBagService.findJobagNotDisplayQty(jobBagList, false);  
			List<JobBag> displayList = jobBagService.findJobagNotDisplayQty(jobBagList, true);
			
			Session session = HibernateSessionFactory.getSession();
			
			//SQLQuery updateSqlQuery = session.createSQLQuery("update LGForm set JOB_BAG_NO = REPLACE(JOB_BAG_NO, ? , '') where JOB_BAG_NO like ? and DISPATCH_DATE > ?");
			//SQLQuery updateQuery = session.createSQLQuery("update Old_LGForm set JOB_BAG_NO = REPLACE(JOB_BAG_NO, ? , '') where JOB_BAG_NO like ? and DISPATCH_DATE > ?");
			Query updateQuery = session.createQuery("update OldLgform set jobBagNo = replace(jobBagNo, ? , '') where jobBagNo like ? and dispatchDate > ?");
			//先把以前的jobBagNo去除
 			if(notDisplayList != null)
			   for(JobBag jobBag : notDisplayList){
				   Transaction tx = session.beginTransaction();
				   String jobbagNo = jobBag.getJobBagNo();
				   // 這是用逗號分開的，所以有可能有多個
				   //updateSqlQuery.setString(0, jobbagNo).setString(1, "%" + jobbagNo + "%").executeUpdate();
				   updateQuery.setString(0, jobbagNo).setString(1, "%" + jobbagNo + "%").setDate(2, dispatchDate.getTime()).executeUpdate();
				   tx.commit();
			   }
			if(displayList != null)
			   for(JobBag jobBag : displayList){               
				   Transaction tx = session.beginTransaction();
				   String jobbagNo = jobBag.getJobBagNo();
				   //這是用逗號分開的，所以有可能有多個
				   updateQuery.setString(0, jobbagNo).setString(1, "%" + jobbagNo + "%").setDate(2, dispatchDate.getTime()).executeUpdate();
				   tx.commit();
			   }
			
			session.close();
			
			String jobBagListCopy = jobBagList; 
			jobBagList = "";
			if(displayList != null)
			   for(JobBag jobbag : displayList){
				   if(jobbag != null)
				      jobBagList += "'" + jobbag.getJobBagNo() + "',";
			   }
			if(!jobBagList.equals(""))
			   jobBagList = jobBagList.substring(0, jobBagList.length() - 1);
			
			queryString ="";
			for (int i=1;i<6;i++) {
				lg_code_mail_to_area = "lg_code_mail_to_area" + i;
				queryString = queryString +  " select " 
	    		+ " j.idf_cust_no idfCustNo,   l.lg_no lgNo, l.prog_nm lgTitle," 
	    		+ " j."+lg_code_mail_to_area+" codeMailToArea, c1.code_value_tw mailToArea, " 
	    		//+ " j.code_lg_type codeLgType, c2.code_value_tw lgType, " 
	    		+ " j.code_lg_type codeLgType, price.LG_TYPE_NAME lgType, "
	    		//+ " j.code_mail_category codeMailCategory , c3.code_value_tw mailCategory, "
	    		+ " j.code_mail_category codeMailCategory , price.LG_MAIL_CATEGORY_NAME mailCategory, "
	    		+ " price.price," 
	    		+ " price.weight," 
	    		+ " '' jobCodeNo, " 
	    		+ " sum(j.accounts) qty , " 
	    		+ " c.cust_name custName," 
	    		+ " c.path_cust_stamp_image pathCustStampImage " 
	    		+ " from job_bag j " 
	    		+ " join lginfo l on j.idf_lg_no = l.lg_no " 
	    		+ " join customer c on j.idf_cust_no =c.cust_no " 
	    		+ " join code c1 on c1.id = j."+lg_code_mail_to_area+"  " 
	    		+ " join code c2 on c2.id = j.code_lg_type  " 
	    		+ " join code c3 on c3.id = j.code_mail_category" 
	    		+ " left outer join lg_price price on price.lg_type = j.code_lg_type and price.lg_mail_category = j.code_mail_category" 
	    		+ " where  " 
	    		+ " j.job_bag_status in ('COMPLETED_MP','NON_MP','PRINTED_LG_FORM','COMPLETED_LG', 'COMPLETED_LP', 'PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_LP', 'ACCOUNTING_LOCKED', 'ACCT_DN_GENERATED') " 
	    		+ " and j.job_bag_no in (" + jobBagList+ ")" 
	    		+ " and  j.use_lg = 1 " 
	    		+ " and  j.idf_lg_no is not null "  
	    		+ " and j.LG_P_LIST <> 1" 
	    	    //+ " group by  j.idf_cust_no,  l.lg_no, l.prog_nm, j."+lg_code_mail_to_area+", c1.code_value_tw , j.code_lg_type, c2.code_value_tw, j.code_mail_category, c3.code_value_tw, price.price,"
	    		+ " group by  j.idf_cust_no,  l.lg_no, l.prog_nm, j."+lg_code_mail_to_area+", c1.code_value_tw , j.code_lg_type, price.LG_TYPE_NAME, j.code_mail_category, price.LG_MAIL_CATEGORY_NAME, price.price," 
	    		+ " price.weight,  c.cust_name , c.path_cust_stamp_image     "
				+ " union" ;
			}

			queryString = queryString.substring(0,queryString.length()-5); // 去除最後一個 union
			//queryString = queryString +  " order by j.idf_cust_no, l.prog_nm, j.lg_code_mail_to_area1, c1.code_value_tw , j.code_lg_type, c2.code_value_tw, j.code_mail_category, c3.code_value_tw      " ; 
			queryString = queryString +  " order by j.idf_cust_no, l.prog_nm, j.lg_code_mail_to_area1, c1.code_value_tw , j.code_lg_type, price.LG_TYPE_NAME, j.code_mail_category, price.LG_MAIL_CATEGORY_NAME      " ;
            //如果jobbagList不為空值才進行Query
            if(!jobBagList.equals("")){
   		       sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
   		       sqlQuery.setResultTransformer(Transformers.aliasToBean(LGFormBean.class));
   	           lgformList = sqlQuery.list();
            }else{
               lgformList = new ArrayList();;
            }
            
            
            //查出不顯示數字的郵資單
            jobBagList = "";
            if(notDisplayList != null)
 			   for(JobBag jobbag : notDisplayList){
 				   if(jobbag != null)
 				      jobBagList += "'" + jobbag.getJobBagNo() + "',";
 			   }
 			if(!jobBagList.equals(""))
 			   jobBagList = jobBagList.substring(0, jobBagList.length() - 1);
 			
   	        queryString ="";
			for (int i=1;i<6;i++) {
				lg_code_mail_to_area = "lg_code_mail_to_area" + i;
				queryString = queryString +  " select " 
	    		+ " j.idf_cust_no idfCustNo,   l.lg_no lgNo, l.prog_nm lgTitle," 
	    		+ " j."+lg_code_mail_to_area+" codeMailToArea, c1.code_value_tw mailToArea, " 
	    		//+ " j.code_lg_type codeLgType, c2.code_value_tw lgType, " 
	    		+ " j.code_lg_type codeLgType, price.LG_TYPE_NAME lgType, "
	    		//+ " j.code_mail_category codeMailCategory , c3.code_value_tw mailCategory, "
	    		+ " j.code_mail_category codeMailCategory , price.LG_MAIL_CATEGORY_NAME mailCategory, "
	    		+ " 0.00 price," 
	    		+ " 0 weight," 
	    		+ " '' jobCodeNo, " 
	    		+ " sum(j.accounts) qty , " 
	    		+ " c.cust_name custName," 
	    		+ " c.path_cust_stamp_image pathCustStampImage " 
	    		+ " from job_bag j " 
	    		+ " join lginfo l on j.idf_lg_no = l.lg_no " 
	    		+ " join customer c on j.idf_cust_no =c.cust_no " 
	    		+ " join code c1 on c1.id = j."+lg_code_mail_to_area+"  " 
	    		+ " join code c2 on c2.id = j.code_lg_type  " 
	    		+ " join code c3 on c3.id = j.code_mail_category" 
	    		+ " left outer join lg_price price on price.lg_type = j.code_lg_type and price.lg_mail_category = j.code_mail_category" 
	    		+ " where  " 
	    		+ " j.job_bag_status in ('COMPLETED_MP','NON_MP','PRINTED_LG_FORM','COMPLETED_LG', 'COMPLETED_LP', 'PRINTED_LP', 'PRINTED_MP', 'PRINTED_LG', 'NON_LP', 'ACCOUNTING_LOCKED', 'ACCT_DN_GENERATED') " 
	    		+ " and j.job_bag_no in (" + jobBagList+ ")" 
	    		+ " and  j.use_lg = 1 " 
	    		+ " and  j.idf_lg_no is not null " 
	    		+ " and j.LG_P_LIST <> 1" 
	    	    //+ " group by  j.idf_cust_no,  l.lg_no, l.prog_nm, j."+lg_code_mail_to_area+", c1.code_value_tw , j.code_lg_type, c2.code_value_tw, j.code_mail_category, c3.code_value_tw, price.price,"
	    		+ " group by  j.idf_cust_no,  l.lg_no, l.prog_nm, j."+lg_code_mail_to_area+", c1.code_value_tw , j.code_lg_type, price.LG_TYPE_NAME, j.code_mail_category, price.LG_MAIL_CATEGORY_NAME, " 
	    		+ " c.cust_name , c.path_cust_stamp_image     "
				+ " union" ;
			}

			queryString = queryString.substring(0, queryString.length() - 5); // 去除最後一個 union
			//queryString = queryString +  " order by j.idf_cust_no, l.prog_nm, j.lg_code_mail_to_area1, c1.code_value_tw , j.code_lg_type, c2.code_value_tw, j.code_mail_category, c3.code_value_tw      " ; 
			queryString = queryString +  " order by j.idf_cust_no, l.prog_nm, j.lg_code_mail_to_area1, c1.code_value_tw , j.code_lg_type, price.LG_TYPE_NAME, j.code_mail_category, price.LG_MAIL_CATEGORY_NAME      " ;

			List lgFormNotDisplayQtys = null;
			if(!jobBagList.equals("")){
		       sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		       sqlQuery.setResultTransformer(Transformers.aliasToBean(LGFormBean.class));
		       lgFormNotDisplayQtys = sqlQuery.list();
			}
			if(lgFormNotDisplayQtys != null && lgFormNotDisplayQtys.size() > 0){
				if(lgformList == null)
					lgformList = new ArrayList();
				for(int i = 0 ; i < lgFormNotDisplayQtys.size() ; i++){
					LGFormBean lgFormBean = (LGFormBean)lgFormNotDisplayQtys.get(i);
					lgformList.add(lgFormBean);
				}
			}
			

			//jobBagNo like '%" + jobBagNo + "%' or
   	        
   	        

        	currenStatementNo = 0;           	        	
	        prevFormBean = new LGFormBean();
	        prevFormBean.setCodeLgType(0);
	        prevFormBean.setCodeMailCategory(0);
	        prevFormBean.setCodeMailToArea(0);
	        
	        prevLgform = new Lgform();
	        prevLgform.setIdfCustNo("");
	        prevLgform.setStatementNo(0);
	        
	        counter=0;
	        for (int ii=0; ii<lgformList.size(); ii++) {
	        	LGFormBean lgFormBean = (LGFormBean)lgformList.get(ii);

            	HibernateSessionFactory.getSession().clear();
            	HibernateSessionFactory.getSession().getTransaction().begin();
            	
            	if (!lgFormBean.getCustName().equalsIgnoreCase(prevFormBean.getCustName())  || 
            		lgFormBean.getCodeMailToArea().compareTo(prevFormBean.getCodeMailToArea())!= 0  || 
            		lgFormBean.getCodeLgType().compareTo(prevFormBean.getCodeLgType())!= 0  ||
            		lgFormBean.getCodeMailCategory().compareTo(prevFormBean.getCodeMailCategory())!= 0 ||
            		lgFormBean.getLgNo().compareTo(prevFormBean.getLgNo())!= 0 ||
            		counter.compareTo(6)==0 ) {
            		
            		
            		//  客戶 + 寄送地區 + 郵資單總類 + 交寄方式 + LgNo  不同時 or counter =5 , 就要分頁 ( 產生新的 currenStatementNo ) 
            		currenStatementNo = this.getStatementNo(lgFormBean.getIdfCustNo()); //取得這客戶的 目前的 statementNo
            		//if(currenStatementNo < theNewMothSerial)
            			//currenStatementNo = theNewMothSerial;
            		currenStatementNo = currenStatementNo + 1;
            		counter = 1;
	        	}
	        	Lgform lgform = new Lgform();
	        	lgform.setIdfCustNo(lgFormBean.getIdfCustNo());	    	        	
	        	lgform.setCustName(lgFormBean.getCustName());
	        	lgform.setPathCustStampImage(lgFormBean.getPathCustStampImage());
	        	lgform.setCodeMailToArea(lgFormBean.getCodeMailToArea());
	        	lgform.setMailToArea(lgFormBean.getMailToArea());
	        	lgform.setCodeLgType(lgFormBean.getCodeLgType());
	        	lgform.setLgType(lgFormBean.getLgType());
	        	lgform.setCodeMailCategory(lgFormBean.getCodeMailCategory());
	        	lgform.setMailCategory(lgFormBean.getMailCategory());	        	
	        	lgform.setWeight(lgFormBean.getWeight());
	        	if (null!= lgFormBean.getPrice() )
	        		lgform.setPrice(lgFormBean.getPrice().doubleValue());
	        	lgform.setJobCodeNo(lgFormBean.getJobCodeNo());
	        	
	        	//找出每一張jobcode 對應的工單清單
	        	//在找出每張工單 cycle Date 及 這張工單是否要顯示數量  
	        	//List _jobBagList = getJobBagListByJobCode(jobBagList, lgFormBean.getJobCodeNo());
	        	List _jobBagList = getJobBagListByLgNo(jobBagListCopy, lgFormBean.getLgNo());
	        	Integer displayQty=0;
	        	String cycleDateString="";
	        	String lgTitle="";
	        	String jobBagNos = "";
	        	String jobCodeNos = "";
	        	String lgNos = "";
	        	for (int i=0; i<_jobBagList.size(); i++) {
	        		JobBag _jobbag = (JobBag)_jobBagList.get(i);
	        		if (i==0) {	        			
	        			if(_jobbag.getLgDisplayQty() != null && !_jobbag.getLgDisplayQty() ){
	        				//如果設定不顯示數量，就直接跳過分區判斷
	        				displayQty = 0;
	        			}else{
	        				//如果設定顯示數量，進行分區判斷
		        		   if (null!= _jobbag.getCodeByLgCodeMailToArea1() && _jobbag.getCodeByLgCodeMailToArea1().getId().compareTo(0) >0) displayQty = displayQty +1;
		        		   if (null!= _jobbag.getCodeByLgCodeMailToArea2() && _jobbag.getCodeByLgCodeMailToArea2().getId().compareTo(0) >0) displayQty = displayQty +1;
		        		   if (null!= _jobbag.getCodeByLgCodeMailToArea3() && _jobbag.getCodeByLgCodeMailToArea3().getId().compareTo(0) >0) displayQty = displayQty +1;
		        		   if (null!= _jobbag.getCodeByLgCodeMailToArea4() && _jobbag.getCodeByLgCodeMailToArea4().getId().compareTo(0) >0) displayQty = displayQty +1;
		        		   if (null!= _jobbag.getCodeByLgCodeMailToArea5() && _jobbag.getCodeByLgCodeMailToArea5().getId().compareTo(0) >0) displayQty = displayQty +1;
	        			}
	        		}
	        		String cycleDate = new SimpleDateFormat("yyyy/MM/dd").format(_jobbag.getCycleDate());
	        		if (cycleDateString.indexOf(cycleDate.substring(5,10)) < 0) // 當cycle date 不存在時, 再加入
	        			cycleDateString = cycleDateString + cycleDate.substring(5,10) + ",";
	        		if(_jobbag.getJobBagNo() != null && jobBagNos.indexOf(_jobbag.getJobBagNo()) < 0)
	        		   jobBagNos += _jobbag.getJobBagNo() + ",";
	        		if(_jobbag.getJobCode() != null && jobCodeNos.indexOf(_jobbag.getJobCode().getJobCodeNo()) < 0)
	        		   jobCodeNos += _jobbag.getJobCode().getJobCodeNo() + ",";
	        		if(_jobbag.getLginfo() != null && lgNos.indexOf(_jobbag.getLginfo().getLgNo()) < 0)
	        		   lgNos += _jobbag.getLginfo().getLgNo() + ",";
	        		// 若郵資單title 有值, 就抓 郵資單title 做為寄送人, 
	        		// 否則將抓工單.getProgNm
	        		Lginfo lginfo = this.findById(lgFormBean.getLgNo());
	        		if (null != lginfo.getProgNm() && lginfo.getProgNm().length() >0)
	        			lgTitle = lginfo.getProgNm();
	        		else
	        			lgTitle = _jobbag.getProgNm();
	        	}
	        	if(cycleDateString.endsWith(","))
	        		cycleDateString = cycleDateString.substring(0, cycleDateString.length() - 1);
	        	lgTitle = lgTitle + "(" + cycleDateString + ")";
	        	
	        	if (displayQty.compareTo(1)==0)
	        		lgform.setQty(lgFormBean.getQty()); //若只有一個寄送地區顯示 數量     
	        	else
	        		lgform.setQty(null); //多個寄送地區, 顯示 0
	        	lgform.setStatementNo(currenStatementNo);
	        	lgform.setDispatchDate(calendar.getTime());
	        	lgform.setLgTitle(lgTitle);// 活動 名稱 + cycleDate list
	        	if(jobBagNos.endsWith(","))
	        		jobBagNos = jobBagNos.substring(0, jobBagNos.length() - 1);
	        	if(jobCodeNos.endsWith(","))
	        		jobCodeNos = jobCodeNos.substring(0, jobCodeNos.length() - 1);
	        	if(lgNos.endsWith(","))
	        		lgNos = lgNos.substring(0, lgNos.length() - 1);
	        	
	        	if(!jobBagNos.equals(""))
	        	   lgform.setJobBagNo(jobBagNos);
	        	if(!jobCodeNos.equals(""))
	        	   lgform.setJobCodeNo(jobCodeNos);
	        	if(!lgNos.equals(""))
	        	   lgform.setLgNo(lgNos);

	        	lgFormService.save(lgform);
	        	OldLgform oform = new OldLgform();
	        	org.springframework.beans.BeanUtils.copyProperties(lgform, oform);
	        	HibernateSessionFactory.getSession().save(oform);
	        	HibernateSessionFactory.getSession().getTransaction().commit(); 

	        	//當 客戶ID or Statement 變換, 增加一筆 至retLGFormList
	        	if (! lgform.getIdfCustNo().equalsIgnoreCase(prevLgform.getIdfCustNo()) || lgform.getStatementNo().compareTo(prevLgform.getStatementNo())!=0 )
	        		retLGFormList.add(lgform);
	        	
	        	
	        	java.util.Date defaultValue = null;
	        	BigDecimal bdDefault = null;
	        	DateConverter converter = new DateConverter(defaultValue);
	        	BigDecimalConverter bDconverter = new BigDecimalConverter(bdDefault);
	        	ConvertUtils.register(converter, java.util.Date.class);    	        	    	        		
	        	ConvertUtils.register(bDconverter, BigDecimal.class);
	        	
        		BeanUtils.copyProperties(prevFormBean,lgFormBean);
        		BeanUtils.copyProperties(prevLgform,lgform);
        		counter = counter + 1;
	        }	        
			
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(HibernateSessionFactory.getSession().isOpen())
			   HibernateSessionFactory.closeSession();
		}

		return retLGFormList;
		
	}

	/*
	 * 根據郵資單代號, 找出 工單
	 */
	private List getJobBagListByLgNo(String jobBagList, String lgNo) {
		try {
	    	String queryString = " select {j.*}  "
	    		+ " from job_bag j "
	    		+ " where " 
	    	    + " j.job_bag_no in ("+jobBagList+")   " 
	    	    + " and j.idf_lg_no=? order by j.job_bag_no ";

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("j", JobBag.class);
		    sqlQuery.setParameter(0, lgNo);
			List retList = sqlQuery.list();

			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	/*
	 * 
	 */
	public List getJobBagListByJobCode(String jobBagList, String jobCodeNo) {
		// TODO Auto-generated method stub
		try {
	    	String queryString = " select {j.*}  "
	    		+ " from job_bag j "
	    		+ " where " 
	    	    //+ " j.job_bag_status in ('COMPLETED_MP','NON_MP','PRINTED_LG_FORM')   "
	    	    + " j.job_bag_no in ("+jobBagList+")   " 
	    	    //+ " and  j.use_lg = 1    " 
	    	    //+ " and  j.idf_lg_no is not null   "
	    	    + " and j.idf_LC_NO= ?   ";


	    	
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("j", JobBag.class);
		    sqlQuery.setParameter(0, jobCodeNo);

			List retList = sqlQuery.list();

			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}
	

}
