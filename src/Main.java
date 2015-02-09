import java.util.List;

import core.endpoints.Destination;
import core.endpoints.InvalidEndPointException;
import core.network.Lane;
import core.network.Road;
import core.vehicle.Car;
import core.vehicle.Vehicle;


public class Main {
	

	public static void main(String[] args) {	
		int laneLength=20;
		int numOfLanes=5;
		
		//We create 2 roads
		Road r1 = new Road(numOfLanes, laneLength);
		Road r2 = new Road(3, laneLength);
		
		//We create 3 destinations
		//It will look like this: |A| ----- |B| ----- |C|
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		
		r1.setSource(A);
		r1.setSink(B);
		
		r2.setSource(B);
		r2.setSink(C);
		
		Vehicle v1 = new Car(2,0,4);
		Vehicle v2 = new Car(1,1,10);
		
		Vehicle v3 = new Car(1,0,10);
		Vehicle v4 = new Car(1,0,10);
		Vehicle v5 = new Car(1,0,10);
		
		A.addVehicle(v1);
		System.out.println("Traffic Simulator");
		
		for(int i = 0; i < 20; i++)
		{
			System.out.println("\nTick "+i);
			List<Lane> lanes = r1.getLanes();
			List<Lane> lanes2 = r2.getLanes();

			int max= lanes.size()>lanes2.size() ? lanes.size() : lanes2.size();
			for(int j = 0; j < max; j++){
				
				if(j < lanes.size())
					System.out.printf("|A|%s|B|", lanes.get(j));
				else
					System.out.printf("|A| %s |B|", "no lane");
				if(j < lanes2.size())
					System.out.printf("%s|C|\n", lanes2.get(j));
				else
					System.out.printf("%s |C|\n", "no lane");
			}
			try {
				r2.moveTraffic();
				r1.moveTraffic();
				
			} catch (InvalidEndPointException e) {
				e.printStackTrace();
			}
			if(i == 2){
				A.addVehicle(v2);
			}
			
			if(i == 3){
				A.addVehicle(v3);
			}
			
			if(i == 4){
				A.addVehicle(v4);
			}
			if(i == 5){
				A.addVehicle(v5);
			}
				
			
			
		}
	}
	
}
