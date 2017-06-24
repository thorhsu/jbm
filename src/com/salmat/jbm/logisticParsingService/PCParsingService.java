package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class PCParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(PCParsingService.class);
	

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		
		
		switch(fileLength) { 
	    case 10: 
	    	//XXXXmmdd_D
	    	if(fileName.matches("....(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])_D"))  {
	    		filePattern = "XXXXmmdd_D";
	    		filePattern = fileName.substring(0, 4) + "mmdd_D" ;		    		
	    	} 
	    	//XXXXmmdd_S
	    	else if(fileName.matches("....(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])_S"))  {
		    		filePattern = "XXXXmmdd_S";
		    		filePattern = fileName.substring(0, 4) + "mmdd_S" ;			    		
		    	}		    		
	        break; 
	    case 9: 
	    	//XXXmmdd_D 
	    	if(fileName.matches("...(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])_D"))  {
	    		filePattern = "XXXmmdd_D";
	    		filePattern = fileName.substring(0, 3) + "mmdd_D" ;		    		
	    	} 
	    	//XXXmmdd_S
	    	else if(fileName.matches("...(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])_S"))  {
		    		filePattern = "XXXmmdd_S";
		    		filePattern = fileName.substring(0, 3) + "mmdd_S" ;				    		
		    	}		    		
	        break;		
	    case 8: 
	    	//C32dd1_D, C32111_D, A07A0414 C32dd1_D C32111_D
	    	if(fileName.matches("...(0[1-9]|1[0-9]|2[0-9]|3[0-1])1_D"))  {
	    		filePattern = "XXXdd1_D";
	    		filePattern = fileName.substring(0, 3) + "dd1_D" ;	
	    	} 	
	    	//C32dd7_D
	    	else if(fileName.matches("...(0[1-9]|1[0-9]|2[0-9]|3[0-1])7_D"))  {
	    		filePattern = "XXXdd7_D";
	    		filePattern = fileName.substring(0, 3) + "dd7_D" ;	
	    	} 
	    	//XXXXmmdd
	    	//(A07Ammdd,A07Bmmdd, A07Cmmdd)
	    	else if(fileName.matches("....(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXXXmmdd";
	    		filePattern = fileName.substring(0, 4) + "mmdd" ;		
	    		
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
