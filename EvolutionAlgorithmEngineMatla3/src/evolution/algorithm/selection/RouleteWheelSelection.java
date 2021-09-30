package evolution.algorithm.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import evolution.algorithm.DNA;

public class RouleteWheelSelection extends Selection {

	@Override
	public List<DNA> selectFathersForNewGeneration(List<DNA> currentPopulation) {
		// TODO Auto-generated method stub
	      
		  List<DNA> fathersForNewGeneration=new ArrayList<>();
		  double [] probalityOnWheel=new double[currentPopulation.size()];
		  probalityOnWheel[0]=currentPopulation.get(0).getFitness();
		  for(int i=1; i<currentPopulation.size(); i++)
		  {
			  probalityOnWheel[i]=probalityOnWheel[i-1]+currentPopulation.get(i).getFitness();
		  }
		  
		
		  for(int i=0; i<currentPopulation.size(); i++)
		  {
			 int fatherIndex= getFatherIndex(probalityOnWheel);
			 fathersForNewGeneration.add(currentPopulation.get(fatherIndex));
		  }
		  
		  
		  return fathersForNewGeneration;
	}
	private int getFatherIndex(double []probalityOnWheel)
	{
		  double sumOfFitness= probalityOnWheel[probalityOnWheel.length-1];
		  double randomProbality=  Math.random()*sumOfFitness;
		    int randomIndexBasedOnProbality= Arrays.binarySearch(probalityOnWheel,randomProbality);
		     if(randomIndexBasedOnProbality<0)
		     {
		    	 randomIndexBasedOnProbality=Math.abs(randomIndexBasedOnProbality+1);
		     }
		     return randomIndexBasedOnProbality;
		
	}
	@Override
	public String toString() {
		return  SelectionName.RouletteWheel.toString();
	}
	
	
	
	

}
