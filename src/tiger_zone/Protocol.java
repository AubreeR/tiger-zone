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
// import tiger_zone.ui.Main;

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
		Game gameAlias = games.get(gid);

		while(current.getRotation() != Integer.parseInt(orientation)){
			current.rotate();
		}
		gameAlias.getBoard().getPile().pop();
		gameAlias.getBoard().addTile(pos, current);
		gameAlias.nextPlayer();

		System.out.printf("(game %s should now be our move)\n", gid);

		if (zoneIndex.equals("movezone")) {
			return;
		}

		Tiger tiger = null;

		current.addTiger(current.getZone(Integer.parseInt(zoneIndex)), tiger);
	}


	public void tournamentProtocol()
	{
		String fromServer = this.receiveFromServer();
		// THIS IS SPARTA! only called once

		authenticationProtocol(fromServer);

	}

	public void authenticationProtocol(String fromServer)
	{

		//send username and password
		String toServer = "JOIN " + this.getTournamentPassword();
		sendToServer(toServer);
		System.out.println("Client: " + toServer);
		//hello
		fromServer = receiveFromServer();

		//identifying ourself
		toServer = "I AM " + getUserName() + " " + getPassword();
		sendToServer(toServer);
		System.out.println("Client: " + toServer);

		StringTokenizer strTok;
		//WELCOME <pid> PLEASE WAIT FOR THE NEXT CHALLENGE
		fromServer = receiveFromServer();
		strTok = new StringTokenizer(fromServer, " ");
		String tok = "";
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

		challengeProtocol();
		fromServer = receiveFromServer();
	}

	public void challengeProtocol()
	{
		int count = 0;
		do{

			String fromServer =	receiveFromServer();
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
			count = strTok.countTokens();
		}
		while(count >=5);


	}

	/**
	 * Parses round protocol messages:
	 *
	 * <pre>
	 * BEGIN ROUND <rid> OF <rounds>
	 * (match protocol)
 	 * END OF ROUND <rid> OF <rounds>
 	 * (or)
     * END OF ROUND <rid> OF <rounds> PLEASE WAIT FOR THE NEXT MATCH
	 * </pre>
	 * @param fromServer
	 */
	public void roundProtocol(String fromServer) {
		// BEGIN ROUND <rid> OF <rounds>
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

		for(int i = 0; i < Integer.parseInt(rounds); i++) {
			// Receive subsequent "BEGIN ROUND" messages (first one has already been parsed above, hence why we begin at
			// index 1.
			if (i > 0) {
				this.receiveFromServer();
			}

			matchProtocol(receiveFromServer());

			// END OF ROUND <rid> OF <rounds>
			// (or)
			// END OF ROUND <rid> OF <rounds> PLEASE WAIT FOR THE NEXT MATCH
			fromServer = receiveFromServer();
			System.out.println(i);
		}
	}

	/**
	 * Parses the following messages:
	 *
	 * <pre>
	 * YOUR OPPONENT IS PLAYER <pid>
	 * STARTING TILE IS <tile> AT <x> <y> <orientation>
	 * THE REMAINING <number_tiles> TILES ARE [ <tiles> ]
	 * MATCH BEGINS IN <timeplan> SECONDS
	 * (move protocol repeated <number_tiles> times, alternating the <gid>)
	 * GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
	 * GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
	 * </pre>
	 *
	 * @param fromServer
	 */
	public void matchProtocol(String fromServer)
	{
		// YOUR OPPONENT IS PLAYER <pid>
		StringTokenizer strTok = new StringTokenizer(fromServer, " ");
		String tok = "";

		for (int i = 0; strTok.hasMoreTokens(); i++) {
			tok = strTok.nextToken();
			switch(i) {
			case 4:
				opid  = tok;
				break;
			default:
				break;
			}
		}

		System.out.println("opid: " + opid);

		// STARTING TILE IS <tile> AT <x> <y> <orientation>
		fromServer = receiveFromServer();
		strTok = new StringTokenizer(fromServer, " ");
		String tile = "";
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

		// THE REMAINING <number_tiles> TILES ARE [ <tiles> ]
		fromServer = receiveFromServer();
		strTok = new StringTokenizer(fromServer, " ");
		for (int i = 0; strTok.hasMoreTokens(); i++) {
			tok = strTok.nextToken();
			switch(i) {
			case 2:
				number_tiles = tok;
				tiles = new String[Integer.parseInt(number_tiles)];
				break;
			default:
				if(i >= 6 && i - 6 < tiles.length) {
					tiles[i-6] = tok;
				}
				break;
			}
		}
		System.out.print("Tile #: " + number_tiles+ ", Tile stack: ");
		for(String s : tiles)
			System.out.print(s + " ");
		System.out.println("");

		// MATCH BEGINS IN <timeplan> SECONDS
		fromServer = receiveFromServer();
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
		for (int i = tiles.length - 1; i >= 0; i--) {
			pile.push(Board.tileMap.get(tiles[i].toLowerCase()).clone());
		}

		Tile startingTile = Board.tileMap.get(tile.toLowerCase()).clone();
		this.board = new Board((Stack<Tile>)pile.clone(), startingTile, new Position(x, y), orientation);

		// (move protocol repeated <number_tiles> times, alternating the <gid>)
		String result = "";
		for (int i = 0; i < Integer.parseInt(number_tiles) && result.length() == 0; i++) {
			result = moveProtocol(receiveFromServer());
		}

		String gameOverGid = "";

		if (result.length() > 0) {
			// GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
			fromServer = receiveFromServer();
			strTok = new StringTokenizer(fromServer, " ");
			for(int i = 0;strTok.hasMoreTokens(); i++) {
				tok = strTok.nextToken();

				switch(i) {
				case 1:
					gameOverGid = tok;
					break;
				default:
					break;
				}
			}

			// delete first game
			games.remove(gameOverGid);

			// GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
			fromServer = receiveFromServer();

			strTok = new StringTokenizer(fromServer, " ");
			for(int i = 0;strTok.hasMoreTokens(); i++) {
				tok = strTok.nextToken();

				switch(i)
				{
				case 1 :
					gameOverGid = tok;
					break;
				default:
					break;
				}
			}

			// delete second game
			games.remove(gameOverGid);

			// return early since we just handled both game overs
			return;
		}

		// GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
		fromServer = receiveFromServer();

		strTok = new StringTokenizer(fromServer, " ");
		for(int i = 0;strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();

			switch(i)
			{
			case 1 :
				gameOverGid = tok;
				break;
			default:
				break;
			}
		}

		// delete first game
		games.remove(gameOverGid);

		// GAME <gid> OVER PLAYER <pid> <score> PLAYER <pid> <score>
		fromServer = receiveFromServer();

		gameOverGid = "";
		strTok = new StringTokenizer(fromServer, " ");
		for(int i = 0;strTok.hasMoreTokens(); i++)
		{
			tok = strTok.nextToken();

			switch(i)
			{
			case 1 :
				gameOverGid = tok;
				break;
			default:
				break;
			}
		}

		// delete second game
		games.remove(gameOverGid);
	}


	/**
	 * Parses move protocol messages.
	 *
	 * @param moveInstruction
	 * @return
	 */
	public String moveProtocol(String moveInstruction){
		String moveNumber = "movenumber";
		String moveTile = "movetile";
		String moveGid = "movegid";
		String movePid = "movepid";
		String moveTime  = "movetime";
		String moveX = "movex";
		String moveY = "movey";
		String moveOrientation = "moveorientation";
		String moveZone = "movezone";

		// MAKE YOUR MOVE IN GAME <gid> WITHIN <moveTime> SECOND/SECONDS: MOVE <moveNumber> PLACE <moveTile>
		StringTokenizer strTok = new StringTokenizer(moveInstruction, " ");
		String tok = "";
		boolean gameOver = false;
		String overGid = "";
		String firstWord = strTok.nextToken();
		String secondWord = strTok.nextToken();
		String thirdWord = strTok.nextToken();

		// If we get a Forfeited message now, it is time to go back to Match Protocol
		if (moveInstruction.contains("FORFEITED:")) {
			return moveInstruction;
		}

		// Otherwise, it's opponent's move
		else if (!(firstWord.equals("MAKE"))) {
			analyzeMove(moveInstruction);
		}

		// or our own move -- MAKE MOVE
		else {
			for (int i = 3; strTok.hasMoreTokens(); i++) {
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

				players.add(new Player(0, this.pid));
				players.add(new Player(1, this.opid));
				aiPlayers.add(new CloseAiPlayer(game));
				aiPlayers.add(new NetworkAiPlayer(game));

				game.setPlayers(players);
				game.setAiPlayers(aiPlayers);

				this.games.put(moveGid, game);
			}

			final Action action = this.games.get(moveGid).conductTurn();

			String input = "";

			// CrocodilePlacement and TigerPlacement should be above TilePlacement here
			if (action instanceof CrocodilePlacementAction) {
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT "
						+ moveX + " " + moveY +  " " + moveOrientation + " CROCODILE ";
			}
			else if (action instanceof TigerPlacementAction) {
				TigerPlacementAction tpa = (TigerPlacementAction) action;
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT "
						+ tpa.getPosition().getX() + " " + tpa.getPosition().getY() +  " " + tpa.getTile().getRotation()
						+ " TIGER " + tpa.getZone() + " ";
			}
			else if (action instanceof PassAction) {
				input = "GAME " + moveGid + " MOVE " + moveNumber + " TILE " + moveTile + " UNPLACEABLE PASS";
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
			else if (action instanceof TilePlacementAction) {
				TilePlacementAction tpa = (TilePlacementAction) action;
				input = "GAME " + moveGid + " MOVE " + moveNumber + " PLACE " + moveTile + " AT "
						+ tpa.getPosition().getX() + " " + tpa.getPosition().getY() +  " "
						+ tpa.getTile().getRotation() + " NONE ";
			}
			else {
				if (action == null) {
					System.err.printf("null action recevied\n");
				}
				else {
					System.err.println("unknown action type");
				}
			}

			System.out.println("Client: " + input);
			sendToServer(input);

			for (int i = 0; i < games.size(); i++) {
				this.analyzeMove(this.receiveFromServer());
			}
		}
		return "";
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
				if(!tok.equals("FORFEITED:"))
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

		// we do not have an instance of Game for this gid
		if (!games.containsKey(moveGid)) {
			Game game = new Game(this.board.clone());

			List<Player> players = new ArrayList<Player>(2);
			List<AiPlayer> aiPlayers = new ArrayList<AiPlayer>(2);

			players.add(new Player(0, this.opid));
			players.add(new Player(1, this.pid));
			aiPlayers.add(new NetworkAiPlayer(game));
			aiPlayers.add(new CloseAiPlayer(game));

			game.setPlayers(players);
			game.setAiPlayers(aiPlayers);

			this.games.put(moveGid, game);
		}

		if(forfeit) {
			// Main.displayGame(games.get(moveGid));
			games.remove(moveGid);
			return;
		}

		// TODO: figure out why we need containsKey here; any game overs should not make it this far
		if(!movePid.equals(pid) && games.containsKey(moveGid))
		{
			System.out.printf("(about to makeMove, moveTile must be defined: %s)\n", moveTile);
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
