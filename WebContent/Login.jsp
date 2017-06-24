<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JBM v2.0</title>
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
	function csSubmitForm() {
		var fid = window.document.getElementById("fid");
		fid.value = "submitCs";
		document.forms[0].submit();
	}
	function jbmSubmitForm() {
		var fid = window.document.getElementById("fid");
		document.forms[0].submit();
	}
</script>


</head>
<body>


	<div align="center">


		<table border="0" width="1024">
			<tr>
				<td align="center">
									<table border="0" width="100%">
						<tr>
							<td width="126" valign="top"></td>
							<td ><H1 align="center">JBM 2.0</H1></td>
							<td width="119"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
			    <td align="center">
			    <div style="color:red">
			       <logic:messagesPresent message="true">
			       

						<html:messages id="message" message="true">
								<bean:write name="message" />
						   <br />
						</html:messages>
					</logic:messagesPresent>
				</div>
			    </td>
			</tr>

			<tr>
				<td>
					<form action="login.do" name="myform" id="myform" method="post">
						<input type="hidden" name="fid" id="fid" value="submit" />
						<div id="wrap">
							<div id="center">
								<div id="centerTextWrap">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="right" width="30%" height="30" >
											   <bean:message
													key="label.user_id" />
										    </td>
											<td width="70%" align="left"><input type="text"
												id="userId" name="userId" class="required" /></td>
										</tr> 
										<tr>
											<td height="30" align="right"><bean:message
													key="label.user_password" /></td>
											<td align="left"><input type="password" name="password"
												class="required" /></td>
										</tr>
										<tr>
											<td height="30" align="right"><bean:message
													key="label.user_langugae" /></td>
											<td align="left"><select size="1" name="language">
													<option value="TW">中文/Chinese</option>
													<option value="EN">英文/English</option>
											</select></td>
										</tr>
										<tr>

											<td colspan="2" align="center" style="color: red">
											    
											</td>
										</tr>



										<tr>
											<td height="30">&nbsp;</td>
											<td align="left">
											   <input name="button2" type="reset"
												   id="button2" value="<bean:message key="label.reset" />" />
											   <input type="button" id="button7"
												   value="<bean:message key="label.login" />"
												    onclick="jbmSubmitForm();" />
												<!--      
											    <input type="button"
												    id="button7" value="<bean:message key="label.logincs" />"
												    onclick="csSubmitForm();" />
												 -->
										   </td>
										</tr>
									</table>
								</div>
							</div>
						</div>



					</form>

				</td>
			</tr>
		</table>
	</div>
</body>








</html>
