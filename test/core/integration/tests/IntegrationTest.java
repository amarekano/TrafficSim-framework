package core.integration.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import service.Network;
import service.TrafficSignalScheduler;
import service.clock.SimulationClock;
import core.endpoints.Destination;
import core.endpoints.EndPointException;
import core.network.Road;
import core.network.interfaces.InterfaceException;
import core.network.junction.Junction;
import core.network.junction.Junction.JUNCTION;
import core.network.junction.JunctionRouter;
import core.vehicle.Car;
import core.vehicle.Vehicle;
import core.vehicle.VehicleException;

public class IntegrationTest {

	@Test
	public void test_junction_transfer_to_three_destinations() throws InterfaceException, EndPointException, VehicleException 
	{
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		Road r1 = new Road(1,3);
		Road r2 = new Road(1,3);
		Road r3 = new Road(1,3);
		Road r4 = new Road(1,3);
		
		Junction junc = new Junction();
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		/*
		 * AM > Road-junction wiring
		 * 			  |B|
		 * 			   |
		 * 			   r2
		 * 			   |
		 * |A|--r1---|junc|---r3--|C|
		 * 			   |
		 * 			  r4
		 * 			   |
		 * 			  |D|
		 */
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.NORTH);
		r2.setSink(B);
		r3.setSource(junc, JUNCTION.EAST);
		r3.setSink(C);
		r4.setSource(junc, JUNCTION.SOUTH);
		r4.setSink(D);
		
		//AM > Setup routing table
		JunctionRouter juncRouter = new JunctionRouter();
		juncRouter.add(A, junc.getInterface(JUNCTION.WEST));
		juncRouter.add(B, junc.getInterface(JUNCTION.NORTH));
		juncRouter.add(C, junc.getInterface(JUNCTION.EAST));
		juncRouter.add(D,  junc.getInterface(JUNCTION.SOUTH));
		junc.setRoutingTable(juncRouter);
		
		//AM > Setup traffic signals
		junc.getInterface(JUNCTION.WEST).setSignalState(junc.getInterface(JUNCTION.EAST), true);
		junc.getInterface(JUNCTION.WEST).setSignalState(junc.getInterface(JUNCTION.NORTH), true);
		junc.getInterface(JUNCTION.WEST).setSignalState(junc.getInterface(JUNCTION.SOUTH), true);
		
		//AM > Setup vehicle destinations
		v1.setDestination(B);
		v2.setDestination(C);
		v3.setDestination(D);
		
		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		
		for(int i = 0; i < 7; i++)
		{
			r1.moveTraffic();
			r2.moveTraffic();
			r3.moveTraffic();
			r4.moveTraffic();
		}
		
		assertEquals(1, B.getConsumedQueueLength());
		assertEquals(1, C.getConsumedQueueLength());
		assertEquals(1, D.getConsumedQueueLength());
	}

	@Test
	public void test_bidirectional_traffic_between_two_destinations() throws EndPointException, VehicleException
	{
		Destination A = new Destination();
		Destination B = new Destination();
		
		Road r1 = new Road(1,3);
		Road r2 = new Road(1,4);
		
		/*
		 * AM > Setup wiring
		 * |A|-->--r1-->--|B|
		 * |A|--<--r2--<--|B|
		 */
		r1.setSource(A);
		r1.setSink(B);
		r2.setSource(B);
		r2.setSink(A);
		
		//AM > Add 5 vehicles to A
		for(int i = 0; i < 5; i++)
			A.addVehicle(new Car());
		//AM > Add 3 vehicles to B with speed of 2
		for(int i = 0; i < 3; i++)
			B.addVehicle(new Car(2,0,2));
		
		for(int i = 0; i < 8; i++)
		{
			r1.moveTraffic();
			r2.moveTraffic();
		}
		
		assertEquals(3,A.getConsumedQueueLength());
		assertEquals(5,B.getConsumedQueueLength());
	}

	public void test_traffic_signals_should_direct_traffic() throws InterfaceException, VehicleException, EndPointException
	{
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		Road r1 = new Road(1,3);
		Road r2 = new Road(1,3);
		Road r3 = new Road(1,3);
		Road r4 = new Road(1,3);
		
		Junction junc = new Junction();
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		/*
		 * AM > Road-junction wiring
		 * 			  |B|
		 * 			   |
		 * 			   r2
		 * 			   |
		 * |A|--r1---|junc|---r3--|C|
		 * 			   |
		 * 			  r4
		 * 			   |
		 * 			  |D|
		 */
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.NORTH);
		r2.setSink(B);
		r3.setSource(junc, JUNCTION.EAST);
		r3.setSink(C);
		r4.setSource(junc, JUNCTION.SOUTH);
		r4.setSink(D);
		
		//AM > Setup routing table
		JunctionRouter juncRouter = new JunctionRouter();
		juncRouter.add(A, junc.getInterface(JUNCTION.WEST));
		juncRouter.add(B, junc.getInterface(JUNCTION.NORTH));
		juncRouter.add(C, junc.getInterface(JUNCTION.EAST));
		juncRouter.add(D,  junc.getInterface(JUNCTION.SOUTH));
		junc.setRoutingTable(juncRouter);
		
		//AM > Setup traffic signals
		junc.setSignalController();
		
		//AM > Setup vehicle destinations
		v1.setDestination(B);
		v2.setDestination(C);
		v3.setDestination(D);
		
		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		
		for(int i = 0; i < 7; i++)
		{
			r1.moveTraffic();
			r2.moveTraffic();
			r3.moveTraffic();
			r4.moveTraffic();
			junc.getSignalController().changeSignals();
		}
		
		assertEquals(1, B.getConsumedQueueLength());
		assertEquals(1, C.getConsumedQueueLength());
		assertEquals(1, D.getConsumedQueueLength());
	}

	@Test
	public void test_bidirectional_traffic_using_simulation_clock() throws VehicleException, EndPointException, InterruptedException
	{
		Destination A = new Destination();
		Destination B = new Destination();
		
		Road r1 = new Road(1,3);
		Road r2 = new Road(1,4);
		
		/*
		 * AM > Setup wiring
		 * |A|-->--r1-->--|B|
		 * |A|--<--r2--<--|B|
		 */
		r1.setSource(A);
		r1.setSink(B);
		r2.setSource(B);
		r2.setSink(A);
		
		//AM > Add 5 vehicles to A
		for(int i = 0; i < 5; i++)
			A.addVehicle(new Car());
		//AM > Add 3 vehicles to B with speed of 2
		for(int i = 0; i < 3; i++)
			B.addVehicle(new Car(2,0,2));
		
		SimulationClock clock = SimulationClock.getInstance();
		clock.setInterval(100);
		clock.resetClock();
		A.setClock(clock);
		B.setClock(clock);
		
		Network network = new Network();
		network.addRoad(r1);
		network.addRoad(r2);
		clock.addObserver(network);
		
		clock.startClock();
		Thread.sleep(8*100);
		assertEquals(3,A.getConsumedQueueLength());
		assertEquals(5,B.getConsumedQueueLength());
	}
	

	@Test
	public void test_junction_transfer_using_simulation_clock() throws InterfaceException, VehicleException, InterruptedException
	{
		Destination A = new Destination();
		Destination B = new Destination();
		Destination C = new Destination();
		Destination D = new Destination();
		
		Road r1 = new Road(1,3);
		Road r2 = new Road(1,3);
		Road r3 = new Road(1,3);
		Road r4 = new Road(1,3);
		
		Junction junc = new Junction();
		
		Vehicle v1 = new Car();
		Vehicle v2 = new Car();
		Vehicle v3 = new Car();
		
		/*
		 * AM > Road-junction wiring
		 * 			  |B|
		 * 			   |
		 * 			   r2
		 * 			   |
		 * |A|--r1---|junc|---r3--|C|
		 * 			   |
		 * 			  r4
		 * 			   |
		 * 			  |D|
		 */
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.NORTH);
		r2.setSink(B);
		r3.setSource(junc, JUNCTION.EAST);
		r3.setSink(C);
		r4.setSource(junc, JUNCTION.SOUTH);
		r4.setSink(D);
		
		//AM > Setup routing table
		JunctionRouter juncRouter = new JunctionRouter();
		juncRouter.add(A, junc.getInterface(JUNCTION.WEST));
		juncRouter.add(B, junc.getInterface(JUNCTION.NORTH));
		juncRouter.add(C, junc.getInterface(JUNCTION.EAST));
		juncRouter.add(D,  junc.getInterface(JUNCTION.SOUTH));
		junc.setRoutingTable(juncRouter);
		
		//AM > Setup signal scheduler
		junc.setSignalController();
		TrafficSignalScheduler scheduler = new TrafficSignalScheduler();
		scheduler.setSignalInterval(5);
		scheduler.addSignalController(junc.getSignalController());
		
		//AM > Setup vehicle destinations
		v1.setDestination(B);
		v2.setDestination(C);
		v3.setDestination(D);
		
		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		
		Network network = new Network();
		network.addRoad(r1);
		network.addRoad(r2);
		network.addRoad(r3);
		network.addRoad(r4);
		
		SimulationClock clock = SimulationClock.getInstance();
		A.setClock(clock);
		B.setClock(clock);
		C.setClock(clock);
		D.setClock(clock);
		
		clock.setInterval(100);
		clock.resetClock();
		
		clock.addObserver(network);
		clock.addObserver(scheduler);
		clock.startClock();
		
		Thread.sleep(5*1000);

		assertEquals(1, B.getConsumedQueueLength());
		assertEquals(1, C.getConsumedQueueLength());
		assertEquals(1, D.getConsumedQueueLength());
	}
}
