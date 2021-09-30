package evolution.timetable.rule;

import java.util.HashMap;
import java.util.Map;



public enum RuleName {
	SINGULARITY,
	TEACHER_IS_HUMAN,
	KNOWLEDGEABLE,
	SATISFACTORY,
	DAYOFFTEACHER,
	SEQUENTIALITY,
	DayOffClass,
	WorkingHoursPreference
	;
	
	private static Map<String,RuleName>rulesMap = new HashMap<>();
	static
	{
		for(RuleName ruleName : values())
		{
			
			rulesMap.put(ruleName.toString().replaceAll("_","").toUpperCase(), ruleName);
		}
			
	}
	public static RuleName getRuleByName(String ruleNameString)
	{
		return rulesMap.get(ruleNameString.toUpperCase());
	}
	
}
