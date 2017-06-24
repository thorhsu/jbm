<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	String message = (String )request.getAttribute("message");
	EmployeeService employeeService = EmployeeService.getInstance();
	List employeeList = employeeService.getActiveEmployees();
	CodeService codeService = CodeService.getInstance();
	List deadlineList = codeService.findBycodeTypeName("DEADLINE"); //DEADLINE	
	List billingCycleList = codeService.findBycodeTypeName("BILLING_CYCLE"); //DEADLINE
	
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
<jsp:useBean id="customerBean" scope="request" beanName="com.salmat.jbm.service.CustomerService" type="com.salmat.jbm.service.CustomerService" />
<script type="text/javascript">
      var allReceivers = ${customerExtendsForm.allCustomerReceiversJson};
      var acctItemFxs = ${customerExtendsForm.allAcctItemFxsJson};
      var acctItemFxCounter = ${customerExtendsForm.acctItemSize};
      //檢查contractNo是否存在
      var checkPKExist = function(){    	  
    	  var pkVal = $('#contractNo').val();
    	  $.getJSON('${pageContext.request.contextPath}/customerExtends.do', 
    	        {'contractNo':pkVal, 'fid':'checkPKExist'}, function(ret) {
    	        	if (ret.result === "BLANK") {
    	        		$('#msg_pk_exist').html("代號不可為空白");
    	        		$('#btnAdd').attr('disabled', true);
    	        	}else if (ret.result === "NON_EXIST") {
    	        		$('#msg_pk_exist').html("代號可以使用");
    	        		$('#btnAdd').attr('disabled', false);
    	        	}
    	        	else {
    	        		$('#msg_pk_exist').html("代號已經存在");
    	        		$('#btnAdd').attr('disabled', true);
    	           }
    	        } 
    	     );
      }
      
      function toAcctItemFx(obj){
    	  var myId = $(obj).parent().find(".acctItemFxs").val();
    	  if(myId !== "")
    	     toEditLocation("acctItemFx", myId, "id", true);
      }
      function changeReceivers() {
    	  //變更客戶聯絡人
	      $("#receiverNo > option" ).remove();
	      $("#receiverNo").append($("<option ></option>").attr("value", "").text("請選擇"));

	      allReceivers.forEach(function(element){			  			  
		     if($( "#custNo" ).val() === element.custNo ){				  
			     $("#receiverNo").append($("<option ></option>").attr("value", element.receiverNo).text(element.receiver));				
		      }			  
	      });		 
	      //變更計費項目選項	      
	      $(".acctItemFxs > option" ).remove();
		  $(".acctItemFxs").append($("<option ></option>").attr("value", "").text("請選擇"));
		  acctItemFxs.forEach(function(element){			  
			 if($( "#custNo" ).val() === element.custNo || element.custNo === "all"){				  
			   $(".acctItemFxs").append($("<option ></option>").attr("value", element.id).text(element.name));				
			 }			  
		   });
      }
   
      function addAcctItemFxs(){
	      if(acctItemFxCounter > 20){
		      alert("最多20項，無法再增加")
		      return false;
          }
	      acctItemFxCounter ++;   	   
	      $("#acctItemFxs" + acctItemFxCounter).show();
      }
      var oriServiceDisplayLine = 2;
      function addServices(displayLine){
    	  if(oriServiceDisplayLine <= displayLine){
    		  oriServiceDisplayLine = displayLine;
    	  }
    	  $("#service" + oriServiceDisplayLine).show();
    	  oriServiceDisplayLine ++;    	  
      }
      
      var oriOperationDisplayLine = 2;
      function addOps(displayLine){
    	  if(oriOperationDisplayLine <= displayLine){
    		  oriOperationDisplayLine = displayLine;
    	  }
    	  $("#operation" + oriOperationDisplayLine).show();
    	  oriOperationDisplayLine ++;    	  
      }

      var oriSlaDisplayLine = 2;
      function addSlas(displayLine){
    	  if(oriSlaDisplayLine <= displayLine){
    		  oriSlaDisplayLine = displayLine;
    	  }
    	  $("#sla" + oriSlaDisplayLine).show();
    	  oriSlaDisplayLine ++;    	  
      }

      var oriPenaltyDisplayLine = 2;
      function addPenaliess(displayLine){
    	  if(oriPenaltyDisplayLine <= displayLine){
    		  oriPenaltyDisplayLine = displayLine;
    	  }
    	  $("#penalty" + oriPenaltyDisplayLine).show();
    	  oriPenaltyDisplayLine ++;    	  
      }
      function selectObj(name, val){
    	  this.name = name;
    	  this.val = val;
      }
       function pickOptions(){
    	   //被選定後，其它的下拉選單的選項必須被刪除
   		   var selectVals = new Array();
   		   //先選出之前選的值
   		   $(".acctItemFxs").each(function(){
   			   var name = $(this).attr("name");
   			   if($(this).val() !== ""){				
   				   var obj = new selectObj(name, $(this).val());
   				   selectVals.push(obj);				
   			   }
   		   });  
    	   //所有選項全部刷新一次
  		   $(".acctItemFxs > option" ).remove();
  		   $(".acctItemFxs").append($("<option ></option>").attr("value", "").text("請選擇"));
  		   
  		   acctItemFxs.forEach(function(element){			  
  			   if($( "#custNo" ).val() === element.custNo || element.custNo === "all"){
  			     //$(".acctItemFxs").append($("<option ></option>").attr("value", element.id).text(element.name));
  				   $(".acctItemFxs").each(function(){
  					   var selectObject = $(this); //select element
  				       var eleName = selectObject.attr("name"); //取得select element的名字
  				       if(selectVals.length !== 0){
  				    	  var isEqual = false;
  				          selectVals.forEach(function(ele){					
  						     if(ele.val === element.id && ele.name === eleName){
  							     selectObject.append($("<option selected></option>").attr("value", element.id).text(element.name));
  							     isEqual = true;
  							     return;
  						     }else if(ele.val === element.id){  						    	 
  							     //如果選定的值和id相同，而且不是被選定的element的話，什麼事都不做
  							     isEqual = true;
  							     return;
  						     }
  					      });
  				          if(!isEqual)
  				        	selectObject.append($("<option ></option>").attr("value", element.id).text(element.name));
  				       }else{
  				    	     selectObject.append($("<option ></option>").attr("value", element.id).text(element.name));
  				       }
  				     
  				   });
  			   }			  
  		  });
      }


$().ready(function() {

	$('#contractNo').blur(checkPKExist);
	
	//客戶改變時，聯絡人清單也要變
	$( "#custNo" )
	     .change(function(){
	    	 changeReceivers();
	    	 $(".toCustPrice").val("");
     });
	
	//填入收費項目的計費預設值，變更選項，選過的選項不會再出現選
	$(".acctItemFxs").change(function(){
		var selectedVal = $(this).val();
		pickOptions();
		//$(".acctItemFxs[name!=" + name + "] option[value=" + selectedVal + "]").remove();		
		var div = $(this).parent();
		if( selectedVal === "" ){
			$(div).find(".toCustPrice").val("");
		}else{
		   acctItemFxs.forEach(function(element){			  
			    if( selectedVal === element.id ){				  
				    $(div).find(".toCustPrice").val(element.acctPrice);				
			    }		  
		   });
		}
	});
	
	
		
	$( "#contractBegin" ).datepicker({
	      showOn: "button",
	      buttonImage: "${pageContext.request.contextPath}/images/calendar.gif",
	      buttonImageOnly: true,
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd"
	    });
	$( "#contractEnd" ).datepicker({
	      showOn: "button",
	      buttonImage: "${pageContext.request.contextPath}/images/calendar.gif",
	      buttonImageOnly: true,
	      changeMonth: true,
	      changeYear: true,
	      dateFormat: "yy-mm-dd"
	    });
	
	//下拉選單設定值
	
	
	$("#custNo").val("${customerExtendsForm.customerContract.customer.custNo}");	
	changeReceivers();	
	$("#custService").val('${customerExtendsForm.customerContract.custService}');	
	$("#backupStaff").val('${customerExtendsForm.customerContract.backupStaff}');
	$("#accountManager").val('${customerExtendsForm.customerContract.accountManager}');
	$("#deadlineCode").val('${customerExtendsForm.customerContract.deadlineCode == 0 ? "" : customerExtendsForm.customerContract.deadlineCode}');
	$("#remindBeforeDeadline").val('${customerExtendsForm.customerContract.remindBeforeDeadline}');
	$("#receiverNo").val('${customerExtendsForm.customerContract.receiver.receiverNo}');
	$("#idfBillingCycleCode").val('${customerExtendsForm.customerContract.billingCycleCode.id}');
	$("#acctItemFxs1 > select").val('${customerExtendsForm.acctItemFxs1}');
	$("#acctItemFxs2 > select").val('${customerExtendsForm.acctItemFxs2}');
	$("#acctItemFxs3 > select").val('${customerExtendsForm.acctItemFxs3}');
	$("#acctItemFxs4 > select").val('${customerExtendsForm.acctItemFxs4}');
	$("#acctItemFxs5 > select").val('${customerExtendsForm.acctItemFxs5}');
	$("#acctItemFxs6 > select").val('${customerExtendsForm.acctItemFxs6}');
	$("#acctItemFxs7 > select").val('${customerExtendsForm.acctItemFxs7}');
	$("#acctItemFxs8 > select").val('${customerExtendsForm.acctItemFxs8}');
	$("#acctItemFxs9 > select").val('${customerExtendsForm.acctItemFxs9}');
	$("#acctItemFxs10 > select").val('${customerExtendsForm.acctItemFxs10}');
	$("#acctItemFxs11 > select").val('${customerExtendsForm.acctItemFxs11}');
	$("#acctItemFxs12 > select").val('${customerExtendsForm.acctItemFxs12}');
	$("#acctItemFxs13 > select").val('${customerExtendsForm.acctItemFxs13}');
	$("#acctItemFxs14 > select").val('${customerExtendsForm.acctItemFxs14}');
	$("#acctItemFxs15 > select").val('${customerExtendsForm.acctItemFxs15}');
	$("#acctItemFxs16 > select").val('${customerExtendsForm.acctItemFxs16}');
	$("#acctItemFxs17 > select").val('${customerExtendsForm.acctItemFxs17}');
	$("#acctItemFxs18 > select").val('${customerExtendsForm.acctItemFxs18}');
	$("#acctItemFxs19 > select").val('${customerExtendsForm.acctItemFxs19}');
	$("#acctItemFxs20 > select").val('${customerExtendsForm.acctItemFxs20}');
	pickOptions();
	
	for(var i = ( ${customerExtendsForm.acctItemSize} + 1) ; i <= 20 ; i++){
		$("#acctItemFxs" + i).hide();		
	}
	$("#myform").validate({
		rules: {
			custNo: {
		      required: true		      
		    },
		    receiverNo: {
		      required: true
		    },
		    custService: {
			  required: true	
			},
			contractNo: {
			    required: true,
			    maxlength: 13
		    },
		    contractName: {
			    required: true,
			    maxlength: 25
		    },
			contractBegin: {
				required: true						
			},
			contractEnd: {
				required: true							
			},
			toCustPrice1:{
				number: true
			},
			toCustPrice2:{
				number: true
			},
			toCustPrice3:{
				number: true
			},
			toCustPrice4:{
				number: true
			},
			toCustPrice5:{
				number: true
			},
			toCustPrice6:{
				number: true
			},
			toCustPrice7:{
				number: true
			},
			toCustPrice8:{
				number: true
			},
			toCustPrice9:{
				number: true
			},
			toCustPrice10:{
				number: true
			},
			toCustPrice11:{
				number: true
			},
			toCustPrice12:{
				number: true
			},
			toCustPrice13:{
				number: true
			},
			toCustPrice14:{
				number: true
			},
			toCustPrice15:{
				number: true
			},
			toCustPrice16:{
				number: true
			},
			toCustPrice17:{
				number: true
			},
			toCustPrice18:{
				number: true
			},
			toCustPrice19:{
				number: true
			},
			toCustPrice20:{
				number: true
			}
		}
    });
	
	$("#saveAsNew").click(function(){
		$("#fid").val("saveAsNewInit");
		alert($("#fid").val());
		window.document.forms[0].submit();
	});
	
});
</script>
	

                        
	<html:form styleId="myform" action="/customerExtends" method="post" enctype="multipart/form-data">
	    <c:if test="${ACTION_MODE == 'EDIT'}">
	        <c:set var="readonly" value="readonly disabled"/>
	    </c:if>
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />
		<input type="hidden" name="fid" id="fid" value="<%=fid %>" />
		<div align="center">		              
		    <div class="row">		    
			      <div class="col-md-12">
                       <div class="panel-success">
                          <div class="panel-heading">
                             <h3 class="panel-title">客戶合約維護</h3>
                          </div>
                       </div>
                  </div>
			</div>            

			<div class="row query-grid">
                 <div class="col-md-2  text-center"><font color="#FF0000" size="2">*</font>客戶代號</div>
                 <div class="col-md-4 text-left bg-white">
                 	<select id="custNo" name="custNo" size="1" class="required" ${readonly}>
                 	  <c:forEach items="${customerBean.allCustomers}" var="customer">
                 	     <option value="${customer.custNo}" >${customer.easyName}</option>
                 	  </c:forEach>
                 	</select>
                 	<c:if test="${ACTION_MODE == 'EDIT'}">
	                    <input type="hidden" name="custNo" value="${customerExtendsForm.customerContract.customer.custNo}" />
	                </c:if>
                 </div>
                 <div class="col-md-2 text-center">
                    <font color="#FF0000" size="2">*</font>
                    CS
                 </div>
				 <div class="col-md-4 text-left">
					 <select name="custService" id="custService" class="required" size="1"  >
					    <option value="" >請選擇</option>
					 	<c:forEach items="${customerExtendsForm.allCss}" var="employee">
                 	        <option value="${employee.empNo}" >${employee.userId}</option>
                 	    </c:forEach>
                 	  </select>
				</div>				
			</div>
			<div class="row query-grid">
                 <div class="col-md-2  text-center">Backup Staff</div>
                 <div class="col-md-4 text-left bg-white">
                 	<select name="backupStaff" id="backupStaff" size="1"  >
					    <option value="" >請選擇</option>
					 	<c:forEach items="${customerExtendsForm.allCss}" var="employee">
                 	        <option value="${employee.empNo}" >${employee.userId}</option>
                 	    </c:forEach>
                 	  </select>
                 </div>
                 <div class="col-md-2 text-center">                    
                    Account Manager
                 </div>
				 <div class="col-md-4 text-left">
					 <select name="accountManager" id="accountManager" size="1"  >
					    <option value="" >請選擇</option>
					 	<c:forEach items="${customerExtendsForm.allAccounts}" var="employee">
                 	        <option value="${employee.empNo}" >${employee.userId}</option>
                 	    </c:forEach>
                 	  </select>
				</div>
				
			</div>
			
			<div class="row query-grid">
                <div class="col-md-2  text-center"><font color="#FF0000" size="2">*</font>合約代號</div>
                 <div class="col-md-4 text-left">
                 	<input name="contractNo" class="required" id="contractNo" value="${customerExtendsForm.customerContract.contractNo}" ${readonly}/>
                 	<span id="msg_pk_exist" style="color:red"></span>
                 	<c:if test="${ACTION_MODE == 'EDIT'}">
	                    <input type="hidden" name="contractNo" value="${customerExtendsForm.customerContract.contractNo}" />
	                </c:if>
                 </div> 
                 <div class="col-md-2  text-center">
                    <font color="#FF0000" size="2">*</font>合約名稱
                 </div>
				 <div class="col-md-4 text-left">					 
                 	<input name="contractName" id="contractName" value="${customerExtendsForm.customerContract.contractName}" />
                 </div>
				</div>
			</div>
			<div class="row query-grid">
                 <div class="col-md-2  text-center">交貨期限</div>
                 <div class="col-md-4 text-left">
                 	<select name="deadlineCode" id="deadlineCode" size="1" >
                         <option value="" >請選擇</option>
                               <% for (int i=0; i<deadlineList.size(); i++) {
	                               Code _code = (Code)deadlineList.get(i);	
                               %>					 
							<option value="<%=_code.getCodeValueTw() %>"  >
								<%=_code.getCodeValueTw() %>Hr
							</option>
                              <% }%>	
                     </select>
                 </div>                                                   	
                 <div class="col-md-2  text-center">
                     <font color="#FF0000" size="2">*</font>合約區間
                 </div>
				 <div class="col-md-4 text-left">				 					 
                 	<input name="contractBegin" class="required" id="contractBegin" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${customerExtendsForm.customerContract.contractBegin}" />' size="8" readonly/> ~ <input name="contractEnd" id="contractEnd" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${customerExtendsForm.customerContract.contractEnd}" />' class="required" size="8" readonly/>                 	
                 </div>                 
			</div>
			<div class="row query-grid">
			   <div class="col-md-2  text-center">到期提醒</div>
                    <div class="col-md-4 text-left">                                                     
                    	<select name="remindBeforeDeadline" id="remindBeforeDeadline" size="1" >                         
					   		<option value="15" >15天	</option>                         	
							<option value="30" >30天	</option>
							<option value="180" >180天</option>
                      </select>
                </div>
                <div class="col-md-2  text-center"><font color="#FF0000" size="2">*</font>客戶聯絡人
                </div>
                 <div class="col-md-4 text-left">
                    <select name="receiverNo" id="receiverNo">
                    </select>                                                     
                    	
                </div>
            </div>			
			<div class="row query-grid">
                 <div class="col-md-2  text-center">付款方式</div>
                 <div class="col-md-4 text-left">
                 	<input name="payment" id="payment" value="${customerExtendsForm.customerContract.billingWay}" />
                 </div>
                 <div class="col-md-2  text-center">
                                                     付款週期
                 </div>
				 <div class="col-md-4 text-left">					 
                 	<select name="idfBillingCycleCode" id="idfBillingCycleCode" size="1" >                 	  
                 	     <option value="" >請選擇</option>
                               <% for (int i=0; i < billingCycleList.size(); i++) {
	                               Code _code = (Code)billingCycleList.get(i);	
                               %>					 
							<option value="<%=_code.getCodeValueTw() %>"  >
								<%=_code.getCodeValueTw() %>
							</option>
                              <% }%>
                 	</select>
                 </div>
                 
			</div>
			<div class="row query-grid">
                 <div class="col-md-2  text-center">贈送時數</div>
                 <div class="col-md-4 text-left">
                 	<input name="freeHours" id="freeHours" value="${customerExtendsForm.customerContract.freeHours}" />
                 </div>
                 <div class="col-md-2  text-center">
                                                     已使用時數
                 </div>
				 <div class="col-md-4 text-left">					 
                 	${customerExtendsForm.customerContract.usedHours}
                 </div>                 
			</div>
			<div class="row query-grid">			
			    <div class="col-md-2 text-center">
                                                 合約附件                           
                </div>
			    <div class="col-md-4 text-left">					 
              	    <a href="${pageContext.request.contextPath}/contractFiles/${customerExtendsForm.customerContract.fileName}">${customerExtendsForm.customerContract.fileName}</a> 
                </div>
                <div class="col-md-2 text-center">
                                                 上傳附件                           
                </div>
			    <div class="col-md-4 text-left">					 
              	       檔案 : <html:file property="file" /> <font color="red"><html:errors /></font> 
                </div>
           </div>
           <div class="row query-grid">
                 <div class="col-md-2 text-center">計價標準</div>
                 <div class="col-md-4 text-left">
                    <div  id="acctItemFxs1">
                 	    <select name="acctItemFxs1" class="acctItemFxs" > 
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>                 	    
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	         報價: <input class="toCustPrice" size="2" id="toCustPrice1" name="toCustPrice1" value="${customerExtendsForm.toCustPrice1}" /> 
                 	    <br/>
                 	</div>                 	
                 	<div  id="acctItemFxs2" >
                 	    <select name="acctItemFxs2" class="acctItemFxs" >                  	                       	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice2" name="toCustPrice2" value="${customerExtendsForm.toCustPrice2}" />
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs3" >
                 	    <select name="acctItemFxs3" class="acctItemFxs">                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice3" name="toCustPrice3" value="${customerExtendsForm.toCustPrice3}" />
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs4" >
                 	    <select name="acctItemFxs4" class="acctItemFxs" id="acctItemFxs1">                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	           報價: <input class="toCustPrice" size="2" id="toCustPrice4" name="toCustPrice4" value="${customerExtendsForm.toCustPrice4}" />
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs5" >
                 	    <select name="acctItemFxs5" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice5" name="toCustPrice5" value="${customerExtendsForm.toCustPrice5}" />
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs6" >
                 	    <select name="acctItemFxs6" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice6" name="toCustPrice6" value="${customerExtendsForm.toCustPrice6}" />                 	    
                 	    <br/>
                 	</div>                 	
                 	<div  id="acctItemFxs7" >
                 	    <select name="acctItemFxs7" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice7" name="toCustPrice7" value="${customerExtendsForm.toCustPrice7}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs8" >
                 	    <select name="acctItemFxs8" class="acctItemFxs">                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice8" name="toCustPrice8" value="${customerExtendsForm.toCustPrice8}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs9" >
                 	    <select name="acctItemFxs9" class="acctItemFxs" id="acctItemFxs1">                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice9" name="toCustPrice9" value="${customerExtendsForm.toCustPrice9}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs10" >
                 	    <select name="acctItemFxs10" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice10" name="toCustPrice10" value="${customerExtendsForm.toCustPrice10}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs11" >
                 	    <select name="acctItemFxs11" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice11" name="toCustPrice11" value="${customerExtendsForm.toCustPrice11}" />                 	    
                 	    <br/>
                 	</div>                 	
                 	<div  id="acctItemFxs12" >
                 	    <select name="acctItemFxs12" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice12" name="toCustPrice12" value="${customerExtendsForm.toCustPrice12}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs13" >
                 	    <select name="acctItemFxs13" class="acctItemFxs">                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice13" name="toCustPrice13" value="${customerExtendsForm.toCustPrice13}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs14" >
                 	    <select name="acctItemFxs14" class="acctItemFxs" id="acctItemFxs1">                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice14" name="toCustPrice14" value="${customerExtendsForm.toCustPrice14}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs15" >
                 	    <select name="acctItemFxs15" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice15" name="toCustPrice15" value="${customerExtendsForm.toCustPrice15}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs16" >
                 	    <select name="acctItemFxs16" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice16" name="toCustPrice16" value="${customerExtendsForm.toCustPrice16}" />                 	    
                 	    <br/>
                 	</div>                 	
                 	<div  id="acctItemFxs17" >
                 	    <select name="acctItemFxs17" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice17" name="toCustPrice17" value="${customerExtendsForm.toCustPrice17}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs18" >
                 	    <select name="acctItemFxs18" class="acctItemFxs">                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice18" name="toCustPrice18" value="${customerExtendsForm.toCustPrice18}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs19" >
                 	    <select name="acctItemFxs19" class="acctItemFxs" id="acctItemFxs1">                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice19" name="toCustPrice19" value="${customerExtendsForm.toCustPrice19}" />                 	    
                 	    <br/>
                 	</div>
                 	<div  id="acctItemFxs20" >
                 	    <select name="acctItemFxs20" class="acctItemFxs" >                 	                   	     
                 	        <option value="" >請選擇</option>
                 	        <c:forEach items="${customerExtendsForm.custAcctItemFxs}" var="acctItemFx">
                 	            <option value="${acctItemFx.id}" >${acctItemFx.name}</option>
                 	       </c:forEach>               	                   	     
                 	    </select>
                 	    <a href="#" onclick="toAcctItemFx(this);">連結</a>
                 	          報價: <input class="toCustPrice" size="2" id="toCustPrice20" name="toCustPrice20" value="${customerExtendsForm.toCustPrice20}" />                 	    
                 	    <br/>
                 	</div>
                 	<button type="button" class="btn btn-xs btn-link" onclick="addAcctItemFxs();">增加計費項目</button>
                 </div>
                       <div class="col-md-2 text-center">相關IT人員</div>
                 <div class="col-md-4 text-left">
                     <c:forEach items="${customerExtendsForm.itEmps}" var="employee">
                        <c:set var="checked" value=""/>
                        <c:forEach items="${customerExtendsForm.customerContract.itEmployees}" var="emp">
                           <c:if test="${emp == employee.empNo}">
                              <c:set var="checked" value="checked"/>
                           </c:if>
                        </c:forEach>
                        <input type="checkbox" name="itEmp" value="${employee.empNo}" ${checked }> ${employee.userId} &nbsp;&nbsp;                         
                     </c:forEach>
                 </div>
            </div>
			
			<div class="row query-grid">
                 <div class="col-md-2 text-center">服務項目</div>
                 <div class="col-md-4 text-left">
                    <c:set var="size" value="0"/>
                 	<c:forEach items="${customerExtendsForm.customerContract.services}" var="service" varStatus="status">
                 	    <c:set var="size" value="${status.count}"/>
                 	    <div id="service${status.count}"> 
                 	      ${status.count}.&nbsp;<input type="text" name="service" value="${service.contents}" size="30"/> <br />
                 	    </div>
                 	</c:forEach>
                 	<div id="service${size + 1}" > 
                 	      ${size + 1}.&nbsp;<input type="text" name="service" value="" size="30"/> <br />
                 	</div>
                 	<c:forEach begin="${size + 2}" end="20" var="service" >                 	    
                 	    <div id="service${service}" style="display:none"> 
                 	      ${service}.&nbsp;<input type="text" name="service" value="" size="30"/> <br />
                 	    </div>
                 	</c:forEach>
                 	<button type="button" class="btn btn-xs btn-link" onclick="addServices(${size + 2});">增加服務項目</button>
                 </div>
                 <div class="col-md-2 text-center">
                                                           作業內容
                 </div>
				 <div class="col-md-4 text-left">					 
                 	<c:set var="size" value="0"/>
                 	<c:forEach items="${customerExtendsForm.customerContract.operations}" var="operation" varStatus="status">
                 	    <c:set var="size" value="${status.count}"/>
                 	    <div id="operation${status.count}"> 
                 	      ${status.count}.&nbsp;<input type="text" name="operation" value="${operation.contents}" size="30"/> <br />
                 	    </div>
                 	</c:forEach>
                 	<div id="operation${size + 1}" > 
                 	      ${size + 1}.&nbsp;<input type="text" name="operation" value="" size="30"/> <br />
                 	</div>
                 	<c:forEach begin="${size + 2}" end="20" var="operation">                 	    
                 	    <div id="operation${operation}" style="display:none"> 
                 	      ${operation}.&nbsp;<input type="text" name="operation" value="" size="30"/> <br />
                 	    </div>
                 	</c:forEach>
                 	<button type="button" class="btn btn-xs btn-link" onclick="addOps(${size + 2});">增加作業項目</button>
                 </div>
			</div>
			<div class="row query-grid">
                 <div class="col-md-2 text-center">SLA</div>
                 <div class="col-md-4 text-left">
                 	<c:set var="size" value="0"/>
                 	<c:forEach items="${customerExtendsForm.customerContract.slas}" var="sla" varStatus="status">
                 	    <c:set var="size" value="${status.count}"/>
                 	    <div id="sla${status.count}"> 
                 	      ${status.count}.&nbsp;<input type="text" name="sla" value="${sla.contents}" size="30"/> <br />
                 	    </div>
                 	</c:forEach>
                 	<div id="sla${size + 1}" > 
                 	      ${size + 1}.&nbsp;<input type="text" name="sla" value="" size="30"/> <br />
                 	</div>
                 	<c:forEach begin="${size + 2}" end="20" var="sla">                 	    
                 	    <div id="sla${sla}" style="display:none"> 
                 	      ${sla}.&nbsp;<input type="text" name="sla" value="" size="30"/> <br />
                 	    </div>
                 	</c:forEach>
                 	<button type="button" class="btn btn-xs btn-link" onclick="addSlas(${size + 2});">增加SLA項目</button>
                 </div>
                 <div class="col-md-2 text-center">
                                                           罰則/損害賠償
                 </div>
				 <div class="col-md-4 text-left">					 
                 	<c:set var="size" value="0"/>
                 	<c:forEach items="${customerExtendsForm.customerContract.penalties}" var="penalty" varStatus="status">
                 	    <c:set var="size" value="${status.count}"/>
                 	    <div id="penalty${status.count}"> 
                 	      ${status.count}.&nbsp;<input type="text" name="penalty" value="${penalty.contents}" size="30"/> <br />
                 	    </div>
                 	</c:forEach>
                 	<div id="penalty${size + 1}" > 
                 	      ${size + 1}.&nbsp;<input type="text" name="penalty" value="" size="30"/> <br />
                 	</div>
                 	<c:forEach begin="${size + 2}" end="20" var="penalty">                 	    
                 	    <div id="penalty${penalty}" style="display:none"> 
                 	      ${penalty}.&nbsp;<input type="text" name="penalty" value="" size="30"/> <br />
                 	    </div>
                 	</c:forEach>
                 	<button type="button" class="btn btn-xs btn-link" onclick="addPenaliess(${size + 2});">增加罰則項目</button>

                 </div>
			</div>

			
			<!-- 下方命令列控制區塊 -->			
			<div class="row">
					<div class="col-md-12">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" class="btn btn-success" value="新增" />
						<input name="btnCandcel" type="button"  class="btn btn-success" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<c:out value="${pagesIndex}" /> 
						<c:if test="${prevId != null && prevId != ''}">
						   <input name="button6" type="button" class="btn btn-success" onclick="location='customer.do?fid=editInit&idList=<c:out value="${idList}"/>&custNo=${prevId }&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit" class="btn btn-success" id="button6" value="更新" onclick="return confirm('確定更新？')"/>
						<input name="button6" type="button" class="btn btn-success" id="saveAsNew" value="複製新增" />
						
						<input  type="button" class="btn btn-success" onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='customer.do?fid=editInit&idList=<c:out value="${idList}"/>&custNo=${nextId }&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
						</c:if>
<%  } else { %>
						<input  type="button" class="btn btn-success"  onclick="backToList()"   value="回列表" />
<%  } %>
					    </p>
					 </div>
				</div>
<!-- end 下方命令列控制區塊 -->					


	</html:form>		
