package testsaxparser;


 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



 
public class TestSaxParser
{
    public static void main(String[] args) throws FileNotFoundException, IOException
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
        
    }
}


 