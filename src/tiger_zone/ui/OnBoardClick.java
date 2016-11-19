package tiger_zone.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import tiger_zone.Tile;

public class OnBoardClick extends MouseAdapter {
	public void mouseClicked(MouseEvent event) {
		Tile placedTile = Main.board.getPile().pop();
		Tile nextTile = Main.board.getPile().peek();

		ImageIcon previewImg = new ImageIcon(nextTile.getImagePath());
		BoardFrame.tilePreview.setImg(previewImg);
		BoardFrame.preview.add(BoardFrame.tilePreview, BorderLayout.SOUTH);
		BoardFrame.preview.setSize(250, 250);
		BoardFrame.preview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BoardFrame.preview.setVisible(true);

		TilePanel j = (TilePanel)event.getSource();
		ImageIcon img = new ImageIcon(placedTile.getImagePath());
		j.setImg(img);
		j.setRotation(placedTile.getRotation());
	}
}
