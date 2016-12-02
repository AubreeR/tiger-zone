package tiger_zone;

/**
 * The <code>Tiger</code> class represents a tiger, which may be used by a <code>Player</code> to claim completeable
 * features (jungles, lakes, game trails, and dens).
 */
public class Tiger {
	/**
	 * The owner of this tiger.
	 */
	private final Player owner;

	/**
	 * Creates a new instance of tiger with the specified owner.
	 *
	 * @param owner Player instance that owns this tiger.
	 */
	public Tiger(final Player owner) {
		this.owner = owner;
	}

	/**
	 * Returns this tiger's owner.
	 *
	 * @return owner
	 */
	public final Player getOwner() {
		return this.owner;
	}
}
