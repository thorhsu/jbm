<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.painter.util.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@ page import="java.util.StringTokenizer" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	//AccountService accountService = AccountService.getInstance();
	Employee sessionAccount = SessionUtil.getAccount(session);
%>
<div align="center">
<table width="100%" >
  <tr>
  <td valign="top" width="100">
  <font size="2">JBM v2.0 </font>
  </td>
  <td valign="top" bordercolor="#C0C0C0" align="left">
  <font size="2"><a href="login.do?fid=tocsform">to 客戶需求系統</a></font>
  </td>
  <td valign="top" width="129">
  <font size="2">Hi! </strong> <%=sessionAccount.getUserId() %> <a href="login.do?fid=logout"><bean:message key="label.logout" /></a> </font>
  </td>
  </tr>

  </table>
</div>
<hr color="#DFF4DD" size="1">