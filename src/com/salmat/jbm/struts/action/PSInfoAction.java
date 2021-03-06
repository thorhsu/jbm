/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.action;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.ValueListRequestUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.Transaction;

import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.CodeService;
import com.salmat.jbm.service.CustomerService;
import com.salmat.jbm.service.EmployeeService;
import com.salmat.jbm.service.PSInfoService;
import com.salmat.jbm.service.ValueListService;
import com.salmat.jbm.struts.form.*;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.painter.util.Global;
import com.painter.util.SessionUtil;
import com.painter.util.Util;


/**
 * MyEclipse Struts Creation date: 02-23-2007
 * 
 * XDoclet definition:
 * 
 * @struts.action parameter="fid" validate="true"
 * @struts.action-forward name="init" path=".findUserAccount"
 */
public class PSInfoAction extends DispatchAction {
    /*
     * Generated Methods
     */
    private static final PSInfoService psinfoService = PSInfoService.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final CodeService codeService = CodeService.getInstance();
    private static ValueListService valueListService = ValueListService.getInstance();    
    private static Logger log = Logger.getLogger(PSInfoAction.class);

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

    //複製新增
    public ActionForward saveAsNewInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        PSInfoForm myForm = (PSInfoForm) form;
        String psNo = myForm.getPsNo();

        try {
        	Psinfo psinfo = psinfoService.findById(psNo);
            request.setAttribute("psinfo", psinfo);
            request.setAttribute("ACTION_MODE", "SAVE_AS_NEW");
        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("editInit");
    }    
    
    
    
    public ActionForward addInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        PSInfoForm myForm = (PSInfoForm) form;

        try {
        	Psinfo psinfo = new Psinfo();

            request.setAttribute("psinfo", psinfo);
            request.setAttribute("ACTION_MODE", "ADD");
        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("editInit");
    }    
    
    public ActionForward addSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        PSInfoForm myForm = (PSInfoForm) form;
        String psNo = myForm.getPsNo();

        try {
        	HibernateSessionFactory.getSession().clear();
        	HibernateSessionFactory.getSession().getTransaction().begin();

        	Psinfo psinfo = new Psinfo();
        	BeanUtils.copyProperties(psinfo,myForm);

        	if (null== myForm.getNextEffectiveDate() || (null!= myForm.getNextEffectiveDate() && myForm.getNextEffectiveDate().length() == 0 ))
        		psinfo.setNextEffectiveDate(null);
        	
        	
        	//設定客戶代碼
        	String custNo = myForm.getCustNo();
        	Customer customer = customerService.findById(custNo);
        	psinfo.setCustomer(customer);
        	
        	//設定摺疊方式
        	Integer codePsType = myForm.getCodePsType();
        	Code _codePsType = codeService.findById(codePsType);
        	psinfo.setCodeByCodePsType(_codePsType);

        	
        	//設定NEXT摺疊方式
        	Integer nextCodePsType = myForm.getNextCodePsType();
        	Code _codeNextPsType = codeService.findById(nextCodePsType);
        	psinfo.setCodeByNextCodePsType(_codeNextPsType);
        	

        	psinfoService.save(psinfo);
            HibernateSessionFactory.getSession().getTransaction().commit();
            request.setAttribute("message", "新增成功");
            return mapping.findForward("message");
        } catch (Exception e) {
            log.error("", e);
            HibernateSessionFactory.getSession().getTransaction().rollback();
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }

    }    
    
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
    	ActionMessages messages = new ActionMessages();
        PSInfoForm myForm = (PSInfoForm) form;

        try {
        	ValueList dataList = getValueList(request, myForm,"psinfoList");
            request.setAttribute("dataList", dataList);

            String idList = "";
        	for(Object obj : dataList.getList()){
            	idList += BeanUtils.getProperty(obj, "ps_no") + ",";
            }
            request.setAttribute("idList", idList);

            
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

    
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        PSInfoForm myForm = (PSInfoForm) form;
        String psNo=null;
        Psinfo psinfo = null;
        if (null != myForm.getPsNo()) {
        	psNo = myForm.getPsNo();
        	psinfo = psinfoService.findById(psNo);
        }

        try {
        	request.setAttribute("ACTION_MODE", "VIEW");
            request.setAttribute("psinfo", psinfo);
        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("editInit");
    }
    
    public ActionForward checkPKExist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        PSInfoForm myForm = (PSInfoForm) form;
        String psNo = myForm.getPsNo();

        try {
        	Psinfo psinfo = psinfoService.findById(psNo);
        	if (null ==psinfo)
        		request.setAttribute("result", "NON_EXIST");
        	else
        		request.setAttribute("result", "EXIST");
        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("jsonMessage");
    }       
        
    
    public ActionForward editInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        PSInfoForm myForm = (PSInfoForm) form;
        String psNo = myForm.getPsNo();
        
        String prevJobCodeNo = "";
        String nextJobCodeNo = "";
        String[] idList = request.getParameter("idList") == null ? null : request.getParameter("idList").split(",");
        int nowPage = 0;
        if(idList != null){
           for(int i = 0 ; i < idList.length ; i++){
                if(idList[i].equalsIgnoreCase(psNo)){
                	nowPage = i + 1;
                	if(i != 0)
                		prevJobCodeNo = idList[i - 1];
                	if(i != idList.length -1)
                		nextJobCodeNo = idList[i + 1];
                }
           }
        }
        request.setAttribute("pagesIndex", nowPage + "/" + idList.length);
        request.setAttribute("prevId", prevJobCodeNo);
        request.setAttribute("nextId", nextJobCodeNo);
        
        
        try{
        	HibernateSessionFactory.getSession().clear();
        	Psinfo psinfo = psinfoService.findById(psNo);
        	request.setAttribute("ACTION_MODE", "EDIT");
            request.setAttribute("psinfo", psinfo);
        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("editInit");
    }
    
    
    
    
    public ActionForward editSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        PSInfoForm myForm = (PSInfoForm) form;
        String psNo = myForm.getPsNo();

        try {
        	HibernateSessionFactory.getSession().clear();
        	HibernateSessionFactory.getSession().getTransaction().begin();

        	Psinfo psinfo = psinfoService.findById(psNo);
        	BeanUtils.copyProperties(psinfo,myForm);

        	if (null== myForm.getNextEffectiveDate() || (null!= myForm.getNextEffectiveDate() && myForm.getNextEffectiveDate().length() == 0 ))
        		psinfo.setNextEffectiveDate(null);
        	
        	
        	//設定客戶代碼
        	String custNo = myForm.getCustNo();
        	Customer customer = customerService.findById(custNo);
        	psinfo.setCustomer(customer);
        	
        	//設定摺疊方式
        	Integer codePsType = myForm.getCodePsType();
        	Code _codePsType = codeService.findById(codePsType);
        	psinfo.setCodeByCodePsType(_codePsType);

        	
        	//設定NEXT摺疊方式
        	Integer nextCodePsType = myForm.getNextCodePsType();
        	Code _codeNextPsType = codeService.findById(nextCodePsType);
        	psinfo.setCodeByNextCodePsType(_codeNextPsType);

        	psinfoService.save(psinfo);
            HibernateSessionFactory.getSession().getTransaction().commit();
            request.setAttribute("message", "修改成功");
            
          //導回編輯頁面     	
            request.setAttribute("ACTION_MODE", "EDIT");
            request.setAttribute("psinfo", psinfo);
            return mapping.findForward("editInit");
            
            //return mapping.findForward("message");
        } catch (Exception e) {
            log.error("", e);
            HibernateSessionFactory.getSession().getTransaction().rollback();
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
    }
    
    
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        PSInfoForm myForm = (PSInfoForm) form;
        String psNo = myForm.getPsNo();

        try {
        	HibernateSessionFactory.getSession().getTransaction().begin();
        	Psinfo psinfo = psinfoService.findById(psNo);

        	psinfoService.delete(psinfo);
            HibernateSessionFactory.getSession().getTransaction().commit();
            request.setAttribute("message", "刪除成功");  

        } catch (Exception e) {
            log.error("delete catch exception", e);
            HibernateSessionFactory.getSession().getTransaction().rollback();
        	request.setAttribute("message", "資料被引用, 不能刪除");  
			return mapping.findForward("message");
        }
        return mapping.findForward("message");
    }    
    
    
    private ValueList getValueList(HttpServletRequest request, PSInfoForm myForm, String entryKey) {
        Map param = new HashMap();

        String ps_no = myForm.getPsNo();
        if (ps_no != null )
        	param.put("ps_no", ps_no);
        String idf_cust_no = myForm.getCustNo();
        if (idf_cust_no != null )
        	param.put("idf_cust_no", idf_cust_no);
        
        ValueList valueList = valueListService.getValueList(request, entryKey, param);
        return valueList;
    }    
}