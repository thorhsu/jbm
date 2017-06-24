package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class SLParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(SLParsingService.class);
	

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		
		switch(fileLength) { 
	    case 8: 
	    	//XXmmddnn (S4040601 – S4040699)
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[0-9][1-9]"))  {
	    		filePattern = "XXmmddnn";
	    		filePattern = fileName.substring(0,2) + "mmddnn" ;	
	    		//logFileSEQ = "0" + fileName.substring(6, 8);
	    	} 	    		
	    	break; 
	    case 6: 
	    	//XXmmdd (S10406, SB0406, SC0406)
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXmmdd";
	    		filePattern = fileName.substring(0,2) + "mmdd" ;		    		
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
