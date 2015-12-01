package client;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HomePage {
	  private JFrame mainFrame;
	  ArrayList<User> users;
	  ArrayList<Group> groups;
	  ArrayList<groupRecords> records;
	  
	HomePage(){
		users=new ArrayList<User>();
		
		
		User Som3a=new User(5,"Sherouk ",1);
		Som3a.setIP("192.168.1.21");
		User Anwar=new User(5,"Anwar",1);
		Anwar.setIP("192.168.1.19");
		
		users.add(Anwar);
		users.add(Som3a);
		users.add(new User(4,"Hussien",1));
		users.add(new User(5,"Ashraf",1));
		User YourSelf=new User(5,"yourSelf",1);
		YourSelf.setIP("localhost");
		users.add(YourSelf);
		
		groups=new ArrayList<Group>();
		groups.add(new Group(0,"ASU"));
		groups.add(new Group(1,"BUE"));
		groups.add(new Group(2,"AUC"));
		groups.add(new Group(3,"GUC"));
		
	     prepareGUI();     
	}
	 private void prepareGUI(){
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
		            	
		            	new peerTopeer(thisUser);
		            	
		            }
		            
		            
		        });     
	    	  
	    	  Contacts.add(contact);
	      }
	      
	      JPanel Groups=new JPanel();
	      Groups.setLayout(new GridLayout(0,1));
	      for(int i=0;i<groups.size();i++){
	    	  JButton group=new JButton(groups.get(i).getGroupName());
	    	  final Group thisGroup=groups.get(i);
	    	  group.addActionListener(new ActionListener()
	    			  {
	    		  public void actionPerformed(ActionEvent e)
		            {
		            	
		            	new groupChat(thisGroup);
		            	
		            }
	    		  
	    			  });
	    	  Groups.add(group);
	      }
	      
	      mainFrame.add(Contacts);
	      mainFrame.add(Groups);
	      
	      mainFrame.setVisible(true);  
	 }
}
