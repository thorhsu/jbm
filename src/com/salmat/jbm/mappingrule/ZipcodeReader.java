package com.salmat.jbm.mappingrule;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import java.text.SimpleDateFormat;
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
public class ZipcodeReader {

	/**
	 * Log4j instance.
	 */
	private final static Logger log = Logger.getLogger(ZipcodeReader.class);

	/* Constants */
	private static final ZipcodeReader instance = new ZipcodeReader();
	private static final ZipcodeInfoService zipcodeInfoService = ZipcodeInfoService.getInstance();
	private static final ZipcodeDetailService zipcodeDetailService = ZipcodeDetailService.getInstance();
	private static final SyslogService syslogService = SyslogService.getInstance();	
	private static final JobBagService jobbagService = JobBagService.getInstance();	
	private static Map<Integer, Integer> dispatchTimeMappings; // 交寄方式(JBM代碼,郵局代碼)
	private static Map<Integer, Integer> dispatchKindMappings; // 郵資單種類(JBM代碼,郵局代碼)

	private ZipcodeReader() {
		dispatchTimeMappings = new HashMap<Integer, Integer>();
		// (CODE_MAIL_CATEGORY)
		dispatchTimeMappings.put(26, 0); // 普通
		dispatchTimeMappings.put(27, 1); // 限時專送
		dispatchTimeMappings.put(28, 0); // 普通掛號
		dispatchTimeMappings.put(29, 1); // 限時掛號
		dispatchTimeMappings.put(30, 0); // 掛號附回執
		dispatchTimeMappings.put(31, 1); // 限時附回執
		dispatchTimeMappings.put(32, 0); // 航空(預設為郵局的普通)
		dispatchTimeMappings.put(33, 0); // 合併交寄(預設為郵局的普通)
		
		dispatchKindMappings = new HashMap<Integer, Integer>();
		
		dispatchKindMappings.put(13, 1); // 信函
		dispatchKindMappings.put(14, 7); // 郵簡
		dispatchKindMappings.put(15, 5); // 印刷品
		dispatchKindMappings.put(16, 1); // 包裹 (預設為郵局的信函)
		dispatchKindMappings.put(17, 1); // 小包 (預設為郵局的信函)
		dispatchKindMappings.put(18, 6); // 明信片
		dispatchKindMappings.put(19, 3); // 雜誌
		dispatchKindMappings.put(20, 4); // 新聞紙
		dispatchKindMappings.put(180, 1); // 小包(極美) (預設為郵局的信函)
	}

	public static ZipcodeReader getInstance() {
		return instance;
	}


	public static void readfile() {
		String folderString = Util.getString("zipcode.folder");
		String processFolderString = Util.getString("zipcode.folder") +"\\processing\\" ;
		String backupFolderString = Util.getString("zipcode.folder") +"\\backup\\";		

        File folder = new File(folderString);

        if (folder.isDirectory()) {
        	
            String[] in_files = folder.list(); // 列出檔案
           
            // step 1. 若資料夾下無待處理檔案 => 直接結束
            // ==================================================
            if(in_files.length <= 1){
            	log.info("no zip code file to process!");
            	return ;
            }
            
            // step 2. 處理file
            // ==================================================
            for (int i=0; i<in_files.length;i++) {
                BufferedReader reader = null;
                
                if ( in_files[i].substring(in_files[i].length()-3, in_files[i].length()).equalsIgnoreCase("txt") ) { // 判斷副檔名是否為txt
                	
                	// 先將檔案搬移至processing資料夾
	                Boolean retMoving = FileOperate.moveFile(folderString+in_files[i], processFolderString+in_files[i]); 
	                File file = new File(processFolderString+in_files[i]);
	                
	                // 若搬移成功
	                if (retMoving) {
	                	String text = null;
	                    ZipCodeFormater formater = null;
	                    
	                    int counter = 0; // 記數用 for 特別處理
	                    String jobCodeNo = null; // 工單代號
	                    String logFileName = file.getName(); // 處理檔案名稱
	                    ZipcodeInfo zipcodeInfo = new ZipcodeInfo();
	                	
	                	log.info("process file:" + logFileName);
		                try {
		                    reader = new BufferedReader(new FileReader(file));
		                    while ((text = reader.readLine()) != null && !text.trim().equals("")) {
		                    	try{
		                    		// 若為標題則略過		
		                    		if(counter == 0){
		                    			counter ++;
		                    			continue;
		                    		}
		                    		
		                    		String[] tokens = text.split(","); // 以逗點為分隔parse資料
		                    		
		                    		// 判斷是否有8行, 若無則skip
		                    		if(tokens.length != 8){
		                    			log.error("Data format is not spilt in 8 , split length = " + tokens.length);
		                    			SysLog syslog = new SysLog();
		  	                			syslog.setLogType("CREATE_ZIPCODE");
		  	                			syslog.setSubject("產生郵遞區號資料發生錯誤, FILENAME:" + logFileName);
		  	                			syslog.setMessageBody("Data format is not spilt in 8 , split length = " + tokens.length);
		  	                			syslog.setIsException(true);
		  	                			syslog.setCreateDate(new Date());
		  	                			syslogService.save(syslog);
		                    			break;
		                    		}
		                    		
		                    		
		                    		formater = new ZipCodeFormater();
		                    		
		                    		formater.setClientID(tokens[0].trim()); // 客戶代號
		                    		
		                    		if(StringUtils.equals("IN", formater.getClientID())){
		                    			formater.setClientID("FB");
		                    		}
		                    		
		                    		formater.setCycleDate(formatDate(tokens[1].trim())); // CYCLEDATE
		                    		String afpNm = tokens[2].trim();
		                    		if(afpNm.endsWith(".pdf") || afpNm.endsWith(".afp"))
		                    			afpNm = afpNm.substring(0, afpNm.length() - 4);
		                    		formater.setAfpFileName(tokens[2].trim()); // AFP NAME
		                    		formater.setReceiveDate(formatDate(tokens[3].trim())); // RECEIVE DATE
		                    		formater.setZipCode(tokens[4].trim()); 
		                    		formater.setSum(Long.valueOf(tokens[5].trim()));
		                    		formater.setPattern(tokens[6].trim()); // JOB_CODE Pattern
		                    		
		                    		// TOTAL數有可能為空
		                    		if(StringUtils.isBlank(tokens[7])){
		                    			formater.setTotal(null);
		                    		}else{
		                    			formater.setTotal(Long.valueOf(tokens[7].trim()));
		                    		}
		                    		
		                    		formater.setLogFileName(logFileName);
		                    		
		                    		// 若為第1筆資料, 則取得JoBCodeNo
		                    		if(counter == 1){
		                    			 List<JobBag> jobBags = jobbagService.findJobBagsByCycleAndCustNoAndFile(formater.getAfpFileName(), formater.getClientID(), formater.getCycleDate());
		                    			 
		                    			 // 若找不到工單或找出來的工單大於兩筆 => 紀錄錯誤並結束
		                    			 if(jobBags.isEmpty()){
		                    				  
		                    				 SysLog syslog = new SysLog();
			  	                			 syslog.setLogType("CREATE_ZIPCODE");
			  	                			 syslog.setSubject("產生郵遞區號資料發生錯誤, FILENAME:" + logFileName);
			  	                			 syslog.setMessageBody("Find JobBag size = " + jobBags.size() + ", must be equal to 1");
			  	                			 syslog.setIsException(true);
			  	                			 syslog.setCreateDate(new Date());
			  	                			 syslogService.save(syslog);
		                    			   
		                    				 break ;
		                    			 }
		                    			 
		                    			 // 若找出兩筆以上工單, 則以更細的方式判斷(有可能同一CYCLE DATE內有兩筆以上工單)
		                    			 if(jobBags.size() >= 2){
		                    				jobCodeNo = null; // initialize
		                    				for(JobBag jobBag : jobBags){
		                    					// 1. 判斷收檔日期, 再判斷筆數日期, 都相同則直接採用此筆(較嚴格做法)
		                    					if(jobBag.getReceiveDate().compareTo(formater.getReceiveDate()) == 0 
		                    							&& Long.valueOf(jobBag.getAccounts().toString()).compareTo(formater.getTotal()) == 0){
		                    						jobCodeNo = jobBag.getJobBagNo();
		                    						break;
		                    					}
		                    					// 2. 若收檔日期不同,直接判斷筆數(較寬鬆做法)
		                    					if(Long.valueOf(jobBag.getAccounts().toString()).compareTo(formater.getTotal()) == 0){
		                    						jobCodeNo = jobBag.getJobBagNo();
		                    						break;
		                    					}	
		                    				}
		                    				// 若上述都找不到, 則記錄並break
		                    				if(jobCodeNo == null){
		                    					
		                    					SysLog syslog = new SysLog();
				  	                			syslog.setLogType("CREATE_ZIPCODE");
				  	                			syslog.setSubject("產生郵遞區號資料發生錯誤, FILENAME:" + logFileName);
				  	                			syslog.setMessageBody("Find JobBag size more than 1, size = " + jobBags.size());
				  	                			syslog.setIsException(true);
				  	                			syslog.setCreateDate(new Date());
				  	                			syslogService.save(syslog);
				  	                			 
		                    					break;
		                    				}   				 
		                    			 }
		                    			 
		                    			 jobCodeNo = jobBags.get(0).getJobBagNo(); // 取得工單號碼
		                    			 
		                    			 // 新增/更新郵遞區號索引資料(zipcode_info)   			 
		                    			 zipcodeInfo.setCustNo(formater.getClientID());
		                    			 zipcodeInfo.setSrcFilename(logFileName);
		                    			 zipcodeInfo.setZipcodeTotal(formater.getTotal() == null ? null : formater.getTotal().intValue());
		                    			 zipcodeInfo.setJobBagNo(jobCodeNo);
		                    			 zipcodeInfo.setCycleDate(formater.getCycleDate());
		                    			 zipcodeInfo.setReceiveDate(formater.getReceiveDate());
		                    			 zipcodeInfo.setAfpFilename(formater.getAfpFileName());
		                    			 zipcodeInfo.setFilePattern(formater.getPattern());
		                    			 zipcodeInfo.setCreateDate(new Date());
		                    			 zipcodeInfo.setDetailIsDelete(false);
		                    			 
		                    			// 新增對應配送方式(JBM=>郵局)
		                    			 if(jobBags.get(0).getCodeByCodeMailCategory() != null){
		                    			    Integer codeMailCategoryId = jobBags.get(0).getCodeByCodeMailCategory().getId();
		                    			    if(dispatchTimeMappings.containsKey(codeMailCategoryId)){
				                    		   	zipcodeInfo.setDispatchTime(dispatchTimeMappings.get(codeMailCategoryId).toString());
				                    		}else{
				                    			log.error("can't find mapping dispatch time to Post, jobCodeNo = " + jobCodeNo + ", codeMailCategoryId = " + codeMailCategoryId);
				                    			zipcodeInfo.setDispatchTime("0"); // 若找不到則預設為普通
				                    		}
		                    			 }else{		                    			     
		                    				 log.error("can't find mapping dispatch time to Post, jobCodeNo = " + jobCodeNo + ", codeMailCategoryId = " + null);		  		                    	     
		  		                    	     SysLog syslog = new SysLog();
		  	                			     syslog.setLogType("CREATE_ZIPCODE");
		  	                			     syslog.setSubject("讀取郵遞區號資料發生錯誤, FILENAME:" + logFileName);
		  	                			     syslog.setMessageBody(jobBags.get(0).getJobBagNo() + "沒有設定mail category" );
		  	                			     syslog.setIsException(false);
		  	                			     syslog.setCreateDate(new Date());
		  	                			     syslogService.save(syslog);
		  	                			     zipcodeInfo.setDispatchTime("0"); // 若找不到則預設為普通		  	                			     
				                    		 
		                    			 }
		                    			 
		                    			 Integer codeLgTypeId = jobBags.get(0).getCodeByCodeLgType().getId();
		                    			 if(dispatchKindMappings.containsKey(codeLgTypeId)){
				                    			zipcodeInfo.setDispatchKind(dispatchKindMappings.get(codeLgTypeId).toString());
				                    		}else{
				                    			log.error("can't find mapping dispatch kind to Post, jobCodeNo = " + jobCodeNo + ",codeLgTypeId = " + codeLgTypeId);
				                    			zipcodeInfo.setDispatchKind("1"); // 若找不到則預設為信函
				                    		}
				                    	
		                    			// 判斷資料庫中是否已存在相同工單號碼
		                    			 List<ZipcodeInfo> zipcodeInfos = zipcodeInfoService.findByJobBagNo(jobCodeNo);
		                    			 
		                    			 
		                    			 if(zipcodeInfos == null || zipcodeInfos.isEmpty()){ // 若無則新增此筆資料
		                    				 log.info("#### insert new ZipcodeInfo - start, jobCodeNo = " + jobCodeNo + " ####");
		                    				 
		                    				 HibernateSessionFactory.getSession().clear();
		                    			     HibernateSessionFactory.getSession().getTransaction().begin();
		                    				 zipcodeInfoService.save(zipcodeInfo);
		                    				 HibernateSessionFactory.getSession().getTransaction().commit();
		                    				 
		                    				 log.info("#### save zipcode_info - end ####, id = " + zipcodeInfo.getZipcodeInfoNo());
		                    				 
		                    			 }else{ // 若已存在相同工單資訊, 則更新ZipcodeInfo資訊, 並清空ZipcodeDetail資料
		                    				 
		                    				 Integer zipcodeInfoNo = zipcodeInfos.get(0).getZipcodeInfoNo();
		                    				 
		                    				 zipcodeInfo.setZipcodeInfoNo(zipcodeInfoNo);
		                    				 log.info("#### update ZipcodeInfo, ZipcodeInfoNo = " + zipcodeInfoNo + " ####");
		                    				 HibernateSessionFactory.getSession().clear();
		                    			     HibernateSessionFactory.getSession().getTransaction().begin();
		                    				 zipcodeInfoService.save(zipcodeInfo); // 更新ZipCodeInfo
		                    				 HibernateSessionFactory.getSession().getTransaction().commit();
		                    				 log.info("#### delete ZipcodeDetail by ZipcodeInfoNo = " + zipcodeInfoNo + " ####");
		                    				 // 刪除ZipcodeDetail=>以下重新匯入
		                    				 int deleteCount = zipcodeDetailService.deleteZipcodeDetailByZipcodeInfoNo(zipcodeInfoNo);
		                    				 log.info("#### delete count = " + deleteCount + " ####");
		                    			 }
		                    		}
		                    		
		                    		formater.setJobBagNo(jobCodeNo);
		                    		log.debug("formater = " + formater.toString());
		                    		
		                    		//  新增郵遞區號明細資料(zipcode_detail)
		                    		ZipcodeDetail zipcodeDetail = new ZipcodeDetail();
		                    		zipcodeDetail.setZipcodeInfo(zipcodeInfo);
		                    		zipcodeDetail.setZipcode(formater.getZipCode());
		                    		zipcodeDetail.setZipcodeSum(formater.getSum().intValue());
		                    		zipcodeDetail.setZipcodeTotal(formater.getTotal() == null ? null : formater.getTotal().intValue());
		                    		
		                    		zipcodeDetailService.save(zipcodeDetail);
		                    		SysLog syslog = new SysLog();
		                			syslog.setLogType("CREATE_ZIPCODE");
		                			syslog.setSubject("產生郵遞區號成功");
		                			syslog.setMessageBody("產生郵遞區號成功" + logFileName + ":" + text);
		                			syslog.setIsException(false);
		                			syslog.setCreateDate(new Date());
		                			syslogService.save(syslog);
		                		
		                    		counter ++;
		                       }catch(Exception e){
		                    	   e.printStackTrace();
		                    	   log.error("", e);
		                    	   if(HibernateSessionFactory.getSession().getTransaction().isActive())
				                	    HibernateSessionFactory.getSession().getTransaction().rollback();
		                    	   SysLog syslog = new SysLog();
	                			   syslog.setLogType("CREATE_ZIPCODE");
	                			   syslog.setSubject("讀取郵遞區號資料發生錯誤, FILENAME:" + logFileName);
	                			   syslog.setMessageBody( e.getLocalizedMessage() );
	                			   syslog.setIsException(true);
	                			   syslog.setCreateDate(new Date());
	                			   syslogService.save(syslog);
	                			   break; // 發生錯誤時此筆SKIP
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
		             log.info("move file:" + file.getName() + ",isSuccess:" + ret);
		             
	             } //
           }// for 
       }
	}
	
	/**
	 * 處理字串日期格式,使之變為YYYY/MM/DD
	 * 
	 * @param dateStr 輸入字串
	 * @return 標準YYYY/MM/DD格式
	 * @throws Exception 格式不符時
	 */
	private static String formatDate(String dateStr) throws Exception{
		
		// 輸入為空時, 回傳null
		if(StringUtils.isBlank(dateStr)){
			return null;
		}
		
		// parse date
		String[] dates = StringUtils.split(dateStr, "/");
		
		// 未有年月日三段時 = > 丟出例外
		if(dates.length != 3){	
			throw new Exception ("parse yyyy/MM/dd  error, input date = " + dateStr);
		}
		
		if(StringUtils.length(dates[0]) == 2){ // 若年分只有後兩碼
			dateStr = "20" + dateStr;  // 年加上20
		}
		
		return dateStr;
		
	}
}
