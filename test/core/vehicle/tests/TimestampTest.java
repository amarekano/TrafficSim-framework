package core.vehicle.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import service.SimulationClock;
import core.endpoints.Destination;
import core.endpoints.EndPointException;
import core.network.Road;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.VehicleException;

public class TimestampTest {

	/*@Test
	public void test_timestamp() throws EndPointException, VehicleException {
		int laneLength=5;
		int numOfLanes=1;
		Road road = new Road(numOfLanes, laneLength);
		Destination A = new Destination();
		Destination B = new Destination();
		SimulationClock clock = SimulationClock.getInstance();
		clock.resetClock();
		A.setClock(clock);
		B.setClock(clock);
		road.setSource(A);
		road.setSink(B);
		
		Car c1 = new Car();
		
		Bus b1 = new Bus();
		A.addVehicle(c1);
		
		for(int i = 0; i < 30; i++)
		{
			road.moveTraffic();
			if(i == 2){
				A.addVehicle(b1);
			}
			clock.incrementClock();
		}
		assertEquals(0,c1.getStartTime());
		assertEquals(4,c1.getEndTime());
		
		assertEquals(2,b1.getStartTime());
		assertEquals(6,b1.getEndTime());
		
		assertEquals(-1,road.getVehicleNodeIndex(c1));
		assertEquals(-1,road.getVehicleNodeIndex(b1));
	}*/

}
