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
	String message = (String )request.getAttribute("message");
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	EmployeeService employeeService = EmployeeService.getInstance();
	CodeService codeService = CodeService.getInstance();
	
	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>

<script type="text/javascript"> 

$().ready(function() {
	
	$("#unitCode").val("${acctItemFxForm.acctItemFx.unitCode}");
	$("#custNo").val("${acctItemFxForm.acctItemFx.custNo}");
	$("#nonJbChargeItemId").val("${acctItemFxForm.acctItemFx.acctChargeItem.id}");
	$("#isMaterial").val("${acctItemFxForm.acctItemFx.isMaterial == true? '1' : '0'}");
	$("#categoryCode").val("${acctItemFxForm.acctItemFx.categoryCode}");
	$("#lengthUnitNm").val("${acctItemFxForm.acctItemFx.lengthUnitNm}");
	$("#weightUnitNm").val("${acctItemFxForm.acctItemFx.weightUnitNm}");
	
	
	//非物料時不能填入版號
	$( "#isMaterial" )
	  .change(function() {		  		  
		  if($( "#isMaterial" ).val() === '0'){			  
			  $( ".material" ).val("");
			  $( ".material" ).attr("disabled", "disabled");
		  }else{
			  $( ".material" ).removeAttr("disabled");
		  }		 
	  }).change();
	$( "#unitCode" )
	     .change(function() {	    	 
	    	 $("#unitName").val($("#unitCode > option:selected").text());
	  }).change();
	
	$( "#categoryCode" ).change(function() {	    	 
   	    $("#categoryName").val($("#categoryCode > option:selected").text());
    });
	
	
	// validate my form when it is submitted
	$("#myform").validate({
			rules: {
				name: {
			      required: true,
			      maxlength: 30
			    },
			    cost: {
				  number: true	
				},
				acctPrice: {
					number: true		
				},
				printNo: {
					maxlength: 20							
				},
				width: {
					number: true		
				},
				length: {
					number: true		
				},
				pound: {
					number: true		
				},
				weight:{
					number: true
				}
			}
	});		
	$("#saveAsNew").click(function(){
		$("#fid").val("saveAsNewInit");
		window.document.forms[0].submit();
	});
	
});
</script>
	

                        
	<html:form action="/acctItemFx" method="post" styleId="myform">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />
		<input type="hidden" name="fid" id="fid" value="<%=fid %>" />
		<input type="hidden" name="unitName" id="unitName" value="${acctItemFxForm.acctItemFx.unitName}" />
		<input type="hidden" name="id" id="id" value="${acctItemFxForm.acctItemFx.id}" />
		<div align="center">		              
		    <div class="row">		    
			      <div class="col-md-12">
                       <div class="panel-success">
                          <div class="panel-heading">
                             <h3 class="panel-title">物料／計價標準維護</h3>
                          </div>
                       </div>
                  </div>
			</div>            

			<div class="row query-grid">
                 <div class="col-md-2  text-center"><font color="#FF0000" size="2">*</font>客戶代號</div>
                 <div class="col-md-4 text-left">
                 	<select id="custNo" name="custNo" size="1" >
                 	  <option value="all" >共用</option>
                 	  <c:forEach items="${acctItemFxForm.allCustomers}" var="customer">
                 	     <option value="${customer.custNo}" >${customer.easyName}</option>
                 	  </c:forEach>
                 	</select>
                 </div>
                 <div class="col-md-2 text-center">
                    <font color="#FF0000" size="2">*</font>名稱
                 </div>
				 <div class="col-md-4 text-left">
					 <input id="name" name="name" value="${acctItemFxForm.acctItemFx.name}">
				</div>
			</div>
			
			<div class="row query-grid">
                 <div class="col-md-2  text-center">成本</div>
                 <div class="col-md-4 text-left">
                 	<input name="cost" id="cost" value="${acctItemFxForm.acctItemFx.cost}" />
                 </div>
                 <div class="col-md-2  text-center">會計計價</div>
                 <div class="col-md-4 text-left">
                 	<input name="acctPrice" id="acctPrice" value="${acctItemFxForm.acctItemFx.acctPrice}" />
                 </div>                          
				</div>
			</div>
			<div class="row query-grid">
                 <div class="col-md-2  text-center">版號</div>
                 <div class="col-md-4 text-left">
                 	<input name="printNo" class="material" id="printNo" value="${acctItemFxForm.acctItemFx.printNo}" />
                 </div>                                                   	
                 <div class="col-md-2  text-center">
                                                    物料／非物料
                 </div>
				 <div class="col-md-4 text-left">					 
                 	<select id="isMaterial" size=1 name="isMaterial"> 
						<option value="1" >物料</option>
						<option value="0" >非物料</option>
				    </select>                 	
                 </div>                 
			</div>
			<div class="row query-grid">			   
                <div class="col-md-2  text-center">計算單位
                </div>
                 <div class="col-md-4 text-left">
                    <select name="unitCode" id="unitCode">
                        <c:forEach items="${acctItemFxForm.allUnits}" var="code">
                 	        <option value="${code.id}" >${code.codeValueTw}</option>
                 	  </c:forEach>
                    </select>                            	
                </div>
                <div class="col-md-2  text-center">收費項目
                </div>
                 <div class="col-md-4 text-left">
                    <select name="nonJbChargeItemId" id="nonJbChargeItemId">
                        <c:forEach items="${acctItemFxForm.nonJbChargeItems}" var="chargeItem">
                 	        <option value="${chargeItem.id}" >${chargeItem.itemNameTw}</option>
                 	  </c:forEach>
                    </select>                            	
                </div>                
            </div>						
            <div class="row query-grid">			   
                <div class="col-md-2  text-center">物料種類
                </div>
                 <div class="col-md-4 text-left">
                    <select name="categoryCode" id="categoryCode" class="material">
                        <option value="" >請選擇</option>
                        <c:forEach items="${acctItemFxForm.allCategories}" var="code">
                 	        <option value="${code.id}" >${code.codeValueTw}</option>
                 	  </c:forEach>
                    </select>                          
                    <input type="hidden" name="categoryName" id="categoryName" class="material" value="${acctItemFxForm.acctItemFx.categoryName}"/>  	
                </div>
                <div class="col-md-2  text-center">顏色
                </div>
                 <div class="col-md-4 text-left">
                    <input class="material" name="color" id="color" value="${acctItemFxForm.acctItemFx.color}" />                       	
                </div>                
            </div>
            <div class="row query-grid">			   
                <div class="col-md-2  text-center">尺寸單位
                </div>
                 <div class="col-md-4 text-left">
                    <select name="lengthUnitNm" id="lengthUnitNm" class="material">
                        <option value="" >請選擇</option>
                        <option value="cm" >公分</option>
                        <option value="inch" >英吋</option>
                    </select>                            	
                </div>
                <div class="col-md-2  text-center">尺寸
                </div>
                <div class="col-md-4 text-left">
                                                   長：<input class="material" name="length" id="length" value="${acctItemFxForm.acctItemFx.length}" size="3"/> &nbsp;&nbsp;&nbsp;寬：<input class="material" name="width" id="width" value="${acctItemFxForm.acctItemFx.width}" size="3"/>                       	
                </div>                
            </div>
            <div class="row query-grid">			   
                <div class="col-md-2  text-center">
                                                    重量單位
                </div>
                 <div class="col-md-4 text-left">
                    <select name="weightUnitNm" id="weightUnitNm" class="material">
                        <option value="" >請選擇</option>
                        <option value="g" >公克</option>
                        <option value="kg" >公斤</option>
                        <option value="pound" >英磅</option>
                    </select>                            	
                </div>
                <div class="col-md-2  text-center">重量
                </div>
                <div class="col-md-4 text-left">
                    <input class="material" name="weight" id="weight" value="${acctItemFxForm.acctItemFx.weight}" size="3"/>                       	
                </div>                
            </div>
            <div class="row query-grid">			   
                <div class="col-md-2  text-center">紙張磅數
                </div>
                 <div class="col-md-4 text-left">
                    <input class="material" name="pound" id="pound" value="${acctItemFxForm.acctItemFx.pound}" size=""/>
                </div>
                                
            </div>

			
			<!-- 下方命令列控制區塊 -->			
			<div class="row">
					<div class="col-md-12">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" class="btn btn-success" value="新增" onclick="return confirm('確定新增？')"/>
						<input name="btnAdd" type="button"  id="btnAdd" class="btn btn-success" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<c:out value="${pagesIndex}" /> 
						<c:if test="${prevId != null && prevId != ''}">
						   <input name="button6" type="button" class="btn btn-success" onclick="location='acctItemFx.do?fid=editInit&idList=<c:out value="${idList}"/>&custNo=${prevId }&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit" class="btn btn-success" id="button6" value="更新" onclick="return confirm('確定更新？')"/>
						<input name="button6" type="button" class="btn btn-success" id="saveAsNew" value="複製新增" />
						
						<input  type="button" class="btn btn-success" onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='acctItemFx.do?fid=editInit&idList=<c:out value="${idList}"/>&custNo=${nextId }&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
						</c:if>
<%  } else { %>
						<input  type="button" class="btn btn-success"  onclick="backToList()"   value="回列表" />
<%  } %>
					    </p>
					 </div>
				</div>
<!-- end 下方命令列控制區塊 -->					


	</html:form>		
