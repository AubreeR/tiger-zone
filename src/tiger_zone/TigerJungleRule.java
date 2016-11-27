package tiger_zone;

import java.util.HashSet;
import java.util.Set;

public class TigerJungleRule extends PlacementRule {
	private final Tile tilePlaced;
	private final Set<Position> visited = new HashSet<Position>();
	private final int zone;
	
	
	public TigerJungleRule(Board boardState,int cartX, int cartY, Tile tilePlaced, int zone) {
		super(boardState, cartX, cartY, true);
		super.setRuleName("TigerJungle Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
	}
	
	private final boolean recursiveJungle(Set<Position> testedTiles, int x, int y) {
		boolean ret = true;
		
		if (boardState.getTile(x,y).hasTiger()){
			return false;
		}
		
		// already visited this tile or no more tiles to check
		if (testedTiles.contains(new Position(x, y)) || boardState.getTile(x, y) == null){
			return true;
		}
		
		for (int i = 0; i < 4; i++) {
			if (boardState.getTile(x, y).getSide(i) == 'j') {
				switch(i) {
					case 0:
						testedTiles.add(new Position(x, y));
						ret = ret && this.recursiveJungle(testedTiles, x, y + 1);
						break;
				}
			}
		}
		return ret;
	}
}