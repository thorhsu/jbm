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

function batchExportToEP1(){
   $("#fid").val("batchExportToEP1");
   document.getElementById("acctDbNoteForm").submit();
}
//-->
// Thor add
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
//Thor

</script>


<div align="center">
       <form action="acctDebitNote.do" method="get" id="acctDbNoteForm" >
       
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">Debit Note 查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" id="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >

					<TD class="queryTableCellBKColor" width="10%" align="left">客戶代號</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="custNo" maxLength=15 size=10 name="custNo"></TD>
					<TD class="queryTableCellBKColor" width="9%" align="left" >Debit Note No</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="debitNo"  maxLength=15 size=10 name="debitNo"></TD>									
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
	        <td height="30" align="center">
			   <input name="button" type="submit"  id="button" value="搜尋" />
			   &nbsp;&nbsp;&nbsp;&nbsp;
               <input name="button" type="button" onclick="location='acctDebitNote.do?fid=addInit&backToListURL=${backToListURL}'"  id="button" value="新增" />
			   <input type="hidden" name="includeParams_value" value="custNo,debitNo,pageSize" />
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="button" type="button" onclick="batchExportToEP1()"  id="button" value="批次轉出EP1" />
				
            </td>
          </tr>
          
          
	      <tr>
	        <td>
	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >         
			<tr >
			    <vlh:root value="dataList"  url="?" includeParameters="*">
	            <vlh:header>
	            <vlh:column title=""  property=""  attributes="width=\"5%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Debit Note No"      property="debit_no"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	                        
	            <vlh:column title="最後修改日期"      property="modified_date"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="客戶代號"      property="cust_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="section"      property="section"  sortable="desc" attributes="width=\"4%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="交寄日_起"      property="des_sdt"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄日_訖"      property="des_edt"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Cycle Date_起"      property="cycle_sdt"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Cycle Date_訖"   property="cycle_edt"  sortable="desc"  attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="轉出EP1"      property="ep1"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="確認轉出"   property="ep1_confirm"  sortable="desc"  attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            </vlh:header>
              </tr>         
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
                <td><input type="checkbox"  name="debitNotesNos" id="debitNotesNos${stauts.count}" value="${bean.debit_no}" onclick="checkedLine('debitNotesNos${stauts.count}', 'rowsCount${stauts.count}', '${rowStyle}')"/></td>
	            <td height="30" ><c:out value="${bean.debit_no}" /></td>
	            <td ><c:out value="${bean.modified_date}" /></td>
	            <td ><c:out value="${bean.cust_no}" /></td>
	            <td ><c:out value="${bean.section}" /></td>
	            <td ><c:out value="${bean.des_sdt}" /></td>
	            <td ><c:out value="${bean.des_edt}" /></td>
	            <td ><c:out value="${bean.cycle_sdt}" /></td>
	            <td ><c:out value="${bean.cycle_edt}" /></td>   
	            <td ><c:out value="${bean.ep1}" /></td>	            	             	                	            
	            <td ><c:out value="${bean.ep1_confirm}" /></td>	            
	            <td ><a href="acctDebitNote.do?fid=editInit&debitNo=<c:out value='${bean.debit_no}' />&backToListURL=${backToListURL}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('acctDebitNote.do?fid=delete&debitNo=<c:out value="${bean.debit_no}"/>&backToListURL=${backToListURL}')"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td> 
				<td ><input name="button1" type="button" onclick="location='acctDebitNote.do?fid=exportToEP1&debitNo=<c:out value="${bean.debit_no}" />&backToListURL=${backToListURL}'" id="button" value="轉出EP1" /></td>	
				<td ><input name="button2" type="button" onclick="location='acctDebitNote.do?fid=confirmToEP1&debitNo=<c:out value="${bean.debit_no}" />&backToListURL=${backToListURL}'" id="button" value="確認轉出EP1" /></td>	
				           
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
            </table>
            </td>
          </tr>
          </vlh:root>
        </table>
      </form>
</div>