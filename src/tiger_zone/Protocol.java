package tiger_zone;

import java.util.Stack;
import java.util.StringTokenizer;

public class Protocol extends Client {
	private String tPassword, pid, opid, gid, cid, rounds, rid, number_tiles, time, userName, password;
	private String[] tiles;
	private Game game1 = null;
	private Game game2 = null;
	
	public Protocol(String serverName, int portNumber, String tPassword, String userName, String password)
	{
		super(serverName, portNumber);
		setPid("-1");

		setTournamentPassword(tPassword);
		setUserName(userName);
		setPassword(password);
	}
	
	
	public void tournamentProtocol()
	{
		String fromServer = this.receiveFromServer();
		//THIS IS SPARTA! only caleld once
		System.out.println("Server: " + fromServer);
		if(fromServer.equals("THIS IS SPARTA!"))
		{
			authenticationProtocol(fromServer);
		}
	}
	
	public void authenticationProtocol(String fromServer)
	{
		//send username and password
		String toServer = "Join " + this.getTournamentPassword();
		sendToServer(toServer);
		System.out.println("Client: " + toServer);
		//hello
		fromServer = receiveFromServer();
		System.out.println("Server: "  + fromServer);
		
		//identifying ourself
		toServer = "I AM " + getUserName() + " " + getPassword();
		sendToServer(toServer);
		System.out.println("Client: " + toServer);
		
		StringTokenizer strTok;
		//WELCOME <pid> PLEASE WAIT FOR THE NEXT CHALLENGE 
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
		
		while(challengeProtocol(receiveFromServer()))
		{}
	}
	
	public boolean challengeProtocol(String fromServer)
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
		
		//END OF CHALLENGES or PLEASE WAIT FOR NEXT CHALLEGE
		fromServer = receiveFromServer();
		strTok = new StringTokenizer(fromServer, " ");
		int count = strTok.countTokens();
		if(count < 4)
			return false;//no more challenges
		return true;
		
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
		String tile = null;
		int x = 0;
		int y = 0;
		int orientation = 0;
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 3 : 
				tile = tok;
				break;
			case 5 :
				x = Integer.parseInt(tok);
				break;
			case 6 : 
				y = Integer.parseInt(tok);
				break;
			case 7 : 
				orientation = Integer.parseInt(tok);
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
		
		Stack<Tile> pile = new Stack<Tile>();
		
		for (String s : tiles) {
			pile.push(Board.tileMap.get(s.toLowerCase()).clone());
		}
		
		Board board = new Board((Stack<Tile>)pile.clone());
		Tile startingTile = Board.tileMap.get(tile.toLowerCase()).clone();
		
		while (startingTile.getRotation() != orientation) {
			startingTile.rotate();
		}
		
		board.addTileWithNoValidation(new Position(x, y), startingTile);
		game1 = new Game(board.clone());
		game2 = new Game(board.clone());
		
		for(int i = 0; i < Integer.parseInt(number_tiles); i++)
		{
			moveProtocol(receiveFromServer());
		}
		//get first game score
		fromServer = receiveFromServer();		
		System.out.println("Server: " + fromServer);
		//get second game score
		fromServer = receiveFromServer();		
		System.out.println("Server: " + fromServer);
	}
	
	
	public void moveProtocol(String moveInstruction){
		String moveNumber = "movenumber";
		String moveTile = "movetile";
		String moveGid = "movegid";
		String movePid = "movepid";
		String moveTime  = "movetime";
		String moveX = "movex";
		String moveY = "movey";
		String moveOrientation = "moveorientation";
		String moveZone = "movezone";
		
		//LINE 1 -- MAKE YOUR MOVE IN GAME <gid> WITHIN <moveTime> SECOND/SECONDS: MOVE <moveNumber> PLACE <moveTile>
		System.out.println("Server: " + moveInstruction);
		StringTokenizer strTok = new StringTokenizer(moveInstruction, " ");
		String tok = "";
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 5:
				moveGid = tok;
				break;
			case 7:
				moveTime = tok;
				break;
			case 10:
				moveNumber = tok;
				break;
			case 12:
				moveTile = tok;
				break;
			default:
					break;
			}
		}
		System.out.println("moveGid: " + moveGid + " moveTime: " + moveTime + " moveNumber: " + moveNumber + " moveTile: " + moveTile);
		
		
		//SEND OUR MOVE
		//getMove()
		String input = "";
		switch(0/*getMoveValue*/)
		{
		case 0 : 
			input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT " 
					+ moveX + " " + moveY +  " " + moveOrientation + " NONE ";
			break;
		case 1 : 
			input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT " 
					+ moveX + " " + moveY +  " " + moveOrientation + " CROCODILE ";
			break;	
		case 2 : 
			input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT " 
					+ moveX + " " + moveY +  " " + moveOrientation + " TIGER " + moveZone + " ";
			break;
		case 3 : 
			input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " UNPLACEABLE PASS";
			break;
		case 4 : 
			input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " UNPLACEABLE RETRIEVE TIGER AT "
					+ moveX + " " + moveY + " ";
			break;
		case 5 : 
			input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " UNPLACEABLE ADD ANOTHER TIGER TO "
					+ moveX + " " + moveY + " ";
			break;
		default:
			input = "GAME " + moveGid + " MOVE " + moveNumber +" PLACE " + moveTile + " AT " 
					+ moveX + " " + moveY +  " " + moveOrientation + " NONE ";
		}
		sendToServer(input);
		
		String fromServer = receiveFromServer();		
		System.out.println("Server: " + fromServer);
		strTok = new StringTokenizer(fromServer, " ");
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 1: 
				moveGid = tok;
				break;
			case 3: 
				moveNumber = tok;
				break;
			default:
				break;
			}
		}
		
		fromServer = receiveFromServer();		
		System.out.println("Server: " + fromServer);
		strTok = new StringTokenizer(fromServer, " ");
		for(int i = 0; strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();
			switch(i)
			{
			case 1: 
				moveGid = tok;
				break;
			case 3: 
				moveNumber = tok;
				break;
			case 5:
				movePid = tok;
				break;
			case 6:
				if(!tok.equals("FORFEITED"))
				{
					moveX = tok;
					moveY = (strTok.hasMoreTokens()) ? strTok.nextToken() : moveY;
					moveOrientation = (strTok.hasMoreTokens()) ? strTok.nextToken() : moveOrientation;
				}
				break;
			default:
				break;
			}
		}
		
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