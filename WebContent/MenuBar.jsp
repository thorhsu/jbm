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
	
<li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">工單處理進度 <b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li><%=pemissionService.findMenuItemValue(request, "JOB_BAG_STATUS","工單處理進度總表")%></li>
           </ul>
</li>

