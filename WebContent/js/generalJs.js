 //回列表 
   function backToList(){
      var backToListURL = decodeURIComponent("${backToListURL}");
      alert(backToListURL);
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
	   }
   }