package core.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.endpoints.Destination;
import core.endpoints.EndPoint;
import core.endpoints.InvalidEndPointException;
import core.endpoints.InterfaceEntry;
import core.endpoints.InterfaceExit;
import core.vehicle.Vehicle;

public class Road {
	private List<Lane> lanes;
	private int number_of_lanes;
	private EndPoint source;
	private EndPoint sink;

	//AM > Create lane(s) and set their length
	public Road(int number_of_lanes, int lane_length)
	{
		//AM > There has to be atleast one lane
		this.number_of_lanes = number_of_lanes < 1 ? 1 : number_of_lanes;

		lanes = new ArrayList<Lane>();
		for(int i = 0; i < this.number_of_lanes; i++)
		{
			Lane lane = new Lane(lane_length);
			lanes.add(lane);
		}	
	}

	public List<Lane> getLanes() {
		return lanes;
	}

	public void setLanes(List<Lane> lanes) {
		this.lanes = lanes;
	}

	public EndPoint getSource() {
		return source;
	}

	public void setSource(EndPoint source) {
		this.source = source;
	}

	public EndPoint getSink() {
		return sink;
	}

	public void setSink(EndPoint sink) {
		this.sink = sink;
	}

	/*
	 * AM > Randomly add car to a lane
	 * if the lane is occupied add car to the next lane
	 * if all lanes are full then return false
	 * on successful insertion return true;
	 */
	public boolean addVehicle(Vehicle v)
	{
		int randomLane = new Random().nextInt((number_of_lanes - 1) + 1) + 1;
		Lane chosenLane = lanes.get(randomLane-1);

		if(chosenLane.addVehicle(v))
		{
			return true;
		}
		else
		{
			//AM > Attempt to add a car to another lane.
			for(Lane l: lanes)
			{
				if(!l.equals(chosenLane))
				{
					if(l.addVehicle(v))
						return true;
				}
			}
			// AM > We have exhausted all lanes return false;
			return false;
		}
	}


	public boolean addVehicle(Vehicle v, int laneNumber)
	{
		/*
		 * NC >> Add car to a chosen lane
		 */
		if(laneNumber<1 || laneNumber>lanes.size()){
			return false;
		}
		Lane chosenLane = lanes.get(laneNumber-1);

		if(chosenLane.addVehicle(v))
		{
			return true;
		}
		return false;
	}

	public int getVehicleLaneIndex(Vehicle v)
	{
		//NC >> Returns the lane number where the car is on. If the car is not found it returns -1
		int carIndex=-1;

		for(int i=0;i<lanes.size();i++){
			carIndex=lanes.get(i).getVehicleIndex(v);
			if(carIndex!=-1){
				return i;
			}
		}

		return -1;
	}

	public int getVehicleNodeIndex(Vehicle v)
	{
		//NC >> Returns the car index where the car is on. If the car is not found it returns -1
		int carIndex=-1;

		for(int i=0;i<lanes.size();i++){
			carIndex=lanes.get(i).getVehicleIndex(v);
			if(carIndex!=-1){
				return carIndex;
			}
		}

		return carIndex;
	}

	/*
	 * AM > Pull vehicle from the source and add them to the road. Move the traffic along.
	 * 		If vehicles are leaving the network then push them into the sink  
	 */
	public void moveTraffic() throws InvalidEndPointException{
		//AM > If source is not null check if it has a vehicle to be added
		if(source != null)
		{
			//AM > If source is a Destination
			if(source.getClass() == Destination.class)
			{
				Destination origin = (Destination) source;
				while(origin.getQueueLength() > 0)
				{
					Vehicle v = origin.getVehicle();
					//AM > If adding vehicle was successful release the vehicle from the source
					if(addVehicle(v))
					{
						origin.releaseVehicle(v);
					}
					else
					{
						//AM > Road is full cannot add more vehicles
						break;
					}
				}
			}
			else if(source.getClass() == InterfaceExit.class)
			{
				//AM > We don't bother with JunctionExits, just added to force proper assignments to source
			}
			else
			{
				//AM > Most likely an invalid source assignment. throw exception.
				throw new InvalidEndPointException("Unknown Endpoint assignment");
			}
		}

		//AM > Move traffic along
		List<Vehicle> exitingVehicles = new ArrayList<Vehicle>();
		for(int i=0;i<lanes.size();i++){
		 exitingVehicles.addAll(lanes.get(i).moveVehicles());
		}
		
		if(sink != null)
		{
			//AM > If sink is a destination, then collect exiting vehicles and add them to the destination
			if(sink.getClass() == Destination.class)
			{
				Destination dest = (Destination) sink;
				for(Vehicle v : exitingVehicles)
				{
					dest.addVehicle(v);
				}
			}
			else if(sink.getClass() == InterfaceEntry.class)
			{
				//AM > Move traffic over to the connected road
			}
			else
			{
				//AM > Most likely an invalid assignment. throw exception.
				throw new InvalidEndPointException("Unknown Endpoint assignment");
			}
		}
	}
}
