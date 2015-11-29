/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsaxparser;

import java.util.ArrayList;
import java.util.Stack;
 
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class ParserHandler extends DefaultHandler
{
    //This is the list which shall be populated while parsing the XML.
    private ArrayList userList = new ArrayList();
    private ArrayList GroupList = new ArrayList();
 
    //As we read any XML element we will push that in this stack
    private Stack elementStack = new Stack();
 
    //As we complete one user block in XML, we will push the User instance in userList
    private Stack objectStack = new Stack();
    
    boolean username = false;
    boolean password = false;
    boolean status = false;
    boolean groupName = false;
    private Group group=null;
    private User user=null;
 
    public void startDocument() throws SAXException
    {
       // System.out.println("start of the document: ");
    }
 
    public void endDocument() throws SAXException
    {
       // System.out.println("end of the document: ");
    }
 
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        
        //Push it in element stack
        this.elementStack.push(qName);
 
        //If this is start of 'user' element then prepare a new User instance and push it in object stack
        if ("user".equals(qName))
        {
            //New User instance
            user = new User();
 
            //Set all required attributes in any XML element here itself
            
                user.setId(Integer.parseInt(attributes.getValue("id")));
      
                
            
            this.objectStack.push(user);
        }
        else if("username".equals(qName))
        { 
            username = true;
        }
         else if(qName.equals("password"))
        { 
            password = true;
        }
         else if(qName.equals("status"))
        { 
            status = true;
        }
         if ("group".equals(qName))
        {
            //New User instance
            group = new Group();
 
            //Set all required attributes in any XML element here itself
            
                group.setId(Integer.parseInt(attributes.getValue("id")));
      
                
            
            this.objectStack.push(group);
        }
        else if("groupName".equals(qName))
        { 
           groupName = true;
        }
    }
 
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        //Remove last added  element
        this.elementStack.pop();
 
        //User instance has been constructed so pop it from object stack and push in userList
        if ("user".equals(qName))
        {
            User object = (User) this.objectStack.pop();
            this.userList.add(object);
        }
         if ("group".equals(qName))
        {
            Group object = (Group) this.objectStack.pop();
            this.GroupList.add(object);
        }
    }
 
    /**
     * This will be called everytime parser encounter a value node
     * */
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        
        String value = new String(ch, start, length).trim();
       // System.out.print(value+" ");
 
        if (value.length() == 0)
        {
            return; // ignore white space
        }
       else if (username) {
            //age element, set Employee age
            user.setUsername(value);
            username = false;
        } else if (password) {
            user.setPassword(value);
            password = false;
        }
         else if (status) {
           
             user.setStatus(Integer.parseInt(value));
            status = false;
        }
         else if (groupName) {
            //age element, set Employee age
            group.setGroupName(value);
            groupName = false;
         }
 
        //handle the value based on to which element it belongs
       /* if ("firstName".equals(currentElement()))
        {
            User user = (User) this.objectStack.peek();
            user.setFirstName(value);
            System.out.print(value);
            
        }
        else if ("lastName".equals(currentElement()))
        {
            User user = (User) this.objectStack.peek();
            user.setLastName(value);
            System.out.print(user.getLastName());
        }*/
    }
 
    
    
    
    /**
     * Utility method for getting the current element in processing
     * */
    private String currentElement()
    {
        return (String) this.elementStack.peek();
    }
 
    //Accessor for userList object
    public ArrayList getUsers()
    {
        return userList;
    }
     public ArrayList getGroups()
    {
        return GroupList;
    }
}
