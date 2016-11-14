package tiger_zone;

/**
 * The <code>BoardCell</code> class represents a position on the <code>Board</code>.
 */
class BoardCell {
	private final int x;
	private final int y;
	private Tile tile = null;

	/**
	 * Creates a new instance of <code>BoardCell</code>.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public BoardCell(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate of this cell.
	 *
	 * @return the x coordinate of this cell
	 */
	public final int getXCoord() {
		return this.x;
	}

	/**
	 * Returns the y coordinate of this cell.
	 *
	 * @return the y coordinate of this cell
	 */
	public final int getYCoord() {
		return this.y;
	}

	/**
	 * Returns the instance of <code>Tile</code> that is stored in this cell.
	 *
	 * @return the tile stored in this cell
	 */
	public final Tile getTile() {
		return this.tile;
	}

	/**
	 * Sets the tile stored in this cell to <code>tile</code>.
	 *
	 * @param tile The tile to store in this cell
	 */
	public final void setTile(final Tile tile) {
		this.tile = tile;
	}
}
