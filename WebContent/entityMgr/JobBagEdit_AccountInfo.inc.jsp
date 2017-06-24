<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.painter.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%

JobBag jobcode= (JobBag)request.getAttribute("jobbag");

//編輯模式 判斷
String fid="editSubmit"; //預設editSubmit
String disabledPK="readonly class=\"disabled\"";
String disabledVIEW="";
String actionMode = (String)request.getAttribute("ACTION_MODE");

if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
	fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
	disabledPK="";
	jobcode.setJobBagNo("");
} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
	disabledVIEW="readonly disabled class=\"disabled\"";
}

%>

					<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" bgColor=#4aae66  id="table4">
                          <tr>
                            <TD bgColor=#dff4dd width="80" ><font color="#FF0000" size="2">*</font>Cycle Date</TD>
                            <TD bgColor=#f4faf3  width="150">
							<input type="text" name="cycleDate_form" id="cycleDate_form" size="6" readonly class="required" value="<%=Util.getDateFormat( jobcode.getCycleDate()) %>" <%=disabledVIEW %>/>
<img border="0" alt="日期選擇輔助工具" id="cycleDate_datepicker" src="images/calendar2.gif" <%=disabledVIEW %> />
<script language="javascript">
		Calendar.setup({
			inputField : "cycleDate_form",						
		    button : "cycleDate_datepicker"
		});
</script>
							</TD>
                            </tr>
                          <tr>
                            <TD bgColor=#dff4dd ><font color="#FF0000" size="2">*</font>收檔期間 </TD>
                            <TD bgColor=#f4faf3  width="100">
							<input type="text" name="receiveDate_form" id="receiveDate_form" size="6" readonly class="required" value="<%=Util.getDateFormat( jobcode.getReceiveDate()) %>" <%=disabledVIEW %>/>                            
<img border="0" alt="日期選擇輔助工具" id="receiveDate_datepicker" src="images/calendar2.gif" <%=disabledVIEW %> />
<script language="javascript">
		Calendar.setup({
			inputField : "receiveDate_form",						
		    button : "receiveDate_datepicker"
		});
</script>

							</TD>
                            </tr>
                          <tr>
                            <TD bgColor=#dff4dd >
							工單狀態</TD>
                            <TD bgColor=#f4faf3  width="100">
                            <input type="hidden" name="jobBagStatus" value="<%=jobcode.getJobBagStatus()%>">
							<%=Util.getCodeTypeValue ("JOB_BAG_STATUS", jobcode.getJobBagStatus())%></TD>
                            </tr>
                          <tr>
                            <TD bgColor=#dff4dd >
							accounts</TD>
                            <TD bgColor=#f4faf3  width="100">
							<INPUT id="accounts" size="8"  name="accounts" value="<%=Util.getDecimalFormatWithThousandSeparator( jobcode.getAccounts()  )%>" <%=disabledVIEW %>>
							</TD>
                            </tr>
                          <tr>
                            <TD bgColor=#dff4dd >頁數</TD>
                            <TD bgColor=#f4faf3  width="100">
							<INPUT id="pages" size="8"  name="pages" value="<%=Util.getDecimalFormatWithThousandSeparator( jobcode.getPages()) %>" <%=disabledVIEW %>>
							</TD>
                            </tr>
                          <tr>
                            <TD bgColor=#dff4dd >紙張數</TD>
                            <TD bgColor=#f4faf3  width="100">
							<INPUT id="sheets" size="8"  name="sheets" value="<%=Util.getDecimalFormatWithThousandSeparator( jobcode.getSheets()) %>" <%=disabledVIEW %>>
							</TD>
                            </tr>
                          </TABLE>
