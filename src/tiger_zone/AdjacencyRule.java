package tiger_zone;

public class AdjacencyRule extends PlacementRule
{
	
	public AdjacencyRule(BoardCell[][] boardState,int row, int col)
	{
		super(boardState,row, col);
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
			boolean checkSize = (row < boardState.length && row >= 0) && (col < boardState.length && col >= 0);
			boolean PlacementTileEmpty = boardState[row][col].getTile() == null;
			
			//if its a valid size and the tile you want to put something is empty, 
			//check to see if there is an adjacent tile
			if(checkSize && PlacementTileEmpty)
			{
				if(row != 0)
					checkUp = boardState[row-1][col] != null;
				if(row != boardState.length - 1)
					checkDown = boardState[row+1][col] != null;
				if(col != 0)
					checkLeft = boardState[row][col-1] != null;
				if(col != boardState[row].length)
					checkRight = boardState[row][col+1] != null;
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
