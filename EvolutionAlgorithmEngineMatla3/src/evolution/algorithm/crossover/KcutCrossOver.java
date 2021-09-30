package evolution.algorithm.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import Utils.TemplateFunctions;
import evolution.algorithm.DNA;
import evolution.algorithm.DnaComponent;

public class KcutCrossOver implements CrossOver {

	private int cuttingPoints;
	public KcutCrossOver(int cuttingPoints)
	{
		this.cuttingPoints=cuttingPoints;
	}
	@Override
	public List<DNA> createOffSprings(DNA father1, DNA father2) {
		
		// TODO Auto-generated method stub
		List<DNA>childs=new ArrayList<DNA>();
		int solutionSize=Math.max(father1.getChromoson().size(),father2.getChromoson().size());
		Set<Integer> randomCuttingPoints= createRandomCutPoints(solutionSize);
		randomCuttingPoints.add(solutionSize);
		DNA child1=father1.createEmptyDNA();
		DNA child2=father1.createEmptyDNA();
		boolean evenIteration=true;
		int leftBound=0;
		List<DnaComponent> chromoson1=father1.getChromoson();
		List<DnaComponent> chromoson2=father2.getChromoson();
		for(Integer cutPoint: randomCuttingPoints)
		{
			for(; leftBound<cutPoint; leftBound++)
			{
				if(leftBound<father1.getChromoson().size())
				{
					
					if(evenIteration)
					{
						
						child1.getChromoson().add(chromoson1.get(leftBound).duplicate());
					  
					
					}
					else
					{
						child2.getChromoson().add(chromoson1.get(leftBound).duplicate());
					}
				}
				if(leftBound<father2.getChromoson().size())
				{
					if(evenIteration)
					{
						child2.getChromoson().add(chromoson2.get(leftBound).duplicate());
					}
					else
					{
						child1.getChromoson().add(chromoson2.get(leftBound).duplicate());
					}
				}
			}
			
			evenIteration=!evenIteration;
			leftBound=cutPoint;
		}
		
		childs.add(child1);
		childs.add(child2);
		return childs;
	}
	private Set<Integer> createRandomCutPoints(int solutionSize)
	{
		Set<Integer>randomCuttingPoints=new TreeSet<>();
		List<Integer>possibleCutPoints=new ArrayList<>();
		for(int possiblePoint=1; possiblePoint<solutionSize; possiblePoint++)
			possibleCutPoints.add(possiblePoint);
		for(int i=0; i<cuttingPoints&&possibleCutPoints.size()>0; i++)
		{
			Integer randomCutPoint=TemplateFunctions.getRandomElement(possibleCutPoints);
			randomCuttingPoints.add(randomCutPoint);
			possibleCutPoints.remove(randomCutPoint);
			
		}
		
		
		return randomCuttingPoints;
				
	}
	public int getCuttingPoints() {
		return cuttingPoints;
	}
	public void setCuttingPoints(int cuttingPoints) {
		this.cuttingPoints = cuttingPoints;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cuttingPoints);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KcutCrossOver other = (KcutCrossOver) obj;
		return cuttingPoints == other.cuttingPoints;
	}
	
	
	
	
}
