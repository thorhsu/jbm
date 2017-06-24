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
	
	Employee employee= (Employee)request.getAttribute("employee");
	CodeService codeService = CodeService.getInstance();
	List codeList = codeService.findBycodeTypeName("DEPT");
	
	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");
 
	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		employee.setEmpNo("");
		employee.setUserId("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
	 
	
	// 判斷是否為編輯個人資料
	Boolean editMyprofile = false;
	String disabledMyprofile="";
	if (request.getQueryString().indexOf("editMyprofileInit") >0 ) {
		editMyprofile = true;
		disabledMyprofile="readonly disabled class=\"disabled\"  ";
	}
	 
	
%>
<script type="text/javascript"> 
   var allEmployees = ${employeeForm.allEmployeesJson};
   function changeSubstitutes() {
	     //變更客戶聯絡人
       $("#substitute > option" ).remove();
       $("#substitute").append($("<option ></option>").attr("value", "").text("請選擇"));       
       allEmployees.forEach(function(element){			  			  
	     if($("#codeDept").val() === element.codeDept ){				  
		     $("#substitute").append($("<option ></option>").attr("value", element.empNo).text(element.userId));				
	      }			  
    });		     
   }


$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	
	$( "#codeDept" ).change(function(){
		changeSubstitutes();   	 
     }).change();
	
	$("#substitute").val("<%= Util.getStringFormat(employee.getSubstitute())%>");
	
	//大密技, 將attr= disabled, 轉成 正常, 這樣才能送出submit 帶出預設值
	$("#myform").submit(function(){
		
		if ($("#password").val() != $("#confirmPassword").val() && $("#confirmPassword").val() !== "")  {
			submitFinished();
			alert("密碼 跟 確認密碼 不同" );
			return false;
		}
		$("#role1s").attr("disabled","");
		$("#role1c").attr("disabled","");
		$("#role2s").attr("disabled","");
		$("#role2c").attr("disabled","");
		$("#role3s").attr("disabled","");
		$("#role3c").attr("disabled","");
		$("#role4s").attr("disabled","");
		$("#role4c").attr("disabled","");
		$("#role5s").attr("disabled","");
		$("#role5c").attr("disabled","");
		$("#role6m").attr("disabled","");	
		$("#enabled").attr("disabled","");
		return true;
	});


	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('employee.do?fid=checkPKExist', 
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
    
    <html:form action="/employee" method="post" styleId="myform">	
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">帳號修改</td>
				</tr>
				<tr>
				  <td height="15">&nbsp;</td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%"
				class="editTableBKColor"  id="table8">

				<tr>
					<TD class="editTableLabelCellBKColor" width="10%" align=left>
						<font size="2">員工代號</font>
					</TD>
					<TD class="editTableContentCellBKColor" width="40%" align=left>
						<INPUT id="empNo" name="empNo" value="<%=Util.getStringFormat(employee.getEmpNo()) %>" <%=disabledPK %> <%=disabledVIEW %> class="required" minlength="4" ><span id="msg_pk_exist"></span>
						
					</TD>
				</tr>
				<TR>
					<TD class="editTableLabelCellBKColor" width="10%" align=left>
						<font size="2">帳號</font>
					</TD>
					<TD class="editTableContentCellBKColor" width="40%" align=left>
						<INPUT id="userId"  name="userId" value="<%=Util.getStringFormat(employee.getUserId()) %>" <%=disabledPK %> <%=disabledVIEW %>  class="required" >
						
					</TD>
				</TR>
				<TR>
					<TD class="editTableLabelCellBKColor" width="10%" align=left>
						<font size="2">密碼</font>
					</TD>
					<TD class="editTableContentCellBKColor" width="40%" align=left>
						<INPUT type="password" id="password" name="password" <%=disabledVIEW %> >
						<font size="2"> 6~20個字元, 若不修改則不需填寫</font>
					</TD>
				</TR>
				<TR>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">確認密碼</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT type="password" id="confirmPassword" name="confirmPassword" <%=disabledVIEW %>>
						<font size="2"> 請再輸入一次密碼</font>
					</TD>
				</TR>
				<TR>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">姓名</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="userName" name="userName" value="<%=Util.getStringFormat(employee.getUserName()) %>" <%=disabledVIEW %>>
						
					</TD>
				</TR>
				<TR>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">權限</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<table border="0" width="100%" id="table9">
							<tr>
								<td width="259">
									<input type="checkbox" id="role1s" name="role1s" value="1" <% if (null!= employee.getRole1s() && employee.getRole1s()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>  >Production Controller
								</td>
								<td>
									<input type="checkbox" id="role1c" name="role1c" value="1" <% if (null!= employee.getRole1c() &&employee.getRole1c()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>> MP Staff
								</td>
							</tr>
							<tr>
								<td width="259">
									<input type="checkbox" id="role2s"  name="role2s" value="1" <% if (null!= employee.getRole2s() && employee.getRole2s()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>>Dispatch
								</td>
								<td>
									<input type="checkbox" id="role2c" name="role2c" value="1" <% if (null!= employee.getRole2c() && employee.getRole2c()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>>LP Staff
								</td>
							</tr>
							<tr>
								<td width="259">
									<input type="checkbox" id="role4s" name="role4s" value="1" <% if (null!= employee.getRole4s() &&  employee.getRole4s()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>>Sales
								</td>
								<td>
									<input type="checkbox" id="role5c" name="role5c" value="1" <% if (null!= employee.getRole5c() &&  employee.getRole5c()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>>Engineering
								</td>
							</tr>
							<tr>
								<td width="259">
									<input type="checkbox" id="role4c" name="role4c" value="1" <% if (null!= employee.getRole4c() &&   employee.getRole4c()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>>Account
								</td>
								<td>
								</td>
							</tr>							
							<tr>
								<td width="259">
									<input type="checkbox" id="role5s" name="role5s" value="1" <% if (null!= employee.getRole5s() &&   employee.getRole5s()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>>IT System Maintenance
								</td>
								<td>
									<input type="checkbox" id="role6m" name="role6m" value="1" <% if (null!= employee.getRole6m() &&   employee.getRole6m()) out.println("checked");%> <%=disabledVIEW %> <%=disabledMyprofile %>>Managers
								</td>
							</tr>
						</table>
					</TD>
				</TR>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">狀態</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<select size="1" id="enabled" name="enabled" <%=disabledVIEW %> <%=disabledMyprofile %>>
							<option value="" >請選擇</option>
							<option value="1" <% if ( null != employee.getEnabled() && employee.getEnabled()) out.println("selected"); %> >啟用</option>
							<option value="0" <% if ( null != employee.getEnabled() && !employee.getEnabled()) out.println("selected"); %> > 關閉</option>
						</select>
						<font size="2"> </font>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left colspan="2">
						基本資料
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">英文名稱</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="EName" name="EName" value="<%=Util.getStringFormat(employee.getEName()) %>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">中文名稱</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="CName" name="CName" value="<%=Util.getStringFormat(employee.getCName()) %>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">身分證字號</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="pid"name="pid" value="<%=Util.getStringFormat(employee.getPid()) %>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">部門</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<select size="1" id="codeDept" name="codeDept" <%=disabledVIEW %>>
						<option value="" >請選擇</option>
<% for (int i=0; i<codeList.size(); i++) {
	Code _code = (Code)codeList.get(i);
	String selectString = "";
	if (null!= employee.getCode() && _code.getId().compareTo(employee.getCode().getId())==0) 
		selectString="selected";
%>			
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>							

						</select>
						<font size="2"> </font>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">職稱</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="title" name="title" value="<%=Util.getStringFormat(employee.getTitle()) %>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">本機登入帳號</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="adName" name="adName" value="<%=Util.getStringFormat(employee.getAdName()) %>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<TR>
					<TD class="editTableLabelCellBKColor" width="10%" align=left>
						<font size="2">本機登入密碼</font>
					</TD>
					<TD class="editTableContentCellBKColor" width="40%" align=left>
						<INPUT type="password" id="adPassword" name="adPassword" <%=disabledVIEW %> >
						<font size="2"> 若不修改則不需填寫</font>
					</TD>
				</TR>
				<TR>
					<TD class="editTableLabelCellBKColor" width="10%" align=left>
						<font size="2">職務代理人</font>
					</TD>
					<TD class="editTableContentCellBKColor" width="40%" align=left>
						<select size="1" id="substitute" name="substitute" <%=disabledVIEW %>>
						</select>
					</TD>
				</TR>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">分機號碼</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="ext"name="ext" value="<%=Util.getStringFormat(employee.getExt()) %>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">住宅地址</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="HAddress" name="HAddress" value="<%=Util.getStringFormat(employee.getHAddress()) %>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">電話區域號</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="areaCode" name="areaCode" value="<%=Util.getStringFormat(employee.getAreaCode())%>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">住家電話</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="HTel" name="HTel" value="<%=Util.getStringFormat(employee.getHTel())%>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">傳呼號碼</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="bbcall" name="bbcall" value="<%=Util.getStringFormat(employee.getBbcall())%>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">大哥大號碼</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
						<INPUT id="mobile" name="mobile" value="<%=Util.getStringFormat(employee.getMobile())%>" <%=disabledVIEW %>>
					</TD>
				</tr>
				<tr>
					<TD class="editTableLabelCellBKColor" align=left>
						<font size="2">生日</font>
					</TD>
					<TD class="editTableContentCellBKColor" align=left>
<input type="text" name="birthday" id="birthday" size="8" readonly value="<%=Util.getStringFormat( employee.getBirthday()) %>" <%=disabledVIEW %>/>
<img border="0" alt="日期選擇輔助工具" id="birthday_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "birthday",						
		    button : "birthday_datepicker"
		});
</script>

						
						
					</TD>
				</tr>

<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan=2 align=left bgcolor="#FFFFFF">
						<p align="center">
<% if (editMyprofile){ %>
						<input name="button6" type="submit"  id="button6" value="更新" />							
<%	} else if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='employee.do?fid=saveAsNewInit&empNo=<%=employee.getEmpNo()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<input name="button6" type="button"  onclick="javascript:history.go(-1)"  id="button6" value="取消, 回列表" />
<%  } else { %>
						<input name="button6" type="button"  onclick="javascript:history.go(-1)"  id="button6" value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</html:form>		
