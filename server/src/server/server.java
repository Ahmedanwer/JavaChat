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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;






public class server {

	
	 public static   ArrayList<User> users;
	 public static ArrayList<Group> groups;
	 public static ArrayList<groupRecords.groupRecord> records;
	 public static	ArrayList<Socket> activeClients;
	 
    public server() throws FileNotFoundException
    {
    	 
    	        //Locate the file
    	     
    	        File xmlFile = new File("users.txt");
    	        File xmlGroupsFile = new File("groups.txt");
    	        File xmlGroupRecordsFile = new File("grouprecords.txt");
    	     
    	        
    	        //Create the parser instance
    	        XmlParser parser = new XmlParser();
    	 
    	        //Parse the file
    	       users = parser.parseXml(new FileInputStream(xmlFile));
    	       groups = parser.parseGroupsXml(new FileInputStream(xmlGroupsFile));
    	       records = parser.parseRecordsXml(new FileInputStream(xmlGroupRecordsFile));
    	       
    	     /* for(int i=0; i<users.size(); i++)
    	       {  
    	           
    	          System.out.print((users.get(i).toString()));
    	       }
    	      for(int i=0; i<groups.size(); i++)
    	       {  
    	           
    	          System.out.print((groups.get(i).toString()));
    	       }*/
    	      for(int i=0; i<records.size(); i++)
    	       {  
    	           
    	          System.out.print((records.get(i).toString()));
    	       }
    	              
    	 
    	        //Verify the result
    	        
    	    
    }
    //destructor
    protected void finalize() throws FileNotFoundException
    {
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
    		for(int i=0; i<users.size();i++)
    		{
    		Element user = usersFile.createElement("user");
    		userRoot.appendChild(user);

    		// set attribute to staff element
    		
    		Attr attr = usersFile.createAttribute("id");
    		
    			
    		attr.setValue(String.valueOf(users.get(i).getId()));
    		user.setAttributeNode(attr);
    		

    		
    		Element username = usersFile.createElement("username");
    		username.appendChild(usersFile.createTextNode(users.get(i).getUsername()));
    		
    		user.appendChild(username);

    		
    		Element password = usersFile.createElement("password");
    		password.appendChild(usersFile.createTextNode(users.get(i).getPassword()));
    		user.appendChild(password);

    		// nickname elements
    		Element status = usersFile.createElement("status");
    		status.appendChild(usersFile.createTextNode(String.valueOf(users.get(i).getStatus())));
    		user.appendChild(status);

    		

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
    		for(int i=0; i<groups.size();i++)
    		{
    		Element group = groupsFile.createElement("group");
    		groupRoot.appendChild(group);

    		
    		
    		Attr attr = groupsFile.createAttribute("id");
    		
    			
    		attr.setValue(String.valueOf(groups.get(i).getId()));
    		group.setAttributeNode(attr);
    		

    		
    		Element groupName = groupsFile.createElement("groupName");
    		groupName.appendChild(groupsFile.createTextNode(groups.get(i).getGroupName()));
    		
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
    		for(int i=0; i<records.size();i++)
    		{
    		Element record = groupRecordsFile.createElement("record");
    		recordRoot.appendChild(record);

    		
    		
    		Attr attr = groupRecordsFile.createAttribute("id");
    		
    			
    		attr.setValue(String.valueOf(records.get(i).getId()));
    		record.setAttributeNode(attr);
    		

    		
    		Element userID = groupRecordsFile.createElement("userID");
    		userID.appendChild(groupRecordsFile.createTextNode(String.valueOf(records.get(i).getUserID())));
    		
    		record.appendChild(userID);
    		
    		Element groupID = groupRecordsFile.createElement("groupID");
    		groupID.appendChild(groupRecordsFile.createTextNode(String.valueOf(records.get(i).getGroupID())));
    		
    		record.appendChild(groupID);

    		
    		

    		

    		// write the content into xml file
    		TransformerFactory transformerFactoryrecords = TransformerFactory.newInstance();
    		Transformer transformer2 = transformerFactoryrecords.newTransformer();
    		DOMSource source2 = new DOMSource(groupRecordsFile);
    		PrintWriter records = new PrintWriter("grouprecords.txt");
    		StreamResult result2 = new StreamResult(records);
    		

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
    	
   
    	
    }
   
    

	

    public static void main(String[] args) throws IOException { 
    	
    	server s = new server();
    	users.get(0).setUsername("Ahmed Anwar"); //changing certain elements
    	groups.get(0).setGroupName("Minions");
    	records.get(0).setGroupID(65788757);
    	
    	
    	
    	s.finalize();
    	
    	

	    
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
        	activeClients=new ArrayList<Socket>();
        	
        	
        	
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
                clientThread arrivedClient=new clientThread(c);// later activeClients will be replaced by active members in group
                arrivedClient.start();
                
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    

}
    
    
    	
}