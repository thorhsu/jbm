package com.salmat.jbm.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

import com.salmat.jbm.hibernate.*;


public class HistoryReportService extends HistoryReportDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(HistoryReportService.class);
    
    /* Constants */
    private static final HistoryReportService instance = new HistoryReportService();
    
    private HistoryReportService() {
    }
    
	public static HistoryReportService getInstance() {
        return instance;
    }
	
    /**
     * get HistoryReport List
     * return EffectiveLgInfo List
     */
	public List getHistoryReportList(String empNo) {

		try {
	    	String queryString = " select {hr.*} "
	    		+ " from history_report hr  "
	    		+ " where hr.emp_no=? " ;
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("hr", HistoryReport.class);
		    sqlQuery.setParameter(0, empNo);

			List retList = sqlQuery.list();
			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}	
	
	public Map<String, String> getEmpnoList() {

		try {
	    	String queryString = "select distinct hr.emp_no, em.USER_ID from history_report hr, employee em where hr.emp_no = em.EMP_NO" ;
	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);		    
			List<Object[]> retList = sqlQuery.list();
			Map<String, String> retMap = new TreeMap<String, String>();
			for(Object[] row: retList){
				retMap.put((String)row[1], (String)row[0]);
			}			
			return retMap;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}		

	}
	
	
}
