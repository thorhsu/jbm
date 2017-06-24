package com.salmat.jbm.service;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.ZipcodeDetailDAO;

public class ZipcodeDetailService extends ZipcodeDetailDAO {

	private final static Logger log = Logger.getLogger(ZipcodeDetailService.class);
	private static final ZipcodeDetailService instance = new ZipcodeDetailService();
	
	private ZipcodeDetailService(){
		
	}
	
	public static ZipcodeDetailService getInstance() {
        return instance;
    }
	
	/**
	 * 根據zipcodeInfoNo刪除資料
	 * 
	 * @param zipcodeInfoNo
	 * @return 刪除筆數
	 */
	public int deleteZipcodeDetailByZipcodeInfoNo(Integer zipcodeInfoNo){
		try {
			String updateStr = "delete zipcode_detail where ZIPCODE_INFO_NO = ?";
			SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession()
					.createSQLQuery(updateStr);
			sqlQuery.setParameter(0, zipcodeInfoNo);
			return sqlQuery.executeUpdate();
		} catch (Exception e) {
			log.error("", e);
			return 0;
		}
	}
}
