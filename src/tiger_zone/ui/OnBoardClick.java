package tiger_zone.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class OnBoardClick extends MouseAdapter {
	public void mouseClicked(MouseEvent event) {
		JPanel j = (JPanel)event.getSource();
		j.setBackground(Color.YELLOW);	
	}
}