package core.endpoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import service.SimulationClock;
import core.vehicle.Vehicle;
import core.vehicle.VehicleException;

/*
 * AM > This class represents a Destination.
 * 		Destinations are spawn points where cars originate and terminate
 */
public class Destination extends EndPoint {

	private List<Vehicle> waitingQueue;
	private List<Vehicle> consumedQueue;
	private SimulationClock clock;
	private String label;
	
	//AM > Create a profile for generated vehicle velocity
	private int minVehicleVelocity;
	public int getMinVehicleVelocity() {
		return minVehicleVelocity;
	}

	public void setMinVehicleVelocity(int minVehicleVelocity) {
		if(minVehicleVelocity >= 1 && minVehicleVelocity <= this.maxVehicleVelocity)
			this.minVehicleVelocity = minVehicleVelocity;
	}

	public int getMaxVehicleVelocity() {
		return maxVehicleVelocity;
	}

	public void setMaxVehicleVelocity(int maxVehicleVelocity) {
		if(maxVehicleVelocity >= 1 && maxVehicleVelocity >= this.minVehicleVelocity)
			this.maxVehicleVelocity = maxVehicleVelocity;
	}

	public double getVelocityProbability() {
		return velocityProbability;
	}

	public void setVelocityProbability(double velocityProbability) {
		if(velocityProbability >=0.0 && velocityProbability <= 1.0)
			this.velocityProbability = velocityProbability;
	}

	public int getMinVehicleAcceleration() {
		return minVehicleAcceleration;
	}

	public void setMinVehicleAcceleration(int minVehicleAcceleration) {
		if(minVehicleAcceleration >= 0 && minVehicleAcceleration <= this.maxVehicleAcceleration)
		this.minVehicleAcceleration = minVehicleAcceleration;
	}

	public int getMaxVehicleAcceleration() {
		return maxVehicleAcceleration;
	}

	public void setMaxVehicleAcceleration(int maxVehicleAcceleration) {
		if(maxVehicleAcceleration >= 0 && maxVehicleAcceleration >= this.minVehicleAcceleration)
			this.maxVehicleAcceleration = maxVehicleAcceleration;
	}

	public double getAccelerationProbability() {
		return accelerationProbability;
	}

	public void setAccelerationProbability(double accelerationProbability) {
		this.accelerationProbability = accelerationProbability;
	}

	private int maxVehicleVelocity;
	private double velocityProbability;
	
	//AM > Create a profile for generated vehicle acceleration
	private int minVehicleAcceleration;
	private int maxVehicleAcceleration;
	private double accelerationProbability;
	
	public Destination()
	{
		waitingQueue = new ArrayList<Vehicle>();
		consumedQueue = new ArrayList<Vehicle>();
	}
	
	public Destination(String label)
	{
		waitingQueue = new ArrayList<Vehicle>();
		consumedQueue = new ArrayList<Vehicle>();
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getWaitingQueueLength()
	{
		return waitingQueue.size();
	}
	
	public int getConsumedQueueLength()
	{
		return consumedQueue.size();
	}
	

	public SimulationClock getClock() {
		return clock;
	}

	public void setClock(SimulationClock clock) {
		this.clock = clock;
	}


	public boolean addVehicle(Vehicle v) throws VehicleException
	{	
		
		if(v != null)
		{
			v.setSource(this);
			
			Random r = new Random();
			//AM > Set a random velocity
			if(r.nextDouble() < velocityProbability)
			{
				int velocity = r.nextInt((maxVehicleVelocity - minVehicleVelocity) + 1) + minVehicleVelocity;
				v.setVelocity(velocity);
			}
			
			//AM > Set a random acceleration
			if(r.nextDouble() < accelerationProbability)
			{
				int acceleration  = r.nextInt((maxVehicleAcceleration - minVehicleAcceleration) + 1) + minVehicleAcceleration;
				v.setAcceleration(acceleration);
			}
			
			if(!waitingQueue.contains(v))
				waitingQueue.add(v);
			return true;
		}
		return false;
	}
	
	public void setVehicleVelocityProfile(int max, int min, double probability)
	{
		this.maxVehicleVelocity = max > 1 ? max : 1;
		this.minVehicleVelocity = min > 1 && min < maxVehicleVelocity ? min : 1;
		if(probability >= 0.0 && probability <= 1.0)
			this.velocityProbability = probability;
	}
	
	public void setVehicleAccelerationProfile(int max, int min, double probability)
	{
		this.maxVehicleAcceleration = max > 0 ? max : 0;
		this.minVehicleAcceleration = min >= 0 && min < maxVehicleAcceleration ? min : 0;
		if(probability >= 0.0 && probability <= 1.0)
			this.accelerationProbability = probability;
	}
	
	public void consumeVehicle(Vehicle v)
	{
		if(v != null)
		{
			if(clock != null)
				v.setEndTime(clock.getTime());
			if(!consumedQueue.contains(v))
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
			if(clock != null)
				v.setStartTime(clock.getTime());
			waitingQueue.remove(v);
		}
	}
	
	public void clearConsumedQueue()
	{
		consumedQueue.clear();
	}
	
	public List<Vehicle> getConsumedVehicles(){
		return consumedQueue;
	}

	@Override
	public String toString()
	{
		return label;
	}
}
