<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
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

<body  onload="self.focus()">
<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">Report 1: 工單列表</td>
          </tr>
	      <tr>
	           <td height="15"><font color="red"> ${message} </font></td>
          </tr>


			<tr>
				<td>
				<table border=0 cellSpacing=1 cellPadding=6 width="100%" bgcolor=#4aae66 align=left id="table76">
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
				<td align="center">
				<input name="button" type="button"  onclick="location='acctDebitNote.do?fid=step2_addInit&debitNo=${acctDno.debitNo}&custNo=${acctDno.customer.custNo}&backToListURL=${backToListURL}'"  id="button" value="新增工單" />
				</td>
			</tr>

	      <tr>
	        <td>

	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title="工單號碼	"      property="job_bag_no"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	                        
	            <vlh:column title="活動名稱	"      property="prog_nm"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="客戶代號"      property="idf_cust_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="Cycle Date"      property="cycle_date"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="Completed Date"      property="completed_date"  sortable="desc" attributes="width=\"18%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Accounts"      property="accounts"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Pages"      property="pages"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>         
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >

	            <td height="30" ><c:out value="${bean.job_bag_no}" /></td>
	            <td height="30" ><c:out value="${bean.prog_nm}" /></td>
	            <td ><c:out value="${bean.idf_cust_no}" /></td>
	            <td ><fmt:formatDate value="${bean.cycle_date}" pattern="yyyy-MM-dd" /> </td>
	            <td ><fmt:formatDate value="${bean.completed_date}" pattern="yyyy-MM-dd" /> </td>
	            <td ><c:out value="${bean.accounts}" /></td>
	            <td ><c:out value="${bean.pages}" /></td> 
	            <td ><a href="acctDebitNote.do?fid=step2_editInit&jobBagNo=<c:out value="${bean.job_bag_no}" />&backToListURL=${backToListURL}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('acctDebitNote.do?fid=step2_delete&jobBagNo=<c:out value="${bean.job_bag_no}"/>&debitNo=${acctDno.debitNo}&custNo=${acctDno.customer.custNo}&backToListURL=${backToListURL}')"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
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