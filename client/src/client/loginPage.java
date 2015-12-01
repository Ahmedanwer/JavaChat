package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.Socket;

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
              
		      
		      loginButton.addActionListener(new ActionListener() {
		    	
		            public void actionPerformed(ActionEvent e)
		            {
		            	
		            	SendUserData(username.getText(),password.getText(),ipAddress.getText());
		            	perpareData( UserID);
		            	
		            	//username.setText("");
		            	
		            }
		        });   
		      
		      mainFrame.add(myPanel);
		      mainFrame.setVisible(true);  
		      
	   }
	   
	   public String SendUserData(String username,String password, String ip){
		   
		 

		      try {
		      
		          //1.Create Client Socket and connect to the server
		         
		          apiFunctions login = new apiFunctions(username,password,ip);
		          UserID= apiFunctions.login(username, password);
		    
		          //dos.writeUTF(Message);
		          
		         
		         // System.out.print(username+ " "+ password);

		          //4.Close/release resources
		          
		         // dos.close();
		         // otherClient.close();
		        

		      } catch (Exception e) {
		          System.out.println(e.getMessage());
		          
		      }
		      return UserID;
		     
		  }
	   
	   public void perpareData(String id)
	   {
		   
	   }
		}


