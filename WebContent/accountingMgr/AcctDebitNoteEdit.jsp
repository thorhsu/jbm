<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>

<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<script src="js/jbm.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqModal.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.dimensions.js" type="text/javascript"></script>
<head>

<%
	String message = (String )request.getAttribute("message");
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	AcctDno acctDno= (AcctDno)request.getAttribute("acctDno");
	String custNo = "";
	DebitNoteFullInfo debitNoteFullInfo= (DebitNoteFullInfo)request.getAttribute("debitNoteFullInfo");	
	
    int digitsLen = (acctDno.getDigitsLen() == null)? 0 : acctDno.getDigitsLen();
		
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
	

	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		acctDno.setDebitNo("");
	} else if ( null!= acctDno.getEp1Confirm() && acctDno.getEp1Confirm()){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 
function popupDebit8Windows(){
    var popStr = "acctDebitNote.do?fid=step9&debitNo=<%=acctDno.getDebitNo()%>";
    var digitsLen = $('#digitsLen').val();
    popStr = popStr + "&digitsLen=" + digitsLen;
    pop_upWindow( popStr );

}



$().ready(function() {
	//設定Report I設定的初始值
	$("#pageBreak").val("<c:out value='${acctDno.pageBreak}' default='0'/>");
	$("#acctsShow").val(["${(acctDno.acctsShow == true)? '1' : ''}"]);
	$("#pagesShow").val(["${(acctDno.pagesShow == true)? '1' : ''}"]);
	$("#sheetsShow").val(["${(acctDno.sheetsShow == true)? '1' : ''}"]);
	$("#displayOriAnymore").val(["${(acctDno.displayOriAnymore == true)? '1' : ''}"]);
	// validate my form when it is submitted
	$("#myform").validate({
	  rules: {
	    digitsLen: {
	      number: true,
	      minlength: 1,
	      maxlength: 1,
	      min: 0,
	      max: 3
	    }	    	    
	  }
	});
	

	
	//檢查 發票格式錯誤
	$("#myform").submit(function(){
		var inNo = $('#inNo').val() ;
		
		if (inNo.length == 10) {
			re = /^[A-Z][A-Z]\d{8}$/;
			if(! re.test(inNo)) { 
				submitFinished();
				  alert("發票格式錯誤!");
			  	  return false;
			}
		}
        return true;     
	});
	
	
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('acctDebitNote.do?fid=checkPKExist', 
	        {debitNo: $('#debitNo').val()},
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
  	$('#debitNo').blur(checkPKExist);	
	
	
	//計算Due Date
	calcDueDate = function (){
		var day=24*60*60*1000; 

		var inDtString = $('#inDt').val();
		inDtString = inDtString.replace("-","/");
		var myDate = new Date(inDtString);
		var dVal=myDate.valueOf();
		if(!isNaN($('#paymentTerm').val())){ 
		   var paymentTerm = parseInt($('#paymentTerm').val());
		   newDate=new Date(dVal+day*paymentTerm);
		   var month = newDate.getMonth() + 1;
		   var day = newDate.getDate();
		   var year = newDate.getFullYear();
		
	       $('#inDuedt').val(year +"-" + month + "-" + day);
	    }
	    //$('#inDuedt').val(_year );
		//alert("paymentTerm");
	    
	    //$('#inDuedt').val($('#inDt').val());

  	};
  	$('#inDt').blur(calcDueDate);  	
  	
  		
	//重新計算 未稅金額/含稅金額
	reCaleAmount = function (){
	    var tax =  parseInt($('#tax').val());
	    var amountExcludeTax =  parseInt($('#amountExcludeTax').val());
	    var amountIncludeTax =  parseInt($('#amountIncludeTax').val());
	    var vat =  false ; //預設報價含稅
	    
	    if ($('#vat').get(0).checked == true) vat = true; //報價不含稅
	    
	    if (vat) { //報價不含稅, 可調整總含稅金額
	    	amountIncludeTax = amountExcludeTax + tax;
	    	$('#amountIncludeTax').val(amountIncludeTax);
	    } else {
	    	//報價不含稅, 只能調整未稅金額
	    	amountExcludeTax = amountIncludeTax - tax;
	    	$('#amountExcludeTax').val(amountExcludeTax);
	    }
	    
  	};
  	$('#tax').blur(reCaleAmount);	
  		
});

$().ready(function() {
     $('#dialog').jqm().jqDrag();
     $('#reportIView').jqm();
});

function reportIViewShow(){
	   $('#reportIView').jqmShow();
}
function dialogShow(){
	   $('#dialog').jqmShow();
}

function changeDbNoteVal(amountByIntegerToSum, amountBySumToInteger, tax, amountExcludeTax, amountIncludeTax, desSdt, desEdt, cycleSdt, cycleEdt){

     if(amountByIntegerToSum != null && amountByIntegerToSum != "")
        $('#amountByIntegerToSum').val(amountByIntegerToSum);
     if(amountBySumToInteger != null && amountBySumToInteger != "")        
        $('#amountBySumToInteger').val(amountBySumToInteger);
     if(tax != null && tax != "")   
        $('#tax').val(tax);
     if(amountExcludeTax != null && amountExcludeTax != "")   
        $('#amountExcludeTax').val(amountExcludeTax);
     if(amountIncludeTax != null && amountIncludeTax != "")
        $('#amountIncludeTax').val(amountIncludeTax);
     if(desSdt != null && desSdt != "")
        $('#desSdt').val(desSdt);
     if(desEdt != null && desEdt != "")
        $('#desEdt').val(desEdt);
     if(cycleSdt != null && cycleSdt != "")
        $('#cycleSdt').val(cycleSdt);
     if(cycleEdt != null && cycleEdt != "")
        $('#cycleEdt').val(cycleEdt);              
}

</script>

	<form name="myform" id="myform" action="acctDebitNote.do" method="post">
	  <input type="hidden" name="backToListURL" value="${backToListURL}" />
	
	<div class="jqmWindow" id="dialog">

         <a href="#" class="jqmClose">Close</a>
            <hr>
           Debit Note所需小數位數(最多三位)：<input type="text" name="digitsLen" id="digitsLen" value="<%= digitsLen %>" maxlength="1" size="1"/>
     </div>
     
     <div class="jqmWindow2" id="reportIView">
           <table width="100%" border="0" cellSpacing="1" cellPadding="6">
              <tr class="editTableBKColor">
                 <td colspan="2" >  
                   <b>Report I 輸出設定</b>
                 </td>                 
              </tr>
              <tr>
                 <td class="editTableLabelCellBKColor">
                   Report I 額外顯示欄位：
                 </td>
                 <td class="editTableContentCellBKColor">
                   <input type="checkbox" id="acctsShow" name="acctsShow" value="1"/> 顯示Accounts
                    &nbsp;&nbsp;
                    <input type="checkbox" id="pagesShow" name="pagesShow" value="1"/> 顯示Pages
                    &nbsp;&nbsp;
                    <input type="checkbox" id="sheetsShow" name="sheetsShow" value="1"/> 顯示Sheets
                 </td>
              </tr>
              <tr>
                 <td class="editTableLabelCellBKColor" >
                                                      分頁小計設定：
                 </td>
                 <td class="editTableContentCellBKColor">                                                     
                    <select id="pageBreak"  name="pageBreak" id="selectTest"> 
							<option value="0">不統計每頁小計</option>
							<option value="16">每16列分頁</option>
							<option value="17">每17列分頁</option>
							<option value="18">每18列分頁</option>
							<option value="19">每19列分頁</option>
							<option value="20">每20列分頁</option>
							<option value="21">每21列分頁</option>
							<option value="22">每22列分頁</option>
							<option value="23">每23列分頁</option>
							<option value="24">每24列分頁</option>
							<option value="25">每25列分頁</option>
							<option value="26">每26列分頁</option>
							<option value="26">每26列分頁</option>
							<option value="27">每27列分頁</option>
							<option value="28">每28列分頁</option>
							<option value="29">每29列分頁</option>
							<option value="30">每30列分頁</option>
							<option value="31">每31列分頁</option>
							<option value="32">每32列分頁</option>
							<option value="33">每33列分頁</option>
							<option value="34">每34列分頁</option>
							<option value="35">每35列分頁</option>
					</select>					
                 </td>
              </tr>
              <tr>
                 <td class="editTableLabelCellBKColor">
                     Ori與係數調整數值相同仍然顯示
                 </td>
                 <td class="editTableContentCellBKColor">
                   <input type="checkbox" id="displayOriAnymore" name="displayOriAnymore" value="1"/> 不論相同與否均顯示
                 </td>
              </tr>
              <tr>
                 <td colspan="2"> 
                    <input  type="submit"   value="確認設定" /> <input  type="button"   value="關閉對話窗" class="jqmClose"/>
                 </td>
              </tr>
           </table>
     </div>
	
	
	
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">Maintain Debit Note</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >
                 <font color="#FF0000" size="2">*</font>客戶代號</TD>
				 <TD class="editTableContentCellBKColor"  align="left">
					 <SELECT name="custNo" size="1" <%=disabledVIEW %> class="required" <%=disabledPK %>>
					 <option value="" >請選擇</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";

	if (  null!= acctDno.getCustomer() && null!= acctDno.getCustomer().getCustNo() && _customer.getCustNo().equalsIgnoreCase( acctDno.getCustomer().getCustNo()) ) 
		selectString="selected";
		if(acctDno.getCustomer() != null)
		   custNo = acctDno.getCustomer().getCustNo();
		


%>					 
							<option value="<%=_customer.getCustNo() %>" <%=selectString %> >
								<%=_customer.getCustNo() + "-" + _customer.getEasyName() %>
							</option>

<% }%>	
					</SELECT>
				</TD>
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Section</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="section" size="10"  name="section" value="<%=Util.getStringFormat(acctDno.getSection() ) %>" class="required" <%=disabledVIEW %>>
                 	</TD>
			</TR> 
						
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Debit Note No</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="debitNo" size="10"  name="debitNo" value="<%=Util.getStringFormat(acctDno.getDebitNo() ) %>" class="required" <%=disabledPK %>  <%=disabledVIEW %>><span id="msg_pk_exist"></span>
                 	</TD>
			</TR>                 	


			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >交寄起訖日</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="desSdt" size="10"  name="desSdt" readonly value="<%=Util.getStringFormat(acctDno.getDesSdt() ) %>" <%=disabledVIEW %>>
<img border="0" alt="日期選擇輔助工具" id="desSdt_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "desSdt",						
		    button : "desSdt_datepicker"
		});
</script>                 	
                   ~<INPUT type="text" id="desEdt" size="10"  name="desEdt" readonly value="<%=Util.getStringFormat(acctDno.getDesEdt() ) %>" <%=disabledVIEW %>>
<img border="0" alt="日期選擇輔助工具" id="desEdt_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "desEdt",						
		    button : "desEdt_datepicker"
		});
</script>                   
                 </TD>
			</TR> 
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Cycle Date起訖日</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="cycleSdt" size="10"  name="cycleSdt" readonly value="<%=Util.getStringFormat(acctDno.getCycleSdt() ) %>" <%=disabledVIEW %>>
<img border="0" alt="日期選擇輔助工具" id="cycleSdt_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "cycleSdt",						
		    button : "cycleSdt_datepicker"
		});
</script>                 	
                   ~<INPUT type="text" id="cycleEdt" size="10"  name="cycleEdt" readonly value="<%=Util.getStringFormat(acctDno.getCycleEdt() ) %>" <%=disabledVIEW %>>
<img border="0" alt="日期選擇輔助工具" id="cycleEdt_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "cycleEdt",						
		    button : "cycleEdt_datepicker"
		});
</script>                   
                 	</TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Invoice No</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="inNo" size="20"  name="inNo"  value="<%=Util.getStringFormat(acctDno.getInNo() ) %>" maxlength="40" minlength="10" <%=disabledVIEW %>>
                 	</TD>
			</TR>
			
		
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Invoice Date</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="inDt" size="10"  name="inDt"  value="<%=Util.getStringFormat(acctDno.getInDt() ) %>" <%=disabledVIEW %>>
					(點選輸入框, 挑選日期)    
<script language="javascript">
		Calendar.setup({
			inputField : "inDt",						
		    button : "inDt"
		});
</script> 					               
                 	</TD>
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Due Date</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="inDuedt" size="10"  name="inDuedt" readonly value="<%=Util.getStringFormat(acctDno.getInDuedt() ) %>" <%=disabledVIEW %>>
<img border="0" alt="日期選擇輔助工具" id="inDuedt_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "inDuedt",						
		    button : "inDuedt_datepicker"
		});
</script>                 	
                 	</TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" colspan="2"><hr></TD>
			</TR>			
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >VAT</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                    <INPUT type="radio" id="vat" name="vat" disabled value="1" <% if(null!= debitNoteFullInfo && null!=debitNoteFullInfo.getVat() && debitNoteFullInfo.getVat() ) out.println("checked");%> <%=disabledVIEW %> />報價不含稅
                    <INPUT type="radio" id="vat" name="vat" disabled value="0" <% if(null!= debitNoteFullInfo && null!=debitNoteFullInfo.getVat() && !debitNoteFullInfo.getVat() ) out.println("checked");%> <%=disabledVIEW %> />報價含稅                       
                 </TD>
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >paymentTerm</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left><INPUT type="text" name="paymentTerm" id="paymentTerm" readonly disabled value="<%=debitNoteFullInfo.getPaymentTerm()%>" />
                 </TD>
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >合計(取整數後合計)</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT type="text" id="amountByIntegerToSum" size="10" disabled name="amountByIntegerToSum" value="<%=debitNoteFullInfo.getAmountByIntegerToSum() %>" style="text-align:right;" <%=disabledPK %>>                 
                 </TD>
			</TR>			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >合計(合計後取整數)</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT type="text" id="amountBySumToInteger" size="10" disabled name="amountBySumToInteger" value="<%=debitNoteFullInfo.getAmountBySumToInteger() %>" style="text-align:right;" <%=disabledPK %>>                 
                 </TD>
			</TR>		

			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >稅額</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="tax" size="10"  name="tax" value="<%=debitNoteFullInfo.getAmountTax() %>" <%=disabledVIEW %> style="text-align:right;">
                 <input name="button6" type="submit"  id="button6" <%=disabledVIEW %> value="確認 [稅額] 資訊" />
                 </TD>
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >未稅金額</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="amountExcludeTax" size="10" readonly name="amountExcludeTax" value="<%=debitNoteFullInfo.getAmountExcludeTax() %>" style="text-align:right;" <%=disabledPK %>>
                 </TD>
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >含稅金額</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="amountIncludeTax" size="10" readonly name="amountIncludeTax" value="<%=debitNoteFullInfo.getAmountIncludeTax() %>" style="text-align:right;" <%=disabledPK %>>
                 </TD>
			</TR>
						
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" colspan="2"><hr></TD>
			</TR>						
						

			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Refund</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="refund" size="10"  name="refund" value="<%=Util.getStringFormat(acctDno.getRefund() ) %>" <%=disabledVIEW %>>
                 	</TD>
			</TR>
			<TR>
                 <TD class="editTableLabelCellBKColor" width="15%" >Refund Amount</TD>
                 <TD class="editTableContentCellBKColor" width="15%"align=left>
                 	<INPUT type="text" id="refundamt" size="10"  name="refundamt" value="<%=Util.getDecimalFractionFormat(acctDno.getRefundamt() ) %>" style="text-align:right;" <%=disabledVIEW %>>
                 	(以負值表示 )</TD>
			</TR>						
															
			
<!-- 下方功能列 -->
			<TR>
				<TD height=10 colSpan="2" >
                              <table border="0" width="100%" id="table5" align="left">
								<tr>
									<td align="left">
									<input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step1&debitNo=<%=acctDno.getDebitNo()%>&extractMode=ALL&custNo=<%=custNo%>&backToListURL=${backToListURL}')"  id="button" value="1.由JBM攫取資料(ALL)" /><BR>
									<input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step1&debitNo=<%=acctDno.getDebitNo()%>&extractMode=NON_LOCKED&custNo=<%=custNo%>&backToListURL=${backToListURL}')"  id="button" value="1.由JBM攫取資料(UNLOCK)" />
									</td>
									<td align="left"><input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step2_list&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="2.編輯所攫取資料" /></td>
									<td align="left"><input name="button" type="button"  onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step3&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="3.列印Report I" />
									    <a href="#"  style="font-size:9px"  onclick="reportIViewShow()">ReportI Setting</a>
									</td>
									<td align="left"><input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step1_1_MergeDebitNote&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="1-1.Copy Debit Note" /></td>
								</tr>
								<tr>
									<td align="left"><input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step4&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="4.計算" /></td>
									<td align="left"><input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step5_list&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="5.編輯列舉收費相目" /></td>
									<td align="left"><input name="button" type="button"  onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step6&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="6.列印 Report II" /></td>
									<td align="left"><input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step6_1_list&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="6-1. 輸入發票號碼" /></td>
								</tr>
								<tr>
									<td align="left"><input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step7&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="7.產生Debit Note" /></td>
									<td align="left"><input name="button" type="button" <%=disabledVIEW %> onclick="javascript:waitToFinished();pop_upWindow('acctDebitNote.do?fid=step8_list&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')"  id="button" value="8.編輯Debit Note" /></td>
									<td align="left"><input name="button" type="button"  onclick="waitToFinished();popupDebit8Windows()"  id="button" value="9.列印 Report III" />
									   <a href="#"  onclick="dialogShow()" style="font-size:9px">設定小數位數</a>
									</td>
									<td align="left"><input name="button" type="button" <%=disabledVIEW %> onclick="javascript:if (confirm('確定Unlock?')) {waitToFinished();pop_upWindow('acctDebitNote.do?fid=unlock&custNo=<%=custNo%>&debitNo=<%=acctDno.getDebitNo()%>&backToListURL=${backToListURL}')}"  id="button" value="X.UNLOCK" /></td>
								</tr>
								</table>
                            </TD></TR>
																						

<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan=2 align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" <%=disabledVIEW %> value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" <%=disabledVIEW %> onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						
						<input name="button6" type="submit"  id="button6" <%=disabledVIEW %> value="更新" />
						<input name="button6" type="button" <%=disabledVIEW %> onclick="location='acctDebitNote.do?fid=saveAsNewInit&debitNo=<%=acctDno.getDebitNo().trim()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<!--  input name="button6" type="button"  onclick="location='<%//=backToListURL%>'"  id="button6" value="回列表" / -->
						<input  type="button"  onclick="backToList()"   value="回列表" />


<%  } else { %>
                        <input  type="button"  onclick="backToList()"  value="回列表" />						
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
