package evolution.timetable.rule;

import java.util.List;

public class RuleResult {
 private double score,weight;
 private String name ="";
 private RuleType type;
 private double effectOnFitness;
 private double maxEffectOnFitness;
 private List<RuleName>allErrors=null;
 
 //score value between 0 and 1
public RuleResult(double score,double weight) {
	super();
	this.score = score;
	this.weight=weight;
	
}


public RuleType getType() {
	return type;
}


public void setType(RuleType type) {
	this.type = type;
}


public double getScore() {
	return score;
}
public void setScore(double score) {
	this.score = score;
}

public List<RuleName> getAllErrors() {
	return allErrors;
}
public double getWeightedScore()
{
	return getScore()*getWeight();
}
public double getWeight() {
	return weight;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public double getEffectOnFitness() {
	return effectOnFitness;
}
public void setEffectOnFitness(double effectOnFitness) {
	this.effectOnFitness = effectOnFitness;
}
public double getMaxEffectOnFitness() {
	return maxEffectOnFitness;
}
public void setMaxEffectOnFitness(double maxEffectOnFitness) {
	this.maxEffectOnFitness = maxEffectOnFitness;
}



 
}
