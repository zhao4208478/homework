import java.io.Serializable;
import java.util.*;

public class Msg implements Serializable{
	private int n_msg;
	private String info;
	private Random random; 
	private int rdNum;
	
	public Msg(int n_msg) {
		this.n_msg = n_msg;
		MsgState();
	}
	
	public void MsgState(){//����ģ����鶪ʧ��ACK��ʧ�����
		random = new Random();
		rdNum = Math.abs(random.nextInt())%100;
		if (rdNum<20) info = "losePackage";
		else if(rdNum>80) info ="loseAck";
		else info ="noError";
 		
	}
	
		
	public int getN_msg() {
		return n_msg;
	}
	public void setN_msg(int n_msg) {
		this.n_msg = n_msg;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}