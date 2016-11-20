package tiger_zone;

public class AdjacencyRule extends PlacementRule
{
	
	public AdjacencyRule(Board boardState,int cartX, int cartY)
	{
		super(boardState,cartX, cartY);
		super.setRuleName("Adjacency Rule");
	}
	
	
	@Override
	public boolean evaluate()
	{
		boolean checkUp = false;
		boolean checkDown = false;
		boolean checkLeft = false;
		boolean checkRight = false;
		try
		{
			//make sure that the provided values are on the board
			boolean checkSize = (Math.abs(cartX) < boardState.getBoardLength() && Math.abs(cartX) >= 0) && (Math.abs(cartY) < boardState.getBoardLength() && Math.abs(cartY) >= 0);
			boolean PlacementTileEmpty = boardState.getTile(cartX,cartY) == null;
			
			//if its a valid size and the tile you want to put something is empty, 
			//check to see if there is an adjacent tile
			if(checkSize && PlacementTileEmpty)
			{
				if(cartX != boardState.getBoardLength() - 1)
					checkRight = boardState.getTile(cartX+1, cartY) != null;
				if(cartX != (-1) *(boardState.getBoardLength() - 1))
					checkLeft = boardState.getTile(cartX -1, cartY) != null;
				if(cartY != (-1) *(boardState.getBoardLength() - 1))
					checkDown =boardState.getTile(cartX, cartY-1) != null;
				if(cartY != boardState.getBoardLength() - 1)
					checkUp = boardState.getTile(cartX, cartY+1) != null;
			}
			else	
				throw new Exception(super.getName() + " failed under test condition index out of bounds or tile already occupied");
			
			//these will all be false if the size or placement is 
			if(checkUp || checkDown || checkLeft || checkRight)
			{
				return true;
			}
			else
				testFailure();//if the test condition fails
			
		}
		catch(Exception ex)
		{
			System.err.println(ex);//display the error 
			return false;
		}
		return false;
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under test condition that the tile was attempted to be placed in a non adjacent to another tile.");
	}
	
}
