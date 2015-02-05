import core.network.Lane;
import core.vehicle.Car;
import core.vehicle.Vehicle;


public class Main {

	public static void main(String[] args) {
		int laneLength=20;
		Lane lane=new Lane(laneLength);
		System.out.println(lane);
		Vehicle car1=new Car();
		Vehicle car2=new Car();
		Vehicle car3=new Car(1,2,20);
		
		Vehicle car4=new Car(2,1,20);
		
		System.out.println("Adding car to network");
		
		String result = lane.addVehicle(car1) ? "Car1 added" : "Unable to add Car1";
		System.out.println(result);
		
		System.out.println(lane);
		System.out.println("Adding another car");
		result = lane.addVehicle(car2) ? "Car2 added" : "Unable to add Car2";
		System.out.println(result);
		
		System.out.println(lane);
		System.out.println("Moving cars");
		lane.moveVehicles();
		System.out.println("Cars moved");
		System.out.println(lane);
		result = lane.addVehicle(car2) ? "Car2 added" : "Unable to add Car2";
		System.out.println(result);
		System.out.println(lane);
		
		for(int i =0; i < laneLength; i++)
		{
			lane.moveVehicles();
			if(i == 3)
				lane.addVehicle(car3);
			if(i == 7)
				lane.addVehicle(car4);
			System.out.println(lane);
		}
	}
	
}
