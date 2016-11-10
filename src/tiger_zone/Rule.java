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

//Manages rules and validation of tile placement
class RuleEngine
{
	ArrayList<Rule> ruleList;
	RuleEngine()
	{
		ruleList = new ArrayList<Rule>();
	}
	
	//Description: adds a rule to to the existing set of tests
	//returns: void
	void addRule(Rule gameRule)
	{
		ruleList.add(gameRule);	
	}
	
	//Description: removes a rule from the set of rules to be tested
	//	uses the name of the rule to find it
	//returns: 	true == rules successfully found and removed
	//			false == rule was not found or was not removed
	boolean removeRule(String ruleName)
	{
		boolean flagReturn = false;
		for (int i = 0; i < ruleList.size(); i++)
			if(ruleName.compareTo(ruleList.get(i).getName()) == 0)
			{
				ruleList.remove(i);
				flagReturn = true;
			}
				
		return flagReturn;
	}
	
	
	//Description: test a specific subset of the rules
	//returns: 	true == all rules passes
	//			false == a rule failed
	boolean evaluateRules(ArrayList<String> ruleSet)
	{
		boolean flagReturn = true;
		//should probably implement a find functionality that 
		//isn't n^2
		for(String name : ruleSet)
		{
			for(Rule rule : ruleList)
			{
			
				if(rule.getName().compareTo(name) == 0)
				{
					//allows us to run through all evaluations but still
					//retain failure status
					flagReturn = rule.evaluate() && flagReturn;
					 
				}
				
			}
		}
		
		return flagReturn;
	}
	
	//Description: test a specific subset of the rules with the same name
	//returns: 	true == all rules passes
	//			false == a rule failed
	boolean evaluateRules(String testRule)
	{
	    boolean flagReturn = true;	
		for(Rule rule : ruleList)
		{
			if(testRule.compareTo(rule.getName()) == 0)
			{
				flagReturn = rule.evaluate() && flagReturn;
				
			}
			
		}
		return flagReturn;
	}
	
	//Description: test the entire set of existing rules
	//returns: 	true== all rules passed
	//       	false == a rule failed
	boolean evaluateRules()
	{
		boolean flagReturn = true;
		for(Rule rule : ruleList)
			flagReturn = rule.evaluate() && flagReturn;
		return flagReturn;
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
			System.out.println(ex);//display the error 
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

