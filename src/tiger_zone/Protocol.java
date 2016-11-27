package tiger_zone;

import java.util.StringTokenizer;


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
	
	public String getOpid(){
		return opid;
	}
	
	public void setOpid(String opid){
		this.opid = opid;
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
	
	public String getRid() {
		return rid;
	}


	public void setRid(String rid) {
		this.rid = rid;
	}


	public String getTile() {
		return tile;
	}


	public void setTile(String tile) {
		this.tile = tile;
	}


	public String getX() {
		return x;
	}


	public void setX(String x) {
		this.x = x;
	}


	public String getY() {
		return y;
	}


	public void setY(String y) {
		this.y = y;
	}


	public String getOrientation() {
		return orientation;
	}


	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}


	public String getNumber_tiles() {
		return number_tiles;
	}


	public void setNumber_tiles(String number_tiles) {
		this.number_tiles = number_tiles;
	}


	public String[] getTiles() {
		return tiles;
	}


	public void setTiles(String[] tiles) {
		this.tiles = tiles;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}	
}