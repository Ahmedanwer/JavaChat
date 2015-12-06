package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

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
	
	
	
	public ArrayList<String> getAllUsersIDInAGroup (String groupid){
		ArrayList<String> groupUsers=new ArrayList<String>();
		for (groupRecord temp : server.records){
    		if (temp.getGroupID()==Integer.parseInt(groupid)) groupUsers.add(String.valueOf(temp.getUserID()));
    	}
		return groupUsers;
	}

	
	
	public ArrayList<User> getAllUsersObjectInAGroup (String groupid){
		
		ArrayList<String> usersID=new ArrayList<String>();
		ArrayList<User> usersObj=new ArrayList<User>();
		usersID=getAllUsersIDInAGroup(groupid);
		
		for (String id :usersID){
			for (User temp:server.users){
				if (id.equalsIgnoreCase(String.valueOf(temp.getId())));
				usersObj.add(temp);
			}
		}
		return usersObj;
	}
	
	
	
	
	@Override
	public void run() {
		try{
			
        
        //3.Create IO Streams
        DataOutputStream dos = new DataOutputStream(c.getOutputStream());
        DataInputStream dis = new DataInputStream(c.getInputStream());
        
        
        while (true) {
            String clientMsg; 
            clientMsg = dis.readUTF();//read from the client
            System.out.println(clientMsg);
            JSONObject obj=(JSONObject) JSONValue.parse(clientMsg);
            System.out.println("header of msg recevied " +obj.get("header").toString());
            
            if (clientMsg.equalsIgnoreCase("Bye")) { break; }
            
            
            
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
            	String userName, password,ipAdress;
            	userName = obj.get("username").toString();
            	password = obj.get("password").toString();
            	ipAdress=obj.get("ipAdress").toString();
            	//System.out.println("User NAme Received: "+userName+" & Password: "+password);
            	String id="0";
            	for (int j=0;j<server.users.size();j++)
            	{
            		if (server.users.get(j).getUsername().equals(userName) && server.users.get(j).getPassword().equals(password)){
            			id= String.valueOf(server.users.get(j).getId());
            			System.out.println("id sent to client is (inner for loop"+id);
                    	
            			server.users.get(j).setStatus(1);
            			server.users.get(j).setIP(c.getRemoteSocketAddress().toString());
            			System.out.println(c.getRemoteSocketAddress().toString());
            			server.activeLoggedInClients.put(id, c);
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
            	int userID=Integer.parseInt(obj.get("id").toString());
            	System.out.println(String.valueOf(userID));
            	for (groupRecord temp : server.records){
            		if (temp.getUserID()==userID) myGroups.add(getGroupByID(temp.getGroupID()));
            	}
            	System.out.println("grorup size "+ myGroups.size());
            	
            	Gson gson = new Gson();
     		    String json = gson.toJson(myGroups);
    		    dos.writeUTF(json);
            }
            
            
            
            else if (obj.get("header").toString().equalsIgnoreCase("BCMtoGroup")){
            	
	            String group=obj.get("groupID").toString();
	            String userID =obj.get("SenderID").toString();
	            String BCMsg=obj.get("msg").toString();
	            for (String key : getAllUsersIDInAGroup(group)){
	            	Socket  value =server.activeLoggedInClients.get(key);
	            	if (value != null){
	            		DataOutputStream Cdos = new DataOutputStream(value.getOutputStream());
	            		Cdos.writeUTF(BCMsg);
	            	}
	            }
            }
            
            
            
            else if (obj.get("header").toString().equalsIgnoreCase("getMembersOfGroup")){
            
            	String group=obj.get("groupID").toString();
        
            	ArrayList<User> returnUsers=new ArrayList<User>();
            	returnUsers=getAllUsersObjectInAGroup(group);
            	
            	Gson gson = new Gson();
     		    String json = gson.toJson(returnUsers);
    		    dos.writeUTF(json);
            }
            
            
         
            else if (obj.get("header").toString().equalsIgnoreCase("enroll")){
                
            	String group=obj.get("groupID").toString();
            	String user=obj.get("userID").toString();
            	
            	Boolean userExist=false;
            	Boolean groupExist=false;
            	Boolean useralreadyEnrolled=false;
                	
            	String code="0";
            	String msg="Enrollment Failed";
            	
            	for (User temp :server.users){
            		if ( String.valueOf(temp.getId()).equals(user) ) userExist=true;
            	}
            	
            	for (Group temp :server.groups){
            		if ( String.valueOf(temp.getId()).equals(group) ) groupExist=true;
            	}
            	
            	for (groupRecord temp: server.records){
            		if ( ( String.valueOf(temp.getGroupID()).equals(group)) && ( String.valueOf(temp.getUserID()).equals(group)) ) useralreadyEnrolled=true;
            	}
            	
            	if (userExist && groupExist && !useralreadyEnrolled){
            		groupRecords temp = new groupRecords();
            		groupRecord temp2;
            		 temp2 = temp.new groupRecord(Integer.parseInt(group) , Integer.parseInt(user));
            	
            		server.records.add(temp2);
            		code="1";msg="Enrollment Successful";
            	}
            	
            	if ( (!(userExist)) || (!(groupExist)) ) {
            		code="3";msg="User Or Group Doesn't Exist";
            	}
            	
            	if (userExist && groupExist && useralreadyEnrolled ){
            		code="2";msg="User Already Enrolled in Group of ID: "+group;
            	}
            	
            	JSONObject responseObj=new JSONObject();
     		    responseObj.put("code", code);
     		    responseObj.put("msg", msg);
     			   
     		    dos.writeUTF(responseObj.toJSONString());
            }
          
            
            else if (obj.get("header").toString().equalsIgnoreCase("leave")){
                
            	String group=obj.get("groupID").toString();
            	String user=obj.get("userID").toString();
            	
            	int recordIndex=0;
            	
            	Boolean userExist=false;
            	Boolean groupExist=false;
            	Boolean useralreadyEnrolled=false;
                	
            	String code="0";
            	String msg="Leaving Group Failed";
            	
            	for (User temp :server.users){
            		if ( String.valueOf(temp.getId()).equals(user) ) userExist=true;
            	}
            	
            	for (Group temp :server.groups){
            		if ( String.valueOf(temp.getId()).equals(group) ) groupExist=true;
            	}
            	
            	for (groupRecord temp: server.records){
            		if ( ( String.valueOf(temp.getGroupID()).equals(group)) && ( String.valueOf(temp.getUserID()).equals(group)) ) {
            			useralreadyEnrolled=true;
            			recordIndex=server.records.indexOf(temp);
            			}
            	}
            	
            	if (userExist && groupExist && !useralreadyEnrolled){
            		code="2";msg="User Not Enrolled in this Group";
            	}
            	
            	if ( (!(userExist)) || (!(groupExist)) ) {
            		code="3";msg="User Or Group Doesn't Exist";
            	}
            	
            	if (userExist && groupExist && useralreadyEnrolled ){
            		server.records.remove(recordIndex);
            		code="1";msg="Leaving Group Was Successful";
            	}
            	
            	JSONObject responseObj=new JSONObject();
     		    responseObj.put("code", code);
     		    responseObj.put("msg", msg);
     			   
     		    dos.writeUTF(responseObj.toJSONString());
            }
            
            
            
            else if (obj.get("header").toString().equalsIgnoreCase("getmyip")){
            	
            	String user =obj.get("userid").toString();
    		    dos.writeUTF(c.getLocalAddress().toString().substring(1));
            }
            
            
            
            else if (obj.get("header").toString().equalsIgnoreCase("getotherip")){
            	
            	String user =obj.get("userid").toString();	
    		    dos.writeUTF(server.activeLoggedInClients.get(user).getLocalAddress().toString().substring(1));
            }
            
            
            else if (obj.get("header").toString().equalsIgnoreCase("kick")){
                
            	String group=obj.get("groupID").toString();
            	String user=obj.get("userID").toString();
            	
            	int recordIndex=0;
            	
            	Boolean userExist=false;
            	Boolean groupExist=false;
            	Boolean useralreadyEnrolled=false;
                	
            	String code="0";
            	String msg="Kicking User Failed";
            	
            	for (User temp :server.users){
            		if ( String.valueOf(temp.getId()).equals(user) ) userExist=true;
            	}
            	
            	for (Group temp :server.groups){
            		if ( String.valueOf(temp.getId()).equals(group) ) groupExist=true;
            	}
            	
            	for (groupRecord temp: server.records){
            		if ( ( String.valueOf(temp.getGroupID()).equals(group)) && ( String.valueOf(temp.getUserID()).equals(group)) ) {
            			useralreadyEnrolled=true;
            			recordIndex=server.records.indexOf(temp);
            			}
            	}
            	
            	if (userExist && groupExist && !useralreadyEnrolled){
            		code="2";msg="User Not Enrolled in this Group";
            	}
            	
            	if ( (!(userExist)) || (!(groupExist)) ) {
            		code="3";msg="User Or Group Doesn't Exist";
            	}
            	
            	if (userExist && groupExist && useralreadyEnrolled ){
            		server.records.remove(recordIndex);
            		code="1";msg="Kicking User From Group Was Successful";
            	}
            	
            	JSONObject responseObj=new JSONObject();
     		    responseObj.put("code", code);
     		    responseObj.put("msg", msg);
     			   
     		    dos.writeUTF(responseObj.toJSONString());
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
