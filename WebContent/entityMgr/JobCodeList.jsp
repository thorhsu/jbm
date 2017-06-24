<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	 //List dataList = (List)request.getAttribute("dataList");
	 CustomerService customerService = CustomerService.getInstance();
%>

<script type="text/javascript">

function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}
//Thor add
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
//Thor

</script>

<script type="text/javascript"> 
$().ready(function() {
    var rowsCount = "rowsCount";
	selectAll = function (){
	        var i = 0;
			$("input[name='jobCodeNos']").each(function() { 
				$(this).attr("checked", true);
				i++;
				var lineid = document.getElementById(rowsCount + i);  
				$(lineid).addClass("checkedLine"); 
			}); 
  	};
  	$('#selectAll').click(selectAll);
  	
	selectNon = function (){
	        var i = 0;
			$("input[name='jobCodeNos']").each(function() { 
				$(this).attr("checked", false);
				i++;
				var lineid = document.getElementById(rowsCount + i);
				$(lineid).removeClass("checkedLine");
			}); 
  	};
  	$('#selectNon').click(selectNon);
});
</script>

<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">工單樣本查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="jobcode.do" method="get">
          <input type="hidden" name="includeParams_value" value="custNo,pageSize,jobCodeNo,isEnabledContract,jobCodeType,custConfirm" />
          <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align="left" id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="5%" align="left" >工作代號</TD>
					<TD class="queryTableCellBKColor" width="10%" align="left" ><INPUT id="jobCodeNo"  maxLength=15 size=10 name="jobCodeNo" ></TD>
					<TD class="queryTableCellBKColor" width="5%" align="left">客戶代號</TD>
					<TD class="queryTableCellBKColor" width="10%" align="left" ><INPUT id="custNo" maxLength=15 size=10 name="custNo" ></TD>
					<TD class="queryTableCellBKColor" width="5%" align="left">有效工作資料</TD>
					<TD class="queryTableCellBKColor" width="10%" align="left" ><select name="isEnabledContract"><option value="0">無效</option><option value="1">有效</option></select></TD>
					<TD class="queryTableCellBKColor" width="5%" align="left">需客戶確認</TD>
					<TD class="queryTableCellBKColor" width="10%" align="left" >
					     <select name="custConfirm" id="custConfirm">
					         <option value="0">全部</option><option value="1">需要</option>
					     </select>
					</TD>
					<TD class="queryTableCellBKColor" width="5%" align="left">工作種類</TD>
					<TD class="queryTableCellBKColor" width="10%" align="left" >
					   <select name="jobCodeType" id="jobCodeType">
					      <option value="">全部</option>
					      <c:forEach var="jobCodeType" items="${jobCodeTypeCodes}" varStatus="stauts" >
					          <option value="${jobCodeType.id}">${jobCodeType.codeValueTw}</option>
					      </c:forEach>					        
					   </select>
					</TD>
					<TD class="queryTableCellBKColor" width="5%" align="left">每頁筆數</TD>
					<TD class="queryTableCellBKColor" width="10%" align="left" >						
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
            </form>
	      <tr>
	        <td>
	        
			<form action="jobcode.do" method="get">
			<input type="hidden" name="includeParams_value" value="custNo,pageSize,jobCodeNo,isEnabledContract,custConfirm" />
			<input type="hidden" name="fid" value="batchEditSumbit">
	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title=""    attributes="width=\"2%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>	            
	            <vlh:column title="工作樣本"      property="job_code_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="活動名稱"      property="prog_nm"  sortable="desc" attributes="width=\"20%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Laser<BR>Printing"      property="is_lp"  sortable="desc" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="MP/PS/DM"   property="mp_dm_ps"  sortable="desc"  attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Dispatch"      property="is_lg"  sortable="desc" attributes="width=\"5%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="信紙代號"      property="idf_lp_no1"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="封裝代號"      property="idf_mp_no1"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="壓封代號"   property="idf_ps_no"  sortable="desc"  attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="郵資單<BR>代號"      property="idf_lg_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="管制表<BR>代號"   property="idf_lc_no"  sortable="desc"  attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="fileName"      property="filename"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>	            
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >

                <td ><INPUT type="checkbox" name="jobCodeNos"  id="jobCodeNos${stauts.count}" value="<c:out value="${bean.job_code_no};${stauts.index}" />" onclick="checkedLine('jobCodeNos${stauts.count}', 'rowsCount${stauts.count}', '${rowStyle}')" /></td>
	            <td height="30"><a href="jobcode.do?fid=view&idList=${idList}&backToListURL=<c:out value='${backToListURL}' default='' />&jobCodeNo=<c:out value="${bean.job_code_no}" />"><c:out value="${bean.job_code_no}" /></a></td>
	            <td align="left" ><c:out value="${bean.prog_nm}" /></td>
	            <td ><c:out value="${bean.is_lp}" /></td>
	            <td ><c:out value="${bean.mp_dm_ps}" /></td>
	            <td ><c:out value="${bean.is_lg}" /></td>
	            <td ><INPUT value="<c:out value="${bean.idf_lp_no1}" />" type="text" id="lpNos" name="lpNos" size="4">  
	            </td>
	            <td ><INPUT value="<c:out value="${bean.idf_mp_no1}" />" type="text" id="mpNos" name="mpNos" size="4"></td>
	            <td ><c:out value="${bean.idf_ps_no}" /></td>
	            <td ><c:out value="${bean.idf_lg_no}" /></td>
	            <td ><c:out value="${bean.idf_lc_no}" /></td>
	            <td ><c:out value="${bean.filename}" /></td>	            
	            <td ><a href="jobcode.do?fid=editInit&jobCodeNo=<c:out value="${bean.job_code_no}" />&idList=${idList}&backToListURL=<c:out value='${backToListURL}' default=''  />"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('jobcode.do?fid=delete&jobCodeNo=<c:out value="${bean.job_code_no}"/>')"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
	            <td ><a href="jobbag.do?fid=addInit&jobCodeNo=<c:out value="${bean.job_code_no}" />&idList=${idList}&backToListURL=<c:out value='${backToListURL}' default=''  />"><img src="images/icon-add.png" width="67" height="21"  alt="產生工單" border="0"/></a></td>
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
          <tr><td align="center"><input name="selectAll" type="button"  id="selectAll" value="全選" /> | <input name="selectNon" type="button"  id="selectNon" value="清除" /> | <input name="button" type="submit"  id="button" value="批次更新" /></td></tr>
        </table></vlh:root>
        </form>
</div>