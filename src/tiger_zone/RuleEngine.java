package tiger_zone;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages rules and validation of tile placement.
 */
public final class RuleEngine
{
	private final List<Rule> rules;
	
	public RuleEngine()
	{
		this.rules = new ArrayList<Rule>();
	}

	//Description: adds a rule to to the existing set of tests
	//returns: void
	public final void addRule(final Rule gameRule)
	{
		this.rules.add(gameRule);
	}

	public final void clearRules()
	{
		this.rules.clear();
	}

	//Description: test the entire set of existing rules
	//returns: 	true== all rules passed
	//       	false == a rule failed
	public final boolean evaluateRules()
	{
		for (Rule rule : this.rules)
		{
			if (!rule.evaluate()) {
				return false;
			}
		}
		return true;
	}
}
