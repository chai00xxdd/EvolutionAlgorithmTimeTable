package evolution.algorithm.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import evolution.algorithm.DNA;

public abstract class Selection {
	
   private int elitism=0;
   public  SelectedFathers selectFathersOfNextGeneration(List<DNA> currentPopulation)
   {
	  
	  
	  List<DNA>eliteFathers=getFathersByElitism(currentPopulation);
	//  System.out.println(eliteFathers.size());
	  int populationWithOutElites= currentPopulation.size()-eliteFathers.size();
	  List<DNA> currentPopulationWithOutElites= currentPopulation.subList(0, populationWithOutElites);
	  List<DNA>FathersBySelection = new ArrayList<>();
	  if(populationWithOutElites > 0)  
	  {
		  FathersBySelection=  selectFathersForNewGeneration(currentPopulationWithOutElites);
	  }
	 
	  
	  return new SelectedFathers(FathersBySelection, eliteFathers);
	   
	 
	  
	  
   }
   protected abstract List<DNA> selectFathersForNewGeneration(List<DNA>currentPopulation);
public int getElitism() {
	return elitism;
}
public void setElitism(int elitism) {
	this.elitism = elitism;
}
private List<DNA> getFathersByElitism(List<DNA> currentPopulation)
{
	List<DNA> ElitesFather=new ArrayList<>();
	int populationSize=currentPopulation.size();
	for(int i=0; i<elitism; i++)
	{
		int fatherIndex= populationSize-1-i;
		if(fatherIndex<0)
		{
			break;
		}
		ElitesFather.add(currentPopulation.get(fatherIndex));
		
	}
	return  ElitesFather;
}
@Override
public int hashCode() {
	return Objects.hash(elitism);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Selection other = (Selection) obj;
	return elitism == other.elitism;
}


   
}
