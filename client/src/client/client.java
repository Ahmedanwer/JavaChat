package client;

import java.util.ArrayList;

public class client {

	
 
   
   public static void main(String[] args) {
        {

        	//new peerTopeer();
        	new HomePage();
        	apiFunctions api = new apiFunctions("ahmed", "2222", "192.168.1.20");
        	apiFunctions.BCMsg("test bcm");
        /*	
        	ArrayList<Group> groups= apiFunctions.getAllGroups();
        	System.out.println("Size of group retreived "+groups.size());
        	
        	ArrayList<User> users= apiFunctions.getAllUsers();
        	System.out.println("Size of user retreived "+users.size());
        	*/
        	
        }

    }
}