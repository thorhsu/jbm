/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;




import com.salmat.jbm.hibernate.Employee;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.SysLog;
import com.salmat.jbm.service.EmployeeService;
import com.painter.util.SessionUtil;
import com.painter.util.Util;

/** 
 * MyEclipse Struts
 * Creation date: 08-24-2010
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class LoginAction extends DispatchAction{
    /*
     * Generated Methods
     */
    public static final EmployeeService employeeService = EmployeeService.getInstance();
    private String loginLocation = "login_success";
    
    
    /** 
     * Method execute
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    @Override
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        
        return mapping.findForward("login");
    }
    
    
    
    public ActionForward changePassword(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
    	ActionMessages messages = new ActionMessages();
        DynaActionForm loginForm = (DynaActionForm) form;
        String userId = loginForm.getString("userId");
        String password = loginForm.getString("password");
        
    	HibernateSessionFactory.getSession().clear();
    	HibernateSessionFactory.getSession().getTransaction().begin();

		try {
			
	        Employee employee = SessionUtil.getAccount(request);
	        if (null == employee) {
	        	//request.setAttribute("message", "帳號或密碼錯誤");
	        	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg_account_password_error"));
	            this.saveMessages(request, messages);
	            return mapping.findForward("login");
	        }
	        
	
	    	String ret = employeeService.checkPasswordRule(request, employee, password);
	    	if (ret.equalsIgnoreCase("PASS")) {
		
	        	employee.setPassword(Util.hash(password));
	        	employee.setModifyDate(new Date());
	        	employee.setLoginErrorCount(0);
	        	employeeService.save(employee);
	        	HibernateSessionFactory.getSession().getTransaction().commit();
	        	
	            SysLog syslog = new SysLog();
	    		syslog.setLogType("EMP_PERSIST");
	    		syslog.setSubject("修改密碼");
	    		syslog.setIsException(false);
	    		Date today = new Date();
	    		syslog.setMessageBody(employee.getEmpNo() + ":" + employee.getUserId() + ":" + employee.getUserName() + "change pwd success");
	    	    syslog.setCreateDate(today);
	    	    HibernateSessionFactory.getSession().save(syslog);
	        	
	            HttpSession session = request.getSession(true);

	            if (session.isNew()) {
	                return mapping.findForward("homepage");
	            }
	            
	            session.invalidate();
	            session = null;
	        	request.setAttribute("message", "密碼更新成功, 請重新登入!!");
	            return mapping.findForward("login");
	    	} 	else {
	    		SysLog syslog = new SysLog();
	    		syslog.setLogType("EMP_PERSIST");
	    		syslog.setSubject("修改密碼");
	    		syslog.setIsException(false);
	    		Date today = new Date();
	    		syslog.setMessageBody(employee.getEmpNo() + ":" + employee.getUserId() + ":" + employee.getUserName() + "change pwd failed");
	    	    syslog.setCreateDate(today);
	    	    HibernateSessionFactory.getSession().save(syslog);
	            request.setAttribute("message", ret);
        		request.setAttribute("userId", userId);
                return mapping.findForward("change_password");	
	
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   



        return mapping.findForward("homepage");
    }
    
    public ActionForward submitCs(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {    	
    	return loginSubmit(mapping, form, request, response, true);
    }
    public ActionForward submit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
    	return loginSubmit(mapping, form, request, response, false);
    }
    
    public ActionForward loginSubmit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, boolean fromCs) {
    	if(fromCs)
    		this.loginLocation = "login_cs_success";
    	else
    		this.loginLocation = "login_success";
    	ActionMessages messages = new ActionMessages();    	
        DynaActionForm loginForm = (DynaActionForm) form;
        String userId = loginForm.getString("userId");
        String password = loginForm.getString("password");
        String language = loginForm.getString("language");
        
    	HibernateSessionFactory.getSession().clear();
    	HibernateSessionFactory.getSession().getTransaction().begin();

    	
        List accounts = employeeService.findByUserId(userId);
        if (null ==accounts || 0 == accounts.size()) {
        	//request.setAttribute("message", "帳號或密碼錯誤");
        	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg_account_password_error"));
            this.saveMessages(request, messages);
            this.loginLocation = "login_success";
            return mapping.findForward("login");
        }
        Employee employee = (Employee)(accounts.get(0));


        boolean isLogin = employeeService.verifyPassword(employee.getEmpNo(), password);
        
        //檢查是不是超過60天
        boolean overLimit = false;
     	Calendar sixtyDaysAgo = Calendar.getInstance();
     	sixtyDaysAgo.set(sixtyDaysAgo.get(Calendar.YEAR), sixtyDaysAgo.get(Calendar.MONTH) , sixtyDaysAgo.get(Calendar.DATE) - 60 );
    	Date empDate = employee.getModifyDate();
    	if(empDate == null)
    		empDate = employee.getCreateDate();    	
    	if(empDate == null)
    		overLimit = true;
    	else if(empDate.compareTo(sixtyDaysAgo.getTime()) <= 0)
    		overLimit = true;
    	else 
    		overLimit = false;
    	
        if (isLogin) {
        	//因應Policy要求超過5週未登入會將ID disable--------------------------------------------------------------        	
         	Calendar fiveweekAgo = Calendar.getInstance();
         	fiveweekAgo.set(fiveweekAgo.get(Calendar.YEAR), fiveweekAgo.get(Calendar.MONTH) , fiveweekAgo.get(Calendar.DATE) - 35 );
         	Date empLogDate = (employee.getLatestLoginDate() == null ? new Date() : employee.getLatestLoginDate());
            if(empLogDate.compareTo(fiveweekAgo.getTime()) < 0)  {
        		//request.setAttribute("message", "帳號被停權，請重新申請");
                this.saveMessages(request, messages);
                messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg_account_expired"));
                employee.setEnabled(false);
                employeeService.save(employee);
                this.saveMessages(request, messages);
                this.loginLocation = "login_success";
                return mapping.findForward("login");
            }

        	
        	//檢查是否是第一次登入 password == NEWUSER
        	if (password.equalsIgnoreCase("NEWUSER")) {
        		request.setAttribute("userId", userId);
        		request.setAttribute("message", "首次登入, 密碼變更");
        		SessionUtil.setAccount(request.getSession(), employee);
        		this.loginLocation = "login_success";
                return mapping.findForward("change_password");
        	}else if(overLimit){
        		request.setAttribute("userId", userId);
        		request.setAttribute("message", "上次密碼變更時間:"+ employee.getModifyDate() +  "距離上次密碼變更超過60天, 密碼變更");
        		SessionUtil.setAccount(request.getSession(), employee);
        		this.loginLocation = "login_success";
                return mapping.findForward("change_password");
        	}
        	        	
        	
        	//只要登入成功, LoginErrorCount =0;
        	employee.setLoginErrorCount(0);
    		Calendar calendar = Calendar.getInstance();
    		Date now = new Date();
    		calendar.setTime(now);
            
        	employee.setLatestLoginDate(calendar.getTime());
        	employeeService.save(employee);
        	HibernateSessionFactory.getSession().getTransaction().commit();
        	if (!employee.getEnabled()) {
                request.setAttribute("message", "帳號停權");
                this.loginLocation = "login_success";
                return mapping.findForward("login");            		
        	}
            SessionUtil.setAccount(request.getSession(), employee);
            SessionUtil.setLanguage(language);
            
            if (language.equalsIgnoreCase("TW")) {
            	request.getSession().setAttribute("org.apache.struts.action.LOCALE", new java.util.Locale("zh"));
            	setLocale(request,new java.util.Locale("zh","TW"));
            }
            else if (language.equalsIgnoreCase("EN")) {
            	request.getSession().setAttribute("org.apache.struts.action.LOCALE", new java.util.Locale("en"));
            	setLocale(request,new java.util.Locale("en","US"));
            }
            else if (language.equalsIgnoreCase("CN"))
            	request.getSession().setAttribute("org.apache.struts.action.LOCALE", Locale.CHINA);
            
            
        } else {

        	if (null!= employee.getLoginErrorCount())
        		employee.setLoginErrorCount(employee.getLoginErrorCount() + 1);
        	else
        		employee.setLoginErrorCount(1);
        	
        	if (employee.getLoginErrorCount().compareTo(5) >=0) {
        		employee.setEnabled(false);
        		//request.setAttribute("message", "連續五次登入錯誤, 帳號鎖住" );   
            	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg_account_lock"));
                this.saveMessages(request, messages);   
        	}else {
        		//request.setAttribute("message", "帳號或密碼錯誤, 連續登錄錯誤 次數:" + employee.getLoginErrorCount());    
            	messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("msg_login_error_count"));
                this.saveMessages(request, messages);   
        	}
        	employeeService.save(employee);
            HibernateSessionFactory.getSession().getTransaction().commit();
            this.loginLocation = "login_success";
            return mapping.findForward("login");
        }
        
   
        return mapping.findForward(this.loginLocation);
    }
    
    public ActionForward logout(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws
            Exception {

        HttpSession session = request.getSession(true);

        if (session.isNew()) {
            return mapping.findForward("homepage");
        }

        session.invalidate();
        session = null;


        return mapping.findForward("homepage");
    }
    public ActionForward tocsform(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws
            Exception {
        
        return mapping.findForward("login_cs_success");
    }
}