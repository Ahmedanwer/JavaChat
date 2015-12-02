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

import server.groupRecords.groupRecord;

public class clientThread extends Thread {

	Socket c;
	ArrayList<Socket> activeGroupClients;
	
	public clientThread(Socket clinetArrived) {
		this.c = clinetArrived;
		this.activeGroupClients=server.activeClients;
	}

	public Group getGroupByID (int x){
		
		for (Group temp : server.groups) {
			if (temp.getId()==x) return temp;
		} return null;
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
            System.out.println(clientMsg);
            JSONObject obj=(JSONObject) JSONValue.parse(clientMsg);
            System.out.println("header of msg recevied " +obj.get("header").toString());
            
            if (clientMsg.equalsIgnoreCase("Bye")) {
            	
                break;
            }
            
            else if (obj.get("header").toString().equalsIgnoreCase("BCM")) {
            	
            	System.out.println("if conditon of bcm enterd");
            	clientMsg = obj.get("msg").toString();
            	System.out.println("recevied msg and commencing BC-ing "+clientMsg);
            	System.out.println("Sender IP = "+obj.get("SenderIP").toString());
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
            			server.users.get(j).setStatus(1);
            			server.users.get(j).setIP(c.getLocalAddress().toString().substring(1));
            			System.out.println( "ip of connected client is "+c.getLocalAddress().toString().substring(1));
            			break;
            		}
            	}
            	 dos.writeUTF(id);
            }
            
            else if (obj.get("header").toString().equalsIgnoreCase("getallusers")){
            	
            	Gson gson = new Gson();
     		   String json = gson.toJson(server.users);
     		   dos.writeUTF(json);
            }
            
            else if (obj.get("header").toString().equalsIgnoreCase("getallgroups")){
            	
            	Gson gson = new Gson();
     		    String json = gson.toJson(server.groups);
    		    dos.writeUTF(json);
            }
            
            else if (obj.get("header").toString().equalsIgnoreCase("getmygroups")){
            	
            	ArrayList<Group> myGroups =new ArrayList<Group>();
            	int userID=Integer.parseInt((String) obj.get("id".toString()));
            	System.out.println(String.valueOf(userID));
            	for (groupRecord temp : server.records){
            		if (temp.getUserID()==userID) myGroups.add(getGroupByID(temp.getGroupID()));
            	}
            	System.out.println("grorup size "+ myGroups.size());
            	
            	Gson gson = new Gson();
     		    String json = gson.toJson(myGroups);
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
