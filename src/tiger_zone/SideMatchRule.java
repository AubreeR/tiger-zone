package tiger_zone;



public class SideMatchRule extends PlacementRule
{
	private Tile nextTile;//we must know the next tile to be placed in order to match sides
	
	public SideMatchRule(BoardCell[][] boardState,int row, int col, Tile nextTile)
	{
		super(boardState,row, col);
		this.nextTile = nextTile;
		
	}
	
	@Override
	public boolean evaluate()
	{
		try
		{
			//check each tile around it
			//center ALWAYS HAS TO MATCH
			//side fields on a face can diverge iff a diagonal side matches the divergent
			if(true/*check all adjacent tiles*/)
			{
				return true;
				
			}
			else
				testFailure();//if the test condition fails
			
		}
		catch(Exception ex)
		{
			System.out.println(ex);//display the error 
			return false;
		}
		return false;
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input: \"" + "\" does not equal \"Hello World\"");
	}
	
}

