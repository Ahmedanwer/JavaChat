package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class WriteToServerThread extends Thread{
	
	Socket serverConnection;
	

	public WriteToServerThread(Socket serverConnection) {
		this.serverConnection = serverConnection;
	}



	public void run() {
	    try {

	    	
	    	 DataOutputStream dos = new DataOutputStream(serverConnection.getOutputStream());
	            //Create a Scanner to read inputs from the user
	            dos.writeUTF("Client: Welcome From User Client");
	            System.out.println("client sent msg to server saying : Client: Welcome From User Client");
	            
	            BufferedReader into = new BufferedReader(new InputStreamReader(System.in));
	            //Scanner sc = new Scanner(System.in);
	            String msgToBeSent;
	            //3.Perform IO operations with the server
	            while (true) {
	                //read from the user
	            //	msgToBeSent = sc.nextLine();
	            	msgToBeSent = into.readLine();
	                dos.writeUTF(msgToBeSent);
	               System.out.println("client sent msg to server saying :"+msgToBeSent);
	                if (msgToBeSent.equalsIgnoreCase("Bye")) {
	                    break;
	                }
	            }
	           // sc.close();

	            //4.Close/release resources
	            
	            dos.close();
	            serverConnection.close();
	            System.out.println("Connection With Server Closed");

	    	
        /*    //2.if accepted create IO streams
            DataOutputStream dos = new DataOutputStream(serverConnection.getOutputStream());
            //Create a Scanner to read inputs from the user
            dos.writeUTF("Client: Welcome From User Client");
	        
            
            BufferedReader into = new BufferedReader(new InputStreamReader(System.in));
            ////Scanner sc = new Scanner(System.in);
            String msgToBeSent;
            //3.Perform IO operations with the server
            while (true) {
                //read from the user
            //	msgToBeSent = sc.nextLine();
            	msgToBeSent = into.readLine();
                dos.writeUTF(msgToBeSent);
               System.out.println("msgToBeSent: "+msgToBeSent);
                if (msgToBeSent.equalsIgnoreCase("Bye")) {
                    break;
                }
            }
           // sc.close();

            //4.Close/release resources
            
            dos.close();
            serverConnection.close();
            System.out.println("Connection With Server Closed");
            
            
            */
	    	
	    	
	    	
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

	}

	

}
