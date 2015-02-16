package core.endpoints.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
import core.vehicle.Car;
import core.vehicle.Vehicle;

public class DestinationTest {

	@Test
	public void adding_a_vehicle_to_a_destination_should_increase_waiting_queue_length()
	{
		Destination A = new Destination();
		A.addVehicle(new Car());
		A.addVehicle(new Car());
		
		assertEquals(2, A.getWaitingQueueLength());
	}
	@Test
	public void pulling_a_vehicle_from_the_queue_should_reduce_waiting_queue_length()
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
	public void Destination_should_maintain_two_queues_for_ingress_and_egress_traffic()
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
}
