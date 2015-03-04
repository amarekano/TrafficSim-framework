package service.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
import core.endpoints.EndPointException;
import core.network.Road;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.VehicleException;
import service.Network;
import service.clock.SimulationClock;

public class SimulationClockTest {

	@Test
	public void test_only_one_clock_instance_should_exist() {
		SimulationClock clock = SimulationClock.getInstance();
		SimulationClock clock2 = SimulationClock.getInstance();
		assertEquals(clock,clock2);
	}
	
	@Test
	public void test_roads_move_traffic_based_on_simulation_clock() throws EndPointException, VehicleException, InterruptedException {
		int laneLength=5;
		int numOfLanes=1;
		Road road = new Road(numOfLanes, laneLength);
		Destination A = new Destination();
		Destination B = new Destination();
		SimulationClock clock = SimulationClock.getInstance();
		
		A.setClock(clock);
		B.setClock(clock);
		road.setSource(A);
		road.setSink(B);
		
		Car c1 = new Car();
		
		Bus b1 = new Bus();
		A.addVehicle(c1);
		A.addVehicle(b1);
		Network network= new Network();
		network.addRoad(road);
		clock.addObserver(network);
		clock.resetClock();
		clock.startClock();

		Thread.sleep(10*1000);

		assertEquals(2, B.getConsumedQueueLength());
		
		assertEquals(-1,road.getVehicleNodeIndex(c1));
		assertEquals(-1,road.getVehicleNodeIndex(b1));
	}
	
	@Test
	public void test_suspend_and_resume_simulation_clock() throws InterruptedException, VehicleException{
		int laneLength=5;
		int numOfLanes=1;
		Road road = new Road(numOfLanes, laneLength);
		Destination A = new Destination();
		Destination B = new Destination();
		SimulationClock clock = SimulationClock.getInstance();
		
		A.setClock(clock);
		B.setClock(clock);
		road.setSource(A);
		road.setSink(B);
		
		Car c1 = new Car();
		
		Bus b1 = new Bus();
		A.addVehicle(c1);
		A.addVehicle(b1);
		Network network= new Network();
		network.addRoad(road);
		clock.addObserver(network);
		clock.resetClock();
		clock.startClock();
		
		Thread.sleep(5*1000);
		clock.pauseClock();
		assertEquals(5, clock.getTime());

		clock.resumeClock();
		Thread.sleep(10*1000);

		assertEquals(-1,road.getVehicleNodeIndex(c1));
		assertEquals(-1,road.getVehicleNodeIndex(b1));
	}
	
	@Test
	public void test_changing_tick_interval() throws InterruptedException
	{
		SimulationClock clock = SimulationClock.getInstance();
		clock.resetClock();
		clock.startClock();
		Thread.sleep(5*1000);
		assertEquals(5, clock.getTime());
		assertEquals(1000, clock.getInterval());
		
		clock.setInterval(2000);
		clock.resetClock();
		Thread.sleep(6*1000);
		assertEquals(3, clock.getTime());
		assertEquals(2000, clock.getInterval());
	}
}
