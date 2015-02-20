package service.clock;

import java.util.Observable;

public class SimulationClock extends Observable{
	private long currentTime;
	public static SimulationClock clock;
	private long interval;
	
	private SimulationClock()
	{
		this.currentTime = 0;
		this.interval=1000;
	}
	
	public static SimulationClock getInstance()
	{
		if(clock == null){
			clock = new SimulationClock();
		}
		
		return clock;
	}
	
	public long getTime()
	{
		return currentTime;
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
