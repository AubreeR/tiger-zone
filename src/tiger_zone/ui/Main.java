package tiger_zone.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;

import tiger_zone.Board;
import tiger_zone.Game;
import tiger_zone.Player;
import tiger_zone.Position;
import tiger_zone.Protocol;
import tiger_zone.Tile;
import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.CloseAiPlayer;
import tiger_zone.ai.PoorAiPlayer;

public class Main {
	
	
	public static void main(String[] args) {
		
		////
		//Protocol p = new Protocol(null,0,null,null,null); 
		Protocol p = new Protocol("0.0.0.0",52000,"tpass","Steven","Remington");
//		if(args.length > 0){
//			p = new Protocol(args[0], Integer.parseInt(args[1]),args[2], args[3], args[4]);
//		}
		
		p.tournamentProtocol(); 
//		String tileStrings = p.getTiles();
//		String[] parseTiles = tileStrings.split("\\s+"); 
//		
//		for(int j = 0; j < parseTiles.length; j++){
//			System.out.println(".." + parseTiles[j] + "..");
//		}
		
		
		Stack<Tile> pile = Board.createDefaultStack();
		Tile t = Board.tileMap.get("tltj-");

		Collections.shuffle(pile);
		Game game = new Game(pile);
		game.getBoard().addTileWithNoValidation(new Position(0, 0), t);

		// Protocol p = new Protocol("10.138.7.222",8000,"tpass","user","pass");
		// p.tournamentProtocol();

		List<Player> players = new ArrayList<Player>(2);
		players.add(new Player(0, "p1"));
		players.add(new Player(1, "p2"));
		game.setPlayers(players);

		AiPlayer skynet1 = new CloseAiPlayer(game);
		AiPlayer skynet2 = new PoorAiPlayer(game);
		
		List<AiPlayer> ai = new ArrayList<AiPlayer>();
		ai.add(skynet1);
		ai.add(skynet2);
		game.setAiPlayers(ai);

		while (!game.isOver()) {
			game.conductTurn();
		}

		BoardFrame bf = new BoardFrame(game);
		bf.setSize(900, 900);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}
