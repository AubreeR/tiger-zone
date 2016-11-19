package tiger_zone;

import java.lang.*;
import java.io.*;
import java.net.*;

public class Client {
	private String serverName;
	private int portNumber;
	private Socket echoSocket;
	private PrintWriter output;
	private BufferedReader input, stdIn;
	
   public Client()
   {
	   serverName = new String ("0.0.0.0");
	   portNumber = 0;
       System.out.println ("Attemping to connect to host " +
		serverName + " on port " + portNumber);
       
       echoSocket = null;
       output = null;
       input = null;
       stdIn = null;

       try {
           //getting the socket to the server
           echoSocket = new Socket(serverName,portNumber);
           //out is used to send information to the server
           output = new PrintWriter(echoSocket.getOutputStream(), true);
           //inis used to recieve information from the server
           input = new BufferedReader(new InputStreamReader(
                                       echoSocket.getInputStream()));
           //stdIn is used to get user input
           stdIn = new BufferedReader(
                   new InputStreamReader(System.in));
           String userInput;

           System.out.print ("input: ");
           while ((userInput = stdIn.readLine()) != null) 
           {
        	   output.println(userInput);//send input to the server
        	   System.out.println("echo: " + input.readLine());//ouput the server's response
               System.out.print ("input: ");
           }

           output.close();
           input.close();
           stdIn.close();
           echoSocket.close();
       } catch (UnknownHostException e) {
           System.err.println("Host Does Not Exist: " + serverName);
           System.exit(1);
       } catch (IOException e) {
           System.err.println("Couldn't get I/O for the connection to: " + serverName);
           System.exit(1);
       }
   }
   
   
   

}