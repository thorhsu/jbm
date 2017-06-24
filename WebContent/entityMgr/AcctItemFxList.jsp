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

<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
</script>


<div align="center">
	    <div class="row">
	      <div class="col-md-12">
                <div class="panel-success">
                    <div class="panel-heading">
                        <h3 class="panel-title">物料／計價標準查詢</h3>
                    </div>
               </div>
         </div>
          <html:form action="/acctItemFx" method="get" >
	      <div class="row query-grid">	        
	         <input type="hidden" name="includeParams_value" value="pageSize,name,custNo,isMaterialStr" />
	         <input type="hidden" name="fid" value="list">
		     <div class="col-md-2  text-center">客戶</div>
			 <div class="col-md-4 text-left">
				 <select id="custNo" name="custNo" size="1" class="required">
				    <option value="" >全部</option>
				    <option value="all" >共用</option>               	    
                 	<c:forEach items="${acctItemFxForm.allCustomers}" var="customer">
                 	   <option value="${customer.custNo}" >${customer.easyName}</option>
                 	</c:forEach>
                 </select>
			 </div>
			 <div class="col-md-2  text-center">計價名稱</div>
			 <div class="col-md-4 text-left">
			 	<input id="name" name="name" >
			 </div>
		  </div>
		  <div class="row query-grid">
			 <div class="col-md-2  text-center">物料/非物料</div>
			 <div class="col-md-4 text-left">
					<select id="isMaterialStr" size=1 name="isMaterialStr"> 
						<option value="" >全部</option>
						<option value="1" >物料</option>
						<option value="0" >非物料</option>
				    </select>
			 </div>
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
					<td><input name="button" type="button"class="btn btn-success" onclick="location='acctItemFx.do?fid=addInit&backToListURL=${backToListURL}'"  id="button" value="新增" /></td>
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
	               <vlh:column title="id "  property="id"  sortable="asc" attributes="class='success'"/>
	               <vlh:column title="客戶"      property="custNo"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="名稱"      property="name"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="成本"  property="cost"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="會計計價"       property="acct_price"  sortable="desc" attributes="class='success'"/>	               
	               <vlh:column title="單位"      property="unitName"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="是否為物料"      property="isMaterial"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="版號"      property="printNo"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title="重量"      property="weight"  sortable="desc" attributes="class='success'"/>
	               <vlh:column title=""   attributes="class='success'"/>
	               <vlh:column title=""   attributes="class='success'"  />	            
	           </vlh:header>
            
              
			 <c:forEach var="bean" items="${dataList}" varStatus="stauts" >
	          <tr id="rowsCount${stauts.count}" >
	            <td height="30" ><c:out value="${bean.id}" /></td>
	            <td ><c:out value="${bean.custNo}" /></td>
	            <td ><c:out value="${bean.name}" /></td>
	            <td ><c:out value="${bean.cost}" /></td>
	            <td align="left" ><c:out value="${bean.acct_price}" /></td>
	            <td align="left" ><c:out value="${bean.unitName}" /></td>
	            <td align="left" ><c:out value="${bean.isMaterial}" /></td>
	            <td align="left" ><c:out value="${bean.printNo}" /></td>
	            <td align="right" ><c:out value="${bean.weight}" /></td>
	            <td ><a href="acctItemFx.do?fid=editInit&id=<c:out value="${bean.id}" />&idList=${idList}&backToListURL=${backToListURL}"><img src="${pageContext.request.contextPath}/images/icon_edit.png" width="43" height="21" alt="edit" border="0"/></a></td>
	            <td ><a href="javascript:confirmDelete('acctItemFx.do?fid=delete&id=<c:out value="${bean.id}"/>&backToListURL=${backToListURL}')"><img src="${pageContext.request.contextPath}/images/icon_delete.png" width="41" height="20" alt="edit" border="0"/></a></td>
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