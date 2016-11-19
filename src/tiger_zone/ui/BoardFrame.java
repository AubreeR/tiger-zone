package tiger_zone.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

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
	public static TilePanel tilePreview = new TilePanel();
	public static JButton rotateTile = new JButton("Rotate");
	public static JButton placeTiger = new JButton("Add Tiger");
	public static JPanel rotatePanel = new JPanel();
	public static JPanel tigerPanel = new JPanel();

	public BoardFrame() {
		final int rows = 10;		// Eventually replace these with BoardFrame size
		final int cols = 10;

		rotateTile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("TEST\n");
			}
		});

		MouseAdapter ma = new OnBoardClick();

		this.setLayout(new GridLayout(rows, cols));

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// By default, all panels are "blank" with a transparent image
				TilePanel panel = new TilePanel();

				Tile current = Main.board.getTile(i, j);

				// A tile exists at the position, so get its image and rotation and apply them to this panel
				if (current != null) {
					panel.setImg(new ImageIcon(current.getImagePath()));
					panel.setRotation(current.getRotation());
				}

				panel.addMouseListener(ma);
				panel.setBorder(new LineBorder(Color.BLACK));
				this.add(panel);
			}
		}

		Tile nextTile = Main.board.getPile().peek();
		tilePreview.setImg(new ImageIcon(nextTile.getImagePath()));
		preview.add(tilePreview, BorderLayout.SOUTH);

		rotatePanel.add(rotateTile);
		tigerPanel.add(placeTiger);

		preview.add(rotatePanel, BorderLayout.NORTH);
		preview.add(tigerPanel, BorderLayout.CENTER);

		preview.setSize(250, 250);
		preview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		preview.setVisible(true);
	}
}
