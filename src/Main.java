import core.*;

public class Main {

	public static void main(String[] args) {
		Lane lane=new Lane(10);
		System.out.println(lane);
		Car car1=new Car();
		Car car2=new Car();
		Car car3=new Car();
		System.out.println("Adding car to network");
		
		String result = lane.addCar(car1) ? "Car1 added" : "Unable to add Car1";
		System.out.println(result);
		
		System.out.println(lane);
		System.out.println("Adding another car");
		result = lane.addCar(car2) ? "Car2 added" : "Unable to add Car2";
		System.out.println(result);
		
		System.out.println(lane);
		System.out.println("Moving cars");
		lane.moveCars();
		System.out.println("Cars moved");
		System.out.println(lane);
		result = lane.addCar(car2) ? "Car2 added" : "Unable to add Car2";
		System.out.println(result);
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
