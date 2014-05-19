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
	      System.out.println("Socket�����І��} !" ); 
	      System.out.println("IOException :" + e.toString()); 
	    } 
	  } 	
	
	public void run() 
	  { 
	    Socket socket ; 
	    ObjectInputStream in ; 
	 
	    System.out.println("�ŷ����ц��� !"  ); 
	    while(!OutServer) 
	    { 
	      socket = null; 
	      try 
	      { 
	        synchronized(server) 
	        { 
	          socket = server.accept(); 
	        } 
	        //System.out.println("ȡ���B�� : InetAddress = " + socket.getInetAddress()); 
	        socket.setSoTimeout(15000); 
	 
	 
	        in = new ObjectInputStream(socket.getInputStream()); 
	        Msg data = (Msg)in.readObject(); 
	        System.out.println("���Ͷ����ڷ��͵�����"+data.getN_msg()+"  ״̬��"+data.getInfo()); 
	        if(data.getInfo().equals("losePackage"))
	        {
	        	System.out.println("���鶪ʧ");
	        	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	        	dos.writeBytes("resend1"+'\n');
	        	dos.close();
	        }
	        else if(data.getInfo().equals("loseAck"))
	        {
	        	System.out.println("ACK��ʧ");
	        	DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	        	dos.writeBytes("resend2"+'\n');
	        	dos.close();
	        }
	        else
	        {
	        	System.out.println("���շ��� "+data.getN_msg()+"   ����ACK "+data.getN_msg());
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
	    	  System.out.println("Socket�B���І��}111 !" ); 
	    	  System.out.println("IOException :" + e.toString()); 
	      } 
	      catch(java.lang.ClassNotFoundException e) 
	      { 
	    	  System.out.println("ClassNotFoundException :" + e.toString()); 
	      } 
	    } 
	  } 
	
}