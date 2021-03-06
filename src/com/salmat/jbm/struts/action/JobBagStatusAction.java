/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.action;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.ValueListRequestUtil;
import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.Transaction;

import com.salmat.jbm.bean.LpinfoJSON;
import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.*;
import com.salmat.jbm.struts.form.*;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.painter.util.Global;
import com.painter.util.SessionUtil;
import com.painter.util.Util;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import java.util.*;



/**
 * MyEclipse Struts Creation date: 02-23-2007
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="fid" validate="true"
 * @struts.action-forward name="init" path=".findUserAccount"
 */
/**
 * @author painterlin
 *
 */
public class JobBagStatusAction extends DispatchAction {
    /*
     * Generated Methods
     */
	private static final LPInfoService lpinfoService = LPInfoService.getInstance();
	private static final MPInfoService mpinfoService = MPInfoService.getInstance();
	private static final PSInfoService psinfoService = PSInfoService.getInstance();
	private static final LGInfoService lginfoService = LGInfoService.getInstance();
	private static final ReturnInfoService returninfoService = ReturnInfoService.getInstance();
	private static final LCInfoService lcinfoService = LCInfoService.getInstance();
	private static final JobCodeService jobcodeService = JobCodeService.getInstance();
    private static final JobBagService jobbagService = JobBagService.getInstance();
	private static final JobBagSpliteService jobbagSpliteService = JobBagSpliteService.getInstance();    
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final CodeService codeService = CodeService.getInstance();
    private static ValueListService valueListService = ValueListService.getInstance();    
    private static Logger log = Logger.getLogger(JobBagStatusAction.class);

    /**
     * Method execute
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        return mapping.findForward("homepage");
    }


    /**
     * Method 工單狀態總表
     */    
    public ActionForward summary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
    	ActionMessages messages = new ActionMessages();
        JobBagForm myForm = (JobBagForm) form;

        try {
        	ValueList dataList = getValueList(request, myForm,"jobbagStatus_Summary");
            request.setAttribute("dataList", dataList);
            
            //將dataList 放到session 
            request.getSession().setAttribute("dataList", dataList);
            String backToListURL = request.getRequestURI() +"?" + request.getQueryString();
            //request.getSession().setAttribute("backToListURL", backToListURL);    
            request.setAttribute("backToListURL", URLEncoder.encode(backToListURL, "UTF-8"));

        } catch (Exception e) {
            log.error("", e);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    Global.MESSAGE_SYSTEMERROR_KEY));
            this.saveMessages(request, messages);
            return mapping.findForward(Global.FORWARD_INCLUDE_MESSAGE_KEY);
        }
        return mapping.findForward("summary");
    }

    /**
     * Method 工單狀態清單, 個別狀態
     */    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
    	ActionMessages messages = new ActionMessages();
        JobBagForm myForm = (JobBagForm) form;

        try {
        	ValueList dataList = getValueList(request, myForm,"jobbagList");
            request.setAttribute("dataList", dataList);
            
            //將dataList 放到session 
            request.getSession().setAttribute("dataList", dataList);
            String backToListURL = request.getRequestURI() +"?" + request.getQueryString();
            //request.getSession().setAttribute("backToListURL", backToListURL);             
            request.setAttribute("backToListURL", URLEncoder.encode(backToListURL, "UTF-8"));
        } catch (Exception e) {
            log.error("", e);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    Global.MESSAGE_SYSTEMERROR_KEY));
            this.saveMessages(request, messages);
            return mapping.findForward(Global.FORWARD_INCLUDE_MESSAGE_KEY);
        }
        return mapping.findForward("list");
    }
    

    

    
    private ValueList getValueList(HttpServletRequest request, JobBagForm myForm, String entryKey) {
        Map param = new HashMap();



        String[] jobBagStatuses = myForm.getJobBagStatuses();
        String job_bag_status=""; // INIT 當作預設值
        
        if (null == jobBagStatuses) {
        	job_bag_status="'COMPLETED_MP','NON_MP'";
        }
        else {
	        for (int i=0; i<jobBagStatuses.length; i++)  {
	        	if (i==0 ) 
	        		job_bag_status = "'" + jobBagStatuses[i] + "'";
	        	else
	        		job_bag_status = job_bag_status + ", '" + jobBagStatuses[i] + "'";
	        }
        }
        param.put("job_bag_status", job_bag_status);  


        
        String job_bag_no = myForm.getJobBagNo();
        if (null != job_bag_no && job_bag_no.length() >0  )
        	param.put("job_bag_no", job_bag_no);
        String idf_cust_no = myForm.getCustNo();
        if (null != idf_cust_no && idf_cust_no.length() >0   )
        	param.put("idf_cust_no", idf_cust_no);
        
        String cycle_date = myForm.getCycleDate_form();        
        if (null != cycle_date && cycle_date.length() >0   )        	
        	param.put("cycle_date", cycle_date);
        
        String receive_date = myForm.getReceiveDate_form();      
        if (null != receive_date && receive_date.length() >0   )        
        	param.put("receive_date", receive_date);        

        String hasDamage = myForm.getHasDamage_form();         
        if (null != hasDamage && hasDamage.equalsIgnoreCase("1"))
        	param.put("has_damage", " j.has_damage = 1 ");      
        if (null != hasDamage && hasDamage.equalsIgnoreCase("0"))
        	param.put("has_damage", " (j.has_damage is null or j.has_damage =0 ) ");            
        
        String isDamage = myForm.getIsDamage_form();         
        if (null != isDamage && isDamage.equalsIgnoreCase("1"))
        	param.put("is_damage", " j.is_damage = 1 ");      
        if (null != isDamage && isDamage.equalsIgnoreCase("0"))
        	param.put("is_damage", " (j.is_damage is null or j.is_damage =0 ) ");        
        
        
        String isDeleted = myForm.getIsDeleted_form();         
        if (null != isDeleted && isDeleted.equalsIgnoreCase("1"))
        	param.put("is_deleted", " j.is_deleted = 1 " );      
        if (null != isDeleted && isDeleted.equalsIgnoreCase("0"))
        	param.put("is_deleted", " (j.is_deleted is null or j.is_deleted =0 )  ");       
        if (null == isDeleted )
        	param.put("is_deleted", " (j.is_deleted is null or j.is_deleted =0 )  ");         
        

        String isConvertResult = myForm.getIsConvertResult_form();         
        if (null != isConvertResult && isConvertResult.equalsIgnoreCase("1"))
        	param.put("is_convert_result", " j.is_convert_result = 1 ");      
        if (null != isConvertResult && isConvertResult.equalsIgnoreCase("0"))
        	param.put("is_convert_result", " (j.is_convert_result is null or j.is_convert_result=0 )  ");  

        
        Integer code_lg_type = myForm.getCodeLgType();
        if (null != code_lg_type && code_lg_type >0  )
        	param.put("code_lg_type", code_lg_type);        
        
        
        Integer code_mail_category = myForm.getCodeMailCategory();
        if (null != code_mail_category && code_mail_category >0  )
        	param.put("code_mail_category",code_mail_category);        
        
        Integer code_mail_to_postoffice = myForm.getCodeMailToPostoffice();
        if (null != code_mail_to_postoffice && code_mail_to_postoffice >0  )
        	param.put("code_mail_to_postoffice", code_mail_to_postoffice);                

        
        ValueList valueList = valueListService.getValueList(request, entryKey, param);
        return valueList;
    }    
}