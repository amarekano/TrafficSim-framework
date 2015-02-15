package core.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import core.endpoints.Destination;
import core.endpoints.EndPoint;
import core.endpoints.EndPointException;
import core.endpoints.JunctionEntry;
import core.endpoints.JunctionExit;
import core.network.Lane.LANE;
import core.network.interfaces.Interface;
import core.network.interfaces.InterfaceException;
import core.network.junction.InvalidRouteException;
import core.network.junction.Junction;
import core.network.junction.Junction.JUNCTION;
import core.network.junction.JunctionException;
import core.vehicle.Vehicle;

public class Road implements Observer{
	private List<Lane> lanes;
	private int number_of_lanes;
	private EndPoint source;
	private EndPoint sink;
	private Junction sourceJunction;
	private Junction sinkJunction;
	private JUNCTION face;

	//AM > Create lane(s) and set their length
	public Road(int number_of_lanes, int lane_length)
	{
		//AM > There has to be atleast one lane
		this.number_of_lanes = number_of_lanes < 1 ? 1 : number_of_lanes;

		lanes = new ArrayList<Lane>();
		for(int i = 0; i < this.number_of_lanes; i++)
		{
			Lane lane = new Lane(lane_length);
			lane.addObserver(this);
			lanes.add(lane);
			
		}	
		
		//AM > Road isn't connected to any junctions
		sourceJunction = null;
		sinkJunction = null;
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

	public void setSource(Destination source) {
		this.source = source;
	}
	
	public void setSource(Junction junction, JUNCTION face) throws InterfaceException
	{
		//AM > Store junction information
		sourceJunction = junction;
		
		//AM > Set source to JunctionExit
		JunctionExit juncExit = sourceJunction.getJunctionExit(face);
		juncExit.setLanes(lanes);
	}

	public EndPoint getSink() {
		return sink;
	}

	public void setSink(Destination sink) {
		this.sink = sink;
	}
	
	public void setSink(Junction junction, JUNCTION face) throws InterfaceException
	{
		//AM > Store junction information
		sinkJunction = junction;
		
		//AM > Store interface information
		this.face = face;
		
		//AM > Set sink to JunctionEntry
		JunctionEntry juncEntry = sinkJunction.getJunctionEntry(this.face);
		juncEntry.setLanes(lanes);
		sink = juncEntry;
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
	public void moveTraffic() throws EndPointException{

		//AM > If source is a Destination
		if(source instanceof Destination)
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

		//AM > If sink is a destination, then collect exiting vehicles and add them to the destination
		if(sink instanceof Destination)
		{
			List<Vehicle> exitingVehicles = new ArrayList<Vehicle>();
			for(Lane l : lanes){
				exitingVehicles.addAll(l.moveVehicles());
			}
			Destination dest = (Destination) sink;
			for(Vehicle v : exitingVehicles)
			{
				dest.addVehicle(v);
			}
		}
		else
		{
			for(Lane l : lanes)
				l.moveVehicles();
		}
	}
	

	@Override
	public void update(Observable lane, Object vehicle)
	{
		Vehicle v = (Vehicle) vehicle;
		Lane l = (Lane) lane;
		
		if(sink instanceof Destination)
		{
			l.setState(LANE.MOVE);
		}
		else if(sink instanceof JunctionEntry)
		{
			try
			{
				//AM > Get the vehicles destination
				Destination d = v.getDestination();
				//AM > Get the destination interface
				Interface exitInterface = sinkJunction.getExitInterface(d);
				//AM > If signal to interface is green
				if(sinkJunction.isExitGreen(sinkJunction.getInterface(face), exitInterface))
				{
					//AM > Get lanes to junction exit
					List<Lane> exitLanes = exitInterface.getExit().getLanes();

					//AM > Perform lane transfer
					l.setTransferLanes(exitLanes);
					l.setState(LANE.TRANSFER);
				}
			}
			catch(InvalidRouteException e)
			{
				e.printStackTrace();
			} catch (InterfaceException e) {
				e.printStackTrace();
			} catch (JunctionException e) {
				e.printStackTrace();
			}
		}
		else
		{
			l.setState(LANE.MOVE);
		}
	}
}
