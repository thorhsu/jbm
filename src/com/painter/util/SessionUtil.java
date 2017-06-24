package com.painter.util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.mlw.vlh.ValueList;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import com.salmat.jbm.hibernate.*;


/**
 * User: Erik Date: 2005-4-13 Time: 10:06:35
 */
public class SessionUtil {
    private static Logger log = Logger.getLogger(SessionUtil.class);

    public static final String ATTR_LOCALE = "usr_locale";

    public static final String ATTR_USER = "usr_current_user";

    public static final String ATTR_WE = "usr_work_env";
        
    private static String language = "TW"; //TW/EN/CN 對應至DB Value 欄位
    
    //private static Employee employee = null; //TW/EN/CN 對應至DB Value 欄位

    public static void setLocale(HttpServletRequest hreq, String locale) {
        setLocale(hreq.getSession(), locale);
    }

    public static void setLocale(HttpSession session, String locale) {
        if (null != session) {
            session.setAttribute(ATTR_LOCALE, locale);
        }
    }

    public static String getLocale(HttpServletRequest hreq) {
        return getLocale(hreq.getSession());
    }

    /**
     * get the user'StatusMap preferred language, it is used to customized the
     * UI whic the user will see.
     * 
     * @param session
     * @return
     */
    public static String getLocale(HttpSession session) {
        if (null == session) {
            return null;
        }
        return (String) session.getAttribute(ATTR_LOCALE);
    }

    public static Employee getAccount(HttpSession session) {
        if (null == session) {
            return null;
        }
        return (Employee) session.getAttribute("employee");
    }
    
    public static Employee getAccount(HttpServletRequest hreq) {
        return getAccount(hreq.getSession());
    } 
    
    

    
    public static void setAccount(HttpSession session, Employee account) {
        if (null != session) {
            session.setAttribute("employee", account);
            //employee = account;
        }
    }
    

    

    public static boolean isLogined(HttpServletRequest hreq) {
    	boolean ret = true;

    	Employee account = (Employee) hreq.getSession().getAttribute("employee");

    	
    	if (null == account) {
    		String ret_url = hreq.getRequestURI() +"?" + hreq.getQueryString();
    		hreq.getSession().setAttribute("RETURN_URL", ret_url);	
    		return false;
    	}
    	return ret;
    }

	public static String getLanguage() {
		return language;
	}

	public static void setLanguage(String language) {
		SessionUtil.language = language;
	}

    /**
     * Method 取得郵資單的上一筆 custNo , statementNo
     * @param custNo
     * @param statementNo
     * @return custNo , statementNo 組合的字串 custNo=TC&statementNo=48
     */
	
    public static String getPrevLgFormRecord(HttpServletRequest hreq, String custNo,Integer statementNo) {
    	String ret="";
        if (null != hreq) {
        	List lgformList = (List)hreq.getSession().getAttribute("lgformList");
            for(int i=0;i<lgformList.size();i++) {
            	Lgform lgform = (Lgform)lgformList.get(i);
            	if (lgform.getIdfCustNo().equalsIgnoreCase(custNo) && lgform.getStatementNo().compareTo(statementNo)==0 && i>0) {
            		Lgform retlgform = (Lgform)lgformList.get(i-1);
            		return "custNo="+retlgform.getIdfCustNo()+"&statementNo=" + retlgform.getStatementNo();
            	}
            	else if (lgform.getIdfCustNo().equalsIgnoreCase(custNo) && lgform.getStatementNo().compareTo(statementNo)==0 && i==0)
            		return "custNo="+lgform.getIdfCustNo()+"&statementNo=" + lgform.getStatementNo();
            }
        }
        return null;

    }
    
    /**
     * Method 取得郵資單的下一筆 custNo , statementNo
     * @param custNo
     * @param statementNo
     * @return custNo , statementNo 組合的字串 custNo=TC&statementNo=48
     */    
    public static String getNextLGFormRecord(HttpServletRequest hreq, String custNo,Integer statementNo) {
        if (null != hreq) {
        	List lgformList = (List)hreq.getSession().getAttribute("lgformList");
            for(int i=0;i<lgformList.size();i++) {
            	Lgform lgform = (Lgform)lgformList.get(i);
            	if (lgform.getIdfCustNo().equalsIgnoreCase(custNo) && lgform.getStatementNo().compareTo(statementNo)==0 && i<lgformList.size()-1) {
            		Lgform retlgform = (Lgform)lgformList.get(i+1);
            		return "custNo="+retlgform.getIdfCustNo()+"&statementNo=" + retlgform.getStatementNo();
            	}
            	else if (lgform.getIdfCustNo().equalsIgnoreCase(custNo) && lgform.getStatementNo().compareTo(statementNo)==0 && i==lgformList.size()-1)
            		return "custNo="+lgform.getIdfCustNo()+"&statementNo=" + lgform.getStatementNo();
            }
        }
        return null;

    }
    
    /**
     * Method 取得郵資單地N頁 custNo , statementNo
     * @param custNo
     * @param statementNo
     * @return custNo , statementNo 組合的字串 custNo=TC&statementNo=48
     */    
    public static String getNextLGFormPageInfo(HttpServletRequest hreq, String custNo,Integer statementNo) {
        if (null != hreq) {
        	List lgformList = (List)hreq.getSession().getAttribute("lgformList");
        	if(lgformList != null){
               for(int i=0 ; i < lgformList.size() ; i++) {
            	   Lgform lgform = (Lgform)lgformList.get(i);
            	   if (lgform.getIdfCustNo().equalsIgnoreCase(custNo) && lgform.getStatementNo().compareTo(statementNo)==0 ) {
            		   return i+1 + "/" + lgformList.size();
            	   }

               }
        	}else{
        		return "no_lgformList";
        	}
        }
        return null;

    }    
    
    public static String getPrevRecord(HttpServletRequest hreq, String currentRecord,String fieldName) {
    	String ret="";
        if (null != hreq) {
        	ValueList dataList = (ValueList)hreq.getSession().getAttribute("dataList");
            List navigationList = dataList.getList();
            for(int i=0;i<navigationList.size();i++) {
            	try {
            		String retRecord="";
            		if (! fieldName.equalsIgnoreCase("id"))
            			retRecord= (String)PropertyUtils.getProperty(navigationList.get(i),fieldName);
            		else 
            			retRecord = PropertyUtils.getProperty(navigationList.get(i),fieldName).toString();
            		
					if (retRecord.equalsIgnoreCase(currentRecord) && i >0)
						return (String)PropertyUtils.getProperty(navigationList.get(i-1),fieldName).toString();
					else if (retRecord.equalsIgnoreCase(currentRecord) && i ==0)
						return currentRecord;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        
        return null;

    }

    public static String getNextRecord(HttpServletRequest hreq, String currentRecord,String fieldName) {
    	String ret="";
        if (null != hreq) {
        	ValueList dataList = (ValueList)hreq.getSession().getAttribute("dataList");
            List navigationList = dataList.getList();
            for(int i=0;i<navigationList.size();i++) {
            	try {
            		String retRecord="";
            		if (! fieldName.equalsIgnoreCase("id"))
            			retRecord= (String)PropertyUtils.getProperty(navigationList.get(i),fieldName);
            		else 
            			retRecord = PropertyUtils.getProperty(navigationList.get(i),fieldName).toString();
            		
					if (retRecord.equalsIgnoreCase(currentRecord) && i <navigationList.size()-1)
						return (String)PropertyUtils.getProperty(navigationList.get(i+1),fieldName).toString();
					else if (retRecord.equalsIgnoreCase(currentRecord) && i == navigationList.size()-1)
						return currentRecord;
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
            
        }
        
        return null;

    }
    
    /**
     * Method getPageInfo
     * @param hreq
     * @param currentRecord
     * @param fieldName
     * @return N/M 筆
     */    
    public static String getPageInfo(HttpServletRequest hreq, String currentRecord,String fieldName) {
        if (null != hreq) {
        	ValueList dataList = (ValueList)hreq.getSession().getAttribute("dataList");
            List navigationList = dataList.getList();
            for(int i=0;i<navigationList.size();i++) {
            	try {            	
	        		String retRecord="";
	        		if (! fieldName.equalsIgnoreCase("id"))
	        			retRecord= (String)PropertyUtils.getProperty(navigationList.get(i),fieldName);
	        		else 
	        			retRecord = PropertyUtils.getProperty(navigationList.get(i),fieldName).toString();
	        		
	            	if (retRecord.equalsIgnoreCase(currentRecord) ) {
	            		return i+1 + "/"+navigationList.size();
	            	}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}            	

            }
        }
        return null;

    }      

}
