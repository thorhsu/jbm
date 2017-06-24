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
	List amexCSVNameList = (List)request.getAttribute("amexCSVNameList");
 %>
 
 <script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();	


});
</script>


<div align="center">
		<form action="report.do" method="post" name="myform">
	    <input type="hidden" name="fid" value="amexJobReportSubmit">		
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">AMEX JOB REPORT</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>

	      <tr>
	        <td height="30">
	        

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >請挑選CSV 檔</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" >
					 <SELECT name="CSVName" size="1" class="required">
<% for (int i=0; i<amexCSVNameList.size(); i++) {
	AmexCSVNameBean amexCSVNameBean = (AmexCSVNameBean)amexCSVNameList.get(i);
	
%>					 
							<option value="<%=amexCSVNameBean.getLogFileName() %>;<%=amexCSVNameBean.getCycleDate().substring(0,10) %>" >
								<%=amexCSVNameBean.getLogFileName() %>_<%=amexCSVNameBean.getCycleDate() %>
							</option>
<% }%>	
					</SELECT>					
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