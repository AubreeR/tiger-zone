package tiger_zone;

public class PlacementRule implements Rule {
	private String ruleName = "";
	protected Board boardState;
	protected int cartX, cartY;
	protected boolean trace;
	
	public PlacementRule(Board boardState, int cartX, int cartY, boolean trace) {
		this.boardState = boardState;
		this.cartX = cartX;
		this.cartY = cartY;
		this.trace = trace;
	}
	
	@Override
	public void setBoardState(Board boardState, int cartX, int cartY) {
		this.boardState = boardState;
		this.cartX = cartX;
		this.cartY = cartY;
		
	}
	
	@Override
	public void setPosition(int cartX, int cartY) {
		this.cartX = cartX;
		this.cartY = cartY;
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