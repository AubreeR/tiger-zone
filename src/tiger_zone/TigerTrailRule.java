package tiger_zone;



public class TigerTrailRule extends PlacementRule
{
	private Tile tilePlaced;//we must know the next tile to be placed in order to match sides
	private Tile[] adj;
	
	public TigerTrailRule(Board boardState,int cartX, int cartY, Tile tilePlaced)
	{

		super(boardState,cartX, cartY);

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
	
	private void recurse(int x, int y, Tile tile)
	{
		return;
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input: ");
	}
	
}

