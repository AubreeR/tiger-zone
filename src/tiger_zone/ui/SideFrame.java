package tiger_zone.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SideFrame extends JFrame {
	private static final long serialVersionUID = 5269548914681174575L;

	public SideFrame() {
		JPanel windowContent = new JPanel();
		windowContent.setLayout(new BorderLayout());
		windowContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		super.setContentPane(windowContent);
		super.pack();
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLocationRelativeTo(null);
	}
}