package core.vehicle;

import java.util.Random;
import core.endpoints.Destination;

public abstract class Vehicle 
{
	private int velocity;
	private int acceleration;
	private int max_velocity;
	private double decelaration_probability;
	private Destination destination;
	private Destination source;
	
	public abstract int getLength();
	
	protected Vehicle()
	{
		this.velocity = 1;
		this.acceleration = 0;
		this.max_velocity = 1;
		this.decelaration_probability = 0.0;
		this.destination = null;
	}
	
	protected Vehicle(int velocity, int acceleration, int max_velocity)
	{
		if(velocity < 1)
		{
			this.velocity = 1;
			this.acceleration = 0;
		}
		else
		{
			this.velocity = velocity;
			this.acceleration = acceleration;
		}
		this.max_velocity = max_velocity < this.velocity ? this.velocity : max_velocity;
		this.decelaration_probability = 0.0;
		this.destination = null;
	}
	
	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) throws VehicleException {
		if(destination == source)
			throw new VehicleException("Destination cannot be the same as the source");
		
		this.destination = destination;
	}
	
	public double getDecelaration_probability() {
		return decelaration_probability;
	}

	public void setDecelaration_probability(double decelaration_probability) {
		this.decelaration_probability = decelaration_probability;
	}

	public int getMax_velocity() {
		return max_velocity;
	}

	public void setMax_velocity(int max_velocity) {
		this.max_velocity = max_velocity;
	}

	public int getAcceleration() {
		if(new Random().nextDouble() <= decelaration_probability)
		{
			acceleration = acceleration > 1 ? acceleration -1 : 0;
		}
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}
	
	public int getVelocity()
	{
		return velocity;
	}
	
	public void setVelocity(int velocity)
	{
		this.velocity = velocity;
	}

	public Destination getSource() {
		return source;
	}

	public void setSource(Destination source) throws VehicleException {
		if(source == destination)
			throw new VehicleException("Source cannot be the same as the destination");
		
		this.source = source;
	}
	
}
