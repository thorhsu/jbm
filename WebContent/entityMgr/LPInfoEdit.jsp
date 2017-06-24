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
	Lpinfo lpinfo= (Lpinfo)request.getAttribute("lpinfo");
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
	

	CodeService codeService = CodeService.getInstance();
	List printerTypeList = codeService.findBycodeTypeName("PRINTER_TYPE"); //印表機類型
	List printTypeList = codeService.findBycodeTypeName("PRINT_TYPE");	//列印方式

	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		lpinfo.setLpNo("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

function changeCodePrintTypeOption(){
   var codePrinterVal = $('#codePrinter').val();
   var codePrintType = $('#codePrintType');
   
   codePrintType.find('option').remove();
   if(codePrinterVal == "3"){
       codePrintType.append("<option value=\"5\" >單面雙模</option>");      
       codePrintType.append("<option value=\"7\" >雙面雙模</option>");
   }else if(codePrinterVal == "1" || codePrinterVal == "2"){
       codePrintType.append("<option value=\"4\" >單面單模</option>");      
       codePrintType.append("<option value=\"6\" >雙面單模</option>");
   }
   
}

function changeNextCodePrintTypeOption(){
   var nextCodePrinterVal = $('#nextCodePrinter').val();
   var nextCodePrintType = $('#nextCodePrintType');

   nextCodePrintType.find('option').remove();
   if(nextCodePrinterVal == "3"){
       nextCodePrintType.append("<option value=\"5\" >單面雙模</option>");      
       nextCodePrintType.append("<option value=\"7\" >雙面雙模</option>");
   }else if(nextCodePrinterVal == "1" || nextCodePrinterVal == "2"){
       nextCodePrintType.append("<option value=\"4\" >單面單模</option>");      
       nextCodePrintType.append("<option value=\"6\" >雙面單模</option>");
   }

}

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate({
	  rules: {
	    prodmemo: {
	      maxlength: 20
	    },
	    nextProdmemo: {
	      maxlength: 20
	    },	    
	    pcode1: {
	       maxlength: 10
	    },
	    pcode2: {
		   maxlength: 10
		},
	    pcode3: {
		   maxlength: 10
		},
	    pcode4: {
		   maxlength: 10
		},
	    pcode5: {
		   maxlength: 10
		},
		pcode6: {
		   maxlength: 10
	    },
		pcode7: {
		   maxlength: 10
		},
		pcode8: {
		   maxlength: 10
	    },		
		nextPcode1: {
		   maxlength: 10
		},
		nextPcode2: {
		   maxlength: 10
	    },
		nextPcode3: {
		   maxlength: 10
	    },
		nextPcode4: {
		   maxlength: 10
		},
		nextPcode5: {
		   maxlength: 10
	    },
		nextPcode6: {
		   maxlength: 10
		},
		nextPcode7: {
		   maxlength: 10
		},
		nextPcode8: {
		   maxlength: 10
		}		
	  }
	});
	$("#myform").submit(function(){
		var nextEffectiveDate = $('#nextEffectiveDate').val();
		var nextCodePrinter = $('#nextCodePrinter').val();
		var nextCodePrintType = $('#nextCodePrintType').val();
		var nextPcode1 = $('#nextPcode1').val();
		
		if (nextEffectiveDate != "") {
			if (nextCodePrinter=="" || nextCodePrintType=="" || nextPcode1=="") {
				submitFinished();
				alert("當預約生效日期有值時，下方的印表機類型 / 列印方式 / 紙槽一 為必填欄位");
				return false;
			}
		}
		return true;
	});
	
	
	//複製資料
	copyData = function (){
		$('#nextProdmemo').val( $('#prodmemo').val() );
		$('#nextPcode1').val( $('#pcode1').val() );		
		$('#nextPcode2').val( $('#pcode2').val() );
		$('#nextPcode3').val( $('#pcode3').val() );		
		$('#nextPcode4').val( $('#pcode4').val() );
		$('#nextPcode5').val( $('#pcode5').val() );		
		$('#nextPcode6').val( $('#pcode6').val() );
		$('#nextPcode7').val( $('#pcode7').val() );		
		$('#nextPcode8').val( $('#pcode8').val() );
		$('#nextRemark').val( $('#remark').val() );
		$('#nextCodePrinter').val( $('#codePrinter').val() );
		changeNextCodePrintTypeOption();
		$('#nextCodePrintType').val( $('#codePrintType').val() );		
		
  	};
	$('#btnCopy').click(copyData);
	
	
		
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('lpinfo.do?fid=checkPKExist', 
	        {lpNo: $('#lpNo').val()},
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
  	$('#lpNo').blur(checkPKExist);
  	
});

//設定列印方式的預設值
$().ready(function() {
   changeCodePrintTypeOption();
   changeNextCodePrintTypeOption();
   var nomatch = true;
   $('#codePrintType option').each(function(){
	   if($(this).val() == "${lpinfo.codeByCodePrintType.id}")
		   nomatch = false;
   });
   //設定與防呆不符時需提出警告
   if(nomatch){
	   var showText;
	   var myPrintType = "${lpinfo.codeByCodePrintType.id}";
	   var codePrintType = $('#codePrintType');
	   if(myPrintType == "5"){
		   showText = "<font color='red'>單面雙模</font>，與印表機防呆設定不符，請檢查是否需要修改 ";
		   codePrintType.append("<option value=\"5\" >單面雙模</option>");
	   }else if(myPrintType == "7"){
		   showText = "<font color='red'>雙面雙模</font>，與印表機防呆設定不符，請檢查是否需要修改 ";
		   codePrintType.append("<option value=\"7\" >雙面雙模</option>");
	   }else if(myPrintType == "4"){
		   showText = "<font color='red'>單面單模</font>，與印表機防呆設定不符，請檢查是否需要修改 ";
		   codePrintType.append("<option value=\"4\" >單面單模</option>");
	   }else if(myPrintType == "6"){
		   showText = "<font color='red'>雙面單模</font>，與印表機防呆設定不符，請檢查是否需要修改 ";
		   codePrintType.append("<option value=\"6\" >雙面單模</option>");
	   }
       $('#showText').html(showText);
   }
	   
	   
   $('#codePrintType').val("${lpinfo.codeByCodePrintType.id}");
   $('#nextCodePrintType').val("${lpinfo.codeByNextCodePrintType.id}");
   
});

</script>

	<form name="myform" id="myform" action="lpinfo.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />	    
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">信紙資料維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>信紙代號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="lpNo" size="40"  name="lpNo" value="<%=lpinfo.getLpNo() %>"  <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="4" minlength="4"><span id="msg_pk_exist"></span></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >客戶代號</TD>
				 <TD class="editTableContentCellBKColor">
					 <SELECT name="custNo" size="1" <%=disabledVIEW %> >
					 <option value="" >共用物料</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";
	if (null!= lpinfo.getCustomer() && _customer.getCustNo().equalsIgnoreCase( lpinfo.getCustomer().getCustNo()) ) 
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
                 	<INPUT id="prodmemo" size="40"  name="prodmemo" value="<%=Util.getStringFormat(lpinfo.getProdmemo()) %>" <%=disabledVIEW %>>
                 </TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>印表機類型</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<SELECT name="codePrinter" size="1" <%=disabledVIEW %> class="required" id="codePrinter" onchange="changeCodePrintTypeOption()">
<option value="" >請選擇</option>
<% for (int i=0; i<printerTypeList.size(); i++) {
	Code _code = (Code)printerTypeList.get(i);
	String selectString = "";
	if (null!= lpinfo.getCodeByCodePrinter() && _code.getId().compareTo(lpinfo.getCodeByCodePrinter().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>                 	
				</TD>
                 
			</TR>
            <TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>列印方式</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                      <SELECT name="codePrintType" size="1" <%=disabledVIEW %> class="required" id="codePrintType" >
                      </SELECT>
					  <span id="showText"></span>
				 </TD>
				 <TD class="editTableLabelCellBKColor" width="10%" >注意事項</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<TEXTAREA cols="40" name="remark"  id="remark" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( lpinfo.getRemark() ) %></TEXTAREA>
                 </TD>
                 
			</TR>
			
			<TR>
			     <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>紙槽一</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="pcode1" size="40"  name="pcode1" value="<%=Util.getStringFormat(lpinfo.getPcode1()) %>" <%=disabledVIEW %> class="required" />
                 </TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >紙槽二</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="pcode2" size="40"  name="pcode2" value="<%=Util.getStringFormat(lpinfo.getPcode2()) %>" <%=disabledVIEW %> />
                 </TD>
                 
			</TR>
			<TR>
			     <TD class="editTableLabelCellBKColor" width="10%" >紙槽三</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="pcode3" size="40"  name="pcode3" value="<%=Util.getStringFormat(lpinfo.getPcode3()) %>" <%=disabledVIEW %> />
                 </TD>
			     <TD class="editTableLabelCellBKColor" width="10%" >紙槽四</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="pcode4" size="40"  name="pcode4" value="<%=Util.getStringFormat(lpinfo.getPcode4()) %>" <%=disabledVIEW %> />
                 </TD>                                  
			</TR>
			<TR>
			     <TD class="editTableLabelCellBKColor" width="10%" >紙槽五</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="pcode5" size="40"  name="pcode5" value="<%=Util.getStringFormat(lpinfo.getPcode5()) %>" <%=disabledVIEW %> />
                 </TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >紙槽六</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="pcode6" size="40"  name="pcode6" value="<%=Util.getStringFormat(lpinfo.getPcode6()) %>" <%=disabledVIEW %> />
                 </TD>
                 
			</TR>
			<TR>
			     <TD class="editTableLabelCellBKColor" width="10%" >紙槽七</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="pcode7" size="40"  name="pcode7" value="<%=Util.getStringFormat(lpinfo.getPcode7()) %>" <%=disabledVIEW %> />
                 </TD>
			     <TD class="editTableLabelCellBKColor" width="10%" >紙槽八</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="pcode8" size="40"  name="pcode8" value="<%=Util.getStringFormat(lpinfo.getPcode8()) %>" <%=disabledVIEW %> />
                 </TD>                                  
			</TR>								

			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >修改後資料生效日期 </TD>
                 <TD colSpan="3"  class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextEffectiveDate" size="40"  name="nextEffectiveDate" value="<%=Util.getStringFormat(lpinfo.getNextEffectiveDate()) %>" <%=disabledVIEW %>>
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
                 	<INPUT id="nextProdmemo" size="40"  name="nextProdmemo" value="<%=Util.getStringFormat(lpinfo.getNextProdmemo()) %>" <%=disabledVIEW %>>
                 </TD>
                                  <TD class="editTableNextLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>印表機類型</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<SELECT name="nextCodePrinter" size="1" <%=disabledVIEW %> id="nextCodePrinter" onchange="changeNextCodePrintTypeOption()" >
<option value="" >請選擇</option>
<% for (int i=0; i<printerTypeList.size(); i++) {
	Code _code = (Code)printerTypeList.get(i);
	String selectString = "";
	if (null!= lpinfo.getCodeByNextCodePrinter() && _code.getId().compareTo(lpinfo.getCodeByNextCodePrinter().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>                 	
				</TD>                 
			</TR>
            <TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>列印方式</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<SELECT name="nextCodePrintType" size="1" <%=disabledVIEW %> id="nextCodePrintType">
<option value="" >請選擇</option>
<% for (int i=0; i<printTypeList.size(); i++) {
	Code _code = (Code)printTypeList.get(i);
	String selectString = "";
	if (null!= lpinfo.getCodeByNextCodePrintType() && _code.getId().compareTo(lpinfo.getCodeByNextCodePrintType().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
					
					</TD>
					<TD class="editTableNextLabelCellBKColor" width="10%" >注意事項</TD>
                    <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	   <TEXTAREA cols="40" name="nextRemark"  id="nextRemark" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( lpinfo.getNextRemark() ) %></TEXTAREA>                 	
                    </TD>                 
			</TR>
			
			<TR>
			     <TD class="editTableNextLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>紙槽一</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextPcode1" size="40"  name="nextPcode1" value="<%=Util.getStringFormat(lpinfo.getNextPcode1()) %>" <%=disabledVIEW %>>
                 </TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >紙槽二</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="nextPcode2" size="40"  name="nextPcode2" value="<%=Util.getStringFormat(lpinfo.getNextPcode2()) %>" <%=disabledVIEW %>>
                 </TD>                 
			</TR>
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >紙槽三</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextPcode3" size="40"  name="nextPcode3" value="<%=Util.getStringFormat(lpinfo.getNextPcode3()) %>" <%=disabledVIEW %>>
                 </TD> 
                 <TD class="editTableNextLabelCellBKColor" width="10%" >紙槽四</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextPcode4" size="40"  name="nextPcode4" value="<%=Util.getStringFormat(lpinfo.getNextPcode4()) %>" <%=disabledVIEW %>>
                 </TD>
                 
			</TR>			
			<TR>
			     <TD class="editTableNextLabelCellBKColor" width="10%" >紙槽五</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextPcode5" size="40"  name="nextPcode5" value="<%=Util.getStringFormat(lpinfo.getNextPcode5()) %>" <%=disabledVIEW %>>
                 </TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >紙槽六</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="nextPcode6" size="40"  name="nextPcode6" value="<%=Util.getStringFormat(lpinfo.getNextPcode6()) %>" <%=disabledVIEW %>>
                 </TD>                 
			</TR>
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >紙槽七</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextPcode7" size="40"  name="nextPcode7" value="<%=Util.getStringFormat(lpinfo.getNextPcode7()) %>" <%=disabledVIEW %>>
                 </TD> 
                 <TD class="editTableNextLabelCellBKColor" width="10%" >紙槽八</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextPcode8" size="40"  name="nextPcode8" value="<%=Util.getStringFormat(lpinfo.getNextPcode8()) %>" <%=disabledVIEW %>>
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
						   <input name="button6" type="button" onclick="location='lpinfo.do?fid=editInit&idList=${idList}&lpNo=${prevId}&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='lpinfo.do?fid=saveAsNewInit&backToListURL=${backToListURL}&lpNo=<%=lpinfo.getLpNo()%>'"  id="button6" value="複製新增" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${ nextId != null && nextId != ''}">						
						   <input name="button6" type="button" onclick="location='lpinfo.do?fid=editInit&idList=${idList}&lpNo=${nextId}&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
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
