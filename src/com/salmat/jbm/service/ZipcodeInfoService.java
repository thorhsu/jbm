package com.salmat.jbm.service;

import com.salmat.jbm.hibernate.ZipcodeInfoDAO;

public class ZipcodeInfoService extends ZipcodeInfoDAO {
	
	private static final ZipcodeInfoService instance = new ZipcodeInfoService();
	
	private ZipcodeInfoService(){
		
	}
	
	public static ZipcodeInfoService getInstance() {
        return instance;
    }
	
	
}
