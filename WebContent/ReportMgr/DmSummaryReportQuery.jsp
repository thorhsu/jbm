<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	Calendar calendar = Calendar.getInstance();

	Date now = new Date();
	calendar.setTime(now);
	calendar.add(Calendar.DAY_OF_MONTH, -7);
		
	String DateEnd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	String DateBegin = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
 %>
<div align="center">
		<form action="report.do" method="post">
	    <input type="hidden" name="fid" value="dmSummaryReportSubmit">		
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">DM Summary Report Query</td>
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
<input type="text" name="queryDateBegin" id="queryDateBegin" size="8" readonly value="<%=DateBegin %>"/>
<img border="0" alt="日期選擇輔助工具" id="queryDateBegin_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "queryDateBegin",						
		    button : "queryDateBegin_datepicker"
		});
</script> ~ 
<input type="text" name="queryDateEnd" id="queryDateEnd" size="8" readonly value="<%=DateEnd %>"/>
<img border="0" alt="日期選擇輔助工具" id="queryDateEnd_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "queryDateEnd",						
		    button : "queryDateEnd_datepicker"
		});
</script>				
					</TD>
				</TR>
				<TR >				
					<TD class="queryTableCellBKColor" width="10%" align="left">客戶別</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
					
					 <SELECT name="custNo" size="1" class="required">
					 <option value="ALL" >請選擇</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);

%>					 
							<option value="<%=_customer.getCustNo() %>" >
								<%=_customer.getCustNo() + "-" + _customer.getEasyName() %>
							</option>
<% }%>	
					</SELECT>					
					
					
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