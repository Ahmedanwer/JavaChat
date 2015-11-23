package client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class login {

	 private JFrame mainFrame; 
	 private JLabel headerLabel;
	 
	 public login(){
		 prepareGUI();
	 }
	
	 private void prepareGUI(){
	      mainFrame = new JFrame("JavaChat");
	      mainFrame.setSize(400,400);
	      mainFrame.setLayout(new GridLayout(3, 1));
	      headerLabel = new JLabel("hamada",JLabel.CENTER );
	      mainFrame.add(headerLabel);
	      mainFrame.setVisible(true);  
	   }
	
}
