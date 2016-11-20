package tiger_zone.ui;

import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

import javax.swing.JFrame;

import tiger_zone.Board;
import tiger_zone.Tile;
import tiger_zone.Client;
import tiger_zone.Player;

public class Main {
	public static Board board;

	public static void main(String[] args) {
		Stack<Tile> pile = Board.createDefaultStack();
		Collections.shuffle(pile);
		board = new Board(pile);

		Player p = new Player();
		
		
		char[] Gsides = {'j','j','l','l','-'};


		board.addTile(1,0,new Tile(Gsides, 'e', "./src/resources/tile7.png"));
		//System.out.println(p.scores.scoreLake(board, 1,0));

		char[] Hsides = {'l','j','j','j','-'};
		board.addTile(1,-1,new Tile(Hsides, 'e', "./src/resources/tile7.png"));
		System.out.println(p.scores.scoreLake(board, 1,-1));
		//char[] Dsides = {'T','T','T','T','-'};
		//board.addTile(4, 4, new Tile(Dsides, 'r', "./src/resources/tile4.png"));

		BoardFrame bf = new BoardFrame();
		bf.setSize(600, 600);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}