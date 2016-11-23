package tiger_zone.ui;

import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * The <code>Gap</code> class exists as a utility for creating gaps in GridBagLayouts.
 *
 * @author Fred Swartz (public domain, http://www.leepoint.net/GUI/layouts/gridbag-example.html)
 *
 */
public class Gap extends JComponent {
	private static final long serialVersionUID = -1132062558383650580L;

    /**
     * Creates filler with minimum size, but expandable infinitely.
     */
    public Gap() {
        Dimension min = new Dimension(0, 0);
        Dimension max = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        setMinimumSize(min);
        setPreferredSize(min);
        setMaximumSize(max);
    }

    /**
     * Creates rigid filler.
     *
     * @param size
     */
    public Gap(int size) {
        Dimension dim = new Dimension(size, size);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);
    }
}
