package DTO;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.Mutation;

public class MutationDTO {
	
	private String name;
	private double probality;
	private Parameter parameter;
	private String parameterString = "";
	private int id;
	
	public MutationDTO(Mutation mutation)
	{
		name = mutation.toString();
		probality = mutation.getProbalityForMutation();
		if(mutation instanceof Configurable)
		{
			parameter = ((Configurable)mutation).getParemter();
			parameterString = ((Configurable)mutation).getParemetersString();
		}
		
		id = mutation.getId();
	}

	public String getName() {
		return name;
	}

	public double getProbality() {
		return probality;
	}

	public Parameter getParemter() {
		return parameter;
	}

	public int getId() {
		return id;
	}
	
	

}
