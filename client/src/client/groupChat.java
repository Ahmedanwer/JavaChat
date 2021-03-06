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
	   private JTextArea writingArea;
	   private JTextArea chatArea;
	   JButton sendButton;
	   int groupID;
	   Group receiverGroup;
	   int userID;
	   String userName;
	   String serverIP ;
	   
	   
	   
	   public groupChat()
	       {

		   }

	   
	   public groupChat(Group group,User user,String serverIP)
	   
	       {
		      receiverGroup=group;
		      groupID=group.getId();
		      userID=user.getId();
		      userName=user.getUsername();
		      this.serverIP =serverIP;
		      prepareGUI(); 
		     
		  
	          }
	   
		      
	
	   public void setTextofChat(String Messege){
			 if(this.chatArea.getText()==""){
				 this.chatArea.append(Messege);
			 }else{
				 this.chatArea.append("\n"+Messege);
			 }
			
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
		            	
		            	//apiFunctions.connect(serverIP);
		            	apiFunctions.BCMsgToGroup(writingArea.getText(), userID+"", groupID+"");
		  		        
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
			 myPanel.add(sendButton,gBC); 
			   
		     
		      mainFrame.add(myPanel);
		     // mainFrame.add(Box.createRigidArea(new Dimension(5,0)));
		    
		     
		      mainFrame.setVisible(true);  
		   }
	   
	   
	  

	   
	   

}
