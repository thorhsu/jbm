<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	JobCode jobcode = (JobCode) request.getAttribute("jobcode");

	//編輯模式 判斷
	String fid = "editSubmit"; //預設editSubmit
	String disabledPK = "readonly class=\"disabled\"";
	String disabledVIEW = "";
	String actionMode = (String) request.getAttribute("ACTION_MODE");

	if (null != actionMode
			&& (actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode
					.equalsIgnoreCase("ADD"))) {
		fid = "addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK = "";
		//jobcode.setJobCodeNo("");
		jobcode.setJobCodeId("");
	} else if (null != actionMode
			&& actionMode.equalsIgnoreCase("VIEW")) {
		disabledVIEW = "readonly disabled class=\"disabled\"";
	}
%>

<script type="text/javascript"> 
$().ready(function() {	
	selectAll = function (){
		var selectByWeek = $("#selectByWeek").val();
		if(selectByWeek == "0"){
		   for(var i=1;i<=31;i++) {
			  if (i<10)
				  $('#cycle0'+i).attr("checked", true);
			  else
				  $('#cycle'+i).attr("checked", true);
		  }
		}else{
			$("input[id^=weekDay]").attr("checked", true);
		}
  	};
  	$('#selectAll').click(selectAll);
  	
	selectNon = function (){
		var selectByWeek = $("#selectByWeek").val();
		if(selectByWeek == "0"){
		   for(var i=1;i<=31;i++) {
			   if (i<10)
				   $('#cycle0'+i).attr("checked", false);
			   else
				   $('#cycle'+i).attr("checked", false);
		   }
		}else{
			$("input[id^=weekDay]").attr("checked", false);
		}
  	};
  	$("#selectByWeek").change(function(){
  	    tableDisplayJudge();
  	});
  	$('#selectNon').click(selectNon);
  	$('#transferType').val("${jobcode.transferType}");
  	$('#selectByWeek').val('${jobcode.selectByWeek? "1" : "0"}');
  	tableDisplayJudge();  
  	$('#mydialog').jqm();
});
function tableDisplayJudge(){
	if($("#selectByWeek").val() == "0"){
		$("input[id^=weekDay]").attr("checked", false);
	    $('#tableWeek').hide();	
	    $('#table22').show();
	}else{
		$("input[id^=cycle]").attr("checked", false);
	    $('#tableWeek').show();	
	    $('#table22').hide();
	}
} 
function dialogShow(){   
	   $('#mydialog').jqmShow();	   
}
</script>

<TABLE border="0" width="260" id="table9" bgColor="#dff4dd">
<TR>
	<TD>客戶傳檔周期</TD>
	<TD>
	   <select id="selectByWeek" name="selectByWeek">
	       <option value="0">依日期</option>
	       <option value="1">依星期幾</option>
	   </select>
	   <input name="selectAll" type="button"  id="selectAll" value="全選" <%=disabledVIEW%>/>
	   <input name="selectNon" type="button"  id="selectNon" value="清除" <%=disabledVIEW%>/>	   
	</TD>
</TR>
</table>
<table id="tableWeek" width="260" cellspacing="0" cellpadding="0" >
<tr>
   <td colspan="7">勾選傳檔周期</td>
</tr>
<tr>
   <td>
      <input type="checkbox"  id="weekDay1" name="weekDay1" value="1" <%=disabledVIEW%> ${jobcode.weekDay1? "checked" : "" } />日 &nbsp;      
   <td>
   <td>
      <input type="checkbox"  id="weekDay2" name="weekDay2" value="1" <%=disabledVIEW%> ${jobcode.weekDay2? "checked" : "" } />一  &nbsp;    
   <td>
   <td>
      <input type="checkbox"  id="weekDay3" name="weekDay3" value="1" <%=disabledVIEW%> ${jobcode.weekDay3? "checked" : "" } />二    &nbsp;
   <td>
   <td>
      <input type="checkbox"  id="weekDay4" name="weekDay4" value="1" <%=disabledVIEW%> ${jobcode.weekDay4? "checked" : "" } />三    &nbsp;
   <td>
   <td>
      <input type="checkbox"  id="weekDay5" name="weekDay5" value="1" <%=disabledVIEW%> ${jobcode.weekDay5? "checked" : "" } />四    &nbsp;
   <td>
   <td>
      <input type="checkbox"  id="weekDay6" name="weekDay6" value="1" <%=disabledVIEW%> ${jobcode.weekDay6? "checked" : "" } />五    &nbsp;
   <td>
   <td>
      <input type="checkbox"  id="weekDay7" name="weekDay7" value="1" <%=disabledVIEW%> ${jobcode.weekDay7? "checked" : "" } />六    &nbsp;
   <td>
</tr>
<tr>
    <td colspan="7"> </td>
</tr>
</table>
<table id="table22" width="260" cellspacing="0" cellpadding="0">
	<tr>
		<td><input type="checkbox" id="cycle01" name="cycle01" value="1" <%=disabledVIEW%> ${jobcode.cycle01? "checked" : "" } />1</td>
		<td><input type="checkbox" id="cycle02" name="cycle02" value="1" <%=disabledVIEW%> ${jobcode.cycle02? "checked" : "" } />2</td>
		<td><input type="checkbox" id="cycle03" name="cycle03" value="1" <%=disabledVIEW%> ${jobcode.cycle03? "checked" : "" } />3</td>
		<td><input type="checkbox" id="cycle04" name="cycle04" value="1" <%=disabledVIEW%> ${jobcode.cycle04? "checked" : "" } />4</td>
		<td><input type="checkbox" id="cycle05" name="cycle05" value="1" <%=disabledVIEW%> ${jobcode.cycle05? "checked" : "" } />5</td>
		<td><input type="checkbox" id="cycle06" name="cycle06" value="1" <%=disabledVIEW%> ${jobcode.cycle06? "checked" : "" } />6</td>
		<td><input type="checkbox" id="cycle07" name="cycle07" value="1" <%=disabledVIEW%> ${jobcode.cycle07? "checked" : "" } />7</td>
	</tr>
	<tr>
		<td><input type="checkbox" id="cycle08" name="cycle08" value="1" <%=disabledVIEW%> ${jobcode.cycle08? "checked" : "" } />8</td>
		<td><input type="checkbox" id="cycle09" name="cycle09" value="1" <%=disabledVIEW%> ${jobcode.cycle09? "checked" : "" } />9</td>
		<td><input type="checkbox" id="cycle10" name="cycle10" value="1" <%=disabledVIEW%> ${jobcode.cycle10? "checked" : "" } />10</td>
		<td><input type="checkbox" id="cycle11" name="cycle11" value="1" <%=disabledVIEW%> ${jobcode.cycle11? "checked" : "" } />11</td>
		<td><input type="checkbox" id="cycle12" name="cycle12" value="1" <%=disabledVIEW%> ${jobcode.cycle12? "checked" : "" } />12</td>
		<td><input type="checkbox" id="cycle13" name="cycle13" value="1" <%=disabledVIEW%> ${jobcode.cycle13? "checked" : "" } />13</td>
		<td><input type="checkbox" id="cycle14" name="cycle14" value="1" <%=disabledVIEW%> ${jobcode.cycle14? "checked" : "" } />14</td>
	</tr>
	<tr>
		<td><input type="checkbox" id="cycle15" name="cycle15" value="1" <%=disabledVIEW%> ${jobcode.cycle15? "checked" : "" } />15</td>
		<td><input type="checkbox" id="cycle16" name="cycle16" value="1" <%=disabledVIEW%> ${jobcode.cycle16? "checked" : "" } />16</td>
		<td><input type="checkbox" id="cycle17" name="cycle17" value="1" <%=disabledVIEW%> ${jobcode.cycle17? "checked" : "" } />17</td>
		<td><input type="checkbox" id="cycle18" name="cycle18" value="1" <%=disabledVIEW%> ${jobcode.cycle18? "checked" : "" } />18</td>
		<td><input type="checkbox" id="cycle19" name="cycle19" value="1" <%=disabledVIEW%> ${jobcode.cycle19? "checked" : "" } />19</td>
		<td><input type="checkbox" id="cycle20" name="cycle20" value="1" <%=disabledVIEW%> ${jobcode.cycle20? "checked" : "" } />20</td>
		<td><input type="checkbox" id="cycle21" name="cycle21" value="1" <%=disabledVIEW%> ${jobcode.cycle21? "checked" : "" } />21</td>
	</tr>
	<tr>
		<td><input type="checkbox" id="cycle22" name="cycle22" value="1" <%=disabledVIEW%> ${jobcode.cycle22? "checked" : "" } />22</td>
		<td><input type="checkbox" id="cycle23" name="cycle23" value="1" <%=disabledVIEW%> ${jobcode.cycle23? "checked" : "" } />23</td>
		<td><input type="checkbox" id="cycle24" name="cycle24" value="1" <%=disabledVIEW%> ${jobcode.cycle24? "checked" : "" } />24</td>
		<td><input type="checkbox" id="cycle25" name="cycle25" value="1" <%=disabledVIEW%> ${jobcode.cycle25? "checked" : "" } />25</td>
		<td><input type="checkbox" id="cycle26" name="cycle26" value="1" <%=disabledVIEW%> ${jobcode.cycle26? "checked" : "" } />26</td>
		<td><input type="checkbox" id="cycle27" name="cycle27" value="1" <%=disabledVIEW%> ${jobcode.cycle27? "checked" : "" } />27</td>
		<td><input type="checkbox" id="cycle28" name="cycle28" value="1" <%=disabledVIEW%> ${jobcode.cycle28? "checked" : "" } />28</td>
	</tr>
	<tr>
		<td><input type="checkbox" id="cycle29" name="cycle29" value="1" <%=disabledVIEW%> ${jobcode.cycle29? "checked" : "" } />29</td>
		<td><input type="checkbox" id="cycle30" name="cycle30" value="1" <%=disabledVIEW%> ${jobcode.cycle30? "checked" : "" } />30</td>
		<td><input type="checkbox" id="cycle31" name="cycle31" value="1" <%=disabledVIEW%> ${jobcode.cycle31? "checked" : "" } />31</td>
		<td></td>
		<td></td>
		<td></td>
		<td><input type="checkbox" id="cycleEnd" name="cycleEnd"
			value="1" <%=disabledVIEW%> ${jobcode.cycleEnd? "checked" : "" } />月底</td>
	</tr>
	<tr>
	</tr>
	<tr>
	</tr>

</table>


<table border="0" width="260" id="table9" bgColor="#dff4dd">
<tr>
	<td align="left">根據周期產生工單&nbsp;<input type="checkbox" name="createByCycle"   id="createByCycle" value="1" <%=disabledVIEW%> ${jobcode.createByCycle? "checked" : "" } /></td>
</tr>
<tr>
	<td><a href="#"  onclick="dialogShow()" >客戶傳檔方式</a>&nbsp;
	     <select name="transferType" id="transferType">
	      <c:forEach var="transferType" items="${transferTypes}" varStatus="stauts" >
	          <option value="${transferType.id}">${transferType.codeKey}</option>
	      </c:forEach>	   
	   </select> 
	</td>	
</tr>
               <div class="jqmWindowPreview" id="mydialog" align="left">
                  <a href="#" class="jqmClose">Close</a>
                     <hr>
                      <c:forEach var="transferType" items="${transferTypes}" varStatus="stauts" >
	                        <b>${transferType.codeKey}</b>&nbsp; - &nbsp; ${transferType.f1}
	                        <br />
	                  </c:forEach>	   
                     
               </div>

    
</table>
