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
<script language="javascript">

$().ready(function() {
	$('#password').keyup(function() {
		var password = $('#password').val();		
		var ATOZ="ABCDEFGHIJKLMNOPQRISTUVWZXY";
		var aTOz="abcdefghijklmnopqristuvwxyz";
		var zeroTO9="0123456789";
		var symbol="!@#$%^&*()";
		var passCheck = 0;
		var ret = "OK";
		
		if(password.length < 8)
			ret = "長度至少8碼";
		
		for (var i=0 ; i < ATOZ.length ; i++) {
			if (password.indexOf(ATOZ.substring(i, i+1)) >= 0) {
				passCheck = passCheck + 1;
				break;
			}
		}
		
		for (var i=0 ; i < aTOz.length; i++) {
			if (password.indexOf(aTOz.substring(i, i+1)) >= 0) {
				passCheck = passCheck + 1;
				break;
			}
		}
		
		for (var i=0;i<zeroTO9.length;i++) {
			if (password.indexOf(zeroTO9.substring(i, i+1)) >= 0) {
				passCheck = passCheck + 1;
				break;
			}
		}
		
		for (var i=0;i<symbol.length;i++) {
			if (password.indexOf(symbol.substring(i, i+1)) >= 0) {
				passCheck = passCheck + 1;
				break;
			}
		}
		
		
		if (passCheck < 3)  {
			ret="密碼須包含[ 大寫英文字母(A~Z) | 小寫英文字母(a~z) | 數字(0~9) | 符號( !@#$%^&*() ) ] ";			
		}
		$('#passwordTxt').html(ret);
		
		var confirmPassword = $('#confirmPassword').val();
		if(confirmPassword == "")
			$('#confirmPasswordTxt').html("");
		else if(password != confirmPassword )
			$('#confirmPasswordTxt').html("上下密碼欄位不相等");
		else
			$('#confirmPasswordTxt').html("OK，上下密碼欄位相等");
			
	});
	$('#confirmPassword').keyup(function() {
		var password = $('#password').val();
		var confirmPassword = $('#confirmPassword').val();
		if(password != confirmPassword)
			$('#confirmPasswordTxt').html("上下密碼欄位不相等");
		else
			$('#confirmPasswordTxt').html("OK，上下密碼欄位相等");
	});
	
});

function clearText(){
	$('#confirmPasswordTxt').html("");
	$('#passwordTxt').html("");
}
</script>
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
  	      <td height="30" style="text-align:center;" colspan="2"><c:out value="${message}" /></td>
  	      
        </tr>		
  	    <tr>
  	      <td height="30" style="text-align:right;">密碼/Password：</td>
  	      <td>
  	        <span >
  	        <input type="password" id="password" name="password" class="required"/>
  	        </span>
  	        <span id="passwordTxt" style="font-size:9px; color:#FF0000;"></span>
  	      </td>
        </tr>
  	    <tr>
  	      <td height="30" style="text-align:right;">確認密碼/Password：</td>
  	      <td>
  	         <span >
  	            <input type="password" id="confirmPassword" name="confirmPassword" class="required"/>
  	         </span>
  	         <span id="confirmPasswordTxt"  style="font-size:9px; color:#FF0000;" ></span>
  	      </td>
        </tr>
	
	

  	    <tr>
  	      <td height="30">&nbsp;</td>
  	      <td>
  	        <input name="button2" type="reset"  onclick="clearText()" id="button2" value="重新填寫" />
            <input name="button7" type="submit" id="button7" value="確認變更密碼" />
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