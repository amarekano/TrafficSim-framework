package service.clock;

import java.util.Observable;

public class SimulationClock extends Observable{
	private long currentTime;
	public final static SimulationClock clock = new SimulationClock();
	private long interval;
	
	private SimulationClock()
	{
		this.currentTime = 0;
		//AM > Time in ms between each clock tick
		this.interval=1000;
	}
	
	public static SimulationClock getInstance()
	{
		return clock;
	}
	
	public long getTime()
	{
		return currentTime;
	}
	
	public void resetClock()
	{
		currentTime = 0;
	}
	
	public void incrementClock()
	{
		setChanged();
		notifyObservers();
		this.currentTime++;
	}
	
	public void setInterval(long interval){
		this.interval=interval;
	}
	
	public long getInterval()
	{
		return interval;
	}
}
