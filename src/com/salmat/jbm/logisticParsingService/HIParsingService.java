package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class HIParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(HIParsingService.class);
	

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		switch(fileLength) { 
	    case 6: 
	    	//XXmmnn (MS0601, ML0601, QS0301, QL0301, YS0101, YL0101 )
	    	if(fileName.matches("..(0[1-9]|1[0-2])[0-9][1-9]"))  {
	    		filePattern = "XXmmnn";
	    		filePattern = fileName.substring(0, 2) + "mmnn";	
	    		//logFileSEQ = "0" + fileName.substring(4, 6);
	    	} 
	    	//XXqqnn (QS0101, QL0201, QS0301, QL0401,第幾季 )
	    	else if(fileName.matches("Q.(0[1-4])[0-9][1-9]"))  {
	    		filePattern = "XXqqnn";
	    		filePattern = fileName.substring(0, 2) + "qqnn";	
	    		//logFileSEQ = "0" + fileName.substring(4, 6);
	    	} 
	    	//XXyynn (YS0101 上半年, YL0201下半年 )
	    	else if(fileName.matches("Y.[0-9][0-9][0-9][1-9]"))  {
	    		filePattern = "XXyynn";
	    		filePattern = fileName.substring(0, 2) + "yynn";
	    		//logFileSEQ = "0" + fileName.substring(4, 6);
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
