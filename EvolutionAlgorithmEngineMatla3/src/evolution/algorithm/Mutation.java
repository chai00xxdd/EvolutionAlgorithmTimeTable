package evolution.algorithm;

public abstract class Mutation {

	private double probalityForMutation;
	private boolean enabled = true;
	private int id;
	private static int idGenerator = 1;
	private static Object IdGeneratorLock = new Object();
	public Mutation(double probalityForMutation) {
		this.probalityForMutation=probalityForMutation;
		
		synchronized (IdGeneratorLock) {
			id = idGenerator++;
		}
		
	}
	
	
	public int getId() {
		return id;
	}


	public  void mutate(DNA dna)
	{
		if(dna.getChromoson().size()==0)
			return;
		double proablity= Math.random();
		if(proablity<probalityForMutation && isEnabled())
		{
			
			applyMutation(dna);
		}
		
	}
	public abstract void applyMutation(DNA dna);
	public double getProbalityForMutation() {
		return probalityForMutation;
	}
	public void setProbalityForMutation(double probalityForMutation) {
		this.probalityForMutation = probalityForMutation;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	
	
	
	
	
}
