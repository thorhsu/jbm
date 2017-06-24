<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@page import="com.salmat.jbm.hibernate.*"%>
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
function changeDbNoteVal(){
	if(window.opener != null)
       window.opener.submitFinished();
    var desSdt = "${debitNoteFullInfo.desSdt}";
	var desEdt = "${debitNoteFullInfo.desEdt}";
	var cycleSdt = "${debitNoteFullInfo.cycleSdt}";
	var cycleEdt = "${debitNoteFullInfo.cycleEdt}";
	
	 
    var amountByIntegerToSum = '<c:out value="${debitNoteFullInfo.amountByIntegerToSum}" default="" />';
    var amountBySumToInteger = "${debitNoteFullInfo.amountBySumToInteger}";
    var tax = "${debitNoteFullInfo.amountTax}";
    var amountExcludeTax = "${debitNoteFullInfo.amountExcludeTax}";
    var amountIncludeTax = "${debitNoteFullInfo.amountIncludeTax}";    
    var refund = "${acctDno.refund}";
    var refundamt = "${acctDno.refundamt}";
    
    if( (amountByIntegerToSum == "0" || amountByIntegerToSum == '') && (amountBySumToInteger == "0" || amountBySumToInteger == "") && tax == "" && amountExcludeTax == "0" && amountIncludeTax == "0" && desSdt == "" && desEdt == "" && cycleSdt == "" && cycleEdt == ""){
    }else{
    	if(window.opener != null)
           window.opener.changeDbNoteVal(amountByIntegerToSum, amountBySumToInteger, tax, amountExcludeTax, amountIncludeTax, desSdt, desEdt, cycleSdt, cycleEdt); 
    } 

}
</script>
<body onload="changeDbNoteVal()" onBlur="self.focus()">
      <table border="0" cellpadding="0" cellspacing="0" id="SysMiddleSet2" width="100%">

        <tr>
          <td height="28" background="images/green_title_bg.jpg" class="black12b" style="background-repeat:no-repeat; padding-left:10px">系統訊息</td>
        </tr>
        <tr>
          <td height="28" style="padding-top:10px">
          <div align="left">
            <c:if test="${not empty requestScope.message}">
        	<p class="text"><font color="red"><%=(String)request.getAttribute("message")%></font></p>
			</c:if>
		</div>
		</td>
        </tr>
      </table>
</body>
<%
HibernateSessionFactory.closeSession();
 %>
