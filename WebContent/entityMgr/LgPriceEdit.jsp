<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>

<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate({
			rules: {
				lgType: {
			      required: true
			    },
			    lgTypeName: {
				  required: true
				},
				lgMailCategory: {
				  required: true
				},
				weight: {
				   number: true,	
	 		       digits: true	
				},
				price: {
				  number: true				  
			    }			    
			}
	});
	$("#myform").submit(function(){
		$("#lgTypeCodeValueTw").val($("#lgType option:selected").text());
		$("#lgMailCategoryName").val($("#lgMailCategory option:selected").text());		
	});
	
	$("#lgType").val("${lgPrice.lgType}");
	$("#lgMailCategory").val("${lgPrice.lgMailCategory}");
});

function changeCodeTw(){
	var selectedValue = $("#lgType").val();
	if(selectedValue != ""){
		$("#lgTypeCodeValueTw").val($("#lgType option:selected").text());
		$("#lgTypeName").val($("#lgType option:selected").text());
	}
}

function changeLgMailCategoryName(){
	var selectedValue = $("#lgMailCategory").val();
	if(selectedValue != "")
		$("#lgMailCategoryName").val($("#lgMailCategory option:selected").text());
}

function addAsNew(){
	$("#id").val("0");
	$("#lgPriceId").text("");
	$("#addSubmit").hide();
}
</script>

	<form name="myform" id="myform" action="lgPrice.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />	    
		<input type="hidden" name="fid" value="editSubmit" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">郵資價格維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000">${message}</font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>id</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<span id="lgPriceId">${lgPrice.id }</span>
                 	<input type="hidden" id="id" name="id" value='<c:out value="${lgPrice.id }" default="0"/>' />
                 </TD>
                 <TD class="editTableLabelCellBKColor" width="10%" >
                     <font color="#FF0000" size="2">*</font>郵資單種類
                 </TD>
				 <TD class="editTableContentCellBKColor">
					 <select id="lgType" size=1 name="lgType" onchange="changeCodeTw()">
					    <option value="" >請選擇</option>
					    <c:forEach var="code" items="${lgTypesCode}">
					            <option value="${code.id}" >${code.codeValueTw}</option>
					     </c:forEach>
					  </select>
					  <input type="hidden" id="lgTypeCodeValueTw" name="lgTypeCodeValueTw" value=""/>
				</TD>
				<TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>郵資單顯示名稱</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                     <input id="lgTypeName" name="lgTypeName" value="${lgPrice.lgTypeName}"/>
                 </TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>交寄方式</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<select id="lgMailCategory" size=1 name="lgMailCategory" onchange="changeLgMailCategoryName()">
                 	     <option value="" >請選擇</option>
					     <c:forEach var="code" items="${lgMailCategoriesCode}" >
					          <option value="${code.id}" >${code.codeValueTw}</option>
					      </c:forEach>
					 </select>
					 <input type="hidden" id="lgMailCategoryName" name="lgMailCategoryName" value="${lgPrice.lgMailCategoryName}"/>
                 <TD class="editTableLabelCellBKColor" width="10%" >價格</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left colspan="3">
                       <input id="price" name="price" value="${lgPrice.price }" maxlength="5" />
				 </TD>
			</TR>

			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" >重量</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
					<input id="weight" name="weight" value="${lgPrice.weight }" />										
				</TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ></TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left colspan="3"></TD>					
			</TR>
			

			

<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan="6" align=left bgcolor="#FFFFFF">
						<p align="center">
						<c:out value="${pagesIndex}" /> 
						<c:if test="${prevId != null && prevId != ''}">
						   <input name="button6" type="button" onclick="location='lgPrice.do?fid=editInit&idList=${idList}&id=${prevId}&backToListURL=${backToListURL}'"  value="上一筆" />
						</c:if>
						<input name="button6" type="submit"  id="button6" value="送出" />
						<c:if test="${lgPrice.id != null && lgPrice.id != 0}">
						   <input id="addSubmit" type="button" onclick="addAsNew()"   value="複製新增" />
						</c:if>
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='lgPrice.do?fid=editInit&idList=${idList}&id=${nextId}&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
						</c:if>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
