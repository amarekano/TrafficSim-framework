package core;
import java.util.*;

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
		maxLength = n;
		nodes = new ArrayList<Node>(maxLength);
		for(int i = 0; i < maxLength; i++)
		{
			Node node=new Node();
			nodes.add(node);
		}
	}
	
	public boolean addCar(Car car){
		if(nodes.get(0).isOccupied()){
			return false;
		}
		else{
			nodes.get(0).setCar(car);
			nodes.get(0).setOccupied(true);
			return true;
			
		}
	}
	
	public void moveCars()
	{
		int followingCarIndex = maxLength;
		
		for(int i = nodes.size()-1; i >= 0; i--)
		{
			if(nodes.get(i).isOccupied())
			{
				//AM > We get the car and compute its next position
				int currentIndex = i;
				Car c = nodes.get(currentIndex).getCar();
				
				int currentVelocity = c.getVelocity() + c.getAcceleration();
				
				//AM > Ensure there is no over speeding
				if(currentVelocity > c.getMax_velocity())
					currentVelocity = c.getMax_velocity();
				
				int predictedIndex = currentIndex+currentVelocity;
				
				if( predictedIndex >= maxLength && followingCarIndex == maxLength)
				{
					//AM > Remove the car from the network
					nodes.get(currentIndex).setCar(null);
					nodes.get(currentIndex).setOccupied(false);
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
					nodes.get(currentIndex).setCar(null);
									
					c.setVelocity(finalVelocity);
					
					nodes.get(finalIndex).setOccupied(true);
					nodes.get(finalIndex).setCar(c);
					
					followingCarIndex = finalIndex;
		
				}
			}
		}
	}
	
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
	
	public int getCarIndex(Car c)
	{
		//NC >> returns the index of the car in the lane. If it doesn't exists returns -1
		
		for(int i=0;i<nodes.size();i++){
			Car currentCar = nodes.get(i).getCar();
			if(currentCar != null && currentCar.equals(c)){
				return i;
			}
		}
		
		return -1;
	}
}
