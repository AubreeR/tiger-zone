package tiger_zone.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import tiger_zone.Game;
import tiger_zone.Tile;

public class OnBoardClick extends MouseAdapter {
	private int rowClick;
	private int colClick;
	private final Game game;

	public OnBoardClick(final Game game) {
		this.game = game;
	}

	public void mouseClicked(MouseEvent event) {
		Tile placedTile = this.game.getBoard().getPile().pop();
		Tile nextTile = this.game.getBoard().getPile().peek();

		ImageIcon previewImg = new ImageIcon(nextTile.getImagePath());
		BoardFrame.tilePreview.setImg(previewImg);
		BoardFrame.preview.add(BoardFrame.tilePreview, BorderLayout.SOUTH);
		BoardFrame.preview.setSize(250, 250);
		BoardFrame.preview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BoardFrame.preview.setVisible(true);

		TilePanel j = (TilePanel)event.getSource();
		rowClick = (int) j.getClientProperty("row");
		colClick = (int) j.getClientProperty("col");

		if(this.game.getBoard().addTile(rowClick, colClick, placedTile))
		{
			System.out.println("Board: " + rowClick + ", " + colClick);
			ImageIcon img = new ImageIcon(placedTile.getImagePath());
			j.setImg(img);
			j.setRotation(placedTile.getRotation());
		}
		else
		{
			this.game.getBoard().getPile().push(placedTile);
			previewImg = new ImageIcon(placedTile.getImagePath());
			BoardFrame.tilePreview.setImg(previewImg);
		}


	}
}
