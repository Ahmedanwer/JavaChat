package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class apiFunctions {

	private String userName,password;
	static Socket serverConnection;
	static DataOutputStream Sdos;
	static DataInputStream Sdis;
	static String id;


	   public static String Login(String user, String pass){

		   JSONObject obj = new JSONObject();
		   		
		   	  obj.put("header", "login");
		      obj.put("username", user);
		      obj.put("password", pass);
		      try {
				Sdos.writeUTF(obj.toJSONString());
				id=Sdis.readUTF();
				if (id!="0") {
					System.out.println("Login Successful, ID Retrived= "+id);
					return id;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		      System.out.println("login failed,ID Retrived= "+id );
			return "0";
			  
	   }
	   
	   public static Boolean BCMsg (String msg) {

		   JSONObject obj = new JSONObject();
		   		
		   	  obj.put("header", "BCM");
		      obj.put("msg", msg);
		      try {
				obj.put("SenderIP", InetAddress.getLocalHost());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		      try {
				System.out.println(obj.toJSONString());
				Sdos.writeUTF(obj.toJSONString());
				if (Sdis.readUTF().equalsIgnoreCase("login successful")) {
					System.out.println("BCM Successful");
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		      System.out.println("BCM failed");
			return false;
			  
	}
	   
	   public static JSONArray getAllUsers (){
		return null;
		   
	   }
	
}
