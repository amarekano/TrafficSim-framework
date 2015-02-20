import java.util.List;
import java.util.TimerTask;

import services.SimulationClock;
import core.endpoints.Destination;
import core.network.Lane;
import core.network.Road;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;
import core.vehicle.VehicleException;

public class Main extends Thread{
	
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
		 
		SimulationClock clock = new SimulationClock();
			
		A.setClock(clock);
		B.setClock(clock);
		C.setClock(clock);
		
		r1.setSource(A);
		r1.setSink(B);
		
		r2.setSource(B);
		r2.setSink(C);
		
		Vehicle v1 = new Car(2,0,4);
		Vehicle v2 = new Car(1,1,10);
		
		Vehicle v3 = new Car(1,0,10);
		Vehicle v4 = new Car(1,0,10);
		Vehicle v5 = new Car(1,0,10);
		
		Vehicle v6 = new Bus(2,0,10);
		Vehicle v7 = new Bus(3,0,10);
		Vehicle v8 = new Bus(1,0,10);
		
		Vehicle c9 = new Car(3,0,10);
		
		try {
			A.addVehicle(v1);
		} catch (VehicleException e1) {
			e1.printStackTrace();
		}
		System.out.println("Traffic Simulator");
		
		for(int i = 0; i < 30; i++)
		{
			System.out.println("\nTick "+clock.getTime());
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
				
				if(i == 6){
					A.addVehicle(v6);
				}
				
				if(i == 7){
					A.addVehicle(v7);
				}
				if(i == 8){
					A.addVehicle(v8);
				}
				if(i == 9){
					A.addVehicle(c9);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
				
			clock.incrementClock();
		}
	}
}

