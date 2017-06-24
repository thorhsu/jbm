<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%><head>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	String message = (String )request.getAttribute("message");
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	Customer customer= (Customer)request.getAttribute("customer");
	EmployeeService employeeService = EmployeeService.getInstance();
	List employeeList = employeeService.getActiveEmployees();
	
	
	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		customer.setCustNo("");
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
	    $.getJSON('customer.do?fid=checkPKExist', 
	        {custNo: $('#custNo').val()},
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
  	$('#custNo').blur(checkPKExist);
});
</script>
	


	<form name="myform" id="myform" action="customer.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">客戶資料維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>客戶代號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="custNo" size="40"  name="custNo" value="<%=Util.getStringFormat(customer.getCustNo()) %>" <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="3" minlength="2"><span id="msg_pk_exist"></span></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >
                 <font color="#FF0000" size="2">*</font>負責人員</TD>
				 <TD class="editTableContentCellBKColor">
					 <SELECT name="empNo" size="1" <%=disabledVIEW %> class="required">
					 <option value="" >請選擇</option>
<% for (int i=0; i<employeeList.size(); i++) {
	Employee _employee = (Employee)employeeList.get(i);
	String selectString = "";
	if (null!= customer.getEmployee() && _employee.getEmpNo().equalsIgnoreCase( customer.getEmployee().getEmpNo()) ) 
		selectString="selected";
%>					 
							<option value="<%=_employee.getEmpNo() %>" <%=selectString %> >
								<%=_employee.getUserId() %>
							</option>
<% }%>	
					</SELECT>
				</TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>客戶簡稱</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="easyName" value="<%=Util.getStringFormat(customer.getEasyName()) %>" <%=disabledVIEW %> class="required"></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >客戶全名</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="custName" value="<%=Util.getStringFormat(customer.getCustName()) %>" <%=disabledVIEW %>></TD>
			</TR>

			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >公司地址</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="address1" value="<%=Util.getStringFormat(customer.getAddress1()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >營業地址</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="address2" value="<%=Util.getStringFormat(customer.getAddress2()) %>" <%=disabledVIEW %>></TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >發票地址</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="address3" value="<%=Util.getStringFormat(customer.getAddress3()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >統一編號</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="PNo" size="40"  name="PNo" value="<%=Util.getStringFormat(customer.getPNo()) %>" <%=disabledVIEW %>></TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >公司電話</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="tel1" value="<%=Util.getStringFormat(customer.getTel1()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >傳真號碼</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="fax" value="<%=Util.getStringFormat(customer.getFax()) %>" <%=disabledVIEW %>></TD>
			</TR>									

			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >連絡人</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="contaceP" value="<%=Util.getStringFormat(customer.getContaceP()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >連絡人電話</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="tel2" value="<%=Util.getStringFormat(customer.getTel2()) %>" <%=disabledVIEW %>></TD>
			</TR>					
 
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >銀行名稱</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="bankName" size="40"  name="bankName" value="<%=Util.getStringFormat(customer.getBankName()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >銀行帳號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="bankAccount" size="40"  name="bankAccount" value="<%=Util.getStringFormat(customer.getBankAccount()) %>" <%=disabledVIEW %> ></TD>

			</TR>
			
						
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >EP1 Acc NO.</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="empNo" size="40"  name="ep1no" value="<%=Util.getStringFormat(customer.getEp1no()) %>" <%=disabledVIEW %> maxlength="7"></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >客戶名稱<BR>交寄圖章</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
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
						   <input name="button6" type="button" onclick="location='customer.do?fid=editInit&idList=<c:out value="${idList}"/>&custNo=${prevId }&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='customer.do?fid=saveAsNewInit&pagesIndex=${pagesIndex}&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='customer.do?fid=editInit&idList=<c:out value="${idList}"/>&custNo=${nextId }&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
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
