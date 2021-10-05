package DTO;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.algorithm.selection.Selection;

public class SelectionDTO {
	
	private String name = "";
	private String ellitism = "0";
	private String parameterString = "";
	private Parameter parameter = null;
	
	public SelectionDTO(Selection selection)
	{
		name = selection.toString();
		ellitism = selection.getElitism()+"";
		if(selection instanceof Configurable)
		{
			parameterString = ((Configurable)selection).getParemetersString();
			parameter =  ((Configurable)selection).getParemter();
		}
	}
	
	public SelectionDTO(String name,String ellitism,String paramter)
	{
		this.parameterString = paramter;
		this.name = name;
		this.ellitism =ellitism;
	}

	public String getName() {
		return name;
	}

	public String getEllitism() {
		return ellitism;
	}

	public String getParameterString() {
		return parameterString;
	}

	public Parameter getParameter() {
		return parameter;
	}
	
	

}
