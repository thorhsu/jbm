package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class HLParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(HLParsingService.class);
	

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		switch(fileLength) { 
	    case 11: 
	    	//XXmmddn_SEP (IN04011_SEP, )
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]_SEP"))  {
	    		filePattern = "XXmmddn_SEP";
	    		filePattern = fileName.substring(0, 2) + "mmddn_SEP";	
	    		//logFileSEQ = "00" + fileName.substring(6, 7);
	    	} 
	        break;			
	    case 10: 
	    	//XXXXyymmdd (AANT110401 )
	    	if(fileName.matches("....[0-9][0-9](0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXXXyymmdd";
	    		filePattern = fileName.substring(0, 4) + "yymmdd";		    		
	    	} 
	        break; 
	    case 7: 
	    	//XXmmddn (IN04011, )
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]"))  {
	    		filePattern = "XXmmddn";
	    		filePattern = fileName.substring(0, 2) + "mmddn";	
	    		//logFileSEQ = "00" + fileName.substring(6, 7);
	    	} 
	        break; 	
	    case 6: 
	    	//XXmmdd (LT0401, DA0401)
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXmmdd";
	    		filePattern = fileName.substring(0, 2) + "mmdd";		    		
	    	} 
	        break; 	
	    case 5: 
	    	//POSmm (POS01,POS02, …)
	    	if(fileName.matches("...(0[1-9]|1[0-2])"))  {
	    		filePattern = "POSmm";
	    		filePattern = fileName.substring(0, 3) + "mm";		    		
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
