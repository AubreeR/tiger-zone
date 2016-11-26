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
import tiger_zone.UnionFind;
import tiger_zone.ai.AiPlayer;
import tiger_zone.ai.PoorAiPlayer;
import tiger_zone.Protocol;

public class Main {
	public static void main(String[] args) {
		char[] Nsides = {'j','l','l','j'};
		char[] Ntigers = {'j','=','=','=','=','l','=','l','='};
		char[] Ncrocs = {'=','=','j','=','=','=','j','=','='};
		Tile t = new Tile( Nsides, '-', Ntigers, Ncrocs, "./src/resources/tile14.png");
		t.rotate();
		t.rotate();
		UnionFind uf = new UnionFind(t);
		for(int i = 0; i < 9; i++)
		{
			if(i % 3 == 0 && i != 0)
				System.out.println("");
			System.out.print(t.getZone(i+1) + "\t");
		}
		System.out.println("");
		for(int i = 0; i < 9; i++)
		{
			if(i % 3 == 0 && i != 0)
				System.out.println("");
			System.out.print(uf.findRoot(i) + "\t");
		}
		System.out.println("");
		
		/**
		 * GUI Implementation. 
		 * @Uncomment this and comment the rest to enable a 
		 * human player to play the game and interact with the UI
		 */
		


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
		players.add(new Player(0, "AI1"));
		players.add(new Player(1, "AI2"));
		game.setPlayers(players);

		List<AiPlayer> aiPlayers = new ArrayList<AiPlayer>(2);
		aiPlayers.add(new PoorAiPlayer(game, "s1"));
		aiPlayers.add(new PoorAiPlayer(game, "s2"));
		game.setAiPlayers(aiPlayers);

		game.conductTurn();

		BoardFrame bf = new BoardFrame(game);
		bf.setSize(900, 900);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}
