package com.salmat.jbm.mappingrule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.painter.util.FileOperate;
import com.painter.util.Util;
import com.salmat.jbm.bean.HScardFormater;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.bean.OneGoldFormater;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.HsCardRpt;
import com.salmat.jbm.hibernate.JobBag;
import com.salmat.jbm.hibernate.JobCode;
import com.salmat.jbm.hibernate.SysLog;
import com.salmat.jbm.service.AmexService;
import com.salmat.jbm.service.EmployeeService;
import com.salmat.jbm.service.JobBagService;
import com.salmat.jbm.service.JobCodeService;
import com.salmat.jbm.service.SchedulerService;
import com.salmat.jbm.service.SyslogService;

public class HScardReader {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(HScardReader.class);

	/* Constants */
	private static final HScardReader instance = new HScardReader();
	private static final AmexService amexService = AmexService.getInstance();
	private static final SyslogService syslogService = SyslogService.getInstance();
	private static final JobBagService jobbagService = JobBagService.getInstance();


	public HScardReader() {

	}

	public static HScardReader getInstance() {
		return instance;
	}
	

	
	public static void readfile() {
		String folderString = Util.getString("hscard.folder");
		String processFolderString = Util.getString("hscard.folder") +"\\processing\\" ;
		String backupFolderString = Util.getString("hscard.folder") +"\\backup\\";
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
			                    HScardFormater formater = null;
                                int counter = 1;
			                    while ((text = reader.readLine()) != null) {
			                    	if(text.length() < 101 || text.length() > 256){
			                			SysLog syslog = new SysLog();
			                			syslog.setLogType("CREATE_JOBBAG_HSCARD");
			                			syslog.setSubject("輸入資料長度錯誤");
			                			syslog.setMessageBody(text.length() + ":" + text);			                			
			                			syslog.setCreateDate(new Date());
			                			syslog.setIsException(true);
			                			syslogService.save(syslog);	
			                    	}
			                    	if (text.length() >= 101 && text.length() <= 256) { //長度判斷, 長度不對的資料, skip
				                    	formater = new HScardFormater();
				                		formater.setClientID("HS") ;//固定為 HS
				                		String receiveDateString = text.substring(0, 12).trim(); 	//PDATE
				                		//formater.setReceiveDate(receiveDate)(
				                		formater.setAfpFileName(text.substring(12, 27).trim()) ;	//FILENAME, 實際的afp 檔案名稱
				                		formater.setAccounts(text.substring(27, 36).trim());//ACCOUNTS
				                		formater.setPageno(text.substring(36, 45).trim());//PAGES
				                		formater.setAA(text.substring(45, 51).trim());//AA
				                		formater.setBB(text.substring(51, 57).trim());	//BB
				                		formater.setCC(text.substring(57, 63).trim()) ;//CC
				                		formater.setGG(text.substring(63, 69).trim());//GG
				                		formater.setF2(text.substring(69, 77).trim());		//FEEDER2
				                		formater.setF3(text.substring(77, 85).trim());		//FEEDER3
				                		formater.setF4(text.substring(85, 93).trim()) ;//FEEDER4
				                		formater.setF5(text.substring(93, 101).trim()) ;//FEEDER5				                		
				                		formater.setLogFileName(in_files[i]);				//log file Name

				                		formater.setCycleDate(in_files[i].substring(4, 8)); //抓log 檔案日期, hsbc1017.RPT
				                		
				                		//收檔日期
				                		//格式 10/26/2011
			                        	try {
			                        		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			                        		Date receiveDate = sdf1.parse(receiveDateString);	
			                        		formater.setReceiveDate(receiveDate);
						                } catch (ParseException e) {
						                    e.printStackTrace();
						                    formater.setReceiveDate(new Date());
						                    log.error("日期格式錯誤" + formater.toString());
						                }

				                		formater.setLineData(text);
				                		
					                    // 找出 此資料的 jobCode findJobCodeByHScard
				                		JobCode jobcode = amexService.findJobCodeByHScard(formater);
				                		
				                		
				                		//建立工單 
				                    	HibernateSessionFactory.getSession().clear();
				                    	HibernateSessionFactory.getSession().getTransaction().begin();
				                    	
				                		if (null == jobcode) {
				                			SysLog syslog = new SysLog();
				                			syslog.setLogType("CREATE_JOBBAG_HSCARD");
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

				                			counter++;
				                        	Integer accounts = Integer.parseInt(formater.getAccounts());
				                        	Integer pages = Integer.parseInt(formater.getPageno());
				                        	Integer sheets = 0;
				                        	String afpName = formater.getAfpFileName();
				                        	String nOrM = "";
				                        	//M使用兩種tray，tray1為雙面，tray2為單面
				                        	//N使用一種tray，均為雙面
				                        	if(afpName != null && afpName.length() > 3){
				                        		nOrM = afpName.substring(2, 3);
				                        	}
				                        	if("M".equalsIgnoreCase(nOrM)){
				                        		sheets = accounts;
				                        		sheets += (pages - accounts * 2);
				                        	}else if("N".equalsIgnoreCase(nOrM)){
				                        		sheets = accounts;				                        		
				                        	}
				                        	
				                        	Date cycleDate = new Date();
				                        	Date receiveDate = formater.getReceiveDate();
				                        	String cycleDate_str = formater.getCycleDate(); //mmdd
				                    		Calendar calendar = Calendar.getInstance();
				                    		Date now = new Date();
				                    		calendar.setTime(now);
				                    		Integer yyyy = calendar.get(Calendar.YEAR);
					                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
					                        cycleDate = sdf1.parse(yyyy.toString() + cycleDate_str);
					                        
					                        if (cycleDate.compareTo(now) > 0) {
					                        	//若cycleDate大於目 前時間， 表示跨年問題 => yyyy  = yyyy -1
					                        	yyyy = yyyy -1; 
						                        cycleDate = sdf1.parse(yyyy.toString() + cycleDate_str);					                        	
					                        }
					                        	
					                        
					                        
				                        	String logFilename  = in_files[i];
				                        	Integer feeder2 = (formater.getF2() == null || "".equals(formater.getF2()))? null : Integer.parseInt(formater.getF2());
				                        	Integer feeder3 = (formater.getF3() == null || "".equals(formater.getF3()))? null : Integer.parseInt(formater.getF3());
				                        	Integer feeder4 = (formater.getF4() == null || "".equals(formater.getF4()))? null : Integer.parseInt(formater.getF4());
				                        	Integer feeder5 = (formater.getF5() == null || "".equals(formater.getF5()))? null : Integer.parseInt(formater.getF5());
	
				                        	JobBag jobbag = jobbagService.createNewJobBag(jobcode, accounts, pages,  sheets,  afpName ,  cycleDate,  receiveDate, logFilename, feeder2, feeder3, feeder4, feeder5,0,0,0,0,0,0,0,0,formater.getLineData(),0,0,0,0,0,0,0);
				                        	if (null!= jobbag) {
				                        	    List list = jobbagService.getJobBagSplites(jobbag);
				                                if(list != null){
				                            	    jobbag.setSpliteCount(list.size());
				                            	    jobbagService.save(jobbag);
				                                }
				                        	
				                        	
					                			SysLog syslog = new SysLog();
					                			syslog.setLogType("CREATE_JOBBAG_HSCARD");
					                			syslog.setSubject("完成建立工單 ");
					                			syslog.setMessageBody(jobbag.getJobBagNo() + ":" + formater.toString() );
					                			calendar.setTime(now);
					                			syslog.setCreateDate(calendar.getTime());
					                			syslogService.save(syslog);	
				                        	}
				                		}
				                        HibernateSessionFactory.getSession().getTransaction().commit();
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
			                    	log.error("", e);
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
	             } //end of RPT file
	             else if(in_files[i].substring(in_files[i].length()-3, in_files[i].length()).equalsIgnoreCase("CSV") ) 
	             {
		                Boolean retMoving = FileOperate.moveFile(folderString+in_files[i], processFolderString+in_files[i]);
		                File file = new File(processFolderString+in_files[i]);		                
		                if (retMoving ) {
		                	try {
			                    reader = new BufferedReader(new FileReader(file));
			                    String text = null;			                    
                                int counter = 0;
                                SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			                    while ((text = reader.readLine()) != null) {
			                    	if(text.trim().startsWith("pdate"))
			                    		continue;
			                    	text = text.replaceAll("\"", "");
			                    	String[] splitTxt = text.split(",");
			                    	if(splitTxt.length == 16){
			                    		if(splitTxt[1].startsWith("Total Accts"))
			                    			continue;
			                    		else if(splitTxt[1].startsWith("Over-weight")){
			                    			
			                    			
			                    		}else{
			                    			Session session = null;
			                    			Transaction tx = null;
			                    			try{
			                    			    session = HibernateSessionFactory.getSession();
			                    			    tx = session.beginTransaction();
			                    			    HsCardRpt hsCRpt = new HsCardRpt();
			                    			    hsCRpt.setAa(Integer.parseInt(splitTxt[4]));
			                    			    hsCRpt.setAccounts(Integer.parseInt(splitTxt[2]));
			                    			    hsCRpt.setAfpName(splitTxt[1]);
			                    			    hsCRpt.setBb(Integer.parseInt(splitTxt[5]));
			                    			    hsCRpt.setCc(Integer.parseInt(splitTxt[6]));
			                    			    hsCRpt.setCycleDate(sdf1.parse(splitTxt[0]));
			                    			    hsCRpt.setFeeder2(Integer.parseInt(splitTxt[8]));
			                    			    hsCRpt.setFeeder3(Integer.parseInt(splitTxt[9]));
			                    			    hsCRpt.setFeeder4(Integer.parseInt(splitTxt[10]));
			                    			    hsCRpt.setFeeder5(Integer.parseInt(splitTxt[11]));
			                    			    hsCRpt.setFileName(in_files[i]);
			                    			    hsCRpt.setGg(Integer.parseInt(splitTxt[7]));
			                    			    hsCRpt.setMorePages(Integer.parseInt(splitTxt[15]));
			                    			    hsCRpt.setOnePage(Integer.parseInt(splitTxt[12]));
			                    			    hsCRpt.setPages(Integer.parseInt(splitTxt[3]));
			                    			    hsCRpt.setThreePages(Integer.parseInt(splitTxt[14]));
			                    			    hsCRpt.setTwoPages(Integer.parseInt(splitTxt[13]));
			                    			    
			                    			    Criteria criteria = session.createCriteria(JobBag.class);
			                    			    criteria.add(Restrictions.eq("cycleDate", hsCRpt.getCycleDate()));
			                    			    criteria.add(Restrictions.eq("afpName", hsCRpt.getAfpName()));
			                    			    List<JobBag> jobBags = criteria.list();
			                    			    if(jobBags == null || jobBags.size() == 0){
			                    			    	SysLog syslog = new SysLog();
			        	                			syslog.setLogType("CREATE_HSCARD_RPT");
			        	                			syslog.setSubject("Couldn't find the job bag");
			        	                			syslog.setMessageBody(hsCRpt.getAfpName() + "|" + hsCRpt.getCycleDate() + "|" + file.getName());
			        	                			syslog.setIsException(true);
			        	                			syslog.setCreateDate(new Date());
			        	                			syslogService.save(syslog);	
			                    			    }else if(jobBags.size() > 1){
			                    			    	SysLog syslog = new SysLog();
			        	                			syslog.setLogType("CREATE_HSCARD_RPT");
			        	                			syslog.setSubject("find more than one job bag");
			        	                			syslog.setMessageBody(hsCRpt.getAfpName() + "|" + hsCRpt.getCycleDate() + "|" + file.getName());
			        	                			syslog.setIsException(true);
			        	                			syslog.setCreateDate(new Date());
			        	                			syslogService.save(syslog);	
			                    			    }else{
			                    			    	JobBag jobBag = jobBags.get(0);
			                    			    	hsCRpt.setJobBagNo(jobBag.getJobBagNo());
			                    			    	if(hsCRpt.getFeeder2() == 0 && hsCRpt.getFeeder3() == 0 && hsCRpt.getFeeder4() == 0 && hsCRpt.getFeeder5() == 0){
			                    			    		//jobBag.
			                    			    	}
			                    			    }
			                    			    
			                    			    
			                    			    tx.commit();
			                    			}catch(Exception e){
			                    				if(tx != null)
			                    				   tx.rollback();
			                    			}finally{
			                    				if(session != null && session.isOpen())
			                    					session.close();
			                    			}
			                    		}
			                    	}
			                    	
			                    	counter ++;
			                    } // end of while
		                	}catch(Exception e){
		                		SysLog syslog = new SysLog();
	                			syslog.setLogType("CREATE_HSCARD_RPT");
	                			syslog.setSubject("Exception Happen");
	                			syslog.setMessageBody(file.getName() + ":" + e.getMessage());
	                			syslog.setIsException(true);
	                			syslog.setCreateDate(new Date());
	                			syslogService.save(syslog);	
		                	}
		                }
		                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			            String backupFileName = file.getName() + "."+formatter.format(new Date());
			            Boolean ret = FileOperate.moveFile(processFolderString+file.getName(), backupFolderString+backupFileName);
	            	 
	             } 
            }// for 
        }

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final SchedulerService scheduleService = SchedulerService.getInstance();
		HScardReader logisticReader = new HScardReader();
		logisticReader.readfile();
	}

}
