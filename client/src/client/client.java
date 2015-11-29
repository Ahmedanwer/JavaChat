package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class client {
	   private JFrame mainFrame;
	
	   private JTextArea writingArea;
	   private JTextArea ChatArea;
	   
	 
	   public client(){
		      prepareGUI();
		   }
	   private void prepareGUI(){
		      mainFrame = new JFrame("Java SWING Examples");
		      mainFrame.setSize(400,400);
		      mainFrame.setLayout(new GridLayout(5, 5));

		            
		      JTextArea writingArea = new JTextArea();
		      writingArea.setSize(1,4);
		      writingArea.setLineWrap(true);
		      writingArea.setWrapStyleWord(true);
		   
		      
		      
		      
		      
		      JTextArea ChatArea = new JTextArea();
		      ChatArea.setLineWrap(true);
		      ChatArea.setWrapStyleWord(true);
		      ChatArea.setColumns(4);
		      ChatArea.setRows(1);
		      
		      JButton sendButton = new JButton("Send");
		      
		     sendButton.setSize(1,1);
		     
		     
		      mainFrame.addWindowListener(new WindowAdapter() {
		         public void windowClosing(WindowEvent windowEvent){
			        System.exit(0);
		         }        
		      });    
		     
		     
		      mainFrame.add(ChatArea);
		     // mainFrame.add(Box.createRigidArea(new Dimension(5,0)));
		      mainFrame.add(writingArea);
		    //  mainFrame.add(Box.createRigidArea(new Dimension(5,0)));
		      mainFrame.add(sendButton);
		     
		      mainFrame.setVisible(true);  
		   }


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
			                   ChatArea.append(clientMsg);
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
        	client Client2=new client();
        	
        	receiver myReceiver = Client2.new receiver();
        	myReceiver.start();
        	

        	 String otherPairIP = "192.168.1.13";


            try {
            
                //1.Create Client Socket and connect to the server
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

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        
        }

    }
}