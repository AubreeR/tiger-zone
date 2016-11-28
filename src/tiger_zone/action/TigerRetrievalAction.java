package tiger_zone.action;

import tiger_zone.Position;

/**
 * Represents the action of retrieving a tiger from a tile.
 */
public class TigerRetrievalAction extends Action {
	private final Position position;
	
	/**
	 * Creates a new instance of <code>TigerRetrievalAction</code> for retrieving a tiger from the tile at the specified
	 * position.
	 * 
	 * @param position Position of the tile.
	 */
	public TigerRetrievalAction(final Position position) {
		super();
		this.position = position;
	}
	
	/**
	 * Returns the position of the tile wherein the tiger was retrieved from.
	 * 
	 * @return
	 */
	public final Position getPosition() {
		return this.position;
	}
}
