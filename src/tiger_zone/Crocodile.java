package tiger_zone;

/**
 * The <code>Crocodile</code> class represents a crocodile who decreases the point value of lakes/trails with prey
 * animals on them.
 */
public class Crocodile {
	private final Player owner;

	/**
	 * Creates a new instance of <code>Crocodile</code>.
	 *
	 * @param owner The player that owns this crocodile.
	 */
	public Crocodile(Player owner) {
		this.owner = owner;
	}

	/**
	 * Returns the player that owns this crocodile.
	 *
	 * @return owner
	 */
	public final Player getOwner() {
		return this.owner;
	}
}
