package com.salmat.jbm.logisticParsingService;



import org.apache.log4j.Logger;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class TCParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(TCParsingService.class);
		@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		
		switch(fileLength) { 
	    case 5: 
	    	//XXmmX (TP06P, TP06S, TM06S, TM06L, FM06S, FM06L)
	    	if(fileName.matches("..(0[1-9]|1[0-2])."))  {
	    		filePattern = "XXmmX";
	    		filePattern = fileName.substring(0,2) + "mm" + fileName.substring(4,5) ;		    		
	    	} 	    		
	    	break; 			
	    case 6: 
	    	//XXmmXn (TP06P1, TP06S1, TM06S1, TM06L1)
	    	if(fileName.matches("..(0[1-9]|1[0-2]).[1-9]"))  {
	    		filePattern = "XXmmXn";
	    		filePattern = fileName.substring(0,2) + "mm" + fileName.substring(4,5) + "n";		
	    		//logFileSEQ = "00" + fileName.substring(5, 6);
	    	} 	    		
	    	break; 		
	    case 7: 
	    	//XXmmddX (TP0601P, TP0601S, TM0601S, TM0601L)
	    	if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])."))  {
	    		filePattern = "XXmmddX";
	    		filePattern = fileName.substring(0,2) + "mmdd" + fileName.substring(6,7);
	    	} 	    		
	    	break; 		
	    case 8: 
	    	//TC2D0909, TM1121S1
	    	if(fileName.matches("....(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXXXmmdd";
	    		filePattern = fileName.substring(0,4) + "mmdd" ;
	    	}else if(fileName.matches("..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])..")){
	    		filePattern = "XXmmddXX";
	    		filePattern = fileName.substring(0,2) + "mmdd" + fileName.substring(6,8);
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
