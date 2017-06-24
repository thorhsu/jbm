<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%

	EmployeeService employeeService = EmployeeService.getInstance();
	List employeeList = employeeService.getActiveEmployees();
 %>
<div align="center">
		<form action="report.do" method="post">
	    <input type="hidden" name="fid" value="userAuthorityReportSubmit">		
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">User Authority</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>

	      <tr>
	        <td height="30">
	        

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">

				<TR >				
					<TD class="queryTableCellBKColor" width="10%" align="left">使用者狀態</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
					<INPUT type="radio" id="taskType" name="userStatus"   value="ALL" checked /> All					
					<INPUT type="radio" id="taskType" name="userStatus"   value="enabled"  /> Enabled
					<INPUT type="radio" id="taskType" name="userStatus"   value="disabled" /> disabled			
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