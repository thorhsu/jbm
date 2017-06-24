<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.painter.util.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>



<%
	String  targetURI = (String)request.getAttribute("targetURI");
	String  queryString = (String)request.getAttribute("queryString");

	String body_id ="Web01Products";
	
	if (targetURI.equalsIgnoreCase("findGroupbuy.do") ) {
		
		if (queryString.equalsIgnoreCase("fid=homepage")  ) 
			body_id ="webHome";
		else 
			body_id ="Web01Products";
	}

%>	
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JBM v2.0</title>
<link rel="stylesheet" type="text/css" href="css/backend.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/screen.css" /> 
<link rel="stylesheet" href="js/aqua/theme.css" type="text/css" />
<!-- 讓ie可以使用css3 -->
<script src="js/selectivizr-min.js" type="text/javascript"></script> 
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script src="js/jquery.timer.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/calendar.js"></script>
<script src="js/calendar-font.js"></script>
<script src="js/calendar-setup.js"></script>

<script type="text/javascript">

   //滑鼠在grid上移動時變換class   
   var prevClassName = "";
   function changeClass(overLineId, className){
      
      var overLine = document.getElementById(overLineId);
      if(prevClassName == ""){
         prevClassName = $(overLine).attr("class");
      }
      if(className != null){
         $(overLine).attr("class", className);
      }else if(prevClassName != ""){
         $(overLine).attr("class", prevClassName);
         prevClassName = "";
      }
      //alert(className);
   }
   
   //checkbox勾選後變換class
   function checkedLine(checkLineId, lineId, className){
       
       var checkLine = document.getElementById(checkLineId);
       var ischecked = $(checkLine).attr('checked');
       var checkedLine = document.getElementById(lineId);
       if(ischecked){
          $(checkedLine).addClass("checkedLine");
          prevClassName = "checkedLine";          
       }else{
          $(checkedLine).removeClass("checkedLine");
          prevClassName = className;       
       }
       
   }
      
   function waitToFinished(){
	   $('#tilesMainFrame').hide();
	   $('#tilesLeftMenu').hide();	   
	   $('#tilesWait').show();
   }
   function submitFinished(){
	   $('#tilesMainFrame').show();
	   $('#tilesLeftMenu').show();	   
	   $('#tilesWait').hide();
   }
   //回列表 
   function backToList(){
      var backToListURL = decodeURIComponent("${backToListURL}");
      waitToFinished();
      if(backToListURL.indexOf("null") >= 0 || backToListURL == "")
         javascript:history.go(-1)
      else
         location = backToListURL;
   }

   //超連結到編輯頁面
   function toEditLocation(actionLocation, id, pkName, edit){
	   var viewOrEdit;
	   if(edit != null  && edit == true){
		   viewOrEdit = "editInit";
	   }else{
		   viewOrEdit = "view";
	   }
	   var myId = document.getElementById(id);
	   var idVal = $(myId).val();
	   if(idVal != null && idVal != "" && idVal != "請選擇"){		   
	      window.location = actionLocation + ".do?fid=" + viewOrEdit + "&" + pkName + "=" + idVal + "&idList=" + idVal + "&backToListURL=${backToListURL}";
	      waitToFinished();
	   }
   }
   
   //submit時攔截並顯示等待畫面
   $().ready(function() {
	      $('input[name=backToListURL]').val(decodeURIComponent("${backToListURL}"));	      
	      $('img[src="images/calendar2.gif"]').addClass('handCursor');
	      $("form").submit(
	    		 function(){
	    			 var thisAction = $(this).attr("action");	    			 
	    			 if(thisAction.indexOf("completedJob.do") >= 0 ){
	    				 //alert(thisAction);
	    			 }else{	    		     
	    			    waitToFinished();
	    		     }
	    		 }
	       );
	      
	      $("a[href!= '#']").click(
		    	 function(){		    		 
		    		 var thisVal =  $(this).attr("href");
		    		 if(thisVal != null && (thisVal.indexOf("pdf/") >= 0 || thisVal.indexOf("download.do") >= 0 || thisVal == "" 
		    				 || thisVal.indexOf("completedJob.do") >= 0 || thisVal.indexOf("confirmDelete(") >= 0)){
		    			 
		    		 }else if(thisVal.indexOf(".do?") >= 0){
		    		    waitToFinished();
		    		 }
		    	 }
	      );
	      
	  });
   

</script>

</head>
<body >

<div align="left">

<table border="0" width="95%" id="table1" >
	
	<tr>
		<td colspan="2" valign="top" ><div align="center"><tiles:insert attribute="header" /></div></td>
	</tr>

	<tr>
		<td width="60" valign="top"><div id="tilesLeftMenu" align="left"><tiles:insert attribute="left" /></div></td>
		<td valign="top" >
		    <div align="left" id="tilesMainFrame"><tiles:insert attribute="workArea" /></div>
		    <div align="center" align="center" id="tilesWait" style="display:none"><img src="images/wait_65.gif" /></div>
		</td>
		<input type="hidden" name="backToListURL" value="${backToListURL}" />
	</tr>
	<tr>
		<td width="60" valign="top"></td>
		<td valign="top"><tiles:insert attribute="footer" /></td>
	</tr>
</table>

</div>
</body>
</html>
<%
HibernateSessionFactory.closeSession();
 %>

