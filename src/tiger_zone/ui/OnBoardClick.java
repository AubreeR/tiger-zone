package tiger_zone.ui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OnBoardClick extends MouseAdapter {
	public void mouseClicked(MouseEvent event) {
		JPanel j = (JPanel)event.getSource();
		ImageIcon img = new ImageIcon("./src/resources/tile3.png");
		ImageIcon imgScale = new ImageIcon(img.getImage().
				getScaledInstance(j.getHeight(), j.getWidth(), Image.SCALE_SMOOTH));
		JLabel label = new JLabel();
		label.setIcon(imgScale);
		j.add(label);
		j.revalidate();
		//j.setBackground(Color.YELLOW);	
	}
}