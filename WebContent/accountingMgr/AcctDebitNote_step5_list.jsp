<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
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
	AcctDno acctDno= (AcctDno)request.getAttribute("acctDno");
%>

<script type="text/javascript">
<!--
function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}
$().ready(function() {
    window.opener.submitFinished();
    var desSdt = "${debitNoteFullInfo.desSdt}";
	var desEdt = "${debitNoteFullInfo.desEdt}";
	var cycleSdt = "${debitNoteFullInfo.cycleSdt}";
	var cycleEdt = "${debitNoteFullInfo.cycleEdt}";
	
	 
    var amountByIntegerToSum = '<c:out value="${debitNoteFullInfo.amountByIntegerToSum}" default="" />';
    var amountBySumToInteger = "${debitNoteFullInfo.amountBySumToInteger}";
    var tax = "${debitNoteFullInfo.amountTax}";
    var amountExcludeTax = "${debitNoteFullInfo.amountExcludeTax}";
    var amountIncludeTax = "${debitNoteFullInfo.amountIncludeTax}";    
    var refund = "${acctDno.refund}";
    var refundamt = "${acctDno.refundamt}";
    if( (amountByIntegerToSum == "0" || amountByIntegerToSum == '') && (amountBySumToInteger == "0" || amountBySumToInteger == "") && tax == "" && amountExcludeTax == "0" && amountIncludeTax == "0" && desSdt == "" && desEdt == "" && cycleSdt == "" && cycleEdt == ""){
    }else{
       window.opener.changeDbNoteVal(amountByIntegerToSum, amountBySumToInteger, tax, amountExcludeTax, amountIncludeTax, desSdt, desEdt, cycleSdt, cycleEdt);
    }
	//window.opener.location.reload(true);
 });
var prevClassName = "";
function changeClass(overLineId, className){
   
   var overLine = document.getElementById(overLineId);
   if(prevClassName == ""){
      prevClassName = $(overLine).attr("class");
   }
   if(className != null){
      $(overLine).attr("class", className);
   }else if(prevClassName != ""){
      $(overLine).attr("class", prevClassName);
      prevClassName = "";
   }
   //alert(className);
}
//-->
</script>
<body>

<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">編輯列舉項目</td>
          </tr>
	      <tr>
	           <td height="15"><font color="red"> ${message} </font></td>
          </tr>


			<tr>
				<td>
				<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" bgColor=#4aae66 align=left id="table76">
					<tr>
						<td bgcolor=#dff4dd>Cust No: </td>
						<td bgcolor=#f4faf3><%=acctDno.getCustomer().getCustNo() %></td>						
						<td bgcolor=#dff4dd>Debit Note No:</td>
						<td bgcolor=#f4faf3><%=acctDno.getDebitNo() %></td>						
						<td bgcolor=#dff4dd>Section:</td>
						<td bgcolor=#f4faf3><%=acctDno.getSection() %></td>						
						<td bgcolor=#dff4dd>During:</td>
						<td bgcolor=#f4faf3>
						   <table>
						      <tr>
						         <td align="right">
						                               交寄日：
						         </td>
						         <td>
						            <%= (acctDno.getDesSdt() == null)? "" : acctDno.getDesSdt() %>~<%= (acctDno.getDesEdt() == null)? "" : acctDno.getDesEdt()%>
						         </td>
						      </tr>
						      <tr> 
						         <td align="right">
						             Cycle Date：
						         </td>
						         <td>
						              <%=acctDno.getCycleSdt() == null? "" : acctDno.getCycleSdt() %>~<%=acctDno.getCycleEdt() == null? "" : acctDno.getCycleEdt() %>
						         </td>
						      </tr> 
						   </table>
						</td>						
					</tr>
				</table>
				</td>
			</tr>
		 <tr>
	        <td height="30">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" align="center" id="table7">			
				<TR>
					<TD align="center"><input name="button" type="button" onclick="location='acctDebitNote.do?fid=step5_addInit&debitNo=<%=acctDno.getDebitNo() %>&backToListURL=${backToListURL}'"  id="button" value="新增" /></TD>
				</TR>
			</TABLE>
            </td>
          </tr>
          
          
	      <tr>
	        <td>
	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title="收費代號"      property="id"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	                        
	            <vlh:column title="收費項目"      property="item_name"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="收費項目_TW"      property="item_name_tw"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="價格"      property="unit_price"  sortable="desc" attributes="width=\"18%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="數量"      property="sum_qty"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="總價"      property="sub_sum"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="cost_center"      property="cost_center"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>         
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >	          
	          <tr id="rowsCount${stauts.count}" >

	            <td height="30" ><c:out value="${bean.id}" /></td>
	            <td ><c:out value="${bean.item_name}" /></td>
	            <td ><c:out value="${bean.item_name_tw}" /></td>
	            <td align="right"><c:out value="${bean.unit_price}" /></td>
	            <td align="right"><c:out value="${bean.sum_qty}" /></td>
	            <td align="right"><c:out value="${bean.sub_sum}" /></td>
	            <td ><c:out value="${bean.cost_center}" /></td>
	            <td ><a href="acctDebitNote.do?fid=step5_editInit&sum2Id=<c:out value="${bean.id}" />&debitNo=<%=acctDno.getDebitNo() %>&backToListURL=${backToListURL}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('acctDebitNote.do?fid=step5_delete&sum2Id=<c:out value="${bean.id}"/>&backToListURL=${backToListURL}')"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
              </tr>
              </c:forEach>
	          
	          </table>
	           
	          </td>
          </tr>
	      <tr>
	        <td>&nbsp;</td>
          </tr>
	      <tr>
	        <td>
	        
	        
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td height="30" style="text-align:center;">總共 <span class="red12"><c:choose>
                        <c:when test="${dataList.valueListInfo.totalNumberOfEntries}==0">
                          0
                        </c:when>
                        <c:otherwise>
                          <c:out value="${dataList.valueListInfo.totalNumberOfEntries}" />
                        </c:otherwise>
                      </c:choose></span> 筆, [ <c:out value="${dataList.valueListInfo.pagingPage}" />
                      /
                      <c:out value="${dataList.valueListInfo.totalNumberOfPages}" />
                      	] 頁</td>
              </tr>
	          <tr>
	            <td height="30" style="text-align:center;">
	            <div align="center">
	            <vlh:paging pages="5">
                        <c:out value="${page}" />
                        &nbsp;
                </vlh:paging>
                </div>
	            </td>
              </tr>
            </table></td>
          </tr>
        </table></vlh:root>
</div>
</body>
</html>
<%
HibernateSessionFactory.closeSession();
 %>
