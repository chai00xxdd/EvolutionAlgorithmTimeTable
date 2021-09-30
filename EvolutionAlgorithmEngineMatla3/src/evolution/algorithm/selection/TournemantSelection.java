package evolution.algorithm.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import Utils.TemplateFunctions;
import evolution.algorithm.DNA;

public class TournemantSelection extends Selection implements Configurable  {

	private double predefinedTournamenEqualizer;
	
	
	public TournemantSelection(double predefinedTournamenEqualizer) {
		super();
		setPredefinedTournamenEqualizer(predefinedTournamenEqualizer);
	}


	public double getPredefinedTournamenEqualizer() {
		return predefinedTournamenEqualizer;
	}


	public void setPredefinedTournamenEqualizer(double predefinedTournamenEqualizer) {
		
		this.predefinedTournamenEqualizer = predefinedTournamenEqualizer;
	}


	@Override
	public List<DNA> selectFathersForNewGeneration(List<DNA> currentPopulation) {
		// TODO Auto-generated method stub
		List<DNA>fathers=new ArrayList<>();
		for(int i=0; i<currentPopulation.size(); i++)
		{
		  
		 fathers.add(getFatherByTourenmant(currentPopulation));
		   
		}
		return fathers;
	}
	
	private DNA getFatherByTourenmant(List<DNA> currentPopulation)
	{
		 DNA dna1= TemplateFunctions.getRandomElement(currentPopulation);
		 DNA dna2= TemplateFunctions.getRandomElement(currentPopulation);
		 DNA dnaWithMinimumFitness= dna1.getFitness()<dna2.getFitness()?dna1:dna2;
		 DNA dnaWithMaximumFitness= dna1.getFitness()>dna2.getFitness()?dna1:dna2;
		  double probality= Math.random();
		  if(probality<predefinedTournamenEqualizer)
		  {
			 return  dnaWithMinimumFitness;
		  }
		 return dnaWithMaximumFitness;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(predefinedTournamenEqualizer);
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
		TournemantSelection other = (TournemantSelection) obj;
		return Double.doubleToLongBits(predefinedTournamenEqualizer) == Double
				.doubleToLongBits(other.predefinedTournamenEqualizer);
	}


	@Override
	public String getParemetersString() {
		// TODO Auto-generated method stub
		return "PTE = "+predefinedTournamenEqualizer + "";
	}


	@Override
	public Parameter getParemter() {
		// TODO Auto-generated method stub
		return new Parameter("pte",predefinedTournamenEqualizer);
	}


	@Override
	public String toString() {
		return SelectionName.Tournemant.toString();
	}
	
	
	
	

	
}
