<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>

<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%><head>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	Amex amex= (Amex)request.getAttribute("amex");

 
	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE"); 

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		amex.setId(null);
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();

});
</script>

	<form name="myform" id="myform" action="amex.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="<%=fid %>" />
		<input type="hidden" name="id" value="<%=amex.getId() %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">AMEX Mapping 維護</td>
				</tr>
				<tr>
				  <td height="15">&nbsp;</td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>AMEX ID</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<%=Util.getDecimalFormat( amex.getId() ) %></TD>
                 <TD class="editTableContentCellBKColor" width="50%" > 流水號, 系統產生 </TD>
			</TR>

			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>Country</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="country"   size=40 name="country" value="<%=Util.getStringFormat( amex.getCountry()) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" ></TD>
			</TR>
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>AFP Name</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="afpName"   size=40 name="afpName" value="<%=Util.getStringFormat( amex.getAfpName()) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" ></TD>
			</TR>
			
			
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>Type of File</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="typefile"   size=40 name="typefile" value="<%=Util.getStringFormat( amex.getTypefile()) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" >
                 0 —Diversion<br>
				 1 —Overweight<br>
				 2 —Mail Direct<br>
				 4 —Centrally Billed<br>
				 5 —Overweight Centrally Billed<br>
				 * —Unconditional (For AEB)
                 </TD>
			</TR>
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>Job Code No</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="jobCodeNo"   size=40 name="jobCodeNo" value="<%=Util.getStringFormat( amex.getJobCodeNo() ) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" ></TD>
			</TR>
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>Language Speaker</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="speaker"   size=40 name="speaker" value="<%=Util.getStringFormat( amex.getSpeaker() ) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" >
					* : English/Local,<br>
					S : SG, <br>
					O : Oversea, <br>
					B : Brunei               
                 </TD>
			</TR>
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>Special</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="special"   size=40 name="special" value="<%=Util.getStringFormat( amex.getSpecial()) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" ></TD>
			</TR>
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>MacReport</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="macreport"   size=40 name="macreport" value="<%=Util.getStringFormat( amex.getMacreport() ) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" ></TD>
			</TR>
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>AEB Type</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="aebType"   size=40 name="aebType" value="<%= Util.getStringFormat( amex.getAebType() ) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" ></TD>
			</TR>
			<TR>                 
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>Pages</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="pages"   size=40 name="pages" value="<%=Util.getDecimalFormat( amex.getPages() ) %>" <%=disabledVIEW %>> </TD>
                 <TD class="editTableContentCellBKColor" width="50%" ></TD>
			</TR>
												

<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan=4 align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='amex.do?fid=saveAsNewInit&id=<%=amex.getId()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<input name="button6" type="button"  onclick="javascript:history.go(-1)"  id="button6" value="取消, 回列表" />
<%  } else { %>
						<input name="button6" type="button"  onclick="javascript:history.go(-1)"  id="button6" value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
