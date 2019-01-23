package blockchain;

import java.sql.Timestamp;

public class Block {

	public int iIndex;              //索引
	public String sProof;           //工作量证明，在这个例子里面，其实就是一个经过验证的正确的成语
	public String sPreviousHash;    //前一个区块的Hash值
	public Timestamp tsCreateTime;  //区块创建时间戳
	
	
	/*数据块
	 * 
	 * 用户每接上一个成语，会得到系统10元钱的奖励，同时会赢得前面一个用户的2元钱
	 * 数据区同时需要记录自己的用户名和回答出上一个成语的用户名
	 * 
	 * */
	public String sSender;           //回答出上一个成语的用户名
	public String sRecipient;        //回答出当前这个成语的用户名
	public final int iMoneyAward=10; //系统奖励，数额固定
	public final int iMoneyWin=2;    //赢取奖励，数额固定
	
	
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
