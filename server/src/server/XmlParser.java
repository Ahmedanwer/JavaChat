/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
 
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import server.groupRecords.groupRecord;
 
public class XmlParser
{
    public ArrayList parseXml(InputStream in)
    {
        //Create a empty link of users initially
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Group> groups = new ArrayList<Group>();
        ArrayList<groupRecord> records = new ArrayList<groupRecord>();
        try
        {
            //Create default handler instance
            ParserHandler handler = new ParserHandler();
 
            //Create parser from factory
            XMLReader parser = XMLReaderFactory.createXMLReader();
 
            //Register handler with parser
            parser.setContentHandler(handler);
 
            //Create an input source from the XML input stream
            InputSource source = new InputSource(in);
 
            //parse the document
            parser.parse(source);
 
            //populate the parsed users list in above created empty list; You can return from here also.
            users = handler.getUsers();

 
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
 
        }
        return users;
        
    }
     public ArrayList parseGroupsXml(InputStream in)
    {
        //Create a empty link of users initially

        ArrayList<Group> groups = new ArrayList<Group>();
        try
        {
            //Create default handler instance
            ParserHandler handler = new ParserHandler();
 
            //Create parser from factory
            XMLReader parser = XMLReaderFactory.createXMLReader();
 
            //Register handler with parser
            parser.setContentHandler(handler);
 
            //Create an input source from the XML input stream
            InputSource source = new InputSource(in);
 
            //parse the document
            parser.parse(source);
 
            //populate the parsed users list in above created empty list; You can return from here also.
      
            groups = handler.getGroups();
 
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
 
        }
        return groups;
        
    }
     public ArrayList parseRecordsXml(InputStream in)
     {
         //Create a empty link of users initially

         ArrayList<groupRecords> records = new ArrayList<groupRecords>();
         try
         {
             //Create default handler instance
             ParserHandler handler = new ParserHandler();
  
             //Create parser from factory
             XMLReader parser = XMLReaderFactory.createXMLReader();
  
             //Register handler with parser
             parser.setContentHandler(handler);
  
             //Create an input source from the XML input stream
             InputSource source = new InputSource(in);
  
             //parse the document
             parser.parse(source);
  
             //populate the parsed users list in above created empty list; You can return from here also.
       
             records = handler.getRecords();
  
         } catch (SAXException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } finally {
  
         }
         return records;
         
     }
}

