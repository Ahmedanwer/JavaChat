package client;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class HomePage {
	  private JFrame mainFrame;
	  ArrayList<User> users;
	  ArrayList<Group> groups;
	  ArrayList<Group> myGroups;
	  ArrayList<groupRecords> records;
	  HashMap<Integer,peerTopeer> PeerChatWindows;
	  JPanel Contacts;
	  JPanel Groups;
	  User ThisUser;
	  String serverIP ;




	  public HomePage(ArrayList<User> users, ArrayList<Group> groups, ArrayList<Group> myGroups,User thisUser,String serverIP) {

			super();
			this.users = users;
			this.groups = groups;
			this.myGroups = myGroups;

			this.ThisUser =thisUser;
	
			this.serverIP = serverIP;


		
		receiver myReceiver = new receiver();
	      myReceiver.start();
	      refresh Refresher= new refresh();
	      Refresher.start();
	   
		
		 

		PeerChatWindows=new   HashMap<Integer,peerTopeer>();
		
	     prepareGUI(users,groups,myGroups);     

	}
	  
public void updateContacts(){
	
	 for(int i=0;i<users.size();i++){
   	  JButton contact=new JButton(users.get(i).getUsername());
   	  
   	  final User SendTo=users.get(i);
   	
   	  if(SendTo.getStatus()==0){
   		  contact.setEnabled(false);
   	  }
   	  System.out.println(SendTo.getUsername()+" "+SendTo.getStatus());
   	  
   	  contact.addActionListener(new ActionListener() {
		    	
	            public void actionPerformed(ActionEvent e)
	            {
	            	
	            	peerTopeer newChat =new peerTopeer(SendTo,ThisUser);
	            	PeerChatWindows.put(SendTo.getId(), newChat);
	            	
	            }
	        });     
   	  
   	  Contacts.add(contact);
     }
	
}

	
	 private void prepareGUI(ArrayList<User> users, ArrayList<Group> groups, ArrayList<Group> myGroups){
	      mainFrame = new JFrame("Home Page");
	      mainFrame.setLayout(new GridLayout(1,2));
	      mainFrame.setSize(600,400);
	      
	      Contacts=new JPanel();
	      Contacts.setLayout(new GridLayout(0,1));
	     
	      updateContacts();
	      
	      
	      Groups=new JPanel();
	      Groups.setLayout(new GridLayout(0,1));
	      JButton leaveGroup = new JButton("Leave Group");

		  JButton JoinGroup = new JButton("Join Group");
	      for(int i=0;i<groups.size();i++){
	    	  JPanel groupPanel=new JPanel();

	    	  final Group thisGroup=groups.get(i);

	    	  JButton group=new JButton(groups.get(i).getGroupName());

	    	  
	    	  final Group thisGroup2 = groups.get(i);
	    	  group.addActionListener(new ActionListener()
	    			  {
	    		  public void actionPerformed(ActionEvent e)
	    		  {
	    			  groupChat newChat = new groupChat(thisGroup2,ThisUser,serverIP);
	    			  
	    		  }
	    			  });
	    	  groupPanel.add(group);
	    	 

	    	 
	    	  for (Group g : myGroups){
					if(g.getId()==groups.get(i).getId())
					{  
						 groupPanel.add(leaveGroup);
						}
					 else{
							 groupPanel.add(JoinGroup);
							 group.setEnabled(false);
			    	  }
					}
	    	 
	    		
	    	  Groups.add(groupPanel);

	      }
	      
	      JoinGroup.addActionListener(new ActionListener() {
		    	
	            public void actionPerformed(ActionEvent e)
	            {
	            	
	            	//apiFunctions.enrollInAGroup(groupid, myData.getId());
	            	
	            }
	        }); 
	      
	  
	      
	      mainFrame.add(Contacts);
	      mainFrame.add(Groups);
	      
	      mainFrame.setVisible(true);  
	 }
	 public User GetUserByID(int id){
		 for(int i=0;i<users.size();i++){
			 if(users.get(i).getId()==id){
				 return users.get(i);
			 }
		 }
		 return null;
	 }
	 

	 private class refresh extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
		
			
			try {
			while(true){	
				sleep(5000);
				Contacts.removeAll();
				Contacts.revalidate();
				
				users= apiFunctions.getAllUsers();
				updateContacts();
				
				 mainFrame.add(Contacts);
			      mainFrame.add(Groups);
			      mainFrame.revalidate();
					mainFrame.repaint();
			      mainFrame.setVisible(true);
				
				System.out.println("refresh");
				
			}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		 
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
				           ServerSocket mySocket = new ServerSocket(1256);
				           //Server is always On
				          
				          
				        	   Socket c;
					        
				               //4.Perform IO Operations with the client
				               while (true) {
				            	   c = mySocket.accept();       
					               DataInputStream dis = new DataInputStream(c.getInputStream());
				                   
				                   System.out.println("Before");
				                   
				                   String JsonMessege;
				                   String clientMsg;
				                   JsonMessege = dis.readUTF();//read from the client
				                   JSONObject obj=(JSONObject) JSONValue.parse(JsonMessege);  
				                   //HERE i want to know who is the sender 
				                 
				                   int SenderID=Integer.valueOf(obj.get("senderID").toString());
				                   clientMsg=obj.get("messege").toString();
				                   
				                   if(PeerChatWindows.containsKey(SenderID)){
				                   PeerChatWindows.get(SenderID).setTextofChat(clientMsg);
				                   }else{
				                	
				                	peerTopeer newChat =new peerTopeer(GetUserByID(SenderID),ThisUser);
						            PeerChatWindows.put(SenderID, newChat);   
				                   }
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