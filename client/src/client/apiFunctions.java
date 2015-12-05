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
import com.sun.glass.ui.CommonDialogs.Type;

public class apiFunctions {

	private String userName,password;
	static Socket serverConnection;
	static DataOutputStream Sdos;
	static DataInputStream Sdis;
	static String id;


	
	
		//constructor that establishes a server connection and login
		public apiFunctions(String userName, String password ,String ip ) {
		
		this.userName = userName;
		this.password = password;
		try {
			
			connect(ip);
			login(userName, password);
		} catch (Exception e) {e.printStackTrace();}
	}
	
		
		
		//constructor that does nothing
		public apiFunctions() {}
	
		
		
		// connect to server by server ip
		public static void connect (String ip){
			
			try {
			serverConnection = new Socket (ip, 1555);
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
	   public static String BCMsgToGroup (String msg, String SenderID, String GroupIP) {

		   JSONObject obj = new JSONObject();
		   
		   try {	
		   	  obj.put("header", "BCMtoGroup");
		      obj.put("msg", msg);
		      obj.put("SenderID", SenderID);
		      obj.put("groupID", GroupIP);
		      System.out.println(obj.toJSONString());
		      Sdos.writeUTF(obj.toJSONString());
		      return  Sdis.readUTF();
			} 
		   catch (Exception e) {e.printStackTrace();}
		return "Something went wrong";		  
	}
	   
	   
	   
	   //return all registered users in server
	   public static ArrayList<User> getAllUsers (){

			
		
		   ArrayList<User> arrayList= new ArrayList<>();
		   
		   try {

		   JSONObject obj = new JSONObject();
	   		
		   	  obj.put("header", "getallusers");
		      
				Sdos.writeUTF(obj.toJSONString());
		   
				Gson gson = new Gson();
				String json=Sdis.readUTF();
				java.lang.reflect.Type type = new TypeToken<ArrayList<User>>(){}.getType();
				arrayList = gson.fromJson(json, type);
				

		   }catch (Exception e){e.printStackTrace();}
		   
				return arrayList;
	   }
	
	   
	   

	   //return all groups in server
	   public static ArrayList<Group> getAllGroups (){
			
		   ArrayList<Group> arrayList= new ArrayList<>();
		   try {

		   JSONObject obj = new JSONObject();
		   	   obj.put("header", "getallgroups");
		   	   Sdos.writeUTF(obj.toJSONString());
		   
		   	   Gson gson = new Gson();
		   	   String json=Sdis.readUTF();
				
		   	   java.lang.reflect.Type type = new TypeToken<ArrayList<Group>>(){}.getType();
		   	   arrayList = gson.fromJson(json, type);
		   }catch (Exception e){e.printStackTrace();}
		   
				return arrayList;
	   }
	   
	   
	   
	   
	   //takes user id and return list of groups that contain that user
	   public static ArrayList<Group> getMyGroups (String userID){
			
		   ArrayList<Group> arrayList= new ArrayList<>();
		   try {

			    JSONObject obj = new JSONObject();
		   	  	obj.put("header", "getmygroups"); 
		   	  	obj.put("id", userID);
				Sdos.writeUTF(obj.toJSONString());
				
				Gson gson = new Gson();
				String json=Sdis.readUTF();

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
			   JSONObject obj=new JSONObject();
			   obj.put("header", "enroll");
			   obj.put("groupID", groupid);
			   obj.put("userID", userid);
		   	   Sdos.writeUTF(obj.toJSONString());
		   


		   	  String response = Sdis.readUTF();//read from the server
	          JSONObject responseObj=(JSONObject) JSONValue.parse(response);
	          
	          System.out.println(responseObj.get("msg").toString());
	      	  return responseObj.get("code").toString();
	         
		   }catch (Exception e){e.printStackTrace();}
		   
		   System.out.println("Enrollment Failed");
		   return "0";
	   }
	   
	   
	   
	   
	   // user quits group; return "0" for failed, "1" for success,  "2" for user not enrolled, and "3" if user or group doesnt exist;;msg is printed to the console
	   public static String leaveGroup (String groupid, String userid){
		   
		   try {
			   JSONObject obj=new JSONObject();
			   obj.put("header", "leave");
			   obj.put("groupID", groupid);
			   obj.put("userID", userid);
		   	   Sdos.writeUTF(obj.toJSONString());
		   


		   	  String response = Sdis.readUTF();//read from the server
	          JSONObject responseObj=(JSONObject) JSONValue.parse(response);
	          
	          System.out.println(responseObj.get("msg").toString());
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
	   
	   
	   
	   
	   //kick off another user from a group,  return "0" for failed, "1" for success,  "2" for user not enrolled, and "3" if user or group doesnt exist;;msg is printed to the console
	   public static String kickOff (String groupid,String clientid){

		   try {
			   JSONObject obj=new JSONObject();
			   obj.put("header", "kick");
			   obj.put("groupID", groupid);
			   obj.put("userID", clientid);
		   	   Sdos.writeUTF(obj.toJSONString());
		   


		   	  String response = Sdis.readUTF();//read from the server
	          JSONObject responseObj=(JSONObject) JSONValue.parse(response);
	          
	          System.out.println(responseObj.get("msg").toString());
	      	  return responseObj.get("code").toString();
	         
		   }catch (Exception e){e.printStackTrace();}
		   
		   System.out.println("Kicking User Failed");
		   return "0";  
	   }
	   
}