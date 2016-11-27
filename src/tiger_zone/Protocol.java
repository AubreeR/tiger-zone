package tiger_zone;

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
	number_tiles, tiles, time, userName, password;

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
		if(!fromServer.equals("THIS IS SPARTA!"))
			return;
		String toServer = "Join " + this.getTournamentPassword();
		sendToServer(toServer);
		System.out.println("Client: " + toServer);
		
		fromServer = receiveFromServer();
		System.out.println("Server: "  + fromServer);
		//THIS ALWAYS GOES TO FALSE FOR SOMEREASON
		//boolean serverTest = "Hello!".equals(fromServer.trim());
		//if(!serverTest)
		//	return;
		toServer = "I AM " + getUserName() + " " + getPassword();
		sendToServer(toServer);
		System.out.println("Client: " + toServer);
		
		fromServer = receiveFromServer();
		System.out.println("Server: "  + fromServer);
		
		for(int i = 0; i < fromServer.length(); i++)
		{
			char j = fromServer.charAt(i);
			
			if(j == ' ')
			{
				pid = "";
				for(int k = i + 1; k < fromServer.length(); k++)
				{
					if(fromServer.charAt(k) == ' ')
						break;
					pid = pid + fromServer.charAt(k);
				}
				break;
			}
		}
		challengeProtocol(receiveFromServer());
	}
	
	public void challengeProtocol(String fromServer)
	{
		System.out.println("Server: " + fromServer);
		int spaceCount = 0;
		for(int i = 0; i < fromServer.length(); i++)
		{
			if(fromServer.charAt(i) == ' ')
				spaceCount++;
			switch(spaceCount)
			{
			case 2 : cid = "";
				for(int k = i+1; k < fromServer.length(); k++)
				{
					if(fromServer.charAt(k) == ' ')
					{
						spaceCount++;
						i = k;
						break;
					}
					cid = cid + fromServer.charAt(k);
				}
				break;
			case 6 : rounds = "";
				for(int k = i+1; k < fromServer.length(); k++)
				{
					if(fromServer.charAt(k) == ' ')
					{
						spaceCount++;
						i = k;
						break;
					}
					rounds = rounds + fromServer.charAt(k);
				}
				break;
			default:
				break;
			}

		}
		
		
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
		int spaceCount = 0;
		for(int i = 0; i < fromServer.length(); i++)
		{
			if(fromServer.charAt(i) == ' ')
				spaceCount++;
			switch(spaceCount)
			{
			case 2 : rid = "";	
			for(int k = i+1; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ' ')
				{
					spaceCount++;
					i = k;
					break;
				}
				rid = rid + fromServer.charAt(k);
			}
			break;
			
			case 5 : rounds = "";
			for(int k = i+1; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == 'P')	// If "please" is contained in string, more rounds are to come
				{
					return;
				}
				rounds = rounds + fromServer.charAt(k);
			}
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
		int spaceCount = 0;
		for(int i = 0; i < fromServer.length(); i++)
		{
			if(fromServer.charAt(i) == ' ')
				spaceCount++;
			switch(spaceCount)
			{
			case 4 : opid = "";	
			for(int k = i+1; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ' ')
				{
					spaceCount++;
					i = k;
					break;
				}
				
				opid = opid + fromServer.charAt(k);
				if(k + 1 == fromServer.length())
					i = k;
			}
			break;
			
			default:
				break;
			}
			
		}
		System.out.println("opid: " + opid);

		///// Line 2 - Get tile, x, y, and orientation //////
		
		fromServer = receiveFromServer();
		System.out.println("Server: " + fromServer);
		spaceCount = 0;
		for(int i = 0; i < fromServer.length(); i++)
		{
			
			if(fromServer.charAt(i) == ' ')
				spaceCount++;
			switch(spaceCount)
			{
			case 3 : tile = "";	
			for(int k = i+1; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ' ')
				{
					spaceCount++;
					i = k;
					break;
				}
				tile = tile + fromServer.charAt(k);
			}
			break;
			
			case 5 : x = "";	
			for(int k = i; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ' ' && k != i)
				{
					spaceCount++;
					i = k;
					break;
				}
				if(fromServer.charAt(k) != ' ')
				x = x + fromServer.charAt(k);
			}
			break;
			
			case 6 : y = "";	
			for(int k = i; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ' '&& k != i)
				{
					spaceCount++;
					i = k;
					break;
				}
				if(fromServer.charAt(k) != ' ')

				y = y + fromServer.charAt(k);
			}
			break;
			
			case 7 : orientation = "";	
			for(int k = i; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ' ' && k != i)
				{
					spaceCount++;
					i = k;
					break;
				}
				if(fromServer.charAt(k) != ' ')
					orientation = orientation + fromServer.charAt(k);
				if(k + 1 == fromServer.length())
					i = k;
			}
			break;
			
			
			default:
				break;
			}
			if(i == fromServer.length())
				break;
		}
		System.out.println("tile: " + tile + ", x: " + x + ", y: " + y + ", orientation: " + orientation);

		////// Line 3 - Get number of tiles and tile stack //////
		
		fromServer = receiveFromServer();		
		System.out.println("Server: " + fromServer);
		spaceCount = 0;
		for(int i = 0; i < fromServer.length(); i++)
		{
			if(fromServer.charAt(i) == ' ')
				spaceCount++;
			switch(spaceCount)
			{
			case 2 : number_tiles = "";	
			for(int k = i+1; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ' ')
				{
					spaceCount++;
					i = k;
					break;
				}
				number_tiles = number_tiles + fromServer.charAt(k);
			}
			break;
			
			case 6 : tiles = "";	
			for(int k = i+1; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ']')
				{
					spaceCount++;
					i = k;
					break;
				}
				tiles = tiles + fromServer.charAt(k);
				if(k + 1 == fromServer.length())
					i = k;
			}
			System.out.println("Tile #: " + number_tiles + ", Tile stack: " + tiles);
			break;
			
		default:
			break;
			}
		}
		
		////// Line 4 - Getting time until match start //////
		fromServer = receiveFromServer();		
		System.out.println("Server: " + fromServer);
		spaceCount = 0;
		for(int i = 0; i < fromServer.length(); i++)
		{
			if(fromServer.charAt(i) == ' ')
				spaceCount++;
			switch(spaceCount)
			{
			case 3 : time = "";	
			for(int k = i+1; k < fromServer.length(); k++)
			{
				if(fromServer.charAt(k) == ' ')
				{
					spaceCount++;
					i = k;
					break;
				}
				time = time + fromServer.charAt(k);
			}
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


	public String getTiles() {
		return tiles;
	}


	public void setTiles(String tiles) {
		this.tiles = tiles;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}	
}