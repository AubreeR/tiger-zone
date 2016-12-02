package tiger_zone;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages rules and validation of tile placement.
 */
public class RuleEngine
{
	private final List<Rule> ruleList;
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
		this.ruleList.clear();;
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
