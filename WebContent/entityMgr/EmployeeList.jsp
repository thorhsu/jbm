<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	// List accountList = (List)request.getAttribute("dataList");
%>

<script type="text/javascript">

function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
</script>


<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">帳號查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="employee.do" method="get">
          <input type="hidden" name="includeParams_value" value="empNo,CName,EName" />
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >員工代號</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="empNo"  maxLength=15 size=10 name="empNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">中文姓名</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="CName" maxLength=15 size=10 name="CName"></TD>
					<TD class="queryTableCellBKColor" width="8%" align="left" >英文姓名</TD>
					<TD class="queryTableCellBKColor" width="26%" align="left" ><INPUT id="EName" maxLength=15 size=10 name="EName"></TD>
					<TD class="queryTableCellBKColor" width="7%" align="left">&nbsp;狀態</TD>
					<TD class="queryTableCellBKColor" width="27%" align="left">
						<select size="1" name="queryEnabled"><option selected value="ALL">全部</option>
						<option value="ENABLED">啟用</option>
						<option value="DISABLED">關閉</option>
						</select>
					</TD>
				</TR>
			</TABLE>
            </td>
          </tr>
		 <tr>
	        <td height="30">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" align="center" id="table7">			
				<TR>
					<TD align="right"><input name="button" type="submit"  id="button" value="搜尋" /></TD>
					<TD><input name="button" type="button" onclick="location='employee.do?fid=addInit&backToListURL=${backToListURL}'"  id="button" value="新增" /></TD>
				</TR>
			</TABLE>
            </td>
          </tr>
            </form>
	      <tr>
	        <td>
	        

	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title="員工代號"      property="emp_no"  sortable="desc" attributes="width=\"19%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="帳號"      property="user_id"  sortable="desc" attributes="width=\"20%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="中文姓名"      property="c_name"  sortable="desc" attributes="width=\"16%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="英文姓名"      property="e_name"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="部門"      property="dept"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="職稱"   property="title"  sortable="desc"  attributes="width=\"13%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="分機號碼"      property="ext"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="住家電話"   property="h_tel"  sortable="desc"  attributes="width=\"13%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="狀態"   property="enabled"  sortable="desc"  attributes="width=\"13%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td height="30" ><a href="employee.do?fid=view&empNo=<c:out value="${bean.emp_no}" />&backToListURL=${backToListURL}"><c:out value="${bean.emp_no}" /> </a></td>
	            <td ><c:out value="${bean.user_id}" /></td>
	            <td ><c:out value="${bean.c_name}" /></td>
	            <td ><c:out value="${bean.e_name}" /></td>
	            <td ><c:out value="${bean.dept}" /></td>
	            <td ><c:out value="${bean.title}" /></td>
	            <td ><c:out value="${bean.ext}" /></td>
	            <td ><c:out value="${bean.h_tel}" /></td>
	            <td ><c:out value="${bean.enabled}" /></td>
	            <td ><a href="employee.do?fid=editInit&empNo=<c:out value="${bean.emp_no}" />&backToListURL=${backToListURL}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('employee.do?fid=delete&empNo=<c:out value="${bean.emp_no}"/>')"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
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