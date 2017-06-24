package com.salmat.jbm.mappingrule;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.painter.util.FileOperate;
import com.painter.util.Util;
import com.salmat.jbm.bean.ZipCodeFormater;
import com.salmat.jbm.hibernate.EddNotSend;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JobBag;
import com.salmat.jbm.hibernate.SysLog;
import com.salmat.jbm.hibernate.ZipcodeDetail;
import com.salmat.jbm.hibernate.ZipcodeInfo;
import com.salmat.jbm.service.JobBagService;
import com.salmat.jbm.service.SyslogService;
import com.salmat.jbm.service.ZipcodeDetailService;
import com.salmat.jbm.service.ZipcodeInfoService;

/**
 * 讀入3+2郵遞區號資料
 * 
 * @author JamesTsai
 *
 */
public class NotSendEddFinder {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(NotSendEddFinder.class);

	/* Constants */
	private static final NotSendEddFinder instance = new NotSendEddFinder();
	private static final ZipcodeInfoService zipcodeInfoService = ZipcodeInfoService.getInstance();
	private static final ZipcodeDetailService zipcodeDetailService = ZipcodeDetailService.getInstance();
	private static final SyslogService syslogService = SyslogService.getInstance();	
	private static final JobBagService jobbagService = JobBagService.getInstance();	
	private static Map<Integer, Integer> dispatchTimeMappings; // 交寄方式(JBM代碼,郵局代碼)
	private static Map<Integer, Integer> dispatchKindMappings; // 郵資單種類(JBM代碼,郵局代碼)

	private NotSendEddFinder() {
		
	}

	public static NotSendEddFinder getInstance() {
		return instance;
	}


	public static void findNotSendEdd(){
		//*注意*
		//尚未implement 紙本加電子的檔案轉換
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			Date now = new Date();
			//只找十天內的
			List<EddNotSend> notSends =  session.createQuery("from EddNotSend where syncToJobbag = false or syncToJobbag is null and insertDate > ?").setParameter(0, new Date(now.getTime() - 10L * 24 * 60 * 60 * 1000)).list();
			//同檔名且同客戶，且不是從damage這個channel轉過來的，
			Query query = session.createQuery("select jobBag from JobBag jobBag inner join jobBag.customer as customer "
					+ " where jobBag.receiveDate between ? and ? and customer.custNo = ? and jobBag.afpName = ? and (jobBag.fromDamage is null or jobBag.fromDamage = false)"
					+ " and (jobBag.isDamage is null or jobBag.isDamage = false) and (jobBag.isDeleted is null or jobBag.isDeleted = false)");
			if(notSends != null && notSends.size() > 0){
				for(EddNotSend notSend : notSends){
					Date processDate = notSend.getInsertDate();
					String afpName = notSend.getAfpName();
					String custNo = notSend.getCustNo();
					//處理日在insertDate的七天前及五天後 
					Date receiveDateBeg = new Date(processDate.getTime() - (7L * 24 * 60 * 60 * 1000)); //7天前
					Date receiveDateEnd = new Date(processDate.getTime() + (5L * 24 * 60 * 60 * 1000)); //5天後
					query.setParameter(0, receiveDateBeg);
					query.setParameter(1, receiveDateEnd);
					query.setParameter(2, custNo);
					query.setParameter(3, afpName);
					List<JobBag> jobBags = query.list();
					if(jobBags != null && jobBags.size() == 1){
						JobBag jobBag = jobBags.get(0);
						int notSendNo = notSend.getNotSent() == null? 0 : notSend.getNotSent();
						jobBag.setNotSent(notSendNo * -1); //設為負值
						notSend.setSyncToJobbag(true);
						notSend.setJobBagNo(jobBag.getJobBagNo());
						session.update(jobBag);
						session.update(notSend);	
						SysLog syslog = new SysLog();
	         			syslog.setLogType("CREATE_NOT_SEND_NO");
	         			syslog.setSubject("產生未寄送EDD數字  ");
	         			syslog.setMessageBody(jobBag.getJobBagNo() + ":未寄出EDD:" + notSendNo);
	         			syslog.setIsException(false);
	         			Calendar calendar = Calendar.getInstance();	         			
	         			calendar.setTime(now);
	         			syslog.setCreateDate(calendar.getTime());
	         			session.save(syslog);
					}else if(jobBags.size() > 1){
						SysLog syslog = new SysLog();
	         			syslog.setLogType("CREATE_NOT_SEND_NO");
	         			syslog.setSubject("多Job Bag");
	         			String messageBody = notSend.getAfpName() + "|" + notSend.getInsertDate() + "|";
	         			for(JobBag jobBag : jobBags){
	         				messageBody += jobBag.getJobBagNo() + ":";
	         			}
	         			syslog.setMessageBody(messageBody);
	         			syslog.setIsException(true);
	         			Calendar calendar = Calendar.getInstance();
	         			calendar.setTime(now);
	         			syslog.setCreateDate(calendar.getTime());
	         			session.save(syslog);
					}else{
						SysLog syslog = new SysLog();
	         			syslog.setLogType("CREATE_NOT_SEND_NO");
	         			syslog.setSubject("找不到Job Bag");
	         			String messageBody =  notSend.getAfpName() + "|" + notSend.getInsertDate() + "|";	         			
	         			syslog.setMessageBody(messageBody + "無法找到對應的Job Bag");
	         			syslog.setIsException(true);
	         			Calendar calendar = Calendar.getInstance();
	         			calendar.setTime(now);
	         			syslog.setCreateDate(calendar.getTime());
	         			session.save(syslog);
					}					
				}
			}
			tx.commit();
		}catch(Exception e){
			if(tx != null)
				tx.rollback();
			log.error("", e);
			SysLog syslog = new SysLog();
 			syslog.setLogType("CREATE_NOT_SEND_NO");
 			syslog.setSubject("Exception"); 				         			
 			syslog.setMessageBody(e.getMessage());
 			syslog.setIsException(true);
 			Calendar calendar = Calendar.getInstance();
 			Date now = new Date();
 			calendar.setTime(now);
 			syslog.setCreateDate(calendar.getTime());
 			syslogService.save(syslog);
			
		}finally{
			if(session != null && session.isOpen())
				session.close();
		}
				
	}
}
