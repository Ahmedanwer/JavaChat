package client;

import java.util.ArrayList;
import java.util.HashMap;

public class client {

	
 
   
   public static void main(String[] args) {
        {

        	//new peerTopeer();
        	new loginPage();
        	ArrayList<User> users=new ArrayList<User>();
    		
    		
    		

    		 User ThisUser=new User(103,"ahmed",1);
    		 ThisUser.setIP("192.168.1.19");
    		 
    		 Group ThisGroup = new Group(1,"ASU");
    		 
    		 
    		 
    		
    		users.add(ThisUser);
    		
    		users.add(new User(3,"Hussien",1));
    		users.add(new User(4,"Ashraf",1));
    		
    		
    		
    		
    		ArrayList<Group> groups=new ArrayList<Group>();
    		groups.add(ThisGroup);
    		groups.add(new Group(2,"BUE"));
    		groups.add(new Group(3,"AUC"));
    		groups.add(new Group(4,"GUC"));
        	new HomePage(users,groups,groups,ThisUser,ThisGroup);
        }

    }
}