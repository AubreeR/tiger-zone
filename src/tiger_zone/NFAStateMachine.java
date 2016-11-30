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


public class NFAStateMachine {
	final String tournamentPassword = "TIGERZONE";
	final String userName = "TEAMJ";
	final String password = "IAMJ";
		  String ourPid = "";
		  String opponentPid = "";
		  String currentRound = "";
		  String maxRounds = "";
		  String challengeId = "";
		  String stackSize = "";
		  String startingTile = "";//<tile>
			
			String startingX = "";
			String startingY = "";
			String startingOrientation = "";
		  String tileStack[];
		  private Board board;
		  private Stack<Tile> pile;
		  private final Map<String, Game> games = new HashMap<String, Game>();
		  Client c;
	public NFAStateMachine(String server, int port) {
		// TODO Auto-generated method stub
		c = new Client(server, port);
		String fromServer = c.receiveFromServer();
		System.out.println("Server: "  + fromServer);
		boolean ref = firstWord(fromServer);
		do
		{
			
			fromServer = c.receiveFromServer();
			System.out.println("Server: "  + fromServer);
			ref = firstWord(fromServer);
		}while(ref);
	}
	
	public boolean firstWord(String protocol)
	{
		StringTokenizer str = new StringTokenizer(protocol, " ");
		String token = str.nextToken();
		switch(token)
		{
		case "THANK"://end of the game
			return false;
			
		case "THIS": thisProtocol();
			break;
		case "HELLO!": helloProtocol();
			break;
		case "WELCOME": welcomeProtocol(str);
			break;
		case "NEW": newProtocol(str);
			break;
		case "END": endProtocol(str);
			break;
		case "PLEASE": pleaseProtocol(str);
			break;
		case "BEGIN": beginProtocol(str);
			break;
		case "YOUR": yourProtocol(str);
			break;
		case "STARTING": startingProtocol(str);
			break;
		case "THE": theProtocol(str);
			break;
		case "MATCH": matchProtocol(str);
			break;
		case "MAKE": makeProtocol(str);
			break;
		case "GAME": gameProtocol(str);
			break;
		}
		return true;
	}
	
	public void thisProtocol()
	{
		System.out.println("JOIN " + tournamentPassword);
		c.sendToServer("JOIN " + tournamentPassword);
	}
//////////////////////////////////////////////////////
	public void helloProtocol()
	{
		System.out.println("I AM " + userName + " " + password);
		c.sendToServer("I AM " + userName + " " + password);
	}
//////////////////////////////////////////////	
	public void welcomeProtocol(StringTokenizer strTok)
	{
		//<pid> .... leftover
		this.ourPid = strTok.nextToken();
	}
/////////////////////////////////////////////////
	public void newProtocol(StringTokenizer strTok)
	{
		strTok.nextToken();//CHALLENGES
		this.challengeId = strTok.nextToken();//<cid>
		strTok.nextToken();//YOU
		strTok.nextToken();//WILL
		strTok.nextToken();//PLAY
		this.maxRounds = strTok.nextToken();//<rounds>
		strTok.nextToken();//Match or matches
		
	}
///////////////////////////////////////	
	public void endProtocol(StringTokenizer strTok)
	{
		String of = strTok.nextToken();//should be of can ignore
		String challenge_round = strTok.nextToken();
		switch(challenge_round)
		{
		case "CHALLENGES":challengesProtocol(strTok);
			break;
		case "ROUND":roundProtocol(strTok);
			break;
		}
	}
	
	public void challengesProtocol(StringTokenizer strTok)
	{
		System.out.println("#########################please kill challenges loop");
	}
	
	public void roundProtocol(StringTokenizer strTok)
	{
		this.currentRound = strTok.nextToken();
		strTok.nextToken();
		this.maxRounds = strTok.nextToken();
		if(strTok.hasMoreTokens())
			{}
	}
	
///////////////////////////////////////////
	public void pleaseProtocol(StringTokenizer strTok)
	{
		strTok.nextToken();//WAIT
		strTok.nextToken();//FOR
		strTok.nextToken();//THE
		strTok.nextToken();//NEXT
		strTok.nextToken();//CHALLENGE
		strTok.nextToken();//TO
		strTok.nextToken();//BEGIN
		
	}
////////////////////////////////////////////
	public void beginProtocol(StringTokenizer strTok)
	{
		strTok.nextToken();//ROUND
		this.currentRound = strTok.nextToken();//rid
		strTok.nextToken();//OF
		this.maxRounds = strTok.nextToken();//maxROunds
	}
//////////////////////////////////////////
	public void yourProtocol(StringTokenizer strTok)
	{
		strTok.nextToken();//opponent
		strTok.nextToken();//IS
		strTok.nextToken();//PLAYER
		this.opponentPid = strTok.nextToken();//<pid>	
	}
/////////////////////////////////////////
	public void startingProtocol(StringTokenizer strTok)
	{
		strTok.nextToken();//TILE
		strTok.nextToken();//IS
		this.startingTile = strTok.nextToken();//<tile>
		strTok.nextToken();//AT
		this.startingX = strTok.nextToken();
		this.startingY = strTok.nextToken();
		this.startingOrientation = strTok.nextToken();

		
		
		
	}
///////////////////////////////////////
	public void theProtocol(StringTokenizer strTok)
	{
		strTok.nextToken();//REMAINING
		this.stackSize = strTok.nextToken();//<number_tiles>
		this.tileStack = new String[Integer.parseInt(this.stackSize)];
		strTok.nextToken();//TILES
		strTok.nextToken();//ARE
		strTok.nextToken();// [
		tileStackProtocol(strTok);
	}
	
	public void tileStackProtocol(StringTokenizer strTok)
	{
		for(int i = 0; i < this.tileStack.length; i++)
			this.tileStack[i] = strTok.nextToken();
		strTok.nextToken();// ] 
		Stack<Tile> pile = new Stack<Tile>();
		Board.createDefaultStack();
		for (int i = tileStack.length - 1; i >= 0; i--) {
			pile.push(Board.tileMap.get(tileStack[i].toLowerCase()).clone());
		}
		Tile start = Board.tileMap.get(startingTile.toLowerCase()).clone();
		final Position p = new Position(Integer.parseInt(startingX), Integer.parseInt(startingY));
		this.board = new Board((Stack<Tile>)pile.clone(), start, p , Integer.parseInt(startingOrientation));
		
	}
////////////////////////////////////////////
	public void matchProtocol(StringTokenizer strTok)
	{
		//used to get time plan but we dont even need it
	}
///////////////////////////////////////////
	public void makeProtocol(StringTokenizer strTok)
	{
		strTok.nextToken(); //YOUR
		strTok.nextToken(); //MOVE
		strTok.nextToken(); //IN
		strTok.nextToken(); //GAME
		String moveGid = strTok.nextToken();
		strTok.nextToken(); //WITHIN
		String moveTime = strTok.nextToken();
		strTok.nextToken();//SECONDS/SECOND
		strTok.nextToken();//MOVE
		String moveNumber = strTok.nextToken();
		strTok.nextToken();//PLACE
		String moveTile = strTok.nextToken();
		if (!games.containsKey(moveGid)) {
			Game game = new Game(this.board.clone());

			List<Player> players = new ArrayList<Player>(2);
			List<AiPlayer> aiPlayers = new ArrayList<AiPlayer>(2);

			players.add(new Player(0, this.ourPid));
			players.add(new Player(1, this.opponentPid));
			aiPlayers.add(new CloseAiPlayer(game));
			aiPlayers.add(new NetworkAiPlayer(game));

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
					+ "moveX" + " " + "moveY" +  " " + "moveOrientation" + " CROCODILE ";
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
			input = "GAME " + moveGid + " MOVE " + moveNumber + " TILE " + moveTile
					+ " UNPLACEABLE RETRIEVE TIGER AT " + tra.getPosition().getX() + " "
					+ tra.getPosition().getY() + " ";
		}
		else if (action instanceof TigerAdditionAction) {
			TigerAdditionAction taa = (TigerAdditionAction) action;
			input = "GAME " + moveGid + " MOVE " + moveNumber + " TILE " + moveTile
					+ " UNPLACEABLE ADD ANOTHER TIGER TO " + taa.getPosition().getX() + " "
					+ taa.getPosition().getY() + " ";
		}
		else {
			input = "GAME " + moveGid + " MOVE " + moveNumber +" PLACE " + moveTile + " AT "
					+ "moveX" + " " + "moveY" +  " " + "moveOrientation" + " NONE ";
		}
		System.out.println("Client: " + input);
		c.sendToServer(input);
		
	}
//////////////////////////////////////////////////
	public void gameProtocol(StringTokenizer strTok)
	{
		String currentGid = strTok.nextToken();
		String over_move = strTok.nextToken();
		switch(over_move)
		{
		case "OVER": overProtocol(strTok, currentGid);
			break;
		case "MOVE": moveProtocol(strTok, currentGid);
			break;
		}
	}
	
	public void overProtocol(StringTokenizer strTok, String currentGid)
	{
		strTok.nextToken();//PLAYER
		String gamePid =  strTok.nextToken();
		games.remove(currentGid);
		//we don't care about scores
	}
	
	public void moveProtocol(StringTokenizer strTok, String currentGid)
	{
		if (!games.containsKey(currentGid)) {
			Game game = new Game(this.board.clone());

			List<Player> players = new ArrayList<Player>(2);
			List<AiPlayer> aiPlayers = new ArrayList<AiPlayer>(2);

			players.add(new Player(0, this.ourPid));
			players.add(new Player(1, this.opponentPid));
			aiPlayers.add(new CloseAiPlayer(game));
			aiPlayers.add(new NetworkAiPlayer(game));

			game.setPlayers(players);
			game.setAiPlayers(aiPlayers);

			this.games.put(currentGid, game);
		}
		String moveNumber = strTok.nextToken();
		strTok.nextToken();//PLAYER
		String playerPid = strTok.nextToken();
		String placed_forfeited = strTok.nextToken();//MOVE
		
		switch(placed_forfeited)
		{
		case "PLACED": subMoveProtocol(strTok, currentGid, playerPid);
			break;
		case "FORFEITED:": forfeitedProtocol(currentGid);
			break;
		}
		
	
	}
	public void forfeitedProtocol(String currentGid)
	{
		games.remove(currentGid);
		///doesn't matter how
	}
	public void subMoveProtocol(StringTokenizer strTok, String currentGid, String playerPid)
	{
		
		String moveTile = strTok.nextToken();
		strTok.nextToken();//AT
		String moveX = strTok.nextToken();
		String moveY = strTok.nextToken();
		String moveOrientation = strTok.nextToken();
		String none_tiger = (strTok.hasMoreTokens()) ? strTok.nextToken():"";
		if(!playerPid.equals(this.ourPid))
		switch(none_tiger)
		{
		case "TIGER": tigerProtocol(strTok, currentGid, playerPid, moveTile, moveX, moveY, moveOrientation);
			break;
		case "CROCODILE":
		default:
			Board.createDefaultStack();
			makeMove(Board.tileMap.get(moveTile.toLowerCase()), moveX, moveY, moveOrientation,"",  currentGid);
			break;
		}
	}
	
	public void tigerProtocol(StringTokenizer strTok, String currentGid, String playerPid, String moveTile, String moveX, String moveY, String moveRot )
	{
		String tigerZone = strTok.nextToken();
		Board.createDefaultStack();
		makeMove(Board.tileMap.get(moveTile.toLowerCase()), moveX, moveY, moveRot,tigerZone,  currentGid);
	}

	public final void makeMove(Tile current, String x, String y, String orientation, String zoneIndex, String gid) {
		Position pos = new Position(Integer.parseInt(x), Integer.parseInt(y));
		Game gameAlias = null;

		gameAlias = games.get(gid);

		while(current.getRotation() != Integer.parseInt(orientation)){
			current.rotate();
		}
		gameAlias.getBoard().getPile().pop();
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

}
