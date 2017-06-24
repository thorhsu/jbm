<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="net.mlw.vlh.ValueList"%>
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
	String actionName="jobbag";
	
	if (targetURI.equalsIgnoreCase("jobcode.do") ) 
		actionName="jobcode";
    Calendar deadLine = Calendar.getInstance();
    deadLine.set(deadLine.get(Calendar.YEAR), deadLine.get(Calendar.MONTH), deadLine.get(Calendar.DATE), 0, 0, 0);    
	deadLine.set(Calendar.MILLISECOND, 0);
	
	JobBag jobcode= (JobBag)request.getAttribute("jobbag");
	
	//算出dead line
	if(jobcode.getDeadTime() != null && jobcode.getDeadLine() == null){
		int days = Integer.parseInt(jobcode.getDeadTime()) / 24;
		//找出未來30天的Holiday的list
		List<Holiday> holidays = HolidayService.queryByNow(deadLine.getTime());
		for(int i = 1 ; i <= days ; i++){
			//加一天
			deadLine.add(Calendar.DATE, 1);
			for(Holiday holiday : holidays){
				//如果是假期時，就往下再算一天 
				if(holiday.getDate().getTime() == deadLine.getTimeInMillis() && holiday.getIsHoliday() != null && holiday.getIsHoliday()){
					i--;
				}
			}
		}
		jobcode.setDeadLine(deadLine.getTime());
	}
	
	
	
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
	List printerTypeList = codeService.findBycodeTypeName("PRINTER_TYPE"); //印表機類型
	List printTypeList = codeService.findBycodeTypeName("PRINT_TYPE");	//列印方式
	List psTypeList = codeService.findBycodeTypeName("PS_TYPE"); //摺疊方式	
	
	List jobcodeTypeList = codeService.findBycodeTypeName("JOB_CODE_TYPE"); //工作種類
	List deadlineList = codeService.findBycodeTypeName("DEADLINE"); //DEADLINE
	List lgTypeList = codeService.findBycodeTypeName("LG_TYPE"); //郵資單總類
	List lgMailCategory = codeService.findBycodeTypeName("LG_MAIL_CATEGORY"); //交寄方式	
	List lgMailToPostofficeList = codeService.findBycodeTypeName("LG_MAIL_TO_POSTOFFICE"); //交寄郵局或公司
	List lgMailToAreaList = codeService.findBycodeTypeName("LG_MAIL_TO_AREA"); //郵資單寄送地區	

	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");


	//若工單狀態 == ACCOUNTING_LOCKED , ACCOUNTING_EP1, actionMode 轉為 VIEW, 不能再被編輯 
	if (null!=jobcode.getJobBagStatus() && (  jobcode.getJobBagStatus().equalsIgnoreCase("ACCOUNTING_LOCKED") || jobcode.getJobBagStatus().equalsIgnoreCase("ACCOUNTING_EP1") )){
		actionMode ="VIEW";
	}		
	 
	
	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		jobcode.setJobBagNo("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
	


	String disabledSpilted="";
	if (!targetURI.equalsIgnoreCase("jobbagOP.do") ) 
		disabledSpilted="readonly disabled class=\"disabled\""; //只有OP 才能分檔, 其他 action 過來的 都只能view 
		
				 
%>
<script type="text/javascript"> 
var lplist = <%=lpinfoJSONArray%>;
var mplist = <%=mpinfoJSONArray%>;
var pslist = <%=psinfoJSONArray%>;
var lglist = <%=lginfoJSONArray%>;
var returnlist = <%=returninfoJSONArray%>;
var lclist = <%=lcinfoJSONArray%>;


$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	var extraMsg = "${extraMsg}";


	//大密技, 將attr= disabled, 轉成 正常, 這樣才能送出submit 帶出預設值
	$("#myform").submit(function(){
		$("#codeLgType").attr("disabled","");
		$("#codeMailToPostoffice").attr("disabled","");
		$("#codeMailCategory").attr("disabled","");
		$("#spliteCount").attr("disabled","");
		return true;
	});
	

  	
  	
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
	    
	    //rturn list
	    $.getJSON('customer.do?fid=getCustomerReturnList', 
	        {custNo: $('#custNo').val()},
	        function(json) {
	        	returnlist = json;
	        	$("#jobBagNo").find('option').remove();
	        	$("#jobBagNo").append("<option value='' >請選擇</option>");

		        for(var i=0;i<json.length;i++){
		        	$("#jobBagNo").append("<option value='"+json[i].jobBagNo+"' >"+json[i].jobBagNo+"</option>");
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
				$('#LPInfo0<%=i%>_prodmemo').val(lplist[i].prodmemo);
				$('#LPInfo0<%=i%>_printer').val(lplist[i].printer);
				$('#LPInfo0<%=i%>_printType').val(lplist[i].printType);
				$('#LPInfo0<%=i%>_pcode1').val(lplist[i].pcode1);
				$('#LPInfo0<%=i%>_pcode2').val(lplist[i].pcode2);
				$('#LPInfo0<%=i%>_pcode3').val(lplist[i].pcode3);
				$('#LPInfo0<%=i%>_pcode4').val(lplist[i].pcode4);				
				$('#LPInfo0<%=i%>_pcode5').val(lplist[i].pcode5);
				$('#LPInfo0<%=i%>_pcode6').val(lplist[i].pcode6);
				$('#LPInfo0<%=i%>_pcode7').val(lplist[i].pcode7);
				$('#LPInfo0<%=i%>_pcode8').val(lplist[i].pcode8);
				$('#LPInfo0<%=i%>_remark').val(lplist[i].remark);					
				
			}
		}
  	};
  	$('#lpNo<%=i%>').change(getLpNo<%=i%>);
<%}%>  	




<% for (int i=1; i<=3; i++) {%>  	
	getMpNo<%=i%> = function (){
		for(var i=0;i<mplist.length;i++){
			if (mplist[i].mpNo == $('#mpNo<%=i%>').val()) {
				$('#MPInfo0<%=i%>_prodmemo').val(mplist[i].prodmemo);
				$('#MPInfo0<%=i%>_printCode').val(mplist[i].printCode);	


				if (mplist[i].stampSetFg) 			
					$('input[id="MPInfo0<%=i%>_stampSetFg"]')[0].checked = true;		
				else 
					$('input[id="MPInfo0<%=i%>_stampSetFg"]')[1].checked = true;
				
				if (mplist[i].zipFg) 			
					$('input[id="MPInfo0<%=i%>_zipFg"]')[0].checked = true;		
				else 
					$('input[id="MPInfo0<%=i%>_zipFg"]')[1].checked = true;


				if ($("input[id='mp1Iflag']").is(":checked"))
					$("#nextMp1Iflag").attr("checked",true);
				else
					$("#nextMp1Iflag").attr("checked",false);
			
			
				if (mplist[i].mp1Iflag)
					$("#MPInfo0<%=i%>_mp1Iflag").attr("checked",true);
				else
					$("#MPInfo0<%=i%>_mp1Iflag").attr("checked", false);
					
				if (mplist[i].mp2Iflag)
					$("#MPInfo0<%=i%>_mp2Iflag").attr("checked", true);
				else
					$("#MPInfo0<%=i%>_mp2Iflag").attr("checked", false);
					
				if (mplist[i].mp3Iflag)
					$("#MPInfo0<%=i%>_mp3Iflag").attr("checked", true);
				else
					$("#MPInfo0<%=i%>_mp3Iflag").attr("checked", false);
					
				if (mplist[i].mp4Iflag)
					$("#MPInfo0<%=i%>_mp4Iflag").attr("checked", true);
				else
					$("#MPInfo0<%=i%>_mp4Iflag").attr("checked", false);															
									
				$('#MPInfo0<%=i%>_mp1Word').val(mplist[i].mp1Word);	
				$('#MPInfo0<%=i%>_mp2Word').val(mplist[i].mp2Word);
				$('#MPInfo0<%=i%>_mp3Word').val(mplist[i].mp3Word);
				$('#MPInfo0<%=i%>_mp4Word').val(mplist[i].mp4Word);																												
				$('#MPInfo0<%=i%>_remark').val(mplist[i].remark);					
			}
		}
  	};
  	$('#mpNo<%=i%>').change(getMpNo<%=i%>);
<%}%>


	
	//Change PSList
	getPsNo = function (){
		for(var i=0;i<pslist.length;i++){
			if (pslist[i].psNo == $('#psNo').val()) {
				$('#PSInfo01_prodmemo').val(pslist[i].prodmemo);
				$('#PSInfo01_psType').val(pslist[i].psType);

				if (pslist[i].zipFg) 			
					$('input[id="PSInfo01_zipFg"]')[0].checked = true;		
				else 
					$('input[id="PSInfo01_zipFg"]')[1].checked = true;
					
									
				$('#PSInfo01_remark').val(pslist[i].remark);					
				
			}
		}
  	};
  	$('#psNo').change(getPsNo);
  	
	//Change LGList
	getLgNo = function (){
		for(var i=0;i<lglist.length;i++){
			if (lglist[i].lgNo == $('#lgNo').val()) {
				$('#LGInfo01_prodmemo').val(lglist[i].progNm);
				$('#LGInfo01_mailToAreaDisplay').val(lglist[i].mailToAreaDisplay);	
				
				// alert(window.document.myform.codeLgType.length);		
				//alert($('#codeLgType').find("option").length);		
				//設定  郵資單總類 = 郵資單. 郵資單總類
				window.document.myform.codeLgType.selectedIndex=0;
				for (var ii=0; ii< $('#codeLgType').find("option").length ; ii++) {
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
				window.document.myform.codeMailToPostoffice.selectedIndex= 0;
				for (var ii=0; ii< window.document.myform.codeMailToPostoffice.length ; ii++) {
					if ( window.document.myform.codeMailToPostoffice.options[ii].text ==lglist[i].mailToPostoffice )
						window.document.myform.codeMailToPostoffice.selectedIndex= ii;
				}				 
				

				if (   lglist[i].codeMailToArea1 == $("#codeMailToArea0").val() || lglist[i].codeMailToArea2 == $("#codeMailToArea0").val() || lglist[i].codeMailToArea3 == $("#codeMailToArea0").val() 
					|| lglist[i].codeMailToArea4 == $("#codeMailToArea0").val() || lglist[i].codeMailToArea5 == $("#codeMailToArea0").val() || lglist[i].codeMailToArea6 == $("#codeMailToArea0").val()
					) 	
					$("#codeMailToArea0").attr("checked",true);
				else
					$("#codeMailToArea0").attr("checked",false);
					
				if (   lglist[i].codeMailToArea1 == $("#codeMailToArea1").val() || lglist[i].codeMailToArea2 == $("#codeMailToArea1").val() || lglist[i].codeMailToArea3 == $("#codeMailToArea1").val() 
					|| lglist[i].codeMailToArea4 == $("#codeMailToArea1").val() || lglist[i].codeMailToArea5 == $("#codeMailToArea1").val() || lglist[i].codeMailToArea6 == $("#codeMailToArea1").val()
					)
					$("#codeMailToArea1").attr("checked",true);
				else
					$("#codeMailToArea1").attr("checked",false);
					
				if (   lglist[i].codeMailToArea1 == $("#codeMailToArea2").val() || lglist[i].codeMailToArea2 == $("#codeMailToArea2").val() || lglist[i].codeMailToArea3 == $("#codeMailToArea2").val() 
					|| lglist[i].codeMailToArea4 == $("#codeMailToArea2").val() || lglist[i].codeMailToArea5 == $("#codeMailToArea2").val() || lglist[i].codeMailToArea6 == $("#codeMailToArea2").val()
					)
					$("#codeMailToArea2").attr("checked",true);
				else
					$("#codeMailToArea2").attr("checked",false);
					
				if (   lglist[i].codeMailToArea1 == $("#codeMailToArea3").val() || lglist[i].codeMailToArea2 == $("#codeMailToArea3").val() || lglist[i].codeMailToArea3 == $("#codeMailToArea3").val() 
					|| lglist[i].codeMailToArea4 == $("#codeMailToArea3").val() || lglist[i].codeMailToArea5 == $("#codeMailToArea3").val() || lglist[i].codeMailToArea6 == $("#codeMailToArea3").val()
					)	
					$("#codeMailToArea3").attr("checked",true);
				else
					$("#codeMailToArea3").attr("checked",false);
					
				if (   lglist[i].codeMailToArea1 == $("#codeMailToArea4").val() || lglist[i].codeMailToArea2 == $("#codeMailToArea4").val() || lglist[i].codeMailToArea3 == $("#codeMailToArea4").val() 
					|| lglist[i].codeMailToArea4 == $("#codeMailToArea4").val() || lglist[i].codeMailToArea5 == $("#codeMailToArea4").val() || lglist[i].codeMailToArea6 == $("#codeMailToArea4").val()
					)
					$("#codeMailToArea4").attr("checked",true);
				else
					$("#codeMailToArea4").attr("checked",false);		
							
				if (   lglist[i].codeMailToArea1 == $("#codeMailToArea5").val() || lglist[i].codeMailToArea2 == $("#codeMailToArea5").val() || lglist[i].codeMailToArea3 == $("#codeMailToArea5").val() 
					|| lglist[i].codeMailToArea4 == $("#codeMailToArea5").val() || lglist[i].codeMailToArea5 == $("#codeMailToArea5").val() || lglist[i].codeMailToArea6 == $("#codeMailToArea5").val()
					)
					$("#codeMailToArea5").attr("checked",true);
				else
					$("#codeMailToArea5").attr("checked",false);				
	
												
				//$('#codeLgType').attr("disabled", true);
				//$('#codeMailToPostoffice').attr("disabled", true);
				//$('#codeMailCategory').attr("disabled", true);


			}
		}
  	};
  	$('#lgNo').change(getLgNo);
  	
	//Change ReturnList
	getReturnNo = function (){
		for(var i=0;i<returnlist.length;i++){
			if (returnlist[i].returnNo == $('#returnNo').val()) {
				$('#ReturnInfo01_userName').val(returnlist[i].userName);
				$('#ReturnInfo01_userCompany').val(returnlist[i].userCompany);
				$('#ReturnInfo01_returnAddress').val(returnlist[i].returnAddress);
				$('#ReturnInfo01_userTel').val(returnlist[i].userTel);					
			}
		}
  	};
  	$('#returnNo').change(getReturnNo);
  	
	//Change LGCList
	getLcNo = function (){
		for(var i=0;i<lclist.length;i++){
			if (lclist[i].lcNo == $('#lcNo').val()) {
				$('#LCInfo01_prodmemo').val(lclist[i].progNm);			
			}
		}
  	};
  	$('#lcNo').change(getLcNo);
  	
  	
  	//若開立郵資單 已被勾選, 以下輸入控制項目 壓為 disabled 
  	if ($('#useLg').attr("checked")) {
		//$('#codeLgType').attr("disabled", true);
		//$('#codeMailToPostoffice').attr("disabled", true);
		//$('#codeMailCategory').attr("disabled", true);  	
  	}
  	
	//開立郵資單 Change
	useLg = function (){
	  	if ($('#useLg').attr("checked")) {
			//$('#codeLgType').attr("disabled", true);
			//$('#codeMailToPostoffice').attr("disabled", true);
			//$('#codeMailCategory').attr("disabled", true);  	
	  	} else {
			//$('#codeLgType').attr("disabled", false);
			//$('#codeMailToPostoffice').attr("disabled", false);
			//$('#codeMailCategory').attr("disabled", false);  		  	
	  	}
  	};
  	$('#useLg').change(useLg);	
  	$('#isEdd').change(eddChange);	  	
  	$('#mailType').change(eddChange);
  	if(extraMsg != ""){
		alert(extraMsg);
	}
  	
  	var mailType = "${jobbag.mailType}";
    if(mailType !== ""){
    	$("#mailType").attr("checked", true);
    }
    eddChange();
});
//eDD Change
var eddChange = function (){
  	if ($('#isEdd').attr("checked") && !$('#mailType').attr("checked")) {
		$('#isLp').attr("disabled", true);
		$('#mpDmPs').attr("disabled", true);
		$('#isLg').attr("disabled", true);  	
  	} else {
		$('#isLp').attr("disabled", false);
		$('#mpDmPs').attr("disabled", false);
		$('#isLg').attr("disabled", false); 		  	
  	}
};
</script>

	<form name="myform" id="myform" action="jobbag.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
		<input type="hidden" name="fid" value="<%=fid %>" />
		<input type="hidden" name="jobBagNo" value="<%=jobcode.getJobBagNo() %>" />
		
		
			<TABLE width="95%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD height="26" class="title">工單維護</TD>
				</TR>
				<TR>
				  <TD height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></TD>
				</TR>				
			</table>

			<TABLE border=0 cellSpacing=0 cellPadding=0 width="95%" class="editTableBKColor"  id="table8">
<!-- Line 1 -->			
			<TR>
                <TD  width="20%">
				<TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" align=left id="table4">
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70"><font color="#FF0000">*</font>客戶代號</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
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
					</TD>
					<TD class="editTableLabelCellBKColor" width="43" >列印</TD>
					<TD class="editTableContentCellBKColor" align=left width="125">
					<input type="checkbox" onclick="javascript:hiddenTable(LPInfo)" id="isLp" name="isLp" value="1" <% if (null!= jobcode.getIsLp() && jobcode.getIsLp()) out.println("checked");%> <%=disabledVIEW %>>Laser Printing
					</TD>
					<TD class="editTableLabelCellBKColor"  width="70" rowspan="2"><font color="#FF0000" size="2">*</font>交寄時間</TD>
					<TD class="editTableContentCellBKColor" align=left width="67" rowspan="2">
					<SELECT id="dispatchTime" size=1 name="dispatchTime" <%=disabledVIEW %>>
					<OPTION value="AM">AM</OPTION>
					<OPTION value="PM">PM</OPTION>
					</SELECT>
					</TD>
					<TD class="editTableLabelCellBKColor"  width="93" rowspan="2">有效工作資料</TD>
					<TD class="editTableContentCellBKColor" align=left width="110" rowspan="2">
					<input type="checkbox"  id="isEnabledContract" name="isEnabledContract" value="1" <% if (null!= jobcode.getIsEnabledContract() && jobcode.getIsEnabledContract()) out.println("checked");%> <%=disabledVIEW %>>
					</TD>
					<TD class="editTableContentCellBKColor" align=left rowspan="5" valign="top" width="200">
<!-- 工單右邊區塊 -->
<%if (actionName.equalsIgnoreCase("jobcode")) { %>
					<jsp:include page="/entityMgr/JobCodeEdit_Cycle.inc.jsp" />
<% } else  {%>				
					<jsp:include page="/entityMgr/JobBagEdit_AccountInfo.inc.jsp" />
<% } %>	


					</TD>
					</TR> 
<!-- Line 2 -->					
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70"><font color="#FF0000" size="2">*</font>工作代號</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
					<INPUT id="jobCodeId" size="4"  name="jobCodeId" value="<%=jobcode.getJobCodeId() %>"  <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="4" minlength="4"><span id="msg_pk_exist"></span>
					</TD>
					<TD class="editTableLabelCellBKColor" width="43" >裝封</TD>
					<TD class="editTableContentCellBKColor" align=left width="125">
                              <TABLE id="table24">
                                <TR>
                                <TD>
								<INPUT onclick="javascript:changeMP(MPInfo)" type="radio" id="mpDmPs" name="mpDmPs"  value="MP" <% if(null!=jobcode.getMpDmPs() && jobcode.getMpDmPs().equalsIgnoreCase("MP") ) out.println("checked");%> <%=disabledVIEW %> class="required"/>													
								</TD>
                                <TD>
								<INPUT onclick="javascript:changeMP(PSInfo)" type="radio" id="mpDmPs" name="mpDmPs"  value="PS" <% if(null!=jobcode.getMpDmPs() && jobcode.getMpDmPs().equalsIgnoreCase("PS") ) out.println("checked");%> <%=disabledVIEW %> class="required"/>                                
								</TD>
                                <TD>
								<INPUT onclick="javascript:changeMP(DMInfo)" type="radio" id="mpDmPs" name="mpDmPs"  value="DM" <% if(null!=jobcode.getMpDmPs() && jobcode.getMpDmPs().equalsIgnoreCase("DM") ) out.println("checked");%> <%=disabledVIEW %> class="required"/>                                
								</TD>
                                <TD>
								<INPUT onclick="javascript:changeMP(NULLInfo)" type="radio" id="mpDmPs" name="mpDmPs"  value="" <% if(null==jobcode.getMpDmPs() ||  jobcode.getMpDmPs().equalsIgnoreCase("") ) out.println("checked");%> <%=disabledVIEW %> class="required"/>                                
								</TD>
                                </TR>
                                <TR>
                                <TD>MP</TD>
                                <TD>PS</TD>
                                <TD>DM</TD>
                                <TD>NULL</TD>
                                </TR>
                                </TABLE>
					</TD>
                    </TR>
<!-- Line 3 -->	                            
					<TR>
					<TD class="editTableLabelCellBKColor"   width="70" rowspan="2"> <font color="#FF0000" size="2">*</font>工作種類</TD>
					<TD class="editTableContentCellBKColor" align=left width="82" rowspan="2">			
<SELECT name="jobCodeType" id="jobCodeType" size="1" <%=disabledVIEW %> class="required" <%=disabledPK %>>
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

					
					</TD>
					<TD class="editTableLabelCellBKColor" width="43" >交寄</TD>
					<TD class="editTableContentCellBKColor" align=left width="125">
					<input type="checkbox" onclick="javascript:hiddenTable(LGInfo)" id="isLg" name="isLg" value="1" <% if (null!= jobcode.getIsLg() && jobcode.getIsLg()) out.println("checked");%> <%=disabledVIEW %>>Dispatch
					</TD>
					
					<TD class="editTableLabelCellBKColor"  width="70" rowspan="2"><font color="#FF0000" size="2">*</font>Deadline</TD>
					<TD class="editTableContentCellBKColor" align=left width="67" rowspan="2">
					<input type="hidden" name="deadTime" id="deadTime" value='<%= jobcode.getDeadTime() == null ? "" : jobcode.getDeadTime()%>' />
                    <input name="deadLine_form" id="deadLine_form" size="8" value="<%= jobcode.getDeadLine() == null ? "" : Util.getDateFormat(jobcode.getDeadLine())%>" <%=disabledVIEW %> />                    
                    <img border="0" alt="日期選擇輔助工具" id="deadLine_datepicker" src="images/calendar2.gif" <%=disabledVIEW %> />
                              <script language="javascript">
		                         Calendar.setup({
			                         inputField : "deadLine_form",						
		                             button : "deadLine_datepicker"
		                          });
                              </script>
					
					</TD>
					<TD class="editTableLabelCellBKColor"  width="93" rowspan="2">客戶確認日期
					<td class="editTableContentCellBKColor" align=left width="110" rowspan="2">
					   <% if (null != jobcode.getCustConfirm() && jobcode.getCustConfirm()) {%>
					   <input id="custConfirmDate" name="custConfirmDate" size="8" readonly value="<%=Util.getDateFormat( jobcode.getCustConfirmDate()) %>" <%=disabledVIEW %> />					   
					   <img border="0" alt="日期選擇輔助工具" id="custConfirmDate_datepicker" src="images/calendar2.gif" <%=disabledVIEW %> />
                              <script language="javascript">
		                         Calendar.setup({
			                         inputField : "custConfirmDate",						
		                             button : "custConfirmDate_datepicker"
		                          });
                              </script>
                        <%} %>
					</td>
					</TR>
					
					<TR>
					<TD class="editTableLabelCellBKColor" width="43"  >寄送型態</TD>
					<TD class="editTableContentCellBKColor" align=left width="125" >
					      <input type="checkbox" onclick="javascript:hiddenTable(EDDInfo)" id="isEdd" name="isEdd" value="1" <% if (null!= jobcode.getIsEdd() && jobcode.getIsEdd()) out.println("checked");%> <%=disabledVIEW %>>EDD
					      &nbsp;					      
					      <input type="checkbox"  id="mailType" name="mailType" value="mail" <%=disabledVIEW %> />紙本
					</TD>
					
					</TR>
					
<!-- Line 4 -->					
					<TR>
					<TD class="editTableLabelCellBKColor"   width="70"><font color="#FF0000" size="2">*</font>活動名稱</TD>
					<TD class="editTableContentCellBKColor" align=left width="276"  colspan="3">
					<INPUT id="progNm" maxLength=40 size=40 name="progNm" value="<%=Util.getStringFormat( jobcode.getProgNm() ) %>" <%=disabledVIEW %> class="required">
					</TD>
					<TD class="editTableLabelCellBKColor"  width="70" >檔名規則</TD>
					<TD class="editTableContentCellBKColor" align=left width="100"  >
						<INPUT id="filename" size="10" name="filename" value="<%=Util.getStringFormat( jobcode.getFilename() )  %>" <%=disabledVIEW %> >
					</TD>
					<TD class="editTableLabelCellBKColor"  width="70" >客戶單位<BR>(富邦專用欄位)</TD>
					<TD class="editTableContentCellBKColor" align=left width="50"  >
						<INPUT id="customerDept" size="10" name="customerDept" value="<%=Util.getStringFormat( jobcode.getCustomerDept() ) %>" <%=disabledVIEW %>>
					</TD>					
					</TR>
					
					

					
										
				</TABLE>
				
				</TD>
				</TR>
				</TABLE>
				
				
<!-- 中間空白行-->	
				<TABLE width="95%" border="0" cellspacing="0" cellpadding="0">
				<TR><TD height=10 >
				</TD></TR>
                </TABLE>
                
                				
<!-- 分檔區塊-->	

<!-- 工單右邊區塊 -->
<% if ( targetURI.equalsIgnoreCase("jobbagOP.do")  || targetURI.equalsIgnoreCase("jobbagLG.do") ) { %>
					<jsp:include page="/entityMgr/JobBagEdit_Splite.inc.jsp" /> 
<% } else  {%>				
					<jsp:include page="/entityMgr/JobBagEdit_Splite_SC.inc.jsp" />
<% } %>	
<!-- end 分檔區塊-->
                				
<!-- 中間空白行-->	
				<TABLE width="95%" border="0" cellspacing="0" cellpadding="0">
				<TR><TD height=10 >
				</TD></TR>
                </TABLE>

<!-- 中間區塊-->                
				<TABLE width="95%" border="0" cellspacing="0" cellpadding="0">
				<TR>
<!-- LP Start-->				
				<TD width="33%" valign="top" >
			
				<TABLE border="0" width="100%" id="LPInfo" <% if (null!= jobcode.getIsLp() && jobcode.getIsLp()) out.println("style=\"visibility: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
				<TR>
					<TD bgcolor="#006F3F"><font size="2" color="#FFFFFF">列印</font></TD>
				</TR>
				<TR>
					<TD>
<!-- LP Area Start-->				  

                  <TABLE border=0 cellSpacing=1 cellPadding=1 width="100%" bgColor=#006f3f id="table48">
                    <TBODY>
                    <TR>
                      <TD bgColor="#ffffff">
						<TABLE border="0" width="100%" id="table49" bgcolor="#DFF4DD">
						<TR>
							<TD width="35%">產品代號</TD>
							<TD width="61%">
							</TD>
						</TR>
						<TR>
							<TD width="35%">
							<SELECT name="lpNo1" id="lpNo1" <%=disabledVIEW %> >
							<option>請選擇</option>
<% 
	LpinfoJSON _selectJON_LP = new LpinfoJSON() ;	
	if (null != lpinfoJSONlist) {

		for(int j=0; j<lpinfoJSONlist.size();j++) {
			String selectString="";
			LpinfoJSON _jons = (LpinfoJSON)lpinfoJSONlist.get(j);
	 
			if (  null != jobcode.getLpinfoByIdfLpNo1() && _jons.getLpNo().equalsIgnoreCase(jobcode.getLpinfoByIdfLpNo1().getLpNo())   ) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON_LP,_jons);
			}
			out.println("<option value=\""+_jons.getLpNo()  + "\" "+selectString + ">"+_jons.getLpNo() +"</option>");				
	
		}
	}
	
	String printer="";
	String printType="";
	if (null!= jobcode.getCodeByLpCodePrinter())
		printer = jobcode.getCodeByLpCodePrinter().getCodeValueTw();
	if (null!= jobcode.getCodeByLpCodePrintType())
		printType=jobcode.getCodeByLpCodePrintType().getCodeValueTw();
	
	
%>							
                    		</SELECT> 
                    		<a href="#" onclick="toEditLocation('lpinfo', 'lpNo1', 'lpNo', false)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a>
                    		</TD>

						</TR>
						
						<TR>
							<TD colspan="2">

			                  <TABLE id="LPInfo01" width="100%" style="DISPLAY: visible" >
			                    <TBODY>
			                    <TR>
			                      <TD width="15%" bgcolor="#DFF4DD">產品說明</TD>
			                      <TD width="85%" colspan="3">
			                      <INPUT id="LPInfo01_prodmemo" name="lpProdmemo" value="<%=Util.getStringFormat( jobcode.getLpProdmemo() )  %>" <%=disabledVIEW %>>
			                      </TD>
			                      </TR>
								<TR>
			                      <TD width="15%" bgcolor="#DFF4DD">印表機類型</TD>
			                      <TD width="85%" colspan="3">
<SELECT name="codeLpPrinter" size="1" <%=disabledVIEW %>  id="LPInfo01_printer" >
<option value="" >請選擇</option>
<% for (int i=0; i<printerTypeList.size(); i++) {
	Code _code = (Code)printerTypeList.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByLpCodePrinter() && _code.getId().compareTo(jobcode.getCodeByLpCodePrinter().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
			                      
			                      </TD>
			                      </TR>
								<TR>
			                      <TD width="15%" bgcolor="#DFF4DD">列印方式</TD>
			                      <TD width="85%" colspan="3">
<SELECT name="codeLpPrintType" size="1" <%=disabledVIEW %>  id="LPInfo01_printType" >
<option value="" >請選擇</option>
<% for (int i=0; i<printTypeList.size(); i++) {
	Code _code = (Code)printTypeList.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByLpCodePrintType() && _code.getId().compareTo(jobcode.getCodeByLpCodePrintType().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
								  </TD>
			                      </TR>
								<TR>
			                      <TD width="15%" bgcolor="#DFF4DD">物料一</TD>
			                      <TD width="35%">
			                        <INPUT id="LPInfo01_pcode1" name="lpPcode1" value="<%=Util.getStringFormat( jobcode.getLpPcode1() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>
			                      <TD width="15%" bgcolor="#DFF4DD" align="right">物料二</TD>
			                      <TD width="35%" >
			                         <INPUT id="LPInfo01_pcode2" name="lpPcode2" value="<%=Util.getStringFormat( jobcode.getLpPcode2() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>
			                    </TR>
								<TR>
								  <TD width="15%" bgcolor="#DFF4DD">物料三</TD>
			                      <TD width="35%">
			                         <INPUT id="LPInfo01_pcode3" name="lpPcode3" value="<%=Util.getStringFormat( jobcode.getLpPcode3() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>
			                      <TD width="15%" bgcolor="#DFF4DD" align="right">物料四</TD>
			                      <TD width="35%">
			                         <INPUT id="LPInfo01_pcode4" name="lpPcode4" value="<%=Util.getStringFormat( jobcode.getLpPcode4() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>			                      
			                    </TR>
								<TR>
			                      <TD width="15%" bgcolor="#DFF4DD">物料五</TD>
			                      <TD width="35%">
			                        <INPUT id="LPInfo01_pcode5" name="lpPcode5" value="<%=Util.getStringFormat( jobcode.getLpPcode5() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>
			                      <TD width="15%" bgcolor="#DFF4DD" align="right">物料六</TD>
			                      <TD width="35%">
			                         <INPUT id="LPInfo01_pcode6" name="lpPcode6" value="<%=Util.getStringFormat( jobcode.getLpPcode6() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>
			                    </TR>
								<TR>
								  <TD width="15%" bgcolor="#DFF4DD">物料七</TD>
			                      <TD width="35%">
			                         <INPUT id="LPInfo01_pcode7" name="lpPcode7" value="<%=Util.getStringFormat( jobcode.getLpPcode7() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>
			                      <TD width="15%" bgcolor="#DFF4DD" align="right">物料八</TD>
			                      <TD width="35%">
			                         <INPUT id="LPInfo01_pcode8" name="lpPcode8" value="<%=Util.getStringFormat( jobcode.getLpPcode8() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>			                      
			                    </TR>
			                    <TR>
			                      <TH colSpan="4" align=left >特別注意事項</TH>
			                    </TR>
			                    <TR>
			                      <TD colSpan="4" ><TEXTAREA cols="50" id="LPInfo01_remark" rows="4" name="lpRemark" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getLpRemark()) %> </TEXTAREA></TD>
			                    </TR></TBODY>
			                    </TABLE>
							

							
							</TD>
						</TR>
					</table>
						</TD></TR></TBODY></TABLE>
	

						
<!-- LP  本日公告-->						
					<TABLE id="table63" width="100%" >
                    <TBODY>
                    <TR>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></TR>
                    <TR>
                      <TD width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="lpNote"  id="lpNote" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getLpNote()) %></TEXTAREA></TD></TR></TBODY>
						</TABLE>
					</TD>
				</TR>
			</table>
			
<!--LP end -->
			</TD>
			
			
			
			<TD width="33%" valign="top">
<!--MP Start-->

			<TABLE border="0" width="100%" id="MPInfo" <% if (null!= jobcode.getMpDmPs() && ( jobcode.getMpDmPs().equalsIgnoreCase("MP")  )) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"DISPLAY: none\" ");%>>
				<TR>
					<TD bgcolor="#006F3F"><font size="2" color="#FFFFFF">裝封方式</font></TD>
				</TR>
				
				<TR>
					<TD>

                  <TABLE border=0 cellSpacing=1 cellPadding=1 width="100%" bgColor=#006f3f id="table48">
                    <TBODY>
                    <TR>
                      <TD bgColor="#ffffff">
						<TABLE border="0" width="100%" id="table49" bgcolor="#DFF4DD">
						<TR>
							<TD width="35%">產品代號</TD>
							<TD width="61%">
							</TD>
						</TR>
						<TR>
							<TD width="35%">
							<SELECT name="mpNo1" id="mpNo1" <%=disabledVIEW %>>
							<option>請選擇</option>
<% 
	MpinfoJSON _selectJON_MP = new MpinfoJSON() ;	
	if (null != mpinfoJSONlist) {
		for(int j=0; j<mpinfoJSONlist.size();j++) {
			MpinfoJSON _jons = (MpinfoJSON)mpinfoJSONlist.get(j);
			String selectString="";
			if ( null != jobcode.getMpinfoByIdfMpNo1() && _jons.getMpNo().equalsIgnoreCase(jobcode.getMpinfoByIdfMpNo1().getMpNo()) ) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON_MP,_jons);
			}
			out.println("<option value=\""+_jons.getMpNo()  + "\" "+selectString + ">"+_jons.getMpNo() +"</option>");
		}
	}
%>							
                    		</SELECT> 
                    		<a href="#" onclick="toEditLocation('mpinfo', 'mpNo1', 'mpNo', false)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a>
                    		</TD>

						</TR>
						
						<TR>
							<TD colspan="2">

			                  <TABLE id="MPInfo01" width="100%" style="DISPLAY: visible">
			                    <TBODY>
			                    <TR>
			                      <TD width="30%" bgcolor="#DFF4DD">產品說明</TD>			                      
			                      <TD width="70%">
			                      <INPUT id="MPInfo01_prodmemo" name="mpProdmemo" value="<%=Util.getStringFormat( jobcode.getMpProdmemo() )  %>" <%=disabledVIEW %>>
								  </TD>
			                      </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">物料版號</TD>
			                      <TD width="70%">
			                      <INPUT id="MPInfo01_printCode" name="mpPrintCode" value="<%=Util.getStringFormat( jobcode.getMpPrintCode() )  %>" <%=disabledVIEW %>>			                      
			                      </TD>
			                      </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">郵戳設定</TD>
			                      <TD width="70%">
									<INPUT type="radio" id="MPInfo01_stampSetFg" name="mpStampSetFg" value="1"  <%=disabledVIEW %> <% if (null!= jobcode.getMpStampSetFg() && jobcode.getMpStampSetFg()) out.println("checked");%> /> 打郵戳
									<INPUT type="radio" id="MPInfo01_stampSetFg" name="mpStampSetFg" value="0"  <%=disabledVIEW %> <% if (null!= jobcode.getMpStampSetFg() && !jobcode.getMpStampSetFg()) out.println("checked");%>/> 不打郵戳
							      </TD>
			                      </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">分區</TD>
			                      <TD width="70%">
									<INPUT type="radio" id="MPInfo01_zipFg" name="mpZipFg" value="1"  <%=disabledVIEW %> <% if (null!= jobcode.getMpZipFg() && jobcode.getMpZipFg()) out.println("checked");%> /> 分區
									<INPUT type="radio" id="MPInfo01_zipFg" name="mpZipFg" value="0"  <%=disabledVIEW %> <% if (null!= jobcode.getMpZipFg() && !jobcode.getMpZipFg()) out.println("checked");%>/> 不分區
							      </TD>
			                      </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">插件二</TD>
			                      <TD width="70%">
			                      <input type="checkbox" id="MPInfo01_mp1Iflag" name="mpMp1Iflag" value="1" <%=disabledVIEW %> <% if (null!= jobcode.getMpMp1Iflag() && jobcode.getMpMp1Iflag()) out.println("checked");%>>
			                      <INPUT id="MPInfo01_mp1Word" name="mpMp1Word" value="<%=Util.getStringFormat( jobcode.getMpMp1Word() )  %>" <%=disabledVIEW %>>
								  </TD>
			                    </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">插件三</TD>
			                      <TD width="70%">
			                      <input type="checkbox" id="MPInfo01_mp2Iflag" name="mpMp2Iflag" value="1" <%=disabledVIEW %> <% if (null!= jobcode.getMpMp2Iflag() && jobcode.getMpMp2Iflag()) out.println("checked");%>>
			                      <INPUT id="MPInfo01_mp2Word" name="mpMp2Word" value="<%=Util.getStringFormat( jobcode.getMpMp2Word() )  %>" <%=disabledVIEW %>>
								  </TD>

			                    </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">插件四</TD>
			                      <TD width="70%">
			                      <input type="checkbox" id="MPInfo01_mp3Iflag" name="mpMp3Iflag" value="1" <%=disabledVIEW %> <% if (null!= jobcode.getMpMp3Iflag() && jobcode.getMpMp3Iflag()) out.println("checked");%>>
			                      <INPUT id="MPInfo01_mp3Word" name="mpMp3Word" value="<%=Util.getStringFormat( jobcode.getMpMp3Word() )  %>" <%=disabledVIEW %>>
								  </TD>

			                    </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">插件五</TD>
			                      <TD width="70%">
			                      <input type="checkbox" id="MPInfo01_mp4Iflag" name="mpMp4Iflag" value="1" <%=disabledVIEW %> <% if (null!= jobcode.getMpMp4Iflag() && jobcode.getMpMp4Iflag()) out.println("checked");%>>
			                      <INPUT id="MPInfo01_mp4Word" name="mpMp4Word" value="<%=Util.getStringFormat( jobcode.getMpMp4Word() )  %>" <%=disabledVIEW %>>
								  </TD>

			                    </TR>
			                    <TR>
			                      <TH colSpan=2 align=left width="30%">本日公告</TH></TR>
			                    <TR>
			                      <TD colSpan=2 width="30%"><TEXTAREA cols="50" id="MPInfo01_remark" rows="4" name="mpRemark" <%=disabledVIEW %>><%if (null != jobcode.getMpRemark()) out.println(Util.getStringFormat(jobcode.getMpRemark())); %></TEXTAREA></TD></TR></TBODY>
			                      </TABLE>
							

							
							</TD>
						</TR>
					</table>
						</TD></TR></TBODY></TABLE>
				

						
<!-- MP  本日公告-->						
					<TABLE id="table63" width="100%" >
                    <TBODY>
                    <TR>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></TR>
                    <TR>
                      <TD width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="mpNote"  id="lpNote" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getMpNote() )%></TEXTAREA></TD></TR></TBODY>
						</TABLE>
					</TD>
				</TR>
			</table>
			
<!--MP end -->

<!--PSInfoList Start-->

			<TABLE border="0" width="100%" id="PSInfo" <% if (null!= jobcode.getMpDmPs() && (  jobcode.getMpDmPs().equalsIgnoreCase("PS") )) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"DISPLAY: none\" ");%>>
				<TR>
					<TD bgcolor="#006F3F"><font size="2" color="#FFFFFF">壓封方式 PSInfo</font></TD>
				</TR>
				
				<TR>
					<TD>

                  <TABLE border=0 cellSpacing=1 cellPadding=1 width="100%" bgColor=#006f3f id="table48">
                    <TBODY>
                    <TR>
                      <TD bgColor="#ffffff">
						<TABLE border="0" width="100%" id="table49" bgcolor="#DFF4DD">
						<TR>
							<TD width="35%">產品代號</TD>
						</TR>
						<TR>
							<TD width="35%">
							<SELECT name="psNo" id="psNo" <%=disabledVIEW %>>
							<option>請選擇</option>
<% 
	PsinfoJSON _selectJON_PS = new PsinfoJSON() ;	
	String psType="";
	if (null != psinfoJSONlist) {
		for(int j=0; j<psinfoJSONlist.size();j++) {
			PsinfoJSON _jons = (PsinfoJSON)psinfoJSONlist.get(j);
			String selectString="";
			if (null != jobcode.getPsinfo() && _jons.getPsNo().equalsIgnoreCase(jobcode.getPsinfo().getPsNo())) {
				selectString=" selected";
				BeanUtils.copyProperties(_selectJON_PS,_jons);
			}
			out.println("<option value=\""+_jons.getPsNo()  + "\" "+selectString + ">"+_jons.getPsNo() +"</option>");
		}
	}
	
	if (null!= jobcode.getCodeByPsCodePsType())
		psType = jobcode.getCodeByPsCodePsType().getCodeValueTw();
%>							
                    		</SELECT> 
                    		<a href="#" onclick="toEditLocation('psinfo', 'psNo', 'psNo', false)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a>
                    		</TD>
							<TD width="61%">

						</TR>
						
						<TR>
							<TD colspan="2">

			                  <TABLE id="PSInfo01" width="100%" >
			                    <TBODY>
			                    <TR>
			                      <TD width="30%" bgcolor="#DFF4DD">產品說明</TD>
			                      <TD width="70%">
			                      <INPUT id="PSInfo01_prodmemo" name="psProdmemo" value="<%=Util.getStringFormat( jobcode.getPsProdmemo() )  %>" <%=disabledVIEW %>>			                      
									</TD>
			                      </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">摺疊方式</TD>
			                      <TD width="70%">
<SELECT id="PSInfo01_psType"  name="codePsType" size="1" <%=disabledVIEW %>  >
<option value="" >請選擇</option>
<% for (int i=0; i<psTypeList.size(); i++) {
	Code _code = (Code)psTypeList.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByPsCodePsType() && _code.getId().compareTo(jobcode.getCodeByPsCodePsType().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT> 
								</TD>
			                      </TR>
								<TR>
			                      <TD width="30%" bgcolor="#DFF4DD">分區</TD>
			                      <TD width="70%">
									<INPUT type="radio" id="PSInfo01_zipFg" name="psZipFg" value="1"  <%=disabledVIEW %> <% if (null!= jobcode.getPsZipFg() && jobcode.getPsZipFg()) out.println("checked");%> /> 分區
									<INPUT type="radio" id="PSInfo01_zipFg" name="psZipFg" value="0"  <%=disabledVIEW %> <% if (null!= jobcode.getPsZipFg() && !jobcode.getPsZipFg()) out.println("checked");%>/> 不分區
								  </TD>
			                      </TR>

			                    <TR>
			                      <TH colSpan=2 align=left width="30%">本日公告</TH></TR>
			                    <TR>
			                      <TD colSpan=2 width="30%"><TEXTAREA cols="50" id="PSInfo01_remark" rows="4" name="psRemark" <%=disabledVIEW %>><%=Util.getStringFormat(jobcode.getPsRemark()) %></TEXTAREA></TD></TR></TBODY>
			                      </TABLE>
							

							
							</TD>
						</TR>
					</table>
						</TD></TR></TBODY></TABLE>

						
<!-- PSInfoList  本日公告-->						
					<TABLE id="table63" width="100%" >
                    <TBODY>
                    <TR>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></TR>
                    <TR>
                      <TD width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="psNote"  id="psNote" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getPsNote() ) %></TEXTAREA></TD></TR></TBODY>
						</TABLE>
					</TD>
				</TR>
			</table>
			


<!--PSInfoList End-->

<!--DM Star -->
		<TABLE border="0" width="100%" id="DMInfo" <% if (null!= jobcode.getMpDmPs() && (  jobcode.getMpDmPs().equalsIgnoreCase("DM") )) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"DISPLAY: none\" ");%> height="356">
				<TR>
					<TD bgcolor="#006F3F"><font size="2" color="#FFFFFF">Direct Mail</font></TD>
				</TR>
				<TR>
					<TD valign="top">
					

					<div align="center">
                  <TABLE border=0 cellSpacing=1 cellPadding=1 width="100%" 
                  bgColor=#006f3f id="table54" height="164">
                    <TBODY>
                    <TR>
                      <TD bgColor=#ffffff>
					
					<TABLE border="0" width="100%" id="table55" bgcolor="#DFF4DD">
						
						<TR>
							<TD colspan="2">
<TABLE id="table83" width="100%" >
                    <TBODY>
                    <TR>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></TR>
                    <TR>
                      <TD width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="dmNote"  id="dmNote" rows="20" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getDmNote() ) %></TEXTAREA></TD></TR></TBODY></TABLE>
							</TD>
						</TR>
					</table></TD></TR></TBODY></TABLE></div>
					</TD>
				</TR>
			</table>
			
			
<!--DM end-->

<!--Null star-->
		<TABLE border="0" width="100%" id="NULLInfo" style="DISPLAY: none" height="356">
				<TR>
					<TD bgcolor="#006F3F"></TD>
				</TR>
		</TABLE>
<!--Null end-->		
		
		</TD>
			
			
		<TD width="33%" valign="top">
<!--LGInfo Start-->
<TABLE border="0" width="100%" id="LGInfo" <% if (null!= jobcode.getIsLg() && jobcode.getIsLg()) out.println("style=\"visibility: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
		<TR>
			<TD bgcolor="#006F3F"><font size="2" color="#FFFFFF">交寄</font></TD>
		</TR>
		<TR>
		<TD>
		<TABLE border=0 cellSpacing=1 cellPadding=1 width="100%" bgColor=#006f3f id="table48">
		<TBODY>
		<TR>
		<TD bgColor=#ffffff>
		<TABLE border="0" width="100%" id="table49" bgcolor="#DFF4DD">
		<TR>
		<TD>
            <TABLE id=table56 border=0 cellSpacing=0 cellPadding=0 width="100%">
              	<TR>
                <TD width=100>出貨方式</TD>
                <TD>
					<SELECT size=1 name="dispatchType" id="dispatchType" onchange="javascript:changeDispatchType()" <%=disabledVIEW %>> 
					<option>請選擇</option>
					<OPTION value="MAIL" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("MAIL")) out.println("selected"); %> ()>一般郵件區</OPTION>  
	                <OPTION value="CUSTOMER" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("CUSTOMER")) out.println("selected"); %>>客戶親取</OPTION> 
					<option value="RETURN_CUSTOMER" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("RETURN_CUSTOMER")) out.println("selected"); %>>退回客戶</option>
					<option value="TO_CS" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("TO_CS")) out.println("selected"); %>>送交業務</option>
	                </SELECT>  
                </TD>
				</TR>

				<TR>
				<TD colSpan=2>
					<table border="0" width="100%" id="dispatchType_MAIL" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("MAIL")) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
				
				<TR>
                <TD colSpan=2>
                  	<input type="checkbox" onclick="javascript:hiddenTable(diapatchTable)" id="useLg" name="useLg" value="1" <% if (null!= jobcode.getUseLg() && jobcode.getUseLg() ) out.println("checked");%> <%=disabledVIEW %>>
                  	開立郵資單</TD>
				</TR>
				<TR>
				<TD colSpan=2>
					<TABLE border="0" width="100%" id="diapatchTable" <% if (null== jobcode.getUseLg() || (null!= jobcode.getUseLg() &&  !jobcode.getUseLg()) ) out.println("style=\"visibility: hidden \" ");%>>
					<TR>
	                <TD>郵資單代號</TD>
	                <TD>
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
	                    <a href="#" onclick="toEditLocation('lginfo', 'lgNo', 'lgNo', false)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a>
	                    </TD>
					</TR>

                      <TR>
                      <TD width="30%" bgcolor="#DFF4DD">郵資單抬頭</TD>
                      <TD width="70%">
					  <INPUT id="LGInfo01_prodmemo" name="lgProgNm" value="<%=Util.getStringFormat( jobcode.getLgProgNm() )  %>" <%=disabledVIEW %>>
					  </TD>
                      </TR>
                 
						<TR>
                      <TD width="30%" bgcolor="#DFF4DD">寄送地區</TD>
                      <TD width="70%">
<table border="0" width="100%" id="table6">
<tr>
<% for (int i=0; i<lgMailToAreaList.size(); i++) {
	Code _code = (Code)lgMailToAreaList.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByLgCodeMailToArea1() && _code.getId().compareTo(jobcode.getCodeByLgCodeMailToArea1().getId())==0 ) 
		selectString="checked";
	if (null!= jobcode.getCodeByLgCodeMailToArea2() && _code.getId().compareTo(jobcode.getCodeByLgCodeMailToArea2().getId())==0 ) 
		selectString="checked";
	if (null!= jobcode.getCodeByLgCodeMailToArea3() && _code.getId().compareTo(jobcode.getCodeByLgCodeMailToArea3().getId())==0 ) 
		selectString="checked";
	if (null!= jobcode.getCodeByLgCodeMailToArea4() && _code.getId().compareTo(jobcode.getCodeByLgCodeMailToArea4().getId())==0 ) 
		selectString="checked";
	if (null!= jobcode.getCodeByLgCodeMailToArea5() && _code.getId().compareTo(jobcode.getCodeByLgCodeMailToArea5().getId())==0 ) 
		selectString="checked";
	if (null!= jobcode.getCodeByLgCodeMailToArea6() && _code.getId().compareTo(jobcode.getCodeByLgCodeMailToArea6().getId())==0 ) 
		selectString="checked";	
%>					 
							<td><input type="checkbox" id="codeMailToArea<%=i %>" name="codeMailToArea" value="<%=_code.getId() %>" <%=selectString %> <%=disabledVIEW %> ><font size="2"><%=_code.getCodeValueTw() %></font></td>

<% }%>	
</tr>
</table>
					  </TD>
                      </TR>
					</TABLE>
				</TD>
				</TR>
				
              <TR>
                <TD width="30%">郵資單總類</TD>
                <TD width="70%">
<SELECT  id="codeLgType" name="codeLgType" size="1" <%=disabledVIEW %>>
<option value="" >請選擇</option>
<% for (int i=0; i<lgTypeList.size(); i++) {
	Code _code = (Code)lgTypeList.get(i);
	String selectString = "";
	if(null != jobcode.getCodeByCodeLgType() ){
	   if (_code.getId().compareTo(jobcode.getCodeByCodeLgType().getId())==0 ) 
		   selectString="selected";
	}else if ( disabledVIEW.equals("") && _code.getCodeValueTw().equalsIgnoreCase(_selectJON_LG.getLgType()) ){ 
		selectString="selected";	
	}
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT> 
				</TD>
				</TR>
						 
              <TR>
                <TD width="30%">交寄方式</TD>
                <TD width="70%">
<SELECT id="codeMailCategory" name="codeMailCategory" size="1" <%=disabledVIEW %>>
<option value="" >請選擇</option>
<% for (int i=0; i<lgMailCategory.size(); i++) {
	Code _code = (Code)lgMailCategory.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByCodeMailCategory() && _code.getId().compareTo(jobcode.getCodeByCodeMailCategory().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
				</TD>
				</TR>
				
										      				
              <TR>
                <TD width="30%" >交寄郵局或公司</TD>
                <TD width="70%">
<SELECT id="codeMailToPostoffice" name="codeMailToPostoffice" size="1" <%=disabledVIEW %> >
<option value="" >請選擇</option>
<% for (int i=0; i<lgMailToPostofficeList.size(); i++) {
	Code _code = (Code)lgMailToPostofficeList.get(i);
	String selectString = "";
	if (null!= jobcode.getCodeByCodeMailToPostoffice() && _code.getId().compareTo(jobcode.getCodeByCodeMailToPostoffice().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
				</TD>
				</TR>
		</table> <!-- end "dispatchType_MAIL" -->
		<table border="0" width="100%" id="logistic_controller" <% if (null!=jobcode.getDispatchType() && (jobcode.getDispatchType().equalsIgnoreCase("MAIL") || jobcode.getDispatchType().equalsIgnoreCase("RETURN_CUSTOMER"))) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>

              <tr>
                <TD>交寄管制表</TD>
                <TD>
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
	                    <a href="#" onclick="toEditLocation('lcinfo', 'lcNo', 'lcNo', false)"><SPAN STYLE="font-size: xx-small">連結</SPAN></a>
				</TD>
				</tr>
				<tr>
                      <TD width="30%" bgcolor="#DFF4DD">交寄管制表抬頭</TD>
                      <TD width="70%">
						<INPUT id="LCInfo01_prodmemo" name="lcProgNm" value="<%=Util.getStringFormat( jobcode.getLcProgNm() )  %>" <%=disabledVIEW %>>
					  </TD>
                 </tr>
                      
                      
				
								
				</table> 
				</TD>
				</TR>
				

				<TR>
				<TD colSpan=2>
					<TABLE border="0" width="100%" id="dispatchType_RETURN" <% if (null!=jobcode.getDispatchType() && jobcode.getDispatchType().equalsIgnoreCase("RETURN_CUSTOMER")) out.println("style=\"DISPLAY: visible\" "); else  out.println("style=\"visibility: hidden \" ");%>>
					<TR>
	                <TD>收件人代號</TD>
	                <TD>
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
	                 </TD>
					</TR>
                      <TR>
                      <TD width="30%" bgcolor="#DFF4DD">收件人公司</TD>
                      <TD width="70%">
			          <INPUT id="ReturnInfo01_userCompany" name="retUserCompany" value="<%=Util.getStringFormat( jobcode.getRetUserCompany() )  %>" <%=disabledVIEW %>>                      
                      </TD>
                      </TR>
                      <TR>
                      <TD width="30%" bgcolor="#DFF4DD">收件人名稱</TD>
                      <TD width="70%">
			          <INPUT id="ReturnInfo01_userName" name="retUserName" value="<%=Util.getStringFormat( jobcode.getRetUserName() )  %>" <%=disabledVIEW %>>                      
					  </TD>
                      </TR>
                      
						<TR>
                      <TD width="30%" bgcolor="#DFF4DD">收件人地址</TD>
                      <TD width="70%">
			          <INPUT id="ReturnInfo01_returnAddress" name="retReturnAddress" value="<%=Util.getStringFormat( jobcode.getRetReturnAddress() )  %>" <%=disabledVIEW %>>                      
                      </TD>
                      </TR>
						<TR>
                      <TD width="30%" bgcolor="#DFF4DD">收件人電話</TD>
                      <TD width="70%">
			          <INPUT id="ReturnInfo01_userTel" name="retUserTel" value="<%=Util.getStringFormat( jobcode.getRetUserTel() )  %>" <%=disabledVIEW %>>                      
                      </TD>
                      </TR>
					</TABLE>
		      	</TD>
		      	</TR>




			
			</TABLE>
							

							</TD>
						</TR>
					</table>
					
					
					
						</TD></TR></TBODY></TABLE>
					</TD>
				</TR>
				
<TR>
					<TD>
					

                  <TABLE border=0 cellSpacing=1 cellPadding=1 width="100%" 
                  bgColor=#006f3f id="table48">
                    <TBODY>
                    <TR>
                      <TD bgColor=#ffffff>
					
					<TABLE border="0" width="100%" id="table49" bgcolor="#DFF4DD">
						<TR>
							<TD>
							

                    <TR>
                      <TH width="30%" bgcolor="#006F3F">
						<font size="2" color="#FFFFFF">本日公告</font></TH></TR>
                    <TR>
                      <TD width="30%" bgcolor="#006F3F" height="59">
						<p align="center">
						<TEXTAREA cols="50" name="lgNote"  id="lgNote" rows="4" <%=disabledVIEW %>><%=Util.getStringFormat( jobcode.getLgNote() ) %></TEXTAREA></TD></TR></TABLE>
							

							</TD>
						</TR>
					</table>
					
					
					
						</TD></TR></TABLE>
<!--LGInfo end -->
		</TD>
		</TR>
	</table>			


<!-- 下方命令列控制區塊 -->		
	<TABLE width="95%" border="0" cellspacing="0" cellpadding="0">	
			<TR>
					<TD colSpan=4 align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='jobbag.do?fid=saveAsNewInit&jobBagNo=<%=jobcode.getJobBagNo()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
<%  } else { %>
						<input  type="button"  onclick="backToList()"   value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>

	</form>		
