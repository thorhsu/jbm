<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="java.util.List"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	String message = ((String )request.getAttribute("message") == null)? "" : ((String )request.getAttribute("message")).trim();
	Lgform lgform= (Lgform)request.getAttribute("lgform");
	
	String dispatchDate = new SimpleDateFormat("yyyy-MM-dd").format(lgform.getDispatchDate());
	
	
	Integer iYYYY=Integer.parseInt(dispatchDate.substring(0,4)) - 1911;
	String YYYY = iYYYY.toString();
	String MM=dispatchDate.substring(5,7);
	String DD=dispatchDate.substring(8,10);


%>



<script type="text/javascript">
<!--
function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}
//-->
</script>

<script type="text/javascript"> 
$().ready(function() {

	selectAll = function (){
			$("input[name='jobBagNos']").each(function() { 
				$(this).attr("checked", true); 
			}); 
  	};
  	$('#selectAll').click(selectAll);
  	
	selectNon = function (){
			$("input[name='jobBagNos']").each(function() { 
				$(this).attr("checked", false); 
			}); 
  	};
  	$('#selectNon').click(selectNon);
  	if("<%=message%>" != ""){
  		alert("<%=message%>");
  	}
});


$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate({
	  rules: {
	    lgYear: {
	      required: true,
	      number: true
	    },
	    lgMonth: {
	      required: true,
	      number: true,
	      min: 1,
	      max: 12
	    },
	    lgDate: {
	      required: true,
	      number: true,
	      min: 1,
	      max: 31
	    }
	  }
	});
  });


</script>



<div align="center">

		<table border="0" width="95%" id="table177">
			<tr>
				<td width="33%">　</td>
				<td width="33%" height="26" class="title" >特約郵件郵資單</td>
				<td width="33%">　</td>
			</tr>
			
		</table>

        <form action="lgform.do" method="post" name="myform" id="myform">
        <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<table border="0" width="95%" id="table178">
			<tr>
				<td width="33%"><%=lgform.getCustName() %></td>
				<!-- //Thor add修改交寄日期 2011/10/20 -->
				<td width="33%">交寄日期: <INPUT name="lgYear" id="lgYear" maxLength=4 size=5  value="<%=YYYY %>"> 年 <INPUT name="lgMonth" id="lgMonth"  maxLength=2 size=3  value="<%=MM %>">  月 <INPUT name="lgDate" id="lgDate" maxLength=2 size=3  value="<%=DD %>"> 日 </td>
				<td width="33%">
				　</td>
			</tr>
		</table>


		<table border="0" width="95%" id="table176">
			<tr>
				<td width="66%">寄件人名稱:<%=lgform.getLgTitle() %> </td>
				<td width="33%">
				<% 
				    String displayStateNo = lgform.getStatementNo() + "";
				    //Integer displayStateNo = Integer.parseInt(stateNoStr.substring(stateNoStr.length() - 4));				   
				%>
				<p align="right">Statement 第 <font color="#FF0000"><%= displayStateNo %></font> 號</td>
			</tr>
		</table>

		
		<input type="hidden" name="fid" value="editSubmit">	     
		<input type="hidden" name="custNo" value="<%=lgform.getIdfCustNo() %>">
		<input type="hidden" name="statementNo" value="<%=lgform.getStatementNo() %>">   
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">

	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>

	      <tr>
	        <td>
	        

	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
			<vlh:root value="dataList"  url="?" includeParameters="*">
			<tr >
	            <vlh:header>
	            <vlh:column title=""    attributes="width=\"1%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="刪除"    attributes="width=\"1%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="寄達地區"      property="mail_to_area" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>            	            
	            <vlh:column title="郵件類別|航空|水陸路"      property="lg_type"  attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" colspan=\"2\"  style=\"text-align:center;\""/>
	            <vlh:column title="件數"      property="qty"  attributes="width=\"8%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="每件重量"      property="weight"  attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="每件郵資"      property="price" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="備註"      property="prog_nm" attributes="width=\"20%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            </vlh:header>
              </tr>
              
                            
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td ><INPUT type="hidden" name="ids"  id="ids" value="<c:out value="${bean.id}" />" ><c:out value="${stauts.index+1}" /></td>
	            <td align="center"><INPUT type='checkbox' value='<c:out value="${bean.id}" />' name='delIds' ></td>
	            <td ><INPUT id="mailToAreas"  maxLength=10 size=10 name="mailToAreas" value="<c:out value="${bean.mail_to_area}" />"> </td>
	            <td ><INPUT id="lgTypes"  maxLength=10 size=10 name="lgTypes" value="<c:out value="${bean.lg_type}" />"></td>
	            <td ><INPUT id="mailCategories"  maxLength=10 size=10 name="mailCategories" value="<c:out value="${bean.mail_category}" />"></td>
	            <td ><INPUT id="qties"  maxLength="5" size="5" name="qties" value="<c:out value="${bean.qty}" />"></td>
	            <td ><INPUT id="weights"  maxLength="5" size="5" name="weights" value="<c:out value="${bean.weight}" />"></td>
	            <td ><INPUT id="prices"  maxLength="5" size="5"  name="prices" value="<c:out value="${bean.price}" />"></td>
	            <td ><INPUT id="prognms"   size="30"  name="prognms" value="<c:out value="${bean.prog_nm}" />"></td>
              </tr>
              </c:forEach>
	          </vlh:root>
	          
	          <tr  >
	            <td ><INPUT type="hidden" name="ids"  id="ids" value="" ></td>
	            <td ></td>
	            <td ><INPUT id="mailToAreas"  maxLength=10 size=10 name="mailToAreas" value=""> </td>
	            <td ><INPUT id="lgTypes"  maxLength=10 size=10 name="lgTypes" value="" ></td>
	            <td ><INPUT id="mailCategories"  maxLength=10 size=10 name="mailCategories" value=""></td>
	            <td ><INPUT id="qties"  maxLength="5" size="5" name="qties" value=""></td>
	            <td ><INPUT id="weights"  maxLength="5" size="5"  name="weights" value=""></td>
	            <td ><INPUT id="prices"  maxLength="5" size="5"  name="prices" value=""></td>
	            <td ><INPUT id="prognms"   size="30"  name="prognms" value=""></td>
              </tr>
	          </table>
	           
	          </td>
          </tr>
	      <tr>
	        <td>&nbsp;</td>
          </tr>
          <% 
            String lgFormList = SessionUtil.getNextLGFormPageInfo(request, lgform.getIdfCustNo(), lgform.getStatementNo());
            if(!"no_lgformList".equals(lgFormList)){
          %>
          <tr><td align="center">            
            <%= lgFormList %>
            <input name="button" type="button" onclick="location='jobbagLG.do?fid=batchPrintLGFormSumbit&backToListURL=${backToListURL}'"  id="button" value="列印郵資單+交寄管制表 " /> 
			<input name="button6" type="submit"  id="button6" value="更新" />          
			<input name="button6" type="button" onclick="location='lgform.do?fid=editInit&<%=SessionUtil.getPrevLgFormRecord(request, lgform.getIdfCustNo(), lgform.getStatementNo()) %>&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
			<input name="button6" type="button" onclick="location='lgform.do?fid=editInit&<%=SessionUtil.getNextLGFormRecord(request, lgform.getIdfCustNo(), lgform.getStatementNo()) %>&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
          </td></tr>
          <% } %>
        </table>
        </form>
</div>