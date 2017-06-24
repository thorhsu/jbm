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
	List jobCodeList= (List)request.getAttribute("jobCodeList"); // JobCode
	List acctJobCodeChargeItemList= (List)request.getAttribute("acctJobCodeChargeItemList"); //acctJobCodeChargeItem
	List acctCustomerContractList= (List)request.getAttribute("acctCustomerContractList"); //acctCustomerContractList

	// 找出第一筆 作為顯示用
	JobCode jobcode = (JobCode)jobCodeList.get(0);

	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate({
		  rules: {
		    "splitJobCodePercent1": {
		      number: true
		    },
			 "splitJobCodePercent2": {
		      number: true
		    },
			 "splitJobCodePercent3": {
		      number: true
		    },
			 "splitJobCodePercent4": {
			 number: true
			}
		  }
		});
	
	//檢查合計是否相同 
	$("#myform").submit(function(){
		var splitJobCodePercent1 = $('#splitJobCodePercent1').val() ;
		var splitJobCodePercent2 = $('#splitJobCodePercent2').val() ;
		var splitJobCodePercent3 = $('#splitJobCodePercent3').val() ;
		var splitJobCodePercent4 = $('#splitJobCodePercent4').val() ;		
		if (parseFloat(splitJobCodePercent1) > 1 || parseFloat(splitJobCodePercent2) > 1 ||
			parseFloat(splitJobCodePercent3) > 1 || parseFloat(splitJobCodePercent4) > 1) {
			submitFinished();
			alert("拆帳比例要小於 1 ");
			return false;
		}
		
		if (parseFloat(splitJobCodePercent1) +  parseFloat(splitJobCodePercent2)  + 
				parseFloat(splitJobCodePercent3) +  parseFloat(splitJobCodePercent4) >= 1) {
			    submitFinished();
				alert("拆帳比例總合 要小於 1 ");
				return false;
			}
		
		return ret; 	
	});	

});
</script>

	<form name="myform" id="myform" action="acctJobCodeChargeItem.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />	    
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">工單收費項目維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >
                 <font color="#FF0000" size="2">*</font>客戶代號</TD>
				 <TD class="editTableContentCellBKColor" colspan="4"> 
					 <%=jobcode.getCustomer().getCustNo() %> | <%=jobcode.getCustomer().getCustName() %>
				</TD>
			</TR>
<%	
	String costCenter="";
	String splitJobCodeNo1="";
	String splitJobCodeNo2="";
	String splitJobCodeNo3="";
	String splitJobCodeNo4="";	
	Double splitJobCodePercent1=0D;
	Double splitJobCodePercent2=0D;
	Double splitJobCodePercent3=0D;
	Double splitJobCodePercent4=0D;	
	String mainJobCodeNo="";
	String mainJobCodeProgNm="";
	for (int i=0; i<jobCodeList.size();i++)  { 
		JobCode _jobcode = (JobCode)jobCodeList.get(i);
		if (null!= _jobcode.getCostCenter()) costCenter = _jobcode.getCostCenter();
		if (null!= _jobcode.getSplitJobCodeNo1()) splitJobCodeNo1 = _jobcode.getSplitJobCodeNo1();
		if (null!= _jobcode.getSplitJobCodeNo2()) splitJobCodeNo2 = _jobcode.getSplitJobCodeNo2();
		if (null!= _jobcode.getSplitJobCodeNo3()) splitJobCodeNo3 = _jobcode.getSplitJobCodeNo3();
		if (null!= _jobcode.getSplitJobCodeNo4()) splitJobCodeNo4 = _jobcode.getSplitJobCodeNo4();
		
		if (null!= _jobcode.getSplitJobCodePercent1()) splitJobCodePercent1 = _jobcode.getSplitJobCodePercent1();		
		if (null!= _jobcode.getSplitJobCodePercent2()) splitJobCodePercent2 = _jobcode.getSplitJobCodePercent2();
		if (null!= _jobcode.getSplitJobCodePercent3()) splitJobCodePercent3 = _jobcode.getSplitJobCodePercent3();
		if (null!= _jobcode.getSplitJobCodePercent4()) splitJobCodePercent4 = _jobcode.getSplitJobCodePercent4();	
		if (null!= _jobcode.getMainJobCodeNo()) mainJobCodeNo = _jobcode.getMainJobCodeNo();	
		if (null!= _jobcode.getMainJobCodeProgNm()) mainJobCodeProgNm = _jobcode.getMainJobCodeProgNm();	
		
%>			
<script type="text/javascript"> 
$().ready(function() {
	$("#splitJobCodeNo1").val("<%=splitJobCodeNo1%>");
	$("#splitJobCodeNo2").val("<%=splitJobCodeNo2%>");
	$("#splitJobCodeNo3").val("<%=splitJobCodeNo3%>");
	$("#splitJobCodeNo4").val("<%=splitJobCodeNo4%>");
});
</script>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >工單樣本代號</TD>
				 <TD class="editTableContentCellBKColor" colspan="4">
				 	 <input type="hidden" name="jobCodeNos" value="<%=_jobcode.getJobCodeNo() %>">
					 <%=_jobcode.getJobCodeNo() %> | <%=_jobcode.getProgNm() %>
				</TD>					
			</TR>

			
						
<%} %>			

			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" colspan="5">			<HR></TD>
				
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Cost Center</TD>
				 <TD class="editTableContentCellBKColor" colspan="4">
				 	 <input type="text" name="costCenter" id="costCenter" value="<%=costCenter %>">
				</TD>
			</TR>						
			

			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >合併工單設定<BR>用於 多張工單要合併到一張工單計價</TD>
				 <TD class="editTableContentCellBKColor" colspan="4">
				 	 <input type="text" name="mainJobCodeNo" id="mainJobCodeNo" value="<%=mainJobCodeNo %>"> 
				 	 合併工單名稱 <input type="text" name="mainJobCodeProgNm" id="mainJobCodeProgNm" value="<%=mainJobCodeProgNm %>">
				</TD>
			</TR>			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >工單拆帳設定<BR>一張工單要拆成多張工單計價</TD>
				 <TD class="editTableContentCellBKColor" colspan="4">
				 	工單編號1
				 	    <select name="splitJobCodeNo1" id="splitJobCodeNo1">
				 	       <option value="">請選擇</option>
				 	       <c:forEach var="splitJobCode" items="${spliteJobCodes}" varStatus="stauts" >
				 	           <option value="${splitJobCode.jobCodeNo}">${splitJobCode.jobCodeNo}</option>
				 	       </c:forEach>
				 	    </select>
				 	 拆帳比例1
				 	 <input type="text" name="splitJobCodePercent1" id="splitJobCodePercent1" value="<%=splitJobCodePercent1 %>">(小數比例, ex: 0.1)<BR>
				 	工單編號2
				 	    <select name="splitJobCodeNo2" id="splitJobCodeNo2">
				 	       <option value="">請選擇</option>
				 	       <c:forEach var="splitJobCode" items="${spliteJobCodes}" varStatus="stauts" >
				 	           <option value="${splitJobCode.jobCodeNo}">${splitJobCode.jobCodeNo}</option>
				 	       </c:forEach>
				 	    </select>
				 	 拆帳比例2
				 	 <input type="text" name="splitJobCodePercent2" id="splitJobCodePercent2" value="<%=splitJobCodePercent2 %>">(小數比例, ex: 0.1)<BR>
				 	工單編號3
				 	   <select name="splitJobCodeNo3" id="splitJobCodeNo3">
				 	       <option value="">請選擇</option>
				 	       <c:forEach var="splitJobCode" items="${spliteJobCodes}" varStatus="stauts" >
				 	           <option value="${splitJobCode.jobCodeNo}">${splitJobCode.jobCodeNo}</option>
				 	       </c:forEach>
				 	    </select>
				 	 拆帳比例3
				 	 <input type="text" name="splitJobCodePercent3" id="splitJobCodePercent3" value="<%=splitJobCodePercent3 %>">(小數比例, ex: 0.1)<BR>
				 	工單編號4
				 	   <select name="splitJobCodeNo4" id="splitJobCodeNo4">
				 	       <option value="">請選擇</option>
				 	       <c:forEach var="splitJobCode" items="${spliteJobCodes}" varStatus="stauts" >
				 	           <option value="${splitJobCode.jobCodeNo}">${splitJobCode.jobCodeNo}</option>
				 	       </c:forEach>
				 	    </select>
				 	
				 	 拆帳比例4
				 	 <input type="text" name="splitJobCodePercent4" id="splitJobCodePercent4" value="<%=splitJobCodePercent4 %>">(小數比例, ex: 0.1)			 	 				 	 				 	 
				</TD>							
			</TR>
						
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" colspan="5">			<HR></TD>
				
			</TR>			

			<TR>              			
                 <TD class="editTableLabelCellBKColor" width="30%" >收費項目</TD>
				 <TD class="editTableLabelCellBKColor" width="15%">單價金額</TD>
                 <TD class="editTableLabelCellBKColor" width="15%" >是否收費</TD>
                 <TD class="editTableLabelCellBKColor" width="15%" >調整係數</TD>
                 <TD class="editTableLabelCellBKColor" width="15%" >列印次數</TD>
			</TR>
			
<% 
Date today = new Date();
for(int i=0; i<acctCustomerContractList.size(); i++)  { 
	AcctCustomerContract acctCustomerContract = (AcctCustomerContract)acctCustomerContractList.get(i);
	
	String checkedString = "";
	Integer acctJobCodeChargeItemId=0;
	Double adjustmentPercent=1.0D;
	Integer printTime=1;
	String warnStr = "";
	//找出工單樣本是否有此收費項目
	if (null != acctJobCodeChargeItemList ) {
		for (int j=0; j<acctJobCodeChargeItemList.size();j++) {
			AcctJobCodeChargeItem _acctJobCodeChargeItem = (AcctJobCodeChargeItem)acctJobCodeChargeItemList.get(j);

			 if (_acctJobCodeChargeItem.getAcctChargeItem().getId().compareTo(acctCustomerContract.getAcctChargeItem().getId()) ==0 ) {
				 checkedString = "checked";
				 if(today.getTime() > _acctJobCodeChargeItem.getAcctCustomerContract().getContactDateEnd().getTime())
					 warnStr = "已超過合約收費期限";
				 else if(today.getTime() < _acctJobCodeChargeItem.getAcctCustomerContract().getContactDateBegin().getTime())
					 warnStr = "尚未到達合約收費起始日";
				 acctJobCodeChargeItemId = _acctJobCodeChargeItem.getId();
				 if (null!= _acctJobCodeChargeItem.getAdjustmentPercent() )
				 	adjustmentPercent = _acctJobCodeChargeItem.getAdjustmentPercent();
				 
				 if (null!= _acctJobCodeChargeItem.getAdjustmentPercent() )
					 	adjustmentPercent = _acctJobCodeChargeItem.getAdjustmentPercent();
				 
				 if (null!= _acctJobCodeChargeItem.getPrintTimes() )
					 printTime = _acctJobCodeChargeItem.getPrintTimes();
				 
			 }
		}
	}
%>			
			<TR>
                 <TD class="editTableLabelCellBKColor"  >
                 <input type="hidden" name="acctJobCodeChargeItemIds" value="<%=acctJobCodeChargeItemId %>">
                 <input type="hidden" name="chargeItemIds" value="<%=acctCustomerContract.getAcctChargeItem().getId() %>">
                 <input type="hidden" name="acctCustomerContractIds" value="<%=acctCustomerContract.getId() %>">
                    <a href="acctChargeItem.do?fid=view&id=<%=acctCustomerContract.getAcctChargeItem().getId() %>&idList=<%=acctCustomerContract.getAcctChargeItem().getId() %>&backToListURL=${backToListURL}">
                       <%=acctCustomerContract.getAcctChargeItem().getItemName() + "|" +  acctCustomerContract.getAcctChargeItem().getItemNameTw() %>
                    </a>
                    <font color="red"><%= warnStr%></font></p>
                 </TD>                 
				 <TD class="editTableContentCellBKColor" ><%=acctCustomerContract.getUnitPrice() %></TD>
                 <TD class="editTableContentCellBKColor"  ><INPUT type="checkbox" id="selectChargeItemIds" size="10"  name="selectChargeItemIds" value="<%=acctCustomerContract.getAcctChargeItem().getId() %>" <%=checkedString %> ></TD>
                 <TD class="editTableContentCellBKColor"  ><input type="text" name="adjustmentPercents" id="adjustmentPercents" value="<%=adjustmentPercent %>"></TD>
                 <TD class="editTableContentCellBKColor"  ><input type="text" name="printTimes" id="printTimes" value="<%=printTime %>"></TD>
			</TR>
<%} %>
<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan="5" align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<c:out value="${pagesIndex}" /> 
						<c:if test="${prevId != null && prevId != ''}">
						   <input name="button6" type="button" onclick="location='acctJobCodeChargeItem.do?fid=editInit&idList=${idList}&jobCodeNo=${prevId}&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>   
						<input name="button6" type="submit"  id="button6" value="更新" />
						<!-- input name="button6" type="button"  onclick="location='<%//=backToListURL%>'"  id="button6" value="回列表" / -->
						<input  type="button"  onclick="backToList()"  value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='acctJobCodeChargeItem.do?fid=editInit&idList=${idList}&jobCodeNo=${nextId}&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
						</c:if>   
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("BATCH_EDIT")   ) { %>
						<input name="button6" type="submit"  id="button6" value="批次更新" />
						<input name="button6" type="button"  onclick="location='acctJobCodeChargeItem.do?fid=list&backToListURL=${backToListURL}'"  id="button6" value="回列表" />
<%  } else { %>
                        <input  type="button"  onclick="backToList()"  value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
