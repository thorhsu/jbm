<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
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
	AcctCustomerContractService acctCustomerContractService = AcctCustomerContractService.getInstance();
	AcctInvoiceService acctInvoiceService = AcctInvoiceService.getInstance();	
	AcctSum2Service acctSum2Service = AcctSum2Service.getInstance();
	
	AcctDno acctDno = (AcctDno)request.getAttribute("acctDno");
	
	
    String NowTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

    /*
    response.reset();
    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    String filename="report1_"+NowTime+".xls";
    response.setHeader("Content-Disposition" ,"attachment;filename="+filename);
    */
    
    String debitNoteNo = acctDno.getDebitNo();
    String processDateBegin = acctDno.getDesSdt() == null? "" : acctDno.getDesSdt();
    String processDateEnd = acctDno.getDesEdt() == null? "" : acctDno.getDesEdt();
    String cycleDateBegin = acctDno.getCycleSdt() == null? "" : acctDno.getCycleSdt();
    String cycleDateEnd = acctDno.getCycleEdt() == null? "" : acctDno.getCycleEdt();


	String serverPath = request.getSession().getServletContext().getRealPath("");
    
    String sourcefile  = serverPath + "\\report\\reportII.xls";

    String targetfileName = "reportII_" + debitNoteNo + "_" + NowTime + ".xls";;
    
    String targetfile  = serverPath + "\\pdf\\" + targetfileName;
    
    jxl.Workbook rw = jxl.Workbook.getWorkbook(new File(sourcefile));
	//jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile), rw);    

  	//jxl.write.WritableSheet ws = wwb.getSheet(0);

    //OutputStream os = response.getOutputStream();//將 WritableWorkbook 寫入到輸出流
    
 
	
	jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,###0"); 			//用於Number的格式
	WritableCellFormat numberCellFormat = new jxl.write.WritableCellFormat(nf);
	numberCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);	
	
	jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("#0.000000"); 		//用於小數的格式
	WritableCellFormat priceCellFormat = new jxl.write.WritableCellFormat(nf2);
	priceCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);		
	
	jxl.write.NumberFormat nf3 = new jxl.write.NumberFormat("#0.000"); 		//用於整數/小數的格式
	WritableCellFormat priceCellFormat3 = new jxl.write.WritableCellFormat(nf3);
	priceCellFormat3.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);						
	
	WritableCellFormat StringCellFormat = new jxl.write.WritableCellFormat();	//用於字串的格式
	StringCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);			
	


	WritableFont wfBOLD = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);	
	WritableCellFormat StringCellFormatSummary = new jxl.write.WritableCellFormat();	//用於字串的格式
	StringCellFormatSummary.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);		
	StringCellFormatSummary.setFont(wfBOLD);
	
	WritableCellFormat StringCellFormatSummaryNoBorder = new jxl.write.WritableCellFormat();	//用於字串的格式無格線
	StringCellFormatSummaryNoBorder.setBorder(jxl.format.Border.NONE, jxl.format.BorderLineStyle.NONE);		
	StringCellFormatSummaryNoBorder.setFont(wfBOLD);

	WritableCellFormat numberCellFormatSummary = new jxl.write.WritableCellFormat(nf);
	numberCellFormatSummary.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);	
	numberCellFormatSummary.setFont(wfBOLD);
	
	WritableCellFormat numberCellFormatSummaryNoBorder = new jxl.write.WritableCellFormat(nf);
	numberCellFormatSummaryNoBorder.setBorder(jxl.format.Border.NONE, jxl.format.BorderLineStyle.NONE);	
	numberCellFormatSummaryNoBorder.setFont(wfBOLD);
	
	 
	
 	WritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile), rw);  //創建可寫工作薄
	WritableSheet ws = wwb.getSheet(0);
	SheetSettings sheetSettings= ws.getSettings();
	sheetSettings.setScaleFactor(100); 
	sheetSettings.setPrintTitlesRow(0, 4);
	
	
	Label labelC2 = new Label(0, 1, "Debit Note No :"+debitNoteNo);
	Label labelC3 = null;
	Label labelF3 = null;
	if(!"".equals(processDateBegin) || !"".equals(processDateEnd))
	   labelC3 = new Label(0, 2, "Process Period :" + processDateBegin + "~"  + processDateEnd);
	else
	   labelC3 = new Label(0, 2, "Process Period :");
	if(!"".equals(cycleDateBegin) || !"".equals(cycleDateEnd))
	   labelF3 = new Label(4, 2, "Cycle :" + cycleDateBegin + "~"  + cycleDateEnd);
	else
	   labelF3 = new Label(4, 2, "Cycle :");
	
    ws.addCell(labelC2);
    ws.addCell(labelC3);
    ws.addCell(labelF3);    

	String[] header = new String[1000];
	String[] price = new String[1000];	
	acctDno.getCustomer().getCustNo();
	  
	// 找出客戶 + 今天 合約上的收費項目跟價格 
	String NowTime2 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	List acctCustomerContractList = acctCustomerContractService.getAcctCustomerContractListInDebiteNote(acctDno, acctDno.getCustomer().getCustNo(), NowTime2);
	header[0] = "CostCenter";
	header[1] = "Job Name";  
	header[2] = "Expense";
	
	price[0] = " ";
	price[1] = " "; 
	price[2] = " ";
	
		
	for (int i=0; i<acctCustomerContractList.size();i++) {
		AcctCustomerContract acctCustomerContract = (AcctCustomerContract)acctCustomerContractList.get(i);
		header[4+acctCustomerContract.getAcctChargeItem().getId()] = acctCustomerContract.getReportTitle();
		price[4+acctCustomerContract.getAcctChargeItem().getId()] = acctCustomerContract.getUnitPrice().toString();		
	} 
	 
	List  acctSum2List = acctSum2Service.findByDebitNote(acctDno); //找出itemType = "JOB_BAG" 
	
	String itemName="";
	Integer sumQty =0;
	String[][] line = new String[1000][1000];
	String prevCostCenter=" ";
	String prevJobCodeNo=" ";
	Integer lineCounter=-1; 
	Double expense = 0.D;

	for (int i=0; i<acctSum2List.size(); i++) {
		AcctSum2 acctSum2 = (AcctSum2)acctSum2List.get(i);
		if (null!= acctSum2.getItemType() && acctSum2.getItemType().equalsIgnoreCase("JOB_BAG")) {
			//根據 cost_center + job_code 合併成一列
			if ( !prevCostCenter.equalsIgnoreCase(acctSum2.getCostCenter()) || !prevJobCodeNo.equalsIgnoreCase(acctSum2.getIdfJobCodeNo())) {
				lineCounter = lineCounter + 1;
				if (null!= acctSum2.getCostCenter() ) {
					prevCostCenter = acctSum2.getCostCenter();
					prevJobCodeNo = acctSum2.getIdfJobCodeNo();
				}
				expense =0D;
			}				
			Integer acctAcctChargeItem = acctSum2.getAcctChargeItem().getId();
			
			if (null!= acctSum2.getCostCenter() )
				line[lineCounter][0] = acctSum2.getCostCenter();
			else
				line[lineCounter][0] = " ";
			line[lineCounter][1] = acctSum2.getProgNm();
			expense += acctSum2.getTotal();
			line[lineCounter][2] = expense.toString();
		    Double prev = line[lineCounter][4+acctSum2.getAcctChargeItem().getId()] == null ? 0 : Double.parseDouble(line[lineCounter][4+acctSum2.getAcctChargeItem().getId()]);
		    line[lineCounter][4+acctSum2.getAcctChargeItem().getId()] = ((acctSum2.getSumQty() == null ? 0 : acctSum2.getSumQty())+ prev ) + "";
		} else { //列舉項目
			lineCounter = lineCounter + 1;		
			if (null!= acctSum2.getCostCenter() )		
				line[lineCounter][0] = acctSum2.getCostCenter().trim();
			else
				line[lineCounter][0] = " ";
				
			if (null!= acctSum2.getPrintCode())
				line[lineCounter][1] = acctSum2.getItemName() + "|" + acctSum2.getItemNameTw() + "|" + acctSum2.getPrintCode();
			else
				line[lineCounter][1] = acctSum2.getItemName() + "|" + acctSum2.getItemNameTw() ;
			line[lineCounter][2] = acctSum2.getTotal().toString();
		} 
	}
    
    int cycleDateLength = 0;
	//若總欄位數 < 6.25, 重新計算 欄寬
	// 13 * 6.25 = 81.25
	if (acctCustomerContractList.size() < 11 ) {
		for (int i=0; i<10; i++){
			if (i < acctCustomerContractList.size()){ 
				ws.setColumnView(3+i, 83 / acctCustomerContractList.size() );
				if(3 + i == 4 || 3 + i == 5 || 3 + i == 6){
			       cycleDateLength += 83 / acctCustomerContractList.size();
			    }
		    }else{
				ws.setColumnView(3+i, 1 );//設定 Cell 寬度
				if(3 + i == 4 || 3 + i == 5 || 3 + i == 6){
			       cycleDateLength += 1;
			    }
			}			
	    }	    
	}
	if(cycleDateLength < 22){
	    ws.setColumnView(6, 22);
	}
	
	//列印 header
	Integer x=0;
	for (int i=0; i<1000;i++) {
		if (null!=header[i]) {
			Cell cell = 	ws.getCell(2,4);
		    Label label = new Label(x,4,header[i], StringCellFormat);
			label.setCellFormat(cell.getCellFormat());
			ws.addCell(label);
			x = x +1; 
			
		}
	}
	
	//列印 price	
	x=0;
	for (int i=0; i<1000;i++) {
		if (null!=price[i] ) {
			if (price[i]!=" ") {
				Double unitPrice = Double.valueOf(price[i]);
				if (unitPrice.compareTo(1.0) >0) {
					jxl.write.Number n = new jxl.write.Number(x,5,unitPrice,priceCellFormat3);
					ws.addCell(n);
				}
				else {
				    if(unitPrice.doubleValue() != 0){   
					   jxl.write.Number n = new jxl.write.Number(x,5,unitPrice,priceCellFormat);
					   ws.addCell(n);
					}else{
					   ws.addCell(new Label(x,5,"", StringCellFormat));
					}
				}
						
			}	
			x = x +1;	
		}
	
		 
	}
		
	//列印明細, 根據 cost center 合計
	x=0;
	int y=0;
	//double[1000] sum= new double[100];
	double[] sum = new double[1000];
	for (int i=0; i<=lineCounter;i++) {
			x=0;
			for (int j=0; j<1000; j++) {
				if (null!=header[j]) {
					if (null!= line[i][j] &&  j >=2 ) {
						Double totalINT = Double.valueOf(line[i][j]) ;
						if (null == totalINT)
							totalINT = 0.0;
						sum[j] += totalINT;
						if(sum[j] != 0){
						   jxl.write.Number n = new jxl.write.Number(x, y+6,totalINT,numberCellFormat);
						   ws.addCell(n);
						}else{
						   ws.addCell(new Label(x, y+6,"",StringCellFormat));
						}
					} else {
						Cell cell = 	ws.getCell(1,5);
						Label label = new Label(x, y+6,line[i][j], StringCellFormat);
						label.setCellFormat(cell.getCellFormat());								
						ws.addCell(label);
					}
					x = x +1; 
				} else {
					sum[j] = -1; // -1 表示這個col 不顯示合計
				}
			} 
			//根據cost_center 合計
			x=2; // 從合計開始
			if (i< 1000 && !line[i][0].equals(line[i+1][0])) {
				y+=1; 
				for (int j=0; j<1000; j++) {
					// 發票號碼處理
					String invoiceNo = acctInvoiceService.findByDebitNoCostCenter(debitNoteNo, line[i][0]);
					if (null!=invoiceNo && invoiceNo.length() >0 ) 
						invoiceNo = "INVOICE NO : " + invoiceNo; 
					Label labelEmpty = new Label(0, y+6,"", StringCellFormatSummaryNoBorder); //空白CELL
					ws.addCell(labelEmpty);
					Label labelInvoiceNo = new Label(1, y+6,invoiceNo, StringCellFormatSummaryNoBorder); //發票號碼處理
					ws.addCell(labelInvoiceNo);			
					if (sum[j] >= 0 && j >=2) {
					    if(sum[j] != 0){
						   jxl.write.Number n = new jxl.write.Number(x, y+6,sum[j],numberCellFormatSummaryNoBorder);
						   ws.addCell(n);
						}else{
						   ws.addCell(new Label(x, y+6,"",StringCellFormatSummaryNoBorder));
						}
						x = x +1;
					}
				}
				//RESET sum[] =0				
				for (int j=0; j<1000;j++)
					sum[j]=0.0;		
			}
			y+=1;
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
          	Report II 檔名(<%=targetfileName%>): <a href="download.do?fileName=<%=targetfileName%>&application=msexcel" target="new">>開啟</a>
		  </td>
        </tr>        


        
      </table>
      
      
</body>
</html>
<%
HibernateSessionFactory.closeSession();
%>
  

   