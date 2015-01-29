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
		
		
		for(int i = nodes.size()-1; i >= 0; i--)
		{
			if(nodes.get(i).isOccupied())
			{
				//We get the car and move it to a new location in the duplicate list
				int currentIndex = i;
				Car c = nodes.get(currentIndex).getCar();
				int currentVelocity = c.getVelocity();
				if( currentIndex + currentVelocity >= maxLength)
				{
					//Remove the car from the network
					nodes.get(currentIndex).setCar(null);
					nodes.get(currentIndex).setOccupied(false);
				}
				else
				{
					int predictedIndex = currentIndex+currentVelocity;
					int finalIndex = currentIndex;
					int finalVelocity = currentVelocity;
					/* 
					 * AM > Iterate from current position to predicted position
					 *	to check for a clear path
					*/
					for(int j = 1; j <= predictedIndex && j <= currentVelocity; j++)
					{
						if(!nodes.get(currentIndex+j).isOccupied())
						{
							finalIndex = currentIndex +j;
							finalVelocity = j;
							break;
						}
					}
						
					nodes.get(currentIndex).setOccupied(false);
					nodes.get(currentIndex).setCar(null);
					
					c.setVelocity(finalVelocity);
					nodes.get(finalIndex).setOccupied(true);
					nodes.get(finalIndex).setCar(c);
					
		
				}
			}
		}
	}
	
	public String toString(){
		String state="";
		for(int i=0;i<nodes.size();i++){
			if(nodes.get(i).isOccupied()){
				state=state.concat("1 ");
			}
			else{
				state=state.concat("0 ");
			}
		}
		return state;
	}
}
