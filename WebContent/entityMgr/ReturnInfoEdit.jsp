<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>

<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	String message = (String )request.getAttribute("message");
//	String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	Returninfo returninfo= (Returninfo)request.getAttribute("returninfo");
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
	

	CodeService codeService = CodeService.getInstance();
	List psTypeList = codeService.findBycodeTypeName("PS_TYPE"); //摺疊方式


	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");
 
	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK=""; 
		returninfo.setReturnNo("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	
	
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('returninfo.do?fid=checkPKExist', 
	        {returnNo: $('#returnNo').val()},
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
  	$('#returnNo').blur(checkPKExist);
});
</script>

	<form name="myform" id="myform" action="returninfo.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />	    
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">客戶退回資訊維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>收件人代號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="returnNo" size="40"  name="returnNo" value="<%=returninfo.getReturnNo() %>"  <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="4" minlength="4"><span id="msg_pk_exist"></span></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>
                 客戶代號</TD>
				 <TD class="editTableContentCellBKColor">
					 <SELECT name="custNo" size="1" <%=disabledVIEW %> class="required">
					 <option value="" >請選擇</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";
	if (null!= returninfo.getCustomer() && _customer.getCustNo().equalsIgnoreCase( returninfo.getCustomer().getCustNo()) ) 
		selectString="selected";
%>					 
							<option value="<%=_customer.getCustNo() %>" <%=selectString %> >
								<%=_customer.getCustNo() + "-" + _customer.getEasyName() %>
							</option>
<% }%>	
					</SELECT>
				</TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >收件人公司</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                    <INPUT id="userCompany" size="40"  name="userCompany" value="<%=Util.getStringFormat(returninfo.getUserCompany()) %>" <%=disabledVIEW %>>
				</TD>

                 <TD class="editTableLabelCellBKColor" width="10%" >收件人名稱</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="userName" size="40"  name="userName" value="<%=Util.getStringFormat(returninfo.getUserName()) %>" <%=disabledVIEW %>>
                 </TD>
			</TR>

			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >收件人地址</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left >
					<INPUT id="returnAddress" size="40"  name="returnAddress" value="<%=Util.getStringFormat(returninfo.getReturnAddress()) %>" <%=disabledVIEW %> >
				</TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >收件人電話</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left >
					<INPUT id="userTel" size="40"  name="userTel" value="<%=Util.getStringFormat(returninfo.getUserTel()) %>" <%=disabledVIEW %> >
				</TD>				
			</TR>
			



<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan=4 align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<c:out value="${pagesIndex}" /> 
						<c:if test="${prevId != null && prevId != ''}">
						   <input name="button6" type="button" onclick="location='returninfo.do?fid=editInit&idList=${idList}&returnNo=${prevId}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='returninfo.do?fid=saveAsNewInit&returnNo=<%=returninfo.getReturnNo()%>'"  id="button6" value="複製新增" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='returninfo.do?fid=editInit&idList=${idList}&returnNo=${nextId}'"  id="button6" value="下一筆" />
						</c:if>


<%  } else { %>
						<input  type="button"  onclick="backToList()"   value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
