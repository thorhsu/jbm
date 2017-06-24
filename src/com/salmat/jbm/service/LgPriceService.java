package com.salmat.jbm.service;



import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.salmat.jbm.hibernate.*;


public class LgPriceService extends LgPriceDAO {
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(LgPriceService.class);
    
    /* Constants */
    private static final LgPriceService instance = new LgPriceService();
    //private static CustomerDAO CustomerDao;

    
    private LgPriceService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static LgPriceService getInstance() {
        return instance;
    }
	
	public static List<LgPrice> findCategoryAndType(int lgType, int lgMailCategory){
		Criteria criteria = LgPriceService.getInstance().getSession().createCriteria(LgPrice.class);
		criteria.add(Restrictions.eq("lgType", lgType)).add(Restrictions.eq("lgMailCategory", lgMailCategory));		
		return criteria.list();
		
	}
	
}
