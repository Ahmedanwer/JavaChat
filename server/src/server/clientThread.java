package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.swing.internal.plaf.synth.resources.synth;

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
	
public User getUserByID (int x){
		
		for (User temp : server.users) {
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
           // System.out.println(clientMsg);
            JSONObject obj=(JSONObject) JSONValue.parse(clientMsg);
            //System.out.println("header of msg recevied " +obj.get("header").toString());
            
            if (clientMsg.equalsIgnoreCase("Bye")) { break; }
            
            
            
            else if (obj.get("header").toString().equalsIgnoreCase("BCM")) {
            	
            	//System.out.println("if conditon of bcm enterd");
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
            	
            	//System.out.println("User NAme Received: "+userName+" & Password: "+password);
            	String id="0";
            	for (int j=0;j<server.users.size();j++)
            	{
            		if (server.users.get(j).getUsername().equals(userName) && server.users.get(j).getPassword().equals(password)){
            			id= String.valueOf(server.users.get(j).getId());
            			//System.out.println("id sent to client is (inner for loop"+id);
                    	
            			server.users.get(j).setStatus(1);
            			
           
            			
            			server.users.get(j).setIP(c.getRemoteSocketAddress().toString().split(":")[0].substring(1));
            			//System.out.println("ip adress"+server.users.get(j).getIP());
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
            	//System.out.println(String.valueOf(userID));
            	for (groupRecord temp : server.records){
            		if (temp.getUserID()==userID) myGroups.add(getGroupByID(temp.getGroupID()));
            	}
            	
            	Gson gson = new Gson();
     		    String json = gson.toJson(myGroups);
    		    dos.writeUTF(json);
    		   // System.out.println(json);
            }
            
            
            
            else if (obj.get("header").toString().equalsIgnoreCase("BCMtoGroup")){
            	
	            String group=obj.get("groupID").toString();
	            String userID =obj.get("SenderID").toString();
	            String BCMsg=obj.get("msg").toString();
	            
	            System.out.println(clientMsg);
	            System.out.println("Bcing, all user of group :"+getAllUsersIDInAGroup(group));
            	System.out.println("BCing, all logged in users "+server.activeLoggedInClients);
            	
	            for (String key : getAllUsersIDInAGroup(group)){
	            	
	            	Socket  value =server.activeLoggedInClients.get(key);
	            	
	            	if (value != null){
	            		
	            		System.out.println("for this iteration, groupUser"+key+"value of socket"+value+" conditin evaluation "+(value != null));
	            		
	            		DataOutputStream Cdos = new DataOutputStream(value.getOutputStream());
	            		
	                 	JSONObject responseObj=new JSONObject();
	         		    responseObj.put("GroupID", group);
	         		    responseObj.put("msg", getUserByID(Integer.parseInt(userID)).getUsername()+" : "+ BCMsg);
	         			   
	         		    System.out.println(responseObj.toJSONString());
	         		    Cdos.writeUTF(responseObj.toJSONString());
	         		    
	            		
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
               //  System.out.println(clientMsg);
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
            		if ( ( String.valueOf(temp.getGroupID()).equals(group)) && ( String.valueOf(temp.getUserID()).equals(user)) ) useralreadyEnrolled=true;
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
            		if ( ( String.valueOf(temp.getGroupID()).equals(group)) && ( String.valueOf(temp.getUserID()).equals(user)) ) {
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
            		//System.out.println("before"+server.records);
            		server.records.remove(recordIndex);
            		//System.out.println("before"+server.records);
            		code="1";msg="Leaving Group Was Successful";
            	}
            	
            	JSONObject responseObj=new JSONObject();
     		    responseObj.put("code", code);
     		    responseObj.put("msg", msg);
     		  // System.out.println(responseObj.toJSONString());
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
            
            else if (obj.get("header").toString().equalsIgnoreCase("LogOut")){
            	
            	
            String	userName = obj.get("userName").toString();
            	
            	
            	//System.out.println("User NAme Received: "+userName+" & Password: "+password);
            	String id="0";
            	for (int j=0;j<server.users.size();j++)
            	{
            		if (server.users.get(j).getUsername().equals(userName)){
            			id= String.valueOf(server.users.get(j).getId());
            			//System.out.println("id sent to client is (inner for loop"+id);
                    	
            			server.users.get(j).setStatus(0);
            			server.activeLoggedInClients.remove(id);
            			break;
            		}
            	}     	
            	
            	
            	
            	
            }
            
            else if (obj.get("header").toString().equalsIgnoreCase("kick")){
                
            	String user=obj.get("userID").toString();

            	
            	
            	
            	Boolean userExist=false;
                	
            	String code="0";
            	String msg="Kicking User Failed";
            	int userIndex=0;
            	
            	for (User temp :server.users){
            		if ( String.valueOf(temp.getId()).equals(user) ) userExist=true;
            	}
            	
            	
            	
            	for (User temp: server.users){
            		if (  ( String.valueOf(temp.getId()).equals(user)) ) {
            			
            			userIndex=server.users.indexOf(temp);
            			}
            	}
            	
            	
            	
            	
            	
            	if (userExist ){

            		System.out.println("user " + server.users.get(userIndex).getUsername() + " is kicked");
            		
            		server.users.remove(userIndex);
            		code="2";
            		msg="Kicking User From Group Was Successful";
            		
            	}
            	
            	/*for (User temp :server.users){
            		System.out.println(temp.toString());
            	}*/
            	
            	JSONObject responseObj=new JSONObject();
     		    responseObj.put("code", code);
     		    responseObj.put("msg", msg);
     			   
     		    dos.writeUTF(responseObj.toJSONString());
     		    
     		    //TODO  find all active users and broadcast the user that it is kicked off
            }
            
 else if (obj.get("header").toString().equalsIgnoreCase("update")){
            	
            	String code="0";
            	try {  //building users file

            		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            		// root elements
            		Document usersFile = docBuilder.newDocument();
            		Document groupsFile = docBuilder.newDocument();
            		Document groupRecordsFile = docBuilder.newDocument();
            		
            		
            		
            		Element userRoot = usersFile.createElement("users");
            		Element groupRoot = groupsFile.createElement("groups");
            		Element recordRoot = groupRecordsFile.createElement("records");
            		
            		
            		usersFile.appendChild(userRoot);
            		groupsFile.appendChild(groupRoot);
            		groupRecordsFile.appendChild(recordRoot);


            	//for users file
            		for(int i=0; i<server.users.size();i++)
            		{
            		Element user = usersFile.createElement("user");
            		userRoot.appendChild(user);

            		// set attribute to staff element
            		
            		Attr attr = usersFile.createAttribute("id");
            		
            			
            		attr.setValue(String.valueOf(server.users.get(i).getId()));
            		user.setAttributeNode(attr);
            		

            		
            		Element username = usersFile.createElement("username");
            		username.appendChild(usersFile.createTextNode(server.users.get(i).getUsername()));
            		
            		user.appendChild(username);

            		
            		Element password = usersFile.createElement("password");
            		password.appendChild(usersFile.createTextNode(server.users.get(i).getPassword()));
            		user.appendChild(password);

            		// nickname elements
            		Element status = usersFile.createElement("status");
            		status.appendChild(usersFile.createTextNode(String.valueOf(server.users.get(i).getStatus())));
            		user.appendChild(status);
            		
            		Element admin = usersFile.createElement("admin");
            		admin.appendChild(usersFile.createTextNode(String.valueOf(server.users.get(i).getAdmin())));
            		user.appendChild(admin);

            		

            		// write the content into xml file
            		TransformerFactory transformerFactory = TransformerFactory.newInstance();
            		Transformer transformer = transformerFactory.newTransformer();
            		DOMSource source = new DOMSource(usersFile);
            		PrintWriter users = new PrintWriter("users.txt");
            		StreamResult result = new StreamResult(users);
            		

            		// Output to console for testing
            		//StreamResult result = new StreamResult(System.out);

            		transformer.transform(source, result);
            		}
            		
            		
            		//for groups file
            		for(int i=0; i<server.groups.size();i++)
            		{
            		Element group = groupsFile.createElement("group");
            		groupRoot.appendChild(group);

            		
            		
            		Attr attr = groupsFile.createAttribute("id");
            		
            			
            		attr.setValue(String.valueOf(server.groups.get(i).getId()));
            		group.setAttributeNode(attr);
            		

            		
            		Element groupName = groupsFile.createElement("groupName");
            		groupName.appendChild(groupsFile.createTextNode(server.groups.get(i).getGroupName()));
            		
            		group.appendChild(groupName);

            		
            		// write the content into xml file
            		TransformerFactory transformerFactorygroups = TransformerFactory.newInstance();
            		Transformer transformer1 = transformerFactorygroups.newTransformer();
            		DOMSource source1 = new DOMSource(groupsFile);
            		PrintWriter groups = new PrintWriter("groups.txt");
            		StreamResult result1 = new StreamResult(groups);
            		

            		// Output to console for testing
            		//StreamResult result = new StreamResult(System.out);

            		transformer1.transform(source1, result1);
            		}
            		
            		
            		//for groupRecords file
            		for(int i=0; i<server.records.size();i++)
            		{
            		Element record = groupRecordsFile.createElement("record");
            		recordRoot.appendChild(record);

            		
            		
            		Attr attr = groupRecordsFile.createAttribute("id");
            		
            			
            		attr.setValue(String.valueOf(server.records.get(i).getId()));
            		record.setAttributeNode(attr);
            		

            		
            		Element userID = groupRecordsFile.createElement("userID");
            		userID.appendChild(groupRecordsFile.createTextNode(String.valueOf(server.records.get(i).getUserID())));
            		
            		record.appendChild(userID);
            		
            		Element groupID = groupRecordsFile.createElement("groupID");
            		groupID.appendChild(groupRecordsFile.createTextNode(String.valueOf(server.records.get(i).getGroupID())));
            		
            		record.appendChild(groupID);

            		
            		

            		

            		// write the content into xml file
            		TransformerFactory transformerFactoryrecords = TransformerFactory.newInstance();
            		Transformer transformer2 = transformerFactoryrecords.newTransformer();
            		DOMSource source2 = new DOMSource(groupRecordsFile);
            		PrintWriter records = new PrintWriter("grouprecords.txt");
            		StreamResult result2 = new StreamResult(records);
            		code ="succeed";
            		

            		// Output to console for testing
            		//StreamResult result = new StreamResult(System.out);

            		transformer2.transform(source2, result2);
            		}


            		//System.out.println("File saved!");

            	  } catch (ParserConfigurationException pce) {
            		pce.printStackTrace();
            	  } catch (TransformerException tfe) {
            		tfe.printStackTrace();
            	  }
            	code ="1";
            	 dos.writeUTF(code);
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
