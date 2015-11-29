package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;





public class server {
   /* public server() throws FileNotFoundException
    {
    	 
    	        //Locate the file
    	     
    	        File xmlFile = new File("users.txt");
    	        File xmlGroupsFile = new File("groups.txt");
    	        //System.out.print(xmlFile);
    	        
    	        //Create the parser instance
    	        XmlParser parser = new XmlParser();
    	 
    	        //Parse the file
    	       ArrayList users = parser.parseXml(new FileInputStream(xmlFile));
    	       ArrayList groups = parser.parseGroupsXml(new FileInputStream(xmlGroupsFile));
    	       
    	      for(int i=0; i<users.size(); i++)
    	       {  
    	           
    	          System.out.print((users.toArray())[i].toString());
    	       }
    	      for(int i=0; i<groups.size(); i++)
    	       {  
    	           
    	          System.out.print((groups.toArray())[i].toString());
    	       }
    	              
    	 
    	        //Verify the result
    	        
    	    
    }*/
	

    public static void main(String[] args) throws FileNotFoundException {
    	
        File xmlFile = new File("users.txt");
	        File xmlGroupsFile = new File("groups.txt");
	        //System.out.print(xmlFile);
	        
	        //Create the parser instance
	        XmlParser parser = new XmlParser();
	 
	        //Parse the file
	       ArrayList users = parser.parseXml(new FileInputStream(xmlFile));
	       ArrayList groups = parser.parseGroupsXml(new FileInputStream(xmlGroupsFile));
	       
	      for(int i=0; i<users.size(); i++)
	       {  
	           
	          System.out.print((users.toArray())[i].toString());
	       }
	      for(int i=0; i<groups.size(); i++)
	       {  
	           
	          System.out.print((groups.toArray())[i].toString());
	       }
	              
	 
	        //Verify the result
	        
	    
        // TODO code application logic here
        try {
        /*	//initiate list of groups
        	ArrayList<group> groups= new ArrayList<group>();
        	groups.add(new group("Test Group 1"));
        	groups.add(new group("Test Group 2"));
        	
        	//initiate list of users
        	ArrayList<user> users=new ArrayList<user>();
        	groups.add(new user("Aly","Pass",true,"192.168.1.1"));
        	groups.add(new user ("AlyElTany","AnwarPass",false,"192.168.1.2"));
        	groups.add(new user("AlyElTalaet","Shro2Pass",false,"192.168.1.3"));
        	
        	//intiate list of admins
        	ArrayList<admin> admins=new ArrayList<admin>();
        	admins.add(new admin("hamda","adminPASS",false,"192.168.1.5"));
       */ 	
        	//list of logged in clients
        	ArrayList<Socket> activeClients=new ArrayList<Socket>();
        	
        	
        	
            //1.Create Server Socket

            ServerSocket sv = new ServerSocket(1555);

            //ServerSocket sv = new ServerSocket(1243);

            //Server is always On
            while (true) {
            //2.Listen for Clients
                Socket c;
                c = sv.accept();
                System.out.println("new client arrived ");
                activeClients.add(c);
                clientThread arrivedClient=new clientThread(c, activeClients);// later activeClients will be replaced by active members in group
                arrivedClient.start();
                
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    

}
}