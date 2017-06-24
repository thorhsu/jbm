package com.salmat.jbm.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.JbmCounter;
import com.salmat.jbm.hibernate.JbmCounterDAO;

public class JbmCounterService extends JbmCounterDAO {
	
	private static final JbmCounterService instance = new JbmCounterService();
	private static Logger log = Logger.getLogger(JbmCounterService.class);
	
	private JbmCounterService(){
		
	}
	
	public static JbmCounterService getInstance() {
        return instance;
    }
	
	/**
	 * 取得序號
	 * 
	 * @param counterName 序號項目名稱
	 * @return
	 */
	public Integer generateCounter(String counterName){
		
		 JbmCounter counter = this.findByCounterName(counterName);
		 
		 Integer nextCounter = counter.getCurCounterNo() + 1;
 
		 return nextCounter;
	}
	
	/**
	 * 取得郵遞區號3+2碼用序號</br>
	 * 不同於一般序號為, 若上一個序號建立時間小於今日,則須reset
	 * 
	 * @param counterName 序號項目名稱
	 * @return 序號
	 * @throws Exception 若新序號超過99
	 */
	public Integer generateCounterZipcode(String counterName) throws Exception{
		
		// 取得現行Counter
		JbmCounter counter = this.findByCounterName(counterName);
		Integer nextCounter = counter.getCurCounterNo() + 1;
		HibernateSessionFactory.getSession().clear();
    	HibernateSessionFactory.getSession().getTransaction().begin();
    	
		// 若最後CREATE_DATE不等於SYSDATE,則將序號歸1
		if(!DateUtils.truncatedEquals(new Date(), counter.getCreateDate(), Calendar.DATE)){
			log.warn("last create date is not equal to sysdate, last create date = " + counter.getCreateDate() + "seqNo = " + counter.getCurCounterNo() 
					+ " & reset CurCounterNo");
			nextCounter = 1; // 將counter歸1
		}
		
		// 若序號大於99, 丟出Exception
		if(nextCounter > 99){
			HibernateSessionFactory.getSession().getTransaction().rollback();
			throw new Exception("counterName = " + counterName + "SeqNo is to limit!");
		}
		
		counter.setCurCounterNo(nextCounter); 
		counter.setCreateDate(new Date());
		
		this.save(counter); // 取完新的seqNo後, 更新Counter
		HibernateSessionFactory.getSession().getTransaction().commit();	
	
		return nextCounter;
	}
}
