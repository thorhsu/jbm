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
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<script src="js/jbm.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqModal.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.js" type="text/javascript"></script>
<script src="js/lib/jquery.jqDnR.dimensions.js" type="text/javascript"></script>
<script type="text/javascript">
	$().ready(function() {
		var year = "${holidayForm.year}";
		var month = "${holidayForm.month}";
		$("#selectedYear").val(year);
		$("#selectedMonth").val(month);

	});
	function submitMyForm() {
		if (window.confirm("確認送出")) {
			$('#fid').val("submit");
			$("#selectedYear").val("${holidayForm.year}");
			$("#selectedMonth").val("${holidayForm.month}");
			document.myform.submit();
		}

	}
</script>

<form name="myform" action="holidayAction.do" method="post">
	<table border="0" cellSpacing="1" cellPadding="6" width="95%">
		<tr>
			<td height="26" class="title">假日資訊維護</td>
		</tr>
		<tr>
			<td height="30">
				<table border="0" cellSpacing="1" cellPadding="6" width="95%"
					class="queryTableBKColor" align="left">
					<tr>
						<td class="editTableContentCellBKColor">
						    <select name="year"
							   id="selectedYear">
								<c:forEach var="year" items="${holidayForm.years}">
									<option value="${year}">${year }</option>
								</c:forEach>
						    </select> 年 &nbsp;&nbsp;&nbsp; 
						    <select name="month" id="selectedMonth">
								<option value="0">1</option>
								<option value="1">2</option>
								<option value="2">3</option>
								<option value="3">4</option>
								<option value="4">5</option>
								<option value="5">6</option>
								<option value="6">7</option>
								<option value="7">8</option>
								<option value="8">9</option>
								<option value="9">10</option>
								<option value="10">11</option>
								<option value="11">12</option>
						    </select> 月 &nbsp;&nbsp;&nbsp; 
						    <input type="submit" value="查詢" />
					    </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<c:if test="${!holidayForm.tableShow }">
		<c:set var="tableshow" value="style='display:none'" />
	</c:if>

	<br />
	<table border="0" cellSpacing="1" cellPadding="6" width="700"
		class="queryTableBKColor" align="left" ${tableshow }>
		<tr align="center">
			<td height="20" class="title editTableLabelCellBKColor" colspan="7">
				${holidayForm.year}年 ${holidayForm.month + 1}月</td>
		</tr>

		<tr>
			<th class="checkedLine" width="100" align="center">日</th>
			<th width="100" align="center">一</th>
			<th width="100" align="center">二</th>
			<th width="100" align="center">三</th>
			<th width="100" align="center">四</th>
			<th width="100" align="center">五</th>
			<th class="checkedLine" width="100" align="center">六</th>
		<tr>
			<c:forEach var="line" items="${holidayForm.holidayTable}">
				<tr>
					<c:forEach var="holiday" items="${line }">
						<c:set var="tdClass" value="evenLine" />
						<c:set var="checked" value="" />
						<c:if test="${holiday.isHoliday}">
							<c:set var="tdClass" value="checkedLine" />
							<c:set var="checked" value="checked='checked'" />
						</c:if>
						<c:if test="${holiday.isToday}">
							<c:set var="tdClass" value="mouseOver" />							
						</c:if>
						<td align="center" class="${tdClass }" height="50" width="100"
							id="td${holiday.dateStr}"><c:if test="${holiday != null}">
                          ${holiday.daysfMonth} 
                          <br />
								<input type="checkbox" id="${holiday.dateStr}"
									name="holidayCheck" value="${holiday.dateStr }"
									${checked } onclick="checkedLine('${holiday.dateStr}', 'td${holiday.dateStr}', '${tdClass}')" />
								<c:choose>
									<c:when test="${holiday.isHoliday }">
                                      ${holiday.holidayName }
                                    </c:when>
									<c:otherwise>假日</c:otherwise>
								</c:choose>
							</c:if>
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		<tr class="oddLine">
			<td align="center" colspan="7"><input type="button" value="送出"
				onclick="submitMyForm()" />
			</td>
		</tr>
	</table>
	<input type="hidden" id="fid" name="fid" value="query" />
</form>
