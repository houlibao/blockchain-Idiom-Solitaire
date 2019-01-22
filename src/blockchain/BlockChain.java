package blockchain;


import blockchain.Block;

import java.sql.Timestamp;
import java.util.*;
import java.io.*;
import java.net.*;

public class BlockChain {
	public static List<Block> lBlockchain=new ArrayList<>();    //�����洢����
		
	
	
	public BlockChain(){
		//lBlockchain.add(null);
	}
	
	
	
	static final String  sIPPre="192.168.1.";                 //�Ծ������ڵĵ��Խ���ɨ�裬�ҵ�����������ص�����
	static final String  sDataFileDir="d://blockchain";     //���ش洢·��
	
	//��ʶ�㷨
	//���� true ��ʾ��ǰ�ڵ㹤�����Ա��Ͽ�
	public static boolean Unanimous(){
		boolean bRet=true;
		
		//ɨ���ܱߵĽڵ㣬�ҵ�����������ص�����
		int iLastLen=0;
		String sLastChain="";
		for(int i=0;i<255;i+=1){
			String sThisURL="http://"+sIPPre+i+":8080/blockchain/chain.jsp";
			
			System.out.println(sThisURL);
			
			String sChain=httpRequest(sThisURL);
			
			if(sChain!=""){
				System.out.println(sChain);
				String sTemp[]=sChain.split("##");
				if(sTemp.length>iLastLen){
					iLastLen=sTemp.length;
					sLastChain=sChain;
				}
			}
		}
		
		BlockChain.LoadData();
		try{
			//��������ڵ���ڳ��ȴ��ڱ��ؽ�3��+1�������򱾴Ρ��ڿ���Ч
			if(sLastChain!="" && iLastLen >= BlockChain.lBlockchain.size()+1){
				bRet=false;
				FileOutputStream out = new FileOutputStream(new File(sDataFileDir+"//data.txt"));
				sLastChain=sLastChain.replace("##", "\r\n");
				out.write((sLastChain+"\r\n").getBytes());
				out.close();
			}
		}catch(Exception e){	
		}
		return bRet;
	}
	
	
	//�������ȡ���������ݵ������ļ�
	public static void DowloadData(){

		//��������ļ�Ŀ¼�������ھʹ���
        File dirFile = new File(sDataFileDir);
        boolean bFile   = dirFile.exists();
		if(!bFile ){
			bFile = dirFile.mkdir();
			//���´����ı����ļ�����дһ��������
			try{
				FileOutputStream out = new FileOutputStream(new File(dirFile+"//data.txt"));
				out.write((BlockChain.CreateFirstBlock().toInfoString()+"\r\n").getBytes());
				out.close();
			}catch(Exception e){	
			}
		}
		
		
		//ɨ���ܱߵĽڵ㣬�ҵ�����������ص�����
		int iLastLen=0;
		String sLastChain="";
		for(int i=0;i<255;i+=1){
			String sThisURL="http://"+sIPPre+i+":8080/blockchain/chain.jsp";
			
			System.out.println(sThisURL);
			
			String sChain=httpRequest(sThisURL);
			
			if(sChain!=""){
				System.out.println(sChain);
				String sTemp[]=sChain.split("##");
				if(sTemp.length>iLastLen){
					iLastLen=sTemp.length;
					sLastChain=sChain;
				}
			}
		}
		
		try{
			if(sLastChain!=""){
				FileOutputStream out = new FileOutputStream(new File(dirFile+"//data.txt"));
				//System.out.println("before:"+sLastChain);
				sLastChain=sLastChain.replace("##", "\r\n");
				//System.out.println("after:"+sLastChain);
				out.write((sLastChain+"\r\n").getBytes());
				out.close();
			}
		}catch(Exception e){	
		}
		
	}
		
	//���ļ���ȡ���������ڴ�
	public static void LoadData(){
		BlockChain.lBlockchain.clear();
		
		File file=new File(sDataFileDir+"//data.txt");  
        BufferedReader reader=null;  
        String temp=null;   
        try{  
                reader=new BufferedReader(new FileReader(file));  
                while((temp=reader.readLine())!=null){                  	
                	String [] sInfo=temp.split("#");
                	//System.out.println(sInfo[5]);
                	Timestamp tThis=new Timestamp((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(sInfo[3]).getTime());
                	Block bThis = BlockChain.NewBlock(Integer.parseInt(sInfo[0]), sInfo[1], (sInfo[2].equals("*")?"":sInfo[2]), tThis, (sInfo[4].equals("*")?"":sInfo[4]), (sInfo[5].equals("*")?"":sInfo[5]));
                	BlockChain.lBlockchain.add(bThis);
                }  
        }  
        catch(Exception e){  
            e.printStackTrace();  
        }  
        finally{  
            if(reader!=null){  
                try{  
                    reader.close();  
                }  
                catch(Exception e){  
                    //e.printStackTrace();  
                }  
            }  
        }  		
	}
	
	//�����������ڴ�д���ļ�
	public static void WriteData(){
		
		String sChain="";
		
		int i=0;
		while(i<BlockChain.lBlockchain.size()){
			Block bThis=BlockChain.lBlockchain.get(i++);
			sChain+=(bThis.toInfoString()+"\r\n");
		}
		
		try{
				FileOutputStream out = new FileOutputStream(new File(sDataFileDir+"//data.txt"));
				out.write((sChain).getBytes());
				out.close();
		}catch(Exception e){	
		}		
	}
	
	public static String StringBlockchain(){
		String sRet="";
		
		int i=0;
		while(i<BlockChain.lBlockchain.size()){
			Block bThis=BlockChain.lBlockchain.get(i++);
			if(sRet=="")sRet=bThis.toInfoString();
			else sRet+="##"+bThis.toInfoString();
		}
		
		return sRet;
	}
	
	//�����¿�
	public static Block NewBlock(int index,String proof,String hash,Timestamp createtime,String sender,String recipient){
		Block bRet=null;
		
		//�����ﴴ��һ���¿�
		bRet = new Block(index,proof,hash,createtime,sender,recipient);
		
		return bRet;
	}
	
	//��ʼ��Ĵ�������������һ���飬�����ǹ̶�����Ϣ
	//�߼�����˵��ֻ������������Ʒ�ĵ�һ���û���һ��������ʱ�򣬲Ż���Ҫ����������
	public static Block CreateFirstBlock(){
		try{
			Timestamp t=new Timestamp((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2018-01-01 01:01:01").getTime());
			return NewBlock(0,"�������","",t,"","");
		}catch(Exception e){
			return null;
		}
	}
	
	
	//Hash һ����
	public static String Hash(Block block){
		String sHash=null;
		
		//������Hash һ����
		String s=block.sPreviousHash+block.sProof+block.sRecipient+block.sSender+block.tsCreateTime.toString();
		
		sHash = MD5(s);
	    
		return sHash;
	}
	
	public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // ���MD5ժҪ�㷨�� MessageDigest ����
            java.security.MessageDigest mdInst = java.security.MessageDigest.getInstance("MD5");
            // ʹ��ָ�����ֽڸ���ժҪ
            mdInst.update(btInput);
            // �������
            byte[] md = mdInst.digest();
            // ������ת����ʮ�����Ƶ��ַ�����ʽ
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }	
	
	//��֤��ǰ�ĳ����Ƿ���Ϲ���
	//pre ǰһ������
	//cur ��һ������
	public static boolean ValidProof(String pre,String cur){
		
		//��֤��������ͷһ�����ǲ�����һ����������һ����
		if(cur.charAt(0)!=pre.charAt(pre.length()-1)){
			return false;
		}
		
		//��֤�Ƿ��ǳ���
		//http://chengyu.t086.com/chaxun.php?q=%B9%E2%C3%F7%D5%FD%B4%F3&t=ChengYu
		String content=httpRequest("http://chengyu.t086.com/chaxun.php?q="+cur+"&t=ChengYu");
		if(content=="" || content.indexOf("û���ҵ�����������صĳ���")!=-1 || content.indexOf("������̫��")!=-1){
			return false;
		}
		
		return true;
	}

	
	
	//�������
	public static void main(String[] args) {
		System.out.println("�����������������...");
		
		DowloadData();
		
		//BlockChain.LoadData();
		//System.out.println(BlockChain.StringBlockchain());
		
		//System.out.println(ValidProof("�������","��ý��Ȣ"));
		
		
		/*
		try{
			System.out.println("������IP = " + java.net.InetAddress.getLocalHost());
		}catch(Exception e){
			
		}
		*/
		/*
		int x=5;
		int y=0;
		while(true){
			String md5=BlockChain.MD5(""+(x*y));
			System.out.println(x+"*"+y+"md5="+md5);
			if(md5.charAt(md5.length()-1)=='0' && md5.charAt(md5.length()-2)=='0'){
				break;
			}
			y+=1;
		}
		*/
		//System.out.println();
	}
	
	public static String httpRequest(String url){
		String content = "";
		HttpURLConnection connection = null;
		try{
			URL u = new URL(url);
			connection = (HttpURLConnection)u.openConnection();
			

			connection.setConnectTimeout(200);
			//connection.setReadTimeout(2000);
			
			connection.setRequestMethod("GET");

			int code = connection.getResponseCode();

			if(code == 200){
				InputStream in = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(in,"gb2312");
				BufferedReader reader = new BufferedReader(isr);
				String line = null;
				while((line = reader.readLine()) != null){
					content += line;
				}
			}

		}catch(MalformedURLException e){
			//e.printStackTrace();
		}catch(IOException e){
			//e.printStackTrace();
		}finally{
			if(connection != null){
				connection.disconnect();
			}
		}
		return content;
	
	}	
	
}
