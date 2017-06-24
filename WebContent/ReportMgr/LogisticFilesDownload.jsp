<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="vlh"
	uri="http://valuelist.sourceforge.net/tags-valuelist"%>
<%@ taglib uri='/WEB-INF/fmt.tld' prefix='fmt'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%
	// List accountList = (List)request.getAttribute("dataList");
%>



<div align="center">
	<form action="report.do" method="get">
		<table width="95%" border="0" cellspacing="0" cellpadding="0">

			<tr>
				<td height="26" class="title">下載logistic檔案</td>
			</tr>
			<tr>
				<td height="15">&nbsp;</td>
			</tr>
						
			<tr>
				<td>
					<table border="0" width="100%" id="table9"
						class="resultTableBKColor">
						<tr>
	                       <th nowrap="true"  height="30" class="resultTableHeaderBKColor"  style="text-align:center;">一般logistic.csv檔案</th>
	                       <th nowrap="true"  height="30" class="resultTableHeaderBKColor"  style="text-align:center;">國壽logistic.csv檔案</th>	                       	                       
                        </tr>
                        <tr>
                           <td class="editTableContentCellBKColor"  style="text-align:center;">
                                <c:forEach var="file" items="${logisticFiles}"  >                                
                                    <a href="download.do?fileName=logistic/${file }&charset=utf-8" target="new">${file}</a>
                                    <br />
                                </c:forEach>
                           </td>
	                       <td class="editTableContentCellBKColor"  style="text-align:center;">
                                <c:forEach var="file" items="${caFiles}"  >
                                    <a href="download.do?fileName=logistic/${file }&charset=utf-8" target="new">${file}</a>
                                    <br />
                                </c:forEach>
                           </td>
                        </tr>                        
					</table>
				</td>
			</tr>
			
		</table>
	</form>
</div>