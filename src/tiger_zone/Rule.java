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

