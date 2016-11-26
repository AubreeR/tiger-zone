package tiger_zone.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;

import tiger_zone.Board;

import tiger_zone.Client;
import tiger_zone.Game;
import tiger_zone.Player;
import tiger_zone.Tile;
import tiger_zone.ai.PoorAiPlayer;
import tiger_zone.Protocol;

public class Main {
	public static void main(String[] args) {
		
//		Stack<Tile> pile = Board.createDefaultStack();
//		Collections.shuffle(pile);
//		Game game = new Game(pile);
//
//
//		//Protocol p = new Protocol("0.0.0.0",8000,"tpass","user","pass");
//		//p.tournamentProtocol();
//
//		List<Player> players = new ArrayList<Player>(2);
//		players.add(new Player(0));
//		players.add(new Player(1));
//		game.setPlayers(players);
//
//		BoardFrame bf = new BoardFrame(game);
//		bf.setSize(900, 900);
//		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		bf.setVisible(true);
		
		Stack<Tile> pile = Board.createDefaultStack();
		Collections.shuffle(pile);
		Game game = new Game(pile);

		
		//Protocol p = new Protocol("0.0.0.0",8000,"tpass","user","pass");
		//p.tournamentProtocol();

		List<Player> players = new ArrayList<Player>(2);
		players.add(new Player(0));
		players.add(new Player(1));
		game.setPlayers(players);

		PoorAiPlayer skynet = new PoorAiPlayer(game);
		while (game.getBoard().getPile().size() > 1) {
			skynet.makeMove();
			
		}

		BoardFrame bf = new BoardFrame(game);
		bf.setSize(900, 900);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}
