package evolution.timetable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import evolution.timetable.rule.Rule;
import evolution.timetable.rule.RuleType;
import school.Course;
import school.School;

public class TimeTableProblem {
 private School school;
 private int days;
 private int hours;
 List<Rule> rules=new ArrayList<>();
 private double hardRuleWeight=0; 
private int id = 0;
private static int idGenerator = 1;
private static final Object idGenratorLock = new Object();
 

public TimeTableProblem(School school, int days, int hours,List<Rule> rules,int hardRuleWeight) {
	super();
	this.school = school;
	this.days = days;
	this.hours = hours;
	this.rules = rules;
	this.hardRuleWeight=hardRuleWeight;
	
	synchronized (idGenratorLock) {
	id = idGenerator++;
	}
	

}
public School getSchool() {
	return school;
}

public School getSchoolShallowClone()
{
	return new School(school);
}
public void setSchool(School school) {
	this.school = school;
}
public int getDays() {
	return days;
}
public void setDays(int days) {
	this.days = days;
}
public int getHours() {
	return hours;
}
public void setHours(int hours) {
	this.hours = hours;
}

public final List<Rule> getRules() {
	return rules;
}
public final void setRules(List<Rule> rules) {
	this.rules = rules;
}
public TimeTableSolution createTimeTableDNA()
{
	return new TimeTableSolution(school,rules,days,hours);
}
public double getHardRuleWeight() {
	return hardRuleWeight;
}
public void setHardRuleWeight(double hardRuleWeight) {
	this.hardRuleWeight = hardRuleWeight;
	for(Rule rule:rules)
	{
		double weightOfRule=rule.getType()==RuleType.HARD?hardRuleWeight:100-hardRuleWeight;
		rule.setWeight(weightOfRule);
	}
}

public Collection<TimeProblemAttribute> getAttributes()
{
	Collection<TimeProblemAttribute> attributes = new ArrayList<>();
	attributes.add(new TimeProblemAttribute("days", days+""));
	attributes.add(new TimeProblemAttribute("hours",hours+""));
	attributes.add(new TimeProblemAttribute("hard-rules-weight",hardRuleWeight+""));
	attributes.add(new TimeProblemAttribute("soft-rules-weight",(100-hardRuleWeight)+""));
	
	return attributes;
}
@Override
public int hashCode() {
	return Objects.hash(id);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	TimeTableProblem other = (TimeTableProblem) obj;
	return id == other.id;
}
public int getId() {
	return id;
}
public int getHardRulesCount() {
	// TODO Auto-generated method stub
	return (int)rules.stream().filter(rule -> rule.getType() == RuleType.HARD).count();
}

public int getSoftRulesCount() {
	// TODO Auto-generated method stub
	return (int)rules.stream().filter(rule -> rule.getType() == RuleType.SOFT).count();
}




}
