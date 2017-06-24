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
<script type="text/javascript">
    function markDelete(){
    	var fid = document.getElementById('fid');    	
    	if(confirm('標記刪除' + $('#jobBagNo').val() + '此工單？')){
    		fid.value = 'markDelete';
    		return true;
    	}else{
    		return false;
    	}
    }

</script>

	<form name="myform" id="myform" action="jobbag.do" method="post">
	    <input type="hidden" id="fid" name="fid" value="markDelete" />
		<div align="center">
			<table width="95%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td height="26" class="title">刪除Job Bag</td>
				</tr>
				<tr>
				</tr>				
			</table>

			<table border="0" cellspacing="1" cellpadding="6" width="95%" class="editTableBKColor"  id="table8">
			     <TR>
			        <TD class="editTableLabelCellBKColor"  width="70"><font color="#FF0000">*</font>請輸入工單代號</TD>
				    <TD class="editTableContentCellBKColor" align=left width="82"><input type="text" id="jobBagNo" name="jobBagNo" /></TD>
				</TR>
				<tr>
				    <td colspan="2">
				       <!--  
                       <c:if test="${employee.role5s}">				    
				          <input name="button6" type="submit"   value="完全刪除" onclick="return confirm('確定刪除' + $('#jobBagNo').val() + '此工單？')"/>
				          &nbsp;&nbsp;
				       </c:if>
				        -->
				       <input type="submit"   value="刪除" onclick="return markDelete()"/>   
				    </td>
				</tr>
			</table>
		</div>
	</form>		
