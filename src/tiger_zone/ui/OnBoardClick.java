package tiger_zone.ui;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tiger_zone.Tile;

public class OnBoardClick extends MouseAdapter {
	public void mouseClicked(MouseEvent event) {
		JPanel j = (JPanel)event.getSource();
		
		Tile t = Main.board.getPile().pop();
		ImageIcon img = new ImageIcon(t.getImagePath());
		ImageIcon imgScale = new ImageIcon(img.getImage().
				getScaledInstance(j.getHeight(), j.getWidth(), Image.SCALE_SMOOTH));

		JLabel label = new JLabel();
		label.setIcon(imgScale);
		j.add(label);
		j.revalidate();
	}
}