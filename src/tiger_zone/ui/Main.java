package tiger_zone.ui;

import java.util.Collections;
import java.util.Stack;

import javax.swing.JFrame;

import tiger_zone.Board;
import tiger_zone.Tile;
import tiger_zone.Protocol;

public class Main {
	public static Board board;

	public static void main(String[] args) {
		Stack<Tile> pile = Board.createDefaultStack();
		Collections.shuffle(pile);
		board = new Board(pile);

		//Protocol p = new Protocol("0.0.0.0",8000,"tpass","user","pass");
		//p.tournamentProtocol();
		char[] Gsides = {'T','J','T','T','-'};

		board.addTile(4, 5,new Tile(Gsides, 'e', "./src/resources/tile7.png"));

		char[] Dsides = {'T','T','T','T','-'};
		board.addTile(4, 4, new Tile(Dsides, 'r', "./src/resources/tile4.png"));

		BoardFrame bf = new BoardFrame();
		bf.setSize(600, 600);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}
