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
 	String message = (String )request.getAttribute("message");
 	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	String  targetURI = (String)request.getAttribute("targetURI");
	String actionName="jobcode";

	if (targetURI.equalsIgnoreCase("jobbag.do") ) 
		actionName="jobbag";


	JobCode jobcode = (JobCode)request.getAttribute("jobcode");
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
  
    //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; 
		disabledPK="";
		jobcode.setJobCodeId("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script src="js/lib/jquery.jqModal.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.dimensions.js" type="text/javascript"></script>
<script type="text/javascript"> 
var lplist = <%=lpinfoJSONArray%>;
var mplist = <%=mpinfoJSONArray%>;
var pslist = <%=psinfoJSONArray%>;
var lglist = <%=lginfoJSONArray%>;
var returnlist = <%=returninfoJSONArray%>;
var lclist = <%=lcinfoJSONArray%>;
function previewDateShow(){
   $(':html').scrollTop(0);
   $('#jqmPreviewArea').jqmShow().jqDrag();
}
function checkAutoDelete(obj){
	var isChecked = $(obj).attr("checked");	
	if( !isChecked  && window.confirm("被勾選為無效工單後，請問是否同意讓系統自動刪除此工單樣本所產生的工單（保留五年），並在工單刪除完畢後自動刪除此工單樣本？")){		
		$('#autoDelete').val("true");
	}else{
		$('#autoDelete').val("0");
	}		
}


function preview(){
	if($('#previewDate').val() == ""){
		alert("請選擇預覽Cycle Date");
	}else{
	   $('#fid').val("preview");
	   document.myform.submit();
	}
}
function changeMonthOptions(selectId){
  
   var monthId = document.getElementById(selectId);
   var month = $(monthId).val();
   
   //轉成date的select
   selectId = selectId.replace("Month", "date");
   
   var dateObj = document.getElementById(selectId);
   
   $(dateObj).find('option').remove();
   if(month == "0"){
   
       $(dateObj).append("<option value=\"0\" >全部</option>");
   }
   for(var i = 1 ; i <= 29; i++){
      $(dateObj).append("<option value='" + i + "'>" + i + "</option>");
   }
   if(month == "4" || month == "6" || month == "9" || month == "11"){
      $(dateObj).append("<option value='30' >30</option>");
   }else if(month != "2"){
   
      $(dateObj).append("<option value='30' >30</option>");
      $(dateObj).append("<option value='31' >31</option>");
   }
   

}

//eDD Change
var eddChange = function(){
	//edd被check時，其它的選項都不能選
	if($('#isEdd').attr("checked") && !$('#mailType').attr("checked")){
	   $('#isLp').attr("checked", false);
	   $('#isLg').attr("checked", false);
	   $('#mpDmPs3').attr("checked", true);	   
	   $('#LPInfo').css("visibility", "hidden")
	   changeMP('NULLInfo');
	   $('#LGInfo').css("visibility", "hidden")
	}    
	$('#isLp').attr("disabled", ($('#isEdd').attr("checked") & !$('#mailType').attr("checked")));
	$('input[name="mpDmPs"]').attr("disabled", ($('#isEdd').attr("checked") & !$('#mailType').attr("checked")));
	$('#isLg').attr("disabled", ($('#isEdd').attr("checked") & !$('#mailType').attr("checked"))); 
	
};


$().ready(function() {
        $('#jqmPreviewArea').jqm({overlay: 0});
        $('#lpBeginMonth1').val('<c:out value="${jobcode.lpBeginMonth1}" default="0"/>');
		$('#lpEndMonth1').val('<c:out value="${jobcode.lpEndMonth1}" default="0"/>');
		$('#lpBeginMonth2').val('<c:out value="${jobcode.lpBeginMonth2}" default="0"/>');
		$('#lpEndMonth2').val('<c:out value="${jobcode.lpEndMonth2}" default="0"/>');
		$('#lpBeginMonth3').val('<c:out value="${jobcode.lpBeginMonth3}" default="0"/>');
		$('#lpEndMonth3').val('<c:out value="${jobcode.lpEndMonth3}" default="0"/>');

        $('#mpBeginMonth1').val('<c:out value="${jobcode.mpBeginMonth1}" default="0"/>');
		$('#mpEndMonth1').val('<c:out value="${jobcode.mpEndMonth1}" default="0"/>');
		$('#mpBeginMonth2').val('<c:out value="${jobcode.mpBeginMonth2}" default="0"/>');
		$('#mpEndMonth2').val('<c:out value="${jobcode.mpEndMonth2}" default="0"/>');
		$('#mpBeginMonth3').val('<c:out value="${jobcode.mpBeginMonth3}" default="0"/>');
		$('#mpEndMonth3').val('<c:out value="${jobcode.mpEndMonth3}" default="0"/>');
		
		$('#noJobBagFromStr').val("${jobcode.noJobBagFromStr}");
		$('#noJobBagEndStr').val("${jobcode.noJobBagEndStr}");
		
		
		var lp1JudgeByRadios = $('input:radio[name="lp1JudgeBy"]');
		    lp1JudgeByRadios.filter('[value=<c:out value="${jobcode.lp1JudgeBy}" default="cycleDate"/>]').attr('checked', true);
		var lp2JudgeByRadios = $('input:radio[name="lp2JudgeBy"]');
		    lp2JudgeByRadios.filter('[value=<c:out value="${jobcode.lp2JudgeBy}" default="cycleDate"/>]').attr('checked', true);
		var lp3JudgeByRadios = $('input:radio[name="lp3JudgeBy"]');		
		    lp3JudgeByRadios.filter('[value=<c:out value="${jobcode.lp3JudgeBy}" default="cycleDate"/>]').attr('checked', true);
		
		var mp1JudgeByRadios = $('input:radio[name="mp1JudgeBy"]');
		    mp1JudgeByRadios.filter('[value=<c:out value="${jobcode.mp1JudgeBy}" default="cycleDate"/>]').attr('checked', true);
		var mp2JudgeByRadios =$('input:radio[name="mp2JudgeBy"]');
		    mp2JudgeByRadios.filter('[value=<c:out value="${jobcode.mp2JudgeBy}" default="cycleDate"/>]').attr('checked', true);
		var mp3JudgeByRadios =$('input:radio[name="mp3JudgeBy"]');
		    mp3JudgeByRadios.filter('[value=<c:out value="${jobcode.mp3JudgeBy}" default="cycleDate"/>]').attr('checked', true);
		
		for(var i = 1 ; i <= 3 ; i++){
		    changeMonthOptions("lpBeginMonth" + i);
		    changeMonthOptions("lpEndMonth" + i);
		    changeMonthOptions("mpBeginMonth" + i);
		    changeMonthOptions("mpEndMonth" + i);		    		    
		}
		//重設option後要重設date
		$('#lpBegindate1').val('<c:out value="${jobcode.lpBegindate1}" default="0"/>');
		$('#lpEnddate1').val('<c:out value="${jobcode.lpEnddate1}" default="0"/>');
		$('#lpBegindate2').val('<c:out value="${jobcode.lpBegindate2}" default="0"/>');
		$('#lpEnddate2').val('<c:out value="${jobcode.lpEnddate2}" default="0"/>');
		$('#lpBegindate3').val('<c:out value="${jobcode.lpBegindate3}" default="0"/>');
		$('#lpEnddate3').val('<c:out value="${jobcode.lpEnddate3}" default="0"/>');
        $('#mpBegindate1').val('<c:out value="${jobcode.mpBegindate1}" default="0"/>');
		$('#mpEnddate1').val('<c:out value="${jobcode.mpEnddate1}" default="0"/>');
		$('#mpBegindate2').val('<c:out value="${jobcode.mpBegindate2}" default="0"/>');
		$('#mpEnddate2').val('<c:out value="${jobcode.mpEnddate2}" default="0"/>');
		$('#mpBegindate3').val('<c:out value="${jobcode.mpBegindate3}" default="0"/>');
		$('#mpEnddate3').val('<c:out value="${jobcode.mpEnddate3}" default="0"/>');
		
		//綁定選單事件
		$('#lpBeginMonth1').change(function(){changeMonthOptions('lpBeginMonth1')});
		$('#lpEndMonth1').change(function(){changeMonthOptions('lpEndMonth1')});
		$('#lpBeginMonth2').change(function(){changeMonthOptions('lpBeginMonth2')});
		$('#lpEndMonth2').change(function(){changeMonthOptions('lpEndMonth2')});
		$('#lpBeginMonth3').change(function(){changeMonthOptions('lpBeginMonth3')});
		$('#lpEndMonth3').change(function(){changeMonthOptions('lpEndMonth3')});
		$('#mpBeginMonth1').change(function(){changeMonthOptions('mpBeginMonth1')});
		$('#mpEndMonth1').change(function(){changeMonthOptions('mpEndMonth1')});
		$('#mpBeginMonth2').change(function(){changeMonthOptions('mpBeginMonth2')});
		$('#mpEndMonth2').change(function(){changeMonthOptions('mpEndMonth2')});
		$('#mpBeginMonth3').change(function(){changeMonthOptions('mpBeginMonth3')});
		$('#mpEndMonth3').change(function(){changeMonthOptions('mpEndMonth3')});
		$('#dispatchTime').val('<c:out value="${jobcode.dispatchTime}" default="AM"/>');		
			
	    var mailType = "${jobcode.mailType}";
	    if(mailType !== ""){
	    	$("#mailType").attr("checked", true);
	    }
	    eddChange();
});





$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
    
	
	//大密技, 將attr= disabled, 轉成 正常, 這樣才能送出submit 帶出預設值
	$("#myform").submit(function(){		
		var jobCodeTypeText = $("#jobCodeType option:selected").text();
		jobCodeTypeText = $.trim(jobCodeTypeText);
		if(jobCodeTypeText == "拆帳" && ($("#createByCycle").attr("checked") || $("#filename").val() != "")){
			alert("工作種類為拆帳時，此工單樣本只能拆帳使用，不可正常產生工單");
			$("#createByCycle").attr("checked", false);
			$("#filename").val("");
			submitFinished()
			return false;
		}
		
		if($("#createByCycle").attr("checked")){			
	    	if($("#filename").val() != ""){
	    		if( !window.confirm("您已勾選根據周期產生工單，且檔名規則不為空白，這樣有可能在同一個cycle date中產生兩份工單，請問你是否仍要送出資料？")){
	    			submitFinished();
	    			return false;
	    		}
	    	}
	    }else{	    	
	    	if($("#filename").val() == "" && jobCodeTypeText != "拆帳"){
	    		if(!window.confirm("您沒有勾選根據周期產生工單，且檔名規則為空白，這樣有可能產生不出工單，請問你是否仍要送出資料？")){
	    			submitFinished()
	    			return false;
	    		}
	    	}
	    }
		
		
		$("#codeLgType").attr("disabled","");
		$("#codeMailToPostoffice").attr("disabled","");
		$("#codeMailCategory").attr("disabled","");
		if($("#isLg").attr('checked')){
		   if($("#dispatchType").val() == ""){
			   submitFinished(); 
		      alert("勾選Dispatch後，出貨方式一定要選擇");
		      return false;
		   }
		}
		
		
		// 檢查 LP 使用區間 是否重疊
	
		var lpBegindate1 = parseInt($('#lpBegindate1').val());
		var lpEnddate1 = parseInt($('#lpEnddate1').val());
		var lpBegindate2 = parseInt($('#lpBegindate2').val());
		var lpEnddate2 = parseInt($('#lpEnddate2').val());
		var lpBegindate3 = parseInt($('#lpBegindate3').val());
		var lpEnddate3 = parseInt($('#lpEnddate3').val());		
		
		var lpBeginMonth1 = parseInt($('#lpBeginMonth1').val());
		var lpEndMonth1 = parseInt($('#lpEndMonth1').val());
		var lpBeginMonth2 = parseInt($('#lpBeginMonth2').val());
		var lpEndMonth2 = parseInt($('#lpEndMonth2').val());
		var lpBeginMonth3 = parseInt($('#lpBeginMonth3').val());
		var lpEndMonth3 = parseInt($('#lpEndMonth3').val());
		var lpMonthNotSet = false;
		if(lpBeginMonth1 == 0 && lpBeginMonth2 == 0 && lpBeginMonth3 == 0
		    &&
		   lpEndMonth1 == 0 && lpEndMonth2 == 0 && lpEndMonth3 == 0 ){
		   lpMonthNotSet = true;
		} 

		if ( lpMonthNotSet && (lpBegindate1==0 && lpEnddate1!=0) || (lpBegindate1!=0 && lpEnddate1==0)) {
			submitFinished();
			alert("列印1  使用區間 錯誤");
			return false;
		}
		
		if (lpMonthNotSet &&  (lpBegindate2==0 && lpEnddate2!=0) || (lpBegindate2!=0 && lpEnddate2==0)) {
			submitFinished();
			alert("列印  使用區間2 錯誤");
			return false;
		}
		
		if (lpMonthNotSet &&  (lpBegindate3==0 && lpEnddate3!=0) || (lpBegindate3!=0 && lpEnddate3==0)) {
			submitFinished();
			alert("列印  使用區間3  錯誤");
			return false;
		}				
		
		if (lpMonthNotSet && lpMonthNotSet && lpBegindate1 > lpEnddate1  ) {
			submitFinished();
			alert("列印  使用區間1_開始日期  不能大於  區間1_結束日期");			
			return false;
		}
	    
		if (lpMonthNotSet && lpBegindate2 >0 && lpBegindate1 >0) {
			if (lpEnddate2 >= lpBegindate2) {
				if (lpBegindate2 >= lpBegindate1 && lpBegindate2 <= lpEnddate1) {
					submitFinished();
					alert("列印  使用區間2_日期  不能跟區間1 重疊 ");
					return false;
				}
				
				if (lpEnddate2 >= lpBegindate1 && lpEnddate2 <= lpEnddate1) {
					submitFinished();
					alert("列印  使用區間2_日期  不能跟區間1 重疊 ");
					return false;
				} 
				if (lpBegindate2 <= lpBegindate1 && lpEnddate2 >= lpEnddate1) {
					submitFinished();
					alert("列印  使用區間2_日期  不能跟區間1 重疊 ");
					return false;
				}
			} else { //跨31日
				if (!(lpBegindate2 > lpEnddate1 && lpEnddate2 < lpBegindate1) ) {
					submitFinished();
					alert("列印  使用區間2_日期  不能跟區間1 重疊 ");
					return false;
				}			
			}
		}
		
		if (lpMonthNotSet && lpBegindate3 >0 && lpBegindate1 >0) {		
			if (lpEnddate3 >= lpBegindate3) {
				if (lpBegindate3 >= lpBegindate1 && lpBegindate3 <= lpEnddate1) {
					submitFinished();
					alert("列印  使用區間3_日期  不能跟區間1 重疊 ");
					return false;
				}
				
				if (lpEnddate3 >= lpBegindate1 && lpEnddate3 <= lpEnddate1) {
					submitFinished();
					alert("列印  使用區間3_日期  不能跟區間1 重疊 ");
					return false;
				}
				if (lpBegindate3 <= lpBegindate1 && lpEnddate3 >= lpEnddate1) {
					submitFinished();
					alert("列印  使用區間3_日期  不能跟區間1 重疊 ");
					return false;
				}
			} else { //跨31日
				if (!(lpBegindate3 > lpEnddate1 && lpEnddate3 < lpBegindate1) ) {
					submitFinished();
					alert("列印  使用區間3_日期  不能跟區間1 重疊 ");
					return false;
				}			
			}		
		}
		
		//區間2 跟 3 的比較		
		if (lpMonthNotSet && lpBegindate3 >0 && lpBegindate2 >0) {			
			if (lpEnddate2 >= lpBegindate2) {
				if (lpEnddate3 >= lpBegindate3) {
					if (lpBegindate3 >= lpBegindate2 && lpBegindate3 <= lpEnddate2) {
						submitFinished();
						alert("列印  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
					
					if (lpEnddate3 >= lpBegindate2 && lpEnddate3 <= lpEnddate2) {
						submitFinished();
						alert("列印  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
					if (lpBegindate3 <= lpBegindate2 && lpEnddate3 >= lpEnddate2) {
						submitFinished();
						alert("列印  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
				} else { //跨31日
					if (!(lpBegindate3 > lpEnddate2 && lpEnddate3 < lpBegindate2) ) {
						submitFinished();
						alert("列印  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}			
				}		
			} else { //跨31日
				if (lpEnddate3 >= lpBegindate3) {
					if (lpBegindate3 >= lpBegindate2 && lpBegindate3 <= lpEnddate2) {
						submitFinished();
						alert("列印  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
					
					if (lpEnddate3 >= lpBegindate2 && lpEnddate3 <= lpEnddate2) {
						submitFinished();
						alert("列印  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
					if (!(lpBegindate3 > lpEnddate2 && lpEnddate3 < lpBegindate2) ) {
						submitFinished();
						alert("列印  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
				} 	else { //跨31日
					    submitFinished();
						alert("列印  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
		
				}	
			}
		}
		
		

		// 檢查 mp 使用區間 是否重疊
	
		var mpBegindate1 = parseInt($('#mpBegindate1').val());
		var mpEnddate1 = parseInt($('#mpEnddate1').val());
		var mpBegindate2 = parseInt($('#mpBegindate2').val());
		var mpEnddate2 = parseInt($('#mpEnddate2').val());
		var mpBegindate3 = parseInt($('#mpBegindate3').val());
		var mpEnddate3 = parseInt($('#mpEnddate3').val());	
		
		var mpBeginMonth1 = parseInt($('#mpBeginMonth1').val());
		var mpEndMonth1 = parseInt($('#mpEndMonth1').val());
		var mpBeginMonth2 = parseInt($('#mpBeginMonth2').val());
		var mpEndMonth2 = parseInt($('#mpEndMonth2').val());
		var mpBeginMonth3 = parseInt($('#mpBeginMonth3').val());
		var mpEndMonth3 = parseInt($('#mpEndMonth3').val());
		var mpMonthNotSet = false;
		if(mpBeginMonth1 == 0 && mpBeginMonth2 == 0 && mpBeginMonth3 == 0
		    &&
		   mpEndMonth1 == 0 && mpEndMonth2 == 0 && mpEndMonth3 == 0 ){
		   mpMonthNotSet = true;
		} 	
		

		if (mpMonthNotSet && (mpBegindate1==0 && mpEnddate1!=0) || (mpBegindate1!=0 && mpEnddate1==0)) {
			submitFinished();
			alert("裝封1  使用區間 錯誤");
			return false;
		}
		
		if (mpMonthNotSet &&  (mpBegindate2==0 && mpEnddate2!=0) || (mpBegindate2!=0 && mpEnddate2==0)) {
			submitFinished();
			alert("裝封  使用區間2 錯誤");
			return false;
		}
		
		if (mpMonthNotSet &&  (mpBegindate3==0 && mpEnddate3!=0) || (mpBegindate3!=0 && mpEnddate3==0)) {
			submitFinished();
			alert("裝封  使用區間3  錯誤");
			return false;
		}				
		
		if (mpMonthNotSet && mpBegindate1 > mpEnddate1  ) {
			submitFinished();
			alert("裝封  使用區間1_開始日期  不能大於  區間1_結束日期");
			return false;
		}
	
		if (mpMonthNotSet && mpBegindate2 >0 && mpBegindate1 >0) {
			if (mpEnddate2 >= mpBegindate2) {
				if (mpBegindate2 >= mpBegindate1 && mpBegindate2 <= mpEnddate1) {
					submitFinished();
					alert("裝封  使用區間2_日期  不能跟區間1 重疊 ");
					return false;
				}
				
				if (mpEnddate2 >= mpBegindate1 && mpEnddate2 <= mpEnddate1) {
					submitFinished();alert("裝封  使用區間2_日期  不能跟區間1 重疊 ");
					return false;
				}
				if (mpBegindate2 <= mpBegindate1 && mpEnddate2 >= mpEnddate1) {
					submitFinished();alert("裝封  使用區間2_日期  不能跟區間1 重疊 ");
					return false;
				}
			} else { //跨31日
				if (!(mpBegindate2 > mpEnddate1 && mpEnddate2 < mpBegindate1) ) {
					submitFinished();alert("裝封  使用區間2_日期  不能跟區間1 重疊 ");
					return false;
				}			
			}
		}
		
		if (mpMonthNotSet && mpBegindate3 >0 && mpBegindate1 >0) {		
			if (mpEnddate3 >= mpBegindate3) {
				if (mpBegindate3 >= mpBegindate1 && mpBegindate3 <= mpEnddate1) {
					submitFinished();alert("裝封  使用區間3_日期  不能跟區間1 重疊 ");
					return false;
				}
				
				if (mpEnddate3 >= mpBegindate1 && mpEnddate3 <= mpEnddate1) {
					submitFinished();alert("裝封  使用區間3_日期  不能跟區間1 重疊 ");
					return false;
				}
				if (mpBegindate3 <= mpBegindate1 && mpEnddate3 >= mpEnddate1) {
					submitFinished();alert("裝封  使用區間3_日期  不能跟區間1 重疊 ");
					return false;
				}
			} else { //跨31日
				if (!(mpBegindate3 > mpEnddate1 && mpEnddate3 < mpBegindate1) ) {
					submitFinished();alert("裝封  使用區間3_日期  不能跟區間1 重疊 ");
					return false;
				}			
			}		
		}
		
		//區間2 跟 3 的比較		
		if (mpMonthNotSet && mpBegindate3 >0 && mpBegindate2 >0) {			
			if (mpEnddate2 >= mpBegindate2) {
				if (mpEnddate3 >= mpBegindate3) {
					if (mpBegindate3 >= mpBegindate2 && mpBegindate3 <= mpEnddate2) {
						submitFinished();alert("裝封  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
					
					if (mpEnddate3 >= mpBegindate2 && mpEnddate3 <= mpEnddate2) {
						submitFinished();alert("裝封  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
					if (mpBegindate3 <= mpBegindate2 && mpEnddate3 >= mpEnddate2) {
						submitFinished();alert("裝封  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
				} else { //跨31日
					if (!(mpBegindate3 > mpEnddate2 && mpEnddate3 < mpBegindate2) ) {
						submitFinished();alert("裝封  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}			
				}		
			} else { //跨31日
				if (mpEnddate3 >= mpBegindate3) {
					if (mpBegindate3 >= mpBegindate2 && mpBegindate3 <= mpEnddate2) {
						submitFinished();alert("裝封  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
					
					if (mpEnddate3 >= mpBegindate2 && mpEnddate3 <= mpEnddate2) {
						submitFinished();alert("裝封  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
					if (!(mpBegindate3 > mpEnddate2 && mpEnddate3 < mpBegindate2) ) {
						submitFinished();alert("裝封  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
					}
				} 	else { //跨31日
	
						submitFinished();alert("裝封  使用區間3_日期  不能跟區間2 重疊 ");
						return false;
		
				}	
			}
		}
		
		
		
		
		return true;
	});
	
	



	
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('jobcode.do?fid=checkPKExist', 
	        {jobCodeNo: $('#custNo').val() + $('#jobCodeId').val()},
	        function(ret) {
	        	if (ret.result=="NON_EXIST") {
	        		$('#msg_pk_exist').html("代號可以使用");
	        		$('#btnAdd').attr('disabled', false);
	        	}
	        	else {
	        		$('#msg_pk_exist').html("代號已經存在");
	        		$('#btnAdd').attr('disabled', true);
	           }
	        }
	       );
  	};
  	var chekJobCodeExist = function (){
  		var selfJobCode = $('#custNo').val() + $('#jobCodeId').val();
  		selfJobCode = selfJobCode.toUpperCase();
  		$('#damageJobCodeNo').val($('#damageJobCodeNo').val().toUpperCase());
  		if(selfJobCode === $('#damageJobCodeNo').val().toUpperCase()){
  			$('#job_code_exist').html("與自己工單代號相同時不需設定");
  			$('#damageJobCodeNo').val("");
  			$('#btnAdd').attr('disabled', false);
  			$('#btnEdit').attr('disabled', false);
  		}else if($('#damageJobCodeNo').val() !== ""){
	       $.getJSON('jobcode.do?fid=checkDamagePKExist', 
	           {jobCodeNo: $('#damageJobCodeNo').val() ,
	    	    custNo: $('#custNo').val()},
	           function(ret) {
	        	   if (ret.result=="NON_EXIST") {
	        		   $('#job_code_exist').html("此工單樣本不存在");
	        		   $('#btnAdd').attr('disabled', true);
	        		   $('#btnEdit').attr('disabled', true);
	        	   }else if(ret.result=="WRONG_TYPE") {
	        		   $('#job_code_exist').html("必須為相同客戶的工單樣本");
	        		   $('#btnAdd').attr('disabled', true);
	        		   $('#btnEdit').attr('disabled', true)
	               }else {
	        		   $('#job_code_exist').html("");
	        		   $('#btnAdd').attr('disabled', false);
	        		   $('#btnEdit').attr('disabled', false);
	              }
	           }
	         );
  	    }else{
 		   $('#job_code_exist').html("");
		   $('#btnAdd').attr('disabled', false);
  	    }
  	};
  	
  	checkFilePatternExist = function (){
	    $.getJSON('jobcode.do?fid=checkFilePatternExist', 
	        {
	    	    custNo: $('#custNo').val(), 
	    	    filename: $('#filename').val(), 
	    	    jobCodeNo: $('#custNo').val() + $('#jobCodeId').val()
	        },
	        function(ret) {	        	
	        	if (ret.result=="NON_EXIST") {	        		
	        		$('#filename_exist').text("");
	        	}else if($("#filename").val() != ""){
	        		$('#filename_exist').text("注意：" + ret.result + "有相同的file pattern");
	           }
	        }
	       );
  	};
  	
  	$('#jobCodeId').blur(checkPKExist);
  	$('#filename').blur(checkFilePatternExist);
  	$('#damageJobCodeNo').blur(chekJobCodeExist);
  	
  	
  	
	//取得客戶 LP/MP/PS/Return/LG/LC list
	getCustomerLists = function (){
		//LP list
	    $.getJSON('customer.do?fid=getCustomerLPList', 
	        {custNo: $('#custNo').val()},
	        function(json) {
	        	lplist = json;
	        	$("#lpNo1").find('option').remove();
	        	$("#lpNo1").append("<option value='' >請選擇</option>");
	        	$("#lpNo2").find('option').remove();
	        	$("#lpNo2").append("<option value='' >請選擇</option>");
	        	$("#lpNo3").find('option').remove();
	        	$("#lpNo3").append("<option value='' >請選擇</option>");

		        for(var i=0;i<json.length;i++){
		        	$("#lpNo1").append("<option value='"+json[i].lpNo+"' >"+json[i].lpNo+"</option>");
					$("#lpNo2").append("<option value='"+json[i].lpNo+"' >"+json[i].lpNo+"</option>");	
					$("#lpNo3").append("<option value='"+json[i].lpNo+"' >"+json[i].lpNo+"</option>");
		        }
	        });
	    //MP list
	    $.getJSON('customer.do?fid=getCustomerMPList', 
	        {custNo: $('#custNo').val()},
	        function(json) {
	        	mplist = json;
	        	$("#mpNo1").find('option').remove();
	        	$("#mpNo1").append("<option value='' >請選擇</option>");
	        	$("#mpNo2").find('option').remove();
	        	$("#mpNo2").append("<option value='' >請選擇</option>");
	        	$("#mpNo3").find('option').remove();
	        	$("#mpNo3").append("<option value='' >請選擇</option>");

		        for(var i=0;i<json.length;i++){
		        	$("#mpNo1").append("<option value='"+json[i].mpNo+"' >"+json[i].mpNo+"</option>");
					$("#mpNo2").append("<option value='"+json[i].mpNo+"' >"+json[i].mpNo+"</option>");	
					$("#mpNo3").append("<option value='"+json[i].mpNo+"' >"+json[i].mpNo+"</option>");
		        }
	        });
	    
	    //PS list
	    $.getJSON('customer.do?fid=getCustomerPSList', 
	        {custNo: $('#custNo').val()},
	        function(json) {
	        	pslist = json;
	        	$("#psNo").find('option').remove();
	        	$("#psNo").append("<option value='' >請選擇</option>");

		        for(var i=0;i<json.length;i++){
		        	$("#psNo").append("<option value='"+json[i].psNo+"' >"+json[i].psNo+"</option>");
		        }
	        });
	    
	    //LG list
	    $.getJSON('customer.do?fid=getCustomerLGList', 
	        {custNo: $('#custNo').val()},
	        function(json) {
	        	lglist = json;
	        	$("#lgNo").find('option').remove();
	        	$("#lgNo").append("<option value='' >請選擇</option>");

		        for(var i=0;i<json.length;i++){
		        	$("#lgNo").append("<option value='"+json[i].lgNo+"' >"+json[i].lgNo+"</option>");
		        }
	        });
	    
	    //return list
	    $.getJSON('customer.do?fid=getCustomerReturnList', 
	        {custNo: $('#custNo').val()},
	        function(json) {
	        	returnlist = json;
	        	$("#returnNo").find('option').remove();
	        	$("#returnNo").append("<option value='' >請選擇</option>");

		        for(var i=0;i<json.length;i++){
		        	$("#returnNo").append("<option value='"+json[i].returnNo+"' >"+json[i].returnNo+"</option>");
		        }
	        });
	    
	    //LC list
	    $.getJSON('customer.do?fid=getCustomerLCList', 
	        {custNo: $('#custNo').val()},
	        function(json) {
	        	lclist = json;
	        	$("#lcNo").find('option').remove();
	        	$("#lcNo").append("<option value='' >請選擇</option>");

		        for(var i=0;i<json.length;i++){
		        	$("#lcNo").append("<option value='"+json[i].lcNo+"' >"+json[i].lcNo+"</option>");
		        }
	        });
	    
	    
  	};
  	$('#custNo').change(getCustomerLists);

<% for (int i=1; i<=3; i++) {%>  	
	//更新 LPInfo01 內容
	getLpNo<%=i%> = function (){
		//alert($('#lpNo1').val() );
		for(var i=0;i<lplist.length;i++){
			//alert(lplist[i].lpNo );
			if (lplist[i].lpNo == $('#lpNo<%=i%>').val()) {
				$('#LPInfo0<%=i%>_prodmemo').html(lplist[i].prodmemo);
				$('#LPInfo0<%=i%>_printer').html(lplist[i].printer);
				$('#LPInfo0<%=i%>_printType').html(lplist[i].printType);
				$('#LPInfo0<%=i%>_pcode1').html(lplist[i].pcode1);
				$('#LPInfo0<%=i%>_pcode2').html(lplist[i].pcode2);
				$('#LPInfo0<%=i%>_pcode3').html(lplist[i].pcode3);
				$('#LPInfo0<%=i%>_pcode4').html(lplist[i].pcode4);				
				$('#LPInfo0<%=i%>_pcode5').html(lplist[i].pcode5);
				$('#LPInfo0<%=i%>_pcode6').html(lplist[i].pcode6);
				$('#LPInfo0<%=i%>_pcode7').html(lplist[i].pcode7);
				$('#LPInfo0<%=i%>_pcode8').html(lplist[i].pcode8);
				$('#LPInfo0<%=i%>_remark').html(lplist[i].remark);
			}
		}
  	};
  	$('#lpNo<%=i%>').change(getLpNo<%=i%>);
<%}%>  	


<% for (int i=1; i<=3; i++) {%>  	
	getMpNo<%=i%> = function (){
		for(var i=0;i<mplist.length;i++){
			if (mplist[i].mpNo == $('#mpNo<%=i%>').val()) {
				$('#MPInfo0<%=i%>_prodmemo').html(mplist[i].prodmemo);
				$('#MPInfo0<%=i%>_printCode').html(mplist[i].printCode);	


				if (mplist[i].stampSetFg) 			
					$('input[name="MPInfo0<%=i%>_stampSetFg"]')[0].checked = true;		
				else 
					$('input[name="MPInfo0<%=i%>_stampSetFg"]')[1].checked = true;
				
				if (mplist[i].zipFg) 			
					$('input[name="MPInfo0<%=i%>_zipFg"]')[0].checked = true;		
				else 
					$('input[name="MPInfo0<%=i%>_zipFg"]')[1].checked = true;

				if (mplist[i].mp1Iflag)
					$('#MPInfo0<%=i%>mp1Iflag').attr("checked", true);
				else
					$('#MPInfo0<%=i%>mp1Iflag').attr("checked", false);
					
				if (mplist[i].mp2Iflag)
					$('#MPInfo0<%=i%>mp2Iflag').attr("checked", true);
				else
					$('#MPInfo0<%=i%>mp2Iflag').attr("checked", false);
					
				if (mplist[i].mp3Iflag)
					$('#MPInfo0<%=i%>mp3Iflag').attr("checked", true);
				else
					$('#MPInfo0<%=i%>mp3Iflag').attr("checked", false);
					
				if (mplist[i].mp4Iflag)
					$('#MPInfo0<%=i%>mp4Iflag').attr("checked", true);
				else
					$('#MPInfo0<%=i%>mp4Iflag').attr("checked", false);															
									
				$('#MPInfo0<%=i%>_mp1Word').html(mplist[i].mp1Word);	
				$('#MPInfo0<%=i%>_mp2Word').html(mplist[i].mp2Word);
				$('#MPInfo0<%=i%>_mp3Word').html(mplist[i].mp3Word);
				$('#MPInfo0<%=i%>_mp4Word').html(mplist[i].mp4Word);																												
				$('#MPInfo0<%=i%>_remark').html(mplist[i].remark);					
			}
		}
  	};
  	$('#mpNo<%=i%>').change(getMpNo<%=i%>);
<%}%>


	
	//Change PSList
	getPsNo = function (){
		for(var i=0;i<pslist.length;i++){
			if (pslist[i].psNo == $('#psNo').val()) {
				$('#PSInfo01_prodmemo').html(pslist[i].prodmemo);
				$('#PSInfo01_psType').html(pslist[i].psType);
				$('#PSInfo01_zipFg').html(pslist[i].zipFg);
				$('#PSInfo01_remark').html(pslist[i].remark);					
				
			}
		}
  	};
  	$('#psNo').change(getPsNo);
  	
	//Change LGList
	getLgNo = function (){
		for(var i=0;i<lglist.length;i++){
			if (lglist[i].lgNo == $('#lgNo').val()) {
				$('#LGInfo01_prodmemo').html(lglist[i].progNm);
				$('#LGInfo01_mailToAreaDisplay').html(lglist[i].mailToAreaDisplay);	
				
				//設定  郵資單總類 = 郵資單. 郵資單總類
				window.document.myform.codeLgType.selectedIndex=0;
				for (var ii=0; ii< window.document.myform.codeLgType.length ; ii++) {
					if ( window.document.myform.codeLgType.options[ii].text ==lglist[i].lgType )
						window.document.myform.codeLgType.selectedIndex= ii;
				}	
				
				//設定  交寄方式 = 郵資單. 交寄方式
				window.document.myform.codeMailCategory.selectedIndex=0;
				for (var ii=0; ii< window.document.myform.codeMailCategory.length ; ii++) {
					if ( window.document.myform.codeMailCategory.options[ii].text ==lglist[i].mailCategory )
						window.document.myform.codeMailCategory.selectedIndex= ii;
				}	
				
								
				//設定  交寄郵局或公司 = 郵資單.交寄郵局或公司
				window.document.myform.codeMailToPostoffice.selectedIndex =0;
				for (var ii=0; ii< window.document.myform.codeMailToPostoffice.length ; ii++) {
					if ( window.document.myform.codeMailToPostoffice.options[ii].text ==lglist[i].mailToPostoffice )
						window.document.myform.codeMailToPostoffice.selectedIndex= ii;
				}				 
				$('#codeLgType').attr("disabled", true);
				$('#codeMailToPostoffice').attr("disabled", true);
				$('#codeMailCategory').attr("disabled", true);


			}
		}
  	};
  	$('#lgNo').change(getLgNo);
  	
	//Change ReturnList
	getReturnNo = function (){
		for(var i=0;i<returnlist.length;i++){
			if (returnlist[i].returnNo == $('#returnNo').val()) {
				$('#ReturnInfo01_userName').html(returnlist[i].userName);
				$('#ReturnInfo01_userCompany').html(returnlist[i].userCompany);
				$('#ReturnInfo01_returnAddress').html(returnlist[i].returnAddress);
				$('#ReturnInfo01_userTel').html(returnlist[i].userTel);					
			}
		}
  	};
  	$('#returnNo').change(getReturnNo);
  	
	//Change LGCList
	getLcNo = function (){
		for(var i=0;i<lclist.length;i++){
			if (lclist[i].lcNo == $('#lcNo').val()) {
				$('#LCInfo01_prodmemo').html(lclist[i].progNm);			
			}
		}
  	};
  	$('#lcNo').change(getLcNo);
  	
  	
  	//若開立郵資單 已被勾選, 以下輸入控制項目 壓為 disabled 
  	if ($('#useLg').attr("checked")) {
		$('#codeLgType').attr("disabled", true);
		$('#codeMailToPostoffice').attr("disabled", true);
		$('#codeMailCategory').attr("disabled", true);  	
  	}
  	
	//開立郵資單 Change
	useLg = function (){
	  	if ($('#useLg').attr("checked")) {
			$('#codeLgType').attr("disabled", true);
			$('#codeMailToPostoffice').attr("disabled", true);
			$('#codeMailCategory').attr("disabled", true);  	
	  	} else {
			$('#codeLgType').attr("disabled", false);
			$('#codeMailToPostoffice').attr("disabled", false);
			$('#codeMailCategory').attr("disabled", false);  		  	
	  	}
  	};
  	$('#useLg').change(useLg);
  	$('#mailType').click(eddChange);  	
  	$('#isEdd').click(eddChange);	
});
</script>

	<form name="myform" id="myform" action="jobcode.do" method="post">	        	
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
		<input type="hidden" name="fid" id="fid" value="<%=fid %>" />
		
		
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">工單樣本維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<table border=0 cellSpacing=0 cellPadding=0 width="95%" class="editTableBKColor"  id="table8">
<!-- Line 1 -->			
			<tr>
                <td  width="20%">
				<table border=0 cellSpacing=1 cellPadding=6 width="100%" align=left id="table4">
					<tr>
					<td class="editTableLabelCellBKColor"  width="70"><font color="#FF0000">*</font>客戶代號</td>
					<td class="editTableContentCellBKColor" align=left width="82">
					 <SELECT id="custNo"  name="custNo" size="1" <%=disabledVIEW %> <%=disabledPK %> class="required" >
					 <option value="" >請選擇</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";
	if (null!= jobcode.getCustomer() && _customer.getCustNo().equalsIgnoreCase( jobcode.getCustomer().getCustNo()) ) 
		selectString="selected"; 
%>					 
							<option value="<%=_customer.getCustNo() %>" <%=selectString %> >
								<%=_customer.getCustNo() + "-" + _customer.getEasyName() %>
							</option>
<% }%>	
					</SELECT>                            
					</td>
					<td class="editTableLabelCellBKColor" width="43" >列印</td>
					<td class="editTableContentCellBKColor" align=left width="125">
					<input type="checkbox" onclick="javascript:hiddenTable('LPInfo')" id="isLp" name="isLp" value="1" <% if (null!= jobcode.getIsLp() && jobcode.getIsLp()) out.println("checked");%> <%=disabledVIEW %>>Laser Printing
					</td>
					<td class="editTableLabelCellBKColor"  width="70" ><font color="#FF0000" size="2">*</font>交寄時間</td>
					<td class="editTableContentCellBKColor" align=left width="67" >
					<SELECT id="dispatchTime" size=1 name="dispatchTime" <%=disabledVIEW %> >
					<OPTION value="AM">AM</OPTION>
					<OPTION value="PM">PM</OPTION>
					</SELECT>
					</td>
					<td class="editTableLabelCellBKColor"  width="93" >有效工作資料</td>
					<td class="editTableContentCellBKColor" align=left width="50" >
					   <input type="checkbox" onchange="checkAutoDelete(this)"  id="isEnabledContract" name="isEnabledContract" value="1" <% if (null != jobcode.getIsEnabledContract() && jobcode.getIsEnabledContract()) out.println("checked");%> <%=disabledVIEW %>>
					   <input type="hidden" name="autoDelete" id="autoDelete" value="${jobcode.autoDelete? '1': '0'}" />
					</td>
					<td class="editTableContentCellBKColor" align=left rowspan="5" valign="top" width="260">
<!-- 工單右邊區塊 -->
<%if (actionName.equalsIgnoreCase("jobcode")) { %>
					<jsp:include page="/entityMgr/JobCodeEdit_Cycle.inc.jsp" />
<% } else  {%>				
					<jsp:include page="/entityMgr/JobBagEdit_AccountInfo.inc.jsp" />
<% } %>						
							
					</td>
					</tr> 
<!-- Line 2 -->					
					<tr>
					<td class="editTableLabelCellBKColor"  width="70"><font color="#FF0000" size="2">*</font>工作代號</td>
					<td class="editTableContentCellBKColor" align=left width="82">
					<INPUT id="jobCodeId" size="4"  name="jobCodeId" value="<%=jobcode.getJobCodeId() %>"  <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="4" minlength="4"><span id="msg_pk_exist" style="color:red"></span>
					</td>
					<td class="editTableLabelCellBKColor" width="43" >裝封</td>
					<td class="editTableContentCellBKColor" align=left width="125">
                              <table id="table24">
                                <tr>
                                <td>
								<INPUT onclick="javascript:changeMP('MPInfo')" type="radio" id="mpDmPs" name="mpDmPs"  value="MP" <% if(null!=jobcode.getMpDmPs() && jobcode.getMpDmPs().equalsIgnoreCase("MP") ) out.println("checked");%> <%=disabledVIEW %> class="required"/>													
								</td>
                                <td>
								<INPUT onclick="javascript:changeMP('PSInfo')" type="radio" id="mpDmPs1" name="mpDmPs"  value="PS" <% if(null!=jobcode.getMpDmPs() && jobcode.getMpDmPs().equalsIgnoreCase("PS") ) out.println("checked");%> <%=disabledVIEW %> class="required"/>                                
								</td>
                                <td>
								<INPUT onclick="javascript:changeMP('DMInfo')" type="radio" id="mpDmPs2" name="mpDmPs"  value="DM" <% if(null!=jobcode.getMpDmPs() && jobcode.getMpDmPs().equalsIgnoreCase("DM") ) out.println("checked");%> <%=disabledVIEW %> class="required"/>                                
								</td>
                                <td>
								<INPUT onclick="javascript:changeMP('NULLInfo')" type="radio" id="mpDmPs3" name="mpDmPs"  value="" <% if(null==jobcode.getMpDmPs() ||  jobcode.getMpDmPs().equalsIgnoreCase("") ) out.println("checked");%> <%=disabledVIEW %> class="required"/>                                
								</td> 
                                </tr>
                                <tr> 
                                <td>MP</td>
                                <td>PS</td>
                                <td>DM</td>
                                <td>NULL</td>
                                </tr>
                                </table>
					</td>
					<td class="editTableLabelCellBKColor" width="43" >停止產生工單區間</td>
					<td class="editTableContentCellBKColor" align=left colspan="3" >
					      <INPUT id="noJobBagFromStr" maxLength=15 size=10 name="noJobBagFromStr" readonly="readonly">
                              <img border="0" alt="日期選擇輔助工具" id="noJobBagFrom_datepicker" src="images/calendar2.gif" />
                          <script language="javascript">
		                         Calendar.setup({
			                              inputField : "noJobBagFromStr",						
		                                  button : "noJobBagFrom_datepicker"
		                         });
                          </script>
                          &nbsp; ~ &nbsp;
					      <INPUT id="noJobBagEndStr" maxLength=15 size=10 name="noJobBagEndStr" readonly="readonly">
                              <img border="0" alt="日期選擇輔助工具" id="noJobBagEnd_datepicker" src="images/calendar2.gif" />
                          <script language="javascript">
		                         Calendar.setup({
			                              inputField : "noJobBagEndStr",						
		                                  button : "noJobBagEnd_datepicker"
		                         });
                          </script>
					
					</td>
                    </tr>
<!-- Line 3 -->	                            
					<tr>
					    <td class="editTableLabelCellBKColor"   width="70" rowspan="2"> <font color="#FF0000" size="2">*</font>工作種類</td>
					    <td class="editTableContentCellBKColor" align=left width="82" rowspan="2">			
<SELECT name="jobCodeType" id="jobCodeType" size="1" <%=disabledVIEW %> class="required">
<option value="" >請選擇</option>
<% for (int i=0; i<jobcodeTypeList.size(); i++) {
	Code _code = (Code)jobcodeTypeList.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByCodeJobCodeType() && _code.getId().compareTo(jobcode.getCodeByCodeJobCodeType().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT> 

					
					    </td>
					    <td class="editTableLabelCellBKColor" width="43" >交寄</td>
					    <td class="editTableContentCellBKColor" align=left width="125">
					       <input type="checkbox" onclick="javascript:hiddenTable('LGInfo')" id="isLg" name="isLg" value="1" <% if (null!= jobcode.getIsLg() && jobcode.getIsLg()) out.println("checked");%> <%=disabledVIEW %>>Dispatch
					    </td>
					
					    <td class="editTableLabelCellBKColor"  width="70" ><font color="#FF0000" size="2">*</font>Deadline</td>
					    <td class="editTableContentCellBKColor" align=left width="67" >
<SELECT name="deadTime" id="deadTime" size="1" <%=disabledVIEW %> class="required">
<option value="" >請選擇</option>
<% for (int i=0; i<deadlineList.size(); i++) {
	Code _code = (Code)deadlineList.get(i);
	String selectString = "";
	if (null!= jobcode.getDeadTime() && _code.getCodeValueTw().equalsIgnoreCase(jobcode.getDeadTime())) 
		selectString="selected";
%>					 
							<option value="<%=_code.getCodeValueTw() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>Hr
							</option>
<% }%>	
</SELECT>
					
					   </td>
					   <td class="editTableLabelCellBKColor"  width="93" >
					                  需要客戶確認
                       </td>
					   <td class="editTableContentCellBKColor" align=left width="50" >
					      <input type="checkbox"  id="custConfirm" name="custConfirm" value="1" <% if (null != jobcode.getCustConfirm() && jobcode.getCustConfirm()) out.println("checked");%> <%=disabledVIEW %>>
					   </td>					   
					</tr>
					
					<tr>
					   <td class="editTableLabelCellBKColor" width="43"  >寄送型態</td>
					   <td class="editTableContentCellBKColor" align=left width="125" >
					      <input type="checkbox" onclick="javascript:hiddenTable('EDDInfo')" id="isEdd" name="isEdd" value="1" <% if (null!= jobcode.getIsEdd() && jobcode.getIsEdd()) out.println("checked");%> <%=disabledVIEW %> />EDD
					      &nbsp;					      
					      <input type="checkbox"  id="mailType" name="mailType" value="mail" <%=disabledVIEW %> />紙本
					   </td>
					   <td class="editTableLabelCellBKColor"   ><font color="#FF0000">*</font>檔名規則</td>
					   <td class="editTableContentCellBKColor" align=left width="100"  >
						   <input id="filename" size="10" name="filename" value="<%=Util.getStringFormat( jobcode.getFilename() ) %>" <%=disabledVIEW %> /><span id="filename_exist" style="color:red"></span>
					   </td>
					   <td class="editTableLabelCellBKColor"   >限定頁數</td>
					   <td class="editTableContentCellBKColor" align=left  >
						   <input id="fromPages" size="2" name="fromPages" value='<%=jobcode.getFromPages() == null ? "" : jobcode.getFromPages()%>' <%=disabledVIEW %> />
						   ~
						   <input id="endPages" size="2" name="endPages" value='<%=jobcode.getEndPages() == null ? "" : jobcode.getEndPages()%>' <%=disabledVIEW %> />
					   </td>
					   					
					</tr>
					
<!-- Line 4 -->					
					<tr>
					<td class="editTableLabelCellBKColor"   width="70"><font color="#FF0000" size="2">*</font>活動名稱</td>
					<td class="editTableContentCellBKColor" align=left width="276"  colspan="3">
					<input id="progNm" maxLength=40 size=40 name="progNm" value="<%=Util.getStringFormat( jobcode.getProgNm() ) %>" <%=disabledVIEW %> />
					</td>
					<td class="editTableLabelCellBKColor">
					       Damage轉其它工單
                    </td>
					<td class="editTableContentCellBKColor" align=left >
					   <input size="7" id="damageJobCodeNo" name="damageJobCodeNo"  value='<%=jobcode.getDamageJobCodeNo() == null? "" : jobcode.getDamageJobCodeNo() %>' <%=disabledVIEW %>>
					   <span id="job_code_exist" style="color:red"></span>
					</td>
					<td class="editTableLabelCellBKColor"  width="70" >客戶單位<BR>(富邦專用欄位)</td>
					<td class="editTableContentCellBKColor" align=left width="50"  >
						<input id="customerDept" size="10" name="customerDept" value="<%=Util.getStringFormat( jobcode.getCustomerDept() ) %>" <%=disabledVIEW %> />
					</td>					
					</tr>
				</table>
				
				</td>
				</tr>
				</table>
				
<!-- 中間空白行-->	
				<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr><td height=10 ></td></tr>
                </table>

<!-- 中間區塊-->                
				<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
<!-- LP Start-->				
				<td width="33%" valign="top" >
			
				<table border="0" width="100%" id="LPInfo" <% if (null!= jobcode.getIsLp() && jobcode.getIsLp()) out.println("style=\"visibility: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
				<tr>
					<td bgcolor="#006F3F"><font size="2" color="#FFFFFF">列印</font></td>
				</tr>
				<tr>
					<td>
<!-- LP Area Start-->				  
<% for (int i=1;i<=3;i++) {
	String displayStyle="";
	if (i==1)
		displayStyle="style=\"DISPLAY: visible\"" ;
	else
		displayStyle="style=\"DISPLAY: none\"" ;

%>
                  <table border=0 cellSpacing=1 cellPadding=1 width="100%" bgColor=#006f3f id="table48">
                    <TBODY>
                    <tr>
                      <td bgColor="#ffffff">
						<table border="0" width="100%" id="table49" bgcolor="#DFF4DD">
						<tr>
							<td width="23%"><a href="javascript:expand('LPInfo0<%=i %>')">+</a>產品代號</td>
							<td width="77%">
							使用區間:&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="lp<%=i%>JudgeBy" value="cycleDate" />Cycle Date&nbsp; <input type="radio" name="lp<%=i%>JudgeBy" value="systemDate" />系統日 &nbsp; <input type="radio" name="lp<%=i%>JudgeBy" value="receivDate" />轉檔日
							</td> 
						</tr>
						<tr>
							<td width="23%">
							
							<SELECT name="lpNo<%=i %>" id="lpNo<%=i %>" <%=disabledVIEW %>>
							<option value="">請選擇</option>
<% 
	LpinfoJSON _selectJON = new LpinfoJSON() ;	
	if (null != lpinfoJSONlist) {

		for(int j=0; j<lpinfoJSONlist.size();j++) {
			String selectString="";
			LpinfoJSON _jons = (LpinfoJSON)lpinfoJSONlist.get(j);
	
			if ( (i==1 && null != jobcode.getLpinfoByIdfLpNo1() && _jons.getLpNo().equalsIgnoreCase(jobcode.getLpinfoByIdfLpNo1().getLpNo()) )  ||
				 (i==2 && null != jobcode.getLpinfoByIdfLpNo2() && _jons.getLpNo().equalsIgnoreCase(jobcode.getLpinfoByIdfLpNo2().getLpNo()) )  ||
				 (i==3 && null != jobcode.getLpinfoByIdfLpNo3() && _jons.getLpNo().equalsIgnoreCase(jobcode.getLpinfoByIdfLpNo3().getLpNo())) ) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON,_jons);
			}
			out.println("<option value=\""+_jons.getLpNo()  + "\" "+selectString + ">"+_jons.getLpNo() +"</option>");				
	
		}
	}
%>							
                    		</SELECT>
                    		   <a href="#" onclick="toEditLocation('lpinfo', 'lpNo<%=i %>', 'lpNo', true)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a> 
                    		</td>
							<td width="77%">
							<SELECT name="lpBeginMonth<%=i %>" id="lpBeginMonth<%=i %>" size="1" <%=disabledVIEW %>>
							    <option value="0" >全部</option>
							    <option value="1" >1</option>
							    <option value="2" >2</option>
							    <option value="3" >3</option>
							    <option value="4" >4</option>
							    <option value="5" >5</option>
							    <option value="6" >6</option>
							    <option value="7" >7</option>
							    <option value="8" >8</option>
							    <option value="9" >9</option>
							    <option value="10" >10</option>
							    <option value="11" >11</option>
							    <option value="12" >12</option>
							</SELECT>
							月
							<SELECT name="lpBegindate<%=i %>" id="lpBegindate<%=i %>" size="1" <%=disabledVIEW %>>
							</SELECT>日
							 ~ 
							<SELECT name="lpEndMonth<%=i %>" id="lpEndMonth<%=i %>" size="1" <%=disabledVIEW %>>
							    <option value="0" >全部</option>
							    <option value="1" >1</option>
							    <option value="2" >2</option>
							    <option value="3" >3</option>
							    <option value="4" >4</option>
							    <option value="5" >5</option>
							    <option value="6" >6</option>
							    <option value="7" >7</option>
							    <option value="8" >8</option>
							    <option value="9" >9</option>
							    <option value="10" >10</option>
							    <option value="11" >11</option>
							    <option value="12" >12</option>
							</SELECT>
							月
							<SELECT name="lpEnddate<%=i %>" id="lpEnddate<%=i %>" size="1" <%=disabledVIEW %>> 

							</SELECT>
							日
							</td>
						</tr>
						
						<tr>
							<td colspan="2">

			                  <table id="LPInfo0<%=i %>" width="100%" <%=displayStyle %>>
			                    <TBODY>
			                    <tr>
			                      <td width="15%" bgcolor="#DFF4DD">產品說明</td>
			                      <td width="85%" colspan="3"><div id="LPInfo0<%=i %>_prodmemo" ><%=Util.getStringFormat( _selectJON.getProdmemo()) %></div></td>
			                      </tr>
								<tr>
			                      <td width="15%" bgcolor="#DFF4DD">印表機類型</td>
			                      <td width="85%" colspan="3"><div id="LPInfo0<%=i %>_printer" ><%=Util.getStringFormat(_selectJON.getPrinter())%></div></td>
			                      </tr>
								<tr>
			                      <td width="15%" bgcolor="#DFF4DD">列印方式</td>
			                      <td width="85%" colspan="3"><div id="LPInfo0<%=i %>_printType" ><%=Util.getStringFormat(_selectJON.getPrintType()) %></div></td>
			                      </tr>
								<tr>
			                      <td width="15%" bgcolor="#DFF4DD">物料一</td>
			                      <td width="35%"><div id="LPInfo0<%=i %>_pcode1" ><%=Util.getStringFormat(_selectJON.getPcode1()) %></div></td>
			                      <td width="15%" bgcolor="#DFF4DD">物料二</td>
			                      <td width="35%"><div id="LPInfo0<%=i %>_pcode2" ><%=Util.getStringFormat(_selectJON.getPcode2()) %></div></td>
			                    </tr>								
								<tr>
			                      <td width="15%" bgcolor="#DFF4DD">物料三</td>
			                      <td width="35%"><div id="LPInfo0<%=i %>_pcode3" ><%=Util.getStringFormat(_selectJON.getPcode3()) %></div></td>
			                      <td width="15%" bgcolor="#DFF4DD">物料四</td>
			                      <td width="35%"><div id="LPInfo0<%=i %>_pcode4" ><%=Util.getStringFormat(_selectJON.getPcode4()) %></div></td>
			                    </tr>
			                    <tr>
			                      <td width="15%" bgcolor="#DFF4DD">物料五</td>
			                      <td width="35%"><div id="LPInfo0<%=i %>_pcode5" ><%=Util.getStringFormat(_selectJON.getPcode5()) %></div></td>
			                      <td width="15%" bgcolor="#DFF4DD">物料六</td>
			                      <td width="35%"><div id="LPInfo0<%=i %>_pcode6" ><%=Util.getStringFormat(_selectJON.getPcode6()) %></div></td>
			                    </tr>								
								<tr>
			                      <td width="15%" bgcolor="#DFF4DD">物料七</td>
			                      <td width="35%"><div id="LPInfo0<%=i %>_pcode7" ><%=Util.getStringFormat(_selectJON.getPcode7()) %></div></td>
			                      <td width="15%" bgcolor="#DFF4DD">物料八</td>
			                      <td width="35%"><div id="LPInfo0<%=i %>_pcode8" ><%=Util.getStringFormat(_selectJON.getPcode8()) %></div></td>
			                    </tr>
			                    <tr>
			                      <TH colSpan="4" align=left >特別注意事項</TH></tr>
			                    <tr>
			                      <td colSpan="4" ><TEXTAREA cols="50" id="LPInfo0<%=i %>_remark" rows="4" <%=disabledVIEW %>> <%=Util.getStringFormat(_selectJON.getRemark()) %></TEXTAREA></td></tr></TBODY>
			                      </table>
							

							
							</td>
						</tr>
					</table>
						</td></tr></TBODY></table>
<%} %>						

						
<!-- LP  本日公告-->						
					<table id="table63" width="100%" >
                    <TBODY>
                    <tr>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></tr>
                    <tr>
                      <td width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="lpNote"  id="lpNote" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getLpNote()) %></TEXTAREA></td></tr></TBODY>
						</table>
					</td>
				</tr>
			</table>
			
<!--LP end -->
			</td>
			
			
			
			<td width="33%" valign="top">
<!--MP Start-->

			<table border="0" width="100%" id="MPInfo" <% if (null!= jobcode.getMpDmPs() && ( jobcode.getMpDmPs().equalsIgnoreCase("MP")  )) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"DISPLAY: none\" ");%>>
				<tr>
					<td bgcolor="#006F3F"><font size="2" color="#FFFFFF">裝封方式</font></td>
				</tr>
				
				<tr>
					<td>
<!-- MP Area Start-->				  
<% for (int i=1;i<=3;i++) {
	String displayStyle="";
	if (i==1)
		displayStyle="style=\"DISPLAY: visible\"" ;
	else
		displayStyle="style=\"DISPLAY: none\"" ;

%>
                  <table border=0 cellSpacing=1 cellPadding=1 width="100%" bgColor=#006f3f id="table48">
                    <TBODY>
                    <tr>
                      <td bgColor="#ffffff">
						<table border="0" width="100%" id="table49" bgcolor="#DFF4DD">
						<tr>
							<td width="23%"><a href="javascript:expand('MPInfo0<%=i %>')">+</a>產品代號</td>
							<td width="77%">
							使用區間:&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="mp<%=i%>JudgeBy" value="cycleDate" />Cycle Date &nbsp; <input type="radio" name="mp<%=i%>JudgeBy" value="systemDate" />系統日 &nbsp; <input type="radio" name="mp<%=i%>JudgeBy" value="receivDate" />轉檔日
							</td>
						</tr>
						<tr>
							<td width="23%">
							<SELECT name="mpNo<%=i %>" id="mpNo<%=i %>" <%=disabledVIEW %>>
							<option>請選擇</option>
<% 
	MpinfoJSON _selectJON = new MpinfoJSON() ;	
	if (null != mpinfoJSONlist) {
		for(int j=0; j<mpinfoJSONlist.size();j++) {
			MpinfoJSON _jons = (MpinfoJSON)mpinfoJSONlist.get(j);
			String selectString="";
			if ( (i==1 && null != jobcode.getMpinfoByIdfMpNo1() && _jons.getMpNo().equalsIgnoreCase(jobcode.getMpinfoByIdfMpNo1().getMpNo()) )  ||
				 (i==2 && null != jobcode.getMpinfoByIdfMpNo2() && _jons.getMpNo().equalsIgnoreCase(jobcode.getMpinfoByIdfMpNo2().getMpNo()) )  ||
				 (i==3 && null != jobcode.getMpinfoByIdfMpNo3() && _jons.getMpNo().equalsIgnoreCase(jobcode.getMpinfoByIdfMpNo3().getMpNo())) ) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON,_jons);
			}
			out.println("<option value=\""+_jons.getMpNo()  + "\" "+selectString + ">"+_jons.getMpNo() +"</option>");
		}
	}
%>							
                    		</SELECT> 
                    		   <a href="#" onclick="toEditLocation('mpinfo', 'mpNo<%=i %>', 'mpNo', true)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a>
                    		</td>
							<td width="77%">
							<SELECT name="mpBeginMonth<%=i %>" id="mpBeginMonth<%=i %>" size="1" <%=disabledVIEW %>>
							    <option value="0" >全部</option>
							    <option value="1" >1</option>
							    <option value="2" >2</option>
							    <option value="3" >3</option>
							    <option value="4" >4</option>
							    <option value="5" >5</option>
							    <option value="6" >6</option>
							    <option value="7" >7</option>
							    <option value="8" >8</option>
							    <option value="9" >9</option>
							    <option value="10" >10</option>
							    <option value="11" >11</option>
							    <option value="12" >12</option>
							</SELECT>
							月
							<SELECT name="mpBegindate<%=i %>" id="mpBegindate<%=i %>" size="1" <%=disabledVIEW %>>
							</SELECT>日
							 ~
							<SELECT name="mpEndMonth<%=i %>" id="mpEndMonth<%=i %>" size="1" <%=disabledVIEW %>>
							    <option value="0" >全部</option>
							    <option value="1" >1</option>
							    <option value="2" >2</option>
							    <option value="3" >3</option>
							    <option value="4" >4</option>
							    <option value="5" >5</option>
							    <option value="6" >6</option>
							    <option value="7" >7</option>
							    <option value="8" >8</option>
							    <option value="9" >9</option>
							    <option value="10" >10</option>
							    <option value="11" >11</option>
							    <option value="12" >12</option>
							</SELECT>
							月 
							<SELECT name="mpEnddate<%=i %>" id="mpEnddate<%=i %>" size="1" <%=disabledVIEW %>> 
							</SELECT>
							日
							</td>
						</tr>
						
						<tr>
							<td colspan="2">

			                  <table id="MPInfo0<%=i %>" width="100%" <%=displayStyle %>>
			                    <TBODY>
			                    <tr>
			                      <td width="30%" bgcolor="#DFF4DD">產品說明</td>
			                      <td width="70%"><div id="MPInfo0<%=i %>_prodmemo" ><%=Util.getStringFormat( _selectJON.getProdmemo()) %></div></td>
			                      </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">物料版號</td>
			                      <td width="70%"><div id="MPInfo0<%=i %>_printCode" ><%=Util.getStringFormat(_selectJON.getPrintCode())%></div></td>
			                      </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">郵戳設定</td>
			                      <td width="70%">
									<INPUT type="radio" id="MPInfo0<%=i %>_stampSetFg" name="MPInfo0<%=i %>_stampSetFg" value="1"  readonly disabled class="disabled" <% if (null!= _selectJON.getStampSetFg() && _selectJON.getStampSetFg()) out.println("checked");%> /> 打郵戳
									<INPUT type="radio" id="MPInfo0<%=i %>_stampSetFg" name="MPInfo0<%=i %>_stampSetFg" value="0"  readonly disabled class="disabled" <% if (null!= _selectJON.getStampSetFg() && !_selectJON.getStampSetFg()) out.println("checked");%>/> 不打郵戳
							      </td>
			                      </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">分區</td>
			                      <td width="70%">
									<INPUT type="radio" id="MPInfo0<%=i %>_zipFg" name="MPInfo0<%=i %>_zipFg" value="1"  readonly disabled class="disabled" <% if (null!= _selectJON.getZipFg() && _selectJON.getZipFg()) out.println("checked");%> /> 分區
									<INPUT type="radio" id="MPInfo0<%=i %>_zipFg" name="MPInfo0<%=i %>_zipFg" value="0"  readonly disabled class="disabled" <% if (null!= _selectJON.getZipFg() && !_selectJON.getZipFg()) out.println("checked");%>/> 不分區
							      </td>
			                      </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">插件二</td>
			                      <td width="70%">
			                      <input type="checkbox" id="MPInfo0<%=i %>_mp1Iflag" name="MPInfo0<%=i %>_mp1Iflag" value="1" disabled class="disabled" <% if (null!= _selectJON.getMp1Iflag() && _selectJON.getMp1Iflag()) out.println("checked");%>>
			                      <span id="MPInfo0<%=i %>_mp1Word" > <%=Util.getStringFormat(_selectJON.getMp1Word()) %></span>
								  </td>
			                    </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">插件三</td>
			                      <td width="70%">
			                      <input type="checkbox" id="MPInfo0<%=i %>_mp2Iflag" name="MPInfo0<%=i %>_mp2Iflag" value="1" disabled class="disabled" <% if (null!= _selectJON.getMp2Iflag() && _selectJON.getMp2Iflag()) out.println("checked");%>>
			                      <span id="MPInfo0<%=i %>_mp2Word" > <%=Util.getStringFormat(_selectJON.getMp2Word()) %></span>
								  </td>

			                    </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">插件四</td>
			                      <td width="70%">
			                      <input type="checkbox" id="MPInfo0<%=i %>_mp3Iflag" name="MPInfo0<%=i %>_mp3Iflag" value="1" disabled class="disabled" <% if (null!= _selectJON.getMp3Iflag() && _selectJON.getMp3Iflag()) out.println("checked");%>>
			                      <span id="MPInfo0<%=i %>_mp3Word" > <%=Util.getStringFormat(_selectJON.getMp3Word()) %></span>
								  </td>

			                    </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">插件五</td>
			                      <td width="70%">
			                      <input type="checkbox" id="MPInfo0<%=i %>_mp4Iflag" name="MPInfo0<%=i %>_mp4Iflag" value="1" disabled class="disabled" <% if (null!= _selectJON.getMp4Iflag() && _selectJON.getMp4Iflag()) out.println("checked");%>>
			                      <span id="MPInfo0<%=i %>_mp4Word" > <%=Util.getStringFormat(_selectJON.getMp4Word()) %></span>
								  </td>

			                    </tr>
			                    <tr>
			                      <TH colSpan=2 align=left width="30%">特別注意事項</TH></tr>
			                    <tr>
			                      <td colSpan=2 width="30%"><TEXTAREA cols="50" id="MPInfo0<%=i %>_remark" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat(_selectJON.getRemark()) %></TEXTAREA></td></tr></TBODY>
			                      </table>
							

							
							</td>
						</tr>
					</table>
						</td></tr></TBODY></table>
<%} %>						

						
<!-- MP  本日公告-->						
					<table id="table63" width="100%" >
                    <TBODY>
                    <tr>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></tr>
                    <tr>
                      <td width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="mpNote"  id="lpNote" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getMpNote() )%></TEXTAREA></td></tr></TBODY>
						</table>
					</td>
				</tr>
			</table>
			
<!--MP end -->

<!--PSInfoList Start-->

			<table border="0" width="100%" id="PSInfo" <% if (null!= jobcode.getMpDmPs() && (  jobcode.getMpDmPs().equalsIgnoreCase("PS") )) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"DISPLAY: none\" ");%>>
				<tr>
					<td bgcolor="#006F3F"><font size="2" color="#FFFFFF">壓封方式 PSInfo</font></td>
				</tr>
				
				<tr>
					<td>

                  <table border=0 cellSpacing=1 cellPadding=1 width="100%" bgColor=#006f3f id="table48">
                    <TBODY>
                    <tr>
                      <td bgColor="#ffffff">
						<table border="0" width="100%" id="table49" bgcolor="#DFF4DD">
						<tr>
							<td width="35%"><a href="javascript:expand('PSInfo01')">+</a>產品代號</td>
						</tr>
						<tr>
							<td width="35%">
							<SELECT name="psNo" id="psNo" <%=disabledVIEW %>>
							<option>請選擇</option>
<% 
	PsinfoJSON _selectJON = new PsinfoJSON() ;	
	if (null != psinfoJSONlist) {
		for(int j=0; j<psinfoJSONlist.size();j++) {
			PsinfoJSON _jons = (PsinfoJSON)psinfoJSONlist.get(j);
			String selectString=""; 
			if (null != jobcode.getPsinfo() && _jons.getPsNo().equalsIgnoreCase(jobcode.getPsinfo().getPsNo())) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON,_jons);
			}
			out.println("<option value=\""+_jons.getPsNo()  + "\" "+selectString + ">"+_jons.getPsNo() +"</option>");
		}
	}
%>							
                    		</SELECT>
                    		   <a href="#" onclick="toEditLocation('psinfo', 'psNo', 'psNo', true)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a> 
                    		</td>
							<td width="61%">

						</tr>
						
						<tr>
							<td colspan="2">

			                  <table id="PSInfo01" width="100%" >
			                    <TBODY>
			                    <tr>
			                      <td width="30%" bgcolor="#DFF4DD">產品說明</td>
			                      <td width="70%"><div id="PSInfo01_prodmemo" ><%=Util.getStringFormat( _selectJON.getProdmemo()) %></div></td>
			                      </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">摺疊方式</td>
			                      <td width="70%"><div id="PSInfo01_psType" ><%=Util.getStringFormat(_selectJON.getPsType())%></div></td>
			                      </tr>
								<tr>
			                      <td width="30%" bgcolor="#DFF4DD">分區</td>
			                      <td width="70%"><div id="PSInfo01_zipFg" ><%=_selectJON.getZipFg() %></div></td>
			                      </tr>

			                    <tr>
			                      <TH colSpan=2 align=left width="30%">特別注意事項</TH></tr>
			                    <tr>
			                      <td colSpan=2 width="30%"><TEXTAREA cols="50" id="PSInfo01_remark" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat(_selectJON.getRemark()) %></TEXTAREA></td></tr></TBODY>
			                      </table>
							

							
							</td>
						</tr>
					</table>
						</td></tr></TBODY></table>

						
<!-- PSInfoList  本日公告-->						
					<table id="table63" width="100%" >
                    <TBODY>
                    <tr>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></tr>
                    <tr>
                      <td width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="psNote"  id="psNote" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getPsNote() )%></TEXTAREA></td></tr></TBODY>
						</table>
					</td>
				</tr>
			</table>
			


<!--PSInfoList End-->

<!--DM Star -->
		<table border="0" width="100%" id="DMInfo" <% if (null!= jobcode.getMpDmPs() && (  jobcode.getMpDmPs().equalsIgnoreCase("DM") )) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"DISPLAY: none\" ");%> height="356">
				<tr>
					<td bgcolor="#006F3F"><font size="2" color="#FFFFFF">Direct Mail</font></td>
				</tr>
				<tr>
					<td valign="top">
					

					<div align="center">
                  <table border=0 cellSpacing=1 cellPadding=1 width="100%" 
                  bgColor=#006f3f id="table54" height="164">
                    <TBODY>
                    <tr>
                      <td bgColor=#ffffff>
					
					<table border="0" width="100%" id="table55" bgcolor="#DFF4DD">
						
						<tr>
							<td colspan="2">
<table id="table83" width="100%" >
                    <TBODY>
                    <tr>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></tr>
                    <tr>
                      <td width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="dmNote"  id="dmNote" rows="20" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getDmNote() )%></TEXTAREA></td></tr></TBODY></table>
							</td>
						</tr>
					</table></td></tr></TBODY></table></div>
					</td>
				</tr>
			</table>
			
			
<!--DM end-->

<!--Null star-->
		<table border="0" width="100%" id="NULLInfo" style="DISPLAY: none" height="356">
				<tr>
					<td bgcolor="#006F3F"></td>
				</tr>
		</table>
<!--Null end-->
		
		</td>
			
		<td width="33%" valign="top">
<!--LGInfo Start-->
<table border="0" width="100%" id="LGInfo" <% if (null!= jobcode.getIsLg() && jobcode.getIsLg()) out.println("style=\"visibility: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
		<tr>
			<td bgcolor="#006F3F"><font size="2" color="#FFFFFF">交寄</font></td>
		</tr>
		<tr>
		<td>
		<table border=0 cellSpacing=1 cellPadding=1 width="100%" bgColor=#006f3f id="table48">
		<TBODY>
		<tr>
		<td bgColor=#ffffff>
		<table border="0" width="100%" id="table49" bgcolor="#DFF4DD">
		<tr>
		<td>
            <table id=table56 border=0 cellSpacing=0 cellPadding=0 width="100%">
              	<tr>
                <td width=100>出貨方式</td>
                <td>
					<SELECT size=1 name="dispatchType" id="dispatchType" onchange="javascript:changeDispatchType()" <%=disabledVIEW %>> 
					<option value="">請選擇</option>
					<OPTION value="MAIL" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("MAIL")) out.println("selected"); %> ()>一般郵件區</OPTION>  
	                <OPTION value="CUSTOMER" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("CUSTOMER")) out.println("selected"); %>>客戶親取</OPTION> 
					<option value="RETURN_CUSTOMER" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("RETURN_CUSTOMER")) out.println("selected"); %>>退回客戶</option>
					<option value="TO_CS" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("TO_CS")) out.println("selected"); %>>送交業務</option>
	                </SELECT>  
                </td>
				</tr>

				<tr>
				<td colSpan=2>
					<table border="0" width="100%" id="dispatchType_MAIL" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("MAIL")) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
				
				<tr>
                <td colSpan=2>
                  	<input type="checkbox" onclick="javascript:hiddenTable('diapatchTable')" id="useLg" name="useLg" value="1" <% if (null!= jobcode.getUseLg() && jobcode.getUseLg() ) out.println("checked");%> <%=disabledVIEW %>>
                  	開立郵資單</td>
				</tr>
				<tr>
				<td colSpan=2>
					<table border="0" width="100%" id="diapatchTable" <% if (null== jobcode.getUseLg() || (null!= jobcode.getUseLg() &&  !jobcode.getUseLg()) ) out.println("style=\"visibility: hidden \" ");%>>
					<tr>
	                <td>郵資單代號</td>
	                <td>
	                <SELECT id="lgNo" size=1 name="lgNo" <%=disabledVIEW %>>
	                <option>請選擇</option>
<% 
	LginfoJSON _selectJON_LG = new LginfoJSON() ;	
	if (null != lginfoJSONlist) {
		for(int j=0; j<lginfoJSONlist.size();j++) {
			String selectString="";
			LginfoJSON _jons = (LginfoJSON)lginfoJSONlist.get(j);
	
			if ( null != jobcode.getLginfo() && _jons.getLgNo().equalsIgnoreCase(jobcode.getLginfo().getLgNo())  ) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON_LG,_jons);
			}
			out.println("<option value=\""+_jons.getLgNo()  + "\" "+selectString + ">"+_jons.getLgNo() +"</option>");				
	
		}
	}
%>
	                    </SELECT>
	                       <a href="#" onclick="toEditLocation('lginfo', 'lgNo', 'lgNo', true)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a>
	                    </td>
					</tr>

                      <tr>
                      <td width="30%" bgcolor="#DFF4DD">郵資單抬頭</td>
                      <td width="70%"><div id="LGInfo01_prodmemo" ><%=Util.getStringFormat( _selectJON_LG.getProgNm()) %></div></td>
                      </tr>
                      <!-- 
                      <tr>
                      <td width="30%" bgcolor="#DFF4DD">郵資單種類</td>
                      <td width="70%"><div id="LGInfo01_lgType" ><%=Util.getStringFormat( _selectJON_LG.getLgType()) %></div></td>
                      </tr>

						<tr>
                      <td width="30%" bgcolor="#DFF4DD">交寄方式</td>
                      <td width="70%"><div id="LGInfo01_mailCategory" ><%=Util.getStringFormat( _selectJON_LG.getMailCategory()) %></div></td>
                      </tr>
                       -->                      
						<tr>
                      <td width="30%" bgcolor="#DFF4DD">寄送地區</td>
                      <td width="70%"><div id="LGInfo01_mailToAreaDisplay" ><%=Util.getStringFormat( _selectJON_LG.getMailToAreaDisplay()) %></div></td>
                      </tr>
					</table>
				</td>
				</tr>
				
              <tr>
                <td width="30%">郵資單總類</td>
                <td width="70%">
<SELECT  id="codeLgType" name="codeLgType" size="1" <%=disabledVIEW %>>
<option value="" >請選擇</option>
<% for (int i=0; i<lgTypeList.size(); i++) {
	Code _code = (Code)lgTypeList.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByCodeLgType() && _code.getId().compareTo(jobcode.getCodeByCodeLgType().getId())==0 ) 
		selectString="selected";
	else if ( _code.getCodeValueTw().equalsIgnoreCase(_selectJON_LG.getLgType()) ) 
		selectString="selected";	
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
				</td>
				</tr>
						 
              <tr>
                <td width="30%">交寄方式</td>
                <td width="70%">
<SELECT id="codeMailCategory" name="codeMailCategory" size="1" <%=disabledVIEW %>>
<option value="" >請選擇</option>
<% for (int i=0; i<lgMailCategory.size(); i++) {
	Code _code = (Code)lgMailCategory.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByCodeMailCategory() && _code.getId().compareTo(jobcode.getCodeByCodeMailCategory().getId())==0 ) 
		selectString="selected";
	else if ( _code.getCodeValueTw().equalsIgnoreCase(_selectJON_LG.getMailCategory()) ) 
		selectString="selected";		
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
				</td>
				</tr>
				
										      				
              <tr>
                <td width="30%" >交寄郵局或公司</td>
                <td width="70%">
<SELECT id="codeMailToPostoffice" name="codeMailToPostoffice" size="1" <%=disabledVIEW %> >
<option value="" >請選擇</option>
<% for (int i=0; i<lgMailToPostofficeList.size(); i++) {
	Code _code = (Code)lgMailToPostofficeList.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByCodeMailToPostoffice() && _code.getId().compareTo(jobcode.getCodeByCodeMailToPostoffice().getId())==0 ) 
		selectString="selected";
	else if ( _code.getCodeValueTw().equalsIgnoreCase(_selectJON_LG.getMailToPostoffice()) ) 
		selectString="selected";		
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
				</td>
				</tr>
		  </table>
          <table border="0" width="100%" id="logistic_controller" <% if (null!=jobcode.getDispatchType() && (jobcode.getDispatchType().equalsIgnoreCase("MAIL") || jobcode.getDispatchType().equalsIgnoreCase("RETURN_CUSTOMER"))) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
              <tr>
                <td>交寄管制表</td>
                <td>
	                <SELECT id="lcNo" size=1 name="lcNo" <%=disabledVIEW %>>
	                <option>請選擇</option>
<% 
	LcinfoJSON _selectJON_LC = new LcinfoJSON() ;	
	if (null != lcinfoJSONlist) {
		for(int j=0; j<lcinfoJSONlist.size();j++) {
			String selectString="";
			LcinfoJSON _jons = (LcinfoJSON)lcinfoJSONlist.get(j);
	
			if ( null != jobcode.getLcinfo() && _jons.getLcNo().equalsIgnoreCase(jobcode.getLcinfo().getLcNo())  ) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON_LC,_jons);
			}
			out.println("<option value=\""+_jons.getLcNo()  + "\" "+selectString + ">"+_jons.getLcNo() +"</option>");				
	
		}
	}
%>
	                    </SELECT>
	                       <a href="#" onclick="toEditLocation('lcinfo', 'lcNo', 'lcNo', true)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a>
				</td>
				</tr>
				<tr>
                      <td width="30%" bgcolor="#DFF4DD">交寄管制表抬頭</td>
                      <td width="70%"><div id="LCInfo01_prodmemo" ><%=Util.getStringFormat( _selectJON_LC.getProgNm()) %></div></td>
                      </tr>
                      
                      
				
								
				</table>
				</td>
				</tr>
				

				<tr>
				<td colSpan=2>
					<table border="0" width="100%" id="dispatchType_RETURN" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("RETURN_CUSTOMER")) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
					<tr>
	                <td>收件人代號</td>
	                <td>
	                <SELECT id="returnNo" size=1 name="returnNo" <%=disabledVIEW %>>
	                <option>請選擇</option>
<% 
	ReturninfoJSON _selectJON_Return = new ReturninfoJSON() ;	
	if (null != returninfoJSONlist) {
		for(int j=0; j<returninfoJSONlist.size();j++) {
			String selectString="";
			ReturninfoJSON _jons = (ReturninfoJSON)returninfoJSONlist.get(j);
	
			if ( null != jobcode.getReturninfo() && _jons.getReturnNo().equalsIgnoreCase(jobcode.getReturninfo().getReturnNo())  ) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON_Return,_jons);
			}
			out.println("<option value=\""+_jons.getReturnNo()  + "\" "+selectString + ">"+_jons.getReturnNo()+"-"+_jons.getUserName() +"</option>");	
	
		}
	}
%>
	                    </SELECT>
	                 </td>
					</tr>
                      <tr>
                      <td width="30%" bgcolor="#DFF4DD">收件人公司</td>
                      <td width="70%"><div id="ReturnInfo01_userCompany" ><%=Util.getStringFormat( _selectJON_Return.getUserCompany()) %></div></td>
                      </tr>
                      <tr>
                      <td width="30%" bgcolor="#DFF4DD">收件人名稱</td>
                      <td width="70%"><div id="ReturnInfo01_userName" ><%=Util.getStringFormat( _selectJON_Return.getUserName()) %></div></td>
                      </tr>
                      
						<tr>
                      <td width="30%" bgcolor="#DFF4DD">收件人地址</td>
                      <td width="70%"><div id="ReturnInfo01_returnAddress" ><%=Util.getStringFormat( _selectJON_Return.getReturnAddress()) %></div></td>
                      </tr>
						<tr>
                      <td width="30%" bgcolor="#DFF4DD">收件人電話</td>
                      <td width="70%"><div id="ReturnInfo01_userTel" ><%=Util.getStringFormat( _selectJON_Return.getUserTel()) %></div></td>
                      </tr>
					</table>
		      	</td>
		      	</tr>




			
			</table>
							

							</td>
						</tr>
					</table>
					
					
					
						</td></tr></TBODY></table>
					</td>
				</tr>
				
<tr>
					<td>
					

                  <table border=0 cellSpacing=1 cellPadding=1 width="100%" 
                  bgColor=#006f3f id="table48">
                    <TBODY>
                    <tr>
                      <td bgColor=#ffffff>
					
					<table border="0" width="100%" id="table49" bgcolor="#DFF4DD">
						<tr>
							<td>
							

                    <tr>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></tr>
                    <tr>
                      <td width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="lgNote"  id="lgNote" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getLgNote() ) %></TEXTAREA></td></tr></table>
							

							</td>
						</tr>
					</table>
					
					
					
						</td></tr></table>
<!--LGInfo end -->
		</td>
		</tr>
	</table>			


<!-- 下方命令列控制區塊 -->		
	<table width="95%" border="0" cellspacing="0" cellpadding="0">	
			<tr>
					<td colSpan=4 align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<c:out value="${pagesIndex}" /> <input type="hidden" name="pagesIndex" value="${pagesIndex}" />
						<c:if test="${prevId != null && prevId != ''}">
						   <input name="button6" type="button" onclick="location='jobcode.do?fid=editInit&idList=${idList}&jobCodeNo=${prevId }&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit"  id="btnEdit" value="更新" />
						<input name="button6" type="button" onclick="location='jobcode.do?fid=saveAsNewInit&jobCodeNo=<%=jobcode.getJobCodeNo()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<input type="button" value="預覽"   onclick="previewDateShow()" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='jobcode.do?fid=editInit&idList=${idList}&jobCodeNo=${nextId}&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
                        </c:if>

<%  } else { %>
						<input  type="button"  onclick="backToList()"   value="回列表" />						
<%  } %>
					</td>
				</tr>
<!-- end 下方命令列控制區塊 -->
			</table>
             <div class="jqmWindowPreview" id="jqmPreviewArea" align="left">
                  <a href="#" class="jqmClose">Close</a>
                     <hr>
                                                        選擇預覽Cycle Date：
                     <INPUT id="previewDate" maxLength="10" size="10" name="previewDate" readonly="readonly">
                          <img border="0" alt="日期選擇輔助工具" id="previewDate_datepicker" src="images/calendar2.gif" />
                          <script language="javascript">
		                         Calendar.setup({
			                              inputField : "previewDate",						
		                                  button : "previewDate_datepicker"
		                         });
                          </script>                       
                     <input  type="button" onclick="preview()"  id="lpbutton" value="確定預覽" />
               </div>               
	</form>		
