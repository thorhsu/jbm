<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c' %> 


<HTML><HEAD><TITLE>JBM</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="MSHTML 6.00.2900.3243" name=GENERATOR></HEAD>
<%
String global_redirect_url =(String) request.getAttribute("global_redirect_url");  
%>

<script language="JavaScript" type="text/JavaScript">
<!--
function redirect() {
	//alert("<%=global_redirect_url%>");
	window.location.href="<%=global_redirect_url%>";
}


//-->
</script>


<BODY leftMargin=0 topMargin=0 onload="redirect();">

</BODY>
</HTML>