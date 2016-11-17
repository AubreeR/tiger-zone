package tiger_zone;



public class SideMatchRule extends PlacementRule
{
	private Tile nextTile;//we must know the next tile to be placed in order to match sides
	private Tile[] adj;
	
	public SideMatchRule(BoardCell[][] boardState,int row, int col, Tile nextTile)
	{
		super(boardState,row, col);
		this.nextTile = nextTile;
		adj = new Tile[8];//UL, UC, UR, RC, DR, DC, DL, LC
		
	//		UL	UC	RU
	//		CL	XX	CR
	//		DL	DC	DR
		
	}
	
	@Override
	public boolean evaluate()
	{
		try
		{
			if(row > boardState.length || row < 0 || col > boardState[0].length || col < 0)
				throw new Exception(super.getName() + " failed under condition: tile tile desired palcement out of bounds.");
			/*
			 * check to see which tiles are adjacent if any
			 */
			if(row - 1 >= 0 && row - 1 < boardState.length)//up
			{
				if(col - 1 >= 0 && col - 1 < boardState[0].length)//upleft
					adj[0] = boardState[row-1][col-1].getTile();
				if(col >= 0 && col < boardState[0].length)
					adj[1] = boardState[row - 1][col].getTile();
				if(col + 1 >= 0 && col+1 < boardState[0].length)
					adj[2] = boardState[row - 1][col+1].getTile();
					
					
			}
			if(row + 1 >= 0 && row + 1 < boardState.length)//down
			{
				if(col - 1 >= 0 && col - 1 < boardState[0].length)//downleft
					adj[4] = boardState[row+1][col-1].getTile();
				if(col >= 0 && col < boardState[0].length)//downcenter
					adj[5] = boardState[row + 1][col].getTile();
				if(col + 1 >= 0 && col+1 < boardState[0].length)//downright
					adj[6] = boardState[row + 1][col+1].getTile();
					
					
			}
				
			if(col - 1 >= 0 && col - 1 < boardState[0].length)//centerleft
				adj[7] = boardState[row][col-1].getTile();
			
			if(col + 1 >= 0 && col+1 < boardState[0].length)//center right
				adj[3] = boardState[row][col+1].getTile();
			
			for(int i = 0; i < 8; i++)//cycle through all possible adjacent tiles
			{
				//don't do checks on something that isnt there
				if(adj[i] == null)
					continue;
				//											0	1	2
				//		UL	UC	RU		0	1	2		11				3
				//		CL	XX	CR		7		3		10				4
				//		DL	DC	DR		6	5	4		9				5
				//											8	7	6
				switch(i)//this is to figure out what side we are checking
				{
				case 0:
					break;
				case 1: //UC Case
					if(nextTile.getSide(1) != adj[i].getSide(1))
						throw new Exception(super.getName() + " failed under condition that the northern tile did not match");
					break;
				case 2:
					break;
				case 3://center right
					if(nextTile.getSide(4) != adj[i].getSide(4))
						throw new Exception(super.getName() + " failed under condition that the Easter tile did not match");
					break;
				case 4:
					
					break;
				case 5://center down
					if(nextTile.getSide(7) != adj[i].getSide(7))
						throw new Exception(super.getName() + " failed under condition that the southern tile did not match");
					break;
				case 6:
					break;
				case 7:
					if(nextTile.getSide(10) != adj[i].getSide(10))
						throw new Exception(super.getName() + " failed under condition that the western tile did not match");
					break;
				default:
						
				}
				
			}
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

