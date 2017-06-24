package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class ABParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(ABParsingService.class);

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;

		Integer fileLength = fileName.length();
		String filePattern = null;
		switch (fileLength) {
		case 6:
			// XXyymm (AA1106,AE1106,AM1106,AN1106,AR1106,PB1106,TD1106)
			if (fileName.matches("..[0-9][0-9](0[1-9]|1[0-2])")) {
				filePattern = "XXyymm";
				filePattern = fileName.substring(0, 2) + "yymm";
			} else {
				filePattern = "Error";
				log.warn("No Matcing :" + formater.toString());
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
