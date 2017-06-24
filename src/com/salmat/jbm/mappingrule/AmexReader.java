package com.salmat.jbm.mappingrule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import com.painter.util.FileOperate;
import com.painter.util.Util;
import com.salmat.jbm.bean.AmexFormater;
import com.salmat.jbm.bean.CiticardFormater;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.bean.OneGoldFormater;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JobBag;
import com.salmat.jbm.hibernate.JobBagSplite;
import com.salmat.jbm.hibernate.JobCode;
import com.salmat.jbm.hibernate.SysLog;
import com.salmat.jbm.service.AmexService;
import com.salmat.jbm.service.EmployeeService;
import com.salmat.jbm.service.JobBagService;
import com.salmat.jbm.service.JobBagSpliteService;
import com.salmat.jbm.service.JobCodeService;
import com.salmat.jbm.service.SchedulerService;
import com.salmat.jbm.service.SyslogService;

public class AmexReader {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(AmexReader.class);

	/* Constants */
	private static final AmexReader instance = new AmexReader();
	private static final AmexService amexService = AmexService.getInstance();
	private static final SyslogService syslogService = SyslogService.getInstance();
	private static final JobBagService jobbagService = JobBagService.getInstance();
	private static final JobBagSpliteService jobbagSpliteService = JobBagSpliteService.getInstance();

	public AmexReader() {

	}

	public static AmexReader getInstance() {
		return instance;
	}
	

	
	public static void readfile() {
		String folderString = Util.getString("amex.folder");
		String processFolderString = Util.getString("amex.folder") +"\\processing\\" ;
		String backupFolderString = Util.getString("amex.folder") +"\\backup\\";		

        File folder = new File(folderString);

        //
        // Check to see if the object represent a directory.
        //
        if (folder.isDirectory()) {
        	FilenameFilter filter= new FilenameFilter() { 
		      public boolean accept(File folder, String name) {
		          File currFile = new File(folder, name);
		          if (currFile.isFile() && name.indexOf(".csv") > 0 
		        		  && !currFile.getName().toUpperCase().startsWith("AMEX_GCSFXTW_MLD")
		        		  && !currFile.getName().toUpperCase().startsWith("AMEX_GCSFXTW_OWT")) {
		            return true;
		          } else {
		            return false;
		          }
		        }
        	};
        	FilenameFilter gcFilter= new FilenameFilter() { 
  		      public boolean accept(File folder, String name) {
  		          File currFile = new File(folder, name);
  		          if (currFile.isFile() && name.indexOf(".csv") > 0 
  		        		  && (currFile.getName().toUpperCase().startsWith("AMEX_GCSFXTW_MLD")
  		        		        || currFile.getName().toUpperCase().startsWith("AMEX_GCSFXTW_OWT"))) {
  		            return true;
  		          } else {
  		            return false;
  		          }
  		        }
          	};
          	File gcFiles[] = folder.listFiles(gcFilter);
          	//GCSFXTW files
          	for(File gcFile : gcFiles){
          		Boolean retMoving = FileOperate.moveFile(folderString + gcFile.getName(), processFolderString + gcFile.getName());
          		if(retMoving){
          		   File file = new File(processFolderString, gcFile.getName());
          		   Long lastModified = file.lastModified();                
            	   SimpleDateFormat sdfBetween = new SimpleDateFormat("yyyy-MM-dd");
            	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	   Date cycleDate = new Date(lastModified);  //檔案日期            	   
            	   BufferedReader reader = null;
            	   FileReader fr = null;
            	   String text = null;
            	   try{ 
            		    fr = new FileReader(file);
	                    reader = new BufferedReader(fr);	                    	                   
	                    Integer lineCounter = 0;
	                    while ((text = reader.readLine()) != null) {
	                    	text = text.trim();
	                    	lineCounter++;
	                    	//#HDR laser filename, input filename,mailpacks,sheets,impressions,creation date,create time
	                    	//AMEX_GCSFXTW_MLD561.L,GCSFX561.ZIP,50,100,100,2015-05-26,10:03:47
	                    	if(text.startsWith("AMEX_GCSFXTW")){
	                    		String[] lineSplit = text.split(",");
	                    		if(lineSplit.length == 7){
	                    			String fileName = lineSplit[0].trim();
	                    			String designateStr = fileName.substring(0, 16);
	                    			String fileZip = lineSplit[1].trim();
	                    			Integer accounts = new Integer(lineSplit[2].trim());
	                    			Integer sheets = new Integer(lineSplit[3].trim());
	                    			Integer pages = new Integer(lineSplit[4].trim());
	                    			Date receivedDate = sdf.parse(lineSplit[5].trim() + " " + lineSplit[6].trim()); 
	                    			String queryString = "select jobCode from JobCode jobCode inner join jobCode.customer as customer where customer.custNo = 'AE' "
	                    					+ " and substring(jobCode.filename, 1, 16) = '" + designateStr + "' " ;
	                    		    List<JobCode> jobCodes = HibernateSessionFactory.getSession().createQuery(queryString).list();
	                    	    	if(jobCodes.size() == 1){
	                    	    		JobCode jobcode = jobCodes.get(0);
	                    	    		String logFilename  = file.getName();
		                        	    JobBag jobbag = jobbagService.createNewJobBag(jobcode, accounts, pages,  sheets,  fileName,  cycleDate,  receivedDate, logFilename,0,0,0,0,0,0,0,0,0,0,0,0,text,0,0,0,0,0,0,0);	                    	    		
	                    			    SysLog syslog = new SysLog();
	                			        syslog.setLogType("CREATE_JOBBAG_AMEX");
	                			        syslog.setSubject("完成建立工單 ");
	                			        syslog.setMessageBody(jobbag.getJobBagNo() + ":" + text );
	                			        Calendar calendar = Calendar.getInstance();
	                			        syslog.setIsException(false);
	                			        Date now = new Date();
	                			        calendar.setTime(now);
	                			        syslog.setCreateDate(calendar.getTime());
	                			        syslogService.save(syslog);
	                    	    	}else if(jobCodes.size() == 0){
	                    	    		SysLog syslog = new SysLog();
		                			    syslog.setLogType("CREATE_JOBBAG_AMEX");
		                			    syslog.setSubject("找不到JobCode");
		                			    syslog.setMessageBody("無法由" + text + "找到對應的工單樣本");
		                			    syslog.setIsException(true);
		                			    Calendar calendar = Calendar.getInstance();
		                			    Date now = new Date();
		                			    calendar.setTime(now);
		                			    syslog.setCreateDate(calendar.getTime());
		                			    syslogService.save(syslog);
	                    	    	}else if(jobCodes.size() > 1){
	                    	    		SysLog syslog = new SysLog();
		                			    syslog.setLogType("CREATE_JOBBAG_AMEX");
		                			    syslog.setSubject("多個JobCode");
		                			    String msgBody = "由" + text + "找到多個工單樣本:";
		                			    for(JobCode jobCode : jobCodes){
		                			    	msgBody += jobCode.getJobCodeNo() + ",";
		                			    }
		                			    syslog.setMessageBody(msgBody);
		                			    syslog.setIsException(true);
		                			    Calendar calendar = Calendar.getInstance();
		                			    Date now = new Date();
		                			    calendar.setTime(now);
		                			    syslog.setCreateDate(calendar.getTime());
		                			    syslogService.save(syslog);
	                    	    	}
	                    		}else{
	                    			SysLog syslog = new SysLog();
	                			    syslog.setLogType("CREATE_JOBBAG_AMEX");
	                			    syslog.setSubject("檔案內容有誤 ");
	                			    syslog.setMessageBody(text + "無法解析");
	                			    syslog.setIsException(true);
	                			    Calendar calendar = Calendar.getInstance();
	                			    Date now = new Date();
	                			    calendar.setTime(now);
	                			    syslog.setCreateDate(calendar.getTime());
	                			    syslogService.save(syslog);	                    				                    			
	                    		}
	                    	}	                    	
	                    }// end of file reading
            	   }catch(Exception e){
            		   log.error("", e);
            		   SysLog syslog = new SysLog();
       			       syslog.setLogType("CREATE_JOBBAG_AMEX");
       			       syslog.setSubject("檔案處理異常");
       			       syslog.setMessageBody(text + ":" + e.getMessage());
       			       syslog.setIsException(true);
       			       Calendar calendar = Calendar.getInstance();
       			       Date now = new Date();
       			       calendar.setTime(now);
       			       syslog.setCreateDate(calendar.getTime());
       			       syslogService.save(syslog);
            	   }finally{
            		   try{
            		      if(reader != null)
            			     reader.close();
            		      if(fr != null)
            			     fr.close();
            		   }catch(Exception e){
            			   log.error("", e);
            		   }
            		   reader = null;
            		   fr = null;
            		   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			           String backupFileName = file.getName() + "."+formatter.format(new Date());
			           //全部處理完畢移到backup folder
	                   FileOperate.moveFile(processFolderString + file.getName(), backupFolderString + backupFileName);             			
             	   }
          		}//End of retMoving
          	}//End of gc File processing
        	
            File in_files[] = folder.listFiles(filter); 
            boolean allResolved = true;
            for (int i=0; i<in_files.length;i++) {
            	String betweenStr1 = null;
        		String betweenStr2 = null;
          
        		Boolean retMoving = FileOperate.moveFile(folderString+in_files[i].getName(), processFolderString+in_files[i].getName());
                File file = new File(processFolderString + in_files[i].getName());
                Long lastModified = file.lastModified();                
            	SimpleDateFormat sdfBetween = new SimpleDateFormat("yyyy-MM-dd");
            	Date date = new Date(lastModified);  //檔案日期
        		//Date date = new Date();  //系統日期
        		
        		Calendar cal = Calendar.getInstance();
        		cal.setTime(date); //設為檔案日期
        		Calendar cal2 = Calendar.getInstance();
        		cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE) - 15, 23, 59, 00); //15天前		                						                		
        		betweenStr1 = sdfBetween.format(cal.getTime());
        		betweenStr2 = sdfBetween.format(cal2.getTime());            	
        		int hour = cal.get(Calendar.HOUR_OF_DAY);
        		//0點到10點送到的，cycle date為前一天
        		if(hour >= 0 && hour <= 10){
        			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE) - 1, 23, 59, 00);
        			date = cal.getTime();
        		}
            	
            	//檢查這15天中是不是有相同檔名的amex job
        		String queryString = "select top 1  {j.*} from  job_bag j  where   j.LOG_FILENAME = '" + in_files[i].getName() 
        		   + "' and j.idf_CUST_NO = 'AE' and (j.cycle_date between '" + betweenStr2 + " 00:00:00.000' and '" 
        		   + betweenStr1 + " 23:59:59.998')  order by j.CYCLE_DATE desc" ;
	    	    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
    		    sqlQuery.addEntity("j", JobBag.class);
    		    List retList = sqlQuery.list();    		    

    		    //如果有，cycleDate設為相同 
    		    if(retList != null && retList.size() > 0){
    		       JobBag aeJobBag = (JobBag)retList.get(0);
    		       if(aeJobBag.getCycleDate() != null)
                      date = aeJobBag.getCycleDate();
    		    }
            	
                BufferedReader reader = null;
                
                if (retMoving ) {
		                try {
		                    reader = new BufferedReader(new FileReader(file));
		                    String text = null;
		                    AmexFormater formater = null;
		                    Integer lineNumber=1;
		                    while ((text = reader.readLine()) != null) {
		                    	String errorFileName = "";
			                    formater = new AmexFormater();
			                	String[] tokens = text.split(","); 
			                	if (! tokens[0].trim().equalsIgnoreCase("job")) { //過濾 header
			                		String wriFullName=tokens[0].trim(); //201106JGTW7201, 201106FXTW62E1
			                		formater.setWriFileName(tokens[0].trim());//WriFileName
			                		formater.setCountry(wriFullName.substring(6, 10)) ;//Country
			                		formater.setCycleNumber(wriFullName.substring(10, 11));//CycleNumber
			                		formater.setTypeOfFile(wriFullName.substring(11, 12));//TypeOfFile
			                		formater.setLanguage(wriFullName.substring(12, 13));//Language(
			                		formater.setSequence(wriFullName.substring(13, 14));//Sequence(
			                		formater.setAccounts(tokens[2].trim()); //accounts ==Mail Packs
			                		formater.setPages(tokens[3].trim()) ;//Pages = impressions
			                		formater.setSheets(tokens[4].trim()) ;//Sheets 
			                		formater.setCsvFileName(in_files[i].getName());//log file name 
			                		cal2.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DATE) - 15, 23, 59, 00); //15天前		                						                		
			                		betweenStr1 = sdfBetween.format(cal.getTime());
			                		betweenStr2 = sdfBetween.format(cal2.getTime());
			                		formater.setCycleDate(date);  
			                		formater.setReceiveDate(new Date(lastModified));//抓log 檔案日期

			                		// 2011/10/28 
			                		// String afpFileName = getAfpFileName(folderString + wriFullName+".L.wri"); //從wri檔案中, 找出 afpFileName
			                		// 改寫抓 accounts/pages/afpFileName 
			                		String afpFileName = "";
			                		String wriAccounts = "";
			                		String wriSheets = "";			                		
			                		String wriPages = "";
			                		String wriMRDF = "";
			                		boolean fileCorrect = true;
			                		
			                		try {
			                			File wriFile = new File(folderString + wriFullName+".L.wri");
				                		
			                			BufferedReader wriReader = new BufferedReader(new FileReader(wriFile));
			                            String readLine = "";
			                            String[] wriText = new String[25];
			                            int wriLineCounter=0;
			                            while ((readLine = wriReader.readLine()) != null) {
			                            	wriText[wriLineCounter++] = readLine;
 
			                            }
			                            
			                            // 抓 afpFileName
			                            String patternStr = "/laser/(.+).afp";
			                            Pattern pattern = Pattern.compile(patternStr);
			                            Matcher matcher = pattern.matcher(wriText[8]);
			                            boolean matchFound = matcher.find();

			                            if (matchFound) {
			                              for(int j = 0; j <= matcher.groupCount(); j++) {
			                                String groupStr = matcher.group(j);
			                                if(j == 1)
			                                	afpFileName = groupStr.trim();
			                              }
			                            }      
			                            
			                            // 抓 Mailpacks = account
			                            patternStr = "Mailpacks(.+)no breaks";
			                            pattern = Pattern.compile(patternStr);
			                            matcher = pattern.matcher(wriText[22]);
			                            matchFound = matcher.find();			                            
			                            if (matchFound) {
				                              for(int j = 0; j <= matcher.groupCount(); j++) {
				                                String groupStr = matcher.group(j);
				                                if(j == 1) {
				                                	groupStr = groupStr.substring(0, groupStr.length() -1);
				                                	groupStr = groupStr.replaceAll(",", ""); //去除"," 符號
				                                	wriAccounts = groupStr.trim();
				                                }
				                              }
				                        } 
			                            
			                            // 抓 Sheets
			                            patternStr = "Sheets: (.+) time:";
			                            pattern = Pattern.compile(patternStr);
			                            matcher = pattern.matcher(wriText[10]);
			                            matchFound = matcher.find();				                            
			                            if (matchFound) {
				                              for(int j = 0; j <= matcher.groupCount(); j++) {
				                                String groupStr = matcher.group(j);
				                                if(j == 1)
				                                	groupStr = groupStr.replaceAll(",", ""); //去除"," 符號
				                                	wriSheets = groupStr.trim();
				                              }
				                        } 		
			                            
			                            // 抓 Impressions = pages 
			                            patternStr = "Impressions: (.+) operator:";
			                            pattern = Pattern.compile(patternStr);
			                            matcher = pattern.matcher(wriText[12]);
			                            matchFound = matcher.find();				                            
			                            if (matchFound) {
				                              for(int j = 0; j <= matcher.groupCount(); j++) {
				                                String groupStr = matcher.group(j);
				                                if(j == 1)
				                                	groupStr = groupStr.replaceAll(",", ""); //去除"," 符號
				                                	wriPages = groupStr.trim();
				                              }
				                        } 	
			                            
			                            // 抓 Mailpacks MRDF 
			                            patternStr = "Mailpacks MRDF (.+)";
			                            pattern = Pattern.compile(patternStr);
			                            matcher = pattern.matcher(wriText[20]);
			                            matchFound = matcher.find();				                            
			                            if (matchFound) {
				                              for(int j = 0; j <= matcher.groupCount(); j++) {
				                                String groupStr = matcher.group(j);
				                                if(j == 1)
				                                	groupStr = groupStr.replaceAll(",", ""); //去除"," 符號
				                                	wriMRDF = groupStr.trim();
				                              }
				                        }

			                            // reset accounts/pages/sheets
			                            if (wriAccounts.length() >0)
			                            	formater.setAccounts(wriAccounts);
			                            
			                            if (wriSheets.length() >0)
			                            	formater.setSheets(wriSheets);
			                            
			                            if (wriPages.length() >0)
			                            	formater.setPages(wriPages);
			                            
			                            if (wriMRDF.length() >0)
			                            	formater.setMRDF(wriMRDF);	                            
			                            
			                            //wri檔與csv 檔的時間差異
			                            long timeDiff = (wriFile.lastModified() - lastModified) ;
			                            
			                            //如果wri檔在csv檔的一天前送到的話，視為問題件
			                            if(timeDiff < 0 && Math.abs(timeDiff) > 24 * 60 * 60 * 1000){
			                            	errorFileName = wriFullName + ".L.wri 時間為" + new Date(wriFile.lastModified());
			                            	fileCorrect = false;
				                			allResolved = false;
			                            }
			                		} catch (FileNotFoundException e) {
			                			errorFileName = wriFullName + ".L.wri不存在";
			                			//log.error("", e); 太常發生了，所以mark起來，error log只留真正有用的資訊
			                			// TODO Auto-generated catch block
			                			e.printStackTrace();
			                		} catch (IOException e) {
			                			fileCorrect = false;
			                			allResolved = false;
			                			errorFileName = wriFullName + ".L.wri存取錯誤";
			                			log.error("", e);
			                			// TODO Auto-generated catch block
			                			e.printStackTrace();
			                		}catch (Exception e) {
			                			fileCorrect = false;
			                			allResolved = false;
			                			errorFileName = wriFullName + ".L.wri解析異常";
			                			log.error("", e);
			                			// TODO Auto-generated catch block
			                			e.printStackTrace();			                			
			                		}
			                		// end 改寫抓 accounts/pages/...
			                		
			                		
			                		//找不到afp file代表檔案有錯
			                		if (null== afpFileName || ( null!= afpFileName && afpFileName.length()==0 ))  {
			                			/*
				                        if (reader != null) {
				                            reader.close();
				                        }
			                			Boolean ret = FileOperate.moveFile(processFolderString+file.getName(), folderString +file.getName() );
			                			break;
			                			*/
			                			if("".equals(errorFileName))
			                				errorFileName = wriFullName + ".L.wri正常存取，但解析不出AFP檔名";
			                			fileCorrect = false;
			                			allResolved = false;
			                			
			                		}
			                		if(fileCorrect){
			                		   //////////////////////////////////////////////////////////
			                		   // 抓.MRD 檔案 2011/10/29
			                		   //當實際AFP的檔名不為DIV時要增加下列邏輯為(計算F2-F7的值)
			                		   //否 f2~f2 = 0;
			                		   if (afpFileName.indexOf(".DIV.") >= 0 ) {
			                			   formater.setFeeder2("0");
			                			   formater.setFeeder3("0");
			                			   formater.setFeeder4("0");
			                			   formater.setFeeder5("0");
			                			   formater.setFeeder6("0");
			                			   formater.setFeeder7("0");
			                		   } else {
			                			   //找到對應的檔案201110DSTW70E1.MRD, 當計算欄位值為 “1”的數量有多少:
			                			   //F2: column 52, 當column 570-575 不為”000000”
			                			   //F3: column 53, 當column 576-581 不為”000000”
 			                			   //F4: column 54, 當column 582-587 不為”000000”
			                			   //F5: column 55, 當column 588-593 不為”000000”
			                			   //F6: column 56, 當column 594-599 不為”000000”
			                			   //F7: column 57, 當column 600-605 不為”000000”		
			                			   Integer _feeder2=0;
			                			   Integer _feeder3=0;
			                			   Integer _feeder4=0;
			                			   Integer _feeder5=0;
			                			   Integer _feeder6=0;
			                			   Integer _feeder7=0;
			                			   File mrdFile = new File(folderString + wriFullName+".MRD");
				                		   try {
				                			   BufferedReader mrdReader = new BufferedReader(new FileReader(mrdFile));
				                               String readLine = "";
				                               String[] mrdText = new String[99999];
				                               int mrdLineCounter=0;
				                               while ((readLine = mrdReader.readLine()) != null) {
				                            	   mrdText[mrdLineCounter++] = readLine;
				                               }

				                               for (int ii=0; ii<mrdLineCounter; ii++) {
				                            	   String _mrdLineText = mrdText[ii];
				                            	   if (_mrdLineText.length() >=605) {
				                            	   String _f2=_mrdLineText.substring(51, 52);
				                            	   String _f3=_mrdLineText.substring(52, 53);
				                            	   String _f4=_mrdLineText.substring(53, 54);
				                            	   String _f5=_mrdLineText.substring(54, 55);
				                            	   String _f6=_mrdLineText.substring(55, 56);
				                            	   String _f7=_mrdLineText.substring(56, 57);
				                            	   String _f570=_mrdLineText.substring(569, 575);
				                            	   String _f576=_mrdLineText.substring(575, 581);
				                            	   String _f582=_mrdLineText.substring(581, 587);
				                            	   String _f588=_mrdLineText.substring(587, 593);
				                            	   String _f594=_mrdLineText.substring(593, 599);
				                            	   String _f600=_mrdLineText.substring(599, 605);		
				                            	   if (!_f570.equalsIgnoreCase("000000") && _f2.equalsIgnoreCase("1")) 	_feeder2 += 1;
				                            	   if (!_f576.equalsIgnoreCase("000000") && _f3.equalsIgnoreCase("1")) 	_feeder3 += 1;
				                            	   if (!_f582.equalsIgnoreCase("000000") && _f4.equalsIgnoreCase("1")) 	_feeder4 += 1;
				                            	   if (!_f588.equalsIgnoreCase("000000") && _f5.equalsIgnoreCase("1")) 	_feeder5 += 1;
				                            	   if (!_f594.equalsIgnoreCase("000000") && _f6.equalsIgnoreCase("1")) 	_feeder6 += 1;
				                            	   if (!_f600.equalsIgnoreCase("000000") && _f7.equalsIgnoreCase("1")) 	_feeder7 += 1;
				                            	   }
				                               }
				                            
				                			   formater.setFeeder2(String.valueOf(_feeder2));
				                			   formater.setFeeder3(String.valueOf(_feeder3));
				                			   formater.setFeeder4(String.valueOf(_feeder4));
				                			   formater.setFeeder5(String.valueOf(_feeder5));
				                			   formater.setFeeder6(String.valueOf(_feeder6));
				                			   formater.setFeeder7(String.valueOf(_feeder7));
				                			   //mrd檔與csv 檔的時間差異
					                           long timeDiff = (mrdFile.lastModified() - lastModified) ;
					                            
					                           //如果mrd檔在csv檔的一天前送到的話，視為問題件
					                           if(timeDiff < 0 && Math.abs(timeDiff) > 24 * 60 * 60 * 1000){
					                            	errorFileName = wriFullName + ".MRD 時間為" + new Date(mrdFile.lastModified());
					                            	fileCorrect = false;
						                			allResolved = false;
					                           }
				                		   }  catch (FileNotFoundException e) {
				                			    fileCorrect = false;
				                			    allResolved = false;
				                			    errorFileName = wriFullName+".MRD不存在";
					                			log.error("", e);
					                			// TODO Auto-generated catch block
					                			e.printStackTrace();
					                		} catch (IOException e) {
					                			fileCorrect = false;
					                			allResolved = false;
					                			errorFileName = wriFullName+".MRD存取錯誤";
					                			log.error("", e);
					                			// TODO Auto-generated catch block
					                			e.printStackTrace();
					                		}catch (Exception e) {
					                			errorFileName = wriFullName+".MRD解析異常";
					                			fileCorrect = false;
					                			allResolved = false;
					                			log.error("", e);
					                			e.printStackTrace();					                			
					                		}
			                		   }
			                		   // end 抓.MRD 檔案
			                		   if(fileCorrect){ 
			                		      formater.setAfpFileName(afpFileName);	
			                		      formater.setLineData(text);
			                		      //建立工單 
			                    	      HibernateSessionFactory.getSession().clear();
			                    	      HibernateSessionFactory.getSession().getTransaction().begin();
			                    	
		                			      JobCode jobcode = amexService.findJobCodeByAmex(formater);
			                    	
			                		      if (null == jobcode) {
			                			      SysLog syslog = new SysLog();
			                			      syslog.setLogType("CREATE_JOBBAG_AMEX");
			                			      syslog.setSubject("無對應工單樣本 ");
			                			      syslog.setMessageBody(formater.toString());
			                			      syslog.setIsException(true);
			                			      syslog.setIsException(true);
			                			      Calendar calendar = Calendar.getInstance();
			                			      Date now = new Date();
			                			      calendar.setTime(now);
			                			      syslog.setCreateDate(calendar.getTime());
			                			      syslogService.save(syslog);
			                		      } else {
			                        	      Integer accounts = Integer.parseInt(formater.getAccounts());
			                        	      Integer pages = Integer.parseInt(formater.getPages());
			                        	      Integer sheets = Integer.parseInt(formater.getSheets());
			                        	      String afpName = formater.getAfpFileName();
			                        	      Date cycleDate = formater.getCycleDate();
			                        	      Date receiveDate = formater.getReceiveDate();

			                        	      String logFilename  = in_files[i].getName();
			                        	      JobBag jobbag = jobbagService.createNewJobBag(jobcode, accounts, pages,  sheets,  afpName ,  cycleDate,  receiveDate, logFilename,0,0,0,0,0,0,0,0,0,0,0,0,formater.getLineData(),0,0,0,0,0,0,0);
			                        	      if (null!= jobbag) {
		                        		      // 	回壓 job_bag 的 f2~f7, mrdf 等資訊
			                        		      try {
			                        			      jobbag.setFeeder2(Integer.parseInt(formater.getFeeder2()));
			                        			      jobbag.setFeeder3(Integer.parseInt(formater.getFeeder3()));
			                        			      jobbag.setFeeder4(Integer.parseInt(formater.getFeeder4()));
			                        			      jobbag.setFeeder5(Integer.parseInt(formater.getFeeder5()));
			                        			      jobbag.setFeeder6(Integer.parseInt(formater.getFeeder6()));
			                        			      jobbag.setFeeder7(Integer.parseInt(formater.getFeeder7()));
			                        			      jobbag.setMrdf(Integer.parseInt(formater.getMRDF()));
			                        		      }catch (Exception e) {
					                			      // TODO Auto-generated catch block
			                        			      jobbag.setFeeder2(0);
			                        			      jobbag.setFeeder3(0);
			                        			      jobbag.setFeeder4(0);
			                        			      jobbag.setFeeder5(0);
			                        			      jobbag.setFeeder6(0);
			                        			      jobbag.setFeeder7(0);
			                        			      jobbag.setMrdf(0);			                        			
					                			      e.printStackTrace();
					                		      } 
			                        		      jobbagService.save(jobbag);
			                        		      List<JobBagSplite> list = jobbagService.getJobBagSplites(jobbag);
				                                  if(list != null){
				                            	      jobbag.setSpliteCount(list.size());
				                            	      jobbagService.save(jobbag);
				                            	
				                            	      if(list.size() == 1){
				                            		      JobBagSplite jobBagSplite = list.get(0);
				                            		      jobBagSplite.setLpPagesSeqBegin(1);
				                					      jobBagSplite.setLpPagesSeqEnd(jobbag.getPages());
				                					      jobBagSplite.setLpPagesSeqDiff(jobbag.getPages());
				                					      jobBagSplite.setLpAccountSeqBegin(1);
				                					      jobBagSplite.setLpAccountSeqEnd(jobbag.getAccounts());
				                					      jobBagSplite.setLpAccountSeqDiff(jobbag.getAccounts());
				                					      jobbagSpliteService.save(jobBagSplite);
				                            	      }
				                            	      for(JobBagSplite jobbagSplite: list){
				                            		      jobbagSplite.setAccounts(jobbag.getAccounts());
				                            		      jobbagSplite.setPages(jobbag.getPages());
				                            		      jobbagSplite.setSheets(jobbag.getSheets());
				                            		      jobbagSpliteService.save(jobbagSplite);
				                            	      }
				                            	
				                                  }			                        		
				                			      SysLog syslog = new SysLog();
				                			      syslog.setLogType("CREATE_JOBBAG_AMEX");
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
			                		   }
			                	   }
			                	   if(!fileCorrect){
			                		   SysLog syslog = new SysLog();
		                			   syslog.setLogType("CREATE_JOBBAG_AMEX");
		                			   syslog.setSubject(errorFileName);
		                			   syslog.setMessageBody(formater.toString());
		                			   syslog.setIsException(true);
		                			   Calendar calendar = Calendar.getInstance();
		                			   Date now = new Date();
		                			   calendar.setTime(now);
		                			   syslog.setCreateDate(calendar.getTime());
		                			   syslogService.save(syslog);
			                	   }			                	
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
		                        e.printStackTrace();
		                    }		                    
		                    HibernateSessionFactory.closeSession();		                    
		                }
			            //將processing 資料, 搬到 backup 
			             SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			             String backupFileName = file.getName() + "."+formatter.format(new Date());
			             
			             if(allResolved){
			            	//全部處理完畢移到backup folder
			                Boolean ret = FileOperate.moveFile(processFolderString+file.getName(), backupFolderString+backupFileName);
			             }else{
			            	//沒全部處理完畢移到待處理的folder
			            	Boolean ret = FileOperate.moveFile(processFolderString+file.getName(), folderString +file.getName() );
			             }
		             } //
                
	            }// for 
	        }

		}
	
	private static String getAfpFileName(String wriFullName) {
		String retAfpFileName="";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(wriFullName));
            String readLine = null;
            String text = null;
            AmexFormater formater = null;
            while ((readLine = reader.readLine()) != null) {
            	text = text + readLine;
            }
            
            String patternStr = "/laser/(.+).afp";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            boolean matchFound = matcher.find();
            int k = 0;
            if (matchFound) {
              for(int i = 0; i <= matcher.groupCount(); i++) {
                String groupStr = matcher.group(i);
                if(i == 1)
                	retAfpFileName = groupStr;
              }

            }            

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retAfpFileName;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final SchedulerService scheduleService = SchedulerService.getInstance();
		AmexReader logisticReader = new AmexReader();
		logisticReader.readfile();
	}

}
