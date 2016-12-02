package tiger_zone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TigerTrailRule extends PlacementRule {
	private Tile tilePlaced;
	private Set<Position> visited = new HashSet<Position>();
	private ArrayList<Position> usedCells;
	private int zone;
	
	public TigerTrailRule(Board boardState, Position position, Tile tilePlaced, int zone) {
		super(boardState, position, true);
		super.setRuleName("TigerTrail Rule");
		this.tilePlaced = tilePlaced;
		this.zone = zone;
		this.usedCells = new ArrayList<Position>();
	}
	
	@Override
	public boolean evaluate() {
		try {
			if (this.check(this.position, this.tilePlaced)) {
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
	
	private boolean check(Position position, Tile tile) throws Exception
	{
		int count = 0;
		boolean ret = true;
		
		if (tile != null && tile.hasTiger()) {
			return false;
		}
		
		if (visited.contains(position) || tile == null || (tile.isTerminatingRoad() && tile != this.tilePlaced)) {
			return true;
		}
		
		for (int i = 0; i < 4; i++) {
			if(tile.getSide(i) != 't') {
				continue;
			}
			
			if(!visited.contains(position)) {
				this.usedCells.add(position);
			}
			
			visited.add(position);
			
			switch(i) {
				case 0: 
					count++;
					ret = ret && check(position.north(), boardState.getTile(position.north()));
					break;
				case 1:
					count++;
					ret = ret && check(position.east(), boardState.getTile(position.east()));
					break;
				case 2:
					count++;
					ret = ret && check(position.south(), boardState.getTile(position.south()));
					break;
				case 3:
					count++;
					if(boardState.getTile(position.west()) != null) {
						ret = ret && check(position.west(), boardState.getTile(position.west()));
					}
					break;
			}
		}
		if(count == 0) {
			throw new Exception(super.getName() + " failed under condition, tile had no trails");
		}
		return ret;
	}
	
	private boolean recurseTrail(Position position, Tile tile) {
		boolean ret  = true;
		return ret;
	}
	
	private boolean checkChildren(Position position, Tile tile) {
		if(tile.getZone(this.zone) != 't') {
			return false;
		}
		
		boolean ret = true;
		boolean notFirst  = false;
		
		if(tile.hasTiger()) {
			return false;
		}
		
		for (int i = 0; i < 4; i++) {
			if (tile.getSide(i)=='t') {
				if (!notFirst) {
					this.usedCells.add(position);
					notFirst = true;
				}
				
				visited.add(position);
				
				switch(i) {
					case 0:
						if (boardState.getTile(position.north()) != null) {
							ret = ret && recurse(position.north(), boardState.getTile(position.north()), tile, 2);
						}
						break;
					case 1:
						if (boardState.getTile(position.east()) != null) {
							ret = ret && recurse(position.east(), boardState.getTile(position.east()), tile, 3);
						}
						break;
					case 2:
						if (boardState.getTile(position.south()) != null) {
							ret = ret && recurse(position.south(), boardState.getTile(position.south()), tile, 0);
						}
						break;
					case 3:
						if (boardState.getTile(position.west()) !=  null) {
							ret = ret && recurse(position.west(), boardState.getTile(position.west()), tile, 1);
						}
						break;
				}
			}
		}
		return ret;
	}
	
	private boolean recurse(Position position, Tile tile, Tile startTile, int dir) {
		if(tile.hasTiger()) {
			return false;
		}
		
		boolean ret = true;
	
		if((tile == startTile) || visited.contains(position)) {
			return true;
		}
		
		if(tile.isTerminatingRoad()) {
			if(!visited.contains(position)) {
				this.usedCells.add(position);
				return true;
			}
			else {
				return true;
			}
		}
		
		this.usedCells.add(position);
		
		// find all adjacent tiles that have not already been visited
		for(int i = 0; i < 4; i++) {
			//if you are looking in the direction you just came, ignore it
			if (i == dir || tile.getSide(i) != 't') {
				continue;
			}
			
			if (!visited.contains(position)) {
				this.usedCells.add(position);
			}
			
			visited.add(position);
			
			switch(i) {
				case 0:
					if (boardState.getTile(position.north()) != null) {
						ret = ret && recurse(position.north(), boardState.getTile(position.north()), startTile, 2);
					}
					break;
				case 1:
					if (boardState.getTile(position.east()) != null) {
						ret = ret && recurse(position.east(), boardState.getTile(position.east()), startTile, 3);
					}
					break;
				case 2:
					if (boardState.getTile(position.south()) != null) {
						ret = ret && recurse(position.south(), boardState.getTile(position.south()), startTile, 0);
					}
					break;
				case 3:
					if (boardState.getTile(position.west()) !=  null) {
						ret = ret && recurse(position.west(), boardState.getTile(position.west()), startTile, 1);
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

