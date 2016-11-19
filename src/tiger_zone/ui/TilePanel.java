package tiger_zone.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A <code>TilePanel</code> is an extension of a regular JPanel, but with the addition of an <code>ImageIcon</code> and
 * a rotation. This class override's the usual <code>getPreferredSize()</code> of <code>JComponent</code> in order to
 * utilize the size of the image as being the size of the panel as a whole. Additionally, the
 * <code>paintComponent</code> method is overridden to enable the rotation of the image on the panel.
 */
public class TilePanel extends JPanel {
	private static final long serialVersionUID = -7629110018399889413L;
	private ImageIcon img;
	private int rotation;

	/**
	 * Create a new instance of <code>TilePanel</code> with a transparent image and 0 rotation.
	 */
	public TilePanel() {
		this.img = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		this.rotation = 0;
	}

	/**
	 * Creates a new instance of <code>TilePanel</code> with the specified image and rotation.
	 *
	 * @param img Image to display.
	 * @param rotation Rotation of the image in degrees (counterclockwise).
	 */
	public TilePanel(final ImageIcon img, final int rotation) {
		this.img = img;
		this.rotation = rotation;
	}

	/**
	 * Sets the image (useful for turning a transparent image into an actual image).
	 *
	 * @param img New image.
	 */
	public final void setImg(final ImageIcon img) {
		this.img = img;
		this.repaint();
	}

	/**
	 * Sets the rotation.
	 *
	 * @param rotation New rotation.
	 */
	public final void setRotation(final int rotation) {
		this.rotation = rotation;
		this.repaint();
	}

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.img.getIconWidth(), this.img.getIconHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g.create();
        int x = this.getWidth() / 2;
        int y = this.getHeight() / 2;
        g2d.rotate(-Math.toRadians(this.rotation), x, y);
        x = (this.getWidth() - this.img.getIconWidth()) / 2;
        y = (this.getHeight() - this.img.getIconHeight()) / 2;
        g2d.translate(x, y);
        this.img.paintIcon(this, g2d, x, y);
        g2d.dispose();
    }
}
