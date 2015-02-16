package core.vehicle.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import core.vehicle.Bus;
import core.vehicle.Car;
import core.vehicle.Vehicle;

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

}
