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

import org.json.simple.JSONObject;

public class peerTopeer {
	  private JFrame mainFrame;
		
	  private JTextArea writingArea;
	  private JTextArea ChatArea;
	  private  JButton sendButton;
	   String otherPairIP;
	   User ReciverUser;
	   User thisUser;
	   peerTopeer ThisPeer;
	 
	   
	   public peerTopeer(User Reciver,User Sender){
		   ReciverUser=Reciver;
		   thisUser=Sender;
		   otherPairIP=Reciver.getIP();
		      prepareGUI();     
		      ThisPeer=this;
		     
		   }
	   private void prepareGUI(){
		      mainFrame = new JFrame("Chatting with "+(ReciverUser!=null?ReciverUser.getUsername():"Your Self"));
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
		            	SendMessege(ThisPeer.writingArea.getText());
		            	writingArea.setText("");
		            	
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



 public void setTextofChat(String Messege){
	 if(ThisPeer.ChatArea.getText()==""){
		 ThisPeer.ChatArea.append(Messege);
	 }else{
		 ThisPeer.ChatArea.append("\n"+Messege);
	 }
	
 }
  public void SendMessege(String Messege){

      try {
      
          //1.Create Client Socket and connect to the server
          Socket otherClient = new Socket(otherPairIP, 1256);
          //2.if accepted create IO streams
          DataOutputStream dos = new DataOutputStream(otherClient.getOutputStream());
                         //Create a Scanner to read inputs from the user
         
          JSONObject obj = new JSONObject();
       
          obj.put("senderID", thisUser.getId());
          obj.put("messege", Messege);
          dos.writeUTF(obj.toJSONString());
          
          ThisPeer.ChatArea.append("\n"+"You :"+Messege);

          //4.Close/release resources
          
          dos.close();
          otherClient.close();

      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
  }
}
