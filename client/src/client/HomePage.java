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
import javax.swing.JOptionPane;
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
	  HashMap<Integer,groupChat> groupChatWinsows;
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

			System.out.println("this user is"+ThisUser.getUsername());
		
		receiver myReceiver = new receiver();
	      myReceiver.start();
	      refresh Refresher= new refresh();
	      Refresher.start();
	   
	      Groupreceiver myGroupreceiver = new Groupreceiver();
	      myGroupreceiver.start();
		 

		PeerChatWindows=new   HashMap<Integer,peerTopeer>();

		groupChatWinsows=new   HashMap<Integer,groupChat>();
		
	     prepareGUI(users,groups,myGroups);     

	}
	  
public void updateContacts(){
	
	 for(int i=0;i<users.size();i++){
   	  JButton contact=new JButton(users.get(i).getUsername());
   	  
   	  final User SendTo=users.get(i);
   	
   	  if(SendTo.getStatus()==0){
   		  contact.setEnabled(false);
   	  }
   	//  System.out.println(SendTo.getUsername()+" "+SendTo.getStatus());
   	  
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
public void prepareGroups(){
	
	

	 for(int i=0;i<groups.size();i++){
   	  JPanel groupPanel=new JPanel();

   	  
   	  JButton leaveGroup = new JButton("Leave Group");
   	  JButton JoinGroup = new JButton("Join Group");

   	  JButton group=new JButton(groups.get(i).getGroupName());

   	  
   	  final Group thisGroup = groups.get(i);
   	  
   	  group.addActionListener(new ActionListener()
   			  {
   		  public void actionPerformed(ActionEvent e)
   		  {
   			  
   			  groupChat newChat = new groupChat(thisGroup,ThisUser,serverIP);
   			  groupChatWinsows.put(thisGroup.getId(), newChat);
   		  }
   			  });
   	

    	 groupPanel.add(group);
    	 
   	  if(isJoind(thisGroup.getId())){
   		 groupPanel.add(leaveGroup); 
   		group.setEnabled(true);
   		leaveGroup.addActionListener(new ActionListener() {
	    	
            public void actionPerformed(ActionEvent e)
            {
            
            String resp=apiFunctions.leaveGroup(String.valueOf(thisGroup.getId()), String.valueOf(ThisUser.getId()));	
            if (resp.equalsIgnoreCase("0"))	JOptionPane.showMessageDialog(null, "Leaving Group Failed", "Alert", JOptionPane.INFORMATION_MESSAGE);
            else if (resp.equalsIgnoreCase("1"))	JOptionPane.showMessageDialog(null, "You Successfuly Left The Group", "Alert", JOptionPane.INFORMATION_MESSAGE);
            else if (resp.equalsIgnoreCase("2"))	JOptionPane.showMessageDialog(null, "You Are Not Enrolled In This Group", "Alert", JOptionPane.INFORMATION_MESSAGE);
            else if (resp.equalsIgnoreCase("3"))	JOptionPane.showMessageDialog(null, "User ID Or Group Doesnt Exist", "Alert", JOptionPane.INFORMATION_MESSAGE);
           // RefreshGroups();
            apiFunctions.update();
            }
   			}); 
   	  }else{
   		 groupPanel.add(JoinGroup);
   		 JoinGroup.addActionListener(new ActionListener() {
		    	
	            public void actionPerformed(ActionEvent e)
	            {
	            
	            String resp=apiFunctions.enrollInAGroup(String.valueOf(thisGroup.getId()), String.valueOf(ThisUser.getId()));	
	            if (resp.equalsIgnoreCase("0"))	JOptionPane.showMessageDialog(null, "Enroll In Group Failed", "Alert", JOptionPane.INFORMATION_MESSAGE);
	            else if (resp.equalsIgnoreCase("1"))	JOptionPane.showMessageDialog(null, "You Successfuly Joind The Group", "Alert", JOptionPane.INFORMATION_MESSAGE);
	            else if (resp.equalsIgnoreCase("2"))	JOptionPane.showMessageDialog(null, "You Are Already Enrolled", "Alert", JOptionPane.INFORMATION_MESSAGE);
	            else if (resp.equalsIgnoreCase("3"))	JOptionPane.showMessageDialog(null, "User ID Or Group Doesnt Exist", "Alert", JOptionPane.INFORMATION_MESSAGE);
	        //    RefreshGroups();
	            apiFunctions.update();
	            }
	        }); 
	      
   		 
   		  group.setEnabled(false);
   	  }
   	  
   	  
   	  Groups.add(groupPanel);

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
	
	      prepareGroups();
	      
	  
	      
	      mainFrame.add(Contacts);
	      mainFrame.add(Groups);
	      
	      mainFrame.setVisible(true);  
	 }
	 public Boolean isJoind(int groupID){
		 for(int i=0;i<myGroups.size();i++){
			 if(myGroups.get(i).getId()==groupID){
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 public User GetUserByID(int id){
		 for(int i=0;i<users.size();i++){
			 if(users.get(i).getId()==id){
				 return users.get(i);
			 }
		 }
		 return null;
	 }
	 

	 private void RefreshGroups(){
		 
		 	System.out.println("before in refreshGroup: "+myGroups);
			Groups.removeAll();
			Groups.revalidate();
			
			myGroups= apiFunctions.getMyGroups(ThisUser.getId()+"");
		 	System.out.println("after in refreshGroup: "+myGroups);
			prepareGroups();
			
			 mainFrame.add(Contacts);
		      mainFrame.add(Groups);
		      mainFrame.revalidate();
				mainFrame.repaint();
		 
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
				Groups.removeAll();
				Groups.revalidate();
				
				users= apiFunctions.getAllUsers();
				updateContacts();
				myGroups= apiFunctions.getMyGroups(ThisUser.getId()+"");
			 	System.out.println("after in refreshGroup: "+myGroups);
				prepareGroups();
				
				 mainFrame.add(Contacts);
			      mainFrame.add(Groups);
			      mainFrame.revalidate();
					mainFrame.repaint();
			    //  mainFrame.setVisible(true);
			
				
				
			}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		 
	 }

	 
	 private class Groupreceiver extends Thread{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				
				try
				  {
					while(true){
					
					String incomingMsg=apiFunctions.Sdis.readUTF();
					 JSONObject obj=(JSONObject) JSONValue.parse(incomingMsg);  
	                   //HERE i want to know who is the sender 
	                 
	                   int GroupID=Integer.valueOf(obj.get("GroupID").toString());
	                 String  Messge=obj.get("msg").toString();
	                   
	                   if(groupChatWinsows.containsKey(GroupID)){
	                	  groupChatWinsows.get(GroupID).setTextofChat(Messge);
	                   }else{
	                	
	                // TODO open newGroupHere
	                   }
					
					
					
					//chatArea.append(+"\r\n");
					}
				  }
				 catch (Exception e) 
				
				   {
			           System.out.println(e.getMessage());
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
				                	newChat.setTextofChat(clientMsg);
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