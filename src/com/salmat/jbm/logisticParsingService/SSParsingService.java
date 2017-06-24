package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class SSParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(SSParsingService.class);

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		switch(fileLength) { 
	    case 6: 
	    	//XXddnn (SS0601)
	    	if(fileName.matches("..(0[1-9]|1[0-9]|2[0-9]|3[0-1])[0-9][1-9]"))  {
	    		filePattern = "XXddnn";
	    		//logFileSEQ = "0" + fileName.substring(4, 6);
	    		filePattern = fileName.substring(0,2) + "ddnn" ;		    		
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
