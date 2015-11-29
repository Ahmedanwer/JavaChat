package client;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class client {

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

    public static void main(String[] args) {
        {
       // 	client Client2=new client();
        	
        //	receiver myReceiver = Client2.new receiver();
       // 	myReceiver.start();
        	

        	
        	 String otherPairIP = "192.168.1.19";
        	 String ServerIP="192.168.1.2";

            try {
            //	connectToServerThread s= new connectToServerThread(ServerIP);
            //	s.run();
            	
            	//  test
            	
            	
            	Socket serverConnection = new Socket (ServerIP, 1555);
            	System.out.println("Client started");
    			ReadFromServerThread r1=new ReadFromServerThread(serverConnection);
    			WriteToServerThread w1= new WriteToServerThread (serverConnection);
    			w1.start();
    			r1.start();
    			
    	   //2.if accepted create IO streams
    		               		        } catch (Exception e) {
    		            System.out.println(e.getMessage());
    		        }

            	
            	//end test
            	
            	
            	
            	
            	
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