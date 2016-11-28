package tiger_zone.action;

import tiger_zone.Position;
import tiger_zone.Tile;

/**
 * Represents a tile and crocodile placement.
 */
public class CrocodilePlacementAction extends TilePlacementAction {
	/**
	 * Creates a new instance of <code>CrocodilePlacementAction</code> to indicate that a tile and crocodile were placed
	 * at the given position.
	 * 
	 * @param position Position of the placed tile.
	 */
	public CrocodilePlacementAction(final Position position, final Tile tile) {
		super(position, tile);
	}
}