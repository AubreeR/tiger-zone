package tiger_zone;

import java.util.ArrayList;

public class TigerJungleRule extends PlacementRule{
	private Tile tilePlaced;//we must know the next tile to be placed in order to match sides
	private boolean[][] visited;
	private ArrayList<BoardCell> usedTiles;
	private int zone;
	
	
	public TigerJungleRule(Board boardState,int cartX, int cartY, Tile tilePlaced, int zone)
	{
		super(boardState,cartX, cartY, true);
		visited = new boolean[boardState.getBoardLength()][boardState.getBoardLength()];
		super.setRuleName("TigerJungle Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		usedTiles = new ArrayList<BoardCell>();
	}
	
	private boolean recursiveJungle(Boolean testedTiles[][], int x, int y){
		Boolean ret = true;
		
		if(boardState.getTile(x,y).hasTiger()){
			return false;
		}
		//already visited this tile or no more tiles to check
		if(testedTiles[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] || boardState.getTile(x, y) == null){
			return true;
		}
		
		for(int i = 0; i < 4; i++){
			if(boardState.getTile(x, y).getSide(i) == 'j'){
				switch(i)
				{
				case 0:	if(!testedTiles[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)])
							usedTiles.add(this.boardState.getBoardCell(x, y));
						testedTiles[boardState.getBoardPosX(x)][boardState.getBoardPosY(y)] = true;
						
						ret = ret && recursiveJungle(testedTiles, x,y+1);
					break;
			}
		}
		
		}
		return ret;

	}
}
		

