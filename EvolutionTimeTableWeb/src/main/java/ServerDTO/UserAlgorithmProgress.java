package ServerDTO;

import evolution.algorithm.AlgorithmProgress;

public class UserAlgorithmProgress {
	
	private String userName;
	private AlgorithmProgress algorithmProgress;
	
	
	public UserAlgorithmProgress(String username, AlgorithmProgress progress) {
		// TODO Auto-generated constructor stub
		this.userName = username;
		this.algorithmProgress = progress;
	}
	public String getUserName() {
		return userName;
	}
	public AlgorithmProgress getAlgorithmProgress() {
		return algorithmProgress;
	}
	
	

}
