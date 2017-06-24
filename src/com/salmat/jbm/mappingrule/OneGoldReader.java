package com.salmat.jbm.mappingrule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Logger;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.painter.util.FileOperate;
import com.painter.util.Util;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.bean.OneGoldFormater;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JobBag;
import com.salmat.jbm.hibernate.JobCode;
import com.salmat.jbm.hibernate.SysLog;
import com.salmat.jbm.service.AmexService;
import com.salmat.jbm.service.EmployeeService;
import com.salmat.jbm.service.JobBagService;
import com.salmat.jbm.service.JobCodeService;
import com.salmat.jbm.service.SchedulerService;
import com.salmat.jbm.service.SyslogService;

public class OneGoldReader {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(OneGoldReader.class);

	/* Constants */
	private static final OneGoldReader instance = new OneGoldReader();
	private static final AmexService amexService = AmexService.getInstance();
	private static final SyslogService syslogService = SyslogService.getInstance();
	private static final JobBagService jobbagService = JobBagService.getInstance();


	public OneGoldReader() {

	}

	public static OneGoldReader getInstance() {
		return instance;
	}
	

	
	public static void readfile() {
		String folderString = Util.getString("onegold.folder");
		String processFolderString = Util.getString("onegold.folder") +"\\processing\\" ;
		String backupFolderString = Util.getString("onegold.folder") +"\\backup\\";
        File folder = new File(folderString);


        if (folder.isDirectory()) {
            String[] in_files = folder.list();
            
            for (int i=0; i<in_files.length;i++) {
	                BufferedReader reader = null;
	                if ( in_files[i].substring(in_files[i].length()-3, in_files[i].length()).equalsIgnoreCase("RPT") ) {

		                Boolean retMoving = FileOperate.moveFile(folderString+in_files[i], processFolderString+in_files[i]);
		                File file = new File(processFolderString+in_files[i]);		                
		                if (retMoving ) {
			                try {
			                    reader = new BufferedReader(new FileReader(file));
			                    String text = null;
			                    OneGoldFormater formater = null;

			                    while ((text = reader.readLine()) != null) {
			                    	if (text.length()==63) { //長度判斷, 長度不對的資料, skip
				                    	formater = new OneGoldFormater();
				                		formater.setClientID("CT") ;//固定為 CT (CitiOne/CitiGold)
				                		formater.setSplname1(text.substring(0, 8).trim());
				                		formater.setType(text.substring(8, 16).trim()) ;
				                		formater.setStattype(text.substring(16, 20).trim()) ;//Stattype
				                		formater.setAccounts(text.substring(20, 28).trim()) ;//Accounts
				                		formater.setTotsheet(text.substring(28, 36).trim()) ;//Totsheet
				                		//formater.setTotpage(text.substring(36, 44).trim()) ;//Totpage
				                		formater.setTotpage(text.substring(28, 36).trim()) ;//Totpage Thor modified
				                		formater.setTotpage_wb(text.substring(44, 52).trim()) ;//Totpage_wb
				                		formater.setAfpFileName(text.substring(52, 60).trim()) ;//Splname, 實際的afp 檔案名稱
				                		formater.setBiz(text.substring(60, 61).trim()) ;//Biz
				                		formater.setCS(text.substring(61, 62).trim()) ;//CS
				                		formater.setEnvelop(text.substring(62, 63).trim()) ;//Envelop
				                		formater.setLogFileName(in_files[i]);//log file name
				                		String fileName = in_files[i];
				                		if(fileName.indexOf("_") > 0){
				                			fileName = fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf(".RPT"));
				                		}
				                		formater.setCycleDate(fileName.substring(0, 8)); //抓log 檔案日期
				                		Long lastModified = file.lastModified();
				                		Date date = new Date(lastModified);
				                		formater.setReceiveDate(date);
				                		formater.setLineData(text);
				                		
					                    // 找出 此資料的 jobCode findJobCodeByLogistic
				                		JobCode jobcode = amexService.findJobCodeByOneGold(formater);
				                		
				                		
				                		//建立工單 
				                    	HibernateSessionFactory.getSession().clear();
				                    	HibernateSessionFactory.getSession().getTransaction().begin();
				                    	
				                		if (null == jobcode) {
				                			SysLog syslog = new SysLog();
				                			syslog.setLogType("CREATE_JOBBAG_ONEGOLD");
				                			syslog.setSubject("無對應工單樣本 ");
				                			syslog.setMessageBody(formater.toString());
				                			syslog.setIsException(true);
				                			Calendar calendar = Calendar.getInstance();
				                			Date now = new Date();
				                			calendar.setTime(now);
				                			syslog.setCreateDate(calendar.getTime());
				                			syslogService.save(syslog);
				                		}
				                		else {
				                        	Integer accounts = Integer.parseInt(formater.getAccounts());
				                        	Integer pages = Integer.parseInt(formater.getTotpage());
				                        	Integer sheets = Integer.parseInt(formater.getTotsheet());
				                        	String afpName = formater.getAfpFileName();
				                        	Date cycleDate = formater.getRealCycleDate();
				                        	Date receiveDate = formater.getReceiveDate();
				                        	String logFilename  = in_files[i];
	
				                        	JobBag jobbag = jobbagService.createNewJobBag(jobcode, accounts, pages,  sheets,  afpName ,  cycleDate,  receiveDate, logFilename,0,0,0,0,0,0,0,0,0,0,0,0,formater.getLineData(),0,0,0,0,0,0,0);
				                            
				                        	if (null!= jobbag) {
				                        		List list = jobbagService.getJobBagSplites(jobbag);
					                            if(list != null){
					                            	jobbag.setSpliteCount(list.size());
					                            	jobbagService.save(jobbag);
					                            }
					                			SysLog syslog = new SysLog();
					                			syslog.setLogType("CREATE_JOBBAG_ONEGOLD");
					                			syslog.setSubject("完成建立工單 ");
					                			syslog.setMessageBody(jobbag.getJobBagNo() + ":" + formater.toString() );
					                			Calendar calendar = Calendar.getInstance();
					                			Date now = new Date();
					                			calendar.setTime(now);
					                			syslog.setCreateDate(calendar.getTime());
					                			syslogService.save(syslog);	
				                        	}
				                		}
				                        HibernateSessionFactory.getSession().getTransaction().commit();
			                    	}else{
			                    		SysLog syslog = new SysLog();
			                			syslog.setLogType("CREATE_JOBBAG_ONEGOLD");
			                			syslog.setSubject("輸入資料長度錯誤");
			                			syslog.setMessageBody(text.length() + ":" + text);			                			
			                			syslog.setCreateDate(new Date());
			                			syslog.setIsException(true);
			                			syslogService.save(syslog);	
			                    	}
			                    }
	
			                } catch (SecurityException e) {
			                	log.error("", e);
			                    e.printStackTrace();			                    
			                } catch (FileNotFoundException e) {
			                	log.error("", e);
			                    e.printStackTrace();
			                } catch (IOException e) {
			                	log.error("", e);
			                    e.printStackTrace();
			                } catch (Exception e) {
			                	log.error("", e);
			                    e.printStackTrace();		                    
			                } finally {
			                    try {
			                        if (reader != null) {
			                        	reader.close();			                        	
			                        }
			                    } catch (IOException e) {
			                        e.printStackTrace();
			                    }
			                    if(HibernateSessionFactory.getSession().isOpen())
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
		OneGoldReader logisticReader = new OneGoldReader();
		logisticReader.readfile();
	}

}
