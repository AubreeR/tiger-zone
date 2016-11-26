package tiger_zone;

import java.util.ArrayList;

public class TigerLakeRule extends PlacementRule
{
	private Tile tilePlaced;//we must know the next tile to be placed in order to match sides
	private boolean[][] visited;
	private ArrayList<Tile> usedTiles;
	private int zone;
	
	
	public TigerLakeRule(Board boardState,int cartX, int cartY, Tile tilePlaced, int zone)
	{

		super(boardState,cartX, cartY, true);
		visited = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		super.setRuleName("TigerTrail Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		usedTiles = new ArrayList<Tile>();
		
		
	}
	
	@Override
	public boolean evaluate()
	{
		try{
		//System.err.println("HELP I AM NOT DONE. I ONLY GET THE COMPLETION OF A LAKE. CANNOT BE FINISHED UNTIL TIGERS ARE DONE");
		if(checkChildren(this.cartX, this.cartY))
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
	

	
	public boolean checkChildren( int cartX, int cartY)
	{
		boolean[][] testedTiles = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		 
		return recursiveLake(testedTiles, cartX, cartY);
	}
	
		
		
	public boolean recursiveLake(boolean[][] testedTiles, int cartX, int cartY)
	{
		//mark adjacent tiles with lakes
		//check if 
		if(this.tilePlaced.hasTiger())
			return false;
		if(testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] || boardState.getTile(cartX, cartY) == null)
			return true;
		
		boolean ret = true;
		int visitCount = 0;
		for(int i = 0; i < 4; i++)
		{
			if( boardState.getTile(cartX, cartY).getSide(i) == 'l')
			{
				switch(i)
				{
				case 0:	testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] = true;
						ret = ret && recursiveLake(testedTiles, cartX,cartY+1);
						visitCount++;
					break;
				case 1: testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] = true;
						ret = ret && recursiveLake(testedTiles, cartX+1,cartY);
						visitCount++;
					break;
				case 2: testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] = true;
						ret = ret && recursiveLake(testedTiles, cartX,cartY-1);
						visitCount++;
					break;
				case 3: testedTiles[boardState.getBoardPosX(cartX)][boardState.getBoardPosY(cartY)] = true;
						ret = ret && recursiveLake(testedTiles, cartX-1,cartY);
						visitCount++;
					break;
				default:
					break;
					
				}
				
			}
		}
		if(visitCount == 0)
			return false;
		
		//visited this tile
		
		return ret;
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input road is not complete");
	}
	
}

