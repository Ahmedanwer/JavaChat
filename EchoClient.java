import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Eslam
 */
public class EchoClient {

    public static void main(String[] args) {
        {
            try {
                //1.Create Client Socket and connect to the server
                Socket client = new Socket("127.0.0.1", 1234);
                //2.if accepted create IO streams
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                DataInputStream dis = new DataInputStream(client.getInputStream());
                //Create a Scanner to read inputs from the user
                Scanner sc = new Scanner(System.in);
                Scanner sc1 = new Scanner(System.in);
                String username;
                        String password;
                //3.Perform IO operations with the server
                while (true) {
                    //read from the user
                    username = sc.nextLine();
                    password = sc1.nextLine();
                      System.out.println(username+password);
                  
                    dos.writeUTF(username+password);
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
