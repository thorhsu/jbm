<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
<%
JSONArray jsonObject= (JSONArray)request.getAttribute("jsonObject");
out.println( jsonObject.toString() );  
%>