package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class CPParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(CPParsingService.class);

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		switch(fileLength) { 
	    case 6: 
	    	//[D|U]XXddn (DPM051, DMS051, DML051)
	    	if(fileName.matches("(D|U)..(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]"))  {
	    		filePattern = "XXXddn";
	    		filePattern = fileName.substring(0, 3) + "ddn";		
	    		//logFileSEQ = "00" + fileName.substring(5, 6);
	    	} //TXXmmn (TPM051, TMS051, TML051)	
	    	else if (fileName.matches("T..(0[1-9]|1[0-2])[1-9]"))  {
	    		filePattern = "XXXmmn";
	    		filePattern = fileName.substring(0, 3) + "mmn";		
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
