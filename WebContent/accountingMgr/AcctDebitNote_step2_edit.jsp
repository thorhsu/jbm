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
         javascript:history.go(-1)
      else
         location=backToListURL;
   }
   $().ready(function() {
      $('input[name=backToListURL]').val(decodeURIComponent("${backToListURL}"));
   });

</script>
</head>
<%
	String message = (String )request.getAttribute("message");
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	JobBag jobBag= (JobBag)request.getAttribute("jobBag"); 
	AcctDno acctDno= (AcctDno)request.getAttribute("acctDno"); 
	
	List acctSum1List= (List)request.getAttribute("acctSum1List"); //工單收費相目基礎資料    
    List acctCustomerContractList =(List) request.getAttribute("acctCustomerContractList"); //  客戶合約上的收費資訊  
	Customer customer = jobBag.getCustomer();



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
	window.opener.submitFinished();
	

});
</script>
<body>

	<form name="myform" id="myform" action="acctDebitNote.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="step2_editSubmit" />
		
		<input type="hidden" name="debitNo" value="<%=acctDno.getDebitNo() %>" />
		<input type="hidden" name="jobBagNo" value="<%=jobBag.getJobBagNo() %>" />
		

			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">工單收費項目數量維護</td>
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
				<td bgcolor=#dff4dd width="13%" ><font size="2">工單代號</font></td>
				<td bgcolor=#f4faf3><%=jobBag.getJobBagNo() %></td>
				<td bgcolor=#dff4dd width="13%" ><font size="2">活動名稱</font></td>
				<td bgcolor=#f4faf3><%=jobBag.getProgNm() %></td>				
				<td bgcolor=#dff4dd width="13%" ><font size="2">Cycle<BR/>Date</font></td>
				<td bgcolor=#f4faf3><%=Util.getDateFormat( jobBag.getCycleDate() )%></td>
				<td bgcolor=#dff4dd width="13%" ><font size="2">交寄日期</font></td>
				<td bgcolor=#f4faf3><%=Util.getDateFormat( jobBag.getCompletedDate() )%></td>				
				</tr>
				<tr>
				<td bgcolor=#dff4dd colspan="8">
					<table border="0" width="100%" id="table78">
					<tr>
					<td width="112"><font size="2">accounts</font></td>
					<td bgcolor=#f4faf3><font size="2"><%=jobBag.getAccounts() %></font></td>
					<td width="80"><font size="2">pages</font></td>
					<td bgcolor=#f4faf3><font size="2"><%=jobBag.getPages() %></font></td>
					<td><font size="2">sheets</font></td>
					<td bgcolor=#f4faf3 width="115"><font size="2"><%=jobBag.getSheets() %></font></td>
					<td></td>
					<td bgcolor=#f4faf3></td>
					</tr>
					<tr>
					<td width="112"><font size="2">Feeder2</font></td>
					<td bgcolor=#f4faf3><font size="2"><%=jobBag.getFeeder2() %></font></td>
					<td width="80"><font size="2">Feeder3</font></td>
					<td bgcolor=#f4faf3><font size="2"><%=jobBag.getFeeder3() %></font></td>
					<td><font size="2">Feeder4</font></td>
					<td bgcolor=#f4faf3 width="115"><font size="2"><%=jobBag.getFeeder4() %></font></td>
					<td>Feeder5</td>
					<td bgcolor=#f4faf3><%=jobBag.getFeeder5() %></td>
					</tr>
					<tr>
					<td width="112"><font size="2">Tray1</font></td>
					<td bgcolor=#f4faf3><font size="2"><%=jobBag.getTray1() %></font></td>
					<td width="80"><font size="2">Tray2</font></td>
					<td bgcolor=#f4faf3><font size="2"><%=jobBag.getTray2() %></font></td>
					<td><font size="2">Tray3</font></td>
					<td bgcolor=#f4faf3 width="115"><font size="2"><%=jobBag.getTray3() %></font></td>
					<td>Tray4</td>
					<td bgcolor=#f4faf3><%=jobBag.getTray4() %></td>
					</tr>										
					</table>
				</td>
				</tr>
			</table>
			</td>
			</tr>


			<tr>
                 <td class="editTableLabelCellBKColor" width="15%" colspan="6">			<HR></td>
				
			</tr>			

			<tr>
                 <td class="editTableLabelCellBKColor" width="60%" align="center">收費項目</td>
				 <td class="editTableContentCellBKColor" width="5%" align="center">單價金額 (小數6)	</td>	
<!-- 
				 <td class="editTableContentCellBKColor" width="5%">是否收費</td>
 -->				 
				 <td class="editTableContentCellBKColor" width="5%" align="center">數量</td>	 				 		 					 	
			</tr>
<% for(int i=0; i<acctCustomerContractList.size(); i++)  { 
	Double unitPrice =0D;
	AcctCustomerContract acctCustomerContract = (AcctCustomerContract)acctCustomerContractList.get(i);
	Integer acctCustomerContractId = acctCustomerContract.getId();
	AcctChargeItem acctChargeItem = acctCustomerContract.getAcctChargeItem();
	unitPrice = acctCustomerContract.getUnitPrice();	

	Integer sumQty=0;
	String isCharge ="N";
	Integer acctSum1Id=0;
	//找出該工單是否有此收費項目
	for (int j=0; j<acctSum1List.size();j++) {
		 AcctSum1 _acctSum1 = (AcctSum1)acctSum1List.get(j);
		 if (null!= _acctSum1.getAcctCustomerContract() && _acctSum1.getAcctCustomerContract().getId().compareTo(acctCustomerContract.getId()) ==0 ) {
		 	acctSum1Id = _acctSum1.getId();

		 	sumQty = _acctSum1.getSumQty();
		 	isCharge = "Y";
		 }
	}

%>			
			<tr>
                 <td class="editTableLabelCellBKColor"  >
                 <input type="hidden" name="sum1Ids" value="<%=acctSum1Id %>" />
                 <input type="hidden" name="chargeItemIds" value="<%=acctChargeItem.getId() %>" />
                 <input type="hidden" name="acctCustomerContractIds" value="<%=acctCustomerContractId %>" />
                 <%=acctChargeItem.getItemName() + "|" +  acctChargeItem.getItemNameTw() %>
                 </td>
				 <td class="editTableContentCellBKColor" align="right">
				 <input type="hidden" name="unitPrices" value="<%=unitPrice %>" />
				 <%=unitPrice %></td>
<!-- 				 
                 <td class="editTableContentCellBKColor"  >

                 <input type="checkbox" id="isCharges" name="isCharges" value="<%=isCharge %>" <% if (isCharge.equalsIgnoreCase("Y")) out.println("checked");%> />
                 </td>
 -->
				 <td class="editTableContentCellBKColor"  align="right"><input id="sumQties" size="5" style="text-align:right;" name="sumQties" value="<%=sumQty%>" <%=disabledVIEW %> /></td>
			</tr>
<%} %>
<!-- 下方命令列控制區塊 -->			
			<tr>
					<td colspan="8" align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<%=SessionUtil.getPageInfo(request, jobBag.getJobBagNo(), "job_bag_no")%>
						<input name="button6" type="button" onclick="location='acctDebitNote.do?fid=step2_editInit&jobBagNo=<%=SessionUtil.getPrevRecord(request, jobBag.getJobBagNo(), "job_bag_no") %>&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						<input name="button6" type="submit"  id="button6" value="更新" />
						<!-- input name="button6" type="button"  onclick="location='<%//=backToListURL%>'"  id="button6" value="回列表" / -->
						<input type="button"  onclick="backToList()"  value="回列表" />
						<input name="button6" type="button" onclick="location='acctDebitNote.do?fid=step2_editInit&jobBagNo=<%=SessionUtil.getNextRecord(request, jobBag.getJobBagNo(), "job_bag_no") %>&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />



<%  } else { %>
						<input type="button"  onclick="backToList()"  value="回列表" />
<%  } %>
					</td>
				</tr>
<!-- end 下方命令列控制區塊 -->
			</table>

	</form>	
</body>
</html>
<%

   HibernateSessionFactory.closeSession();

%>
