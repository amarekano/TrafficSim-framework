package core.endpoints;

import java.util.ArrayList;
import java.util.List;

import core.vehicle.Vehicle;

/*
 * AM > This class represents a Destination.
 * 		Destinations are spawn points where cars originate and terminate
 */
public class Destination extends EndPoint {

	private List<Vehicle> waitingQueue;
	private List<Vehicle> consumedQueue;
	
	public Destination()
	{
		waitingQueue = new ArrayList<Vehicle>();
		consumedQueue = new ArrayList<Vehicle>();
	}
	
	public int getWaitingQueueLength()
	{
		return waitingQueue.size();
	}
	
	public int getConsumedQueueLength()
	{
		return consumedQueue.size();
	}
	
	public boolean addVehicle(Vehicle v)
	{	
		if(v != null)
		{
			waitingQueue.add(v);
			return true;
		}
		return false;
	}
	
	public void consumeVehicle(Vehicle v)
	{
		if(v != null)
		{
			consumedQueue.add(v);
		}
	}
	
	public Vehicle getWaitingVehicle()
	{
		return waitingQueue.get(0);
	}
	
	public void releaseVehicle(Vehicle v)
	{
		if(v != null)
		{
			waitingQueue.remove(v);
		}
	}

}
