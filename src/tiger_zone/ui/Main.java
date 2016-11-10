package tiger_zone.ui;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		BoardFrame bf = new BoardFrame();
		bf.setSize(500, 500);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bf.setVisible(true);
	}
}