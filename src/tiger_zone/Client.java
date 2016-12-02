package tiger_zone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public final class Client {
	private final String address;//ip address of server
	private final int portNumber;//server port

	private Socket echoSocket;//server connection object
	private PrintWriter output;//used to send data to server
	private BufferedReader input, stdIn;//reads input from server and user, respectively

	public Client()
	{
		this.address = "0.0.0.0";
		this.portNumber = 0;
		this.echoSocket = null;
		this.output = null;
		this.input = null;
		this.stdIn = null;
	}

	/**
	 * primary constructor that initializes the Client with usable server info
	 * 
	 * @param address IP address of the server
	 * @param portNumber port that the tcp communication works through
	 */
	public Client(String address, int portNumber)
	{
		this.address = address;
		this.portNumber = portNumber;
		this.echoSocket = null;
		this.output = null;
		this.input = null;
		this.stdIn = null;

		//tries to connect the server, if it connects do logic
		this.connect();
	}

	/**
	 * attempts to make the connection with the server provided a name and port
	 * @param serverName the IP address of the given server
	 * @param portNumber the port that the tcp socket communicates with
	 * @return true if the connection was successful, false if it failed
	 */
	private final boolean connect()
	{
		System.out.println("Attemping to connect to host " + this.address + ":" + this.portNumber);
		
		try {
			//getting the socket to the server
			this.echoSocket = new Socket(this.address, this.portNumber);
			//out is used to send information to the server
			this.output = new PrintWriter(this.echoSocket.getOutputStream(), true);
			//inis used to recieve information from the server
			this.input = new BufferedReader(new InputStreamReader(this.echoSocket.getInputStream()));
			//stdIn is used to get user input
			this.stdIn = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Client connection to " + this.address + ":" + this.portNumber
					+ (this.isConnected() ? " succeeded." : " failed."));
			return true;
		} catch (UnknownHostException e) {
			System.err.println("Host does not exist: " + this.address);
			return false;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: " + this.address);
			return false;
		}
	}

	/**
	 * closes out of the streams and disconnects from the srever
	 */
	public final void disconnect()
	{
		try {
			this.output.close();
			this.input.close();
			this.stdIn.close();
			this.echoSocket.close();
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
	public final void sendToServer(String input)
	{
		this.output.println(input.trim());
	}

	/**
	 * Gets a string from the server
	 * @return the servers response, null if there is a disconnection
	 */
	public final String receiveFromServer()
	{
		try {
			String response = this.input.readLine().trim();
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
	private final boolean isConnected()
	{
		//a socket will remain "connected" state if it is just closed
		//so we have to ensure that it is connected and currently open
		return this.echoSocket.isConnected() && (!this.echoSocket.isClosed());
	}
}
