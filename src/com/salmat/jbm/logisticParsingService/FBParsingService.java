package com.salmat.jbm.logisticParsingService;


import org.apache.log4j.Logger;
import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class FBParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(FBParsingService.class);
	

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		String typeOfFile = "";
		String speaker = "";
		
		switch(fileLength) { 
	    case 19: 
	    	//03B  (RNB_03B201107060001 - RNB_03B201107069999)
	    	if(fileName.matches("RNB_03B............"))  {
	    		filePattern = "03B";   		
	    	} 
	    	//03O  (RNB_03O201107060001 - RNB_03O201107069999)
	    	else if(fileName.matches("RNB_03O............"))  {
	    		filePattern = "03O";
	    	} 
	    	break; 				
	    case 15: 
	    	//03B  (03B201107060001 - 03B201107069999)
	    	if(fileName.matches("03B............"))  {
	    		filePattern = "03B";    		
	    	} 
	    	//03O  (03O201107060001 - 03O201107069999)
	    	else if(fileName.matches("03O............"))  {
	    		filePattern = "03O";
	    	} 
	    	break; 
	    case 13: 
	    	//RNB_03B  (RNB_03B060001 - RNB_03B069999)
	    	if(fileName.matches("RNB_03B......"))  {
	    		filePattern = "RNB_03B";    		
	    	} 
	    	//RNB_03A  (RNB_03A060000)
	    	else if(fileName.matches("RNB_03A......"))  {
	    		filePattern = "RNB_03A";
	    	} 
	    	//RNB_03N  (RNB_03N060000)
	    	else if(fileName.matches("RNB_03N......"))  {
	    		filePattern = "RNB_03N";
	    	} 		    	
	    	break;		    	
	    case 9: 
	    	//M1A,M1P,M2A,M2P , M1AXXmmdd 
	    	if(fileName.matches("M([1-2])(A|P)..(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "M1AXXmmdd";
	    		filePattern = fileName.substring(0, 3)  ;	    		
	    	} 
	    	//XXXmmddXX , CBDmmddA3/A7 , CBDmmddM1/M2/M2/M3/M4/M5/M6, CBDmmddS1/S2/S5/S6, RCDmmddS1
	    	else if(fileName.matches("(C|R)(B|C)D(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])(A|M|S)[1-7]"))  {
	    		filePattern = "XXXmmddXX";
	    		filePattern = fileName.substring(0, 3);
	    		typeOfFile = fileName.substring(7, 8);
	    		speaker = fileName.substring(8, 9);
	    	} 
	    	break; 
	    case 8:
	    	//XXXX_ddn , (CBAmmdd/RCDmmdd)
	    	if(fileName.matches("...._(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]"))  {
	    		filePattern = "XXXX_ddn";
	    		filePattern = fileName.substring(0, 4)+"_ddn";	    
	    		//logFileSEQ = "00" + fileName.substring(7, 8);
	    	} 
	    	break;		    	
	    case 7: 
	    	//XXXmmdd , (CBAmmdd/RCDmmdd)
	    	if(fileName.matches("...(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXXmmdd";
	    		filePattern = fileName.substring(0, 3) + "mmdd";	
	    	} 
	    	break; 		    	
	    default: 
	    	log.warn("No Matcing :" + formater.toString());
		}
		if(filePattern != null){
			LogisticParsingObj lpo = new LogisticParsingObj();
			lpo.setFilePattern(filePattern);
			lpo.setTypeOfFile(typeOfFile);
			lpo.setSpeaker(speaker);
			return lpo;
		}else{
			return null;
		}
	}
}
