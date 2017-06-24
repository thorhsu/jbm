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

<script type="text/javascript">

function confirmDelete(delUrl) {
  if (confirm("確定刪除?")) {
    document.location = delUrl;
  }
}
$().ready(function() {
	var allReceivers = ${customerExtendsForm.allCustomerReceiversJson};
	
	//客戶改變時，聯絡人清單也要變
	$( "#custNo" )
	  .change(function () {		  		  
		  $("#receiverNo > option" ).remove();
		  $("#receiverNo").append($("<option ></option>").attr("value", "").text("請選擇"));		  
		  allReceivers.forEach(function(element){			  			  
			  if($( "#custNo" ).val() === element.custNo ){				  
				  $("#receiverNo").append($("<option ></option>").attr("value", element.receiverNo).text(element.receiver));				
			  }else if($("#custNo" ).val() === "" ){
				  $("#receiverNo").append($("<option ></option>").attr("value", element.receiverNo).text(element.receiver));
			  }			  
		  });		  
		  //$("#customerReceiver").val('${productBean.product.moldNo}');
	  }).change();
});

<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
</script>


<div align="center">
	    <div class="row">
	      <div class="col-md-12">
                <div class="panel-success">
                    <div class="panel-heading">
                        <h3 class="panel-title">客戶合約查詢</h3>
                    </div>
               </div>
         </div>

          <html:form action="/customerExtends" method="get" >
	      <div class="row query-grid">	        
	         <input type="hidden" name="includeParams_value" value="pageSize,contractName,custNo,contractNo,receiverNo" />
	         <input type="hidden" name="fid" value="list">
		     <div class="col-md-2  text-center">合約號碼</div>
			 <div class="col-md-4 text-left">
				 <input id="contractNo" name="contractNo" >
			 </div>
			 <div class="col-md-2  text-center">計價名稱</div>
			 <div class="col-md-4 text-left">
			 	<input id="contractName" name="contractName" >
			 </div>
		  </div>
		  <div class="row query-grid">
			 <div class="col-md-2  text-center">客戶</div>
			 <div class="col-md-4 text-left">
				<select id="custNo" name="custNo" size="1" class="required">
				    <option value="" >全部</option>               	    
                 	    <c:forEach items="${customerExtendsForm.allCustomers}" var="customer">
                 	       <option value="${customer.custNo}" >${customer.easyName}</option>
                 	    </c:forEach>
                 </select>
			 </div>
			 <div class="col-md-2  text-center">客戶聯絡人</div>
			 <div class="col-md-4 text-left">
				<select id="receiverNo"  name="receiverNo">
				    <option value="" >全部</option> 
					<c:forEach items="${customerExtendsForm.allReceivers}" var="receiver">
                 	    <option value="${receiver.receiverNo}" >${receiver.receiver}</option>
                 	</c:forEach>
				</select>
			 </div>
		  </div>
		  <div class="row query-grid">			 
			 <div class="col-md-2  text-center">每頁筆數</div>
			 <div class="col-md-4 text-left">
				<select id="pageSize" size=1 name="pageSize"> 
						<option value="30" selected >30</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
				</select>
			 </div>				
          </div>
		 <tr>
	        <td height="30">
			<table border=0 cellSpacing=1 cellPadding=6 width="100%" align="center" id="table7">			
				<tr>
					<td align="right"><input name="button" type="submit"  class="btn btn-success" id="button" value="搜尋" /></td>
					<td><input name="button" type="button"class="btn btn-success" onclick="location='customerExtends.do?fid=addInit&backToListURL=${backToListURL}'"  id="button" value="新增" /></td>
				</tr>
			</table>
            </td>
          </tr>
            </html:form>
	      <tr>
	        <td>
	        

	        <table id="table9" class="table table-bordered table-striped table-hover"  >
            <vlh:root value="dataList"  url="?" includeParameters="*">         			
	           <vlh:header>	               
	               <vlh:column title="客戶"      property="contract_no"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="合約號 碼"  property="cost"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="合約名稱"      property="contract_name"  sortable="desc" attributes="class='success'"/>	               
	               <vlh:column title="CS人員"       property="user_id"  sortable="desc" attributes="class='success'"/>	               
	               <vlh:column title="客戶窗口"      property="receiver"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="聯絡電話"      property="tel"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="合約起始日"      property="contractBegin"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="合約結束日"      property="contractEnd"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title=""   attributes="class='success'"/>
	               <vlh:column title=""   attributes="class='success'"  />	            
	           </vlh:header>
            
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td height="30" ><c:out value="${bean.idf_cust_no}" /></td>
	            <td ><c:out value="${bean.contract_no}" /></td>
	            <td ><c:out value="${bean.contract_name}" /></td>
	            <td ><c:out value="${bean.user_id}" /></td>
	            <td align="left" ><c:out value="${bean.receiver}" /></td>
	            <td align="left" ><c:out value="${bean.tel}" /></td>
	            <td align="left" ><fmt:formatDate pattern="yyyy-MM-dd" value="${bean.contractBegin}" /></td>
	            <td align="left" ><fmt:formatDate pattern="yyyy-MM-dd" value="${bean.contractEnd}" /></td>
	            <td ><a href="customerExtends.do?fid=editInit&contractNo=<c:out value="${bean.contract_no}" />&idList=${idList}&backToListURL=${backToListURL}"><img src="${pageContext.request.contextPath}/images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('customerExtends.do?contractNo=delete&id=<c:out value="${bean.contract_no}"/>&backToListURL=${backToListURL}')"><img src="${pageContext.request.contextPath}/images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
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
            </table></td>
          </tr>
          </vlh:root>
        </div>
</div>