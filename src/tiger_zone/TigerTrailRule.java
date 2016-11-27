package tiger_zone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TigerTrailRule extends PlacementRule {
	private Tile tilePlaced;
	private Set<Position> visited = new HashSet<Position>();
	private ArrayList<Position> usedCells;
	private int zone;
	
	public TigerTrailRule(Board boardState,int cartX, int cartY, Tile tilePlaced, int zone) {
		super(boardState, cartX, cartY, true);
		super.setRuleName("TigerTrail Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		this.usedCells = new ArrayList<Position>();
	}
	
	@Override
	public boolean evaluate() {
		try {
			if (check(this.cartX, this.cartY, this.tilePlaced)) {
				for (Position t : this.usedCells) {
					System.err.println("tile: (" + t.getX() +"," + t.getY() + ") Zone: " + this.zone+" Has Tiger: "
							+ (this.boardState.getTile(t).hasTiger() ? "true" : "false"));
				}
				return true;
				
			}
			else {
				testFailure();
			}
		}
		catch(Exception ex) {
			System.err.println(ex);
			return false;
		}
		return false;
	}
	
	private boolean check(int x, int y, Tile tile) throws Exception
	{
		int count = 0;
		boolean ret = true;
		Position p = new Position(x, y);
		
		if (tile != null && tile.hasTiger()) {
			return false;
		}
		
		if (visited.contains(p) || tile == null || (tile.isCrossroad() && tile != this.tilePlaced)) {
			return true;
		}
		
		for (int i = 0; i < 4; i++) {
			if(tile.getSide(i) != 't') {
				continue;
			}
			
			if(!visited.contains(p)) {
				this.usedCells.add(p);
			}
			
			visited.add(p);
			
			switch(i) {
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
					if(boardState.getTile(x-1, y) != null) {
						ret = ret && check(x-1, y, boardState.getTile(x-1, y));
					}
					break;
			}
		}
		if(count == 0) {
			throw new Exception(super.getName() + " failed under condition, tile had no trails");
		}
		return ret;
	}
	
	private boolean recurseTrail(int x, int y, Tile tile) {
		boolean ret  = true;
		return ret;
	}
	
	private boolean checkChildren(int x, int y, Tile tile) {
		if(tile.getZone(this.zone) != 't') {
			return false;
		}
		
		boolean ret = true;
		boolean notFirst  = false;
		Position p = new Position(x, y);
		
		if(tile.hasTiger()) {
			return false;
		}
		
		for (int i = 0; i < 4; i++) {
			if (tile.getSide(i)=='t') {
				if (!notFirst) {
					this.usedCells.add(p);
					notFirst = true;
				}
				
				visited.add(p);
				
				switch(i) {
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
				}
			}
		}
		return ret;
	}
	
	private boolean recurse(int x, int y, Tile tile, Tile startTile, int dir) {
		if(tile.hasTiger()) {
			return false;
		}
		
		boolean ret = true;
		Position p = new Position(x, y);
	
		if((tile == startTile) || visited.contains(p)) {
			return true;
		}
		
		if(tile.isCrossroad()) {
			if(!visited.contains(p)) {
				this.usedCells.add(p);
				return true;
			}
			else {
				return true;
			}
		}
		
		this.usedCells.add(p);
		
		// find all adjacent tiles that have not already been visited
		for(int i = 0; i < 4; i++) {
			//if you are looking in the direction you just came, ignore it
			if (i == dir || tile.getSide(i) != 't') {
				continue;
			}
			
			if (!visited.contains(p)) {
				this.usedCells.add(p);
			}
			
			switch(i) {
				case 0:
					visited.add(p);
					if(boardState.getTile(x, y+1)!= null){
						ret = ret && recurse(x,y+1,boardState.getTile(x, y+1), startTile, 2);
					}
					break;
					
				case 1:
					visited.add(p);
					if(boardState.getTile(x+1, y)!= null){
						ret = ret && recurse(x+1,y,boardState.getTile(x+1, y), startTile,3);
					}
					break;
					
				case 2:
					visited.add(p);
					if(boardState.getTile(x, y-1)!= null){
						ret = ret && recurse(x,y-1,boardState.getTile(x, y-1), startTile, 0);
					}
					break;
					
				case 3:
					visited.add(p);
					if(boardState.getTile(x-1, y)!= null){
						ret = ret && recurse(x-1,y,boardState.getTile(x-1, y), startTile,1);
					}
					break;
			}	
		}
		return ret;
		
	}
	
	@Override
	public void testFailure() throws Exception {
		throw new Exception(super.getName() + " failed under condition input road is not complete");
	}
	
}

