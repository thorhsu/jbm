<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.painter.util.*"%>
<%@page import="com.salmat.jbm.hibernate.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>JBM v2.0</title>
<link rel="stylesheet" type="text/css" href="css/backend.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/screen.css" /> 
<link rel="stylesheet" href="js/aqua/theme.css" type="text/css" /> 
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/calendar.js"></script>
<script src="js/calendar-font.js"></script>
<script src="js/calendar-setup.js"></script>
<script type="text/javascript">
function submitFinished(){
	   $('#tilesMainFrame').show();
	   $('#tilesLeftMenu').show();	   
	   $('#tilesWait').hide();
}
$().ready(function() {
	window.opener.submitFinished();
	submitFinished()
});

</script>

</head>
<body >
  <table border="0" cellpadding="0" cellspacing="0" id="SysMiddleSet2" width="100%">

        <tr>
          <td height="28" background="images/green_title_bg.jpg" class="black12b" style="background-repeat:no-repeat; padding-left:10px">sorry! exception happened!!!!!!!</td>
        </tr>
        <tr>
          <td height="28" style="padding-top:10px">
          <div align="left">            
        	<p class="text"><font color="red">${message }</font></p>			
		</div>
		</td>
        </tr>
      </table>
</body>
</html>
<%
HibernateSessionFactory.closeSession();
 %>

