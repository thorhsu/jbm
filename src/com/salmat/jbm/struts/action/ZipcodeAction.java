package com.salmat.jbm.struts.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mlw.vlh.ValueList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.DispatchAction;

import com.painter.util.SessionUtil;
import com.painter.util.Util;
import com.salmat.jbm.hibernate.Employee;
import com.salmat.jbm.hibernate.HibernateSessionFactory;
import com.salmat.jbm.hibernate.ZipcodeDetail;
import com.salmat.jbm.hibernate.ZipcodeInfo;
import com.salmat.jbm.service.JbmCounterService;
import com.salmat.jbm.service.ValueListService;
import com.salmat.jbm.service.ZipcodeDetailService;
import com.salmat.jbm.service.ZipcodeInfoService;
import com.salmat.jbm.struts.form.ZipcodeForm;

/**
 * 郵遞區號3+2檔案產出Action
 * 
 * @author JamesTsai
 *
 */
public class ZipcodeAction extends DispatchAction {
	
	private static Logger log = Logger.getLogger(ZipcodeAction.class);
	private static ValueListService valueListService = ValueListService.getInstance();  
	private static final ZipcodeInfoService zipcodeInfoService = ZipcodeInfoService.getInstance();
	private static final ZipcodeDetailService zipcodeDetailService = ZipcodeDetailService.getInstance();
	private static final JbmCounterService jbmCounterService = JbmCounterService.getInstance();
	
	
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
     * 查詢
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response){
    	ZipcodeForm myForm = (ZipcodeForm) form;
    	
    	// 若查詢條件交寄筆數不為空值=>檢查是否為數字
    	if(StringUtils.isNotBlank(myForm.getZipcodeTotal_form()) 
    			&& !StringUtils.isNumeric(myForm.getZipcodeTotal_form().trim())){
    		
    		request.setAttribute("message", "交寄筆數須為0-9數字!");
            return mapping.findForward("message"); 
    	}
    	
    	ValueList dataList = getValueList(request, myForm,"zipcodeInfoList");
    	
    	
    	String backToListURL = request.getRequestURI() +"?" + request.getQueryString();
        try {
			request.setAttribute("backToListURL", URLEncoder.encode(backToListURL, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("URLEncoder.encode(" + backToListURL + ", UTF-8) - catch a UnsupportedEncodingException" , e);
			request.setAttribute("backToListURL", "");
		}
        request.setAttribute("dataList", dataList);
        
    	return mapping.findForward("list");
    }
    
    /**
     * 產生上傳郵局txt檔(單檔)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward generateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response){
 
    	// 取得前端傳入資訊
    	ZipcodeForm myForm = (ZipcodeForm) form;
    	String[] checkedZipcodeInfoNos = myForm.getZipcodeInfoNos();
    	
    	// 後端檢查是否有選擇檔案(前端也已有加js檢查)
    	if(checkedZipcodeInfoNos == null){
    		request.setAttribute("message", "未選擇任何檔案!");
            return mapping.findForward("message"); 
    	}
    	
    	log.info("batch generating zipcode file:" + checkedZipcodeInfoNos.length + " size");
    	
    	// 產生後檔案存放路徑
    	myForm.setServerPath(request.getSession().getServletContext().getRealPath("") + "\\pdf\\"); 
    	
    	// 紀錄操作人員
    	Employee employee = SessionUtil.getAccount(request.getSession());
    	myForm.setUpdateUser(employee.getUserId());
    	
    	// 產生郵遞區號檔案
    	for(String zipcodeInfoNo : checkedZipcodeInfoNos){
    		ZipcodeInfo zipcodeInfo = zipcodeInfoService.findByZipcodeInfoNo(Integer.valueOf(zipcodeInfoNo)); // 根據PK找出整個Entity
    		try {
				this.createZipcodeFile(zipcodeInfo, myForm);
			} catch (Exception e) {
				log.error("createZipcodeFile() - catch a Exception", e);
				return null;
			}
    	}
    	
    	ActionRedirect redirect = new ActionRedirect(mapping.findForward("redirect_list")); // 導向查詢頁面(/zipcode.do?fid=list)
        redirect.addParameter("fid","list");
    	
        return redirect;
    }
    
    
    /**
     * 產生上傳郵局的txt檔(多檔案合併)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward generateCombinedFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response){
    	
    	// 取得前端傳入資訊
        ZipcodeForm myForm = (ZipcodeForm) form;
    	String[] checkedZipcodeInfoNos = myForm.getZipcodeInfoNos();
    	
    	// 後端檢查是否有選擇檔案(前端也已有加js檢查)
    	if(checkedZipcodeInfoNos == null){
    		request.setAttribute("message", "未選擇任何檔案!");
            return mapping.findForward("message");
    	}
    	
    	log.info("batch generating combined zipcode file:" + checkedZipcodeInfoNos.length + " size");
    	
    	// 找出勾選的Zipcode_info, 找出zipcode_info資訊, 塞入暫存List中
    	List<ZipcodeInfo> zipcodeInfos = new ArrayList<ZipcodeInfo>();
    	for(String zipcodeInfoNo : checkedZipcodeInfoNos){
    		ZipcodeInfo zipcodeInfo = zipcodeInfoService.findByZipcodeInfoNo(Integer.valueOf(zipcodeInfoNo));
    		zipcodeInfos.add(zipcodeInfo);
    	}
    	

    	// 判斷所有合併交寄之郵件種類是否相同
    	// 將第一筆與其餘筆資料比較
    	for(int i = 0 ; i < zipcodeInfos.size() ; i++){
    		
    		// 第一筆skip
    		if(i == 0){
    			continue;
    		}
    		
    		// 若任一筆與第一筆的配送方式不同=>不可合併產生檔案
    		if(!StringUtils.equals(zipcodeInfos.get(0).getDispatchTime(), zipcodeInfos.get(i).getDispatchTime()) ||
    				!StringUtils.equals(zipcodeInfos.get(0).getDispatchKind(), zipcodeInfos.get(i).getDispatchKind())){
    			request.setAttribute("message", "所選郵件種類須全部相同才可合併產出!");
                return mapping.findForward("message");
    		}
    	}
    	
    	// 檔案存放路徑 
    	myForm.setServerPath(request.getSession().getServletContext().getRealPath("") + "\\pdf\\");    	
    	
    	// 紀錄操作人員
    	Employee employee = SessionUtil.getAccount(request.getSession());
    	myForm.setUpdateUser(employee.getUserId());
    	
    	// 產生合併的txt檔
    	try {
			this.createCombinedZipcodeFile(zipcodeInfos, myForm);
		} catch (Exception e) {
			log.error("createCombinedZipcodeFile() - catch a Exception", e);
			return null;
		} 
    	
    	ActionRedirect redirect = new ActionRedirect(mapping.findForward("redirect_list")); // 導向查詢頁面(/zipcode.do?fid=list)
        redirect.addParameter("fid","list");
        
    	return redirect;
    	
    	
    }
    
    /**
     * list撈取資料
     * 
     * @param request
     * @param myForm
     * @param entryKey
     * @return
     */
    private ValueList getValueList(HttpServletRequest request, ZipcodeForm myForm, String entryKey) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	
    	// 客戶代號
    	if(StringUtils.isNotBlank(myForm.getCustNo())){
    		param.put("custNo", myForm.getCustNo());
    	}
    	
    	// CYCLED DATE
    	if(StringUtils.isNotBlank(myForm.getCycleDate_form())){
    		param.put("cycleDate", myForm.getCycleDate_form());
    	}
    	
    	// 工單代號
    	if(StringUtils.isNotBlank(myForm.getJobBagNo())){
    		param.put("jobBagNo", myForm.getJobBagNo());
    	}
    	
    	// 轉檔日期 (收檔日期)
    	if(StringUtils.isNotBlank(myForm.getReceiveDate_form())){
    		param.put("receive_date", myForm.getReceiveDate_form());
    	}
    	
    	// 產生檔名
    	if(StringUtils.isNotBlank(myForm.getZipcodeFilename())){
    		param.put("zipcodeFilename", myForm.getZipcodeFilename());
    	}
    	
    	// 郵件種類 - 時效
    	if(StringUtils.isNotBlank(myForm.getDispatchTime_form())){
    		param.put("dispatchTime_form", myForm.getDispatchTime_form());
    	}
    	
    	// 郵件種類 - 類型
    	if(myForm.getDispatchKind_form() != null && myForm.getDispatchKind_form().length > 0){
    		
    		String [] dispatchKind_forms = myForm.getDispatchKind_form();
    		String dispatchKind_form="";
    		
    		for (int i=0; i<dispatchKind_forms.length; i++)  {
	        	if (i==0 ) 
	        		dispatchKind_form = "'" + dispatchKind_forms[i] + "'";
	        	else
	        		dispatchKind_form = dispatchKind_form + ", '" + dispatchKind_forms[i] + "'";
	        }
    		param.put("dispatchKind_form", dispatchKind_form);
    	}
    	
    	// 工單狀態
    	if(myForm.getJobBagStatuses() != null && myForm.getJobBagStatuses().length > 0){
    		
    		String [] jobBagStatuses = myForm.getJobBagStatuses();
    		String job_bag_status="";
    		
    		for (int i=0; i<jobBagStatuses.length; i++)  {
	        	if (i==0 ) 
	        		job_bag_status = "'" + jobBagStatuses[i] + "'";
	        	else
	        		job_bag_status = job_bag_status + ", '" + jobBagStatuses[i] + "'";
	        }
	    	param.put("job_bag_status", job_bag_status);
    	}
    	
    	// 交寄郵局或公司
    	if(myForm.getCodeMailToPostoffice()!= null && myForm.getCodeMailToPostoffice()!= 0){
    		param.put("code_mail_to_postoffice", myForm.getCodeMailToPostoffice());
    	}
    	
    	// 是否已產生檔案
    	if(StringUtils.isNotBlank(myForm.getIsCreatedFile())){
    		
    		if(StringUtils.equals("1", myForm.getIsCreatedFile())){ // 是
    			param.put("isCreteadFile", "i.ZIPCODE_FILENAME IS NOT NULL");
    		}
    		
    		if (StringUtils.equals("0", myForm.getIsCreatedFile())){ // 否
    			param.put("isCreteadFile", "i.ZIPCODE_FILENAME IS NULL");
    		}
    	}
    	
    	// 交寄筆數
    	if(StringUtils.isNotBlank(myForm.getZipcodeTotal_form())){
    		param.put("zipcodeTotal_form", Integer.valueOf(myForm.getZipcodeTotal_form().trim()));
    	}
    	
    	// 排序
    	String orderColumns = request.getParameter("sortColumn"); // 排序欄位
    	String sortDirection = request.getParameter("sortDirection"); // 排序方式(DESC, ASC)
    	
    	if(StringUtils.isNotBlank(sortDirection)){
        	sortDirection = sortDirection.trim();
        	if(sortDirection.equals("1"))
        		sortDirection = " asc ";
        	else
        		sortDirection = " desc ";
        }
    	
    	if(StringUtils.isBlank(orderColumns)){ // 無指定排序欄位,預設為id
    		orderColumns = "zipcode_info_no desc";
    	}else{ 
    		orderColumns += sortDirection;
    	}
    	param.put("orderColumns", orderColumns);
    	
    	ValueList valueList = valueListService.getValueList(request, entryKey, param);
    	
        return valueList;
    }
    
    /**
     * 根據ZipcodeInfo產生txt檔, 並將結果回寫DB
     * 
     * @param zipcodeInfo
     * @param myForm
     * @throws Exception
     */
    private void createZipcodeFile(ZipcodeInfo zipcodeInfo, ZipcodeForm myForm) throws Exception{
		// 撈取zipcode_detail資料
    	List<ZipcodeDetail> zipcodeDetails = zipcodeDetailService.findByZipcodeInfo(zipcodeInfo);
    	log.debug("find zipcodeDetails by zipcodeInfoNo = " + zipcodeInfo.getZipcodeInfoNo() + ",result size = " + zipcodeDetails.size());
    	
    	// 產生TXT檔
    	String createdFilename = this.createTxtFile(zipcodeDetails, myForm.getServerPath(), zipcodeInfo.getDispatchTime() + zipcodeInfo.getDispatchKind());
    	
    	// 回寫DB
    	log.info("createdFilename = " + createdFilename);
    	
    	zipcodeInfo.setZipcodeFilename(createdFilename);
    	zipcodeInfo.setUpdateDate(new Date()); // 產出日期
    	zipcodeInfo.setUpdateUser(myForm.getUpdateUser()); // 操作人員
    	zipcodeInfo.setCombinedNum(1); // 合併筆數

    	HibernateSessionFactory.getSession().clear();
    	HibernateSessionFactory.getSession().getTransaction().begin();
    	zipcodeInfoService.save(zipcodeInfo);
    	HibernateSessionFactory.getSession().getTransaction().commit();	
	}
    
    private void createCombinedZipcodeFile(List<ZipcodeInfo> zipcodeInfos, ZipcodeForm myForm) throws Exception{
    	
    	List<ZipcodeDetail> combinedDetailList = new LinkedList<ZipcodeDetail>(); // 結合後List
    	
    	// 撈取zipcode_detail資料, 塞入同一個detail中
    	for(ZipcodeInfo zipcodeInfo : zipcodeInfos){
    		List<ZipcodeDetail> zipcodeDetails = zipcodeDetailService.findByZipcodeInfo(zipcodeInfo);
    		log.debug("ZipcodeDetail size = " + zipcodeDetails.size());
    		combinedDetailList.addAll(zipcodeDetails);
    	}
    	log.info("combined ZipcodeDetail size = " + combinedDetailList.size());
    	
    	// 產生TXT
    	String createdFilename = this.createTxtFile(combinedDetailList, myForm.getServerPath(), zipcodeInfos.get(0).getDispatchTime() + zipcodeInfos.get(0).getDispatchKind());
    	
    	// 回寫DB
    	HibernateSessionFactory.getSession().clear();
    	HibernateSessionFactory.getSession().getTransaction().begin();
    	
    	for(ZipcodeInfo zipcodeInfo : zipcodeInfos){
    		zipcodeInfo.setZipcodeFilename(createdFilename);
        	zipcodeInfo.setUpdateDate(new Date());
        	zipcodeInfo.setUpdateUser(myForm.getUpdateUser());
        	zipcodeInfo.setCombinedNum(zipcodeInfos.size());
        	zipcodeInfoService.save(zipcodeInfo);
    	}
    	
    	HibernateSessionFactory.getSession().getTransaction().commit();	
    }
    
    
    /**
     * 根據資料產生txt檔
     * 
     * @param zipcodeDetails 資料
     * @param filePath 檔案放置路徑
     * @param postType 郵件種類
     * @return 產生後檔名
     * 
     * @throws Exception
     */
    private String createTxtFile(List<ZipcodeDetail> zipcodeDetails, String filePath, String postType) throws Exception{
    	
    	// 若找不到detail資訊, 則丟出例外
    	if(zipcodeDetails == null || zipcodeDetails.isEmpty()){
    		log.error("can't find any zipcodeDetail Data!");
    		throw new Exception("zipcodeDetails is null or empty!");
    	}

    	// 產生檔案
    	// 檔名規則:FTP帳號10碼(固定) + 郵件種類2碼 + YYYYMMDD + 序號2碼(01+99) + .txt 	
    	String ftpname = Util.getString("zipcode.ftp.account"); // FTP帳號
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String datePattern = sdf.format(new Date()); // 日期YYYYMMDD
		Integer seqNo = jbmCounterService.generateCounterZipcode("zipcode.seq");// 序號
		String extension = ".txt"; // 副檔名

		
		String outputFilename = ftpname + postType + datePattern + StringUtils.leftPad(seqNo.toString(), 2, "0") + extension; // 產出檔名
		
		File outputFile = new File(filePath + "/" + outputFilename);
		// ========= 
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(outputFile);
		} catch (IOException e) {
			log.error("1createTxtFile(filePath="+filePath+") new FileWriter - catch a IOException", e);
			throw e;
		}
		
		
		// =================
		BufferedWriter bufferedWriter = null;
		
		bufferedWriter = new BufferedWriter(fileWriter);
		// ===========================
		
		try{	
			bufferedWriter.write(ftpname + postType + datePattern + StringUtils.leftPad(seqNo.toString(), 2, "0"));
			
			for(ZipcodeDetail zipcodeDetail : zipcodeDetails){
				bufferedWriter.newLine();
				bufferedWriter.write(zipcodeDetail.getZipcode() + "," + zipcodeDetail.getZipcodeSum());
			}

		}catch(Exception e){
			log.error("createTxtFile(filePath="+filePath+") - catch a Exception", e);
			throw e;
		}finally{
			try {
				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e1) {
				log.error("createTxtFile(filePath="+filePath+") - catch a IOException", e1);
				throw e1;
			}
		}
		
		return outputFilename;
    }


}
