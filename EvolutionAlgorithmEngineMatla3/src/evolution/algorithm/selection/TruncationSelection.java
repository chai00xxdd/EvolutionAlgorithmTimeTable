package evolution.algorithm.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.DNA;

public class TruncationSelection extends Selection implements Configurable {

	public int topPrecentOfPopulationToChoose;
	public TruncationSelection(int topPrecentOfPopulationToChoose  )
	{
	this.topPrecentOfPopulationToChoose=topPrecentOfPopulationToChoose;	
	}
	@Override
	public List<DNA> selectFathersForNewGeneration(List<DNA> currentPopulation) {
		
		int howManyFittestToChoose= (currentPopulation.size()*topPrecentOfPopulationToChoose)/100;
		if(howManyFittestToChoose==0)//fix
			howManyFittestToChoose=2;
		List<DNA> selectedFathers=new ArrayList<>();
		for(int dnaIndex=1; dnaIndex<=howManyFittestToChoose; dnaIndex++)
		{
			int fatherIndex=currentPopulation.size()-dnaIndex;
			if(fatherIndex<0)
			{
				break;
			}
			selectedFathers.add(currentPopulation.get(fatherIndex));
		}
		return selectedFathers;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return SelectionName.Truncation.toString();
	}
	public int getTopPrecentOfPopulationToChoose() {
		return topPrecentOfPopulationToChoose;
	}
	public void setTopPrecentOfPopulationToChoose(int topPrecentOfPopulationToChoose) {
		this.topPrecentOfPopulationToChoose = topPrecentOfPopulationToChoose;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(topPrecentOfPopulationToChoose);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TruncationSelection other = (TruncationSelection) obj;
		return topPrecentOfPopulationToChoose == other.topPrecentOfPopulationToChoose;
	}
	@Override
	public String getParemetersString() {
		// TODO Auto-generated method stub
		 return "TopPercent = "+topPrecentOfPopulationToChoose + "";
	}
	@Override
	public Parameter getParemter() {
		// TODO Auto-generated method stub
		return new Parameter("TopPercent",topPrecentOfPopulationToChoose);
	}
	
	
	
	
	
	

}
