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

<script type="text/javascript">
	//Thor add
	<c:out value="${setParamJqueryStr}"  escapeXml="false"/>
	//Thor
	function showHideRows(classForChoose, obj){
		var elem = $("." + classForChoose)[0];
		if(elem.style.display == 'none'){
			$("." + classForChoose).show();
		}else{
			$("." + classForChoose).hide();
		}
		
	}
</script>


<div align="center">
	<form action="report.do" method="get">
		<table width="95%" border="0" cellspacing="0" cellpadding="0">

			<tr>
				<td height="26" class="title">預估當日生產查詢</td>
			</tr>
			<tr>
				<td height="15">&nbsp;</td>
			</tr>
			<tr>
				<td height="30"><input type="hidden" name="fid"
					value="estimateProductionList"> <input type="hidden"
					name="includeParams_value"
					value="queryDate,custNo,rows">
					<table border=0 cellSpacing=1 cellPadding=6 width="98%"
						class="queryTableBKColor" align="left">
						<tr>
						    <td class="queryTableCellBKColor" width="6%" align="left">客戶</td>
							<td class="queryTableCellBKColor" width="27%" align="left">
							    <select name="custNo" id="custNo">
							        <option value="all">全部</option>
							        <c:forEach var="customer" items="${customers}">
                                         <option value="${customer.custNo}">${customer.easyName }</option>                 
                                    </c:forEach>
							    </select>
							</td>
							<td class="queryTableCellBKColor" width="6%" align="left">查詢日期</td>
							<td class="queryTableCellBKColor" width="27%" align="left">
								<input type="text" id="queryDate" maxLength="15" size="10"
								name="queryDate" readonly="readonly" /> <img border="0"
								alt="日期選擇輔助工具" id="queryDatePicker"
								src="images/calendar2.gif" /> <script language="javascript">
									Calendar.setup({
										inputField : "queryDate",
										button : "queryDatePicker"
									});
								</script>
						    </td>
						    <td class="queryTableCellBKColor" width="6%" align="left">每頁筆數</td>
							<td class="queryTableCellBKColor" width="28%" align="left">
							    <select name="rows" id="rows">
							        <option value="100">100</option>
							        <option value="200">200</option>
							        <option value="300">300</option>
							    </select>
							</td>						    
						</tr>
					</table>
			   </td>
			</tr>
			<tr>
				<td height="30">
					<table border=0 cellSpacing=1 cellPadding=6 width="100%"
						align="center" id="table7">
						<tr>
							<td align="center"><input name="button" type="submit"
								id="button" value="搜尋" />
							</td>

						</tr>
					</table></td>
			</tr>

			<tr>
				<td>
					<table border="0" width="100%" id="table9"
						class="resultTableBKColor">
						<tr>
						   <th width="1%" nowrap="true"  height="30" class="resultTableHeaderBKColor"  style="text-align:center;"></th>
	                       <th nowrap="true"  height="30" class="resultTableHeaderBKColor"  style="text-align:center;">客戶</th>
	                       <th nowrap="true"  height="30" class="resultTableHeaderBKColor"  style="text-align:center;">Jobe Code</th>
	                       <th nowrap="true"  height="30" class="resultTableHeaderBKColor"  style="text-align:center;">活動名稱</th>
	                       <th nowrap="true"  height="30" class="resultTableHeaderBKColor"  style="text-align:center;">傳檔型態</th>
	                       <th nowrap="true"  height="30" class="resultTableHeaderBKColor"  style="text-align:center;">預估Cycle Date</th>	                       
                        </tr>                        
                        <c:forEach var="bean" items="${jobCodes}" varStatus="stauts" >
                            <!-- 有沒有jobBag -->                
                            <c:if test="${bean[4] == null}">
                                <c:choose>
                                   <c:when test="${prevRowStyle eq 'evenLine'}">
                                      <c:set var="rowStyle" scope="page" value="oddLine"/>
                                      <c:set var="prevRowStyle" scope="page" value="oddLine"/>
                                   </c:when>
                                   <c:otherwise>
                                      <c:set var="rowStyle" scope="page" value="evenLine"/>
                                      <c:set var="prevRowStyle" scope="page" value="evenLine"/>
                                   </c:otherwise>
                                </c:choose>                                
                            </c:if>
                            <c:if test="${bean[4] != null}">                                
                                <c:set var="rowStyle" scope="page" value="${bean[1]}"/>
                            </c:if>
	                        <tr  class="${rowStyle}" id="rowsCount${stauts.count}" >
	                            <c:choose>	                               
                                   <c:when test="${bean[4] == null}">
                                      <!-- 新的一行 -->
                                      <c:set var="initNewLine" value="newLine" />
                                      <td><div id="${bean[1]}" style="display:none"><a href="javascript: void(0)" onclick="showHideRows('${bean[1]}', this)">+</a></div></td>
	                                  <td height="30" align="center"><c:out value="${bean[0]}" /></td>
	                                  <td align="left" >
	                                      <a href="jobcode.do?fid=view&backToListURL=<c:out value='${backToListURL}' default='' />&jobCodeNo=<c:out value="${bean[1]}" />"><c:out value="${bean[1]}" /></a>
	                                  </td>
	                                  <td align="left" ><c:out value="${bean[2]}" /></td>	            
	                                  <td align="left" ><c:out value="${bean[3]}" /></td>
	                                  <td align="left">${category[bean[3]]}</td>
	                               </c:when>
	                            </c:choose>	                            
	                            <c:choose>
                                   <c:when test="${bean[4] != null}">
                                       <c:if test="${initNewLine eq 'newLine' }">
                                          <script type="text/javascript">
                                               $().ready(function() {
                                            	   $(".${bean[1]}").hide();
                                            	   $("#${bean[1]}").show();                                            	
                                               });
                                          </script>
                                          
                                          <th colspan="2" class="detailTableHeaderBKColor"  style="text-align:center;">JobBag No</th>                                          
	                                      <th class="detailTableHeaderBKColor"  style="text-align:center;">AFP檔名</th>	                                      
	                                      <th class="detailTableHeaderBKColor"  style="text-align:center;">工單狀態</th>
	                                      <th class="detailTableHeaderBKColor"  style="text-align:center;">工單產生時間</th>
	                                      <th class="detailTableHeaderBKColor"  style="text-align:center;">Cycle Date</th>
	                                      </tr>
	                                      <tr class="${rowStyle}" id="rowsCount${stauts.count}first" >
                                          <c:set var="initNewLine" value="detailTable" />
                                       </c:if>                                       
	                                   <td align="center" class="editTableContentCellBKColor" colspan="2">
	                                      <a href="jobbag.do?fid=view&jobBagNo=<c:out value="${bean[4]}" />&backToListURL=${backToListURL}"><c:out value="${bean[4]}" /></a>
	                                   </td>	                                   
	                                   <td align="center" class="editTableContentCellBKColor"><c:out value="${bean[7]}" /></td>	                                   
	                                   <td align="center" class="editTableContentCellBKColor"><c:out value="${category[bean[8]]}" /></td>
	                                   <td align="center" class="editTableContentCellBKColor"><fmt:formatDate value="${bean[6]}" pattern="yyyy-MM-dd HH:mm" /></td>
	                                   <td align="center" class="editTableContentCellBKColor"><fmt:formatDate value="${bean[5]}" pattern="yyyy-MM-dd" /></td>
	                                </c:when>
	                            </c:choose>
                            </tr>
                        </c:forEach>
					</table>
				</td>
			</tr>
			<tr>
			   <td align="center">
			       <c:if test="${prevPage != null}">
			              <input name="button" type="submit"  value="上一頁"  onclick='$("#page").val($("#prevPage").val())' />
						  <input type="hidden" name="prevPage" id="prevPage" value="${prevPage }"/>  
				   </c:if> 
				   &nbsp;&nbsp;
			                      共<font color="red">${totalRows}</font>筆，[${nowPage + 1 }/${totalPages }]頁
			       &nbsp;&nbsp;
			       <c:if test="${nextPage != null}">
			              <input name="button" type="submit"  value="下一頁"  onclick='$("#page").val($("#nextPage").val())' />
						  <input type="hidden" name="nextPage" id="nextPage" value="${nextPage}" />  
				   </c:if>
				   <input type="hidden" name="page" id="page" value="" />
			   </td>
			</tr>
			
		</table>
	</form>
</div>