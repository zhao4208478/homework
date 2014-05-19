import java.io.*;
import java.net.*;

public class Sender extends Thread{
	private String address = "127.0.0.1"; 
	 private int port = 9878; 
	 
	 public Sender() {
		 Socket client = null ;
		 for(int i=0;i<10; i++)
		 {
			 client = new Socket() ;
			 Msg data = new Msg(i%2); 
			 InetSocketAddress isa = new InetSocketAddress(this.address,this.port); 
	        try 
	        { 
	        	client.connect(isa,10000); 
	        	ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream()); 
	        	
	        	out.writeObject(data); 
	        	System.out.println("���ͷ��� "+data.getN_msg());
	        	
	        	
	        	BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		        String str = br.readLine();
		        if(str.equals("resend1"))
		        {
		        	System.out.println("���鶪ʧ���ط�");
		        	i--;
	        		out.flush(); 
		        	out.close(); 
		        	out = null ; 
		        	data = null ;
		        	client.close();
					client = null ; 
	        		continue;
		        }
		        else if(str.equals("resend2"))
		        {
		        	System.out.println("ACK��ʧ���ط�");
		        	i--;
	        		out.flush(); 
		        	out.close(); 
		        	out = null ; 
		        	data = null ;
		        	client.close();
					client = null ; 
	        		continue;
		        }
		        else System.out.println("\n"+"����ACK"+data.getN_msg()+"      ���ͷ���"+(data.getN_msg()+1)%2);
	        	
	        	
	        	out.flush(); 
	        	out.close(); 
	        	out = null ; 
	        	data = null ;
	        	client.close();
				client = null ; 
	 
	        } 
	        catch(java.io.IOException e) 
	        { 
	          System.out.println("Socket�B���І��} 222!" ); 
	          System.out.println("IOException :" + e.toString()); 
	        } 
	        try {
				this.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	}
	 
	public static void main(String[] args) {
		new Sender();
	}

}