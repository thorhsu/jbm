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
	    <input type="hidden" name="fid" value="dailyJobControlReportSubmit">		
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">每日生產管制表(Daily Job Control Log )</td>
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