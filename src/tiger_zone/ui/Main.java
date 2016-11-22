package tiger_zone.ui;

import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

import javax.swing.JFrame;

import tiger_zone.Board;

import tiger_zone.Client;
import tiger_zone.Player;
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

		Player p = new Player(0);
		
		
	


		BoardFrame bf = new BoardFrame();
		bf.setSize(900, 900);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}
