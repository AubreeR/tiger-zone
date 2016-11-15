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


import java.util.*;


class AdjacencyRule extends BasicRule
{
	
	
	
	@Override
	public boolean evaluate()
	{
		try
		{
			if(boardState[r][c])
			{
				
			}
			else
				testFailure();//if the test condition fails
			
		}
		catch(Exception ex)
		{
			System.err.println(ex);//display the error 
			return false;
		}
		return false;
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input: \"" + "\" does not equal \"Hello World\"");
	}
	
}

class SideMatchRule extends BasicRule
{
	
	@Override
	public boolean evaluate()
	{
		try
		{
			if(true/*check all adjacent tiles*/)
			{
				
			}
			else
				testFailure();//if the test condition fails
			
		}
		catch(Exception ex)
		{
			System.out.println(ex);//display the error 
			return false;
		}
		return false;
	}
	
	@Override
	public void testFailure() throws Exception
	{
		throw new Exception(super.getName() + " failed under condition input: \"" + "\" does not equal \"Hello World\"");
	}
	
}



//Maps basic rules 
class BasicRule implements Rule
{
	
	private String ruleName = "";
	private BoardCell[][] boardState;
	private int row, col;
	
	@Override
	public void setBoardState(BoardCell[][] boardState, int row, int col)
	{
		this.boardState = boardState;
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

