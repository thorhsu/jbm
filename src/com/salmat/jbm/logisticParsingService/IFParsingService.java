package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class IFParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(IFParsingService.class);


	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		switch(fileLength) { 
	    case 6: 
	    	//XX_Xdd (IF_R06, IT_M06)
	    	if(fileName.matches(".._.(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XX_Xdd";
	    		filePattern = fileName.substring(0, 4) + "dd" ;		    		
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
