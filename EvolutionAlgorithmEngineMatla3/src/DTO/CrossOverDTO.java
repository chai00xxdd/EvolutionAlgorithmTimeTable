package DTO;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.crossover.CrossOver;
import evolution.algorithm.crossover.KcutCrossOver;

public class CrossOverDTO {

	private String name;
	private String cuttingPoints = "0";
	private Parameter parameter;
	private String paremterString ="" ;
	
	public CrossOverDTO(CrossOver crossover)
	{
		this.name = crossover.toString();
		if(crossover instanceof KcutCrossOver)
		{
			this.cuttingPoints = ((KcutCrossOver) crossover).getCuttingPoints()+"";
		}
		
		if(crossover instanceof  Configurable)
		{
			
			parameter =((Configurable)crossover).getParemter();
			paremterString =((Configurable)crossover).getParemetersString();
		}
		
		
	}
	
	

	public CrossOverDTO(String name, String cuttingPoints, String paremterString) {
		super();
		this.name = name;
		this.cuttingPoints = cuttingPoints;
		this.paremterString = paremterString;
	}



	public String getName() {
		return name;
	}

	public String getCuttingPoints() {
		return cuttingPoints;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public String getParemterString() {
		return paremterString;
	}
	
	
	
	
}
