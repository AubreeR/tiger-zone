package tiger_zone.action;

import tiger_zone.Position;

/**
 * Represents the addition of a tiger to a tile which already has a tiger.
 */
public class TigerAdditionAction extends Action {
	private final Position position;
	
	/**
	 * Creates a new instance of <code>TigerAdditionAction</code> for the addition of a new tiger on the tile at
	 * position.
	 * 
	 * @param position Position of tile.
	 */
	public TigerAdditionAction(final Position position) {
		this.position = position;
	}
	
	/**
	 * Returns the position of the tile where the additional tiger was placed.
	 * 
	 * @return position
	 */
	public final Position getPosition() {
		return this.position;
	}
}
