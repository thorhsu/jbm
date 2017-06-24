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
	        <td height="26" class="title">郵資價格查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="lgPrice.do" method="get">
	      <tr>
	        <td height="30">
	         <input type="hidden" name="includeParams_value" value="pageSize,lgType,lgMailCategory" />
	        <input type="hidden" name="fid" value="list">
			<table border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<tr >
					<td class="queryTableCellBKColor" width="10%" align="left">郵資單種類</td>
					<td class="queryTableCellBKColor" width="17%" align="left" >
					      <select id="lgType" size=1 name="lgType">
					         <option value="all" >全部</option>
					         <c:forEach var="code" items="${lgTypesCode}">
					             <option value="${code.id}" >${code.codeValueTw}</option>
					         </c:forEach>
					      </select>
					</td>
					<td class="queryTableCellBKColor" width="10%" align="left">交寄方式</td>
					<td class="queryTableCellBKColor" width="17%" align="left" >
					      <select id="lgMailCategory" size=1 name="lgMailCategory">
					         <option value="all" >全部</option>
					         <c:forEach var="code" items="${lgMailCategoriesCode}">
					             <option value="${code.id}" >${code.codeValueTw}</option>
					         </c:forEach>
					      </select>
					</td>
					<td class="queryTableCellBKColor" width="10%" align="left">每頁筆數</td>
					<td class="queryTableCellBKColor" width="17%" align="left" >						
					    <select id="pageSize" size=1 name="pageSize"> 
							<option value="30" selected >30</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select></td>					
				</tr>			
			</table>
            </td>
          </tr>
		 <tr>
	        <td height="30">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" align="center" id="table7">			
				<TR>
					<TD align="right"><input name="button" type="submit"  id="button" value="搜尋" /></TD>
					<TD><input name="button" type="button" onclick="location='lgPrice.do?fid=addInit&backToListURL=${backToListURL}'"  id="button" value="新增" /></TD>
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
	            <vlh:column title="id "      property="id"  sortable="asc" attributes=" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="郵資單種類"      property="code_value_tw"  sortable="desc" attributes=" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="郵資單顯示名稱"  property="lg_type_name"  sortable="desc" attributes=" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄方式"       property="lg_mail_category_name"  sortable="desc" attributes=" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="價格"      property="price"  sortable="desc" attributes=" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="重量"      property="weight"  sortable="desc" attributes=" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td height="30" ><c:out value="${bean.id}" /></td>
	            <td ><c:out value="${bean.code_value_tw}" /></td>
	            <td ><c:out value="${bean.lg_type_name}" /></td>
	            <td align="left" ><c:out value="${bean.lg_mail_category_name}" /></td>
	            <td align="left" ><c:out value="${bean.price}" /></td>
	            <td align="left" ><c:out value="${bean.weight}" /></td>
	            <td ><a href="lgPrice.do?fid=editInit&id=<c:out value="${bean.id}" />&idList=${idList}&backToListURL=${backToListURL}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('lgPrice.do?fid=delete&id=<c:out value="${bean.id}"/>&backToListURL=${backToListURL}')"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
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
          </vlh:root>
        </table>
</div>