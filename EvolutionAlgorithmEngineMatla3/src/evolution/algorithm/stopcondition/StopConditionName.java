package evolution.algorithm.stopcondition;

import javax.naming.NameNotFoundException;



public enum StopConditionName {
 Fitness,
 Generations,
 Time;
 
 public static StopConditionName getByName(String name) 
 {
 	for(StopConditionName stopConditionName : values())
 	{
 		if(stopConditionName.toString().toUpperCase().equals(name.toUpperCase()))
 		{
 			return stopConditionName;
 		}
 	}
 	
 	return null;
 	
 	
 }
}
