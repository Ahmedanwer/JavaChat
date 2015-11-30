package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class peerTopeer {
	  private JFrame mainFrame;
		
	   static JTextArea writingArea;
	   static JTextArea ChatArea;
	   JButton sendButton;
	   String otherPairIP = "192.168.43.64";
	 
	   public peerTopeer(){
		      prepareGUI();     
		      receiver myReceiver = new receiver();
		      myReceiver.start();
		   }
	   private void prepareGUI(){
		      mainFrame = new JFrame("Java SWING Examples");
		      mainFrame.setLayout(new FlowLayout());
		      mainFrame.setSize(410,410);
		    
		     
		      JPanel myPanel=new JPanel(); 
		      
		      myPanel.setLayout(new GridBagLayout());
		      myPanel.setSize(400,400);
		      
		            
		      writingArea = new JTextArea();
		      writingArea.setLineWrap(true);
		      writingArea.setWrapStyleWord(true);
		      writingArea.setPreferredSize(new Dimension(300,30));
		   
		      
		      
		      
		      
		      ChatArea = new JTextArea();
		      ChatArea.setLineWrap(false);
		      ChatArea.setEditable(false);
		      ChatArea.setPreferredSize(new Dimension(390,370));
		      ChatArea.setWrapStyleWord(false);
		     
		      JScrollPane scroll = new JScrollPane (ChatArea, 
		    		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		      
		    
		   
		      
		      sendButton = new JButton("Send");

		      
		      sendButton.addActionListener(new ActionListener() {
		    	
		            public void actionPerformed(ActionEvent e)
		            {
		            	
		            	// System.out.println(c.writingArea.getText());
		            	SendMessege(writingArea.getText());
		            	writingArea.setText("");
		            	
		            }
		        });      		    
		      
		      mainFrame.addWindowListener(new WindowAdapter() {
		         public void windowClosing(WindowEvent windowEvent){
			        System.exit(0);
		         }        
		      });    
		     
		      GridBagConstraints gBC = new GridBagConstraints();
		      gBC.fill = GridBagConstraints.HORIZONTAL;
		     
		      gBC.gridx = 0;
		       gBC.gridy = 0;
		       gBC.gridwidth = 2;
		      myPanel.add(scroll,gBC);
		      gBC.gridwidth = 1;
		      gBC.gridx = 0;
		       gBC.gridy = 1;
		       
		      myPanel.add(writingArea,gBC); 
		      gBC.gridx = 1;
		       gBC.gridy = 1;
			  myPanel.add(sendButton,gBC); ;
			   
		     
		      mainFrame.add(myPanel);
		     // mainFrame.add(Box.createRigidArea(new Dimension(5,0)));
		    
		     
		      mainFrame.setVisible(true);  
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
			                   String clientMsg;
			                   System.out.println("Before");
			                   clientMsg = dis.readUTF();//read from the client
			                   ChatArea.append("\n"+clientMsg);
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
  
  public void SendMessege(String Messege){

      try {
      
          //1.Create Client Socket and connect to the server
          Socket otherClient = new Socket(otherPairIP, 1243);
          //2.if accepted create IO streams
          DataOutputStream dos = new DataOutputStream(otherClient.getOutputStream());
                         //Create a Scanner to read inputs from the user
         
    
          dos.writeUTF(Messege);
          
          ChatArea.append("\n"+"You :"+Messege);

          //4.Close/release resources
          
          dos.close();
          otherClient.close();

      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
  }
}
