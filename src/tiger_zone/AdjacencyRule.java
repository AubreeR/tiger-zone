package tiger_zone;

public class AdjacencyRule extends PlacementRule {
	public AdjacencyRule(Board boardState, Position position, boolean trace) {
		super(boardState, position, trace);
		super.setRuleName("Adjacency Rule");
	}

	@Override
	public boolean evaluate() {
		boolean checkUp = false;
		boolean checkDown = false;
		boolean checkLeft = false;
		boolean checkRight = false;
		
		try {
			boolean placementTileEmpty = boardState.getTile(this.position) == null;
			
			// if the tile you want to put something is empty, check to see if there is an adjacent tile
			if (placementTileEmpty) {
				checkRight = this.boardState.getTile(this.position.east()) != null;
				checkLeft = this.boardState.getTile(this.position.west()) != null;
				checkDown = this.boardState.getTile(this.position.south()) != null;
				checkUp = this.boardState.getTile(this.position.north()) != null;
			}
			else {
				throw new Exception(super.getName() + " failed under test condition index out of bounds or tile already occupied");
			}
			
			// these will all be false if the size or placement is
			if (checkUp || checkDown || checkLeft || checkRight) {
				return true;
			}
			else {
				testFailure(); // if the test condition fails
			}
		}
		catch (Exception ex) {
			if(super.trace) {
				System.err.println(ex);//display the error
			}
			return false;
		}
		return false;
	}
	
	@Override
	public void testFailure() throws Exception {
		throw new Exception(super.getName() + " failed under test condition that the tile was attempted to be placed in a non adjacent to another tile.");
	}
}
