package tiger_zone.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

class BoardFrame extends JFrame {
	private static final long serialVersionUID = 575149023846295616L;

	public BoardFrame() {
		final int rows = 5;
		final int cols = 5;
		
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

		ImageIcon img = new ImageIcon("C:/Users/Kyle/Desktop/Tiles/Tile1.png");
		JLabel label = new JLabel();
		label.setIcon(img);
		panelBoard[2][2].add(label);
	}
}