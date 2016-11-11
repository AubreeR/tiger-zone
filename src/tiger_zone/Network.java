package tiger_zone;

import java.lang.*;
import java.io.*;
import java.net.*;

class Client {
   Client()
   {
	   String serverHostname = new String ("");

       System.out.println ("Attemping to connect to host " +
		serverHostname + " on port 8000.");

       Socket echoSocket = null;
       PrintWriter out = null;
       BufferedReader in = null;
       BufferedReader stdIn = null;

       try {
           // echoSocket = new Socket("", );
           echoSocket = new Socket(serverHostname,8000);
           out = new PrintWriter(echoSocket.getOutputStream(), true);
           in = new BufferedReader(new InputStreamReader(
                                       echoSocket.getInputStream()));
           stdIn = new BufferedReader(
                   new InputStreamReader(System.in));
           String userInput;

           System.out.print ("input: ");
           while ((userInput = stdIn.readLine()) != null) 
           {
        	   out.print(userInput);
        	   System.out.println("echo: " + in.readLine());
               System.out.print ("input: ");
           }

           out.close();
           in.close();
           stdIn.close();
           echoSocket.close();
       } catch (UnknownHostException e) {
           System.err.println("Don't know about host: " + serverHostname);
           System.exit(1);
       } catch (IOException e) {
           System.err.println("Couldn't get I/O for "
                              + "the connection to: " + serverHostname);
           System.exit(1);
       }
   }

}