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
		List<Node> duplicateNodes = new ArrayList<Node>(maxLength);
		for(int i=0;i<nodes.size();i++){
			Node node=new Node();
			duplicateNodes.add(node);
		}
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
					int newIndex = currentIndex+currentVelocity;
					if(newIndex < maxLength && duplicateNodes.get(newIndex).isOccupied() == false)
					{
						nodes.get(currentIndex).setOccupied(false);
						nodes.get(currentIndex).setCar(null);
						duplicateNodes.get(newIndex).setCar(c);
						duplicateNodes.get(newIndex).setOccupied(true);
					}
					else
					{
						//If the predicted node is occupied then
						int reducedVelocity = 0;
						//Calculate the new velocity for the car
						for(; (reducedVelocity+1 < maxLength) && !duplicateNodes.get(reducedVelocity+1).isOccupied(); reducedVelocity++);
						
						c.setVelocity(reducedVelocity);
						newIndex = currentIndex + reducedVelocity;
						duplicateNodes.get(newIndex).setOccupied(true);
						duplicateNodes.get(newIndex).setCar(c);
						
						nodes.get(currentIndex).setOccupied(false);
						nodes.get(currentIndex).setCar(null);
					}
				}
			}
		}
		
		nodes = new ArrayList<Node>(duplicateNodes);
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
