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
	String queryDateBegin = (String)request.getAttribute("queryDateBegin");
	String queryDateEnd = (String)request.getAttribute("queryDateEnd");
	String custNo = (String)request.getAttribute("custNo");	


	String serverPath = request.getSession().getServletContext().getRealPath("");
    String sourcefile  = serverPath + "\\report\\DMSummaryReport.xls";
    String targetfileName = "DMSummaryReport_" + custNo + "_" + queryDateBegin+ "_" + queryDateEnd + ".xls";
    
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
	
	jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,###0"); //用於Number的格式
	WritableCellFormat numberCellFormat = new jxl.write.WritableCellFormat(nf);
	numberCellFormat.setBorder(jxl.format.Border.ALL, BorderLineStyle.NONE);	

	//														
	for (int i =0; i<jobbagList.size(); i++ ) {
		JobBag jobbag = (JobBag)jobbagList.get(i);
		Label labelA1 = new Label(0, i+1, Util.getDateFormat(jobbag.getCycleDate()));  //mmdd
		Label labelB1 = new Label(1, i+1, jobbag.getProgNm());  //filename
		Label labelC1 = new Label(2, i+1, jobbag.getAfpName());  //filename
		jxl.write.Number labelD1 = new jxl.write.Number(3, i+1, jobbag.getAccounts(), numberCellFormat);  //acctno
		String mp1Iflag="F";
		String mp2Iflag="F";
		String mp3Iflag="F";
		String mp4Iflag="F";
		if (null!= jobbag.getMpMp1Iflag() && jobbag.getMpMp1Iflag()) mp1Iflag = "S";
		if (null!= jobbag.getMpMp2Iflag() && jobbag.getMpMp2Iflag()) mp2Iflag = "S";
		if (null!= jobbag.getMpMp3Iflag() && jobbag.getMpMp3Iflag()) mp3Iflag = "S";
		if (null!= jobbag.getMpMp4Iflag() && jobbag.getMpMp4Iflag()) mp4Iflag = "S";
		
		Label labelE1 = new Label(4, i+1, mp1Iflag);  //mp1_iflag
		Label labelF1 = new Label(5, i+1, jobbag.getMpMp1Word());  //mp_word1
		
		jxl.write.Number labelG1 = new jxl.write.Number(6, i+1, jobbag.getFeeder2(),  numberCellFormat);  //f2
		Label labelH1 = new Label(7, i+1, mp2Iflag);  //mp2_iflag
		Label labelI1 = new Label(8, i+1, jobbag.getMpMp2Word());  //mp_word2
		
		jxl.write.Number labelJ1 = new jxl.write.Number(9, i+1, jobbag.getFeeder3(),  numberCellFormat);  //f3
		Label labelK1 = new Label(10, i+1, mp3Iflag);  //mp3_iflag
		Label labelL1 = new Label(11, i+1, jobbag.getMpMp3Word());  //mp_word3
		
		jxl.write.Number labelM1 = new jxl.write.Number(12, i+1, jobbag.getFeeder4(),  numberCellFormat);  //f4
		Label labelN1 = new Label(13, i+1, mp4Iflag);  //mp4_iflag
		Label labelO1 = new Label(14, i+1, jobbag.getMpMp4Word());  //mp_word4
		
				
		jxl.write.Number labelP1 = new jxl.write.Number(15, i+1, jobbag.getFeeder5(),  numberCellFormat);  //f5
		
		
		ws.addCell(labelA1);
		ws.addCell(labelB1);
		ws.addCell(labelC1);
		ws.addCell(labelD1);
		ws.addCell(labelE1);
		ws.addCell(labelF1);
		ws.addCell(labelG1);
		ws.addCell(labelH1);
		ws.addCell(labelI1);
		ws.addCell(labelJ1);
		ws.addCell(labelK1);
		ws.addCell(labelL1);
		ws.addCell(labelM1);
		ws.addCell(labelN1);
		ws.addCell(labelO1);
		ws.addCell(labelP1);
			
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
  

   