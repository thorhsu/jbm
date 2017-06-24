<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JBM v2.0</title>
<link rel="stylesheet" type="text/css" href="css/backend.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/screen.css" /> 
<link rel="stylesheet" href="js/aqua/theme.css" type="text/css" /> 
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
   $().ready(function() {
      $('input[name=backToListURL]').val(decodeURIComponent("${backToListURL}"));
      window.opener.submitFinished();
   });
   
</script>

<%
    java.text.NumberFormat nf = new java.text.DecimalFormat("#0.000");
    
	AcctDebitNoteService acctDebitNoteService = AcctDebitNoteService.getInstance();
	
	List dataList= (List)request.getAttribute("dataList");
	AcctDno acctDno= (AcctDno)request.getAttribute("acctDno"); 
	String purpose="'','DISPLAY_ONLY'";
	DebitNoteFullInfo debitNoteFullInfo = acctDebitNoteService.getDebitNoteFullInfo(acctDno, purpose);		
	AcctSectionReceiverMapping acctSectionReceiverMapping = (AcctSectionReceiverMapping)request.getAttribute("acctSectionReceiverMapping"); 

%>


<body >
<form name="myform" id="myform" action="acctDebitNote.do" method="post">
<input type="hidden" name="backToListURL" value="${backToListURL}" />
<input type="hidden" name="fid" value="step8_editSubmit" />
<input type="hidden" name="debitNo" value="<%=acctDno.getDebitNo() %>" />

<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">編輯Debit Note</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>


			<tr>
				<td>
				<table border=0 cellspacing=1 cellpadding=6 width="100%" bgcolor=#4aae66 align=left id="table76">
					<tr>
							<td bgcolor=#dff4dd>Cust No: </td>
						<td bgcolor=#f4faf3><%=acctDno.getCustomer().getCustNo() %></td>						
						<td bgcolor=#dff4dd>Debit Note No:</td>
						<td bgcolor=#f4faf3><%=acctDno.getDebitNo() %></td>						
						<td bgcolor=#dff4dd>Section:</td>
						<td bgcolor=#f4faf3><%=acctDno.getSection() %></td>						
						<td bgcolor=#dff4dd>During:</td>
						<td bgcolor=#f4faf3>
						   <table>
						      <tr>
						         <td align="right">
						                               交寄日：
						         </td>
						         <td>
						            <%= (acctDno.getDesSdt() == null)? "" : acctDno.getDesSdt() %>~<%= (acctDno.getDesEdt() == null)? "" : acctDno.getDesEdt()%>
						         </td>
						      </tr>
						      <tr> 
						         <td align="right">
						             Cycle Date：
						         </td>
						         <td>
						              <%=acctDno.getCycleSdt() == null? "" : acctDno.getCycleSdt() %>~<%=acctDno.getCycleEdt() == null? "" : acctDno.getCycleEdt() %>
						         </td>
						      </tr> 
						   </table>
						</td>						
					</tr>
				</table>
				</td>
			</tr>


	      <tr>
	        <td>
	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
			<tr >
	            <td class="resultTableHeaderBKColor" height="30">編號</td>
	            <td class="resultTableHeaderBKColor">Title</td>
	            <td class="resultTableHeaderBKColor">Sub Title</td>
	            <td class="resultTableHeaderBKColor">print code</td>
	            <td class="resultTableHeaderBKColor">Qty</td>
	            <td class="resultTableHeaderBKColor">Price</td>
	            <td class="resultTableHeaderBKColor">Total</td>
	            <td class="resultTableHeaderBKColor">Total(INT)</td>	            
              </tr>         
<%
Integer rowCounter=-1;
Integer nonMergeReportCounter=0;
Double amountBySumToInteger=0.0;
Integer amountByIntegerToSum=0;

for (int i=0; i<dataList.size(); i++) { 
	AcctSum3 acctSum3 = (AcctSum3)dataList.get(i);
	if ( (null == acctSum3.getPurpose() || ( null!=acctSum3.getPurpose() &&  !acctSum3.getPurpose().equalsIgnoreCase("MERGE_REPORT") ) ) && acctSum3.getTotal() != null) {
		rowCounter = rowCounter + 1; 
		nonMergeReportCounter = nonMergeReportCounter + 1;
		amountBySumToInteger += acctSum3.getTotal();
		amountByIntegerToSum += (int) Math.round(acctSum3.getTotal());
%>
	          <tr  >
	            <td  ><input type="hidden" name="sum3Ids" id="sum3Ids" value="<%=acctSum3.getId() %>"/><%=acctSum3.getId() %></td>
	            <td ><input type="text" name="titles" id="titles" size="30" value="<%=acctSum3.getTitle() %>"/></td>
	            <td ><input type="text" name="subtitles" id="subtitles" size="22" value="<%=acctSum3.getSubtitle() %>"/></td>
	            <td ><input type="text" name="printCodes" id="printCodes" size="8" value="<%=(acctSum3.getPrintCode() == null)? "" : acctSum3.getPrintCode()  %>"/></td>
	            <td align="right"><input type="hidden" name="sumQties" id="sumQties" value="<%=acctSum3.getSumQty().intValue() %>"/><%=acctSum3.getSumQty().intValue() %></td>
	            <td align="right"><input type="hidden" name="unitPrices" id="unitPrices" value="<%=acctSum3.getUnitPrice() %>"/> <%=acctSum3.getUnitPrice() %></td>
	            <td align="right"><input type="text" name="totals" id="totals" size="10" style="text-align:right;" value="<%= nf.format(acctSum3.getTotal()) %>" onblur="calcIntTotal(<%=rowCounter%>)"/>
	            <input type="hidden" name="purposes" id="purposes"  value="<%=acctSum3.getPurpose() %>"/>
	            </td>
				<td >
				<input type="text" name="totalsINT" id="totalsINT" size="5" readonly size="10" style="text-align:right;"  value="<%=(int) Math.round(acctSum3.getTotal())  %>"/>
				</td>	            
              </tr>
<%} //end if  

}%>
<!-- 非合併報表 合計 -->
	          <tr>
	            <td height="30" ></td>
	            <td ></td>
	            <td ></td>
	            <td align="right"></td>
	            <td align="right"></td>
	            <td align="right"></td>
	            <td align="right">
	            <input type="text" name="amountBySumToInteger" id="amountBySumToInteger" readonly size="10" style="text-align:right;" value="<%=Math.round(amountBySumToInteger)%>"/>
	            </td>
				<td >
				<input type="text" name="amountByIntegerToSum" id="amountByIntegerToSum" size="5" readonly size="10" style="text-align:right;"  value="<%=amountByIntegerToSum%>"/>
				</td>	            
              </tr>
	          </table>
	           
	          </td>
          </tr>
	      <tr>
	        <td> <hr/> </td>
          </tr>   
          
<%



 %>                 
	      <tr>
	        <td>合併報表設定</td>
          </tr>
	      <tr>
	        <td>
	          <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
			<tr >
	            <td class="resultTableHeaderBKColor" height="30">挑選</td>
	            <td class="resultTableHeaderBKColor">Title</td>
	            <td class="resultTableHeaderBKColor">Sub Title</td>
	            <td class="resultTableHeaderBKColor">Qty</td>
	            <td class="resultTableHeaderBKColor">Price</td>
	            <td class="resultTableHeaderBKColor">Total</td>
	            <td class="resultTableHeaderBKColor">Total(INT)</td>	            
              </tr>
<%
Integer mergeReportCounter=0;
Double mergeAmountBySumToInteger=0.0;
Integer mergeAamountByIntegerToSum=0;
for (int i=0; i<dataList.size(); i++) { 
	AcctSum3 acctSum3 = (AcctSum3)dataList.get(i);
	if (null!=acctSum3.getPurpose() && acctSum3.getPurpose().equalsIgnoreCase("MERGE_REPORT")) {
		mergeReportCounter = mergeReportCounter + 1;
		rowCounter = rowCounter + 1;
		mergeAmountBySumToInteger += acctSum3.getTotal();
		mergeAamountByIntegerToSum += (int) Math.round(acctSum3.getTotal());		
%>              	          
				<tr>
					<td width="5%" ><input type="hidden" name="sum3Ids" id="sum3Ids" value="<%=acctSum3.getId() %>"/><%=acctSum3.getId() %>
					</td>	
					<td width="30%" >
					<input type="text" name="titles" id="titles" size="30" value="<%=acctSum3.getTitle() %>"/>
					</td>
					<td width="30%" >
					<input type="text" name="subtitles" id="subtitles" size="30" value="<%=acctSum3.getSubtitle() %>"/>
					</td>					
					<td width="10%" >
					<input type="text" name="sumQties" id="sumQties" size="5" style="text-align:right;"  value="<%=acctSum3.getSumQty().intValue()%>" onblur="calcTotal(<%=rowCounter%>)"/>
					</td>
					<td width="10%" >
					<input type="text" name="unitPrices" id="unitPrices" size="5" size="5" style="text-align:right;"  value="<%=acctSum3.getUnitPrice()%>" onblur="calcTotal(<%=rowCounter%>)"/>
					</td>
					<td width="25%" >
					<input type="text" name="totals" id="totals" size="5" size="10" style="text-align:right;"  value="<%=acctSum3.getTotal() %>" onblur="calcIntTotal(<%=rowCounter%>)"/>
					<input type="hidden" name="purposes" id="purposes"  value="MERGE_REPORT"/>
					</td>
					<td >
					<input type="text" name="totalsINT" id="totalsINT" size="5" readonly size="10" style="text-align:right;"  value="<%=(int) Math.round(acctSum3.getTotal())  %>"/>
					</td>																	
				</tr>
<%} //end if  

}%>

<%
String title="";
String subTitle="";
for (int i=mergeReportCounter; i < 6 ; i++) { 
	rowCounter = rowCounter + 1;
	if (null!= acctSectionReceiverMapping.getMergeReportIiiTitle1() && i==0) {
		title = acctSectionReceiverMapping.getMergeReportIiiTitle1();
		subTitle = acctSectionReceiverMapping.getMergeReportIiiSubtitle1();
	}
	if (null!= acctSectionReceiverMapping.getMergeReportIiiTitle2() && i==1) {
		title = acctSectionReceiverMapping.getMergeReportIiiTitle2();
		subTitle = acctSectionReceiverMapping.getMergeReportIiiSubtitle2();
	}
	if (null!= acctSectionReceiverMapping.getMergeReportIiiTitle3() && i==2) {
		title = acctSectionReceiverMapping.getMergeReportIiiTitle3();
		subTitle = acctSectionReceiverMapping.getMergeReportIiiSubtitle3();
	}		
%>              	          
				<tr>
					<td width="5%" >
					<input type="hidden" name="sum3Ids" id="sum3Ids" value="0"/>				
					</td>	
					<td width="30%" >
					<input name="titles" id="titles" size="30" value="<%=title %>"/>
					</td>
					<td width="30%" >
					<input name="subtitles" id="subtitles" size="30" value="<%=subTitle %>"/>
					</td>					
					<td width="10%" >
					<input type="text" name="sumQties" id="sumQties" size="5" style="text-align:right;"  value="" onblur="calcTotal(<%=rowCounter %>)"/>
					</td>
					<td width="10%" >
					<input type="text" name="unitPrices" id="unitPrices" size="5" size="5" style="text-align:right;"  value="" onblur="calcTotal(<%=rowCounter %>)"/>
					</td>
					<td >
					<input type="text" name="totals" id="totals" size="5" size="10" style="text-align:right;"  value="" onblur="calcIntTotal(<%=rowCounter %>)"/>
					<input type="hidden" name="purposes" id="purposes"  value="MERGE_REPORT"/>
					</td>
					<td >
					<input type="text" name="totalsINT" id="totalsINT" size="5" readonly size="10" style="text-align:right;"  value=""/>
					</td>															
				</tr>
<%}%>
<!-- 合併報表 合計 -->
	          <tr>
	            <td height="30" ></td>
	            <td ></td>
	            <td ></td>
	            <td align="right"></td>
	            <td align="right"></td>
	            <td align="right">
	            <input type="text" name="mergeAmountBySumToInteger" id="mergeAmountBySumToInteger" size="5" style="text-align:right;" value="<%=(int) Math.round(mergeAmountBySumToInteger)%>" />
	            </td>
				<td >
				<input type="text" name="mergeAamountByIntegerToSum" id="mergeAamountByIntegerToSum" size="5" readonly size="10" style="text-align:right;"  value="<%=mergeAamountByIntegerToSum%>"/>
				</td>	            
              </tr>
	          </table>	        
	        
	        
	        </td>
          </tr>           
          	
          <tr><td align="center"><input name="selectAll" type="submit"  id="selectAll" value="確定" />
		  </td></tr>          	

        </table>
</div>
</form>


<script language="javascript">
<!--
//計算每一行的整數
function calcIntTotal(index) {
		var totals=document.getElementsByName("totals");
		var totalsINT=document.getElementsByName("totalsINT");
		document.getElementsByName("totalsINT")[index].value = parseInt(Math.round(totals[index].value) );
		reCalcAmount();
}

//計算Debit 總金額 
function reCalcAmount() {
		var totals=document.getElementsByName("totals");
		var totalsINT=document.getElementsByName("totalsINT");
		var nonMergeReportCounter =  <%=nonMergeReportCounter%>;
		var _amountBySumToInteger=0;
		var _amountByIntegerToSum=0;		
		for (var i=0;i < nonMergeReportCounter ; i++ ) {
			_amountBySumToInteger += parseFloat(totals[i].value);
			_amountByIntegerToSum += parseFloat(totalsINT[i].value);
		}
		document.getElementsByName("amountBySumToInteger")[0].value = parseInt(Math.round(_amountBySumToInteger) );
		document.getElementsByName("amountByIntegerToSum")[0].value = parseInt(_amountByIntegerToSum);
		return true;
}


//計算 小計合計 -- 合併報表 
function calcTotal(index) {
		var sumQties=document.getElementsByName("sumQties");
		var unitPrices=document.getElementsByName("unitPrices");
		var totals=document.getElementsByName("totals");
		if (sumQties[index].value.length>0 && unitPrices[index].value.length>0) {
			document.getElementsByName("totals")[index].value = parseFloat(sumQties[index].value ) * parseFloat(unitPrices[index].value );
			document.getElementsByName("totalsINT")[index].value = parseInt(Math.round(totals[index].value) );		
			reCalcAmountMerge();
		}
}

//計算Debit 總金額 -- -- 合併報表
function reCalcAmountMerge() {
		var totals=document.getElementsByName("totals");
		var totalsINT=document.getElementsByName("totalsINT");
		var nonMergeReportCounter =  <%=nonMergeReportCounter%>;
		var totalCounter =  <%=rowCounter%> + 1;		
		var _amountBySumToInteger=0;
		var _amountByIntegerToSum=0;		
		for (var i=nonMergeReportCounter;i < totalCounter ; i++ ) {
			if (totals[i].value.length>0 ) {
				_amountBySumToInteger += parseFloat(totals[i].value);
				_amountByIntegerToSum += parseFloat(totalsINT[i].value);
			}
		}
		document.getElementsByName("mergeAmountBySumToInteger")[0].value = parseInt(Math.round(_amountBySumToInteger) );
		document.getElementsByName("mergeAamountByIntegerToSum")[0].value = parseInt(_amountByIntegerToSum);		
		return true;
}
 
-->
</script>

<script type="text/javascript"> 


  	

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate({
	  rules: {
	    "sumQties": {
	      number: true
	    },
		 "unitPrices": {
	      number: true
	    },
		 "totals": {
	      number: true
	    }
	  }
	});
	
	//檢查合計是否相同 
	$("#myform").submit(function(){
		var amountBySumToInteger = $('#amountBySumToInteger').val() ;
		var amountByIntegerToSum = $('#amountByIntegerToSum').val() ;
		var mergeAmountBySumToInteger = $('#mergeAmountBySumToInteger').val() ;
		var mergeAamountByIntegerToSum = $('#mergeAamountByIntegerToSum').val() ;		
		var ret = true;
		if (amountBySumToInteger != amountByIntegerToSum) {
			if (confirm("合計(取整數後合計) <> 合計(合計後取整數) , 確定要存檔 ? ") )
				ret = true;
			else 
				ret = false;
		}

		if (mergeAmountBySumToInteger != mergeAamountByIntegerToSum) {
			if (confirm("[合併報表] 合計(取整數後合計) <> 合計(合計後取整數) , 確定要存檔 ? ") )
				ret = true;
			else 
				ret = false;
		}
		
		if (amountBySumToInteger != mergeAmountBySumToInteger && mergeAmountBySumToInteger!="0") {
			if (confirm("報表3合計值 跟  合併報表  合計值不合 , 確定要存檔 ? ") )
				ret = true;
			else 
				ret = false;
		}		
		
		return ret; 	
	});
  	
  	
});
</script>


</body>
</html>
<%
HibernateSessionFactory.closeSession();
%>
