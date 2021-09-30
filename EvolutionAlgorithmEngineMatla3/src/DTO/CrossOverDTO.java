package DTO;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.crossover.CrossOver;
import evolution.algorithm.crossover.KcutCrossOver;

public class CrossOverDTO {

	private String name;
	private int cuttingPoints = 0;
	private Parameter parameter;
	private String paremterString ="" ;
	
	public CrossOverDTO(CrossOver crossover)
	{
		this.name = crossover.toString();
		if(crossover instanceof KcutCrossOver)
		{
			this.cuttingPoints = ((KcutCrossOver) crossover).getCuttingPoints();
		}
		
		if(crossover instanceof  Configurable)
		{
			parameter =((Configurable)crossover).getParemter();
			paremterString =((Configurable)crossover).getParemetersString();
		}
		
		
	}
}
