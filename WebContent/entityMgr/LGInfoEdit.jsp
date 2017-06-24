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
<%
	String message = (String )request.getAttribute("message");
	//String backToListURL = (String )request.getSession().getAttribute("backToListURL");
	Lginfo lginfo= (Lginfo)request.getAttribute("lginfo");
	CustomerService customerService = CustomerService.getInstance();
	List customerList = customerService.findAll();
	

	CodeService codeService = CodeService.getInstance();
	List lgTypeList = codeService.findBycodeTypeName("LG_TYPE"); //郵資單總類
	List lgMailToPostofficeList = codeService.findBycodeTypeName("LG_MAIL_TO_POSTOFFICE"); //交寄郵局或公司
	
	List lgMailCategoryList = codeService.findBycodeTypeName("LG_MAIL_CATEGORY"); //交寄方式
	List lgMailToAreaList = codeService.findBycodeTypeName("LG_MAIL_TO_AREA"); //郵資單寄送地區
	String lgDisplayQtySelect = (lginfo.getLgDisplayQty() == null || lginfo.getLgDisplayQty())? "" : "checked";
	String nextLgDisplayQtySelect = (lginfo.getNextLgDisplayQty() == null || lginfo.getNextLgDisplayQty())? "" : "checked";


	//編輯模式 判斷
	String fid="editSubmit"; //預設editSubmit
	String disabledPK="readonly class=\"disabled\"";
	String disabledVIEW="";
	String actionMode = (String)request.getAttribute("ACTION_MODE");

	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD")) ) {
		fid="addSubmit"; //若為SAVE_AS_NEW, 則將fid 轉為 addSubmit
		disabledPK="";
		lginfo.setLgNo("");
	} else if (null!=actionMode &&  actionMode.equalsIgnoreCase("VIEW") ){
		disabledVIEW="readonly disabled class=\"disabled\"";
	}
		
%>
<script type="text/javascript"> 

$().ready(function() {
	// validate my form when it is submitted
	$("#myform").validate();
	
	$("#myform").submit(function(){
		var nextEffectiveDate = $('#nextEffectiveDate').val();
		var nextCodeLgType = $('#nextCodeLgType').val();
		var codeMailToPostoffice = $('#codeMailToPostoffice').val();
		var nextCodeMailToPostoffice = $('#nextCodeMailToPostoffice').val();
		var codeMailCategory = $('#codeMailCategory').val();

		var codeMailToArea0 = "";
		var codeMailToArea1 = "";
		var codeMailToArea2 = "";
		var codeMailToArea3 = "";
		var codeMailToArea4 = "";
		var nextCodeMailToArea0 = "";
		var nextCodeMailToArea1 = "";
		var nextCodeMailToArea2 = "";
		var nextCodeMailToArea3 = "";
		var nextCodeMailToArea4 = "";		
			
		if (($("input[id='codeMailToArea0']").is(":checked"))==true) codeMailToArea0 = $('#codeMailToArea0').val();
		if (($("input[id='codeMailToArea1']").is(":checked"))==true) codeMailToArea1 = $('#codeMailToArea1').val();
		if (($("input[id='codeMailToArea2']").is(":checked"))==true) codeMailToArea2 = $('#codeMailToArea2').val();
		if (($("input[id='codeMailToArea3']").is(":checked"))==true) codeMailToArea3 = $('#codeMailToArea3').val();
		if (($("input[id='codeMailToArea4']").is(":checked"))==true) codeMailToArea4 = $('#codeMailToArea4').val();
		if (($("input[id='nextCodeMailToArea0']").is(":checked"))==true) nextCodeMailToArea0 = $('#nextCodeMailToArea0').val();
		if (($("input[id='nextCodeMailToArea1']").is(":checked"))==true) nextCodeMailToArea1 = $('#nextCodeMailToArea1').val();
		if (($("input[id='nextCodeMailToArea2']").is(":checked"))==true) nextCodeMailToArea2 = $('#nextCodeMailToArea2').val();
		if (($("input[id='nextCodeMailToArea3']").is(":checked"))==true) nextCodeMailToArea3 = $('#nextCodeMailToArea3').val();
		if (($("input[id='nextCodeMailToArea4']").is(":checked"))==true) nextCodeMailToArea4 = $('#nextCodeMailToArea4').val();		

		var PList = "";
		var nextPList = "";
		
		if ($('#PList').get(0).checked == true) PList = "1";
		if ($('#nextPList').get(0).checked == true) nextPList = "1";		


		//連動選單規則: 
		//交寄郵局 ==金南郵局(54), 北市(21)/外地(22)/雜項(23)/國外(25) ,多選
		//交寄郵局 ==汐止郵局(55), 分區(24)/雜項(23)/國外(25) ,多選
		//交寄郵局 ==新竹郵局(56), 北市(21)/外地(22)/雜項(23) ,多選
		if (codeMailToPostoffice == "54") {
			if (codeMailToArea0!="" && codeMailToArea0!="21" && codeMailToArea0!="22" && codeMailToArea0!="23" && codeMailToArea0!="25" ) {
				
				submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
				return false;
			}
			if (codeMailToArea1!="" && codeMailToArea1!="21" && codeMailToArea1!="22" && codeMailToArea1!="23" && codeMailToArea1!="25" ) {
				submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
				return false;
			}
			if (codeMailToArea2!="" && codeMailToArea2!="21" && codeMailToArea2!="22" && codeMailToArea2!="23" && codeMailToArea2!="25" ) {
				submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
				return false;
			}
			if (codeMailToArea3!="" && codeMailToArea3!="21" && codeMailToArea3!="22" && codeMailToArea3!="23" && codeMailToArea3!="25" ) {
				submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
				return false;
			}
			if (codeMailToArea4!="" && codeMailToArea4!="21" && codeMailToArea4!="22" && codeMailToArea4!="23" && codeMailToArea4!="25" ) {
				submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
				return false;
			}												
		}
		else if (codeMailToPostoffice == "55") {
			if (codeMailToArea0!="" && codeMailToArea0!="24" && codeMailToArea0!="23" && codeMailToArea0!="25"  ) {
				submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
				return false;
			}
			if (codeMailToArea1!="" && codeMailToArea1!="24" && codeMailToArea1!="23" && codeMailToArea1!="25"  ) {
				submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
				return false;
			}
			if (codeMailToArea2!="" && codeMailToArea2!="24" && codeMailToArea2!="23" && codeMailToArea2!="25"  ) {
				submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
				return false;
			}
			if (codeMailToArea3!="" && codeMailToArea3!="24" && codeMailToArea3!="23" && codeMailToArea3!="25"  ) {
				submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
				return false;
			}
			if (codeMailToArea4!="" && codeMailToArea4!="24" && codeMailToArea4!="23" && codeMailToArea4!="25"  ) {
				submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
				return false;
			}												
		}
		else if (codeMailToPostoffice == "56") {
			if (codeMailToArea0!="" && codeMailToArea0!="21" && codeMailToArea0!="22" && codeMailToArea0!="23"  ) {
				submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
				return false;
			}
			if (codeMailToArea1!="" && codeMailToArea1!="21" && codeMailToArea1!="22" && codeMailToArea1!="23"  ) {
				submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
				return false;
			}
			if (codeMailToArea2!="" && codeMailToArea2!="21" && codeMailToArea2!="22" && codeMailToArea2!="23"  ) {
				submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
				return false;
			}
			if (codeMailToArea3!="" && codeMailToArea3!="21" && codeMailToArea3!="22" && codeMailToArea3!="23"  ) {
				submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
				return false;
			}
			if (codeMailToArea4!="" && codeMailToArea4!="21" && codeMailToArea4!="22" && codeMailToArea4!="23"  ) {
				submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
				return false;
			}												
		}		

		//若勾選  "列印同一型態明細", 則 郵資單寄送地區只能是 "雜項", 0815
		if (PList=="1" && ( (codeMailToArea0!="" && codeMailToArea0!="23") ||
			(codeMailToArea1!="" && codeMailToArea1!="23") || (codeMailToArea2!="" && codeMailToArea2!="23") ||
			(codeMailToArea3!="" && codeMailToArea3!="23") || (codeMailToArea4!="" && codeMailToArea4!="23") )	)  {
				submitFinished();alert("若勾選  [列印同一型態明細], 則 郵資單寄送地區只能是 [雜項]");
				return false;			
		}
			
			
		if (nextEffectiveDate != "") {
			if ( nextCodeLgType=="" ||  codeMailToPostoffice=="" || codeMailCategory=="") {
				submitFinished();alert("郵資單總類 / 交寄郵局或公司 / 交寄方式  為必填欄位");
				return false;
			}
			
			if ( ($("input[id='nextCodeMailToArea0']").is(":checked"))==false && 
			     ($("input[id='nextCodeMailToArea1']").is(":checked"))==false && 
			     ($("input[id='nextCodeMailToArea2']").is(":checked"))==false && 
			     ($("input[id='nextCodeMailToArea3']").is(":checked"))==false && 
			     ($("input[id='nextCodeMailToArea4']").is(":checked"))==false 			     			     			     
			     ) {
				submitFinished();alert("郵資單寄送地區  為必填欄位");
				return false;
			}	
			
			//連動選單規則: 
			//交寄郵局 ==金南郵局(54), 北市(21)/外地(22)/雜項(23)/國外(25) ,多選
			//交寄郵局 ==汐止郵局(55), 分區(24)/雜項(23)/國外(25) ,多選
			//交寄郵局 ==新竹郵局(56), 北市(21)/外地(22)/雜項(23) ,多選

			if (nextCodeMailToPostoffice == "54") {
				if (nextCodeMailToArea0!="" && nextCodeMailToArea0!="21" && nextCodeMailToArea0!="22" && nextCodeMailToArea0!="23" && nextCodeMailToArea0!="25" ) {
					submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
					return false;
				}
				if (nextCodeMailToArea1!="" && nextCodeMailToArea1!="21" && nextCodeMailToArea1!="22" && nextCodeMailToArea1!="23" && nextCodeMailToArea1!="25" ) {
					submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
					return false;
				}
				if (nextCodeMailToArea2!="" && nextCodeMailToArea2!="21" && nextCodeMailToArea2!="22" && nextCodeMailToArea2!="23" && nextCodeMailToArea2!="25" ) {
					submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
					return false;
				}
				if (nextCodeMailToArea3!="" && nextCodeMailToArea3!="21" && nextCodeMailToArea3!="22" && nextCodeMailToArea3!="23" && nextCodeMailToArea3!="25" ) {
					submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
					return false;
				}
				if (nextCodeMailToArea4!="" && nextCodeMailToArea4!="21" && nextCodeMailToArea4!="22" && nextCodeMailToArea4!="23" && nextCodeMailToArea4!="25" ) {
					submitFinished();alert("金南郵局 只能送達至 北市/外地/雜項/國外  等區域");
					return false;
				}												
			}
			else if (nextCodeMailToPostoffice == "55") {
				if (nextCodeMailToArea0!="" && nextCodeMailToArea0!="24" && nextCodeMailToArea0!="23" && nextCodeMailToArea0!="25"  ) {
					submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
					return false;
				}
				if (nextCodeMailToArea1!="" && nextCodeMailToArea1!="24" && nextCodeMailToArea1!="23" && nextCodeMailToArea1!="25"  ) {
					submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
					return false;
				}
				if (nextCodeMailToArea2!="" && nextCodeMailToArea2!="24" && nextCodeMailToArea2!="23" && nextCodeMailToArea2!="25"  ) {
					submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
					return false;
				}
				if (nextCodeMailToArea3!="" && nextCodeMailToArea3!="24" && nextCodeMailToArea3!="23" && nextCodeMailToArea3!="25"  ) {
					submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
					return false;
				}
				if (nextCodeMailToArea4!="" && nextCodeMailToArea4!="24" && nextCodeMailToArea4!="23" && nextCodeMailToArea4!="25"  ) {
					submitFinished();alert("汐止郵局 只能送達至 分區/雜項/國外 等區域");
					return false;
				}												
			}
			else if (nextCodeMailToPostoffice == "56") {
				if (nextCodeMailToArea0!="" && nextCodeMailToArea0!="21" && nextCodeMailToArea0!="22" && nextCodeMailToArea0!="23"  ) {
					submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
					return false;
				}
				if (nextCodeMailToArea1!="" && nextCodeMailToArea1!="21" && nextCodeMailToArea1!="22" && nextCodeMailToArea1!="23"  ) {
					submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
					return false;
				}
				if (nextCodeMailToArea2!="" && nextCodeMailToArea2!="21" && nextCodeMailToArea2!="22" && nextCodeMailToArea2!="23"  ) {
					submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
					return false;
				}
				if (nextCodeMailToArea3!="" && nextCodeMailToArea3!="21" && nextCodeMailToArea3!="22" && nextCodeMailToArea3!="23"  ) {
					submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
					return false;
				}
				if (nextCodeMailToArea4!="" && nextCodeMailToArea4!="21" && nextCodeMailToArea4!="22" && nextCodeMailToArea4!="23"  ) {
					submitFinished();alert("新竹郵局 只能送達至 北市/外地/雜項 等區域");
					return false;
				}												
			}		
	
			//若勾選  "列印同一型態明細", 則 郵資單寄送地區只能是 "雜項", 0815
			if (nextPList=="1" && ( (nextCodeMailToArea0!="" && nextCodeMailToArea0!="23") ||
				(nextCodeMailToArea1!="" && nextCodeMailToArea1!="23") || (nextCodeMailToArea2!="" && nextCodeMailToArea2!="23") ||
				(cnextCodeMailToArea3!="" && nextCodeMailToArea3!="23") || (nextCodeMailToArea4!="" && nextCodeMailToArea4!="23") )	)  {
					submitFinished();alert("若勾選  [列印同一型態明細], 則 郵資單寄送地區只能是 [雜項]");
					return false;			
			}			
		}			
		return true;
	});
	
	
	//複製資料
	copyData = function (){
		$('#nextProgNm').val( $('#progNm').val() );
		$('#nextCodeLgType').val( $('#codeLgType').val() );			
		$('#nextCodeMailToPostoffice').val( $('#codeMailToPostoffice').val() );	
		$('#nextCodeMailCategory').val( $('#codeMailCategory').val() );			
		

		if ($('#PList').get(0).checked == true)
			$('input[name="nextPList"]')[0].checked = true;
		else
			$('input[name="nextPList"]')[1].checked = true;
		

		if ($("input[id='codeMailToArea0']").is(":checked"))
			$("#nextCodeMailToArea0").attr("checked",true);
		else
			$("#nextCodeMailToArea0").attr("checked",false);
			
		if ($("input[id='codeMailToArea1']").is(":checked"))
			$("#nextCodeMailToArea1").attr("checked",true);
		else
			$("#nextCodeMailToArea1").attr("checked",false);
			
		if ($("input[id='codeMailToArea2']").is(":checked"))
			$("#nextCodeMailToArea2").attr("checked",true);
		else
			$("#nextCodeMailToArea2").attr("checked",false);
			
		if ($("input[id='codeMailToArea3']").is(":checked"))
			$("#nextCodeMailToArea3").attr("checked",true);
		else
			$("#nextCodeMailToArea3").attr("checked",false);
			
		if ($("input[id='codeMailToArea4']").is(":checked"))
			$("#nextCodeMailToArea4").attr("checked",true);
		else
			$("#nextCodeMailToArea4").attr("checked",false);	
		
		if ($("#lgDisplayQty").is(":checked"))
			$("#nextLgDisplayQty").attr("checked",true);
		else
			$("#nextLgDisplayQty").attr("checked",false);
		
  	};
	$('#btnCopy').click(copyData);
	
		
	
	//檢查PK 是否存在 by AJAX.getJSON
	checkPKExist = function (){
	    $.getJSON('lginfo.do?fid=checkPKExist', 
	        {lgNo: $('#lgNo').val()},
	        function(ret) {
	        	if (ret.result=="NON_EXIST") {
	        		$('#msg_pk_exist').html("代號可以使用");
	        		$('#btnAdd').attr('disabled', false);
	        	}
	        	else {
	        		$('#msg_pk_exist').html("代號已經存在");
	        		$('#btnAdd').attr('disabled', true);
	           }
	        });
  	};
  	$('#lgNo').blur(checkPKExist);
});
</script>

	<form name="myform" id="myform" action="lginfo.do" method="post">
	    <input type="hidden" name="backToListURL" value="${backToListURL}" />
	    <input type="hidden" name="idList" value="${idList}" />	    
	    <input type="hidden" name="nextId" value="${nextId}" />
	    <input type="hidden" name="prevId" value="${prevId}" />
	    <input type="hidden" name="pagesIndex" value="${pagesIndex}" />	    
		<input type="hidden" name="fid" value="<%=fid %>" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">郵資單資料維護</td>
				</tr>
				<tr>
				  <td height="15"><font color="#FF0000"><%=Util.getStringFormat( message ) %></font></td>
				</tr>				
			</table>

			<TABLE border=0 cellSpacing=1 cellPadding=6 width="95%" class="editTableBKColor"  id="table8">
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>郵資單代號</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="lgNo" size="40"  name="lgNo" value="<%=lginfo.getLgNo() %>"  <%=disabledPK %> <%=disabledVIEW %> class="required" maxlength="6" minlength="6"><span id="msg_pk_exist"></span></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>
                 客戶代號</TD>
				 <TD class="editTableContentCellBKColor">
					 <SELECT name="custNo" size="1" <%=disabledVIEW %> class="required">
					 <option value="" >請選擇</option>
<% for (int i=0; i<customerList.size(); i++) {
	Customer _customer = (Customer)customerList.get(i);
	String selectString = "";
	if (null!= lginfo.getCustomer() && _customer.getCustNo().equalsIgnoreCase( lginfo.getCustomer().getCustNo()) ) 
		selectString="selected"; 
%>					 
							<option value="<%=_customer.getCustNo() %>" <%=selectString %> >
								<%=_customer.getCustNo() + "-" + _customer.getEasyName() %>
							</option>
<% }%>	
					</SELECT>
				</TD>
			</TR>
			
			<TR>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>郵資單抬頭</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="progNm" size="40"  name="progNm" value="<%=Util.getStringFormat(lginfo.getProgNm()) %>" <%=disabledVIEW %> class="required"></TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>郵資單總類</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
<SELECT name="codeLgType" size="1" <%=disabledVIEW %> class="required" id="codeLgType">
<option value="" >請選擇</option>
<% for (int i=0; i<lgTypeList.size(); i++) {
	Code _code = (Code)lgTypeList.get(i);
	String selectString = "";
	if (null!= lginfo.getCodeByCodeLgType() && _code.getId().compareTo(lginfo.getCodeByCodeLgType().getId())==0 ) 
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
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>交寄郵局或公司</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<SELECT name="codeMailToPostoffice" size="1" <%=disabledVIEW %> id="codeMailToPostoffice" class="required">
<option value="" >請選擇</option>
<% for (int i=0; i<lgMailToPostofficeList.size(); i++) {
	Code _code = (Code)lgMailToPostofficeList.get(i);
	String selectString = "";
	if (null!= lginfo.getCodeByCodeMailToPostoffice() && _code.getId().compareTo(lginfo.getCodeByCodeMailToPostoffice().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
				</TD>
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>交寄方式</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<SELECT name="codeMailCategory" size="1" id="codeMailCategory" <%=disabledVIEW %> class="required">
<option value="" >請選擇</option>
<% for (int i=0; i<lgMailCategoryList.size(); i++) {
	Code _code = (Code)lgMailCategoryList.get(i);
	String selectString = "";
	if (null!= lginfo.getCodeByCodeMailCategory() && _code.getId().compareTo(lginfo.getCodeByCodeMailCategory().getId())==0 ) 
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
                 <TD class="editTableLabelCellBKColor" width="10%" ><font color="#FF0000" size="2">*</font>郵資單寄送地區</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<table border="0" width="100%" id="table6">
<tr>
<% for (int i=0; i<lgMailToAreaList.size(); i++) {
	Code _code = (Code)lgMailToAreaList.get(i);
	String selectString = "";
	if (null!= lginfo.getCodeByCodeMailToArea1() && _code.getId().compareTo(lginfo.getCodeByCodeMailToArea1().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByCodeMailToArea2() && _code.getId().compareTo(lginfo.getCodeByCodeMailToArea2().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByCodeMailToArea3() && _code.getId().compareTo(lginfo.getCodeByCodeMailToArea3().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByCodeMailToArea4() && _code.getId().compareTo(lginfo.getCodeByCodeMailToArea4().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByCodeMailToArea5() && _code.getId().compareTo(lginfo.getCodeByCodeMailToArea5().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByCodeMailToArea6() && _code.getId().compareTo(lginfo.getCodeByCodeMailToArea6().getId())==0 ) 
		selectString="checked";	
%>					 
							<td><input type="checkbox" id="codeMailToArea<%=i %>" name="codeMailToArea" value="<%=_code.getId() %>" <%=selectString %> <%=disabledVIEW %> class="required" ><font size="2"><%=_code.getCodeValueTw() %></font></td>

<% }%>	
</tr>
</table>		
				</TD>
                 <TD class="editTableLabelCellBKColor" width="10%" rowspan="2"><font color="#FF0000" size="2">*</font>列印同一型態明細</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left rowspan="2">
					<INPUT type="radio" id="PList" name="PList"  value="1" <% if(null!=lginfo.getPList() && lginfo.getPList() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 是
					<INPUT type="radio" id="PList" name="PList"  value="0" <% if(null!=lginfo.getPList() && !lginfo.getPList() ) out.println("checked");%> <%=disabledVIEW %> class="required"/> 否
				</TD>	                 
				</TR>		
				<TR>
				   <TD class="editTableLabelCellBKColor" width="10%" >不顯示數量</TD>
                   <TD class="editTableContentCellBKColor"width="40%" align=left>
                      <input type="checkbox" id="lgDisplayQty" name="lgDisplayQty" value="0"  <%=disabledVIEW %> <%=lgDisplayQtySelect %> />不顯示數量
                   </TD>
				</TR>	

			
			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >修改後資料生效日期</TD>
                 <TD colSpan="3"  class="editTableContentCellBKColor"width="40%" align=left>
                 	<INPUT id="nextEffectiveDate" size="40"  name="nextEffectiveDate" value="<%=Util.getStringFormat(lginfo.getNextEffectiveDate()) %>" <%=disabledVIEW %>>
<img border="0" alt="日期選擇輔助工具" id="nextEffectiveDate_datepicker" src="images/calendar2.gif"/>
<script language="javascript">
		Calendar.setup({
			inputField : "nextEffectiveDate",						
		    button : "nextEffectiveDate_datepicker"
		});
</script>                 	
                 	<input name="btnCopy" type="button"  id="btnCopy" value="複製" /> </TD>
			</TR>					

			<TR>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >郵資單抬頭</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
                 	<INPUT id="nextProgNm" size="40"  name="nextProgNm" value="<%=Util.getStringFormat(lginfo.getNextProgNm()) %>" <%=disabledVIEW %> ></TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >郵資單總類</TD>
                 <TD class="editTableContentCellBKColor" width="40%" align=left>
<SELECT name="nextCodeLgType" size="1" <%=disabledVIEW %> id="nextCodeLgType">
<option value="" >請選擇</option>
<% for (int i=0; i<lgTypeList.size(); i++) {
	Code _code = (Code)lgTypeList.get(i);
	String selectString = "";
	if (null!= lginfo.getCodeByNextCodeLgType() && _code.getId().compareTo(lginfo.getCodeByNextCodeLgType().getId())==0 ) 
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
                 <TD class="editTableNextLabelCellBKColor" width="10%" >交寄郵局或公司</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<SELECT name="nextCodeMailToPostoffice" size="1" id="nextCodeMailToPostoffice" <%=disabledVIEW %> >
<option value="" >請選擇</option>
<% for (int i=0; i<lgMailToPostofficeList.size(); i++) {
	Code _code = (Code)lgMailToPostofficeList.get(i);
	String selectString = "";
	if (null!= lginfo.getCodeByNextCodeMailToPostoffice() && _code.getId().compareTo(lginfo.getCodeByNextCodeMailToPostoffice().getId())==0 ) 
		selectString="selected";
%>					 
							<option value="<%=_code.getId() %>" <%=selectString %> >
								<%=_code.getCodeValueTw() %>
							</option>
<% }%>	
</SELECT>
				</TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" >交寄方式</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<SELECT name="nextCodeMailCategory" size="1" id="nextCodeMailCategory" <%=disabledVIEW %> >
<option value="" >請選擇</option>
<% for (int i=0; i<lgMailCategoryList.size(); i++) {
	Code _code = (Code)lgMailCategoryList.get(i);
	String selectString = "";
	if (null!= lginfo.getCodeByNextCodeMailCategory() && _code.getId().compareTo(lginfo.getCodeByNextCodeMailCategory().getId())==0 ) 
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
                 <TD class="editTableNextLabelCellBKColor" width="10%" >郵資單寄送地區</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left>
<table border="0" width="100%" id="table6">
<tr>
<% for (int i=0; i<lgMailToAreaList.size(); i++) {
	Code _code = (Code)lgMailToAreaList.get(i);
	String selectString = "";
	if (null!= lginfo.getCodeByNextCodeMailToArea1() && _code.getId().compareTo(lginfo.getCodeByNextCodeMailToArea1().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByNextCodeMailToArea2() && _code.getId().compareTo(lginfo.getCodeByNextCodeMailToArea2().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByNextCodeMailToArea3() && _code.getId().compareTo(lginfo.getCodeByNextCodeMailToArea3().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByNextCodeMailToArea4() && _code.getId().compareTo(lginfo.getCodeByNextCodeMailToArea4().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByNextCodeMailToArea5() && _code.getId().compareTo(lginfo.getCodeByNextCodeMailToArea5().getId())==0 ) 
		selectString="checked";
	if (null!= lginfo.getCodeByNextCodeMailToArea6() && _code.getId().compareTo(lginfo.getCodeByNextCodeMailToArea6().getId())==0 ) 
		selectString="checked";	
%>					 
							<td><input type="checkbox" id="nextCodeMailToArea<%=i %>" name="nextCodeMailToArea" value="<%=_code.getId() %>" <%=selectString %> <%=disabledVIEW %>  ><font size="2"><%=_code.getCodeValueTw() %></font></td>

<% }%>	
</tr>
</table>		
				</TD>
                 <TD class="editTableNextLabelCellBKColor" width="10%" rowspan="2">列印同一型態明細</TD>
                 <TD class="editTableContentCellBKColor"width="40%" align=left rowspan="2">
					<INPUT type="radio" id="nextPList" name="nextPList"  value="1" <% if(null!=lginfo.getNextPList() && lginfo.getNextPList() ) out.println("checked");%> <%=disabledVIEW %> /> 是
					<INPUT type="radio" id="nextPList" name="nextPList"  value="0" <% if(null!=lginfo.getNextPList() && !lginfo.getNextPList() ) out.println("checked");%> <%=disabledVIEW %> /> 否
				</TD>	                 
				</TR>
				<TR>
				   <TD class="editTableNextLabelCellBKColor" width="10%" >不顯示數量</TD>
                   <TD class="editTableContentCellBKColor"width="40%" align=left>
                      &nbsp;<input type="checkbox" id="nextLgDisplayQty" name="nextLgDisplayQty" value="false"  <%=disabledVIEW %>  <%=nextLgDisplayQtySelect %> />不顯示數量
                   </TD>
				</TR>	
				
				

<!-- 下方命令列控制區塊 -->			
			<TR>
					<TD colSpan=4 align=left bgcolor="#FFFFFF">
						<p align="center">
<%	if (null!=actionMode &&  ( actionMode.equalsIgnoreCase("SAVE_AS_NEW") || actionMode.equalsIgnoreCase("ADD"))  ) { %>
						<input name="btnAdd" type="submit"  id="btnAdd" value="新增" />
						<input name="btnAdd" type="button"  id="btnAdd" onclick="javascript:history.go(-1)"  id="button6" value="取消" />
<%  } else if (null!=actionMode &&  actionMode.equalsIgnoreCase("EDIT")   ) { %>
						<c:out value="${pagesIndex}" /> 
						<c:if test="${prevId != null && prevId != ''}">						
						   <input name="button6" type="button" onclick="location='lginfo.do?fid=editInit&idList=${idList}&lgNo=${prevId}&backToListURL=${backToListURL}'"  id="button6" value="上一筆" />
						</c:if>
						<input name="button6" type="submit"  id="button6" value="更新" />
						<input name="button6" type="button" onclick="location='lginfo.do?fid=saveAsNewInit&lgNo=<%=lginfo.getLgNo()%>&backToListURL=${backToListURL}'"  id="button6" value="複製新增" />
						<input  type="button"  onclick="backToList()"   value="回列表" />
						<c:if test="${nextId != null && nextId != ''}">
						   <input name="button6" type="button" onclick="location='lginfo.do?fid=editInit&idList=${idList}&lgNo=${nextId}&backToListURL=${backToListURL}'"  id="button6" value="下一筆" />
						</c:if>

<%  } else { %>
						<input  type="button"  onclick="backToList()"   value="回列表" />
<%  } %>
					</TD>
				</TR>
<!-- end 下方命令列控制區塊 -->
			</TABLE>
			</div>
	</form>		
