package tiger_zone.action;

import tiger_zone.Position;
import tiger_zone.Tile;

/**
 * Represents the action of placing a tile+tiger.
 */
public class TigerPlacementAction extends TilePlacementAction {
	private final int zone;

	/**
	 * @param position Position where tile was placed.
	 * @param tile The tile placed.
	 * @param zone Zone where tiger was placed.
	 */
	public TigerPlacementAction(final Position position, final Tile tile, final int zone) {
		super(position, tile);
		this.zone = zone;
	}
	
	/**
	 * Returns the zone where the tiger was placed.
	 * 
	 * @return zone
	 */
	public final int getZone() {
		return this.zone;
	}
}
