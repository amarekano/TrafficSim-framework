package core.endpoints;

import java.util.ArrayList;
import java.util.List;

import core.vehicle.Vehicle;

/*
 * AM > This class represents a Destination.
 * 		Destinations are spawn points where cars originate and terminate
 */
public class Destination extends EndPoint {

	private List<Vehicle> vehicleQueue;
	
	public Destination()
	{
		vehicleQueue = new ArrayList<Vehicle>();
	}
	
	public int getQueueLength()
	{
		return vehicleQueue.size();
	}
	
	public boolean addVehicle(Vehicle v)
	{	
		if(v != null)
		{
			vehicleQueue.add(v);
			return true;
		}
		return false;
	}
	
	public Vehicle getVehicle()
	{
		return vehicleQueue.get(0);
	}
	
	public void releaseVehicle(Vehicle v)
	{
		if(v != null)
		{
			vehicleQueue.remove(v);
		}
	}

}
