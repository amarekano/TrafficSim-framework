import core.*;

public class Main {

	public static void main(String[] args) {
		/*
		 * AM > Simulate single lane traffic with constant speed cars
		 * NC > P
		 */

		Node start = new Node();
		Node currentNode = start;
		
		//Create a road with 5 nodes
		System.out.println("Building Road");
		for(int i = 0; i < 9; i++)
		{
			Node node = new Node();
			currentNode.setNextNode(node);
			currentNode = node;
		}
		
		System.out.println("Road complete");
		
		Car car = new Car();
		
		//Putting car on the road
		start.setCar(car);
		start.setOccupied(true);
		
		//Print the state of the road
		currentNode = start;
		while(currentNode != null)
		{
			if(currentNode.isOccupied())
				System.out.print("1 ");
			else
				System.out.print("0 ");
			
			//move the link along
			currentNode = currentNode.getNextNode();
		}
		System.out.println();
		
		//Move the car by one node
		currentNode=start;
		while(currentNode != null)
		{
			if(currentNode.isOccupied()){
				Car tempCar = currentNode.getCar();
				int velocity = tempCar.getVelocity();
				
				//Find if the node after velocity units is empty
				Node temp = currentNode;
				for(int i = 0; i < velocity; i++)
				{
					temp = temp.getNextNode();
				}
				
				//check if node is empty
				if(temp != null && !temp.isOccupied())
				{
					temp.setCar(tempCar);
					temp.setOccupied(true);
					currentNode.setCar(null);
					currentNode.setOccupied(false);
				}
				else
				{
					System.out.println("Not moving car... node is occupied");
				}
			}
			
			currentNode = currentNode.getNextNode();
		}
		
		currentNode = start;
		while(currentNode != null)
		{
			if(currentNode.isOccupied())
				System.out.print("1 ");
			else
				System.out.print("0 ");
			
			//move the link along
			currentNode = currentNode.getNextNode();
		}
	}

	
}
