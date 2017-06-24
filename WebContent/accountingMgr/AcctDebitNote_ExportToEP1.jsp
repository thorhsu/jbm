<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<script type="text/javascript">
   $().ready(function() {
      window.opener.submitFinished();
   });   
</script>
<%  
	AcctDebitNoteService acctDebitNoteService = AcctDebitNoteService.getInstance();
	AcctDno acctDno = (AcctDno)request.getAttribute("acctDno");
	String[] targetfileName = acctDebitNoteService.exportEp1(acctDno);
%>

      <table border="0" cellpadding="0" cellspacing="0" id="SysMiddleSet2" width="100%">

        <tr>
          <td height="28" background="images/green_title_bg.jpg" class="black12b" style="background-repeat:no-repeat; padding-left:10px">系統訊息</td>
        </tr>

        
        <tr>
          <td height="28" style="padding-top:10px">
          	總表:(<%=targetfileName[0]%>): <a href="download.do?fileName=<%=targetfileName[0]%>" target="new">開啟</a>
		  </td>
        </tr>        
        <tr>
          <td height="28" style="padding-top:10px">
          	發票資訊:(<%=targetfileName[1]%>): <a href="download.do?fileName=<%=targetfileName[1]%>&application=msexcel" target="new">>開啟</a>
		  </td>
        </tr>  
        <tr>
          <td height="28" style="padding-top:10px">
          	作業內容資料:(<%=targetfileName[2]%>): <a href="download.do?fileName=<%=targetfileName[2]%>&application=msexcel" target="new">開啟</a>
		  </td>
        </tr>          

        
      </table>
