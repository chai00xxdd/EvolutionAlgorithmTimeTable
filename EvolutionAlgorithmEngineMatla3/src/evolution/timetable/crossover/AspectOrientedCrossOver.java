package evolution.timetable.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.DNA;
import evolution.algorithm.crossover.KcutCrossOver;
import evolution.timetable.TimeTableSolution;
import school.Leacture;


public class AspectOrientedCrossOver extends KcutCrossOver implements Configurable {
	
	AspectOrientation aspectOrientation;
	public AspectOrientedCrossOver(int cuttingPoints,AspectOrientation aspectOrientation ) {
		super(cuttingPoints);
		this.aspectOrientation=aspectOrientation;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<DNA> createOffSprings(DNA father1DNA, DNA father2DNA) {
		// TODO Auto-generated method stub
		List<DNA> offSpringsOfAspect=new ArrayList<>();
		TimeTableSolution father1=(TimeTableSolution)father1DNA;
		TimeTableSolution father2=(TimeTableSolution)father2DNA;
	
		List<List<Leacture>>aspectedLeactures1=aspectOrientation.orientation(father1);
		List<List<Leacture>>aspectedLeactures2=aspectOrientation.orientation(father2);
		TimeTableSolution child1=father1.shallowCloneWithOutLeactures();
		TimeTableSolution child2=father1.shallowCloneWithOutLeactures();
		for(int t=0; t<aspectedLeactures1.size(); t++)
		{
			TimeTableSolution childAspect1=father1.shallowCloneWithOutLeactures();
			TimeTableSolution childAspect2=father1.shallowCloneWithOutLeactures();
			childAspect1.setLeactures(aspectedLeactures1.get(t));
			childAspect2.setLeactures(aspectedLeactures2.get(t));
		
			
			List<DNA> aspectsChildsOfAspectIndexT=super.createOffSprings(childAspect1,childAspect2);
			List<TimeTableSolution> newSolutionsOfT=convertToTimeTableSolution(aspectsChildsOfAspectIndexT);
			child1.getLeactures().addAll(newSolutionsOfT.get(0).getLeactures());
			child2.getLeactures().addAll(newSolutionsOfT.get(1).getLeactures());
			
	
		}
		
		
		
		offSpringsOfAspect.add(child1);
		offSpringsOfAspect.add(child2);
		return super.createOffSprings(father1, father2);
		
	}
	private List<TimeTableSolution> convertToTimeTableSolution(List<DNA> timeSolutions)
	{
		List<TimeTableSolution> solutions=new ArrayList<>();
		for(DNA dna:timeSolutions)
		{
			solutions.add((TimeTableSolution)dna);
		}
		return solutions;
	}

	@Override
	public String toString() {
		
		return CrossOverName.AspectOriented.toString();
	}

	public AspectOrientation getAspectOrientation() {
		return aspectOrientation;
	}

	public void setAspectOrientation(AspectOrientation aspectOrientation) {
		this.aspectOrientation = aspectOrientation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(aspectOrientation);
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
		AspectOrientedCrossOver other = (AspectOrientedCrossOver) obj;
		return aspectOrientation == other.aspectOrientation;
	}

	@Override
	public String getParemetersString() {
		// TODO Auto-generated method stub
		 return " Orientation = "+aspectOrientation.toString();
	}

	@Override
	public Parameter getParemter() {
		// TODO Auto-generated method stub
		return new Parameter("Orientation", aspectOrientation.toString());
	}
	
	
	
	
	
	
	

	
	

}
