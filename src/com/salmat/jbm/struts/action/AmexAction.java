/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.action;

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
import com.salmat.jbm.service.AmexService;
import com.salmat.jbm.service.CodeService;
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
public class AmexAction extends DispatchAction {
    /*
     * Generated Methods
     */
    private static final AmexService amexService = AmexService.getInstance();
    private static final CodeService codeService = CodeService.getInstance();
    private static ValueListService valueListService = ValueListService.getInstance();    
    private static Logger log = Logger.getLogger(AmexAction.class);

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

    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
    	ActionMessages messages = new ActionMessages();
        AmexForm myForm = (AmexForm) form;

        try {
        	ValueList dataList = getValueList(request, myForm,"amexList");
            request.setAttribute("dataList", dataList);

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
        AmexForm myForm = (AmexForm) form;
        Amex  amex = null;
    	HibernateSessionFactory.getSession().clear();        
        if (null != myForm.getId()) {
        	amex = amexService.findById(myForm.getId());
        }
    


        try {
        	request.setAttribute("ACTION_MODE", "VIEW");
            request.setAttribute("amex", amex);
        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("editInit");
    }
    
    

    
    public ActionForward editInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        AmexForm myForm = (AmexForm) form;
        Integer id = myForm.getId();
    	HibernateSessionFactory.getSession().clear();
        try {
        	HibernateSessionFactory.getSession().clear();
            Amex amex = amexService.findById(id);
            request.setAttribute("ACTION_MODE", "EDIT");
            request.setAttribute("amex", amex);
        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("editInit");
    }
    
    
    
    
    public ActionForward editSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        AmexForm myForm = (AmexForm) form;

        Integer id = myForm.getId();


        try {
        	HibernateSessionFactory.getSession().clear();
        	HibernateSessionFactory.getSession().getTransaction().begin();

            Amex amex = amexService.findById(id);
        	BeanUtils.copyProperties(amex,myForm);
        	if (null == amex.getSpecial() )
        		amex.setSpecial("");


            amexService.save(amex);
            HibernateSessionFactory.getSession().getTransaction().commit();
            request.setAttribute("message", "修改成功");
            return mapping.findForward("message");
        } catch (Exception e) {
            log.error("", e);
            HibernateSessionFactory.getSession().getTransaction().rollback();
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        //return mapping.findForward("viewAccount");
    }
    
    
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        AmexForm myForm = (AmexForm) form;
        Integer id = myForm.getId();


        try {
        	HibernateSessionFactory.getSession().getTransaction().begin();
            Amex amex = amexService.findById(id);

            amexService.delete(amex);
            HibernateSessionFactory.getSession().getTransaction().commit();
            request.setAttribute("message", "刪除成功");  

        } catch (Exception e) {
            log.error("delete catch exception", e);
            HibernateSessionFactory.getSession().getTransaction().rollback();
        	request.setAttribute("message", "帳號被引用, 不能刪除");  
			return mapping.findForward("message");
        }
        return mapping.findForward("message");
    }    
    
    
    //複製新增
    public ActionForward saveAsNewInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        ActionMessages messages = new ActionMessages();
        AmexForm myForm = (AmexForm) form;
        Integer id = myForm.getId();

        try {
            Amex amex = amexService.findById(id);

            request.setAttribute("amex", amex);
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

        try {
        	Amex amex = new Amex();

            request.setAttribute("amex", amex);
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
        AmexForm myForm = (AmexForm) form;



        try {
        	HibernateSessionFactory.getSession().clear();
        	HibernateSessionFactory.getSession().getTransaction().begin();

        	Amex amex = new Amex();
            
        	BeanUtils.copyProperties(amex,myForm);



            amexService.save(amex);
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
    
    private ValueList getValueList(HttpServletRequest request, AmexForm myForm, String entryKey) {
        Map param = new HashMap();
        Integer id = myForm.getId();
        if (id != null && id >0 )
        	param.put("id", id);
        
        String afp_name = myForm.getAfpName();
        if (null != afp_name &&  afp_name.length() >0)
        	param.put("afp_name", afp_name);
        
        String mapping_type = myForm.getMappingType();
        if (null != mapping_type &&  mapping_type.length() >0)
        	param.put("mapping_type", mapping_type);
        
        String country = myForm.getCountry();
        if (null != country &&  country.length() >0)
        	param.put("country", country);
        
        String job_code_no = myForm.getJobCodeNo();
        if (null != job_code_no &&  job_code_no.length() >0)
        	param.put("job_code_no", job_code_no);        
        
        
        ValueList valueList = valueListService.getValueList(request, entryKey, param);
        return valueList;
    }    
}