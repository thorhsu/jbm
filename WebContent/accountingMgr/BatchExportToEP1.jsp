<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@page import="com.salmat.jbm.bean.*"%>
<%@page import="com.salmat.jbm.service.*"%>
<%@page import="com.painter.util.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="jxl.*" %>
<%@page import="jxl.write.*" %>
<%@page import="java.io.*" %>
<%@page import="net.mlw.vlh.ValueList"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<script type="text/javascript">
   $().ready(function() {
      window.opener.submitFinished();
   });   
</script>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>export to ep1</title>
  </head>
  <body>
     <table border="1">
      <c:forEach var="dbnoAndReason" items="${notExport}">
        <tr>
            <th>
                                            無法輸出的Debit Note No：
            </th>
            <th>
                                           原因：
            </th>
        </tr>
        <tr>
            <td>
                ${dbnoAndReason[0]}
            </td>
            <td>
                ${dbnoAndReason[1]}
                <br />
                ${dbnoAndReason[2]}
                <br />
                ${dbnoAndReason[3]}
                <br />
                ${dbnoAndReason[4]}
                <br />
                ${dbnoAndReason[5]}
            </td>
        </tr>
      </c:forEach>
    </table>
    
    <br />
  
    <table border="1">
      <c:forEach var="dbAndFileNms" items="${fileList}">
        <tr>
            <th>
                                           輸出的Debit Note No：
            </th>
            <th>
                                           檔案下載：
            </th>
        </tr>
        <tr>
            <td>
               ${dbAndFileNms[0]}
            </td>
            <td>
                                        總表:(${dbAndFileNms[1]}): <a href="download.do?fileName=${dbAndFileNms[1]}" target="new">開啟</a>
               <br />
                                       發票資訊:(${dbAndFileNms[2]}): <a href="download.do?fileName=${dbAndFileNms[2]}&application=msexcel" target="new">開啟</a>
               <br />
                                       作業內容資料:(${dbAndFileNms[3]}): <a href="download.do?fileName=${dbAndFileNms[3]}&application=msexcel" target="new">開啟</a>
            </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
