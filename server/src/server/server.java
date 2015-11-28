package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class server {

	

    public static void main(String[] args) {
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
