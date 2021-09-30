package DTO;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.selection.Selection;

public class SelectionDTO {
	
	private String name = "";
	private int ellitism = 0;
	private String parameterString = "";
	private Parameter parameter = null;
	
	public SelectionDTO(Selection selection)
	{
		name = selection.toString();
		ellitism = selection.getElitism();
		if(selection instanceof Configurable)
		{
			parameterString = ((Configurable)selection).getParemetersString();
			parameter =  ((Configurable)selection).getParemter();
		}
	}

}
