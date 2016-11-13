package tiger_zone.ui;

import java.util.Collections;
import java.util.Stack;

import javax.swing.JFrame;

import tiger_zone.Board;
import tiger_zone.Tile;

public class Main {
	public static Board board;

	public static void main(String[] args) {
		Stack<Tile> pile = Board.createDefaultStack();
		Collections.shuffle(pile);
		board = new Board(pile);
		
		BoardFrame bf = new BoardFrame();
		bf.setSize(500, 500);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}