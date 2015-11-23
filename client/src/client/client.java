package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class client {


    public static void main(String[] args) {
        {
            try {
                //1.Create Client Socket and connect to the server
                Socket client = new Socket("localhost", 1243);
                //2.if accepted create IO streams
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                DataInputStream dis = new DataInputStream(client.getInputStream());
                //Create a Scanner to read inputs from the user
                Scanner sc = new Scanner(System.in);
                String userInput;
                //3.Perform IO operations with the server
                while (true) {
                    //read from the user
                    userInput = sc.nextLine();
                    dos.writeUTF(userInput);
                    String response;
                    //read the response from the server
                    response = dis.readUTF();
                    //Print response
                    System.out.println(response);
                    if (response.equalsIgnoreCase("Bye")) {
                        break;
                    }
                }

                //4.Close/release resources
                dis.close();
                dos.close();
                client.close();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}