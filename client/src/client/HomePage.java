package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class HomePage {
	  private JFrame mainFrame;
		
	HomePage(ArrayList<User> users, ArrayList<Group> groups, ArrayList<Group> mygroups){
	     prepareGUI( users,  groups,  mygroups);     
	}
	 private void prepareGUI(ArrayList<User> users, ArrayList<Group> groups, ArrayList<Group> mygroups){
	      mainFrame = new JFrame("Home Page");
	      mainFrame.setLayout(new FlowLayout());
	      mainFrame.setSize(600,400);
	      
	   
	 }
}
