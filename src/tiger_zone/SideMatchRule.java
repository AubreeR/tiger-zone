package tiger_zone;



public class SideMatchRule extends PlacementRule
{
	
	public SideMatchRule(BoardCell[][] boardState,int row, int col)
	{
		super(boardState,row, col);
	}
	
	@Override
	public boolean evaluate()
	{
		try
		{
			if(true/*check all adjacent tiles*/)
			{
				
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

