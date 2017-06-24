<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.painter.util.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">   
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/bootstrap/assets/ico/favicon.png">    
    <title>需求單系統</title>    

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <!-- Documentation extras -->
     <link href="${pageContext.request.contextPath}/bootstrap/assets/css/docs.css" rel="stylesheet">
     <link href="${pageContext.request.contextPath}/bootstrap/assets/css/jquery-ui-1.10.3.custom.css" rel="stylesheet">
     <link href="${pageContext.request.contextPath}/css/generalLayout_cs.css" rel="stylesheet">
     <!-- 讓ie可以使用css3 -->
     <script src="${pageContext.request.contextPath}/js/selectivizr-min.js" type="text/javascript"></script>     

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9] -->
      <script src="${pageContext.request.contextPath}/bootstrap/assets/js/html5shiv.js"></script>
      <script src="${pageContext.request.contextPath}/bootstrap/assets/js/respond.min.js"></script>
    <!-- [endif]-->
    
    <script src="${pageContext.request.contextPath}/bootstrap/assets/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/assets/js/jquery-ui-1.10.3.custom.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.validate.js" type="text/javascript"></script>
    <script  type="text/javascript">
    //回列表 
    function backToList(){
       var backToListURL = decodeURIComponent("${backToListURL}");
       if(backToListURL.indexOf("null") >= 0 || backToListURL == "")
          javascript:history.go(-1)
       else
          location = backToListURL;
    }

    //超連結到編輯頁面
    function toEditLocation(actionLocation, idVal, pkName, edit){
 	   var viewOrEdit;
 	   if(edit != null  && edit == true){
 		   viewOrEdit = "editInit";
 	   }else{
 		   viewOrEdit = "view";
 	   }
 	   if(idVal != null && idVal != "" && idVal != "請選擇"){
 	      window.location = actionLocation + ".do?fid=" + viewOrEdit + "&" + pkName + "=" + idVal + "&idList=" + idVal + "&backToListURL=${backToListURL}";	      
 	   }
    }
    $().ready(function() {
    	var message = '${alert_message}';
    	if(message === ''){
    		$("#alert_div_block").hide();
    	}else{
    		$("#alert_div_block").show();
    		$("#alert_message").text(message);    		
    	}
    });
    
    </script>
  </head>

  <body>
<%
  com.salmat.jbm.service.MenuItemPermissionService pemissionService = com.salmat.jbm.service.MenuItemPermissionService.getInstance();
//AccountService accountService = AccountService.getInstance();
	Employee sessionAccount = SessionUtil.getAccount(session);
%>
    <!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>          
          <a class="navbar-brand" href="#">客戶需求單系統 </a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">            
               <li class="dropdown">
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown">客戶資料管理 <b class="caret"></b></a>
                 <ul class="dropdown-menu">
                    <li><%=pemissionService.findMenuItemValue(request, "CUSTOMER_CS_MGR","客戶合約管理") %></li>
                    <li><%=pemissionService.findMenuItemValue(request, "ACCT_ITEM_MGR","物料／計價標準管理") %></li>
                 </ul>
                 
               </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/jobbagStatus.do?fid=summary">back to JBM</a></li>
                <li class="dropdown">
                 <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=sessionAccount.getUserId() %> <b class="caret"></b></a>
                 <ul class="dropdown-menu">
                   <li><a href="login.do?fid=logout">Log Out</a></li>
                 </ul>
               </li>                                                                              
            </ul>            
        </div><!--/.nav-collapse -->
      </div>
    </div>
    <div class="alert alert-${alert_class} alert-dismissible" role="alert" id="alert_div_block">
       <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
       <p class="text-center"><strong id="alert_message"></strong></p>
    </div>
    
    <!-- Main component for a primary marketing message or call to action -->
    <div class="container theme-showcase">
      <div class="panel panel-primary">
         <div id="workArea_cs"><tiles:insert attribute="workArea_cs" /></div>            
	     <div align="center" id="tilesWait" style="display:none"><img src="${pageContext.request.contextPath}/images/wait_65.gif" /></div>            
	  </div>
	  
    </div> 
    
    <!-- /container -->
    


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->    
  </body>
</html>
<%
HibernateSessionFactory.closeSession();
%>

