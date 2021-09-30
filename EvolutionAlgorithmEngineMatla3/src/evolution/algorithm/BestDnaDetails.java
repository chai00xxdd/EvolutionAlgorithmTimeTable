package evolution.algorithm;

public class BestDnaDetails {
  private DNA dna;
  private int generation;
  private int timeCreated;
  
  public BestDnaDetails(DNA dna,int generation, int timeCreated)
  {
	  this.dna = dna;
	  this.generation= generation;
	  this.timeCreated = timeCreated;
  }

public DNA getDna() {
	return dna;
}

public int getGeneration() {
	return generation;
}

public int getTimeCreated() {
	return timeCreated;
}
 
public double getFitness()
{
	return dna.getFitness();
}
  
}
