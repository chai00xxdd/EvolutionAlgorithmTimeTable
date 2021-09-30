package evolution.algorithm.crossover;

import java.util.List;

import evolution.algorithm.DNA;

public interface CrossOver {
	

	public abstract List<DNA> createOffSprings (DNA father1,DNA father2);

}
