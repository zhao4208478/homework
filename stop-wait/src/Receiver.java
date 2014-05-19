import java.io.*;
import java.net.*;

public class Receiver extends Thread{
	 private boolean OutServer = false; 
	 private static ServerSocket server ; 
	 private static  int ServerPort = 9878; 
	
	public static void main(String[] args) {
		(new Receiver()).start();
		}
	
	public Receiver() 
	  { 
	    try 
	    { 
	      server = new ServerSocket(ServerPort); 
	    } 
	    catch(java.io.IOException e) 
	    { 
	      System.out.println("Socket啟動有問題 !" ); 
	      System.out.println("IOException :" + e.toString()); 
	    } 
	  } 	
	
	public void run() 
	  { 
	    Socket socket ; 
	    ObjectInputStream in ; 
	 
	    System.out.println("伺服器已啟動 !"  ); 
	    while(!OutServer) 
	    { 
	      socket = null; 
	      try 
	      { 
	        synchronized(server) 
	        { 
	          socket = server.accept(); 
	        } 
	        //System.out.println("取得連線 : InetAddress = " + socket.getInetAddress()); 
	        socket.setSoTimeout(15000); 
	 
	 
	        in = new ObjectInputStream(socket.getInputStream()); 
	        Msg data = (Msg)in.readObject(); 
	        System.out.println("发送端正在发送的内容"+data.getN_msg()+"  状态："+data.getInfo()); 
	        if(data.getInfo().equals("losePackage"))
	        {
	        	System.out.println("分组丢失");
	        	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	        	dos.writeBytes("resend1"+'\n');
	        	dos.close();
	        }
	        else if(data.getInfo().equals("loseAck"))
	        {
	        	System.out.println("ACK丢失");
	        	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	        	dos.writeBytes("resend2"+'\n');
	        	dos.close();
	        }
	        else
	        {
	        	System.out.println("接收分组 "+data.getN_msg()+"   发送ACK "+data.getN_msg());
	        	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	        	dos.writeBytes("ok"+'\n');
	        	dos.close();
	        }
	        System.out.println();
	        
	        
	        in.close(); 
	        in = null ; 
	        socket.close();
	 
	      } 
	      catch(java.io.IOException e) 
	      { 
	    	  System.out.println("Socket連線有問題111 !" ); 
	    	  System.out.println("IOException :" + e.toString()); 
	      } 
	      catch(java.lang.ClassNotFoundException e) 
	      { 
	    	  System.out.println("ClassNotFoundException :" + e.toString()); 
	      } 
	    } 
	  } 
	
}