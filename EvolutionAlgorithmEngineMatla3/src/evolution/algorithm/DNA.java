package evolution.algorithm;


import java.util.List;


public interface DNA 
{
	
	 public DNA createRandomDNA();
	 public DNA duplicate();
	 public DNA createEmptyDNA();
	 public double calculateFitness();
	 public double getFitness();
	 public List<DnaComponent> getChromoson();
	 
	

}