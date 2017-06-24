<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	Date now = new Date();		
	String DateEnd = new SimpleDateFormat("yyyy-MM-dd").format(now);
	String DateBegin = new SimpleDateFormat("yyyy-MM-dd").format(now);
 %>
 <script lang="javascript">
    //Thor add
    <c:out value="${setParamJqueryStr}"  escapeXml="false"/>
    //Thor
    $().ready(function() {
        $("#updateEmail").click(function(){
        	if(confirm("確定更改email？")){
        	   $("#fid").val("updateEmail");
        	   return true;
        	}else{
        	   return false;
        	}
        });
        $("#custNo").change(function(){
        	$("#fid").val("citiMailReport");
        	$("#reportForm").submit();
        });
        $("#scheduleTime").val("${reportForm.scheduleTime}");
    });
    
 </script>
 
<div align="center">
		<form id="reportForm" action="report.do" method="post">
	    <input type="hidden" name="includeParams_value" value="custNo,queryDateBegin,queryDateEnd">		
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	       
	      <tr>
	        <td height="26" class="title">
	                          客戶寄件報表
	          <input type="hidden" id="fid" name="fid" value="citiMailReportQuery" />
	        </td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>

	      <tr>
	        <td height="30">
	        

			<table border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<tr >
					<td class="queryTableCellBKColor" width="9%" align="left" >請輸入統計之年月日</td>
					<td class="queryTableCellBKColor" width="39%" align="left" >
<input type="text" name="queryDateBegin" id="queryDateBegin" size="8" readonly value="<%=DateBegin %>"/>
<img border="0" alt="日期選擇輔助工具" id="queryDateBegin_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "queryDateBegin",						
		    button : "queryDateBegin_datepicker"
		});
</script> ~ 
<input type="text" name="queryDateEnd" id="queryDateEnd" size="8" readonly value="<%=DateEnd %>"/>
<img border="0" alt="日期選擇輔助工具" id="queryDateEnd_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "queryDateEnd",						
		    button : "queryDateEnd_datepicker"
		});
</script>				
					</td>
					<td class="queryTableCellBKColor" width="9%" align="left" >客戶</td>
					<td class="queryTableCellBKColor" width="39%" align="left" >
			             <select name="custNo" size="1"   id="custNo" >                           
                             <c:forEach var="customer" items="${reportForm.customers}" >
                                 <option value="${customer.custNo}" >${customer.easyName}</option>
                             </c:forEach>
                         </select>
					</td>
				</tr>
		 </table>
		 <tr>
	        <td height="30" >
			    <input name="button" type="submit" value="查詢"   />
			</td>
	    </tr>
	    <tr>
	       <td>
		     <table border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left >
				<tr>
				   <td class="queryTableCellBKColor"  align="center" colspan="4"><b>每日交寄電子郵件信箱</b></td>				   					 
				</tr>
				   
			    <c:forEach var="email" items="${reportForm.scheduleEmails}" varStatus="count">
				     <c:if test="${count.index % 2 == 0}" >
				       <tr>
				     </c:if>
				        <td class="queryTableCellBKColor" align="center" colspan="2">
				           <input type="text" name="scheduleEmails" value="${email}" size="50"/>
				        </td>
				     <c:if test="${count.index % 2 == 1 || count.last}" >
				       </tr>
				     </c:if>
			    </c:forEach>
			    <tr>
				   <td class="queryTableCellBKColor"  align="center" colspan="4"><b>交寄時間</b></td>				   					 
				</tr>
				<tr>
				   <td class="queryTableCellBKColor" align="center" colspan="4">
				               每天:<select name="scheduleTime" id="scheduleTime">
				               <option value="0">0</option>
				               <option value="1">1</option>
				               <option value="2">2</option>
				               <option value="3">3</option>
				               <option value="4">4</option>
				               <option value="5">5</option>
				               <option value="6">6</option>
				               <option value="7">7</option>
				               <option value="8">8</option>
				               <option value="9">9</option>
				               <option value="10">10</option>
				               <option value="11">11</option>
				               <option value="12">12</option>
				               <option value="13">13</option>
				               <option value="14">14</option>
				               <option value="15">15</option>
				               <option value="16">16</option>
				               <option value="17">17</option>
				               <option value="18">18</option>
				               <option value="19">19</option>
				               <option value="20">20</option>
				               <option value="21">21</option>
				               <option value="22">22</option>
                               <option value="23">23</option>
				          </select>
				              時寄送
				   </td>
				   
				</tr>   
			</table>
            </td>
          </tr>
          <tr>
	        <td height="30" >
		      <input  id="updateEmail" type="submit" value="更新email名單" />			    
            </td>
          </tr>
          </table>
		</form>

</div>