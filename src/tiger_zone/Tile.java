package tiger_zone;

/**
 * The <code>Tile</code> class represents a game tile.
 */
public class Tile implements Cloneable {
	private final char[] sides;
	private final char[] originalSides;
	private final char center;
	private final String imagePath;
	private int rotation = 0;
	private int tigerPosition = -1;
	private Tiger placedTiger = null;

	/**
	 * Creates a new instance of <code>Tile</code> with the specified sides, center, and image path.
	 *
	 * @param sides Describes the edges of this <code>Tile</code> in a clockwise fashion, beginning at the top-left
	 *              corner.
	 * @param center Describes the center of this <code>Tile</code>.
	 * @param imagePath The path to the image file which portrays this <code>Tile</code>.
	 */
	public Tile(char[] sides, char center, String imagePath) {
		this.originalSides = sides.clone();
		this.sides = sides.clone();
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

	public final String getOriginalSides() {
		return ("" + this.originalSides[0] + this.originalSides[1] + this.originalSides[2] + this.originalSides[3]).trim();
	}

	/**
	 * Returns the center (special attribute) of a tile.
	 *
	 * @return center char
	 */
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
	 * Returns the rotation of this tile in degrees, counterclockwise.
	 *
	 * @return rotation
	 */
	public final int getRotation() {
		return this.rotation;
	}

	/**
	 * Rotates this tile 90 degrees counterclockwise.
	 */
	public final void rotate() {
		this.rotation += 90;
		if (this.rotation == 360) {
			this.rotation = 0;
		}

		// char[] rotation, source: http://codereview.stackexchange.com/a/69305
		final int offset = sides.length - 1 % sides.length;
		if (offset > 0) {
			final char[] copy = this.sides.clone();
			for (int i = 0; i < sides.length; i++) {
				final int j = (i + offset) % sides.length;
				this.sides[j] = copy[i];
			}
		}
	}

	/**
	 * Returns the zone of the tiger on this tile. Returns -1 if no tile exists on this tile.
	 *
	 * @return tiger position
	 */
	public final int getTigerPosition() {
		return this.tigerPosition;
	}


	/**
	 * Adds a tiger to this tile at the specified grid position.
	 *
	 * @param gridPos Position on grid.
	 * @param tiger Tiger to add.
	 * @return if placement was valid
	 */
	public boolean addTiger(int gridPos, Tiger tiger) {
		if (gridPos >= 1 && gridPos <= 9) {
			this.tigerPosition = gridPos;
			this.placedTiger = tiger;
			return true;
		}
		return false;
	}

	/**
	 * Returns true if this tile contains a tiger. Otherwise returns false.
	 *
	 * @return has tiger
	 */
	public boolean hasTiger() {
		return this.placedTiger != null;
	}

	public char getZone(int index) {
		if(this.originalSides[0] == 'j' &&this.originalSides[1] == 'l' &&this.originalSides[2] == 'l' &&this.originalSides[3] == 'j')
		{
			if(index == 5)
				return 'j';
			for(int i = 0; i < 4; i++)
			{
				if(this.getSide(i) == 'l')
				{
					int j = (i != 3) ? i+1 : 0;
					int k = (i != 0) ? i-1 : 3;
					if(this.getSide(j) == 'l')
					{
						switch(j)
						{
						case 0:
							if(index == 1)
								return 'j';
							break;
						case 1:
							if(index == 3)
								return 'j';
							break;
						case 2:
							if(index == 9)
								return 'j';
							break;
						case 3:
							if(index == 7)
								return 'j';

						}
					}
					else if(this.getSide(k) == 'l')
					{
						switch(k)
						{
						case 0:
							if(index == 3)
								return 'j';
							break;
						case 1:
							if(index == 9)
								return 'j';
							break;
						case 2:
							if(index == 7)
								return 'j';
							break;
						case 3:
							if(index == 1)
								return 'j';
						}

					}
				}
			}
		}
		else if(this.originalSides[0] == 'j' &&this.originalSides[1] == 'l' &&this.originalSides[2] == 'j' &&this.originalSides[3] == 'l')
		{
			if(index == 5)
				return 'l';
		}
		else if(this.originalSides[0] == 'l' &&this.originalSides[1] == 'j' &&this.originalSides[2] == 'l' &&this.originalSides[3] == 'j')
		{
			if(index == 5)
				return 'j';
		}

		//look at all the zones
		switch(index)
		{
		case 1://top left
			if (this.getSide(0) == 'l' && this.getSide(3) == 'l')
				return 'l';
			else
				return 'j';


		case 2: //top center
			return this.getSide(0);

		case 3: //top right
			if (this.getSide(0) == 'l' && this.getSide(1) == 'l')
				return 'l';
			else
				return 'j';


		case 4: //middle left
			return this.getSide(3);

		case 5: //middle center
			if(isTerminatingRoad())
				return '=';
			else if(this.getSide(0) == 't' ||this.getSide(1) == 't' ||this.getSide(2) == 't' ||this.getSide(3) == 't' )
				return 't';
			else if (this.getCenter() == 'x')
				return 'x';
			else
				return 'E';

		case 6: //middle right
			return this.getSide(1);
		case 7: //bottom left
			if (this.getSide(2) == 'l' && this.getSide(3) == 'l')
				return 'l';
			else
				return 'j';

		case 8: //bottom center
			return this.getSide(2);

		case 9: //bottom right
			if (this.getSide(1) == 'l' && this.getSide(2) == 'l')
				return 'l';
			else
				return 'j';

		default:
			System.err.println("Invalid zone index");
		}

		return '=';
	}

	/**
	 * Returns true if this tile has a den. Otherwise returns false.
	 *
	 * @return has den?
	 */
	public final boolean hasDen() {
		return this.getCenter() == 'x';
	}

	/**
	 * Returns true if this tile has a prey animal on it. Otherwise returns false.
	 *
	 * @return has prey animal?
	 */
	public final boolean hasAnimal() {
		char ch = this.getCenter();
		return ch == 'b' || ch == 'd' || ch == 'p';
	}

	/**
	 * Returns true if this tile terminates a road. Otherwise, returns false.
	 *
	 * @return is terminating road?
	 */
	public final boolean isTerminatingRoad() {
		int count = 0;
		for(int i = 0; i < 4; i++) {
			count += ((this.getSide(i) == 't') ? 1 : 0);
		}

		// 0 road-sides: won't ever be connected to a road anyways
		// 1 road-side: road hits a lake (terminates)
		// 2 road-sides: straight road or turn
		// 3 road-sides: crossroads (terminates)
		// 4 road-sides: crossroads (terminates)
		return count == 1 || count == 3 || count == 4;
	}

	/**
	 * Returns the owner of the tiger on this tile. Returns null if no tiger exists on this tile.
	 *
	 * @return tiger's owner, or null if no tiger is on this tile
	 */
	public final Player getTigerOwner() {
		if (this.tigerPosition == -1) {
			return null;
		}
		return this.placedTiger.getOwner();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public final Tile clone() {
		final Tile copy = new Tile(this.sides, this.center, this.imagePath);

		while (copy.getRotation() != this.rotation) {
			copy.rotate();
		}

		return copy;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public final String toString() {
		return Character.toUpperCase(this.sides[0]) + "" + Character.toUpperCase(this.sides[1]) + ""
				+ Character.toUpperCase(this.sides[2]) + "" + Character.toUpperCase(this.sides[3]) + ""
				+ Character.toUpperCase(this.center);
	}
}
