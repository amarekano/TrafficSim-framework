import java.util.List;
import java.util.TimerTask;

import core.endpoints.Destination;
import core.network.Lane;
import core.network.Road;
import core.timer.MyTimer;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;


public class Main extends Thread{
	static Vehicle v1,v2,v3,v4,v5,v6,v7,v8,c9;
	static Road r1,r2;
	static Destination A,B,C;
	static int i=0;
	static MyTimer timer;

	public static void main(String[] args) {	
		int laneLength=20;
		int numOfLanes=5;
		
		//We create 2 roads
		 r1 = new Road(numOfLanes, laneLength);
		 r2 = new Road(3, laneLength);
		
		//We create 3 destinations
		//It will look like this: |A| ----- |B| ----- |C|
		 A = new Destination();
		 B = new Destination();
		 C = new Destination();
		
		r1.setSource(A);
		r1.setSink(B);
		
		r2.setSource(B);
		r2.setSink(C);
		
		v1 = new Car(2,0,4);
		 v2 = new Car(1,1,10);
		
		 v3 = new Car(1,0,10);
		 v4 = new Car(1,0,10);
		 v5 = new Car(1,0,10);
		
		 v6 = new Bus(2,0,10);
		 v7 = new Bus(3,0,10);
		 v8 = new Bus(1,0,10);
		
		 c9 = new Car(3,0,10);
		//Vehicle v10 = new Car(2,0,10);
		//Vehicle v11 = new Car(1,1,10);
		
		A.addVehicle(v1);
		System.out.println("Traffic Simulator");
		//new Timer().start();
		//timer = new Timer();
		new Thread(run1).start();;
		 //start();
		/*
		for(int i = 0; i < 30; i++)
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
				
			} catch (Exception e) {
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
				
			
			
		}*/
	}
	
	static Runnable run1 = new Runnable() {
		  public void run() {
		    try {
		      while (true) {
		        //System.out.println("Hello, world!");
		        Thread.sleep(1000);
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
					
				} catch (Exception e) {
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
				i++;
				MyTimer.time=i;
			}
		    } catch (InterruptedException iex) {}
		  }
		};

		/*public int getTime(){
			return i;
		}*/
	
}

