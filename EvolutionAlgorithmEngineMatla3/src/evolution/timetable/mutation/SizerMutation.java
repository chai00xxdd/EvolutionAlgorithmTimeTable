package evolution.timetable.mutation;

import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.DNA;
import evolution.algorithm.Mutation;
import evolution.timetable.TimeTableSolution;
import school.Leacture;

public class SizerMutation extends Mutation implements Configurable {

	private int totalTupplesToSizer=0;
	public SizerMutation(int totalTupplesToSizer,double probalityForMutation) {
		super(probalityForMutation);
		// TODO Auto-generated constructor stub
		this.totalTupplesToSizer=totalTupplesToSizer;
	}

	@Override
	public void applyMutation(DNA i_dna) {
		// TODO Auto-generated method stub
		TimeTableSolution dna=(TimeTableSolution)i_dna;
		int randomTupplesToSizer= (int) (Math.random()*Math.abs(totalTupplesToSizer));
		List<Leacture> chromoson=dna.getLeactures();
		boolean addTupples= totalTupplesToSizer>0;
		
		if(addTupples)
		{
			int i=0;
			while(i<dna.getDays()*dna.getHours()&&randomTupplesToSizer>0)
			{
				chromoson.add(dna.createNotNullRandomLeacture());
				randomTupplesToSizer--;
				i++;
			}
		}
		else
		{
			while(chromoson.size()>dna.getDays()&&randomTupplesToSizer>0)
			{
			   int randomLeactureIndexToRemove=(int)(Math.random()*chromoson.size());
			   chromoson.remove(randomLeactureIndexToRemove);
				randomTupplesToSizer--;
			}
			
		}
		
		
		
	}

	public int getTotalTupplesToSizer() {
		return totalTupplesToSizer;
	}

	public void setTotalTupplesToSizer(int totalTupplesToSizer) {
		this.totalTupplesToSizer = totalTupplesToSizer;
	}

	@Override
	public String toString() {
		return MutationName.Sizer.toString();
	}

	@Override
	public String getParemetersString() {
		// TODO Auto-generated method stub
		return "TotalTupples = "+totalTupplesToSizer+"";
	}

	@Override
	public Parameter getParemter() {
		// TODO Auto-generated method stub
		return new Parameter("TotalTupples",totalTupplesToSizer);
	}
	
	
	

	
	

}
