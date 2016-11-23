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
	protected Board boardState;
	protected int cartX, cartY;
	protected boolean trace;
	PlacementRule(Board boardState, int cartX, int cartY, boolean trace)
	{
		this.boardState = boardState;
		this.cartX = cartX;
		this.cartY = cartY;
		this.trace = trace;
	}
	
	@Override
	public void setBoardState(Board boardState, int cartX, int cartY)
	{
		this.boardState = boardState;
		this.cartX = cartX;
		this.cartY = cartY;
		
	}
	
	@Override
	public void setPosition(int cartX, int cartY)
	{
		this.cartX = cartX;
		this.cartY = cartY;
	}
	
	@Override
	public void setBoardState(Board boardState)
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
	void setBoardState(Board boardState);
	void setBoardState(Board boardState, int cartX, int cartY);
	
	void setPosition(int cartX, int cartY);
	
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

