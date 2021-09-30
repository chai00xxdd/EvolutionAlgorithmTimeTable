package evolution.algorithm.stopcondition;

public interface StopCondition {
	
	public void setEnabled(boolean enabled);
	
	public boolean isEnabled();
	
	public String getValue();
	
	public double getProgress();
	
	public void clearProgress();

}
