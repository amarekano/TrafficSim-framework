import core.*;

public class Main {

	public static void main(String[] args) {
		Lane lane=new Lane(10);
		System.out.println(lane);
		Car car1=new Car();
		Car car2=new Car();
		Car car3=new Car();
		System.out.println("Adding car to network");
		
		String result = lane.addCar(car1) ? "Car added" : "Unable to add Car";
		System.out.println(result);
		
		System.out.println(lane);
		System.out.println("Adding another car");
		result = lane.addCar(car2) ? "Car added" : "Unable to add Car";
		System.out.println(result);
		
		System.out.println(lane);
		System.out.println("Moving cars");
		lane.moveCars();
		System.out.println(lane);
		result = lane.addCar(car2) ? "Car added" : "Unable to add Car";
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
