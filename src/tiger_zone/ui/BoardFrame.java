package tiger_zone.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import tiger_zone.Board;
import tiger_zone.Tile;

/**
 * @BoardFrame The GUI representation of the Board class. 
 * Generates a 2d array of JPanels which can be clicked and their icon
 * set to that of the next tile
 * 
 * @Params
 * Mouseadapter ma - Listens for the click of a JPanel
 * panelBoard - 2d array of JPanels
 * preview - JFrame window holding image of next tile on stack
 * previewLabel - Will hold the actual icon of next tile
 */ 
class BoardFrame extends JFrame {
	private static final long serialVersionUID = 575149023846295616L;
	public static JFrame preview = new JFrame(); 
	public static JLabel previewLabel = new JLabel();
	
	public BoardFrame() {
		final int rows = 10;
		final int cols = 10;
		
		MouseAdapter ma = new OnBoardClick(); 
				
		JPanel[][] panelBoard = new JPanel[rows][cols];
		this.setLayout(new GridLayout(rows,cols));
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				panelBoard[i][j] = new JPanel();
				panelBoard[i][j].addMouseListener(ma);
				panelBoard[i][j].setBorder(new LineBorder(Color.BLACK));
				this.add(panelBoard[i][j]);
			}
		}
	
		Tile test = Main.board.getTile(5,5);
		ImageIcon img = new ImageIcon(test.getImagePath());
		//ImageIcon imgScale = new ImageIcon(img.getImage().
		//		getScaledInstance(panelBoard[1][1].getHeight(), panelBoard[1][1].getWidth(), Image.SCALE_SMOOTH));
		JLabel label = new JLabel();
		label.setIcon(img);
		
		panelBoard[5][5].add(label);
		
		
		Tile nextTile = Main.board.getPile().peek(); 
		ImageIcon previewImg = new ImageIcon(nextTile.getImagePath());
		previewLabel.setIcon(previewImg);
		preview.add(previewLabel);
		preview.setSize(200, 200);
		preview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		preview.setVisible(true);
		
	}
}