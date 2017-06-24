<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%> 
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
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
</head>
<%
	String message = (String )request.getAttribute("message");
	AcctDno acctDno= (AcctDno)request.getAttribute("acctDno"); 

	Customer customer = acctDno.getCustomer();



	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	$('input[name=backToListURL]').val(decodeURIComponent("${backToListURL}"));
	window.opener.submitFinished();

});
</script>
<body>

	<form name="myform" id="myform" action="acctDebitNote.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="step1_1_MergeDebitNoteSubmit" />
		
		<input type="hidden" name="debitNo" value="<%=acctDno.getDebitNo() %>" />
		
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">合併Debit Note</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<table border=0 cellspacing=1 cellpadding=6 width="95%"  id="table8">
			<tr>
				<td colspan="6">
				<table border=0 cellspacing=1 cellpadding=6 width="100%" bgcolor=#4aae66 align=left id="table76">
					<tr>
						<td bgcolor=#dff4dd>CustomerNo: </td>
						<td bgcolor=#f4faf3><%=acctDno.getCustomer().getCustNo() %></td>						
						<td bgcolor=#dff4dd>Debit Note Number:</td>
						<td bgcolor=#f4faf3><%=acctDno.getDebitNo() %></td>						
						<td bgcolor=#dff4dd>Section:</td>
						<td bgcolor=#f4faf3><%=acctDno.getSection() %></td>						
						<td bgcolor=#dff4dd>During:</td>
						<td bgcolor=#f4faf3><%=acctDno.getDesSdt() %>~<%=acctDno.getDesEdt() %></td>						
					</tr>
				</table>
				</td>
			</tr>
						


			<tr >
			<td colspan="6">
			<table border=0 cellspacing=1 cellpadding=6 width="100%" bgcolor=#4aae66 align=left id="table76">
				<tr>
				<td bgcolor=#dff4dd width="13%" ><font size="2">請輸入 要被合併的Debit Note No</font></td>
				<td bgcolor=#f4faf3><input type="text" name="mergedDebitNo" /></td>		
				</tr>
				
			</table>
			</td>
			</tr>



<!-- 下方命令列控制區塊 -->			
			<tr>
					<td colspan="8" align=left bgcolor="#FFFFFF">
						<p align="center">
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />


					</td>
				</tr>
<!-- end 下方命令列控制區塊 -->
			</table>
			</div>
	</form>	
</body>
</html>
<%
HibernateSessionFactory.closeSession();
 %>
