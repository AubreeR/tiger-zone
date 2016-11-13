package tiger_zone;

public class Tile {
	private char[] sides;
	private char center;
	private boolean hasPennant = false;
	private String imagePath;
	
	/**
	 * The <code>Tile</code> class represents a game tile.
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
	 * Returns the path to the image file which portrays this tile.
	 * 
	 * @return path to image file
	 */
	public String getImagePath() {
		return this.imagePath;
	}
}