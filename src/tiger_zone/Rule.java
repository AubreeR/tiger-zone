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
			if(true)
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

//Description: an extension of a basic rule meant to see if the input string
//		says hello world. Eventually this will probably need to know about the
//		board somehow to check. see Basic rule and rule interface for method descriptions
class TestRule extends BasicRule
{
	String input;
	TestRule(String in)
	{
		this.input = in;
		this.setRuleName("AdjacencyRule");
	}
	
	@Override
	public boolean evaluate()
	{
		try
		{
			if(input.compareTo("Hello World") == 0)
			{
				System.out.println("TestRule passed under condition input: \"" + input + "\" equals \"Hello World\"");
				return true;
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
		throw new Exception(super.getName() + " failed under condition input: \"" + input + "\" does not equal \"Hello World\"");
	}
	
	
}

//Maps basic rules 
class BasicRule implements Rule
{
	
	private String ruleName = "";
	
	public boolean evaluate() 
	{
		return false;
	}
	
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

