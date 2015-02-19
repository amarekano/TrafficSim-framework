package core.endpoints.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;
import core.vehicle.VehicleException;

public class DestinationTest {

	@Test
	public void adding_a_vehicle_to_a_destination_should_increase_waiting_queue_length() throws VehicleException
	{
		Destination A = new Destination();
		A.addVehicle(new Car());
		A.addVehicle(new Car());
		
		assertEquals(2, A.getWaitingQueueLength());
	}
	@Test
	public void pulling_a_vehicle_from_the_queue_should_reduce_waiting_queue_length() throws VehicleException
	{
		Destination A = new Destination();
		A.addVehicle(new Car());
		A.addVehicle(new Car());
		
		assertEquals(2, A.getWaitingQueueLength());
		
		Vehicle v = A.getWaitingVehicle();
		A.releaseVehicle(v);
		
		assertEquals(1, A.getWaitingQueueLength());
	}

	@Test
	public void Destination_should_maintain_two_queues_for_ingress_and_egress_traffic() throws VehicleException
	{
		Destination A = new Destination();
		A.addVehicle(new Car());
		A.addVehicle(new Car());
		A.addVehicle(new Car());
		
		for(int i = 0; i < 2; i++)
		{
			Vehicle v = A.getWaitingVehicle();
			A.releaseVehicle(v);
			A.consumeVehicle(v);
		}
		
		assertEquals(2, A.getConsumedQueueLength());
		assertEquals(1, A.getWaitingQueueLength());
	}
	
	@Test
	public void test_destinations_should_have_a_label()
	{
		Destination A = new Destination("A");
		assertEquals(true, A.getLabel().equalsIgnoreCase("A"));
	}
	
	@Test
	public void test_adding_a_vehicle_to_a_destiantion_sets_its_source_destination() throws VehicleException
	{
		Destination A = new Destination("A");
		Vehicle v1 = new Car();
		Vehicle v2 = new Bus();
		A.addVehicle(v1);
		A.addVehicle(v2);
		
		assertEquals(A, v1.getSource());
		assertEquals(A, v2.getSource());
		
	}
}
