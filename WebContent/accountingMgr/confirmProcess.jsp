<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri='/WEB-INF/c.tld' prefix='c' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<script src="js/lib/jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
function confirmSubmit(){
     if(confirm("此動作可能耗費大量時間，確定進行？")){
    	 $("#alertStr").text("在完成工作前，請勿關閉視窗或點擊跳轉其它頁面");
    	 alert("在完成工作前，請勿關閉視窗或點擊跳轉其它頁面");    	 
    	 $("#notTilesWait").show();
    	 window.location = "${params}";
    	 window.opener.submitFinished();
     }else{
    	 window.opener.submitFinished();
     }

}
</script>
<body onload="confirmSubmit()" >
      <table border="0" cellpadding="0" cellspacing="0" id="SysMiddleSet2" width="100%">

        <tr>
          <td height="28" background="images/green_title_bg.jpg" class="black12b" style="background-repeat:no-repeat; padding-left:10px">系統訊息</td>
        </tr>
        <tr>
          <td height="28" style="padding-top:10px">
          <div align="left">            
        	  <p class="text"><font color="red">${message}</font></p>
        	  <p class="text"><font color="red"><span id="alertStr"></span></font></p>			
		  </div>
		</td>
        </tr>
        <tr>
        <td height="28" style="padding-top:10px" align="center">
          <div align="center" style="display:none" id="notTilesWait">            
        	   <img src="images/wait_65.gif" />			
		  </div>
		</td>
        </tr>
      </table>
</body>
