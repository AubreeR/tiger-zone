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
		players.add(new Player(0, "p1"));
		players.add(new Player(1, "p2"));
		game.setPlayers(players);

		PoorAiPlayer skynet1 = new PoorAiPlayer(game, "s1");
		PoorAiPlayer skynet2 = new PoorAiPlayer(game, "s2");
		
		while (game.getBoard().getPile().size() >= 1) {
			skynet1.makeMove();
			skynet2.makeMove();
			System.out.println(game.getBoard().getPile().size());
		}

		BoardFrame bf = new BoardFrame(game);
		bf.setSize(900, 900);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}
