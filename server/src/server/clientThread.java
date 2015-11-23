package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class clientThread extends Thread {

	Socket c;
	ArrayList<Socket> activeGroupClients;
	
	public clientThread(Socket clinetArrived, ArrayList<Socket> activeClients) {
		this.c = clinetArrived;
		this.activeGroupClients=activeClients;
	}


	@Override
	public void run() {
		try{
			
        //indicate the arrival of a new client
        System.out.println("New Client Arrived");
        
        //3.Create IO Streams
        DataOutputStream dos = new DataOutputStream(c.getOutputStream());
        
        //say Hi
        dos.writeUTF("Server: Welcome new user");
        
        DataInputStream dis = new DataInputStream(c.getInputStream());
        
        
        //4.Perform IO Operations with the client
        while (true) {
            String clientMsg; 
            clientMsg = dis.readUTF();//read from the client
            //dos.writeUTF(clientMsg);//Echo the msg back to the client            
            if (clientMsg.equalsIgnoreCase("Bye")) {
                break;
            }else  {
            	BCMsg CurrentBC= new BCMsg(clientMsg,activeGroupClients);
            	CurrentBC.run();
            	}
            }
        
        //5.Close/release resources
        dis.close();
        dos.close();
        c.close();
        System.out.println("Client Left");
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
		

	
	
	
}
