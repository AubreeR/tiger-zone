package tiger_zone.action;

/**
 * An Action is intended to represent some sort of change to the state of the game. Examples of Actions may be found in
 * the class which extend this one.
 */
public abstract class Action {
	/**
	 * Creates a new instance of <code>Action</code>. Should never be used directly, but rather called by an extending
	 * class.
	 */
	public Action() {
		super();
	}
}