package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class client {

  
    public static void main(String[] args) {
        {

        	loginInterface login=new loginInterface(); 
                login.show();
                homePageInterface homePage = new homePageInterface();
                homePage.show();
                conversationInterface conv = new conversationInterface();
                conv.show();

        }

    }
}