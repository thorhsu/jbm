<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="java.util.List"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	String message = (String )request.getAttribute("message");


%>




<div align="center">

		<table border="0" width="95%" id="table177">
			<tr>
				<td width="33%">　</td>
				<td width="33%" height="26" class="title" >你挑選的工單 無郵資單需要列印! </td>
				<td width="33%">
				　</td>
			</tr>
			<tr>
				<td width="33%">　</td>
				<td width="33%" height="26" class="title" ><input name="button" type="button" onclick="location='jobbagLG.do?fid=batchPrintLGFormSumbit&backToListURL=${backToListURL}'"  id="button" value="列印交寄管制表 " /></td>
				<td width="33%">
				　</td>
			</tr>			
			 
		</table>

</div>