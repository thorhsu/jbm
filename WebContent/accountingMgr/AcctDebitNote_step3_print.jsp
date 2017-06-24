<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="jxl.format.PageOrientation"%>
<%@page import="jxl.*" %>
<%@page import="jxl.write.*" %>
<%@page import="java.io.*" %>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JBM v2.0</title>
<link rel="stylesheet" type="text/css" href="css/backend.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/screen.css" /> 
<link rel="stylesheet" href="js/aqua/theme.css" type="text/css" /> 
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
$().ready(function() {
	window.opener.submitFinished();
});
</script>


</head>
<%  
    int arraylength = 1003; //*****注意****   收費項目id假設最大不會超過1000，如果超過1000此arrayLength要增加
	AcctCustomerContractService acctCustomerContractService = AcctCustomerContractService.getInstance();
	AcctDebitNoteService acctDebitNoteService = AcctDebitNoteService.getInstance();
	AcctSum1Service acctSum1Service = AcctSum1Service.getInstance();
	final JobCodeService jobCodeService = JobCodeService.getInstance();       
	AcctSectionReceiverMappingService sectionReceiverMappingService = AcctSectionReceiverMappingService.getInstance(); 
	//AcctSectionJobCodeMappingService jobCodeMappingService = AcctSectionJobCodeMappingService.getInstance();
	AcctJobCodeChargeItemService acctJobCodeChargeItemService = AcctJobCodeChargeItemService.getInstance(); 
	
    		
	AcctDno acctDno = (AcctDno)request.getAttribute("acctDno");
	short pageBreak = acctDno.getPageBreak() == null? 0 : acctDno.getPageBreak();	
	boolean displayOriAnymore = acctDno.getDisplayOriAnymore() == null? false : acctDno.getDisplayOriAnymore();
	
	java.lang.Boolean trueFlag = true;
    String[] header = new String[arraylength];
    Map<String, Integer []> jobBagDetailMap = new HashMap<String, Integer[]>();
    int columnCounts = 0;
    //是否一定要顯示accts, pages, sheets
    if(trueFlag.equals(acctDno.getSheetsShow()) || trueFlag.equals(acctDno.getAcctsShow()) || trueFlag.equals(acctDno.getPagesShow())){
    	List<JobBag> jobBagList = acctDebitNoteService.findJobBagListByDebitNote(acctDno);
    	if(trueFlag.equals(acctDno.getAcctsShow())){
    		columnCounts ++;
    		header [arraylength - 3] = "accounts";
    	}
    	if(trueFlag.equals(acctDno.getPagesShow())){
    		columnCounts ++;
    		header [arraylength - 2] = "pages";
    	}
    	if(trueFlag.equals(acctDno.getSheetsShow())){
    		columnCounts ++;
    		header [arraylength - 1] = "sheets";
    	}
    	
    	for(JobBag jobBag: jobBagList){
    		Integer[] detail = new Integer[3]; 
    		detail[0] = jobBag.getCtAcctsOri();
    		if(jobBag.getCtAcctsOri() == null && jobBag.getAccounts() != null){
    			detail[0] = jobBag.getAccounts();
    		}
    		detail[1] = jobBag.getPages();
    		detail[2] = jobBag.getSheets();
    		jobBagDetailMap.put(jobBag.getJobBagNo(), detail);    		
    	}    	
    }
	
	
	AcctSectionReceiverMapping acctSectionReceiverMapping= sectionReceiverMappingService.findByDebitNo(acctDno);
	
	//找出此Debit note的收費項目
	List<AcctJobCodeChargeItem> acctJobCodeChargeItemList = acctJobCodeChargeItemService.findChargeItemsByAcctDno(acctDno);
	
    String NowTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    
    
    String debitNoteNo = acctDno.getDebitNo();
    String processDateBegin = acctDno.getDesSdt() == null? "" : acctDno.getDesSdt();
    String processDateEnd = acctDno.getDesEdt() == null? "" : acctDno.getDesEdt();
    String cycleDateBegin = acctDno.getCycleSdt() == null? "" : acctDno.getCycleSdt();
    String cycleDateEnd = acctDno.getCycleEdt() == null? "" : acctDno.getCycleEdt();

 
	String serverPath = request.getSession().getServletContext().getRealPath("");
    String sourcefile  = serverPath + "\\report\\reportI.xls";
    String targetfileName = "reportI_" + debitNoteNo + "_" + NowTime + ".xls";;
    
    String targetfile  = serverPath + "\\pdf\\" + targetfileName;
    
    
	jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,###0"); 			//用於Number的格式
	WritableCellFormat numberCellFormat = new jxl.write.WritableCellFormat(nf);
	numberCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);	
	
	jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("#0.000000"); 		//用於小數的格式
	WritableCellFormat priceCellFormat = new jxl.write.WritableCellFormat(nf2);
	priceCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);		
	
	WritableCellFormat StringCellFormat = new jxl.write.WritableCellFormat();	//用於字串的格式
	StringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);		 
	
	WritableCellFormat StringCellFormatNoBorder = new jxl.write.WritableCellFormat();	//用於字串的格式無格線
	StringCellFormatNoBorder.setBorder(jxl.format.Border.NONE, jxl.format.BorderLineStyle.NONE);
	
	WritableFont wfBOLD = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
	
	WritableCellFormat numberCellFormatSummaryNoBorder = new jxl.write.WritableCellFormat(nf); //用於數字總合的格式無格線
	numberCellFormatSummaryNoBorder.setBorder(jxl.format.Border.NONE, jxl.format.BorderLineStyle.NONE);	
	numberCellFormatSummaryNoBorder.setFont(wfBOLD);

	
		
	WritableCellFormat StringCellFormatSummary = new jxl.write.WritableCellFormat();	//用於字串的格式
	StringCellFormatSummary.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);		
	StringCellFormatSummary.setFont(wfBOLD);
	
	WritableCellFormat StringCellFormatSummaryNoBorder = new jxl.write.WritableCellFormat();	//用於字串的格式 無格線
	StringCellFormatSummaryNoBorder.setBorder(jxl.format.Border.NONE, jxl.format.BorderLineStyle.NONE);		
	StringCellFormatSummaryNoBorder.setFont(wfBOLD);

	WritableCellFormat numberCellFormatSummary = new jxl.write.WritableCellFormat(nf);
	numberCellFormatSummary.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);	
	numberCellFormatSummary.setFont(wfBOLD);
	 
    jxl.Workbook rw = jxl.Workbook.getWorkbook(new File(sourcefile));
 	WritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile), rw);  //創建可寫工作薄
	WritableSheet ws = wwb.getSheet(0);
 	SheetSettings sheetSettings = ws.getSettings();



	Label labelC2 = new Label(0, 1, "Debit Note No :"+debitNoteNo);
	Label labelC3 = null;
	if(!"".equals(processDateBegin) || !"".equals(processDateEnd))
	   labelC3 = new Label(0, 2, "Process Period :" + processDateBegin + " --  "  + processDateEnd);
	else
	   labelC3 = new Label(0, 2, "Process Period :");
	Label labelF3 = null;
	if(!"".equals(cycleDateBegin) || !"".equals(cycleDateEnd))
	   labelF3 = new Label(4, 2, "Cycle:" + cycleDateBegin + " --  "  + cycleDateEnd);
	else
	   labelF3 = new Label(4, 2, "Cycle:");
    ws.addCell(labelC2);
    ws.addCell(labelC3);
    ws.addCell(labelF3);
    
	
	// 找出客戶 + 今天 合約上的收費項目跟價格
	String NowTime2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	List acctCustomerContractList = acctCustomerContractService.getAcctCustomerContractListInDebiteNote(acctDno, acctDno.getCustomer().getCustNo(), NowTime2);
	header[0] = "CostCenter";
	header[1] = "Job Name"; 
	header[2] = "JobNo";
	header[3] = "CycleDate";
	
	
	Map<Integer, List<Integer>> adjItems = new HashMap<Integer, List<Integer>>(); //有調整係數的收費項目 
	List  acctSum1List = acctSum1Service.findByDebitNote(acctDno);//.getAcctSum1s();
	List<String>  jobCodeList = acctSum1Service.findJobCodeByDebitNote(acctDno);//acctSum1含有那些JobCode;
	for (int i=0; i<acctCustomerContractList.size();i++) {
	    boolean adjPercents = false;
		AcctCustomerContract acctCustomerContract = (AcctCustomerContract)acctCustomerContractList.get(i);
		Set<AcctJobCodeChargeItem> chargeItemList = acctCustomerContract.getAcctJobCodeChargeItems();		
	    for(AcctJobCodeChargeItem chargeItem : acctJobCodeChargeItemList){
           if((chargeItem.getAdjustmentPercent() != null && chargeItem.getAdjustmentPercent().doubleValue() != 1) 
        		   || (chargeItem.getPrintTimes() != null && chargeItem.getPrintTimes().intValue() != 1)){
        	  if(jobCodeList != null && jobCodeList.contains(chargeItem.getJobCode().getJobCodeNo())){
		         AcctChargeItem acctChargeItem = chargeItem.getAcctChargeItem();
		         if(acctChargeItem.getId().intValue() == acctCustomerContract.getAcctChargeItem().getId().intValue()){
		              adjItems.put(acctChargeItem.getId(), null);
		              adjPercents = true;
		         }
        	  }
		   }
		   header[4+acctCustomerContract.getAcctChargeItem().getId()] = acctCustomerContract.getReportTitle();
	   }
	} 
	
	int cycleDateLength = 0;
	//若總欄位數 < 6.25, 重新計算 欄寬
	// 13 * 6.25 = 81.25
	if (acctCustomerContractList.size() + adjItems.size() + columnCounts < 13 ) {
		for (int i=0; i<12; i++)
			if (i < (acctCustomerContractList.size() + adjItems.size() + columnCounts)){ 
				int columnView = 83 / (acctCustomerContractList.size() + adjItems.size() +  columnCounts);
				if(pageBreak != 0 && columnView > 27)
					columnView = 27;
				ws.setColumnView(4+i, columnView );
				if(3 + i == 4 || 3 + i == 5 || 3 + i == 6 || 3 + i == 7){
			       cycleDateLength += 83 / acctCustomerContractList.size();			       
			    }
			}else{
				ws.setColumnView(4+i, 1 );//設定 Cell 寬度
				if(3 + i == 4 || 3 + i == 5 || 3 + i == 6 || 3 + i == 7){
			       cycleDateLength += 1;			       
			    }
			}
		
	}
	if(cycleDateLength < 22){
	    ws.setColumnView(7, 22);
	}
	
	//如果需要產生分頁小計的話，要進行下列設定，尤其是FitToPages(false)，如果不設定會無法分頁
 	if(pageBreak != 0){
  	   sheetSettings.setFitToPages(false);
  	   sheetSettings.setPageBreakPreviewMode(true);
  	   int frac = (Math.round(11 / (acctCustomerContractList.size() + adjItems.size() + columnCounts) * 10) ) * 10;
  	   if(frac >= 100)
  		  sheetSettings.setScaleFactor(95);
  	   else
  	      sheetSettings.setScaleFactor(frac);
  	}
	
	
	String itemName="";
	Integer sumQty =0;
	String[][] line = new String[20000][arraylength];
	String prevJobBagNo="";
	Integer lineCounter = -1;
	Integer adjLineCounter = -1;
	//本列是不是全部都是零 ??
	boolean lineAllZero = true;
	for (int i=0; i<acctSum1List.size(); i++) {
		AcctSum1 acctSum1 = (AcctSum1)acctSum1List.get(i);
		//job_bag_no不同時，換下一列 
		if ( !prevJobBagNo.equalsIgnoreCase(acctSum1.getIdfJobBagNo())) {			
			if(lineAllZero && lineCounter >= 0){
			}			
			lineCounter ++;
			prevJobBagNo = acctSum1.getIdfJobBagNo();
			lineAllZero = true;
		}		
		//JobCode _jobCode = jobCodeService.findById(acctSum1.getIdfJobCodeNo());
		if (null!=acctSum1.getCostCenter() && acctSum1.getCostCenter().length() >0)
			line[lineCounter][0] = acctSum1.getCostCenter();
		else
			line[lineCounter][0] = "";
		line[lineCounter][1] = acctSum1.getProgNm();
		line[lineCounter][2] = acctSum1.getIdfJobBagNo();
		line[lineCounter][3] = Util.getDateFormat(acctSum1.getCycleDate());
		Integer id = acctSum1.getAcctChargeItem().getId();
		line[lineCounter][4 + id] = acctSum1.getSumQty().toString();		
		//如果不是零，lineAllZero設為false
		if(acctSum1.getSumQty() != null && acctSum1.getSumQty().intValue() != 0)			
			lineAllZero = false;
		
		//adjItems有此收費項目的id時才放入值
		if(adjItems.containsKey(id) ){
		    List<Integer> sumList = adjItems.get(id);
		    if(sumList == null){
		       sumList = new ArrayList<Integer> ();
		    }
		    sumList.add(acctSum1.getSumQtyOri() == null? 0 : acctSum1.getSumQtyOri());
		    adjItems.put(id, sumList);
		}
		Integer [] jobBagDetail =  jobBagDetailMap.get(acctSum1.getIdfJobBagNo());
		//原始accounts
		if(trueFlag.equals(acctDno.getAcctsShow()) && jobBagDetail != null && header[arraylength - 3] != null){			
			line[lineCounter][arraylength - 3] = jobBagDetail[0] + "";
		}
		//原始pages
		if(trueFlag.equals(acctDno.getPagesShow()) && jobBagDetail != null && header[arraylength - 2] != null){			
			line[lineCounter][arraylength - 2] = jobBagDetail[1] + "";
		}
		//原始sheets
		if(trueFlag.equals(acctDno.getSheetsShow()) && jobBagDetail != null && header[arraylength - 1] != null){			
			line[lineCounter][arraylength - 1] = jobBagDetail[2] + "";
		}
	}
	
	
	Integer x=0;
	Integer y=0;
	for (int i=0; i<arraylength;i++) {
		if (null!=header[i]) {
			Cell cell = 	ws.getCell(4,4);
			Label label = new Label(x,4,header[i], StringCellFormat);
			label.setCellFormat(cell.getCellFormat());
			x = x +1;
			ws.addCell(label);
			if(adjItems.containsKey( i - 4)){
			     cell = 	ws.getCell(4,4);
			     label = new Label(x,4,header[i] + "(ORI)", StringCellFormat);
			     label.setCellFormat(cell.getCellFormat());
			     x = x +1;
			     ws.addCell(label);
			}
		}
	}
	
	
	//列印明細, 根據 job_code + cycle date 合計
	x=0;
	y=0;
	double[] sum = new double[arraylength]; //小計
	double[] totalSum = new double[arraylength];  //全部合計
	double[] oriTotalSum = new double[arraylength];  //Original全部合計
	double[] oriSubSum = new double[arraylength];  //未調整係數前的sub sum
	double[] pageSubSum = new double[arraylength]; //每頁小計
	double[] oriPageSubSum = new double[arraylength]; //每頁未調整係數前的sub sum
	int pageCounter = 0; //頁數計算器
	
	int pageLineCounter = 0;
	boolean noCostCenter = true;
	Map<Integer, Integer> counterMap = new HashMap<Integer, Integer>(); //計算小計的
	int writeLines = 0;
	boolean allColumnsBlank = true;
	for (int i=0; i<=lineCounter;i++) {
		    allColumnsBlank = true;
			x=0;
			writeLines++;
			pageLineCounter++;
			
			for (int j=0; j<arraylength; j++) {
			    if(j < 4){
			       totalSum[j] = -1;
			    }
				if (null!=header[j]) {
					if (null!= line[i][j] && !"".equals(line[i][j].trim())  &&  j >=4) {
						Double totalINT = Double.valueOf(line[i][j]);
						sum[j] += totalINT;//小計
						pageSubSum[j] += totalINT ; // 每頁小計 						
						totalSum[j] += totalINT; //全部合計
						jxl.write.Number n = null;
						if(totalINT != null && totalINT.doubleValue() != 0){
						   n = new jxl.write.Number(x, y+5,totalINT,numberCellFormat);
						   ws.addCell(n); 
						   allColumnsBlank = false;
						}else{
						   ws.addCell( new Label(x, y+5 ,"", StringCellFormat));
						}
						
						//加入original的值
						if(adjItems.containsKey( j - 4)){
						   if(!counterMap.containsKey(j - 4)){
						       counterMap.put(j - 4, 0 );
						   }
						   
						   Integer counter = counterMap.get(j-4);
						   x++;
						   Double oriTotalInt = totalINT;
						   if(adjItems.get(j-4).get(counter) != null)
						      totalINT = Double.valueOf(adjItems.get(j-4).get(counter));
						   
						   boolean oriEqTotal = true; //Original和調整後是不是相等
						   if(oriTotalInt == null && totalINT == null)
							   oriEqTotal = true;
						   else if((oriTotalInt != null &&  totalINT == null) || (oriTotalInt == null &&  totalINT != null)){
							   oriEqTotal = false;
						   }else{
							   oriEqTotal = (oriTotalInt.doubleValue() == totalINT.doubleValue());
						   }
						    
                           //不相等時才出現
						   if(displayOriAnymore || (totalINT != null && totalINT.doubleValue() != 0  && !oriEqTotal)){
							  oriTotalSum[j] += totalINT; 
							  oriSubSum[j] += totalINT;  //未調整係數前的sub sum
							  oriPageSubSum[j] += totalINT;  //未調整係數前的每頁sub sum 
						      n = new jxl.write.Number(x, y+5,totalINT,numberCellFormat);
						      ws.addCell(n);
						      allColumnsBlank = false;
						   }else{
						      ws.addCell( new Label(x, y+5, "" ,StringCellFormat));						      
						   }
						   
						   counter ++;						   
						   counterMap.put(j -4, counter);
						   
						}
					} else {
						Label label = new Label(x, y+5 ,line[i][j], StringCellFormat);
						ws.addCell(label);	
						//sum[j] = -1;
						//oriSubSum[j] = -1;
						if(adjItems.containsKey( j - 4)){ 
						   x++;  
						   label = new Label(x, y+5 ,"", StringCellFormat);
						   ws.addCell(label);						   
						}
					}
					x = x +1; 
				} else {
					sum[j] = -1; // -1 表示這個col 不顯示合計
					oriSubSum[j] = -1;
					totalSum[j] = -1;
					pageSubSum[j] = -1;
					oriPageSubSum[j] = -1;
				}		
			}
			//如果全部空白，移除此行
			if(allColumnsBlank){
				ws.removeRow(y + 5);
				writeLines--;
				pageLineCounter--;
				y--;
			}
			
			
			
			//page sum 檢查
			x = 2;
			if(pageBreak != 0 && pageLineCounter >= pageBreak){
			   pageCounter++;
			   y++;
			   Label subTotal = new Label(1, y+5,"Page " + pageCounter + " Sub-total:", StringCellFormatSummaryNoBorder); //寫出小計
			   ws.addCell(subTotal);		
			   
			   for (int j=0; j<arraylength; j++) {
				   if (pageSubSum[j] >= 0 && j >=2) {
				       //如果有小計，加入合計
				       if(pageSubSum[j] != 0){
					      jxl.write.Number n = new jxl.write.Number(x, y+5,pageSubSum[j],numberCellFormatSummaryNoBorder);
					      ws.addCell(n);
					   }else{
					      ws.addCell(new Label(x, y+5,"", StringCellFormatSummaryNoBorder));
					   }
					   x++;
				   }else{
				       if(j > 4 && header[j] != null){
				          //如果沒有小計，但有header，代表仍須填空值
				          Label label = new Label(x, y+5 ,"", StringCellFormatNoBorder);
				          ws.addCell(label);
				          x++;
				       }
				   }				
				   //加入Original的小計及空格 
				   if(adjItems.containsKey( j - 4)){
				        if(oriPageSubSum[j] > 0){
				           jxl.write.Number n = new jxl.write.Number(x, y+5,oriPageSubSum[j],numberCellFormatSummaryNoBorder);
					       ws.addCell(n);
				        }else{
				           Label label = new Label(x, y+5 ,"", StringCellFormatNoBorder);
				           ws.addCell(label);
				        }
				        x++;
				   }
			   }
				//RESET sum[] =0				
				for (int j=0; j<arraylength;j++){
				   pageSubSum[j] = 0.0;
				   oriPageSubSum[j] = 0;		
				}
			   pageLineCounter = 0;
			   ws.addRowPageBreak(y + 6);
			}
			
			//根據cost_center 合計
			x=2; // 從合計開始
			if (null!= acctSectionReceiverMapping.getIsSummarizationOfSum1() && acctSectionReceiverMapping.getIsSummarizationOfSum1()) {
			    noCostCenter = false;
			    
				if ((!line[i][1].equals(line[i+1][1])   //活動名稱 不同
					|| !line[i][3].equals(line[i+1][3] ) ) //CYCLE DATE 
					) {
					//超過一行才寫小計					
					if(writeLines > 1){						
					   y += 1;
					   //if(writeLine > 1)
					   Label subTotal = new Label(1, y+5,"Sub-total:", StringCellFormatSummaryNoBorder); //寫出小計
					   ws.addCell(subTotal);		
					   //加入sub total				
					   for (int j=0; j<arraylength; j++) {
						   if (sum[j] >= 0 && j >=2) {
						       //如果有小計，加入合計
						       if(sum[j] != 0){
							      jxl.write.Number n = new jxl.write.Number(x, y+5,sum[j],numberCellFormatSummaryNoBorder);
							      ws.addCell(n);
							   }else{
							      ws.addCell(new Label(x, y+5,"", StringCellFormatSummaryNoBorder));
							   }
							   x = x +1;
						   }else{
						       if(j > 4 && header[j] != null){
						          //如果沒有小計，但有header，代表仍須填空值
						          Label label = new Label(x, y+5 ,"", StringCellFormatNoBorder);
						          ws.addCell(label);
						          x++;
						       }
						   }
						
						   //加入Original的小計及空格 
						   if(adjItems.containsKey( j - 4)){
						        if(oriSubSum[j] > 0){
						           jxl.write.Number n = new jxl.write.Number(x, y+5,oriSubSum[j],numberCellFormatSummaryNoBorder);
							       ws.addCell(n);
						        }else{
						           Label label = new Label(x, y+5 ,"", StringCellFormatNoBorder);
						           ws.addCell(label);
						        }
						        x++;
						   }
					   }
					   pageLineCounter++;
					}   
					//RESET sum[] =0				
					for (int j=0; j<arraylength;j++){
  					   sum[j]=0.0;
					   oriSubSum[j] = 0;		
					}
					writeLines = 0;					
				}
			}
			
			//page sum 檢查
			x = 2;
			if(pageBreak != 0 && pageLineCounter >= pageBreak){
			   pageCounter++;
			   y++;
			   Label subTotal = new Label(1, y+5,"Page " + pageCounter + " Sub-total:", StringCellFormatSummaryNoBorder); //寫出小計
			   ws.addCell(subTotal);		
			   
			   for (int j=0; j<arraylength; j++) {
				   if (pageSubSum[j] >= 0 && j >=2) {
				       //如果有小計，加入合計
				       if(pageSubSum[j] != 0){
					      jxl.write.Number n = new jxl.write.Number(x, y+5,pageSubSum[j],numberCellFormatSummaryNoBorder);
					      ws.addCell(n);
					   }else{

					      ws.addCell(new Label(x, y+5,"", StringCellFormatSummaryNoBorder));
					   }
					   x++;
				   }else{
				       if(j > 4 && header[j] != null){
				          //如果沒有小計，但有header，代表仍須填空值
				          Label label = new Label(x, y+5 ,"", StringCellFormatNoBorder);
				          ws.addCell(label);
				          x++;
				       }
				   }				
				   //加入Original的小計及空格 
				   if(adjItems.containsKey( j - 4)){
				        if(oriPageSubSum[j] > 0){
				           jxl.write.Number n = new jxl.write.Number(x, y+5,oriPageSubSum[j],numberCellFormatSummaryNoBorder);
					       ws.addCell(n);
				        }else{
				           Label label = new Label(x, y+5 ,"", StringCellFormatNoBorder);
				           ws.addCell(label);
				        }
				        x++;
				   }
			   }
			   
				//RESET sum[] =0				
				for (int j=0; j<arraylength;j++){
				   pageSubSum[j] = 0.0;
				   oriPageSubSum[j] = 0;		
				}
			   pageLineCounter = 0;
			   ws.addRowPageBreak(y + 6);
			}
			y += 1; //列數加一
	}
	Label total = new Label(1, y+5, "Total:", StringCellFormatSummary); //合計
	ws.addCell(total);			
	x = 4;//從合計開始			
	for (int j=0; j<arraylength; j++) {
		if (totalSum[j] >= 0 && j >=2) {
		    if(totalSum[j] != 0){
			   jxl.write.Number n = new jxl.write.Number(x, y+5,totalSum[j], numberCellFormatSummary);
			   ws.addCell(n);
			}else{
			   ws.addCell(new Label(x, y+5 ,"", StringCellFormatSummary));
			}
			if(adjItems.containsKey( j - 4)){
		       x++;
		       if(oriTotalSum[j] > 0){
		           jxl.write.Number n = new jxl.write.Number(x, y+5, oriTotalSum[j], numberCellFormatSummary);
			       ws.addCell(n);
		       }else{
		           Label label = new Label(x, y+5 ,"", StringCellFormatSummary);
		           ws.addCell(label);
		       }
		    }
			x = x +1;
		}
	}
	
	


    wwb.write();
    wwb.close();


%>


  
<body>


      <table border="0" cellpadding="0" cellspacing="0" id="SysMiddleSet2" width="100%">

        <tr>
          <td height="28" background="images/green_title_bg.jpg" class="black12b" style="background-repeat:no-repeat; padding-left:10px">系統訊息</td>
        </tr>

        
        <tr>
          <td height="28" style="padding-top:10px">
          	Report I 檔名(<%=targetfileName%>): <a href="download.do?fileName=<%=targetfileName%>&application=msexcel&charset=utf-8" target="new">開啟</a>
          	<!-- a href="pdf/<%//= targetfileName%>" target="new">開啟</a -->
		  </td>
        </tr>        


        
      </table>
      
      
</body>
</html>
<%

HibernateSessionFactory.closeSession();

%>
  

   