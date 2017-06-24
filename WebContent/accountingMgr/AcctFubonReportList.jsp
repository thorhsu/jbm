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

$().ready(function() {
    var rowsCount = "rowsCount";
	selectAll = function (){
		var i = 0;
		$("input[name='debitNotesNos']").each(function() { 
		    i++;
			$(this).attr("checked", true);			
			var lineid = document.getElementById(rowsCount + i);			
			$(lineid).addClass("checkedLine");			
		});
  	};
  	$('#selectAll').click(selectAll);
  	
	selectNon = function (){
		var i = 0; 
		$("input[name='debitNotesNos']").each(function() {
		    i++; 
			$(this).attr("checked", false);
			var lineid = document.getElementById(rowsCount + i);
			$(lineid).removeClass("checkedLine");
		});
  	};
  	$('#selectNon').click(selectNon);
});
// Thor add
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
//Thor

</script>

<div align="center">
	<form action="acctDebitNote.do" method="get">
	<input type="hidden" name="fid" value="funbonReportSubmit">      
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">Fubon Report 查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>

	      <tr>
	        <td>

	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title="Debit Note No"      property="debit_no"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="最後修改日期"      property="modified_date"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	                        
	            <vlh:column title="客戶代號"      property="cust_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="section"      property="section"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="交寄日_起"      property="des_sdt"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄日_訖"      property="des_edt"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Cycle Date_起"      property="cycle_sdt"  sortable="desc" attributes="width=\"7%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Cycle Date_訖"   property="cycle_edt"  sortable="desc"  attributes="width=\"7%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>         
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td height="30" >
	                 <INPUT type="checkbox" name="debitNotesNos"  id="debitNotesNos${stauts.count}" value="<c:out value="${bean.debit_no}" />"  onclick="checkedLine('debitNotesNos${stauts.count}', 'rowsCount${stauts.count}', '${rowStyle}')"/>	            
	                 <a href="acctDebitNote.do?fid=editInit&debitNo=${bean.debit_no}"><c:out value="${bean.debit_no}" /></a>
	            </td>
	            <td ><c:out value="${bean.modified_date}" /></td>
	            <td ><c:out value="${bean.cust_no}" /></td>
	            <td ><c:out value="${bean.section}" /></td>
	            <td ><c:out value="${bean.des_sdt}" /></td>
	            <td ><c:out value="${bean.des_edt}" /></td>
	            <td ><c:out value="${bean.cycle_sdt}" /></td>
	            <td ><c:out value="${bean.cycle_edt}" /></td>   
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
          <tr><td align="center"><input name="selectAll" type="button"  id="selectAll" value="全選" /> 
          | <input name="selectNon" type="button"  id="selectNon" value="清除" /> 
          | <input name="button" type="submit" id="button" value="產生報表" />  
          </td></tr>          
          
        </table></vlh:root>
		</form>
</div>