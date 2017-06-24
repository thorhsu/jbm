package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class JPParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(JPParsingService.class);

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		switch(fileLength) { 
	    case 8: 
	    	//XXXXXddX (D276PddM, D276SddM, D276LddM, D276PddR)
	    	if(fileName.matches(".....(0[1-9]|1[0-9]|2[0-9]|3[0-1])."))  {
	    		filePattern = "XXXXXddX";
	    		filePattern = fileName.substring(0, 5) + "dd" +fileName.substring(7, 8);		    		
	    	} 
	    	break; 
	    	
	    case 6: 
	    	//XXddXn (YP07R1, YS07R1, YP07M1, YS07M1, YL07M1, QP07R1, QS07R1, QP07M1, QS07M1, QL07M1)
	    	if(fileName.matches("..(0[1-9]|1[0-9]|2[0-9]|3[0-1]).[1-9]"))  {
	    		filePattern = "XXddXn";
	    		filePattern = fileName.substring(0, 2) + "dd" +fileName.substring(4, 6);	
	    		//logFileSEQ = "00" + fileName.substring(5, 6);
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
