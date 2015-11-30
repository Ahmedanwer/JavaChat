package client;

import java.awt.FlowLayout;
import java.awt.GridLayout;
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
		users.add(new User(1,"hamada",1));
		users.add(new User(2,"Mohsen",1));
		users.add(new User(3,"Sarhan",1));
		users.add(new User(4,"Hussien",1));
		users.add(new User(5,"Ashraf",1));
		groups=new ArrayList<Group>();
		groups.add(new Group(1,"ASU"));
		groups.add(new Group(2,"BUE"));
		groups.add(new Group(3,"AUC"));
		groups.add(new Group(4,"GUC"));
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
	    	  Contacts.add(contact);
	      }
	      
	      JPanel Groups=new JPanel();
	      Groups.setLayout(new GridLayout(0,1));
	      for(int i=0;i<groups.size();i++){
	    	  JButton group=new JButton(groups.get(i).getGroupName());
	    	  Groups.add(group);
	      }
	      
	      mainFrame.add(Contacts);
	      mainFrame.add(Groups);
	      
	      mainFrame.setVisible(true);  
	 }
}
