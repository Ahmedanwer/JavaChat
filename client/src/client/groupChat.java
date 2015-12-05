package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class groupChat {
	
	
	   private JFrame mainFrame;
	   static JTextArea writingArea;
	   static JTextArea chatArea;
	   JButton sendButton;
	   int groupID;
	   Group receiverGroup;
	   
	   
	   
	   public groupChat()
	   
	       {
		      prepareGUI();     
		      receiver myReceiver = new receiver();
		      myReceiver.start();
		      
		   }
	   
	   public groupChat(Group group)
	   
	       {
		      receiverGroup=group;
		      groupID=group.getId();
		      prepareGUI();     
		      receiver myReceiver = new receiver();
		      myReceiver.start();
		   }
	   
	   private void prepareGUI()
	   
	   {
		      mainFrame = new JFrame("Chatting with "+(receiverGroup!=null?receiverGroup.getGroupName():"Your Self"));
		      mainFrame.setLayout(new FlowLayout());
		      mainFrame.setSize(410,410);
		    
		     
		      JPanel myPanel=new JPanel(); 
		      
		      myPanel.setLayout(new GridBagLayout());
		      myPanel.setSize(400,400);
		      
		            
		      writingArea = new JTextArea();
		      writingArea.setLineWrap(true);
		      writingArea.setWrapStyleWord(true);
		      writingArea.setPreferredSize(new Dimension(300,30));
		   
		      
		      
		      
		      
		      chatArea = new JTextArea();
		      chatArea.setLineWrap(false);
		      chatArea.setEditable(false);
		      chatArea.setPreferredSize(new Dimension(390,370));
		      chatArea.setWrapStyleWord(false);
		     
		      JScrollPane scroll = new JScrollPane (chatArea   , 
		    		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		      
		      sendButton = new JButton("Send");

		      
		      sendButton.addActionListener(new ActionListener() {
		    	
		            public void actionPerformed(ActionEvent e)
		            {
		            	
		            	// System.out.println(c.writingArea.getText());
		            	
		            	
		            	
		            	//apiFunctions.connect(ip);
		  		        apiFunctions.BCMsg(writingArea.getText());
		                writingArea.setText("");
		            	
		            }
		        });      		    
		      
		     
		     
		      GridBagConstraints gBC = new GridBagConstraints();
		      gBC.fill = GridBagConstraints.HORIZONTAL;
		     
		      gBC.gridx = 0;
		      gBC.gridy = 0;
		      gBC.gridwidth = 2;
		      myPanel.add(scroll,gBC);
		      gBC.gridwidth = 1;
		      gBC.gridx = 0;
		      gBC.gridy = 1;
		       
		      myPanel.add(writingArea,gBC); 
		      gBC.gridx = 1;
		      gBC.gridy = 1;
			  myPanel.add(sendButton,gBC); ;
			   
		     
		      mainFrame.add(myPanel);
		     // mainFrame.add(Box.createRigidArea(new Dimension(5,0)));
		    
		     
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
	 			          
	 			          
	 			        	   Socket c;
	 				        
	 			               //4.Perform IO Operations with the client
	 			               while (true) {
	 			            	   c = mySocket.accept();       
	 				               DataInputStream dis = new DataInputStream(c.getInputStream());
	 			                   String clientMsg;
	 			                   System.out.println("Before");
	 			                   clientMsg = dis.readUTF();//read from the client
	 			                   chatArea.append("\n"+clientMsg);
	 			                   System.out.println("B says "+clientMsg);
	 			                   if (clientMsg.equalsIgnoreCase("Bye")) {
	 			                        break;
	 			                    }
	 			                   System.out.println("after");

	 				               dis.close();
	 			               }
	 			             
	 			               c.close();
	 			           

	 			       } catch (Exception e) {
	 			           System.out.println(e.getMessage());
	 			       }
	 			
	 		}  
	   }

	   
	   

}
