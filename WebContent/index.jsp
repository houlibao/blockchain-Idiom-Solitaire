<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>������������������ݳ�ʼ��...</title>

<script type="text/javascript"> 
function disptime(){
 var today = new Date(); //��õ�ǰʱ��
 var hh = today.getHours();  //���Сʱ�����ӡ���
 var mm = today.getMinutes();
 var ss = today.getSeconds();
 /*����div������Ϊ��ǰʱ��*/
 document.getElementById("myclock").innerHTML="����ʱ��:"+hh+":"+mm+":"+ss;
/*
  ʹ��setTimeout�ں���disptime()�����ٴε���setTimeout
  ���ö�ʱ��ÿ��1�루1000���룩�����ú���disptime()ִ�У�ˢ��ʱ����ʾ
*/
  var myTime=setTimeout("disptime()",500);
}

function disptimes(){
	 var today = new Date(); //��õ�ǰʱ��
	 var hh = today.getHours();  //���Сʱ�����ӡ���
	 var mm = today.getMinutes();
	 var ss = today.getSeconds();
	 /*����div������Ϊ��ǰʱ��*/
	 document.getElementById("myclocks").innerHTML="��ʼʱ��:"+hh+":"+mm+":"+ss;
	}
	
function InitData(){
	
	var xmlhttp;
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			//alert("|"+xmlhttp.responseText+"|");
			  if(xmlhttp.responseText=='0'){
				  	alert('���ݳ�ʼ�����!');
				  	document.getElementById("isLoading").value="no";
					document.getElementById("subbtn").disabled="";
			  }else{
			  		alert('���ݳ�ʼ������!');
			  }
		}
	}
	
	
	xmlhttp.open("GET","/blockchain-Idiom-Solitaire/init.jsp?t="+new Date().getTime(),true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send();
	
}	

function checkinput(obj){
	if(obj.isLoading.value=='yes'){
		alert('����ͬ���������ݣ��������������ͬ����ſ��Կ�ʼ���������Ϸ�����Ժ�');
		return false;
	}
	if(obj.mobile.value=='' || obj.mobile.value.length!=11 || isNaN(obj.mobile.value)){
		alert('����������ֻ��������棬����ȷ��������ֻ�����!');
		return false;
	}
	return true;
}
</script>
</head>
<body onload="disptime()">


<% java.util.Date d = new java.util.Date(); %>
<h1>
��ӭʹ����������������� <br>
</h1>

���Եȣ��������������ͬ����ſ��Կ�ʼ���������Ϸ��<br>
<br>
����ͬ����...<br>
<br>
<div id="myclocks"></div>
<br>
<div id="myclock"></div>

<br>
<form action="/blockchain-Idiom-Solitaire/detail.jsp" method="post" onsubmit="return checkinput(this);">
	<input type="hidden" name="isLoading" id="isLoading" value="yes">
	����ֻ����룺<input type="text" name="mobile">&nbsp;<input type="submit" id="subbtn" value="��ʼ��Ϸ" disabled="disabled">
</form>

<script type="text/javascript">
disptimes();
InitData();
</script>

<br>
-----------------------------------------------------------------------------------<br>
<a href="http://www.hlbhcz.com/" target="_blank"><b>��ӭ���롰Yvan��������</b></a>����������Ա�����������������ճ�Ϊ����ְҵ�ߣ�

</body>
</html>