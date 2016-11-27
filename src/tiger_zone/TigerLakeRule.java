package tiger_zone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TigerLakeRule extends PlacementRule {
	private Tile tilePlaced;
	private Set<Position> visited = new HashSet<Position>();
	private ArrayList<Position> usedTiles;
	private int zone;
	
	
	public TigerLakeRule(Board boardState,int cartX, int cartY, Tile tilePlaced, int zone) {
		super(boardState,cartX, cartY, true);
		super.setRuleName("TigerLake Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		this.usedTiles = new ArrayList<Position>();
	}
	
	public TigerLakeRule(Board boardState, int cartX, int cartY, Tile tilePlaced, int zone, boolean trace) {
		super(boardState,cartX, cartY, trace);
		super.setRuleName("TigerLake Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		this.usedTiles = new ArrayList<Position>();
	}
	
	@Override
	public boolean evaluate() {
		try {
			if(checkChildren(this.cartX, this.cartY)) {
				if(trace) {
					for(Position t : this.usedTiles) {
						System.err.println("tile: (" + t.getX() +"," + t.getY() + ") Zone: " + this.zone+" Has Tiger: "
								+ (this.boardState.getTile(t.getX(), t.getY()).hasTiger() ? "true" : "false"));
					}
				}
				return true;
				
			}
			else {
				testFailure();
			}
		}
		catch(Exception ex) {
			if(trace) {
				System.err.println("tigerlake" + ex);
			}
			return false;
		}
		return false;
	}

	public boolean checkChildren( int cartX, int cartY) {
		Set<Position> testedTiles = new HashSet<Position>();
		if (this.tilePlaced.getZone(this.zone) != 'l'|| this.tilePlaced.hasTiger()) {
			 return false;
		}
		return recursiveLake(testedTiles, cartX, cartY);
	}
	
	public boolean recursiveLake(Set<Position> testedTiles, int X, int Y) {
		if(boardState.getTile(X, Y) == null) {
			return true;
		}
		
		//mark adjacent tiles with lakes
		if(boardState.getTile(X, Y).hasTiger()) {
			return false;
		}
		
		//already visited this tile or no more tiles to check
		if(testedTiles.contains(new Position(X, Y))) {
			return true;
		}
		
		boolean ret = true;
		for (int i = 0; i < 4; i++) {
			if (boardState.getTile(X, Y).getSide(i) == 'l') {
				if(!testedTiles.contains(new Position(X, Y))) {
					usedTiles.add(new Position(X, Y));
					testedTiles.add(new Position(X, Y));
				}
				switch(i) {
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
	public void testFailure() throws Exception {
		throw new Exception(super.getName() + " failed under condition lake is not complete");
	}
}

