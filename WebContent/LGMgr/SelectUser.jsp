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
	List lpStaffList= (List)request.getAttribute("lpStaffList");
	List mpStaffList= (List)request.getAttribute("mpStaffList");

	String productionController="";
	
	if (employee.getRole1s()) //判斷是否為管理員 
		productionController = employee.getEmpNo() + "  " + employee.getUserId();
		

	
%>



<div align="center">
<form action="completedJob.do" method="post">
<input type="hidden" name="backToListURL" value="${backToListURL}" />
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
                            <SELECT name="empNo" size="1"> 
<%
for (int i=0; i<lpStaffList.size(); i++) {
	Employee _employee= (Employee)lpStaffList.get(i);
	out.println("<OPTION value='"+_employee.getEmpNo() +"'>"+_employee.getUserId()+"</OPTION>" );
} 
for (int i=0; i<mpStaffList.size(); i++) {
	Employee _employee= (Employee)mpStaffList.get(i);
	out.println("<OPTION value='"+_employee.getEmpNo() +"'>"+_employee.getUserId()+"</OPTION>" );
}
%>
							</SELECT></TD>
                            <TD bgColor=#dff4dd width="15%" >作業管理師</TD>
                            <TD bgColor=#f4faf3 width="35%" align=left><%=productionController %>
                          </TD>
							</tr>
							</TBODY></TABLE>
							</TD></TR>
							<TR> <TD align="center">
							<input type="submit" name="submit" value="確認">
							</TD></TR>
</TABLE>
      
</form>      

</div>