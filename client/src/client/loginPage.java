package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class loginPage {
	
	private JFrame mainFrame;
	
	   static JTextArea username;
	   static JTextArea password;
	   static JTextArea ipAddress;
	   JButton loginButton;
	   String UserID="";
	   ArrayList<User> users;
	   ArrayList<Group> allGroups;
	   ArrayList<Group> myGroups;
	   
	   public loginPage()
	   {
		   prepareGUI();
	   }
	   
	   private void prepareGUI(){
		      mainFrame = new JFrame("Login");
		      mainFrame.setSize(600,400);
		      
		      JPanel myPanel=new JPanel(); 
		      myPanel.setSize(400,400);
		      
		      username = new JTextArea();
		      username.setLineWrap(true);
		      username.setWrapStyleWord(true);
		      
		      
		      password = new JTextArea();
		      password.setLineWrap(true);
		      password.setWrapStyleWord(true);
		      
		      ipAddress = new JTextArea();
		      ipAddress.setLineWrap(true);
		      ipAddress.setWrapStyleWord(true);
		      
              loginButton = new JButton("Login");

              myPanel.add(username);
              myPanel.add(password);
              myPanel.add(ipAddress);
              myPanel.add(loginButton);
              
		      
		      
		      
		      mainFrame.add(myPanel);
		      mainFrame.setVisible(true);  
		      loginButton.addActionListener(new ActionListener() {
			    	
		            public void actionPerformed(ActionEvent e)
		            {
		            	
		            	SendUserData(username.getText(),password.getText(),ipAddress.getText());
		            	perpareUsersData( );
		            	prepareGroupsData();
		            	GetMyGroups(UserID);
		            	mainFrame.setVisible(false);
		            	User thisUser=new User(Integer.parseInt(UserID),username.getText(),1);
		            	try {
							thisUser.setIP(InetAddress.getLocalHost().toString());
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		            	new HomePage(users,allGroups,myGroups,thisUser,ipAddress.getText());

		            
		            	
		            }
		        });  
		      
	   }
	   
	   public String SendUserData(String username,String password, String ip){
		   
		 

		      try {
		      
		          //1.Create Client Socket and connect to the server
		         
		          apiFunctions login = new apiFunctions(username,password,ip);
		          UserID= apiFunctions.id;
		    
		          
		        

		      } catch (Exception e) {
		          System.out.println(e.getMessage());
		          
		      }
		      return UserID;
		     
		  }
	   
	   public void perpareUsersData()
	   {
		   users=apiFunctions.getAllUsers();
		   
	   }
	   
	   public void prepareGroupsData()
	   {
		   allGroups = apiFunctions.getAllGroups();
		   
	   }
	   
	   public void GetMyGroups(String userID)
	   {
		   myGroups= apiFunctions.getMyGroups(userID);
		   
	   }
		

}


