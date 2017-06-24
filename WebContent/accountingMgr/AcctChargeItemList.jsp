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
<!--
function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}
//-->
// Thor add
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
//Thor

</script>






<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">收費項目查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="acctChargeItem.do" method="get">
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="10%" align="left">item_type</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
					    <select name="itemType" id="itemType"><option value="">全部</option>
							<option value="JOB_BAG">JOB_BAG</option>
							<option value="NON_JOB_BAG">NON_JOB_BAG</option>
							</select>
					</TD>
					<TD class="queryTableCellBKColor" width="9%" align="left" >item_name</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="itemName"  maxLength=15 size=10 name="itemName"></TD>
					<TD class="queryTableCellBKColor" width="9%" align="left" >item_name_tw</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="itemNameTw"  maxLength=15 size=10 name="itemNameTw"></TD>																				
					<TD class="queryTableCellBKColor" width="10%" align="left">每頁筆數</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >						
					    <SELECT id="pageSize" size=1 name="pageSize"> 
							<option value="30" selected >30</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</SELECT></TD>					
				</TR>			
			</TABLE>
            </td>
          </tr>
		 <tr>
	        <td height="30">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" align="center" id="table7">			
				<TR>
					<TD align="right"><input name="button" type="submit"  id="button" value="搜尋" /></TD>
					<TD><input name="button" type="button" onclick="location='acctChargeItem.do?fid=addInit&backToListURL=${backToListURL}'"  id="button" value="新增" /></TD>
				</TR>
			</TABLE>
            </td>
          </tr>
          <input type="hidden" name="includeParams_value" value="itemType,pageSize">
            </form>
	      <tr>
	        <td>

	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title="id"      property="id"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>	            
	            <vlh:column title="item_type"      property="item_type"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="item_name"      property="item_name"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="item_name_tw"      property="item_name_tw"  sortable="desc" attributes="width=\"18%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="title"      property="title"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="sub_title"      property="sub_title"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="ep1_account_code"   property="ep1_account_code"  sortable="desc"  attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>         
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td height="30" ><a href="acctChargeItem.do?fid=view&id=<c:out value="${bean.id}" />"><c:out value="${bean.id}" /> </a></td>
	            <td ><c:out value="${bean.item_type}" /></td>
	            <td ><c:out value="${bean.item_name}" /></td>
	            <td ><c:out value="${bean.item_name_tw}" /></td>
	            <td ><c:out value="${bean.title}" /></td>
	            <td ><c:out value="${bean.sub_title}" /></td>
	            <td ><c:out value="${bean.ep1_account_code}" /></td>
	            <td ><a href="acctChargeItem.do?fid=editInit&id=<c:out value="${bean.id}" />&idList=${idList}&backToListURL=${backToListURL}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('acctChargeItem.do?fid=delete&id=<c:out value="${bean.id}"/>')"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
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