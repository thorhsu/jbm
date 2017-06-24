package com.salmat.jbm.mappingrule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.painter.util.FileOperate;
import com.painter.util.Util;
import com.salmat.jbm.bean.FbRegisterFormater;
import com.salmat.jbm.hibernate.FbRegisterNos;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JobBag;
import com.salmat.jbm.hibernate.SysLog;
import com.salmat.jbm.service.FbRegisterNosService;
import com.salmat.jbm.service.JobBagService;
import com.salmat.jbm.service.SyslogService;

public class FbRegisterReader {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(FbRegisterReader.class);

	/* Constants */
	private static final FbRegisterReader instance = new FbRegisterReader();
	private static final SyslogService syslogService = SyslogService.getInstance();	
	private static final JobBagService jobbagService = JobBagService.getInstance();	
	private static final FbRegisterNosService fbRegisterNosService = FbRegisterNosService.getInstance();


	public FbRegisterReader() {

	}

	public static FbRegisterReader getInstance() {
		return instance;
	}


	public static void readfile() {
		
		String folderString = Util.getString("fbregist.folder");
		String processFolderString = Util.getString("fbregist.folder") +"\\processing\\" ;
		String backupFolderString = Util.getString("fbregist.folder") +"\\backup\\";
        File folder = new File(folderString);
        File processFolder = new File(processFolderString);
        File backupFolder = new File(backupFolderString);
        if(!processFolder.exists())
        	processFolder.mkdirs();
        if(!backupFolder.exists())
        	backupFolder.mkdirs();
        Calendar cal = Calendar.getInstance();

        if (folder.isDirectory()) {
            String[] in_files = folder.list();
            for (int i=0; i<in_files.length;i++) {
            	File csvFile = new File(processFolderString, in_files[i]);            	
            	//超過30天就移去backup
            	if(csvFile.isFile() &&  cal.getTimeInMillis() - csvFile.lastModified() > (30L * 24 * 60 * 60 * 1000)){
	                Boolean ret = FileOperate.moveFile(processFolderString + in_files[i], backupFolderString + in_files[i]);
	                continue;
            	}
            	
            	
            	boolean parsingSuccess = true;

                BufferedReader reader = null;
                if ( in_files[i].substring(in_files[i].length()-3, in_files[i].length()).equalsIgnoreCase("CSV") ) {
                	//移到processing folder
	                Boolean retMoving = FileOperate.moveFile(folderString + in_files[i], processFolderString+in_files[i]);
	                File file = new File(processFolderString + in_files[i]);		
	                if (retMoving) {
	                	//是不是有重送，有的話把之前的記錄殺掉
	                	List<FbRegisterNos> findList =  fbRegisterNosService.findByProperty("csvName", in_files[i]);
	                	if(findList != null && findList.size() > 0){
	                	   HibernateSessionFactory.getSession().beginTransaction();
	                	   for(FbRegisterNos fbRegisterNos : findList ){
	                		   fbRegisterNosService.delete(fbRegisterNos);
	                	   }	                	
	                	   HibernateSessionFactory.getSession().getTransaction().commit();
	                	}
	                	HibernateSessionFactory.getSession().beginTransaction();
		                try {		                	
		                    reader = new BufferedReader(new FileReader(file));
		                    String text = null;
		                    while ((text = reader.readLine()) != null && !text.trim().equals("")) {
		                       String [] textSplits = text.split(",");
		                       if(textSplits.length == 5){
                                  FbRegisterFormater fbRegisterFormater = new FbRegisterFormater();
                                  fbRegisterFormater.setAfpName(textSplits[0].trim());
                                  fbRegisterFormater.setCycleDate(textSplits[1].trim());
                                  fbRegisterFormater.setRegisterNoBegin(textSplits[2].trim());
                                  fbRegisterFormater.setRegisterNoEnd(textSplits[3].trim());
                                  fbRegisterFormater.setFilePattern(textSplits[4].trim());
                                  //取得無副檔名的檔案名稱
                                  String afpName =  fbRegisterFormater.getAfpNameWithoutExtensionName();
                                  String registerNoBegin = fbRegisterFormater.getRegisterNoBegin();
                                  String registerNoEnd = fbRegisterFormater.getRegisterNoEnd();
                                  Date cycleDate = fbRegisterFormater.getCycleDate();
                                  List<JobBag> jobBags = jobbagService.findJobBagsByCycleAndCustNoAndFile(afpName, "FB", cycleDate);                                  
                                  if(jobBags == null || jobBags.size() != 1){
                                	  //取得有副檔名的檔案名稱，再試一次看看
                                	  afpName = fbRegisterFormater.getAfpName();
                                	  jobBags = jobbagService.findJobBagsByCycleAndCustNoAndFile(afpName, "FB", cycleDate);
                                  }
                                  //完全找不到時，找IN看看
                                  if(jobBags == null || jobBags.size() == 0){
                                	  afpName = fbRegisterFormater.getAfpNameWithoutExtensionName();
                                      jobBags = jobbagService.findJobBagsByCycleAndCustNoAndFile(afpName, "IN", cycleDate);
                                      if(jobBags == null || jobBags.size() != 1){
                                    	  afpName = fbRegisterFormater.getAfpName();
                                    	  jobBags = jobbagService.findJobBagsByCycleAndCustNoAndFile(afpName, "IN", cycleDate);
                                      }
                                  }
                                  
                                  
                                  if(jobBags != null && jobBags.size() == 1){
                                	  JobBag jobBag = jobBags.get(0);
                                	  FbRegisterNos fbRegisterNos = new FbRegisterNos();
                                	  fbRegisterNos.setAfpName(afpName);
                                	  fbRegisterNos.setCsvName(in_files[i]);
                                	  fbRegisterNos.setCycleDate(jobBag.getCycleDate());
                                	  fbRegisterNos.setJobBagNo(jobBag.getJobBagNo());
                                	  fbRegisterNos.setJobCodeNo(jobBag.getJobCode().getJobCodeNo());
                                	  fbRegisterNos.setReadLine(text);
                                	  fbRegisterNos.setReceiveDate(jobBag.getReceiveDate());
                                	  fbRegisterNos.setRegisterNoBegin(registerNoBegin);
                                	  fbRegisterNos.setRegisterNoEnd(registerNoEnd);
                                	  HibernateSessionFactory.getSession().save(fbRegisterNos);
                                	  
                                	  SysLog syslog = new SysLog();
                          			  syslog.setLogType("CREATE_FB_REGNO");
                          			  syslog.setSubject("產生富邦掛號號碼");
                          			  syslog.setMessageBody(jobBag.getJobBagNo() + ":" +  afpName + ":" + registerNoBegin + "~" + registerNoEnd +  ":" + in_files[i] );
                          			  syslog.setIsException(false);                      			  
                          			  syslog.setCreateDate(new Date());
                          			  syslogService.save(syslog);
                                  }else if(jobBags == null || jobBags.size() == 0){
                                	  //有可能尚未送到
                                	  parsingSuccess = false;                                	                                  	  
                                	  SysLog syslog = new SysLog();
                          			  syslog.setLogType("CREATE_FB_REGNO");
                          			  syslog.setSubject("找不到JobBag");
                          			  syslog.setMessageBody( afpName + ":" + registerNoBegin + "~" + registerNoEnd + ":" + in_files[i] );
                          			  syslog.setIsException(true);                      			  
                          			  syslog.setCreateDate(new Date());
                          			  syslogService.save(syslog);
                                  }else if(jobBags != null && jobBags.size() > 1){    
                                	  //超過一個不算解析錯誤，所以parsing success flag不設為false
                                	  SysLog syslog = new SysLog();
                          			  syslog.setLogType("CREATE_FB_REGNO");
                          			  syslog.setSubject("找到超過一個JobBag");
                          			  syslog.setMessageBody(afpName + ":" + registerNoBegin + "~" + registerNoEnd + ":" + in_files[i] );
                          			  syslog.setIsException(true);                      			  
                          			  syslog.setCreateDate(new Date());
                          			  syslogService.save(syslog);
                                  }
		                       }                               
		                    }
		                    HibernateSessionFactory.getSession().getTransaction().commit();
		                } catch (Exception e) {
		                	parsingSuccess = false;
		                	HibernateSessionFactory.getSession().getTransaction().rollback();
		                	SysLog syslog = new SysLog();
	            			syslog.setLogType("CREATE_FB_REGNO");
	            			syslog.setSubject("產生富邦掛號號碼異常");
	            			syslog.setMessageBody(in_files[i] + ":" + e.getMessage());
	            			syslog.setIsException(true);                      			  
	            			syslog.setCreateDate(new Date());
	            			syslogService.save(syslog);

		                    log.error("", e);
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
	                
	                if(parsingSuccess){
		               // 如果解析成功且jobBag都有找到，將processing 資料, 搬到 backup 
		                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		                String backupFileName = file.getName() + "."+formatter.format(new Date());
		                Boolean ret = FileOperate.moveFile(processFolderString + file.getName(), backupFolderString + backupFileName);            	
                    }else {	                	
	                	FileOperate.moveFile(processFolderString + file.getName(), folderString + file.getName());
	                }		                
	             } //
           }// for 
       }
	}


}
