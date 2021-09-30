package DTO;



import java.util.List;

import evolution.algorithm.EvolutionAlgorithmConfiguration;

public class AlgorithmConfuigrationDTO {
	
	
	private int populationSize = 0 ;
	private SelectionDTO selection;
	private CrossOverDTO crossover;
	private List<MutationDTO> mutations;
	private List<StopConditionDTO> stopConditions;
	private String userName;
	
	
	
	public AlgorithmConfuigrationDTO( EvolutionAlgorithmConfiguration confuigration)
	{
		 populationSize = confuigration.getPopulationSize();
	     selection = confuigration.getSelectionDTO();
	     crossover = confuigration.getCrossOverDTO();
	     mutations = confuigration.getMutationsDTO();
	     stopConditions = confuigration.getStopConditionsDTO();
	     
	     
		

	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	

}

