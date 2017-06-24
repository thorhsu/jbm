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
	        <td height="26" class="title">Section跟工單對應規則查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="sectionMapping.do" method="get">
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >

					<TD class="queryTableCellBKColor" width="10%" align="left">客戶代號</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="custNo" maxLength=15 size=10 name="custNo"></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">Section</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="section" maxLength=15 size=10 name="section"></TD>					
					<TD class="queryTableCellBKColor" width="9%" align="left" >工作代號</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="jobCodeNo"  maxLength=15 size=10 name="jobCodeNo"></TD>										
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
					<TD align="center"><input name="button" type="submit"  id="button" value="搜尋" /></TD>
					
				</TR>
			</TABLE>
            </td>
          </tr>
          <input type="hidden" name="includeParams_value" value="custNo,jobCodeNo,section,pageSize">
            </form>
	      <tr>
	        <td>
			<form action="sectionMapping.do" method="get" name="myform"> 
			<input type="hidden" name="fid" value="batchSectionToJobCodeMappingSumbit">	  

	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title="客戶代號"      property="idf_cust_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="Section"      property="section"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="工作代號"      property="job_code_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="cost center"      property="cost_center"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="活動名稱"      property="prog_nm"  sortable="desc" attributes="width=\"25%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr  id="rowsCount${stauts.count}" >
	            <td height="30" >
	            <input type="hidden" name="mappingIds" id="mappingIds" value="<c:out value="${bean.id}" />">
	            <input type="hidden" name="custNos" id="custNos" value="<c:out value="${bean.idf_cust_no}" />">
	            <c:out value="${bean.idf_cust_no}" />
	            </td>
	            <td ><INPUT id="sections"  maxLength=10 name="sections" value="<c:out value="${bean.section}" />"></td>
	            <td  >
	            <input type="hidden" name="jobCodeNos" id="jobCodeNos" value="<c:out value="${bean.job_code_no}" />">
	            <c:out value="${bean.job_code_no}" />
	            </td>	            
	            <td ><c:out value="${bean.cost_center}" /></td>	            
	            <td ><c:out value="${bean.prog_nm}" /></td>
	            <td ><a href="sectionMapping.do?fid=editInit&id=<c:out value="${bean.receiver_id}" />&custNo=<c:out value="${bean.idf_cust_no}" />&section=<c:out value="${bean.section}" />&backToListURL=${backToListURL}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
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
          <tr><TD align="center">
          <input name="button" type="submit"  id="button" value="批次修改" />
          </td></tr>   
          </form>       
        </table></vlh:root>
</div>