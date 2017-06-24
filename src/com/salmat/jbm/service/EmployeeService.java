package com.salmat.jbm.service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.*;
import com.painter.util.EnCryptUtils;
import com.painter.util.Global;
import com.painter.util.MailUtil;
import com.painter.util.SessionUtil;
import com.painter.util.Util;



import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.String;
import java.net.URLEncoder;


public class EmployeeService extends EmployeeDAO {
    
    /**
     * Log4j instance.
     */
    private final static Logger log = Logger.getLogger(EmployeeService.class);
    
    /* Constants */
    private static final EmployeeService instance = new EmployeeService();
    //private static EmployeeDAO employeeDao;

    
    private EmployeeService() {
    	//employeeDao = new EmployeeDAO();

    }
    
	public static EmployeeService getInstance() {
        return instance;
    }
    
	/**
	 * 使用者帳號是否註冊
	 * @param Employee Object
	 * @return boolean 
	 */	    
    public boolean isRegister(Employee employee) {
        boolean isRegister = false;
        Employee _employee = super.findById(employee.getEmpNo());
        if (null != employee) {
            isRegister = true;
        }
        return isRegister;
    }
    
    /**
	 * 取得enable的it部門人員 
	 * @param accountNo 帳號編號
	 * @param password  密碼
	 * @return boolean 
	 */    
    public List<Employee> getAllItEmp() {
         try {
        	 return HibernateSessionFactory.getSession().createQuery("from Employee e where e.enabled = true and e.code.id = 46").list();
        	 
         } catch (Exception e) {
             log.error("verifyPassword catch exception", e);
                e.printStackTrace();
         }
         return null;
    
    }
    
    /**
	 * 取得enable的Sales人員 
	 * @param accountNo 帳號編號
	 * @param password  密碼
	 * @return boolean 
	 */    
    public List<Employee> getAllCss() {
         try {
        	 return HibernateSessionFactory.getSession().createQuery("from Employee e where e.enabled = true and e.code.id = 52").list();
        	 
         } catch (Exception e) {
             log.error("verifyPassword catch exception", e);
                e.printStackTrace();
         }
         return null;
    }
    
    /**
	 * 取得enable的Account人員 
	 * @param accountNo 帳號編號
	 * @param password  密碼
	 * @return boolean 
	 */    
    public List<Employee> getAllAccounts() {
         try {
        	 return HibernateSessionFactory.getSession().createQuery("from Employee e where e.enabled = true and e.code.id = 41").list();
        	 
         } catch (Exception e) {
             log.error("verifyPassword catch exception", e);
                e.printStackTrace();
         }
         return null;
    
    }

	/**
	 * 帳號, 密碼檢查 
	 * @param accountNo 帳號編號
	 * @param password  密碼
	 * @return boolean 
	 */    
    public boolean verifyPassword(String employeeId, String password) {
        boolean isVerify = false;
            try {
            	Employee _employee = super.findById(employeeId);
                isVerify = _employee.getPassword().equals(Util.hash(password));
            } catch (Exception e) {
                log.error("verifyPassword catch exception", e);
                e.printStackTrace();
            }
            return isVerify;
    }

	/**
	 * 取得為  LP/MP 角色的員工清單 
	 * @param accountNo 帳號編號
	 * @param password  密碼
	 * @return boolean 
	 */ 
	public List getLPMPEmployees () {
		try {
	    	String queryString = "select  {e.*} " 
	    		+ " from Employee e" 
	        	+ " where e.role2c = 1 or  e.role1c = 1 order by user_id " ;
	    	
		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("e", Employee.class);
	
	
	        List retList = sqlQuery.list();
	        return retList;
	
	        
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}
	
	public List<Employee> getSalesEmployees () {
		try {
	    	String queryString = "select  {e.*} " 
	    		+ " from Employee e" 
	        	+ " where e.CODE_DEPT = 52 order by user_id " ;
	    	
		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("e", Employee.class);
	
	
	        List<Employee> retList = sqlQuery.list();
	        return retList;
	
	        
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	/**
	 * 取得為  LG 角色的員工清單 
	 * @param accountNo 帳號編號
	 * @param password  密碼
	 * @return boolean 
	 */ 
	public List getLGEmployees () {
		try {
	    	String queryString = "select  {e.*} " 
	    		+ " from Employee e" 
	        	+ " where e.role2c = 1 or  e.role1c = 1 order by user_id " ;
	    	
		    SQLQuery sqlQuery = (SQLQuery) HibernateSessionFactory.getSession().createSQLQuery(queryString);
		    sqlQuery.addEntity("e", Employee.class);
	
	
	        List retList = sqlQuery.list();
	        return retList;
	
	        
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}	
	
	
	/**
	 * 取得員工狀態 = ebabled 的員工清單 
	 */ 
	public List getActiveEmployees () {
		try {
	    	String queryString = "" 
	    		+ " from Employee " 
	        	+ " where enabled = true and userId is not null order by userId  " ;
	    	Query sqlQuery = HibernateSessionFactory.getSession().createQuery(queryString);
		    List retList = sqlQuery.list();
	        return retList;
	
	        
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	/**
	 * password 規則檢查
	 */ 
	public String checkPasswordRule(HttpServletRequest request, Employee employee, String newPassword) {
		//1. 長度至少8碼
		//2. 新密碼不得跟前6次密碼相同
		//3.  密碼組合必須包含以下3種規則以上
		//    . 大寫英文字母(A~Z)
		//    . 小寫英文字母(a~z)
		//    . 數字(0~9)
		//    . 符號( !@#$%^&*() )

		String ret="PASS";
		
		//若是管理員, 重設PASSWD ==NEWUSER, 可以跳過 password rule check 
		Employee myAccount = SessionUtil.getAccount(request.getSession());
		if (newPassword.equalsIgnoreCase("NEWUSER") && myAccount.getRole5s())
			return ret;
		
		if (newPassword.length()<8) {
			ret="長度至少8碼";
			return ret;
		}
		String ATOZ="ABCDEFGHIJKLMNOPQRISTUVWZXY";
		String aTOz="abcdefghijklmnopqristuvwxyz";
		String zeroTO9="0123456789";
		String symbol="!@#$%^&*()";
		Integer passCheck=0;
		for (int i=0;i<ATOZ.length();i++) {
			if (newPassword.indexOf(ATOZ.substring(i, i+1)) >= 0) {
				passCheck = passCheck + 1;
				break;
			}
		}
		for (int i=0;i<aTOz.length();i++) {
			if (newPassword.indexOf(aTOz.substring(i, i+1)) >= 0) {
				passCheck = passCheck + 1;
				break;
			}
		}
		for (int i=0;i<zeroTO9.length();i++) {
			if (newPassword.indexOf(zeroTO9.substring(i, i+1)) >= 0) {
				passCheck = passCheck + 1;
				break;
			}
		}
		for (int i=0;i<symbol.length();i++) {
			if (newPassword.indexOf(symbol.substring(i, i+1)) >= 0) {
				passCheck = passCheck + 1;
				break;
			}
		}		
		if (passCheck<3)  {
			ret="密碼組合必須包含以下3種規則以上, [ 大寫英文字母(A~Z) |  小寫英文字母(a~z) |  數字(0~9) |  符號( !@#$%^&*() ) ] ";
			return ret;
		}
		
		try {
			if (Util.hash(newPassword).equalsIgnoreCase(employee.getPassword())) 
				ret="新密碼不得跟前6次密碼相同";
			else if (Util.hash(newPassword).equalsIgnoreCase(employee.getPassword1())) 
				ret="新密碼不得跟前6次密碼相同";
			else if (Util.hash(newPassword).equalsIgnoreCase(employee.getPassword2())) 
				ret="新密碼不得跟前6次密碼相同";
			else if (Util.hash(newPassword).equalsIgnoreCase(employee.getPassword3())) 
				ret="新密碼不得跟前6次密碼相同";
			else if (Util.hash(newPassword).equalsIgnoreCase(employee.getPassword4())) 
				ret="新密碼不得跟前6次密碼相同";		
			else if (Util.hash(newPassword).equalsIgnoreCase(employee.getPassword5())) 
				ret="新密碼不得跟前6次密碼相同";				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret="未知的錯誤";	
		}
		return ret;
	}		
}
