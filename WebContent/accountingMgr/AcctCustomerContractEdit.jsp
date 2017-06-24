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
	AcctCustomerContract acctCustomerContract= (AcctCustomerContract)request.getAttribute("acctCustomerContract"); 
	List acctCustomerContractList= (List)request.getAttribute("acctCustomerContractList"); 
    List chargeItemList =(List) request.getAttribute("chargeItemList"); //收費項目清單
    List chargeItemsNonJobBag =(List) request.getAttribute("chargeItemsNonJobBag"); //收費項目清單
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
 
	if (null!= acctCustomerContractList && acctCustomerContractList.size() >0 ) {
		acctCustomerContract = (AcctCustomerContract)acctCustomerContractList.get(0);
	    request.setAttribute("acctCustomerContract", acctCustomerContract);
	}

	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		acctCustomerContract.setId(null);
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate({
	  rules: {
	    unitPrices: {
	      number: true
	    }	    
	  }
	});	
	
	//設定自動展延的值
	$("#autoExtend").val(['<c:out value="${acctCustomerContract.autoExtendForm}" default="1" />']);
});

function dropDownMenu(clickObj){
	clickObj.selectedIndex=clickObj.defaultIndex;
	alert("如欲修改收費項目，請先刪除後再新增");
}
</script>

	<form name="myform" id="myform" action="acctCustomerContract.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">客戶合約價格維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" >
                 <font color="#FF0000" size="2">*</font>客戶代號</TD>
				 <TD class="editTableContentCellBKColor" align="left">
					 <SELECT name="custNo" size="1" <%=disabledVIEW %> <%=disabledPK %> class="required" >
					 <option value="" >請選擇</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";
	if (null!= acctCustomerContract.getCustomer() && _customer.getCustNo().equalsIgnoreCase( acctCustomerContract.getCustomer().getCustNo()) ) 
		selectString="selected";
%>					 
							<option value="<%=_customer.getCustNo() %>" <%=selectString %> >
								<%=_customer.getCustNo() + "-" + _customer.getEasyName() %>
							</option>
<% }%>	
					</SELECT>
				</TD>
                 <TD class="editTableLabelCellBKColor"  ><font color="#FF0000" size="2">*</font>Start Date</TD>
                 <TD class="editTableContentCellBKColor"  align="left">
                 <INPUT id="contactDateBegin_form" size="10"  name="contactDateBegin_form" <%=disabledPK %> value="<%=Util.getDateFormat(acctCustomerContract.getContactDateBegin()) %>" class="required" <%=disabledVIEW %>>
<% if (null!=actionMode &&  !actionMode.equalsIgnoreCase("EDIT") ){             %>     	
<img border="0" alt="日期選擇輔助工具" id="contactDateBegin_form_datepicker" src="images/calendar2.gif" />
<script language="javascript">
		Calendar.setup({
			inputField : "contactDateBegin_form",						
		    button : "contactDateBegin_form_datepicker"
		});
</script>
<% } %>	
                 
                 </TD>                 
                 				
			</TR>
						
			<TR>
                 <TD class="editTableLabelCellBKColor"  >
                                                       合約自動展延（每次一個月）
                 </TD>
				 <TD class="editTableContentCellBKColor" align="left">
                     <input type="checkbox" id="autoExtend" name="autoExtend" value="1"  <%=disabledVIEW %>  />自動展延        				 
				</TD>
                 <TD class="editTableLabelCellBKColor"  >
                 <font color="#FF0000" size="2">*</font>End Date</TD>
				 <TD class="editTableContentCellBKColor" align="left" >
                 	<INPUT id="contactDateEnd_form" size="10"  name="contactDateEnd_form" <%=disabledPK %> value="<%=Util.getDateFormat(acctCustomerContract.getContactDateEnd()) %>" class="required" <%=disabledVIEW %>>	
<img border="0" alt="日期選擇輔助工具" id="contactDateEnd_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "contactDateEnd_form",						
		    button : "contactDateEnd_datepicker"
		});
</script>		 
				</TD>
			</TR>
			</TABLE>
            <TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor" >
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" colspan="4">			<HR></TD>
				
			</TR>
			<TR>
			     <TD class="editTableLabelCellBKColor" width="3%" >刪除</TD>
                 <TD class="editTableLabelCellBKColor" width="30%" >收費項目</TD>
				 <TD class="editTableContentCellBKColor" width="15%">單價金額 (小數6)	</TD>			 
                 <TD class="editTableContentCellBKColor" width="15%" >報表上顯示的名稱</TD>			
			</TR>
<!-- 該客戶+區間 已經存在的項目  -->
<% if (null!= acctCustomerContractList && acctCustomerContractList.size() >0 ) { %>


<%  for (int loop=0; loop< acctCustomerContractList.size(); loop++) { %>			
<% 
	String chargeItemSelector="<SELECT  id=\"chargeItemIds" + loop + "\" name=\"chargeItemIds\" size=\"1\" onfocus=\"this.defaultIndex=this.selectedIndex;\" onchange=\"dropDownMenu(this)\" > ";
	Double unitPrice =0.0D;
	String reportTitle="";
	Integer acctCustomerContractId = 0;

	 AcctCustomerContract _acctCustomerContract = (AcctCustomerContract)acctCustomerContractList.get(loop);
	 acctCustomerContractId = _acctCustomerContract.getId();
	 if (null!= _acctCustomerContract.getReportTitle())
	 	reportTitle = _acctCustomerContract.getReportTitle();
	

			 
boolean hadSelected = false;			 		
for(int i=0; i<chargeItemList.size(); i++)  { 
	AcctChargeItem acctChargeItem = (AcctChargeItem)chargeItemList.get(i);
	String selectString="";
	 if (null!= _acctCustomerContract.getAcctChargeItem() && _acctCustomerContract.getAcctChargeItem().getId().compareTo(acctChargeItem.getId()) == 0 ) {
	 	unitPrice = _acctCustomerContract.getUnitPrice();
		if (reportTitle.length()==0 &&  null!= acctChargeItem.getReportTitle())
	 		reportTitle = acctChargeItem.getReportTitle();
	 	selectString = "selected=\"selected\" ";
	 	hadSelected = true;
	 }
	
	chargeItemSelector= chargeItemSelector + "<option value='" + acctChargeItem.getId() + "' " + selectString +" >" + acctChargeItem.getItemName() + "|" + acctChargeItem.getItemNameTw() + "</option>";
}
String appendStr = "";
if(!hadSelected){
	appendStr = "<font color='red'>此收費項目為列舉類，請刪除</font>";
	chargeItemSelector = "<SELECT  id=\"chargeItemIds" + loop + "\" name=\"chargeItemIds\" size=\"1\" onfocus=\"this.defaultIndex=this.selectedIndex;\" onchange=\"dropDownMenu(this)\" > ";
	for(int i=0; i<chargeItemsNonJobBag.size(); i++)  { 
		AcctChargeItem acctChargeItem = (AcctChargeItem)chargeItemsNonJobBag.get(i);
		String selectString="";		
		 if (null!= _acctCustomerContract.getAcctChargeItem() && _acctCustomerContract.getAcctChargeItem().getId().compareTo(acctChargeItem.getId()) == 0 ) {
		 	unitPrice = _acctCustomerContract.getUnitPrice();
			if (reportTitle.length()==0 &&  null!= acctChargeItem.getReportTitle())
		 		reportTitle = acctChargeItem.getReportTitle();
		 	selectString = "selected=\"selected\" ";
		 	hadSelected = true;
		 }		
		chargeItemSelector= chargeItemSelector + "<option value='" + acctChargeItem.getId() + "' " + selectString +" >" + acctChargeItem.getItemName() + "|" + acctChargeItem.getItemNameTw() + "</option>";
	}	
}
chargeItemSelector= chargeItemSelector + "</SELECT>" ;
if(!hadSelected){
	appendStr = "<font color='red'>此收費項目已被刪除，請刪除</font>";
	chargeItemSelector = "";
}
%>			
			<TR>
			     <TD class="editTableLabelCellBKColor"  >
                      <input type="checkbox" name="delIds" value="<%=acctCustomerContractId %>">
                 </TD>
                 <TD class="editTableLabelCellBKColor"  ><%=chargeItemSelector %> <a href="#" onclick="toEditLocation('acctChargeItem', 'chargeItemIds<%=loop %>', 'id')">連結</a><%=appendStr %>
                 <input type="hidden" name="acctCustomerContractIds" value="<%=acctCustomerContractId %>">
                 </TD>
				 <TD class="editTableContentCellBKColor" ><INPUT id="unitPrices" size="10"  name="unitPrices" value="<%=unitPrice %>" <%=disabledVIEW %> class="required"></TD>			 
                 <TD class="editTableContentCellBKColor"  ><INPUT id="reportTitles" size="20"  name="reportTitles" value="<%=reportTitle%>" <%=disabledVIEW %> ></TD>				
			</TR>
<%} %> 


<%} %>


<!-- 新增項目 20 項 -->	
<% 
	Double unitPrice =0D;
	String reportTitle="";
	Integer orderby=0;
	Integer acctCustomerContractId = 0;		
for (int loop=0; loop<20; loop++) { 

%>			
<% 
    int initSize = 0;
    if(acctCustomerContractList != null)
    	initSize = acctCustomerContractList.size();
	String chargeItemSelector="<SELECT  id=\"chargeItemIds" + (initSize + loop) + "\" name=\"chargeItemIds\" size=\"1\"> ";
	chargeItemSelector = chargeItemSelector + "<option value=''>請選擇</option>";

		
for(int i=0; i<chargeItemList.size(); i++)  { 
	AcctChargeItem acctChargeItem = (AcctChargeItem)chargeItemList.get(i);
	chargeItemSelector= chargeItemSelector + "<option value=" + acctChargeItem.getId() + ">" + acctChargeItem.getItemName() + "|" + acctChargeItem.getItemNameTw() + "</option>";
}

chargeItemSelector= chargeItemSelector + "</SELECT>" ;
%>			
			<TR>
			     <TD class="editTableLabelCellBKColor"  ></TD>
                 <TD class="editTableLabelCellBKColor"  ><%=chargeItemSelector %> <a href="#" onclick="toEditLocation('acctChargeItem', 'chargeItemIds<%=(initSize + loop) %>', 'id')">連結</a>
                 <input type="hidden" name="acctCustomerContractIds" value="<%=acctCustomerContractId %>">
                 </TD>
				 <TD class="editTableContentCellBKColor" ><INPUT id="unitPrices" size="10"  name="unitPrices" value="<%=Util.getDecimalFormatToEmpty(unitPrice) %>" <%=disabledVIEW %> class="required"></TD>			 
                 <TD class="editTableContentCellBKColor"  ><INPUT id="reportTitles" size="20"  name="reportTitles" value="<%=reportTitle%>" <%=disabledVIEW %> ></TD>				
			</TR>
<%} %>



<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan="4" align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>

						
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='acctCustomerContract.do?fid=saveAsNewInit&custNo=<%=acctCustomerContract.getCustomer().getCustNo()%>&contactDateBegin_form=<%=Util.getDateFormat(acctCustomerContract.getContactDateBegin())%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<!--  input name="button6" type="button"  onclick="location='<%//=backToListURL%>'"  id="button6" value="回列表" / -->
						<input  type="button"  onclick="backToList()"   value="回列表" />




<%  } else { %>
						<input  type="button"  onclick="backToList()"   value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
