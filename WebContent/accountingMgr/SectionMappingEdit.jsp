<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>

<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<% 

	String message = (String )request.getAttribute("message");
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	String queryString = request.getQueryString();
	
	AcctSectionReceiverMapping sectionReceiverMapping= (AcctSectionReceiverMapping)request.getAttribute("sectionReceiverMapping");
	Customer customer= (Customer)request.getAttribute("customer");


	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");
	
    //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; 
		disabledPK="";
		sectionReceiverMapping.setId(null);
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
	

		
%>
<script type="text/javascript"> 
function checkReceivers(){
	var receivers = $("#receivers").val();
	if(receivers == "0" ){
		submitFinished();
		alert("無請款人，請先增加請款人資訊，才可新增或修改");
		return false;
	}else{
		return true;		
	}
}

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	
	

});
</script>

	<form name="myform" id="myform" action="sectionMapping.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="<%=fid %>" />
		<input type="hidden" name="id" value="<%=sectionReceiverMapping.getId() %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">Section跟請款人對應規則維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="20%" >
                 <font color="#FF0000" size="2">*</font>客戶代號</TD>
				 <TD class="editTableContentCellBKColor" align="left"><%=customer.getCustNo() + " | " +  customer.getCustName() %>
				 <input type="hidden" name="custNo" id="custNo" value="<%=customer.getCustNo()%>">
				</TD>

			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="20%" >Section</TD>
                 <TD class="editTableContentCellBKColor" width="30%"align=left>
                 <%=Util.getStringFormat(sectionReceiverMapping.getSection()  ) %>
                 <input type="hidden" name="section" id="section" value="<%=sectionReceiverMapping.getSection()%>">
                 </TD>
			</TR>


			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >VAT</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                    <INPUT type="radio" id="vat" name="vat"  value="1" <% if(null!=sectionReceiverMapping.getVat() && sectionReceiverMapping.getVat() ) out.println("checked");%> <%=disabledVIEW %> />報價不含稅
                    <INPUT type="radio" id="vat" name="vat"  value="0" <% if(null!=sectionReceiverMapping.getVat() && !sectionReceiverMapping.getVat() ) out.println("checked");%> <%=disabledVIEW %> />報價含稅
                    
                                                     
                 </TD>
			</TR>
			

			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >付款天數</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="paymentTerm" size="40"  name="paymentTerm" value="<%=Util.getDecimalFormat(sectionReceiverMapping.getPaymentTerm()) %>" <%=disabledVIEW %>></TD>
			</TR>
			
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="20%" >Report III Title</TD>
                 <TD class="editTableContentCellBKColor" width="30%"align=left>
                 <INPUT type="text" id="reportIiiTitle" size="40"  name="reportIiiTitle" value="<%=Util.getStringFormat(sectionReceiverMapping.getReportIiiTitle() ) %>" <%=disabledVIEW %>>
				 </TD>
			</TR>
			


						
			<TR>
                 <TD class="editTableLabelCellBKColor" width="20%" >合併Report III_1</TD>
                 <TD class="editTableContentCellBKColor" width="30%"align=left>
                 <INPUT type="checkbox" id="mergeReportIiiFg1"  name="mergeReportIiiFg1" value="1" <% if ( null!= sectionReceiverMapping.getMergeReportIiiFg1() && sectionReceiverMapping.getMergeReportIiiFg1() ) out.println("checked"); %> >
                 title:<INPUT type="text" id="mergeReportIiiTitle1" size="40"  name="mergeReportIiiTitle1" value="<%=Util.getStringFormat(sectionReceiverMapping.getMergeReportIiiTitle1() ) %>" <%=disabledVIEW %>>                 
                 sub title:<INPUT type="text" id="mergeReportIiiSubtitle1" size="40"  name="mergeReportIiiSubtitle1" value="<%=Util.getStringFormat(sectionReceiverMapping.getMergeReportIiiSubtitle1() ) %>" <%=disabledVIEW %>>
				 </TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="20%" >合併Report III_2</TD>
                 <TD class="editTableContentCellBKColor" width="30%"align=left>
                 <INPUT type="checkbox" id="mergeReportIiiFg2"  name="mergeReportIiiFg2" value="1" <% if ( null!= sectionReceiverMapping.getMergeReportIiiFg2() && sectionReceiverMapping.getMergeReportIiiFg2() ) out.println("checked"); %> >
                 title:<INPUT type="text" id="mergeReportIiiTitle2" size="40"  name="mergeReportIiiTitle2" value="<%=Util.getStringFormat(sectionReceiverMapping.getMergeReportIiiTitle2() ) %>" <%=disabledVIEW %>>
                 sub title:<INPUT type="text" id="mergeReportIiiSubtitle2" size="40"  name="mergeReportIiiSubtitle2" value="<%=Util.getStringFormat(sectionReceiverMapping.getMergeReportIiiSubtitle2() ) %>" <%=disabledVIEW %>>
				 </TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="20%" >合併Report III_3</TD>
                 <TD class="editTableContentCellBKColor" width="30%"align=left>
                 <INPUT type="checkbox" id="mergeReportIiiFg3"  name="mergeReportIiiFg3" value="1" <% if ( null!= sectionReceiverMapping.getMergeReportIiiFg3() && sectionReceiverMapping.getMergeReportIiiFg3() ) out.println("checked"); %> >
                 title:<INPUT type="text" id="mergeReportIiiTitle3" size="40"  name="mergeReportIiiTitle3" value="<%=Util.getStringFormat(sectionReceiverMapping.getMergeReportIiiTitle3() ) %>" <%=disabledVIEW %>>                 
                 sub title:<INPUT type="text" id="mergeReportIiiSubtitle3" size="40"  name="mergeReportIiiSubtitle3" value="<%=Util.getStringFormat(sectionReceiverMapping.getMergeReportIiiSubtitle3() ) %>" <%=disabledVIEW %>>
				 </TD>
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="20%" >Report I 是否需要合計</TD>
                 <TD class="editTableContentCellBKColor" width="30%"align=left>
                 <INPUT type="checkbox" id="isSummarizationOfSum1"  name="isSummarizationOfSum1" value="1" <% if ( null!= sectionReceiverMapping.getIsSummarizationOfSum1() && sectionReceiverMapping.getIsSummarizationOfSum1() ) out.println("checked"); %> >
                 (打勾表示要合計)
				 </TD>
			</TR>

			<TR>
                 <TD class="editTableLabelCellBKColor" width="20%" >請款人資訊<BR>(請挑選請款人)</TD>
                 <TD class="editTableContentCellBKColor" width="80%"align=left>
					<table border="0" width="100%" id="table7" bgcolor="#DFF4DD" >
						<tr>
							<td bgcolor="#4AAE66" width="58">　</td>
							<td bgcolor="#4AAE66" width="33"><font size="2">編號</font></td>
							<td bgcolor="#4AAE66" width="33"><font size="2">客戶<br>
							代號</font></td>
							<td bgcolor="#4AAE66" width="72"><font size="2">
							客戶簡稱</font></td>
							<td bgcolor="#4AAE66" width="59"><font size="2">請款人</font></td>
							<td bgcolor="#4AAE66" width="78"><font size="2">發票抬頭</font></td>
							<td bgcolor="#4AAE66" width="67"><font size="2">電話</font></td>
							<td bgcolor="#4AAE66" width="78"><font size="2">地址</font></td>
							<td bgcolor="#4AAE66"><font size="2">統一編號</font></td>
						</tr>

<%
Set customerReceivers = customer.getCustomerReceivers();	
int receivers = (customerReceivers == null)? 0 : customerReceivers.size();
Iterator i = customerReceivers.iterator();
while (i.hasNext() ) {
	CustomerReceiver customerReceiver = (CustomerReceiver)i.next();
%>	
	

						<tr>
							<td width="58"><INPUT type="radio" id="receiverNo" name="receiverNo"  value="<%=customerReceiver.getReceiverNo() %>" <% if(null!=sectionReceiverMapping.getCustomerReceiver() && sectionReceiverMapping.getCustomerReceiver().getReceiverNo().equalsIgnoreCase(customerReceiver.getReceiverNo()) ) out.println("checked");%> <%=disabledVIEW %> class="required"/> </td>
							<td width="33"><font size="2"><%=customerReceiver.getReceiverNo() %></font></td>
							<td width="33"><font size="2"><%=customerReceiver.getCustomer().getCustNo() %></font></td>
							<td width="72"><font size="2"><%=customerReceiver.getCustomer().getCustName() %></font></td>
							<td width="59"><font size="2"><%=customerReceiver.getReceiver() %></font></td>
							<td width="78"><font size="2"><%=customerReceiver.getInvoiceTitle() %></font></td>
							<td width="67"><font size="2"><%=customerReceiver.getTel() %></font></td>
							<td width="192"><font size="2"><%=customerReceiver.getAddress() %></font></td>
							<td><font size="2"><%=customerReceiver.getPin() %></font></td>
						</tr>

<%
}
%>
						</table>


				 </TD>
			</TR>							
																																			
<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan="2" align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" onclick="return checkReceivers()" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<input name="button6" type="submit"  id="button6" value="更新" onclick="return checkReceivers()" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
<%  } else { %>
						<input  type="button"  onclick="backToList()"   value="回列表" />
<%  } %>
                        <input type="hidden" id="recievers" value="<%= receivers %>"/>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
