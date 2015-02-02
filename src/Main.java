import core.Lane;
import core.Car;


public class Main {

	public static void main(String[] args) {
		int laneLength=20;
		Lane lane=new Lane(laneLength);
		System.out.println(lane);
		Car car1=new Car();
		Car car2=new Car();
		Car car3=new Car(1,2,20);
		
		Car car4=new Car(2,1,20);
		
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
		
		for(int i =0; i < laneLength; i++)
		{
			lane.moveCars();
			if(i == 3)
				lane.addCar(car3);
			if(i == 7)
				lane.addCar(car4);
			System.out.println(lane);
		}
	}
	
}
