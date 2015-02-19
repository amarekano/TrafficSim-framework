package core.vehicle.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.endpoints.Destination;
import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;
import core.vehicle.VehicleException;

public class VehicleCreationTest {

	@Test
	public void test_creating_a_car() {
		Vehicle v= new Car();
		assertEquals(1,v.getLength());
	}
	
	@Test
	public void test_creating_a_bus() {
		Vehicle v= new Bus();
		assertEquals(2,v.getLength());
	}
	
	@Test(expected=VehicleException.class)
	public void test_source_and_destination_cannot_be_the_same() throws VehicleException
	{
		Destination A = new Destination();
		Vehicle v1 = new Bus();
		v1.setSource(A);
		v1.setDestination(A);
	}
	
	@Test(expected=VehicleException.class)
	public void test_source_and_destination_cannot_be_the_same2() throws VehicleException
	{
		Destination A = new Destination();
		Vehicle v1 = new Bus();
		A.addVehicle(v1);
		v1.setDestination(A);
	}
}
