package com.salmat.jbm.mappingrule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.painter.filter.SetParamValFilter;
import com.painter.util.FileOperate;
import com.painter.util.Util;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JobBag;
import com.salmat.jbm.hibernate.JobBagSplite;
import com.salmat.jbm.hibernate.JobCode;
import com.salmat.jbm.hibernate.SysLog;
import com.salmat.jbm.service.AmexService;
import com.salmat.jbm.service.EmployeeService;
import com.salmat.jbm.service.JobBagService;
import com.salmat.jbm.service.JobCodeService;
import com.salmat.jbm.service.SchedulerService;
import com.salmat.jbm.service.SyslogService;

public class LogisticReader {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(LogisticReader.class);

	/* Constants */
	private static final LogisticReader instance = new LogisticReader();
	private static final AmexService amexService = AmexService.getInstance();
	private static final SyslogService syslogService = SyslogService.getInstance();	
	private static final JobBagService jobbagService = JobBagService.getInstance();	


	public LogisticReader() {

	}

	public static LogisticReader getInstance() {
		return instance;
	}
	/*
	public static void readfile() {
		SQLQuery query = HibernateSessionFactory.getSession().createSQLQuery("select {j.*} from job_bag j where  j.CREATE_DATE between '2011-11-19 00:00:00.000'  and '2011-11-21 23:59:59.998'");
		query.addEntity("j", JobBag.class);
		List<JobBag> list = query.list();
		for(JobBag jobBag : list){
			
			if(!"ACCOUNTING_LOCKED".equals(jobBag.getJobBagStatus()) && !"EDD".equals(jobBag.getJobBagStatus()) && !"COMPLETED_LG".equals(jobBag.getJobBagStatus()) 
					){
			   HibernateSessionFactory.getSession().beginTransaction();
			   System.out.println("job bag status:" + jobBag.getJobBagStatus());
			   Set<JobBagSplite> set = jobBag.getJobBagSplites();
			   boolean allLGUpdate = true;
			   boolean allMPUpdate = true;
			   boolean allLPUpdate = true;
			   for(JobBagSplite splite: set){
				 if(splite.getLgCompletedDateByUser() == null)
					 allLGUpdate = false;
				 
				 if(splite.getMpCompletedDateByUser() == null)
					 allMPUpdate = false;
				 
				 if(splite.getLpCompletedDateByUser() == null)
					 allLPUpdate = false;
				 					 
			   }
			   String queryStr = "";
			   if(set != null && set.size() > 0){
			      if(allLGUpdate){
				      queryStr = "update job_bag set job_bag_status = 'COMPLETED_LG' where job_bag_no = ?";
			      }else if(allMPUpdate){
			          queryStr = "update job_bag set job_bag_status = 'COMPLETED_MP' where job_bag_no = ?";
			      }else if(allLPUpdate){
			          queryStr = "update job_bag set job_bag_status = 'COMPLETED_LP' where job_bag_no = ?";
			      }
			   }
				   
			   if(!"".equals(queryStr)){
			      query = HibernateSessionFactory.getSession().createSQLQuery(queryStr);			
			      query.setParameter(0, jobBag.getJobBagNo());
	              int ret5 = query.executeUpdate();
	              System.out.println("job bag no:" + jobBag.getJobBagNo());
	              System.out.println("update line:" + ret5);
	              System.out.println("queryStr:" + queryStr);
			   }
			   HibernateSessionFactory.getSession().getTransaction().commit();
		    }
		    		    
		}
        System.out.println("finished all");
		HibernateSessionFactory.closeSession();
	}
	*/


	public static void readfile() {
		String folderString = Util.getString("logistic.folder");
		String processFolderString = Util.getString("logistic.folder") +"\\processing\\" ;
		String backupFolderString = Util.getString("logistic.folder") +"\\backup\\";		

        File folder = new File(folderString);


        if (folder.isDirectory()) {
            String[] in_files = folder.list();
            for (int i = 0 ; i < in_files.length ; i++) {
                BufferedReader reader = null;
                if ( in_files[i].substring(in_files[i].length()-3, in_files[i].length()).equalsIgnoreCase("CSV") ) {
	                Boolean retMoving = FileOperate.moveFile(folderString+in_files[i], processFolderString+in_files[i]);
	                File file = new File(processFolderString+in_files[i]);
	                
	                //把logistic 檔案copy到/pdf/logistic下
	                File logisticPath = new File(SetParamValFilter.getRealPath("/pdf/logistic"));
	                if(!logisticPath.exists())
	                	logisticPath.mkdirs();
	                File logisticFile = new File(logisticPath, in_files[i]);	                
	                try{
	                	if(logisticFile.exists())
	                	   FileUtils.forceDelete(logisticFile);
	                	FileUtils.copyFile(file, logisticFile);
	                }catch(Exception e){
	                	log.error("", e);
	                }
	                
	                
	                String jobbagNo="";
	                if (retMoving) {
		                try {
		                    reader = new BufferedReader(new FileReader(file));
		                    String text = null;
		                    LogisticFormater formater = null;

		                    while ((text = reader.readLine()) != null && !text.trim().equals("")) {
		                    	try{
		                    	   formater = new LogisticFormater();
		                		   String[] tokens = text.split(","); 
		                		   formater.setClientID(tokens[0].trim()) ;//ClientID
		                		   formater.setAfpFileName(tokens[1].trim()) ;//Filename
		                		   formater.setCycleDate(tokens[2].trim()) ;//CycleDate
		                		   formater.setProcessDate(tokens[3].trim()) ;//ProcessDate==Received Date
		                		   formater.setAccounts(tokens[4].trim().equals("")? "0" : tokens[4].trim()) ;//Accounts
		                		   formater.setPages(tokens[5].trim().equals("")? "0" : tokens[5].trim()) ;//Pages
		                		   if(tokens.length > 6){
		                		       formater.setFeeder2(tokens[6].trim().equals("")? "0" : tokens[6].trim()) ;//Feeder2
		                		       formater.setFeeder3(tokens[7].trim().equals("")? "0" : tokens[7].trim()) ;//Feeder3
		                		       formater.setFeeder4(tokens[8].trim().equals("")? "0" : tokens[8].trim()) ;//Feeder4
		                		       formater.setFeeder5(tokens[9].trim().equals("")? "0" : tokens[9].trim()) ;//Feeder5
		                		       formater.setTray1(tokens[10].trim().equals("")? "0" : tokens[10].trim()) ;//Tray1
		                		       formater.setTray2(tokens[11].trim().equals("")? "0" : tokens[11].trim()) ;//Tray2
		                		       formater.setTray3(tokens[12].trim().equals("")? "0" : tokens[12].trim()) ;//Tray3
		                		       formater.setTray4(tokens[13].trim().equals("")? "0" : tokens[13].trim()) ;//Tray4
		                		   }
		                		   if (tokens.length==16) {
		                			   formater.setPattern(tokens[14].trim()); //pattern
		                			   formater.setSEQ(tokens[15].trim()); //SEQ
		                		   }
		                		   if (tokens.length>=23) {
		                			   formater.setPattern(tokens[14].trim()); //pattern
		                			   formater.setSEQ(tokens[15].trim()); //SEQ
		                			   formater.setP1accounts(tokens[16].trim().equals("")? "0" : tokens[16].trim());
		                			   formater.setP2accounts(tokens[17].trim().equals("")? "0" : tokens[17].trim());
		                			   formater.setP3accounts(tokens[18].trim().equals("")? "0" : tokens[18].trim());
		                			   formater.setP4accounts(tokens[19].trim().equals("")? "0" : tokens[19].trim());
		                			   formater.setP5accounts(tokens[20].trim().equals("")? "0" : tokens[20].trim());
		                			   formater.setP6accounts(tokens[21].trim().equals("")? "0" : tokens[21].trim());
		                			   formater.setPxaccounts(tokens[22].trim().equals("")? "0" : tokens[22].trim());		                			
		                		   }		                	
		                		   if (tokens.length >= 24) {
		                			   Integer tray5 = 0;
		                			   try{
		                				   tray5 = new Integer(tokens[23].trim());
		                			   }catch(Exception e){
		                				   log.error("", e);
		                			   }
		                			   formater.setTray5(tray5 + "");
		                		   }
		                		   if (tokens.length >= 25) {
		                			   Integer tray6 = 0;
		                			   try{
		                				   tray6 = new Integer(tokens[24].trim());
		                			   }catch(Exception e){
		                				   log.error("", e);
		                			   }
		                			   formater.setTray6(tray6 + "");
		                		   }
		                		   //formater.setSEQ("001"); //SEQ
		                		   formater.setLogFileName(in_files[i]);//log file name 
		                		   formater.setLineData(text);
		                		
		                		   if (formater.getClientID().equals("IN"))
		                			   formater.setClientID("FB");
		                		
			                       // 找出 此資料的 jobCode findJobCodeByLogistic
		                		   if (!formater.getAfpFileName().equalsIgnoreCase("Filename")) { //避開header
		                			   JobCode jobcode = amexService.findJobCodeByLogistic(formater);	
		                			
			                		
			                		   //建立工單 
			                    	   HibernateSessionFactory.getSession().clear();
			                    	   HibernateSessionFactory.getSession().getTransaction().begin();
			                    	
			                		   if (null == jobcode) {
			                			   SysLog syslog = new SysLog();
			                			   syslog.setLogType("CREATE_JOBBAG_LOGISTIC");
			                			   syslog.setSubject("無對應工單樣本 ");
			                			   syslog.setMessageBody(formater.toString());
			                			   syslog.setIsException(true);
			                			   Calendar calendar = Calendar.getInstance();
			                			   Date now = new Date();
			                			   calendar.setTime(now);
			                			   syslog.setCreateDate(calendar.getTime());
			                			   syslogService.save(syslog);
			                		   } else {			                			
			                        	   Integer accounts = Integer.parseInt(formater.getAccounts());
			                        	   Integer pages = Integer.parseInt(formater.getPages());

			                        	   Integer tray1 = Integer.parseInt(formater.getTray1());
			                        	   Integer tray2 = Integer.parseInt(formater.getTray2());
			                        	   Integer tray3 = Integer.parseInt(formater.getTray3());
			                        	   Integer tray4 = Integer.parseInt(formater.getTray4());
			                        	   Integer tray5 = formater.getTray5() == null? 0 : Integer.parseInt(formater.getTray5());
			                        	   Integer tray6 = formater.getTray6() == null? 0 : Integer.parseInt(formater.getTray6());
			                        	   Integer tray7 = formater.getTray7() == null? 0 : Integer.parseInt(formater.getTray7());
			                        	   Integer tray8 = formater.getTray8() == null? 0 : Integer.parseInt(formater.getTray8());
			                        	   Integer sheets =tray1 + tray2 + tray3 + tray4 + tray5 + tray6 + tray7 + tray8;
			                        	   //DHL的tray4有特殊用途，所以不加進去
			                        	   if("DH".equalsIgnoreCase(formater.getClientID())){
			                        		   sheets =tray1 + tray2 + tray3 + tray5 + tray6 + tray7 + tray8;
			                        	   }
			                        	
			                        	   Integer feeder2 = Integer.parseInt(formater.getFeeder2());
			                        	
			                        	   Integer feeder3 = Integer.parseInt(formater.getFeeder3());
			                        	   Integer feeder4 = Integer.parseInt(formater.getFeeder4());
			                        	   Integer feeder5 = Integer.parseInt(formater.getFeeder5());
			                        	   Integer p1accounts = 0;
			                        	   Integer p2accounts = 0;
			                        	   Integer p3accounts = 0;
			                        	   Integer p4accounts = 0;
			                        	   Integer p5accounts = 0;
			                        	   Integer p6accounts = 0;
			                        	   Integer pxaccounts = 0;
			                        	   if (null!=formater.getP1accounts() && formater.getP1accounts().length() >0 ) 
			                        		   p1accounts = Integer.parseInt(formater.getP1accounts());
			                        	   if (null!=formater.getP2accounts() && formater.getP2accounts().length() >0 ) 
			                        		   p2accounts = Integer.parseInt(formater.getP2accounts());
			                        	   if (null!=formater.getP3accounts() && formater.getP3accounts().length() >0 ) 
			                        		   p3accounts = Integer.parseInt(formater.getP3accounts());
			                        	   if (null!=formater.getP4accounts() && formater.getP4accounts().length() >0 ) 
			                        		   p4accounts = Integer.parseInt(formater.getP4accounts());
			                        	   if (null!=formater.getP5accounts() && formater.getP5accounts().length() >0 ) 
			                        		   p5accounts = Integer.parseInt(formater.getP5accounts());
			                        	   if (null!=formater.getP6accounts() && formater.getP6accounts().length() >0 ) 
			                        		   p6accounts = Integer.parseInt(formater.getP6accounts());
			                        	   if (null!=formater.getPxaccounts() && formater.getPxaccounts().length() >0 ) 
			                        		   pxaccounts = Integer.parseInt(formater.getPxaccounts());
			                        	
			                        	   String afpName = formater.getAfpFileName();
			                        	
			                        	   Date cycleDate = new Date();
			                        	   Date receiveDate = new Date();
			                        	   String cycleDate_str = formater.getCycleDate();
			                        	   String receiveDate_str = formater.getProcessDate();
			                        	
			                        	   //cycleDate_str 及 receiveDate_str 額外處理
			                        	   // if cycleDate_str=="" , cycleDate_str = receiveDate_str
  			                        	   // if receiveDate_str=="" , receiveDate_str = cycleDate_str			                        	
			                        	   if (( cycleDate_str.length()==0 || cycleDate_str.equalsIgnoreCase("　　　　　")) && receiveDate_str.length() >0)
			                        		   cycleDate_str = receiveDate_str ;
			                        	   if (( receiveDate_str.length()==0 || receiveDate_str.equalsIgnoreCase("　　　　　")) && cycleDate_str.length() >0)			                        	
			                        		   receiveDate_str = cycleDate_str ;			                        	
			                        	
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
				                        	   try {
				                        		   SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
				                        		   cycleDate = sdf1.parse(cycleDate_str);	
							                   } catch (ParseException e) {
							                       e.printStackTrace();
							                       log.error("日期格式錯誤" + formater.toString());
							                   }
			                        	   }
			                        	
			                        	   if (receiveDate_str.length() >0 && !receiveDate_str.equalsIgnoreCase("　　　　　") ) {
				                        	   String receiveDateArray[] = receiveDate_str.split("/");						                
				                        	   if (receiveDateArray.length ==3 ) {
				                        		   String YYYY= receiveDateArray[0];
				                        		   String MM= receiveDateArray[1];
				                        		   String DD= receiveDateArray[2];
				                        		
				                        		   if (YYYY.length() < 3) 
				                        			   YYYY = "20"+ YYYY;
				                        		
				                        		   if (MM.length() < 2) 
				                        			   MM = "0"+ MM;
				                        		
				                        		   if (DD.length() < 2) 
				                        			   DD = "0"+ DD;			                        		
				                        		   receiveDate_str = YYYY +"/" + MM+ "/" + DD;
				                        	   }
				                        	   try {
				                        		   SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
				                        		   receiveDate = sdf1.parse(receiveDate_str);	
							                   } catch (ParseException e) {
							                       e.printStackTrace();
							                       log.error("日期格式錯誤" + formater.toString());
							                   }
			                        	   }
						                
			                        	   String logFilename  = in_files[i];

			        	                   JobBag jobbag = jobbagService.createNewJobBag(jobcode, accounts, pages,  sheets,  afpName ,  cycleDate,  receiveDate, logFilename,feeder2,feeder3,feeder4,feeder5,tray1,tray2,tray3,tray4,tray5,tray6,tray7,tray8 ,formater.getLineData(),  p1accounts,  p2accounts,  p3accounts,  p4accounts,  p5accounts,  p6accounts,  pxaccounts);
			        	                
			        	                   //SB 夾寄2個DM不用收費, 特殊規則 -- 
			        	                   //很怪，看能不能改
			        	                   if (null!= jobbag && jobbag.getCustomer().getCustNo().equalsIgnoreCase("SB")) {
			        	                	   int feederCounter = 0;
			        	                	   if(jobbag.getFeeder2() != null && jobbag.getFeeder2() > 0)
			        	                	      feederCounter++;
			        	                	   if(jobbag.getFeeder3() != null && jobbag.getFeeder3() > 0)
				        	                	  feederCounter++;
			        	                	   if(jobbag.getFeeder4() != null && jobbag.getFeeder4() > 0)
					        	                  feederCounter++;
			        	                	   if(jobbag.getFeeder5() != null && jobbag.getFeeder5() > 0)
					        	                  feederCounter++;
			        	                	   
			        	                	   if(feederCounter > 1){
			        	                	      //(I)	Feeder2>0 AND Feeder3>0 AND Feeder4>0 AND Feeder5>0
			        	                	      //SET Feeder2 = 0, Feeder3=0
			        	                	      if (jobbag.getFeeder2().compareTo(0) >0 && jobbag.getFeeder3().compareTo(0) >0 && jobbag.getFeeder4().compareTo(0) >0 && jobbag.getFeeder4().compareTo(0) >0) {
			        	                		      jobbag.setFeeder2(0);
			        	                		      jobbag.setFeeder3(0);
			        	                	      } 
			        	                	      //(II)	(Feeder2>0 AND Feeder3>0 AND Feeder4>0) OR (Feeder2>0 AND Feeder3>0 AND Feeder5>0)
			        	                	      //SET Feeder2 = 0, Feeder3=0
			        	                	      else if ( (jobbag.getFeeder2().compareTo(0) >0 && jobbag.getFeeder3().compareTo(0) >0 && jobbag.getFeeder4().compareTo(0) >0 )  || (jobbag.getFeeder2().compareTo(0) >0 && jobbag.getFeeder3().compareTo(0) >0 && jobbag.getFeeder5().compareTo(0) >0 ) ) {
			        	                		     jobbag.setFeeder2(0);
			        	                		     jobbag.setFeeder3(0);
			        	                	      } 			        	                	
			        	                	      //(III)	Feeder2>0 AND Feeder4>0 AND Feeder5>0
			        	                	      //SET Feeder2 = 0, Feeder5 = 0
			        	                	      else if ( jobbag.getFeeder2().compareTo(0) >0 && jobbag.getFeeder4().compareTo(0) >0 && jobbag.getFeeder5().compareTo(0) >0 )  {
			        	                		      jobbag.setFeeder2(0);
			        	                		      jobbag.setFeeder5(0);
			        	                	      }			        	                	
			        	                	      //(IV)	Feeder3>0 AND Feeder4>0 AND Feeder5>0
			        	                	      //SET Feeder3=0, Feeder4=0
			        	                	      else if ( jobbag.getFeeder3().compareTo(0) >0 && jobbag.getFeeder4().compareTo(0) >0 && jobbag.getFeeder5().compareTo(0) >0 )  {
			        	                		     jobbag.setFeeder3(0);
			        	                		     jobbag.setFeeder4(0);
			        	                	      }				        	                	
			        	                	      //(V)	Others
			        	                	      //SET Feeder2=0, Feeder3=0, Feeder4=0, Feeder5=0
			        	                	      else {
			        	                		     jobbag.setFeeder2(0);
			        	                		     jobbag.setFeeder3(0);	
			        	                		     jobbag.setFeeder4(0);
			        	                		     jobbag.setFeeder5(0);				        	                		
			        	                	      }
			        	                       }
			        	                	   jobbagService.save(jobbag);
			        	                   }
			        	                   // end SB 夾寄2個DM不用收費, 特殊規則			        	                
			        	                   if (null!= jobbag) {
				        	                   List list = jobbagService.getJobBagSplites(jobbag);
				                               if(list != null){
				                            	   jobbag.setSpliteCount(list.size());
				                            	   jobbagService.save(jobbag);
				                               }
			        	                	
				        	                   jobbagNo = jobbag.getJobBagNo();
				                			   SysLog syslog = new SysLog();
				                			   syslog.setLogType("CREATE_JOBBAG_LOGISTIC");
				                			   syslog.setSubject("完成建立工單 ");
				                			   syslog.setMessageBody(jobbag.getJobBagNo() + ":" + formater.toString() );
				                			   Calendar calendar = Calendar.getInstance();
				                			   Date now = new Date();
				                			   calendar.setTime(now);
				                			   syslog.setCreateDate(calendar.getTime());
				                			   syslogService.save(syslog);	
			        	                   }
			                        	
			                		   }
			                		/*
			                        if (null!= jobbagNo && jobbagNo.length() >0) {

				                		JobBag mergeJabbag = jobbagService.findById(jobbagNo);
				                		
				                        //若是 ddn,ddnn..合併處理, LogFileSeq != 001 
				                		if (null!=mergeJabbag && mergeJabbag.getLogFileSeq() != null && !mergeJabbag.getLogFileSeq().equalsIgnoreCase("001")) {
				                			//HibernateSessionFactory.getSession().getTransaction().begin();	
				                			Boolean ret= jobbagService.mergeJabbag(mergeJabbag);
				                			
				                			if (ret)
						                        HibernateSessionFactory.getSession().getTransaction().commit();
						                    
				                			else {
				                				HibernateSessionFactory.getSession().getTransaction().rollback();
				                			}
				                			
				                		}else{
				                			HibernateSessionFactory.getSession().getTransaction().commit();			    
					                        HibernateSessionFactory.getSession().clear();			                        
				                		}
			                        }else{
			                        */
			                            HibernateSessionFactory.getSession().getTransaction().commit();			    
				                        HibernateSessionFactory.getSession().clear();
		                        
			                        //}
		                		   }
		                       }catch(Exception e){
		                    	   e.printStackTrace();
		                    	   log.error("", e);
		                    	   if(HibernateSessionFactory.getSession().getTransaction().isActive())
				                	    HibernateSessionFactory.getSession().getTransaction().rollback();
		                    	   SysLog syslog = new SysLog();
	                			   syslog.setLogType("CREATE_JOBBAG_LOGISTIC");
	                			   syslog.setSubject("建立工單發生錯誤 ");
	                			   syslog.setMessageBody( e.getLocalizedMessage() );
	                			   syslog.setIsException(true);
	                			   syslog.setCreateDate(new Date());
	                			   syslogService.save(syslog);
		                       }
		                    }		                    
		                } catch (FileNotFoundException e) {		                	
		                    e.printStackTrace();
		                    log.error("", e);
		                    if(HibernateSessionFactory.getSession().getTransaction().isActive())
		                	    HibernateSessionFactory.getSession().getTransaction().rollback();
		                } catch (IOException e) {		                	
		                    e.printStackTrace();
		                    log.error("", e);
		                    if(HibernateSessionFactory.getSession().getTransaction().isActive())
			                	   HibernateSessionFactory.getSession().getTransaction().rollback();		                
		                } finally {
		                    try {
		                        if (reader != null) {
		                            reader.close();
		                        }
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }
	                        HibernateSessionFactory.closeSession();
		                }
	                }
		            //將processing 資料, 搬到 backup 
		             SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		             String backupFileName = file.getName() + "."+formatter.format(new Date());
		             Boolean ret = FileOperate.moveFile(processFolderString+file.getName(), backupFolderString+backupFileName);
	             } //
           }// for 
       }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final SchedulerService scheduleService = SchedulerService.getInstance();
		LogisticReader logisticReader = new LogisticReader();
		logisticReader.readfile();
	}

}
