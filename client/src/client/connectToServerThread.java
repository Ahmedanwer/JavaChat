package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class connectToServerThread extends Thread{

	String serverIP;
	
	
	public connectToServerThread(String serverIP) {
		this.serverIP = serverIP;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		try {
			Socket serverConnection = new Socket (serverIP, 1555);
			ReadFromServerThread r1=new ReadFromServerThread(serverConnection);
			WriteToServerThread w1= new WriteToServerThread (serverConnection);
			r1.start();
			w1.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

	
	
}
