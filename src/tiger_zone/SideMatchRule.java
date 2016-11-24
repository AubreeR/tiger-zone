package tiger_zone;



public class SideMatchRule extends PlacementRule
{
	private Tile nextTile;//we must know the next tile to be placed in order to match sides
	private Tile[] adj;
	
	public SideMatchRule(Board boardState,int cartX, int cartY, Tile nextTile, boolean trace)
	{

		super(boardState,cartX, cartY, trace);

		super.setRuleName("SideMatch Rule");
		this.nextTile = nextTile;
		adj = new Tile[4];//UL, UC, UR, RC, DR, DC, DL, LC
		
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
			if(Math.abs(cartY + 1) < length)//center up
				adj[0] = boardState.getTile(cartX,cartY+1);
			if(Math.abs(cartX + 1) < length && Math.abs(cartY) < length)//right center				
				adj[1] = boardState.getTile(cartX + 1,cartY);
			if(Math.abs(cartY - 1) < length)//down center
				adj[2] = boardState.getTile(cartX,cartY-1);
			if(Math.abs(cartX - 1) < length && Math.abs(cartY) < length)//left center
				adj[3] = boardState.getTile(cartX - 1,cartY);
		
			
			for(int i = 0; i < 4; i++)//cycle through all possible adjacent tiles
			{
				//don't do checks on something that isnt there
				if(adj[i] == null)
					continue;
				//												0
				//		UL	UC	RU			0							
				//		CL	XX	CR		3		1		3				1
				//		DL	DC	DR			2							
				//												2	
				switch(i)//this is to figure out what side we are checking
				{
				case 0://adjacent is UP
					if(trace){
						System.out.println("NextTile Sides: "+nextTile.getSides()[0]+","+nextTile.getSides()[1]+","+nextTile.getSides()[2]+","+nextTile.getSides()[3]);
						System.out.println("adj north Sides: "+adj[i].getSides()[0]+","+adj[i].getSides()[1]+","+adj[i].getSides()[2]+","+adj[i].getSides()[3]);
					}
					if(nextTile.getSide(0) != adj[i].getSide(2))
						throw new Exception(super.getName() + " failed under condition that the northern tile did not match");
				break;
				case 1: //adjacent is right
					if(trace){
						System.out.println("NextTile Sides: "+nextTile.getSides()[0]+","+nextTile.getSides()[1]+","+nextTile.getSides()[2]+","+nextTile.getSides()[3]);
						System.out.println("adj east Sides: "+adj[i].getSides()[0]+","+adj[i].getSides()[1]+","+adj[i].getSides()[2]+","+adj[i].getSides()[3]);
					}
					if(nextTile.getSide(1) != adj[i].getSide(3))
						throw new Exception(super.getName() + " failed under condition that the Easter tile did not match");
					break;
				case 2://adjacent is down
					if(trace){
						System.out.println("NextTile Sides: "+nextTile.getSides()[0]+","+nextTile.getSides()[1]+","+nextTile.getSides()[2]+","+nextTile.getSides()[3]);
						System.out.println("adj south Sides: "+adj[i].getSides()[0]+","+adj[i].getSides()[1]+","+adj[i].getSides()[2]+","+adj[i].getSides()[3]);
					}
					if(nextTile.getSide(2) != adj[i].getSide(0))
						throw new Exception(super.getName() + " failed under condition that the southern tile did not match");
					break;
					
				case 3://adjacent is left
					if(trace){
						System.out.println("NextTile Sides: "+nextTile.getSides()[0]+","+nextTile.getSides()[1]+","+nextTile.getSides()[2]+","+nextTile.getSides()[3]);
						System.out.println("adj west Sides: "+adj[i].getSides()[0]+","+adj[i].getSides()[1]+","+adj[i].getSides()[2]+","+adj[i].getSides()[3]);
					}
					if(nextTile.getSide(3) != adj[i].getSide(1))
						throw new Exception(super.getName() + " failed under condition that the western tile did not match");
					break;
				default:
						
				}
				
			}
			
			return true;
			
		}
		catch(Exception ex)
		{
			if(super.trace)
			{
				System.err.println(ex);//display the error 
			}
			return false;
		}
		
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input: \"" + "\" does not equal \"Hello World\"");
	}
	
}

