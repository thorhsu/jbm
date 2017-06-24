<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
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
<script type="text/javascript">
  $().ready(function() {
     window.opener.submitFinished();
  });
</script>

</head>

<%
	AcctDebitNoteService acctDebitNoteService = AcctDebitNoteService.getInstance();
	AcctSectionReceiverMappingService sectionReceiverMappingService = AcctSectionReceiverMappingService.getInstance(); 	
		
	List dataList= (List)request.getAttribute("dataList");
	AcctDno acctDno= (AcctDno)request.getAttribute("acctDno");
	AcctSectionReceiverMapping acctSectionReceiverMapping= sectionReceiverMappingService.findByDebitNo(acctDno);	
	
	String includeTax = "NO";
	if (null!= acctSectionReceiverMapping.getVat() && !acctSectionReceiverMapping.getVat())
		includeTax = "YES";
		 
	Integer debiteNoteTax =0;
	if (null!= acctDno.getTax())
		debiteNoteTax = acctDno.getTax().intValue();
%>


<script language="javascript">
//計算TAX
function calcTax(index) {
		var expenses=document.getElementById("expenses" + index);
		var isIncludeTax = "<%=includeTax%>";
		var tax=0;
		if (isIncludeTax =="YES")
			tax = expenses.value - (expenses.value / 1.05);
		else
			tax = expenses.value * 0.05;    	
		document.getElementById("taxs" + index).value = parseInt(Math.round(tax ) );
}

//計算TAX2
function calcTax2(index) {
	    var invoiceNo = $.trim(document.getElementById("invoicenos" + index).value);
	    if(invoiceNo != ""){
		   var expenses=document.getElementById("expenses" + index);
		   var isIncludeTax = "<%=includeTax%>";
		   var tax=0;
		   if (isIncludeTax =="YES")
			   tax = expenses.value - (expenses.value / 1.05);
		   else
			   tax = expenses.value * 0.05;
                	
		   document.getElementById("taxs" + index).value = parseInt(Math.round(tax ) );
	    }
}
</script>


<script type="text/javascript"> 
$().ready(function() {
     $('input[name=backToListURL]').val(decodeURIComponent("${backToListURL}"));
 });


$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate({
	  rules: {
	    "expenses": {
	      digits: true
	    },
		 "taxs": {
	      digits: true
	    },
		 "costCenters": {
	       maxlength: 8
	    }
	  }
	});
	
			

	
	//檢查 發票格式錯誤
	$("#myform").submit(function(){
		var checkFG = true;
	    $("input[name^=invoicenos]").each(function() {   
			var inNo =$(this).val() ; // 每一個invocie No
			if (inNo != "") { //只檢查有輸入的發票資料
				re = /^[A-Z][A-Z]\d{8}$/;
				if(! re.test(inNo)) { 
				  alert("發票格式錯誤!");
				  checkFG = false;
			  	  return false;
			  	}
		  	}
	    });

		if (! checkFG) 
			return false;


		// 檢查稅額是否相同

		
		var expenses=document.getElementsByName("expenses");	
		var taxs=document.getElementsByName("taxs");	
		var invoicetyps=document.getElementsByName("invoicetyps");		
		var costCenters=document.getElementsByName("costCenters");			
		var sum2TotlaTax=0;
		var type1TotlaTax=0;
		var sum2TotalAmt=0;
		var type1TotalAmt=0;
		var debiteNoteTax = <%=debiteNoteTax%>;

		for (var i=0;i < <%=dataList.size()%> + 15 ; i++ ) {
			if(taxs[i].value != "")
			   sum2TotlaTax += parseInt(taxs[i].value) ;
			if (invoicetyps[i].value!="1" ) {
				
				sum2TotalAmt += parseInt(expenses[i].value) ;  
			}else{
			   if (taxs[i].value!="")
					type1TotlaTax += parseInt(taxs[i].value) ;
				
			   if (expenses[i].value!="")
					type1TotalAmt += parseInt(expenses[i].value) ;  				
			}
			
		}
		//alert("type1TotalAmt:" + type1TotalAmt + "|| sum2TotalAmt:" + sum2TotalAmt + "||type1TotlaTax:" + type1TotlaTax + "||sum2TotlaTax:" + sum2TotlaTax + "||debiteNoteTax:" + debiteNoteTax);
		if (type1TotalAmt!=0 && sum2TotalAmt != type1TotalAmt) {
			alert("自訂發票總金額[" +type1TotalAmt +"] 跟 sum2 總金額[" + sum2TotalAmt + "]不合");
		}
				
		if (type1TotlaTax!=0 && type1TotlaTax != sum2TotlaTax) {
			alert("自訂發票稅額 [" + type1TotlaTax + "] 跟  sum2 稅額 [" + sum2TotlaTax + "]不同");
		}
							
		if (debiteNoteTax != sum2TotlaTax) {
			alert("sum2發票稅額 [" + sum2TotlaTax + "] 跟 Debite Note 稅額 [" + debiteNoteTax + "]不同");
		}else{
			
		}

		return true;    
	});
	
});
</script>


<body>
<form name="myform" id="myform" action="acctDebitNote.do" method="post">
<input type="hidden" name="backToListURL" value="${backToListURL}" />
<input type="hidden" name="fid" value="step6_1_editSubmit" />
<input type="hidden" name="debitNo" value="<%=acctDno.getDebitNo() %>" />
<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">Step 6.1: 發票維護</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>


			<tr>
				<td>
				<table border=0 cellspacing=1 width="100%" class="resultTableBKColor"align=left id="table76" class="resultTableHeaderBKColor">
				<tr >
				    <td class="resultTableHeaderBKColor" width="5%">刪除</td>
		            <td class="resultTableHeaderBKColor" width="25%" height="30">Cost Center</td>
		            <td class="resultTableHeaderBKColor" width="20%">Expense</td>
		            <td class="resultTableHeaderBKColor" width="20%">Invoice No</td>
		            <td class="resultTableHeaderBKColor" width="20%">TAX</td>
		            <td class="resultTableHeaderBKColor" width="10%">TYPE</td>   
	              </tr>


<%

int notType1Counter = 0;
int counter = 0;

for (int i=0; i<dataList.size(); i++) {
	AcctInvoice acctInvoice = (AcctInvoice)dataList.get(i);
	if(!"1".equals(acctInvoice.getInvoicetyp()))
		notType1Counter ++;
}

Integer rowCounter=-1;
int notType1SumTax = 0;
for (int i=0; i<dataList.size(); i++) {
	int tax=0;
	String invoiceType="";
	String invoiceNo ="";
	int expense =0;
	rowCounter = rowCounter + 1;
	AcctInvoice acctInvoice = (AcctInvoice)dataList.get(i);

	if (null != acctInvoice.getInvoiceno())
		invoiceNo = acctInvoice.getInvoiceno();
		
	if (null != acctInvoice.getInvoicetyp())
		invoiceType = acctInvoice.getInvoicetyp();		
			
	if (null != acctInvoice.getExpense())
		expense = acctInvoice.getExpense().intValue();
		
	if (null != acctInvoice.getTax())
		tax = acctInvoice.getTax().intValue();		
	
%>
	          <tr  >
				
				<td>
				   <%if(acctInvoice.getId() != null && !acctInvoice.getId().equals(new Integer(0))){%>
				      <input type="checkbox" name="delInvoiceIds" id="delInvoiceIds" value="<%=acctInvoice.getId() %>"/>
				   <% } %>
				   <input type="hidden" name="invoiceIds" id="invoiceIdAs2" value="<%=acctInvoice.getId() == null ? "" : acctInvoice.getId()%>" />
				</td>
	            <td >
	            <% 
	               String  expensesName = "readonly='readonly' name='expenses'";
	               String  invoiceNosName = "name='invoicenos'";
	               String  taxsName = " name='taxs'";
	               String  invoicetypsName = "readonly='readonly' name='invoicetyps'";
	               String costCentersName = "readonly='readonly' name='costCenters'";
	               if("1".equals(invoiceType)){
	                   invoicetypsName = "name='invoicetyps'";
	                   taxsName += "name='taxs'";
	                   invoiceNosName = "name='invoicenos'";
	                   expensesName = "name='expenses'";
	                   costCentersName = "name='costCenters'";
	               }else{
	            	   counter++;
	            	   if (includeTax =="YES" && tax == 0)
	 	       			  tax = (int)(Math.round( expense - (expense / 1.05)));
	 	       		   else  if( tax == 0)
	 	       			  tax = (int)Math.round( expense * 0.05);	            	   	            	   
	            	   if(counter == notType1Counter && tax == 0 )
	            		   tax = debiteNoteTax - notType1SumTax;
	            	   	            	   
	            	   notType1SumTax += tax;
	               }
	               String costCenter = "";
	               if(acctInvoice.getCostCenter() == null || acctInvoice.getCostCenter().equals("defaul_C")){
	            	   
	               } else {
	            	   costCenter = acctInvoice.getCostCenter();
	               }
	               
	               
	               
	               	   
	            %>
	            
	            <input type="text" <%= costCentersName %> id="costCenters<%=rowCounter%>" size="20" value="<%=costCenter %>" maxlength="8"/></td>
	            <td ><input type="text" <%= expensesName %> id="expenses<%=rowCounter%>" size="10"  style="text-align:right;" value="<%=expense %>" onchange="calcTax(<%=rowCounter%>)"/></td>
	            <td ><input type="text" <%= invoiceNosName %> id="invoicenos<%=rowCounter%>" size="10" maxlength="10" minlength="10" value="<%=invoiceNo %>" onblur="calcTax2(<%=rowCounter%>)"/></td>
	            <td ><input type="text" <%= taxsName %> id="taxs<%=rowCounter%>" size="9" value="<%=tax %>"  style="text-align:right;"/> </td>
	            <td ><input type="text" <%= invoicetypsName %> id="invoicetyps<%=rowCounter%>" readonly size="1"  value="<%=invoiceType %>" />
	            </td>        
              </tr>  
<% }%>
<!-- 非合併報表 合計 -->

	           
<%for (int i=0; i<15; i++)  { 
	rowCounter = 	rowCounter + 1;
%>	           
	          <tr  >
	            <td></td>
	            <td  >
	            <input type="hidden" name="invoiceIds" id="invoiceIds<%=rowCounter%>" value=""/>
	            <input type="text" name="costCenters" id="costCenters<%=rowCounter%>" size="20" value="" maxlength="8"/></td>
	            <td ><input type="text" name="expenses" id="expenses<%=rowCounter%>" size="10"  style="text-align:right;" value="" onblur="calcTax(<%=rowCounter%>)"/></td>
	            <td ><input type="text" name="invoicenos" id="invoicenos<%=rowCounter%>" size="10"  value="" maxlength="10" minlength="10"/></td>
	            <td ><input type="text" name="taxs" id="taxs<%=rowCounter%>" size="9" style="text-align:right;" value=""/></td>
	            <td ><input type="text" name="invoicetyps" id="invoicetyps<%=rowCounter%>" readonly size="1" value="1" /></td> 
              </tr>
<%} %>              	          
	          </table>
	           
	          </td>
          </tr>
	      <tr>
	        <td>
	           Type 1: 為EP1 開立多張發票使用,如單價為未稅值, 則輸入未稅值, 如單價為含稅值, 則輸入含稅值。<br/>
	           Tax與Expense均只能輸入正整數。Cost Center最長八位數
	        </td>
          </tr>
	      <tr>
	        <td>
			<HR/>
	        </td>
          </tr>
          
			<tr>
				<td align="center">
				<input name="button" type="submit" id="button" value="儲存" />
				</td>
			</tr>          
        </table>
</div>
</form>
</body>
</html>
<%
HibernateSessionFactory.closeSession();
%>
