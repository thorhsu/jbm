<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
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
	AcctDebitNoteService acctDebitNoteService = AcctDebitNoteService.getInstance();
	AcctCustomerContractService acctCustomerContractService = AcctCustomerContractService.getInstance();
	AcctSectionReceiverMappingService sectionReceiverMappingService = AcctSectionReceiverMappingService.getInstance(); 	
	AcctSum3Service acctSum3Service = AcctSum3Service.getInstance();
	
	AcctDno acctDno = (AcctDno)request.getAttribute("acctDno");
	int digitLen = (acctDno.getDigitsLen() == null)? 0 : acctDno.getDigitsLen(); //呈現的小數位數
	
	CustomerReceiver customerReceiver = (CustomerReceiver)request.getAttribute("customerReceiver");
	
	
	String purpose="'','DISPLAY_ONLY'";
	DebitNoteFullInfo debitNoteFullInfo = acctDebitNoteService.getDebitNoteFullInfo(acctDno, purpose);	
	AcctSectionReceiverMapping acctSectionReceiverMapping= sectionReceiverMappingService.findByDebitNo(acctDno);	
	String reportIIITitle = (acctSectionReceiverMapping.getReportIiiTitle() == null)? "" : acctSectionReceiverMapping.getReportIiiTitle();
	
    String NowTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    
    String debitNoteNo = acctDno.getDebitNo();
    String processDateBegin = acctDno.getDesSdt();
    String processDateEnd = acctDno.getDesEdt();
    String cycleDateBegin = acctDno.getCycleSdt();
    String cycleDateEnd = acctDno.getCycleEdt();

	String serverPath = request.getSession().getServletContext().getRealPath("");
    String sourcefile  = serverPath + "\\report\\reportIII.xls";
    String targetfileName = "reportIII_" + debitNoteNo + "_" + NowTime + ".xls";;
    String targetfile  = serverPath + "\\pdf\\" + targetfileName;
    
    jxl.Workbook rw = jxl.Workbook.getWorkbook(new File(sourcefile));
 	WritableWorkbook wwb = Workbook.createWorkbook(new File(targetfile), rw);  //創建可寫工作薄
	WritableSheet ws = wwb.getSheet(0);
	
	WritableFont wf = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
	WritableCellFormat titleCellFormat = new WritableCellFormat(wf);
	titleCellFormat.setAlignment(jxl.format.Alignment.LEFT);//文字置中
	titleCellFormat.setBackground(jxl.format.Colour.GRAY_25);
	titleCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
	
	WritableCellFormat subtitleCellFormat = new WritableCellFormat();
	subtitleCellFormat.setAlignment(jxl.format.Alignment.getAlignment(5));//文字置中
	subtitleCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);	
	jxl.write.NumberFormat nf = null;
	if(digitLen == 0)
        nf = new jxl.write.NumberFormat("#,###0"); //用於Number的格式
	else if(digitLen == 1)
	    nf = new jxl.write.NumberFormat("#,###0.0"); //用於Number的格式
	else if(digitLen == 2)
	   nf = new jxl.write.NumberFormat("#,###0.00"); //用於Number的格式
	else if(digitLen >= 3)
	   nf = new jxl.write.NumberFormat("#,###0.000"); //用於Number的格式
	
	WritableCellFormat numberCellFormat = new jxl.write.WritableCellFormat(nf);
	numberCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);	
	
	jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("#0.000000"); //用於Number的格式
	WritableCellFormat priceCellFormat = new jxl.write.WritableCellFormat(nf2);
	priceCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);		
	
    String invoiceTitle = (customerReceiver.getInvoiceTitle() == null || customerReceiver.getInvoiceTitle().trim().equals(""))? customerReceiver.getCustomer().getCustName() : customerReceiver.getInvoiceTitle();  
	Label labelA3 = new Label(0, 2, invoiceTitle);  //HSBC Retail Banking
	Label labelA4 = new Label(0, 3, customerReceiver.getReceiver());  //聯絡人：Ms. Doris Chen
	Label labelA5 = new Label(0, 4, customerReceiver.getAddress());  //地址 : 新北市板橋區文化路二段285號10樓
	Label labelA6 = new Label(0, 5, customerReceiver.getTel());  //TEL：（02）8252-9636
	
	Label labelD3 = new Label(3, 2, acctDno.getDebitNo()); //Debit Note :	HSBC11M06WL-1
	Label labelD4 = new Label(3, 3, acctDno.getInNo()); //Invoice No :	UC42161008
	Label labelD5 = new Label(3, 4, acctDno.getInDt()); //Invoice Date	30-Jun-11
	Label labelD6 = new Label(3, 5, acctDno.getInDuedt());	//Due Date :	30-Jul-11
	

	
	Label labelB8 = new Label(1, 7, "Cycle date : " + cycleDateBegin + " ~ " + cycleDateEnd);
    
    WritableFont wfBOLD = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);	
	WritableCellFormat stringCellFormatSummary = new jxl.write.WritableCellFormat();	//用於字串的格式
	stringCellFormatSummary.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN);		
	stringCellFormatSummary.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN);
	stringCellFormatSummary.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.NONE);
	stringCellFormatSummary.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.NONE);
	stringCellFormatSummary.setFont(wfBOLD);
	
	WritableCellFormat stringCellFormat = new jxl.write.WritableCellFormat();	//用於字串的格式
	stringCellFormat.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN);		
	stringCellFormat.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN);
	stringCellFormat.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.NONE);
	stringCellFormat.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.NONE);
	
	WritableCellFormat stringCellFormatLeftTop = new jxl.write.WritableCellFormat();	//只有左邊及上方有邊線
	stringCellFormatLeftTop.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN);		
	stringCellFormatLeftTop.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.THIN);
	stringCellFormatLeftTop.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.NONE);
	stringCellFormatLeftTop.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.NONE);
	
	WritableCellFormat stringCellFormatLeftBottom = new jxl.write.WritableCellFormat();	//左側及下方有邊線
	stringCellFormatLeftBottom.setBorder(jxl.format.Border.LEFT, jxl.format.BorderLineStyle.THIN);		
	stringCellFormatLeftBottom.setBorder(jxl.format.Border.TOP, jxl.format.BorderLineStyle.NONE);
	stringCellFormatLeftBottom.setBorder(jxl.format.Border.RIGHT, jxl.format.BorderLineStyle.NONE);
	stringCellFormatLeftBottom.setBorder(jxl.format.Border.BOTTOM, jxl.format.BorderLineStyle.THIN);
				
	Label labelRpt3Tit = new Label(0, 9, reportIIITitle, stringCellFormatSummary);
	
	Label labelA38 = new Label(0, 37, "PAYMENT TERMS - " + acctSectionReceiverMapping.getPaymentTerm() + " DAYS FROM DATE OF INVOICE", stringCellFormat);		
	jxl.write.Number labelD38 = new jxl.write.Number(3, 37, debitNoteFullInfo.getAmountExcludeTax(), numberCellFormat);			
	jxl.write.Number labelD39 = new jxl.write.Number(3, 38, debitNoteFullInfo.getAmountIncludeTax(), numberCellFormat);			
	

	
	Label labelA45 = new Label(0, 44, "Bank : " + customerReceiver.getCustomer().getBankName());	//Bank : 上海商業儲蓄銀行 汐止分行
	Label labelA46 = new Label(0, 45, "A/C : " + customerReceiver.getCustomer().getBankAccount());	//A/C   : #45101000007381	
	 
 
    ws.addCell(labelA3);
    ws.addCell(labelA4);
    ws.addCell(labelA5);
    ws.addCell(labelA6);
        
    ws.addCell(labelD3);
    ws.addCell(labelD4);
    ws.addCell(labelD5);
    ws.addCell(labelD6);
    
    ws.addCell(labelB8);    
    ws.addCell(labelRpt3Tit);
    
    ws.addCell(labelA38);
    ws.addCell(labelD38);
    ws.addCell(labelD39);              
        
    ws.addCell(labelA45);
    ws.addCell(labelA46); 
    
    purpose="'','DISPLAY_ONLY'";
    
	List  acctSum3List = acctSum3Service.findByDebitNote(acctDno, purpose); //找出正常的 sum3 資料, purpose = '' 
			

	String[][] line = new String[1000][4];
	//0: Welcome Letter
	//1: Quantity	
	//2: Unit Price	
	//3: Total
	
	String prevTitle=" ";
	Integer lineCounter=0;
	Double total = 0.D;
	for (int i=0; i<acctSum3List.size(); i++) {
		AcctSum3 acctSum3 = (AcctSum3)acctSum3List.get(i);
		if(acctSum3.getTotal() != null && acctSum3.getTotal() != 0){
		   if ( !prevTitle.equalsIgnoreCase(acctSum3.getTitle())) {
			   line[lineCounter][0] = acctSum3.getTitle();
			   line[lineCounter][1] = "";
			   line[lineCounter][2] = "";
			   line[lineCounter][3] = "";
			   lineCounter = lineCounter + 1;
			   prevTitle = acctSum3.getTitle();
		   }				
		       String displayTitle = (acctSum3.getDisplayTitle() == null || acctSum3.getDisplayTitle().trim().equals(""))?  acctSum3.getSubtitle() : acctSum3.getDisplayTitle();   
		       String printCode = acctSum3.getPrintCode() == null ? "" : acctSum3.getPrintCode();
		       if(!"".equals(printCode))
		          displayTitle += "(" + printCode + ")"; 
			   line[lineCounter][0] = displayTitle;
			   line[lineCounter][1] = acctSum3.getSumQty().toString();
			   line[lineCounter][2] = acctSum3.getUnitPrice().toString();
			   double _total = 0;
			   if(digitLen == 0)
			       _total =   Math.round(acctSum3.getTotal());
			   else if(digitLen == 1)
			       _total =   (Math.round(acctSum3.getTotal() * 10)) / 10;
			   else if(digitLen == 2)
			       _total =   (Math.round(acctSum3.getTotal() * 100)) / 100;
			   else if(digitLen >= 3)
			       _total =   acctSum3.getTotal();
			   line[lineCounter][3] = "" + _total + "";
			   lineCounter = lineCounter + 1;			
		}
	}

	//列印根據 cost center 列印 job_bag 類的單據
	//因為模板只有27行，如果超過27行就要再增加行數
	int x=0;
	if(lineCounter > 27){
		for(int i = 0 ; i < lineCounter - 27 ; i++){
			ws.insertRow(10);
		}
	}
	for (int i=0; i<=lineCounter;i++) {
			x=0;
			for (int j=0; j<4; j++) {
				Label label = null ;
				if (j==0 && line[i][2]=="") {
					label = new Label(x, i+10, line[i][j], titleCellFormat);
					ws.addCell(label);
				} else if (j==0 && line[i][2]!="") {
					label = new Label(x, i+10, line[i][j], subtitleCellFormat);
					ws.addCell(label);
				} else if (j>0 && line[i][j]!="" && null !=line[i][j] ) {
					//System.out.println(line[i][j]);				
					Double _total = Double.valueOf(line[i][j]);
					if (j == 2) { // 價格格式, 小數6位, 
						jxl.write.Number n = new jxl.write.Number(x, i+10,_total,priceCellFormat);
						ws.addCell(n);
					} else {      // 整數 格式
						jxl.write.Number n = new jxl.write.Number(x, i+10,_total,numberCellFormat);
						ws.addCell(n);					
					}
				} 
				x = x +1; 
			}
	}
	
	

	//因為模板只有27行，如果超過27行就要往後退
	int plusLine = 0;
	if(lineCounter > 27){
		plusLine = lineCounter - 27;
		labelA38 = new Label(0, lineCounter + 10, "PAYMENT TERMS - " + acctSectionReceiverMapping.getPaymentTerm() + " DAYS FROM DATE OF INVOICE", stringCellFormat);
		ws.addCell(labelA38);
	}		
	// 退費處理
	if (null!= acctDno.getRefundamt() && acctDno.getRefundamt().compareTo(0.0) != 0) {
		Label labelA40 = new Label(0, 39 + plusLine, acctDno.getRefund(), stringCellFormatLeftTop);
		Label labelA41 = new Label(0, 40 + plusLine, "", stringCellFormatLeftBottom);
		jxl.write.Number labelD40 = new jxl.write.Number(3, 39 + plusLine, acctDno.getRefundamt(), numberCellFormat);	
		jxl.write.Number labelD41 = new jxl.write.Number(3, 40 + plusLine, debitNoteFullInfo.getAmountIncludeTax() +  acctDno.getRefundamt(), numberCellFormat);
		
		ws.addCell(labelA40);
		ws.addCell(labelA41);
		ws.addCell(labelD40);
		ws.addCell(labelD41);
	}	
		
    wwb.write();
    wwb.close();
    
    // 合併報表處理
    boolean isMergeReport = false;
	String targetfileNameMERGE_REPORT = "reportIII_MERGE_REPORT_" + debitNoteNo + "_" + NowTime + ".xls";;
	String targetfileMERGE_REPORT  = serverPath + "\\pdf\\" + targetfileNameMERGE_REPORT;    
	purpose="'MERGE_REPORT'";

	List  acctSum3ListMERGE_REPORT = acctSum3Service.findByDebitNote(acctDno, purpose); //找出MERGE_REPORT的 sum3 資料, purpose = 'MERGE_REPORT'
	if (null!= acctSum3ListMERGE_REPORT && acctSum3ListMERGE_REPORT.size() >0)
		isMergeReport = true;	 
	if ( isMergeReport ) {
		    rw = jxl.Workbook.getWorkbook(new File(sourcefile));
		 	wwb = Workbook.createWorkbook(new File(targetfileMERGE_REPORT), rw);  //創建可寫工作薄
			ws = wwb.getSheet(0);
			wf = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
			titleCellFormat = new WritableCellFormat(wf);
			titleCellFormat.setAlignment(jxl.format.Alignment.LEFT);//文字置中
			titleCellFormat.setBackground(jxl.format.Colour.GRAY_25);
			titleCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
			
			subtitleCellFormat = new WritableCellFormat();
			subtitleCellFormat.setAlignment(jxl.format.Alignment.getAlignment(5));//文字置中
			subtitleCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);	
			if(digitLen == 0)
			   nf = new jxl.write.NumberFormat("#,###0"); //用於Number的格式
			else if(digitLen == 1)
			   nf = new jxl.write.NumberFormat("#,###0.0"); //用於Number的格式
			else if(digitLen == 2)
			   nf = new jxl.write.NumberFormat("#,###0.00"); //用於Number的格式
			else if(digitLen >= 3)
			   nf = new jxl.write.NumberFormat("#,###0.000"); //用於Number的格式
			numberCellFormat = new jxl.write.WritableCellFormat(nf);
			numberCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);	
			
			nf2 = new jxl.write.NumberFormat("#0.000000"); //用於Number的格式
			priceCellFormat = new jxl.write.WritableCellFormat(nf2);
			priceCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);					
	
			labelA3 = new Label(0, 2, invoiceTitle);  //HSBC Retail Banking
			labelA4 = new Label(0, 3, customerReceiver.getReceiver());  //聯絡人：Ms. Doris Chen
			labelA5 = new Label(0, 4, customerReceiver.getAddress());  //地址 : 新北市板橋區文化路二段285號10樓 
			labelA6 = new Label(0, 5, customerReceiver.getTel());  //TEL：（02）8252-9636
			labelRpt3Tit = new Label(0, 9, reportIIITitle, stringCellFormatSummary);
			
			labelD3 = new Label(3, 2, acctDno.getDebitNo()); //Debit Note :	HSBC11M06WL-1
			labelD4 = new Label(3, 3, acctDno.getInNo()); //Invoice No :	UC42161008
			labelD5 = new Label(3, 4, acctDno.getInDt()); //Invoice Date	30-Jun-11
			labelD6 = new Label(3, 5, acctDno.getInDuedt());	//Due Date :	30-Jul-11
			
			labelB8 = new Label(1, 7, "Cycle date : " + cycleDateBegin + " ~ " + cycleDateEnd);		
			
			labelA38 = new Label(0, 37, "PAYMENT TERMS - " + acctSectionReceiverMapping.getPaymentTerm() + " DAYS FROM DATE OF INVOICE", stringCellFormat);		
			labelD38 = new jxl.write.Number(3, 37, debitNoteFullInfo.getAmountExcludeTax(), numberCellFormat);			
			labelD39 = new jxl.write.Number(3, 38, debitNoteFullInfo.getAmountIncludeTax(), numberCellFormat);			

			labelA45 = new Label(0, 44, "Bank : " + customerReceiver.getCustomer().getBankName());	//Bank : 上海商業儲蓄銀行 汐止分行
			labelA46 = new Label(0, 45, "A/C : " + customerReceiver.getCustomer().getBankAccount());	//A/C   : #45101000007381
			

		
		    ws.addCell(labelA3);
		    ws.addCell(labelA4);
		    ws.addCell(labelA5);
		    ws.addCell(labelA6);
		    ws.addCell(labelRpt3Tit);
		        
		    ws.addCell(labelD3);
		    ws.addCell(labelD4);
		    ws.addCell(labelD5);
		    ws.addCell(labelD6);
		    
		    ws.addCell(labelB8);    
		    
		    ws.addCell(labelA38);
		    ws.addCell(labelD38);
		    ws.addCell(labelD39);              
		        
		    ws.addCell(labelA45);
		    ws.addCell(labelA46); 
		            

			String[][] line_MERGE_REPORT = new String[12][4];
			//0: Welcome Letter
			//1: Quantity	
			//2: Unit Price	
			//3: Total
			
			prevTitle=" ";
			lineCounter=0;
			total = 0.D;
			for (int i=0; i<acctSum3ListMERGE_REPORT.size(); i++) {
				AcctSum3 acctSum3 = (AcctSum3)acctSum3ListMERGE_REPORT.get(i);
				if (acctSum3.getTitle().length() >0) {
					if ( !prevTitle.equalsIgnoreCase(acctSum3.getTitle())) {
						line_MERGE_REPORT[lineCounter][0] = acctSum3.getTitle();
						line_MERGE_REPORT[lineCounter][1] = "";
						line_MERGE_REPORT[lineCounter][2] = "";
						line_MERGE_REPORT[lineCounter][3] = "";
						lineCounter = lineCounter + 1;
						prevTitle = acctSum3.getTitle();
					}	 			
					    String displayTitle = (acctSum3.getDisplayTitle() == null || acctSum3.getDisplayTitle().trim().equals(""))? acctSum3.getSubtitle() : acctSum3.getDisplayTitle();
						line_MERGE_REPORT[lineCounter][0] = displayTitle;
						line_MERGE_REPORT[lineCounter][1] = acctSum3.getSumQty().toString();
						line_MERGE_REPORT[lineCounter][2] = acctSum3.getUnitPrice().toString();
						Long _total =   Math.round(acctSum3.getTotal());
						line_MERGE_REPORT[lineCounter][3] = ""+_total.toString()+"";
						lineCounter = lineCounter + 1;		
				}	
			}
		
			//列印根據 cost center 列印 job_bag 類的單據
			x=0;
			//因為模板只有27行，如果超過27行就要再增加行數
			if(lineCounter > 27){				
				for(int i = 0 ; i < lineCounter - 27 ; i++){
					ws.insertRow(10);
				}
			}
			for (int i=0; i<lineCounter;i++) {
					x=0;
					for (int j=0; j<4; j++) {
						Label label = null ;
						if (j==0 && line_MERGE_REPORT[i][2]=="") {
							label = new Label(x, i+10, line_MERGE_REPORT[i][j], titleCellFormat);
							ws.addCell(label);
						} else if (j==0 && line_MERGE_REPORT[i][2]!="") {
							label = new Label(x, i+10, line_MERGE_REPORT[i][j], subtitleCellFormat);
							ws.addCell(label);
						} else if (j>0 && line_MERGE_REPORT[i][j]!="" && null !=line_MERGE_REPORT[i][j] ) {
							//System.out.println(line_MERGE_REPORT[i][j]);				
							Double _total = Double.valueOf(line_MERGE_REPORT[i][j]);
							if (j == 2) { // 價格格式, 小數6位, 
								jxl.write.Number n = new jxl.write.Number(x, i+10,_total,priceCellFormat);
								ws.addCell(n);
							} else {      // 整數 格式
								jxl.write.Number n = new jxl.write.Number(x, i+10,_total,numberCellFormat);
								ws.addCell(n);					
							}
						} 
						x = x +1; 
					}
			}	
			//因為模板只有27行，如果超過27行就要往後退			
			plusLine = 0;
			if(lineCounter > 27){
				plusLine = lineCounter - 27;
				labelA38 = new Label(0, lineCounter + 10, "PAYMENT TERMS - " + acctSectionReceiverMapping.getPaymentTerm() + " DAYS FROM DATE OF INVOICE", stringCellFormat);
		        ws.addCell(labelA38);
			}				
			// 退費處理
			if (null!= acctDno.getRefundamt() && acctDno.getRefundamt().compareTo(0.0) != 0) {
				Label labelA40 = new Label(0, 39 + plusLine, acctDno.getRefund() , stringCellFormatLeftTop);
				Label labelA41 = new Label(0, 40 + plusLine, "", stringCellFormatLeftBottom);
				jxl.write.Number labelD40 = new jxl.write.Number(3, 39 + plusLine, acctDno.getRefundamt(), numberCellFormat);	
				jxl.write.Number labelD41 = new jxl.write.Number(3, 40 + plusLine, debitNoteFullInfo.getAmountIncludeTax() +   acctDno.getRefundamt(), numberCellFormat);
				
				ws.addCell(labelA40);
				ws.addCell(labelA41);
				ws.addCell(labelD40);
				ws.addCell(labelD41);
			}				
		    wwb.write();
		    wwb.close();
	}
    //os.close();


%>


  
<body>


      <table border="0" cellpadding="0" cellspacing="0" id="SysMiddleSet2" width="100%">

        <tr>
          <td height="28" background="images/green_title_bg.jpg" class="black12b" style="background-repeat:no-repeat; padding-left:10px">系統訊息</td>
        </tr>

        
        <tr>
          <td height="28" style="padding-top:10px">
          	Report III 檔名(<%=targetfileName%>): <a href="download.do?fileName=<%=targetfileName%>&application=msexcel" target="new">>開啟</a> 
		  </td>
        </tr>        
<%	if ( isMergeReport ) { %>        
        <tr>
          <td height="28" style="padding-top:10px">
          	Report III 檔名(<%=targetfileNameMERGE_REPORT%>): <a href="download.do?fileName=<%=targetfileNameMERGE_REPORT%>&application=msexcel" target="new">>開啟</a>
		  </td>
        </tr> 
<%} %>
        
      </table>
      
      
</body>
</html>
<%
HibernateSessionFactory.closeSession();
%>
  

   