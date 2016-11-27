package tiger_zone;

import java.util.ArrayList;

public class TigerLakeRule extends PlacementRule
{
	private Tile tilePlaced;//we must know the next tile to be placed in order to match sides
	private boolean[][] visited;
	private ArrayList<BoardCell> usedTiles;
	private int zone;
	
	
	public TigerLakeRule(Board boardState,int cartX, int cartY, Tile tilePlaced, int zone)
	{

		super(boardState,cartX, cartY, true);
		visited = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		super.setRuleName("TigerLake Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		usedTiles = new ArrayList<BoardCell>();
		
		
	}
	public TigerLakeRule(Board boardState,int cartX, int cartY, Tile tilePlaced, int zone, boolean trace)
	{

		super(boardState,cartX, cartY, trace);
		visited = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		super.setRuleName("TigerLake Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		usedTiles = new ArrayList<BoardCell>();
		
		
	}
	
	@Override
	public boolean evaluate()
	{
		try{
		//System.err.println("HELP I AM NOT DONE. I ONLY GET THE COMPLETION OF A LAKE. CANNOT BE FINISHED UNTIL TIGERS ARE DONE");
		if(checkChildren(this.cartX, this.cartY))
		{
			if(trace)
			for(BoardCell t : this.usedTiles)
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
			if(trace)
				System.err.println("tigerlake" + ex);
			return false;
		}
		return false;
		
		
	}
	

	
	public boolean checkChildren( int cartX, int cartY)
	{
		boolean[][] testedTiles = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		 if(this.tilePlaced.getZone(this.zone) != 'l'|| this.tilePlaced.hasTiger())
			 return false;
		return recursiveLake(testedTiles, cartX, cartY);
	}
	
		
		
	public boolean recursiveLake(boolean[][] testedTiles, int X, int Y)
	{
		if(boardState.getTile(X, Y) == null)
			return true;
		//mark adjacent tiles with lakes
		//check if 
		if(boardState.getTile(X, Y).hasTiger())
			return false;
		//already visited this tile or no more tiles to check
		if(testedTiles[boardState.getBoardPosX(X)][boardState.getBoardPosY(Y)] )
			return true;
		
		boolean ret = true;
		for(int i = 0; i < 4; i++)
		{
			if( boardState.getTile(X, Y).getSide(i) == 'l')
			{
				if(!testedTiles[boardState.getBoardPosX(X)][boardState.getBoardPosY(Y)])
				{
					usedTiles.add(this.boardState.getBoardCell(X, Y));
					testedTiles[boardState.getBoardPosX(X)][boardState.getBoardPosY(Y)] = true;
				}
				switch(i)
				{
				case 0:	
					ret = ret && recursiveLake(testedTiles, X,Y+1);
					break;
				case 1: 
					ret = ret && recursiveLake(testedTiles, X+1,Y);
					break;
				case 2: 
					ret = ret && recursiveLake(testedTiles, X,Y-1);
					break;
				case 3: 
						ret = ret && recursiveLake(testedTiles, X-1,Y);
					break;
				default:
					break;
					
				}
				
			}
		}
		
		return ret;
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition lake is not complete");
	}
	
}

