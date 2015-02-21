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
import service.clock.SystemClock;
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
		
		SystemClock clk1 = new SystemClock();
		clk1.start();
		Thread.sleep(10*1000);

		assertEquals(2, B.getConsumedQueueLength());
		
		assertEquals(-1,road.getVehicleNodeIndex(c1));
		assertEquals(-1,road.getVehicleNodeIndex(b1));
		clk1.terminate();
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
		
		SystemClock clk1 = new SystemClock();
		clk1.start();
		clk1.suspend();
		Thread.sleep(5*1000);
		clk1.resume();
		Thread.sleep(10*1000);

		assertEquals(-1,road.getVehicleNodeIndex(c1));
		assertEquals(-1,road.getVehicleNodeIndex(b1));
		clk1.terminate();
	}
}
