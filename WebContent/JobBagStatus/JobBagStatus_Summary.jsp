<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	// List accountList = (List)request.getAttribute("dataList");
	
//顯示每個Status 合計值
Integer INIT_Count = 0;
Integer PRINTED_LP_Count = 0;
Integer PRINTED_MP_Count = 0;
Integer PRINTED_LG_Count = 0;
Integer COMPLETED_LP_Count = 0;
Integer NON_LP_Count = 0;
Integer COMPLETED_MP_Count = 0;
Integer NON_MP_Count = 0;
Integer PRINTED_LG_FORM_Count = 0;
Integer COMPLETED_LG_Count = 0;
Integer CLOSED_Count = 0;
Integer INITNONCYCLE_Count = 0;

ValueList dataList = (ValueList)request.getAttribute("dataList");
List list = dataList.getList();
for(int i=0;i<list.size();i++) {
	String job_bag_status= (String)PropertyUtils.getProperty(list.get(i),"job_bag_status");
	Integer count= (Integer)PropertyUtils.getProperty(list.get(i),"count");

	if (job_bag_status.equalsIgnoreCase("INIT")) 
		INIT_Count=count;
	else if (job_bag_status.equalsIgnoreCase("PRINTED_LP")) 
		PRINTED_LP_Count=count;
	else if (job_bag_status.equalsIgnoreCase("PRINTED_MP")) 
		PRINTED_MP_Count=count;
	else if (job_bag_status.equalsIgnoreCase("PRINTED_LG")) 
		PRINTED_LG_Count=count;
	else if (job_bag_status.equalsIgnoreCase("COMPLETED_LP")) 
		COMPLETED_LP_Count=count;				
	else if (job_bag_status.equalsIgnoreCase("NON_LP")) 
		NON_LP_Count=count;
	else if (job_bag_status.equalsIgnoreCase("COMPLETED_MP")) 
		COMPLETED_MP_Count=count;
	else if (job_bag_status.equalsIgnoreCase("NON_MP")) 
		NON_MP_Count=count;
	else if (job_bag_status.equalsIgnoreCase("PRINTED_LG_FORM")) 
		PRINTED_LG_FORM_Count=count;	
	else if (job_bag_status.equalsIgnoreCase("COMPLETED_LG")) 
		COMPLETED_LG_Count=count;
	else if (job_bag_status.equalsIgnoreCase("ACCOUNTING_EP1")) 
		CLOSED_Count=count;
	else if (job_bag_status.equalsIgnoreCase("INITNONCYCLE")) 
		INITNONCYCLE_Count=count;
	
}
%>



<div align="left">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0" align="left">
	      <tr>
	        <td height="26" class="title"> <bean:message key="label.job_bag_status_query" />  </td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="jobbagStatus.do" method="post">
          <input type="hidden" name="backToListURL" value="${backToListURL}" />
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="summary">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align=left id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" ><bean:message key="label.customer_id" /></TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="custNo"  maxLength=15 size=10 name="custNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left"><bean:message key="label.cycle_date" /></TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="cycleDate_form" maxLength=15 size=10 name="cycleDate_form">
<img border="0" alt="日期選擇輔助工具" id="cycleDate_datepicker" src="images/calendar2.gif" />
<script language="javascript">
		Calendar.setup({
			inputField : "cycleDate_form",						
		    button : "cycleDate_datepicker"
		});
</script>					
					</TD>
				</TR>			
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" ><bean:message key="label.job_bag_no" /></TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="jobBagNo"  maxLength=15 size=10 name="jobBagNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left"><bean:message key="label.processing_date" /></TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="receiveDate_form" maxLength=15 size=10 name="receiveDate_form">
<img border="0" alt="日期選擇輔助工具" id="receiveDate_datepicker" src="images/calendar2.gif" />
<script language="javascript">
		Calendar.setup({
			inputField : "receiveDate_form",						
		    button : "receiveDate_datepicker"
		});
</script>					
					
					</TD>
				</TR>			

			</TABLE>
            </td>
          </tr>
		 <tr>
	        <td height="30">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" align="center" id="table7">			
				<TR>
					<TD align="center"><input name="button" type="submit"  id="button" value="<bean:message key="label.search" />" /></TD>
				</TR>
			</TABLE>
            </td>
          </tr>
            </form>
	      <tr>
	        <td>
	        
<!-- 工單處理進度總表 -->	        
<table border="0" width="90%" id="table8" align="left">
				<tr>
					<td>
					<p align="center"><b><bean:message key="label.job_bag_status_summary" /></b></td>
				</tr>
				<tr>
					<td height="106">
					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 360px; top: 296px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=INIT">(<%=INIT_Count %>)</a></font></b></div>
                    <div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 395px; top: 296px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=INIT">(<%=INITNONCYCLE_Count %>)</a></font></b></div>						
					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 375px; top: 351px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=PRINTED_LP">(<%=PRINTED_LP_Count %>)</a></font></b></div>
					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 380px; top: 406px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=COMPLETED_LP">(<%=COMPLETED_LP_Count %>)</a></font></b></div>
					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 370px; top: 506px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=NON_LP">(<%=NON_LP_Count %>)</a></font></b></div>						
					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 630px; top: 406px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=COMPLETED_MP">(<%=COMPLETED_MP_Count %>)</a></font></b></div>
					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 650px; top: 506px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=NON_MP">(<%=NON_MP_Count %>)</a></font></b></div>						

					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 890px; top: 406px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=PRINTED_LG_FORM">(<%=PRINTED_LG_FORM_Count %>)</a></font></b></div>

					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 1050px; top: 406px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=COMPLETED_LG">(<%=COMPLETED_LG_Count %>)</a></font></b></div>
					<div style="position: absolute; width: 34px; height: 21px; z-index: 1; left: 1055px; top: 506px" id="layer1">
						<b><font color="#FF0000"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=ACCOUNTING_EP1">(<%=CLOSED_Count %>)</a></font></b></div>						

					<img border="0" src="images/jobBagStatus.png"><table border="1" width="100%" id="table82">
						<tr>
							<td width="70" bgcolor="#C0C0C0">　</td>
							<td width="242" bgcolor="#C0C0C0"><bean:message key="label.printing" /></td>
							<td bgcolor="#C0C0C0">　</td>
							<td width="240" bgcolor="#C0C0C0"><bean:message key="label.MP_PS_DM" /></td>
							<td width="4" bgcolor="#C0C0C0">　</td>
							<td width="240" bgcolor="#C0C0C0"><bean:message key="label.dispatch" /></td>
							<td width="4" bgcolor="#C0C0C0">　</td>
							<td width="105" bgcolor="#C0C0C0"><bean:message key="label.closed" /></td>
						</tr>
						<tr>
							<td width="70">合計(SUM)</td>
							<td width="242"><%=INIT_Count + INITNONCYCLE_Count + PRINTED_LP_Count +  COMPLETED_LP_Count + NON_LP_Count %></td>
							<td>　</td>
							<td width="240"><%=COMPLETED_MP_Count +  NON_MP_Count %></td>
							<td width="4">　</td>
							<td width="240"><%=COMPLETED_MP_Count +  COMPLETED_LG_Count %></td>
							<td width="4">　</td>
							<td width="105"><%=CLOSED_Count %></td>
						</tr>
						<tr>
							<td width="70">　</td>
							<td width="242"><font size="2">INIT:工單產生</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=INIT">(<%=INIT_Count %>)&nbsp;(<%=INITNONCYCLE_Count %>)</a></font></b></td>
							<td>　</td>
							<td width="240">　</td>
							<td width="4">　</td>
							<td width="240">　</td>
							<td width="4">　</td>
							<td width="105">　</td>
						</tr>
						<tr>
							<td width="70">　</td>
							<td width="242"><font size="2">PRINTED: 已列印工單</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=PRINTED_LP">(<%=PRINTED_LP_Count%>)</a></font></b></td>
							<td>　</td>
							<td width="240"><font size="2">COMPLETED_MP:裝封已完成</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=COMPLETED_MP">(<%=COMPLETED_MP_Count%>)</a></font></b></td>
							<td width="4">　</td>
							<td width="240"><font size="2">已列印郵資單</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=PRINTED_LG_FORM">(<%=PRINTED_LG_FORM_Count%>)</a></font></b></td>
							<td width="4">　</td>
							<td width="105">　</td>
						</tr>
						<tr>
							<td width="70">　</td>
							<td width="242"><font size="2">COMPLETED_LP:列印已完成</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=COMPLETED_LP">(<%=COMPLETED_LP_Count%>)</a></font></b></td>
							<td>　</td>
							<td width="240">　</td>
							<td width="4">　</td>
							<td width="240"><font size="2">COMPLETED_LG:<br>
							交寄已完成</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=COMPLETED_LG">(<%=COMPLETED_LG_Count%>)</a></font></b></td>
							<td width="4">　</td>
							<td width="105">　</td>
						</tr>
						<tr>
							<td width="70">　</td>
							<td width="242">　</td>
							<td>　</td>
							<td width="240">　</td>
							<td width="4">　</td>
							<td width="240">　</td>
							<td width="4">　</td>
							<td width="105">　</td>
						</tr>
						<tr>
							<td width="70">　</td>
							<td width="242"><font size="2">NON_LP:不需列印</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=NON_LP">(<%=NON_LP_Count%>)</a></font></b></td>
							<td>　</td>
							<td width="240"><font size="2">NON_MP:不需裝封</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=NON_MP">(<%=NON_MP_Count%>)</a></font></b></td>
							<td width="4">　</td>
							<td width="240">　</td>
							<td width="4">　</td>
							<td width="105"><font size="2">已結案</font><b><font color="#FF0000" size="4"><a href="jobbagStatus.do?fid=list&backToListURL=${backToListURL}&jobBagStatuses=ACCOUNTING_EP1">(<%=CLOSED_Count%>)</a></font></b></td>
						</tr>
						<tr>
							<td width="70">　</td>
							<td width="242">　</td>
							<td>　</td>
							<td width="240">　</td>
							<td width="4">　</td>
							<td width="240">　</td>
							<td width="4">　</td>
							<td width="105">　</td>
						</tr>
					</table>
					</td>
				</tr>
				</table>	        



	           
	          </td>
          </tr>
	      <tr>
	        <td>&nbsp;</td>
          </tr>

        </table>
</div>