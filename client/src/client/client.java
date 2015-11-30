package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class client {

	private String userName,password;
	static Socket serverConnection;
	static DataOutputStream Sdos;
	static DataInputStream Sdis;
	static String id;
	
   private class receiver extends Thread
   {
	   @Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			 try 
				{
			           //1.Create Server Socket
			           ServerSocket mySocket = new ServerSocket(1243);
			           //Server is always On
			          
			           while (true) {
			        	   Socket c;
				           c = mySocket.accept();

			                                            
			               DataInputStream dis = new DataInputStream(c.getInputStream());
			               //4.Perform IO Operations with the client
			               while (true) {
			                   String clientMsg;
			                   clientMsg = dis.readUTF();//read from the client
			                   System.out.println("B says "+clientMsg);
			                   if (clientMsg.equalsIgnoreCase("Bye")) {
			                        break;
			                    }
			               }
			               //5.Close/release resources
			               dis.close();
			             
			               c.close();
			           }

			       } catch (Exception e) {
			           System.out.println(e.getMessage());
			       }
			
		}  
   }

   public static String Login(String user, String pass){

	   JSONObject obj = new JSONObject();
	   		
	   	  obj.put("header", "login");
	      obj.put("username", user);
	      obj.put("password", pass);
	      try {
			Sdos.writeUTF(obj.toJSONString());
			id=Sdis.readUTF();
			if (id!="0") {
				System.out.println("Login Successful, ID Retrived= "+id);
				return id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	      System.out.println("login failed,ID Retrived= "+id );
		return "0";
		  
   }
   
   public static Boolean BCMsg (String msg) {

	   JSONObject obj = new JSONObject();
	   		
	   	  obj.put("header", "BCM");
	      obj.put("msg", msg);
	      try {
			obj.put("SenderIP", InetAddress.getLocalHost());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	      try {
			System.out.println(obj.toJSONString());
			Sdos.writeUTF(obj.toJSONString());
			if (Sdis.readUTF().equalsIgnoreCase("login successful")) {
				System.out.println("BCM Successful");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	      System.out.println("BCM failed");
		return false;
		  
}
   
   public static JSONArray getAllUsers (){
	return null;
	   
   }
   
   public static void main(String[] args) {
        {
       // 	client Client2=new client();
        	
        //	receiver myReceiver = Client2.new receiver();
       // 	myReceiver.start();
        	

        	
        	 String otherPairIP = "192.168.1.19";
        	 String ServerIP="192.168.43.64";

            try {
         	
            	
            	serverConnection = new Socket (ServerIP, 1555);
            	Sdos=new DataOutputStream(serverConnection.getOutputStream()) ;
    			Sdis=new DataInputStream(serverConnection.getInputStream());
            	System.out.println("Client started");
            	System.out.println(Sdis.readUTF());
            	System.out.println(Login("aya","123"));
            	System.out.println(BCMsg("Teseting BCming"));
            	
    			//ReadFromServerThread r1=new ReadFromServerThread(serverConnection);
    			WriteToServerThread w1= new WriteToServerThread (serverConnection);
    			w1.start();
    			//r1.start();
    			
    	 
    		   } catch (Exception e) {System.out.println(e.getMessage());}

          
            	
      /*          //1.Create Client Socket and connect to the server
                Socket otherClient = new Socket(otherPairIP, 1243);
                //2.if accepted create IO streams
                DataOutputStream dos = new DataOutputStream(otherClient.getOutputStream());
                               //Create a Scanner to read inputs from the user
                Scanner sc = new Scanner(System.in);
               String msgToBeSent;
                //3.Perform IO operations with the server
                while (true) {
                    //read from the user
                	msgToBeSent = sc.nextLine();
                    dos.writeUTF(msgToBeSent);
                   
                    if (msgToBeSent.equalsIgnoreCase("Bye")) {
                        break;
                    }
                }

                //4.Close/release resources
                
                dos.close();
                otherClient.close();
*/
      
        
        }

    }
}