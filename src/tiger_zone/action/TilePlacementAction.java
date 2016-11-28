package tiger_zone.action;

import tiger_zone.Position;
import tiger_zone.Tile;

/**
 * Represents the action of placing a tile.
 */
public class TilePlacementAction extends Action {
	private final Position position;
	private final Tile tile;
	
	/**
	 * Creates a new instance of <code>TilePlacementAction</code> for the placing of tile at position.
	 * 
	 * @param position Position of tile.
	 * @param tile Tile being placed.
	 */
	public TilePlacementAction(final Position position, final Tile tile) {
		super();
		this.position = position;
		this.tile = tile;
	}
	
	/**
	 * Returns the position where tile was placed.
	 * 
	 * @return position
	 */
	public final Position getPosition() {
		return this.position;
	}
	
	/**
	 * Returns the tile that was placed.
	 * 
	 * @return tile
	 */
	public final Tile getTile() {
		return this.tile;
	}
}
