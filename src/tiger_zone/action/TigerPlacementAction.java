package tiger_zone.action;

import tiger_zone.Position;

/**
 * Represents the action of placing a tile+tiger.
 */
public class TigerPlacementAction extends Action {
	private final Position position;
	
	/**
	 * Creates a new instance of <code>TigerPlacementAction</code> for placing a tiger+tile at the specified position.
	 * 
	 * @param position
	 */
	public TigerPlacementAction(final Position position) {
		super();
		this.position = position;
	}
	
	/**
	 * Returns the position of the tile that was placed with the tiger.
	 * 
	 * @return position
	 */
	public final Position getPosition() {
		return this.position;
	}
}
