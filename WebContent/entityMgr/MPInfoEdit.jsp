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
	Mpinfo mpinfo= (Mpinfo)request.getAttribute("mpinfo");
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
	
	//編輯模式 判斷 
	CodeService codeService = CodeService.getInstance();
	List printerTypeList = codeService.findBycodeTypeName("PRINTER_TYPE"); //印表機類型
	List printTypeList = codeService.findBycodeTypeName("PRINT_TYPE");	//列印方式

	
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		mpinfo.setMpNo("");
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
		var nextStampSetFg = $('#nextStampSetFg').val();
		var nextSelectFg = $('#nextSelectFg').val();

		if (nextEffectiveDate != "") {
			if ( ($('#nextStampSetFg').get(0).checked==false && $('#nextStampSetFg').get(1).checked == false ) || 
			     ($('#nextSelectFg').get(0).checked==false && $('#nextSelectFg').get(1).checked == false )) {
				submitFinished();
				alert("郵戳設定 / 分區／不分區  為必填欄位");
				return false;
			}
		}
		return true;
	});
	
	
	//複製資料
	copyData = function (){
		$('#nextProdmemo').val( $('#prodmemo').val() );
		$('#nextPrintCode').val( $('#printCode').val() );	
		if ($('#stampSetFg').get(0).checked == true)
			$('input[name="nextStampSetFg"]')[0].checked = true;
		else
			$('input[name="nextStampSetFg"]')[1].checked = true;
			
		if ($('#zipFg').get(0).checked == true)
			$('input[name="nextZipFg"]')[0].checked = true;
		else
			$('input[name="nextZipFg"]')[1].checked = true;
		
		if ($("input[name='mp1Iflag']").is(":checked"))
			$("#nextMp1Iflag").attr("checked",true);
		else
			$("#nextMp1Iflag").attr("checked",false);
			
		if ($("input[name='mp2Iflag']").is(":checked"))
			$("#nextMp2Iflag").attr("checked",true);
		else
			$("#nextMp2Iflag").attr("checked",false);
			
		if ($("input[name='mp3Iflag']").is(":checked"))
			$("#nextMp3Iflag").attr("checked",true);
		else
			$("#nextMp3Iflag").attr("checked",false);
			
		if ($("input[name='mp4Iflag']").is(":checked"))
			$("#nextMp4Iflag").attr("checked",true);
		else
			$("#nextMp4Iflag").attr("checked",false);									
		$('#nextMp1Word').val( $('#mp1Word').val() );
		$('#nextMp2Word').val( $('#mp2Word').val() );
		$('#nextMp3Word').val( $('#mp3Word').val() );
		$('#nextMp4Word').val( $('#mp4Word').val() );				
		$('#nextRemark').val( $('#remark').val() );		
		
  	};
	$('#btnCopy').click(copyData);
	
	
		
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('mpinfo.do?fid=checkPKExist', 
	        {mpNo: $('#mpNo').val()},
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
  	$('#mpNo').blur(checkPKExist);
});
</script>

	<form name="myform" id="myform" action="mpinfo.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />	    
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">裝封資料維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>裝封代號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="mpNo" size="40"  name="mpNo" value="<%=mpinfo.getMpNo() %>"  <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="4" minlength="4"><span id="msg_pk_exist"></span></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >客戶代號</TD>
				 <TD class="editTableContentCellBKColor">
					 <SELECT name="custNo" size="1" <%=disabledVIEW %> >
					 <option value="" >共用物料</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";
	if (null!= mpinfo.getCustomer() && _customer.getCustNo().equalsIgnoreCase( mpinfo.getCustomer().getCustNo()) ) 
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
                 <TD class="editTableContentCellBKColor" width="40%" align="left" >
                 	<INPUT id="prodmemo" size="40"  name="prodmemo" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getProdmemo()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >物料版號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="printCode" size="40" maxlength="20" name="printCode" value="<%=Util.getStringFormat(mpinfo.getPrintCode()) %>" <%=disabledVIEW %>></TD>
			</TR>

			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>郵戳設定</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<INPUT type="radio" id="stampSetFg" name="stampSetFg" value="1" <% if(null!=mpinfo.getStampSetFg() && mpinfo.getStampSetFg() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 打郵戳
					<INPUT type="radio" id="stampSetFg" name="stampSetFg"  value="0" <% if(null!=mpinfo.getStampSetFg() && !mpinfo.getStampSetFg() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 不打郵戳
				</TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>分區／不分區</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<INPUT type="radio" id="zipFg" name="zipFg"  value="1" <% if(null!=mpinfo.getZipFg() && mpinfo.getZipFg() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 分區
					<INPUT type="radio" id="zipFg" name="zipFg"  value="0" <% if(null!=mpinfo.getZipFg() && !mpinfo.getZipFg() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 不分區
				</TD>					

			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >插件內容二</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input type="checkbox" id="mp1Iflag" name="mp1Iflag" value="1" <% if(null!=mpinfo.getMp1Iflag() && mpinfo.getMp1Iflag() ) out.println("checked");%> <%=disabledVIEW %>>智慧型
					<INPUT id="mp1Word" size="40"  name="mp1Word" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getMp1Word()) %>" <%=disabledVIEW %>>
				 </TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >插件內容三</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input type="checkbox" id="mp2Iflag" name="mp2Iflag" value="1" <% if(null!=mpinfo.getMp2Iflag() && mpinfo.getMp2Iflag() ) out.println("checked");%> <%=disabledVIEW %>>智慧型
					<INPUT id="mp2Word" size="40"  name="mp2Word" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getMp2Word()) %>" <%=disabledVIEW %>>
                 </TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >插件內容四</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input type="checkbox" id="mp3Iflag" name="mp3Iflag" value="1" <% if(null!=mpinfo.getMp3Iflag() && mpinfo.getMp3Iflag() ) out.println("checked");%> <%=disabledVIEW %>>智慧型
					<INPUT id="mp3Word" size="40"  name="mp3Word" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getMp3Word()) %>" <%=disabledVIEW %>>
				</TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >插件內容五</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input type="checkbox" id="mp4Iflag" name="mp4Iflag" value="1" <% if(null!=mpinfo.getMp4Iflag() && mpinfo.getMp4Iflag() ) out.println("checked");%> <%=disabledVIEW %>>智慧型
					<INPUT id="mp4Word" size="40"  name="mp4Word" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getMp4Word()) %>" <%=disabledVIEW %>>
                 </TD>
			</TR>									

			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >注意事項</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left colspan="3">
					<TEXTAREA cols="40" name="remark" id="remark" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( mpinfo.getRemark() ) %></TEXTAREA>                 	
                 </TD>
			</TR>
			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >修改後資料生效日期</TD>
                 <TD colSpan="3"  class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextEffectiveDate" size="40"  name="nextEffectiveDate" value="<%=Util.getStringFormat(mpinfo.getNextEffectiveDate()) %>" <%=disabledVIEW %>>
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
                 	<INPUT id="nextProdmemo" size="40" maxlength="20"  name="nextProdmemo" value="<%=Util.getStringFormat(mpinfo.getNextProdmemo()) %>" <%=disabledVIEW %>></TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >物料版號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="nextPrintCode" size="40" maxlength="10" name="nextPrintCode" value="<%=Util.getStringFormat(mpinfo.getNextPrintCode()) %>" <%=disabledVIEW %>></TD>
			</TR>

			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>郵戳設定</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<INPUT type="radio" id="nextStampSetFg" name="nextStampSetFg" value="1" <% if(null!=mpinfo.getNextStampSetFg() && mpinfo.getNextStampSetFg() ) out.println("checked");%> <%=disabledVIEW %>/> 打郵戳
					<INPUT type="radio" id="nextStampSetFg" name="nextStampSetFg" value="0" <% if(null!=mpinfo.getNextStampSetFg() && !mpinfo.getNextStampSetFg() ) out.println("checked");%> <%=disabledVIEW %>/> 不打郵戳
				</TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>分區／不分區</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<INPUT type="radio" id="nextZipFg" name="nextZipFg" value="1" <% if(null!=mpinfo.getNextZipFg() && mpinfo.getNextZipFg() ) out.println("checked");%> <%=disabledVIEW %>/> 分區
					<INPUT type="radio" id="nextZipFg" name="nextZipFg" value="0" <% if(null!=mpinfo.getNextZipFg() && !mpinfo.getNextZipFg() ) out.println("checked");%> <%=disabledVIEW %>/> 不分區

			</TR>
			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >插件內容二</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input type="checkbox" id="nextMp1Iflag" name="nextMp1Iflag" value="1" <% if(null!=mpinfo.getNextMp1Iflag() && mpinfo.getNextMp1Iflag() ) out.println("checked");%> <%=disabledVIEW %>>智慧型
					<INPUT id="nextMp1Word" size="40"  name="nextMp1Word" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getNextMp1Word()) %>" <%=disabledVIEW %>>
				 </TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >插件內容三</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input type="checkbox" id="nextMp2Iflag" name="nextMp2Iflag" value="1" <% if(null!=mpinfo.getNextMp2Iflag() && mpinfo.getNextMp2Iflag() ) out.println("checked");%> <%=disabledVIEW %>>智慧型
					<INPUT id="nextMp2Word" size="40"  name="nextMp2Word" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getNextMp2Word()) %>" <%=disabledVIEW %>>
                 </TD>
			</TR>
			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >插件內容四</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input type="checkbox" id="nextMp3Iflag" name="nextMp3Iflag" value="1" <% if(null!=mpinfo.getNextMp3Iflag() && mpinfo.getNextMp3Iflag() ) out.println("checked");%> <%=disabledVIEW %>>智慧型
					<INPUT id="nextMp3Word" size="40"  name="nextMp3Word" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getNextMp3Word()) %>" <%=disabledVIEW %>>
				</TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >插件內容五</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input type="checkbox" id="nextMp4Iflag" name="nextMp4Iflag" value="1" <% if(null!=mpinfo.getNextMp4Iflag() && mpinfo.getNextMp4Iflag() ) out.println("checked");%> <%=disabledVIEW %>>智慧型
					<INPUT id="nextMp4Word" size="40"  name="nextMp4Word" maxlength="20" value="<%=Util.getStringFormat(mpinfo.getNextMp4Word()) %>" <%=disabledVIEW %>>
                 </TD>
			</TR>									

			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%"  >注意事項</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left colspan="3">
					<TEXTAREA cols="40" name="nextRemark"  id="nextRemark" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( mpinfo.getNextRemark() ) %></TEXTAREA>                 	
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
						   <input name="button6" type="button" onclick="location='mpinfo.do?fid=editInit&idList=${idList}&mpNo=${prevId}&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
                        </c:if>						   
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='mpinfo.do?fid=saveAsNewInit&mpNo=<%=mpinfo.getMpNo()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='mpinfo.do?fid=editInit&idList=${idList}&mpNo=${nextId}&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
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
