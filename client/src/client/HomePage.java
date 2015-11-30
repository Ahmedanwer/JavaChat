package client;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class HomePage {
	  private JFrame mainFrame;
		
	HomePage(){
	     prepareGUI();     
	}
	 private void prepareGUI(){
	      mainFrame = new JFrame("Home Page");
	      mainFrame.setLayout(new FlowLayout());
	      mainFrame.setSize(600,400);
	      
	      mainFrame.setVisible(true);  
	 }
}
