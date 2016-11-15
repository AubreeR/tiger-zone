package tiger_zone;

/*************************
 * TODO:
 * 	implement better selective evaluation in RuleEngine:evaluateRules
 *************************/

/*EXAMPLE FOR USING RULE ENGINE
 * 		RuleEngine re = new RuleEngine();
 * 		TestRule ar = new TestRule("input");
 *		re.addRule(ar);
 *		ar = new TestRule("Hello World");
 *		re.addRule(ar);
 *		re.evaluateRules();
 */
//Maps basic rules 
class PlacementRule implements Rule
{
	
	private String ruleName = "";
	protected BoardCell[][] boardState;
	protected int row, col;
	PlacementRule(BoardCell[][] boardState, int row, int col)
	{
		this.boardState = boardState;
		this.row = row;
		this.col = col;
	}
	
	@Override
	public void setBoardState(BoardCell[][] boardState, int row, int col)
	{
		this.boardState = boardState;
		this.row = row;
		this.col = col;
		
	}
	
	@Override
	public void setPosition(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	@Override
	public void setBoardState(BoardCell[][] boardState)
	{
		this.boardState = boardState;
	}
	
	
	@Override
	public boolean evaluate() 
	{
		return false;
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception("Basic Rule Method: testFailure was not overriden");
	}
	
	@Override
	public boolean setRuleName(String ruleName) 
	{
		this.ruleName = ruleName;
		return true;
	}
	
	@Override
	public String getName() 
	{ return ruleName; }
}

public interface Rule
{
	void setBoardState(BoardCell[][] boardState);
	void setBoardState(BoardCell[][] boardState, int row, int col);
	
	void setPosition(int row, int col);
	
	//Description:hold conditions for passing or failing 
	//	the given rule set
	//returns: 	true if rule is validated
	//			false if rule fails
	boolean evaluate();
	
	
	//Description: the result of failing a rule
	//returns: void
	void testFailure() throws Exception;
	
	//Description: basic rule name getter
	//returns: the name of the string
	String getName();
	
	//Description: basic rule name setter
	//returns: true is name is set
	boolean setRuleName(String ruleName);
	
}

