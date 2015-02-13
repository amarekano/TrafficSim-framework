package core.vehicle;

import core.endpoints.Destination;

public abstract class Vehicle {

	public abstract int getAcceleration();
	
	public abstract void setAcceleration(int acceleration);
	
	public abstract int getVelocity();
	
	public abstract void setVelocity(int velocity);
	
	public abstract int getMax_velocity();
	
	public abstract void setMax_velocity(int max_velocity);
	
	public abstract double getDecelaration_probability();
	
	public abstract void setDecelaration_probability(double probability);
	
	public abstract Destination getDestination();
	
	public abstract void setDestination(Destination d);
}
