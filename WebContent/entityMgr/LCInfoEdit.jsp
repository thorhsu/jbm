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
	Lcinfo lcinfo= (Lcinfo)request.getAttribute("lcinfo");
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
		lcinfo.setLcNo("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 
function changePList(){
  
  var templateVal = $("#template").val();
  var nextTemplateVal = $("#nextTemplate").val();
  if(templateVal == "FUBON"){
      var pLists = $('input:radio[name="PList"]');
	  pLists.filter('[value=0]').attr('checked', true);
	  pLists.filter('[value=1]').attr('checked', false);
      $("#PList1").hide();
      $("#PList2").show();
  }else{
      $("#PList1").show();
      $("#PList2").show();
  }
  if(nextTemplateVal == "FUBON"){
      var nextPLists = $('input:radio[name="nextPList"]');
	  nextPLists.filter('[value=0]').attr('checked', true);
	  nextPLists.filter('[value=1]').attr('checked', false);
      $("#nextPList1").hide();
      $("#nextPList2").show();
  }else{
      $("#nextPList1").show();
      $("#nextPList2").show();
  }
  
}

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	
	
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('lcinfo.do?fid=checkPKExist', 
	        {lcNo: $('#lcNo').val()},
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
  	$('#lcNo').blur(checkPKExist);
  	changePList()
});
</script>

	<form name="myform" id="myform" action="lcinfo.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />	    
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">交寄管制表資料維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>交寄管制表代號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="lcNo" size="40"  name="lcNo" value="<%=lcinfo.getLcNo() %>"  <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="4" minlength="4"><span id="msg_pk_exist"></span></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>
                 客戶代號</TD>
				 <TD class="editTableContentCellBKColor">
					 <SELECT name="custNo" size="1" <%=disabledVIEW %> class="required">
					 <option value="" >請選擇</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";
	if (null!= lcinfo.getCustomer() && _customer.getCustNo().equalsIgnoreCase( lcinfo.getCustomer().getCustNo()) ) 
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
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>交寄管制表抬頭</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="progNm" size="40"  name="progNm" value="<%=Util.getStringFormat(lcinfo.getProgNm()) %>" <%=disabledVIEW %> class="required"></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>列印同一型態明細</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<span id="PList1" ><INPUT type="radio"  name="PList"  value="1" <% if(null!=lcinfo.getPList() && lcinfo.getPList() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 是</span>
					<span id="PList2" ><INPUT type="radio"  name="PList"  value="0" <% if(null!=lcinfo.getPList() && !lcinfo.getPList() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 否</span>
				</TD>
			</TR>

			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%"  ><font color="#FF0000" size="2">*</font>交寄明細模板</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left >
<SELECT name="template" size="1" <%=disabledVIEW %> id="template" class="required" onchange="changePList()">
<option value="" >請選擇</option>
<option value="STANDARD_POST_OFFICE" <% if (null!= lcinfo.getTemplate() && lcinfo.getTemplate().equalsIgnoreCase("STANDARD_POST_OFFICE")) out.println("selected"); %> >標準版(依郵局分頁)</option>
<option value="STANDARD_POST_OFFICE_CUSTOMER" <% if (null!= lcinfo.getTemplate() && lcinfo.getTemplate().equalsIgnoreCase("STANDARD_POST_OFFICE_CUSTOMER")) out.println("selected"); %> >標準版(依郵局+客戶分頁)</option>
<option value="FUBON" <% if (null!= lcinfo.getTemplate() && lcinfo.getTemplate().equalsIgnoreCase("FUBON")) out.println("selected"); %> >富邦</option>
</SELECT>                    	 
				</TD>

                 <TD class="editTableLabelCellBKColor" width="10%" >額外交寄方式</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<table border="0" width="100%" >
						<tr>
							<td>1</td>
							<td>
								<INPUT id="extraLgType1" size="40"  name="extraLgType1" value="<%=Util.getStringFormat(lcinfo.getExtraLgType1()) %>" <%=disabledVIEW %>>
							</td>
						</tr>
						<tr>
							<td>2</td>
							<td>
								<INPUT id="extraLgType2" size="40"  name="extraLgType2" value="<%=Util.getStringFormat(lcinfo.getExtraLgType2()) %>" <%=disabledVIEW %>>
							</td>
						</tr>
						<tr>							
							<td>3</td>
							<td>
								<INPUT id="extraLgType3" size="40"  name="extraLgType3" value="<%=Util.getStringFormat(lcinfo.getExtraLgType3()) %>" <%=disabledVIEW %>>
							</td>
						</tr>
						<tr>							
							<td>4</td>
							<td>
								<INPUT id="extraLgType4" size="40"  name="extraLgType4" value="<%=Util.getStringFormat(lcinfo.getExtraLgType4()) %>" <%=disabledVIEW %>>
							</td>
						</tr>
						<tr>							
							<td>5</td>
							<td>
								<INPUT id="extraLgType5" size="40"  name="extraLgType5" value="<%=Util.getStringFormat(lcinfo.getExtraLgType5()) %>" <%=disabledVIEW %>>
							</td>
						</tr>
						<tr>							
							<td>6</td>
							<td>
								<INPUT id="extraLgType6" size="40"  name="extraLgType6" value="<%=Util.getStringFormat(lcinfo.getExtraLgType6()) %>" <%=disabledVIEW %>>
							</td>							
						</tr>
					</table>
					</TD>
				
				
			</TR>
			



							
							
			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >修改後資料生效日期</TD>
                 <TD colSpan="3"  class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextEffectiveDate" size="40"  name="nextEffectiveDate" value="<%=Util.getStringFormat(lcinfo.getNextEffectiveDate()) %>" <%=disabledVIEW %>>
<img border="0" alt="日期選擇輔助工具" id="nextEffectiveDate_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "nextEffectiveDate",						
		    button : "nextEffectiveDate_datepicker"
		});
</script>                 	
                 	</TD>
			</TR>					

			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>交寄管制表抬頭</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="nextProgNm" size="40"  name="nextProgNm" value="<%=Util.getStringFormat(lcinfo.getNextProgNm()) %>" <%=disabledVIEW %> ></TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>列印同一型態明細</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<span id="nextPList1"><INPUT type="radio"  name="nextPList"  value="1" <% if(null!=lcinfo.getNextPList() && lcinfo.getNextPList() ) out.println("checked");%> <%=disabledVIEW %> /> 是</span>
					<span id="nextPList2"><INPUT type="radio"  name="nextPList"  value="0" <% if(null!=lcinfo.getNextPList() && !lcinfo.getNextPList() ) out.println("checked");%> <%=disabledVIEW %> /> 否</span>
				</TD>
			</TR>

			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%"  ><font color="#FF0000" size="2">*</font>交寄明細模板</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left colspan="3">
<SELECT name="nextTemplate" id="nextTemplate" size="1" <%=disabledVIEW %> onchange="changePList()" >
<option value="" >請選擇</option>
<option value="STANDARD_POST_OFFICE" <% if (null!= lcinfo.getNextTemplate() && lcinfo.getNextTemplate().equalsIgnoreCase("STANDARD_POST_OFFICE")) out.println("selected"); %> >標準版(依郵局分頁)</option>
<option value="STANDARD_POST_OFFICE_CUSTOMER" <% if (null!= lcinfo.getNextTemplate() && lcinfo.getNextTemplate().equalsIgnoreCase("STANDARD_POST_OFFICE_CUSTOMER")) out.println("selected"); %> >標準版(依郵局+客戶分頁)</option>
<option value="FUBON" <% if (null!= lcinfo.getNextTemplate() && lcinfo.getNextTemplate().equalsIgnoreCase("FUBON")) out.println("selected"); %> >富邦</option>
</SELECT>                    	</TD>
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
						   <input name="button6" type="button" onclick="location='lcinfo.do?fid=editInit&idList=${idList}&lcNo=${prevId }&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='lcinfo.do?fid=saveAsNewInit&lcNo=<%=lcinfo.getLcNo()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='lcinfo.do?fid=editInit&idList=${idList}&lcNo=${nextId }&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
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
