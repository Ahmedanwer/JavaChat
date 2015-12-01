package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.javafx.scene.paint.GradientUtils.Parser;

public class clientThread extends Thread {

	Socket c;
	ArrayList<Socket> activeGroupClients;
	
	public clientThread(Socket clinetArrived) {
		this.c = clinetArrived;
		this.activeGroupClients=server.activeClients;
	}


	@Override
	public void run() {
		try{
			
        
        //3.Create IO Streams
        DataOutputStream dos = new DataOutputStream(c.getOutputStream());
        DataInputStream dis = new DataInputStream(c.getInputStream());
        
        //say Hi
        //dos.writeUTF("Server: Welcome new user");
      
        
        //4.Perform IO Operations with the client
        while (true) {
            String clientMsg; 
            clientMsg = dis.readUTF();//read from the client
            JSONObject obj=(JSONObject) JSONValue.parse(clientMsg);
            //dos.writeUTF(clientMsg);//Echo the msg back to the client     
            
            if (clientMsg.equalsIgnoreCase("Bye")) {
            	
                break;
            }else if (obj.get("header").toString().equalsIgnoreCase("BCM")) {
            	
            	System.out.println("if conditon of bcm enterd");
            	clientMsg = obj.get("msg").toString();
            	System.out.println("recevied msg and commencing BC-ing "+clientMsg);
            	System.out.println("Sender IP = "+obj.get("senderIP").toString());
            	BCMsg CurrentBC= new BCMsg(obj.get("msg").toString());
            	CurrentBC.start();
            	}
            else if (obj.get("header").toString().equalsIgnoreCase("login")){
            	
            	System.out.println("if conditon of login enterd");
            	String userName, password;
            	userName = obj.get("username").toString();
            	password = obj.get("password").toString();
            	System.out.println("User NAme Received: "+userName+" & Password: "+password);
            	String id="0";
            	for (int j=0;j<server.users.size();j++)
            	{
            		if (server.users.get(j).getUsername().equals(userName) && server.users.get(j).getPassword().equals(password)){
            			id= String.valueOf(server.users.get(j).getId());
            			break;
            		}
            	}
            	 dos.writeUTF(id);
            }
            
            else if (obj.get("header").toString().equalsIgnoreCase("getallusers")){
            	
            	Gson gson = new Gson();

     		   // Serializing to a JSON element node
     		  // JsonElement jsonElement = gson.toJsonTree(server.users);
     		   //System.out.println(jsonElement.isJsonArray()); // true
     		   // Or, directly to JSON string
     		   String json = gson.toJson(server.users);
     		   System.out.println(json);
     		   dos.writeUTF(json);

            }
            
            
            else if (obj.get("header").toString().equalsIgnoreCase("getallgroups")){
            	
            	Gson gson = new Gson();

     		   // Serializing to a JSON element node
     		   //JsonElement jsonElement = gson.toJsonTree(server.groups);
     		   //System.out.println(jsonElement.isJsonArray()); // true
     		   // Or, directly to JSON string
     		   String json = gson.toJson(server.groups);
     		   System.out.println(json);
     		  dos.writeUTF(json);

            }

            
            else {
            	
            	dos.writeUTF("Server: please enter a valid command, available commands are: bye, bcm, and login");
            }
          }
        
        //5.Close/release resources
        dis.close();
        dos.close();
       
        c.close();
        System.out.println("Client Left");
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
		

	
	
	
}
