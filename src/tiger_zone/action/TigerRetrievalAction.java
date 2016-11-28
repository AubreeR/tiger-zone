package tiger_zone.action;

import tiger_zone.Position;

public class TigerRetrievalAction extends Action {
	private final Position position;
	
	public TigerRetrievalAction(final Position position) {
		super();
		this.position = position;
	}
	
	public final Position getPosition() {
		return this.position;
	}
}
