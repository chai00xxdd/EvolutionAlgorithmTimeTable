package DTO;

import Utils.Configurable;
import evolution.timetable.rule.Rule;
import evolution.timetable.rule.RuleType;

public class RuleDTO {
	
	private String name;
	private RuleType type;
	private String paremeters ="";
	
	public RuleDTO(Rule rule)
	{
		name = rule.toString();
		type = rule.getType();
		if(rule instanceof Configurable)
		{
			paremeters = ((Configurable)rule).getParemetersString();
		}
		
	}

	public String getName() {
		return name;
	}

	public RuleType getType() {
		return type;
	}

	public String getParemeters() {
		return paremeters;
	}
	
	

}
