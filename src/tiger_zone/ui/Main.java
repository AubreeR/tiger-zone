package tiger_zone.ui;

import java.util.Collections;
import java.util.Stack;

import javax.swing.JFrame;

import tiger_zone.Board;
import tiger_zone.Tile;
import tiger_zone.RuleEngine;
import tiger_zone.AdjacencyRule;
import tiger_zone.SideMatchRule;

public class Main {
	public static Board board;

	public static void main(String[] args) {
		Stack<Tile> pile = Board.createDefaultStack();
		Collections.shuffle(pile);
		board = new Board(pile);
		
		char[] sides = {'j','r','j','l','l','l','j','r','j','j','j','j'};
		Tile init = new Tile(sides, 'r', "./src/resources/tile19.png");
		board.addTile(5, 5, init);

		//testing rule engine
		RuleEngine placementRules = new RuleEngine();
		
		AdjacencyRule ar;
		long millis = System.currentTimeMillis() % 1000;
		for(int i = 0; i < 1000; i++)
		{
			ar = new AdjacencyRule(board.getGameState(), 2,2);
			placementRules.addRule(ar);
		}
		placementRules.evaluateRules();

		millis = System.currentTimeMillis() % 1000 - millis;
		System.out.println("Millieconds to evaluate 1000 rules: " + millis);
		
		BoardFrame bf = new BoardFrame();
		bf.setSize(600, 600);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}