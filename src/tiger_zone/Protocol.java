package tiger_zone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import tiger_zone.action.Action;
import tiger_zone.action.CrocodilePlacementAction;
import tiger_zone.action.PassAction;
import tiger_zone.action.TigerRetrievalAction;
import tiger_zone.action.TigerAdditionAction;
import tiger_zone.action.TigerPlacementAction;
import tiger_zone.action.TilePlacementAction;
import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.CloseAiPlayer;
import tiger_zone.ai.NetworkAiPlayer;

public class Protocol extends Client {
	private String tPassword, pid, opid, gid, cid, rounds, rid, number_tiles, time, userName, password;
	private String[] tiles;
	private Board board;
	private Stack<Tile> pile;
	private final Map<String, Game> games = new HashMap<String, Game>();
	
	public Protocol(String serverName, int portNumber, String tPassword, String userName, String password) {
		super(serverName, portNumber);
		this.setPid("-1");
		this.setTournamentPassword(tPassword);
		this.setUserName(userName);
		this.setPassword(password);
	}
	
	public final void makeMove(Tile current, String x, String y, String orientation, String zoneIndex, String gid) {
		Position pos = new Position(Integer.parseInt(x), Integer.parseInt(y));
		Game gameAlias = null;
		
		gameAlias = games.get(gid);
			
		while(current.getRotation() != Integer.parseInt(orientation)){
			current.rotate();
		}
		
		gameAlias.getBoard().addTile(pos, current);
		gameAlias.nextPlayer();
		Tiger tiger = null;
		
		if (zoneIndex.equals("")) {
			return;
		}
		else {
			current.addTiger(current.getZone(Integer.parseInt(zoneIndex)), tiger);
			return;
		}
	}
	
	
	public void tournamentProtocol()
	{
		String fromServer = this.receiveFromServer();
		// THIS IS SPARTA! only called once
		System.out.println("Server: " + fromServer);
		
		authenticationProtocol(fromServer);
		
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
				tile = tok.toLowerCase();
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
		Board.createDefaultStack();
		for (String s : tiles) {
			pile.push(Board.tileMap.get(s.toLowerCase()).clone());
		}

		Tile startingTile = Board.tileMap.get(tile.toLowerCase()).clone();
		this.board = new Board((Stack<Tile>)pile.clone(), startingTile, new Position(x, y), orientation);
		
		for (int i = 0; i < Integer.parseInt(number_tiles); i++) {
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
		String tok = strTok.nextToken();
		if (!tok.equals("MAKE")) {
			analyzeMove(moveInstruction);
		}
		else {
			for (int i = 1; strTok.hasMoreTokens(); i++) {
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
			
			// we do not have an instance of Game for this gid
			if (!games.containsKey(moveGid)) {
				Game game = new Game(this.board.clone());
				
				List<Player> players = new ArrayList<Player>(2);
				List<AiPlayer> aiPlayers = new ArrayList<AiPlayer>(2);
				
				if (games.isEmpty()) {
					players.add(new Player(0, this.pid));
					players.add(new Player(1, this.opid));
					aiPlayers.add(new CloseAiPlayer(game));
					aiPlayers.add(new NetworkAiPlayer(game));
				}
				else {
					players.add(new Player(0, this.opid));
					players.add(new Player(1, this.pid));
					aiPlayers.add(new NetworkAiPlayer(game));
					aiPlayers.add(new CloseAiPlayer(game));
				}
				
				game.setPlayers(players);
				game.setAiPlayers(aiPlayers);
				
				this.games.put(moveGid, game);
			}
			
			final Action action = this.games.get(moveGid).conductTurn();
			
			String input = "";
			if (action instanceof TilePlacementAction) {
				TilePlacementAction tpa = (TilePlacementAction) action;
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT " 
						+ tpa.getPosition().getX() + " " + tpa.getPosition().getY() +  " "
						+ tpa.getTile().getRotation() + " NONE ";
			}
			else if (action instanceof CrocodilePlacementAction) {
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT " 
						+ moveX + " " + moveY +  " " + moveOrientation + " CROCODILE ";
			}
			else if (action instanceof TigerPlacementAction) {
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT " 
						+ moveX + " " + moveY +  " " + moveOrientation + " TIGER " + moveZone + " ";
			}
			else if (action instanceof PassAction) {
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " UNPLACEABLE PASS";
			}
			else if (action instanceof TigerRetrievalAction) {
				TigerRetrievalAction tra = (TigerRetrievalAction) action;
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile
						+ " UNPLACEABLE RETRIEVE TIGER AT " + tra.getPosition().getX() + " "
						+ tra.getPosition().getY() + " ";
			}
			else if (action instanceof TigerAdditionAction) {
				TigerAdditionAction taa = (TigerAdditionAction) action;
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile
						+ " UNPLACEABLE ADD ANOTHER TIGER TO " + taa.getPosition().getX() + " "
						+ taa.getPosition().getY() + " ";
			}
			else {
				input = "GAME " + moveGid + " MOVE " + moveNumber +" PLACE " + moveTile + " AT " 
						+ moveX + " " + moveY +  " " + moveOrientation + " NONE ";
			}
			
			sendToServer(input);
			
			for (int i = 0; i < games.size(); i++) {
				this.analyzeMove(this.receiveFromServer());
			}
		}
	}
	
	public void analyzeMove(String fromServer)
	{
		String tok = "";
		String moveNumber = "movenumber";
		String moveTile = "movetile";
		String moveGid = "movegid";
		String movePid = "movepid";
		String moveTime  = "movetime";
		String moveX = "movex";
		String moveY = "movey";
		String moveOrientation = "moveorientation";
		String moveZone = "movezone";
		boolean forfeit = false;
		System.out.println("Server: " + fromServer);
		StringTokenizer strTok = new StringTokenizer(fromServer, " ");
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
					if(tok.equals("PLACED"))
					{
						moveTile = (strTok.hasMoreTokens()) ? strTok.nextToken() : moveTile;
						strTok.nextToken();//AT
						moveX = (strTok.hasMoreTokens()) ? strTok.nextToken() : moveX;
						moveY = (strTok.hasMoreTokens()) ? strTok.nextToken() : moveY;
						moveOrientation = (strTok.hasMoreTokens()) ? strTok.nextToken() : moveOrientation;
						if(strTok.hasMoreTokens()){
							strTok.nextToken();//tiger
							moveZone = (strTok.hasMoreTokens()) ? strTok.nextToken() : moveZone;
						}
					}
				}
				else 
					forfeit = true;
				break;
			default:
				break;
			}
		}
		if(forfeit) {
			games.remove(moveGid);
		}
		if(!movePid.equals(pid))
		{
			makeMove(Board.tileMap.get(moveTile.toLowerCase()).clone(),moveX,moveY,moveOrientation,moveZone, moveGid);
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