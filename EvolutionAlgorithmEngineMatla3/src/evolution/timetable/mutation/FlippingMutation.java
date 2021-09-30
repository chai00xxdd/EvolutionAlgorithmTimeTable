package evolution.timetable.mutation;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.DNA;
import evolution.algorithm.Mutation;
import evolution.timetable.TimeTableSolution;
import school.Leacture;


public class FlippingMutation extends Mutation implements Configurable {
	private int maxTupplesToChange;
	private FlippingComponent componentToChange;
	public FlippingMutation(int maxTupplesToChange,double probalityForMutation,FlippingComponent componentToChange )
	{
		super(probalityForMutation);
		this.maxTupplesToChange=maxTupplesToChange;
		this.componentToChange=componentToChange;
		
		
	}
	
	
	@Override
	public void applyMutation(DNA i_dna) {
		
		TimeTableSolution dna=(TimeTableSolution)i_dna;
		int howManyTupplesToChange=(int)(Math.random()*maxTupplesToChange)+1;
		List<Leacture> leacturesOfDna=dna.getLeactures();
		for(int i=0; i<howManyTupplesToChange&&i<leacturesOfDna.size(); i++)
		{
		
			int randomLeactureIndex=(int) (Math.random()*leacturesOfDna.size());
			Leacture randomLeactureToChange=leacturesOfDna.get(randomLeactureIndex);
			componentToChange.flip(dna, randomLeactureToChange);
		}
	}


	public int getMaxTupplesToChange() {
		return maxTupplesToChange;
	}


	public void setMaxTupplesToChange(int maxTupplesToChange) {
		this.maxTupplesToChange = maxTupplesToChange;
	}


	public FlippingComponent getComponentToChange() {
		return componentToChange;
	}


	public void setComponentToChange(FlippingComponent componentToChange) {
		this.componentToChange = componentToChange;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
	    return MutationName.Flipping.toString();
	}


	@Override
	public String getParemetersString() {
		// TODO Auto-generated method stub
		return "component = "+componentToChange.toString()+",MaxTupples = "+maxTupplesToChange;
	}


	@Override
	public Parameter getParemter() {
		// TODO Auto-generated method stub
		return new Parameter("component",componentToChange.toString());
	}
	
	
	


	

	
	
	
		
	

}
