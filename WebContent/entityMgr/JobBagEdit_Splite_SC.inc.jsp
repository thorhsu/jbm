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

<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<script src="js/jbm.js" type="text/javascript"></script>
<%
	JobBagService jobbagService = JobBagService.getInstance();


	String  targetURI = (String)request.getAttribute("targetURI");
	String disabledSpilted="";
	if (!targetURI.equalsIgnoreCase("jobbagOP.do") ) 
		disabledSpilted="readonly disabled class=\"disabled\""; //只有OP 才能分檔, 其他 action 過來的 都只能view


 
	JobBag jobcode= (JobBag)request.getAttribute("jobbag");
	List lpinfoJSONlist = (List)request.getAttribute("lpinfoJSON"); //JSON Object of lpinfo
	JSONArray  lpinfoJSONArray = JSONArray.fromObject(lpinfoJSONlist);
	List psinfoJSONlist = (List)request.getAttribute("psinfoJSON"); //JSON Object of psinfo
	JSONArray  psinfoJSONArray = JSONArray.fromObject(psinfoJSONlist);
	List mpinfoJSONlist = (List)request.getAttribute("mpinfoJSON"); //JSON Object of mpinfo
	JSONArray  mpinfoJSONArray = JSONArray.fromObject(mpinfoJSONlist);
	List lginfoJSONlist = (List)request.getAttribute("lginfoJSON"); //JSON Object of lginfo
	JSONArray  lginfoJSONArray = JSONArray.fromObject(lginfoJSONlist);
	List returninfoJSONlist = (List)request.getAttribute("returninfoJSON"); //JSON Object of return
	JSONArray  returninfoJSONArray = JSONArray.fromObject(returninfoJSONlist);	
	List lcinfoJSONlist = (List)request.getAttribute("lcinfoJSON"); //JSON Object of lcinfo
	JSONArray  lcinfoJSONArray = JSONArray.fromObject(lcinfoJSONlist);
	
	
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
	
	CodeService codeService = CodeService.getInstance();
	List jobcodeTypeList = codeService.findBycodeTypeName("JOB_CODE_TYPE"); //工作種類
	List deadlineList = codeService.findBycodeTypeName("DEADLINE"); //DEADLINE
	List lgTypeList = codeService.findBycodeTypeName("LG_TYPE"); //郵資單總類
	List lgMailCategory = codeService.findBycodeTypeName("LG_MAIL_CATEGORY"); //交寄方式	
	List lgMailToPostofficeList = codeService.findBycodeTypeName("LG_MAIL_TO_POSTOFFICE"); //交寄郵局或公司

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
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%"  bgColor=#4aae66 id="table217">
				<tr>		
                <TD class="editTableLabelCellBKColor"   width="50">	分檔狀態</TD>
                <TD bgColor=#f4faf3 width="4%">


                <INPUT id="spliteCount" name="spliteCount" maxlength="3" size="2" value="<%=Util.getDecimalFormat ( jobcode.getSpliteCount() ) %>" <%=disabledSpilted %>> 


                </TD>
                <TD bgColor=#dff4dd  width="7%">AFP檔名</TD>
                <TD bgColor=#f4faf3 width="41%">
                <INPUT id="afpName" size="60" name="afpName" value="<%=Util.getStringFormat( jobcode.getAfpName() ) %>" <%=disabledVIEW %> >
                </TD>
                <TD bgColor=#dff4dd  width="7%">工單代碼</TD>
                <TD bgColor=#f4faf3 width="21%"><b><%=Util.getStringFormat( jobcode.getJobBagNo()) %></b></TD>
                </tr>
                
				<tr>
                <TD class="editTableLabelCellBKColor"   width="50">分檔</TD>
                <TD bgColor=#f4faf3 colspan="5">
  <div class="d01" style="padding-left:0px; padding-top:0px;  width:900px; height:150px; z-index:1; visibility: visible; overflow: auto;">      
         
					<table border="0" width="100%" id="table218">
						<tr>
							<td bgcolor="#DFF4DD" width="35">
							<input type="submit" value="列印" name="confirm3"   ></td>						
							<td bgcolor="#DFF4DD" width="35">批號</td>
							<td bgcolor="#DFF4DD" width="75">pages_起</td>
							<td bgcolor="#DFF4DD" width="75">pages_迄</td>							
							<td bgcolor="#DFF4DD" width="75">accounts_起</td>
							<td bgcolor="#DFF4DD" width="75">accounts_迄</td>
							<td bgcolor="#DFF4DD">列印作業完成日期</td>
							<td bgcolor="#DFF4DD">是否損壞</td>
							<td bgcolor="#DFF4DD">裝封作業完成日期</td>
							<td bgcolor="#DFF4DD">交寄作業完成日期</td>
							<td bgcolor="#DFF4DD">刪除</td>
						</tr>
<%
List splites = jobbagService.getJobBagSplites(jobcode);
for (int i=0;i<splites.size();i++) {
	String rowStyle = "evenLine";
	if( i % 2 == 0)
		rowStyle = "oddLine";
	
	JobBagSplite splite = (JobBagSplite)splites.get(i);
	String spliteSEQ = splite.getJobBagSpliteNo().substring(splite.getJobBagSpliteNo().length()-3, splite.getJobBagSpliteNo().length());
%>						
						<tr class="<%=rowStyle %>" id="rowsCount<%=i %>" onmousemove="changeClass('rowsCount<%=i %>', 'mouseOver')"  onmouseout="changeClass('rowsCount<%=i %>', null)">
							<td width="35">
							<INPUT id="jobBagSpliteNos" type=checkbox name="jobBagSpliteNos" value="<%=splite.getJobBagSpliteNo()%>" <%=disabledSpilted %>></td>
							<td width="35"><%=spliteSEQ %></td>
							<td width="57"><%=Util.getDecimalFormat( splite.getLpPagesSeqBegin()) %></td>
							<td width="53"><%=Util.getDecimalFormat( splite.getLpPagesSeqEnd() )%></td>
							<td width="57"><%=Util.getDecimalFormat( splite.getLpAccountSeqBegin()) %></td>
							<td width="53"><%=Util.getDecimalFormat( splite.getLpAccountSeqEnd() )%></td>
							<td width="119"><%=Util.getDateTimeFormat( splite.getLpCompletedDateByUser() )%></td>
							<td><%=Util.getBooleanFormat( splite.getMpHasDamage())  %></td>
							<td><%=Util.getDateTimeFormat( splite.getMpCompletedDateByUser() )%></td>
							<td><%=Util.getDateTimeFormat( splite.getLgCompletedDateByUser() )%></td>
							<td ><input name="button" type="button" onclick="javascript:confirmDelete('jobbagOP.do?fid=deleteJobBagSplite&jobBagSpliteNo=<%=splite.getJobBagSpliteNo()%>&backToListURL=${backToListURL}')"  id="button" value="刪除" <%=disabledSpilted %>/></td>
						</tr>
<%} %>						

					</table>

</div> 					
				</TD>
                </tr>
				</TABLE>
