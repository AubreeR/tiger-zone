package tiger_zone;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum ProtocolState
{
	TOURNAMENT,
	AUTHENTICATION,
	CHALLENGE,
	ROUND,
	MATCH,
	MOVE,
	UNDEFINED
}

public class Protocol extends Client
{
	private String tPassword, pid, opid, gid, cid, rounds, rid, tile, x, y, orientation,
	number_tiles, time, userName, password;
	private String[] tiles;
	private ProtocolState pState; 
	
	public Protocol(String serverName, int portNumber, String tPassword, String userName, String password)
	{
		super(serverName, portNumber);
		setPid("-1");
		setProtocolState(ProtocolState.UNDEFINED);
		setTournamentPassword(tPassword);
		setUserName(userName);
		setPassword(password);
		
	}
	
	
	public void tournamentProtocol()
	{
		
		
		String fromServer = this.receiveFromServer();
		
		System.out.println("Server: " + fromServer);
		if(fromServer.equals("THIS IS SPARTA!"))
		{
			authenticationProtocol(fromServer);
		}
	}
	
	public void authenticationProtocol(String fromServer)
	{
		
		String toServer = "Join " + this.getTournamentPassword();
		sendToServer(toServer);
		System.out.println("Client: " + toServer);
		
		fromServer = receiveFromServer();
		System.out.println("Server: "  + fromServer);
		
				
		toServer = "I AM " + getUserName() + " " + getPassword();
		sendToServer(toServer);
		System.out.println("Client: " + toServer);
		
		StringTokenizer strTok;
		
		fromServer = receiveFromServer();
		strTok = new StringTokenizer(fromServer, " ");
		String tok = "";
		System.out.println("Server: "  + fromServer);
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 1: 
				pid = tok;
				break;
			default:
				break;
			}
		}
		challengeProtocol(receiveFromServer());
	}
	
	public void challengeProtocol(String fromServer)
	{
		System.out.println("Server: " + fromServer);
		StringTokenizer strTok = new StringTokenizer(fromServer," ");
		String tok = "";
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 2 : 
				cid = tok;
				break;
			case 6 : 
				rounds = tok;
				break;
			default:
				break;
			}

		}
		System.out.println("CID: " + cid + " ROUNDS: " + rounds);
		
		for(int i = 0; i < Integer.parseInt(rounds); i++)
		{
			roundProtocol(receiveFromServer());
		}
		
		fromServer = receiveFromServer();
		//END OF CHALLENGES or PLEASE WAIT FOR NEXT CHALLEGE
	}
	
	public void roundProtocol(String fromServer)
	{
 
		System.out.println("Server: " + fromServer);
		StringTokenizer strTok = new StringTokenizer(fromServer, " ");
		String tok = "";
		for(int i = 0;strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 2 : 
				rid = tok;
				break;
			case 5 : 
				rounds = tok;
				break;
			default:
				break;
			}
		}
		
		System.out.println("rid: " + rid + ", rounds = " + rounds);
		
		for(int i = 0; i < Integer.parseInt(rounds); i++)
		{
			matchProtocol(receiveFromServer());
		}
			
	}
	
	public void matchProtocol(String fromServer)
	{
		System.out.println("Server: " + fromServer);
		StringTokenizer strTok = new StringTokenizer(fromServer, " ");
		String tok = "";
		
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 4 :
				opid  = tok;
				break;
			default:
				break;
			}
			
		}
		System.out.println("opid: " + opid);

		///// Line 2 - Get tile, x, y, and orientation //////
		
		fromServer = receiveFromServer();
		System.out.println("Server: " + fromServer);
		strTok = new StringTokenizer(fromServer, " ");
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 3 : 
				tile = tok;
				break;
			case 5 :
				x = tok;
				break;
			case 6 : 
				y = tok;
				break;
			case 7 : 
				orientation = tok;
				break;

			default:
				break;
			}
			
		}
		System.out.println("tile: " + tile + ", x: " + x + ", y: " + y + ", orientation: " + orientation);

		////// Line 3 - Get number of tiles and tile stack //////
		
		fromServer = receiveFromServer();		
		System.out.println("Server: " + fromServer);
		strTok = new StringTokenizer(fromServer, " ");
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 2 : 
				number_tiles = tok;	
				tiles = new String[Integer.parseInt(number_tiles)];
				break;
			
			default:
				if(i >= 6 && i - 6 < tiles.length)
					tiles[i-6] = tok;
				break;
			}
		}
		System.out.print("Tile #: " + number_tiles+ ", Tile stack: ");
		for(String s : tiles)
			System.out.print(s + " ");
		System.out.println("");
		////// Line 4 - Getting time until match start //////
		fromServer = receiveFromServer();		
		System.out.println("Server: " + fromServer);
		strTok = new StringTokenizer(fromServer, " ");
		for(int i = 0;strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			
			switch(i)
			{
			case 3 : 
				time = tok;
				break;
			default:
				break;
			}
		}
		System.out.println("Time till match: " + time);
		
		for(int i = 0; i < Integer.parseInt(number_tiles); i++)
		{
			moveProtocol(receiveFromServer());
		}
	}
	
	
	public void moveProtocol(String clientMove){
		sendToServer(clientMove);
		System.out.println(clientMove);
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTournamentPassword() {
		return tPassword;
	}

	public void setTournamentPassword(String tPassword) {
		this.tPassword = tPassword;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public ProtocolState getProtocolState() {
		return pState;
	}

	public void setProtocolState(ProtocolState pState) {
		this.pState = pState;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getRounds() {
		return rounds;
	}


	public void setRounds(String rounds) {
		this.rounds = rounds;
	}
	
	
}