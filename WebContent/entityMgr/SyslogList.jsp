<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import=" com.salmat.jbm.service.SyslogService"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<%!
	static HashMap<String, String> optionsMap = null;
%>
<% 
    if(optionsMap == null){
    	optionsMap = new HashMap<String, String>();
    	optionsMap.put("CREATE_JOBBAG_ONEGOLD", "ONEGOLD");
    	optionsMap.put("CREATE_JOBBAG_CITICARD", "CITICARD");
    	optionsMap.put("CREATE_JOBBAG_LOGISTIC", "LOGISTIC");
    	optionsMap.put("CREATE_JOBBAG_AMEX", "AMEX");
    	optionsMap.put("CREATE_JOBBAG_DM", "周期性工單");
    	optionsMap.put("CREATE_JOBBAG_DAMAGE", "DAMAGE");
    	optionsMap.put("CREATE_JOBBAG_HSCARD", "HSCARD");
    }
%>
<script type="text/javascript">

function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
<% 
   SyslogService sysLogService = SyslogService.getInstance();
   List<String> logTypes = sysLogService.getAllLogTypes(); 

%>
</script>


<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">系統紀錄查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <html:form action="syslog" method="get">
          <input type="hidden" name="includeParams_value" value="logType,logDateBegin,logDateEnd,pageSize">
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="8%" align="left">紀錄類別</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
					    <select name="logType" id="logType">
					        <option value="">全部</option>
					        <% 
					           for(String logType: logTypes){
					        	   String displayNm = optionsMap.get(logType);
					        	   if(displayNm == null)
					        		   displayNm = logType;
					        %>
					           <option value="<%=logType %>"><%=displayNm%></option>
					        <% } %>
					    </select>
					</TD>
					<TD class="queryTableCellBKColor"  align="left">日期</TD>
					<TD class="queryTableCellBKColor"  align="left" >
					<html:text property="logDateBegin" styleId="logDateBegin" size="10"/>
<img border="0" alt="日期選擇輔助工具" id="logDateBegin_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "logDateBegin",						
		    button : "logDateBegin_datepicker"
		});
</script>
					
					~ <html:text property="logDateEnd" styleId="logDateEnd" size="10"/>
<img border="0" alt="日期選擇輔助工具" id="logDateEnd_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "logDateEnd",						
		    button : "logDateEnd_datepicker"
		});
</script>					
					</TD>
					<TD class="queryTableCellBKColor"  align="left">是否異常</TD>
					<TD class="queryTableCellBKColor"  align="left" >
					<html:radio property="isException" value="1">是</html:radio>
					<html:radio property="isException" value="0">否</html:radio>
					</TD>	
					<TD class="queryTableCellBKColor"  align="left">每頁筆數</TD>
					<TD class="queryTableCellBKColor"  align="left" >						
					    <html:select property="pageSize" styleId="pageSize">
							<html:option value="30">30</html:option>
							<html:option value="50">50</html:option>
							<html:option value="100">100</html:option>
							<html:option value="200">200</html:option>
						</html:select>
						</TD>													
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
            </html:form>
	      <tr>
	        <td>
	        

	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title="ID"      property="id"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>	            
	            <vlh:column title="產生時間"      property="create_date"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="紀錄類別"      property="log_type"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="標題"      property="log_type"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="紀錄內容"      property="message_body"  sortable="desc" attributes="width=\"18%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="是否異常"   property="is_exception"  sortable="desc"  attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >

	            <td height="30" ><c:out value="${bean.id}" /></td>
	            <td ><c:out value="${bean.create_date}" /></td>
	            <td ><c:out value="${bean.log_type}" /></td>
	            <td ><c:out value="${bean.subject}" /></td>
	            <td ><c:out value="${bean.message_body}" /></td>
	            <td ><c:out value="${bean.is_exception}" /></td>
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