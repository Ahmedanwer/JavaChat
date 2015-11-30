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
	ArrayList<User> users;
	ArrayList<Group> groups;
	ArrayList<groupRecords.groupRecord> records;
	
	public server() throws FileNotFoundException
	{
		File xmlFile = new File("users.txt");
        File xmlGroupsFile = new File("groups.txt");
        File xmlrecordsFile = new File("grouprecords.txt");
       
        
        //Create the parser instance
        XmlParser parser = new XmlParser();
 
        //Parse the file
      users = parser.parseXml(new FileInputStream(xmlFile));
        groups = parser.parseGroupsXml(new FileInputStream(xmlGroupsFile));
        records = parser.parseRecordsXml(new FileInputStream(xmlrecordsFile));
       
      for(int i=0; i<users.size(); i++)
       {  
           
          System.out.print(users.get(i).toString());
       }
      for(int i=0; i<groups.size(); i++)
       {  
           
          System.out.print(groups.get(i).toString());
       }
      for(int i=0; i<records.size(); i++)
       {  
           
          System.out.print(records.get(i).toString());
       }
	}
   
	

    public static void main(String[] args) throws IOException { 
    	
    	
    	leaveServer();
    	
    	
    	
    	
    	
    	
    	
    	
	    
        // TODO code application logic here
      //  try {
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
        	//ArrayList<Socket> activeClients=new ArrayList<Socket>();
        	
        	
        	
            //1.Create Server Socket
            //ServerSocket sv = new ServerSocket(1243);
            
            //Server is always On
           /* while (true) {
            //2.Listen for Clients
                Socket c;
                c = sv.accept();
                System.out.println("new client arrived ");
                activeClients.add(c);
                clientThread arrivedClient=new clientThread(c, activeClients);// later activeClients will be replaced by active members in group
                arrivedClient.run();
                
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/

}
    
    public  void leaveServer() throws IOException
    {
    	try {

    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    		// root elements
    		Document doc = docBuilder.newDocument();
    		Element rootElement = doc.createElement("users");
    		doc.appendChild(rootElement);

    		// staff elements
    		for(int i=0; i<users.size();i++)
    		{
    		Element user = doc.createElement("user");
    		rootElement.appendChild(user);

    		// set attribute to staff element
    		
    		Attr attr = doc.createAttribute("id");
    		
    			
    		attr.setValue(String.valueOf(users.get(i).getId()));
    		user.setAttributeNode(attr);
    		

    		
    		Element username = doc.createElement("username");
    		username.appendChild(doc.createTextNode(users.get(i).getUsername()));
    		//username.appendChild(doc.createTextNode("aya bs"));
    		user.appendChild(username);

    		
    		Element password = doc.createElement("password");
    		password.appendChild(doc.createTextNode(users.get(i).getPassword()));
    		user.appendChild(password);

    		// nickname elements
    		Element status = doc.createElement("status");
    		status.appendChild(doc.createTextNode(String.valueOf(users.get(i).getStatus())));
    		user.appendChild(status);

    		

    		// write the content into xml file
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);
    		PrintWriter users = new PrintWriter("users.txt");
    		StreamResult result = new StreamResult(users);
    		

    		// Output to console for testing
    		//StreamResult result = new StreamResult(System.out);

    		transformer.transform(source, result);
    		}

    		//System.out.println("File saved!");

    	  } catch (ParserConfigurationException pce) {
    		pce.printStackTrace();
    	  } catch (TransformerException tfe) {
    		tfe.printStackTrace();
    	  }
    }
   
}