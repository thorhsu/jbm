<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="org.apache.commons.beanutils.BeanUtils"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%><head>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	List codeList= (List)request.getAttribute("codeList");
	CodeService codeService = CodeService.getInstance();

	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="readonly  class=\"disabled\"";
	String actionMode = (String)request.getAttribute("ACTION_MODE");


	Employee employee = SessionUtil.getAccount(request);
	String disabledForCS="";	

	
			
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	
	
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('code.do?fid=checkPKExist', 
	        {empNo: $('#empNo').val()},
	        function(ret) {
	        	if (ret.result=="NON_EXIST") {
	        		$('#msg_pk_exist').html("代號可以使用");
	        		$('#btnAdd').attr('disabled', false);
	        	}
	        	else {
	        		$('#msg_pk_exist').html("代號已經存在");
	        		$('#btnAdd').attr('disabled', true);
	           }
	        });
  	};
  	$('#empNo').blur(checkPKExist);
});
</script>

	<form name="myform" id="myform" action="code.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="<%=fid %>" />
		
		
<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">代碼檔查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>

	      <tr>
	        <td>
	        
					<table border="0" width="100%" id="table9" class="resultTableBKColor">

						<tr>

							<td class="resultTableHeaderBKColor"><font size="2">編號</font></td>
							<td class="resultTableHeaderBKColor"><font size="2">代碼類別</font></td>
							<td class="resultTableHeaderBKColor"><font size="2">代碼類別名稱</font></td>
							<td class="resultTableHeaderBKColor"><font size="2">代碼</font></td>
							<td class="resultTableHeaderBKColor"><font size="2">代碼說明</font></td>
							<td class="resultTableHeaderBKColor"><font size="2">彈性欄位一</font></td>
							<td class="resultTableHeaderBKColor"><font size="2">彈性欄位二</font></td>
							<td class="resultTableHeaderBKColor"><font size="2">彈性欄位三</font></td>
<!-- 							
							<td class="resultTableHeaderBKColor"><font size="2">是否隱藏</font></td>
-->
						</tr>

<% 
Code newCode = new Code();
for (int i=0;i<codeList.size();i++)  {
	Code code=(Code)codeList.get(i);
	
	if ( !employee.getRole5s() &&  !( 
			code.getCodeTypeName().equalsIgnoreCase("JOB_CODE_TYPE") ||
			code.getCodeTypeName().equalsIgnoreCase("LG_MAIL_CATEGORY") ||
			code.getCodeTypeName().equalsIgnoreCase("LG_MAIL_TO_AREA") ||
			code.getCodeTypeName().equalsIgnoreCase("LG_MAIL_TO_POSTOFFICE") ||
			code.getCodeTypeName().equalsIgnoreCase("LG_TYPE") )
			  ) 
			disabledForCS="readonly disabled class=\"disabled\""; //disabled CS不能編輯的代碼表
			 
				 
	if (i==0)
		BeanUtils.copyProperties(newCode,code);
%>

	          <tr >
	            <td height="30" ><INPUT id="ids"  name="ids" value="<%=code.getId() %>" <%=disabledVIEW %> size="2"></td>
	            <td ><INPUT id="codeTypeNames"  name="codeTypeNames" value="<%=code.getCodeTypeName() %>" <%=disabledVIEW %>></td>
	            <td ><INPUT id="codeTypeNameTws"  name="codeTypeNameTws" value="<%=code.getCodeTypeNameTw() %>" <%=disabledVIEW %>></td>
	            <td ><INPUT id="codeKeys"  name="codeKeys" value="<%=code.getCodeKey() %>" <%=disabledForCS %>></td>
	            <td ><INPUT id="codeValueTws"  name="codeValueTws" value="<%=code.getCodeValueTw() %>" <%=disabledForCS %>></td>          
	            <td ><INPUT id="f1s"  name="f1s" value="<%=code.getF1() %>" <%=disabledForCS %>></td>
	            <td ><INPUT id="f2s"  name="f2s" value="<%=code.getF2() %>" <%=disabledForCS %>></td>
	            <td ><INPUT id="f3s"  name="f3s" value="<%=code.getF3() %>" <%=disabledForCS %>></td>
<!-- 	  	            
	            <td ><INPUT id="isHiddens"  name="isHiddens" value="<%=code.getIsHidden() %>" size="2" <%=disabledForCS %>></td>      
 -->	                  
              </tr>
<%} %>
	          <tr >
	            <td height="30" ><INPUT id="ids"  name="ids" value="" <%=disabledVIEW %> size="2"></td>
	            <td ><INPUT id="codeTypeNames"  name="codeTypeNames" value="<%=newCode.getCodeTypeName() %>" <%=disabledVIEW %>></td>
	            <td ><INPUT id="codeTypeNameTws"  name="codeTypeNameTws" value="<%=newCode.getCodeTypeNameTw() %>" <%=disabledVIEW %>></td>
	            <td ><INPUT id="codeKeys"  name="codeKeys"  <%=disabledForCS %>></td>
	            <td ><INPUT id="codeValueTws"  name="codeValueTws"  <%=disabledForCS %>></td>
	            <td ><INPUT id="f1s"  name="f1s" value="" <%=disabledForCS %>></td>
	            <td ><INPUT id="f2s"  name="f2s" value="" <%=disabledForCS %>></td>
	            <td ><INPUT id="f3s"  name="f3s" value="" <%=disabledForCS %>></td>
<!-- 	            
	            <td ><INPUT id="isHiddens"  name="isHiddens"  size="2" <%=disabledForCS %>></td>     
 -->	                   
              </tr>
              	          
	          </table>
	           
	          </td>
          </tr>
	      <tr>
	        <td>&nbsp;</td>
          </tr>



<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan=2 align=left bgcolor="#FFFFFF">
						<p align="center">
						<input name="button6" type="submit"  id="button6" value="更新" <%=disabledForCS %> />
						<input name="button6" type="button"  onclick="javascript:history.go(-1)"  id="button6" value="取消, 回列表" />

					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
