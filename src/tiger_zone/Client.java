package tiger_zone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	protected String serverName;//ip address of server
	protected int portNumber;//server port

	private Socket echoSocket;//server connection object
	private PrintWriter output;//used to send data to server
	private BufferedReader input, stdIn;//reads input from server and user, respectively

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
	}

	/**
	 * primary constructor that initializes the Client with usable server info
	 * @param serverName IP address of the server
	 * @param portNumber port that the tcp communication works through
	 */
	public Client(String serverName, int portNumber)
	{
		this.serverName = serverName;
		this.portNumber = portNumber;
		System.out.println ("Attemping to connect to host " +
				serverName + ":" + portNumber);

		echoSocket = null;
		output = null;
		input = null;
		stdIn = null;

		//tries to connect the server, if it connects do logic
		connect(this.serverName, this.portNumber);

	}

	/**
	 * attempts to make the connection with the server provided a name and port
	 * @param serverName the IP address of the given server
	 * @param portNumber the port that the tcp socket communicates with
	 * @return true if the connection was successful, false if it failed
	 */
	public boolean connect(String serverName, int portNumber)
	{
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
			System.out.println("Client connection to " + serverName + ":" + portNumber + (this.isConnected() ? " succeeded." : " failed."));
			return true;
		} catch (UnknownHostException e) {
			System.err.println("Host Does Not Exist: " + serverName);
			return false;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + serverName);
			return false;
		}
	}

	/**
	 * closes out of the streams and disconnects from the srever
	 */
	public void disconnect()
	{
		try{
			output.close();
			input.close();
			stdIn.close();
			echoSocket.close();
		}
		catch(IOException io)
		{
			System.err.println(io);
		}
	}

	/**
	 * Sends data to the server over a PrintWriter
	 * @param input the string value to send to the server
	 */
	public void sendToServer(String input)
	{
		output.println(input.trim());
	}

	/**
	 * Gets a string from the server
	 * @return the servers response, null if there is a disconnection
	 */
	public String receiveFromServer()
	{
		try{
			String response = input.readLine().trim();
			System.out.printf("Server: %s\n", response);
			return response;
		}
		catch(IOException io)
		{
			System.err.println(io);
			return null;
		}
	}
	
	/**
	 * isConnected tests if the socket connection is still usable
	 * @return true if socket connection is usable, false if it is dead
	 */
	public boolean isConnected()
	{
		//a socket will remain "connected" state if it is just closed
		//so we have to ensure that it is connected and currently open
		return echoSocket.isConnected() && (!echoSocket.isClosed());
	}

	/**
	 * 
	 * @return the name of the active 
	 */
	public String getServerName()
	{
		return serverName;
	}

	public int getPortNumber()
	{
		return portNumber;
	}
}
