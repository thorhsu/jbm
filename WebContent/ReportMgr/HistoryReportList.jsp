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
//Thor add
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
//Thor
</script>


<div align="center">
   <form action="report.do" method="get">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	    	      
	      <tr>
	        <td height="26" class="title">歷史報表查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>                    
          <tr>
	        <td height="30">
	           	        	        
	           <input type="hidden" name="fid" value="historyReportList">
	           <input type="hidden" name="includeParams_value" value="queryDateBegin,queryDateEnd,reportName,empNo">
			   <table border=0 cellSpacing=1 cellPadding=6 width="98%" class="queryTableBKColor" align="left" >
				   <tr >										
					   <td class="queryTableCellBKColor" width="10%" align="left">報表建立日期區間</td>
					   <td class="queryTableCellBKColor" width="40%" align="left" >
					      <input type="text" id="queryDateBegin" maxLength="15" size="10" name="queryDateBegin" readonly="readonly"/>
                             <img border="0" alt="日期選擇輔助工具" id="queryDateBeginPicker" src="images/calendar2.gif" />
                                <script language="javascript">
		                             Calendar.setup({
			                           inputField : "queryDateBegin",						
		                               button : "queryDateBeginPicker"
		                             });
                                </script>					
					         ~
					      <input type="text" id="queryDateEnd" maxLength="15" size="10" name="queryDateEnd" readonly="readonly"/>
                             <img border="0" alt="日期選擇輔助工具" id="queryDateEndPicker" src="images/calendar2.gif" />
                                <script language="javascript">
		                             Calendar.setup({
			                           inputField : "queryDateEnd",						
		                               button : "queryDateEndPicker"
		                             });
                                </script>					
					   </td>
					   <td class="queryTableCellBKColor" width="10%" align="left">報表名稱</td>
					   <td class="queryTableCellBKColor" width="40%" align="left" ><input type="text" id="reportName" maxLength="60" size="40" name="reportName" />
					   </td>
				   </tr>
                   <tr >										
					   <td class="queryTableCellBKColor" width="10%" align="left">員工ID</td>
					   <td class="queryTableCellBKColor">
					      <SELECT id="empNo" size=1 name="empNo">
					      <option value="all" >全部</option>
					      <c:forEach var="employee" items="${empMap}">
					           <option value="${employee.value}" >${employee.key}</option>
					      </c:forEach>
					   </SELECT>
					   </td>
					   <td class="queryTableCellBKColor"></td>
					   <td class="queryTableCellBKColor"></td>
				   </tr>
			   </table>			   
		    </td>
		  </tr>
		  <tr>
	        <td height="30">
			<table border=0 cellSpacing=1 cellPadding=6 width="100%" align="center" id="table7">			
				<tr>
					<td align="center"><input name="button" type="submit"  id="button" value="搜尋" /></td>

				</tr>
			</table>
            </td>
          </tr>
		  
	      <tr>
	        <td>
	        
	
	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title="編號"      property="id"  sortable="desc" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="報表名稱"      property="report_name"  sortable="desc" attributes="width=\"45%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="報表檔案"      property="pdf_path"  sortable="desc" attributes="width=\"12%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="員工ID"      property="user_id"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="員工姓名"      property="c_name"  sortable="desc" attributes="width=\"10%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="建立日期"      property="create_date"  sortable="desc" attributes="width=\"23%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td height="30" ><c:out value="${bean.id}" /></td>
	            <td ><c:out value="${bean.report_name}" /></td>
	            <td align="left" ><a href="pdf/<c:out value="${bean.report_name}" />" target="_new">下載檔案</a></td>
	            <td align="left" ><c:out value="${bean.user_id}" /></td>
	            <td align="left" ><c:out value="${bean.c_name}" /></td>
	            <td align="left" ><c:out value="${bean.create_date}" /></td>
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
        </form>
</div>