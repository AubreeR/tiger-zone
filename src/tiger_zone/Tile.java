package tiger_zone;

/**
 * The <code>Tile</code> class represents a game tile.
 */
public class Tile {
	private char[] sides;
	private char center;
	private String imagePath;
	private int rotation = 0;

	/**
	 * Creates a new instance of <code>Tile</code> with the specified sides, center, and image path.
	 *
	 * @param sides Describes the edges of this <code>Tile</code> in a clockwise fashion, beginning at the top-left
	 *              corner.
	 * @param center Describes the center of this <code>Tile</code>.
	 * @param imagePath The path to the image file which portrays this <code>Tile</code>.
	 */
	public Tile(char[] sides, char center, String imagePath) {
		this.sides = sides;
		this.center = center;
		this.imagePath = imagePath;
	}

	/**
	 * Returns the sides of this tile.
	 *
	 * @return sides
	 */
	public final char[] getSides() {
		return this.sides;
	}

	public final char getSide(int index) {
		return this.sides[index];
	}

	public final char getCenter() {
		return this.center;
	}

	/**
	 * Returns the path to the image file which portrays this tile.
	 *
	 * @return path to image file
	 */
	public final String getImagePath() {
		return this.imagePath;
	}

	/**
	 * Returns the rotation of this tile in degrees, clockwise from the northern axis.
	 *
	 * @return rotation
	 */
	public final int getRotation() {
		return this.rotation;
	}

	/**
	 * Rotates this tile 90 degrees clockwise.
	 */
	public final void rotate() {
		this.rotation += 90;
		if (this.rotation == 360) {
			this.rotation = 0;
		}

		// char[] rotation, source: http://codereview.stackexchange.com/a/69305
		final int offset = sides.length - 3 % sides.length;
		if (offset > 0) {
			final char[] copy = sides.clone();
			for (int i = 0; i < sides.length; i++) {
				final int j = (i + offset) % sides.length;
				sides[i] = copy[j];
			}
		}
	}
}
