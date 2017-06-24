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
	CodeService codeService = CodeService.getInstance();
    List machineLPList = codeService.findBycodeTypeName("MACHINE_LP"); //MACHINE_LP	
	List machineMPList = codeService.findBycodeTypeName("MACHINE_MP"); //MACHINE_MP
	List jobcodeTypeList = codeService.findBycodeTypeName("JOB_CODE_TYPE"); //工作種類
	List deadlineList = codeService.findBycodeTypeName("DEADLINE"); //DEADLINE
%>
<script src="js/lib/jquery.jqModal.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.dimensions.js" type="text/javascript"></script>
<script type="text/javascript">

function batchCompletedLPSumbit() {
  document.myform.fid.value ="batchCompletedLPSumbit";
  document.myform.submit();
  return true;
}

function printpdf() {
  document.myform.fid.value ="print";
  document.myform.submit();
  return true;
}

function batchCompletedMPSumbit() {
  document.myform.fid.value ="batchCompletedMPSumbit";
  document.myform.submit();
  return true;
}

function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}

function checkAllBox(){
    var ischecked = $('#checkAll').attr("checked");
    if(ischecked){
       $(':checkbox[name="jobBagStatuses"]').attr("checked", true);
    }else{
       $(':checkbox[name="jobBagStatuses"]').attr("checked", false);
    }
}

</script>

<script type="text/javascript"> 
// Thor add
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
//Thor



$().ready(function() {
    var rowsCount = "rowsCount";
	selectAllJobBag = function (){
	        var i = 0;
			$("input[name='jobBagNos']").each(function() { 
			    i++;
				$(this).attr("checked", true);
				var lineid = document.getElementById(rowsCount + i);  
				$(lineid).addClass("checkedLine");
			}); 
  	};
  	$('#selectAll').click(selectAllJobBag);
  	
	selectNoJobBag = function (){
	        var i = 0; 
			$("input[name='jobBagNos']").each(function() {
			    i++; 
				$(this).attr("checked", false);
				var lineid = document.getElementById(rowsCount + i);
				$(lineid).removeClass("checkedLine");
			}); 
  	};
  	$('#selectNon').click(selectNoJobBag);
});

$().ready(function() {
     $('#lpDialog').jqm();
     $('#mpDialog').jqm();
     $('#dialog').jqm().jqDrag();
     
     var timer = 
 		$.timer(
 			function() {
 				$.getJSON('jobbagOP.do?fid=updateJobBagStatus', 
 				        {idList: "${idList}"},
 				        function(data) {
 				        	var lineCounter = 0; 				        	
 				        	$.each(data, function(i){ 				        		
 				        		var jobBagStatusId = document.getElementById(data[i].jobBagNo);
 				        		var spliteCountId = document.getElementById("spliteCounts" + data[i].jobBagNo);
 				        		var oriSpliteCountVal = spliteCountId.value; 
 				        		var oriText = $(jobBagStatusId).text();
 				        		if(oriText != data[i].jobBagStatus){
 				        			if(lineCounter == 0)
 					        			$('#updateMessage').text("");
 					        		lineCounter ++;
 				        			var dialogTxt = $('#updateMessage').html() + data[i].jobBagNo + " 工單進度變為：" + data[i].jobBagStatus + "<br />";
 				        			$('#updateMessage').html(dialogTxt);
 				        		   $(jobBagStatusId).text(data[i].jobBagStatus);
 				        		   if(data[i].jobBagStatus != "工單產生" && data[i].jobBagStatus != "不需列印")
 				        			   $("#delete" + data[i].jobBagNo).hide();
 				        		   $('#dialog').jqmShow(); 				        		   
 				        		   $(jobBagStatusId).addClass("mouseOver");
 				        		   setTimeout ( function() {$(jobBagStatusId).removeClass("mouseOver");}, 1000 );
 				        	    }
 				        		if(oriSpliteCountVal != data[i].spliteCount){
 				        			var dialogTxt = $('#updateMessage').html() + data[i].jobBagNo + " 分檔批次變為：" + data[i].spliteCount + "<br />";
 				        			$('#updateMessage').html(dialogTxt);
 				        		   $(spliteCountId).val(data[i].spliteCount);
 				        		   $('#dialog').jqmShow();				        		    				        		   
 				        	    }
 				        		
 				        	 });				  
 				        }
 				 );
 				
 			},
 			50000,
 			true
 		);
});

function lpDialogShow(){
   $('#lpDialog').jqmShow();
}
function mpDialogShow(){
   $('#mpDialog').jqmShow();
}
</script>



<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">工單查詢及分檔列印</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="jobbagOP.do" method="post">
                    
         
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="98%" class="queryTableBKColor" align="left" id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="10%" align="left" >客戶代號</TD>
					<TD class="queryTableCellBKColor" width="39%" align="left" ><INPUT id="custNo"  maxLength="3" size="10" name="custNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">Cycle Date</TD>
					<TD class="queryTableCellBKColor" align="left" ><INPUT id="cycleDate_form" maxLength="15" size="10" name="cycleDate_form">
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
					<TD class="queryTableCellBKColor"  align="left" >工單代號</TD>
					<TD class="queryTableCellBKColor"  align="left" ><INPUT id="jobBagNo"  maxLength="15" size="13" name="jobBagNo" ></TD>
					<TD class="queryTableCellBKColor"  align="left">轉檔日期<BR>(收檔日期)</TD>
					<TD class="queryTableCellBKColor" align="left" ><INPUT id="receiveDate_form" maxLength="15" size="10" name="receiveDate_form">
<img border="0" alt="日期選擇輔助工具" id="receiveDate_datepicker" src="images/calendar2.gif" />
<script language="javascript">
		Calendar.setup({
			inputField : "receiveDate_form",						
		    button : "receiveDate_datepicker"
		});
</script>					
					
					</TD>
					<TD class="queryTableCellBKColor"  align="left">工單產生日期</TD>
					<TD class="queryTableCellBKColor" align="left" colspan="3"><INPUT id="createDate_form" maxLength="15" size="10" name="createDate_form">
<img border="0" alt="日期選擇輔助工具" id="createDate_datepicker" src="images/calendar2.gif" />
<script language="javascript">
		Calendar.setup({
			inputField : "createDate_form",						
		    button : "createDate_datepicker"
		});
</script>					
					</TD>
					
					
				</TR>		
				<TR >
					<TD class="queryTableCellBKColor"  align="left" >工作種類</TD>
					<TD class="queryTableCellBKColor"  align="left" >
<SELECT name="jobCodeType" id="jobCodeType" size="1"  >
<option value="" >全部</option>
<% for (int i=0; i<jobcodeTypeList.size(); i++) {
	Code _code = (Code)jobcodeTypeList.get(i);
%>					 
							<option value="<%=_code.getId() %>"  >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT> 					
					
					</TD>
					<TD class="queryTableCellBKColor"  align="left">Deadline</TD>
					<TD class="queryTableCellBKColor"  align="left">
<SELECT name="deadTime" id="deadTime" size="1" >
<option value="" >全部</option>
<% for (int i=0; i<deadlineList.size(); i++) {
	Code _code = (Code)deadlineList.get(i);

%>					 
							<option value="<%=_code.getCodeValueTw() %>"  >
								<%=_code.getCodeValueTw() %>Hr
							</option>
<% }%>	
</SELECT>					
					</TD>
					<TD class="queryTableCellBKColor"  align="left" >AFP檔名</TD>
					<TD class="queryTableCellBKColor"  align="left" ><INPUT id="afpName"  maxLength="20" size="10" name="afpName" ></TD>
				</TR>						
				<TR >
					<TD class="queryTableCellBKColor"  align="left" rowspan="2">工單進度狀態</TD>
					<TD class="queryTableCellBKColor"  align="left" rowspan="2">
						<table border="0" width="100%" id="table81">
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="INIT" checked >工單產生</td>
							<td><input type="checkbox" name="jobBagStatuses" value="PRINTED_LP">已列印工單</td>
							</tr>
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="COMPLETED_LP">列印已完成</td>
							<td><input type="checkbox" name="jobBagStatuses" value="NON_LP">不需列印</td>
							</tr>
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="COMPLETED_MP">裝封已完成</td>
							<td><input type="checkbox" name="jobBagStatuses" value="NON_MP">不需裝封</td>
							</tr>
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="PRINTED_LG_FORM">已列印郵資單(管制表)</td>
							<td><input type="checkbox" name="jobBagStatuses" value="COMPLETED_LG">交寄已完成</td>
							</tr>			
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="ACCOUNTING_LOCKED">會計立帳</td>
							<td><input type="checkbox" name="jobBagStatuses" value="ACCT_DN_GENERATED">產生DebitNote</td>
							</tr>
							<tr>														
							<td><input type="checkbox" name="jobBagStatuses" value="ACCOUNTING_EP1">拋轉至EP1</td>
							<td><input type="checkbox" id="checkAll"  onclick="checkAllBox()">全選</td>
							</tr>	

						</table>

					</TD>

					<TD class="queryTableCellBKColor"  align="left" ></TD>
					<TD class="queryTableCellBKColor"  align="left" colspan="3">
					    <div style="display:none">
							刪除<SELECT id="isDeleted_form" size=1 name="isDeleted_form"> 
							<option value="">全部</option>
							<option value="1"  >是</option>
							<option value="0" selected>否</option>
						</SELECT>
						</div>
						損毀	<SELECT id="isDamage_form" size=1 name="isDamage_form"> 
							<option value="" selected>全部</option>
							<option value="1"  >是</option>
							<option value="0" >否</option>
						</SELECT>					
						有檔案<SELECT id="withFile" size=1 name="withFile"> 
							<option value="" >全部</option>
							<option value="1" >是</option>
							<option value="0" >否</option>
						</SELECT>	
					</TD>

				
				</TR>

				<TR >
					<TD class="queryTableCellBKColor"  align="left">每頁筆數</TD>
					<TD class="queryTableCellBKColor"  align="left" colspan="3">
						<SELECT id="pageSize" size=1 name="pageSize"> 
							<option value="30" selected >30</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
							<option value="500">500</option>
						</SELECT></TD>	
				</TR>
		
			</TABLE>
            </td>
          </tr>
		 <tr>
	        <td height="30">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" align="center" id="table7">			
				<TR>
					<TD align="center"><input name="button" type="submit"  id="button" value="搜尋" /></TD>

				</TR>
			</TABLE>
            </td>
          </tr>
          <input type="hidden" name="includeParams_value" value="custNo,createDate_form,cycleDate_form,jobBagNo,receiveDate_form,jobCodeType,deadTime,jobBagStatuses,isDeleted_form,isDamage_form,withFile,pageSize,afpName">
            </form>
	      <tr>
	        <td>
	        
			<form action="jobbagOP.do" method="post" name="myform">
			   <div class="jqmWindow" id="dialog">
                   <a href="#" class="jqmClose">Close</a>
                   <hr />
                   <div id="updateMessage"></div>
               </div>
			   
			   <div class="jqmWindow2" id="lpDialog" align="left">
                  <a href="#" class="jqmClose">Close</a>
                     <hr>
                                                        選擇列印機台：
                       <SELECT name="lpMachineName" id="lpMachineName" size="1"   >            
                         <% 
                            for (int i=0; i<machineLPList.size(); i++) {
	                           Code _code = (Code)machineLPList.get(i); %>					 
							   <option value="<%=_code.getCodeValueTw() %>" >
								   <%=_code.getCodeValueTw() %>
							   </option>
                         <% }%>	
                      </SELECT>
                      <input name="button" type="button" onclick="return batchCompletedLPSumbit();"  id="lpbutton" value="確認機台" />
               </div>          
               <div class="jqmWindow2" id="mpDialog" align="left" class="jqDrag">
                  <a href="#" class="jqmClose">Close</a>
                     <hr>
                                                        選擇裝封機台：
                     <SELECT name="mpMachineName" id="mpMachineName" size="1" >
                        <% for (int i=0; i<machineMPList.size(); i++) {
	                         Code _code = (Code)machineMPList.get(i);
                        %>					 
							<option value="<%=_code.getCodeValueTw() %>" >
								<%=_code.getCodeValueTw() %>
							</option>
                        <% }%>	
                     </SELECT>									
                   <input name="button" type="button" onclick="return batchCompletedMPSumbit();"  id="mpbutton" value="確認機台" />
               </div>
			
			
			<input type="hidden" name="includeParams_value" value="custNo,createDate_form,cycleDate_form,jobBagNo,receiveDate_form,jobCodeType,deadTime,jobBagStatuses,isDeleted_form,isDamage_form,withFile,pageSize"> 
			<input type="hidden" name="fid" value="batchPrintSumbit">	        
	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title=""    attributes="width=\"2%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="工單代號"      property="job_bag_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="工單種類"      property="job_code_type"  sortable="desc" attributes="width=\"20%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>	            	            
	            <vlh:column title="活動名稱"      property="prog_nm"  sortable="desc" attributes="width=\"20%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="cycle date"      property="cycle_date"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="工單進度"      property="job_bag_status"  sortable="desc" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="分檔批次"      property="splite_count"  sortable="desc" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Deadline"      property="dead_time"  sortable="desc" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            	            
	            <vlh:column title="accounts"   property="accounts"  sortable="desc"  attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="pages"      property="pages"  sortable="desc" attributes="width=\"5%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="sheets"      property="sheets"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="損毀"      property="has_damage"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="周期性工單"   property="create_by_cycle"  sortable="desc"  attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="刪除"   property="is_deleted"  sortable="desc"  attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="AFP檔名"   property="afp_name"  sortable="desc"  attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td ><INPUT type="checkbox" name="jobBagNos"  id="jobBagNos${stauts.count}" value="<c:out value="${bean.job_bag_no};${stauts.index}" />" onclick="checkedLine('jobBagNos${stauts.count}', 'rowsCount${stauts.count}', '${rowStyle}')" /></td>
	            <td height="30" ><a href="jobbagOP.do?fid=view&jobBagNo=<c:out value="${bean.job_bag_no}" />&backToListURL=${backToListURL}"><c:out value="${bean.job_bag_no}" /> </a></td>
	            <td align="left" ><c:out value="${bean.job_code_type}" /></td>
	            <td align="left" ><c:out value="${bean.prog_nm}" /></td>
	            <td align="left" ><fmt:formatDate value="${bean.cycle_date}" pattern="yyyy-MM-dd" /></td>
	            <td align="left" id='${bean.job_bag_no}' ><c:out value="${bean.job_bag_status}" /></td>
	            <td align="center" ><INPUT id="spliteCounts${bean.job_bag_no}"  maxlength="3" size="2" name="spliteCounts" value="<c:out value="${bean.splite_count}" />"> </td>
	            <td align="center" ><c:out value="${bean.dead_time}" />Hr</td>
	            <td align="right" ><c:out value="${bean.accounts}" /></td>
	            <td align="right" ><c:out value="${bean.pages}" /></td>
	            <td align="right" ><c:out value="${bean.sheets}" /></td>
	            <td align="center" ><c:out value="${bean.is_damage}" /></td>
	            <td align="center" ><c:out value="${bean.create_by_cycle}" /></td>
	            <td align="center" ><c:out value="${bean.is_deleted}" /></td>
	            <td align="left" ><c:out value="${bean.afp_name}" /></td>
	            <td >
	                <c:if test="${bean.job_bag_status=='工單產生' || bean.job_bag_status == '不需列印'}" >
	                   <a href="javascript:confirmDelete('jobbagOP.do?fid=deleteJobbagInit&jobBagNo=<c:out value="${bean.job_bag_no}"/>&backToListURL=${backToListURL}')" id="delete${bean.job_bag_no}"><img src="images/icon_delete.png" width="41" height="20" alt="delete" border="0"/></a>
	                </c:if>
	            </td>
              </tr>
              </c:forEach>
	          
	          </table>
	           
	          </td>
          </tr>
	      <tr>
	        <td>&nbsp;</td>
          </tr>
	      <tr>
	        <td>
	        
	        
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td height="30" style="text-align:center;">總共 <span class="red12"><c:choose>
                        <c:when test="${dataList.valueListInfo.totalNumberOfEntries}==0">
                          0
                        </c:when>
                        <c:otherwise>
                          <c:out value="${dataList.valueListInfo.totalNumberOfEntries}" />
                        </c:otherwise>
                      </c:choose></span> 筆, [ <c:out value="${dataList.valueListInfo.pagingPage}" />
                      /
                      <c:out value="${dataList.valueListInfo.totalNumberOfPages}" />
                      	] 頁</td>
              </tr>
	          <tr>
	            <td height="30" style="text-align:center;">
	            <div align="center">
	            <vlh:paging pages="5">
                        <c:out value="${page}" />
                        &nbsp;
                </vlh:paging>
                </div>
	            </td>
              </tr>
            </table>
            </td>
          </tr>
          <tr><td align="center"><input name="selectAll" type="button"  id="selectAll" value="全選" /> 
          | <input name="selectNon" type="button"  id="selectNon" value="清除" /> 
          | <input name="button" type="submit"  id="button" value="列印+批次分檔" />
          <div style="display: none">| <input name="button" type="button" onclick="printpdf();" id="print" value="列印" /></div> 
          | <input name="button" type="button" onclick="lpDialogShow()"   value="批次記錄列印作業完成" /> 
          | <input name="button" type="button" onclick="mpDialogShow()"   value="批次記錄裝封作業完成" /> 
          
          </td></tr>
          </form>
        </table></vlh:root>
        
</div>