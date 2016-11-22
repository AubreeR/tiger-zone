package tiger_zone;



public class TigerTrailRule extends PlacementRule
{
	private Tile tilePlaced;//we must know the next tile to be placed in order to match sides
	private boolean[][] visited;
	
	public TigerTrailRule(Board boardState,int cartX, int cartY, Tile tilePlaced)
	{

		super(boardState,cartX, cartY);
		visited = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		super.setRuleName("TigerTrail Rule");
		this.tilePlaced = tilePlaced;
		
		
	}
	
	@Override
	public boolean evaluate()
	{
		try{
		
		if(true/*check all adjacent tiles*/)
		{
			return true;
			
		}
		else
			testFailure();//if the test condition fails
		}
		catch(Exception ex)
		{
			System.err.println(ex);
			return false;
		}
		return false;
		
		
	}
	
	private boolean isCrossroad(Tile t)
	{
		int count  = 0;
		//go through all sides
		for(int i = 0; i < 4; i++)
		{
			count += ((t.getSide(i) == 't') ? 1:0);
		}
		return count > 2 || count == 1;
	}
	
	private boolean recurse(int x, int y, Tile tile, Tile startTile)
	{
		if(tile == null)
			return false;
		boolean ret = false;
		
		if(isCrossroad(tile) ||(tile == startTile) || visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)])
		{
			return true;
		}
		else
		{	
			visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] = true;
			//find all adj tiles that have not already been visited
			for(int i = 0; i < 4; i++)
			{
				if(tile.getSide(i)=='t')
				{
					switch(i)
					{
					case 0:ret = ret && recurse(x,y+1,boardState.getTile(x, y+1), startTile);
						break;
					case 1:ret = ret && recurse(x,y+1,boardState.getTile(x+1, y), startTile);
						break;
					case 2:ret = ret && recurse(x,y+1,boardState.getTile(x, y-1), startTile);
						break;
					case 3:ret = ret && recurse(x,y+1,boardState.getTile(x-1, y), startTile);
						break;
					default:
					}
				}
			}
			return ret;
		}
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input: ");
	}
	
}

