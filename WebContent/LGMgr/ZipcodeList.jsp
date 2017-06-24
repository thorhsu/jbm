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
	List lgMailToPostofficeList = codeService.findBycodeTypeName("LG_MAIL_TO_POSTOFFICE"); //交寄郵局或公司
%>

<script src="js/lib/jquery.jqModal.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.dimensions.js" type="text/javascript"></script>
<script type="text/javascript">


// 判斷是否有選擇產出資料
function isChecked(){
	if($("input[name='zipcodeInfoNos']:checked").length === 0){
		  alert("請選擇產出資料!");
		  return false;
	  }
	return true;
}

// 產生3+2檔案
function generateFileSumbit() {
	
	if(!isChecked()){
		return false;
	}
	
	$("#generator_fid").val("generateFile");
	document.myform.submit();
	
	return true;
}

// 產生合併3+2檔案
function generateCombinedFileSumbit() {
	
	if(!isChecked()){
		return false;
	}
	
	$("#generator_fid").val("generateCombinedFile");
  	document.myform.submit();
  	return true;
}

// 工單狀態全選check box
function checkAllBox(){
    var ischecked = $('#checkAll').attr("checked");
    if(ischecked){
       $(':checkbox[name="jobBagStatuses"]').attr("checked", true);
    }else{
       $(':checkbox[name="jobBagStatuses"]').attr("checked", false);
    }
}

function nonLPCheck(){
    var ischecked = $(':checkbox[value="INIT"]').attr("checked");
    if(ischecked){
       $(':checkbox[value="NON_LP"]').attr("checked", true);
    }
    
}
</script>


<script type="text/javascript"> 

<%-- 記錄搜尋項目 --%>	
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>

$().ready(function() {
	
    var rowsCount = "rowsCount";
	selectAllJobBag = function (){
	        var i = 0;
			$("input[name='zipcodeInfoNos']").each(function() { 
			    i++;
				$(this).attr("checked", true);
				var lineid = document.getElementById(rowsCount + i);
				$(lineid).addClass("checkedLine");
			}); 
  	};
  	$('#selectAll').click(selectAllJobBag);
  	
	selectNoJobBag = function (){
	        var i = 0; 
			$("input[name='zipcodeInfoNos']").each(function() {
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
	        <td height="26" class="title">郵件3+2郵遞區號資料查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="zipcode.do" method="post" >
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
					<TD class="queryTableCellBKColor" width="9%" align="left" >zip code檔案名稱</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="zipcodeFilename"  maxLength="30" size="26" name="zipcodeFilename" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">郵件種類</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" >				
						時限
						<SELECT id="dispatchTime_form" size=1 name="dispatchTime_form"> 
							<option value="" selected>全部</option>
							<option value="0" >普通</option>
							<option value="1" >限時</option>
						</SELECT>
							<table border="0" width="100%" id="table81">
							<tr>
							<td><input type="checkbox" name="dispatchKind_form" value="1">信函</td>
							<td><input type="checkbox" name="dispatchKind_form" value="3">雜誌</td>
							</tr>
							<tr>
							<td><input type="checkbox" name="dispatchKind_form" value="4">新聞紙</td>
							<td><input type="checkbox" name="dispatchKind_form" value="5">印刷物</td>
							</tr>
							<tr>
							<td><input type="checkbox" name="dispatchKind_form" value="6">明信片</td>
							<td><input type="checkbox" name="dispatchKind_form" value="7">郵簡</td>
							</tr>
						</table>
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
					<TR>
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
						是否已產生檔案
						<SELECT id="isCreatedFile" size=1 name="isCreatedFile"> 
							<option value="" selected>全部</option>
							<option value="1" >是</option>
							<option value="0" >否</option>
						</SELECT>	
						&nbsp;&nbsp;&nbsp;&nbsp;
						交寄筆數
						<INPUT id="zipcodeTotal_form"  maxLength="6" size="4" name="zipcodeTotal_form" > 以上				
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
          	<input type="hidden" name="includeParams_value" value="custNo,cycleDate_form,jobBagNo,receiveDate_form,zipcodeFilename,dispatchTime_form,dispatchKind_form,jobBagStatuses,codeMailToPostoffice,pageSize,isCreatedFile,zipcodeTotal_form">
            </form>
	      <tr>
	        <td>
			<form action="zipcode.do" method="post" name="myform">
			<input type="hidden" name="includeParams_value" value="custNo,cycleDate_form,jobBagNo,receiveDate_form,zipcodeFilename,dispatchTime_form,dispatchKind_form,jobBagStatuses,codeMailToPostoffice,pageSize,isCreatedFile,zipcodeTotal_form">
			<input id="generator_fid" type="hidden" name="fid" value="">
			
	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         
			<tr >
	            <vlh:header>
	            <vlh:column title=""    attributes="width=\"2%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="項次"      property="zipcode_info_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="客戶代號"      property="cust_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>    
	            <vlh:column title="工單代號"      property="job_bag_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="活動名稱"      property="prog_nm"  sortable="desc" attributes="width=\"12%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="工單進度"      property="job_bag_status"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>    
	            <vlh:column title="cycle date"      property="cycle_date"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="轉檔(收檔)日期"    property="receive_date"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄筆數"      property="zipcode_total"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="郵件種類"      property="dispatch_time"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="交寄郵局或公司"      property="code_mail_to_postoffice"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="檔案產生日期"      property="update_date"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="檔案名稱"      property="zipcode_filename"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""    attributes="width=\"4%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td ><INPUT type="checkbox" name="zipcodeInfoNos"  id="zipcodeInfoNos${stauts.count}" value="<c:out value="${bean.zipcode_info_no}" />" onclick="checkedLine('zipcodeInfoNos${stauts.count}', 'rowsCount${stauts.count}', '${rowStyle}')" /></td>
	            <td align="left" ><c:out value="${bean.zipcode_info_no}" /></td>
	            <td ><a href="customer.do?fid=view&custNo=<c:out value="${bean.cust_no}" />&backToListURL=${backToListURL}"><c:out value="${bean.cust_no}" /> </a></td>
	            <td height="30" ><a href="jobbagLG.do?fid=view&jobBagNo=<c:out value="${bean.job_bag_no}" />&backToListURL=${backToListURL}"><c:out value="${bean.job_bag_no}" /> </a></td>
	            <td align="left" ><c:out value="${bean.prog_nm}" /></td>
	            <td align="left" ><c:out value="${bean.job_bag_status}" /></td>
	            <td align="left" ><fmt:formatDate value="${bean.cycle_date}" pattern="yyyy-MM-dd" /></td>
	            <td align="left" ><fmt:formatDate value="${bean.receive_date}" pattern="yyyy-MM-dd" /></td>
	            <td align="left" ><c:out value="${bean.zipcode_total}" /></td>
	            <td align="left" >
	            	<c:if test="${bean.dispatch_time == '0'}">
	            	普通
	            	</c:if>
	            	<c:if test="${bean.dispatch_time == '1'}">
	            	限時
	            	</c:if>
	            	<c:if test="${bean.dispatch_kind == '1'}">
	            	信函
	            	</c:if>
	            	<c:if test="${bean.dispatch_kind == '3'}">
	            	雜誌
	            	</c:if>
	            	<c:if test="${bean.dispatch_kind == '4'}">
	            	新聞紙
	            	</c:if>
	            	<c:if test="${bean.dispatch_kind == '5'}">
	            	印刷物
	            	</c:if>
	            	<c:if test="${bean.dispatch_kind == '6'}">
	            	明信片
	            	</c:if>
	            	<c:if test="${bean.dispatch_kind == '7'}">
	            	郵簡
	            	</c:if>
	            </td>
	            <td align="left" ><c:out value="${bean.mail_to_postoffice}" /></td>
	            <td align="left" ><fmt:formatDate value="${bean.update_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>           
	            <td align="left" >
		            <c:out value="${bean.zipcode_filename}" />
	            </td>     
	            <td>
		            <c:if test="${bean.zipcode_filename != null}">
		            	<a class="download" href="pdf/<c:out value="${bean.zipcode_filename}" />"  target="new" download>下載檔案</a>
		            </c:if>
	            </td>
              </tr>
              </c:forEach>
	          
	          </table>
	           </form>
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
          | <input name="batchGenerateLGForm" type="button" onclick="return generateFileSumbit();"  id="button" value="產生郵遞區號檔案" /> 
          | <input name="batchCompleted" type="button" onclick="return generateCombinedFileSumbit();"  id="button" value="合併產出郵遞區號檔案" /> </td></tr>
        </table></vlh:root>
</div>