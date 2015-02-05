package core.endpoints;

import core.vehicle.Vehicle;

/*
 * AM > Endpoints define connections between Roads and Junctions
 */

public abstract class EndPoint {
	public abstract int getQueueLength();
	
	public abstract Vehicle getVehicle();
	
	public abstract void releaseVehicle(Vehicle v);
	
	public abstract boolean addVehicle(Vehicle v);
}
