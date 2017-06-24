<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="org.apache.commons.beanutils.BeanUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%> 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<script src="js/jbm.js" type="text/javascript"></script>
<%
	String currentDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
	JobBagService jobbagService = JobBagService.getInstance();

	JobBag jobbag= (JobBag)request.getAttribute("jobbag");
	Employee employee = (Employee)request.getAttribute("employee");

	String empNo="";

	
	if (null!= employee) empNo = employee.getEmpNo();

		

%>




		
<div align="center">		
			<TABLE width="95%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD height="26" class="title">工單刪除</TD>
				</TR>
				<TR>
				  <TD height="15">&nbsp;</TD>
				</TR>				
			</table>

			<form action="jobbagOP.do" method="post">
			<input type="hidden" name="backToListURL" value="${backToListURL}" />
			<input type="hidden" name="fid" value="deleteJobbagSubmit" />
			<input type="hidden" name="empNo" value="<%=empNo %>" />
			<input type="hidden" name="jobBagNo" value="<%=jobbag.getJobBagNo() %>" />
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="95%" class="editTableBKColor"  id="table8">
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70">工單代號</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
					<%=jobbag.getJobBagNo() %>           
					</TD>
					</TR>
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70">活動名稱</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
					<%=jobbag.getProgNm() %>
					</TD>
					</TR>
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70">動作</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
					<INPUT  type="radio" id="deleteAction" name="deleteAction"  value="DELETE" class="required"   <% if( null!=jobbag && null!=jobbag.getIsDeleted() && jobbag.getIsDeleted() ) out.println("checked");%> class="required"/>刪除
					<INPUT  type="radio" id="deleteAction" name="deleteAction"  value="RECOVERY" class="required" <% if( null!=jobbag && null!=jobbag.getIsDeleted() && !jobbag.getIsDeleted() ) out.println("checked");%> class="required"/>還原
					<INPUT  type="radio" id="deleteAction" name="deleteAction"  value="RECOVERY" class="required" <% if( null ==jobbag.getIsDeleted() ) out.println("checked");%> class="required"/>未刪除
					</TD>
					</TR>									
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70">因素</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
					<TEXTAREA cols="80" name="deletedReason"  id="deletedReason" rows="4" ><%=Util.getStringFormat(jobbag.getDeletedReason() ) %></TEXTAREA>              
					</TD>
					</TR>
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70">人員</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
					<%=employee.getEmpNo() + "  " + employee.getUserId() %>             
					</TD>
					</TR>										
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70">時間</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
					<%=currentDateTime %>            
					</TD>
					</TR>	

                            					
			</TABLE>
			<input name="button6" type="submit"  id="button6" value="確定" />

			</form>		
</div>