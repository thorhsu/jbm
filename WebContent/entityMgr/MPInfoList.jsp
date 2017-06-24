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
	        <td height="26" class="title">裝封資料查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="mpinfo.do" method="get">
          <input type="hidden" name="includeParams_value" value="mpNo,custNo,pageSize" />
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >封裝代號</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="mpNo"  maxLength=15 size=10 name="mpNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">客戶代號</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="custNo" maxLength=15 size=10 name="custNo" ></TD>
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
					<TD><input name="button" type="button" onclick="location='mpinfo.do?fid=addInit&backToListURL=${backToListURL}'"  id="button" value="新增" /></TD>
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
	            <vlh:column title="裝封代號"      property="mp_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="客戶代號"      property="idf_cust_no"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="產品說明"      property="prodmemo"  sortable="desc" attributes="width=\"18%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="物料版號"      property="print_code"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="插件"      property="select_fg"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="分區"      property="zip_fg"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="郵戳設定"   property="stamp_set_fg"  sortable="desc"  attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td height="30" ><a href='mpinfo.do?fid=view&mpNo=<c:out value="${bean.mp_no}" />&backToListURL=${backToListURL}'><c:out value="${bean.mp_no}" /> </a></td>
	            <td ><a href="customer.do?fid=view&custNo=<c:out value="${bean.idf_cust_no}" />&backToListURL=${backToListURL}"><c:out value="${bean.idf_cust_no}" /> </a></td>
	            <td align="left" ><c:out value="${bean.prodmemo}" /></td>
	            <td align="left" ><c:out value="${bean.print_code}" /></td>
	            <td align="left" ><c:out value="${bean.select_fg}" /></td>
	            <td align="left" ><c:out value="${bean.zip_fg}" /></td>
	            <td align="left" ><c:out value="${bean.stamp_set_fg}" /></td>
	            <td ><a href="mpinfo.do?fid=editInit&mpNo=<c:out value="${bean.mp_no}" />&idList=${idList}&backToListURL=${backToListURL}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('mpinfo.do?fid=delete&mpNo=<c:out value="${bean.mp_no}"/>&backToListURL=${backToListURL}')"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
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