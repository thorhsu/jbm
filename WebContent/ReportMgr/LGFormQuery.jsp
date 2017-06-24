<%@ page contentType="text/html; charset=UTF-8"%>
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
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
 %>
<div align="center">
		<form action="report.do" method="post">
	    <input type="hidden" name="fid" value="LGformSubmit">		
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">空白郵資單</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>

	      <tr>
	        <td height="30">
	        

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >				
					<TD class="queryTableCellBKColor" width="10%" align="left">客戶別</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
					
					 <SELECT name="custNo" size="1" class="required">
					 <option >請選擇</option>
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
			<input name="button" type="submit"   id="button" value="產生空白郵資單" />
            </td>
          </tr>
          </table>
		</form>

</div>