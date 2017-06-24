package com.salmat.jbm.service;

import com.salmat.jbm.bean.LogisticFormater;
import com.salmat.jbm.logisticParsingService.LogisticParsingObj;

public interface LogisticParsingService {		
	public LogisticParsingObj parseFilePattern(LogisticFormater formater);
}
