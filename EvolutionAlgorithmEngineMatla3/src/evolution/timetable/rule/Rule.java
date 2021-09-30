package evolution.timetable.rule;

import java.lang.reflect.Constructor;


import evolution.timetable.TimeTableSolution;

public abstract class Rule {
	
	protected double weight;
	protected RuleType type;
	protected Rule(double weight,RuleType type)
	{
		this.weight=weight;
		this.type=type;
		
	}
	protected Rule(Rule other)
	{
		this(other.weight,other.type);
	}
	public abstract RuleResult applyOnDNA(TimeTableSolution dna);
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public RuleType getType() {
		return type;
	}
	public void setType(RuleType type) {
		this.type = type;
		
	}
	
	
	
	public static Rule deepCopy(Rule rule)
	{
		try {
            Class<? extends Rule> clazz = rule.getClass();
            Constructor<? extends Rule> constructor = clazz.getDeclaredConstructor(clazz);
            constructor.setAccessible(true);
            return constructor.newInstance(rule);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
	}
	
	

}
