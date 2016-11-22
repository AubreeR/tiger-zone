package tiger_zone;



public class SideMatchRule extends PlacementRule
{
	private Tile nextTile;//we must know the next tile to be placed in order to match sides
	private Tile[] adj;
	
	public SideMatchRule(Board boardState,int cartX, int cartY, Tile nextTile)
	{

		super(boardState,cartX, cartY);

		super.setRuleName("SideMatch Rule");
		this.nextTile = nextTile;
		adj = new Tile[8];//UL, UC, UR, RC, DR, DC, DL, LC
		
	//		UL	UC	RU
	//		CL	XX	CR
	//		DL	DC	DR
		
	}
	
	@Override
	public boolean evaluate()
	{
		int length = boardState.getBoardLength();
		try
		{
			if(Math.abs(cartX) > length || (Math.abs(cartY) > length ))
				throw new Exception(super.getName() + " failed under condition: tile tile desired palcement out of bounds.");
			/*
			 * check to see which tiles are adjacent if any
			 */
			if(cartX - 1 >= (-1)*(length - 1))//left
			{
				if(cartY - 1 >= 0 && cartY - 1 < length)//downleft
					adj[6] = boardState.getTile(cartX-1,cartY-1);
				if(cartY >= 0 && cartY < length)//left center
					adj[7] = boardState.getTile(cartX - 1,cartY);
				if(cartY + 1 >= 0 && cartY+1 < length)//left up
					adj[0] = boardState.getTile(cartX - 1,cartY+1);
					
					
			}
			if(cartX + 1 >= (-1)*(length - 1) && cartX + 1 <length)//right
			{
				if(cartY - 1 >= 0 && cartY - 1 < length)//downright
					adj[4] = boardState.getTile(cartX+1,cartY-1);
				if(cartY >= 0 && cartY < length)//center right
					adj[3] = boardState.getTile(cartX + 1,cartY);
				if(cartY + 1 >= 0 && cartY+1 < length)//upright
					adj[2] = boardState.getTile(cartX + 1,cartY+1);
					
					
			}
				
			if(cartY - 1 >= 0 && cartY - 1 < length)//down center
				adj[5] = boardState.getTile(cartX,cartY-1);
			
			if(cartY + 1 >= 0 && cartY+1 < length)//center up
				adj[1] = boardState.getTile(cartX,cartY+1);
			
			for(int i = 0; i < 8; i++)//cycle through all possible adjacent tiles
			{
				//don't do checks on something that isnt there
				if(adj[i] == null)
					continue;
				//												0
				//		UL	UC	RU		0	1	2						
				//		CL	XX	CR		7		3		3				1
				//		DL	DC	DR		6	5	4						
				//												2	
				switch(i)//this is to figure out what side we are checking
				{
				case 0:
					break;
				case 1: //UC Case

					if(nextTile.getSides()[0] != adj[i].getSides()[2])
						throw new Exception(super.getName() + " failed under condition that the northern tile did not match");
					break;
				case 2:
					break;
				case 3://center right
					if(nextTile.getSides()[1] != adj[i].getSides()[3])
						throw new Exception(super.getName() + " failed under condition that the Easter tile did not match");
					break;
				case 4:
					
					break;
				case 5://center down
					if(nextTile.getSides()[2] != adj[i].getSides()[0])
						throw new Exception(super.getName() + " failed under condition that the southern tile did not match");
					break;
				case 6:
					break;
				case 7:
					if(nextTile.getSides()[3] != adj[i].getSides()[1])
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
			System.err.println(ex);//display the error 
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

