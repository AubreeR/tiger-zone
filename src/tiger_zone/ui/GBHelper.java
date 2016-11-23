package tiger_zone.ui;

import java.awt.GridBagConstraints;

/**
 * Keeps track of the current position in GridBagLayout. Supports a few GridBag features, like position, width, height,
 * and expansion. All methods return the GBHelper object for call chaining.
 *
 * @author Fred Swartz (public domain, see http://www.leepoint.net/GUI/layouts/gridbag-example.html)
 *
 */
public class GBHelper extends GridBagConstraints {
	private static final long serialVersionUID = 2766581791980477136L;

    /**
     * Creates helper at top left, component always fills cells.
     */
    public GBHelper() {
        gridx = 0;
        gridy = 0;
        fill = GridBagConstraints.BOTH;  // Component fills area
    }

    /**
     * Moves the helper's cursor to the right one column.
     *
     * @return this
     */
    public GBHelper nextCol() {
        gridx++;
        return this;
    }

    /**
     * Moves the helper's cursor to the first column in the next row.
     *
     * @return this
     */
    public GBHelper nextRow() {
        gridx = 0;
        gridy++;
        return this;
    }

    /**
     * Expandable width. Returns new helper allowing horizontal expansion. A new helper is created so the expansion
     * values don't pollute the original helper.
     *
     * @return new helper
     */
    public GBHelper expandW() {
        GBHelper duplicate = (GBHelper)this.clone();
        duplicate.weightx = 1.0;
        return duplicate;
    }

    /**
     * Expandable height. Returns new helper allowing vertical expansion.
     *
     * @return new helper
     */
    public GBHelper expandH() {
        GBHelper duplicate = (GBHelper)this.clone();
        duplicate.weighty = 1.0;
        return duplicate;
    }

    /**
     * Sets the width of the area in terms of the number of columns.
     *
     * @param colsWide
     * @return new helper
     */
    public GBHelper width(int colsWide) {
        GBHelper duplicate = (GBHelper)this.clone();
        duplicate.gridwidth = colsWide;
        return duplicate;
    }

    /**
     * Width is set to all remaining columns of the grid.
     *
     * @return new helper
     */
    public GBHelper width() {
        GBHelper duplicate = (GBHelper)this.clone();
        duplicate.gridwidth = REMAINDER;
        return duplicate;
    }

    /**
     * Sets the height of the area in terms of rows.
     *
     * @param rowsHigh
     * @return new helper
     */
    public GBHelper height(int rowsHigh) {
        GBHelper duplicate = (GBHelper)this.clone();
        duplicate.gridheight = rowsHigh;
        return duplicate;
    }

    /**
     * Height is set to all remaining rows.
     *
     * @return new helper
     */
    public GBHelper height() {
        GBHelper duplicate = (GBHelper)this.clone();
        duplicate.gridheight = REMAINDER;
        return duplicate;
    }

    /**
     * Alignment is set by parameter.
     *
     * @param alignment
     * @return new helper
     */
    public GBHelper align(int alignment) {
        GBHelper duplicate = (GBHelper)this.clone();
        duplicate.fill = NONE;
        duplicate.anchor = alignment;
        return duplicate;
    }
}
