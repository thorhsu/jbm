package com.salmat.jbm.service;

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.salmat.jbm.hibernate.*;
import java.lang.String;


public class AcctInvoiceService extends AcctInvoiceDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(AcctInvoiceService.class);
    
    /* Constants */
    private static final AcctInvoiceService instance = new AcctInvoiceService();
    //private static CustomerDAO CustomerDao;

    
    private AcctInvoiceService() {
    	//CustomerDao = new CustomerDAO();

    }
    
	public static AcctInvoiceService getInstance() {
        return instance;
    }


	/*
	 * 根據  debitNoteNo 及 cost_center 找出 發票號碼
	 */
	public String findByDebitNoCostCenter(String debitNoteNo, String cost_center) {
		String invoiceNo="";
		try {		
	    	String queryString = " select {i.*}  "
	    		+ " from dbo.acct_invoice i "
	    		+ " where i.idf_debit_no= ? and i.cost_center=? " ;

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("i", AcctInvoice.class);
		    sqlQuery.setParameter(0, debitNoteNo);
		    sqlQuery.setParameter(1, cost_center);

			List retList = sqlQuery.list();
			if (null!= retList && retList.size() >0) {
				AcctInvoice acctInvoice = (AcctInvoice)retList.get(0);
				invoiceNo = acctInvoice.getInvoiceno();
			}
			return invoiceNo;

		} catch (RuntimeException re) {
			log.error("", re);
			return "";
		}			
		
	}


	/*
	 * 根據  debitNoteNo 找出 發票清單
	 */	
	public List findByDebitNote(AcctDno acctDno) {
		try {		
			/*
	    	String queryString = " select ai.id, s2.cost_center costCenter,  cast(ROUND(sum( s2.total ),0) as float )  expense , ai.invoiceno, ai.dept, cast(ai.tax as float ) tax, ai.invoicetyp  "
	    		+ " from dbo.acct_sum2 s2 "
	    		+ " left outer join  dbo.acct_invoice ai on s2.idf_debit_no = ai.idf_debit_no and s2.cost_center = ai.cost_center " 
	    		
	    		+ " where s2.idf_debit_no = ? " 
	    		+ " group by ai.id, s2.cost_center,  ai.invoiceno, ai.dept, ai.tax, ai.invoicetyp " 
	    		+ " union " 
	    		+ " select distinct  ai.id, ai.cost_center costCenter, cast(ai.expense as float )  expense  , ai.invoiceno, ai.dept, cast(ai.tax as float ) tax, ai.invoicetyp  " 
	    		+ " from dbo.acct_invoice ai " 	    	
	    		
	    		+ " where" 	    
	    		+ " ai.idf_debit_no = ? " 	    
	    		+ " and ai.cost_center  not in (select distinct cost_center from dbo.acct_sum2 " 	    
	    		+ " where idf_debit_no = ? ) " 	;	    
	    	*/
	    	String queryString = " select ai.id, s2.cost_center costCenter,  cast(ROUND(sum( s2.total ),0) as float )  expense , ai.invoiceno, ai.dept, cast(ai.tax as float ) tax, ai.invoicetyp "  
    		 + " from dbo.acct_sum2 s2 " 
    		 + " left outer join  dbo.acct_invoice ai on s2.idf_debit_no = ai.idf_debit_no and s2.cost_center = ai.cost_center " 	    		
             + " where s2.idf_debit_no = ? and  s2.cost_center  not in (select distinct cost_center from dbo.acct_invoice ai "
    		 + " where idf_debit_no = ? ) " 
    		 + " group by ai.id, s2.cost_center,  ai.invoiceno, ai.dept, ai.tax, ai.invoicetyp " 
    		 + " union " 
    		 + " select distinct  ai.id, ai.cost_center costCenter, cast(ai.expense as float )  expense  , ai.invoiceno, ai.dept, cast(ai.tax as float ) tax, ai.invoicetyp "  
    		 + " from dbo.acct_invoice ai " 	    		
    		 + " where ai.idf_debit_no = ? ";

	    	

	    	SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.setParameter(0, acctDno.getDebitNo());
		    sqlQuery.setParameter(1, acctDno.getDebitNo());
		    sqlQuery.setParameter(2, acctDno.getDebitNo());
   		    sqlQuery.setResultTransformer(Transformers.aliasToBean(AcctInvoice.class));		    

			List retList = sqlQuery.list();

			return retList;

		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}			
	}


}
