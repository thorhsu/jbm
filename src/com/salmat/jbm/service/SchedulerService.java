package com.salmat.jbm.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;










import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.mappingrule.AmexReader;
import com.salmat.jbm.mappingrule.CiticardReader;
import com.salmat.jbm.mappingrule.DamageReader;
import com.salmat.jbm.mappingrule.FbRegisterReader;
import com.salmat.jbm.mappingrule.HScardReader;
import com.salmat.jbm.mappingrule.LogisticReader;
import com.salmat.jbm.mappingrule.NotSendEddFinder;
import com.salmat.jbm.mappingrule.OneGoldReader;
import com.salmat.jbm.mappingrule.ZipcodeReader;
import com.salmat.jbm.struts.action.ReportAction;
import com.painter.filter.SetParamValFilter;
import com.painter.util.FtpConfig;
import com.painter.util.FtpUtil;
import com.painter.util.MailSender;
import com.painter.util.SmsHttpClient;
import com.painter.util.Util;










import org.hibernate.*;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.net.SocketException;

public class SchedulerService {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(SchedulerService.class);
    private static final LPInfoService lpinfoService = LPInfoService.getInstance();
    private static final MPInfoService mpinfoService = MPInfoService.getInstance();
    private static final PSInfoService psinfoService = PSInfoService.getInstance();
    private static final LGInfoService lginfoService = LGInfoService.getInstance();
    private static final LCInfoService lcinfoService = LCInfoService.getInstance();    
	private static final SyslogService syslogService = SyslogService.getInstance();	   
	private static final JobBagService jobbagService = JobBagService.getInstance();	   	

	/* Constants */
	private static final SchedulerService instance = new SchedulerService();
	private static boolean smsRunning = false;


	private SchedulerService() {

	}

	public static SchedulerService getInstance() {
		return instance;
	}

	/*
	 * 檢查帳號若已超過六十天沒使用，
	 * 則狀態 變更關閉
	 */
	public static void checkAccountActive(){
		Session session = null;
		Transaction tx = null;
		
        try {
        	session = HibernateSessionFactory.getSession();
        	session.clear();
        	tx = session.beginTransaction();
        	
         	
        	Calendar cal = Calendar.getInstance();
        	cal.add(Calendar.DATE,-35);//今天日期-35天
           	Criteria criteria = session.createCriteria(Employee.class);
        	criteria.add(Restrictions.le("latestLoginDate",cal.getTime()));
          	List<Employee>  list = criteria.list();
        	for(Employee employee : list){
        		//狀態 變更關閉
        		employee.setEnabled(false);        		  
        		session.update(employee);       
         	}      		
        	tx.commit();
        } catch (Exception e) {
            log.error("", e);
            tx.rollback();

        }finally{
        	if(session != null && session.isOpen())
        		session.close();
        }
	}
	
	public static void sendSmsMessage(){
		if(smsRunning)
			return;
		
		smsRunning = true;
		Session session = null;
		Transaction tx = null;
		try {
        	session = HibernateSessionFactory.getSession();
        	session.clear();
        	tx = session.beginTransaction();
        	List<Sms> list = session.createCriteria(Sms.class).add(Restrictions.eq("sent", false)).list();
        	if(list != null && list.size() > 0){
        		for(Sms sms : list){
        			String[] phoneNo = sms.getPhoneNos().split(",");
        			String msg =  sms.getMsg();
        			SmsHttpClient.sendMessageGet(phoneNo, msg);
        			sms.setSent(true);
        			session.update(sms);
        		}
        	}
        	tx.commit();
        } catch (Exception e) {
            log.error("", e);
            tx.rollback();

        }finally{
        	try{
        	   if(session != null && session.isOpen())
        		   session.close();
        	}catch(Exception e){
        		log.error("", e);
        	}finally{
        		smsRunning = false;        		
        	}
        	
        }
	}
	/*
	 * 變更物料資訊, 若有設定effective date, 則變更物料
	 * 自動展延合約 
	 */
	public static void changeMaterialInfo() {
		changeLpInfo();
		changeMpInfo();
		changePsInfo();
		changeLgInfo();
		extendContractEndDate(); //自動展延合約
	}
	
		
	//延長合約的方法
	public static void extendContractEndDate() {		
		Session session = null;
		Transaction tx = null;
		
        try {
        	session = HibernateSessionFactory.getSession();
        	session.clear();
        	
        	Criteria criteria = session.createCriteria(AcctCustomerContract.class);
        	
        	Calendar today = Calendar.getInstance();
        	Calendar yesterday = Calendar.getInstance();
        	Calendar oneMonthLater = Calendar.getInstance();
        	
        	//取得今天的日期
        	int todayDate = today.get(Calendar.DATE);
        	int thisMonthLastDate = today.getActualMaximum(Calendar.DAY_OF_MONTH);
        	
        	//先把展延的日期設為下月一號
        	oneMonthLater.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, 1, 23, 59, 58);
            //下月的最後一天是幾號        	
        	int lastDate = oneMonthLater.getActualMaximum(Calendar.DAY_OF_MONTH);
        	
        	//每月最後一天的判斷
        	if(todayDate == thisMonthLastDate) //如果今天是本月最後一天，下個月也是最後一天
        		oneMonthLater.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, lastDate, 23, 59, 58);
        	else if(todayDate > lastDate) //如果今天的日期大於下個月最後一天，下個月則設為最後一天
        		oneMonthLater.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, lastDate, 23, 59, 58);
        	else //如果都不是，直接加一個月就行
        		oneMonthLater.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, todayDate, 23, 59, 58);
        	
        	today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), 23, 59, 59);
        	yesterday.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE) - 1, 23, 59, 59);
    	    //檢查合約日從昨天23:59:59到今天23:59:59
    	    List<AcctCustomerContract> contracts = criteria
			    .add(Restrictions.between("contactDateEnd", yesterday.getTime(), today.getTime()))
			    .add(Restrictions.or(Restrictions.isNull("autoExtend"), Restrictions.eq("autoExtend", new Boolean(true))))
			    .list();
    	    
    	    if(contracts != null && contracts.size() > 0){
    	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	    	tx = session.beginTransaction();
    	    	for(AcctCustomerContract contract : contracts){
    	    		
    	    		//設為一個月後
    	    		contract.setContactDateEnd(oneMonthLater.getTime());
    	    		session.update(contract);
    	    		
    	    		SysLog syslog = new SysLog();
    				syslog.setLogType("Extend_Contract");
    				syslog.setSubject("Acct Customer Contract Extend: ");
    				syslog.setMessageBody(contract.getCustomer().getCustNo() + ":" + contract.getId() + " end date extends to " + sdf.format(oneMonthLater.getTime()));
    				syslog.setIsException(false);    				
    				syslog.setCreateDate(new Date());
    				session.save(syslog); 
    	    	}
    	    	   	    	
    	    	tx.commit();
    	    }
    	    
        }catch(Exception e){
        	if(tx != null)
        		tx.rollback();
        	log.error("", e);
        	
        }finally{
        	if(session != null)
        		session.close();
        }
	}
	
	/*
	 * 變更物料資訊, LpInfo, 則變更物料 
	 */
	public static void changeLpInfo() {		
		Session session = null;
		Transaction tx = null;
		
        try {
        	session = HibernateSessionFactory.getSession();
        	session.clear();
        	tx = session.getTransaction();
        	tx.begin();
			List<Lpinfo> list = lpinfoService.getEffectiveLpInfo();
			for(int i=0; i<list.size(); i++) {
				Lpinfo info = (Lpinfo)list.get(i);
				String oldInfo="";
				
				oldInfo = info.getProdmemo()+":" +info.getCodeByCodePrinter().getCodeValueTw()+":" +info.getCodeByCodePrintType().getCodeValueTw();
				oldInfo = oldInfo + ":" +info.getPcode1()+":" +info.getPcode2()+":" +info.getPcode3()+":" +info.getPcode4()+":" +info.getRemark();
				
				info.setProdmemo(info.getNextProdmemo());
				info.setCodeByCodePrinter(info.getCodeByNextCodePrinter());
				info.setCodeByCodePrintType(info.getCodeByNextCodePrintType());
				info.setPcode1(info.getNextPcode1());
				info.setPcode2(info.getNextPcode2());
				info.setPcode3(info.getNextPcode3());
				info.setPcode4(info.getNextPcode4());
				info.setPcode5(info.getNextPcode5());
				info.setPcode6(info.getNextPcode6());
				info.setPcode7(info.getNextPcode7());
				info.setPcode8(info.getNextPcode8());
				info.setRemark(info.getNextRemark());
				
				//將 NextXXX reset 
				info.setNextProdmemo("");
				info.setCodeByNextCodePrinter(null);
				info.setCodeByNextCodePrintType(null);
				info.setNextPcode1("");
				info.setNextPcode2("");
				info.setNextPcode3("");
				info.setNextPcode4("");
				info.setNextPcode5("");
				info.setNextPcode6("");
				info.setNextPcode7("");
				info.setNextPcode8("");
				info.setNextRemark("");
				info.setNextEffectiveDate("");
				lpinfoService.save(info);
				
				SysLog syslog = new SysLog();
				syslog.setLogType("CHANGE_MATERIAL");
				syslog.setSubject("LPInfo: " + info.getLpNo());
				syslog.setMessageBody(oldInfo);
				syslog.setIsException(false);
				Calendar calendar = Calendar.getInstance();
				Date now = new Date();
				calendar.setTime(now);
				syslog.setCreateDate(calendar.getTime());
				syslogService.save(syslog);

			}
			
			tx.commit();
        } catch (Exception e) {
            log.error("", e);
            tx.rollback();
        }finally{
        	if(session != null && session.isOpen())
        		session.close();
        }
	}
	
	/*
	 * 變更物料資訊, MpInfo, 則變更物料 
	 */
	public static void changeMpInfo() {		
		log.info("changeMpInfo Start....");
		Session session = null;
		Transaction tx = null;
		
        try {
        	session = HibernateSessionFactory.getSession();
        	session.clear();
        	tx = session.getTransaction();
        	tx.begin();
			List<Mpinfo> list = mpinfoService.getEffectiveMpInfo();
			for(int i=0; i<list.size(); i++) {
				Mpinfo info = (Mpinfo)list.get(i);
				String oldInfo="";
				
				oldInfo = info.getProdmemo()+":" +info.getPrintCode()+":" +info.getStampSetFg();
				oldInfo = oldInfo + ":" +info.getZipFg()+":" +info.getMp1Iflag()+":" +info.getMp2Iflag()+":" +info.getMp3Iflag()+":" +info.getMp4Iflag();
				oldInfo = oldInfo + ":" +info.getMp1Word()+":" +info.getMp2Word()+":" +info.getMp3Word()+":" +info.getMp4Word()+":" +info.getRemark();				
				
				info.setProdmemo(info.getNextProdmemo());
				info.setPrintCode(info.getNextPrintCode());
				info.setStampSetFg(info.getNextStampSetFg());	
				info.setZipFg(info.getNextZipFg());
				info.setMp1Iflag(info.getNextMp1Iflag());
				info.setMp2Iflag(info.getNextMp2Iflag());
				info.setMp3Iflag(info.getNextMp3Iflag());
				info.setMp4Iflag(info.getNextMp4Iflag());				
				info.setMp1Word(info.getNextMp1Word());
				info.setMp2Word(info.getNextMp2Word());
				info.setMp3Word(info.getNextMp3Word());
				info.setMp4Word(info.getNextMp4Word());				
				info.setRemark(info.getNextRemark());
				
				//將 NextXXX reset 
				info.setNextProdmemo("");
				info.setNextPrintCode("");
				info.setNextStampSetFg(null);	
				info.setNextZipFg(null);				
				info.setNextMp1Iflag(null);
				info.setNextMp2Iflag(null);
				info.setNextMp3Iflag(null);
				info.setNextMp4Iflag(null);				
				info.setNextMp1Word("");
				info.setNextMp2Word("");
				info.setNextMp3Word("");
				info.setNextMp4Word("");				
				info.setNextRemark("");
				info.setNextEffectiveDate(null);
				mpinfoService.save(info);
				
				SysLog syslog = new SysLog();
				syslog.setLogType("CHANGE_MATERIAL");
				syslog.setSubject("MPInfo: " + info.getMpNo());
				syslog.setMessageBody(oldInfo);
				syslog.setIsException(false);
				Calendar calendar = Calendar.getInstance();
				Date now = new Date();
				calendar.setTime(now);
				syslog.setCreateDate(calendar.getTime());
				syslogService.save(syslog);

			}
			
			tx.commit();
        } catch (Exception e) {
            log.error("", e);
            tx.rollback();

        }finally{
        	if(session != null && session.isOpen())
        		session.close();
        }
        log.info("changeMpInfo End....");
	}
	
	/*
	 * 變更物料資訊, PsInfo, 則變更物料 
	 */
	public static void changePsInfo() {	
		Session session = null;
		Transaction tx = null;
		
        try {
        	session = HibernateSessionFactory.getSession();
        	session.clear();
        	tx = session.getTransaction();
        	tx.begin();
			List<Psinfo> list = psinfoService.getEffectivePsInfo();
			for(int i=0; i<list.size(); i++) {
				Psinfo info = (Psinfo)list.get(i);
				String oldInfo="";
				
				oldInfo = info.getProdmemo()+":" +info.getCodeByCodePsType().getCodeValueTw()+":" +info.getZipFg() + ":"+info.getRemark();
				
				info.setProdmemo(info.getNextProdmemo());
				info.setCodeByCodePsType(info.getCodeByNextCodePsType());
				info.setZipFg(info.getNextZipFg());
				info.setRemark(info.getNextRemark());
				
				//將 NextXXX reset 
				info.setNextProdmemo("");
				info.setCodeByNextCodePsType(null);
				info.setNextZipFg(null);
				info.setNextRemark("");
				info.setNextEffectiveDate("");
				psinfoService.save(info);
				
				SysLog syslog = new SysLog();
				syslog.setLogType("CHANGE_MATERIAL");
				syslog.setSubject("PSInfo: " + info.getPsNo());
				syslog.setMessageBody(oldInfo);
				syslog.setIsException(false);
				Calendar calendar = Calendar.getInstance();
				Date now = new Date();
				calendar.setTime(now);
				syslog.setCreateDate(calendar.getTime());
				syslogService.save(syslog);

			}			
			tx.commit();
        } catch (Exception e) {
            log.error("", e);
            tx.rollback();
        }finally{
        	if(session != null && session.isOpen())
        		session.close();
        }
	}	
	
	public static void deleteJobBag(){
		//刪除JobBag
		Session session = null;
		Transaction tx = null;
		try{
		    session = HibernateSessionFactory.getSession();
		    session.clear();
    	    tx = session.getTransaction();
    	    tx.begin();
    	    
    	    Calendar daysBefore = Calendar.getInstance();
    	    //20天前
    	    daysBefore.set(daysBefore.get(Calendar.YEAR), daysBefore.get(Calendar.MONTH), daysBefore.get(Calendar.DATE) - 20, 0, 0, 0);    	    
    	    
    	    Criteria jbCriteria = session.createCriteria(JobBag.class);
    	    //20天前，一直維持在工單初始狀態的且accounts為0的
    	    List<JobBag> jobBags = jbCriteria
			    .add(Restrictions.le("createDate", daysBefore.getTime()))
			    .add(Restrictions.in("jobBagStatus", new String[]{"INIT", "NON_LP", "PRINTED_LP", "PRINTED_MP", "PRINTED_LG", "EDD"}))
   			    .add(Restrictions.or(Restrictions.isNull("accounts"), Restrictions.eq("accounts", 0)))
			    .list();
    	    int delCounter = 0;
    	    for(JobBag jobBag : jobBags){
    	    	delCounter++;
    	    	Set<JobBagSplite> spliteSet = jobBag.getJobBagSplites();
    	    	for(JobBagSplite jbSplite: spliteSet){
    	    		log.info("delete JobBagSplite :" + jbSplite.getJobBagSpliteNo());
    	    		session.delete(jbSplite);    	    		
    	    	}
    	    	log.info("delete JobBag over twenty days and status in init:" + jobBag.getJobBagNo());
    	    	session.delete(jobBag);	
    	    }
    	    SysLog syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("20 days before and Accts is Zero" );
			syslog.setMessageBody( "deleted " + delCounter + " JobBags");
			syslog.setIsException(false);
			Date now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
			delCounter = session.createQuery("delete from SysLog where createDate < ? and (logType = 'CREATE_ZIPCODE' or logType = 'CREATE_ZIPCODE_ERROR')").setDate(0, daysBefore.getTime()).executeUpdate();
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("delete zip log" );
			syslog.setMessageBody( "deleted " + delCounter + " syslog");
			syslog.setIsException(false);			
			syslog.setCreateDate(now);
			syslogService.save(syslog);

			
    	    
    	    delCounter = 0;
    	    //一年前，一直維持在工單初始狀態的，不管accounts是不是為零，都刪除
    	    daysBefore = Calendar.getInstance();
    	    //一年前
    	    daysBefore.set(daysBefore.get(Calendar.YEAR) - 1, daysBefore.get(Calendar.MONTH), daysBefore.get(Calendar.DATE), 0, 0, 0);    	    
    	    jbCriteria = session.createCriteria(JobBag.class);
    	    jobBags = jbCriteria
			    .add(Restrictions.le("createDate", daysBefore.getTime()))
			    .add(Restrictions.in("jobBagStatus", new String[]{"INIT", "NON_LP", "EDD"}))
			    .list();
    	    for(JobBag jobBag : jobBags){
    	    	delCounter++;
    	    	Set<JobBagSplite> spliteSet = jobBag.getJobBagSplites();
    	    	for(JobBagSplite jbSplite: spliteSet){
    	    		log.info("delete JobBagSplite :" + jbSplite.getJobBagSpliteNo());
    	    		session.delete(jbSplite);    	    		
    	    	}
    	    	log.info("delete JobBag over twenty days and status in init:" + jobBag.getJobBagNo());
    	    	session.delete(jobBag);	
    	    }
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("delete job bags one years ago and in init" );
			syslog.setMessageBody( "deleted " + delCounter + " JobBags");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
			
			//一年前，被設為標記刪除的，不管accounts是不是為零，都刪除
			delCounter = 0;
			jbCriteria = session.createCriteria(JobBag.class);
			jobBags = jbCriteria
		        .add(Restrictions.le("createDate", daysBefore.getTime()))
		        .add(Restrictions.eq("isDeleted", true))
		    .list();
	        for(JobBag jobBag : jobBags){
	    	   delCounter++;
	    	   Set<JobBagSplite> spliteSet = jobBag.getJobBagSplites();
	    	   for(JobBagSplite jbSplite: spliteSet){
	    		   log.info("delete JobBagSplite :" + jbSplite.getJobBagSpliteNo());
	    		   session.delete(jbSplite);    	    		
	    	   }
	    	   log.info("delete JobBag over twenty days and status in init:" + jobBag.getJobBagNo());
	    	   session.delete(jobBag);	
	        }
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("job bag before one years ago and is_deleted marked" );
			syslog.setMessageBody( "deleted " + delCounter + " JobBags");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
			
			
			//一年前，Account為0，除非狀態進入會計作業，都刪除
			delCounter = 0;
			jbCriteria = session.createCriteria(JobBag.class);
			jobBags = jbCriteria
		        .add(Restrictions.le("createDate", daysBefore.getTime()))
		        .add(Restrictions.not(Restrictions.in("jobBagStatus", new String[]{"ACCOUNTING_LOCKED", "ACCOUNTING_EP1", "ACCT_DN_GENERATED"})))
		        .add(Restrictions.or(Restrictions.isNull("accounts"), Restrictions.eq("accounts", 0)))
		        .list();
	        for(JobBag jobBag : jobBags){
	    	   delCounter++;
	    	   Set<JobBagSplite> spliteSet = jobBag.getJobBagSplites();
	    	   for(JobBagSplite jbSplite: spliteSet){
	    		   log.info("delete JobBagSplite :" + jbSplite.getJobBagSpliteNo());
	    		   session.delete(jbSplite);    	    		
	    	   }
	    	   log.info("delete JobBag over one year and accounts is zero:" + jobBag.getJobBagNo());
	    	   session.delete(jobBag);	
	        }
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("job bag before one years ago and accounts is zero " );
			syslog.setMessageBody( "deleted " + delCounter + " JobBags");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
    	    tx.commit();
    	    
    	    
    	    
    	    session.clear();
    	    tx = session.getTransaction();
    	    tx.begin();    	    
    	    Calendar halfYearbefore = Calendar.getInstance();
    	    Calendar oneYearbefore = Calendar.getInstance();
    	    //系統記錄保留一年前
    	    halfYearbefore.set(halfYearbefore.get(Calendar.YEAR), halfYearbefore.get(Calendar.MONTH) - 6, halfYearbefore.get(Calendar.DATE), 0, 0, 0);    	        	    
    	    oneYearbefore.set(oneYearbefore.get(Calendar.YEAR) - 1, oneYearbefore.get(Calendar.MONTH), oneYearbefore.get(Calendar.DATE), 0, 0, 0);
    	    Criteria slCriteria = session.createCriteria(SysLog.class);
    	    //刪除SysLog
    	    List<SysLog> sysLogs = slCriteria
			    .add(Restrictions.le("createDate", oneYearbefore.getTime()))
			    .list();
    	    delCounter = 0;
    	    for(SysLog sysLog : sysLogs){    	    	
    	    	log.info("delete SysLog over one year ");
    	    	session.delete(sysLog);
    	    	delCounter++;
    	    }
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("system log before one years ago " );
			syslog.setMessageBody( "deleted " + delCounter + " system logs");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
    	    tx.commit();
    	    
    	    
    	    //被標定為無效工單的工單系統記錄保留五年
    	    session.clear();
    	    tx = session.getTransaction();
    	    tx.begin();    	    
    	    Calendar fiveYearbefore = Calendar.getInstance();
    	    //被標定為無效工單的工單系統記錄保留五年
    	    fiveYearbefore.set(fiveYearbefore.get(Calendar.YEAR) -5, fiveYearbefore.get(Calendar.MONTH), fiveYearbefore.get(Calendar.DATE), 0, 0, 0);    	        	        	    
    	    Criteria fivecriteria = session.createCriteria(JobBag.class);
    	    //查出JobBag後刪除
    	    JobBag exampleJobBag = new JobBag();
    	    exampleJobBag.setCreateDate(fiveYearbefore.getTime());
    	    JobCode jobCode = new JobCode();
    	    jobCode.setIsEnabledContract(false);
    	    jobCode.setAutoDelete(true);
    	    exampleJobBag.setJobCode(jobCode);
    	    jobBags = fivecriteria
			    .add(Example.create(exampleJobBag))
			    .list();
    	    delCounter = 0;
    	    for(JobBag jobbag : jobBags){    	    	
    	    	log.info("delete Jobbag not enable over five year ");
    	    	session.delete(jobbag);
    	    	delCounter++;
    	    }
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("The job bags that their job code was not enabled. only preserved five years" );
			syslog.setMessageBody( "deleted " + delCounter + " job bag");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
    	    tx.commit();
    	    
    	    session.clear();
    	    tx = session.getTransaction();
    	    tx.begin();
    	    //刪除acct_section_job_code_mapping裡job_bag為0的
    	    String sql = "delete from acct_section_job_code_mapping where idf_JOB_CODE_NO in " +
    	    		"( select j.JOB_CODE_NO from job_code j left join job_bag jb on j.JOB_CODE_NO = jb.idf_JOB_CODE_NO where " +
    	    		"j.IS_ENABLED_CONTRACT <> 1 and j.autoDelete = 1  group by j.JOB_CODE_NO having count(jb.JOB_BAG_NO) = 0)";
            SQLQuery sqlQuery = session.createSQLQuery(sql);
    	    int sectionMapping  = sqlQuery.executeUpdate();
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("The job codes that is not enabled and have no job bag. Delete mapping account section" );
			syslog.setMessageBody( "deleted " + sectionMapping + " acctount section mapping");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
    	    
    	    
    	    //刪除acct_job_code_charge_item
    	    sql = "delete from acct_job_code_charge_item where idf_JOB_CODE_NO in " +
    	    		"( select j.JOB_CODE_NO from job_code j left join job_bag jb on j.JOB_CODE_NO = jb.idf_JOB_CODE_NO  where " +
    	    		"j.IS_ENABLED_CONTRACT <> 1 and j.autoDelete = 1  group by j.JOB_CODE_NO having count(jb.JOB_BAG_NO) = 0)";
            sqlQuery = session.createSQLQuery(sql);
    	    int jobCodeMapping  = sqlQuery.executeUpdate();
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("The job codes that is not enabled and have no job bag. Delete charge item mapping" );
			syslog.setMessageBody( "deleted " + jobCodeMapping + " acctount charge item mapping");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
            
    	    
    	  //刪除job code
    	    sql = "delete from job_code where JOB_CODE_NO in " +
    	    		"( select j.JOB_CODE_NO from job_code j left join job_bag jb on j.JOB_CODE_NO = jb.idf_JOB_CODE_NO where " +
    	    		"j.IS_ENABLED_CONTRACT <> 1 and j.autoDelete = 1  group by j.JOB_CODE_NO having count(jb.JOB_BAG_NO) = 0)";
            sqlQuery = session.createSQLQuery(sql);
    	    int jobCodeDel  = sqlQuery.executeUpdate();
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("The job codes that is not enabled and have no job bag. Delete job code" );
			syslog.setMessageBody( "deleted " + jobCodeDel + " job code");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
    	    tx.commit();
    	    
    	    
    	    session.clear();
    	    tx = session.getTransaction();
    	    tx.begin();    	        	    
    	    slCriteria = session.createCriteria(HistoryReport.class);
    	    //刪除HistoryReport
    	    List<HistoryReport> historyReports = slCriteria
			    .add(Restrictions.le("createDate", halfYearbefore.getTime()))
			    .list();
    	    delCounter = 0;
    	    for(HistoryReport historyReport : historyReports){    	    	
    	    	log.info("delete HostoryReport over six months ");
    	    	session.delete(historyReport);
    	    	delCounter++;
    	    }
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("history report before six months ago " );
			syslog.setMessageBody( "deleted " + delCounter + " history reports");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
    	    tx.commit();
    	    
    	    //刪除半年前的pdf檔案
    	    File docPath = new File(SetParamValFilter.getRealPath("/pdf/"));
    	    File logisticPath = new File(SetParamValFilter.getRealPath("/pdf/logistic"));
    	    log.info("searching " + docPath + " files");
    	    delCounter = 0;
    	    if(docPath.exists()){
    	    	File[] files = docPath.listFiles();
    	    	for(File file : files){
    	    		if(file.isFile() && file.lastModified() < halfYearbefore.getTimeInMillis()){
    	    			file.delete();
    	    			log.info("delete file " + file.getName());
    	    			delCounter ++;
    	    		}
    	    	}
    	    }
    	    if(logisticPath.exists()){
    	    	File[] files = logisticPath.listFiles();
    	    	for(File file : files){
    	    		if(file.isFile() && file.lastModified() < halfYearbefore.getTimeInMillis()){
    	    			file.delete();
    	    			log.info("delete file " + file.getName());
    	    			delCounter ++;
    	    		}
    	    	}
    	    }
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("deleted files in pdf folder before 6 months ago " );
			syslog.setMessageBody( "deleted " + delCounter + " files");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
    	    
    	    
    	    
    	    //刪除半年前的backup檔案
    	    String amexFolder = Util.getString("amex.folder");
    	    String amexBkFolder = Util.getString("amex.folder") + "\\backup\\";
    	    String citicardBkFolder = Util.getString("citicard.folder") + "\\backup\\";
    	    String onegoldBkFolder = Util.getString("onegold.folder") + "\\backup\\";
    	    String logisticBkFolder = Util.getString("logistic.folder") + "\\backup\\";
    	    String damageBkFolder = Util.getString("damage.folder") + "\\backup\\";
    	    String hscardBkFolder = Util.getString("hscard.folder") + "\\backup\\";
    	    String fbRegisterBkFolder = Util.getString("fbregist.folder") + "\\backup\\";
    	    String zipcodeBkFolder = Util.getString("zipcode.folder") + "\\backup\\";
    	    List<String> checkFolders = new ArrayList<String>();
    	    checkFolders.add(amexFolder);
    	    checkFolders.add(amexBkFolder);
    	    checkFolders.add(citicardBkFolder);
    	    checkFolders.add(onegoldBkFolder);
    	    checkFolders.add(logisticBkFolder);
    	    checkFolders.add(damageBkFolder);
    	    checkFolders.add(hscardBkFolder);
    	    checkFolders.add(fbRegisterBkFolder);
    	    checkFolders.add(zipcodeBkFolder);
    	    delCounter = 0;
    	    for(String folderStr : checkFolders){
    	    	File folder = new File(folderStr);
    	    	log.info("searching " + folder.getAbsolutePath() + " files");
        	    if(folder.exists()){
        	    	File[] files = folder.listFiles();
        	    	for(File file : files){
        	    		if(file.isFile() && file.lastModified() < halfYearbefore.getTimeInMillis()){
        	    			file.delete();
        	    			log.info("delete file " + file.getName());
        	    			delCounter++;
        	    		}
        	    	}
        	    }
    	    }
    	    syslog = new SysLog();
			syslog.setLogType("SYSTEM_DELETE");
			syslog.setSubject("deleted backup files before 6 months ago " );
			syslog.setMessageBody( "deleted " + delCounter + " files");
			syslog.setIsException(false);
			now = new Date();
			syslog.setCreateDate(now);
			syslogService.save(syslog);
    	    

    	    
	   }catch (Exception e) {
           log.error("", e);
           if(tx != null )
        	   tx.rollback();
       }finally{
    	   if(session != null && session.isOpen())
    		   session.close();
       }
	}
	
	public static void ftpDBbackup(){
		
		
		FtpUtil ftpClient = new FtpUtil();
		
		try {
			FtpConfig config = new FtpConfig();
			config.init();
			ftpClient.connectServer(config);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			File backupFolder = new File("d:\\Localbackups\\");
			File [] dbBackupFiles = backupFolder.listFiles();
			for(File file: dbBackupFiles){
			  if(file.getName().startsWith("jbm_backup_")){
			      ftpClient.uploadFile(file);
			  }
			}
			FTPFile[] ftpFiles = ftpClient.getFtpClient().listFiles();
			for (FTPFile ftpFile : ftpFiles) {  
	            if (ftpFile.isFile()) {  
	                Calendar cal = ftpFile.getTimestamp();
	                Calendar today = Calendar.getInstance();
	                Calendar oneMonthBefore = Calendar.getInstance();
	                oneMonthBefore.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE) - 20);
	                //20天前的都殺掉
	                if(cal.getTimeInMillis() < oneMonthBefore.getTimeInMillis())
	                	ftpClient.deleteFile(ftpFile.getName());
	                
	            }  
	        }  			
			ftpClient.closeServer();
			log.info("ftp successful");
		} catch (SocketException e) {
			log.error("", e);
			e.printStackTrace();
		} catch (IOException e) {
			log.error("", e);
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 變更物料資訊, LgInfo, 則變更物料 
	 */
	public static void changeLgInfo() {	
		Session session = HibernateSessionFactory.getSession();
    	session.clear();
    	Transaction tx = session.getTransaction();
    	tx.begin();    	
        try {
			List<Lginfo> list = lginfoService.getEffectiveLgInfo();
			for(int i=0; i<list.size(); i++) {
				Lginfo info = (Lginfo)list.get(i);
				String oldInfo="";
				String mailToAreaDisplay="";
				
				oldInfo = info.getProgNm()+":" +info.getCodeByCodeLgType().getCodeValueTw()+":" +info.getCodeByCodeMailCategory().getCodeValueTw();
				oldInfo = oldInfo + ":" +info.getCodeByCodeMailToPostoffice().getCodeValueTw()+":" +info.getMailToAreaDisplay()+":" +info.getPList();
				info.setProgNm(info.getNextProgNm());
				info.setCodeByCodeLgType(info.getCodeByNextCodeLgType());
				info.setCodeByCodeMailCategory(info.getCodeByNextCodeMailCategory());
				info.setCodeByCodeMailToPostoffice(info.getCodeByNextCodeMailToPostoffice());
				info.setCodeByCodeMailToArea1(info.getCodeByNextCodeMailToArea1());
				info.setCodeByCodeMailToArea2(info.getCodeByNextCodeMailToArea2());
				info.setCodeByCodeMailToArea3(info.getCodeByNextCodeMailToArea3());
				info.setCodeByCodeMailToArea4(info.getCodeByNextCodeMailToArea4());
				info.setCodeByCodeMailToArea5(info.getCodeByNextCodeMailToArea5());
				info.setCodeByCodeMailToArea6(info.getCodeByNextCodeMailToArea6());
				info.setLgDisplayQty(info.getNextLgDisplayQty());
				
				if (null!= info.getCodeByNextCodeMailToArea1() )
					mailToAreaDisplay = info.getCodeByNextCodeMailToArea1().getCodeValueTw()+",";
				if (null!= info.getCodeByNextCodeMailToArea2() )
					mailToAreaDisplay = mailToAreaDisplay + info.getCodeByNextCodeMailToArea2().getCodeValueTw()+",";
				if (null!= info.getCodeByNextCodeMailToArea3() )
					mailToAreaDisplay = mailToAreaDisplay + info.getCodeByNextCodeMailToArea3().getCodeValueTw()+",";
				if (null!= info.getCodeByNextCodeMailToArea4() )
					mailToAreaDisplay = mailToAreaDisplay + info.getCodeByNextCodeMailToArea4().getCodeValueTw()+",";
				if (null!= info.getCodeByNextCodeMailToArea5() )
					mailToAreaDisplay = mailToAreaDisplay + info.getCodeByNextCodeMailToArea5().getCodeValueTw()+",";
				if (null!= info.getCodeByNextCodeMailToArea6() )
					mailToAreaDisplay = mailToAreaDisplay + info.getCodeByNextCodeMailToArea6().getCodeValueTw()+",";				
				
				info.setMailToAreaDisplay(mailToAreaDisplay);
				info.setPList(info.getNextPList());

				
				//將 NextXXX reset 
				info.setNextProgNm("");
				info.setCodeByNextCodeLgType(null);
				info.setCodeByNextCodeMailCategory(null);
				info.setCodeByNextCodeMailToPostoffice(null);
				info.setCodeByNextCodeMailToArea1(null);
				info.setCodeByNextCodeMailToArea2(null);
				info.setCodeByNextCodeMailToArea3(null);
				info.setCodeByNextCodeMailToArea4(null);
				info.setCodeByNextCodeMailToArea5(null);
				info.setCodeByNextCodeMailToArea6(null);	
				info.setNextPList(null);				
				info.setNextEffectiveDate("");
				info.setNextLgDisplayQty(true);
				lginfoService.save(info);
				
				SysLog syslog = new SysLog();
				syslog.setLogType("CHANGE_MATERIAL");
				syslog.setSubject("LGInfo: " + info.getLgNo());
				syslog.setMessageBody(oldInfo);
				syslog.setIsException(false);
				Calendar calendar = Calendar.getInstance();
				Date now = new Date();
				calendar.setTime(now);
				syslog.setCreateDate(calendar.getTime());
				syslogService.save(syslog);

			}
			
			tx.commit();
        } catch (Exception e) {
            log.error("", e);
            tx.rollback();
        }finally{
        	if(session != null && session.isOpen())
        		session.close();
        }
	}		
	
	//自動插入兩年
	public static void createHoliday(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		Date today = new Date();
		Calendar begCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		//今年 1月 1日
	    begCal.set(begCal.get(Calendar.YEAR), 0, 1, 0, 0, 0);
	    begCal.set(Calendar.MILLISECOND, 0);
	    
	    //明年12月31日
	    endCal.set(begCal.get(Calendar.YEAR) + 1, 11, 31, 23, 59, 59);
	    
	    Session session = null;
		Transaction tx = null;
		
        try {
        	//先找出兩年內有設定好假期的時間
        	session = HibernateSessionFactory.getSession();
        	session.clear();        	
        	Criteria criteria = session.createCriteria(Holiday.class);        	
        	criteria.add(Restrictions.between("date", begCal.getTime(), endCal.getTime()));
        	List<Holiday> holidayList = criteria.list();
        	
        	tx = session.beginTransaction();
        	while(begCal.compareTo(endCal) < 0){
        		Holiday newDate = new Holiday();
        		newDate.setDate(begCal.getTime());
        		Boolean isHoliday = newDate.commonHolidayJudge();
        		newDate.setIsHoliday(isHoliday);
        		newDate.setModifedPerson("SYSTEM");
        		newDate.setModifiedDate(today);
        	    	
        		if(holidayList != null){
        		   //如果資料庫已經有設定假期了，就不再insert
        		   for(Holiday holiday : holidayList){
        			   if(holiday.getDate().getTime() == begCal.getTimeInMillis()){
        		           newDate = null;		     
        			   }
        		   }        		   
        		}
        		if(newDate != null){
        			session.save(newDate);
        		}        			
        		//加一天，繼續執行
        		begCal.add(Calendar.DATE, 1);
        	}        	
        	
        	if(tx != null )
        		tx.commit();
        }catch(Exception e){
        	log.error("", e);
        	e.printStackTrace();
        	if(tx != null )
        		tx.rollback();
        }finally{
        	if(session != null && session.isOpen())
        		session.close();
        }
	    
	}
	
	public static void createAFPJob() {
		//清空custFilePatternMap，重新載入 客戶工單樣本filepattern
		AmexService.setReloaded();
		log.info("createAFPJob Start....");
		/*
		execOneGold();
		execAmex();
		execCiticard();
		execLogistic();
		execDamage();
		execHScard();
		execFbRegister();
		notSendEddFinder();
		*/
		log.info("createAFPJob End....");
	}		
	
	public static void notSendEddFinder(){
		NotSendEddFinder.findNotSendEdd();
	}
	
	
	public static void execLogistic() {
		LogisticReader reader = new LogisticReader();
		reader.readfile();
	}
	
	public static void execFbRegister() {
		FbRegisterReader reader = new FbRegisterReader();
		reader.readfile();
	}
	
	public static void execOneGold() {
		OneGoldReader reader = new OneGoldReader();
		reader.readfile();
	}
	
	public static void execCiticard() {
		CiticardReader reader = new CiticardReader();
		reader.readfile();
	}	
	
	public static void execAmex() {
		AmexReader reader = new AmexReader();
		reader.readfile();
	}	
	
	public static void execDamage() {
		DamageReader reader = new DamageReader();
		reader.readfile();
	}	
	
	public static void execHScard() {
		HScardReader reader = new HScardReader();
		reader.readfile();
	}		
	
	public static void createDMJob() {
		log.info("createDMJob Start....");
		AmexReader reader = new AmexReader();
		Boolean ret = jobbagService.createDMJob();
		log.info("createDMJob End....");
	}		
	
	//每月一號delete lgform
	public static void deleteLgform(){
		log.info("delete lgform start....");
		String deleteStr = "delete from LGForm";
		
		Session session = HibernateSessionFactory.getSession();
		try{
		   Transaction tx = session.getTransaction();		   		
		   tx.begin();
		   SQLQuery query = session.createSQLQuery(deleteStr);
		   query.executeUpdate();
		   SysLog syslog = new SysLog();
		   syslog.setLogType("CHANGE_MATERIAL");
		   syslog.setSubject("Lgform:");
		   syslog.setMessageBody("Delete all data");
		   syslog.setIsException(false);
		   Calendar calendar = Calendar.getInstance();
		   Date now = new Date();
		   calendar.setTime(now);
		   syslog.setCreateDate(calendar.getTime());
		   session.save(syslog);
	       tx.commit();	       
	       log.info("delete lgform end....");
		}catch(Exception e){
			log.error("", e);
		}finally{
	       if(session.isOpen())
		      session.close();
	       session = null;
		}
	    
	}
	
	/**
	 * 郵局3+2碼相關作業批次
	 * 
	 */
	public static void zipCodeScheduleJob(){
		log.info("start to execute post related schedule...");
		readZipCodeFiles();
		log.info("end to execute post related schedule...");
	}
	
	/**
	 * 讀取郵局ZipCode資訊存入資料庫
	 * 
	 */
	public static void readZipCodeFiles(){
		log.info("start to readZipCode file");
		ZipcodeReader reader = ZipcodeReader.getInstance();
		reader.readfile();
		log.info("end to read ZipCode file");
	}

	public static void sendCitiMail(){
		//寄送citimail的實際功能
		Session session = HibernateSessionFactory.getSession();
		List<SchedulerEmail> emails = null;
		boolean isHoliday = false;
		//本日是不是假日
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    
	    //查詢日
	    Calendar queryDateBegin = Calendar.getInstance();
		queryDateBegin.set(queryDateBegin.get(Calendar.YEAR), queryDateBegin.get(Calendar.MONTH), queryDateBegin.get(Calendar.DATE) - 1, 0, 0, 0);
		queryDateBegin.set(Calendar.MILLISECOND, 0);
	    
		try{
			log.info("Strart mailing to Citi ");
			emails = session.createQuery("from SchedulerEmail where schedulerTime.timerId in (select timerId from SchedulerTime where custNo = 'CT' and url = '" + ReportAction.getCitimailurl() + "' and purpose is null)").list();						
		    Holiday holiday = (Holiday) session.get(Holiday.class, cal.getTime());
		    isHoliday = (holiday.getIsHoliday() != null && holiday.getIsHoliday()) ? true : false;
		    
		    //檢查查詢日是不是假日，是的話再減一天
		    Holiday queryDate = (Holiday) session.get(Holiday.class, queryDateBegin.getTime());
		    while(queryDate.getIsHoliday() != null && queryDate.getIsHoliday()){
		    	queryDateBegin.add(Calendar.DATE, -1);
		    	queryDate = (Holiday) session.get(Holiday.class, queryDateBegin.getTime());
		    }
		    log.info("query date is:" + queryDate.getDateStr());
		}catch(Exception e){
			log.error("", e);
		}finally{
			if(session != null && session.isOpen())
				session.close();
		}
		
	    
		Calendar queryDateEnd = Calendar.getInstance();
		queryDateEnd.set(queryDateBegin.get(Calendar.YEAR), queryDateBegin.get(Calendar.MONTH), queryDateBegin.get(Calendar.DATE), 23, 59, 59);
		queryDateEnd.set(Calendar.MILLISECOND, 998);
		if(!isHoliday && emails != null && emails.size() > 0){
			log.info("emails:" + emails.size());
			String[] mailTo = new String[emails.size()];
			for(int i = 0 ; i < emails.size() ; i++){
				mailTo[i] = emails.get(i).getEmail();
			}
			try {
				String targetFileNm = ReportAction.getMailReport( null, "CT", queryDateBegin.getTime(), queryDateEnd.getTime());
				if(targetFileNm != null && mailTo.length > 0){
				   File attachFile = new File(SetParamValFilter.getRealPath("/pdf"), targetFileNm);
 
                   MailSender mail = new MailSender();	    				
			       mail.setSub("E-Notification Report");
				   mail.setMailTo(mailTo);
				   mail.setMailFrom(Util.getString("mail.smtp.mailFrom"));
				   mail.setHost(Util.getString("mail.smtp.host"));
				   mail.setMsg("E-Notification Report\r\n內容請參考附件" );				      
				   mail.setFileName(attachFile.getAbsolutePath());
				   mail.send();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void sendFbMail(){
		//寄送citimail的實際功能
		Session session = HibernateSessionFactory.getSession();
		List<SchedulerEmail> emails = null;
		boolean isHoliday = false;
		//本日是不是假日
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    
	    //查詢日
	    Calendar queryDateBegin = Calendar.getInstance();
		queryDateBegin.set(queryDateBegin.get(Calendar.YEAR), queryDateBegin.get(Calendar.MONTH), queryDateBegin.get(Calendar.DATE) - 1, 0, 0, 0);
		queryDateBegin.set(Calendar.MILLISECOND, 0);
	    
		try{
			log.info("Strart mailing to FB");
			emails = session.createQuery("from SchedulerEmail where schedulerTime.timerId in (select timerId from SchedulerTime where custNo = 'FB' and url = '" + ReportAction.getCitimailurl() + "' and purpose is null)").list();						
		    Holiday holiday = (Holiday) session.get(Holiday.class, cal.getTime());
		    isHoliday = (holiday.getIsHoliday() != null && holiday.getIsHoliday()) ? true : false;
		    
		    //檢查查詢日是不是假日，是的話再減一天
		    Holiday queryDate = (Holiday) session.get(Holiday.class, queryDateBegin.getTime());
		    while(queryDate.getIsHoliday() != null && queryDate.getIsHoliday()){
		    	queryDateBegin.add(Calendar.DATE, -1);
		    	queryDate = (Holiday) session.get(Holiday.class, queryDateBegin.getTime());
		    }
		    
		}catch(Exception e){
			log.error("", e);
		}finally{
			if(session != null && session.isOpen())
				session.close();
		}
		
	    
		Calendar queryDateEnd = Calendar.getInstance();
		queryDateEnd.set(queryDateBegin.get(Calendar.YEAR), queryDateBegin.get(Calendar.MONTH), queryDateBegin.get(Calendar.DATE), 23, 59, 59);
		queryDateEnd.set(Calendar.MILLISECOND, 998);
		if(!isHoliday && emails != null && emails.size() > 0){
			String[] mailTo = new String[emails.size()];
			for(int i = 0 ; i < emails.size() ; i++){
				mailTo[i] = emails.get(i).getEmail();
			}
			try {
				String targetFileNm = ReportAction.getMailReport( null, "FB", queryDateBegin.getTime(), queryDateEnd.getTime());
				if(targetFileNm != null && mailTo.length > 0){
				   File attachFile = new File(SetParamValFilter.getRealPath("/pdf"), targetFileNm);
 
                   MailSender mail = new MailSender();	    				
				   mail.setSub("富邦寄件報告" + "_" + new SimpleDateFormat("yyyy/MM/dd").format(queryDateBegin.getTime()));
				   mail.setMailTo(mailTo);
				   mail.setMailFrom(Util.getString("mail.smtp.mailFrom"));
				   mail.setHost(Util.getString("mail.smtp.host"));
				   mail.setMsg("富士施樂-富邦郵件寄件報告\r\n內容請參考附件" );
				   mail.setFileName(attachFile.getAbsolutePath());
				   mail.send();			       
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
