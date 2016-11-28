package tiger_zone.action;

import tiger_zone.Position;

/**
 * Represents a tile and crocodile placement.
 */
public class CrocodilePlacementAction extends Action {
	private final Position position;
	
	/**
	 * Creates a new instance of <code>CrocodilePlacementAction</code> to indicate that a tile and crocodile were placed
	 * at the given position.
	 * 
	 * @param position Position of the placed tile.
	 */
	public CrocodilePlacementAction(final Position position) {
		super();
		this.position = position;
	}
	
	/**
	 * Returns the position of the tile that was placed with the crocodile.
	 * 
	 * @return position
	 */
	public final Position getPosition() {
		return this.position;
	}
}