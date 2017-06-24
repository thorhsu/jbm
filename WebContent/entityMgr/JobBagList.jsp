<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh" uri="http://valuelist.sourceforge.net/tags-valuelist" %>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	// List accountList = (List)request.getAttribute("dataList");
%>
<script src="js/lib/jquery.jqModal.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.dimensions.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
	submitFinished();  
    document.location = delUrl;
  }
}
//-->
// Thor add
<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
//Thor

$().ready(function() {
	$('#dialog').jqm().jqDrag();
	
	var timer = 
		$.timer(
			function() {
				$.getJSON('jobbag.do?fid=updateJobBagStatus', 
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
				        		   if(data[i].jobBagStatus != "工單產生" && data[i].jobBagStatus != "不需列印"){
 				        			   $("#delete" + data[i].jobBagNo).hide();
 				        			   $("#edit" + data[i].jobBagNo).hide();
				        		   }
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
});

</script>


<div align="center">
	    <table width="95%" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td height="26" class="title">工單查詢</td>
          </tr>
	      <tr>
	        <td height="15">&nbsp;</td>
          </tr>
          <form action="jobbag.do" method="get">
          <div align="left" class="jqmWindow" id="dialog">
            <a href="#"  class="jqmClose">Close</a>
            <hr />
            <div align="left" id="updateMessage"></div>
          </div>
	      <tr>
	        <td height="30">
	        
	        <input type="hidden" name="fid" value="list">
			<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" class="queryTableBKColor" align="left" id="table7">
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" >客戶代號</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="custNo"  maxLength="3" size="10" name="custNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">Cycle Date</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="cycleDate_form" maxLength="15" size="10" name="cycleDate_form" readonly="readonly" />
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
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="jobBagNo"  maxLength="15" size="13" name="jobBagNo"   /></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left">轉檔日期<BR>(收檔日期)</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="receiveDate_form" maxLength="15" size="10" name="receiveDate_form" readonly="readonly" />
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
					<TD class="queryTableCellBKColor" width="9%" align="left" > 工作樣本代號</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" ><INPUT id="jobCodeNo"  maxLength="6" size="10" name="jobCodeNo" ></TD>
					<TD class="queryTableCellBKColor" width="10%" align="left"> AFP檔名 </TD>
					<TD class="queryTableCellBKColor" width="17%" align="left" ><INPUT id="afpName"  maxLength="20" size="10" name="afpName" ></TD>
				</TR>
				<TR >
					<TD class="queryTableCellBKColor" width="9%" align="left" rowspan="2">工單進度狀態</TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" rowspan="2">
						<table border="0" width="100%" id="table81">
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="INIT" >工單產生</td>
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
							<td><input type="checkbox" name="jobBagStatuses" value="PRINTED_LG_FORM"> 已列印郵資單</td>
							<td><input type="checkbox" name="jobBagStatuses" value="COMPLETED_LG">交寄已完成</td>							
							</tr>
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="ACCOUNTING_LOCKED">會計立帳</td>
							<td><input type="checkbox" name="jobBagStatuses" value="ACCT_DN_GENERATED">產生DebitNote</td>
							</tr>
							<tr>
							<td><input type="checkbox" name="jobBagStatuses" value="ACCOUNTING_EP1">拋轉至EP1</td>
							<td></td>
							</tr>	
						</table>
					</TD>

					<TD class="queryTableCellBKColor" width="9%" align="left" ></TD>
					<TD class="queryTableCellBKColor" width="12%" align="left" >
							刪除<SELECT id="isDeleted_form" size=1 name="isDeleted_form"> 
							<option value="">全部</option>
							<option value="1"  >是</option>
							<option value="0" selected>否</option>
						</SELECT>
						&nbsp;&nbsp;
						損毀	<SELECT id="isDamage_form" size=1 name="isDamage_form"> 
							<option value="" selected>全部</option>
							<option value="1"  >是</option>
							<option value="0" >否</option>
						</SELECT>	
						&nbsp;&nbsp;				
						需客戶確認<SELECT id="isCustConfirm" size=1 name="isCustConfirm"> 
							<option value="" selected>全部</option>
							<option value="1"  >是</option>
							<option value="0" >否</option>
						</SELECT>					
					</TD>

				
				</TR>

				<TR >
					<TD class="queryTableCellBKColor" width="10%" align="left">每頁筆數</TD>
					<TD class="queryTableCellBKColor" width="17%" align="left">
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
					<TD></TD>
				</TR>
			</TABLE>
            </td>
          </tr>
          <input type="hidden" name="includeParams_value" value="custNo,cycleDate_form,jobBagNo,receiveDate_form,jobCodeNo,jobBagStatuses,isDeleted_form,isDamage_form,isCustConfirm,pageSize,afpName">
            </form>
	      <tr>
	        <td>
	        

	        <table border="0" width="100%" id="table9" class="resultTableBKColor"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">
			<tr >
	            <vlh:header>
	            <vlh:column title="工單代號"      property="job_bag_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>	            
	            <vlh:column title="客戶代號"      property="idf_cust_no"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="活動名稱"      property="prog_nm"  sortable="desc" attributes="width=\"20%\" height=\"30\" class=\"resultTableHeaderBKColor\"  style=\"text-align:center;\""/>
	            <vlh:column title="cycle date"      property="cycle_date"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            <vlh:column title="轉檔日期"      property="receive_date"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="客戶確認日"      property="custConfirmDate"  sortable="desc" attributes="width=\"10%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="工單進度"      property="job_bag_status"  sortable="desc" attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="accounts"   property="accounts"  sortable="desc"  attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="pages"      property="pages"  sortable="desc" attributes="width=\"5%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="sheets"      property="sheets"  sortable="desc" attributes="width=\"6%\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="Damage轉換"      property="has_damage"  sortable="desc" attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title="AFP檔名"   property="afp_name"  sortable="desc"  attributes="width=\"6%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	
	            <vlh:column title=""        attributes="width=\"5%\" height=\"30\" class=\"resultTableHeaderBKColor\" style=\"text-align:center;\""/>	            
	            </vlh:header>
              </tr>
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >

	            <td height="30" ><a href="jobbag.do?fid=view&jobBagNo=<c:out value="${bean.job_bag_no}" />&backToListURL=${backToListURL}"><c:out value="${bean.job_bag_no}" /> </a></td>
	            <td ><a href="customer.do?fid=view&custNo=<c:out value="${bean.idf_cust_no}" />&backToListURL=${backToListURL}"><c:out value="${bean.idf_cust_no}" /> </a></td>
	            <td align="left" ><c:out value="${bean.prog_nm}" /></td>
	            <td ><fmt:formatDate value="${bean.cycle_date}" pattern="yyyy-MM-dd" /></td>
	            <td ><fmt:formatDate value="${bean.receive_date}" pattern="yyyy-MM-dd" /></td>
	            <td ><fmt:formatDate value="${bean.cust_confirm_date}" pattern="yyyy-MM-dd" /></td>
	            <td id='${bean.job_bag_no}'><c:out value="${bean.job_bag_status}" /></td>
	            <td ><c:out value="${bean.accounts}" /></td>
	            <td ><c:out value="${bean.pages}" /></td>
	            <td ><c:out value="${bean.sheets}" /></td>
	            <td ><c:out value="${(bean.from_damage or bean.is_damage)? 'true' : ''}" /></td>
	            <td ><c:out value="${bean.afp_name}" /></td>
	            <td >
	               <c:if test="${bean.job_bag_status == '工單產生' || bean.job_bag_status == '不需列印'}" >  
	                  <a href="jobbag.do?fid=editInit&jobBagNo=<c:out value="${bean.job_bag_no}" />&backToListURL=${backToListURL}" id="edit${bean.job_bag_no}"><img src="images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a>
	               </c:if>	               
	           </td>
	            <td >
	               <c:if test="${bean.job_bag_status=='工單產生' || bean.job_bag_status == '不需列印'}" >
	                  <a href="javascript:confirmDelete('jobbagOP.do?fid=deleteJobbagInit&jobBagNo=<c:out value="${bean.job_bag_no}"/>&backToListURL=${backToListURL}')" id="delete${bean.job_bag_no}"><img src="images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a>
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
        </table></vlh:root>
</div>