package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class BCMsg extends Thread{
	
	String msg;
	ArrayList<Socket> activeGroupClients;


	public BCMsg(String msg) {
		this.activeGroupClients = server.activeClients;
		this.msg = msg;
	}


	@Override
	public void run() {
		try{
		
			System.out.println("recevied msg and commencing BC-ing (inside) "+msg);
			for (int i = 0; i < activeGroupClients.size(); i++) {
				System.out.println( "Server Sent to Client No."+i+": "+msg);

			DataOutputStream dos = new DataOutputStream(activeGroupClients.get(i).getOutputStream());
			 dos.writeUTF(msg);
			 
			 

			//  Socket otherClient = new Socket("192.168.1.21", 1243);

			//  Socket otherClient = new Socket("192.168.1.3", 1243);
              //2.if accepted create IO streams
              //DataOutputStream dos = new DataOutputStream(otherClient.getOutputStream());
              //Create a Scanner to read inputs from the user
             // dos.writeUTF("In Response to: "+msg);
              
     
             // otherClient.close();
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	

}
