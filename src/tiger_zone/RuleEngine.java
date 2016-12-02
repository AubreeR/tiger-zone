package tiger_zone;

import java.util.ArrayList;

/**
 * Manages rules and validation of tile placement.
 */
public class RuleEngine
{
	ArrayList<Rule> ruleList;
	public RuleEngine()
	{
		ruleList = new ArrayList<Rule>();
	}

	//Description: adds a rule to to the existing set of tests
	//returns: void
	public void addRule(Rule gameRule)
	{
		ruleList.add(gameRule);
	}

	//Description: removes a rule from the set of rules to be tested
	//	uses the name of the rule to find it
	//returns: 	true == rules successfully found and removed
	//			false == rule was not found or was not removed
	public boolean removeRule(String ruleName)
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

	public void clearRules()
	{
		ruleList = new ArrayList<Rule>();
	}

	//Description: test a specific subset of the rules
	//returns: 	true == all rules passes
	//			false == a rule failed
	public boolean evaluateRules(ArrayList<String> ruleSet)
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
	public boolean evaluateRules(String testRule)
	{
		boolean flagReturn = true;
		for(Rule rule : ruleList)
		{
			if(flagReturn)
			{
				if(testRule.compareTo(rule.getName()) == 0)
				{
					flagReturn = rule.evaluate() && flagReturn;

				}
			}
			else
				break;
		}
		return flagReturn;
	}

	//Description: test the entire set of existing rules
	//returns: 	true== all rules passed
	//       	false == a rule failed
	public boolean evaluateRules()
	{
		for (Rule rule : ruleList)
		{
			if (!rule.evaluate()) {
				return false;
			}
		}
		return true;
	}
}
