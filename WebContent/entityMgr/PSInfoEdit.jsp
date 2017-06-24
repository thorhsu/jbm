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
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	Psinfo psinfo= (Psinfo)request.getAttribute("psinfo");
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
		psinfo.setPsNo("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	
	$("#myform").submit(function(){
		var nextEffectiveDate = $('#nextEffectiveDate').val();
		var nextCodePsType = $('#nextCodePsType').val();
		var nextZipFg = $('#nextZipFg').val();

		if (nextEffectiveDate != "") {
			if ( nextCodePsType=="" ||  ($('#nextZipFg').get(0).checked==false && $('#nextZipFg').get(1).checked == false )) {
				submitFinished();
				alert("摺疊方式 / 分區／不分區  為必填欄位");
				return false;
			}
		}
		return true;
	});
	
	
	//複製資料
	copyData = function (){
		$('#nextProdmemo').val( $('#prodmemo').val() );
		$('#nextCodePsType').val( $('#codePsType').val() );			
		$('#nextRemark').val( $('#remark').val() );	
		

		if ($('#zipFg').get(0).checked == true)
			$('input[name="nextZipFg"]')[0].checked = true;
		else
			$('input[name="nextZipFg"]')[1].checked = true;
		

	
		
  	};
	$('#btnCopy').click(copyData);
	
		
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('psinfo.do?fid=checkPKExist', 
	        {psNo: $('#psNo').val()},
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
  	$('#psNo').blur(checkPKExist);
});
</script>

	<form name="myform" id="myform" action="psinfo.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />	    
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">壓封資料維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>壓封代號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="psNo" size="40"  name="psNo" value="<%=psinfo.getPsNo() %>"  <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="4" minlength="4"><span id="msg_pk_exist"></span></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >
                 客戶代號</TD>
				 <TD class="editTableContentCellBKColor">
					 <SELECT name="custNo" size="1" <%=disabledVIEW %>>
					 <option value="" >共用物料</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";
	if (null!= psinfo.getCustomer() && _customer.getCustNo().equalsIgnoreCase( psinfo.getCustomer().getCustNo()) ) 
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
                 <TD class="editTableLabelCellBKColor" width="10%" >產品說明</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="prodmemo" size="40"  name="prodmemo" value="<%=Util.getStringFormat(psinfo.getProdmemo()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>摺疊方式</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
<SELECT name="codePsType" size="1" <%=disabledVIEW %> class="required" id="codePsType">
<option value="" >請選擇</option>
<% for (int i=0; i<psTypeList.size(); i++) {
	Code _code = (Code)psTypeList.get(i);
	String selectString = "";
	if (null!= psinfo.getCodeByCodePsType() && _code.getId().compareTo(psinfo.getCodeByCodePsType().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>                    	</TD>
			</TR>

			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >注意事項</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<TEXTAREA cols="40" name="remark"  id="remark" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( psinfo.getRemark() ) %></TEXTAREA>
										
				</TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >分區／不分區</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<INPUT type="radio" id="zipFg" name="zipFg"  value="1" <% if(null!=psinfo.getZipFg() && psinfo.getZipFg() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 分區
					<INPUT type="radio" id="zipFg" name="zipFg"  value="0" <% if(null!=psinfo.getZipFg() && !psinfo.getZipFg() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 不分區
			</TR>
			

			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >修改後資料生效日期</TD>
                 <TD colSpan="3"  class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextEffectiveDate" size="40"  name="nextEffectiveDate" value="<%=Util.getStringFormat(psinfo.getNextEffectiveDate()) %>" <%=disabledVIEW %>>
<img border="0" alt="日期選擇輔助工具" id="nextEffectiveDate_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "nextEffectiveDate",						
		    button : "nextEffectiveDate_datepicker"
		});
</script>                 	
                 	<input name="btnCopy" type="button"  id="btnCopy" value="複製" /> </TD>
			</TR>					

			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >產品說明</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="nextProdmemo" size="40"  name="nextProdmemo" value="<%=Util.getStringFormat(psinfo.getNextProdmemo()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>摺疊方式</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
<SELECT name="nextCodePsType" size="1" <%=disabledVIEW %> id="nextCodePsType" >
<option value="" >請選擇</option>
<% for (int i=0; i<psTypeList.size(); i++) {
	Code _code = (Code)psTypeList.get(i);
	String selectString = "";
	if (null!= psinfo.getCodeByNextCodePsType() && _code.getId().compareTo(psinfo.getCodeByNextCodePsType().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>                    	</TD>
			</TR>

			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >注意事項</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<TEXTAREA cols="40" name="nextRemark"  id="nextRemark" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( psinfo.getNextRemark() ) %></TEXTAREA>					
				</TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >分區／不分區</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<INPUT type="radio" id="nextZipFg" name="nextZipFg"  value="1" <% if(null!=psinfo.getNextZipFg() && psinfo.getNextZipFg() ) out.println("checked");%> <%=disabledVIEW %> /> 分區
					<INPUT type="radio" id="nextZipFg" name="nextZipFg"  value="0" <% if(null!=psinfo.getNextZipFg() && !psinfo.getNextZipFg() ) out.println("checked");%> <%=disabledVIEW %> /> 不分區
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
						   <input name="button6" type="button" onclick="location='psinfo.do?fid=editInit&idList=${idList}&psNo=${prevId}&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='psinfo.do?fid=saveAsNewInit&psNo=<%=psinfo.getPsNo()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='psinfo.do?fid=editInit&idList=${idList}&psNo=${nextId}&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
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
