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
	List lgTypeList = codeService.findBycodeTypeName("LG_TYPE"); //郵資單總類
	List lgMailCategory = codeService.findBycodeTypeName("LG_MAIL_CATEGORY"); //交寄方式
	List lgMailToPostofficeList = codeService.findBycodeTypeName("LG_MAIL_TO_POSTOFFICE"); //交寄郵局或公司
%>
<script src="js/lib/jquery.jqModal.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.dimensions.js" type="text/javascript"></script>
<script type="text/javascript">

function batchCompletedSumbit() {
  document.myform.fid.value ="batchCompletedSumbit";
  document.myform.submit();
  return true;
}


function batchGenerateLGFormSumbit() {
  document.myform.fid.value ="batchGenerateLGFormSumbit";
  document.myform.submit();
  return true;
}

function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}

//thor add 勾選連動
function nonLPCheck(){
    var ischecked = $(':checkbox[value="INIT"]').attr("checked");
    if(ischecked){
       $(':checkbox[value="NON_LP"]').attr("checked", true);
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
	$('#dialog').jqm().jqDrag();
	
	var timer = 
		$.timer(
			function() {
				$.getJSON('jobbagLG.do?fid=updateJobBagStatus', 
				        {idList: "${idList}"},
				        function(data) {
				        	var lineCounter = 0;				        	
				        	$.each(data, function(i){
				        		
				        		var jobBagStatusId = document.getElementById(data[i].jobBagNo);
				        		var oriText = $(jobBagStatusId).text();
				        		if(oriText != data[i].jobBagStatus){
				        			if(lineCounter == 0)
					        			$('#updateMessage').text("");
					        		lineCounter ++;
				        			var dialogTxt = $('#updateMessage').html() + data[i].jobBagNo + " 工單進度變為：" + data[i].jobBagStatus + "<br />";
				        			$('#updateMessage').html(dialogTxt);
				        		   $(jobBagStatusId).text(data[i].jobBagStatus);
				        		   $('#dialog').jqmShow();				        		   
				        		   $(jobBagStatusId).addClass("mouseOver");
				        		   setTimeout ( function() {$(jobBagStatusId).removeClass("mouseOver");}, 1000 );
				        	    }				        		
				        	 });				  
				        }
				 );
				
			},
			50000,
			true
		);		
	
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


</script>

<div class="jqmWindow" align="left" id="dialog">
     <a href="#" class="jqmClose">Close</a>
     <hr />
     <div align="left" id="updateMessage"></div>
</div>

<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">郵資單與交寄管制表資料查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="jobbagLG.do" method="post" >
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align="left" id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >客戶代號</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="custNo"  maxLength="3" size="10" name="custNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">Cycle Date</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="cycleDate_form" maxLength="15" size="10" name="cycleDate_form">
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
					<TD class="queryTableCellBKColor" width="9%" align="left" >工單代號</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="jobBagNo"  maxLength="15" size="13" name="jobBagNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">轉檔日期<BR>(收檔日期)</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="receiveDate_form" maxLength="15" size="10" name="receiveDate_form"  />
<img border="0" alt="日期選擇輔助工具" id="receiveDate_datepicker" src="images/calendar2.gif" />
<script language="javascript">
		Calendar.setup({
			inputField : "receiveDate_form",						
		    button : "receiveDate_datepicker"
		});
</script>					
					
					</TD>
				</TR>		
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >郵資單總類</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" >
<SELECT name="codeLgType" id="codeLgType" size="1"  >
<option value="" >全部</option>
<% for (int i=0; i<lgTypeList.size(); i++) {
	Code _code = (Code)lgTypeList.get(i);
%>					 
							<option value="<%=_code.getId() %>"  >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT> 					
					
					</TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">交寄方式</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >
<SELECT name="codeMailCategory" id="codeMailCategory" size="1" >
<option value="" >全部</option>
<% for (int i=0; i<lgMailCategory.size(); i++) {
	Code _code = (Code)lgMailCategory.get(i); 

%>					 
							<option value="<%=_code.getId() %>"  >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>					
					</TD>
				</TR>						
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" rowspan="2">工單進度狀態</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" rowspan="2">
						<table border="0" width="100%" id="table81">
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="INIT" onclick="nonLPCheck()">工單產生</td>
							<td><input type="checkbox" name="jobBagStatuses" value="PRINTED_LP">已列印工單</td>
							</tr>
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="COMPLETED_LP">列印已完成</td>
							<td><input type="checkbox" name="jobBagStatuses" value="NON_LP">不需列印</td>
							</tr>
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="COMPLETED_MP" checked>裝封已完成</td>
							<td><input type="checkbox" name="jobBagStatuses" value="NON_MP" checked>不需裝封</td>
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

					<TD class="queryTableCellBKColor" width="9%" align="left" >交寄郵局或公司</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" >
<SELECT name="codeMailToPostoffice" id="codeMailToPostoffice" size="1" >
<option value="" >全部</option>
<% for (int i=0; i<lgMailToPostofficeList.size(); i++) {
	Code _code = (Code)lgMailToPostofficeList.get(i);

%>					 
							<option value="<%=_code.getId() %>"  >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
					</TD>

				
				</TR>

				<TR >
					<TD class="queryTableCellBKColor" align="left" colspan="2">
					          每頁筆數
						<SELECT id="pageSize" size=1 name="pageSize"> 
							<option value="30" selected >30</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
							<option value="500">500</option>
						</SELECT>
						&nbsp;&nbsp;&nbsp;&nbsp;
						損毀
						<SELECT id="isDamage_form" size=1 name="isDamage_form"> 
							<option value="" >全部</option>
							<option value="1" >是</option>
							<option value="0" selected>否</option>
						</SELECT>					
						
						
				   </TD>	
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
          <!-- thor 修改 -->
          <input type="hidden" name="includeParams_value" value="custNo,cycleDate_form,jobBagNo,receiveDate_form,codeLgType,codeMailCategory,jobBagStatuses,codeMailToPostoffice,pageSize,isDamage_form">
            </form>
	      <tr>
	        <td>
			<form action="jobbagLG.do" method="post" name="myform">
			<input type="hidden" name="includeParams_value" value="custNo,cycleDate_form,jobBagNo,receiveDate_form,codeLgType,codeMailCategory,jobBagStatuses,codeMailToPostoffice,pageSize,isDamage_form">
            
			<input type="hidden" name="fid" value="">
	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title=""    attributes="width=\"2%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="工單代號"      property="job_bag_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>            	            
	            <vlh:column title="活動名稱"      property="prog_nm"  sortable="desc" attributes="width=\"15%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="cycle date"      property="cycle_date"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄期限"      property="dead_line"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="工單進度"      property="job_bag_status"  sortable="desc" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="郵資單種類"      property="lg_type"  sortable="desc" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄方式"      property="mail_cateogry" sortable="desc"   attributes="width=\"5%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>            	            
	            <vlh:column title="accounts"   property="accounts"  sortable="desc"  attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄郵局或公司"      property="mail_to_postoffice"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄完成日"    property="completed_date"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="備註"   property="is_deleted"  sortable="desc"  attributes="width=\"15%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	                        
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td ><INPUT type="checkbox" name="jobBagNos"  id="jobBagNos${stauts.count}" value="<c:out value="${bean.job_bag_no};${stauts.index}" />" onclick="checkedLine('jobBagNos${stauts.count}', 'rowsCount${stauts.count}', '${rowStyle}')" /></td>
	            <td height="30" ><a href="jobbagLG.do?fid=view&jobBagNo=<c:out value="${bean.job_bag_no}" />&backToListURL=${backToListURL}"><c:out value="${bean.job_bag_no}" /> </a></td>
	            <td align="left" ><c:out value="${bean.prog_nm}" /></td>
	            <td align="left" ><fmt:formatDate value="${bean.cycle_date}" pattern="yyyy-MM-dd" /></td>
	            <td align="left" ><fmt:formatDate value="${bean.dead_line}" pattern="yyyy-MM-dd" /></td>	            
	            <td align="left" id='${bean.job_bag_no}' ><c:out value="${bean.job_bag_status}" /></td>
	            <td align="left" ><c:out value="${bean.lg_type}" /></td>
	            <td align="left" ><c:out value="${bean.mail_cateogry}" /></td>
	            <td align="right" ><c:out value="${bean.accounts}" /></td>
	            <td align="left" ><c:out value="${bean.mail_to_postoffice}" /></td>
	            <td align="left" ><fmt:formatDate value="${bean.completed_date}" pattern="yyyy-MM-dd" /></td>
	            <td align="left" ><c:out value="${bean.notes}" /></td>
              </tr>
              </c:forEach>
	          
	          </table>
	           
	          </td>
	          </form>
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
          | <input name="batchGenerateLGForm" type="button" onclick="return batchGenerateLGFormSumbit();"  id="button" value="產生郵資單+交寄管制表" /> 
          | <input name="batchCompleted" type="button" onclick="return batchCompletedSumbit();"  id="button" value="批次記錄完成" /> </td></tr>
        </table></vlh:root>
</div>