<%@page import="jxl.format.BorderLineStyle"%>
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
</head>
<%  

	List jobbagList = (List)request.getAttribute("jobbagList");
    Map<String, String> statementNoMap = (Map<String, String>)request.getAttribute("statementNoMap");
	String queryDateBegin = (String)request.getAttribute("queryDateBegin");
	String queryDateEnd = (String)request.getAttribute("queryDateEnd");
	String custNo = (String)request.getAttribute("custNo");	


	String serverPath = request.getSession().getServletContext().getRealPath("");
    String sourcefile  = serverPath + "\\report\\dailyLogementReport.xls";
    String targetfileName = custNo + "_" + queryDateBegin+ "_" + queryDateEnd + ".xls";
    
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
	
	WritableFont wf12 = new WritableFont(WritableFont.COURIER, 12, WritableFont.NO_BOLD);	
	jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,###0"); //用於Number的格式
	WritableCellFormat numberCellFormat = new jxl.write.WritableCellFormat(nf);
	numberCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.NONE);
	numberCellFormat.setFont(wf12);

	
	for (int i =0; i<jobbagList.size(); i++ ) {
		JobBag jobbag = (JobBag)jobbagList.get(i);
		Label labelA1 = new Label(0, i+1, Util.getDateFormat(jobbag.getCycleDate()));  //proc_date
		Label labelB1 = new Label(1, i+1, jobbag.getJobBagNo());  //job_no_m
		Label labelC1 = new Label(2, i+1, jobbag.getProgNm());  //prog_nm
		Integer pages = jobbag.getPages();
		Integer pagesOri = jobbag.getPagesOri();
		boolean ctSpecialSplite = jobbag.getCtSpecialSplite() == null ? false : jobbag.getCtSpecialSplite();
		if(ctSpecialSplite && pagesOri != null && pagesOri.intValue() != 0)
			pages = pagesOri;
		jxl.write.Number labelD1 = new jxl.write.Number(3, i+1, pages,  numberCellFormat);  //totpages
		jxl.write.Number labelE1 = new jxl.write.Number(4, i+1, jobbag.getAccounts(), numberCellFormat);  //totaccno
		String completedDate = "尚未完成交寄";
		if(jobbag.getCompletedDate() != null){
			completedDate = Util.getDateFormat(jobbag.getCompletedDate());
		}else{
			if(jobbag.getIsEdd() != null && jobbag.getIsEdd() && (jobbag.getMailType() == null || jobbag.getMailType().equals(""))){
				completedDate = Util.getDateFormat(jobbag.getCreateDate());
			}
		}
		Label labelF1 = new Label(5, i+1, completedDate);  //dispatch
		
		// DebitNote
		String isDebitNote = "N";		
		if("ACCOUNTING_LOCKED".equals(jobbag.getJobBagStatus()) ||
				"ACCT_DN_GENERATED".equals(jobbag.getJobBagStatus()) ||
				"ACCOUNTING_EP1".equals(jobbag.getJobBagStatus())){
			isDebitNote = "Y";
		}
		Label labelG1 = new Label(6, i+1, isDebitNote);  //DebitNote
		String statementNo = statementNoMap.get(jobbag.getJobBagNo()) == null? "" : statementNoMap.get(jobbag.getJobBagNo()); 
		Label labelStatementNo = new Label(7, i+1, statementNo);  //DebitNote
		
		
		ws.addCell(labelA1);
		ws.addCell(labelB1);
		ws.addCell(labelC1);
		ws.addCell(labelD1);
		ws.addCell(labelE1);
		ws.addCell(labelF1);
		ws.addCell(labelG1);
		ws.addCell(labelStatementNo);
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
          	(<%=targetfileName%>): <a href="pdf/<%=targetfileName%>" target="new">OPEN</a>
		  </td>
        </tr>        

        
      </table>
      
      
</body>
</html>
  

   