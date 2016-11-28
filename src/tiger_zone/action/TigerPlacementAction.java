package tiger_zone.action;

import tiger_zone.Position;

public class TigerPlacementAction extends Action {
	private final Position position;
	
	public TigerPlacementAction(final Position position) {
		super();
		this.position = position;
	}
	
	public final Position getPosition() {
		return this.position;
	}
}
