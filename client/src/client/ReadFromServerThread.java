package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ReadFromServerThread extends Thread {

	Socket ServerConnection;
	
	

	public ReadFromServerThread(Socket serverConnection) {
		ServerConnection = serverConnection;
	}


	public void run() {
		try{

	        DataInputStream dis = new DataInputStream(ServerConnection.getInputStream());
	        	
	        String serverMsg;
	        
	        //4.Perform IO Operations with the client
	        while (true) {
	            
	            serverMsg = dis.readUTF();//read from the client
	            System.out.println(serverMsg);
	            //dos.writeUTF(clientMsg);//Echo the msg back to the client            
	            if (serverMsg.equalsIgnoreCase("Bye")) {
	                break;
	            }
	            }
	        
	        //5.Close/release resources
	        dis.close();
	     
	        ServerConnection.close();
	        System.out.println("Connection With Server Closed");
			}
			catch (Exception e){
				System.out.println(e.getMessage());
			}
		
	}

	
}
