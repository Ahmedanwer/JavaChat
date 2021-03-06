package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class apiFunctions {

	private String userName,password;
	static Socket serverConnection;
	static DataOutputStream Sdos;
	static DataInputStream Sdis;
	static String id;
	static Boolean serverConnectionEstablished;
	static String serverIP;

	
		//constructor that establishes a server connection and login
		public apiFunctions(String userName, String password ,String ip ) {
			
		serverConnectionEstablished=false;
		this.userName = userName;
		this.password = password;
		try {
			serverIP=ip;
		connect(ip);
			login(userName, password);
		} catch (Exception e) {e.printStackTrace();}
	}
	
		
		
		//constructor that does nothing
		public apiFunctions() {}
	
		
		
		// connect to server by server ip
		public static void connect (String ip){
			
			try {
			serverConnection = new Socket (ip, 5000); 
			Sdis =new DataInputStream(serverConnection.getInputStream());
			Sdos= new DataOutputStream(serverConnection.getOutputStream());
			} catch (Exception e) {e.printStackTrace();}
	
			}
		
		
		
		
		// takes user name and password and login to server and return user id; if id=o then login failed
	   public static String login(String user, String pass){

		   JSONObject obj = new JSONObject();
		   		
	   	  obj.put("header", "login");
	      obj.put("username", user);
	      obj.put("password", pass);
	     
	      
	      try {
				Sdos.writeUTF(obj.toJSONString());
				id=Sdis.readUTF();

				if (!(id.equals("0"))) {
					System.out.println("Login Successful, ID Retrived= "+id);
					return id;
				}
			} catch (Exception e) {e.printStackTrace();}
	      
		      System.out.println("login failed,ID Retrived= "+id );
			return "0";  
	   }
	   
	   
	   
	   
	   //BC msg to all active clients
	   public static void BCMsg (String msg) {

		   JSONObject obj = new JSONObject();
		   
		   try {	
		   	  obj.put("header", "BCM");
		      obj.put("msg", msg);
		      obj.put("SenderIP", InetAddress.getLocalHost().toString());
		      System.out.println(obj.toJSONString());
		      Sdos.writeUTF(obj.toJSONString());
		      System.out.println(Sdis.readUTF());
			} 
		   catch (Exception e) {e.printStackTrace();}		  
	}
	   
	   
	   
	   
	   // BC to active members only of certian group and return either the msg sent or "Something went wrong"
	   public static void BCMsgToGroup (String msg, String SenderID, String GroupIP) {

		   JSONObject obj = new JSONObject();
		   
		   try {	
		   	  obj.put("header", "BCMtoGroup");
		      obj.put("msg", msg);
		      obj.put("SenderID", SenderID);
		      obj.put("groupID", GroupIP);
		      System.out.println(obj.toJSONString());
		      Sdos.writeUTF(obj.toJSONString());
		     // return  Sdis.readUTF();
		     
			} 
		   catch (Exception e) {e.printStackTrace();}
			  
	}
	   
	   
	   
	   //return all registered users in server
	   public static ArrayList<User> getAllUsers (){

		   ArrayList<User> arrayList= new ArrayList<>();
			
			try {
		Socket	serverConnection2 = new Socket (serverIP, 5000); 
		DataInputStream	Sdis2 =new DataInputStream(serverConnection2.getInputStream());
		DataOutputStream	Sdos2= new DataOutputStream(serverConnection2.getOutputStream());
			
			  JSONObject obj = new JSONObject();
		   		
		   	  obj.put("header", "getallusers");
		      
		   	Sdos2.writeUTF(obj.toJSONString());
		   
				Gson gson = new Gson();
				String json=Sdis2.readUTF();
				java.lang.reflect.Type type = new TypeToken<ArrayList<User>>(){}.getType();
				arrayList = gson.fromJson(json, type);
				
			
			
			} catch (Exception e) {e.printStackTrace();
			
			}
	
			
		
			
			return arrayList;
		 
		 
			
	   }
	
	   
	   

	   //return all groups in server
	   public static ArrayList<Group> getAllGroups (){
			
		   ArrayList<Group> arrayList= new ArrayList<>();
		   try {

			   
			   Socket	serverConnection2 = new Socket (serverIP, 5000); 
				DataInputStream	Sdis2 =new DataInputStream(serverConnection2.getInputStream());
				DataOutputStream	Sdos2= new DataOutputStream(serverConnection2.getOutputStream());
				
				
		   JSONObject obj = new JSONObject();
		   	   obj.put("header", "getallgroups");
		   	   Sdos2.writeUTF(obj.toJSONString());
		   
		   	   Gson gson = new Gson();
		   	   String json=Sdis2.readUTF();
				
		   	   java.lang.reflect.Type type = new TypeToken<ArrayList<Group>>(){}.getType();
		   	   arrayList = gson.fromJson(json, type);
		   }catch (Exception e){e.printStackTrace();}
		   
				return arrayList;
	   }
	   
	   
	   
	   
	   //takes user id and return list of groups that contain that user
	   public static ArrayList<Group> getMyGroups (String userID){
			
		   ArrayList<Group> arrayList= new ArrayList<>();
		   try {

			   
			   Socket	serverConnection2 = new Socket (serverIP, 5000); 
				DataInputStream	Sdis2 =new DataInputStream(serverConnection2.getInputStream());
				DataOutputStream	Sdos2= new DataOutputStream(serverConnection2.getOutputStream());
				
			    JSONObject obj = new JSONObject();
		   	  	obj.put("header", "getmygroups"); 
		   	  	obj.put("id", userID);
				Sdos2.writeUTF(obj.toJSONString());
				
				Gson gson = new Gson();
			   	   String json=Sdis2.readUTF();
					System.out.println("my groups recevied as json"+ json);
			   	   java.lang.reflect.Type type = new TypeToken<ArrayList<Group>>(){}.getType();
			   	   arrayList = gson.fromJson(json, type);
			   }catch (Exception e){e.printStackTrace();}
			   
					return arrayList;

	   }
	   
	   
	  
	   
	   
	   
	   
	   // return all members of a certain group; with status for each members updated "online or offline"
	   public static ArrayList<User> getMembersOfGroup (String groupid){
		   
		   
		   	  
		      ArrayList<User> arrayList= new ArrayList<>();
			   
			   try {
				   
				   JSONObject obj = new JSONObject();
				   obj.put("header", "getMembersOfGroup");
				   obj.put("groupID", groupid);
			   	   Sdos.writeUTF(obj.toJSONString());
			   
			   	   Gson gson = new Gson();
			   	   String json=Sdis.readUTF();
					
			   	   java.lang.reflect.Type type = new TypeToken<ArrayList<User>>(){}.getType();
			   	   arrayList = gson.fromJson(json, type);
			   }catch (Exception e){e.printStackTrace();}
			   
					return arrayList;
		   
	   }
	   
	   
	   
	   //issue (unable to create group record at the server)
	   // enroll user in a group; returns "0" for failed, "1" for success,  "2" for user already enrolled, and "3" if user or group doesnt exist;;msg is printed to the console
	   public static String enrollInAGroup (String groupid, String userid){
		   
		   
		   try {
				Socket	serverConnection2 = new Socket (serverIP, 5000); 
				DataInputStream	Sdis2 =new DataInputStream(serverConnection2.getInputStream());
				DataOutputStream	Sdos2= new DataOutputStream(serverConnection2.getOutputStream());
			
			   JSONObject obj=new JSONObject();
			   obj.put("header", "enroll");
			   obj.put("groupID", groupid);
			   obj.put("userID", userid);
		   	   Sdos2.writeUTF(obj.toJSONString());
		   


		   	  String response = Sdis2.readUTF();//read from the server
	          JSONObject responseObj=(JSONObject) JSONValue.parse(response);
	          
	         // System.out.println(responseObj.get("msg").toString());
	          Sdos2.close();
	          Sdis2.close();
	          serverConnection2.close();
	      	  return responseObj.get("code").toString();
	         
		   }catch (Exception e){e.printStackTrace();}
		   
		   System.out.println("Enrollment Failed");
		   return "0";
	   }
	   
	   
	   
	   
	   // user quits group; return "0" for failed, "1" for success,  "2" for user not enrolled, and "3" if user or group doesnt exist;;msg is printed to the console
	   public static String leaveGroup (String groupid, String userid){
		   
		   try {
			   
			
					Socket	serverConnection2 = new Socket (serverIP, 5000); 
					DataInputStream	Sdis2 =new DataInputStream(serverConnection2.getInputStream());
					DataOutputStream	Sdos2= new DataOutputStream(serverConnection2.getOutputStream());
						
			   
			   JSONObject obj=new JSONObject();
			   obj.put("header", "leave");
			   obj.put("groupID", groupid);
			   obj.put("userID", userid);
		   	   Sdos2.writeUTF(obj.toJSONString());
		   


		   	  String response = Sdis2.readUTF();//read from the server
	          JSONObject responseObj=(JSONObject) JSONValue.parse(response);
	          
	          System.out.println(responseObj.get("msg").toString());
	          Sdos2.close();
	          Sdis2.close();
	          serverConnection2.close();
	      	  return responseObj.get("code").toString();
	      	
	         
		   }catch (Exception e){e.printStackTrace();}
		   
		   System.out.println("Leaving Group Failed");
		   return "0";
		   
		   
	   }
	   
	   
	   
	   
	   //return ip of the calling client
	   public static String getMyIP (String userid){

	   JSONObject obj = new JSONObject();
		   
		   try {	
			   
		   	  obj.put("header", "getmyip");
		   	  obj.put("userid", userid);
		      Sdos.writeUTF(obj.toJSONString());
		      
		      return Sdis.readUTF();
			} 
		   catch (Exception e) {e.printStackTrace();}
		   
		return "Something Went Wrong While Retreiving Yor IP";
	   }
	   
	   
	   
	   
	   //return ip of the another client
	   public static String getOtherClientIP (String otherClientid){
		
		   
		 JSONObject obj = new JSONObject();
		   
		   try {	
			   
		   	  obj.put("header", "getotherip");
		   	  obj.put("userid", otherClientid);
		      Sdos.writeUTF(obj.toJSONString());
		      
		      return Sdis.readUTF();
			} 
		   catch (Exception e) {e.printStackTrace();}
		   
		return "Something Went Wrong While Retreiving IP For Client: "+otherClientid;
	   }
	   
	   public static void LogOut (String UserName){
			
		   
			 JSONObject obj = new JSONObject();
			   
			   try {	
				   
			   	  obj.put("header", "LogOut");
			   	  obj.put("userName", UserName);
			      Sdos.writeUTF(obj.toJSONString());
			      System.out.println("logingOut");
			     // return Sdis.readUTF();
				} 
			   catch (Exception e) {e.printStackTrace();}
			   
		   }
	   
	   
	   
	   
	   //kick off another user from a group,  return "0" for failed, "1" for success,  "2" for user not enrolled, and "3" if user or group doesnt exist;;msg is printed to the console
	   public static String kickOff (String clientid){

		   try {
			   JSONObject obj=new JSONObject();
			   obj.put("header", "kick");
			   obj.put("userID", clientid);
		   	   Sdos.writeUTF(obj.toJSONString());
		   	   
		   
		   	   return "2";
		   	   /*
		   	  String response = Sdis.readUTF();//read from the server
	          JSONObject responseObj=(JSONObject) JSONValue.parse(response);
	          
	          System.out.println(responseObj.get("msg").toString());
	      	  return responseObj.get("code").toString();
	      	  */
	         
		   }catch (Exception e){e.printStackTrace();}
		   
		   System.out.println("Kicking User Failed");
		   return "0";  
	   }
	   
	   public static void updateWithout(){

		   JSONObject obj = new JSONObject();
		   		
	   	  obj.put("header", "update");

	      
	      try {
				Sdos.writeUTF(obj.toJSONString());
			
			} catch (Exception e) {e.printStackTrace();}
	       
	   }
	   
	   public static String update(){

		   JSONObject obj = new JSONObject();
		   		
	   	  obj.put("header", "update");

	      
	      try {
	    	  
	    		Socket	serverConnection2 = new Socket (serverIP, 5000); 
				DataInputStream	Sdis2 =new DataInputStream(serverConnection2.getInputStream());
				DataOutputStream	Sdos2= new DataOutputStream(serverConnection2.getOutputStream());
					
	    	  
				Sdos2.writeUTF(obj.toJSONString());
			String 	result=Sdis2.readUTF();
			
			
			  Sdos2.close();
	          Sdis2.close();
	          serverConnection2.close();

				if (!(result.equals("1"))) {
					System.out.println("update Successful");
					return result;
				}
			} catch (Exception e) {e.printStackTrace();}
	      
		      System.out.println("update failed" );
			return "0";  
	   }



}