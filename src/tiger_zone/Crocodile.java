package tiger_zone;

/**
 * The <code>Crocodile</code> class represents a crocodile who decreases the point value of lakes/trails with prey
 * animals on them.
 */
enum CrocodilePlacement{
	LAKE, GAME, NOTPLACED
};



public class Crocodile{
	private final Player owner;
	private int xCoor;
	private int yCoor;
	private Tile tile; 
	private CrocodilePlacement placement;
	private int locationPlaced;
	/**
	 * Creates a new instance of <code>Crocodile</code>.
	 *
	 * @param owner The player that owns this crocodile.
	 */
	public Crocodile(Player owner){
		this.owner = owner;
		xCoor = -9999; // null position--will fix later
		yCoor = -9999; // null position--will fix later
		locationPlaced = -9999; // null position --will fix later
		placement = CrocodilePlacement.NOTPLACED;

	}

	public Crocodile(int xCoor, int yCoor){
		this.owner = null;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		try{
			this.placement = this.evaluateRole();
		}
		catch(Exception ex){
			this.placement = CrocodilePlacement.NOTPLACED;
			System.err.println(ex);
		}
	}

	/**
	 * Returns the player that owns this crocodile.
	 *
	 * @return owner
	 */
	public final Player getOwner() {
		return this.owner;
	}
	public final int getXCoor(){
		return this.xCoor;
	}

	public final int getYCoor(){
		return this.yCoor;
	}
	public final int getLocation(){
		return this.locationPlaced;
	}

	CrocodilePlacement evaluateRole() throws Exception{
		switch(tile.getSides()[this.locationPlaced]){
			case 'g':
				return CrocodilePlacement.GAME;
			case 'l':
				return CrocodilePlacement.LAKE;
		
			default:
				throw new Exception("INVALID CROCODILE PLACEMENT: attempt denied.");
		}
	}

	CrocodilePlacement getCrocodilePlacement(){
		return placement;
	}


}
