package core.vehicle.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import core.endpoints.Destination;
import core.network.Lane;
import core.network.Road;
import core.timer.MyTimer;
import core.vehicle.Bus;
import core.vehicle.Car;

public class TimestampTest {

	@Test
	public void test_timestamp() {
		int laneLength=20;
		int numOfLanes=5;
		Road road = new Road(numOfLanes, laneLength);
		Destination A = new Destination();
		Destination B = new Destination();
		
		road.setSource(A);
		road.setSink(B);
		
		Car c1 = new Car(1,0,4);
		
		Bus b1 = new Bus(2,0,10);
		
		A.addVehicle(c1);
		
		for(int i = 0; i < 30; i++)
		{
			System.out.println("\nTick "+i);
			List<Lane> lanes = road.getLanes();

			for(int j = 0; j < lanes.size(); j++){
				if(j < lanes.size())
					System.out.printf("|A|%s|B|", lanes.get(j));
			}
			try {
				road.moveTraffic();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(i == 3){
				A.addVehicle(b1);
			}
				
			MyTimer.time=MyTimer.time+1;
			
			
		}
		System.out.println();
		System.out.println("c1.getStartTime()"+c1.getStartTime());
		System.out.println("c1.getEndTime()"+c1.getEndTime());
		
		System.out.println("b1.getStartTime()"+b1.getStartTime());
		System.out.println("b1.getEndTime()"+b1.getEndTime());
		
		assertEquals(0,c1.getStartTime());
		assertEquals(19,c1.getEndTime());
		
		assertEquals(4,b1.getStartTime());
		assertEquals(13,b1.getEndTime());
	}

}
