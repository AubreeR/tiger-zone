package tiger_zone;

enum TigerPlacement
{
	JUNGLE,
	LAKE,
	GAME,
	DEN, 
	NOTPLACED
	
};

/**
 * The <code>Tiger</code> class represents a tiger, which may be used by a <code>Player</code> to claim completeable
 * features.
 */
public class Tiger {
	private int xCoor, yCoor;//coordinates of tile on the board(for the purpose of redundancy)
	private Tile tile;//the tile it is placed on
	private int locationPlaced;//what array index is the tiger placed on
	private TigerPlacement placement;//the role fo the tiger
	
	Tiger()
	{
		xCoor = -1;
		yCoor = -1;
		locationPlaced = -1;
		tile = null;
		placement = TigerPlacement.NOTPLACED;
	}
	
	
	Tiger(final int x, final int y, Tile tile, final int locationPlaced)
	{
		
		this.xCoor = x;
		this.yCoor = y;
		this.locationPlaced = locationPlaced;
		this.tile = tile;
		try{
		this.placement = this.evaluateRole();
		}
		catch(Exception ex)
		{
			this.placement = TigerPlacement.NOTPLACED;
			System.err.println(ex);
		}
	}
	
	/**
	 * Looks at the given tile coordinate the tiger was placed on to figure out it's role
	 * @return TigerPlacement value (i.e. Jungle, Lake, Game...)
	 * @throws Exception if it is placed in a blatantly 
	 */
	TigerPlacement evaluateRole() throws Exception
	{
		switch(tile.getSides()[this.locationPlaced])
		{
		case 'j':
			return TigerPlacement.JUNGLE;
		case 'l':
			return TigerPlacement.LAKE;
		case 'r' :
			return TigerPlacement.GAME;
		case 'd':
			return TigerPlacement.DEN;
		case 'e'://cannot place a tiger on e
		default:
			
			throw new Exception("INVALID TIGER PLACEMENT: attempted to be placed on a location that doesn't allow a tiger.");
		
		}
	
	}
	
	TigerPlacement getTigerPlacement() { return placement; }
	int getLocation() { return locationPlaced; }
	int getXCoor() { return xCoor; }
	int getYCoor() { return yCoor; }
	Tile getTile() { return tile;  }
	
	
	

}
