package com.salmat.jbm.logisticParsingService;


import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class AIParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(AIParsingService.class);
	
	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if(fileName == null)
			return null;
		Integer fileLength = fileName.length();		
		String filePattern = null;
		switch(fileLength) { 
	    case 7: 
	    	//XXmmddn (MS06181)
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]"))  {
	    		filePattern = "XXmmddn";
	    		filePattern = fileName.substring(0, 2) + "mmddn";
	    		//logFileSEQ = "00" + fileName.substring(6, 7);
	    	} 
	        break; 			
	    case 6: 
	    	//XXXmmn (AGS061, AGS062, AGS063,AGL061)
	    	if(fileName.matches("...(0[1-9]|1[0-2])[1-9]"))  {
	    		filePattern = "XXXmmn";
	    		filePattern = fileName.substring(0, 3) + "mmn";
	    		//logFileSEQ = "00" + fileName.substring(5, 6);
	    	}
	    	else {
	    		filePattern = "Error";
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
