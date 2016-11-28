package tiger_zone.action;

import tiger_zone.Position;

public class CrocodilePlacementAction extends Action {
	private final Position position;
	
	public CrocodilePlacementAction(final Position position) {
		super();
		this.position = position;
	}
	
	public final Position getPosition() {
		return this.position;
	}
}