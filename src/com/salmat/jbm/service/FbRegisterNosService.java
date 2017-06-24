package com.salmat.jbm.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.salmat.jbm.hibernate.*;


public class FbRegisterNosService extends FbRegisterNosDAO {
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(FbRegisterNosService.class);
    
    /* Constants */
    private static final FbRegisterNosService instance = new FbRegisterNosService();
    //private static CustomerDAO CustomerDao;

    
    private FbRegisterNosService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static FbRegisterNosService getInstance() {
        return instance;
    }
	
	public List<FbRegisterNos> getFromJobBagList(String jobBagList){
		Criteria criteria = getSession().createCriteria(FbRegisterNos.class);
		String[] jobBags = jobBagList.split(",");
		for(int i = 0 ; i < jobBags.length ; i++){
			jobBags[i] = jobBags[i].replaceAll("'", "").trim();
		}
		List<FbRegisterNos> retList= criteria.add(Restrictions.in("jobBagNo", jobBags)).addOrder(Order.asc("jobBagNo")).addOrder(Order.asc("cycleDate")).list();
		return retList;		
	}
	
}
