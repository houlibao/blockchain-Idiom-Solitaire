package blockchain;

import java.sql.Timestamp;

public class Block {

	public int iIndex;              //����
	public String sProof;           //������֤����������������棬��ʵ����һ��������֤����ȷ�ĳ���
	public String sPreviousHash;    //ǰһ�������Hashֵ
	public Timestamp tsCreateTime;  //���鴴��ʱ���
	
	
	/*���ݿ�
	 * 
	 * �û�ÿ����һ�������õ�ϵͳ10ԪǮ�Ľ�����ͬʱ��Ӯ��ǰ��һ���û���2ԪǮ
	 * ������ͬʱ��Ҫ��¼�Լ����û����ͻش����һ��������û���
	 * 
	 * */
	public String sSender;           //�ش����һ��������û���
	public String sRecipient;        //�ش����ǰ���������û���
	public final int iMoneyAward=10; //ϵͳ����������̶�
	public final int iMoneyWin=2;    //Ӯȡ����������̶�
	
	
	public Block(int index,String proof,String hash,Timestamp createtime,String sender,String recipient){
		iIndex=index;
		sProof=proof;
		sPreviousHash=hash;
		tsCreateTime=createtime;
		sSender=sender;
		sRecipient=recipient;
	}
	
	public String toInfoString(){
		return this.iIndex+"#"+this.sProof+"#"+(this.sPreviousHash==""?"*":this.sPreviousHash)+"#"+this.tsCreateTime+"#"+(this.sSender==""?"*":this.sSender)+"#"+(this.sRecipient==""?"*":this.sRecipient);
	}
}
