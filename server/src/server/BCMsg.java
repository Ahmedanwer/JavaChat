package server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class BCMsg extends Thread{
	
	String msg;
	ArrayList<Socket> activeGroupClients;

	public BCMsg(String msg, ArrayList<Socket> activeGroupClients) {
		this.msg = msg;
		this.activeGroupClients=activeGroupClients;
	}

	@Override
	public void run() {
		try{
			
			for (int i = 0; i < activeGroupClients.size(); i++) {
			 DataOutputStream dos = new DataOutputStream(activeGroupClients.get(i).getOutputStream());
			 dos.writeUTF(msg);
			}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	

}
