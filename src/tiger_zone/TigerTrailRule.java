package tiger_zone;

import java.util.ArrayList;

public class TigerTrailRule extends PlacementRule
{
	private Tile tilePlaced;//we must know the next tile to be placed in order to match sides
	private boolean[][] visited;
	private ArrayList<Tile> usedTiles;
	
	
	public TigerTrailRule(Board boardState,int cartX, int cartY, Tile tilePlaced)
	{

		super(boardState,cartX, cartY);
		visited = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		super.setRuleName("TigerTrail Rule");
		this.tilePlaced = tilePlaced;
		usedTiles = new ArrayList<Tile>();
		
		
	}
	
	@Override
	public boolean evaluate()
	{
		try{
		//System.err.println("HELP I AM NOT DONE. I ONLY GET THE COMPLETION OF A TRAIL. CANNOT BE FINISHED UNTIL TIGERS ARE DONE");
		if(checkChildren(this.cartX, this.cartY, this.tilePlaced))
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
		return count == 3|| count == 4 || count == 1;
	}
	
	private boolean checkChildren(int x, int y, Tile tile)
	{
		boolean ret = true;
		int countTrails = 0;
		boolean nullSide = false;
		for(int i = 0; i < 4; i++)
		{
			if(tile.getSide(i)=='t')
			{
				
				switch(i)
				{
				case 0:
					if(boardState.getTile(x, y+1)!= null){
						countTrails++;
						ret = ret && recurse(x,y+1,boardState.getTile(x, y+1), tile, 2);
					}
					else nullSide = true;
					break;
				case 1:
					if(boardState.getTile(x+1, y)!= null){
						countTrails++;
						ret = ret && recurse(x+1,y,boardState.getTile(x+1, y), tile,3);
					}
					else nullSide = true;
					break;
				case 2:
					if(boardState.getTile(x, y-1)!= null){
						countTrails++;
						ret = ret && recurse(x,y-1,boardState.getTile(x, y-1), tile, 0);
					}
					else nullSide = true;
					break;
				case 3:
					if(boardState.getTile(x-1, y)!= null){
						countTrails++;
						ret = ret && recurse(x-1,y,boardState.getTile(x-1, y), tile,1);
					}
					else nullSide = true;
					break;
				default:
				}
			}
		}
		if(countTrails == 0)
			return false;
		if(!isCrossroad(tile) && nullSide)
			return false;
		return ret;
	}
	
	private boolean recurse(int x, int y, Tile tile, Tile startTile, int dir)
	{
		if(tile == null)
			return false;
		boolean ret = true;
		int countTrails = 0;
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
				if(i == dir)
					continue;
				if(tile.getSide(i)=='t')
				{
					switch(i)
					{
					case 0:
						if(boardState.getTile(x, y+1)!= null){
							countTrails++;
							ret = ret && recurse(x,y+1,boardState.getTile(x, y+1), tile, 2);
						}
						break;
					case 1:
						if(boardState.getTile(x+1, y)!= null){
							countTrails++;
							ret = ret && recurse(x+1,y,boardState.getTile(x+1, y), tile,3);
						}
						break;
					case 2:
						if(boardState.getTile(x, y-1)!= null){
							countTrails++;
							ret = ret && recurse(x,y-1,boardState.getTile(x, y-1), tile, 0);
						}
						break;
					case 3:
						if(boardState.getTile(x-1, y)!= null){
							countTrails++;
							ret = ret && recurse(x-1,y,boardState.getTile(x-1, y), tile,1);
						}
						break;
					default:
					}
				}
			}
			if(countTrails == 0)
				return false;
			return ret;
		}
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input road is not complete");
	}
	
}

