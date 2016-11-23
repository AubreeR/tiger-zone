package tiger_zone.ui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tiger_zone.Game;
import tiger_zone.Player;

public class SideFrame extends JFrame {
	private static final long serialVersionUID = 5269548914681174575L;

	public SideFrame(Game game) {
		JPanel content = new JPanel(new GridBagLayout());
		GBHelper pos = new GBHelper();

		for (Player p : game.getPlayers()) {
			content.add(new JLabel("Player " + p.getIndex() + ": " + p.getPoints() + " points"), pos.nextRow());
		}

		JPanel windowContent = new JPanel();
		windowContent.setLayout(new BorderLayout());
		windowContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		windowContent.add(content, BorderLayout.CENTER);

		super.setContentPane(windowContent);
		super.pack();
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLocationRelativeTo(null);
	}
}
