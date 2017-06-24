package com.salmat.jbm.mappingrule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.painter.util.FileOperate;
import com.painter.util.Util;
import com.salmat.jbm.bean.CiticardFormater;
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

public class CiticardReader {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(CiticardReader.class);

	/* Constants */
	private static final CiticardReader instance = new CiticardReader();
	private static final AmexService amexService = AmexService.getInstance();
	private static final SyslogService syslogService = SyslogService.getInstance();
	private static final JobBagService jobbagService = JobBagService.getInstance();

	public CiticardReader() {

	}

	public static CiticardReader getInstance() {
		return instance;
	}
	

	
	public static void readfile() {
		String folderString =  Util.getString("citicard.folder");
		String processFolderString = Util.getString("citicard.folder") +"\\processing\\" ;
		String backupFolderString = Util.getString("citicard.folder") +"\\backup\\";
        File folder = new File(folderString);
        //用來放特殊黑白job的list，之後要加入X_DXX的值
        List<JobBag> specialBlackJob = new ArrayList<JobBag>();
        CiticardFormater X_DXX = null;


        //
        // Check to see if the object represent a directory.
        //
        if (folder.isDirectory()) {
            String[] in_files = folder.list();
            for (int i=0; i<in_files.length;i++) {
            	boolean eddSetBefore = false;
            	boolean ctvvSetBefore = false;
            	FileReader fr = null;
                BufferedReader reader = null;
                if ( in_files[i].substring(0, 4).equalsIgnoreCase("citi") ) {

	                Boolean retMoving = FileOperate.moveFile(folderString+in_files[i], processFolderString+in_files[i]);
	                File file = new File(processFolderString+in_files[i]);		                
	                if (retMoving ) {
		                try {
		                	
		                	// CT 拆帳特殊處理, 優先讀出 Z_......C //Z開頭, , C 結尾  及 X_.....C //X開頭, C 結尾 的資料
		                	// 作為 虛擬工單的 pages2/pages3 (X01)
		                	//. pages2(虛) =sum (Z_accounts.log) * 1.05   Z_......C //X_開頭, C 結尾
		                	//. pages3(虛) =sum (X_accounts.log) * 1.05   X_.....C //Z開頭, C 結尾
		                	
		                	//新合約不再乘1.05		                	
		                	fr = new FileReader(file);
		                	reader = new BufferedReader(fr);
		                    String text = null;
		                    String v_fileName = null;
		                    Integer v_accounts=0;
		                    Integer v_pages2=0;
		                    Integer v_pages3=0;
		                    Integer v_pages2Ori=0;
		                    Integer v_pages3Ori=0;

		                    while ((text = reader.readLine()) != null) {
		                    	if (text.length()==114) { //長度判斷 
		                    		v_accounts=0;
		                    		v_fileName="";
			                		String[] tokens = text.split(","); 
			                		v_fileName = text.substring(0, 20).trim() ;//Splname, 實際的afp 檔案名稱
			                		v_accounts = Integer.parseInt(text.substring(20, 27).trim()) ;//Accounts
			                		// Filename 前兩碼 X_開頭, C 結尾 ==> 
			                		if (v_fileName.substring(0,2).equalsIgnoreCase("Z_") && v_fileName.substring(v_fileName.length()-1, v_fileName.length()).equalsIgnoreCase("C")) {
			                			v_pages2 += v_accounts;	
			                		}
			                		if (v_fileName.substring(0,2).equalsIgnoreCase("X_") && v_fileName.substring(v_fileName.length()-1, v_fileName.length()).equalsIgnoreCase("C")) {
			                			v_pages3 += v_accounts;	
			                		}			                		
		                    	}
		                    } 
		                    
		                    v_pages3Ori = v_pages3;
		                    v_pages2Ori = v_pages2;
		                    
		                	//新合約不再乘1.05
		                    //v_pages3 = (int) Math.round(v_pages3 * 1.05);
		                    //v_pages2 = (int) Math.round(v_pages2 * 1.05);
		                    //end CT 拆帳特殊處理
		                	
		                    reader = new BufferedReader(new FileReader(file));
		                    text = null;
		                    CiticardFormater formater = null;

		                    while ((text = reader.readLine()) != null) {
		                    	if (text.length()==114) { //長度判斷 
			                    	formater = new CiticardFormater();
			                		String[] tokens = text.split(","); 
			                		formater.setClientID("CT") ;//固定為 CT (CitiOne/CitiGold)
			                		formater.setAfpFileName(text.substring(0, 20).trim());//Splname, 實際的afp 檔案名稱			                		
			                		formater.setAccounts(text.substring(20, 27).trim()) ;//Accounts
			                		
			                		formater.setPageno(text.substring(27, 34).trim()) ;//pageno
			                		formater.setF2(text.substring(34, 41).trim()) ;//f2
			                		formater.setF3(text.substring(41, 48).trim()) ;//f3
			                		formater.setF4(text.substring(48, 55).trim()) ;//f4
			                		formater.setF5(text.substring(55, 62).trim()) ;//f5
			                		formater.setProdate(text.substring(62, 72).trim()) ;//prodate
			                		formater.setProtime(text.substring(73, 81).trim()) ;//prodate
			                		formater.setmAcct(text.substring(92, 99).trim()) ;//mAcct
			                		formater.setPagenoB(text.substring(99, 106).trim()) ;//pagenoB
			                		formater.setcAcct(text.substring(106, 113).trim()) ;//cAcct
			                		formater.setSortI(text.substring(113, 114).trim()) ;//sortI
			                		formater.setLogFileName(in_files[i]);//log file name 
			                		formater.setCycleDate(in_files[i].substring(in_files[i].length()-4, in_files[i].length())); //抓log 檔案日期
			                		Long lastModified = file.lastModified();
			                		Date date = new Date(lastModified);
			                		formater.setReceiveDate(date);
			                		formater.setLineData(text);
			                		
			                		//把formater的值塞到X_DXX
			                		if(formater.getAfpFileName().indexOf("X_DXX") >= 0){
			                			X_DXX = formater;
			                		}
			                		
				                    // 找出 此資料的 jobCode findJobCodeByLogistic
			                		// Filename 前兩碼為 "X_" or "Y_" or "Z_" 不處理
			                		String filterFileName="X_Y_Z_";
			                		if (filterFileName.indexOf(formater.getAfpFileName().substring(0, 2)) == -1) {
			                			JobCode jobcode = amexService.findJobCodeByCiticard(formater);	
				                		//建立工單 
				                    	HibernateSessionFactory.getSession().clear();
				                    	HibernateSessionFactory.getSession().getTransaction().begin();
				                    	
				                		if (null == jobcode) {
				                			SysLog syslog = new SysLog();
				                			syslog.setLogType("CREATE_JOBBAG_CITICARD");
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
				                			//account 改抓  mAcct 欄位
				                			Integer accounts = 0;
				                			if(formater.getmAcct() != null && Integer.parseInt(formater.getmAcct()) > 0)
				                        	   accounts = Integer.parseInt(formater.getmAcct());
				                			else
				                			   accounts = Integer.parseInt(formater.getAccounts());
				                        	Integer ctAcctsOri = Integer.parseInt(formater.getAccounts());
				                        	Integer pages = Integer.parseInt(formater.getPageno());
				                        	Integer sheets = 0; //citicard 無sheet
				                        	String afpName = formater.getAfpFileName();
				                        	Date cycleDate = new Date();
				                        	
				                        	Date receiveDate = formater.getReceiveDate();
				                        	String cycleDate_str = formater.getCycleDate();
				                    		Calendar calendar = Calendar.getInstance();
				                    		Date now = new Date();
				                    		calendar.setTime(now);
				                    		Integer yyyy = calendar.get(Calendar.YEAR);
					                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
					                        cycleDate = sdf1.parse(yyyy.toString() + cycleDate_str);
					                        if (cycleDate.compareTo(now) > 0) {
					                        	//因為檔案只有月日，若跨年時，加入的yyyy是今年，會使得cycledate增加了一年 
					                        	//故需比較，因為檔案名稱都是當天或前一天，所以若cycleDate比目前的時間大，代表這是跨年問題, yyyy  = yyyy -1
					                        	yyyy = yyyy -1; 
						                        cycleDate = sdf1.parse(yyyy.toString() + cycleDate_str);					                        	
					                        }
					                        int feeder2 = 0;
					                        int feeder3 = 0;
					                        int feeder4 = 0;
					                        int feeder5 = 0;
					                        try{
					                          feeder2 = Integer.parseInt(formater.getF2());
					                        }catch(Exception e){
					                        	log.error("", e);
					                        }
					                        try{
						                        feeder3 = Integer.parseInt(formater.getF3());
						                    }catch(Exception e){
						                       	log.error("", e);
						                    }
						                    try{
						                        feeder4 = Integer.parseInt(formater.getF4());
						                    }catch(Exception e){
						                       	log.error("", e);
						                    }
						                    try{
						                        feeder5 = Integer.parseInt(formater.getF5());
						                    }catch(Exception e){
						                       	log.error("", e);
						                    }
					                        

				                        	String logFilename  = in_files[i];
				                        	/*
				                        	JobBag createNewJobBag(JobCode jobcode, Integer accounts,
				                        			Integer pages, Integer sheets, String afpName, Date cycleDate,
				                        			Date receiveDate, String logFilename, Integer feeder2,
				                        			Integer feeder3, Integer feeder4, Integer feeder5, Integer tray1,
				                        			Integer tray2, Integer tray3, Integer tray4, String lineData,
				                        			Integer p1accounts, Integer p2accounts, Integer p3accounts,
				                        			Integer p4accounts, Integer p5accounts, Integer p6accounts,
				                        			Integer pxaccounts)
				                        	*/
				                        	JobBag jobbag = jobbagService.createNewJobBag(jobcode, accounts, pages,  sheets,  afpName ,  cycleDate,  receiveDate, logFilename, feeder2, feeder3, feeder4, feeder5,0,0,0,0,0,0,0,0,formater.getLineData(),0,0,0,0,0,0,0, ctAcctsOri);

				                        	
				                        	if (null!= jobbag) {
				                        		
				                        	   if ( pages >= accounts * 2 && (v_pages2 + v_pages3 > 0) && !eddSetBefore && jobbag.getJobBagNo().startsWith("CTESTT")) {
				                        		   jobbag.setVPages2(v_pages2);
				                        		   jobbag.setVPages3(v_pages3);
				                        		   jobbag.setVPages2Ori(v_pages2Ori);
				                        		   jobbag.setVPages3Ori(v_pages3Ori);
				                        		   eddSetBefore = true;
				                        		   jobbagService.save(jobbag);
				                        	   }
				                        	   
				                        	   // CT 拆帳特殊處理, 虛擬工單, 找其中一個job 來存放 pages2/pages3 (X01)
				                        		
				                        	   //以下是寫死的，VPAGE2和Vpage3固定塞在第一個
				                        	   //如果工單樣本有變，此段程式必須修改
				                        	   if ( pages >= accounts * 2 && (v_pages2 + v_pages3 > 0) && !ctvvSetBefore && jobbag.getJobBagNo().startsWith("CTVV")) {
				                        		   jobbag.setVPages2(v_pages2);
				                        		   jobbag.setVPages3(v_pages3);
				                        		   jobbag.setVPages2Ori(v_pages2Ori);
				                        		   jobbag.setVPages3Ori(v_pages3Ori);
				                        		   ctvvSetBefore = true;
				                        		   jobbagService.save(jobbag);
				                        	   }
				                        	   List list = jobbagService.getJobBagSplites(jobbag);
				                               if(list != null){
				                            	   jobbag.setSpliteCount(list.size());
				                               }
				                               jobbag.setCtAcctsOri(ctAcctsOri);
				                               jobbagService.save(jobbag);
				                               //XDB_10CT            206    211    206    199    0      0      2011/12/11,08:32:06           206    0      0      A
				                               //XDC_10CT            5730   5746   5730   5571   0      0      2011/12/11,08:32:30           5730   0      0      A
				                               //X_DXX_10B           1436   1442   1436   1413   0      0      2011/12/11,08:32:38           0      0      0      C
				                               //當檔案解析時，如檔名為XDB_或XDC_開頭時，要把X_DXX_開頭的檔名的第一欄數字塞入最後一個jobbag中，在上例中為1436，塞入到XDC_10CT中
				                               if(jobbag.getAfpName() != null && (jobbag.getAfpName().indexOf("XDB_") >= 0 || jobbag.getAfpName().indexOf("XDC_") >= 0)){
				                            	   specialBlackJob.add(jobbag);
				                               }
				                               
 				                        	   //   end CT 拆帳特殊處理
				                        	
				                        	
				                        	
					                			SysLog syslog = new SysLog();
					                			syslog.setLogType("CREATE_JOBBAG_CITICARD");
					                			syslog.setSubject("完成建立工單 ");
					                			syslog.setMessageBody(jobbag.getJobBagNo() + ":" + formater.toString() );
					                			calendar.setTime(now);
					                			syslog.setCreateDate(calendar.getTime());
					                			syslogService.save(syslog);			                        	
				                        	}
				                		}
				                    	
				                        HibernateSessionFactory.getSession().getTransaction().commit();			                			
			                		}
		                    	}else{
		                    		SysLog syslog = new SysLog();
		                			syslog.setLogType("CREATE_JOBBAG_CITICARD");
		                			syslog.setSubject("輸入資料長度錯誤");
		                			syslog.setMessageBody(text.length() + ":" + text);			                			
		                			syslog.setCreateDate(new Date());
		                			syslog.setIsException(true);
		                			syslogService.save(syslog);	
		                    	}
		                    }
		                    
		                    //如果有特殊黑白列印，且有X_DXX檔時，把X_DXX所記錄的accounts，塞到page3中
		                    //特殊黑白列印不只一個時，只更新一個
		                    if(specialBlackJob != null && specialBlackJob.size() > 0){
		                    	JobBag jobBag = specialBlackJob.get(specialBlackJob.size() - 1);
		                    	if(X_DXX != null ){
		                    		Integer page3 = 0;
		                    		try{
		                    		   page3 = Integer.parseInt(X_DXX.getAccounts());
		                    		}catch(Exception e){
		                    			log.error("", e);
		                    		}
		                    		HibernateSessionFactory.getSession().clear();
			                    	HibernateSessionFactory.getSession().getTransaction().begin();
		                    		jobBag.setPages3(page3);
		                    		jobbagService.save(jobBag);
		                    		HibernateSessionFactory.getSession().getTransaction().commit();
		                    	}
		                    }
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
		                    try{
		                		if(fr != null)
		                			fr.close();
		                	}catch(Exception e){
		                		log.error("", e);
		                		e.printStackTrace();
		                	}
		                	reader = null;
		                    fr = null;
		                    if(HibernateSessionFactory.getSession().isOpen())
		                       HibernateSessionFactory.closeSession();		                    
		                }
			            //將processing 資料, 搬到 backup 
			             SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			             String backupFileName = file.getName() + "."+formatter.format(new Date());
			             Boolean ret = FileOperate.moveFile(processFolderString+file.getName(), backupFolderString+backupFileName);
			             
		             } 
	            } // end if( in_files[i].substring(0, 4).equalsIgnoreCase("citi")) 
	        }// for

		}
		


		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final SchedulerService scheduleService = SchedulerService.getInstance();
		CiticardReader logisticReader = new CiticardReader();
		logisticReader.readfile();
	}

}
