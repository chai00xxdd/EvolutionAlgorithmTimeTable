package evolution.algorithm.selection;

import java.util.List;

import evolution.algorithm.DNA;

public class SelectedFathers {

	private List<DNA> fathers;
	private List<DNA> elite;
	public SelectedFathers(List<DNA> fathers, List<DNA> elite) {
		super();
		this.fathers = fathers;
		this.elite = elite;
	}
	public List<DNA> getFathers() {
		return fathers;
	}
	public List<DNA> getElite() {
		return elite;
	}
	
	
}
