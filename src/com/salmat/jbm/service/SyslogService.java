package com.salmat.jbm.service;

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.salmat.jbm.hibernate.*;


public class SyslogService extends SysLogDAO {
	
	private static List<String> allLogTypes = null;
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(SyslogService.class);
    
    /* Constants */
    private static final SyslogService instance = new SyslogService();
    //private static CustomerDAO CustomerDao;

    
    private SyslogService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static SyslogService getInstance() {
        return instance;
    }
	
	public  List<String> getAllLogTypes(){
		Session session = null;
		if(allLogTypes == null){
		   try{
		      session = getSession();
		      session.clear();
		      Query query = session.createQuery("select distinct logType from SysLog order by logType asc");
		      List<String> retList = query.list();
		      allLogTypes = retList;
		   }catch(Exception e){
			  log.error("", e);
			  e.printStackTrace();
			  return null;
		   }finally{
			  if(session != null && session.isOpen())
				  session.close();
		   }
		}
		return allLogTypes;
	}
    



}
