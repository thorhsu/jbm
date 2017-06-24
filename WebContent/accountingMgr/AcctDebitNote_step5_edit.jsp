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
<script type="text/javascript">
   function changeClass(overLineId, className){
      var overLine = document.getElementById(overLineId);
      $(overLine).attr("class", className);
      //alert(className);
   }
   function backToList(){
      var backToListURL = decodeURIComponent("${backToListURL}");
      if(backToListURL.indexOf("null") >= 0)
         javascript:history.go(-1);
      else
         location = backToListURL;
   }
   $().ready(function() {
      $('input[name=backToListURL]').val(decodeURIComponent("${backToListURL}"));
      window.opener.submitFinished();
   });

</script>
</head>
<%
	String message = (String )request.getAttribute("message");
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	AcctSum2 acctSum2= (AcctSum2)request.getAttribute("acctSum2"); 
	AcctDno acctDno= (AcctDno)request.getAttribute("acctDno"); 
	List chargeItemList= (List)request.getAttribute("chargeItemList"); //列舉收費項目


	Double subSum = 0D;
	if (null!= acctSum2.getTotal() )
		subSum = acctSum2.getTotal();

	//編輯模式 判斷
	String fid="step5_editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="step5_addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		acctSum2.setId(null);
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate({
	  rules: {
	    "calculatUnitPrice": {
	      number: true
	    },
		 "sumQty": {
	      number: true
	    }
	  }
	});
	

	//合計
	calculatSum = function (){
	    $('#subSum').val( $('#calculatUnitPrice').val() * $('#sumQty').val() );
  	};
  	$('#calculatUnitPrice').blur(calculatSum);	
  	$('#sumQty').blur(calculatSum);	  	
});
</script>
<body>
	<form name="myform" id="myform" action="acctDebitNote.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="<%=fid %>" />
		<input type="hidden" name="debitNo" value="<%=acctDno.getDebitNo() %>" />
		
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">列舉項目維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>


			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" bgColor=#4aae66 align=center id="table76">
				<tr>
					<td bgColor=#dff4dd>CustomerNo: </td>
					<td bgColor=#f4faf3><%=acctDno.getCustomer().getCustNo() %></td>						
					<td bgColor=#dff4dd>Debit Note Number:</td>
					<td bgColor=#f4faf3><%=acctDno.getDebitNo() %></td>						
					<td bgColor=#dff4dd>Section:</td>
					<td bgColor=#f4faf3><%=acctDno.getSection() %></td>						
					<td bgColor=#dff4dd>During:</td>
					<td bgColor=#f4faf3><%=acctDno.getDesSdt() %>~<%=acctDno.getDesEdt() %></td>						
				</tr>
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" bgColor=#4aae66 align=center id="table76">
				<tr>
					<td bgColor=#dff4dd><HR></td>					
				</tr>
			</table>			

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>收費代號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="sum2Id" size="40"  name="sum2Id" value="<%=acctSum2.getId() %>" <%=disabledPK %> <%=disabledVIEW %> ><span id="msg_pk_exist"></span>
                 	</TD>
			</TR>
			
			<TR>
<TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>
                 收費項目</TD>
				 <TD class="editTableContentCellBKColor" align="left">
					 <SELECT name="acctChargeItem" size="1" <%=disabledVIEW %> class="required">
					 <option value="" >請選擇</option>
<% for (int i=0; i<chargeItemList.size(); i++) {
	AcctChargeItem _acctChargeItem = (AcctChargeItem)chargeItemList.get(i);
	String selectString = "";
	if (null!= acctSum2.getAcctChargeItem() && acctSum2.getAcctChargeItem().getId().compareTo(_acctChargeItem.getId()) ==0 ) 
		selectString="selected";
		
		

%>					 
							<option value="<%=_acctChargeItem.getId() %>" <%=selectString %> >
								<%=_acctChargeItem.getItemName() + "|" + _acctChargeItem.getItemNameTw() %>
							</option>
<% }%>	
					</SELECT>
				</TD>
			</TR>

			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >價格</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left >
					<INPUT id="calculatUnitPrice" size="40"  name="calculatUnitPrice" value="<%=Util.getDecimalFormatWithThousandSeparator(acctSum2.getUnitPrice())%>" <%=disabledVIEW %> >
				</TD>
			</TR>

			
			<TR>				
                 <TD class="editTableLabelCellBKColor" width="10%" >數量</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left >
					<INPUT id="sumQty" size="40"  name="sumQty" value="<%=Util.getDecimalFormatWithThousandSeparator(acctSum2.getSumQty()) %>" <%=disabledVIEW %> >
				</TD>				
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >總價</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left >
					<INPUT id="subSum" size="40"  name="subSum" value="<%=Util.getDecimalFormatWithThousandSeparator(subSum) %>" <%=disabledVIEW %> <%=disabledPK %>>
				</TD>
			</TR>

			
			<TR>				
                 <TD class="editTableLabelCellBKColor" width="10%" >Cost Center (Date)</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left >
					<INPUT id="costCenter" size="40" maxlength="8" name="costCenter" value="<%=Util.getStringFormat(acctSum2.getCostCenter()) %>" <%=disabledVIEW %> >
				</TD>				
			</TR>
			
			<TR>				
                 <TD class="editTableLabelCellBKColor" width="10%" >printCode</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left >
					<INPUT id="printCode" size="40"  name="printCode" value="<%=Util.getStringFormat(acctSum2.getPrintCode()) %>" <%=disabledVIEW %> >
				</TD>				
			</TR>			
			

<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan=4 align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<%=SessionUtil.getPageInfo(request, acctSum2.getId().toString(), "id")%>
						<input name="button6" type="button" onclick="location='acctDebitNote.do?fid=step5_editInit&sum2Id=<%=SessionUtil.getPrevRecord(request, acctSum2.getId().toString(), "id") %>&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						<input name="button6" type="submit"  id="button6" value="更新" />
						<!-- input name="button6" type="button"  onclick="location='<%//=backToListURL%>'"  id="button6" value="回列表" / -->
						<input  type="button"  onclick="backToList()"  value="回列表" />
						<input name="button6" type="button" onclick="location='acctDebitNote.do?fid=step5_editInit&sum2Id=<%=SessionUtil.getNextRecord(request, acctSum2.getId().toString(), "id") %>&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />


<%  } else { %>
                 <input  type="button"  onclick="backToList()"  value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>

			</div>
	</form>		
</body>
</html>
<%

HibernateSessionFactory.closeSession();

%>
