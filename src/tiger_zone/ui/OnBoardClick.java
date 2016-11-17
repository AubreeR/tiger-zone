package tiger_zone.ui;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tiger_zone.Tile;

public class OnBoardClick extends MouseAdapter {
	public void mouseClicked(MouseEvent event) {
		JPanel j = (JPanel)event.getSource();
		BoardFrame b = new BoardFrame();
		Tile t = Main.board.getPile().pop();
		Tile nextTile = Main.board.getPile().peek(); 
		ImageIcon previewImg = new ImageIcon(nextTile.getImagePath());
		
		b.previewLabel.setIcon(previewImg);
		b.preview.add(b.previewLabel);
		b.preview.setSize(200, 200);
		b.preview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b.preview.setVisible(true);
		
		ImageIcon img = new ImageIcon(t.getImagePath());
		ImageIcon imgScale = new ImageIcon(img.getImage().
				getScaledInstance(j.getHeight(), j.getWidth(), Image.SCALE_SMOOTH));

		JLabel label = new JLabel();
		label.setIcon(imgScale);
		j.add(label);
		j.revalidate();
	}
}