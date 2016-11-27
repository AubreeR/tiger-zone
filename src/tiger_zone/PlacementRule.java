package tiger_zone;

public class PlacementRule implements Rule {
	private String ruleName = "";
	protected Board boardState;
	protected Position position;
	protected boolean trace;
	
	public PlacementRule(Board boardState, Position position, boolean trace) {
		this.boardState = boardState;
		this.position = position;
		this.trace = trace;
	}
	
	@Override
	public void setBoardState(Board boardState, Position position) {
		this.boardState = boardState;
		this.position = position;
	}
	
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}
	
	@Override
	public void setBoardState(Board boardState) {
		this.boardState = boardState;
	}
	
	@Override
	public boolean evaluate()  {
		return false;
	}
	
	@Override
	public void testFailure() throws Exception {
		throw new Exception("Basic Rule Method: testFailure was not overriden");
	}
	
	@Override
	public boolean setRuleName(String ruleName) {
		this.ruleName = ruleName;
		return true;
	}
	
	@Override
	public String getName() {
		return ruleName;
	}
}