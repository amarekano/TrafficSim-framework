package service;

import java.util.Observable;

public class SimulationClock extends Observable implements Runnable{
	private long currentTime;
	public final static SimulationClock clock = new SimulationClock();
	private long interval;
	
	private Thread systemClock;
	private volatile boolean suspended = false;
	private volatile boolean running = false;
	
	private SimulationClock()
	{
		this.currentTime = 0;
		//AM > Time in ms between each clock tick
		this.interval=1000;
		systemClock = new Thread(this);
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				Thread.sleep(clock.getInterval());
				synchronized(this)
				{
					if(!suspended)
					{
						clock.incrementClock();
					}
				}
			}
		}
		catch(InterruptedException e)
		{
		}
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
	
	public synchronized void pauseClock()
	{
		this.suspended = true;
	}
	
	public synchronized void resumeClock()
	{
		this.suspended = false;
	}
	
	public synchronized void startClock()
	{
		if(!running)
		{
			running = true;
			systemClock.start();
		}
	}
}
