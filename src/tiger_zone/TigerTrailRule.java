package tiger_zone;

import java.util.ArrayList;

public class TigerTrailRule extends PlacementRule
{
	private Tile tilePlaced;//we must know the next tile to be placed in order to match sides
	private boolean[][] visited;
	private ArrayList<BoardCell> usedCells;
	private int zone;
	
	
	public TigerTrailRule(Board boardState,int cartX, int cartY, Tile tilePlaced, int zone)
	{

		super(boardState,cartX, cartY, true);
		visited = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		super.setRuleName("TigerTrail Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		usedCells = new ArrayList<BoardCell>();
		
		
	}
	
	@Override
	public boolean evaluate()
	{
		try{
		//System.err.println("HELP I AM NOT DONE. I ONLY GET THE COMPLETION OF A TRAIL. CANNOT BE FINISHED UNTIL TIGERS ARE DONE");
		if(check(this.cartX, this.cartY, this.tilePlaced))
		{
			for(BoardCell t : this.usedCells)
			{
				System.err.println("tile: (" + (t.getXCoord() - boardState.getOrigin()) +"," + ( boardState.getOrigin() -t.getYCoord()) + ") Zone: " + this.zone+" Has Tiger: " + (t.getTile().hasTiger() ? "true":"false"));
			}
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
	
	private boolean check(int x, int y, Tile tile) throws Exception
	{
		int count = 0;
		boolean ret = true;
		if(tile != null && tile.hasTiger())
			return false;
		if(visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] || tile == null || (tile.isCrossroad() && tile != this.tilePlaced))
			return true;
		for(int i = 0; i < 4; i++)
		{
			if(tile.getSide(i) != 't')
				continue;
			if(!visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)])
				this.usedCells.add(boardState.getBoardCell(x,y));
			visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] = true;
			switch(i)
			{
			case 0: 
				count++;
				ret = ret && check(x, y+1, boardState.getTile(x, y+1));
				break;
			case 1:
				count++;
				ret = ret && check(x+1, y, boardState.getTile(x+1, y));
				break;
			case 2:
				count++;
				ret = ret && check(x, y-1, boardState.getTile(x,  y-1));
				break;
			case 3:
				count++;
				if(boardState.getTile(x-1, y) != null)
					ret = ret && check(x-1, y, boardState.getTile(x-1, y));
				break;
			}
		}
		if(count == 0)
			throw new Exception(super.getName() + " failed under condition, tile had no trails");
		return ret;
	}
	private boolean recurseTrail(int x, int y, Tile tile)
	{
		boolean ret  = true;
		
		return ret;
	}
	
	private boolean checkChildren(int x, int y, Tile tile)
	{
		
		if(tile.getZone(this.zone) != 't')
			return false;
		
		boolean ret = true;
		boolean notFirst  = false;
		if(tile.hasTiger())
			return false;
		for(int i = 0; i < 4; i++)
		{
			if(tile.getSide(i)=='t')
			{
				if(!notFirst){
					this.usedCells.add(boardState.getBoardCell(x, y));
					notFirst = true;
				}
				visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] = true;
				switch(i)
				{
				case 0:
					if(boardState.getTile(x, y+1)!= null){
						ret = ret && recurse(x,y+1,boardState.getTile(x, y+1), tile, 2);
					}
					break;
				case 1:
					if(boardState.getTile(x+1, y)!= null){
						ret = ret && recurse(x+1,y,boardState.getTile(x+1, y), tile,3);
					}
					break;
				case 2:
					if(boardState.getTile(x, y-1)!= null){
						ret = ret && recurse(x,y-1,boardState.getTile(x, y-1), tile, 0);
					}
					break;
				case 3:
					if(boardState.getTile(x-1, y)!= null){
						ret = ret && recurse(x-1,y,boardState.getTile(x-1, y), tile,1);
					}
					break;
				default:
				}
			}
		}
		
		return ret;
	}
	
	private boolean recurse(int x, int y, Tile tile, Tile startTile, int dir)
	{
		if(tile.hasTiger())
			return false;
		boolean ret = true;
	
		if((tile == startTile) || visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)])
		{
			return true;
		}
		if(tile.isCrossroad())
		{
			if(!visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)])
			{
				this.usedCells.add(boardState.getBoardCell(x, y));
				return true;
			}
			else
				return true;
			
		}
		
		this.usedCells.add(boardState.getBoardCell(x, y));
		//find all adj tiles that have not already been visited
		for(int i = 0; i < 4; i++)
		{
			//if you are looking in the direction you just came, ignore it
			if(i == dir || tile.getSide(i) != 't')
				continue;
			if(!visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)])
				this.usedCells.add(boardState.getBoardCell(x, y));
			
			switch(i)
			{
			case 0:
				visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] = true;
				if(boardState.getTile(x, y+1)!= null){
					ret = ret && recurse(x,y+1,boardState.getTile(x, y+1), startTile, 2);
				}
				break;
			case 1:
				visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] = true;
				if(boardState.getTile(x+1, y)!= null){

					ret = ret && recurse(x+1,y,boardState.getTile(x+1, y), startTile,3);
				}
				break;
			case 2:

				visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] = true;
				if(boardState.getTile(x, y-1)!= null){

					ret = ret && recurse(x,y-1,boardState.getTile(x, y-1), startTile, 0);
				}
				break;
			case 3:

				visited[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] = true;
				if(boardState.getTile(x-1, y)!= null){

					ret = ret && recurse(x-1,y,boardState.getTile(x-1, y), startTile,1);
				}
				break;
			default:
			}
			
		}

		return ret;
		
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input road is not complete");
	}
	
}

