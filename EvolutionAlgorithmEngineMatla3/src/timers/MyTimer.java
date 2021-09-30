package timers;

public class MyTimer {
	
	private int timePassed=0;
	Long startTime=null,endTime=null;
	
	
	public void tick()
	{
		if(endTime==null)
		{
			endTime=System.currentTimeMillis();
		}
		else
		{
			startTime=endTime;
			endTime=System.currentTimeMillis();
			long timePassedBetweenStartAndEndPeriods=endTime-startTime;
			
			timePassed+=timePassedBetweenStartAndEndPeriods;
		}
		
	}
	public boolean isTimePassed(int time)
	{
		return timePassed>time;
	}
	public int getTimePassed() {
		return timePassed;
	}
	public void setTimePassed(int timePassed) {
		this.timePassed = timePassed;
	}
	public void clear()
	{
		startTime=endTime=null;
		timePassed=0;
	}
	public void pause()
	{
		startTime=endTime=null;
	}
	
	public void resumeFromLastTime()
	{
		pause();
	}
	
	

}
