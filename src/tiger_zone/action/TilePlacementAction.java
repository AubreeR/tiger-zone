package tiger_zone.action;

import tiger_zone.Position;
import tiger_zone.Tile;

public class TilePlacementAction extends Action {
	private final Position position;
	private final Tile tile;
	
	public TilePlacementAction(final Position position, final Tile tile) {
		super();
		this.position = position;
		this.tile = tile;
	}
	
	public final Position getPosition() {
		return this.position;
	}
	
	public final Tile getTile() {
		return this.tile;
	}
}
