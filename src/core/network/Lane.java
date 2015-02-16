package core.network;
import java.util.*;

import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;

public class Lane {
	private List<Node> nodes;
	private int maxLength;
	
	public Lane()
	{
		maxLength = 1;
		nodes = new ArrayList<Node>(maxLength);
		Node node=new Node();
		nodes.add(node);
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
					//AM > Remove the car from the network
					nodes.get(currentIndex).setVehicle(null);
					nodes.get(currentIndex).setOccupied(false);
					//exitingVehicles.add(vehicle);
					/*if(i-1>=0 && vehicle instanceof Bus && vehicle.equals(nodes.get(currentIndex-1).getVehicle()) ){
						
					}
					else{
						exitingVehicles.add(vehicle);
					}*/
					if(!exitingVehicles.contains(vehicle)){
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
					
					if((currentIndex-1>=0)&&vehicle instanceof Bus && vehicle.equals(nodes.get(currentIndex-1).getVehicle())){
						nodes.get(currentIndex).setOccupied(false);
						nodes.get(currentIndex).setVehicle(null);
						
						nodes.get(currentIndex-1).setOccupied(false);
						nodes.get(currentIndex-1).setVehicle(null);
										
						vehicle.setVelocity(finalVelocity);
						
						nodes.get(finalIndex).setOccupied(true);
						nodes.get(finalIndex).setVehicle(vehicle);
						nodes.get(finalIndex-1).setOccupied(true);
						nodes.get(finalIndex-1).setVehicle(vehicle);
						followingVehicleIndex = finalIndex-1;
						i--;

					}
					else if(currentIndex==0 && finalIndex-1>=0 && vehicle instanceof Bus){
						nodes.get(currentIndex).setOccupied(false);
						nodes.get(currentIndex).setVehicle(null);
										
						vehicle.setVelocity(finalVelocity);
						
						nodes.get(finalIndex).setOccupied(true);
						nodes.get(finalIndex).setVehicle(vehicle);
						nodes.get(finalIndex-1).setOccupied(true);
						nodes.get(finalIndex-1).setVehicle(vehicle);
						followingVehicleIndex = finalIndex-1;
					}
					else{
					
					
					nodes.get(currentIndex).setOccupied(false);
					nodes.get(currentIndex).setVehicle(null);
									
					vehicle.setVelocity(finalVelocity);
					
					nodes.get(finalIndex).setOccupied(true);
					nodes.get(finalIndex).setVehicle(vehicle);
					followingVehicleIndex = finalIndex;
					}
					
		
				}
			}
		}
		
		return exitingVehicles;
	}
	
	public String toString(){
		String state="";
		for(int i=0;i<nodes.size();i++){
			if(nodes.get(i).isOccupied()){
				if (nodes.get(i).getVehicle() instanceof Car){
					state=state.concat("1");
				}
				else if (nodes.get(i).getVehicle() instanceof Bus){
					state=state.concat("2");
				}
				
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
}
