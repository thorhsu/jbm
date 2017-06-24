<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	
	
	EmployeeService employeeService = EmployeeService.getInstance();
	List employeeList = employeeService.getActiveEmployees();
 %>
<div align="center">
		<form action="report.do" method="post">
	    <input type="hidden" name="fid" value="dailyJobStatisticsReportSubmit">		
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">每日生產統計表 (Daily Job Statistics Report)</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>

	      <tr>
	        <td height="30">
	        

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >請輸入統計之年月日</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" >
<input type="text" name="queryDate" id="queryDate" size="8" readonly value="<%=currentDate %>"/>
<img border="0" alt="日期選擇輔助工具" id="queryDate_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "queryDate",						
		    button : "queryDate_datepicker"
		});
</script>					
					</TD>
				</TR>
				<TR >				
					<TD class="queryTableCellBKColor" width="10%" align="left">工作類別</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
					<INPUT type="radio" id="taskType" name="taskType"   value="LP" checked /> LP
					<INPUT type="radio" id="taskType" name="taskType"   value="MP_PS_DM" /> MP/PS/DM				
					</TD>
				</TR>
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >排序方式</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" >
					<INPUT type="radio" id="taskType" name="orderBy"   value="USER" checked /> USER 
					<INPUT type="radio" id="taskType" name="orderBy"   value="MACHINE" /> Machine					
					</TD>
				</TR>
				<TR >				
					<TD class="queryTableCellBKColor" width="10%" align="left">輸入員工代號</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
					
					 <SELECT name="empNo" size="1" class="required">
					 <option value="ALL" >請選擇</option>
<% for (int i=0; i<employeeList.size(); i++) {
	Employee _employee = (Employee)employeeList.get(i);

%>					 
							<option value="<%=_employee.getEmpNo() %>"  ><%=_employee.getUserId() %>
							</option>
<% }%>	
					</SELECT>					
					


					
					</TD>
				</TR>	
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >列印班別</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" >
					<INPUT type="radio" id="taskType" name="timePeriod"   value="DAY_SHIFT" /> 早班 
					<INPUT type="radio" id="taskType" name="timePeriod"   value="MIDDLE_SHIFT" /> 小夜班	
					<INPUT type="radio" id="taskType" name="timePeriod"   value="NIGHT_SHIFT" /> 大夜班 
					<INPUT type="radio" id="taskType" name="timePeriod"   value="ALL" checked /> 全部										
					</TD>
				</TR>
				<TR >				
					<TD class="queryTableCellBKColor" width="10%" align="left">報表格式</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
					<INPUT type="radio" id="taskType" name="reportType"   value="PDF" checked /> PDF 
					<INPUT type="radio" id="taskType" name="reportType"   value="EXCEL" /> EXCEL					
					</TD>
				</TR>						
			</TABLE>
            </td>
          </tr>
		 <tr>
	        <td height="30" >
			<input name="button" type="submit"   id="button" value="查詢" />
            </td>
          </tr>
          </table>
		</form>

</div>