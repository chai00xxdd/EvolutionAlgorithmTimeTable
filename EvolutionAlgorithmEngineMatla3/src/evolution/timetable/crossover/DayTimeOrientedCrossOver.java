package evolution.timetable.crossover;



import java.util.Collections;
import java.util.List;

import evolution.algorithm.DNA;

import evolution.algorithm.crossover.KcutCrossOver;
import evolution.timetable.TimeTableSolution;
import school.Leacture;

public class DayTimeOrientedCrossOver extends KcutCrossOver {

	public DayTimeOrientedCrossOver(int cuttingPoints) {
		super(cuttingPoints);
		// TODO Auto-generated constructor stub
	}
	@Override
	public List<DNA> createOffSprings(DNA father1DNA, DNA father2DNA) {
		// TODO Auto-generated method stub
		TimeTableSolution father1=(TimeTableSolution)father1DNA;
		TimeTableSolution father2=(TimeTableSolution)father2DNA;
		sortDnaByDayTime(father1,father2);
		return super.createOffSprings(father1DNA, father2DNA);
		
		
	}
	private void sortDnaByDayTime(TimeTableSolution...dnas)
	{
		for(TimeTableSolution dna:dnas)
		{
			List<Leacture> leactures= dna.getLeactures();
			Collections.sort(leactures);
			
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
       
		return CrossOverName.DayTimeOriented.toString();
	}

	
}
