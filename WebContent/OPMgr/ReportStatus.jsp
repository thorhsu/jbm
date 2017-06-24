<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="org.apache.commons.beanutils.BeanUtils"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c'%>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<script src="js/jbm.js" type="text/javascript"></script>
<%
	String currentDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

	JobBagService jobbagService = JobBagService.getInstance();
	
	String  targetURI = (String)request.getAttribute("targetURI");
	String actionName="jobbag";
	
	if (targetURI.equalsIgnoreCase("jobcode.do") ) 
		actionName="jobcode";


	JobBag jobcode= (JobBag)request.getAttribute("jobbag");
	if(jobcode != null){
	boolean hasLp = false;
    if (null!= jobcode.getIsLp() && jobcode.getIsLp())
        hasLp = true;
 
	String isCONTINUE = "NO";
	if(null != jobcode.getCodeByLpCodePrinter() && jobcode.getCodeByLpCodePrinter().getCodeKey().equalsIgnoreCase("CONTINUE"))
		isCONTINUE = "YES";
	else if(null != jobcode.getCodeByLpCodePrinter() && !jobcode.getCodeByLpCodePrinter().getCodeKey().equalsIgnoreCase("CONTINUE"))
		isCONTINUE = "NO";
	//else if (null != jobcode.getLpinfoByIdfLpNo1() && null!=jobcode.getLpinfoByIdfLpNo1().getCodeByCodePrinter() &&  jobcode.getLpinfoByIdfLpNo1().getCodeByCodePrinter().getCodeKey().equalsIgnoreCase("CONTINUE") )
	 	//isCONTINUE = "YES";
	JobBagSplite jobbagSplite= (JobBagSplite)request.getAttribute("jobbagSplite");
	String deptType = (String)request.getAttribute("deptType");
	
	Employee employee = (Employee)request.getAttribute("employee");
	Employee employeeMgr = (Employee)request.getAttribute("employeeMgr");
	
	
	String empNo="";
	String empManagerNo="";
	
	if (null!= employee) empNo = employee.getEmpNo();
	if (null!= employeeMgr) empManagerNo = employeeMgr.getEmpNo();
		
	String disabledLP="";
	String empLP="";
	String currentDateTimeLP="";
	 
	String disabledMP="";
	String empMP="";	
	String currentDateTimeMP="";
		
	String disabledLG="";
	String empLG="";	
	String currentDateTimeLG="";		

	disabledMP="readonly disabled class='disabled'";
	disabledLG="readonly disabled class='disabled'";
	disabledLP="readonly disabled class='disabled'";
	String disabledLogin="readonly disabled class='disabled'"; //Thor add
	String isLP="NO";
	String isMP="NO";
	String isLG="NO";
	
	if (null!=jobbagSplite.getLpCompletedUser() && jobbagSplite.getLpCompletedUser().length() >0) {
		empLP = jobbagSplite.getLpCompletedUser();
		currentDateTimeLP = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(jobbagSplite.getLpCompletedDateByUser());

	}
		
	if (null!=jobbagSplite.getMpCompletedUser() && jobbagSplite.getMpCompletedUser().length() >0) {
		empMP = jobbagSplite.getMpCompletedUser();
		currentDateTimeMP = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(jobbagSplite.getMpCompletedDateByUser());
		
	}
	
	if (null!=jobbagSplite.getLgCompletedUser() && jobbagSplite.getLgCompletedUser().length() >0) {
		empLG = jobbagSplite.getLgCompletedUser();
		currentDateTimeLG = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(jobbagSplite.getLgCompletedDateByUser());

	}			
			
	if (null!= deptType && deptType.equalsIgnoreCase("LP"))  {
		disabledLP="";
		disabledLogin="";
		if (null==jobbagSplite.getLpCompletedUser() ) {
			empLP = employee.getEmpNo() + "  " + employee.getUserId();
			currentDateTimeLP = currentDateTime;
			isLP = "YES";
		}
	}
	else if (null!= deptType && deptType.equalsIgnoreCase("MP")) 	{
		disabledMP="";
		disabledLogin="";	
		if (null==jobbagSplite.getMpCompletedUser() ) {		
			empMP = employee.getEmpNo() + "  " + employee.getUserId();
			currentDateTimeMP= currentDateTime;
			isMP = "YES";
		}
	}			
	else if (null!= deptType && deptType.equalsIgnoreCase("LG")) {
		// Dispatch 可以改 LP/MP 的回覆
		disabledMP="";
		disabledLP="";
		disabledLG="";
		disabledLogin="";
		if (null==jobbagSplite.getLgCompletedUser() ) {		
			empLG = employee.getEmpNo() + "  " + employee.getUserId();
			currentDateTimeLG= currentDateTime;
			isLP = "YES";
			isMP = "YES";
			isLG = "YES";
		}
	}

	
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
	
	CodeService codeService = CodeService.getInstance();
	List jobcodeTypeList = codeService.findBycodeTypeName("JOB_CODE_TYPE"); //工作種類
	List deadlineList = codeService.findBycodeTypeName("DEADLINE"); //DEADLINE
	List machineLPList = codeService.findBycodeTypeName("MACHINE_LP"); //MACHINE_LP	
	List machineMPList = codeService.findBycodeTypeName("MACHINE_MP"); //MACHINE_MP


	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE"); 

	//若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; 
		disabledPK="";
		jobcode.setJobBagNo("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
	
		
%>

<script type="text/javascript">
var isCONTINUE = "<%=isCONTINUE%>"; //是否連續列印
var isLP = "<%=isLP%>";
var isLG = "<%=isLG%>";
var hasLp = <%=hasLp%>;
var empLP = "<%=jobbagSplite.getLpCompletedUser()%>"; // Thor Add
var isMP = "<%=isMP%>";
var finishedAccountsVal = "${finishedAccounts}";
var finishedPagesVal = "${finishedPages}";
$().ready(function() {
	//"accountsEqual", "LP_ALL_Completed"
	var accountsEqual = "${accountsEqual}";
	
	if(accountsEqual == "LP_ALL_Completed"){
		if(confirm("此Job Bag的acccounts及頁數，目前均已記錄成列印完成，請問要刪除其它尚未記錄完成的分檔批次嗎？")){
			waitToFinished();
			window.location = "completedJob.do?fid=deleteSplites&jobBagSpliteNo=<%=jobbagSplite.getJobBagSpliteNo()%>&empManagerNo=<%=empManagerNo%>&empNo=<%=empNo%>&deptType=LP";
		}		
	}else if(accountsEqual == "MP_ALL_Completed"){
		if(confirm("此Job Bag的acccounts及頁數，目前均已記錄成裝封完成，請問要刪除其它尚未記錄完成的分檔批次嗎？")){
			waitToFinished();
			window.location = "completedJob.do?fid=deleteSplites&jobBagSpliteNo=<%=jobbagSplite.getJobBagSpliteNo()%>&empManagerNo=<%=empManagerNo%>&empNo=<%=empNo%>&deptType=MP";
		}		
	}
	
});


$().ready(function() {
	var pagesVal = $("#pages").val();
    var accountsVal = $("#accounts").val();
    
	if( pagesVal != "" && pagesVal !="0" && finishedPagesVal != "" && parseInt($("#pages").val()) <=  parseInt(finishedPagesVal)){
		alert("請注意，其它分檔批次已記錄完成的總頁數為" + finishedPagesVal + "大於等於工單頁數" + $("#pages").val());
	}
	if( accountsVal != "" && accountsVal !="0" && finishedAccountsVal != "" && parseInt($("#accounts").val()) <=  parseInt(finishedAccountsVal)){
		alert("請注意，其它分檔批次已記錄完成的總accounts為" + finishedAccountsVal + "大於等於工單accounts" + $("#accounts").val());
	}	
	
	// validate my form when it is submitted
	$("#myform").validate({
	  rules: {
	    lpZipCodeBegin: {
	      required: true,
	      number: true
	    },
	    lpZipCodeEnd: {
	      required: true,
	      number: true
	    },
	    lpZipCodeDiff: {
	      required: true,
	      number: true,
	      min: 0	      
	    },	    
	    lpBlankBegin: {
	      required: true,
	      number: true
	    },
	    lpBlankEnd: {
	      required: true,
	      number: true
	    },
	    lpBlankDiff: {
	      required: true,
	      number: true,
	      min: 0	      
	    },	    	
	    lpPaperBegin: {
	      required: true,
	      number: true
	    },
	    lpPaperEnd: {
	      required: true,
	      number: true
	    },	
	    lpPaperDiff: {
	      required: true,
	      number: true,
	      min: 0	      
	    },	    
	    lpPagesSeqBegin: {
	      required: true,
	      number: true
	    },
	    lpPagesSeqEnd: {
	      required: true,
	      number: true
	    },	   
	    lpPagesSeqDiff: {
	      required: true,
	      number: true,
	      min: 0	      
	    },	    
	    lpPagesSeqExtra: {
	      number: true    
	    },		          	      
	    lpAccountSeqBegin: {
	      required: true,
	      number: true
	    },	         	      
	    lpAccountSeqEnd: {
	      required: true,
	      number: true
	    },
	    lpAccountSeqDiff: {
	      required: true,
	      number: true,
	      min: 0	      
	    }	    	    
	  }
	});
	  
	  
	  
	//檢查 開始頁.diff 是否為 pages.diff 的倍數(1,2,4)
	$("#myform").submit(function(){
	    var pagesVal = $("#pages").val();
	    var sheetsVal = $("#sheets").val();
	    var accountsVal = $("#accounts").val();
	    var pagesHiddenVal = $("#pagesHidden").val();
	    var sheetsHiddenVal = $("#sheetsHidden").val();
	    var accountsHiddenVal = $("#accountsHidden").val();
	    if(pagesVal != pagesHiddenVal || sheetsHiddenVal != sheetsVal || accountsHiddenVal != accountsVal){
	       var formSubmit = confirm("你設定的新頁數或accounts或紙張數和原先不同，請確認修改")
	       if(formSubmit){
	          var r = /^[0-9]*[1-9][0-9]*$/;   //正整数 
              if(!r.test(pagesVal) || !r.test(accountsVal) || !r.test(sheetsVal)){
                 alert("修改原有數字時，pages, sheets, 及accounts三項都必須輸入數字");
                 return false;
              }  
              
	          $("#pagesHidden").val(pagesVal);
	          $("#sheetsHidden").val(sheetsVal);
	          $("#accountsHidden").val(accountsVal);
	          $("#changeSubmit").val("true");
	          
	       }else{
	    	  $("#changeSubmit").val("false");
	          return false;
	       }
	    }
	    if($('#lpPagesSeqDiff').val() == '' || $('#lpPagesSeqDiff').val() == '0' ){
	    	if(!confirm("頁數為零或空白，是否繼續送出？"))
	    		return false;
	    	
	    }else if( $('#lpAccountSeqDiff').val() == '' || $('#lpAccountSeqDiff').val() == '0'){
	    	if( !confirm("accounts為零或空白，是否繼續送出？"))	    	
		       return false;
	    }
	    
	    if((finishedAccountsVal != "" && finishedAccountsVal != "0") || (finishedPagesVal != "" && finishedPagesVal != "0")){	    	
	    	var pagesTotal = 0;
	    	var accountsTotal = 0;
	    	
	    	if(finishedPagesVal != "")
	    		pagesTotal = parseInt( $('#lpPagesSeqDiff').val()) + parseInt(finishedPagesVal) ;
	    	if(finishedAccountsVal != "")
	            accountsTotal = parseInt($('#lpAccountSeqDiff').val()) + parseInt(finishedAccountsVal);
	    
	    	
	    	if(pagesVal != "0" && pagesTotal > parseInt(pagesVal)){
	    		alert("已記錄完成的其它分檔批次頁數加上本批次的頁數：" + pagesTotal + "，大於工單的總頁數：" + pagesVal);
	    		return false;
	    	}
	    	
	    	if(accountsVal != "0" && accountsTotal > parseInt(accountsVal)){
	    		alert("已記錄完成的其它分檔批次accounts加上本批次的accounts：" + accountsTotal + "，大於工單的總accounts數：" + accountsVal);
	    		return false;
	    	}
	    	
	    }
	    

        if (isCONTINUE == "YES") {
        	var checkFG = false;
        	//檢查 開始頁.diff 是否為 pages.diff 的倍數(1,2,4)
        	if (parseInt( $('#lpPaperDiff').val() ) == (parseInt( $('#lpPagesSeqDiff').val())  + parseInt( $('#lpPagesSeqExtra').val() )) ){
        		checkFG = true;
        	}else if ( 2* parseInt( $('#lpPaperDiff').val() ) == (parseInt( $('#lpPagesSeqDiff').val())  + parseInt( $('#lpPagesSeqExtra').val() )) ){
        		checkFG = true;        		
        	}else if ( 4* parseInt( $('#lpPaperDiff').val() ) == (parseInt( $('#lpPagesSeqDiff').val())  + parseInt( $('#lpPagesSeqExtra').val())) ){
        		checkFG = true;        		
        	}

        	if ( ! checkFG) {
        	 	alert("連續列印時，開始頁Pre-No的差Diff ("+ $('#lpPaperDiff').val() + ")，必須與頁數Pages Seq.的差Diff加extra值(" 
        	 			+ $('#lpPagesSeqDiff').val() + "+" + $('#lpPagesSeqExtra').val() + "=" 
        	 			+ (parseInt( $('#lpPagesSeqDiff').val())  + parseInt( $('#lpPagesSeqExtra').val()))  + ") 成1或2或4的倍數 關係");
        		return false;
			}
        }
        
        if (isLP == "YES" && hasLp) {
        	if ( $('#lpMachineName').val()=="" ) {
        	 	alert("請選擇列印機台");
        		return false;
			}
        }
        
        if (isMP == "YES") {
        	if ( $('#mpMachineName').val()=="" ) {
        	 	alert("請選擇裝封機台");
        		return false;
			}
        }   
        //Thor modified
        if(hasLp && empLP == "null" && isMP == "YES"){
            alert("需要列印，請先完成列印記錄工作，才能繼續其它記錄工作");
            return false;
        }
        
        return true;     
	});
		  


	

	
	//計算 Diff 
	checkZipDiff = function (){
		//$('#lpZipCodeDiff').val( parseInt($('#lpZipCodeBegin').val()) - parseInt($('#lpZipCodeEnd').val() ) + 1 );
		if (parseInt( $('#lpZipCodeBegin').val()) == 0 && parseInt($('#lpZipCodeEnd').val()) ==0 )
			$('#lpZipCodeDiff').val(0);			
  	};
  	$('#lpZipCodeBegin').blur(checkZipDiff);
  	$('#lpZipCodeEnd').blur(checkZipDiff);
  	
	checkBlankDiff = function (){
	    var diffVal = Math.abs(parseInt($('#lpBlankBegin').val()) - parseInt($('#lpBlankEnd').val()) );
		$('#lpBlankDiff').val( diffVal + 1);
		if (parseInt( $('#lpBlankBegin').val()) == 0 && parseInt($('#lpBlankEnd').val()) ==0 )
			$('#lpBlankDiff').val(0);			
  	};
  	$('#lpBlankBegin').blur(checkBlankDiff);
  	$('#lpBlankEnd').blur(checkBlankDiff);
  	
	checkPaperDiff = function (){	
	    var diffVal = Math.abs(parseInt($('#lpPaperBegin').val()) - parseInt($('#lpPaperEnd').val()  ));
		$('#lpPaperDiff').val(diffVal + 1);
			
		
		if (parseInt( $('#lpPaperBegin').val()) == 0 && parseInt($('#lpPaperEnd').val()) ==0 )
			$('#lpPaperDiff').val(0);			
  	};
  	$('#lpPaperBegin').blur(checkPaperDiff);
  	$('#lpPaperEnd').blur(checkPaperDiff);
  	
	checkPagesSeqDiff = function (){
		$('#lpPagesSeqDiff').val( parseInt($('#lpPagesSeqEnd').val()) - parseInt($('#lpPagesSeqBegin').val()   ) + 1);
		
		if (parseInt( $('#lpPagesSeqEnd').val()) == 0 && parseInt($('#lpPagesSeqBegin').val()) ==0 )
			$('#lpPagesSeqDiff').val(0);
  	};
  	$('#lpPagesSeqBegin').blur(checkPagesSeqDiff);
  	$('#lpPagesSeqEnd').blur(checkPagesSeqDiff);
  	
	checkAccountSeqDiff = function (){
		$('#lpAccountSeqDiff').val( parseInt($('#lpAccountSeqEnd').val()) - parseInt($('#lpAccountSeqBegin').val()  ) + 1);
		if (parseInt( $('#lpAccountSeqEnd').val()) == 0 && parseInt($('#lpAccountSeqBegin').val()) ==0 )
			$('#lpAccountSeqDiff').val(0);		
  	};
  	$('#lpAccountSeqBegin').blur(checkAccountSeqDiff);
  	$('#lpAccountSeqEnd').blur(checkAccountSeqDiff);  
  	$("#jobBagSpliteNo").select();	  	
  	  	  	
});

function nextFocus(event, focusId){
   var nextId = document.getElementById(focusId);
   if(event.keyCode == "17"){
       if($(nextId).attr("type") != "text")
          nextId.focus();
       else
          nextId.select();
       
   }

}
</script>



		
			<TABLE width="95%" border="0" cellspacing="0" cellpadding="0">
				<TR>
					<TD height="26" class="title">記錄完成工作</TD>
				</TR>
				<TR>
				  <TD height="15">&nbsp;</TD>
				</TR>	
				<TR>
					<TD height="26" class="title">
					<form action="completedJob.do" method="post" >
					<input type="hidden" name="empManagerNo" value="<%=empManagerNo %>" />
					<input type="hidden" name="empNo" value="<%=empNo %>" />
					<input type="hidden" name="fid" value="reportStatusInit"/>
	                <INPUT id="jobBagSpliteNo" size="60" name="jobBagSpliteNo"  onkeyup="nextFocus(event, 'lpZipCodeBegin')"/>
	                (請刷Bar Code, 含批號)<input type="submit" value="查詢"/>
	                </form><font color="red">${message}</font>
                	</TD>
				</TR>
			</table>
			
<form name="myform" id="myform" action="completedJob.do" method="post">
<input type="hidden" name="backToListURL" value="${backToListURL}" />
<INPUT type="hidden" id="pagesHidden"   name="pagesHidden" value="<%=Util.getDecimalFormatWithThousandSeparator( jobcode.getPages()) %>" />
<INPUT type="hidden" id="sheetsHidden" name="sheetsHidden" value="<%=Util.getDecimalFormatWithThousandSeparator( jobcode.getSheets()) %>" />
<INPUT type="hidden" id="accountsHidden" name="accountsHidden" value="<%=Util.getDecimalFormatWithThousandSeparator( jobcode.getAccounts()  )%>" />
<input type="hidden" name="fid" value="reportStatusSubmit" />
<input type="hidden" name="empManagerNo" value="<%=empManagerNo %>" />
<input type="hidden" name="empNo" value="<%=empNo %>" />
<input type="hidden" id="deptType" name="deptType" value="<%=deptType %>" />
<input type="hidden" id="changeSubmit" name="changeSubmit" value="false" /> 
<INPUT type="hidden" id="jobBagSpliteNo" name="jobBagSpliteNo" value="<%=Util.getStringFormat( jobbagSplite.getJobBagSpliteNo() ) %>"  >


<!-- LP report  -->
			<TABLE width="95%" border="0" cellspacing="0" cellpadding="0" id="table_LP">
			<tr>
                <TD height=10 >
                        <TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" 
                        bgColor=#4aae66 align=left id="table185">
                          <TBODY>
                          <tr>
                            <TD bgColor=#4AAE66 width="100%" 
                            ><font size="2">雷射列印 LASER PRINT</font></TD>
                            </tr>
                          <TR>
                            <TD bgColor=#DFF4DD width="100%" 
                            >
							<table border="0" width="100%" id="table188">
								<tr>
									<td width="5%">　</td>
									<td width="13%"><font size="2">ZIPCode</font></td>
									<td width="13%"><font size="2">空白Blank</font></td>
									<td width="13%"><font size="2">開始頁Pre-No</font></td>
									<td width="13%"><font size="2">頁數 Pages Seq.</font></td>
									<td width="13%"><font size="2">帳號 Account Seq</font></td>
									<td><font size="2">完成</font></td>
									<td></td>
								</tr>
								<tr>
									<td width="5%"><font size="2">起Start</font></td>
									<td width="13%">
									<INPUT size="10" name="lpZipCodeBegin" id="lpZipCodeBegin" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpZipCodeBegin() )%>" <%=disabledLP%>  onkeyup="checkZipDiff();nextFocus(event, 'lpZipCodeEnd')" ></td>
									<td width="13%">
									<INPUT size="10" name="lpBlankBegin" id="lpBlankBegin" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpBlankBegin() )%>" <%=disabledLP%> onkeyup="checkBlankDiff();nextFocus(event, 'lpBlankEnd')" ></td>
									<td width="13%">
									<INPUT size="10" name="lpPaperBegin" id="lpPaperBegin" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpPaperBegin() )%>" <%=disabledLP%> onkeyup="checkPaperDiff();nextFocus(event, 'lpPaperEnd')" ></td>
									<td width="13%">
									<INPUT size="10" name="lpPagesSeqBegin" id="lpPagesSeqBegin" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpPagesSeqBegin() )%>" <%=disabledLogin%> onkeyup="checkPagesSeqDiff();nextFocus(event, 'lpPagesSeqEnd')" ></td>
									<td width="13%">
									<INPUT size="10" name="lpAccountSeqBegin" id="lpAccountSeqBegin" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpAccountSeqBegin() )%>" <%=disabledLogin%> onkeyup="checkAccountSeqDiff();nextFocus(event, 'lpAccountSeqEnd')"></td>
									<td width="18%" rowspan="3">
							<table border="0" width="100%" id="table199">
								<tr>
									<td width="6%"><font size="2">機台</font></td>
									<td width="12%">
<SELECT name="lpMachineName" id="lpMachineName" size="1" <%=disabledLP%> onkeyup="nextFocus(event, 'submit')" >
<option value="" >請選擇</option>
<% for (int i=0; i<machineLPList.size(); i++) {
	Code _code = (Code)machineLPList.get(i);
	String selectString = "";
	if (null!= jobbagSplite.getLpMachineName() && _code.getCodeValueTw().equalsIgnoreCase(jobbagSplite.getLpMachineName())) 
		selectString="selected";
%>					 
							<option value="<%=_code.getCodeValueTw() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>

									
									</td>
								</tr>
								<tr>
									<td width="6%"><font size="2">時間</font></td>
									<td width="12%"><font size="2"><%=currentDateTimeLP %></font></td>
								</tr>
								<tr>
									<td width="6%"><font size="2">人員</font></td>
									<td width="12%"><%=empLP %></td>
								</tr>
							</table>
									</td>
									<td width="18%" rowspan="3">　</td>
								</tr>
								<tr>
									<td width="5%"><font size="2">迄End</font></td>
									<td width="13%">
									<INPUT size="10" name="lpZipCodeEnd" id="lpZipCodeEnd" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpZipCodeEnd() )%>" <%=disabledLP%> onkeyup="checkZipDiff();nextFocus(event, 'lpBlankBegin')" ></td>
									<td width="13%">
									<INPUT size="10" name="lpBlankEnd" id="lpBlankEnd" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpBlankEnd() )%>" <%=disabledLP%> onkeyup="checkBlankDiff();nextFocus(event, 'lpPaperBegin')" ></td>
									<td width="13%">
									<INPUT size="10" name="lpPaperEnd" id="lpPaperEnd" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpPaperEnd() )%>" <%=disabledLP%> onkeyup="checkPaperDiff();nextFocus(event, 'lpPagesSeqBegin')" ></td>
									<td width="13%">
									<INPUT size="10" name="lpPagesSeqEnd" id="lpPagesSeqEnd" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpPagesSeqEnd() )%>" <%=disabledLogin%> onkeyup="checkPagesSeqDiff();nextFocus(event, 'lpAccountSeqBegin')" ></td>
									<td width="13%">
									<INPUT size="10" name="lpAccountSeqEnd" id="lpAccountSeqEnd" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpAccountSeqEnd() )%>" <%=disabledLogin%> onkeyup="checkAccountSeqDiff();nextFocus(event, 'lpMachineName')" ></td>
								</tr>
								<tr>
									<td width="5%"><font size="2">差Diff</font></td>
									<td width="13%">
									<INPUT size="10" name="lpZipCodeDiff" id="lpZipCodeDiff" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpZipCodeDiff() )%>" readonly class='disabled'></td>
									<td width="13%">
									<INPUT size="10" name="lpBlankDiff" id="lpBlankDiff"  value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpBlankDiff() )%>" readonly class='disabled'></td>
									<td width="13%">
									<INPUT size="10" name="lpPaperDiff" id="lpPaperDiff" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpPaperDiff() )%>" readonly class='disabled'></td>
									<td width="13%">
									<INPUT size="3" name="lpPagesSeqDiff" id="lpPagesSeqDiff" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpPagesSeqDiff() )%>" readonly class='disabled'>
									<INPUT size="3" name="lpPagesSeqExtra" id="lpPagesSeqExtra" value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpPagesSeqExtra() )%>" >
									</td>
									<td width="13%">
									<INPUT size="10" name="lpAccountSeqDiff" id="lpAccountSeqDiff"  value="<%=Util.getDecimalFormatWithThousandSeparator( jobbagSplite.getLpAccountSeqDiff() )%>" readonly class='disabled'></td>
								</tr>
								</table>
							</TD>
                            </TR>
                          </TBODY></TABLE>
				</TD>
				</tr>
				</TABLE>
<!-- MP report  -->
				<TABLE width="95%" border="0" cellspacing="0" cellpadding="0" id="table_MP">
				<TR>
                <TD height=10 >
                        <TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" 
                        bgColor=#4aae66 align=left id="table191">
                          <TBODY>
                          <tr>
                            <TD bgColor=#4AAE66 width="100%" 
                            >MP/PS/DM</TD>
                            </tr>
                          <TR>
                            <TD bgColor=#DFF4DD width="100%" >
							<table border="0" width="100%" id="table192">
								<tr>
									<td>是否損壞Spoils</td>
									<td>機台</td>
									<td>完成人員</td>
									<td>完成時間</td>
									<td width="106"></td>
								</tr>
								<tr>
									<td><INPUT type="checkbox" id="mpHasDamage" name="mpHasDamage"  value="1" <% if(null!=jobbagSplite.getMpHasDamage() && jobbagSplite.getMpHasDamage() ) out.println("checked");%> <%=disabledMP %> />有損壞
									</TD>
									<td>
<SELECT name="mpMachineName" id="mpMachineName" size="1" <%=disabledMP%>>
<option value="" >請選擇</option>
<% for (int i=0; i<machineMPList.size(); i++) {
	Code _code = (Code)machineMPList.get(i);
	String selectString = "";
	if (null!= jobbagSplite.getMpMachineName() && _code.getCodeValueTw().equalsIgnoreCase(jobbagSplite.getMpMachineName())) 
		selectString="selected";
%>					 
							<option value="<%=_code.getCodeValueTw() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>									
									</td>
									<td><%=empMP %></td>
									<td><%=currentDateTimeMP %></td>
									<td width="106"></td>
								</tr>
																
								
								</table>
							</TD>
                            </TR>
                          </TBODY></TABLE>
				</TD></TR>
				</TABLE>
				
<!-- LG rport  -->				
			<TABLE width="95%" border="0" cellspacing="0" cellpadding="0" id="table_LG">
			<TR>
                <TD height=10 >
                        <TABLE border=0 cellSpacing=1 cellPadding=6 width="100%" 
                        bgColor=#4aae66 align=left id="table195">
                          <TBODY>
                          <tr>
                            <TD bgColor=#4AAE66 width="100%" ><font size="2">交寄DISPATCH</font></TD>
                            </tr>
                          <TR>
                            <TD bgColor=#DFF4DD width="100%" >
							<table border="0" width="100%" id="table192">
								<tr>
									<td>完成人員</td>
									<td>完成時間</td>
									<td width="106"></td>
								</tr>
								<tr>
									<td><%=empLG %></td>
									<td><%=currentDateTimeLG %></td>
									<td width="106"></td>
								</tr>
																
								
								</table>
							</TD>
                            </TR>
                          </TBODY></TABLE>
				</TD>
				
				</TR>
				</TABLE>

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
						<input name="button6" type="button"  onclick="javascript:history.go(-1)"  id="button6" value="取消, 回列表" />
<%  } else { %>
						<INPUT id="submit" value="確認完成紀錄" type="submit" name="submit">
						<input name="button6" type="button"  onclick="javascript:history.go(-1)"  id="button6" value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>

</form>	
	
			
			
			

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
					<TD class="editTableLabelCellBKColor"  width="93" rowspan="2"><font color="#FF0000" size="2">*</font>有效工作資料</TD>
					<TD class="editTableContentCellBKColor" align=left width="110" rowspan="2">
					<input type="checkbox"  id="isEnabledContract" name="isEnabledContract" value="1" <% if (null!= jobcode.getIsEnabledContract() && jobcode.getIsEnabledContract()) out.println("checked");%> <%=disabledVIEW %>>
					</TD>
					<TD class="editTableContentCellBKColor" align=left rowspan="5" valign="top" width="200">
<!-- 工單右邊區塊 -->
<%
request.setAttribute("ACTION_MODE", "ADD");
if (actionName.equalsIgnoreCase("jobcode")) { %>
					<jsp:include page="/entityMgr/JobCodeEdit_Cycle.inc.jsp" />
<% } else  {%>				
					<jsp:include page="/entityMgr/JobBagEdit_AccountInfo.inc.jsp" />
<% } 
request.setAttribute("ACTION_MODE", "VIEW");
%>	


					</TD>
					</TR> 
<!-- Line 2 -->					
					<TR>
					<TD class="editTableLabelCellBKColor"  width="70"><font color="#FF0000" size="2">*</font>工作代號</TD>
					<TD class="editTableContentCellBKColor" align=left width="82">
					<INPUT id="jobCodeId" size="6"  name="jobCodeId" value="<%=Util.getStringFormat( jobcode.getJobCodeId() ) %>" <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="6" minlength="4"><span id="msg_pk_exist"></span>
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

					
					</TD>
					<TD class="editTableLabelCellBKColor" width="43" >交寄</TD>
					<TD class="editTableContentCellBKColor" align=left width="125">
					<input type="checkbox" onclick="javascript:hiddenTable(LGInfo)" id="isLg" name="isLg" value="1" <% if (null!= jobcode.getIsLg() && jobcode.getIsLg()) out.println("checked");%> <%=disabledVIEW %>>Dispatch
					</TD>
					
					<TD class="editTableLabelCellBKColor"  width="70" rowspan="2"><font color="#FF0000" size="2">*</font>Deadline</TD>
					<TD class="editTableContentCellBKColor" align=left width="67" rowspan="2">
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
					
					</TD>
					<TD class="editTableLabelCellBKColor"  width="93" rowspan="2"><font color="#FF0000" size="2">*</font>是否有Convert Result
Result</TD>
					<TD class="editTableContentCellBKColor" align=left width="110" rowspan="2">
					<input type="checkbox"  id="isConvertResult" name="isConvertResult" value="1" <% if (null!= jobcode.getIsConvertResult() && jobcode.getIsConvertResult()) out.println("checked");%> <%=disabledVIEW %>>
					</TD>
					</TR>
					
					<TR>
					<TD class="editTableLabelCellBKColor" width="43"  >EDD</TD>
					<TD class="editTableContentCellBKColor" align=left width="125" >
					<input type="checkbox" onclick="javascript:hiddenTable(EDDInfo)" id="isEdd" name="isEdd" value="1" <% if (null!= jobcode.getIsEdd() && jobcode.getIsEdd()) out.println("checked");%> <%=disabledVIEW %>>EDD
					</TD>
					
					</TR>
					
<!-- Line 4 -->					
					<TR>
					<TD class="editTableLabelCellBKColor"   width="70"><font color="#FF0000" size="2">*</font>活動名稱</TD>
					<TD class="editTableContentCellBKColor" align=left width="276"  colspan="3">
					<INPUT id="progNm" maxLength=40 size=40 name="progNm" value="<%=Util.getStringFormat( jobcode.getProgNm() ) %>" <%=disabledVIEW %>>
					</TD>
					<TD class="editTableLabelCellBKColor"  width="70" ><font color="#FF0000">*</font>檔名規則</TD>
					<TD class="editTableContentCellBKColor" align=left width="270"  colspan="3">
						<INPUT id="filename" name="filename" value="<%=Util.getStringFormat( jobcode.getFilename() )  %>" <%=disabledVIEW %>>
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
<jsp:include page="/entityMgr/JobBagEdit_Splite_SC.inc.jsp" />
<!-- end 分檔區塊-->	
                
                				
<!-- 中間空白行-->	
				<TABLE width="95%" border="0" cellspacing="0" cellpadding="0">
				<TR><TD height=10 >
				</TD></TR>
                </TABLE>
<% } %>               

