package evolution.timetable.rule;

import java.util.List;

import evolution.timetable.TimeTableSolution;

public class RulesCalculator {
	
	int hardRulesCounter=0;
	int softRulesCounter=0;
	double hardRulesAverage=0;
	double softRulesAverage=0;
	double hardRulesWeightPercentFromTotalGrade;
	double grade=0;
	double hardRuleWeightPercent;
	double softRuleWeightPercent;
	
	public double calculate(TimeTableSolution solution)
	{
		grade=0;
		 final int OPTIMAL_SOLUTION=1,OPTIMAL_RULE_GRADE=1;
		 List<Rule>rules=solution.getRules();
		  hardRulesCounter=(int)rules.stream().filter(rule -> rule.getType()==RuleType.HARD).count();
		  softRulesCounter=(int)rules.stream().filter(rule -> rule.getType()==RuleType.SOFT).count();
		 if(rules.size()==0)//no rules ----> 100 esay 
		 {
			 grade=OPTIMAL_SOLUTION;

		 }
		 else
		 {
		  	
			 hardRulesAverage= getRulesAvg(rules,solution,RuleType.HARD);
			 softRulesAverage =getRulesAvg(rules,solution,RuleType.SOFT);
		  hardRulesAverage = hardRulesCounter !=0?hardRulesAverage:OPTIMAL_RULE_GRADE;
		  softRulesAverage = softRulesCounter !=0?softRulesAverage:OPTIMAL_RULE_GRADE;
	
		 
		  for(Rule rule:rules)
		  {
			  if(rule.getType()==RuleType.HARD)
			  {
				  hardRuleWeightPercent =rule.getWeight();
				  softRuleWeightPercent = 100- hardRuleWeightPercent;
				  break;
			  }
			  else
			  {
				  softRuleWeightPercent=rule.getWeight();
				  hardRuleWeightPercent = 100-softRuleWeightPercent;
				  break;
			  }
		  }
		    hardRuleWeightPercent/=100;
		     softRuleWeightPercent/=100;
		     
			grade = hardRulesAverage*(hardRuleWeightPercent) + softRulesAverage*(softRuleWeightPercent);                                                               
		 }
		 return grade;
		
	}
	
	private double getRulesAvg(List<Rule>rules,TimeTableSolution solution,RuleType ruleType)
	{
		try
		{
		return rules.stream().filter(rule -> rule.getType()==ruleType)
        .mapToDouble(rule -> rule.applyOnDNA(solution).getScore())
        .average().getAsDouble();
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public int getHardRulesCounter() {
		return hardRulesCounter;
	}

	public int getSoftRulesCounter() {
		return softRulesCounter;
	}

	public double getHardRulesAverage() {
		return hardRulesAverage;
	}

	public double getSoftRulesAverage() {
		return softRulesAverage;
	}

	public double getHardRulesWeightPercentFromTotalGrade() {
		return hardRulesWeightPercentFromTotalGrade;
	}

	public double getGrade() {
		return grade;
	}

	public double getHardRuleWeightPercent() {
		return hardRuleWeightPercent;
	}

	public double getSoftRuleWeightPercent() {
		return softRuleWeightPercent;
	}
	
	
	
	
	

}
