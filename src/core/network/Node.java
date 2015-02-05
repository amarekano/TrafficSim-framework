package core.network;

import core.vehicle.Vehicle;;

public class Node {

	private boolean isOccupied;
	private Vehicle vehicle;
	
	public Node()
	{
		isOccupied = false;
		vehicle = null;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
