package com.salmat.jbm.logisticParsingService;

import org.apache.log4j.Logger;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.service.LogisticParsingService;

public class ALParsingService implements LogisticParsingService {
	private final static Logger log = Logger.getLogger(ALParsingService.class);

	@Override
	public LogisticParsingObj parseFilePattern(LogisticFormater formater) {
		String fileName = formater.getAfpFileName();
		if (fileName == null)
			return null;
		Integer fileLength = fileName.length();
		String filePattern = null;
		switch (fileLength) {
		case 8:
			// XXXX_ddn (PDNM_061,PSNM_061)
			if (fileName.matches("...._(0[1-9]|1[0-9]|2[0-9]|3[0-1])[1-9]")) {
				filePattern = "XXXX_ddn";
				filePattern = fileName.substring(0, 4) + "_ddn";
				// logFileSEQ = "00" + fileName.substring(7, 8);
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
