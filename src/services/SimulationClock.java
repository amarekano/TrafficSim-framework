package services;

public class SimulationClock{
	private long currentTime;
	
	public SimulationClock()
	{
		currentTime = 0;
	}
	
	public long getTime()
	{
		return currentTime;
	}
	
	public void incrementClock()
	{
		this.currentTime++;
	}
}
