<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
 
<%
	Employee employee= (Employee)request.getAttribute("employee");
	List lpmpStaffList= (List)request.getAttribute("lpmpStaffList");

	String productionController="";
	String empMgrNo="";
	String disabledSelectEmployee="readonly disabled class=\"disabled\""; 
	//判斷是否為管理員
	if (employee.getRole1s())  {
		productionController = employee.getEmpNo() + "  " + employee.getUserId();
		empMgrNo = employee.getEmpNo();
		disabledSelectEmployee ="";
	}		
	//目前全都開放，之後考慮要不要設定權限 Thor add
	disabledSelectEmployee ="";

	
%>
<script type="text/javascript"> 
$().ready(function() {

	$("#myform").validate();
	
	
	//大密技, 將attr= disabled, 轉成 正常, 這樣才能送出submit 帶出預設值
	$("#myform").submit(function(){
		$("#empNo").attr("disabled","");
		return true;
	});
	
	
});
</script>

<div align="center">
<form action="completedJob.do" method="post" name="myform"  id="myform">
<input type="hidden" name="fid" value="reportStatusInit">
<TABLE id=table2 border=0 cellSpacing=0 cellPadding=0 width="90%">

              <TR>
                <TH class=title>記錄完成工作</TH>
                </TR>
              <TR><TD>
                        <TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" 
                        bgColor=#4aae66 align=left id="table4">
                          <TBODY>
                          <TR>
                            <TD bgColor=#dff4dd width="15%" 
                            >完成時間</TD>
                            <TD bgColor=#f4faf3 width="35%" 
                              align=left>2011/09/07 &nbsp;&nbsp; 19:04 </TD>
                            <TD bgColor=#dff4dd width="15%" 
                            >　</TD>
                            <TD bgColor=#f4faf3 width="35%" align=left>　</TD></TR>
                          <tr>
                            <TD bgColor=#dff4dd width="15%" 
                            >操作員</TD>
                            <TD bgColor=#f4faf3 width="35%" align=left>
                            <SELECT name="empNo" id="empNo" size="1" <%=disabledSelectEmployee %>> 
<%
for (int i=0; i<lpmpStaffList.size(); i++) {
	Employee _employee= (Employee)lpmpStaffList.get(i);
	if (_employee.getEmpNo().equalsIgnoreCase(employee.getEmpNo() ) )
		out.println("<OPTION value='"+_employee.getEmpNo() +"' selected >"+_employee.getUserId()+"</OPTION>" );
	else
		out.println("<OPTION value='"+_employee.getEmpNo() +"' >"+_employee.getUserId()+"</OPTION>" );
} 
%>
							</SELECT></TD>
                            <TD bgColor=#dff4dd width="15%" >作業管理師</TD>
                            <TD bgColor=#f4faf3 width="35%" align=left>
                            <input type="hidden" name="empManagerNo" value="<%=empMgrNo %>" />
                            <%=productionController %></TD>
							</tr>
							</TBODY></TABLE>
							</TD></TR>
							<TR> <TD align="center">
							<input type="submit" name="submit" value="確認">
							</TD></TR>
</TABLE>
      
</form>      

</div>