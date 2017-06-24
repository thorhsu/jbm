<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	String message = (String )request.getAttribute("message");
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	AcctChargeItem acctChargeItem= (AcctChargeItem)request.getAttribute("acctChargeItem");
	


	CodeService codeService = CodeService.getInstance();
	List titleList = codeService.findBycodeTypeName("ACCT_TITLE"); //ACCT_TITLE
	List subTitleList = codeService.findBycodeTypeName("ACCT_SUB_TITLE");	//ACCT_SUB_TITLE

	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		acctChargeItem.setId(null);
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		 
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();	
	$("#fubonCode").val("${acctChargeItem.fubonCode}");
	
});
</script>

	<form name="myform" id="myform" action="acctChargeItem.do" method="post">
		<input type="hidden" name="fid" value="<%=fid %>" />
		<input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />		
		<input type="hidden" name="id" value="<%=acctChargeItem.getId() %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">收費項目維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >
                 <font color="#FF0000" size="2">*</font>編號</TD>
				 <TD class="editTableContentCellBKColor" align="left">
					 <% if (null!=acctChargeItem.getId()) out.println(acctChargeItem.getId()); else out.println("(系統自動編號)"); %>
				</TD>
			</TR>
						
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >
                 <font color="#FF0000" size="2">*</font>類別</TD>
				 <TD class="editTableContentCellBKColor" align="left">
					 <SELECT name="itemType" size="1" <%=disabledVIEW %> class="required">
					 <option value="" >請選擇</option>
							<option value="JOB_BAG"  <%if (null!= acctChargeItem.getItemType() && acctChargeItem.getItemType().equalsIgnoreCase("JOB_BAG") ) out.println("selected");  %>>工單類</option>
							<option value="NON_JOB_BAG"  <%if (null!= acctChargeItem.getItemType() && acctChargeItem.getItemType().equalsIgnoreCase("NON_JOB_BAG") ) out.println("selected");  %>>列舉</option>
					</SELECT>
				</TD>
			</TR>
			
			<TR>				
                 <TD class="editTableLabelCellBKColor" width="15%" >
                 <font color="#FF0000" size="2" >*</font>Name</TD>
				 <TD class="editTableContentCellBKColor" width="85%" align="left">
				 <INPUT type="text" id="itemName" size="40"  name="itemName" class="required" value="<%=Util.getStringFormat(acctChargeItem.getItemName() ) %>" <%=disabledVIEW %>>
				 </TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Name.TW</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="itemNameTw" size="40"  name="itemNameTw" value="<%=Util.getStringFormat(acctChargeItem.getItemNameTw() ) %>" <%=disabledVIEW %>></TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Report Title</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="reportTitle" size="40"  name="reportTitle" value="<%=Util.getStringFormat(acctChargeItem.getReportTitle() ) %>" <%=disabledVIEW %>></TD>
			</TR>			
			

			
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="15%" ><font color="#FF0000" size="2">*</font>Title</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
<SELECT name="codeTitle" size="1" <%=disabledVIEW %> class="required" id="codeTitle" >
<option value="" >請選擇</option>
<% for (int i=0; i<titleList.size(); i++) {
	Code _code = (Code)titleList.get(i);
	String selectString = "";
	if (null!= acctChargeItem.getCodeByTitle() && _code.getId().compareTo(acctChargeItem.getCodeByTitle().getId())==0 ) 
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
                 <TD class="editTableLabelCellBKColor" width="15%" ><font color="#FF0000" size="2">*</font>sub Title</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
<SELECT name="codeSubTitle" size="1" <%=disabledVIEW %> class="required" id="codeSubTitle" >
<option value="" >請選擇</option>
<% for (int i=0; i<subTitleList.size(); i++) {
	Code _code = (Code)subTitleList.get(i);
	String selectString = "";
	if (null!= acctChargeItem.getCodeBySubTitle() && _code.getId().compareTo(acctChargeItem.getCodeBySubTitle().getId())==0 ) 
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
                 <TD class="editTableLabelCellBKColor" width="15%" ><font color="#FF0000" size="2">*</font>EP1 account Code</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="ep1AccountCode" size="40"  name="ep1AccountCode" class="required" value="<%=Util.getStringFormat(acctChargeItem.getEp1AccountCode()) %>" <%=disabledVIEW %>></TD>
			</TR>
			
			<TR>				 
                 <TD class="editTableLabelCellBKColor" width="15%" >轉入EP1時, 是否不帶數量</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                    <input type="checkbox" id="isExcludeQty" name="isExcludeQty"  value="1" <% if(null!=acctChargeItem.getIsExcludeQty() && acctChargeItem.getIsExcludeQty() ) out.println("checked");%>  /> (勾選表示不帶數量)
                 </TD>
                 	
			</TR>				
			<TR>				 
                 <TD class="editTableLabelCellBKColor" width="15%" >富邦收費項目對照</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                    <select name="fubonCode" size="1" <%=disabledVIEW %>  id="fubonCode" >
                        <option value="" >無</option>
                        <c:forEach var="code" items="${acctChargeItemForm.fubonChargeItems}" >
                            <option value="${code.id}" >${code.codeValueTw}</option>
                        </c:forEach>
	
                    </select>
                 </TD>
                 	
			</TR>

			<TR>				 
                 <TD class="editTableLabelCellBKColor" width="15%" >計價方式<BR>(列舉不用輸入)</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
					<table border="0" width="100%" id="table9"  >
								<tr>
									<td width="116" class="editTableLabelCellBKColor">
									    <input type="radio" id="calculatType" name="calculatType" value="SUM" <% if(null!=acctChargeItem.getCalculatType() && acctChargeItem.getCalculatType().equalsIgnoreCase("SUM") ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 合計勾選欄位
									</td>
									<td width="523" class="editTableLabelCellBKColor">
										<table border="0" width="100%" id="table10">
										<tr>
											<td width="120">
											<input type="checkbox" id="accounts" name="accounts" value="1" <% if (null!= acctChargeItem.getAccounts() && acctChargeItem.getAccounts()) out.println("checked");%> <%=disabledVIEW %>   >accounts(封數)</td>
											<td width="120">
											<input type="checkbox" id="pages" name="pages" value="1" <% if (null!= acctChargeItem.getPages() && acctChargeItem.getPages()) out.println("checked");%> <%=disabledVIEW %>   >pages(頁數)</td>
											<td width="120">
											<input type="checkbox" id="sheets" name="sheets" value="1" <% if (null!= acctChargeItem.getSheets() && acctChargeItem.getSheets()) out.println("checked");%> <%=disabledVIEW %>   >sheets(張數)</td>
											<td width="120">
											<input type="checkbox" id="pages2" name="pages2" value="1" <% if (null!= acctChargeItem.getPages2() && acctChargeItem.getPages2()) out.println("checked");%> <%=disabledVIEW %>   >pages2(頁數)</td>
											<td width="120">
											<input type="checkbox" id="pages3" name="pages3" value="1" <% if (null!= acctChargeItem.getPages3() && acctChargeItem.getPages3()) out.println("checked");%> <%=disabledVIEW %>   >pages3(頁數)</td>											
										</tr>
										<tr>
											<td width="112">
											<input type="checkbox" id="feeder2" name="feeder2" value="1" <% if (null!= acctChargeItem.getFeeder2() && acctChargeItem.getFeeder2()) out.println("checked");%> <%=disabledVIEW %>   >feeder2(夾寄2)</td>
											<td width="80">
											<input type="checkbox" id="feeder3" name="feeder3" value="1" <% if (null!= acctChargeItem.getFeeder3() && acctChargeItem.getFeeder3()) out.println("checked");%> <%=disabledVIEW %>   >feeder3(夾寄3)</td>
											<td>
											<input type="checkbox" id="feeder4" name="feeder4" value="1" <% if (null!= acctChargeItem.getFeeder4() && acctChargeItem.getFeeder4()) out.println("checked");%> <%=disabledVIEW %>   >feeder4(夾寄4)</td>
											<td>
											<input type="checkbox" id="feeder5" name="feeder5" value="1" <% if (null!= acctChargeItem.getFeeder5() && acctChargeItem.getFeeder5()) out.println("checked");%> <%=disabledVIEW %>   >feeder5(夾寄5)</td>
											<td></td>
										</tr>
	
										<tr>
											<td width="112">
											<input type="checkbox" id="tray1" name="tray1" value="1" <% if (null!= acctChargeItem.getTray1() && acctChargeItem.getTray1()) out.println("checked");%> <%=disabledVIEW %>   >tray1(物料張數1)</td>
											<td width="80">
											<input type="checkbox" id="tray2" name="tray2" value="1" <% if (null!= acctChargeItem.getTray2() && acctChargeItem.getTray2()) out.println("checked");%> <%=disabledVIEW %>   >tray2(物料張數2)</td>
											<td>
											<input type="checkbox" id="tray3" name="tray3" value="1" <% if (null!= acctChargeItem.getTray3() && acctChargeItem.getTray3()) out.println("checked");%> <%=disabledVIEW %>   >tray3(物料張數3)</td>
											<td>
											<input type="checkbox" id="tray4" name="tray4" value="1" <% if (null!= acctChargeItem.getTray4() && acctChargeItem.getTray4()) out.println("checked");%> <%=disabledVIEW %>   >tray4(物料張數4)</td>
											<td></td>
										</tr>
										
										<tr>
											<td width="112">
											<input type="checkbox" id="tray5" name="tray5" value="1" <% if (null!= acctChargeItem.getTray5() && acctChargeItem.getTray5()) out.println("checked");%> <%=disabledVIEW %>   >tray5(物料張數5)</td>
											<td width="80">
											<input type="checkbox" id="tray6" name="tray6" value="1" <% if (null!= acctChargeItem.getTray6() && acctChargeItem.getTray6()) out.println("checked");%> <%=disabledVIEW %>   >tray6(物料張數6)</td>
											<td>
											<input type="checkbox" id="tray7" name="tray7" value="1" <% if (null!= acctChargeItem.getTray7() && acctChargeItem.getTray7()) out.println("checked");%> <%=disabledVIEW %>   >tray7(物料張數7)</td>
											<td>
											<input type="checkbox" id="tray8" name="tray8" value="1" <% if (null!= acctChargeItem.getTray8() && acctChargeItem.getTray8()) out.println("checked");%> <%=disabledVIEW %>   >tray8(物料張數8)</td>
											<td></td>
										</tr>
										
										<tr> 
											<td width="120">
											<input type="checkbox" id="p1accounts" name="p1accounts" value="1" <% if (null!= acctChargeItem.getP1accounts() && acctChargeItem.getP1accounts()) out.println("checked");%> <%=disabledVIEW %>   >p1accounts</td>
											<td width="120">
											<input type="checkbox" id="p2accounts" name="p2accounts" value="1" <% if (null!= acctChargeItem.getP2accounts() && acctChargeItem.getP2accounts()) out.println("checked");%> <%=disabledVIEW %>   >p2accounts</td>
											<td width="120">
											<input type="checkbox" id="p3accounts" name="p3accounts" value="1" <% if (null!= acctChargeItem.getP3accounts() && acctChargeItem.getP3accounts()) out.println("checked");%> <%=disabledVIEW %>   >p3accounts</td>
											<td width="120">
											<input type="checkbox" id="p4accounts" name="p4accounts" value="1" <% if (null!= acctChargeItem.getP4accounts() && acctChargeItem.getP4accounts()) out.println("checked");%> <%=disabledVIEW %>   >p4accounts</td>
											<td width="120">
											<input type="checkbox" id="p5accounts" name="p5accounts" value="1" <% if (null!= acctChargeItem.getP5accounts() && acctChargeItem.getP5accounts()) out.println("checked");%> <%=disabledVIEW %>   >p5accounts</td>											
										</tr>
										<tr> 
											<td width="120">
											   <input type="checkbox" id="p6accounts" name="p6accounts" value="1" <% if (null!= acctChargeItem.getP6accounts() && acctChargeItem.getP6accounts()) out.println("checked");%> <%=disabledVIEW %>   >p6accounts
											</td>
											<td width="120">
											   <input type="checkbox" id="pxaccounts" name="pxaccounts" value="1" <% if (null!= acctChargeItem.getPxaccounts() && acctChargeItem.getPxaccounts()) out.println("checked");%> <%=disabledVIEW %>   >pxaccounts
											</td>
											<td width="120">
											   <input type="checkbox" id="ctaccounts" name="ctaccounts" value="1" <% if (null!= acctChargeItem.getCtaccounts() && acctChargeItem.getCtaccounts()) out.println("checked");%> <%=disabledVIEW %>   >ctaccounts
											</td>
											<td width="120">
											   <input type="checkbox" id="notSent" name="notSent" value="1" <% if (null!= acctChargeItem.getNotSent() && acctChargeItem.getNotSent()) out.println("checked");%> <%=disabledVIEW %>   >N(未寄送數，負值)
											</td>
											<td width="120"></td>
											<td width="120"></td>											
										</tr>																				
										</table>
									</td>
								</tr>

								<tr>
									<td width="116">
									   <input type="radio" id="calculatType" name="calculatType" value="EXPRESSION" <% if(null!=acctChargeItem.getCalculatType() && acctChargeItem.getCalculatType().equalsIgnoreCase("EXPRESSION") ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 自訂運算
									</td>								
									<td width="523">
									    <input name="expression" size="60" value="<%=Util.getStringFormat( acctChargeItem.getExpression()) %>" />
									    (($a+$b)*($c))
									    <br />
									    <input type="checkbox" id="zeroExpress" name="zeroExpress" value="1" <% if (null!= acctChargeItem.getZeroExpress() && acctChargeItem.getZeroExpress()) out.println("checked");%> <%=disabledVIEW %>  />計算結果負值時設為零
									</td>
								</tr>
								
								
								<tr>
									<td width="116" class="editTableLabelCellBKColor">
									<INPUT type="radio" id="calculatType" name="calculatType" value="FIXED" <% if(null!=acctChargeItem.getCalculatType() && acctChargeItem.getCalculatType().equalsIgnoreCase("FIXED") ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 固定值
									</td>		
									<!-- Thor modified -->						
									<td width="523" class="editTableLabelCellBKColor"><INPUT name="fixedValue"  value="<%=Util.getDecimalFormat( acctChargeItem.getFixedValue()) %>"></td>
								</tr>

								<tr>
									<td width="116" >
									<INPUT type="radio" id="calculatType" name="calculatType" value="INPUT" <% if(null!=acctChargeItem.getCalculatType() && acctChargeItem.getCalculatType().equalsIgnoreCase("INPUT") ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 會計人員輸入
									</td>									
									<td width="523"></td>
								</tr>


							</table>                 	
				</TD>
			</TR>
			

																														
<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan="2" align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<c:out value="${pagesIndex}" /> 
						<c:if test="${prevId != null && prevId != ''}">
						   <input name="button6" type="button" onclick="location='acctChargeItem.do?fid=editInit&idList=${idList}&id=${prevId}&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
                        </c:if>						   
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='acctChargeItem.do?fid=saveAsNewInit&id=<%=acctChargeItem.getId()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<!--  input name="button6" type="button"  onclick="location='<%//=backToListURL%>'"  id="button6" value="回列表" / -->
						<input type="button"  onclick="backToList()"  value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='acctChargeItem.do?fid=editInit&idList=${idList}&id=${nextId}&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
						</c:if>



<%  } else { %>
                        <input type="button"  onclick="javascript:history.go(-1)"  value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
