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
	  User ThisUser;

	  User myData = new User(0,"","",0,"");
	  
	


	  public HomePage(ArrayList<User> users, ArrayList<Group> groups, ArrayList<Group> myGroups, String UserID) {
			super();
			this.users = users;
			this.groups = groups;
			this.myGroups = myGroups;

			for (User temp : users){
				if(temp.getId()== Integer.parseInt(UserID))
				{   myData.setId(Integer.parseInt(UserID));
					myData.setUsername(temp.getUsername());
					myData.setPassword(temp.getPassword());
					myData.setIP(temp.getIP());
					myData.setStatus(temp.getStatus());
					break;
				}

			}
			
			//System.out.println(myData.getUsername());

		
		receiver myReceiver = new receiver();
	      myReceiver.start();
	      
		
		 
		//users=new ArrayList<User>();
		PeerChatWindows=new   HashMap<Integer,peerTopeer>();
		
		
/*
		 ThisUser=new User(1,"Anwar",1);
		 ThisUser.setIP("192.168.1.19");
		User Som3a=new User(2,"Sherouk ",1);
		Som3a.setIP("192.168.1.21");
		 
		
		users.add(ThisUser);
		users.add(Som3a);
		*/
		
		
		
		
		
	     prepareGUI(users,groups,myGroups);     
	}
	
	 private void prepareGUI(ArrayList<User> users, ArrayList<Group> groups, ArrayList<Group> myGroups){
	      mainFrame = new JFrame("Home Page");
	      mainFrame.setLayout(new GridLayout(1,2));
	      mainFrame.setSize(600,400);
	      
	      JPanel Contacts=new JPanel();
	      Contacts.setLayout(new GridLayout(0,1));
	      for(int i=0;i<users.size();i++){
	    	  JButton contact=new JButton(users.get(i).getUsername());
	    	  
	    	  final User thisUser=users.get(i);
	    	  contact.addActionListener(new ActionListener() {
			    	
		            public void actionPerformed(ActionEvent e)
		            {
		            	
		            	peerTopeer newChat =new peerTopeer(thisUser,ThisUser);
		            	PeerChatWindows.put(thisUser.getId(), newChat);
		            	
		            }
		        });     
	    	  
	    	  Contacts.add(contact);
	      }
	      
	      JPanel Groups=new JPanel();
	      Groups.setLayout(new GridLayout(0,1));
	      JButton leaveGroup = new JButton("Leave Group");

		  JButton JoinGroup = new JButton("Join Group");
	      for(int i=0;i<groups.size();i++){
	    	  JPanel groupPanel=new JPanel();

	    	  final Group thisGroup=groups.get(i);

	    	  JButton group=new JButton(groups.get(i).getGroupName());
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
	      
	   /*   JPanel MyGroups=new JPanel();
	      MyGroups.setLayout(new GridLayout(0,1));
	      for(int k=0;k<myGroups.size();k++){
	    	  JPanel myGroup= new JPanel();
	    	  JButton mygroup=new JButton(myGroups.get(k).getGroupName());
	    	  myGroup.add(mygroup);
	    	  
	    	  for (Group g : myGroups){
					if(g.getId()==groups.get(k).getId())
					{
						 myGroup.add(leaveGroup);
						}
					}
	    	  MyGroups.add(mygroup);
	      }
	      */
	      
	      JButton createGroup = new JButton("Create Group");
	     // MyGroups.add(createGroup);
	      createGroup.addActionListener(new ActionListener() {
		    	
	            public void actionPerformed(ActionEvent e)
	            {
	            	
	            	
	            	
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