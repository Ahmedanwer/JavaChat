package client;

import java.util.ArrayList;

public class client {

	
 
   
   public static void main(String[] args) {
        {

        	//new peerTopeer();
        	new HomePage();
        	apiFunctions api = new apiFunctions("ahmed", "2222", "192.168.1.95");
        	String userid=apiFunctions.login("ahmed", "2222");
        	System.out.println(apiFunctions.getOtherClientIP(userid));
        	
           	/*	//testing 
        	apiFunctions.BCMsg("test bcm");
        	
        	System.out.println(apiFunctions.getMyIP(userid));
        	
        	apiFunctions.BCMsgToGroup("testing bcming group", "100", "1");
     
        	ArrayList<Group> groups= apiFunctions.getMyGroups("101");
        	System.out.println("Size of group retreived "+groups.size());
        	
        	ArrayList<Group> groups= apiFunctions.getAllGroups();
        	System.out.println("Size of group retreived "+groups.size());
        	
        	ArrayList<User> users= apiFunctions.getAllUsers();
        	System.out.println("Size of user retreived "+users.size());
        	*/
        	
        }

    }
}