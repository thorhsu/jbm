/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.salmat.jbm.struts.action;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import com.salmat.jbm.hibernate.*;
import com.salmat.jbm.service.AcctChargeItemService;
import com.salmat.jbm.service.AcctJobCodeChargeItemService;
import com.salmat.jbm.service.CodeService;
import com.salmat.jbm.service.CustomerService;
import com.salmat.jbm.service.EmployeeService;
import com.salmat.jbm.service.AcctCustomerContractService;
import com.salmat.jbm.service.JobCodeService;
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
public class AcctJobCodeChargeItemAction extends DispatchAction {
    /*
     * Generated Methods
     */
    private static final AcctJobCodeChargeItemService acctJobCodeChargeItemService = AcctJobCodeChargeItemService.getInstance();
    private static final AcctCustomerContractService acctCustomerContractService = AcctCustomerContractService.getInstance();    
    private static final JobCodeService jobcodeService = JobCodeService.getInstance();
    private static final AcctChargeItemService acctChargeItemService = AcctChargeItemService.getInstance();    
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final CodeService codeService = CodeService.getInstance();
    private static ValueListService valueListService = ValueListService.getInstance();    
    private static Logger log = Logger.getLogger(AcctJobCodeChargeItemAction.class);
    private List<JobCode> splitJobCodes = null;

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
        AcctJobCodeChargeItemForm  myForm = (AcctJobCodeChargeItemForm ) form;

        try {
        	ValueList dataList = getValueList(request, myForm,"acctJobCodeChargeItemList");
            request.setAttribute("dataList", dataList);
            
            String idList = "";
        	for(Object obj : dataList.getList()){
            	idList += BeanUtils.getProperty(obj, "job_code_no") + ",";
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

    private void setSplitJobCode(HttpServletRequest request, Customer customer){
    	Query query = HibernateSessionFactory.getSession().createQuery("from JobCode j  where j.codeByCodeJobCodeType.codeKey = 'SplitAccounts' and j.codeByCodeJobCodeType.codeTypeName = 'JOB_CODE_TYPE' and j.customer = ?");
    	query.setParameter(0, customer);
    	splitJobCodes = query.list();
    	request.setAttribute("spliteJobCodes", splitJobCodes);
    }


    
    public ActionForward editInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
    	
        ActionMessages messages = new ActionMessages();
        AcctJobCodeChargeItemForm  myForm = (AcctJobCodeChargeItemForm ) form;
        
        try{
        	HibernateSessionFactory.getSession().clear();        	        	
        	String jobCodeNo = myForm.getJobCodeNo();
        	
            String prevJobCodeNo = "";
            String nextJobCodeNo = "";
            String[] idList = request.getParameter("idList") == null ? null : request.getParameter("idList").split(",");
            int nowPage = 0;
            if(idList != null){
               for(int i = 0 ; i < idList.length ; i++){
                    if(idList[i].equalsIgnoreCase(jobCodeNo)){
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
        	
        	
        	JobCode jobcode = jobcodeService.findById(jobCodeNo); 
        	setSplitJobCode(request, jobcode.getCustomer());
        	String jobCodeNoList = "'" + jobCodeNo + "'";        	
        	
        	//配合多張工單維護方式, 轉成 jobCodeList  
         	List jobCodeList = new ArrayList();      
        	jobCodeList.add(jobcode);
        	
        	
        	String custNo="";
        	custNo = jobcode.getCustomer().getCustNo();
        	
        	//找出客戶 挑選工單的收費項目清單
        	List acctJobCodeChargeItemList = acctJobCodeChargeItemService.findByJobCodeNo(jobCodeNoList);        	

            
            //找出客戶 + 今天 合約上的收費項目跟價格
    		String NowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            List acctCustomerContractList = acctCustomerContractService.getAcctCustomerContractList( custNo, NowTime);
            if (null== acctCustomerContractList || acctCustomerContractList.size()==0 ) {
    			request.setAttribute("message", "該客戶尚未建立合約價格資訊 或未在合約期限內");
    			return mapping.findForward("message");            	
            }
            

            request.setAttribute("jobCodeList", jobCodeList);            
            request.setAttribute("acctJobCodeChargeItemList", acctJobCodeChargeItemList);
            request.setAttribute("acctCustomerContractList", acctCustomerContractList);
        	request.setAttribute("ACTION_MODE", "EDIT");

        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("editInit");
    }
    
    
    public ActionForward batchEditInit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
    	
        ActionMessages messages = new ActionMessages();
        AcctJobCodeChargeItemForm  myForm = (AcctJobCodeChargeItemForm ) form;
        
        try{

        	String jobCodeNos[] = myForm.getJobCodeNos();

        	
        	if (null== jobCodeNos ) {
    			request.setAttribute("message", "請選擇工單 ");
    			return mapping.findForward("message");           		
        	}
        	

        	// 組合 jobCodeNoList 清單 
        	String jobCodeNoList = "";
        	List jobCodeList = new ArrayList();
        	String custNo = null;
        	for (int i = 0 ; i < jobCodeNos.length ; i++) {
        		jobCodeNoList = jobCodeNoList + "'" + jobCodeNos[i] + "',";
        		JobCode _jobcode = jobcodeService.findById(jobCodeNos[i]);
        		if(i == 0)
        			setSplitJobCode(request, _jobcode.getCustomer());
        		if(custNo != null && !_jobcode.getCustomer().getCustNo().equals(custNo)){
        			request.setAttribute("message", "你選擇的工單代號屬於不同客戶，無法批次修改");
        			return mapping.findForward("message");            	
        		}
        		custNo = _jobcode.getCustomer().getCustNo();
        		jobCodeList.add(_jobcode);
        	}
        	
        	jobCodeNoList = jobCodeNoList.substring(0,jobCodeNoList.length()-1);
        	//找出客戶 挑選工單的收費項目清單
        	List acctJobCodeChargeItemList = acctJobCodeChargeItemService.findByJobCodeNo(jobCodeNoList);        	

            
            //找出客戶 + 今天 合約上的收費項目跟價格
    		String NowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            List acctCustomerContractList = acctCustomerContractService.getAcctCustomerContractList(custNo, NowTime);
            if (null== acctCustomerContractList || acctCustomerContractList.size()==0 ) {
    			request.setAttribute("message", "該客戶尚未建立合約價格資訊或未在合約期限中");
    			return mapping.findForward("message");            	
            }
            

            request.setAttribute("jobCodeList", jobCodeList);
            request.setAttribute("acctJobCodeChargeItemList", acctJobCodeChargeItemList);
            request.setAttribute("acctCustomerContractList", acctCustomerContractList);
        	request.setAttribute("ACTION_MODE", "BATCH_EDIT");

        } catch (Exception e) {
            log.error("", e);
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }
        return mapping.findForward("editInit");
    }    
    
    public ActionForward editSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {    	
        AcctJobCodeChargeItemForm  myForm = (AcctJobCodeChargeItemForm ) form;
        Integer id = myForm.getId();

        try {
        	HibernateSessionFactory.getSession().clear();
        	HibernateSessionFactory.getSession().getTransaction().begin();

        	String costCenter = myForm.getCostCenter();

        	//Double minimalChargePrice = myForm.getMinimalChargePrice();
        	String[] jobCodeNos = myForm.getJobCodeNos();
        	Integer[] chargeItemIds = myForm.getChargeItemIds();
        	Integer[] selectChargeItemIds= myForm.getSelectChargeItemIds();
        	Integer[] acctJobCodeChargeItemIds= myForm.getAcctJobCodeChargeItemIds();
        	Integer[] acctCustomerContractIds = myForm.getAcctCustomerContractIds();
        	Double[] adjustmentPercents = myForm.getAdjustmentPercents();      
        	Integer[] printTimes = myForm.getPrintTimes();   
        	String splitJobCodeNo1 = myForm.getSplitJobCodeNo1();
        	String splitJobCodeNo2 = myForm.getSplitJobCodeNo2();
        	String splitJobCodeNo3 = myForm.getSplitJobCodeNo3();
        	String splitJobCodeNo4 = myForm.getSplitJobCodeNo4();
        	Double splitJobCodePercent1 = myForm.getSplitJobCodePercent1();
        	Double splitJobCodePercent2 = myForm.getSplitJobCodePercent2();
        	Double splitJobCodePercent3 = myForm.getSplitJobCodePercent3();
        	Double splitJobCodePercent4 = myForm.getSplitJobCodePercent4();
        	String mainJobCodeNo = myForm.getMainJobCodeNo();
        	String mainJobCodeProgNm = myForm.getMainJobCodeProgNm();        	
        	
        	
        	//檢查 合併工單 是否存在
        	if (null!= mainJobCodeNo && mainJobCodeNo.length() >0) {
        		JobCode _jobCode = jobcodeService.findById(mainJobCodeNo);
        		if (null == _jobCode) {
	            	request.setAttribute("message", "合併工單不存在: " + mainJobCodeNo);
	            	return mapping.findForward("message");   
        		}
        	}
        	
        	//檢查 拆帳工單 是否存在
        	if (null!= splitJobCodeNo1 && splitJobCodeNo1.length() >0) {
        		JobCode _jobCode = jobcodeService.findById(splitJobCodeNo1);
        		if (null == _jobCode) {        		
	            	request.setAttribute("message", "拆帳工單不存在: " + splitJobCodeNo1);
	            	return mapping.findForward("message");     
        		}
        	}
        	//檢查 拆帳工單 是否存在
        	if (null!= splitJobCodeNo1 && splitJobCodeNo2.length() >0) {
        		JobCode _jobCode = jobcodeService.findById(splitJobCodeNo2);
        		if (null == _jobCode) {        		
	            	request.setAttribute("message", "拆帳工單不存在: " + splitJobCodeNo2);
	            	return mapping.findForward("message");     
        		}        		
        	}
        	//檢查 拆帳工單 是否存在
        	if (null!= splitJobCodeNo1 && splitJobCodeNo3.length() >0) {
        		JobCode _jobCode = jobcodeService.findById(splitJobCodeNo3);
        		if (null == _jobCode) {        		
	            	request.setAttribute("message", "拆帳工單不存在: " + splitJobCodeNo3);
	            	return mapping.findForward("message");     
        		}        		
        	}
        	//檢查 拆帳工單 是否存在
        	if (null!= splitJobCodeNo1 && splitJobCodeNo4.length() >0) {
        		JobCode _jobCode = jobcodeService.findById(splitJobCodeNo4);
        		if (null == _jobCode) {        		
	            	request.setAttribute("message", "拆帳工單不存在: " + splitJobCodeNo4);
	            	return mapping.findForward("message");     
        		}        		
        	}        	
        	
        	
        	//設定每張工單樣本上的 CostCenter/ MinimalChargePrice/SplitJobCodeNo1/SplitJobCodePercent1/mainJobCodeNo
        	for (int j=0 ; j < jobCodeNos.length ; j++)  {    
            	JobCode jobcode = jobcodeService.findById(jobCodeNos[j]);
            	if(j == 0)
            		setSplitJobCode(request, jobcode.getCustomer());
				jobcode.setCostCenter(costCenter);
				//jobcode.setMinimalChargePrice(minimalChargePrice);
				
				jobcode.setSplitJobCodeNo1(splitJobCodeNo1);
				jobcode.setSplitJobCodeNo2(splitJobCodeNo2);
				jobcode.setSplitJobCodeNo3(splitJobCodeNo3);
				jobcode.setSplitJobCodeNo4(splitJobCodeNo4);
				
				jobcode.setSplitJobCodePercent1(splitJobCodePercent1);
				jobcode.setSplitJobCodePercent2(splitJobCodePercent2);
				jobcode.setSplitJobCodePercent3(splitJobCodePercent3);
				jobcode.setSplitJobCodePercent4(splitJobCodePercent4);
				jobcode.setMainJobCodeNo(mainJobCodeNo);
				jobcode.setMainJobCodeProgNm(mainJobCodeProgNm);
				jobcode.setMinimalChargePrice(null); //清空 MinimalChargePrice, 再由勾選的ChargeItem 設定
				jobcodeService.save(jobcode);
        	}
        	
        	
        	//刪除原工單樣本的收費項目設定 
        	String jobCodeNoList="";
        	for (int i=0;i<jobCodeNos.length;i++) 
        		jobCodeNoList = jobCodeNoList + "'" + jobCodeNos[i] + "',";

        	jobCodeNoList = jobCodeNoList.substring(0,jobCodeNoList.length()-1);
        	Boolean ret = acctJobCodeChargeItemService.deleteByJobCodeNo(jobCodeNoList);
        	if (!ret) {
            	request.setAttribute("message", "刪除原工單樣本收費項目錯誤");
            	return mapping.findForward("message");        		
        	}

        	//minimalChargeItemId 最低收費的 chargeItem Id
        	Integer minimalChargeItemId = Integer.parseInt(Util.getString("accounting.minimalChargeItemId"));
        	
        	for (int i=0; i<chargeItemIds.length;i++) {
        		// 檢查 chargeItemId 勾選
        		Boolean isSelected = false;
        		if (null!= selectChargeItemIds ) {
	        		for (int j=0; j<selectChargeItemIds.length;j++) {
	        			if (chargeItemIds[i].compareTo(selectChargeItemIds[j]) ==0 )
	        				isSelected = true;
	        		}
        		}
        		
        		if ( isSelected ) {
        			//新增每張工單的 ChargeItem
                	for (int j=0;j<jobCodeNos.length;j++)  {
	                	JobCode jobcode = jobcodeService.findById(jobCodeNos[j]);
	                	
	        			AcctJobCodeChargeItem acctJobCodeChargeItem = new AcctJobCodeChargeItem();   	                	
	        			acctJobCodeChargeItem.setJobCode(jobcode);                	
	        			acctJobCodeChargeItem.setCustomer(jobcode.getCustomer());
	                	AcctChargeItem acctChargeItem = (AcctChargeItem)acctChargeItemService.findById(chargeItemIds[i]);
	        			acctJobCodeChargeItem.setAcctChargeItem(acctChargeItem);
	        			AcctCustomerContract acctCustomerContract = (AcctCustomerContract)acctCustomerContractService.findById(acctCustomerContractIds[i]);
	        			acctJobCodeChargeItem.setAcctCustomerContract(acctCustomerContract);
	        			if (null!= adjustmentPercents[i] )
	        				acctJobCodeChargeItem.setAdjustmentPercent(adjustmentPercents[i]);	      
	        			if (null!= printTimes[i] ) acctJobCodeChargeItem.setPrintTimes(printTimes[i]);
	                	acctJobCodeChargeItemService.save(acctJobCodeChargeItem);
	                	
	                	//若有勾選 minimalChargeItemId , 則將此工單 設定 setMinimalChargePrice 價格
	                	if (minimalChargeItemId.compareTo(acctChargeItem.getId()) ==0) {
	                		jobcode.setMinimalChargePrice(acctCustomerContract.getUnitPrice());
	                		jobcodeService.save(jobcode);
	                	} 
                	}
        		} 

        	}


            HibernateSessionFactory.getSession().getTransaction().commit();
            request.setAttribute("message", "修改成功");
            if ( jobCodeNos.length >1 ) {
            	request.setAttribute("message", "批次 修改成功");
            	return mapping.findForward("message");
            }
            
            // 修改後,  再轉到 編輯模式
        	List jobCodeList = new ArrayList();
        	String custNo="";
        	jobCodeNoList="";
        	for (int i=0;i<jobCodeNos.length;i++) {
        		jobCodeNoList = jobCodeNoList + "'" + jobCodeNos[i] + "',";
        		JobCode _jobcode = jobcodeService.findById(jobCodeNos[i]);
        		custNo = _jobcode.getCustomer().getCustNo();
        		jobCodeList.add(_jobcode);
        	}
        	jobCodeNoList = jobCodeNoList.substring(0,jobCodeNoList.length()-1);
        	//找出客戶 挑選工單的收費項目清單
        	List acctJobCodeChargeItemList = acctJobCodeChargeItemService.findByJobCodeNo(jobCodeNoList);        	

            
            //找出客戶 + 今天 合約上的收費項目跟價格
    		String NowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            List acctCustomerContractList = acctCustomerContractService.getAcctCustomerContractList( custNo, NowTime);
            if (null== acctCustomerContractList || acctCustomerContractList.size()==0 ) {
    			request.setAttribute("message", "該客戶 尚未建立合約價格資訊 或未在合約期限中");
    			return mapping.findForward("message");            	
            }
            

            request.setAttribute("jobCodeList", jobCodeList);            
            request.setAttribute("acctJobCodeChargeItemList", acctJobCodeChargeItemList);
            request.setAttribute("acctCustomerContractList", acctCustomerContractList);
        	request.setAttribute("ACTION_MODE", "EDIT");
        	
        	return mapping.findForward("editInit");
        } catch (Exception e) {
            log.error("", e);
            HibernateSessionFactory.getSession().getTransaction().rollback();
			request.setAttribute("message", "系統失敗 ");
			return mapping.findForward("message");
        }

    }
    

    
    private ValueList getValueList(HttpServletRequest request, AcctJobCodeChargeItemForm  myForm, String entryKey) {
        Map param = new HashMap();
        
        String idf_cust_no = myForm.getCustNo();
        if (idf_cust_no != null )
        	param.put("idf_cust_no", idf_cust_no);

        String job_code_no = myForm.getJobCodeNo();
        if (job_code_no != null )
        	param.put("job_code_no", job_code_no);        

        
        ValueList valueList = valueListService.getValueList(request, entryKey, param);
        return valueList;
    }    
}