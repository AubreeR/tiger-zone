package tiger_zone.action;

import tiger_zone.Position;

public class TigerAdditionAction extends Action {
	private final Position position;
	
	public TigerAdditionAction(final Position position) {
		this.position = position;
	}
	
	public final Position getPosition() {
		return this.position;
	}
}
