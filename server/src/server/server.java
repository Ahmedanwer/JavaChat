package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class server {



    public static void main(String[] args) {
        // TODO code application logic here
        try {
            //1.Create Server Socket
            ServerSocket sv = new ServerSocket(1243);
            //Server is always On
            while (true) {
                //2.Listen for Clients
                Socket c;
                c = sv.accept();
                //indicate the arrival of a new client
                System.out.println("New Client Arrived");
                //3.Create IO Streams
                DataOutputStream dos = new DataOutputStream(c.getOutputStream());
                DataInputStream dis = new DataInputStream(c.getInputStream());
                //4.Perform IO Operations with the client
                while (true) {
                    String clientMsg;
                    clientMsg = dis.readUTF();//read from the client
                    dos.writeUTF(clientMsg);//Echo the msg back to the client            
                    if (clientMsg.equalsIgnoreCase("Bye")) {
                        break;
                    }
                }
                //5.Close/release resources
                dis.close();
                dos.close();
                c.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}