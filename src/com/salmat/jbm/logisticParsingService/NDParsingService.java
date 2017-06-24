package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class NDParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(NDParsingService.class);

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		switch(fileLength) { 
	    case 10: 
	    	// XyymmXXXXX (D00086CATV, D00086FTTC)
	    	if(fileName.matches("...(0[1-9]|1[0-2])....."))  {
	    		filePattern = "XXXmmXXXXX";
	    		filePattern = fileName.substring(0, 3) + "mm" + fileName.substring(5, 10);		    		
	    	} 	    	
	        break; 				
	    case 6: 
			if(fileName.matches("D[0-9][0-9](0[1-9]|1[0-2])[0-9]"))  {
				//DXXmmX (D00081, D00082, D00083, D00084, D00085, D00086)
	    		filePattern = "DXXmmX";
	    		filePattern = fileName.substring(0, 3) + "mm" + fileName.substring(5, 6) ;		    		
	    	}
	    	//XXmmdd (DI0405, EI0405)
	    	 else if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXmmdd";
	    		filePattern = fileName.substring(0, 2) + "mmdd";		    		
	    	} 
	    	 				    	
	    	//XXXXdd (STAA05, STBB05, STCC05, STDD05 )   ç New Rule (lost updated)
	    	else if(fileName.matches("....(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXXXdd";
	    		filePattern = fileName.substring(0, 4) + "dd";		    		
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
