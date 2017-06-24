<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JBM v2.0</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
</head>
<%
	String userId = (String )request.getAttribute("userId");
 %>	
	
<body>


<div align="center">
  <table border="0" width="800" height="300">
	<tr>
		<td align="center">
		<table border="0" width="100%">
			<tr>
				<td width="126" valign="top"></td>
				<td><H1>JBM 2.0</H1></td>
				<td width="119"></td>
			</tr>
		</table>
		
		</td>
	</tr>
	<tr>
	<td><P> </P></td></tr>
	
		<tr>
	<td>
<form action="login.do" method="post">
<input type="hidden" name="backToListURL" value="${backToListURL}" />
<input type="hidden" name="fid" value="changePassword" />
<input type="hidden" name="userId" value="<%=userId %>" />
<div id="wrap">
  <div id="center">
  	<div id="centerTextWrap">
  	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  	    <tr>
  	      <td height="30" style="text-align:right;">首次登入, 密碼變更</td>
  	      <td></td>
        </tr>		
  	    <tr>
  	      <td height="30" style="text-align:right;">密碼/Password：</td>
  	      <td><span >
  	        <input type="password" name="password" class="required"/>
  	      </span></td>
        </tr>
  	    <tr>
  	      <td height="30" style="text-align:right;">確認密碼/Password：</td>
  	      <td><span >
  	        <input type="password" name="confirmPassword" class="required"/>
  	      </span></td>
        </tr>
	
	

  	    <tr>
  	      <td height="30">&nbsp;</td>
  	      <td>
  	        <input name="button2" type="reset"  id="button2" value="重新填寫" />
            <input name="button7" type="submit" onclick="return check ();"  id="button7" value="確認變更密碼" />
  	      </td>
        </tr>
      </table>
  	</div>
  </div>
</div>



</form>
	
	</td></tr>
	</table>
</div>
</body>





<script language="javascript">
<!--
<c:if test="${null != requestScope.message}">
alert('<c:out value="${message}" />');
</c:if>
//-->
</script>
</html>