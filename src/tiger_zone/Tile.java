package tiger_zone;

/**
 * The <code>Tile</code> class represents a game tile.
 */
public class Tile {
	private char[] sides;
	private char[] originalSides;
	private char center;
	private String imagePath;
	private int rotation = 0;
	private char[] tigerSpots; 
	private char[] crocSpots;
	private int tigerPosition = -1;
	private String tigerOwner; 
	

	/**
	 * Creates a new instance of <code>Tile</code> with the specified sides, center, and image path.
	 *
	 * @param sides Describes the edges of this <code>Tile</code> in a clockwise fashion, beginning at the top-left
	 *              corner.
	 * @param center Describes the center of this <code>Tile</code>.
	 * @param imagePath The path to the image file which portrays this <code>Tile</code>.
	 */
	public Tile(char[] sides, char center,  String imagePath) {
		this.originalSides = sides.clone();
		this.sides = sides.clone();
		this.tigerSpots = null;
		this.center = center;
		this.imagePath = imagePath;
		this.setTigerOwner("none");
	}
	/**
	 * Creates a new instance of <code>Tile</code> with the specified sides, center, and image path.
	 *
	 * @param sides Describes the edges of this <code>Tile</code> in a clockwise fashion, beginning at the top-left
	 *              corner.
	 * @param center Describes the center of this <code>Tile</code>.
	 * @param imagePath The path to the image file which portrays this <code>Tile</code>.
	 */
	public Tile(char[] sides, char center, char[] tigerSpots, char[] crocSpots, String imagePath) {
		this.originalSides = sides.clone();
		this.sides = sides.clone();
		this.tigerSpots = tigerSpots.clone();
		this.crocSpots = crocSpots.clone();
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
	
	public final String getOriginalSides()
	{
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
		
		if(tigerSpots != null)
		{
			final int offsetb = tigerSpots.length - 3 % tigerSpots.length;
			if (offsetb > 0) {
				final char[] copy = this.tigerSpots.clone();
				for (int i = 0; i < tigerSpots.length; i++) {
					final int j = (i + offsetb) % tigerSpots.length;
					this.tigerSpots[j] = copy[i];
				}
			}
		}
		
	}

	public char[] getTigerSpots() {
		return tigerSpots;
	}

	public int getTigerPosition() {
		return tigerPosition;
	}
	
	/**
	* Adds a tiger to a tile location if such placement is valid
	*
	*@param grisPos the position 1-9 at which the tiger is added
	*/
	public void addTiger(int gridPos, String player){
		if(this.getTigerSpots()[gridPos-1] != '=' && gridPos >= 1 && gridPos <= 9){
			this.getTigerSpots()[gridPos-1] = 'q';
			setTigerOwner(player); 
			tigerPosition = gridPos; 
			System.out.println("Tiger placed!");
		}
		
		else{
			System.err.println("Invalid tiger placement!");
		}
		
	}
	
	public boolean hasTiger()
	{
		
		for(char ch : this.getTigerSpots()){
			if(ch == 'q'){
				//System.out.println("Tile has a tiger!");
				return true;
			}
		}
		
		//System.out.println("Tiger not found on tile!");
		return false;
	}
	
	public char getZone(int index)
	{
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
			if(isCrossroad())
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
	
	public boolean hasDen(){
		char ch = this.getCenter();
		if(ch == 'x'){
			return true;
		}
		
		else{
			return false;
		}
	}
	
	public boolean hasAnimal(){
		char ch = this.getCenter();
		if(ch == 'b' || ch == 'd' || ch == 'p'){
			return true;
		}
		
		else{
			return false;
		}
	}
	
	public boolean isCrossroad()
	{
		int count  = 0;
		//go through all sides
		for(int i = 0; i < 4; i++)
		{
			count += ((this.getSide(i) == 't') ? 1:0);
		}
		return count == 3|| count == 4 || count == 1;
	}
	//tiger position is independent of rotation
	//get rotation and offset to get the actual placement in the array
	public void setTigerPosition(int tigerPosition) {
		if(tigerPosition < this.tigerSpots.length)
		{
			System.err.println("Tiger Position outside  of bounds of tile");
			return;
		}
		if(this.tigerSpots[tigerPosition] != '=')
			this.tigerPosition = tigerPosition;
	}
	public String getTigerOwner() {
		return tigerOwner;
	}
	public void setTigerOwner(String tigerOwner) {
		this.tigerOwner = tigerOwner;
	}

}
