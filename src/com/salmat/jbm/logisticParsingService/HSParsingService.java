package com.salmat.jbm.logisticParsingService;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.hibernate.Amex;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JobCode;
import com.salmat.jbm.service.JobCodeService;
import com.salmat.jbm.service.LogisticParsingService;

public class HSParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(HSParsingService.class);
	private final static JobCodeService jobcodeService = JobCodeService.getInstance();
	

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		String logFileSEQ = "001";
		
		
		
		// 優先用 檔名前6碼 去找jobCode, 若找不到, 再用pattern 去找
		if (fileName.length()>6 ) {
	    	String queryString = "select  {a.*} " 
	    		+ " from amex a " 
	        	+ " where a.country = 'HS' and  a.afp_name = ? " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("a", Amex.class);		    
		    sqlQuery.setParameter(0, fileName.substring(0, 6));

	        List<Amex> retList = sqlQuery.list();
	        if (null!=retList && retList.size() == 1  ) {
	        	Amex amex = (Amex)retList.get(0);
	        	JobCode jobCode = jobcodeService.findById(amex.getJobCodeNo());
	        	if (null== jobCode ) 
	        		log.warn("amex mapping : 工單樣本被刪除" + amex.getJobCodeNo()+ " :" + formater.toString());	
	        	else {
	        		log.info("amex mapping : " + jobCode.getJobCodeNo()+ " :" + formater.toString());	
	        		jobCode.setLogFileSeq(logFileSEQ);
	        		LogisticParsingObj lpo = new LogisticParsingObj();
	        		lpo.setJobCode(jobCode);
	        		return lpo;
	        	}
	        	
	       } else if (null!=retList && retList.size()>1  )  {
	        	//多筆 amex 處理
	        	log.warn("多筆 amex 處理 :" +  retList.size() + "筆 : " +  formater.toString());
	        	return null;
	        }	        
		}
		
		switch(fileLength) { 
	    case 5: 
	    	//Xmmnn (Q0401, Q0402,… )
	    	if(fileName.matches("Q(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "Xmmnn";
	    		filePattern = fileName.substring(0, 1) + "mmdd";	
	    		//logFileSEQ = "0" + fileName.substring(3, 5);
	    	}
	    	//Xmmdd (M0408)
	    	else if(fileName.matches(".(0[1-9]|1[0-2])[0-9][1-9]"))  {
	    		filePattern = "Xmmdd";
	    		filePattern = fileName.substring(0, 1) + "mmdd";		    		
	    	}	
	    	break;
	    case 6: 
	    	//HSBC Welcome(HS)
	    	//XXyymm (WE1106)
	    	if(fileName.matches("(WE|PA|PC|PH|PL|PP|PR)[0-9][0-9](0[1-9]|1[0-2])"))  {
	    		filePattern = "XXyymm";		    		
	    		filePattern = fileName.substring(0, 2) + "yymm";
	    	}
	    	//HSBC TAX / HSBC SYI TAX(HS), 
	    	//	XXmmdd (T20625, S20625, )
	    	else if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXmmdd";
	    		filePattern = fileName.substring(0, 2) + "mmdd";		    		
	    	}
	    	//HSBC Auto Letter(HS)
	    	//  XXXXdd (AUTO25, AULI25, AUG125, AUAN25, AU2025, AU2125, AURZ25 )
	    	else if(fileName.matches("AU..(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXXXdd";
	    		filePattern = fileName.substring(0, 4) + "dd";		    		
	    	}			    	
	        //HSBC Banking / Mutual Fund(HS)
	    	//  XXXddn (PAN241, CMN241, AMN241, B2M251, D1A251, O1A251,…..)
	    	else if(fileName.matches("...(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]"))  {
	    		filePattern = "XXXddn";
	    		filePattern = fileName.substring(0, 3) + "ddn";		
	    		//logFileSEQ = "00" + fileName.substring(5, 6);
	    	}
	    	//  XXXXdd (AULIX1)
	    	else if(fileName.matches("....X[1-9]"))  {
	    		filePattern = "XXXXdd";
	    		filePattern = fileName.substring(0, 4) + "dd";		    		
	    	}		    	
	    	//HSBC Merchant Invoice(HS)
	    	//  mmddXX (0625NO, 0625GP, 0625MM )
	    	else if(fileName.matches("(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]).."))  {
	    		filePattern = "mmddXX";
	    		filePattern = "mmdd" + fileName.substring(4, 6) ;		    		
	    	}		    	
	    	//XXX18n
	    	else if(fileName.matches("...18[1-9]"))  {
	    		filePattern = "XXX18n";
	    		filePattern = fileName.substring(0, 3) + "18n";	
	    		//logFileSEQ = "00" + fileName.substring(5, 6);
	    	}				    	
	    	else {
	    		
	    		log.warn("No Matcing :" + formater.toString());
	    	}
	    	break;
	    case 7: 
	    	//	XXmmddn (IN04011, ) ACS1101
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]"))  {
	    		filePattern = "XXmmddn";
	    		filePattern = fileName.substring(0, 2) + "mmddn";	
	    		//logFileSEQ = "00" + fileName.substring(6, 7);
	    	}else {
	    		
	    		log.warn("No Matcing :" + formater.toString());
	    	}
	        break; 
	    case 8: 
	    	//	HSBC PR(HS)
	    	// XXyymmnn (PR110601 )
	    	if(fileName.matches("..[0-9][0-9](0[1-9]|1[0-2])[0-9][1-9]"))  {
	    		filePattern = "XXyymmnn";
	    		filePattern = fileName.substring(0, 2) + "yymmnn";		
	    		//logFileSEQ = "0" + fileName.substring(6, 8);
	    	}
	    	//HSBC 帳號卡(HS)
	    	//XXXXmmdd (ACPA1106, ACPP1106, ACSA1106, ACSP1106)
	    	else if(fileName.matches("....(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
		    		filePattern = "XXXXmmdd";
		    		filePattern = fileName.substring(0, 4) + "mmdd";		    		
		    }
	    	//HSBC Welcome(HS)
	    	//XXyymm14 (WE110614 )	
	    	else if(fileName.matches("WE[0-9][0-9](0[1-9]|1[0-2])14"))  {
	    		filePattern = "XXyymm14";
	    		filePattern = "XXyymm14";			    		
	    	}		    	
	    	else  	{
	    		
	    		log.warn("No Matcing :" + formater.toString());
	    	}
	        break; 
	    case 10: 
	    	// XXXXyymmdd (AANT110401 )
	    	if(fileName.matches("....[0-9][0-9](0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXXXyymmdd";
	    		filePattern = fileName.substring(0, 4) + "yymmdd";		    		
	    	}else {
	    		
	    		log.warn("No Matcing :" + formater.toString());
	    	}
	        break; 		
	    case 11: 
	    	// XXmmddn_SEP (IN04011_SEP, )
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]_SEP"))  {
	    		filePattern = "XXmmddn_SEP";
	    		filePattern = fileName.substring(0, 2) + "mmddn_SEP";	
	    		//logFileSEQ = "00" + fileName.substring(6, 7);
	    	}else {
	    		
	    		log.warn("No Matcing :" + formater.toString());
	    	}
	        break; 		
	    default: 
	    	log.warn("No Matcing :" + formater.toString());
		}		
		
		if(filePattern != null){
			LogisticParsingObj lpo = new LogisticParsingObj();
			lpo.setFilePattern(filePattern);			
			return lpo;
		}else{
			return null;
		}
	}
}
