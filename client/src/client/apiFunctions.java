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


	
	
	
	public apiFunctions(String userName, String password ,String ip ) {
		
		this.userName = userName;
		this.password = password;
		try {
			serverConnection = new Socket (ip, 1555);
			Sdis =new DataInputStream(serverConnection.getInputStream());
			Sdos= new DataOutputStream(serverConnection.getOutputStream());
			login(userName, password);
		} catch (Exception e) {e.printStackTrace();}
	}

	public static Boolean connect (String ip){
	
		return false;
	}
	
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		      System.out.println("login failed,ID Retrived= "+id );
			return "0";
			  
	   }
	   
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
		   catch (Exception e) {	e.printStackTrace();}
			  
	}
	   
	   public static ArrayList<User> getAllUsers (){
		
		   ArrayList<User> arrayList= new ArrayList<>();
		   try {
		   JSONObject obj = new JSONObject();
	   		
		   	  obj.put("header", "getallusers");
		      
				System.out.println(obj.toJSONString());
				Sdos.writeUTF(obj.toJSONString());
		   
				Gson gson = new Gson();
				String json=Sdis.readUTF();
				
				java.lang.reflect.Type type = new TypeToken<ArrayList<User>>(){}.getType();
				arrayList = gson.fromJson(json, type);
				
		   }catch (Exception e){e.printStackTrace();}
				return arrayList;
		   
	   }
	
	   
	   
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
	   
	   
}
