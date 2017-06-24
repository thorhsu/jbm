package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class SCParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(SCParsingService.class);
	

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		
		if(fileName.matches("Reprint-CardStmt(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-CardStmtdd(reddnn)";    		
    	} 
    	else if(fileName.matches("Reprint-AEB(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-AEBdd(reddnn_AEB)";    		
    	} 
    	else if(fileName.matches("Reprint-IPL355(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-IPL355dd(reddnn_I3)";    		
    	} 
    	else if(fileName.matches("Reprint-IN(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-INdd(reddnn_IN)";    		
    	}			
    	else if(fileName.matches("Reprint-WC(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-WCdd(reddnn_WC)";    		
    	}
    	else if(fileName.matches("Reprint-MMS(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-MMSdd(rmddnn)";    		
    	}
    	else if(fileName.matches("Reprint-MMS-5in1(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-MMS-5in1dd(reddnn)";    		
    	}
    	else if(fileName.matches("Reprint-SN(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-SNdd(rnddnn)";    		
    	}			
    	else if(fileName.matches("Reprint-EP(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-EPdd(rpddnn)";    		
    	}else if(fileName.matches("Reprint-CardStmtDX(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
    		filePattern = "Reprint-CardStmtDXdd(reddnn_dx)";    		
    	}
		
		switch(fileLength) { 
	    case 8: 
	    	//XXXXXdd(DDA0106L)
	    	if(fileName.matches(".....(0[1-9]|1[0-9]|2[0-9]|3[0-1])."))  {
	    		filePattern = "XXXXXddL";
	    		filePattern = fileName.substring(0,5) + "dd" + fileName.substring(7,8);		    		
	    	}     		
	    	break; 			
	    case 7: 
	    	//XXXXXdd(LET0006, DDA0106)
	    	if(fileName.matches(".....(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXXXXdd";
	    		filePattern = fileName.substring(0,5) + "dd" ;		    		
	    	}     		
	    	break; 
	    case 6: 
	    	//XXmmdd(SD0606, SR0606,FN0414)
	    	if(fileName.matches("((S[D|R])|(FN))(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {
	    		filePattern = "XXmmdd";
	    		filePattern = fileName.substring(0,2) + "mmdd" ;		    		
	    	} 
	    	//XXXXdd(L10105, L80829) 額外處理  Thor modified
	    	else if(fileName.matches("(L101|L808)[0-9][1-9]"))  {
	    		filePattern = "XXXXdd";
	    		filePattern = fileName.substring(0,4) + "dd" ;
		    }			    	
	    	//XXddnn(SC0601, CE0601, IN0601, WC0601, IP0601,)
	    	else if(fileName.matches("..(0[1-9]|1[0-9]|2[0-9]|3[0-1])[0-9][1-9]"))  {
		    		filePattern = "XXddnn";
		    		filePattern = fileName.substring(0,2) + "ddnn" ;		
		    		//logFileSEQ = "0" + fileName.substring(4, 6);		    		
		    }
	    	//XXXXdd(NLPS06, BKCD06,MMMA05)
	    	else if(fileName.matches("....(0[1-9]|1[0-9]|2[0-9]|3[0-1])"))  {    		
		    		filePattern = "XXXXdd";
		    		filePattern = fileName.substring(0,4) + "dd" ;				    		
		    }		    	
	    	break; 	
	    case 5: 
	    	//XXXXX(LET00)
	    	if(fileName.matches("....."))  {
	    		filePattern = "XXXXX";
	    		filePattern = fileName.substring(0,5)  ;		    		
	    	}    	
	    	break; 		    	
	    default:  {
	    	   log.warn("No Matcing :" + formater.toString());
	       }
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
