<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%
	String pdfFileName_PostOffice =  (String) request.getAttribute("pdfFileName_PostOffice");
	String pdfFileName_PostOffice_customer =  (String) request.getAttribute("pdfFileName_PostOffice_customer");	
	String pdfFileName_Fubon =  (String) request.getAttribute("pdfFileName_Fubon");
	String pdfFileName_Fubon_subTotal =  (String) request.getAttribute("pdfFileName_Fubon_subTotal");		
	String pdfFileName =  (String) request.getAttribute("pdfFileName");		
	String pdfFileName2 =  (String) request.getAttribute("pdfFileName2");	
	java.util.List<String> fileNames = (java.util.List<String>) request.getAttribute("fileNames");	
	
%>

      <table border="0" cellpadding="0" cellspacing="0" id="SysMiddleSet2" width="100%">

        <tr>
          <td height="28" background="images/green_title_bg.jpg" class="black12b" style="background-repeat:no-repeat; padding-left:10px">系統訊息</td>
        </tr>
        <tr>
          <td height="28" style="padding-top:10px">
          <div align="left">
            <c:if test="${not empty requestScope.message}">
        	<p class="text"><font color="red"><%=(String)request.getAttribute("message")%></font></p>
			</c:if>
		</div>
		</td>
        </tr>

<% if (null !=pdfFileName &&  pdfFileName.length() >0)  { %>	        
        <tr>
          <td height="28" style="padding-top:10px">
          	PDF File Name: (<%=(String)request.getAttribute("pdfFileName")%>): <a href="pdf/<%=(String)request.getAttribute("pdfFileName")%>" target="new">開啟</a>
		  </td>
        </tr>   
<%} %>             
<% if (null !=fileNames &&  fileNames.size() > 0)  { %>
        <%for(String fileName : fileNames){ %>	        
           <tr>
             <td height="28" style="padding-top:10px">
          	     file Name: (<%= fileName%>): <a href="pdf/<%=fileName%>" target="new">開啟</a>
		     </td>
           </tr>   
        <% } %>
<%} %>
<% if (null !=pdfFileName2 &&  pdfFileName2.length() >0)  { %>	        
        <tr>
          <td height="28" style="padding-top:10px">
          	PDF File Name: (<%=(String)request.getAttribute("pdfFileName2")%>): <a href="pdf/<%=(String)request.getAttribute("pdfFileName2")%>" target="new">開啟</a>
		  </td>
        </tr>   
<%} %>        
<% if (null !=pdfFileName_PostOffice &&  pdfFileName_PostOffice.length() >0)  { %>	
        <tr>
          <td height="28" style="padding-top:10px">
          	PDF File Name: (<%=(String)request.getAttribute("pdfFileName_PostOffice")%>): <a href="pdf/<%=(String)request.getAttribute("pdfFileName_PostOffice")%>" target="new">開啟</a>
		  </td>
        </tr>
<%} %>
        
<% if (null !=pdfFileName_PostOffice_customer &&  pdfFileName_PostOffice_customer.length() >0)  { %>	
        <tr>
          <td height="28" style="padding-top:10px">
          	PDF File Name: (<%=(String)request.getAttribute("pdfFileName_PostOffice_customer")%>): <a href="pdf/<%=(String)request.getAttribute("pdfFileName_PostOffice_customer")%>" target="new">開啟</a>
		  </td>
        </tr>
<%} %>

<% if (null !=pdfFileName_Fubon &&  pdfFileName_Fubon.length() >0)  { %>	
        <tr>
          <td height="28" style="padding-top:10px">
          	PDF File Name: (<%=(String)request.getAttribute("pdfFileName_Fubon")%>): <a href="pdf/<%=(String)request.getAttribute("pdfFileName_Fubon")%>" target="new">開啟</a>
		  </td>
        </tr>
<%} %>

<% if (null !=pdfFileName_Fubon_subTotal &&  pdfFileName_Fubon_subTotal.length() >0)  { %>	
        <tr>
          <td height="28" style="padding-top:10px">
          	PDF File Name: (<%=(String)request.getAttribute("pdfFileName_Fubon_subTotal")%>): <a href="pdf/<%=(String)request.getAttribute("pdfFileName_Fubon_subTotal")%>" target="new">開啟</a>
		  </td>
        </tr>
<%} %>        
      </table>
