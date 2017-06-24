<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@ page import="com.painter.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%! SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); %>
<%
	MenuItemPermissionService pemissionService = MenuItemPermissionService.getInstance();
%>
	
<table border="0" width="150" id="table2" bgcolor="#F4FAF3">
	<tr>
		<td width="7%" bgcolor="#006F3F"><font size="2">+</font></td>
		<td colspan="2" bgcolor="#006F3F"><font size="2" color="#FFFFFF">工單處理進度</font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><%=pemissionService.findMenuItemValue(request, "JOB_BAG_STATUS","工單處理進度總表")%></td>
	</tr> 
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "EXCEPTION_JOB_BAG","異常自動開單紀錄") %></font></td>
	</tr>	
	
	<tr>
		<td width="7%" bgcolor="#006F3F"><font size="2">+</font></td>
		<td colspan="2" bgcolor="#006F3F"><font size="2" color="#FFFFFF"><%=pemissionService.findMenuValue("SYSTEM_MGR","系統管理") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "EMPLOYEE_MGR","帳號管理") %>
		</font></td>
	</tr>
	<tr> 
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "CODE_MGR","代碼檔維護") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2">   <%=pemissionService.findMenuItemValue(request, "AMEX_Mapping_Rule_MGR","Amex Mapping Rule 維護") %> 
		</font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "SYS_LOG","系統紀錄") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "Delete JobBag","刪除JobBag") %></font></td>
	</tr>
	<tr>
		<td width="7%" bgcolor="#006F3F"><font size="2">+</font></td>
		<td colspan="2" bgcolor="#006F3F"><font size="2" color="#FFFFFF">基本資料管理</font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "PROFILE_MGR","基本資料維護") %></font></td>
	</tr>
	<tr>
		<td width="7%" bgcolor="#006F3F"><font size="2">+</font></td>
		<td width="82%" colspan="2" bgcolor="#006F3F">
		<font size="2" color="#FFFFFF"><%=pemissionService.findMenuValue("ENTITY_MGR","資料維護") %> </font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<% 
		   HashMap paramMap = new HashMap<String, String>();
		   paramMap.put("isEnabledContract", "1");
		   paramMap.put("includeParams_value", "isEnabledContract");
		%>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "JOB_CODE_MGR","工單樣本維護", paramMap) %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<% 
		   paramMap = new HashMap<String, String>();
           java.util.Calendar cal = java.util.Calendar.getInstance();
           cal.add(java.util.Calendar.YEAR, -1);           
		   paramMap.put("create_date_begin", sdf.format(cal.getTime()));		   
		%>				
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "JOB_BAG_MGR","工單查詢", paramMap) %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "CUSTOMER_MGR","客戶資料查詢維護") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "LPINFO_MGR","信紙資料查詢維護") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "MPINFO_MGR","裝封資料查詢維護") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "PSINFO_MGR","壓封資料查詢維護") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "LGINFO_MGR","郵資單查詢維護") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "LCINFO_MGR","交寄管制表資料查詢維護") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "RETURN_INFO_MGR","退回客戶資訊維護") %></font></td>
	</tr>
	<tr> 
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "CODE_MGR_CS","代碼檔維護") %></font></td>
	</tr>	
	
	
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "ACCT_CUSTOMER_CONTRACT_MGR","維護客戶合約價格") %></font></td>
	</tr>
		
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "ACCT_JOB_CODE_CHARGE_ITEM_MGR","工作計費方式維護") %> </font></td>
	</tr>

	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "CUSTOMER_RECEIVER_MGR","客戶帳務聯絡資訊維護") %> </font></td>
	</tr>
	
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "LGPRICE_MGR","郵資價格查詢") %> </font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "HOLIDAY_EDIT","假日資訊維護") %> </font></td>
	</tr>
	
	
	<tr>
		<td width="7%" bgcolor="#006F3F"><font size="2">+</font></td>
		<td width="82%" colspan="2" bgcolor="#006F3F">
		<font size="2" color="#FFFFFF">線上作業</font></td>
	</tr>
	<tr>
		<td width="7%">　</td> 
		<td width="5%"><font size="2">-</font></td> 
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "JOB_BAG_QUERY_OP","工單查詢及分檔") %> </font></td>
	</tr>
	<tr>  
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "COMPLETED_JOB_OP","記錄完成工作") %>   </font></td>
	</tr>
	<tr>
		<td width="7%" bgcolor="#006F3F"><font size="2">+</font></td>
		<td width="82%" colspan="2" bgcolor="#006F3F">
		<font size="2" color="#FFFFFF">Logistic</font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "JOB_BAG_QUERY_LG","郵資單與交寄管制表") %> </font></td>
	</tr>
	
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "JOB_BAG_QUERY_RETURN_LG","退回客戶清單") %> </font></td>
	</tr>
		
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "COMPLETED_JOB_LP","記錄完成工作") %>   </font></td>
	</tr>
	
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "ZIPCODE_MGR_LG","郵局3+2郵遞區號") %>   </font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "LOGISTIC_FILES_DOWNLOAD","下載LOGISTIC.CSV") %></font></td>
	</tr>

	<tr>
		<td width="7%" bgcolor="#006F3F"><font size="2">+</font></td>
		<td width="82%" colspan="2" bgcolor="#006F3F">
		<font size="2" color="#FFFFFF">會計管理</font></td>
	</tr>

	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "ACCT_CHARGE_ITEM_MGR","維護收費項目") %></font></td>
	</tr>
	


	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "ACCT_SECTION_MAPPING_MGR","維護Section跟工單對應規則") %></font></td>
	</tr>

	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "ACCT_DEBIT_NOTE_MGR","Debit Note 查詢") %></font></td>
	</tr>

	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "ACCT_FUBON_REPORT_LIST","富邦報表") %></font></td>
	</tr>
	
	
	<tr>
		<td width="7%" bgcolor="#006F3F"><font size="2">+</font></td>
		<td width="82%" colspan="2" bgcolor="#006F3F">
		<font size="2" color="#FFFFFF">Report</font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "DAILY_JOB_STATISTICS_REPORT","每日生產統計報表") %>
		</font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "DAILY_JOB_CONTROL_REPORT","每日生產管制表") %>
		</font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "MATERIAL_USED_REPORT","各種用料數量一覽表") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "USER_AUTHORITY_REPORT","使用者代號及權限一覽表") %></font></td>
	</tr>	
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "LG_FORM","空白郵資單") %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "CUSTOMER_ITEM_REPORT","客戶工作明細代號表") %></font></td>
	</tr>
	
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "DAILY_LOGEMENT_REPORT","Daily Logement Report") %></font></td>
	</tr>
	
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "DM_ENCLOUSED_SUMMARY_FILE","DM Encloused Summary File") %></font></td>
	</tr>
	
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "AMEX_JOB_REPORT","AMEX_JOB_REPORT") %></font></td>
	</tr>
				
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<% 
		   paramMap = new HashMap<String, String>();
		   paramMap.put("empNo", SessionUtil.getAccount(request.getSession()).getEmpNo());
		   paramMap.put("includeParams_value", "empNo");
		%>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "HISTORY_REPORT","歷史報表", paramMap) %></font></td>
	</tr>		
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<% 
		   paramMap = new HashMap<String, String>();
		   paramMap.put("queryDate", sdf.format(new java.util.Date()));
		   paramMap.put("includeParams_value", "queryDate");
		%>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "ESTIMATE_PRODUCTION","預估當日生產", paramMap) %></font></td>
	</tr>
	<tr>
		<td width="7%">　</td>
		<td width="5%"><font size="2">-</font></td>
		<td width="77%"><font size="2"><%=pemissionService.findMenuItemValue(request, "CITI_MAIL_REPORT","客戶寄件報表") %></font></td>
	</tr>
    
</table>
