
<%@page import="blockchain.BlockChain"%>
<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>�������������</title>

<script type="text/javascript"> 

var iReload=0

function disptime(){
	iReload+=1;
	
	
	if(iReload>60*10 && document.getElementById("isLoading").value=="no"){ //10���ӣ�����ˢ��һ������������
		InitData();
		iReload=0;
	}
	
	
	 var today = new Date(); //��õ�ǰʱ��
	 var hh = today.getHours();  //���Сʱ�����ӡ���
	 var mm = today.getMinutes();
	 var ss = today.getSeconds();
	 /*����div������Ϊ��ǰʱ��*/

	 
	 if(document.getElementById("isLoading").value=="no"){
		 document.getElementById("myclock").innerHTML="����ʱ��:"+hh+":"+mm+":"+ss+"<br>��������������Ϊ���¡�";
	 }else{
		 document.getElementById("myclock").innerHTML="����ʱ��:"+hh+":"+mm+":"+ss+"<br>���ڼ��͸��±������������ݡ�����";
	 }
	 
	/*
	  ʹ��setTimeout�ں���disptime()�����ٴε���setTimeout
	  ���ö�ʱ��ÿ��1�루1000���룩�����ú���disptime()ִ�У�ˢ��ʱ����ʾ
	*/
	  var myTime=setTimeout("disptime()",1000);
}

	
function InitData(){
	document.getElementById("isLoading").value="yes";
	
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

			  if(xmlhttp.responseText=='0'){
				  	//alert('���ݳ�ʼ�����!');
				  	document.getElementById("isLoading").value="no";
					//document.getElementById("subbtn").disabled="";
			  }else{
			  		//alert('���ݳ�ʼ������!');
			  }
		}
	}
	
	
	xmlhttp.open("GET","/blockchain/init.jsp?t="+new Date().getTime(),true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send();
	
}	

function CheckData(){
	document.getElementById("isLoading").value="yes";
	
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

			  document.getElementById("isLoading").value="no";
			  if(xmlhttp.responseText=='true'){
				  	document.getElementById("subdirect").value="yes";
				 	 document.getElementById("answer").disabled=false;
				  	document.getElementById("subbtn").disabled=false;
				  	document.getElementById("subbtn").value="ȷ��";	  				  
				  	document.getElementById("subform").submit();
			  }else{
			  		alert("������һ�����Ѿ��������û���ǰ�ش��˵�ǰ������������");
			  		window.location.reload(true);
				  	//document.getElementById("subdirect").value="yes";
				 	//document.getElementById("answer").disabled=false;
				  	//document.getElementById("subbtn").disabled=false;
				  	//document.getElementById("subbtn").value="ȷ��";	  				  
				  	//document.getElementById("subform").submit();		  		
			  }
		}
	}
	
	
	xmlhttp.open("GET","/blockchain/check.jsp?t="+new Date().getTime(),true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send();
	
}

function checkinput(obj){
	/*
	if(obj.isLoading.value=='yes'){
		alert('����ͬ���������ݣ��������������ͬ����ſ��Կ�ʼ���������Ϸ�����Ժ�');
		return false;
	}
	*/
	if(document.getElementById("subdirect").value=="yes"){
		return true;
	}
	
	if(document.getElementById("subbtn").disabled==true){
		return false;
	}
	
	if(obj.answer.value==''){
		alert('��������ĳ��������!');
		return false;
	}
	
	CheckData();
	
	document.getElementById("subbtn").value="���ڵݽ��ͼ����Ĵ𰸣����Եȡ�����";
	document.getElementById("subbtn").disabled=true;
	document.getElementById("answer").disabled=true;
	//document.getElementById("linfo").innerText="���ڼ����Ч��...";
	
	return false;
}
</script>
</head>
<body onload="disptime();">




<%
if(request.getParameter("mobile")!=null){
	session.setAttribute("mobile", request.getParameter("mobile"));
}
%>


<%
if(request.getParameter("answer")!=null){
	
	//��������������
	blockchain.BlockChain.LoadData();
	
	//��ȡ�����������һ������
	String sPre=blockchain.BlockChain.lBlockchain.get(blockchain.BlockChain.lBlockchain.size()-1).sProof;
	
	//�û�����ĳ���
	String sCur=new String(request.getParameter("answer").getBytes("ISO8859-1"),"gb2312");
	
	System.out.println(sCur);
	
	//�ж��ǲ�����ȷ�𰸣�����ǣ���Ҫ�����¿鲢��ӵ���������
	if(blockchain.BlockChain.ValidProof(sPre,sCur)){
		
		blockchain.Block bPre=blockchain.BlockChain.lBlockchain.get(blockchain.BlockChain.lBlockchain.size()-1);
		
		//��ȡǰһ�����Hash
		String sHash=blockchain.BlockChain.Hash(bPre);
		
		//�����¿�
		blockchain.Block bCur=blockchain.BlockChain.NewBlock(bPre.iIndex+1, sCur, sHash, new java.sql.Timestamp(System.currentTimeMillis()).toString(), bPre.sRecipient, (String)session.getAttribute("mobile"));
		
		//���������������浽�����ļ�
		blockchain.BlockChain.lBlockchain.add(bCur);
		blockchain.BlockChain.WriteData();
		%>
		<script type="text/javascript">alert('��ϲ��ϲ���ش���ȷ��');</script>
		<%
	}else{
		%>
		<script type="text/javascript">alert('�ܱ�Ǹ���ش����');</script>
		<%
	}
}

%>

<h1>
��ӭʹ����������������� &nbsp;&nbsp;<a href="/blockchain/detail.jsp">ˢ��</a></a><br>
</h1>
<%
blockchain.BlockChain.LoadData();
int iScore=0;
int i=0;
while(i<blockchain.BlockChain.lBlockchain.size()){
	blockchain.Block bThis=blockchain.BlockChain.lBlockchain.get(i++);
	//System.out.println("/"+session.getAttribute("mobile")+"/"+bThis.sRecipient+"/");
	if(session.getAttribute("mobile").equals(bThis.sRecipient)){
		if(bThis.sSender!="")iScore+=(bThis.iMoneyAward+bThis.iMoneyWin);
		else iScore+=(bThis.iMoneyAward);
	}
	if(session.getAttribute("mobile").equals(bThis.sSender)){
		iScore-=bThis.iMoneyWin;
	}	
}
%>
<br>
����ֻ������ǣ�<%= session.getAttribute("mobile") %><br>
<br>
��ķ����ǣ�<%= iScore %><br>
<HR>

<%
String sProofs = "-><b>"+blockchain.BlockChain.lBlockchain.get(blockchain.BlockChain.lBlockchain.size()-1).sProof+"</b>";
if(blockchain.BlockChain.lBlockchain.size()>=2){
	sProofs = "->"+blockchain.BlockChain.lBlockchain.get(blockchain.BlockChain.lBlockchain.size()-2).sProof+sProofs;
}
if(blockchain.BlockChain.lBlockchain.size()>=3){
	sProofs = "->"+blockchain.BlockChain.lBlockchain.get(blockchain.BlockChain.lBlockchain.size()-3).sProof+sProofs;
}
if(blockchain.BlockChain.lBlockchain.size()>3){
	sProofs = "..."+sProofs;
}else{
	//sProofs = "��"+sProofs;
}
%>
��ǰ�ȴ������ĳ����ǣ�<%= sProofs %><br>
<br>
<form id="subform" action="/blockchain/detail.jsp" method="post" onsubmit="return checkinput(this);">
	<input type="hidden" name="isLoading" id="isLoading" value="no">
	<input type="hidden" name="isLoading" id="subdirect" value="no">
	�����롰<%=blockchain.BlockChain.lBlockchain.get(blockchain.BlockChain.lBlockchain.size()-1).sProof %>���Ľ�����<input type="text" id="answer" name="answer">&nbsp;<input type="submit" id="subbtn" value="ȷ��">&nbsp;<lable id="linfo"></lable>
</form>

<HR>
<div id="myclock"></div>
<br>

</body>
</html>