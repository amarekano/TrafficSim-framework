package core.network;
import java.util.*;

import core.vehicle.Vehicle;

public class Lane extends Observable{
	private List<Node> nodes;
	private int maxLength;
	private LANE state;
	private List<Lane> transferLanes;
	
	public enum LANE { MOVE, WAIT, TRANSFER };
	
	public Lane()
	{
		maxLength = 1;
		nodes = new ArrayList<Node>(maxLength);
		Node node=new Node();
		nodes.add(node);
		//AM > Default lane behavior is to move vehicles along
		state = LANE.MOVE;
	}
	
	public Lane(int n)
	{
		//AM > Lane cannot have length less than 1
		maxLength = n < 1 ? 1 : n;
		nodes = new ArrayList<Node>(maxLength);
		for(int i = 0; i < maxLength; i++)
		{
			Node node=new Node();
			nodes.add(node);	
		}
		//AM > Default behavior is to move vehicles along
		state = LANE.MOVE;
	}
	
	public LANE getState() {
		return state;
	}

	public void setState(LANE state) {
		this.state = state;
	}

	public boolean addVehicle(Vehicle vehicle){
		if(nodes.get(0).isOccupied()){
			return false;
		}
		else{
			nodes.get(0).setVehicle(vehicle);
			nodes.get(0).setOccupied(true);
			return true;
			
		}
	}
	
	public List<Vehicle> moveVehicles()
	{
		int followingVehicleIndex = maxLength;
		List<Vehicle> exitingVehicles = new ArrayList<Vehicle>();
		
		for(int i = nodes.size()-1; i >= 0; i--)
		{
			if(nodes.get(i).isOccupied())
			{
				//AM > We get the car and compute its next position
				int currentIndex = i;
				Vehicle vehicle = nodes.get(currentIndex).getVehicle();
				
				int currentVelocity = vehicle.getVelocity() + vehicle.getAcceleration();
				
				//AM > Ensure there is no over speeding
				if(currentVelocity > vehicle.getMax_velocity())
					currentVelocity = vehicle.getMax_velocity();
				
				int predictedIndex = currentIndex+currentVelocity;
				
				if( predictedIndex >= maxLength && followingVehicleIndex == maxLength)
				{
					//AM > Notify observers (i.e Road) that we have an exiting vehicle
					setChanged();
					notifyObservers(vehicle);
					
					//AM > If lane state is TRANSFER
					if(state == LANE.TRANSFER)
					{
						//AM > If the transfer fails make the vehicle wait
						if(!transferVehicle(vehicle))
						{
							int finalIndex = followingVehicleIndex - 1;
							//AM > move vehicles to the end of the lane
							if(finalIndex != currentIndex)
							{
								nodes.get(finalIndex).setVehicle(vehicle);
								nodes.get(finalIndex).setOccupied(true);
								nodes.get(currentIndex).setVehicle(null);
								nodes.get(currentIndex).setOccupied(false);
							}
							followingVehicleIndex = finalIndex;
						}
						else
						{
							nodes.get(currentIndex).setVehicle(null);
							nodes.get(currentIndex).setOccupied(false);
						}
					}
					else if(state == LANE.WAIT)
					{
						int finalIndex = followingVehicleIndex - 1;
						//AM > move vehicles to the end of the lane
						if(finalIndex != currentIndex)
						{
							nodes.get(finalIndex).setVehicle(vehicle);
							nodes.get(finalIndex).setOccupied(true);
							nodes.get(currentIndex).setVehicle(null);
							nodes.get(currentIndex).setOccupied(false);
						}
						followingVehicleIndex = finalIndex;
					}
					//AM > Default action is to move cars
					else
					{
						//AM > Remove the car from the network
						nodes.get(currentIndex).setVehicle(null);
						nodes.get(currentIndex).setOccupied(false);
						exitingVehicles.add(vehicle);
					}
				}
				else
				{
					int finalIndex = currentIndex;
					int finalVelocity = 1;
					/* 
					 * AM > Iterate from current position to predicted position
					 *	to check for a clear path
					*/
					int j = 1;
					while(j <= currentVelocity)
					{
						if(!nodes.get(currentIndex + j).isOccupied())
						{
							finalIndex++;
							finalVelocity = j;
						}
						else
							break;
						j++;
					}
					
					nodes.get(currentIndex).setOccupied(false);
					nodes.get(currentIndex).setVehicle(null);
									
					vehicle.setVelocity(finalVelocity);
					
					nodes.get(finalIndex).setOccupied(true);
					nodes.get(finalIndex).setVehicle(vehicle);
					
					followingVehicleIndex = finalIndex;
		
				}
			}
		}
		
		return exitingVehicles;
	}
	
	//AM > Primitive visualization of lane state
	public String toString(){
		String state="";
		for(int i=0;i<nodes.size();i++){
			if(nodes.get(i).isOccupied()){
				state=state.concat("1");
			}
			else{
				state=state.concat("0");
			}
		}
		return state;
	}
	
	public int getVehicleIndex(Vehicle v)
	{
		//NC >> returns the index of the car in the lane. If it doesn't exists returns -1
		
		for(int i=0;i<nodes.size();i++){
			Vehicle currentVehicle = nodes.get(i).getVehicle();
			if(currentVehicle != null && currentVehicle.equals(v)){
				return i;
			}
		}
		
		return -1;
	}
	
	public List<Lane> getTransferLanes() {
		return transferLanes;
	}

	public void setTransferLanes(List<Lane> transferLanes) {
		this.transferLanes = transferLanes;
	}

	//AM > Move exiting vehicles to destination lanes
	public boolean transferVehicle(Vehicle v)
	{
		if(transferLanes == null)
		{
			return false;
		}
		else
		{
			for(Lane l : transferLanes)
			{
				if(l.addVehicle(v))
					return true;
			}
			return false;
		}
	}
}

