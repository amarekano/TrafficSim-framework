import core.*;

public class Main {

	public static void main(String[] args) {
		Lane lane=new Lane(10);
		System.out.println(lane);
		Car car1=new Car();
		Car car2=new Car();
		Car car3=new Car();
		System.out.println("Adding car to network");
		if(lane.addCar(car1))
		{
			System.out.println("Car added to network");
		}
		else
		{
			System.out.println("Unable to add car to network");
		}
		System.out.println(lane);
		System.out.println("Adding another car");
		
		if(lane.addCar(car2))
		{
			System.out.println("Car added to network");
		}
		else
		{
			System.out.println("Unable to add car to network");
		}
		System.out.println(lane);
		System.out.println("Moving cars");
		lane.moveCars();
		System.out.println(lane);
		if(lane.addCar(car2))
		{
			System.out.println("Car added to network");
		}
		else
		{
			System.out.println("Unable to add car to network");
		}
		
		System.out.println(lane);
		
		for(int i =0; i < 10; i++)
		{
			lane.moveCars();
			if(i == 1)
				lane.addCar(car3);
			System.out.println(lane);
		}
	}
	
}
