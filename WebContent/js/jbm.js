function openPODetail(pageName)
		{window.open(pageName,"pageName", "scrollbars=yes, resizable=false,width=" + screen.availWidth + ", height=" + screen.availHeight);}
		
function pop_upWindow(pageName)	{
	 var x=window.screen.width/2-(800/2);
	 var y=window.screen.height/2-(600/2)-35;	 	 
	 var subWin = window.open("OpenWin.html","pageName", "toolbar=no, menubar=n0, scrollbars=yes, resizable=true,left="+x+", top="+y+" ,width=800, height=600");
	 subWin.location = pageName; 
	 //window.open(pageName,"pageName", "toolbar=no, menubar=n0, scrollbars=yes, resizable=true,left="+x+", top="+y+" ,width=800, height=600");	 	 
}
		
		
function changeMP(selectTable)
{
	document.getElementById('MPInfo').style.display = 'none';
	document.getElementById('PSInfo').style.display = 'none';
	document.getElementById('DMInfo').style.display = 'none';		
	document.getElementById(selectTable).style.display = 'inline';     
}

function changeDispatchType()
{
	document.getElementById('dispatchType_MAIL').style.visibility = 'hidden';
	document.getElementById('dispatchType_RETURN').style.visibility = 'hidden';
	document.getElementById('diapatchTable').style.visibility = 'hidden';
	document.getElementById('logistic_controller').style.visibility = 'hidden';		
	if (window.document.myform.dispatchType.value=="MAIL") {
		document.getElementById('dispatchType_MAIL').style.visibility = 'visible';
		document.getElementById('logistic_controller').style.visibility = 'visible';
		if (window.document.myform.useLg.checked) {
			document.getElementById('diapatchTable').style.visibility = 'visible';		
		}
	}
	if (window.document.myform.dispatchType.value=="RETURN_CUSTOMER"){
		document.getElementById('dispatchType_RETURN').style.visibility = 'visible';
		document.getElementById('logistic_controller').style.visibility = 'visible';
	}
	  
}

function hiddenTable(objTable)
{
   if (document.getElementById(objTable).style.visibility == 'hidden'){
	   document.getElementById(objTable).style.visibility = 'visible';
   }else{
	   document.getElementById(objTable).style.visibility = 'hidden';
   }

}


function expand(objTable)
{
   if (document.getElementById(objTable).style.display != 'none') {
	   document.getElementById(objTable).style.display = 'none';
	}
   else {
	   document.getElementById(objTable).style.display = 'inline';     
	}

}
