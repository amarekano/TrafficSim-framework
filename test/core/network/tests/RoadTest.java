package core.network.tests;

import static org.junit.Assert.*; 

import org.junit.Test;

import core.network.Road;
import core.network.interfaces.InterfaceException;
import core.network.junction.Junction;
import core.network.junction.Junction.JUNCTION;
import core.network.junction.JunctionException;
import core.network.junction.JunctionRouter;
import core.vehicle.Vehicle;
import core.vehicle.Car;
import core.vehicle.VehicleException;
import core.endpoints.Destination;
import core.endpoints.EndPointException;

public class RoadTest {

	@Test
	public void test_adding_car_to_empty_single_lane_road_should_return_true()
	{
		Road r1 = new Road(1, 10);
		Car c = new Car();
		assertEquals(true, r1.addVehicle(c));
	}

	@Test
	public void test_adding_car_to_occupied_single_lane_road_should_return_false()
	{
		Road r1 = new Road(1, 5);
		Car c1 = new Car();
		Car c2 = new Car();

		assertEquals(true, r1.addVehicle(c1));
		assertEquals(false, r1.addVehicle(c2));
	}

	@Test
	public void test_adding_cars_to_dual_lane_road()
	{
		Road r1 = new Road(2,5);
		Car c1 = new Car();
		Car c2 = new Car();

		assertEquals(true, r1.addVehicle(c1));
		assertEquals(true, r1.addVehicle(c2));
	}

	@Test
	public void test_adding_cars_to_multi_lane_road()
	{
		int max_lanes = 5;
		Road r1 = new Road(max_lanes,10);
		for(int i = 0; i < max_lanes; i++)
		{
			Car c = new Car();
			assertEquals(true,r1.addVehicle(c));
		}
	}

	@Test
	public void test_adding_car_to_a_specific_lane()
	{
		Road r1 = new Road(5, 10);
		Car c1 = new Car();

		assertEquals(true, r1.addVehicle(c1, 2));
		assertEquals(1,r1.getVehicleLaneIndex(c1));
	}

	@Test
	public void test_adding_car_to_a_specific_lane_check_car_index()
	{
		Road r1 = new Road(5, 10);
		Car c1 = new Car();

		assertEquals(true, r1.addVehicle(c1, 2));
		assertEquals(0,r1.getVehicleNodeIndex(c1));
	}

	@Test
	public void test_moving_cars_on_a_single_lane_road() throws EndPointException
	{
		Road r1 = new Road(1,5);
		Car c1 = new Car(2,0,10);
		Car c2 = new Car(1,0,10);
		r1.addVehicle(c1,1);
		r1.moveTraffic();
		assertEquals(0,r1.getVehicleLaneIndex(c1));
		assertEquals(2,r1.getVehicleNodeIndex(c1));

		r1.addVehicle(c2,1);
		r1.moveTraffic();
		assertEquals(0,r1.getVehicleLaneIndex(c1));
		assertEquals(4,r1.getVehicleNodeIndex(c1));

		assertEquals(0,r1.getVehicleLaneIndex(c2));
		assertEquals(1,r1.getVehicleNodeIndex(c2));
	}

	@Test
	public void test_moving_cars_on_a_multi_lane_road() throws EndPointException
	{
		Road r1 = new Road(3, 5);
		Car c1 = new Car(2,0,10);
		Car c2 = new Car(1,0,10);
		r1.addVehicle(c1,1);
		r1.moveTraffic();
		assertEquals(0,r1.getVehicleLaneIndex(c1));
		assertEquals(2,r1.getVehicleNodeIndex(c1));

		r1.addVehicle(c2,2);
		r1.moveTraffic();
		assertEquals(0,r1.getVehicleLaneIndex(c1));
		assertEquals(4,r1.getVehicleNodeIndex(c1));

		assertEquals(1,r1.getVehicleLaneIndex(c2));
		assertEquals(1,r1.getVehicleNodeIndex(c2));
	}

	@Test
	public void test_vehicles_move_from_one_destination_to_another() throws EndPointException, VehicleException
	{
		Road r1 = new Road(1, 10);
		Destination A = new Destination();
		Destination B = new Destination();

		r1.setSource(A);
		r1.setSink(B);

		Vehicle v1 = new Car(4,0,4);
		Vehicle v2 = new Car(2,1,10);

		A.addVehicle(v1);
		for(int i = 0; i < 10; i++)
		{
			r1.moveTraffic();
			if(i == 2)
				A.addVehicle(v2);
		}
		assertEquals(2, B.getConsumedQueueLength());
	}

	@Test
	public void test_moving_vehicles_across_a_two_interface_junction() throws InterfaceException, JunctionException, EndPointException, VehicleException 
	{
		Road r1 = new Road(4,5);
		Road r2 = new Road(2,10);
		Junction junc = new Junction();
		junc.disableInterface(JUNCTION.NORTH);
		junc.disableInterface(JUNCTION.SOUTH);
		Destination A = new Destination();
		Destination B = new Destination();

		//AM > Road-Junction wiring
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.EAST);
		r2.setSink(B);

		//AM > Set lights to green
		junc.getInterface(JUNCTION.WEST).setSignalState(junc.getInterface(JUNCTION.EAST), true);

		//AM > Setup routing information
		JunctionRouter juncR = new JunctionRouter();
		juncR.add(A, junc.getInterface(JUNCTION.WEST));
		juncR.add(B, junc.getInterface(JUNCTION.EAST));
		junc.setRoutingTable(juncR);

		//AM > Create vehicle and set destination
		Vehicle v1 = new Car(2,0,2);
		Vehicle v2 = new Car(1,2,3);
		Vehicle v3 = new Car(2,2,10);
		v1.setDestination(B);
		v2.setDestination(B);
		v3.setDestination(B);

		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		for(int i = 0; i < 20; i++)
		{
			r2.moveTraffic();
			r1.moveTraffic();
		}

		assertEquals(3, B.getConsumedQueueLength());
	}

	@Test
	public void test_moving_vehicles_across_a_two_interface_junction_when_the_signal_is_red() throws InterfaceException, JunctionException, EndPointException, VehicleException
	{
		Road r1 = new Road(1,5);
		Road r2 = new Road(1,5);
		Junction junc = new Junction();
		junc.disableInterface(JUNCTION.NORTH);
		junc.disableInterface(JUNCTION.SOUTH);
		Destination A = new Destination();
		Destination B = new Destination();

		//AM > Road-Junction wiring
		r1.setSource(A);
		r1.setSink(junc, JUNCTION.WEST);
		r2.setSource(junc, JUNCTION.EAST);
		r2.setSink(B);

		//AM > Setup routing information
		JunctionRouter juncR = new JunctionRouter();
		juncR.add(A, junc.getInterface(JUNCTION.WEST));
		juncR.add(B, junc.getInterface(JUNCTION.EAST));
		junc.setRoutingTable(juncR);

		//AM > Create vehicle and set destination
		Vehicle v1 = new Car(2,0,2);
		Vehicle v2 = new Car(1,2,3);
		Vehicle v3 = new Car(2,2,10);
		v1.setDestination(B);
		v2.setDestination(B);
		v3.setDestination(B);

		A.addVehicle(v1);
		A.addVehicle(v2);
		A.addVehicle(v3);
		for(int i = 0; i < 20; i++)
		{
			r2.moveTraffic();
			r1.moveTraffic();
		}
		
		assertEquals(4, r1.getVehicleNodeIndex(v1));
		assertEquals(3, r1.getVehicleNodeIndex(v2));
		assertEquals(2, r1.getVehicleNodeIndex(v3));

	}
}
